package com.portal.org.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Organization user details
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrgUserDetails {

	@JsonProperty("userId")
	private Integer userId = null;

	@JsonProperty("logonId")
	private String logonId = null;

	@JsonProperty("email")
	private String email = null;

	@JsonProperty("firstName")
	private String firstName = null;

	@JsonProperty("middleName")
	private String middleName = null;

	@JsonProperty("lastName")
	private String lastName = null;

	@JsonProperty("mobile")
	private String mobile = null;

	@JsonProperty("bpId")
	private String bpId = null;

	@JsonProperty("bpLegalName")
	private String bpLegalName = null;

	@JsonProperty("bpType")
	private String bpType = null;

	@JsonProperty("userType")
	private String userType = null;

	@JsonProperty("userTypeId")
	private Integer userTypeId = null;

	@JsonProperty("role")
	private String role = null;

	@JsonProperty("roleId")
	private String roleId = null;

	@JsonProperty("isUserLocked")
	private Boolean isUserLocked = false;

	@JsonProperty("erpRefId")
	private String erpRefId = null;

	@JsonProperty("regCenter")
	private Integer regCenter;

	@JsonProperty("country")
	private Integer country;

	@JsonProperty("address")
	private String address;

	@JsonProperty("isDeactivated")
	private boolean isDeactivated;

	@JsonProperty("isActiveUser")
	private boolean isActiveUser;

	@JsonProperty("region")
	private Integer region;
	
	@JsonProperty("createdBy")
	private Integer createdBy;

	@JsonProperty("classification")
	private String classification;

	@JsonProperty("regCenterName")
	private String regCenterName = null;

	@JsonProperty("countryName")
	private String countryName = null;

	@JsonProperty("gdCode")
	private String gdCode = null;

	@JsonProperty("emailIds")
	private String emailIds = null;

	@JsonProperty("roleType")
	private String roleType = null;

	@JsonProperty("secondaryRoles")
	private List<String> secondaryRoles = null;

	@JsonProperty("dealerId")
	private String dealerId = null;
	
	@JsonProperty("roleShortId")
	private String roleShortId = null;
	
	@JsonProperty("userFullName")
	private String userFullName = null;
	
	@JsonProperty("state")
	private String state = null;
	
	@JsonProperty("city")
	private String city = null;
	
	@JsonProperty("cityDesc")
	private String cityDesc = null;
	
	@JsonProperty("empCode")
	private String empCode = null;
	
	
	@JsonProperty("attachmentId")
	private String attachmentId;
	
	@JsonProperty("refrenceRole")
	private Integer refrenceRole;
	
	@JsonProperty("bankName")
	private String bankName;
	
	@JsonProperty("branch")
	private String branch;
	
	@JsonProperty("ifsc")
	private String ifsc;
	
	@JsonProperty("panAttachments")
	private String panAttachments;
	
	@JsonProperty("aadharAttachment")
	private String aadharAttachment;
	
	@JsonProperty("aadharAttachmentName")
	private String aadharAttachmentName;
	
	@JsonProperty("panAttachmentName")
	private  String panAttachmentName;
	
	@JsonProperty("otherAttachmentName")
	private String otherAttachmentName;
	
	@JsonProperty("gstAttachment")
	private  String gstAttachment;
	
	@JsonProperty("gstAttachmentName")
	private String gstAttachmentName;
	
	
	@JsonProperty("signatureName")
	private  String signatureName;
	
	@JsonProperty("signature")
	private String signature;
	
	@JsonProperty("classifiedTypeIds")
	private List<String> classifiedTypeIds;

	@JsonProperty("createdByName")
	private String createdByName;
	
	@JsonProperty("createdEmpCode")
	private String createdEmpCode;
	
	@JsonProperty("executiveMobile")
	private String executiveMobile;
	
	@JsonProperty("approvedBy")
	private String approvedBy;
	
	@JsonProperty("approvedtime")
	private String approvedTime;
	
	@JsonProperty("comments")
	private String comments;
	
	@JsonProperty("isSspActive")
	private Boolean isSspActive;	
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("aadharNumber")
	private String aadharNumber;
	
	@JsonProperty("panNumber")
	private String panNumber;
	
	@JsonProperty("holderName")
	private String holderName;
	
	@JsonProperty("gstNo")
	private String gstNo;
	
	
	@JsonProperty("isRmsApprover")
	private Boolean isRmsApprover;
	
	
	public String getActDeactAction() {
		return actDeactAction;
	}

	public void setActDeactAction(String actDeactAction) {
		this.actDeactAction = actDeactAction;
	}

	@JsonProperty("actDeactAction")
	private String actDeactAction = null;

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
	 * @return the userLocked
	 */
	public Boolean isUserLocked() {
		return isUserLocked;
	}

	/**
	 * @param userLocked
	 *            the userLocked to set
	 */
	public void setUserLocked(Boolean userLocked) {
		this.isUserLocked = userLocked;
	}

	public String getErpRefId() {
		return erpRefId;
	}

	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
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

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
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

	public String getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}

	public Boolean getIsUserLocked() {
		return isUserLocked;
	}

	public void setIsUserLocked(Boolean isUserLocked) {
		this.isUserLocked = isUserLocked;
	}

	public String getRoleType() {
		return roleType;
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

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public boolean isActiveUser() {
		return isActiveUser;
	}

	public void setActiveUser(boolean isActiveUser) {
		this.isActiveUser = isActiveUser;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getRoleShortId() {
		return roleShortId;
	}

	public void setRoleShortId(String roleShortId) {
		this.roleShortId = roleShortId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
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

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getAttachmentId() {
		return attachmentId;
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

	public String getSignatureName() {
		return signatureName;
	}

	public void setSignatureName(String signatureName) {
		this.signatureName = signatureName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCityDesc() {
		return cityDesc;
	}

	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
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

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getExecutiveMobile() {
		return executiveMobile;
	}

	public void setExecutiveMobile(String executiveMobile) {
		this.executiveMobile = executiveMobile;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getIsSspActive() {
		return isSspActive;
	}

	public void setIsSspActive(Boolean isSspActive) {
		this.isSspActive = isSspActive;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public Boolean getIsRmsApprover() {
		return isRmsApprover;
	}

	public void setIsRmsApprover(Boolean isRmsApprover) {
		this.isRmsApprover = isRmsApprover;
	}
	

	
}
