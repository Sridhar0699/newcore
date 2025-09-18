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
@Table(name = "gd_rms_positioning_discount")
public class GdRmsPositioningDiscount extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for BIGSERIAL
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name = "positioning_type")
	private String positioningType;
	
	@Column(name = "positioning_desc")
	private String positioningDesc;
	
	@Column(name = "discount")
	private Integer discount;
	
	@Column(name = "erp_ref_id")
	private String erpRefId;
	
	@Column(name = "edition_type")
	private Integer editionType;
	
	@Column(name = "type_of_position")
	private String typeOfPosition;
	
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

	public String getPositioningType() {
		return positioningType;
	}

	public void setPositioningType(String positioningType) {
		this.positioningType = positioningType;
	}

	public String getPositioningDesc() {
		return positioningDesc;
	}

	public void setPositioningDesc(String positioningDesc) {
		this.positioningDesc = positioningDesc;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getErpRefId() {
		return erpRefId;
	}

	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}

	public Integer getEditionType() {
		return editionType;
	}

	public void setEditionType(Integer editionType) {
		this.editionType = editionType;
	}

	public String getTypeOfPosition() {
		return typeOfPosition;
	}

	public void setTypeOfPosition(String typeOfPosition) {
		this.typeOfPosition = typeOfPosition;
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
