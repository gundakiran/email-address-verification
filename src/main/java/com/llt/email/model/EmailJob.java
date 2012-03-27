package com.llt.email.model;

import com.llt.email.annotation.Column;

public class EmailJob {
	@Column(name="jobName")
	private String jobName;
	@Column(name="status")
	private String jobStatus;

}
