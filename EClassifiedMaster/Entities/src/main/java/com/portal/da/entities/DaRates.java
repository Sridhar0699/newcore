package com.portal.da.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "da_rates")
public class DaRates extends BaseEntity{

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
    
    @Column(name = "size_id")
    private Integer sizeId;
    
    @Column(name = "rate")
    private Double rate;
    
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
    
    @Column(name = "category_id")
    private Integer categoryId;

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

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}


}
