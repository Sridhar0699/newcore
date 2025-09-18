package com.portal.clf.models;

public class PostClassifiedRates {

	private Double rate = 0.0;
	private Double extraLineRate = 0.0;
	private Integer minLines;
	private Integer actualLines;
	private Double extraLineAmount;
	private Double totalAmount;
	private Double gstTaxAmount;
	private Double agencyCommitionAmount;
	private Double afterCatgoryDiscount;
	private Double afterSpeicalDiscount;
	private Double categoryDiscountPercentage;
	private Double specialDiscountPercentage;
	
	
	
	public Double getGstTaxAmount() {
		return gstTaxAmount;
	}

	public void setGstTaxAmount(Double gstTaxAmount) {
		this.gstTaxAmount = gstTaxAmount;
	}

	public Double getAgencyCommitionAmount() {
		return agencyCommitionAmount;
	}

	public void setAgencyCommitionAmount(Double agencyCommitionAmount) {
		this.agencyCommitionAmount = agencyCommitionAmount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getExtraLineRate() {
		return extraLineRate;
	}

	public void setExtraLineRate(Double extraLineRate) {
		this.extraLineRate = extraLineRate;
	}

	public Integer getMinLines() {
		return minLines;
	}

	public void setMinLines(Integer minLines) {
		this.minLines = minLines;
	}

	public Integer getActualLines() {
		return actualLines;
	}

	public void setActualLines(Integer actualLines) {
		this.actualLines = actualLines;
	}

	public Double getExtraLineAmount() {
		return extraLineAmount;
	}

	public void setExtraLineAmount(Double extraLineAmount) {
		this.extraLineAmount = extraLineAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getAfterCatgoryDiscount() {
		return afterCatgoryDiscount;
	}

	public void setAfterCatgoryDiscount(Double afterCatgoryDiscount) {
		this.afterCatgoryDiscount = afterCatgoryDiscount;
	}

	public Double getAfterSpeicalDiscount() {
		return afterSpeicalDiscount;
	}

	public void setAfterSpeicalDiscount(Double afterSpeicalDiscount) {
		this.afterSpeicalDiscount = afterSpeicalDiscount;
	}

	public Double getCategoryDiscountPercentage() {
		return categoryDiscountPercentage;
	}

	public void setCategoryDiscountPercentage(Double categoryDiscountPercentage) {
		this.categoryDiscountPercentage = categoryDiscountPercentage;
	}

	public Double getSpecialDiscountPercentage() {
		return specialDiscountPercentage;
	}

	public void setSpecialDiscountPercentage(Double specialDiscountPercentage) {
		this.specialDiscountPercentage = specialDiscountPercentage;
	}

}
