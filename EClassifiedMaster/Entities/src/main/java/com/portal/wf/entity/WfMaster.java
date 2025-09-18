package com.portal.wf.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_master")
public class WfMaster extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "wf_id")
	private String wfId;
	
	@Column(name = "wf_title")
	private String wfTitle;
	
	@Column(name = "wf_name")
	private String wfName;
	
	@Column(name = "wf_type_id")
	private String wfTypeId;
	
	@Column(name = "wf_type")
	private String wfType;
	
	@Column(name = "wf_short_id")
	private String wfShortId;
	
	@Column(name = "status")
	private String status;
	
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
	
	@Column(name = "initiator_flag")
	private Boolean initiatorFlag;
	
	@Column(name = "object_status_field")
	private String objectStatusField;
	
	@Column(name = "object_key_field")
	private String objectKeyField;
	
	@Column(name = "object_collection")
	private String objectCollection;
	
	@Column(name = "process_meta_data")
	private String processMetaData;
	
	@Column(name = "isDefault")
	private Boolean isDefault;
	
	@OneToMany(mappedBy = "wfMaster", fetch = FetchType.LAZY)
	private Set<WfProcess> wfProcess;
	
	@OneToMany(mappedBy = "wfMaster", fetch = FetchType.LAZY)
	private Set<WfUserTasks> wfUserTasks;
	
	@OneToMany(mappedBy = "wfMaster", fetch = FetchType.LAZY)
	private Set<WfSequenceFlow> wfSequenceFlow;
	
	@OneToMany(mappedBy = "wfMaster", fetch = FetchType.LAZY)
	private Set<WfMasterServiceTasks> wfMasterServiceTasks;

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	public String getWfTypeId() {
		return wfTypeId;
	}

	public void setWfTypeId(String wfTypeId) {
		this.wfTypeId = wfTypeId;
	}

	public String getWfType() {
		return wfType;
	}

	public void setWfType(String wfType) {
		this.wfType = wfType;
	}

	public String getWfShortId() {
		return wfShortId;
	}

	public void setWfShortId(String wfShortId) {
		this.wfShortId = wfShortId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Boolean getInitiatorFlag() {
		return initiatorFlag;
	}

	public void setInitiatorFlag(Boolean initiatorFlag) {
		this.initiatorFlag = initiatorFlag;
	}

	public String getObjectStatusField() {
		return objectStatusField;
	}

	public void setObjectStatusField(String objectStatusField) {
		this.objectStatusField = objectStatusField;
	}

	public String getObjectKeyField() {
		return objectKeyField;
	}

	public void setObjectKeyField(String objectKeyField) {
		this.objectKeyField = objectKeyField;
	}

	public String getObjectCollection() {
		return objectCollection;
	}

	public void setObjectCollection(String objectCollection) {
		this.objectCollection = objectCollection;
	}

	public Set<WfProcess> getWfProcess() {
		return wfProcess;
	}

	public void setWfProcess(Set<WfProcess> wfProcess) {
		this.wfProcess = wfProcess;
	}

	public Set<WfUserTasks> getWfUserTasks() {
		return wfUserTasks;
	}

	public void setWfUserTasks(Set<WfUserTasks> wfUserTasks) {
		this.wfUserTasks = wfUserTasks;
	}

	public Set<WfSequenceFlow> getWfSequenceFlow() {
		return wfSequenceFlow;
	}

	public void setWfSequenceFlow(Set<WfSequenceFlow> wfSequenceFlow) {
		this.wfSequenceFlow = wfSequenceFlow;
	}

	public Set<WfMasterServiceTasks> getWfMasterServiceTasks() {
		return wfMasterServiceTasks;
	}

	public void setWfMasterServiceTasks(Set<WfMasterServiceTasks> wfMasterServiceTasks) {
		this.wfMasterServiceTasks = wfMasterServiceTasks;
	}

	public String getProcessMetaData() {
		return processMetaData;
	}

	public void setProcessMetaData(String processMetaData) {
		this.processMetaData = processMetaData;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	
	
}
