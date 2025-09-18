package com.portal.workflow.model;

import java.util.List;

public class WfStatuses {
	
	private Integer stageId;
	private Integer refLevelNo;
	private String stageDesc;
	private String approvalType;
	private List<String> approvers;
	private String approvalLevels;
	private List<String> serviceTaskIds;
	private String onRejectStageId;
	private String onRejectStageDesc;
	private String previousEmailTemplateID;
	private String nextEmailTemplateID;
	private List<String> ruleList;
	private boolean ruleCheckedFlag;
	private String wfStatus;
	public Integer getStageId() {
		return stageId;
	}
	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}
	public Integer getRefLevelNo() {
		return refLevelNo;
	}
	public void setRefLevelNo(Integer refLevelNo) {
		this.refLevelNo = refLevelNo;
	}
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public List<String> getApprovers() {
		return approvers;
	}
	public void setApprovers(List<String> approvers) {
		this.approvers = approvers;
	}
	public String getApprovalLevels() {
		return approvalLevels;
	}
	public void setApprovalLevels(String approvalLevels) {
		this.approvalLevels = approvalLevels;
	}
	public List<String> getServiceTaskIds() {
		return serviceTaskIds;
	}
	public void setServiceTaskIds(List<String> serviceTaskIds) {
		this.serviceTaskIds = serviceTaskIds;
	}
	public String getOnRejectStageId() {
		return onRejectStageId;
	}
	public void setOnRejectStageId(String onRejectStageId) {
		this.onRejectStageId = onRejectStageId;
	}
	public String getOnRejectStageDesc() {
		return onRejectStageDesc;
	}
	public void setOnRejectStageDesc(String onRejectStageDesc) {
		this.onRejectStageDesc = onRejectStageDesc;
	}
	public String getPreviousEmailTemplateID() {
		return previousEmailTemplateID;
	}
	public void setPreviousEmailTemplateID(String previousEmailTemplateID) {
		this.previousEmailTemplateID = previousEmailTemplateID;
	}
	public String getNextEmailTemplateID() {
		return nextEmailTemplateID;
	}
	public void setNextEmailTemplateID(String nextEmailTemplateID) {
		this.nextEmailTemplateID = nextEmailTemplateID;
	}
	public List<String> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<String> ruleList) {
		this.ruleList = ruleList;
	}
	public boolean isRuleCheckedFlag() {
		return ruleCheckedFlag;
	}
	public void setRuleCheckedFlag(boolean ruleCheckedFlag) {
		this.ruleCheckedFlag = ruleCheckedFlag;
	}
	public String getWfStatus() {
		return wfStatus;
	}
	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}
	
	

}
