package com.portal.org.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Delete business partners request
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeleteBusinessPartnerRequest {

	@JsonProperty("orgId")
	private String orgId = null;

	@JsonProperty("bpList")
	private List<String> bpList = new ArrayList<String>();

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
	 * @return the bpList
	 */
	public List<String> getBpList() {
		return bpList;
	}

	/**
	 * @param bpList
	 *            the bpList to set
	 */
	public void setBpList(List<String> bpList) {
		this.bpList = bpList;
	}
}
