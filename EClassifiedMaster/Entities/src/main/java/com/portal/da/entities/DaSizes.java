package com.portal.da.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "da_sizes")
public class DaSizes extends BaseEntity{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for BIGSERIAL
    @Column(name = "size_id", updatable = false, nullable = false)
    private Integer sizeId;
    
    @Column(name = "size")
    private String size;
    
    @Column(name = "description")
    private String description;
    
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
    
    @Column(name = "category_type")
    private Integer categoryType;
    
    @Column(name = "erp_ref_code")
    private String erpRefCode;

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	public String getErpRefCode() {
		return erpRefCode;
	}

	public void setErpRefCode(String erpRefCode) {
		this.erpRefCode = erpRefCode;
	}
    
    
}
