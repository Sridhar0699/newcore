package com.portal.gd.to;

import java.util.List;

import com.portal.gd.entities.GdAccessObjects;

public class GlobalDataTo {

	private String orgId = null;

	private String bpId = null;

	private Integer loggedUser = null;

	private Integer deviceId = null;

	private List<String> finalAccessObjs = null;

	private List<GdAccessObjects> gdAccessObjects = null;

	private String roleName = null;
	
	private Integer roleId = null;
	
	private List<String> secondaryRoles;


	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	 * @return the loggedUser
	 */
	public Integer getLoggedUser() {
		return loggedUser;
	}

	/**
	 * @param loggedUser
	 *            the loggedUser to set
	 */
	public void setLoggedUser(Integer loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
	 * @return the deviceId
	 */
	public Integer getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the finalAccessObjs
	 */
	public List<String> getFinalAccessObjs() {
		return finalAccessObjs;
	}

	/**
	 * @param finalAccessObjs
	 *            the finalAccessObjs to set
	 */
	public void setFinalAccessObjs(List<String> finalAccessObjs) {
		this.finalAccessObjs = finalAccessObjs;
	}

	/**
	 * @return the gdAccessObjects
	 */
	public List<GdAccessObjects> getGdAccessObjects() {
		return gdAccessObjects;
	}

	/**
	 * @param gdAccessObjects
	 *            the gdAccessObjects to set
	 */
	public void setGdAccessObjects(List<GdAccessObjects> gdAccessObjects) {
		this.gdAccessObjects = gdAccessObjects;
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

	public List<String> getSecondaryRoles() {
		return secondaryRoles;
	}

	public void setSecondaryRoles(List<String> secondaryRoles) {
		this.secondaryRoles = secondaryRoles;
	}

}
