package com.llt.email.dao;

import java.util.List;

import com.llt.email.model.EmailResponse;

public interface EmailResponseDao {
	
	public void insert(EmailResponse response);
	public List<EmailResponse> findAll(Integer requestId);

}
