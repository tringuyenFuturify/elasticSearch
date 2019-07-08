package com.furutify.tringuyen.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.furutify.tringuyen.service.SubscriberService;

@Service(value = "subscriberService")
public class SubscriberServiceImpl implements SubscriberService {

	@Override
	@RabbitListener(queues="${jsa.rabbitmq.queue}")
	public void receiveMsg(String msg) {
		System.out.println("Received Message: " + msg);
	}

}