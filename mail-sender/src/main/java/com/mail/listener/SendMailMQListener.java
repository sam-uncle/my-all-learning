package com.mail.listener;

import com.mail.model.MailBean;
import com.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * 监听到MQ后执行邮件发送操作
 */
@Service
public class SendMailMQListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailService mailService;

    /**
     * 通过监听目标队列实现功能
     */
    @JmsListener(destination = "${com.sam.mail.queue}")
    public void dealSenderMailMQ(ObjectMessage message){
        try{
            Serializable object = message.getObject();
            MailBean bean =  (MailBean) object;
            mailService.sendMail(bean.getTo(),bean.getSubject(),bean.getContent(),bean.getFilePath(),bean.getSrcId());
            logger.error("消费者消费邮件信息成功");
        } catch (Exception ex){
            logger.error("消费者消费邮件信息失败："+ ex);
        }

    }
}
