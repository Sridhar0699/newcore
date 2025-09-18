package com.portal.reports.to;

import java.math.BigDecimal;
import java.util.List;

import com.portal.security.model.LoggedUser;

public class ReportsCommonModel {

	public ReportsRequest reportsRequest;
	public LoggedUser loggedUser;
	public ReportsRequestFilter reportsRequestFilter;
	public String rptShortId;
	public String rptStatus;
	public String rptUrl;
	private List<BigDecimal> reReleasedFF;
	private boolean ffInCluse;
	private Integer classifiedType;
	private String requestedDate;
	private String publishedDate;
	private Integer category;
	private Integer edition;

	public ReportsRequestFilter getReportsRequestFilter() {
		return reportsRequestFilter;
	}

	public void setReportsRequestFilter(ReportsRequestFilter reportsRequestFilter) {
		this.reportsRequestFilter = reportsRequestFilter;
	}

	public String getRptShortId() {
		return rptShortId;
	}

	public void setRptShortId(String rptShortId) {
		this.rptShortId = rptShortId;
	}

	public String getRptStatus() {
		return rptStatus;
	}

	public void setRptStatus(String rptStatus) {
		this.rptStatus = rptStatus;
	}

	public String getRptUrl() {
		return rptUrl;
	}

	public void setRptUrl(String rptUrl) {
		this.rptUrl = rptUrl;
	}

	public List<BigDecimal> getReReleasedFF() {
		return reReleasedFF;
	}

	public void setReReleasedFF(List<BigDecimal> reReleasedFF) {
		this.reReleasedFF = reReleasedFF;
	}

	public boolean isFfInCluse() {
		return ffInCluse;
	}

	public void setFfInCluse(boolean ffInCluse) {
		this.ffInCluse = ffInCluse;
	}

	public ReportsRequest getReportsRequest() {
		return reportsRequest;
	}

	public void setReportsRequest(ReportsRequest reportsRequest) {
		this.reportsRequest = reportsRequest;
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Integer getClassifiedType() {
		return classifiedType;
	}

	public void setClassifiedType(Integer classifiedType) {
		this.classifiedType = classifiedType;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

}
