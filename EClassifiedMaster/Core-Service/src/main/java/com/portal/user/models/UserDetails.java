package com.portal.user.models;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User Details
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDetails {

	@JsonProperty("userId")
	private Integer userId = null;

	@JsonProperty("logonId")
	private String logonId = null;

	@JsonProperty("email")
	private String email = null;

	@JsonProperty("firstName")
	private String firstName = null;

	@JsonProperty("lastName")
	private String lastName = null;

	@JsonProperty("mobile")
	private String mobile = null;

	@JsonProperty("password")
	private String password = null;

	@JsonProperty("userType")
	private String userType = null;

	@JsonProperty("userTypeId")
	private Integer userTypeId = null;
	
	@JsonProperty("regCenter")
	private Integer regCenter = null;
	
	@JsonProperty("country")
	private Integer country = null;
	
	@JsonProperty("refrenceRole")
	private Integer refrenceRole = null;
	
	@JsonProperty("roleShortId")
	private String roleShortId = null;
	
	@JsonProperty("classification")
	private List<BigDecimal> classification = null;
	
	@JsonProperty("dealerId")
	private String dealerId = null;
	
	@JsonProperty("middleName")
	private String middleName = null;
	
	@JsonProperty("profileId")
	private String profileId = null;
	
	@JsonProperty("byteArray")
	private byte[] byteArray;
	
	@JsonProperty("imageUrl")
	private String imageUrl;
	
	@JsonProperty("bookingOffice")
	private String bookingOffice;
	
	@JsonProperty("empCode")
	private String empCode;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("appVersion")
	private String appVersion;
	
	@JsonProperty("daAdsTypes")
	private String daAdsTypes;
	
	@JsonProperty("houseNo")
	private String houseNo;
	
	@JsonProperty("pinCode")
	private String pinCode;
	
	@JsonProperty("bankName")
	private String bankName;
	
	@JsonProperty("branch")
	private String branch;
	
	@JsonProperty("ifsc")
	private String ifsc;
	
	@JsonProperty("isSubmitted")
	private boolean isSubmitted;
	
	@JsonProperty("otherAttachmentName")
	private String otherAttachmentName;
	
	@JsonProperty("signature")
	private String signature;
	
	@JsonProperty("signatureName")
	private String signatureName;
	
	@JsonProperty("aadharAttachment")
	private String aadharAttachment;
	
	@JsonProperty("aadharAttachmentName")
	private String aadharAttachmentName;
	
	@JsonProperty("gstAttachmentName")
	private String gstAttachmentName;
	
	@JsonProperty("gstAttachment")
	private String gstAttachment;
	
	@JsonProperty("attachmentId")
	private String attachmentId;
	
	@JsonProperty("termsAndCondId")
	private String termsAndCondId;
	
	@JsonProperty("termsAndCondName")
	private String termsAndCondName;

	
	@JsonProperty("termsAndConditions")
	private Boolean termsAndConditions;
	
	@JsonProperty("agreementForm")
	private Boolean agreementForm;
	
	@JsonProperty("createdBy")
	private Integer createdBy;
	
	@JsonProperty("createdByName")
	private String createdByName;
	
	@JsonProperty("vendorCommission")
	private Integer vendorCommission;
	
	@JsonProperty("executiveMobile")
	private String executiveMobile;
	
	@JsonProperty("executiveEmailId")
	private String executiveEmailId;
	

	@JsonProperty("isRmsApprover")
	private Boolean isRmsApprover;
	




	@JsonProperty("aadharNumber")
	private String aadharNumber;
	
	@JsonProperty("panNumber")
	private String panNumber;
	
	@JsonProperty("holderName")
	private String holderName;
	
	
	@JsonProperty("gstNo")
	private String gstNo;
	

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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

	public String getRoleShortId() {
		return roleShortId;
	}

	public void setRoleShortId(String roleShortId) {
		this.roleShortId = roleShortId;
	}

	public List<BigDecimal> getClassification() {
		return classification;
	}

	public void setClassification(List<BigDecimal> classification) {
		this.classification = classification;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getBookingOffice() {
		return bookingOffice;
	}

	public void setBookingOffice(String bookingOffice) {
		this.bookingOffice = bookingOffice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDaAdsTypes() {
		return daAdsTypes;
	}

	public void setDaAdsTypes(String daAdsTypes) {
		this.daAdsTypes = daAdsTypes;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
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

	public Integer getRefrenceRole() {
		return refrenceRole;
	}

	public void setRefrenceRole(Integer refrenceRole) {
		this.refrenceRole = refrenceRole;
	}

	public boolean getIsSubmitted() {
		return isSubmitted;
	}

	public void setIsSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

	public String getOtherAttachmentName() {
		return otherAttachmentName;
	}

	public void setOtherAttachmentName(String otherAttachmentName) {
		this.otherAttachmentName = otherAttachmentName;
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

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
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

	public Boolean getTermsAndConditions() {
		return termsAndConditions;
	}

	public Boolean getAgreementForm() {
		return agreementForm;
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

	public String getGstAttachmentName() {
		return gstAttachmentName;
	}

	public void setGstAttachmentName(String gstAttachmentName) {
		this.gstAttachmentName = gstAttachmentName;
	}

	public String getGstAttachment() {
		return gstAttachment;
	}

	public void setGstAttachment(String gstAttachment) {
		this.gstAttachment = gstAttachment;
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
	
	
	
}
