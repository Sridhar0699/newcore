package com.portal.gd.models;

import java.util.Date;

public class GdDaSizesModel {

	private Integer sizeId;
	private String size;
	private String description;
	private String erpRefcode;
	private String categoryType;
	private Integer categoryTypeId;
	private Integer createdBy;
	private Date createdTs;
	private Integer changedBy;
	private Date changedTs;
	private Boolean markAsDelete;
	private String action;
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
	public String getErpRefcode() {
		return erpRefcode;
	}
	public void setErpRefcode(String erpRefcode) {
		this.erpRefcode = erpRefcode;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public Integer getCategoryTypeId() {
		return categoryTypeId;
	}
	public void setCategoryTypeId(Integer categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
}
