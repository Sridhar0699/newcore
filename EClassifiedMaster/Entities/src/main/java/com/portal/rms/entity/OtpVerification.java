package com.portal.rms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "otp_verification")
public class OtpVerification extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	@Column(name = "otp_validity_time")
	private Date otpValidityTime;
	
	@Column(name = "otp_num")
	private String otpNum;
	
	@Column(name = "otp_status")
	private String otpStatus;
	
	@Column(name = "otp_attempts")
	private Integer otpAttempts;
	
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
	
	@Column(name = "order_id")
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getOtpValidityTime() {
		return otpValidityTime;
	}

	public void setOtpValidityTime(Date otpValidityTime) {
		this.otpValidityTime = otpValidityTime;
	}

	public String getOtpNum() {
		return otpNum;
	}

	public void setOtpNum(String otpNum) {
		this.otpNum = otpNum;
	}

	public String getOtpStatus() {
		return otpStatus;
	}

	public void setOtpStatus(String otpStatus) {
		this.otpStatus = otpStatus;
	}

	public Integer getOtpAttempts() {
		return otpAttempts;
	}

	public void setOtpAttempts(Integer otpAttempts) {
		this.otpAttempts = otpAttempts;
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
