package com.portal.gd.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegionalCenter {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("rcName")
	private  String rcName;

	@JsonProperty("isAddOrDelete")
	private  Boolean isAddOrDelete;
	
	@JsonProperty("master_type")
	private  String master_type;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the rcName
	 */
	public String getRcName() {
		return rcName;
	}

	/**
	 * @param rcName the rcName to set
	 */
	public void setRcName(String rcName) {
		this.rcName = rcName;
	}

	/**
	 * @return the isAddOrDelete
	 */
	public Boolean getIsAddOrDelete() {
		return isAddOrDelete;
	}

	/**
	 * @param isAddOrDelete the isAddOrDelete to set
	 */
	public void setIsAddOrDelete(Boolean isAddOrDelete) {
		this.isAddOrDelete = isAddOrDelete;
	}

	public String getMaster_type() {
		return master_type;
	}

	public void setMaster_type(String master_type) {
		this.master_type = master_type;
	}

		
	

}
