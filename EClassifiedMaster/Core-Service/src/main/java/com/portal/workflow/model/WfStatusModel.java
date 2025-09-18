package com.portal.workflow.model;

import java.util.List;

public class WfStatusModel {

	private String wfId;
	
	private String wfTitle;
	
	private String wfShortId;
	
	private List<DocumentWfStatus> wfStatus;
	
	private WfInboxDetails  wfInboxDetails;

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

	public String getWfShortId() {
		return wfShortId;
	}

	public void setWfShortId(String wfShortId) {
		this.wfShortId = wfShortId;
	}

	public List<DocumentWfStatus> getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(List<DocumentWfStatus> wfStatus) {
		this.wfStatus = wfStatus;
	}

	public WfInboxDetails getWfInboxDetails() {
		return wfInboxDetails;
	}

	public void setWfInboxDetails(WfInboxDetails wfInboxDetails) {
		this.wfInboxDetails = wfInboxDetails;
	}
	
	
	
}
