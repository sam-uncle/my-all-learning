package com.sam.ptp.listener;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author JAVA开发老菜鸟
 *
 * 观察者消费--监听消费
 */
public class ListenConsumer {

    public void consumer() throws JMSException, IOException {
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {
            factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
            connection = factory.createConnection();
            /**
             * 消费者必须启动连接，否则无法消费
             */
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue(ListenProducer.QUEUE_NAME);
            consumer = session.createConsumer(destination);
            /**
             * 注册监听器，队列中的消息变化会自动触发监听器，接收并自动处理消息
             *
             * 监听器一旦注册，永久有效，一直到程序关闭
             * 监听器可以注册多个，相当于集群
             * activemq自动轮询多个监听器，实现并行处理
             */
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {

                    try {
                        //需要手动确认消息
                        message.acknowledge();
                        TextMessage om = (TextMessage) message;
                        String data = om.getText();
                        System.out.println(data);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(Exception ex){
            throw ex;
        }
    }

    public static void main(String[] args){
        ListenConsumer consumer = new ListenConsumer();
        try{
            consumer.consumer();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
