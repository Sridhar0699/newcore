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
public class ListOfOrgUsers {

	@JsonProperty("users")
	private List<OrgUserDetails> users = new ArrayList<OrgUserDetails>();

	/**
	 * @return the users
	 */
	public List<OrgUserDetails> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<OrgUserDetails> users) {
		this.users = users;
	}

}
