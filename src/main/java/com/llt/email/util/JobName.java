package com.llt.email.util;

public enum JobName {
	JOB_SEND_RESPONSE("SEND_RESPONSE");

	private String code;

	private JobName(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}
}