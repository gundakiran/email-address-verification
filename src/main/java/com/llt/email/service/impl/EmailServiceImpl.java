package com.llt.email.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llt.email.dao.EmailRequestDao;
import com.llt.email.dao.EmailResponseDao;
import com.llt.email.model.EmailRequest;
import com.llt.email.model.EmailResponse;
import com.llt.email.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LoggerFactory
			.getLogger(EmailServiceImpl.class);

	@Autowired
	private EmailRequestDao emailRequestDao;

	@Autowired
	private EmailResponseDao emailResponseDao;

	@Override
	public List<EmailRequest> getAllEmailRequests() {
		List<EmailRequest> requests = emailRequestDao.findAll();

		if (logger.isDebugEnabled()) {
			logger.debug("Number of requests : "
					+ (requests != null ? requests.size() : "0"));
		}

		return requests;
	}

	@Override
	public EmailRequest getEmailRequest(Integer requestId) {
		return emailRequestDao.findById(requestId);
	}

	@Override
	public List<EmailResponse> getEmailResponses(Integer requestId) {
		return emailResponseDao.findByRequestId(requestId);
	}

	@Override
	// TODO: @Transaction configuration is pending.
	public void createEmailRequest(EmailRequest request) {
		emailRequestDao.insert(request);
	}
}