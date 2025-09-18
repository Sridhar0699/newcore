package com.portal.org.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Business partner details
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BusinessPartnerDetails {

	@JsonProperty("bpId")
	private String bpId = null;

	@JsonProperty("bpLegalName")
	private String name = null;

	@JsonProperty("location")
	private String location = null;

	@JsonProperty("locationId")
	private Integer locationId = null;

	@JsonProperty("bpGstinNumber")
	private String bpGstinNumber = null;

	@JsonProperty("drugLicNum")
	private String drugLicNum = null;

	@JsonProperty("gstinStatus")
	private String gstinStatus = null;

	@JsonProperty("erpRefId")
	private String erpRefId = null;

	@JsonProperty("erpProfitCenter")
	private String erpProfitCenter;

	@JsonProperty("erpBusArea")
	private String erpBusArea = null;

	@JsonProperty("bpPan")
	private String bpPan = null;

	@JsonProperty("erpBp")
	private String erpBp = null;

	@JsonProperty("erpBankGl")
	private String erpBankGl = null;

	@JsonProperty("country")
	private String country = null;

	@JsonProperty("countryId")
	private Integer countryId = null;

	@JsonProperty("state")
	private String state = null;

	@JsonProperty("stateId")
	private Integer stateId = null;

	@JsonProperty("addrLine1")
	private String addrLine1 = null;

	@JsonProperty("addrLine2")
	private String addrLine2 = null;

	@JsonProperty("bpZipCode")
	private String bpZipCode = null;

	@JsonProperty("bpContact")
	private String bpContact = null;

	@JsonProperty("bpType")
	private String bpType = null;

	@JsonProperty("duplicateGstin")
	private String duplicateGstin = null;

	/**
	 * @return the bpId
	 */
	public String getBpId() {
		return bpId;
	}

	/**
	 * @param bpId
	 *            the bpId to set
	 */
	public void setBpId(String bpId) {
		this.bpId = bpId;
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the bpGstinNumber
	 */
	public String getBpGstinNumber() {
		return bpGstinNumber;
	}

	/**
	 * @param bpGstinNumber
	 *            the bpGstinNumber to set
	 */
	public void setBpGstinNumber(String bpGstinNumber) {
		this.bpGstinNumber = bpGstinNumber;
	}

	/**
	 * @return the drugLicNum
	 */
	public String getDrugLicNum() {
		return drugLicNum;
	}

	/**
	 * @param drugLicNum
	 *            the drugLicNum to set
	 */
	public void setDrugLicNum(String drugLicNum) {
		this.drugLicNum = drugLicNum;
	}

	/**
	 * @return the gstinStatus
	 */
	public String getGstinStatus() {
		return gstinStatus;
	}

	/**
	 * @param gstinStatus
	 *            the gstinStatus to set
	 */
	public void setGstinStatus(String gstinStatus) {
		this.gstinStatus = gstinStatus;
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
	 * @return the erpProfitCenter
	 */
	public String getErpProfitCenter() {
		return erpProfitCenter;
	}

	/**
	 * @param erpProfitCenter
	 *            the erpProfitCenter to set
	 */
	public void setErpProfitCenter(String erpProfitCenter) {
		this.erpProfitCenter = erpProfitCenter;
	}

	/**
	 * @return the erpBusArea
	 */
	public String getErpBusArea() {
		return erpBusArea;
	}

	/**
	 * @param erpBusArea
	 *            the erpBusArea to set
	 */
	public void setErpBusArea(String erpBusArea) {
		this.erpBusArea = erpBusArea;
	}

	/**
	 * @return the bpPan
	 */
	public String getBpPan() {
		return bpPan;
	}

	/**
	 * @param bpPan
	 *            the bpPan to set
	 */
	public void setBpPan(String bpPan) {
		this.bpPan = bpPan;
	}

	/**
	 * @return the erpBp
	 */
	public String getErpBp() {
		return erpBp;
	}

	/**
	 * @param erpBp
	 *            the erpBp to set
	 */
	public void setErpBp(String erpBp) {
		this.erpBp = erpBp;
	}

	/**
	 * @return the erpBankGl
	 */
	public String getErpBankGl() {
		return erpBankGl;
	}

	/**
	 * @param erpBankGl
	 *            the erpBankGl to set
	 */
	public void setErpBankGl(String erpBankGl) {
		this.erpBankGl = erpBankGl;
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
	public Integer getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(Integer stateId) {
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
	 * @return the bpZipCode
	 */
	public String getBpZipCode() {
		return bpZipCode;
	}

	/**
	 * @param bpZipCode
	 *            the bpZipCode to set
	 */
	public void setBpZipCode(String bpZipCode) {
		this.bpZipCode = bpZipCode;
	}

	/**
	 * @return the bpContact
	 */
	public String getBpContact() {
		return bpContact;
	}

	/**
	 * @param bpContact
	 *            the bpContact to set
	 */
	public void setBpContact(String bpContact) {
		this.bpContact = bpContact;
	}

	/**
	 * @return the bpType
	 */
	public String getBpType() {
		return bpType;
	}

	/**
	 * @param bpType
	 *            the bpType to set
	 */
	public void setBpType(String bpType) {
		this.bpType = bpType;
	}

	/**
	 * @return the duplicateGstin
	 */
	public String getDuplicateGstin() {
		return duplicateGstin;
	}

	/**
	 * @param duplicateGstin
	 *            the duplicateGstin to set
	 */
	public void setDuplicateGstin(String duplicateGstin) {
		this.duplicateGstin = duplicateGstin;
	}
}
