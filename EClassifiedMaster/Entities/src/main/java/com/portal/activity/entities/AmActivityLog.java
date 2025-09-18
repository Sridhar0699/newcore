package com.portal.activity.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.user.entities.UmUsers;

@Entity
@Table(name = "am_activity_log")
public class AmActivityLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "activity_log_id")
	private String activityLogId;

	@Column(name = "org_id")
	private String org_id;

	@Column(name = "org_bp_id")
	private String org_bp_id;

	@ManyToOne
	@JoinColumn(name = "activity_type_id")
	private GdActivityTypes gdActivityTypes;

	@ManyToOne
	@JoinColumn(name = "activity_obj_id")
	private GdActivityObjects gdActivityObjects;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UmUsers umUsers;

	@Column(name = "access_obj_id")
	private String access_obj_id;

	@Column(name = "message")
	private String message;

	@Column(name = "not_for_display")
	private Boolean notForDisplay;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "add_par1")
	private String addPar1;

	@Column(name = "add_par2")
	private String addPar2;

	@Column(name = "add_par3")
	private String addPar3;

	@Column(name = "add_par4")
	private String addPar4;

	@Column(name = "add_par5")
	private String addPar5;

	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	/**
	 * @return the activityLogId
	 */
	public String getActivityLogId() {
		return activityLogId;
	}

	/**
	 * @param activityLogId
	 *            the activityLogId to set
	 */
	public void setActivityLogId(String activityLogId) {
		this.activityLogId = activityLogId;
	}

	/**
	 * @return the org_id
	 */
	public String getOrg_id() {
		return org_id;
	}

	/**
	 * @param org_id
	 *            the org_id to set
	 */
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	/**
	 * @return the org_bp_id
	 */
	public String getOrg_bp_id() {
		return org_bp_id;
	}

	/**
	 * @param org_bp_id
	 *            the org_bp_id to set
	 */
	public void setOrg_bp_id(String org_bp_id) {
		this.org_bp_id = org_bp_id;
	}

	/**
	 * @return the gdActivityTypes
	 */
	public GdActivityTypes getGdActivityTypes() {
		return gdActivityTypes;
	}

	/**
	 * @param gdActivityTypes
	 *            the gdActivityTypes to set
	 */
	public void setGdActivityTypes(GdActivityTypes gdActivityTypes) {
		this.gdActivityTypes = gdActivityTypes;
	}

	/**
	 * @return the gdActivityObjects
	 */
	public GdActivityObjects getGdActivityObjects() {
		return gdActivityObjects;
	}

	/**
	 * @param gdActivityObjects
	 *            the gdActivityObjects to set
	 */
	public void setGdActivityObjects(GdActivityObjects gdActivityObjects) {
		this.gdActivityObjects = gdActivityObjects;
	}

	/**
	 * @return the umUsers
	 */
	public UmUsers getUmUsers() {
		return umUsers;
	}

	/**
	 * @param umUsers
	 *            the umUsers to set
	 */
	public void setUmUsers(UmUsers umUsers) {
		this.umUsers = umUsers;
	}

	/**
	 * @return the access_obj_id
	 */
	public String getAccess_obj_id() {
		return access_obj_id;
	}

	/**
	 * @param access_obj_id
	 *            the access_obj_id to set
	 */
	public void setAccess_obj_id(String access_obj_id) {
		this.access_obj_id = access_obj_id;
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
	 * @return the markAsDelete
	 */
	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @param markAsDelete
	 *            the markAsDelete to set
	 */
	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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

	/**
	 * @return the changedBy
	 */
	public Integer getChangedBy() {
		return changedBy;
	}

	/**
	 * @param changedBy
	 *            the changedBy to set
	 */
	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	/**
	 * @return the changedTs
	 */
	public Date getChangedTs() {
		return changedTs;
	}

	/**
	 * @param changedTs
	 *            the changedTs to set
	 */
	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}
}
