package com.portal.rms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "rms_order_items")
public class RmsOrderItems extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "rms_item_id")
	private String rmsItemId;
	
	@Column(name = "item_id")
	private String itemId;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "no_of_insertions")
	private Integer noOfInsertions;
	
	@Column(name = "size_width")
	private Double sizeWidth;
	
	@Column(name = "size_height")
	private Double sizeHeight;
	
	@Column(name = "space_width")
	private Double spaceWidth;
	
	@Column(name = "space_height")
	private Double spaceHeight;
	
	@Column(name = "page_position")
	private Integer pagePosition;
	
	@Column(name = "created_ts")
	private Date createdTs;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "changed_ts")
	private Date changedTs;
	
	@Column(name = "changed_by")
	private Integer changedBy;
	
	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;
	
	@Column(name = "format_type")
	private Integer formatType;
	
	@Column(name = "fixed_format")
	private Integer fixedFormat;
	
	@Column(name = "page_number")
	private Integer pageNumber;
	
	@Column(name = "category_discount")
	private Double categoryDiscount;
	
	@Column(name = "multi_discount")
	private Double multiDiscount;
	
	@Column(name = "additional_discount")
	private Double additionalDiscount;
	
	@Column(name = "surcharge_rate")
	private Double surchargeRate;
	
	@Column(name = "surcharge_amount")
	private Double surchargeAmount;
	
	@Column(name = "multi_discount_amount")
	private Double multiDiscountAmount;
	
	@Column(name = "additional_discount_amount")
    private Double additionalDiscountAmount;
	
	@Column(name="category_discount_amount")
	private Double categoryDiscountAmount;
	
	@Column(name = "base_amount")
	private Double amount;
	@Column(name = "grand_total")
	private Double grandTotal;
	@Column(name = "discount_total")
	private Double discountTotal;
	@Column(name = "gst_total")
	private Double gstTotal;
	
	@Column(name = "premium_discount")
	private Double premiumDiscount;
	@Column(name = "premium_discount_amount")
	private Double premiumDiscountAmount;

	@Column(name = "ad_type")
	private Integer adType;
	
	@Column(name = "caption")
	private String caption;
	
	@Column(name = "igst_amount")
	private Double igstAmount;
	
	@Column(name = "sgst_amount")
	private Double sgstAmount;
	
	@Column(name = "cgst_amount")
	private Double cgstAmount;
	
	@Column(name = "multi_discount_edi_count")
	private Integer multiDiscountEdiCount;
	
	@Column(name = "billable_days")
	private Integer billableDays;
	
	@Column(name = "aggred_premium_dis_per")
	private Double aggredPremiumDisPer;
	
	@Column(name = "master_premium_per")
	private Double masterPremiumPer;
	
	@Column(name = "premium_amount")
	private Double premiumAmount;
	
	@Column(name = "combined_discount")
	private Double combinedDiscount;
	
	@Column(name = "card_rate")
	private Double cardRate;
	
	public String getRmsItemId() {
		return rmsItemId;
	}

	public void setRmsItemId(String rmsItemId) {
		this.rmsItemId = rmsItemId;
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

	public Integer getNoOfInsertions() {
		return noOfInsertions;
	}

	public void setNoOfInsertions(Integer noOfInsertions) {
		this.noOfInsertions = noOfInsertions;
	}

	public Double getSizeWidth() {
		return sizeWidth;
	}

	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

	public Double getSizeHeight() {
		return sizeHeight;
	}

	public void setSizeHeight(Double sizeHeight) {
		this.sizeHeight = sizeHeight;
	}

	public Double getSpaceWidth() {
		return spaceWidth;
	}

	public void setSpaceWidth(Double spaceWidth) {
		this.spaceWidth = spaceWidth;
	}

	public Double getSpaceHeight() {
		return spaceHeight;
	}

	public void setSpaceHeight(Double spaceHeight) {
		this.spaceHeight = spaceHeight;
	}

	public Integer getPagePosition() {
		return pagePosition;
	}

	public void setPagePosition(Integer pagePosition) {
		this.pagePosition = pagePosition;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public Integer getFormatType() {
		return formatType;
	}

	public void setFormatType(Integer formatType) {
		this.formatType = formatType;
	}

	public Integer getFixedFormat() {
		return fixedFormat;
	}

	public void setFixedFormat(Integer fixedFormat) {
		this.fixedFormat = fixedFormat;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Double getCategoryDiscount() {
		return categoryDiscount;
	}

	public void setCategoryDiscount(Double categoryDiscount) {
		this.categoryDiscount = categoryDiscount;
	}

	public Double getMultiDiscount() {
		return multiDiscount;
	}

	public void setMultiDiscount(Double multiDiscount) {
		this.multiDiscount = multiDiscount;
	}

	public Double getAdditionalDiscount() {
		return additionalDiscount;
	}

	public void setAdditionalDiscount(Double additionalDiscount) {
		this.additionalDiscount = additionalDiscount;
	}

	public Double getSurchargeRate() {
		return surchargeRate;
	}

	public void setSurchargeRate(Double surchargeRate) {
		this.surchargeRate = surchargeRate;
	}

	public Double getSurchargeAmount() {
		return surchargeAmount;
	}

	public void setSurchargeAmount(Double surchargeAmount) {
		this.surchargeAmount = surchargeAmount;
	}

	public Double getMultiDiscountAmount() {
		return multiDiscountAmount;
	}

	public void setMultiDiscountAmount(Double multiDiscountAmount) {
		this.multiDiscountAmount = multiDiscountAmount;
	}

	public Double getAdditionalDiscountAmount() {
		return additionalDiscountAmount;
	}

	public void setAdditionalDiscountAmount(Double additionalDiscountAmount) {
		this.additionalDiscountAmount = additionalDiscountAmount;
	}

	public Double getCategoryDiscountAmount() {
		return categoryDiscountAmount;
	}

	public void setCategoryDiscountAmount(Double categoryDiscountAmount) {
		this.categoryDiscountAmount = categoryDiscountAmount;
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

	public Double getGstTotal() {
		return gstTotal;
	}

	public void setGstTotal(Double gstTotal) {
		this.gstTotal = gstTotal;
	}

	public Double getPremiumDiscount() {
		return premiumDiscount;
	}

	public void setPremiumDiscount(Double premiumDiscount) {
		this.premiumDiscount = premiumDiscount;
	}

	public Double getPremiumDiscountAmount() {
		return premiumDiscountAmount;
	}

	public void setPremiumDiscountAmount(Double premiumDiscountAmount) {
		this.premiumDiscountAmount = premiumDiscountAmount;
	}

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Double getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(Double igstAmount) {
		this.igstAmount = igstAmount;
	}

	public Double getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(Double sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public Double getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(Double cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	public Integer getMultiDiscountEdiCount() {
		return multiDiscountEdiCount;
	}

	public void setMultiDiscountEdiCount(Integer multiDiscountEdiCount) {
		this.multiDiscountEdiCount = multiDiscountEdiCount;
	}

	public Integer getBillableDays() {
		return billableDays;
	}

	public void setBillableDays(Integer billableDays) {
		this.billableDays = billableDays;
	}

	public Double getAggredPremiumDisPer() {
		return aggredPremiumDisPer;
	}

	public void setAggredPremiumDisPer(Double aggredPremiumDisPer) {
		this.aggredPremiumDisPer = aggredPremiumDisPer;
	}

	public Double getMasterPremiumPer() {
		return masterPremiumPer;
	}

	public void setMasterPremiumPer(Double masterPremiumPer) {
		this.masterPremiumPer = masterPremiumPer;
	}

	public Double getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Double getCombinedDiscount() {
		return combinedDiscount;
	}

	public void setCombinedDiscount(Double combinedDiscount) {
		this.combinedDiscount = combinedDiscount;
	}

	public Double getCardRate() {
		return cardRate;
	}

	public void setCardRate(Double cardRate) {
		this.cardRate = cardRate;
	}
	
	
}
