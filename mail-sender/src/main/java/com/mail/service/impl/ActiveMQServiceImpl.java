package com.mail.service.impl;

import com.alibaba.fastjson.JSON;
import com.mail.model.MailBean;
import com.mail.service.ActiveMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActiveMQServiceImpl implements ActiveMQService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JmsTemplate template;

    @Value("${com.sam.mail.queue}")
    private String queueName;

    @Override
    public void sendMQ(String[] to, String subject, String content) {

        this.sendMQ(to,subject,content,null);
    }

    @Override
    public void sendMQ(String[] to, String subject, String content, String filePath) {
        this.sendMQ(to,subject,content,filePath,null);
    }

    @Override
    public void sendMQ(String[] to, String subject, String content, String filePath, String srcId) {
        MailBean bean = new MailBean();
        bean.setTo(to);
        bean.setSubject(subject);
        bean.setContent(content);
        bean.setFilePath(filePath);
        bean.setSrcId(srcId);
        template.convertAndSend(queueName,bean);
        logger.info("邮件已经发送到MQ:"+ JSON.toJSONString(bean));
    }
}
