package com.portal.rms.model;

import java.util.List;

public class InsertionPo {
	
	private String caption;
	private Integer noOfInsertions;
//	private Double pageWidth;
//	private Double pageHeight;
	private String spaceHeight;
	private String spaceWidth;
	private String ratePerSquareCms;
	private Integer scheme;
//	private String adsTypeStr;
//	private String adsSubTypeStr;
//	private Double extraLineRate = 0.0;
//	private Integer minLines;
//	private Integer actualLines;
//	private Double extraLineAmount;
	private String totalAmount;
	private List<String> publishDates;
	private Integer pagePosition;
	private Integer classifiedAdsSubtype;
	
	
	private Integer productGroup;
    private Integer productSubGroup;
    private Integer productChildGroup;
    
    private String categoryDiscount;
    private String multiDiscount;
    private String additionalDiscount;
    private String surchargeRate;
    private String cgst;
    private String sgst;
    private String igst;
//    private Double gstTotal;
//    private Double totalDiscount;
    
    
    private String cgstValue;
    private String sgstValue;
    private String igstValue;
    
    private String multiDiscountAmount;
    private String additionalDiscountAmount;
    private String surchargeAmount;
    private String categoryDiscountAmount;
	
    private Double additionalDiscountEdit;
    private Integer positioningDiscount;
    private Double premiumDiscountEdit;
    private String rateCard;
	
	
	
	
	
	private List<Integer> editions;
	private Integer adsSubType;
	private Integer fixedFormat;
	private Double rate;
	
	private Integer editionType;
	private Integer formatType;
	private Double width;
	private Double height;
	private Integer pageNumber;
	private Integer pageInstructions;
	private List<Integer> discounts;
	private Integer schemeId;
	private Integer employeeBookingId;
	private Integer employeeState;
	private Integer customerIdStateCode;
	
	private Double additionalDiscountPercentage;
	private Double premiumDiscPercent;
	private Integer addType;
	
