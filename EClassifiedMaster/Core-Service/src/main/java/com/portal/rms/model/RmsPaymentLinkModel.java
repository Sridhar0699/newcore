package com.portal.rms.model;

public class RmsPaymentLinkModel {
	
	private String orderId;
	private String itemId;
	private String tokenObject;
	private String action;
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
	public String getTokenObject() {
		return tokenObject;
	}
	public void setTokenObject(String tokenObject) {
		this.tokenObject = tokenObject;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	

}
