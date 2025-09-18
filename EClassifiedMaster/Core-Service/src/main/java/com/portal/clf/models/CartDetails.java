package com.portal.clf.models;

import java.util.List;

public class CartDetails {

	private String customerId;
	private OrderDetails orderDetails;
	private List<ClassifiedsOrderItemDetails> items;
	private String mercid;
	private Integer orderType;
	private Boolean linkFlag;

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getMercid() {
		return mercid;
	}

	public void setMercid(String mercid) {
		this.mercid = mercid;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<ClassifiedsOrderItemDetails> getItems() {
		return items;
	}

	public void setItems(List<ClassifiedsOrderItemDetails> items) {
		this.items = items;
	}

	public Boolean getLinkFlag() {
		return linkFlag;
	}

	public void setLinkFlag(Boolean linkFlag) {
		this.linkFlag = linkFlag;
	}

}
