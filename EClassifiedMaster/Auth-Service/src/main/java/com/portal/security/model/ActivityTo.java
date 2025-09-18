package com.portal.security.model;

import java.util.Date;
import java.util.Map;

/**
 * Activity Transfer object
 * 
 * @author Sathish Babu D
 *
 */
public class ActivityTo {

	private String orgId = null;

	private String orgBpId = null;

	private Integer activityObjId = null;

	private String activityObj = null;

	private Integer activityTypeId = null;

	private String activityType = null;

	private Integer activityLogId = null;

	private String accessObjId = null;

	private Map<String, String> templateProps = null;

	private String message = null;

	private String ipAddress = null;

	private Integer userId = null;

	private String userName = null;

	private Boolean notForDisplay = null;

	private String addPar1 = null;

	private String addPar2 = null;

	private String addPar3 = null;

	private String addPar4 = null;

	private String addPar5 = null;

	private Boolean status = false;

	private Integer pg_num = 1;

	private Integer pg_size = 10;

	private Integer ttlCnt = null;

	private String sortBy = null;

	private String searchBy = null;

	private Date createdTs = null;

	public ActivityTo(Integer userId, Integer activityObjId, Integer activityTypeId, Map<String, String> templateProps,
			String ipAddress, Boolean notForDisplay) {

		this.userId = userId;
		this.activityObjId = activityObjId;
		this.activityTypeId = activityTypeId;
		this.templateProps = templateProps;
		this.ipAddress = ipAddress;
		this.notForDisplay = notForDisplay;
	}

	public ActivityTo() {

	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the orgBpId
	 */
	public String getOrgBpId() {
		return orgBpId;
	}

	/**
	 * @param orgBpId
	 *            the orgBpId to set
	 */
	public void setOrgBpId(String orgBpId) {
		this.orgBpId = orgBpId;
	}

	/**
	 * @return the activityObjId
	 */
	public Integer getActivityObjId() {
		return activityObjId;
	}

	/**
	 * @param activityObjId
	 *            the activityObjId to set
	 */
	public void setActivityObjId(Integer activityObjId) {
		this.activityObjId = activityObjId;
	}

	/**
	 * @return the activityTypeId
	 */
	public Integer getActivityTypeId() {
		return activityTypeId;
	}

	/**
	 * @param activityTypeId
	 *            the activityTypeId to set
	 */
	public void setActivityTypeId(Integer activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	/**
	 * @return the activityLogId
	 */
	public Integer getActivityLogId() {
		return activityLogId;
	}

	/**
	 * @param activityLogId
	 *            the activityLogId to set
	 */
	public void setActivityLogId(Integer activityLogId) {
		this.activityLogId = activityLogId;
	}

	/**
	 * @return the accessObjId
	 */
	public String getAccessObjId() {
		return accessObjId;
	}

	/**
	 * @param accessObjId
	 *            the accessObjId to set
	 */
	public void setAccessObjId(String accessObjId) {
		this.accessObjId = accessObjId;
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
	 * @return the notForDisplay
	 */
	public Boolean getNotForDisplay() {
		return notForDisplay;
	}

	/**
	 * @param notForDisplay
	 *            the notForDisplay to set
	 */
	public void setNotForDisplay(Boolean notForDisplay) {
		this.notForDisplay = notForDisplay;
	}

	/**
	 * @return the addPar1
	 */
	public String getAddPar1() {
		return addPar1;
	}

	/**
	 * @param addPar1
	 *            the addPar1 to set
	 */
	public void setAddPar1(String addPar1) {
		this.addPar1 = addPar1;
	}

	/**
	 * @return the addPar2
	 */
	public String getAddPar2() {
		return addPar2;
	}

	/**
	 * @param addPar2
	 *            the addPar2 to set
	 */
	public void setAddPar2(String addPar2) {
		this.addPar2 = addPar2;
	}

	/**
	 * @return the addPar3
	 */
	public String getAddPar3() {
		return addPar3;
	}

	/**
	 * @param addPar3
	 *            the addPar3 to set
	 */
	public void setAddPar3(String addPar3) {
		this.addPar3 = addPar3;
	}

	/**
	 * @return the addPar4
	 */
	public String getAddPar4() {
		return addPar4;
	}

	/**
	 * @param addPar4
	 *            the addPar4 to set
	 */
	public void setAddPar4(String addPar4) {
		this.addPar4 = addPar4;
	}

	/**
	 * @return the addPar5
	 */
	public String getAddPar5() {
		return addPar5;
	}

	/**
	 * @param addPar5
	 *            the addPar5 to set
	 */
	public void setAddPar5(String addPar5) {
		this.addPar5 = addPar5;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the templateProps
	 */
	public Map<String, String> getTemplateProps() {
		return templateProps;
	}

	/**
	 * @param templateProps
	 *            the templateProps to set
	 */
	public void setTemplateProps(Map<String, String> templateProps) {
		this.templateProps = templateProps;
	}

	/**
	 * @return the pg_num
	 */
	public Integer getPg_num() {
		return pg_num;
	}

	/**
	 * @param pg_num
	 *            the pg_num to set
	 */
	public void setPg_num(Integer pg_num) {
		this.pg_num = pg_num;
	}

	/**
	 * @return the pg_size
	 */
	public Integer getPg_size() {
		return pg_size;
	}

	/**
	 * @param pg_size
	 *            the pg_size to set
	 */
	public void setPg_size(Integer pg_size) {
		this.pg_size = pg_size;
	}

	/**
	 * @return the ttlCnt
	 */
	public Integer getTtlCnt() {
		return ttlCnt;
	}

	/**
	 * @param ttlCnt
	 *            the ttlCnt to set
	 */
	public void setTtlCnt(Integer ttlCnt) {
		this.ttlCnt = ttlCnt;
	}

	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy
	 *            the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return the searchBy
	 */
	public String getSearchBy() {
		return searchBy;
	}

	/**
	 * @param searchBy
	 *            the searchBy to set
	 */
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	/**
	 * @return the activityObj
	 */
	public String getActivityObj() {
		return activityObj;
	}

	/**
	 * @param activityObj
	 *            the activityObj to set
	 */
	public void setActivityObj(String activityObj) {
		this.activityObj = activityObj;
	}

	/**
	 * @return the activityType
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType
	 *            the activityType to set
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the createdTs
	 */
	public Date getCreatedTs() {
		return createdTs;
	}

	/**
	 * @param createdTs
	 *            the createdTs to set
	 */
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}
}
