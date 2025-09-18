package com.portal.common.models;

import java.util.Map;

import com.portal.security.model.LoggedUser;

public class GenericRequestHeaders {

	private String accessObjId = null;
	
	private String orgId = null;
	
	private String orgOpuId = null;
	
	private LoggedUser loggedUser = null;
	
	private String vendorCode = null;
	
	private String vendorId = null;
	
	private Integer userTypeId = null;
	
	private Object object = null;
	
	private String reqFrom = null;
	
	private Map<String, Map> dataAuthorization;
	
	private Map dataAuthorizationMetaData;
	
	private String partnerCode = null;
	
	private String partnerId = null;
	
	private String partnerType = null;
	
	private String userTypeShortId = null;
	
	
	@SuppressWarnings("rawtypes")
	private Map reqHeadersMap = null;
	
	private String accessToken;
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessObjId() {
		return accessObjId;
	}

	public void setAccessObjId(String accessObjId) {
		this.accessObjId = accessObjId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgOpuId() {
		return orgOpuId;
	}

	public void setOrgOpuId(String orgOpuId) {
		this.orgOpuId = orgOpuId;
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getReqFrom() {
		return reqFrom;
	}

	public void setReqFrom(String reqFrom) {
		this.reqFrom = reqFrom;
	}

	/**
	 * @return the reqHeadersMap
	 */
	public Map getReqHeadersMap() {
		return reqHeadersMap;
	}

	/**
	 * @param reqHeadersMap the reqHeadersMap to set
	 */
	public void setReqHeadersMap(Map reqHeadersMap) {
		this.reqHeadersMap = reqHeadersMap;
	}

	public Map<String, Map> getDataAuthorization() {
		return dataAuthorization;
	}

	public void setDataAuthorization(Map<String, Map> dataAuthorization) {
		this.dataAuthorization = dataAuthorization;
	}

	public Map getDataAuthorizationMetaData() {
		return dataAuthorizationMetaData;
	}

	public void setDataAuthorizationMetaData(Map dataAuthorizationMetaData) {
		this.dataAuthorizationMetaData = dataAuthorizationMetaData;
	}

	/**
	 * @return the partnerCode
	 */
	public String getPartnerCode() {
		return partnerCode;
	}

	/**
	 * @param partnerCode the partnerCode to set
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	/**
	 * @return the partnerId
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the partnerType
	 */
	public String getPartnerType() {
		return partnerType;
	}

	/**
	 * @param partnerType the partnerType to set
	 */
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	/**
	 * @return the userTypeShortId
	 */
	public String getUserTypeShortId() {
		return userTypeShortId;
	}

	/**
	 * @param userTypeShortId the userTypeShortId to set
	 */
	public void setUserTypeShortId(String userTypeShortId) {
		this.userTypeShortId = userTypeShortId;
	}
	
}
