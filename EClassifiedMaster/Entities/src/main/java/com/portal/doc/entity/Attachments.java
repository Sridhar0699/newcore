package com.portal.doc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "attachments")
public class Attachments extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "attatch_id")
	private String attachId;

	@Column(name = "attatch_name")
	private String attachName;

	@Column(name = "source_type")
	private String sourceType;

	@Column(name = "attatch_type")
	private Integer attachType;

	@Column(name = "attatch_url")
	private String attachUrl;
	
	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	@Column(name = "mark_as_delete")
	private boolean markAsDelete;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="order_id")
	private String orderId;
	
	@Column(name="user_id")
	private Integer userId;

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	

	

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}


	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getAttachType() {
		return attachType;
	}

	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
	}

	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	public Date getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}
