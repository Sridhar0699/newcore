package com.portal.gd.models;

import java.math.BigInteger;

public class RmsApprovalMatrixModel {

	private BigInteger id;
	private Integer bookingOffice;
	private String bookingOfficeStr;
	private Double rangeFrom;
	private Double rangeTo;
	private Integer approverUserId;
	private String approvalUserEmail;
	private Integer approvaerCCUserId;
	private String approvalCCUserEmail;
	private Integer level;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public Integer getBookingOffice() {
		return bookingOffice;
	}
	public void setBookingOffice(Integer bookingOffice) {
		this.bookingOffice = bookingOffice;
	}
	public String getBookingOfficeStr() {
		return bookingOfficeStr;
	}
	public void setBookingOfficeStr(String bookingOfficeStr) {
		this.bookingOfficeStr = bookingOfficeStr;
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
	public Integer getApproverUserId() {
		return approverUserId;
	}
	public void setApproverUserId(Integer approverUserId) {
		this.approverUserId = approverUserId;
	}
	public String getApprovalUserEmail() {
		return approvalUserEmail;
	}
	public void setApprovalUserEmail(String approvalUserEmail) {
		this.approvalUserEmail = approvalUserEmail;
	}
	public Integer getApprovaerCCUserId() {
		return approvaerCCUserId;
	}
	public void setApprovaerCCUserId(Integer approvaerCCUserId) {
		this.approvaerCCUserId = approvaerCCUserId;
	}
	public String getApprovalCCUserEmail() {
		return approvalCCUserEmail;
	}
	public void setApprovalCCUserEmail(String approvalCCUserEmail) {
		this.approvalCCUserEmail = approvalCCUserEmail;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
