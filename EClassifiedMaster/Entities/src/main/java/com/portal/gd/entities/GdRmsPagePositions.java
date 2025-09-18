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
@Table(name = "gd_rms_page_positions")
public class GdRmsPagePositions extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for BIGSERIAL
    @Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name = "page_name")
	private String pageName;
	
	@Column(name = "page_description")
	private String pageDescription;
	
	@Column(name = "erp_ref_code")
	private String erpRefCode;
	
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
	
	@Column(name = "percentage")
	private Integer percentage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageDescription() {
		return pageDescription;
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	public String getErpRefCode() {
		return erpRefCode;
	}

	public void setErpRefCode(String erpRefCode) {
		this.erpRefCode = erpRefCode;
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

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	
	
	
}
