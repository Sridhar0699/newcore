package com.portal.workflow.model;

import java.util.List;
import java.util.Map;

public class ApproveVendorDataPayload {

	private List<String> refDocIds;
	
	private String orgId;
	
	private String userId;
	
	private String mailAction;

	private Map info;

	public Map getInfo() {
		return info;
	}

	public void setInfo(Map info) {
		this.info = info;
	}

	public List<String> getRefDocIds() {
		return refDocIds;
	}

	public void setRefDocIds(List<String> refDocIds) {
		this.refDocIds = refDocIds;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMailAction() {
		return mailAction;
	}

	public void setMailAction(String mailAction) {
		this.mailAction = mailAction;
	}
}
