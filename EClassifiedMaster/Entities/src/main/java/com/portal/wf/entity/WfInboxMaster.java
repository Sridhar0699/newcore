package com.portal.wf.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_inbox_master")
public class WfInboxMaster extends BaseEntity{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "inbox_master_id")
	private String inboxMasterId;
	@Column(name = "wf_desc")
	private String wfDesc;
	@Column(name = "org_id")
	private String orgId;
	@Column(name = "org_opu_id")
	private String orgOpuId;
	@Column(name = "wf_short_id")
	private String wfShortId;
	@Column(name = "object_ref_id")
	private String objectRefId;
	@Column(name = "current_status")
	private String currentStatus;
	@Column(name = "mark_as_delete")
	private boolean markAsDelete;
	@Column(name = "wf_type")
	private String wfType;
	@Column(name = "wf_type_id")
	private String wfTypeId;
	@Column(name = "wf_title")
	private String wfTitle;
	@Column(name = "created_by")
	private Integer createdBy;
	@Column(name = "created_ts")
	private Date createdTs;
	@Column(name = "changed_by")
	private Integer changedBy;
	@Column(name = "changed_ts")
	private Date changedTs;
	
//	@OneToMany(mappedBy = "wfInboxMaster", fetch = FetchType.LAZY)
//	private Set<WfInboxMasterDetails> wfInboxMasterDetails;
	
	public String getInboxMasterId() {
		return inboxMasterId;
	}
	public void setInboxMasterId(String inboxMasterId) {
		this.inboxMasterId = inboxMasterId;
	}
	public String getWfDesc() {
		return wfDesc;
	}
	public void setWfDesc(String wfDesc) {
		this.wfDesc = wfDesc;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgOpuId() {
		return orgOpuId;
	}
	public void setOrgOpuId(String orgOpuId) {
		this.orgOpuId = orgOpuId;
	}
	public String getWfShortId() {
		return wfShortId;
	}
	public void setWfShortId(String wfShortId) {
		this.wfShortId = wfShortId;
	}
	public String getObjectRefId() {
		return objectRefId;
	}
	public void setObjectRefId(String objectRefId) {
		this.objectRefId = objectRefId;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public boolean isMarkAsDelete() {
		return markAsDelete;
	}
	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}
	public String getWfType() {
		return wfType;
	}
	public void setWfType(String wfType) {
		this.wfType = wfType;
	}
	public String getWfTypeId() {
		return wfTypeId;
	}
	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}
	public String getWfTitle() {
		return wfTitle;
	}
	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
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
//	public Set<WfInboxMasterDetails> getWfInboxMasterDetails() {
//		return wfInboxMasterDetails;
//	}
//	public void setWfInboxMasterDetails(Set<WfInboxMasterDetails> wfInboxMasterDetails) {
//		this.wfInboxMasterDetails = wfInboxMasterDetails;
//	}
	
	
}
