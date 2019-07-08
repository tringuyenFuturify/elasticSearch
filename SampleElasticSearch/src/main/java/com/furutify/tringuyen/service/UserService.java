package com.furutify.tringuyen.service;

import java.util.List;

import com.furutify.tringuyen.model.User;

public interface UserService {

	List<User> getAllUsers();
	
    User addNewUser(User user);
	
}