package com.portal.org.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Add role request
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AddRoleRequest {

	@JsonProperty("orgId")
	private String orgId = null;

	@JsonProperty("roleName")
	private String roleName = null;

	@JsonProperty("roleRef")
	private Integer roleRef = null;

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
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleRef
	 */
	public Integer getRoleRef() {
		return roleRef;
	}

	/**
	 * @param roleRef
	 *            the roleRef to set
	 */
	public void setRoleRef(Integer roleRef) {
		this.roleRef = roleRef;
	}

}
