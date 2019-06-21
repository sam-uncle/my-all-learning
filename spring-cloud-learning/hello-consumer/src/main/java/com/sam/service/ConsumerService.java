package com.sam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ConsumerService {
	
	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "errorMsg")//当出现错误时，调用方法errorMsg
	public String consumer() {
		// 调用hello-service服务，注意这里用的是服务名，而不是具体的ip+port
		System.out.println("*** will invoke hello-service ***");
		restTemplate.getForObject("http://hello-service/hello", String.class);
		return "hello consumer finish !!!";
	}

	public String errorMsg() {
		return "error!!!";
	}
}
