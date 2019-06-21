package com.sam.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class LogDirectReciver {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = null;
		Channel channel = null;
		try {
			// 1.创建连接和通道
			connection = factory.newConnection();
			channel = connection.createChannel();

			// 2.为通道声明direct类型的exchange
			channel.exchangeDeclare(LogDirectSender.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
			// 3.创建随机名字的队列
			String queueName = channel.queueDeclare().getQueue();

			// 4.建立exchange和队列的绑定关系
//			 String[] bindingKeys = { "error", "info", "debug" };
			String[] bindingKeys = { "error" };
			for (int i = 0; i < bindingKeys.length; i++) {
				channel.queueBind(queueName, LogDirectSender.EXCHANGE_NAME, bindingKeys[i]);
				System.out.println(" **** LogDirectReciver keep alive ,waiting for " + bindingKeys[i]);
			}

			// 5.通过回调生成消费者并进行监听
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope,
						com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {

					// 获取消息内容然后处理
					String msg = new String(body, "UTF-8");
					System.out.println("*********** LogDirectReciver" + " get message :[" + msg + "]");
				}
			};
			// 6.消费消息
			channel.basicConsume(queueName, true, consumer);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

}
