package com.portal.workflow.model;

import java.util.Map;

public class WfRequest {

	private String inboxId;
	private String inboxMasterId;
	private String extRefObjectId;
	private String action;
	private String actionComments;
	private String refDocData;
	private String wfDesc;
	private String omOrgFormId;
	private Map<String,Object> asnGatepassDetails;
	private String asnId;
	private String listView;
	private String documentNumber;
	private String documentMasterId;
	private String orderId;
	private String itemId;
	private Double agreedPremiumDisPer;
	private String accessKey;
	private String refToUser;
	private Double additionalDiscountEdit;

	public String getInboxId() {
		return inboxId;
	}

	public void setInboxId(String inboxId) {
		this.inboxId = inboxId;
	}

	public String getInboxMasterId() {
		return inboxMasterId;
	}

	public void setInboxMasterId(String inboxMasterId) {
		this.inboxMasterId = inboxMasterId;
	}

	public String getExtRefObjectId() {
		return extRefObjectId;
	}

	public void setExtRefObjectId(String extRefObjectId) {
		this.extRefObjectId = extRefObjectId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionComments() {
		return actionComments;
	}

	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}

	public String getRefDocData() {
		return refDocData;
	}

	public void setRefDocData(String refDocData) {
		this.refDocData = refDocData;
	}

	public String getWfDesc() {
		return wfDesc;
	}

	public void setWfDesc(String wfDesc) {
		this.wfDesc = wfDesc;
	}

	public String getOmOrgFormId() {
		return omOrgFormId;
	}

	public void setOmOrgFormId(String omOrgFormId) {
		this.omOrgFormId = omOrgFormId;
	}

	public Map<String, Object> getAsnGatepassDetails() {
		return asnGatepassDetails;
	}

	public void setAsnGatepassDetails(Map<String, Object> asnGatepassDetails) {
		this.asnGatepassDetails = asnGatepassDetails;
	}

	public String getAsnId() {
		return asnId;
	}

	public void setAsnId(String asnId) {
		this.asnId = asnId;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentMasterId() {
		return documentMasterId;
	}

	public void setDocumentMasterId(String documentMasterId) {
		this.documentMasterId = documentMasterId;
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

	public Double getAgreedPremiumDisPer() {
		return agreedPremiumDisPer;
	}

	public void setAgreedPremiumDisPer(Double agreedPremiumDisPer) {
		this.agreedPremiumDisPer = agreedPremiumDisPer;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getRefToUser() {
		return refToUser;
	}

	public void setRefToUser(String refToUser) {
		this.refToUser = refToUser;
	}

	public Double getAdditionalDiscountEdit() {
		return additionalDiscountEdit;
	}

	public void setAdditionalDiscountEdit(Double additionalDiscountEdit) {
		this.additionalDiscountEdit = additionalDiscountEdit;
	}
	
}
