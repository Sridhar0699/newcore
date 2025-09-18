package com.portal.org.to;

import java.util.List;

public class RolesTo {

	private Integer roleId = null;

	private String roleName = null;

	private String roleShortId = null;

	private String roleType = null;
	
	private List<ParentObjectTo> accessObjs = null;
	
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
	public List<ParentObjectTo> getAccessObjs() {
		return accessObjs;
	}

	/**
	 * @param accessObjs
	 *            the accessObjs to set
	 */
	public void setAccessObjs(List<ParentObjectTo> accessObjs) {
		this.accessObjs = accessObjs;
	}

	/**
	 * @return the roleShortId
	 */
	public String getRoleShortId() {
		return roleShortId;
	}

	/**
	 * @param roleShortId
	 *            the roleShortId to set
	 */
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
