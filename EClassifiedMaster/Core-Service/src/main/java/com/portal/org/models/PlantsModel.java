package com.portal.org.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Business partner details
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlantsModel {

	@JsonProperty("plant_id")
	private String bpId = null;

	@JsonProperty("plant_name")
	private String name = null;

	public String getBpId() {
		return bpId;
	}

	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
