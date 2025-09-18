package com.portal.rms.model;

import java.util.Date;
import java.util.List;

public class RmsOrderList {

	private String orderNo;
    private String orderDate;
    private String clientCode;
    private String city;
    private String customerName;
    private String orderId;
    private Double amount;
    private String mode;
    private String approvarName;
    private String currentStatus;
    private String approvedTs;
    private String createdByName;
    private Integer createdById;
    
    private List<String> publishDates;
    private String itemId;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
	public List<String> getPublishDates() {
		return publishDates;
	}
	public void setPublishDates(List<String> publishDates) {
		this.publishDates = publishDates;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getApprovarName() {
		return approvarName;
	}
	public void setApprovarName(String approvarName) {
		this.approvarName = approvarName;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getApprovedTs() {
		return approvedTs;
	}
	public void setApprovedTs(String approvedTs) {
		this.approvedTs = approvedTs;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public Integer getCreatedById() {
		return createdById;
	}
	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}
	
	
	
	
    
    
}
