package com.portal.wf.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "wf_process")
public class WfProcess extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "wf_process_id")
	private String wfProcessId;
	
	@Column(name = "start_event_id")
	private String startEventId;
	
	@Column(name = "exclusive_gateway_id")
	private String exclusiveGatewayId;
	
	@Column(name = "approve_end_id")
	private String approveEndId;
	
	@Column(name = "reject_end_id")
	private String rejectEndId;
	
	@Column(name = "revise_end_id")
	private String reviseEndId;
	
	@Column(name = "next_level_mail")
	private String nextLevelMail;
	
	@Column(name = "current_level_mail")
	private String currentLevelMail;
	
	@Column(name = "is_executable")
	private Boolean isExecutable;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_ts")
	private Date createdTs;
	
	@Column(name = "changed_by")
	private Integer changedBy;
	
	@Column(name = "changed_ts")
	private Date changedTs;
	
	@Column(name="mark_as_delete")
	private Boolean markAsDelete;
	
	@ManyToOne
	@JoinColumn(name = "wf_id")
	private WfMaster wfMaster;
	
	@OneToMany(mappedBy = "wfProcess", fetch = FetchType.LAZY)
	private Set<WfUserTasks> wfUserTasks;
	
	@OneToMany(mappedBy = "wfProcess", fetch = FetchType.LAZY)
	private Set<WfSequenceFlow> wfSequenceFlow;
	
	@OneToMany(mappedBy = "wfProcess", fetch = FetchType.LAZY)
	private Set<WfMasterServiceTasks> wfMasterServiceTasks;

	public String getWfProcessId() {
		return wfProcessId;
	}

	public void setWfProcessId(String wfProcessId) {
		this.wfProcessId = wfProcessId;
	}

	public String getStartEventId() {
		return startEventId;
	}

	public void setStartEventId(String startEventId) {
		this.startEventId = startEventId;
	}

	public String getExclusiveGatewayId() {
		return exclusiveGatewayId;
	}

	public void setExclusiveGatewayId(String exclusiveGatewayId) {
		this.exclusiveGatewayId = exclusiveGatewayId;
	}

	public String getApproveEndId() {
		return approveEndId;
	}

	public void setApproveEndId(String approveEndId) {
		this.approveEndId = approveEndId;
	}

	public String getRejectEndId() {
		return rejectEndId;
	}

	public void setRejectEndId(String rejectEndId) {
		this.rejectEndId = rejectEndId;
	}

	public String getReviseEndId() {
		return reviseEndId;
	}

	public void setReviseEndId(String reviseEndId) {
		this.reviseEndId = reviseEndId;
	}

	public String getNextLevelMail() {
		return nextLevelMail;
	}

	public void setNextLevelMail(String nextLevelMail) {
		this.nextLevelMail = nextLevelMail;
	}

	public String getCurrentLevelMail() {
		return currentLevelMail;
	}

	public void setCurrentLevelMail(String currentLevelMail) {
		this.currentLevelMail = currentLevelMail;
	}

	public Boolean getIsExecutable() {
		return isExecutable;
	}

	public void setIsExecutable(Boolean isExecutable) {
		this.isExecutable = isExecutable;
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

	public WfMaster getWfMaster() {
		return wfMaster;
	}

	public void setWfMaster(WfMaster wfMaster) {
		this.wfMaster = wfMaster;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
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
	
	
}
