package com.llt.email.util;

public enum Status {
	NEW("NEW"), IN_PROGRESS("IN_PROG"), COMPLETED("COMPLETED"), INVALID(
			"INVALID");

	private String code;

	private Status(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}

}
