package com.portal.gd.models;

public class MasterDataUpdateRequest {

	private String requestData;
	private String action;
	private String masterType;

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMasterType() {
		return masterType;
	}

	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}

}
