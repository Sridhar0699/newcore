package com.portal.hm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HelpManuals {
	@JsonProperty("help_manuals")
	private List<HelpManualDetails> helpManuals;

	public List<HelpManualDetails> getHelpManuals() {
		return helpManuals;
	}

	public void setHelpManuals(List<HelpManualDetails> helpManuals) {
		this.helpManuals = helpManuals;
	}

}
