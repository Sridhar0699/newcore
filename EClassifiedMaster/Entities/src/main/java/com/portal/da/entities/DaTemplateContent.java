package com.portal.da.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "da_template_content")
public class DaTemplateContent extends BaseEntity{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "template_cnt_id")
    private String templateCntIid;
    
    @Column(name = "template_id")
    private String templateId;
    
    @Column(name = "text_index")
    private String textIndex;
    
    @Column(name = "content")
    private String content;
    
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
    
    @Column(name = "x_coordinate")
    private Integer xCoordinate;
    
    @Column(name = "y_coordinate")
    private Integer yCoordinate;
    
    @Column(name = "width")
    private Integer width;
    
    @Column(name = "font")
    private Integer font;
    
    @Column(name = "height")
    private Integer height;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "image_type")
    private String imageType;

	public String getTemplateCntIid() {
		return templateCntIid;
	}

	public void setTemplateCntIid(String templateCntIid) {
		this.templateCntIid = templateCntIid;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTextIndex() {
		return textIndex;
	}

	public void setTextIndex(String textIndex) {
		this.textIndex = textIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public Integer getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getFont() {
		return font;
	}

	public void setFont(Integer font) {
		this.font = font;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
    
    
}
