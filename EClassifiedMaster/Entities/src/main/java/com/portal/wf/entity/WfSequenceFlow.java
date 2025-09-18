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
@Table(name = "wf_sequence_flow")
public class WfSequenceFlow extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "wf_sequence_flow_id")
	private String wfSequenceFlowId;
	
	@Column(name = "source_ref")
	private String sourceRef;
	
	@Column(name = "target_ref")
	private String targetRef;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "text")
	private String text;
	
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

	public String getWfSequenceFlowId() {
		return wfSequenceFlowId;
	}

	public void setWfSequenceFlowId(String wfSequenceFlowId) {
		this.wfSequenceFlowId = wfSequenceFlowId;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
