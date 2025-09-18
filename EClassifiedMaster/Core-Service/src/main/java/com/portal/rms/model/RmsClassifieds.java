package com.portal.rms.model;

import java.math.BigDecimal;
import java.util.List;

public class RmsClassifieds {
	
	private String orderId;
	private String orderNo;
	private String orderDate;
	private List<String> editions;
	private String city;
	private String publishDate;
	private String gstNo;
	private String clientName;
	private String emailId;
	private String mobileNo;;
	private String address1;
	private String address2;
	private String address3;
	private String pinCode;
	private String state;
	private String clientcode;
	private String itemId;
	private List<String> publishDates;
	private String bankRefNo;
	private String paymentMode;
	private String createdTs;
	
	private Integer classifiedType;
	private String classifiedTypeStr;
	private Integer classifiedAdsType;
	private String classifiedAdsTypeStr;
	private Integer scheme;
	private String schemeStr;
	private Integer category;
	private String categoryStr;
	private Integer subCategory;
	private String subCategoryStr;
	private Integer lang;
	private String langStr;
	private String content;
	private Integer createdBy;
	private Integer changedBy;
	private String changedTs;
	private Integer classifiedAdsSubType;
	private String classifiedAdsSubTypeStr;
	private String customerId;
	private Integer userTypeId;
	private BigDecimal paidAmount;
	private String contentStatus;
	private String respaymentStatus;
	private Boolean downloadStatus;
	private String paymentStatus;
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
	public List<String> getEditions() {
		return editions;
	}
	public void setEditions(List<String> editions) {
		this.editions = editions;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getClientcode() {
		return clientcode;
	}
	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public List<String> getPublishDates() {
		return publishDates;
	}
	public void setPublishDates(List<String> publishDates) {
		this.publishDates = publishDates;
	}
	public String getBankRefNo() {
		return bankRefNo;
	}
	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}
	public Integer getClassifiedType() {
		return classifiedType;
	}
	public void setClassifiedType(Integer classifiedType) {
		this.classifiedType = classifiedType;
	}
	public String getClassifiedTypeStr() {
		return classifiedTypeStr;
	}
	public void setClassifiedTypeStr(String classifiedTypeStr) {
		this.classifiedTypeStr = classifiedTypeStr;
	}
	public Integer getClassifiedAdsType() {
		return classifiedAdsType;
	}
	public void setClassifiedAdsType(Integer classifiedAdsType) {
		this.classifiedAdsType = classifiedAdsType;
	}
	public String getClassifiedAdsTypeStr() {
		return classifiedAdsTypeStr;
	}
	public void setClassifiedAdsTypeStr(String classifiedAdsTypeStr) {
		this.classifiedAdsTypeStr = classifiedAdsTypeStr;
	}
	public Integer getScheme() {
		return scheme;
	}
	public void setScheme(Integer scheme) {
		this.scheme = scheme;
	}
	public String getSchemeStr() {
		return schemeStr;
	}
	public void setSchemeStr(String schemeStr) {
		this.schemeStr = schemeStr;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getCategoryStr() {
		return categoryStr;
	}
	public void setCategoryStr(String categoryStr) {
		this.categoryStr = categoryStr;
	}
	public Integer getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(Integer subCategory) {
		this.subCategory = subCategory;
	}
	public String getSubCategoryStr() {
		return subCategoryStr;
	}
	public void setSubCategoryStr(String subCategoryStr) {
		this.subCategoryStr = subCategoryStr;
	}
	public Integer getLang() {
		return lang;
	}
	public void setLang(Integer lang) {
		this.lang = lang;
	}
	public String getLangStr() {
		return langStr;
	}
	public void setLangStr(String langStr) {
		this.langStr = langStr;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}
	public String getChangedTs() {
		return changedTs;
	}
	public void setChangedTs(String changedTs) {
		this.changedTs = changedTs;
	}
	public Integer getClassifiedAdsSubType() {
		return classifiedAdsSubType;
	}
	public void setClassifiedAdsSubType(Integer classifiedAdsSubType) {
		this.classifiedAdsSubType = classifiedAdsSubType;
	}
	public String getClassifiedAdsSubTypeStr() {
		return classifiedAdsSubTypeStr;
	}
	public void setClassifiedAdsSubTypeStr(String classifiedAdsSubTypeStr) {
		this.classifiedAdsSubTypeStr = classifiedAdsSubTypeStr;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
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
	
	public Boolean getDownloadStatus() {
		return downloadStatus;
	}
	public void setDownloadStatus(Boolean downloadStatus) {
		this.downloadStatus = downloadStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRespaymentStatus() {
		return respaymentStatus;
	}
	public void setRespaymentStatus(String respaymentStatus) {
		this.respaymentStatus = respaymentStatus;
	}
	
	

}
