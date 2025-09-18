package com.portal.rms.model;

public class RmsTaxModel {
	
	private String type;
	private Double percent;
	private Double amount;
	
	private String amountString;
	private String percentString;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getAmountString() {
		return amountString;
	}
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	public String getPercentString() {
		return percentString;
	}
	public void setPercentString(String percentString) {
		this.percentString = percentString;
	}
	
	

}
