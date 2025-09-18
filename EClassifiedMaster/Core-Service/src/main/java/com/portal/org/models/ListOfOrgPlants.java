package com.portal.org.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of business partners
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfOrgPlants {

	@JsonProperty("plantdetails")
	private List<PlantsModel> bps = new ArrayList<PlantsModel>();

	/**
	 * @return the bps
	 */
	public List<PlantsModel> getBps() {
		return bps;
	}

	/**
	 * @param bps
	 *            the bps to set
	 */
	public void setBps(List<PlantsModel> bps) {
		this.bps = bps;
	}

}
