package com.portal.workflow.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WfInboxDetails {

	private String inboxMasterId;
	private String wfDesc;
	private String wfShortId;
	private String objectRefId;
	private String currentStatus;
	private String wfInboxId;
	private String wfRefId;
	private String targetRef;
	private String requestRaisedBy;
	private String requestFor;
	private String orgId;
	private String orgOpuId;
	private String extObjRefId;
	private String refFromType;
	private String refFrom;
	private String refToType;
	private String refTo;
	private Date requestedDate;
	private String reqType;
	private String status;
	private Long expirationDuration;
	private Date expirationTs;
	private String createdBy;
	private String createdTs;
	private Integer updatedBy;
	private Date updatedTs;
	private boolean markAsDelete;
	private String historyKey;
	private boolean historyFlag;
	private String refDocData;
	private List<Map<String, Object>> userInbox;
	private String wfType;
	private String statusDesc;
	private String updatedUser;
	private String wfTitle;
	private String wfTypeId;
	private String wfStatus;
	private List<WfStatuses> wfStatuses;
	private String approvalType;
	private String approvalLevels;
	private String documentNumber;
	private Integer documentType;
	private String itemId;
	private String orderId;
	
	private String inboxId;

	private Integer currentLevel;

	private Date approvalTimestamp;

	private Integer approverUserId;

	private Integer changedBy;

	private Date changedTs;

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

	public String getWfInboxId() {
		return wfInboxId;
	}

	public void setWfInboxId(String wfInboxId) {
		this.wfInboxId = wfInboxId;
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

	public String getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(String targetRef) {
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

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getExpirationDuration() {
		return expirationDuration;
	}

	public void setExpirationDuration(Long expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

	public Date getExpirationTs() {
		return expirationTs;
	}

	public void setExpirationTs(Date expirationTs) {
		this.expirationTs = expirationTs;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getRequestRaisedBy() {
		return requestRaisedBy;
	}

	public void setRequestRaisedBy(String requestRaisedBy) {
		this.requestRaisedBy = requestRaisedBy;
	}

	public String getRequestFor() {
		return requestFor;
	}

	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

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

	public String getHistoryKey() {
		return historyKey;
	}

	public void setHistoryKey(String historyKey) {
		this.historyKey = historyKey;
	}

	public boolean isHistoryFlag() {
		return historyFlag;
	}

	public void setHistoryFlag(boolean historyFlag) {
		this.historyFlag = historyFlag;
	}

	public String getRefDocData() {
		return refDocData;
	}

	public void setRefDocData(String refDocData) {
		this.refDocData = refDocData;
	}

	public List<Map<String, Object>> getUserInbox() {
		return userInbox;
	}

	public void setUserInbox(List<Map<String, Object>> userInbox) {
		this.userInbox = userInbox;
	}

	public String getWfType() {
		return wfType;
	}

	public void setWfType(String wfType) {
		this.wfType = wfType;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	public String getWfTypeId() {
		return wfTypeId;
	}

	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public List<WfStatuses> getWfStatuses() {
		return wfStatuses;
	}

	public void setWfStatuses(List<WfStatuses> wfStatuses) {
		this.wfStatuses = wfStatuses;
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

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Integer getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInboxId() {
		return inboxId;
	}

	public void setInboxId(String inboxId) {
		this.inboxId = inboxId;
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

	public Integer getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(Integer approverUserId) {
		this.approverUserId = approverUserId;
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

	public Double getAgreedPremiumDisPer() {
		return agreedPremiumDisPer;
	}

	public void setAgreedPremiumDisPer(Double agreedPremiumDisPer) {
		this.agreedPremiumDisPer = agreedPremiumDisPer;
	}

	public Integer getLoggedUserId() {
		return loggedUserId;
	}

	public void setLoggedUserId(Integer loggedUserId) {
		this.loggedUserId = loggedUserId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	
	
	
	
}
