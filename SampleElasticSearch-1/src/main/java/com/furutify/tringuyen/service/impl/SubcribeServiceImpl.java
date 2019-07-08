package com.furutify.tringuyen.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SubcribeServiceImpl {

	@RabbitListener(queues = "${jsa.rabbitmq.queue}")
	public void receivedMessage(String msg) {
		System.out.println("Received Message: " + msg);
	}

}