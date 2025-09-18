package com.portal.reports.to;

import java.util.List;

public class ReportsGalleryMaster {

	private String groupName;
	private List<ReportsGaller> reportsGallers;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<ReportsGaller> getReportsGallers() {
		return reportsGallers;
	}

	public void setReportsGallers(List<ReportsGaller> reportsGallers) {
		this.reportsGallers = reportsGallers;
	}

}
