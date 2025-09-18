package com.portal.gd.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of object values
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfObjects {

	@JsonProperty("objs")
	private List<ListObjectDetails> objs = new ArrayList<ListObjectDetails>();

	/**
	 * @return the objs
	 */
	public List<ListObjectDetails> getObjs() {
		return objs;
	}

	/**
	 * @param objs
	 *            the objs to set
	 */
	public void setObjs(List<ListObjectDetails> objs) {
		this.objs = objs;
	}

}
