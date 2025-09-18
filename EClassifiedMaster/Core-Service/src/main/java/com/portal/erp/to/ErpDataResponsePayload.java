package com.portal.erp.to;

public class ErpDataResponsePayload {

	private String portalId;
	private String orderId;
	private String type;
	private String orderStatus;
	private String portalOrderId;
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPortalOrderId() {
		return portalOrderId;
	}
	public void setPortalOrderId(String portalOrderId) {
		this.portalOrderId = portalOrderId;
	}
	
	
}
