package com.portal.gd.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of Configuration values
 * 
 * @author Harika Kancharla
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfConfigGroup {

	@JsonProperty("configGroups")
	private List<ConfigGroup> configGroups = null;

	/**
	 * @return the configGroups
	 */
	public List<ConfigGroup> getConfigGroups() {
		return configGroups;
	}

	/**
	 * @param configGroups
	 *            the configGroups to set
	 */
	public void setConfigGroups(List<ConfigGroup> configGroups) {
		this.configGroups = configGroups;
	}

}
