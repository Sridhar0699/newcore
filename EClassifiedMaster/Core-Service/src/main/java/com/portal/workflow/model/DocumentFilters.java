package com.portal.workflow.model;

import java.util.List;

public class DocumentFilters { 

	private List<String> requestRaisedBy;
	private List<String> refDocData;
	private String requestedTs;
	private String updatedTs;
	private String status;
	private List<String> wfDesc;
	private String fromDate;
	private String toDate;
	private String wfType;
		
	
	/**
	 * @return the updatedTs
	 */
	public String getUpdatedTs() {
		return updatedTs;
	}
	/**
	 * @param updatedTs the updatedTs to set
	 */
	public void setUpdatedTs(String updatedTs) {
		this.updatedTs = updatedTs;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the requestedTs
	 */
	public String getRequestedTs() {
		return requestedTs;
	}
	/**
	 * @param requestedTs the requestedTs to set
	 */
	public void setRequestedTs(String requestedTs) {
		this.requestedTs = requestedTs;
	}
	/**
	 * @return the requestRaisedBy
	 */
	public List<String> getRequestRaisedBy() {
		return requestRaisedBy;
	}
	/**
	 * @param requestRaisedBy the requestRaisedBy to set
	 */
	public void setRequestRaisedBy(List<String> requestRaisedBy) {
		this.requestRaisedBy = requestRaisedBy;
	}
	/**
	 * @return the refDocData
	 */
	public List<String> getRefDocData() {
		return refDocData;
	}
	/**
	 * @param refDocData the refDocData to set
	 */
	public void setRefDocData(List<String> refDocData) {
		this.refDocData = refDocData;
	}
	/**
	 * @return the wfDesc
	 */
	public List<String> getWfDesc() {
		return wfDesc;
	}
	/**
	 * @param wfDesc the wfDesc to set
	 */
	public void setWfDesc(List<String> wfDesc) {
		this.wfDesc = wfDesc;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getWfType() {
		return wfType;
	}
	public void setWfType(String wfType) {
		this.wfType = wfType;
	}
	
	
}
