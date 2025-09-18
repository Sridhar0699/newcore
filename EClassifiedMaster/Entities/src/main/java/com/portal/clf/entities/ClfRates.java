package com.portal.clf.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "clf_rates")
public class ClfRates extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "rate_id")
	private String rateId;
	
	@Column(name = "classified_ads_type")
	private Integer classifiedAdsType;
	
	@Column(name = "classified_ads_subtype")
	private Integer classifiedAdsSubtype;
	
	@Column(name = "edition_id")
	private Integer editionId;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "extra_line_amount")
	private Double extraLineAmount;
	
	@Column(name = "min_lines")
	private Integer minLines;
	
	@Column(name = "char_count_per_line")
	private Integer charCountPerLine;
	
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
	
	@Column(name = "category")
	private Integer category;
	
	@Column(name = "sub_category")
	private Integer subCategory;

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Integer subCategory) {
		this.subCategory = subCategory;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public Integer getClassifiedAdsType() {
		return classifiedAdsType;
	}

	public void setClassifiedAdsType(Integer classifiedAdsType) {
		this.classifiedAdsType = classifiedAdsType;
	}

	public Integer getClassifiedAdsSubtype() {
		return classifiedAdsSubtype;
	}

	public void setClassifiedAdsSubtype(Integer classifiedAdsSubtype) {
		this.classifiedAdsSubtype = classifiedAdsSubtype;
	}

	public Integer getEditionId() {
		return editionId;
	}

	public void setEditionId(Integer editionId) {
		this.editionId = editionId;
	}


	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getExtraLineAmount() {
		return extraLineAmount;
	}

	public void setExtraLineAmount(Double extraLineAmount) {
		this.extraLineAmount = extraLineAmount;
	}

	public Integer getMinLines() {
		return minLines;
	}

	public void setMinLines(Integer minLines) {
		this.minLines = minLines;
	}

	public Integer getCharCountPerLine() {
		return charCountPerLine;
	}

	public void setCharCountPerLine(Integer charCountPerLine) {
		this.charCountPerLine = charCountPerLine;
	}

	public Integer getCreatedBy() {
		return createdBy;
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

}
