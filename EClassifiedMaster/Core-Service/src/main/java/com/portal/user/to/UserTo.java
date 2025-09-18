package com.portal.user.to;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserTo {

	private Integer userId = null;

	private String logonId = null;

	private String pwd = null;

	private String email = null;

	private String firstName = null;
	
	private String middleName = null;

	private String lastName = null;

	private String mobile = null;

	private String userType = null;

	private Integer userTypeId = null;

	private Integer loggedUser = null;

	private Date keyValidTime = null;

	private String resetKey = null;

	private boolean markAsDelete;

	private List<String> bpIds = null;

	private boolean isExisted;

	private boolean isUpdated;

	private Boolean isUserLocked = false;

	private int logonRetries = 0;

	private String currentOrgId = null;;

	private String bpId = null;

	private String bpLegalName = null;

	private String bpType = null;

	private String role = null;
	
	private String roleDesc = null;

	private String roleId = null;

	private boolean isResetPwd;

	private Date passwordExpiredTs;

	private String oldPwd = null;

	private String newPwd = null;

	private boolean isPwdMatched;

	private String message = null;

	private String erpRefId = null;

	private Date created_ts = null;

	private int pwdExpiredDays = 180;

	private String browserName = null;

	private String browserVersion = null;

	private String ipAddress = null;

	private String logonTs = null;

	private String encryOldPwd;
	private String encryNewPwd;
	
	private Integer regCenter;
	private Integer country;
	private String address;
	private boolean isDeactivated;
	private Integer region;
	private String classification;
	private String regCenterName;
	private String countryName;
	private String gdCode;
	private String emailIds;
	private String roleType;
	private Boolean isSspActive;
	private String logonEmail;
	private List<String> secondaryRoles;
	
	private String dealerId;
	
	private String actDeactAction = null;
	
	private String profileId = null;
	
	private String roleShortId = null;
	
	private String address1;
	private String address2;
	private String address3;
	private String officeLocation;
	private String pinCode;
	private String city;
	private String state;
	private String stateDesc;
	private String panNumber;
	private String gstNo;
	private String houseNo;
	private String cityDesc;
	private String typeDesc;
	private String typeShortId;
	private String userFullName;
	
	private String bookingOffice;
	private String empCode;
	private String aadharNumber;
	
	private String clientCode;
	private String customerName;
	private Integer empId;
	
	private String action;
	private String daAdsTypes;
	
	private String attachmentId;
	
	private Integer refrenceRole;
	private String bankName;
	private String branch;
	private String ifsc;
	private String panAttachments;
	private String aadharAttachment;
	private String gstAttachment;
	private String gstAttachmentName;
	
	private String aadharAttachmentName;
	private  String panAttachmentName;
	private String otherAttachmentName;
	
	private String signature;
	private String signatureName;
	
	private List<String> classifiedTypeIds;
	private String termsAndCondId;
	private String termsAndCondName;
	
	private String sspAction;
	private boolean isSubmitted;
	
	private Boolean termsAndConditions;
	private Boolean agreementForm;
	private String createdById;
	
	private Integer createdBy;
	private String createdByName;
	private String createdEmpCode;
	

	private Integer bookingCode;
	
	private String wfUserName;
	

	private Integer vendorCommission;
	private String executiveMobile;
	private String executiveEmailId;
	private Boolean isRmsApprover;
	private String approvedBy;
	private String approvedTime;
	private String status;
	private String comments;
	private String holderName;


	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	private List<BigDecimal> categoryId;
	
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the logonId
	 */
	public String getLogonId() {
		return logonId;
	}

	/**
	 * @param logonId
	 *            the logonId to set
	 */
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd
	 *            the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Boolean getIsSspActive() {
		return isSspActive;
	}

	public void setIsSspActive(Boolean isSspActive) {
		this.isSspActive = isSspActive;
	}

	/**
	 * @return the userTypeId
	 */
	public Integer getUserTypeId() {
		return userTypeId;
	}

	/**
	 * @param userTypeId
	 *            the userTypeId to set
	 */
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	/**
	 * @return the loggedUser
	 */
	public Integer getLoggedUser() {
		return loggedUser;
	}

	/**
	 * @param integer
	 *            the loggedUser to set
	 */
	public void setLoggedUser(Integer integer) {
		this.loggedUser = integer;
	}

	/**
	 * @return the keyValidTime
	 */
	public Date getKeyValidTime() {
		return keyValidTime;
	}

	/**
	 * @param keyValidTime
	 *            the keyValidTime to set
	 */
	public void setKeyValidTime(Date keyValidTime) {
		this.keyValidTime = keyValidTime;
	}

	/**
	 * @return the resetKey
	 */
	public String getResetKey() {
		return resetKey;
	}

	/**
	 * @param resetKey
	 *            the resetKey to set
	 */
	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	/**
	 * @return the markAsDelete
	 */
	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @param markAsDelete
	 *            the markAsDelete to set
	 */
	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	/**
	 * @return the bpIds
	 */
	public List<String> getBpIds() {
		return bpIds;
	}

	/**
	 * @param bpIds
	 *            the bpIds to set
	 */
	public void setBpIds(List<String> bpIds) {
		this.bpIds = bpIds;
	}
	

	public String getLogonEmail() {
		return logonEmail;
	}

	public void setLogonEmail(String logonEmail) {
		this.logonEmail = logonEmail;
	}

	/**
	 * @return the isExisted
	 */
	public boolean isExisted() {
		return isExisted;
	}

	/**
	 * @param isExisted
	 *            the isExisted to set
	 */
	public void setExisted(boolean isExisted) {
		this.isExisted = isExisted;
	}

	/**
	 * @return the isUpdated
	 */
	public boolean isUpdated() {
		return isUpdated;
	}

	/**
	 * @param isUpdated
	 *            the isUpdated to set
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	/**
	 * @return the logonRetries
	 */
	public int getLogonRetries() {
		return logonRetries;
	}

	/**
	 * @param logonRetries
	 *            the logonRetries to set
	 */
	public void setLogonRetries(int logonRetries) {
		this.logonRetries = logonRetries;
	}

	/**
	 * @return the currentOrgId
	 */
	public String getCurrentOrgId() {
		return currentOrgId;
	}

	/**
	 * @param currentOrgId
	 *            the currentOrgId to set
	 */
	public void setCurrentOrgId(String currentOrgId) {
		this.currentOrgId = currentOrgId;
	}

	/**
	 * @return the bpId
	 */
	public String getBpId() {
		return bpId;
	}

	/**
	 * @param bpId
	 *            the bpId to set
	 */
	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	/**
	 * @return the bpLegalName
	 */
	public String getBpLegalName() {
		return bpLegalName;
	}

	/**
	 * @param bpLegalName
	 *            the bpLegalName to set
	 */
	public void setBpLegalName(String bpLegalName) {
		this.bpLegalName = bpLegalName;
	}

	/**
	 * @return the bpType
	 */
	public String getBpType() {
		return bpType;
	}

	/**
	 * @param bpType
	 *            the bpType to set
	 */
	public void setBpType(String bpType) {
		this.bpType = bpType;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the isUserLocked
	 */
	public Boolean isUserLocked() {
		return isUserLocked;
	}

	/**
	 * @param isUserLocked
	 *            the isUserLocked to set
	 */
	public void setUserLocked(Boolean isUserLocked) {
		this.isUserLocked = isUserLocked;
	}

	/**
	 * @return the isResetPwd
	 */
	public boolean isResetPwd() {
		return isResetPwd;
	}

	/**
	 * @param isResetPwd
	 *            the isResetPwd to set
	 */
	public void setResetPwd(boolean isResetPwd) {
		this.isResetPwd = isResetPwd;
	}

	/**
	 * @return the passwordExpiredTs
	 */
	public Date getPasswordExpiredTs() {
		return passwordExpiredTs;
	}

	/**
	 * @param passwordExpiredTs
	 *            the passwordExpiredTs to set
	 */
	public void setPasswordExpiredTs(Date passwordExpiredTs) {
		this.passwordExpiredTs = passwordExpiredTs;
	}

	/**
	 * @return the oldPwd
	 */
	public String getOldPwd() {
		return oldPwd;
	}

	/**
	 * @param oldPwd
	 *            the oldPwd to set
	 */
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	/**
	 * @return the newPwd
	 */
	public String getNewPwd() {
		return newPwd;
	}

	/**
	 * @param newPwd
	 *            the newPwd to set
	 */
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	/**
	 * @return the isPwdMatched
	 */
	public boolean isPwdMatched() {
		return isPwdMatched;
	}

	/**
	 * @param isPwdMatched
	 *            the isPwdMatched to set
	 */
	public void setPwdMatched(boolean isPwdMatched) {
		this.isPwdMatched = isPwdMatched;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public String getErpRefId() {
		return erpRefId;
	}

	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}

	/**
	 * @return the created_ts
	 */
	public Date getCreated_ts() {
		return created_ts;
	}

	/**
	 * @param created_ts
	 *            the created_ts to set
	 */
	public void setCreated_ts(Date created_ts) {
		this.created_ts = created_ts;
	}

	/**
	 * @return the pwdExpiredDays
	 */
	public int getPwdExpiredDays() {
		return pwdExpiredDays;
	}

	/**
	 * @param pwdExpiredDays
	 *            the pwdExpiredDays to set
	 */
	public void setPwdExpiredDays(int pwdExpiredDays) {
		this.pwdExpiredDays = pwdExpiredDays;
	}

	/**
	 * @return the browserName
	 */
	public String getBrowserName() {
		return browserName;
	}

	/**
	 * @param browserName
	 *            the browserName to set
	 */
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	/**
	 * @return the browserVersion
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * @param browserVersion
	 *            the browserVersion to set
	 */
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the logonTs
	 */
	public String getLogonTs() {
		return logonTs;
	}

	/**
	 * @param logonTs
	 *            the logonTs to set
	 */
	public void setLogonTs(String logonTs) {
		this.logonTs = logonTs;
	}

	public String getEncryOldPwd() {
		return encryOldPwd;
	}

	public void setEncryOldPwd(String encryOldPwd) {
		this.encryOldPwd = encryOldPwd;
	}

	public String getEncryNewPwd() {
		return encryNewPwd;
	}

	public void setEncryNewPwd(String encryNewPwd) {
		this.encryNewPwd = encryNewPwd;
	}
	
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isDeactivated() {
		return isDeactivated;
	}

	public void setDeactivated(boolean isDeactivated) {
		this.isDeactivated = isDeactivated;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public Integer getRegCenter() {
		return regCenter;
	}

	public void setRegCenter(Integer regCenter) {
		this.regCenter = regCenter;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getRegCenterName() {
		return regCenterName;
	}

	public void setRegCenterName(String regCenterName) {
		this.regCenterName = regCenterName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getGdCode() {
		return gdCode;
	}

	public void setGdCode(String gdCode) {
		this.gdCode = gdCode;
	}

	public List<BigDecimal> getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(List<BigDecimal> categoryId) {
		this.categoryId = categoryId;
	}

	public String getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}

	public String getRoleType() {
		return roleType;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public List<String> getSecondaryRoles() {
		return secondaryRoles;
	}

	public void setSecondaryRoles(List<String> secondaryRoles) {
		this.secondaryRoles = secondaryRoles;
	}

	public String getActDeactAction() {
		return actDeactAction;
	}

	public void setActDeactAction(String actDeactAction) {
		this.actDeactAction = actDeactAction;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getRoleShortId() {
		return roleShortId;
	}

	public void setRoleShortId(String roleShortId) {
		this.roleShortId = roleShortId;
	}

	public Boolean getIsUserLocked() {
		return isUserLocked;
	}

	public void setIsUserLocked(Boolean isUserLocked) {
		this.isUserLocked = isUserLocked;
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

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getTypeShortId() {
		return typeShortId;
	}

	public void setTypeShortId(String typeShortId) {
		this.typeShortId = typeShortId;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getBookingOffice() {
		return bookingOffice;
	}

	public void setBookingOffice(String bookingOffice) {
		this.bookingOffice = bookingOffice;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDaAdsTypes() {
		return daAdsTypes;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setDaAdsTypes(String daAdsTypes) {
		this.daAdsTypes = daAdsTypes;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Integer getRefrenceRole() {
		return refrenceRole;
	}

	public void setRefrenceRole(Integer refrenceRole) {
		this.refrenceRole = refrenceRole;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getPanAttachments() {
		return panAttachments;
	}

	public void setPanAttachments(String panAttachments) {
		this.panAttachments = panAttachments;
	}

	public String getAadharAttachment() {
		return aadharAttachment;
	}

	public void setAadharAttachment(String aadharAttachment) {
		this.aadharAttachment = aadharAttachment;
	}

	public String getAadharAttachmentName() {
		return aadharAttachmentName;
	}

	public void setAadharAttachmentName(String aadharAttachmentName) {
		this.aadharAttachmentName = aadharAttachmentName;
	}

	public String getPanAttachmentName() {
		return panAttachmentName;
	}

	public void setPanAttachmentName(String panAttachmentName) {
		this.panAttachmentName = panAttachmentName;
	}

	public String getOtherAttachmentName() {
		return otherAttachmentName;
	}

	public void setOtherAttachmentName(String otherAttachmentName) {
		this.otherAttachmentName = otherAttachmentName;
	}

	public List<String> getClassifiedTypeIds() {
		return classifiedTypeIds;
	}

	public void setClassifiedTypeIds(List<String> classifiedTypeIds) {
		this.classifiedTypeIds = classifiedTypeIds;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignatureName() {
		return signatureName;
	}

	public void setSignatureName(String signatureName) {
		this.signatureName = signatureName;
	}

	public String getCityDesc() {
		return cityDesc;
	}

	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}

	public String getSspAction() {
		return sspAction;
	}

	public void setSspAction(String sspAction) {
		this.sspAction = sspAction;
	}

	public boolean isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

	public String getTermsAndCondId() {
		return termsAndCondId;
	}

	public void setTermsAndCondId(String termsAndCondId) {
		this.termsAndCondId = termsAndCondId;
	}

	public String getTermsAndCondName() {
		return termsAndCondName;
	}

	public void setTermsAndCondName(String termsAndCondName) {
		this.termsAndCondName = termsAndCondName;
	}

	public Boolean isTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(Boolean termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public Boolean isAgreementForm() {
		return agreementForm;
	}

	public void setAgreementForm(Boolean agreementForm) {
		this.agreementForm = agreementForm;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Boolean getTermsAndConditions() {
		return termsAndConditions;
	}

	public Boolean getAgreementForm() {
		return agreementForm;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedEmpCode() {
		return createdEmpCode;
	}

	public void setCreatedEmpCode(String createdEmpCode) {
		this.createdEmpCode = createdEmpCode;
	}


	public Integer getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(Integer bookingCode) {
		this.bookingCode = bookingCode;
	}

	public String getWfUserName() {
		return wfUserName;
	}

	public void setWfUserName(String wfUserName) {
		this.wfUserName = wfUserName;
	}


	public Integer getVendorCommission() {
		return vendorCommission;
	}

	public void setVendorCommission(Integer vendorCommission) {
		this.vendorCommission = vendorCommission;
	}

	public String getExecutiveMobile() {
		return executiveMobile;
	}

	public void setExecutiveMobile(String executiveMobile) {
		this.executiveMobile = executiveMobile;
	}

	public String getExecutiveEmailId() {
		return executiveEmailId;
	}

	public void setExecutiveEmailId(String executiveEmailId) {
		this.executiveEmailId = executiveEmailId;
	}

	public Boolean getIsRmsApprover() {
		return isRmsApprover;
	}

	public void setIsRmsApprover(Boolean isRmsApprover) {
		this.isRmsApprover = isRmsApprover;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	public String getGstAttachment() {
		return gstAttachment;
	}

	public void setGstAttachment(String gstAttachment) {
		this.gstAttachment = gstAttachment;
	}

	public String getGstAttachmentName() {
		return gstAttachmentName;
	}

	public void setGstAttachmentName(String gstAttachmentName) {
		this.gstAttachmentName = gstAttachmentName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	
}
