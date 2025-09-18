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
public class ListOfBusinessPartners {

	@JsonProperty("bps")
	private List<BusinessPartnerDetails> bps = new ArrayList<BusinessPartnerDetails>();

	/**
	 * @return the bps
	 */
	public List<BusinessPartnerDetails> getBps() {
		return bps;
	}

	/**
	 * @param bps
	 *            the bps to set
	 */
	public void setBps(List<BusinessPartnerDetails> bps) {
		this.bps = bps;
	}

}
