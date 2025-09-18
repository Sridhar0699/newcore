package com.portal.reports.utility;

import java.util.List;

public class ReportRequestParams {

	private String orgBpId;
	private String orgId;
	private String finPeriod;
	private String reconFinPeriod;
	private List<String> finPeriods;
	private String reportType;
	private String reportShortId;
	private String rtnType;
	private Integer jobId;
	private String responseType;
	private String fromTime;
	private String toTime;
	private String subscriptionId;
	private String finYear;

	public String getOrgBpId() {
		return orgBpId;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getFinPeriod() {
		return finPeriod;
	}

	public String getReconFinPeriod() {
		return reconFinPeriod;
	}

	public List<String> getFinPeriods() {
		return finPeriods;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportShortId() {
		return reportShortId;
	}

	public void setOrgBpId(String orgBpId) {
		this.orgBpId = orgBpId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setFinPeriod(String finPeriod) {
		this.finPeriod = finPeriod;
	}

	public void setReconFinPeriod(String reconFinPeriod) {
		this.reconFinPeriod = reconFinPeriod;
	}

	public void setFinPeriods(List<String> finPeriods) {
		this.finPeriods = finPeriods;
	}

	public void setReportShortId(String reportShortId) {
		this.reportShortId = reportShortId;
	}

	public String getRtnType() {
		return rtnType;
	}

	public void setRtnType(String rtnType) {
		this.rtnType = rtnType;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getFromTime() {
		return fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	
}
