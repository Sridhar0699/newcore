package com.portal.gd.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Country details
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CountryDetails {

	@JsonProperty("id")
	private Integer id = null;

	@JsonProperty("isoCode")
	private String isoCode = null;

	@JsonProperty("name")
	private String name = null;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * @param isoCode
	 *            the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
