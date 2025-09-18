package com.portal.reports.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportsRequestFilter {

	@JsonProperty("fromDate")
	private String fromDate;

	@JsonProperty("toDate")
	private String toDate;
	
	@JsonProperty("classifiedType")
	private Integer classifiedType;
	
	@JsonProperty("categoryId")
	private Integer categoryId;
	
	@JsonProperty("requestedDate")
	private String requestedDate;
	
	@JsonProperty("requestedToDate")
	private String requestedToDate;
	
	@JsonProperty("downloadStatus")
	private String downloadStatus;
	
	@JsonProperty("userId")
	private Integer userId;
	
	public String getRequestedToDate() {
		return requestedToDate;
	}

	public void setRequestedToDate(String requestedToDate) {
		this.requestedToDate = requestedToDate;
	}

	@JsonProperty("contentStatus")
	private String contentStatus;
	
	@JsonProperty("paymentStatus")
	private String paymentStatus;
	
	@JsonProperty("bookingUnit")
	private Integer bookingUnit;
	
	public Integer getBookingUnit() {
		return bookingUnit;
	}

	public void setBookingUnit(Integer bookingUnit) {
		this.bookingUnit = bookingUnit;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Integer getClassifiedType() {
		return classifiedType;
	}

	public void setClassifiedType(Integer classifiedType) {
		this.classifiedType = classifiedType;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
}
