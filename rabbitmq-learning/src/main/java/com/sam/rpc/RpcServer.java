package com.sam.rpc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RpcServer {

	//RPC队列名
	public static final String QUEUE_NAME = "rpc_queue";

	//斐波那契数列，用来模拟工作任务
	public static int fib(int num) {
		if (num == 0)
			return 0;
		if (num == 1)
			return 1;
		return fib(num - 1) + fib(num - 2);
	}

	public static void main(String[] args) throws InterruptedException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = null;
		try {
			// 1.connection & channel
			connection = factory.newConnection();
			final Channel channel = connection.createChannel();

			// 2.declare queue
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			System.out.println("****** rpc server waiting for client request ......");

			// 3.每次只接收一个消息（任务）
			channel.basicQos(1);
			//4.获取消费实例
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					BasicProperties prop = new BasicProperties().builder().correlationId(properties.getCorrelationId())
							.build();
					String resp = "";
					try {
						String msg = new String(body, "UTF-8");
						resp = fib(Integer.valueOf(msg)) + "";
						System.out.println("*** will response to rpc client :" + resp);
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						channel.basicPublish("", properties.getReplyTo(), prop, resp.getBytes());
						channel.basicAck(envelope.getDeliveryTag(), false);
					}

				}
			};
			// 5.消费消息（处理任务）
			channel.basicConsume(QUEUE_NAME, false, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

}
