package com.mail.service;

import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

public interface MailService {

    public void sendSimpleMail(String to, String subject, String context);

    public void sendMimeMail(String to, String subject, String context);

    public void sendAttachMail(String[] to, String subject, String context, String filePath);

    public void sendInlineMail(String to, String subject, String context, String filePath, String resId);

    public void sendMail(String[] to, String subject, String context, String filePath, String resId );

}
