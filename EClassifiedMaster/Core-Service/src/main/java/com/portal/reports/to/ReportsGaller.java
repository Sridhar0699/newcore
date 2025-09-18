package com.portal.reports.to;

import java.util.Date;

public class ReportsGaller {

	private String reportName;
	private String reportShortId;
	private String reportDesc;
	private String accessObjId;
	private String reportGenStatus;
	private Date reportGenDate;

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportShortId() {
		return reportShortId;
	}

	public void setReportShortId(String reportShortId) {
		this.reportShortId = reportShortId;
	}

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	public String getAccessObjId() {
		return accessObjId;
	}

	public void setAccessObjId(String accessObjId) {
		this.accessObjId = accessObjId;
	}

	public String getReportGenStatus() {
		return reportGenStatus;
	}

	public void setReportGenStatus(String reportGenStatus) {
		this.reportGenStatus = reportGenStatus;
	}

	public Date getReportGenDate() {
		return reportGenDate;
	}

	public void setReportGenDate(Date reportGenDate) {
		this.reportGenDate = reportGenDate;
	}

}
