package com.portal.communication.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "nm_email_templates")
public class NmEmailTemplates extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "template_id")
	private Integer templateId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "template_short_id")
	private String templateShortId;

	@Column(name = "template_desc")
	private String templateDesc;

	@Column(name = "email_subject")
	private String emailSubject;

	@Column(name = "template_content")
	private String templateContent;

	@Column(name = "is_configurable")
	private Boolean isConfigurable;

	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	/**
	 * @return the templateId
	 */
	public Integer getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
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

	/**
	 * @return the templateShortId
	 */
	public String getTemplateShortId() {
		return templateShortId;
	}

	/**
	 * @param templateShortId
	 *            the templateShortId to set
	 */
	public void setTemplateShortId(String templateShortId) {
		this.templateShortId = templateShortId;
	}

	/**
	 * @return the templateDesc
	 */
	public String getTemplateDesc() {
		return templateDesc;
	}

	/**
	 * @param templateDesc
	 *            the templateDesc to set
	 */
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}

	/**
	 * @return the emailSubject
	 */
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @param emailSubject
	 *            the emailSubject to set
	 */
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	/**
	 * @return the templateContent
	 */
	public String getTemplateContent() {
		return templateContent;
	}

	/**
	 * @param templateContent
	 *            the templateContent to set
	 */
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	/**
	 * @return the isConfigurable
	 */
	public Boolean getIsConfigurable() {
		return isConfigurable;
	}

	/**
	 * @param isConfigurable
	 *            the isConfigurable to set
	 */
	public void setIsConfigurable(Boolean isConfigurable) {
		this.isConfigurable = isConfigurable;
	}

	/**
	 * @return the markAsDelete
	 */
	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @param markAsDelete
	 *            the markAsDelete to set
	 */
	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdTs
	 */
	public Date getCreatedTs() {
		return createdTs;
	}

	/**
	 * @param createdTs
	 *            the createdTs to set
	 */
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	/**
	 * @return the changedBy
	 */
	public Integer getChangedBy() {
		return changedBy;
	}

	/**
	 * @param changedBy
	 *            the changedBy to set
	 */
	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	/**
	 * @return the changedTs
	 */
	public Date getChangedTs() {
		return changedTs;
	}

	/**
	 * @param changedTs
	 *            the changedTs to set
	 */
	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}
}
