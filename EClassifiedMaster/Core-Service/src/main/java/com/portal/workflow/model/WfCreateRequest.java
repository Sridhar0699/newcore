package com.portal.workflow.model;

import java.util.List;

public class WfCreateRequest {
	
	private String wfType;
	
	private String wfTypeId;
	
	private List<WfStages> wfStages;
	
	private String status;
	
	private String wfTitle;
	
	private String wfId;
	
	private boolean initiatorFlag;
	
	private List<WorkFlowTypesModel> wfTypes;
	
	private List<Integer> documentTypeId;
	
	private List<Integer> locationId;

	public String getWfType() {
		return wfType;
	}

	public void setWfType(String wfType) {
		this.wfType = wfType;
	}

	public List<WfStages> getWfStages() {
		return wfStages;
	}

	public void setWfStages(List<WfStages> wfStages) {
		this.wfStages = wfStages;
	}

	public String getWfTypeId() {
		return wfTypeId;
	}

	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public boolean isInitiatorFlag() {
		return initiatorFlag;
	}

	public void setInitiatorFlag(boolean initiatorFlag) {
		this.initiatorFlag = initiatorFlag;
	}

	public List<WorkFlowTypesModel> getWfTypes() {
		return wfTypes;
	}

	public void setWfTypes(List<WorkFlowTypesModel> wfTypes) {
		this.wfTypes = wfTypes;
	}

	public List<Integer> getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(List<Integer> documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public List<Integer> getLocationId() {
		return locationId;
	}

	public void setLocationId(List<Integer> locationId) {
		this.locationId = locationId;
	}


	
}
