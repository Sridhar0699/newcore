package com.portal.rms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "approval_inbox")
public class ApprovalInbox extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "inbox_id")
	private String inboxId;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "item_id")
	private String itemId;
	
	@Column(name = "current_level")
	private Integer currentLevel;
	
	@Column(name = "approval_timestamp")
	private Date approvalTimestamp;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "approver_user_id")
	private Integer approverUserId;
	
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
	
	@Column(name = "comments")
	private String comments;
	
	public String getInboxId() {
		return inboxId;
	}

	public void setInboxId(String inboxId) {
		this.inboxId = inboxId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}

	public Date getApprovalTimestamp() {
		return approvalTimestamp;
	}

	public void setApprovalTimestamp(Date approvalTimestamp) {
		this.approvalTimestamp = approvalTimestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(Integer approverUserId) {
		this.approverUserId = approverUserId;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
