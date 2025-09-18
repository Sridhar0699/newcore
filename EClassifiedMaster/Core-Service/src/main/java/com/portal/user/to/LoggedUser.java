package com.portal.user.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Sathish
 * @Copyrights Incresol
 *
 *
 */
public class LoggedUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String logonId = null;

	private Integer userId = null;

	private String email = null;

	private String roleId = null;

	private String roleName = null;

	private String roleDesc = null;

	private String currentOrgId = null;

	private String orgName = null;

	private String curentBranchId = null;

	private String bpName = null;

	private String firstName = null;

	private String lastName = null;

	private String mobile = null;

	private String ip = null;

	private String reqHeader = null;

	private String resHeader = null;

	private String reqUrl = null;

	private String reqMethod = null;

	private String browserName = null;

	private String browserVersion = null;

	private List<BigDecimal> categoryId;
	private Integer regCenter;
	private Integer country;
	private String gdCode;
	private String roleType;

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the logonId
	 */
	public String getLogonId() {
		return logonId;
	}

	/**
	 * @param logonId the logonId to set
	 */
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the currentOrgId
	 */
	public String getCurrentOrgId() {
		return currentOrgId;
	}

	/**
	 * @param currentOrgId the currentOrgId to set
	 */
	public void setCurrentOrgId(String currentOrgId) {
		this.currentOrgId = currentOrgId;
	}

	/**
	 * @return the curentBranchId
	 */
	public String getCurentBranchId() {
		return curentBranchId;
	}

	/**
	 * @param curentBranchId the curentBranchId to set
	 */
	public void setCurentBranchId(String curentBranchId) {
		this.curentBranchId = curentBranchId;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the bpName
	 */
	public String getBpName() {
		return bpName;
	}

	/**
	 * @param bpName the bpName to set
	 */
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
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
	 * @param lastName the lastName to set
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
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the reqHeader
	 */
	public String getReqHeader() {
		return reqHeader;
	}

	/**
	 * @param reqHeader the reqHeader to set
	 */
	public void setReqHeader(String reqHeader) {
		this.reqHeader = reqHeader;
	}

	/**
	 * @return the resHeader
	 */
	public String getResHeader() {
		return resHeader;
	}

	/**
	 * @param resHeader the resHeader to set
	 */
	public void setResHeader(String resHeader) {
		this.resHeader = resHeader;
	}

	/**
	 * @return the reqUrl
	 */
	public String getReqUrl() {
		return reqUrl;
	}

	/**
	 * @param reqUrl the reqUrl to set
	 */
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	/**
	 * @return the reqMethod
	 */
	public String getReqMethod() {
		return reqMethod;
	}

	/**
	 * @param reqMethod the reqMethod to set
	 */
	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}

	/**
	 * @return the browserName
	 */
	public String getBrowserName() {
		return browserName;
	}

	/**
	 * @param browserName the browserName to set
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
	 * @param browserVersion the browserVersion to set
	 */
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public List<BigDecimal> getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(List<BigDecimal> categoryId) {
		this.categoryId = categoryId;
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

	public String getGdCode() {
		return gdCode;
	}

	public void setGdCode(String gdCode) {
		this.gdCode = gdCode;
	}

	public Integer getRoleIntId() {
		return roleId != null ? Integer.parseInt(roleId) : null;
	}
}
