package com.portal.org.to;

import java.util.ArrayList;
import java.util.List;

public class ListOfOrganizationsTo {

	private List<OrganizationTo> orgs = new ArrayList<OrganizationTo>();

	private boolean redirect;

	/**
	 * @return the orgs
	 */
	public List<OrganizationTo> getOrgs() {
		return orgs;
	}

	/**
	 * @param orgs
	 *            the orgs to set
	 */
	public void setOrgs(List<OrganizationTo> orgs) {
		this.orgs = orgs;
	}

	/**
	 * @return the redirect
	 */
	public boolean isRedirect() {
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
