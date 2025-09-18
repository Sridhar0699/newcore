package com.portal.clf.models;

import java.util.Date;

public class PaymentsAndRefund {
	
	private String transactionId;
	private String orderId;
	private String amount;
	private String transactionDate;
	private String gateWayId;
	private String paymentMethodType;
	private String paymentStatus;
	private String refundId;
	private String bankRefNo;
	private String refundStatus;
	private String transactionProcessType;
	private Integer orderType;
	private String mercRefundRefNo;
	private String mercId;
	private Double refundAmount;
	private String currency;
	private String objectId;
	private Date refundDate;
	private boolean isRefundDetailsPresent;
	private String transactionErrorDesc;
	private String bookingLocation;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	public String getGateWayId() {
		return gateWayId;
	}
	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}
	public String getPaymentMethodType() {
		return paymentMethodType;
	}
	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getBankRefNo() {
		return bankRefNo;
	}
	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getTransactionProcessType() {
		return transactionProcessType;
	}
	public void setTransactionProcessType(String transactionProcessType) {
		this.transactionProcessType = transactionProcessType;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getMercRefundRefNo() {
		return mercRefundRefNo;
	}
	public void setMercRefundRefNo(String mercRefundRefNo) {
		this.mercRefundRefNo = mercRefundRefNo;
	}
	public String getMercId() {
		return mercId;
	}
	public void setMercId(String mercId) {
		this.mercId = mercId;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public boolean isRefundDetailsPresent() {
		return isRefundDetailsPresent;
	}
	public void setRefundDetailsPresent(boolean isRefundDetailsPresent) {
		this.isRefundDetailsPresent = isRefundDetailsPresent;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionErrorDesc() {
		return transactionErrorDesc;
	}
	public void setTransactionErrorDesc(String transactionErrorDesc) {
		this.transactionErrorDesc = transactionErrorDesc;
	}
	public String getBookingLocation() {
		return bookingLocation;
	}
	public void setBookingLocation(String bookingLocation) {
		this.bookingLocation = bookingLocation;
	}
	
	

}
