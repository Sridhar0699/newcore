package com.portal.gd.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portal.gd.to.GdSettingConfigsTo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfSmtpConfigGroup {

	@JsonProperty("configGroups")
	private List<GdSettingConfigsTo> configGroups = null;

	public List<GdSettingConfigsTo> getConfigGroups() {
		return configGroups;
	}

	public void setConfigGroups(List<GdSettingConfigsTo> configGroups) {
		this.configGroups = configGroups;
	}

}
