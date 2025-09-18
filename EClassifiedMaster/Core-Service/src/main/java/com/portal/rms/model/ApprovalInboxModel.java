package com.portal.rms.model;

import java.util.Date;
import java.util.List;

public class ApprovalInboxModel {

	private String inboxId;

	private String orderId;

	private String itemId;

	private Integer currentLevel;

	private Date approvalTimestamp;

	private String status;

	private Integer approverUserId;

	private Integer createdBy;

	private String createdTs;

	private Integer changedBy;

	private Date changedTs;

	private boolean markAsDelete;

	private String comments;

	private Double additionalDiscPercent;

	private Double premiumDiscPercent;

	private Double grandTotal;

	private String customerName;

	private String clientCode;

	private String adId;
	
	private List<String> editions;
	
	private List<String> publishDates;
	
	private String editionType;
	
	private String createdbyUser;
	
	private String addType;
	
	private String caption;
	
	private String employeeBookingOffice;
	
	private Integer noOfLevels;
	
	private Integer bookingOffice;
	
	private Double agreedPremiumDisPer;
	
	
	private Integer loggedUserId;
	
	private String empCode;

	public String getInboxId() {
		return inboxId;
	}

	public void setInboxId(String inboxId) {
		this.inboxId = inboxId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}

	public Date getApprovalTimestamp() {
		return approvalTimestamp;
	}

	public void setApprovalTimestamp(Date approvalTimestamp) {
		this.approvalTimestamp = approvalTimestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(Integer approverUserId) {
		this.approverUserId = approverUserId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
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

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Double getAdditionalDiscPercent() {
		return additionalDiscPercent;
	}

	public void setAdditionalDiscPercent(Double additionalDiscPercent) {
		this.additionalDiscPercent = additionalDiscPercent;
	}

	public Double getPremiumDiscPercent() {
		return premiumDiscPercent;
	}

	public void setPremiumDiscPercent(Double premiumDiscPercent) {
		this.premiumDiscPercent = premiumDiscPercent;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public List<String> getEditions() {
		return editions;
	}

	public void setEditions(List<String> editions) {
		this.editions = editions;
	}

	public List<String> getPublishDates() {
		return publishDates;
	}

	public void setPublishDates(List<String> publishDates) {
		this.publishDates = publishDates;
	}

	public String getEditionType() {
		return editionType;
	}

	public void setEditionType(String editionType) {
		this.editionType = editionType;
	}

	public String getCreatedbyUser() {
		return createdbyUser;
	}

	public void setCreatedbyUser(String createdbyUser) {
		this.createdbyUser = createdbyUser;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getEmployeeBookingOffice() {
		return employeeBookingOffice;
	}

	public void setEmployeeBookingOffice(String employeeBookingOffice) {
		this.employeeBookingOffice = employeeBookingOffice;
	}

	public Integer getNoOfLevels() {
		return noOfLevels;
	}

	public void setNoOfLevels(Integer noOfLevels) {
		this.noOfLevels = noOfLevels;
	}

	public Integer getBookingOffice() {
		return bookingOffice;
	}

	public void setBookingOffice(Integer bookingOffice) {
		this.bookingOffice = bookingOffice;
	}

	public Integer getLoggedUserId() {
		return loggedUserId;
	}

	public void setLoggedUserId(Integer loggedUserId) {
		this.loggedUserId = loggedUserId;
	}

	public Double getAgreedPremiumDisPer() {
		return agreedPremiumDisPer;
	}

	public void setAgreedPremiumDisPer(Double agreedPremiumDisPer) {
		this.agreedPremiumDisPer = agreedPremiumDisPer;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	
	
	

	
}
