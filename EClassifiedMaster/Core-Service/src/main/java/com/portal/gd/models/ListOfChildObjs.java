package com.portal.gd.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of child access Objects
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfChildObjs {

	@JsonProperty("objId")
	private String objId = null;

	@JsonProperty("objName")
	private String objName = null;

	@JsonProperty("menuIcon")
	private String menuIcon = null;

	@JsonProperty("seqNum")
	private Integer seqNum = null;

	@JsonProperty("navLink")
	private String navLink = null;

	@JsonProperty("permissionLevel")
	private Integer permissionLevel = null;
	
	@JsonProperty("writePermission")
	private Integer writePermission = null;
	
	@JsonProperty("readPermission")
	private Integer readPermission = null;

	
	
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
	 * @param objId
	 *            the objId to set
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
	 * @param objName
	 *            the objName to set
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

}
