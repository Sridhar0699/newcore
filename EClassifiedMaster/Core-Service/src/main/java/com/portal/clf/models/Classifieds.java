package com.portal.clf.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Classifieds {

	private String contactNo;
	private String adId;
	private String requestedDate;
	private String publishedDate;
	private Integer categoryId;
	private String category;
	private String subCategory;
	private List<String> editions;
	private BigDecimal paidAmount;
	private String approvalStatus;
	private String paymentStatus;
	private boolean downloadStatus;
	private String matter;
	private String itemId;
	private String orderId;
	private String clfPaymentStatus;
	private String scheme;
	private String erpOrderId;
	private Integer userTypeId;
	private Integer createdBy;
	private Integer lines;
	
	private String downloadStatues;

	private String edition;
	private String attachedId;
	private String attachmentName;
	private String paymentsOrderId;
	
	private boolean publishDateDownloadStatus;
	private String comments;
	
	private String createdByName;
	private String createdByEmpId;
	private String createdByRole;
	
	private String adSubtype;
	private Integer adSubTypeId;
	private Integer bookingUnitCode;
	private String bookingUnitDesc;
	
	private Integer sizeId;
	private String size;
	
	private String templateId;
	private String templateImageId;
	private String templateImageUrl;
	private String templateImageName;
	private String mainAttachmentId;
	private List<String> attachmentIds;
	private List<String> attachmentNames;
	private Integer vendorCommPer;
	private BigDecimal vendorCommission;
	private Boolean isSettled;
	private String artWorkAttachmentId;
	private String artWorkAttachmentName;
	
	private String createdTs;
	private String logOnId;
	private String empCode;
	private Integer count;
	private Float amount;
	
	private String ecomOrderId;

	public String getAttachedId() {
		return attachedId;
	}

	public void setAttachedId(String attachedId) {
		this.attachedId = attachedId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public List<String> getEditions() {
		return editions;
	}

	public void setEditions(List<String> edition) {
		this.editions = edition;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public boolean isDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(boolean downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public String getMatter() {
		return matter;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getClfPaymentStatus() {
		return clfPaymentStatus;
	}

	public void setClfPaymentStatus(String clfPaymentStatus) {
		this.clfPaymentStatus = clfPaymentStatus;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getErpOrderId() {
		return erpOrderId;
	}

	public void setErpOrderId(String erpOrderId) {
		this.erpOrderId = erpOrderId;
	}

	public Integer getLines() {
		return lines;
	}

	public void setLines(Integer lines) {
		this.lines = lines;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getDownloadStatues() {
		return downloadStatues;
	}

	public void setDownloadStatues(String downloadStatues) {
		this.downloadStatues = downloadStatues;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getPaymentsOrderId() {
		return paymentsOrderId;
	}

	public void setPaymentsOrderId(String paymentsOrderId) {
		this.paymentsOrderId = paymentsOrderId;
	}

	public boolean isPublishDateDownloadStatus() {
		return publishDateDownloadStatus;
	}

	public void setPublishDateDownloadStatus(boolean publishDateDownloadStatus) {
		this.publishDateDownloadStatus = publishDateDownloadStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedByEmpId() {
		return createdByEmpId;
	}

	public void setCreatedByEmpId(String createdByEmpId) {
		this.createdByEmpId = createdByEmpId;
	}

	public String getCreatedByRole() {
		return createdByRole;
	}

	public void setCreatedByRole(String createdByRole) {
		this.createdByRole = createdByRole;
	}

	public String getAdSubtype() {
		return adSubtype;
	}

	public void setAdSubtype(String adSubtype) {
		this.adSubtype = adSubtype;
	}

	public Integer getAdSubTypeId() {
		return adSubTypeId;
	}

	public void setAdSubTypeId(Integer adSubTypeId) {
		this.adSubTypeId = adSubTypeId;
	}

	public Integer getBookingUnitCode() {
		return bookingUnitCode;
	}

	public void setBookingUnitCode(Integer bookingUnitCode) {
		this.bookingUnitCode = bookingUnitCode;
	}

	public String getBookingUnitDesc() {
		return bookingUnitDesc;
	}

	public void setBookingUnitDesc(String bookingUnitDesc) {
		this.bookingUnitDesc = bookingUnitDesc;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateImageId() {
		return templateImageId;
	}

	public void setTemplateImageId(String templateImageId) {
		this.templateImageId = templateImageId;
	}

	public String getTemplateImageUrl() {
		return templateImageUrl;
	}

	public void setTemplateImageUrl(String templateImageUrl) {
		this.templateImageUrl = templateImageUrl;
	}

	public String getTemplateImageName() {
		return templateImageName;
	}

	public void setTemplateImageName(String templateImageName) {
		this.templateImageName = templateImageName;
	}

	public String getMainAttachmentId() {
		return mainAttachmentId;
	}

	public void setMainAttachmentId(String mainAttachmentId) {
		this.mainAttachmentId = mainAttachmentId;
	}

	public List<String> getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(List<String> attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	public List<String> getAttachmentNames() {
		return attachmentNames;
	}

	public void setAttachmentNames(List<String> attachmentNames) {
		this.attachmentNames = attachmentNames;
	}

	public Integer getVendorCommPer() {
		return vendorCommPer;
	}

	public void setVendorCommPer(Integer vendorCommPer) {
		this.vendorCommPer = vendorCommPer;
	}

	public BigDecimal getVendorCommission() {
		return vendorCommission;
	}

	public void setVendorCommission(BigDecimal vendorCommission) {
		this.vendorCommission = vendorCommission;
	}

	public Boolean getIsSettled() {
		return isSettled;
	}

	public void setIsSettled(Boolean isSettled) {
		this.isSettled = isSettled;
	}

	public String getArtWorkAttachmentId() {
		return artWorkAttachmentId;
	}

	public void setArtWorkAttachmentId(String artWorkAttachmentId) {
		this.artWorkAttachmentId = artWorkAttachmentId;
	}

	public String getArtWorkAttachmentName() {
		return artWorkAttachmentName;
	}

	public void setArtWorkAttachmentName(String artWorkAttachmentName) {
		this.artWorkAttachmentName = artWorkAttachmentName;
	}

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public String getLogOnId() {
		return logOnId;
	}

	public void setLogOnId(String logOnId) {
		this.logOnId = logOnId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getEcomOrderId() {
		return ecomOrderId;
	}

	public void setEcomOrderId(String ecomOrderId) {
		this.ecomOrderId = ecomOrderId;
	}
	
	
	
	
}
