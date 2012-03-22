package com.llt.email.dao;

import java.util.List;

import com.llt.email.model.EmailRequest;

public interface EmailRequestDao {
	
	public void insert(EmailRequest request);
	public List<EmailRequest> findAll();
	public EmailRequest findById(Integer requestId);
	
	public static final String SQL_INSERT_REQUEST="SQL_INSERT_REQUEST";
}
