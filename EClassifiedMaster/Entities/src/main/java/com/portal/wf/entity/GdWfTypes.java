package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_wf_types")
public class GdWfTypes extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "wf_type_id")
	private String wfTypeId;
	
	@Column(name = "wf_type")
	private String wfType;
	
	@Column(name = "wf_type_desc")
	private String wfTypeDesc;
	
	@Column(name = "target_collection")
	private String targetCollection;
	
	@Column(name = "key_field_name")
	private String keyFieldName;
	
	@Column(name = "status_field")
	private String statusField;
	
	@Column(name = "mark_as_delete")
	private boolean markAsDelete;
	
	@Column(name = "is_wf_creation")
	private boolean isWfCreation;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_ts")
	private Date createdTs;
	
	@Column(name = "changed_by")
	private Integer changedBy;
	
	@Column(name = "changed_ts")
	private Date changedTs;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getWfTypeDesc() {
		return wfTypeDesc;
	}

	public void setWfTypeDesc(String wfTypeDesc) {
		this.wfTypeDesc = wfTypeDesc;
	}

	public String getTargetCollection() {
		return targetCollection;
	}

	public void setTargetCollection(String targetCollection) {
		this.targetCollection = targetCollection;
	}

	public String getKeyFieldName() {
		return keyFieldName;
	}

	public void setKeyFieldName(String keyFieldName) {
		this.keyFieldName = keyFieldName;
	}

	public String getStatusField() {
		return statusField;
	}

	public void setStatusField(String statusField) {
		this.statusField = statusField;
	}

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public boolean isWfCreation() {
		return isWfCreation;
	}

	public void setWfCreation(boolean isWfCreation) {
		this.isWfCreation = isWfCreation;
	}
	
	
}
