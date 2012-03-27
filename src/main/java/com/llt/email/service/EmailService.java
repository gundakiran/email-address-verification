package com.llt.email.service;

import java.util.List;

import com.llt.email.model.EmailRequest;

public interface EmailService {
	public void createEmailRequest(EmailRequest request);
	
	public List<EmailRequest> getAllEmailRequests();
	/**
	 * 
	 */
	public void evaluateRequests();
	
}
