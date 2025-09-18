package com.portal.wf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_user_tasks")
public class WfUserTasks extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "wf_user_task_id")
	private String wfUserTaskId;
	
	@Column(name = "id")
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "approval_type")
	private String approvalType;
	
	@Column(name = "approval_levels")
	private String approvalLevels;
	
	@Column(name = "candidate_groups")
	private String candidateGroups;
	
	@Column(name = "description")
	private String description;
	
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

	public String getWfUserTaskId() {
		return wfUserTaskId;
	}

	public void setWfUserTaskId(String wfUserTaskId) {
		this.wfUserTaskId = wfUserTaskId;
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

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getApprovalLevels() {
		return approvalLevels;
	}

	public void setApprovalLevels(String approvalLevels) {
		this.approvalLevels = approvalLevels;
	}

	public String getCandidateGroups() {
		return candidateGroups;
	}

	public void setCandidateGroups(String candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
