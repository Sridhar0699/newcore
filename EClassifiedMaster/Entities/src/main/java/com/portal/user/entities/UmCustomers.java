package com.portal.user.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "um_customers")
public class UmCustomers extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "address_1")
	private String address1;

	@Column(name = "address_2")
	private String address2;

	@Column(name = "address_3")
	private String address3;

	@Column(name = "pin_code")
	private String pinCode;

	@Column(name = "office_location")
	private String officeLocation;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "pan_number")
	private String panNumber;

	@Column(name = "aadhar_number")
	private String aadharNumber;

	@Column(name = "erp_ref_id")
	private String erpRefId;

	@Column(name = "user_id")
	private Integer userId;

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
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "house_no")
	private String houseNo;
	
	@Column(name = "attatch_id")
    private String attatchId;
	
	@Column(name = "client_code")
    private String clientCode;
	
	@Column(name = "kyc_required")
	private Boolean kycRequired;

	@Column(name="customer_type_id")
	private Integer customerTypeId;
	
	@Column(name = "emp_id")
	private Integer empId;
	
	@Column(name = "is_master")
	private Boolean isMaster;
	
	@Column(name = "mobile_alt")
	private String mobileAlt;
	
	@Column(name = "signature_id")
    private String signatureId;
	
	public Boolean getKycRequired() {
		return kycRequired;
	}

	public void setKycRequired(Boolean kycRequired) {
		this.kycRequired = kycRequired;
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

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
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

	public Integer getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Integer customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Boolean getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(Boolean isMaster) {
		this.isMaster = isMaster;
	}

	public String getMobileAlt() {
		return mobileAlt;
	}

	public void setMobileAlt(String mobileAlt) {
		this.mobileAlt = mobileAlt;
	}

	public String getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}
	
	
	
	

}
