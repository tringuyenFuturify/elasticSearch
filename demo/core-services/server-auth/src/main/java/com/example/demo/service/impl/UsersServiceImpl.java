package com.example.demo.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.example.demo.config.JwtTokenProvider;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  public UsersServiceImpl(UsersRepository usersRepository,
      AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
    this.usersRepository = usersRepository;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public String signIn(String email, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
      Users user = usersRepository.signIn(email, password).orElse(null);
      if (user == null || user.getRole() == null) {
        throw new RuntimeException("Invalid email or password.");
      }
      return jwtTokenProvider.generateToken(email, user);
    } catch (AuthenticationException e) {
      throw new RuntimeException("Invalid email or password.");
    }
  }

}
