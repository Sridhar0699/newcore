package com.portal.org.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Organization details
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrgDetails {

	@JsonProperty("orgId")
	private String orgId = null;

	@JsonProperty("userId")
	private Integer userId = null;

	@JsonProperty("orgName")
	private String orgName = null;

	@JsonProperty("internalBpName")
	private String internalBpName = null;

	@JsonProperty("erpRefId")
	private String erpRefId;

	@JsonProperty("country")
	private String country = null;

	@JsonProperty("countryId")
	private Integer countryId = null;

	@JsonProperty("state")
	private String state = null;

	@JsonProperty("stateId")
	private String stateId = null;

	@JsonProperty("addrLine1")
	private String addrLine1 = null;

	@JsonProperty("addrLine2")
	private String addrLine2 = null;

	@JsonProperty("zipCode")
	private String zipCode = null;

	@JsonProperty("contact")
	private String contact = null;

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

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
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the erpRefId
	 */
	public String getErpRefId() {
		return erpRefId;
	}

	/**
	 * @param erpRefId
	 *            the erpRefId to set
	 */
	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the countryId
	 */
	public Integer getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId
	 *            the countryId to set
	 */
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the stateId
	 */
	public String getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the addrLine1
	 */
	public String getAddrLine1() {
		return addrLine1;
	}

	/**
	 * @param addrLine1
	 *            the addrLine1 to set
	 */
	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	/**
	 * @return the addrLine2
	 */
	public String getAddrLine2() {
		return addrLine2;
	}

	/**
	 * @param addrLine2
	 *            the addrLine2 to set
	 */
	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the internalBpName
	 */
	public String getInternalBpName() {
		return internalBpName;
	}

	/**
	 * @param internalBpName
	 *            the internalBpName to set
	 */
	public void setInternalBpName(String internalBpName) {
		this.internalBpName = internalBpName;
	}
}
