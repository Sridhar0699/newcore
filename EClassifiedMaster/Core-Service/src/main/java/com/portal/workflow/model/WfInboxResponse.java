package com.portal.workflow.model;

import java.util.List;

public class WfInboxResponse {

	private List<WfInboxDetails> wfInboxData;
	
	private Object totalCnt;
	
	private List<String> formStatus;

	/**
	 * @return the wfInboxData
	 */
	public List<WfInboxDetails> getWfInboxData() {
		return wfInboxData;
	}

	/**
	 * @param wfInboxData the wfInboxData to set
	 */
	public void setWfInboxData(List<WfInboxDetails> wfInboxData) {
		this.wfInboxData = wfInboxData;
	}

	/**
	 * @return the totalCnt
	 */
	public Object getTotalCnt() {
		return totalCnt;
	}

	/**
	 * @param totalCnt the totalCnt to set
	 */
	public void setTotalCnt(Object totalCnt) {
		this.totalCnt = totalCnt;
	}

	public List<String> getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(List<String> formStatus) {
		this.formStatus = formStatus;
	}
	
	
}
