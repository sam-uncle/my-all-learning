package com.sam.rpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RpcClient {

	Connection connection = null;
	Channel channel = null;
	//回调队列:用来接收服务端的响应消息
	String queueName = "";

	// 定义RpcClient
	public RpcClient() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		queueName = channel.queueDeclare().getQueue();
	}

	// 真正的处理逻辑
	public String call(String msg) throws IOException, InterruptedException {
		final String uuid = UUID.randomUUID().toString();
		//后续，服务端根据"replyTo"来指定将返回信息写入到哪个队列
		//后续，服务端根据关联标识"correlationId"来指定返回的响应是哪个请求的
		AMQP.BasicProperties prop = new AMQP.BasicProperties().builder().replyTo(queueName).correlationId(uuid).build();

		channel.basicPublish("", RpcServer.QUEUE_NAME, prop, msg.getBytes());
		final BlockingQueue<String> blockQueue = new ArrayBlockingQueue<String>(1);
		channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {

				if (properties.getCorrelationId().equals(uuid)) {
					String msg = new String(body, "UTF-8");

					blockQueue.offer(msg);
					System.out.println("**** rpc client reciver response :[" + msg + "]");
				}
			}

		});

		return blockQueue.take();
	}

	//关闭连接
	public void close() throws IOException {
		connection.close();
	}

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		RpcClient client = new RpcClient();
		client.call("4");
		client.close();
	}
}
