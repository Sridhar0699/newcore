package com.portal.common.models;

public class AttUploadResponse {

	private String id;
	private String attName;
	private String attatchUrl;
	
	public String getAttatchUrl() {
		return attatchUrl;
	}
	public void setAttatchUrl(String attatchUrl) {
		this.attatchUrl = attatchUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAttName() {
		return attName;
	}
	public void setAttName(String attName) {
		this.attName = attName;
	}
	
}
