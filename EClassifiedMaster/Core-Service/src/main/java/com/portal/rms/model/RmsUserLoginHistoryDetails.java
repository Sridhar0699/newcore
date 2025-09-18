package com.portal.rms.model;

public class RmsUserLoginHistoryDetails {
	
	private Integer userId;
	private Integer userTypeId;
	private String logonId;
	private String entryDate;
	private String action;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getLogonId() {
		return logonId;
	}
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	

}
