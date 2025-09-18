package com.portal.org.to;

import java.util.ArrayList;
import java.util.List;

public class ParentObjectTo {

	private String parentObjId = null;

	private String parentObjName = null;

	private String menuIcon = null;

	private Integer seqNum = null;

	private String navLink = null;

	private Integer permissionLevel = null;
	
	private Integer writePermission = null;
	
	private Integer readPermission = null;

	private List<ChildOjectTo> childObjs = new ArrayList<ChildOjectTo>();

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
	 * @return the parentObjId
	 */
	public String getParentObjId() {
		return parentObjId;
	}

	/**
	 * @param parentObjId
	 *            the parentObjId to set
	 */
	public void setParentObjId(String parentObjId) {
		this.parentObjId = parentObjId;
	}

	/**
	 * @return the parentObjName
	 */
	public String getParentObjName() {
		return parentObjName;
	}

	/**
	 * @param parentObjName
	 *            the parentObjName to set
	 */
	public void setParentObjName(String parentObjName) {
		this.parentObjName = parentObjName;
	}

	/**
	 * @return the menuIcon
	 */
	public String getMenuIcon() {
		return menuIcon;
	}

	/**
	 * @param menuIcon
	 *            the menuIcon to set
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
	 * @param seqNum
	 *            the seqNum to set
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
	 * @param navLink
	 *            the navLink to set
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
	 * @param permissionLevel
	 *            the permissionLevel to set
	 */
	public void setPermissionLevel(Integer permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	/**
	 * @return the childObjs
	 */
	public List<ChildOjectTo> getChildObjs() {
		return childObjs;
	}

	/**
	 * @param childObjs
	 *            the childObjs to set
	 */
	public void setChildObjs(List<ChildOjectTo> childObjs) {
		this.childObjs = childObjs;
	}

}
