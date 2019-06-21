package com.sam.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class LogTopicSender {
	// exchange名字
	public static String EXCHANGE_NAME = "topicExchange";

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = null;
		Channel channel = null;
		try {
			// 1.创建连接和通道
			connection = factory.newConnection();
			channel = connection.createChannel();

			// 2.为通道声明topic类型的exchange
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
			
			// 3.发送消息到指定的exchange,队列指定为空,由exchange根据情况判断需要发送到哪些队列
//			String routingKey = "info";
//			String routingKey = "log4j.error";
//			String routingKey = "logback.error";
			String routingKey = "log4j.warn";
			String msg = " hello rabbitmq, I am " + routingKey;
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
			System.out.println("product send a msg: " + msg);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} finally {
			// 4.关闭连接

			if (connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
