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
@Table(name = "gd_rms_editions")
public class GdRmsEditions extends BaseEntity{

	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for BIGSERIAL
	 @Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name = "edition_name")
	private String editionName;
	
	@Column(name = "erp_edition")
	private String erpEdition;
	
	@Column(name = "erp_ref_id")
	private String erpRefId;
	
	@Column(name = "edition_type")
	private Integer editionType;
	
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
	
	@Column(name = "ffp_flag")
	private Boolean ffpFlag;
	
	@Column(name = "add_type")
	private Integer addType;
	
	@Column(name = "state_code")
	private String stateCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEditionName() {
		return editionName;
	}

	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}

	public String getErpEdition() {
		return erpEdition;
	}

	public void setErpEdition(String erpEdition) {
		this.erpEdition = erpEdition;
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

	public Boolean getFfpFlag() {
		return ffpFlag;
	}

	public void setFfpFlag(Boolean ffpFlag) {
		this.ffpFlag = ffpFlag;
	}

	public Integer getAddType() {
		return addType;
	}

	public void setAddType(Integer addType) {
		this.addType = addType;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	
}
