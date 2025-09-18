package com.portal.gd.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.apilog.entities.PortalApiLog;
import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_api_modules")
public class GdApiModules extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "api_module_id")
	private Integer apiModuleId;

	@Column(name = "short_desc")
	private String shortDesc;

	@Column(name = "module_desc")
	private String moduleDesc;

	@Column(name = "is_api_logging")
	private Boolean isApiLogging;

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

	@OneToMany(mappedBy = "gdApiModules", fetch = FetchType.LAZY)
	private Set<PortalApiLog> ssPortalApiLog;

	/**
	 * @return the apiModuleId
	 */
	public Integer getApiModuleId() {
		return apiModuleId;
	}

	/**
	 * @param apiModuleId
	 *            the apiModuleId to set
	 */
	public void setApiModuleId(Integer apiModuleId) {
		this.apiModuleId = apiModuleId;
	}

	/**
	 * @return the shortDesc
	 */
	public String getShortDesc() {
		return shortDesc;
	}

	/**
	 * @param shortDesc
	 *            the shortDesc to set
	 */
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	/**
	 * @return the moduleDesc
	 */
	public String getModuleDesc() {
		return moduleDesc;
	}

	/**
	 * @param moduleDesc
	 *            the moduleDesc to set
	 */
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdTs
	 */
	public Date getCreatedTs() {
		return createdTs;
	}

	/**
	 * @param createdTs
	 *            the createdTs to set
	 */
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	/**
	 * @return the changedBy
	 */
	public Integer getChangedBy() {
		return changedBy;
	}

	/**
	 * @param changedBy
	 *            the changedBy to set
	 */
	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	/**
	 * @return the changedTs
	 */
	public Date getChangedTs() {
		return changedTs;
	}

	/**
	 * @param changedTs
	 *            the changedTs to set
	 */
	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	/**
	 * @return the markAsDelete
	 */
	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @param markAsDelete
	 *            the markAsDelete to set
	 */
	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	/**
	 * @return the ssPortalApiLog
	 */
	public Set<PortalApiLog> getSsPortalApiLog() {
		return ssPortalApiLog;
	}

	/**
	 * @param ssPortalApiLog
	 *            the ssPortalApiLog to set
	 */
	public void setSsPortalApiLog(Set<PortalApiLog> ssPortalApiLog) {
		this.ssPortalApiLog = ssPortalApiLog;
	}

	/**
	 * @return the markAsDelete
	 */
	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @param markAsDelete
	 *            the markAsDelete to set
	 */
	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	/**
	 * @return the isApiLogging
	 */
	public Boolean isApiLogging() {
		return isApiLogging;
	}

	/**
	 * @param isApiLogging
	 *            the isApiLogging to set
	 */
	public void setIsApiLogging(Boolean isApiLogging) {
		this.isApiLogging = isApiLogging;
	}
}
