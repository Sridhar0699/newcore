package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "rms_approval_matrix")
public class RmsApprovalMatrix extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "booking_office")
	private Integer bookingOffice;
	
	@Column(name = "range_from")
	private Double rangeFrom;
	
	@Column(name = "range_to")
	private Double rangeTo;
	
	@Column(name = "approver_email_id")
	private String approverEmailId;
	
	@Column(name = "approver_cc_mail_id")
	private String approverCcMailId;
	
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
	
	@Column(name = "approver_user_id")
	private Integer approverUserId;
	
	@Column(name = "approver_cc_user_id")
	private Integer approverCcUserId;
	
	@Column(name = "level")
	private Integer level;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookingOffice() {
		return bookingOffice;
	}

	public void setBookingOffice(Integer bookingOffice) {
		this.bookingOffice = bookingOffice;
	}

	public Double getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(Double rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public Double getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(Double rangeTo) {
		this.rangeTo = rangeTo;
	}

	public String getApproverEmailId() {
		return approverEmailId;
	}

	public void setApproverEmailId(String approverEmailId) {
		this.approverEmailId = approverEmailId;
	}

	public String getApproverCcMailId() {
		return approverCcMailId;
	}

	public void setApproverCcMailId(String approverCcMailId) {
		this.approverCcMailId = approverCcMailId;
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

	public Integer getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(Integer approverUserId) {
		this.approverUserId = approverUserId;
	}

	public Integer getApproverCcUserId() {
		return approverCcUserId;
	}

	public void setApproverCcUserId(Integer approverCcUserId) {
		this.approverCcUserId = approverCcUserId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	
	
	
}
