package com.llt.email.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.llt.email.dao.EmailRequestDao;
import com.llt.email.dao.EmailResponseDao;
import com.llt.email.model.EmailRequest;
import com.llt.email.model.EmailResponse;
import com.llt.email.service.EmailService;
import com.llt.email.service.NotificationService;
import com.llt.email.util.Status;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LoggerFactory
			.getLogger(EmailServiceImpl.class);

	@Autowired
	private EmailRequestDao emailRequestDao;

	@Autowired
	private EmailResponseDao emailResponseDao;

	@Autowired
	private NotificationService notificationService;

	private String cutOffMinutes;
	private String fromAddress;

	@Override
	public void createEmailRequest(EmailRequest request) {
		emailRequestDao.insert(request);
	}
	
	@Value("${cut.off.minutes}")
	public void setCutOffMinutes(String cutOffMinutes) {
		this.cutOffMinutes = cutOffMinutes;
	}
	
	@Value("${from.address}")
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	@Override
	public void evaluateRequests() {
		try {
			logger.debug("Processing Invalid Email Address requests!!");
			processRequestsWithInvalidEmailAddresses();
			logger.debug("Processing VAlid Email Address requests!!");
			processRequestsWithValidEmailAddresses();
			logger.debug("Completed processing both valid and invalid email requests!!");
			
		} catch(Exception e) {
			logger.error("Error occurred while processing the requests, "+e.getMessage(), e);
		}
	}

	protected void processRequestsWithInvalidEmailAddresses() {
		List<EmailRequest> invalidRequests = emailRequestDao
				.findInvalidEmailRequests(getCutOffTime());

		if (null == invalidRequests || invalidRequests.size() == 0) {
			logger.debug("There are no invalid requests at the cut off time:"
					+ getCutOffTime());

			return;
		}

		for (EmailRequest req : invalidRequests) {
			processInvalidEmailRequest(req);
		}
	}
	
	protected void processRequestsWithValidEmailAddresses() {
		List<EmailRequest> validRequests = emailRequestDao
				.findValidEmailRequests(getCutOffTime());

		if (null == validRequests || validRequests.size() == 0) {
			logger.debug("There are no valid requests at the cut off time:"
					+ getCutOffTime());

			return;
		}

		for (EmailRequest req : validRequests) {
			processValidEmailRequest(req);
		}
	}

	protected Date getCutOffTime() {
		Calendar now = Calendar.getInstance();
		int cutOff = Integer.valueOf(cutOffMinutes).intValue();
		now.add(Calendar.MINUTE, -cutOff);

		return now.getTime();
	}

	protected String validSubject(String firstName, String lastName) {
		StringBuffer sb = new StringBuffer();
		sb.append(firstName).append(" ").append(lastName);
		sb.append("'s Verified Email Address");

		return sb.toString();
	}

	protected String invalidSubject(String firstName, String lastName) {
		StringBuffer sb = new StringBuffer();
		sb.append(firstName).append(" ").append(lastName);
		sb.append("'s Email Was Not Verified");

		return sb.toString();
	}

	protected void processInvalidEmailRequest(EmailRequest req) {
		// 1. Send the email
		try {
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("firstName", req.getFirstName());
			attributes.put("lastName", req.getLastName());

			notificationService.send(fromAddress,
					req.getRequestorEmailAddress(),
					invalidSubject(req.getFirstName(), req.getLastName()),
					attributes, TEMPLATE_INVALID_EMAIL);

			// 2. Update the EmailRequest Status
			emailRequestDao.update(req.getEmailRequestId(), Status.COMPLETED,
					"");
		} catch (Exception e) {
			logger.error("Unable to send email to "
					+ req.getRequestorEmailAddress());
		}
	}

	protected void processValidEmailRequest(EmailRequest req) {

		try {
			// Get the responses for the request which received 9 invalid
			// responses
			// Ideally it should find only one response with the status NEW as
			// this
			// message is not bounced.
			List<EmailResponse> responses = emailResponseDao.findByStatus(
					req.getEmailRequestId(), Status.NEW);
			
			String validEmailAddress = null;
			if (null != responses && responses.size() > 0) {
				validEmailAddress = responses.get(0).getEmailAddressTried();
			}
			
			if (validEmailAddress == null || validEmailAddress.equals("")) {
				throw new RuntimeException("Valid Email address is not found!!");
			}

			// 2. send email
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("firstName", req.getFirstName());
			attributes.put("lastName", req.getLastName());
			attributes.put("validEmailAddress", validEmailAddress);

			notificationService.send(fromAddress,
					req.getRequestorEmailAddress(),
					validSubject(req.getFirstName(), req.getLastName()),
					attributes, TEMPLATE_VALID_EMAIL);

			// 3. Update the EmailRequest Status
			emailRequestDao.update(req.getEmailRequestId(), Status.COMPLETED,
					validEmailAddress);
		} catch (Exception e) {
			logger.error("Unable to send email to "
					+ req.getRequestorEmailAddress(), e);
		}
	}

	
	private static final String TEMPLATE_INVALID_EMAIL = "email_id_not_found.ftl";
	private static final String TEMPLATE_VALID_EMAIL = "email_id_found.ftl";

	@Override
	public List<EmailRequest> getAllEmailRequests() {
		return emailRequestDao.findAll();
	}
}