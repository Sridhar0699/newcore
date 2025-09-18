package com.portal.workflow.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class WfInboxMasterDetailsModel {
	
	private String wfInboxId;
	
	private String targetRef;
	
	private List<String> refToType;
	
	private List<String> refTo;
	
	private Date requestedDate;
	
	private String status;
	
	private Integer createdBy;
	
	private String requestRaisedBy;
	
	private Date createdTs;
	
	private Boolean markAsDelete;
	
	private String refDocData;
	
	private List<Integer> refToUsers;
	
	private String sourceStageRef;
	
	private String comments;
	
	private Integer changedBy;
	
	private Date changedTs;
	
	private Boolean historyFlag;
	
	private String extObjRefId;
	
	private String approvalType;
	
	private String approvalLevels;
	
	private List<String> candidateGroups;
	
	private List<Integer> candidateUsers;
	
	private String inboxMasterId;
	
	private List<Map<String, Object>> userInbox;
	
	private String accessKey;
	
	private String newWfInboxId;

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

	public List<String> getRefToType() {
		return refToType;
	}

	public void setRefToType(List<String> refToType) {
		this.refToType = refToType;
	}

	public List<String> getRefTo() {
		return refTo;
	}

	public void setRefTo(List<String> refTo) {
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

	public List<String> getCandidateGroups() {
		return candidateGroups;
	}

	public void setCandidateGroups(List<String> candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	

	public String getInboxMasterId() {
		return inboxMasterId;
	}

	public void setInboxMasterId(String inboxMasterId) {
		this.inboxMasterId = inboxMasterId;
	}

	public List<Map<String, Object>> getUserInbox() {
		return userInbox;
	}

	public void setUserInbox(List<Map<String, Object>> userInbox) {
		this.userInbox = userInbox;
	}

	public List<Integer> getRefToUsers() {
		return refToUsers;
	}

	public void setRefToUsers(List<Integer> refToUsers) {
		this.refToUsers = refToUsers;
	}

	public List<Integer> getCandidateUsers() {
		return candidateUsers;
	}

	public void setCandidateUsers(List<Integer> candidateUsers) {
		this.candidateUsers = candidateUsers;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getNewWfInboxId() {
		return newWfInboxId;
	}

	public void setNewWfInboxId(String newWfInboxId) {
		this.newWfInboxId = newWfInboxId;
	}
	
	

}
