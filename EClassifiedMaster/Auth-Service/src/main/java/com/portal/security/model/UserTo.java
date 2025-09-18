package com.portal.security.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserTo {

	
	private Integer userId = null;

	private String logonId = null;

	private String pwd = null;

	private String email = null;

	private String firstName = null;

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
	
	private String secondaryRoles;

	private List<BigDecimal> categoryId;
	
	private String customerId;

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

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getSecondaryRoles() {
		return secondaryRoles;
	}

	public void setSecondaryRoles(String secondaryRoles) {
		this.secondaryRoles = secondaryRoles;
	}

	public Boolean getIsUserLocked() {
		return isUserLocked;
	}

	public void setIsUserLocked(Boolean isUserLocked) {
		this.isUserLocked = isUserLocked;
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
