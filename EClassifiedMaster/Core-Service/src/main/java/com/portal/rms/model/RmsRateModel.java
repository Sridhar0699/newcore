package com.portal.rms.model;

import java.util.List;

public class RmsRateModel {
	
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
	public Double getAdditionalDiscountPercentage() {
		return additionalDiscountPercentage;
	}
	public void setAdditionalDiscountPercentage(Double additionalDiscountPercentage) {
		this.additionalDiscountPercentage = additionalDiscountPercentage;
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
	
	
	
}
