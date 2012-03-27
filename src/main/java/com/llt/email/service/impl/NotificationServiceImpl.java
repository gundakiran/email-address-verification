package com.llt.email.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.llt.email.service.NotificationService;

import freemarker.template.Configuration;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {
	private static final Logger logger = LoggerFactory
			.getLogger(NotificationServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Configuration freemarkerConfiguration;

	@Override
	public void send(String fromEmail, String toEmail, String subject,
			Map<String, Object> attributes, String templateName) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromEmail);
			message.setTo(toEmail);
			message.setSubject(subject);
			message.setText(FreeMarkerTemplateUtils.processTemplateIntoString(
					freemarkerConfiguration.getTemplate(templateName),
					attributes));
			
			mailSender.send(message);
			logger.debug("successfully sent email!!");
			
		} catch (Exception e) {
			logger.error(
					"Error occurred while sending email , " + e.getMessage(), e);
			throw new RuntimeException("Unable to send email");
		}
	}
}