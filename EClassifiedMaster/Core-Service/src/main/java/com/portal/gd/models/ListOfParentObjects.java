package com.portal.gd.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of parent objects
 * 
 * @author Sathish Babu D
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfParentObjects {

	@JsonProperty("parentObjId")
	private String parentObjId = null;

	@JsonProperty("parentObjName")
	private String parentObjName = null;

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

	@JsonProperty("childObjs")
	private List<ListOfChildObjs> childObjs = new ArrayList<ListOfChildObjs>();
	
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
	public List<ListOfChildObjs> getChildObjs() {
		return childObjs;
	}

	/**
	 * @param childObjs
	 *            the childObjs to set
	 */
	public void setChildObjs(List<ListOfChildObjs> childObjs) {
		this.childObjs = childObjs;
	}
}
