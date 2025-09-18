package com.portal.rms.model;

import java.util.List;

public class InsertionObjectDisplay{
	private String content;
	private String spaceWidth;
	private String spaceHeight;
	private String ratePerSquareCms;
	private Integer pagePosition;
	private List<String> publishDates;
//	private BigDecimal fixedRate;
//	private BigDecimal paidAmount;

	private String classifiedAdsSubTypeStr;
	private String itemId;
	private List<String> editions;
	private String schemeStr;

	private String categoryGroupDesc;
	private String categorySubgroupDesc;
	private String categoryChildgroupDesc;

	private String editionTypeDesc;

	private String fixedFormatsDesc;
	private String formatTypeDesc;
	private String pageNumberDesc;
	private String categoryDiscountId;
	private String additionalDiscount;
//	private String surchargeRate;

//	private String cgst;
//	private String sgst;
//	private String igst;
	private Integer bookingCode;

	private Integer customerTypeId;

	private Integer categoryGroup;
	private Integer categorySubGroup;
	private Integer categoryChildGroup;

	private Integer editionType;

	private Integer fixedFormat;
	private Integer formatType;
	private Integer pageNumber;
	private String multiDiscount;

	private String categoryDiscount;
	private String positioningDesc;

	private String additionalDiscountAmount;
//	private String surchargeAmount;
	private String multiDiscountAmount;
	private String categoryDiscountAmount;

