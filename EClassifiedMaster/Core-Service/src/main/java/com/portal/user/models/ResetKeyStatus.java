package com.portal.user.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reset key status
 * 
 * @author Sathish Babu D
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResetKeyStatus {

	@JsonProperty("userId")
	private Integer userId = null;

	@JsonProperty("keyStatus")
	private String keyStatus = null;

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
	 * @return the keyStatus
	 */
	public String getKeyStatus() {
		return keyStatus;
	}

	/**
	 * @param keyStatus
	 *            the keyStatus to set
	 */
	public void setKeyStatus(String keyStatus) {
		this.keyStatus = keyStatus;
	}
}
