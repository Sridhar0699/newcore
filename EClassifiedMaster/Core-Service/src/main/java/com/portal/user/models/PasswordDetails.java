package com.portal.user.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Password details
 * 
 * @author Sathish Babu D
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PasswordDetails {

	@JsonProperty("userId")
	private Integer userId = null;

	@JsonProperty("logonId")
	private String logonId = null;

	@JsonProperty("oldPwd")
	private String oldPwd = null;

	@JsonProperty("newPwd")
	private String newPwd = null;

	@JsonProperty("resetKey")
	private String resetKey = null;

	@JsonProperty("action")
	private String action;

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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

	/**
	 * @return the oldPwd
	 */
	public String getOldPwd() {
		return oldPwd;
	}

	/**
	 * @param oldPwd
	 *            the oldPwd to set
	 */
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	/**
	 * @return the newPwd
	 */
	public String getNewPwd() {
		return newPwd;
	}

	/**
	 * @param newPwd
	 *            the newPwd to set
	 */
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	/**
	 * @return the resetKey
	 */
	public String getResetKey() {
		return resetKey;
	}

	/**
	 * @param resetKey
	 *            the resetKey to set
	 */
	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
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
}
