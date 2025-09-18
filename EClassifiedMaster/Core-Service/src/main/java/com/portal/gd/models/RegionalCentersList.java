package com.portal.gd.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegionalCentersList {
	
	@JsonProperty("RegionalCenters")
	private List<RegionalCenter>  RegionalCenters = null;

	/**
	 * @return the regionalCenters
	 */
	public List<RegionalCenter> getRegionalCenters() {
		return RegionalCenters;
	}

	/**
	 * @param regionalCenters the regionalCenters to set
	 */
	public void setRegionalCenters(List<RegionalCenter> regionalCenters) {
		RegionalCenters = regionalCenters;
	}
	
	


}
