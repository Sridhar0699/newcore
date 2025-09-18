package com.portal.rms.model;

public class CreateOrder {
	private ClientPo clientPo;
	private InsertionPo insertionPo;
	private PaymentDetails paymentDetails;
	private String itemId;
	private String action;
	private String orderId;	
	private Integer bookingOffice;
	private String lat;
	private String lang;
	private String adId;
	private String editionsErpId;
	private String editionsStr;
	private String amount;
	private String wfId;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public ClientPo getClientPo() {
		return clientPo;
	}
	public void setClientPo(ClientPo clientPo) {
		this.clientPo = clientPo;
	}
	public InsertionPo getInsertionPo() {
		return insertionPo;
	}
	public void setInsertionPo(InsertionPo insertionPo) {
		this.insertionPo = insertionPo;
	}
	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public Integer getBookingOffice() {
		return bookingOffice;
	}
	public void setBookingOffice(Integer bookingOffice) {
		this.bookingOffice = bookingOffice;
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
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getEditionsErpId() {
		return editionsErpId;
	}
	public void setEditionsErpId(String editionsErpId) {
		this.editionsErpId = editionsErpId;
	}
	public String getEditionsStr() {
		return editionsStr;
	}
	public void setEditionsStr(String editionsStr) {
		this.editionsStr = editionsStr;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getWfId() {
		return wfId;
	}
	public void setWfId(String wfId) {
		this.wfId = wfId;
	}
	
	
	
	
	
}
