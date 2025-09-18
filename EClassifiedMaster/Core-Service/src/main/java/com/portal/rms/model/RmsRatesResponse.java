package com.portal.rms.model;

import java.util.List;

public class RmsRatesResponse {

//	private Double totalAmount;
//	private Double cgst;
//	private Double sgst;
//	private Double igst;
//	private Double discount;
	private Double amount;
	
	private Double grandTotal;
	private Double discountTotal;
	private Double premiumTotal;
	private List<RmsTaxModel> tax;
	private List<RmsDiscountModel> discounts;
	private List<RmsPremiumModel> premium;
	private Double gstTotal;
	
	private String grandTotalString;
	private String discountTotalString;
	private String premiumTotalString;
	private String gstTotalString;
	private String amountString;
	private Integer multiDiscountEditionCount;
	private Double aggredPremium;
	private Double afterDiscountTotal;
	private Double afterPremiumTotal;
	private Integer billableDays;
	private Double cardRate;
	
	
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
	public List<RmsTaxModel> getTax() {
		return tax;
	}
	public void setTax(List<RmsTaxModel> tax) {
		this.tax = tax;
	}
	public List<RmsDiscountModel> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(List<RmsDiscountModel> discounts) {
		this.discounts = discounts;
	}
	public Double getGstTotal() {
		return gstTotal;
	}
	public void setGstTotal(Double gstTotal) {
		this.gstTotal = gstTotal;
	}
	public String getGrandTotalString() {
		return grandTotalString;
	}
	public void setGrandTotalString(String grandTotalString) {
		this.grandTotalString = grandTotalString;
	}
	public String getDiscountTotalString() {
		return discountTotalString;
	}
	public void setDiscountTotalString(String discountTotalString) {
		this.discountTotalString = discountTotalString;
	}
	public String getGstTotalString() {
		return gstTotalString;
	}
	public void setGstTotalString(String gstTotalString) {
		this.gstTotalString = gstTotalString;
	}
	public String getAmountString() {
		return amountString;
	}
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	public Integer getMultiDiscountEditionCount() {
		return multiDiscountEditionCount;
	}
	public void setMultiDiscountEditionCount(Integer multiDiscountEditionCount) {
		this.multiDiscountEditionCount = multiDiscountEditionCount;
	}
	public Double getAggredPremium() {
		return aggredPremium;
	}
	public void setAggredPremium(Double aggredPremium) {
		this.aggredPremium = aggredPremium;
	}
	public List<RmsPremiumModel> getPremium() {
		return premium;
	}
	public void setPremium(List<RmsPremiumModel> premium) {
		this.premium = premium;
	}
	public Double getPremiumTotal() {
		return premiumTotal;
	}
	public void setPremiumTotal(Double premiumTotal) {
		this.premiumTotal = premiumTotal;
	}
	public String getPremiumTotalString() {
		return premiumTotalString;
	}
	public void setPremiumTotalString(String premiumTotalString) {
		this.premiumTotalString = premiumTotalString;
	}
	public Double getAfterDiscountTotal() {
		return afterDiscountTotal;
	}
	public void setAfterDiscountTotal(Double afterDiscountTotal) {
		this.afterDiscountTotal = afterDiscountTotal;
	}
	public Double getAfterPremiumTotal() {
		return afterPremiumTotal;
	}
	public void setAfterPremiumTotal(Double afterPremiumTotal) {
		this.afterPremiumTotal = afterPremiumTotal;
	}
	public Integer getBillableDays() {
		return billableDays;
	}
	public void setBillableDays(Integer billableDays) {
		this.billableDays = billableDays;
	}
	public Double getCardRate() {
		return cardRate;
	}
	public void setCardRate(Double cardRate) {
		this.cardRate = cardRate;
	}
	
	
	
	
	
	
}
