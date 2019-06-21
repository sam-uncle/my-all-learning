package com.mail;

import com.mail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {
    @Autowired
    MailService mailService;

    @Value("${mail.to}")
    private String mailTo;
    
    @Test
    public  void testSimpleMail(){
        mailService.sendSimpleMail(mailTo,"纯文本邮件","你好，这是一封测试邮件");
    }
    @Test
    public  void testMimeMail(){
        String context = "<html>\n" +
                "<body>\n" +
                "你好，<br>" +
                "这是一封HTML邮件\n" +
                "</body>\n" +
                "</html>";
        mailService.sendMimeMail(mailTo,"HTML邮件",context);
    }

    @Test
    public void testSendAttachMail(){
        String[] to = {mailTo,"sam.ji@foxmail.com"};
        mailService.sendAttachMail(to,"带附件的邮件","你好，这是一封带附件的邮件","D:\\1.jpg");
    }

    @Test
    public void testSendInlineMail(){
        String resId = "1";
        String context = "<html><body>你好，<br>这是一封带静态资源的邮件<br><img src=\'cid:"+resId+"\'></body></html>";
        mailService.sendInlineMail(mailTo,"带静态图片的邮件",context,"D:\\1.jpg",resId);
    }

}
