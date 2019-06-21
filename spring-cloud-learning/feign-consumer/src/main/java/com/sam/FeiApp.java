package com.sam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * 通过@EnableFeignClients来开启spring cloud feign的支持功能
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeiApp {

	public static void main(String[] args) {
		SpringApplication.run(FeiApp.class, args);
	}

}
