package com.portal.clf.models;

import java.util.Date;

public class ClfPaymentsRefund {
	private String transactionId;
	private String orderId;
	private String mercId;
	private String transactionDate;
	private Double amount;
	private Double refundAmount;
	private String currency;
	private String mercRefundRefNo;
	private String objectid;
	private String refundid;
	private String refundStatus;
	private String paymentChildId;
	
	private String transactionResponse;
	private String itemId;
	private String comments;
	
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
	public String getMercId() {
		return mercId;
	}
	public void setMercId(String mercId) {
		this.mercId = mercId;
	}
	
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
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
	public String getMercRefundRefNo() {
		return mercRefundRefNo;
	}
	public void setMercRefundRefNo(String mercRefundRefNo) {
		this.mercRefundRefNo = mercRefundRefNo;
	}
	public String getObjectid() {
		return objectid;
	}
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	public String getRefundid() {
		return refundid;
	}
	public void setRefundid(String refundid) {
		this.refundid = refundid;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getPaymentChildId() {
		return paymentChildId;
	}
	public void setPaymentChildId(String paymentChildId) {
		this.paymentChildId = paymentChildId;
	}
	public String getTransactionResponse() {
		return transactionResponse;
	}
	public void setTransactionResponse(String transactionResponse) {
		this.transactionResponse = transactionResponse;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}

