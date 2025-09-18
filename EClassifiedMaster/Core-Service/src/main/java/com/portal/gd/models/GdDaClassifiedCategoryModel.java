package com.portal.gd.models;

import java.util.Date;

public class GdDaClassifiedCategoryModel {

	private Integer id;
	private String categoryType;
	private String description;
	private String imageUrl;
	private String imageId;
	private Integer createdBy;
	private Date createdTs;
	private Integer changedBy;
	private Date changedTs;
	private Boolean markAsDelete;
	private String action;
	private String attachmentName;
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
		
	
	
}
