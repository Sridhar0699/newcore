package com.portal.send.models;

import java.util.List;
import java.util.Map;

import com.portal.send.service.EmailLogTo;

public class EmailsTo {

	private String from;

	private String to;

	private String subject;

	private Map<String, Object> templateProps;

	private String templateName;

	private String[] bcc;

	private String[] cc;

	private String content;

	private String attachmentName;

	//private DataSource dataSource;
	private List<Map<String, Object>>  dataSource;

	private String bpId;

	private String orgId;
	
	private String loginId;
	
	private EmailLogTo emailLogTo;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the templateProps
	 */
	public Map<String, Object> getTemplateProps() {
		return templateProps;
	}

	/**
	 * @param templateProps
	 *            the templateProps to set
	 */
	public void setTemplateProps(Map<String, Object> templateProps) {
		this.templateProps = templateProps;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName
	 *            the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the bcc
	 */
	public String[] getBcc() {
		return bcc;
	}

	/**
	 * @param bcc
	 *            the bcc to set
	 */
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the cc
	 */
	public String[] getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(String[] cc) {
		this.cc = cc;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attachmentName
	 *            the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * @return the dataSource
	 */
//	public DataSource getDataSource() {
//		return dataSource;
//	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}

	/**
	 * @return the bpId
	 */
	public String getBpId() {
		return bpId;
	}

	/**
	 * @param bpId
	 *            the bpId to set
	 */
	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public EmailLogTo getEmailLogTo() {
		return emailLogTo;
	}

	public void setEmailLogTo(EmailLogTo emailLogTo) {
		this.emailLogTo = emailLogTo;
	}

	public List<Map<String, Object>> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<Map<String, Object>> dataSource) {
		this.dataSource = dataSource;
	}
	
	

}
