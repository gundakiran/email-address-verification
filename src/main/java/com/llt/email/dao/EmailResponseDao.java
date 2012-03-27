package com.llt.email.dao;

import java.util.List;

import com.llt.email.model.EmailResponse;
import com.llt.email.util.Status;

public interface EmailResponseDao {
	
	public void insert(EmailResponse response);
	
	public List<EmailResponse> findByRequestId(Integer requestId);
	public List<EmailResponse> findByStatus(Integer requestId, Status status);
	
	public static final String SQL_INSERT_RESPONSE="SQL_INSERT_RESPONSE";
	public static final String SQL_GET_RESPONSES_BY_REQUEST_ID="SQL_GET_RESPONSES_BY_REQUEST_ID";
	public static final String SQL_GET_RESPONSES_BY_REQUEST_ID_AND_STATUS="SQL_GET_RESPONSES_BY_REQUEST_ID_AND_STATUS";

}