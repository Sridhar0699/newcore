package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_rms_multiple_discount_val")
public class GdRmsMultipleDiscountVal extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name = "edition_id")
	private Integer editionId;
	
	@Column(name = "erp_edition_code")
	private String erpEditionCode;
	
	@Column(name = "erp_position_inst_code")
	private String erpPositionInstCode;
	
	@Column(name = "position_inst_id")
	private Integer positionInstId;
	
	@Column(name = "erp_scheme_code")
	private String erpSchemeCode;
	
	@Column(name = "scheme_id")
	private Integer schemeId;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEditionId() {
		return editionId;
	}

	public void setEditionId(Integer editionId) {
		this.editionId = editionId;
	}

	public String getErpEditionCode() {
		return erpEditionCode;
	}

	public void setErpEditionCode(String erpEditionCode) {
		this.erpEditionCode = erpEditionCode;
	}

	public String getErpPositionInstCode() {
		return erpPositionInstCode;
	}

	public void setErpPositionInstCode(String erpPositionInstCode) {
		this.erpPositionInstCode = erpPositionInstCode;
	}

	public Integer getPositionInstId() {
		return positionInstId;
	}

	public void setPositionInstId(Integer positionInstId) {
		this.positionInstId = positionInstId;
	}

	public String getErpSchemeCode() {
		return erpSchemeCode;
	}

	public void setErpSchemeCode(String erpSchemeCode) {
		this.erpSchemeCode = erpSchemeCode;
	}

	

	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
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
