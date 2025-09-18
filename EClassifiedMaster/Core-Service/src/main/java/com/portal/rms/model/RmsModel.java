package com.portal.rms.model;

import java.util.List;

public class RmsModel {

	private List<String> orderId = null;
	private List<String> itemId = null;
	private String status = null;
	private String approvalType = null;
	private String comments = null;
	public List<String> getOrderId() {
		return orderId;
	}
	public void setOrderId(List<String> orderId) {
		this.orderId = orderId;
	}
	public List<String> getItemId() {
		return itemId;
	}
	public void setItemId(List<String> itemId) {
		this.itemId = itemId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
