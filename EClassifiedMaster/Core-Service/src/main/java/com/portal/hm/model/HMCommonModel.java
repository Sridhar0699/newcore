package com.portal.hm.model;

import com.portal.security.model.LoggedUser;

public class HMCommonModel {
	private LoggedUser loggedUser;
	private HelpManualDetails helpManualDetails;

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public HelpManualDetails getHelpManualDetails() {
		return helpManualDetails;
	}

	public void setHelpManualDetails(HelpManualDetails helpManualDetails) {
		this.helpManualDetails = helpManualDetails;
	}

}
