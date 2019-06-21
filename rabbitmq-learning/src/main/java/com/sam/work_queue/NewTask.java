package com.sam.work_queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	//队列名称
	public static final String QUEUE_NAME = "TASK_QUEUE2";
	//队列是否需要持久化
	public static final boolean DURABLE = true;
	
	//需要发送的消息列表
	public static final String[] msgs = {"sleep", "task 1", "task 2", "task 3", "task 4", "task 5", "task 6"};
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = null;
		Channel channel = null;

		try {
			// 1.connection & channel
			connection = factory.newConnection();
			channel = connection.createChannel();

			// 2.queue
			channel.queueDeclare(QUEUE_NAME, DURABLE, false, false, null);

			// 3.publish msg
			for (int i = 0; i < msgs.length; i++) {
				channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msgs[i].getBytes());
				System.out.println("** new task ****:" + msgs[i]);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}

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
