package com.portal.user.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of online users
 * 
 * @author Sathish Babu D
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfOnlineUsers {

	@JsonProperty("onlineUsers")
	private List<OnlineUserDetails> onlineUsers = new ArrayList<OnlineUserDetails>();

	/**
	 * @return the onlineUsers
	 */
	public List<OnlineUserDetails> getOnlineUsers() {
		return onlineUsers;
	}

	/**
	 * @param onlineUsers
	 *            the onlineUsers to set
	 */
	public void setOnlineUsers(List<OnlineUserDetails> onlineUsers) {
		this.onlineUsers = onlineUsers;
	}
}
