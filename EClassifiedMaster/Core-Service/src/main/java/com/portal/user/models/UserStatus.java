package com.portal.user.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User status
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserStatus {

	@JsonProperty("userId")
	private Integer userId = null;

	@JsonProperty("logonId")
	private String logonId = null;

	@JsonProperty("existed")
	private Boolean existed = false;

	@JsonProperty("status")
	private String status = null;

	@JsonProperty("assigned")
	private Boolean assigned = false;

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
	 * @return the existed
	 */
	public Boolean getExisted() {
		return existed;
	}

	/**
	 * @param existed
	 *            the existed to set
	 */
	public void setExisted(Boolean existed) {
		this.existed = existed;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the assigned
	 */
	public Boolean getAssigned() {
		return assigned;
	}

	/**
	 * @param assigned
	 *            the assigned to set
	 */
	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}

}
