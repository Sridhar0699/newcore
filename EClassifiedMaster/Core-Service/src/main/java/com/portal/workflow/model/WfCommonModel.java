package com.portal.workflow.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.portal.common.models.GenericRequestHeaders;
import com.portal.security.model.LoggedUser;

public class WfCommonModel {

	private LoggedUser loggedUser;
	private WfEvent workFlowEvent;
	private GenericRequestHeaders genericRequestHeaders;
	private List<WfInboxDetails> wfInboxMap;
	private WfDetails wfDetails;
	private String emailTempType;
	private Map info;
	private Map detailView;
	private String formStatus;
	private List<Map<String,Object>> inboxDetails;
	//gatepass changes
	private String omOrgFormId;
	private Map<String,Object> asnGatepassDetails;
	private String asnId;
	private String status;
	//for rule result changes
	private String wfStatus;
	private boolean checkInboxForSkip;
	private String collectionName;
	private boolean isLastStageApprover;
	private String wfTitle;
    private String listView;
    
    private String documentNumber;
    private Date changeTs;
    private Integer changedBy;
    private String userName;
    private String documentMasterId;
    private String itemId;
    private String orderId;
    private Double agreedPremiumDisPer;
    private boolean checkWorkflowForSkip;
    
	public boolean isLastStageApprover() {
		return isLastStageApprover;
	}

	public void setLastStageApprover(boolean isLastStageApprover) {
		this.isLastStageApprover = isLastStageApprover;
	}

	public Map getInfo() {
		return info;
	}

	public void setInfo(Map info) {
		this.info = info;
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public WfEvent getWorkFlowEvent() {
		return workFlowEvent;
	}

	public void setWorkFlowEvent(WfEvent workFlowEvent) {
		this.workFlowEvent = workFlowEvent;
	}

	public GenericRequestHeaders getGenericRequestHeaders() {
		return genericRequestHeaders;
	}

	public void setGenericRequestHeaders(GenericRequestHeaders genericRequestHeaders) {
		this.genericRequestHeaders = genericRequestHeaders;
	}

//	public List<WfInbox> getWfInboxMap() {
//		return wfInboxMap;
//	}
//
//	public void setWfInboxMap(List<WfInbox> wfInboxMap) {
//		this.wfInboxMap = wfInboxMap;
//	}

	public WfDetails getWfDetails() {
		return wfDetails;
	}

	public void setWfDetails(WfDetails wfDetails) {
		this.wfDetails = wfDetails;
	}

	public String getEmailTempType() {
		return emailTempType;
	}

	public void setEmailTempType(String emailTempType) {
		this.emailTempType = emailTempType;
	}

	public Map getDetailView() {
		return detailView;
	}

	public void setDetailView(Map detailView) {
		this.detailView = detailView;
	}

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	public List<Map<String, Object>> getInboxDetails() {
		return inboxDetails;
	}

	public void setInboxDetails(List<Map<String, Object>> inboxDetails) {
		this.inboxDetails = inboxDetails;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public boolean isCheckInboxForSkip() {
		return checkInboxForSkip;
	}

	public void setCheckInboxForSkip(boolean checkInboxForSkip) {
		this.checkInboxForSkip = checkInboxForSkip;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public List<WfInboxDetails> getWfInboxMap() {
		return wfInboxMap;
	}

	public void setWfInboxMap(List<WfInboxDetails> wfInboxMap) {
		this.wfInboxMap = wfInboxMap;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getChangeTs() {
		return changeTs;
	}

	public void setChangeTs(Date changeTs) {
		this.changeTs = changeTs;
	}

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDocumentMasterId() {
		return documentMasterId;
	}

	public void setDocumentMasterId(String documentMasterId) {
		this.documentMasterId = documentMasterId;
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

	public Double getAgreedPremiumDisPer() {
		return agreedPremiumDisPer;
	}

	public void setAgreedPremiumDisPer(Double agreedPremiumDisPer) {
		this.agreedPremiumDisPer = agreedPremiumDisPer;
	}

	public boolean isCheckWorkflowForSkip() {
		return checkWorkflowForSkip;
	}

	public void setCheckWorkflowForSkip(boolean checkWorkflowForSkip) {
		this.checkWorkflowForSkip = checkWorkflowForSkip;
	}
	
}
