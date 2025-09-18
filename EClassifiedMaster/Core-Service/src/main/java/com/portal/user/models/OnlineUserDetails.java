package com.portal.user.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User Details
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OnlineUserDetails {

	@JsonProperty("logonId")
	private String logonId = null;

	@JsonProperty("logonTs")
	private String logonTs = null;

	@JsonProperty("ipAddress")
	private String ipAddress = null;

	@JsonProperty("browserName")
	private String browserName = null;

	@JsonProperty("browserVersion")
	private String browserVersion = null;

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
	 * @return the logonTs
	 */
	public String getLogonTs() {
		return logonTs;
	}

	/**
	 * @param logonTs
	 *            the logonTs to set
	 */
	public void setLogonTs(String logonTs) {
		this.logonTs = logonTs;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the browserName
	 */
	public String getBrowserName() {
		return browserName;
	}

	/**
	 * @param browserName
	 *            the browserName to set
	 */
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	/**
	 * @return the browserVersion
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * @param browserVersion
	 *            the browserVersion to set
	 */
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
}
