package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_inbox")
public class WfInbox extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "wf_inbox_id")
	private Integer wfInboxId;

	@Column(name = "ext_obj_ref_id")
	private String extObjRefId;

	@Column(name = "wf_ref_id")
	private String wfRefId;

	@Column(name = "target_ref")
	private Integer targetRef;

	@Column(name = "ref_from_type")
	private String refFromType;

	@Column(name = "ref_from")
	private String refFrom;

	@Column(name = "ref_to_type")
	private String refToType;

	@Column(name = "ref_to")
	private String refTo;

	@Column(name = "status")
	private String status;

	@Column(name = "mark_as_delete")
	private Integer markAsDelete;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	@Column(name = "changed_by")
	private String changedBy;

	public Integer getWfInboxId() {
		return wfInboxId;
	}

	public void setWfInboxId(Integer wfInboxId) {
		this.wfInboxId = wfInboxId;
	}

	public String getExtObjRefId() {
		return extObjRefId;
	}

	public void setExtObjRefId(String extObjRefId) {
		this.extObjRefId = extObjRefId;
	}

	public String getWfRefId() {
		return wfRefId;
	}

	public void setWfRefId(String wfRefId) {
		this.wfRefId = wfRefId;
	}

	public Integer getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(Integer targetRef) {
		this.targetRef = targetRef;
	}

	public String getRefFromType() {
		return refFromType;
	}

	public void setRefFromType(String refFromType) {
		this.refFromType = refFromType;
	}

	public String getRefFrom() {
		return refFrom;
	}

	public void setRefFrom(String refFrom) {
		this.refFrom = refFrom;
	}

	public String getRefToType() {
		return refToType;
	}

	public void setRefToType(String refToType) {
		this.refToType = refToType;
	}

	public String getRefTo() {
		return refTo;
	}

	public void setRefTo(String refTo) {
		this.refTo = refTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Integer markAsDelete) {
		this.markAsDelete = markAsDelete;
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

	public Date getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	
	

}