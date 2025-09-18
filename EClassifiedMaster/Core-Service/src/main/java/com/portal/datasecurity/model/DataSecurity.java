package com.portal.datasecurity.model;

public class DataSecurity {

	private String orgId = null;

	private Integer userId = null;

	private String objId = null;

	private String bpId = null;

	private String clientIp = null;

	public DataSecurity(String orgId, String bpId, String objId) {

		this.orgId = orgId;
		this.bpId = bpId;
		this.objId = objId;
	}

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the objId
	 */
	public String getObjId() {
		return objId;
	}

	/**
	 * @param objId
	 *            the objId to set
	 */
	public void setObjId(String objId) {
		this.objId = objId;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the bpId
	 */
	public String getBpId() {
		return bpId;
	}

	public void setBpId(String bpId) {
		this.bpId = bpId;
	}
}
