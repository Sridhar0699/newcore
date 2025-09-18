package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_master_doc_types")
public class WfMasterDocTypes extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "wf_master_doc_id")
	private String wfMasterDocId;
	
	@Column(name = "wf_id")
	private String wfId;
	
	@Column(name = "wf_title")
	private String wfTitle;
	
	@Column(name = "wf_name")
	private String wfName;
	
	@Column(name = "wf_type_id")
	private String wfTypeId;
	
	@Column(name = "wf_type")
	private String wfType;
	
	@Column(name = "is_default")
	private Boolean isDefault;
	
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

	public String getWfMasterDocId() {
		return wfMasterDocId;
	}

	public void setWfMasterDocId(String wfMasterDocId) {
		this.wfMasterDocId = wfMasterDocId;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	public String getWfTypeId() {
		return wfTypeId;
	}

	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}

	public String getWfType() {
		return wfType;
	}

	public void setWfType(String wfType) {
		this.wfType = wfType;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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
