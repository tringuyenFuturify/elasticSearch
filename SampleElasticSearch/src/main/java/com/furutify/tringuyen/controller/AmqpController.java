package com.furutify.tringuyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furutify.tringuyen.service.PublisherService;

@RestController
@RequestMapping("/amqp")
public class AmqpController {

	@Autowired
	private PublisherService publisherService;

	@RequestMapping("/send")
	public String sendMessage(@RequestParam("msg") String msg) {
		System.out.println("*****" + msg);
		for (int i = 0; i < 25; i++) {
			publisherService.sendMsg(msg);
		}
		return "Successfully Msg Sent";
	}

}