package com.portal.clf.models;

import java.util.List;

public class ClassifiedRateDetails {

	private List<ClassifiedRates> classifiedRates;
	private Double tax;
	private Integer maxLinesAllowed;
	private Double agencyCommission;
	private Double categoryDiscount;
	private Double specialDiscount;

	public List<ClassifiedRates> getClassifiedRates() {
		return classifiedRates;
	}

	public void setClassifiedRates(List<ClassifiedRates> classifiedRates) {
		this.classifiedRates = classifiedRates;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Integer getMaxLinesAllowed() {
		return maxLinesAllowed;
	}

	public void setMaxLinesAllowed(Integer maxLinesAllowed) {
		this.maxLinesAllowed = maxLinesAllowed;
	}

	public Double getAgencyCommission() {
		return agencyCommission;
	}

	public void setAgencyCommission(Double agencyCommission) {
		this.agencyCommission = agencyCommission;
	}

	public Double getCategoryDiscount() {
		return categoryDiscount;
	}

	public void setCategoryDiscount(Double categoryDiscount) {
		this.categoryDiscount = categoryDiscount;
	}

	public Double getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(Double specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	

}
