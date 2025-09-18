package com.portal.gd.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Child access object details
 * 
 * @author Sathish Babu D
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChildAccessObjects {

	@JsonProperty("objId")
	private String objId = null;

	@JsonProperty("objName")
	private String objName = null;

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
	};
}
