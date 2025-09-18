package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_classified_sub_group")
public class GdClassifiedSubGroup extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "classified_sub_group")
	private String classifiedSubGroup;
	
	@Column(name = "erp_classified_sub_group")
	private String erpClassifiedSubGroup;
	
	@Column(name = "erp_ref_id")
	private String erpRefId;
	
	@Column(name = "group_id")
	private Integer groupId;
	
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

	public String getClassifiedSubGroup() {
		return classifiedSubGroup;
	}

	public void setClassifiedSubGroup(String classifiedSubGroup) {
		this.classifiedSubGroup = classifiedSubGroup;
	}

	public String getErpClassifiedSubGroup() {
		return erpClassifiedSubGroup;
	}

	public void setErpClassifiedSubGroup(String erpClassifiedSubGroup) {
		this.erpClassifiedSubGroup = erpClassifiedSubGroup;
	}

	public String getErpRefId() {
		return erpRefId;
	}

	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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
