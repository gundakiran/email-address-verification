package com.llt.email.dao;

import com.llt.email.model.EmailJob;

public interface EmailJobDao {
	public void updateJob(EmailJob job);
	public void insertJob(EmailJob job);
	public EmailJob getJob(String jobName);
	
	public static final String JOB_STATUS_RUNNING="Running";
	public static final String JOB_STATUS_COMPLETED="Completed";
	
	public static final String SQL_INSERT_JOB="SQL_INSERT_JOB";
	public static final String SQL_UPDATE_JOB="SQL_UPDATE_JOB";
	public static final String SQL_GET_JOB="SQL_GET_JOB";
}