package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "email_log")
public class EmailLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_log_generator")
	@SequenceGenerator(name = "email_log_generator", sequenceName = "EMAIL_LOG_SEQ", allocationSize = 1)
	@Column(name = "email_log_id")
	private Integer emailLogId;

	@Column(name = "from_mail")
	private String fromMail;

	@Column(name = "to_mail")
	private String toMail;

	@Column(name = "cc_mail")
	private String ccMail;

	@Column(name = "bcc_mail")
	private String bccMail;

	@Column(name = "mail_sent")
	private Integer mailSent;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "mail_content")
	private String mailContent;

	@Column(name = "mail_subject")
	private String mailSubject;

	@Column(name = "exception_details")
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
