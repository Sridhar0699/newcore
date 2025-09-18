package com.portal.workflow.model;

import java.util.List;

public class WfSyncModel {

	private List<String> documentMasterIds;
	
	private String documentNumber;

	public List<String> getDocumentMasterIds() {
		return documentMasterIds;
	}

	public void setDocumentMasterIds(List<String> documentMasterIds) {
		this.documentMasterIds = documentMasterIds;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	
}
