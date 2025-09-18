package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wf_master_service_tasks")
public class WfMasterServiceTasks {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "wf_master_service_task_id")
	private String wfMasterServiceTaskId;
	
	@Column(name = "id")
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "service_no")
	private String serviceNo;
	
	@Column(name = "service_class")
	private String serviceClass;
	
	@Column(name = "method")
	private String method;
	
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
	
	@ManyToOne
	@JoinColumn(name = "wf_id")
	private WfMaster wfMaster;
	
	@ManyToOne
	@JoinColumn(name = "wf_process_id")
	private WfProcess wfProcess;

	public String getWfMasterServiceTaskId() {
		return wfMasterServiceTaskId;
	}

	public void setWfMasterServiceTaskId(String wfMasterServiceTaskId) {
		this.wfMasterServiceTaskId = wfMasterServiceTaskId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
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

	public WfMaster getWfMaster() {
		return wfMaster;
	}

	public void setWfMaster(WfMaster wfMaster) {
		this.wfMaster = wfMaster;
	}

	public WfProcess getWfProcess() {
		return wfProcess;
	}

	public void setWfProcess(WfProcess wfProcess) {
		this.wfProcess = wfProcess;
	}
	
	
	
}
