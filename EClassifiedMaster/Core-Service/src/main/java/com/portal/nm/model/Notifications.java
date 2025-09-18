package com.portal.nm.model;

import java.util.Date;
import java.util.List;

public class Notifications {

	private String notificationId;
	private String notificationType;
	private String notificationMessage;
	public boolean isRead;
	private String objectRefNumber;
	private String objectRefId;
	private String objectRefTable;
	private String orgId;
	private Integer userId;
	private String orgOpuId;
	private boolean markAsDelete;
	private List<Notifications> notifiInfoList;
	private Integer pageNumber;
	private Integer pageSize;
	private String docId;
	private String actionStatus;
	private String type;
	private String userName;
	private boolean unReadNotification = false;
	
	private Integer createdBy;
	private Date createdTs;
	private Integer updatedBy;
	private Date updatedTs;

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getObjectRefNumber() {
		return objectRefNumber;
	}

	public void setObjectRefNumber(String objectRefNumber) {
		this.objectRefNumber = objectRefNumber;
	}

	public String getObjectRefId() {
		return objectRefId;
	}

	public void setObjectRefId(String objectRefId) {
		this.objectRefId = objectRefId;
	}

	public String getObjectRefTable() {
		return objectRefTable;
	}

	public void setObjectRefTable(String objectRefTable) {
		this.objectRefTable = objectRefTable;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrgOpuId() {
		return orgOpuId;
	}

	public void setOrgOpuId(String orgOpuId) {
		this.orgOpuId = orgOpuId;
	}

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public List<Notifications> getNotifiInfoList() {
		return notifiInfoList;
	}

	public void setNotifiInfoList(List<Notifications> notifiInfoList) {
		this.notifiInfoList = notifiInfoList;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public boolean isUnReadNotification() {
		return unReadNotification;
	}

	public void setUnReadNotification(boolean unReadNotification) {
		this.unReadNotification = unReadNotification;
	}

}
