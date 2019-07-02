package com.furutify.tringuyen.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furutify.tringuyen.dao.UserDao;
import com.furutify.tringuyen.model.User;

@RestController
@RequestMapping("/repo")
public class UserRepositoryController {

	@Autowired
	private UserDao userDao;

	@RequestMapping("/all")
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		users = StreamSupport.stream(userDao.findAll().spliterator(), false)
				.sorted(Comparator.comparing(User::getUserId))
				.collect(Collectors.toList());
		//userDao.findAll().forEach(users::add);
		return users;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public User addNewUsers(@RequestBody User user) {
		userDao.save(user);
		return user;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public User editNewUsers(@RequestBody User user) {
		User m = userDao.findById(user.getUserId()).orElse(null);
		
		if(m != null) {
			ObjectMapper mapper = new ObjectMapper();
			m = mapper.convertValue(user, User.class);
			userDao.save(m);
		}
		
		return m;
	}

}