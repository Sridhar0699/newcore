package com.portal.wf.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_refrence_data")
public class WfRefrenceData extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "wf_ref_id")
	private String wfRefId;
	
	@Column(name = "wf_type_id")
	private String wfTypeId;
	
	@Column(name = "wf_name")
	private String wfName;
	
	@Column(name = "wf_title")
	private String wfTitle;
	
	@Column(name = "wf_id")
	private String wfId;
	
	@Column(name = "status")
	private String status;
	
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
	
	@Column(name="wf_ref_meta_data")
	private String wfRefMetaData;
	
	@OneToMany(mappedBy = "wfRefrenceData", fetch = FetchType.LAZY)
	private Set<WfData> wfData;

	public String getWfRefId() {
		return wfRefId;
	}

	public void setWfRefId(String wfRefId) {
		this.wfRefId = wfRefId;
	}

	public String getWfTypeId() {
		return wfTypeId;
	}

	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Set<WfData> getWfData() {
		return wfData;
	}

	public void setWfData(Set<WfData> wfData) {
		this.wfData = wfData;
	}

	public String getWfRefMetaData() {
		return wfRefMetaData;
	}

	public void setWfRefMetaData(String wfRefMetaData) {
		this.wfRefMetaData = wfRefMetaData;
	}
	
	

}
