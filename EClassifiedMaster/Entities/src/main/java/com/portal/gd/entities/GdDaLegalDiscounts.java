package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_da_legal_discounts")
public class GdDaLegalDiscounts extends BaseEntity {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "legal_discount_id")
    private Integer legalDiscountId;
    
    @Column(name = "category_id")
    private Integer categoryId;
    
    @Column(name = "edition_name")
    private String editionName;
    
    @Column(name = "edition_erp_code")
    private String editionErpCode;
    
    @Column(name = "discount")
    private Double discount;
    
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
    
    @Column(name = "special_discount")
    private Double specialDiscount;

	public Integer getLegalDiscountId() {
		return legalDiscountId;
	}

	public void setLegalDiscountId(Integer legalDiscountId) {
		this.legalDiscountId = legalDiscountId;
	}

	public String getEditionName() {
		return editionName;
	}

	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}

	public String getEditionErpCode() {
		return editionErpCode;
	}

	public void setEditionErpCode(String editionErpCode) {
		this.editionErpCode = editionErpCode;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
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

	public Double getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(Double specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
    
    
}
