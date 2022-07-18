package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService implements EmailSender {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	private final JavaMailSender mailsender ;
	@Autowired
	public EmailService(JavaMailSender mailsender) {
		this.mailsender = mailsender;
	}


	@Override
	@Async
	public void send(String to, String email) {
		// TODO Auto-generated method stub
		try {
			MimeMessage mimeMessage = mailsender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
			mimeMessageHelper.setText(email, true);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject("Email confirmation");
			mimeMessageHelper.setFrom("Madara@shinoby.com");
			mailsender.send(mimeMessage);
				
		} 
		catch (MessagingException e) {
			LOGGER.error("Cannot send the email", e);
			throw new IllegalStateException("Cannot send the email");
		}

	}

}
