package io.futurify.authentication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.futurify.authentication.controller.exception.AuthorizeException;
import io.futurify.authentication.repository.UsersRepository;
import io.futurify.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UsersRepository usersRepository;



  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public io.futurify.authentication.model.User signIn(String username, String password) {
    log.info("signIn");
    io.futurify.authentication.model.User user = usersRepository.signIn(username, password).orElse(null);
    if (user == null) {
      throw new AuthorizeException("Invalid email or password.");
    }
    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    io.futurify.authentication.model.User user = usersRepository.findByEmail(email).orElse(null);
    log.info("loadUserByUsername");
    if (user == null) {
      throw new UsernameNotFoundException("Invalid email or password.");
    }
    
    List<SimpleGrantedAuthority> permission = user.getPermissions().stream()
        .map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
    String hash = bCryptPasswordEncoder.encode(user.getPassword());

    return new User(email, hash, permission);
  }

}
