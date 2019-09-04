package com.example.demo.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.config.JwtTokenProvider;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService, UserDetailsService {

  private final UsersRepository usersRepository;

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UsersServiceImpl(UsersRepository usersRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider) {
    this.usersRepository = usersRepository;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public String signIn(String email, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
      log.info("signIn");
      Users user = usersRepository.signIn(email, password).orElse(null);
      if (user == null || user.getRole() == null) {
        throw new RuntimeException("Invalid email or password.");
      }
      return jwtTokenProvider.generateToken(email, user);
    } catch (AuthenticationException e) {
      throw new RuntimeException("Invalid email or password.");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Users user = usersRepository.findByEmail(email).orElse(null);
    log.info("loadUserByUsername");
    if (user == null) {
      throw new UsernameNotFoundException("Invalid email or password.");
    }

    List<Role> roles = Arrays.asList(new Role[] {user.getRole()});
    List<SimpleGrantedAuthority> permission = roles.stream()
        .map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    String hash = bCryptPasswordEncoder.encode(user.getPassword());

    return new User(email, hash, permission);
  }

}
