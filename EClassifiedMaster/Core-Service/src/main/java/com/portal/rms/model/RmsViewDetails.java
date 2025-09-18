package com.portal.rms.model;

import java.util.List;

public class RmsViewDetails {

	private CustomerObjectDisplay customerObjectDisplay;
	private InsertionObjectDisplay insertionObjectDisplay;
	private PaymentObjectDisplay paymentObjectDisplay;
	private RmsRatesResponse ratesResponse;
	private List<ApprovalDetailsModel> approvalList;
	private String orderId;
	private String itemId;
	private String adId;
	private String lat;
	private String lang;
	private boolean paymentInProgress;
	private boolean onlinePaymentStatus;

	public CustomerObjectDisplay getCustomerObjectDisplay() {
		return customerObjectDisplay;
	}

	public void setCustomerObjectDisplay(CustomerObjectDisplay customerObjectDisplay) {
		this.customerObjectDisplay = customerObjectDisplay;
	}

	public InsertionObjectDisplay getInsertionObjectDisplay() {
		return insertionObjectDisplay;
	}

	public void setInsertionObjectDisplay(InsertionObjectDisplay insertionObjectDisplay) {
		this.insertionObjectDisplay = insertionObjectDisplay;
	}

	public PaymentObjectDisplay getPaymentObjectDisplay() {
		return paymentObjectDisplay;
	}

	public void setPaymentObjectDisplay(PaymentObjectDisplay paymentObjectDisplay) {
		this.paymentObjectDisplay = paymentObjectDisplay;
	}

	public RmsRatesResponse getRatesResponse() {
		return ratesResponse;
	}

	public void setRatesResponse(RmsRatesResponse ratesResponse) {
		this.ratesResponse = ratesResponse;
	}

	public List<ApprovalDetailsModel> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<ApprovalDetailsModel> approvalList) {
		this.approvalList = approvalList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public boolean isPaymentInProgress() {
		return paymentInProgress;
	}

	public void setPaymentInProgress(boolean paymentInProgress) {
		this.paymentInProgress = paymentInProgress;
	}

	public boolean isOnlinePaymentStatus() {
		return onlinePaymentStatus;
	}

	public void setOnlinePaymentStatus(boolean onlinePaymentStatus) {
		this.onlinePaymentStatus = onlinePaymentStatus;
	}
	

}