package com.portal.clf.models;

import java.util.Date;

public class RmsApprovalDetails {
	
	private String approvedBy;
	private Date approvedTime;
	private Integer level;
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	public Date getApprovedTime() {
		return approvedTime;
	}
	public void setApprovedTime(Date approvedTime) {
		this.approvedTime = approvedTime;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	

}
