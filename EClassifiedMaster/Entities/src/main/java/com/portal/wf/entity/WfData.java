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
@Table(name = "wf_data")
public class WfData extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "wf_data_id")
	private String wfDataId;
	
	@ManyToOne
	@JoinColumn(name = "wf_ref_id")
	private WfRefrenceData wfRefrenceData;
	
	@Column(name = "stage_id")
	private String stageId;
	
	@Column(name = "stage_desc")
	private String stageDesc;
	
	@Column(name = "approvers")
	private String approvers;
	
	@Column(name = "approval_levels")
	private String approvalLevels;
	
	@Column(name = "service_task_ids")
	private String serviceTaskIds;
	
	@Column(name = "on_reject_stage_id")
	private String onRejectStageId;
	
	@Column(name = "previous_email_template_id")
	private String previousEmailTemplateId;
	
	@Column(name = "next_email_template_id")
	private String nextEmailTemplateId;
	
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

	public String getWfDataId() {
		return wfDataId;
	}

	public void setWfDataId(String wfDataId) {
		this.wfDataId = wfDataId;
	}

	public WfRefrenceData getWfRefrenceData() {
		return wfRefrenceData;
	}

	public void setWfRefrenceData(WfRefrenceData wfRefrenceData) {
		this.wfRefrenceData = wfRefrenceData;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getStageDesc() {
		return stageDesc;
	}

	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}

	public String getApprovers() {
		return approvers;
	}

	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}

	public String getApprovalLevels() {
		return approvalLevels;
	}

	public void setApprovalLevels(String approvalLevels) {
		this.approvalLevels = approvalLevels;
	}

	public String getServiceTaskIds() {
		return serviceTaskIds;
	}

	public void setServiceTaskIds(String serviceTaskIds) {
		this.serviceTaskIds = serviceTaskIds;
	}

	public String getOnRejectStageId() {
		return onRejectStageId;
	}

	public void setOnRejectStageId(String onRejectStageId) {
		this.onRejectStageId = onRejectStageId;
	}

	public String getPreviousEmailTemplateId() {
		return previousEmailTemplateId;
	}

	public void setPreviousEmailTemplateId(String previousEmailTemplateId) {
		this.previousEmailTemplateId = previousEmailTemplateId;
	}

	public String getNextEmailTemplateId() {
		return nextEmailTemplateId;
	}

	public void setNextEmailTemplateId(String nextEmailTemplateId) {
		this.nextEmailTemplateId = nextEmailTemplateId;
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
	
	
}
