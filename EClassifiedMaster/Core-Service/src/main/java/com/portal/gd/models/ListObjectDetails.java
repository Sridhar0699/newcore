package com.portal.gd.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of global data objects
 * 
 * @author Sathish Babu D
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListObjectDetails {

	@JsonProperty("id")
	private String id = null;

	@JsonProperty("value")
	private String value = null;
	
	@JsonProperty("stateId")
	private String stateId = null;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	
}
