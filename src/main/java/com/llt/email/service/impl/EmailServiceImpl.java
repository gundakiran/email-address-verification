package com.llt.email.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llt.email.dao.EmailRequestDao;
import com.llt.email.dao.EmailResponseDao;
import com.llt.email.model.EmailRequest;
import com.llt.email.model.EmailResponse;
import com.llt.email.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	@Autowired
	private EmailRequestDao emailRequestDao;

	@Autowired
	private EmailResponseDao emailResponseDao;
	
	@Override
	public List<EmailRequest> getAllEmailRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailRequest getEmailRequest(Integer requestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailResponse> getEmailResponses(Integer requestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createEmailRequest(EmailRequest request) {
		// TODO Auto-generated method stub
		
	}

}