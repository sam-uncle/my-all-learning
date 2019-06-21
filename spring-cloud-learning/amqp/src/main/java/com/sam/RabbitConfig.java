package com.sam;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * rabbitmq配置类，
 * 为了简单，我们这里只配置了Queue
 * 至于exchanges、brokers等用的默认配置
 *
 */
@Configuration
public class RabbitConfig {

	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}
	
	
}
