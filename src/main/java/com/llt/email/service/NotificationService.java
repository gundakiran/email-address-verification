package com.llt.email.service;

import java.util.Map;

public interface NotificationService {
	
	public void send(String fromEmail, String toEmail, String subject, Map<String, Object> attributes, String templateName);

}
