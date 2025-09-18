package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_ref_to_users")
public class WfRefToUsers extends BaseEntity{

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ref_to_user_id")
	private String refToUserId;
	
	@Column(name = "inbox_master_id")
	private String inboxMasterId;
	
	@Column(name = "wf_inbox_id")
	private String wfInboxId;
	
	@Column(name = "ref_to_users")
	private Integer refToUsers;
	
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

	public String getRefToUserId() {
		return refToUserId;
	}

	public void setRefToUserId(String refToUserId) {
		this.refToUserId = refToUserId;
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

	public Integer getRefToUsers() {
		return refToUsers;
	}

	public void setRefToUsers(Integer refToUsers) {
		this.refToUsers = refToUsers;
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
	
	
}