	private Double amount;
	private Double grandTotal;
	private Double discountTotal;
	private List<RmsTaxModel> taxModel;
	private List<RmsDiscountModel> discountModel;
	private Boolean withSchemeFlag;
	private Integer editionCountForMutiDiscount;
	private List<Integer> validEditionsForMultipleDiscount;
	private Integer billableDays;
	private Integer noOfDays;
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public Integer getNoOfInsertions() {
		return noOfInsertions;
	}
	public void setNoOfInsertions(Integer noOfInsertions) {
		this.noOfInsertions = noOfInsertions;
	}
	public String getSpaceHeight() {
		return spaceHeight;
	}
	public void setSpaceHeight(String spaceHeight) {
		this.spaceHeight = spaceHeight;
	}
	public String getSpaceWidth() {
		return spaceWidth;
	}
	public void setSpaceWidth(String spaceWidth) {
		this.spaceWidth = spaceWidth;
	}
	public String getRatePerSquareCms() {
		return ratePerSquareCms;
	}
	public void setRatePerSquareCms(String ratePerSquareCms) {
		this.ratePerSquareCms = ratePerSquareCms;
	}
	public Integer getScheme() {
		return scheme;
	}
	public void setScheme(Integer scheme) {
		this.scheme = scheme;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<String> getPublishDates() {
		return publishDates;
	}
	public void setPublishDates(List<String> publishDates) {
		this.publishDates = publishDates;
	}
	public Integer getPagePosition() {
		return pagePosition;
	}
	public void setPagePosition(Integer pagePosition) {
		this.pagePosition = pagePosition;
	}
	public Integer getClassifiedAdsSubtype() {
		return classifiedAdsSubtype;
	}
	public void setClassifiedAdsSubtype(Integer classifiedAdsSubtype) {
		this.classifiedAdsSubtype = classifiedAdsSubtype;
	}
	public Integer getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(Integer productGroup) {
		this.productGroup = productGroup;
	}
	public Integer getProductSubGroup() {
		return productSubGroup;
	}
	public void setProductSubGroup(Integer productSubGroup) {
		this.productSubGroup = productSubGroup;
	}
	public Integer getProductChildGroup() {
		return productChildGroup;
	}
	public void setProductChildGroup(Integer productChildGroup) {
		this.productChildGroup = productChildGroup;
	}
	public String getCategoryDiscount() {
		return categoryDiscount;
	}
	public void setCategoryDiscount(String categoryDiscount) {
		this.categoryDiscount = categoryDiscount;
	}
	public String getMultiDiscount() {
		return multiDiscount;
	}
	public void setMultiDiscount(String multiDiscount) {
		this.multiDiscount = multiDiscount;
	}
	public String getAdditionalDiscount() {
		return additionalDiscount;
	}
	public void setAdditionalDiscount(String additionalDiscount) {
		this.additionalDiscount = additionalDiscount;
	}
	public String getSurchargeRate() {
		return surchargeRate;
	}
	public void setSurchargeRate(String surchargeRate) {
		this.surchargeRate = surchargeRate;
	}
	public String getCgst() {
		return cgst;
	}
	public void setCgst(String cgst) {
		this.cgst = cgst;
	}
	public String getSgst() {
		return sgst;
	}
	public void setSgst(String sgst) {
		this.sgst = sgst;
	}
	public String getIgst() {
		return igst;
	}
	public void setIgst(String igst) {
		this.igst = igst;
	}
	public String getCgstValue() {
		return cgstValue;
	}
	public void setCgstValue(String cgstValue) {
		this.cgstValue = cgstValue;
	}
	public String getSgstValue() {
		return sgstValue;
	}
	public void setSgstValue(String sgstValue) {
		this.sgstValue = sgstValue;
	}
	public String getIgstValue() {
		return igstValue;
	}
	public void setIgstValue(String igstValue) {
		this.igstValue = igstValue;
	}
	public String getMultiDiscountAmount() {
		return multiDiscountAmount;
	}
	public void setMultiDiscountAmount(String multiDiscountAmount) {
		this.multiDiscountAmount = multiDiscountAmount;
	}
	public String getAdditionalDiscountAmount() {
		return additionalDiscountAmount;
	}
	public void setAdditionalDiscountAmount(String additionalDiscountAmount) {
		this.additionalDiscountAmount = additionalDiscountAmount;
	}
	public String getSurchargeAmount() {
		return surchargeAmount;
	}
	public void setSurchargeAmount(String surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}
	public String getCategoryDiscountAmount() {
		return categoryDiscountAmount;
	}
	public void setCategoryDiscountAmount(String categoryDiscountAmount) {
		this.categoryDiscountAmount = categoryDiscountAmount;
	}
	public Double getAdditionalDiscountEdit() {
		return additionalDiscountEdit;
	}
	public void setAdditionalDiscountEdit(Double additionalDiscountEdit) {
		this.additionalDiscountEdit = additionalDiscountEdit;
	}
	public Integer getPositioningDiscount() {
		return positioningDiscount;
	}
	public void setPositioningDiscount(Integer positioningDiscount) {
		this.positioningDiscount = positioningDiscount;
	}
	public Double getPremiumDiscountEdit() {
		return premiumDiscountEdit;
	}
	public void setPremiumDiscountEdit(Double premiumDiscountEdit) {
		this.premiumDiscountEdit = premiumDiscountEdit;
	}
	public String getRateCard() {
		return rateCard;
	}
	public void setRateCard(String rateCard) {
		this.rateCard = rateCard;
	}
	public List<Integer> getEditions() {
		return editions;
	}
	public void setEditions(List<Integer> editions) {
		this.editions = editions;
	}
	public Integer getAdsSubType() {
		return adsSubType;
	}
	public void setAdsSubType(Integer adsSubType) {
		this.adsSubType = adsSubType;
	}
	public Integer getFixedFormat() {
		return fixedFormat;
	}
	public void setFixedFormat(Integer fixedFormat) {
		this.fixedFormat = fixedFormat;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Integer getEditionType() {
		return editionType;
	}
	public void setEditionType(Integer editionType) {
		this.editionType = editionType;
	}
	public Integer getFormatType() {
		return formatType;
	}
	public void setFormatType(Integer formatType) {
		this.formatType = formatType;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageInstructions() {
		return pageInstructions;
	}
	public void setPageInstructions(Integer pageInstructions) {
		this.pageInstructions = pageInstructions;
	}
	public List<Integer> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(List<Integer> discounts) {
		this.discounts = discounts;
	}
	public Integer getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}
	public Integer getEmployeeBookingId() {
		return employeeBookingId;
	}
	public void setEmployeeBookingId(Integer employeeBookingId) {
		this.employeeBookingId = employeeBookingId;
	}
	public Integer getEmployeeState() {
		return employeeState;
	}
	public void setEmployeeState(Integer employeeState) {
		this.employeeState = employeeState;
	}
	public Integer getCustomerIdStateCode() {
		return customerIdStateCode;
	}
	public void setCustomerIdStateCode(Integer customerIdStateCode) {
		this.customerIdStateCode = customerIdStateCode;
	}
	public Double getAdditionalDiscountPercentage() {
		return additionalDiscountPercentage;
	}
	public void setAdditionalDiscountPercentage(Double additionalDiscountPercentage) {
		this.additionalDiscountPercentage = additionalDiscountPercentage;
	}
	public Double getPremiumDiscPercent() {
		return premiumDiscPercent;
	}
	public void setPremiumDiscPercent(Double premiumDiscPercent) {
		this.premiumDiscPercent = premiumDiscPercent;
	}
	public Integer getAddType() {
		return addType;
	}
	public void setAddType(Integer addType) {
		this.addType = addType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public Double getDiscountTotal() {
		return discountTotal;
	}
	public void setDiscountTotal(Double discountTotal) {
		this.discountTotal = discountTotal;
	}
	public List<RmsTaxModel> getTaxModel() {
		return taxModel;
	}
	public void setTaxModel(List<RmsTaxModel> taxModel) {
		this.taxModel = taxModel;
	}
	public List<RmsDiscountModel> getDiscountModel() {
		return discountModel;
	}
	public void setDiscountModel(List<RmsDiscountModel> discountModel) {
		this.discountModel = discountModel;
	}
	public Boolean getWithSchemeFlag() {
		return withSchemeFlag;
	}
	public void setWithSchemeFlag(Boolean withSchemeFlag) {
		this.withSchemeFlag = withSchemeFlag;
	}
	public Integer getEditionCountForMutiDiscount() {
		return editionCountForMutiDiscount;
	}
	public void setEditionCountForMutiDiscount(Integer editionCountForMutiDiscount) {
		this.editionCountForMutiDiscount = editionCountForMutiDiscount;
	}
	public List<Integer> getValidEditionsForMultipleDiscount() {
		return validEditionsForMultipleDiscount;
	}
	public void setValidEditionsForMultipleDiscount(List<Integer> validEditionsForMultipleDiscount) {
		this.validEditionsForMultipleDiscount = validEditionsForMultipleDiscount;
	}
	public Integer getBillableDays() {
		return billableDays;
	}
	public void setBillableDays(Integer billableDays) {
		this.billableDays = billableDays;
	}
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
    
    
    
	
	
}
