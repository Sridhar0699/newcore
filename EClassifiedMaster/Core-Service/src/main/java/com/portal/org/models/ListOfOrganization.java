package com.portal.org.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of organizations
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfOrganization {

	@JsonProperty("orgs")
	private List<OrganizationDetails> orgs = new ArrayList<OrganizationDetails>();

	@JsonProperty("redirect")
	private boolean redirect;

	/**
	 * @return the orgs
	 */
	public List<OrganizationDetails> getOrgs() {
		return orgs;
	}

	/**
	 * @param orgs
	 *            the orgs to set
	 */
	public void setOrgs(List<OrganizationDetails> orgs) {
		this.orgs = orgs;
	}

	/**
	 * @return the redirect
	 */
	public boolean getRedirect() {
		return redirect;
	}

	/**
	 * @param redirect
	 *            the redirect to set
	 */
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

}
