package com.sam.work_queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Work2 {

	public static void main(String[] args) {
		System.out.println("*** Work ***");
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		try {
			//1.connection & channel
			final Channel channel = factory.newConnection().createChannel();
			
			//2.queue
			channel.queueDeclare(NewTask.QUEUE_NAME, NewTask.DURABLE, false, false, null);

			//3. consumer instance
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String msg = new String(body, "UTF-8");
					//deal task
					try {
						doWork(msg);
					}finally {
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				}
			};
			
			//4.do consumer
			boolean autoAck = false;
			channel.basicQos(1);
			channel.basicConsume(NewTask.QUEUE_NAME, autoAck, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	private static void doWork(String msg) {
		try {
			System.out.println("**** deal task begin :" + msg);
			
			//假装task比较耗时，通过sleep（）来模拟需要消耗的时间
			if ("sleep".equals(msg)) {
				Thread.sleep(1000 * 60);
			} else {
				Thread.sleep(1000);
			}

			System.out.println("**** deal task finish :" + msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
