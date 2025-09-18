package com.portal.org.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portal.gd.models.ListOfParentObjects;

/**
 * Role details
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleDetails {

	@JsonProperty("roleId")
	private Integer roleId = null;

	@JsonProperty("roleName")
	private String roleName = null;
	
	@JsonProperty("roleShortId")
	private String roleShortId = null;
	
	@JsonProperty("roleType")
	private String roleType = null;

	@JsonProperty("accessObjs")
	private List<ListOfParentObjects> accessObjs = new ArrayList<ListOfParentObjects>();
	
	@JsonProperty("userTypeId")
	private Integer userTypeId = null;

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	 * @return the accessObjs
	 */
	public List<ListOfParentObjects> getAccessObjs() {
		return accessObjs;
	}

	/**
	 * @param accessObjs
	 *            the accessObjs to set
	 */
	public void setAccessObjs(List<ListOfParentObjects> accessObjs) {
		this.accessObjs = accessObjs;
	}

	public String getRoleShortId() {
		return roleShortId;
	}

	public void setRoleShortId(String roleShortId) {
		this.roleShortId = roleShortId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
