package com.portal.gd.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of access objects
 * 
 * @author Sathish Babu D
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfAccessObjects {

	@JsonProperty("roleName")
	private String roleName;

	@JsonProperty("accessObjs")
	private List<ListOfParentObjects> accessObjs = new ArrayList<ListOfParentObjects>();

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
}
