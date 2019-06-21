package com.sam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.entity.User;
import com.sam.service.FeignConsumerService;

@RestController
public class FeiConsumerController {

	@Autowired
	FeignConsumerService consumerService;

	@RequestMapping("feign-consumer")
	public String feignConsumer() {
		consumerService.hello();
		return "feign consumer call finished!!!";
	}

	@RequestMapping("feign-consumer-user")
	public User feignConsumerUser(User user) {
		consumerService.hello2();
		return consumerService.printUser(user);
	}
}