	private Integer classifiedAdsSubType;
	private Integer scheme;

//	private String igstValue;
//	private String cgstValue;
//	private String sgstValue;
//	private String amount;
//	private String totalAmount;
	private Integer classifiedType;
	private String classifiedTypeDesc;
	private String rmsItemId;
	private String baseAmount;
	private String grandTotal;
	private String discountTotal;
	private String gstTotal;
	private String premiumTotal;
	private String premiumDiscountAmount;
	private String pagePositionDesc;
	private String status;
	private String orderId;
	private String paymentStatus;
	private List<Integer> editionIds;
	private Integer addType;
	private String addTypeDesc;
	private String caption;
//	private String mobileAlt;
//	private String houseNo;
	private List<Integer> discounts;
	private List<String> discountsDesc;
	private String adId;
	private String premiumDiscount;
	private String edition;
	private Integer noOfDays;
	private Integer billableDays;
	private String agreedPremiumDisPer;
	private String wfId;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSpaceWidth() {
		return spaceWidth;
	}
	public void setSpaceWidth(String spaceWidth) {
		this.spaceWidth = spaceWidth;
	}
	public String getSpaceHeight() {
		return spaceHeight;
	}
	public void setSpaceHeight(String spaceHeight) {
		this.spaceHeight = spaceHeight;
	}
	public String getRatePerSquareCms() {
		return ratePerSquareCms;
	}
	public void setRatePerSquareCms(String ratePerSquareCms) {
		this.ratePerSquareCms = ratePerSquareCms;
	}
	public Integer getPagePosition() {
		return pagePosition;
	}
	public void setPagePosition(Integer pagePosition) {
		this.pagePosition = pagePosition;
	}
	public List<String> getPublishDates() {
		return publishDates;
	}
	public void setPublishDates(List<String> publishDates) {
		this.publishDates = publishDates;
	}
//	public BigDecimal getFixedRate() {
//		return fixedRate;
//	}
//	public void setFixedRate(BigDecimal fixedRate) {
//		this.fixedRate = fixedRate;
//	}
//	public BigDecimal getPaidAmount() {
//		return paidAmount;
//	}
//	public void setPaidAmount(BigDecimal paidAmount) {
//		this.paidAmount = paidAmount;
//	}
	public String getClassifiedAdsSubTypeStr() {
		return classifiedAdsSubTypeStr;
	}
	public void setClassifiedAdsSubTypeStr(String classifiedAdsSubTypeStr) {
		this.classifiedAdsSubTypeStr = classifiedAdsSubTypeStr;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public List<String> getEditions() {
		return editions;
	}
	public void setEditions(List<String> editions) {
		this.editions = editions;
	}
	public String getSchemeStr() {
		return schemeStr;
	}
	public void setSchemeStr(String schemeStr) {
		this.schemeStr = schemeStr;
	}
	public String getEditionTypeDesc() {
		return editionTypeDesc;
	}
	public void setEditionTypeDesc(String editionTypeDesc) {
		this.editionTypeDesc = editionTypeDesc;
	}
	public String getFixedFormatsDesc() {
		return fixedFormatsDesc;
	}
	public void setFixedFormatsDesc(String fixedFormatsDesc) {
		this.fixedFormatsDesc = fixedFormatsDesc;
	}
	public String getFormatTypeDesc() {
		return formatTypeDesc;
	}
	public void setFormatTypeDesc(String formatTypeDesc) {
		this.formatTypeDesc = formatTypeDesc;
	}
	public String getPageNumberDesc() {
		return pageNumberDesc;
	}
	public void setPageNumberDesc(String pageNumberDesc) {
		this.pageNumberDesc = pageNumberDesc;
	}
	
	public String getAdditionalDiscount() {
		return additionalDiscount;
	}
	public void setAdditionalDiscount(String additionalDiscount) {
		this.additionalDiscount = additionalDiscount;
	}
//	public String getSurchargeRate() {
//		return surchargeRate;
//	}
//	public void setSurchargeRate(String surchargeRate) {
//		this.surchargeRate = surchargeRate;
//	}
//	public String getCgst() {
//		return cgst;
//	}
//	public void setCgst(String cgst) {
//		this.cgst = cgst;
//	}
//	public String getSgst() {
//		return sgst;
//	}
//	public void setSgst(String sgst) {
//		this.sgst = sgst;
//	}
//	public String getIgst() {
//		return igst;
//	}
//	public void setIgst(String igst) {
//		this.igst = igst;
//	}
	public Integer getBookingCode() {
		return bookingCode;
	}
	public void setBookingCode(Integer bookingCode) {
		this.bookingCode = bookingCode;
	}
	public Integer getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(Integer customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public Integer getCategoryGroup() {
		return categoryGroup;
	}
	public void setCategoryGroup(Integer categoryGroup) {
		this.categoryGroup = categoryGroup;
	}
	public Integer getCategorySubGroup() {
		return categorySubGroup;
	}
	public void setCategorySubGroup(Integer categorySubGroup) {
		this.categorySubGroup = categorySubGroup;
	}
	public Integer getCategoryChildGroup() {
		return categoryChildGroup;
	}
	public void setCategoryChildGroup(Integer categoryChildGroup) {
		this.categoryChildGroup = categoryChildGroup;
	}
	public Integer getEditionType() {
		return editionType;
	}
	public void setEditionType(Integer editionType) {
		this.editionType = editionType;
	}
	public Integer getFixedFormat() {
		return fixedFormat;
	}
	public void setFixedFormat(Integer fixedFormat) {
		this.fixedFormat = fixedFormat;
	}
	public Integer getFormatType() {
		return formatType;
	}
	public void setFormatType(Integer formatType) {
		this.formatType = formatType;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getMultiDiscount() {
		return multiDiscount;
	}
	public void setMultiDiscount(String multiDiscount) {
		this.multiDiscount = multiDiscount;
	}
	public String getCategoryDiscount() {
		return categoryDiscount;
	}
	public void setCategoryDiscount(String categoryDiscount) {
		this.categoryDiscount = categoryDiscount;
	}
	public String getPositioningDesc() {
		return positioningDesc;
	}
	public void setPositioningDesc(String positioningDesc) {
		this.positioningDesc = positioningDesc;
	}
	public String getAdditionalDiscountAmount() {
		return additionalDiscountAmount;
	}
	public void setAdditionalDiscountAmount(String additionalDiscountAmount) {
		this.additionalDiscountAmount = additionalDiscountAmount;
	}
//	public String getSurchargeAmount() {
//		return surchargeAmount;
//	}
//	public void setSurchargeAmount(String surchargeAmount) {
//		this.surchargeAmount = surchargeAmount;
//	}
	public String getMultiDiscountAmount() {
		return multiDiscountAmount;
	}
	public void setMultiDiscountAmount(String multiDiscountAmount) {
		this.multiDiscountAmount = multiDiscountAmount;
	}
	public String getCategoryDiscountAmount() {
		return categoryDiscountAmount;
	}
	public void setCategoryDiscountAmount(String categoryDiscountAmount) {
		this.categoryDiscountAmount = categoryDiscountAmount;
	}
	public Integer getClassifiedAdsSubType() {
		return classifiedAdsSubType;
	}
	public void setClassifiedAdsSubType(Integer classifiedAdsSubType) {
		this.classifiedAdsSubType = classifiedAdsSubType;
	}
	public Integer getScheme() {
		return scheme;
	}
	public void setScheme(Integer scheme) {
		this.scheme = scheme;
	}
//	public String getIgstValue() {
//		return igstValue;
//	}
//	public void setIgstValue(String igstValue) {
//		this.igstValue = igstValue;
//	}
//	public String getCgstValue() {
//		return cgstValue;
//	}
//	public void setCgstValue(String cgstValue) {
//		this.cgstValue = cgstValue;
//	}
//	public String getSgstValue() {
//		return sgstValue;
//	}
//	public void setSgstValue(String sgstValue) {
//		this.sgstValue = sgstValue;
//	}
//	public String getAmount() {
//		return amount;
//	}
//	public void setAmount(String amount) {
//		this.amount = amount;
//	}
//	public String getTotalAmount() {
//		return totalAmount;
//	}
//	public void setTotalAmount(String totalAmount) {
//		this.totalAmount = totalAmount;
//	}
	public Integer getClassifiedType() {
		return classifiedType;
	}
	public void setClassifiedType(Integer classifiedType) {
		this.classifiedType = classifiedType;
	}
	public String getClassifiedTypeDesc() {
		return classifiedTypeDesc;
	}
	public void setClassifiedTypeDesc(String classifiedTypeDesc) {
		this.classifiedTypeDesc = classifiedTypeDesc;
	}
	public String getRmsItemId() {
		return rmsItemId;
	}
	public void setRmsItemId(String rmsItemId) {
		this.rmsItemId = rmsItemId;
	}
	public String getBaseAmount() {
		return baseAmount;
	}
	public void setBaseAmount(String baseAmount) {
		this.baseAmount = baseAmount;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getDiscountTotal() {
		return discountTotal;
	}
	public void setDiscountTotal(String discountTotal) {
		this.discountTotal = discountTotal;
	}
	public String getGstTotal() {
		return gstTotal;
	}
	public void setGstTotal(String gstTotal) {
		this.gstTotal = gstTotal;
	}
	public String getPremiumTotal() {
		return premiumTotal;
	}
	public void setPremiumTotal(String premiumTotal) {
		this.premiumTotal = premiumTotal;
	}
	public String getPremiumDiscountAmount() {
		return premiumDiscountAmount;
	}
	public void setPremiumDiscountAmount(String premiumDiscountAmount) {
		this.premiumDiscountAmount = premiumDiscountAmount;
	}
	
	public String getPagePositionDesc() {
		return pagePositionDesc;
	}
	public void setPagePositionDesc(String pagePositionDesc) {
		this.pagePositionDesc = pagePositionDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getCategoryDiscountId() {
		return categoryDiscountId;
	}
	public void setCategoryDiscountId(String categoryDiscountId) {
		this.categoryDiscountId = categoryDiscountId;
	}
	public List<Integer> getEditionIds() {
		return editionIds;
	}
	public void setEditionIds(List<Integer> editionIds) {
		this.editionIds = editionIds;
	}
	public String getCategoryGroupDesc() {
		return categoryGroupDesc;
	}
	public void setCategoryGroupDesc(String categoryGroupDesc) {
		this.categoryGroupDesc = categoryGroupDesc;
	}
	public String getCategorySubgroupDesc() {
		return categorySubgroupDesc;
	}
	public void setCategorySubgroupDesc(String categorySubgroupDesc) {
		this.categorySubgroupDesc = categorySubgroupDesc;
	}
	public String getCategoryChildgroupDesc() {
		return categoryChildgroupDesc;
	}
	public void setCategoryChildgroupDesc(String categoryChildgroupDesc) {
		this.categoryChildgroupDesc = categoryChildgroupDesc;
	}
	public Integer getAddType() {
		return addType;
	}
	public void setAddType(Integer addType) {
		this.addType = addType;
	}
	public String getAddTypeDesc() {
		return addTypeDesc;
	}
	public void setAddTypeDesc(String addTypeDesc) {
		this.addTypeDesc = addTypeDesc;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
//	public String getMobileAlt() {
//		return mobileAlt;
//	}
//	public void setMobileAlt(String mobileAlt) {
//		this.mobileAlt = mobileAlt;
//	}
//	public String getHouseNo() {
//		return houseNo;
//	}
//	public void setHouseNo(String houseNo) {
//		this.houseNo = houseNo;
//	}
	public List<Integer> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(List<Integer> discounts) {
		this.discounts = discounts;
	}
	public List<String> getDiscountsDesc() {
		return discountsDesc;
	}
	public void setDiscountsDesc(List<String> discountsDesc) {
		this.discountsDesc = discountsDesc;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getPremiumDiscount() {
		return premiumDiscount;
	}
	public void setPremiumDiscount(String premiumDiscount) {
		this.premiumDiscount = premiumDiscount;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
	public Integer getBillableDays() {
		return billableDays;
	}
	public void setBillableDays(Integer billableDays) {
		this.billableDays = billableDays;
	}
	public String getAgreedPremiumDisPer() {
		return agreedPremiumDisPer;
	}
	public void setAgreedPremiumDisPer(String agreedPremiumDisPer) {
		this.agreedPremiumDisPer = agreedPremiumDisPer;
	}
	public String getWfId() {
		return wfId;
	}
	public void setWfId(String wfId) {
		this.wfId = wfId;
	}
	

	
}