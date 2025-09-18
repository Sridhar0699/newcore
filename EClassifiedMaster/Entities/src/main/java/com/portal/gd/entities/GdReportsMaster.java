package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_reports_master")
public class GdReportsMaster extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RPT_ID")
	private String rptId;

	@Column(name = "RPT_NAME")
	private String rptName;

	@Column(name = "RPT_DESC")
	private String rptDesc;

	@Column(name = "RPT_SHORT_ID")
	private String rptShortId;

	@Column(name = "RPT_GROUP")
	private String rptGroup;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "MARK_AS_DELETE")
	private Boolean markAsDelete;

	@Column(name = "ACCESS_OBJ_ID")
	private String accessObjId;
	
	@Column(name = "RPT_STATUS")
	private String rptStatus;
	
	@Column(name = "RPT_URL")
	private String rptUrl;
	
	@Column(name = "RPT_GEN_DATE")
	private Date rptGenDate;

	public String getRptId() {
		return rptId;
	}

	public void setRptId(String rptId) {
		this.rptId = rptId;
	}

	public String getRptName() {
		return rptName;
	}

	public void setRptName(String rptName) {
		this.rptName = rptName;
	}

	public String getRptDesc() {
		return rptDesc;
	}

	public void setRptDesc(String rptDesc) {
		this.rptDesc = rptDesc;
	}

	public String getRptShortId() {
		return rptShortId;
	}

	public void setRptShortId(String rptShortId) {
		this.rptShortId = rptShortId;
	}

	public String getRptGroup() {
		return rptGroup;
	}

	public void setRptGroup(String rptGroup) {
		this.rptGroup = rptGroup;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getAccessObjId() {
		return accessObjId;
	}

	public void setAccessObjId(String accessObjId) {
		this.accessObjId = accessObjId;
	}

	public String getRptStatus() {
		return rptStatus;
	}

	public void setRptStatus(String rptStatus) {
		this.rptStatus = rptStatus;
	}

	public String getRptUrl() {
		return rptUrl;
	}

	public void setRptUrl(String rptUrl) {
		this.rptUrl = rptUrl;
	}

	public Date getRptGenDate() {
		return rptGenDate;
	}

	public void setRptGenDate(Date rptGenDate) {
		this.rptGenDate = rptGenDate;
	}

}
