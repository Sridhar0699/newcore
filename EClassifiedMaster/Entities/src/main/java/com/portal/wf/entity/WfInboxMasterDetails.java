package com.portal.wf.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_inbox_master_details")
public class WfInboxMasterDetails extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "wf_inbox_id")
	private String wfInboxId;
	
	@Column(name = "target_ref")
	private String targetRef;
	
	@Column(name = "ref_to_type")
	private String refToType;
	
	@Column(name = "ref_to")
	private String refTo;
	
	@Column(name = "requested_date")
	private Date requestedDate;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "request_raised_by")
	private String requestRaisedBy;
	
	@Column(name = "created_ts")
	private Date createdTs;
	
	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;
	
	@Column(name = "ref_doc_data")
	private String refDocData;
	
	@Column(name = "ref_to_users")
	private String refToUsers;
	
	@Column(name = "source_stage_ref")
	private String sourceStageRef;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "changed_by")
	private Integer changedBy;
	
	@Column(name = "changed_ts")
	private Date changedTs;
	
	@Column(name = "inbox_master_id")
	private String inboxMasterId;
	
//	@ManyToOne
//	@JoinColumn(name = "inbox_master_id")
//	private WfInboxMaster wfInboxMaster;
	
//	@OneToMany(mappedBy = "wfInboxMasterDetails", fetch = FetchType.LAZY)
//	private Set<WfUserInbox> wfUserInbox;
	
	@Column(name="history_flag")
	private Boolean historyFlag;
	
	@Column(name="ext_obj_ref_id")
	private String extObjRefId;
	
	@Column(name="approval_type")
	private String approvalType;
	
	@Column(name="approval_levels")
	private String approvalLevels;
	
	@Column(name="candidate_groups")
	private String candidateGroups;
	
	@Column(name="candidate_users")
	private String candidateUsers;
	
	@Column(name = "access_key")
	private String accessKey;

	public String getWfInboxId() {
		return wfInboxId;
	}

	public void setWfInboxId(String wfInboxId) {
		this.wfInboxId = wfInboxId;
	}

	public String getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
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

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getRequestRaisedBy() {
		return requestRaisedBy;
	}

	public void setRequestRaisedBy(String requestRaisedBy) {
		this.requestRaisedBy = requestRaisedBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getRefDocData() {
		return refDocData;
	}

	public void setRefDocData(String refDocData) {
		this.refDocData = refDocData;
	}

	public String getRefToUsers() {
		return refToUsers;
	}

	public void setRefToUsers(String refToUsers) {
		this.refToUsers = refToUsers;
	}

	public String getSourceStageRef() {
		return sourceStageRef;
	}

	public void setSourceStageRef(String sourceStageRef) {
		this.sourceStageRef = sourceStageRef;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

//	public WfInboxMaster getWfInboxMaster() {
//		return wfInboxMaster;
//	}
//
//	public void setWfInboxMaster(WfInboxMaster wfInboxMaster) {
//		this.wfInboxMaster = wfInboxMaster;
//	}

	public Boolean getHistoryFlag() {
		return historyFlag;
	}

	public void setHistoryFlag(Boolean historyFlag) {
		this.historyFlag = historyFlag;
	}

	public String getExtObjRefId() {
		return extObjRefId;
	}

	public void setExtObjRefId(String extObjRefId) {
		this.extObjRefId = extObjRefId;
	}

//	public Set<WfUserInbox> getWfUserInbox() {
//		return wfUserInbox;
//	}
//
//	public void setWfUserInbox(Set<WfUserInbox> wfUserInbox) {
//		this.wfUserInbox = wfUserInbox;
//	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getApprovalLevels() {
		return approvalLevels;
	}

	public void setApprovalLevels(String approvalLevels) {
		this.approvalLevels = approvalLevels;
	}

	public String getCandidateGroups() {
		return candidateGroups;
	}

	public void setCandidateGroups(String candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	public String getCandidateUsers() {
		return candidateUsers;
	}

	public void setCandidateUsers(String candidateUsers) {
		this.candidateUsers = candidateUsers;
	}

	public String getInboxMasterId() {
		return inboxMasterId;
	}

	public void setInboxMasterId(String inboxMasterId) {
		this.inboxMasterId = inboxMasterId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
	
}
