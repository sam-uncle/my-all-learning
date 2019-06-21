package com.mail.service;

import org.springframework.stereotype.Service;

public interface ActiveMQService {
    public void sendMQ(String[] to, String subject, String content);
    public void sendMQ(String[] to, String subject, String content, String filePath);
    public void sendMQ(String[] to, String subject, String content, String filePath, String srcId);
}
