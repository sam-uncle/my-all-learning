package com.mail.controller;

import com.mail.service.ActiveMQService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JAVA开发老菜鸟
 */

@RestController
public class MailSenderController {

	@Resource
	ActiveMQService activeMQService;

	@Value("${mail.to}")
	private String mailTo;
	
	@RequestMapping("/sendSimpleMail.do")
	public void sendSimpleMail(){
		String[] to = {mailTo};
		String subject = "普通邮件";
		String context = "你好，这是一封普通邮件";
		activeMQService.sendMQ(to, subject, context);
	}

	@RequestMapping("/sendAttachMail.do")
	public void sendAttachMail(){
		String[] to = {mailTo};
		String subject = "带附件的邮件";
		String context = "<html><body>你好，<br>这是一封带附件的邮件，<br>具体请见附件</body></html>";
		String filePath = "D:\\1.jpg";
		activeMQService.sendMQ(to, subject, context, filePath);
	}

	@RequestMapping("/sendMimeMail.do")
	public void sendMimeMail(){
		String[] to = {mailTo};
		String subject = "普通邮件";

		String filePath = "D:\\1.jpg";
		String resId = "1.jpg";
		String context = "<html><body>你好，<br>这是一封带图片的邮件，<br>请见图片<br><img src=\'cid:"+resId+"\'></body></html>";
		activeMQService.sendMQ(to, subject, context, filePath, resId);
	}

}
