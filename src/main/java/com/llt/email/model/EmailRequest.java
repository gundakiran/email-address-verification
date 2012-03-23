package com.llt.email.model;

import java.sql.Timestamp;

import com.llt.email.annotation.Column;

public class EmailRequest {
	
	@Column(name="emailRequestId")
	private Integer emailRequestId;
	@Column(name="requestorEmailAddress")
	private String requestorEmailAddress;
	@Column(name="domain")
	private String domain;
	@Column(name="firstName")
	private String firstName;
	@Column(name="lastName")
	private String lastName;
	@Column(name="status")
	private String status;
	@Column(name="createdDate")
	private Timestamp createdDate;
	@Column(name="updatedDate")
	private Timestamp updatedDate;
	
	public Integer getEmailRequestId() {
		return emailRequestId;
	}
	public void setEmailRequestId(Integer emailRequestId) {
		this.emailRequestId = emailRequestId;
	}
	public String getRequestorEmailAddress() {
		return requestorEmailAddress;
	}
	public void setRequestorEmailAddress(String requestorEmailAddress) {
		this.requestorEmailAddress = requestorEmailAddress;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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