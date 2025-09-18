package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name= "gd_classified_schemes")
public class GdClassifiedSchemes extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "scheme")
	private String scheme;
	
	@Column(name = "erp_scheme")
	private String erpScheme;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "no_days")
	private Integer noDays;
	
	@Column(name = "billable_days")
	private Integer billableDays;
	
	@Column(name = "allowed_days")
	private Integer allowedDays;
	
	@Column(name = "erp_ref_id")
	private String erpRefId;
	
	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;
	
	@Column(name = "edition_type")
	private Integer editionType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getErpScheme() {
		return erpScheme;
	}

	public void setErpScheme(String erpScheme) {
		this.erpScheme = erpScheme;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNoDays() {
		return noDays;
	}

	public void setNoDays(Integer noDays) {
		this.noDays = noDays;
	}

	public Integer getBillableDays() {
		return billableDays;
	}

	public void setBillableDays(Integer billableDays) {
		this.billableDays = billableDays;
	}

	public Integer getAllowedDays() {
		return allowedDays;
	}

	public void setAllowedDays(Integer allowedDays) {
		this.allowedDays = allowedDays;
	}

	public String getErpRefId() {
		return erpRefId;
	}

	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
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

	public Integer getEditionType() {
		return editionType;
	}

	public void setEditionType(Integer editionType) {
		this.editionType = editionType;
	}
	
	

}
