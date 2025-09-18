package com.portal.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SSPortal API response
 * 
 * @author Sathish Babu D
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenericApiResponse {

	@JsonProperty("status")
	private int status = 1;

	@JsonProperty("message")
	private String message = null;

	@JsonProperty("errorcode")
	private String errorcode = null;

	@JsonProperty("data")
//	@ApiModelProperty(hidden = true)
	private Object data = null;
	
	@JsonProperty("errorlog")
	private String errorlog = null;

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the errorcode
	 */
	public String getErrorcode() {
		return errorcode;
	}

	/**
	 * @param errorcode
	 *            the errorcode to set
	 */
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorlog() {
		return errorlog;
	}

	public void setErrorlog(String errorlog) {
		this.errorlog = errorlog;
	}
	
	
}
