package com.portal.clf.models;

import java.math.BigDecimal;

public class SSPSummaryModel {

	private Integer createdBy;
	private String createdByName;
	private String logOnId;
	private String empCode;
	private String createdTs;
	private Integer count;
	private BigDecimal amount;
	private String bookingLocation;
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getLogOnId() {
		return logOnId;
	}
	public void setLogOnId(String logOnId) {
		this.logOnId = logOnId;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getBookingLocation() {
		return bookingLocation;
	}
	public void setBookingLocation(String bookingLocation) {
		this.bookingLocation = bookingLocation;
	}
	
	
}
