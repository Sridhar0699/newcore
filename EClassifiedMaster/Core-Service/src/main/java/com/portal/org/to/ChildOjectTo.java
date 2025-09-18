package com.portal.org.to;

public class ChildOjectTo {

	private String objId = null;

	private String objName = null;

	private String menuIcon = null;

	private Integer seqNum = null;

	private String navLink = null;

	private Integer permissionLevel = null;

	private Integer writePermission = null;

	private Integer readPermission = null;

	private String roleType = null;
	private String roleDesc = null;

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getWritePermission() {
		return writePermission;
	}

	public void setWritePermission(Integer writePermission) {
		this.writePermission = writePermission;
	}

	public Integer getReadPermission() {
		return readPermission;
	}

	public void setReadPermission(Integer readPermission) {
		this.readPermission = readPermission;
	}

	/**
	 * @return the objId
	 */
	public String getObjId() {
		return objId;
	}

	/**
	 * @param objId the objId to set
	 */
	public void setObjId(String objId) {
		this.objId = objId;
	}

	/**
	 * @return the objName
	 */
	public String getObjName() {
		return objName;
	}

	/**
	 * @param objName the objName to set
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}

	/**
	 * @return the menuIcon
	 */
	public String getMenuIcon() {
		return menuIcon;
	}

	/**
	 * @param menuIcon the menuIcon to set
	 */
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	/**
	 * @return the seqNum
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * @param seqNum the seqNum to set
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * @return the navLink
	 */
	public String getNavLink() {
		return navLink;
	}

	/**
	 * @param navLink the navLink to set
	 */
	public void setNavLink(String navLink) {
		this.navLink = navLink;
	}

	/**
	 * @return the permissionLevel
	 */
	public Integer getPermissionLevel() {
		return permissionLevel;
	}

	/**
	 * @param permissionLevel the permissionLevel to set
	 */
	public void setPermissionLevel(Integer permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

}
