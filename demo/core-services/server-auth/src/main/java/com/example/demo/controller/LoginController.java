package com.example.demo.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.UsersRequest;
import com.example.demo.service.UsersService;

@RestController
public class LoginController {

  private final UsersService usersService;

  public LoginController(UsersService usersService) {
    this.usersService = usersService;
  }

  @PostMapping("/api/login")
  public ResponseEntity<?> signIn(@Valid @RequestBody UsersRequest request) {
    return ResponseEntity.ok().build();
  }

}
