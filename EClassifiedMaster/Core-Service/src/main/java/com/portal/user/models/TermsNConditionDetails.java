package com.portal.user.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Terms and condition details
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TermsNConditionDetails {

	@JsonProperty("termsId")
	private Integer termsId = null;

	@JsonProperty("content")
	private String content = null;

	@JsonProperty("acceptFlag")
	private boolean acceptFlag;

	@JsonProperty("version")
	private String version = null;

	/**
	 * @return the termsId
	 */
	public Integer getTermsId() {
		return termsId;
	}

	/**
	 * @param termsId
	 *            the termsId to set
	 */
	public void setTermsId(Integer termsId) {
		this.termsId = termsId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the acceptFlag
	 */
	public boolean isAcceptFlag() {
		return acceptFlag;
	}

	/**
	 * @param acceptFlag
	 *            the acceptFlag to set
	 */
	public void setAcceptFlag(boolean acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
