package com.portal.clf.models;

public class OrderDetails {

	private String orderId;
	private String createdUserType;
	private String createdUserName;
	private String orderStatus;
	private String paymentStatus;
	private Double grandTotal;
	private Integer bookingUnit;
	private String paymentChildId;
	private Integer editionType;

	public Integer getEditionType() {
		return editionType;
	}

	public void setEditionType(Integer editionType) {
		this.editionType = editionType;
	}

	public String getPaymentChildId() {
		return paymentChildId;
	}

	public void setPaymentChildId(String paymentChildId) {
		this.paymentChildId = paymentChildId;
	}

	public Integer getBookingUnit() {
		return bookingUnit;
	}

	public void setBookingUnit(Integer bookingUnit) {
		this.bookingUnit = bookingUnit;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCreatedUserType() {
		return createdUserType;
	}

	public void setCreatedUserType(String createdUserType) {
		this.createdUserType = createdUserType;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

}
