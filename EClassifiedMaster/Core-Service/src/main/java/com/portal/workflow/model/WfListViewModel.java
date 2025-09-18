package com.portal.workflow.model;

import java.math.BigInteger;
import java.util.List;

public class WfListViewModel {
	
	private Integer pageSize;
	
	private Integer pageNumber;
	
	private String custSearchVal;
	
	private String wfId;
	
	private String wfTitle;
	
	private String wfName;
	
	private String wfTypeId;
	
	private String wfType;
	
	private String wfShortId;
	
	private String status;
	
	private Integer createdBy;
	
	private String createdTs;
	
	private Boolean markAsDelete;
	
	private String processMetaData;
	
	private BigInteger totalCnt;
	
	private String createdUserName;
	
	private String approverLevels;
	
	
	private Integer documentType;

	
	private Boolean isDefault;

	
	private List<String> wfTypes;
	
	private String inboxMasterId;
	private String documentMasterId;
	
	
	private List<Integer> documentTypes;
	
	private String docName;
	
	private List<String> documentNames;
	
	private List<Integer> location;
	
	private String locName;
	private List<String> locNames;


	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getCustSearchVal() {
		return custSearchVal;
	}

	public void setCustSearchVal(String custSearchVal) {
		this.custSearchVal = custSearchVal;
	}

	public List<Integer> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<Integer> documentTypes) {
		this.documentTypes = documentTypes;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	public String getWfTypeId() {
		return wfTypeId;
	}

	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}

	public String getWfType() {
		return wfType;
	}

	public void setWfType(String wfType) {
		this.wfType = wfType;
	}

	public String getWfShortId() {
		return wfShortId;
	}

	public void setWfShortId(String wfShortId) {
		this.wfShortId = wfShortId;
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

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getProcessMetaData() {
		return processMetaData;
	}

	public void setProcessMetaData(String processMetaData) {
		this.processMetaData = processMetaData;
	}

	public BigInteger getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(BigInteger totalCnt) {
		this.totalCnt = totalCnt;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getApproverLevels() {
		return approverLevels;
	}

	public void setApproverLevels(String approverLevels) {
		this.approverLevels = approverLevels;
	}


	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}


	public List<String> getWfTypes() {
		return wfTypes;
	}

	public void setWfTypes(List<String> wfTypes) {
		this.wfTypes = wfTypes;
	}

	public String getInboxMasterId() {
		return inboxMasterId;
	}

	public void setInboxMasterId(String inboxMasterId) {
		this.inboxMasterId = inboxMasterId;
	}

	public String getDocumentMasterId() {
		return documentMasterId;
	}

	public void setDocumentMasterId(String documentMasterId) {
		this.documentMasterId = documentMasterId;
	}

	public Integer getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public List<String> getDocumentNames() {
		return documentNames;
	}

	public void setDocumentNames(List<String> documentNames) {
		this.documentNames = documentNames;
	}

	public List<Integer> getLocation() {
		return location;
	}

	public void setLocation(List<Integer> location) {
		this.location = location;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public List<String> getLocNames() {
		return locNames;
	}

	public void setLocNames(List<String> locNames) {
		this.locNames = locNames;
	}

	
	
	
	

}
