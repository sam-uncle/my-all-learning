package com.sam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.service.ConsumerService;

/**
 *这里不再直接调用restTemplate，
 *而是通过调用service进行实现 
 *
 */
@RestController
public class ConsumerController {

	@Autowired
//	RestTemplate restTemplate;
	ConsumerService service;
	
	
	@RequestMapping("/hello-consumer")
	public String helloConsumer() {
//		//调用hello-service服务，注意这里用的是服务名，而不是具体的ip+port
//		restTemplate.getForObject("http://hello-service/hello", String.class);
		return service.consumer();
	}
}
