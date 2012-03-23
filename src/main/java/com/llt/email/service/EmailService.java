package com.llt.email.service;

import java.util.List;

import com.llt.email.model.EmailRequest;
import com.llt.email.model.EmailResponse;

public interface EmailService {
	public List<EmailRequest> getAllEmailRequests();
	public EmailRequest getEmailRequest(Integer requestId);
	public List<EmailResponse> getEmailResponses(Integer requestId);
	
	public void createEmailRequest(EmailRequest request);
	
}
