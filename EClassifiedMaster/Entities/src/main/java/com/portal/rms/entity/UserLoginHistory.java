package com.portal.rms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user_login_history")
public class UserLoginHistory {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "login_history_id")
	private String loginhistoryId;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "logon_id")
	private String logonId;
	
	@Column(name = "user_type_id")
	private Integer userTypeId;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "entry_time")
	private Date entryTime;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLogonId() {
		return logonId;
	}
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getLoginhistoryId() {
		return loginhistoryId;
	}
	public void setLoginhistoryId(String loginhistoryId) {
		this.loginhistoryId = loginhistoryId;
	}
	
	
	
	

}
