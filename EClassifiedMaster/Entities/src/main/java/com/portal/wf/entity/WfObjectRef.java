package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_object_ref")
public class WfObjectRef extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "object_ref_id")
	private String objectRefId;
	
	@Column(name = "wf_id")
	private String wfId;
	
	@Column(name = "object_status_field")
	private String objectStatusField;
	
	@Column(name = "object_key_field")
	private String objectKeyField;
	
	@Column(name = "object_collection")
	private String objectCollection;
	
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

	public String getObjectRefId() {
		return objectRefId;
	}

	public void setObjectRefId(String objectRefId) {
		this.objectRefId = objectRefId;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getObjectStatusField() {
		return objectStatusField;
	}

	public void setObjectStatusField(String objectStatusField) {
		this.objectStatusField = objectStatusField;
	}

	public String getObjectKeyField() {
		return objectKeyField;
	}

	public void setObjectKeyField(String objectKeyField) {
		this.objectKeyField = objectKeyField;
	}

	public String getObjectCollection() {
		return objectCollection;
	}

	public void setObjectCollection(String objectCollection) {
		this.objectCollection = objectCollection;
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
