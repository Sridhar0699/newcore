package com.portal.clf.models;

import java.util.List;

public class DashboardFilterTo {

	private Integer classifiedType;
	private String requestedDate;
	private String publishedDate;
	private Integer categoryId;
	private Integer edition;
	private boolean downloadConfimFlag;
	private String type;
	private String contentStatus;
	private String paymentStatus;
	private String downloadStatus;
	private Integer bookingUnit;
	private String requestedToDate;
	private String itemId;
	private Integer userId;
	private List<String> orderIds;
	
	public String getRequestedToDate() {
		return requestedToDate;
	}

	public void setRequestedToDate(String requestedToDate) {
		this.requestedToDate = requestedToDate;
	}

	public Integer getBookingUnit() {
		return bookingUnit;
	}

	public void setBookingUnit(Integer bookingUnit) {
		this.bookingUnit = bookingUnit;
	}

	public Integer getClassifiedType() {
		return classifiedType;
	}

	public void setClassifiedType(Integer classifiedType) {
		this.classifiedType = classifiedType;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

	public boolean isDownloadConfimFlag() {
		return downloadConfimFlag;
	}

	public void setDownloadConfimFlag(boolean downloadConfimFlag) {
		this.downloadConfimFlag = downloadConfimFlag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<String> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}

	
}
