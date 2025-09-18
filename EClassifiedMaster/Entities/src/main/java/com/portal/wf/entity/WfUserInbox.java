package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_user_inbox")
public class WfUserInbox extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "wf_user_inbox_id")
	private String wfUserInboxId;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "default_approval")
	private Integer defaultApproval;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_ts")
	private Date createdTs;
	
	@Column(name = "changed_by")
	private Integer changedBy;
	
	@Column(name = "changed_ts")
	private Date changedTs;
	
	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;
	
	@Column(name="inbox_master_id")
	private String inboxMasterId;
	
	@Column(name="wf_inbox_id")
	private String wfInboxId;
	
//	@ManyToOne
//	@JoinColumn(name = "wf_inbox_id")
//	private WfInboxMasterDetails wfInboxMasterDetails;

	public String getWfUserInboxId() {
		return wfUserInboxId;
	}

	public void setWfUserInboxId(String wfUserInboxId) {
		this.wfUserInboxId = wfUserInboxId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDefaultApproval() {
		return defaultApproval;
	}

	public void setDefaultApproval(Integer defaultApproval) {
		this.defaultApproval = defaultApproval;
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

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getInboxMasterId() {
		return inboxMasterId;
	}

	public void setInboxMasterId(String inboxMasterId) {
		this.inboxMasterId = inboxMasterId;
	}

	public String getWfInboxId() {
		return wfInboxId;
	}

	public void setWfInboxId(String wfInboxId) {
		this.wfInboxId = wfInboxId;
	}

//	public WfInboxMasterDetails getWfInboxMasterDetails() {
//		return wfInboxMasterDetails;
//	}
//
//	public void setWfInboxMasterDetails(WfInboxMasterDetails wfInboxMasterDetails) {
//		this.wfInboxMasterDetails = wfInboxMasterDetails;
//	}
	
	
}
