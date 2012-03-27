package com.llt.email.service;

import com.llt.email.model.EmailRequest;

public interface EmailService {
	public void createEmailRequest(EmailRequest request);
	
	/**
	 * 
	 */
	public void evaluateRequests();
	
}
