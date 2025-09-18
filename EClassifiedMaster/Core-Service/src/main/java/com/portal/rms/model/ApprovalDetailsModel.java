package com.portal.rms.model;

import java.util.List;

public class ApprovalDetailsModel {

	private String approverName;
	
	private Integer approvalLevels;
	
	private String approvalTs;
	
	private String approvalComments;
	
	private String approvalStatus;
	
	private String itemId;
	
	private String approvalDetails;
	
	private List<String> orderId;
	
	private Integer userId;
	
	private String action;

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Integer getApprovalLevels() {
		return approvalLevels;
	}

	public void setApprovalLevels(Integer approvalLevels) {
		this.approvalLevels = approvalLevels;
	}

	public String getApprovalTs() {
		return approvalTs;
	}

	public void setApprovalTs(String approvalTs) {
		this.approvalTs = approvalTs;
	}

	public String getApprovalComments() {
		return approvalComments;
	}

	public void setApprovalComments(String approvalComments) {
		this.approvalComments = approvalComments;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getApprovalDetails() {
		return approvalDetails;
	}

	public void setApprovalDetails(String approvalDetails) {
		this.approvalDetails = approvalDetails;
	}

	public List<String> getOrderId() {
		return orderId;
	}

	public void setOrderId(List<String> orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
