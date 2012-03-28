package com.llt.email.model;

import com.llt.email.annotation.Column;

public class EmailJob {
	@Column(name="jobName")
	private String jobName;
	@Column(name="status")
	private String jobStatus;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
}