package com.llt.email.dao;

import java.util.List;

import com.llt.email.model.EmailRequest;

public interface EmailRequestDao {
	
	public void insert(EmailRequest request);
	public void update(Integer emailRequestId, String status);
	public List<EmailRequest> findAll();
	public EmailRequest findById(Integer requestId);
	
	public static final String SQL_INSERT_REQUEST="SQL_INSERT_REQUEST";
	public static final String SQL_UPDATE_REQUEST="SQL_UPDATE_REQUEST";
	public static final String SQL_GET_ALL_REQUESTS="SQL_GET_ALL_REQUESTS";
	public static final String SQL_FIND_BY_REQUEST_ID="SQL_FIND_BY_REQUEST_ID";
}
