package com.llt.email.model;

import java.sql.Timestamp;

import com.llt.email.annotation.Column;

public class EmailResponse {
	@Column(name="emailResponseId")
	private Integer emailResponseId;
	@Column(name="emailRequestId")
	private Integer emailRequestId;
	@Column(name="emailAddressTried")
	private String emailAddressTried;
	@Column(name="status")
	private String status;
	@Column(name="createdDate")
	private Timestamp createdDate;
	@Column(name="updatedDate")
	private Timestamp updatedDate;
	public Integer getEmailResponseId() {
		return emailResponseId;
	}
	public void setEmailResponseId(Integer emailResponseId) {
		this.emailResponseId = emailResponseId;
	}
	public Integer getEmailRequestId() {
		return emailRequestId;
	}
	public void setEmailRequestId(Integer emailRequestId) {
		this.emailRequestId = emailRequestId;
	}
	public String getEmailAddressTried() {
		return emailAddressTried;
	}
	public void setEmailAddressTried(String emailAddressTried) {
		this.emailAddressTried = emailAddressTried;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}