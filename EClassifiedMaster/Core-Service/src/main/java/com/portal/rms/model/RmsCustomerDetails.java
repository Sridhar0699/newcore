package com.portal.rms.model;

import java.util.Date;

public class RmsCustomerDetails {
	
	
	private String customerId;
	private String customerName;
	private String mobileNo;
	private String emailId;
	private String address1;
	private String address2;
	private String address3;
	private String pinCode;
	private String officeLocation;
	private String gstNo;
	private String panNumber;
	private String aadharNumber;
	private String erpRefId;
	private Integer userId;
	private Integer createdBy;
	private Date createdTs;	
	private Integer changedBy;
	private Date changedTs;
	private boolean markAsDelete;
	private String state;
	private String city;
	private String houseNo;
	private String attatchId;
	private String clientCode;
	private boolean eKycRequired;
	private String cityDesc;
	private String stateDesc;
	private String mobileAlt;
	
	
	
	public boolean iseKycRequired() {
		return eKycRequired;
	}
	public void seteKycRequired(boolean eKycRequired) {
		this.eKycRequired = eKycRequired;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getOfficeLocation() {
		return officeLocation;
	}
	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getErpRefId() {
		return erpRefId;
	}
	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public boolean isMarkAsDelete() {
		return markAsDelete;
	}
	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}
	public String getAttatchId() {
		return attatchId;
	}
	public void setAttatchId(String attatchId) {
		this.attatchId = attatchId;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getCityDesc() {
		return cityDesc;
	}
	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public String getMobileAlt() {
		return mobileAlt;
	}
	public void setMobileAlt(String mobileAlt) {
		this.mobileAlt = mobileAlt;
	}
	
	

}
