package com.portal.clf.models;

import java.math.BigDecimal;

public class PaymentsPendingModel {
	
	private String adId;
	private String orderId;
	private String bookingUnitName;
	private String createdByName;
	private String createdTs;
	private String paidAmount;
	private String creatorMobile;
	private String customerName;
	private String customerMobileNo;
	private String logOnId;
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBookingUnitName() {
		return bookingUnitName;
	}
	public void setBookingUnitName(String bookingUnitName) {
		this.bookingUnitName = bookingUnitName;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getCreatorMobile() {
		return creatorMobile;
	}
	public void setCreatorMobile(String creatorMobile) {
		this.creatorMobile = creatorMobile;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobileNo() {
		return customerMobileNo;
	}
	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}
	public String getLogOnId() {
		return logOnId;
	}
	public void setLogOnId(String logOnId) {
		this.logOnId = logOnId;
	}
	
	

}
