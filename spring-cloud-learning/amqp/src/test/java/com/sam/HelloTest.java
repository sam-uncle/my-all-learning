package com.sam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=RabbitMQApp.class)
public class HelloTest {

	@Autowired
	private Sender sender;

	/**
	 * 调用生产者进行消息发送
	 */
	@Test
	public void hello() throws Exception{
		sender.send();
	}
}
