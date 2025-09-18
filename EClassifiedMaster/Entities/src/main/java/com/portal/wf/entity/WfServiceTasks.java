package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_service_tasks")
public class WfServiceTasks extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "wf_type_id")
	private String wfTypeId;
	@Column(name = "service_id")
	private String serviceId;
	@Column(name = "service_short_id")
	private String serviceShortId;
	@Column(name = "service_desc")
	private String serviceDesc;
	@Column(name = "service_class")
	private String serviceClass;
	@Column(name = "method")
	private String method;
	@Column(name = "service_type")
	private String serviceType;
	@Column(name="allowed_actions")
	private String allowedActions;
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
	
	
	public String getWfTypeId() {
		return wfTypeId;
	}
	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceShortId() {
		return serviceShortId;
	}
	public void setServiceShortId(String serviceShortId) {
		this.serviceShortId = serviceShortId;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public String getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
	public String getAllowedActions() {
		return allowedActions;
	}
	public void setAllowedActions(String allowedActions) {
		this.allowedActions = allowedActions;
	}
	
	
}
