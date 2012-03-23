package com.llt.email.dao;

import java.util.List;

import com.llt.email.model.EmailResponse;

public interface EmailResponseDao {
	
	public void insert(EmailResponse response);
	
	public List<EmailResponse> findByRequestId(Integer requestId);
	
	public static final String SQL_INSERT_REQUEST="SQL_INSERT_RESPONSE";
	public static final String SQL_GET_RESPONSES_BY_REQUEST_ID="SQL_GET_RESPONSES_BY_REQUEST_ID";

}