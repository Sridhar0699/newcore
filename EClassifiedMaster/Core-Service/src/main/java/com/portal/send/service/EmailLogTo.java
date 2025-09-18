package com.portal.send.service;

import java.util.Date;

public class EmailLogTo {

	private Integer emailLogId;

	private String fromMail;

	private String toMail;

	private String ccMail;

	private String bccMail;

	private Integer mailSent;

	private Date createdTs;

	private String createdBy;

	private String mailContent;

	private String mailSubject;

	private String exceptionDetails;

	public Integer getEmailLogId() {
		return emailLogId;
	}

	public void setEmailLogId(Integer emailLogId) {
		this.emailLogId = emailLogId;
	}

	public void setCcMail(String ccMail) {
		this.ccMail = ccMail;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public String getBccMail() {
		return bccMail;
	}

	public void setBccMail(String bccMail) {
		this.bccMail = bccMail;
	}

	public Integer getMailSent() {
		return mailSent;
	}

	public void setMailSent(Integer mailSent) {
		this.mailSent = mailSent;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

}
