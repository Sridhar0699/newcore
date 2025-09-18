package com.portal.da.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "da_templates")
public class DaTemplates extends BaseEntity{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "template_id")
    private String templateId;
    
    @Column(name = "size_id")
    private Integer sizeId;
    
    @Column(name = "height")
    private Integer height;
    
    @Column(name = "width")
    private Integer width;
    
    @Column(name = "template_image_id")
    private String templateImageId;
    
    @Column(name = "status")
    private boolean status;
    
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
    
    @Column(name = "template_image_url")
    private String templateImageUrl;
    
    @Column(name = "lang")
    private Integer lang;
    
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getTemplateImageId() {
		return templateImageId;
	}

	public void setTemplateImageId(String templateImageId) {
		this.templateImageId = templateImageId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
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

	public String getTemplateImageUrl() {
		return templateImageUrl;
	}

	public void setTemplateImageUrl(String templateImageUrl) {
		this.templateImageUrl = templateImageUrl;
	}

	public Integer getLang() {
		return lang;
	}

	public void setLang(Integer lang) {
		this.lang = lang;
	}
    
    

}
