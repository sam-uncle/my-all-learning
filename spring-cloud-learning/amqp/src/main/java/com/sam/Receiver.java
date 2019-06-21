package com.sam;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 通过@RabbitListener对hello队列进行监听
 *
 */
@Component
@RabbitListener(queues="hello")
public class Receiver {

	/**
	 * 通过@RabbitHandler声明的方法，对hello队列中的消息进行处理
	 */
	@RabbitHandler
	public void receiver(String str) {
		System.out.println("Receiver says:[" + str + "]");
	}
}
