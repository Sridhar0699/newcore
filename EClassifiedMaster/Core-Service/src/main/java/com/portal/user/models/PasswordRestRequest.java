package com.portal.user.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reset password request
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PasswordRestRequest {

	@JsonProperty("action")
	private String action = null;

	@JsonProperty("userIds")
	private List<Integer> userIds = new ArrayList<Integer>();

	@JsonProperty("logonId")
	private String logonId = null;

	@JsonProperty("emailId")
	private String emailId = null;
	
	/**
	 * @return the userIds
	 */
	public List<Integer> getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds
	 *            the userIds to set
	 */
	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the logonId
	 */
	public String getLogonId() {
		return logonId;
	}

	/**
	 * @param logonId
	 *            the logonId to set
	 */
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
