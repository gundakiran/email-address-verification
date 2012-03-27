package com.llt.email.dao;

import java.util.Date;
import java.util.List;

import com.llt.email.model.EmailRequest;
import com.llt.email.util.Status;

public interface EmailRequestDao {
	
	public void insert(EmailRequest request);
	public void update(Integer emailRequestId, Status status, String validEmailAddress);
	public List<EmailRequest> findAll();
	public EmailRequest findById(Integer requestId);
	public List<EmailRequest> findInvalidEmailRequests(Date cutOffTime);
	public List<EmailRequest> findValidEmailRequests(Date cutOffTime);
	
	public static final String SQL_INSERT_REQUEST="SQL_INSERT_REQUEST";
	public static final String SQL_UPDATE_REQUEST="SQL_UPDATE_REQUEST";
	public static final String SQL_GET_ALL_REQUESTS="SQL_GET_ALL_REQUESTS";
	public static final String SQL_FIND_BY_REQUEST_ID="SQL_FIND_BY_REQUEST_ID";
	public static final String SQL_GET_INVALID_REQUESTS="SQL_GET_INVALID_REQUESTS";
	public static final String SQL_GET_VALID_REQUESTS="SQL_GET_VALID_REQUESTS";
}
