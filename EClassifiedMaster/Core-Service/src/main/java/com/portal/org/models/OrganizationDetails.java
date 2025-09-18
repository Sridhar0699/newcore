package com.portal.org.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Organization details
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrganizationDetails {

	@JsonProperty("orgId")
	private String orgId = null;

	@JsonProperty("orgName")
	private String name = null;

	@JsonProperty("country")
	private String country = null;

	@JsonProperty("state")
	private String state = null;

	@JsonProperty("addrLine1")
	private String addrLine1 = null;

	@JsonProperty("addrLine2")
	private String addrLine2 = null;

	@JsonProperty("zipCode")
	private String zipCode = null;

	@JsonProperty("contact")
	private String contact = null;

	@JsonProperty("businessPartners")
	private List<OrgBusinessPartners> businessPartners = new ArrayList<OrgBusinessPartners>();

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
	 * @return the businessPartners
	 */
	public List<OrgBusinessPartners> getBusinessPartners() {
		return businessPartners;
	}

	/**
	 * @param businessPartners
	 *            the businessPartners to set
	 */
	public void setBusinessPartners(List<OrgBusinessPartners> businessPartners) {
		this.businessPartners = businessPartners;
	}
}
