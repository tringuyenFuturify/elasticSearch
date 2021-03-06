package com.furutify.tringuyen.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furutify.tringuyen.model.User;
import com.furutify.tringuyen.service.PublisherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/amqp")
public class AmqpController {

	@Autowired
	private PublisherService publisherService;

	@RequestMapping("/send")
	public String sendMessage(@RequestParam("msg") String msg) {
		User user = new User();
		user.setName(msg);
		user.setCreationDate(new Date());
		UUID uuid = UUID.randomUUID();
		user.setUserId(uuid.toString());

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(user);

		publisherService.sendMsg(json);

		return "Successfully Msg Sent";
	}

}