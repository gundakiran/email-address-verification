package com.llt.email.util;

public enum JobStatus {
	
	RUNNING("RUNNING"), IDLE("IDLE");

	private String code;

	private JobStatus(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}

}
