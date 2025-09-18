package com.portal.clf.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "clf_order_item_rates")
public class ClfOrderItemRates extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "item_rate_id")
	private String itemRateId;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "item_id")
	private String itemId;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "lines")
	private Integer lines;
	
	@Column(name = "extra_line_rate")
	private Double extraLineRate;
	
	@Column(name = "line_count")
	private Integer lineCount;
	
	@Column(name = "char_count")
	private Integer charCount;
	
	@Column(name = "total_amount")
	private Double totalAmount;
	
	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;
	
	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;
	
	@Column(name = "gst_total")
	private Double gstTotal;
	
	@Column(name = "cgst")
	private Double cgst;
	
	@Column(name = "sgst")
	private Double sgst;
	
	@Column(name = "igst")
	private Double igst;
	
	@Column(name = "total_discount")
	private Double totalDiscount;

	@Column(name = "agency_commition")
	private Double agencyCommition;
	
	@Column(name = "cgst_value")
	private Double cgstValue;
	
	@Column(name = "sgst_value")
    private Double sgstValue;
	
	@Column(name = "igst_value")
    private Double igstValue;
	
	@Column(name = "amount")
    private Double amount;
	
	@Column(name = "rate_per_square_cms")
    private Double ratePerSquareCms;
	
	@Column(name = "category_discount_amount")
	private Double categoryDiscountAmount;
	
	@Column(name = "special_discount_amount")
	private Double speicalDiscountAmount;
	
	@Column(name = "category_discount_percentage")
	private Double categoryDiscountPercentage;
	
	@Column(name = "special_discount_percentage")
	private Double specialDiscountPercentage;

	
	public Double getAgencyCommition() {
		return agencyCommition;
	}

	public void setAgencyCommition(Double agencyCommition) {
		this.agencyCommition = agencyCommition;
	}
	public String getItemRateId() {
		return itemRateId;
	}

	public void setItemRateId(String itemRateId) {
		this.itemRateId = itemRateId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getLines() {
		return lines;
	}

	public void setLines(Integer lines) {
		this.lines = lines;
	}

	public Double getExtraLineRate() {
		return extraLineRate;
	}

	public void setExtraLineRate(Double extraLineRate) {
		this.extraLineRate = extraLineRate;
	}

	public Integer getLineCount() {
		return lineCount;
	}

	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}

	public Integer getCharCount() {
		return charCount;
	}

	public void setCharCount(Integer charCount) {
		this.charCount = charCount;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	public Date getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public Double getCgst() {
		return cgst;
	}

	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}

	public Double getSgst() {
		return sgst;
	}

	public void setSgst(Double sgst) {
		this.sgst = sgst;
	}

	public Double getIgst() {
		return igst;
	}

	public void setIgst(Double igst) {
		this.igst = igst;
	}

	public Double getGstTotal() {
		return gstTotal;
	}

	public void setGstTotal(Double gstTotal) {
		this.gstTotal = gstTotal;
	}

	public Double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Double getCgstValue() {
		return cgstValue;
	}

	public void setCgstValue(Double cgstValue) {
		this.cgstValue = cgstValue;
	}

	public Double getSgstValue() {
		return sgstValue;
	}

	public void setSgstValue(Double sgstValue) {
		this.sgstValue = sgstValue;
	}

	public Double getIgstValue() {
		return igstValue;
	}

	public void setIgstValue(Double igstValue) {
		this.igstValue = igstValue;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRatePerSquareCms() {
		return ratePerSquareCms;
	}

	public void setRatePerSquareCms(Double ratePerSquareCms) {
		this.ratePerSquareCms = ratePerSquareCms;
	}

	public Double getCategoryDiscountAmount() {
		return categoryDiscountAmount;
	}

	public void setCategoryDiscountAmount(Double categoryDiscountAmount) {
		this.categoryDiscountAmount = categoryDiscountAmount;
	}

	public Double getSpeicalDiscountAmount() {
		return speicalDiscountAmount;
	}

	public void setSpeicalDiscountAmount(Double speicalDiscountAmount) {
		this.speicalDiscountAmount = speicalDiscountAmount;
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
