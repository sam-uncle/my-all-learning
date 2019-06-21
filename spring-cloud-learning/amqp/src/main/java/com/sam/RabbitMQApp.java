package com.sam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这里没什么特殊的地方，就是普通的spring boot 配置
 *
 */
@SpringBootApplication
public class RabbitMQApp {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMQApp.class, args);
	}
}
