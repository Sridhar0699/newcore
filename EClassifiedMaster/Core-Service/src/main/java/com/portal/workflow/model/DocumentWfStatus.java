package com.portal.workflow.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class DocumentWfStatus {

private String wfStageDesc;
	
	private String wfLevelInfo;
	
	private String wfStatus;
	
	private String userName;
	
	private Date actionTakenDt;
	
	private String refDocData;
	
	private String approvalLevel;
	
	private String approvalType;
	
	private List<LinkedHashMap<String, Object>> wfStageApprovers;
	
	private String lastApprovedBy;
	
	private boolean frmEditable;
	
	private String refLevelNo;

	public String getWfStageDesc() {
		return wfStageDesc;
	}

	public void setWfStageDesc(String wfStageDesc) {
		this.wfStageDesc = wfStageDesc;
	}

	public String getWfLevelInfo() {
		return wfLevelInfo;
	}

	public void setWfLevelInfo(String wfLevelInfo) {
		this.wfLevelInfo = wfLevelInfo;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getActionTakenDt() {
		return actionTakenDt;
	}

	public void setActionTakenDt(Date actionTakenDt) {
		this.actionTakenDt = actionTakenDt;
	}

	public String getRefDocData() {
		return refDocData;
	}

	public void setRefDocData(String refDocData) {
		this.refDocData = refDocData;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public List<LinkedHashMap<String, Object>> getWfStageApprovers() {
		return wfStageApprovers;
	}

	public void setWfStageApprovers(List<LinkedHashMap<String, Object>> wfStageApprovers) {
		this.wfStageApprovers = wfStageApprovers;
	}

	public String getLastApprovedBy() {
		return lastApprovedBy;
	}

	public void setLastApprovedBy(String lastApprovedBy) {
		this.lastApprovedBy = lastApprovedBy;
	}

	public boolean isFrmEditable() {
		return frmEditable;
	}

	public void setFrmEditable(boolean frmEditable) {
		this.frmEditable = frmEditable;
	}

	public String getRefLevelNo() {
		return refLevelNo;
	}

	public void setRefLevelNo(String refLevelNo) {
		this.refLevelNo = refLevelNo;
	}
	
	
}
