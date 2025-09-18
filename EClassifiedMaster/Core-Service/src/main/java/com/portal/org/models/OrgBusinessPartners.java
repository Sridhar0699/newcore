package com.portal.org.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Organization business partners
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrgBusinessPartners {

	@JsonProperty("bpId")
	private String bpId = null;

	@JsonProperty("bpLegalName")
	private String bpLegalName = null;

	@JsonProperty("state")
	private String state = null;

	@JsonProperty("city")
	private String city = null;

	@JsonProperty("drugLicNum")
	private String drugLicNum = null;

	@JsonProperty("plantCode")
	private String plantCode = null;

	@JsonProperty("gstin")
	private String gstin = null;

	@JsonProperty("bpType")
	private String bpType = null;

	@JsonProperty("bpAccess")
	private boolean bpAccess = true;

	@JsonProperty("opBalStatus")
	private String opBalStatus = null;

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
	 * @return the bpLegalName
	 */
	public String getBpLegalName() {
		return bpLegalName;
	}

	/**
	 * @param bpLegalName
	 *            the bpLegalName to set
	 */
	public void setBpLegalName(String bpLegalName) {
		this.bpLegalName = bpLegalName;
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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the gstin
	 */
	public String getGstin() {
		return gstin;
	}

	/**
	 * @param gstin
	 *            the gstin to set
	 */
	public void setGstin(String gstin) {
		this.gstin = gstin;
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
	 * @return the bpAccess
	 */
	public boolean isBpAccess() {
		return bpAccess;
	}

	/**
	 * @param bpAccess
	 *            the bpAccess to set
	 */
	public void setBpAccess(boolean bpAccess) {
		this.bpAccess = bpAccess;
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
	 * @return the plantCode
	 */
	public String getPlantCode() {
		return plantCode;
	}

	/**
	 * @param plantCode
	 *            the plantCode to set
	 */
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	/**
	 * @return the opBalStatus
	 */
	public String getOpBalStatus() {
		return opBalStatus;
	}

	/**
	 * @param opBalStatus
	 *            the opBalStatus to set
	 */
	public void setOpBalStatus(String opBalStatus) {
		this.opBalStatus = opBalStatus;
	}
}
