package com.portal.workflow.model;

public class WfUpdatePayload {
	
	
	private String wfId;
	private String omOrgFormValId;
	private String wfTitle;
	private String wfShortId;
	private String collection;
	private String wfType;
	private String action;
	private String status;
	private String module;
	private String omOrgFormId;
	private String inboxMasterId;
	
	
	public String getOmOrgFormValId() {
		return omOrgFormValId;
	}

	public void setOmOrgFormValId(String omOrgFormValId) {
		this.omOrgFormValId = omOrgFormValId;
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

	public String getWfId() {
		return wfId;
	}

	public String getCollection() {
		return collection;
	}

	public String getWfType() {
		return wfType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public void setWfType(String wfType) {
		this.wfType = wfType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getOmOrgFormId() {
		return omOrgFormId;
	}

	public void setOmOrgFormId(String omOrgFormId) {
		this.omOrgFormId = omOrgFormId;
	}

	public String getInboxMasterId() {
		return inboxMasterId;
	}

	public void setInboxMasterId(String inboxMasterId) {
		this.inboxMasterId = inboxMasterId;
	}
	

}
