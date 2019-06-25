package com.futurify.tringuyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.futurify.tringuyen.model.Account;
import com.futurify.tringuyen.service.AccountService;

@RestController
@RequestMapping("/acc")
public class AccountController {

	@Autowired
	private AccountService accService;
	
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody Account m) {
		Object res = null;
		
		try {
			res = accService.save(m);
		}
		catch(Exception ex) {
			res = ex.getMessage();
		}
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("")
	public String edit() {
		return "Success";
	}
	
	@DeleteMapping("")
	public String delete() {
		return "Success";
	}
	
}