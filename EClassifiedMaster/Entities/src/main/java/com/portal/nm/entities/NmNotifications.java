package com.portal.nm.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "NM_NOTIFICATIONS")
public class NmNotifications extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "nm_generator")
	@Column(name = "notification_id")
	private String notificationId;

	@Column(name = "notification_type")
	private String notificationType;

	@Column(name = "notification_message")
	private String notificationMessage;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "org_opu_id")
	private String orgOpuId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "is_read")
	private boolean isRead;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "type")
	private String type;

	@Column(name = "object_ref_id")
	private String objectRefId;

	@Column(name = "objectRefTable")
	private String objectRefTable;

	@Column(name = "action_status")
	private String actionStatus;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "mark_as_delete")
	private boolean markAsDelete;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "updated_by")
	private Integer updatedBy;

	@Column(name = "updated_ts")
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgOpuId() {
		return orgOpuId;
	}

	public void setOrgOpuId(String orgOpuId) {
		this.orgOpuId = orgOpuId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
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

}
