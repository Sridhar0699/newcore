package com.portal.hm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserType {

	@JsonProperty("role_short_id")
	private String roleShortId;

	@JsonProperty("user_type")
	private String userType;
	
	@JsonProperty("notification")
	private Integer notification;

	public String getRoleShortId() {
		return roleShortId;
	}

	public void setRoleShortId(String roleShortId) {
		this.roleShortId = roleShortId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getNotification() {
		return notification;
	}

	public void setNotification(Integer notification) {
		this.notification = notification;
	}

}
