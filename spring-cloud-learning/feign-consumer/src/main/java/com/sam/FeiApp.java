package com.sam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * ͨ��@EnableFeignClients������spring cloud feign��֧�ֹ���
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
