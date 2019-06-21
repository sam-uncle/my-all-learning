package com.sam.ptp;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author JAVA开发老菜鸟
 *
 * 主动消费
 */
public class Consumer {

    public String consumer() throws JMSException {
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
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(Producer.QUEUE_NAME);
            consumer = session.createConsumer(destination);
            /**
             * 获取队列消息
             */
            Message message = consumer.receive();
            String text = ((TextMessage) message).getText();
            return text;
        } catch(Exception ex){
            throw ex;
        } finally {
            /**
             * 7.释放资源
             */
            if(consumer != null){
                consumer.close();
            }
            
            if(session != null){
                session.close();
            }

            if(connection != null){
                connection.close();
            }
        }
    }

    public static void main(String[] args){
        Consumer consumer = new Consumer();
        try{
            String message = consumer.consumer();
            System.out.println("消息消费成功：" + message);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
