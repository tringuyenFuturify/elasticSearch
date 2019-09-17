package io.futurify.authentication.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.futurify.authentication.dto.LoginRequest;
import io.futurify.authentication.dto.JwtResponse;
import io.futurify.authentication.model.User;
import io.futurify.authentication.security.JwtTokenProvider;
import io.futurify.authentication.service.UserService;
import io.futurify.authentication.service.WriterService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	private final WriterService writerService;

	@PostMapping("/login")
	public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest request) {
		User user = userService.signIn(request.getUsername(), request.getPassword());
		JwtResponse jwt = jwtTokenProvider.generateToken(user.getEmail(), user);		
		return ResponseEntity.ok(jwt);
	}
	
	@GetMapping("/a")
	public String test() {
	  //String user, String host, String password, String command1
	  writerService.writer();
	  return "ok";
	}

}
