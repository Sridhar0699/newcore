package com.portal.activity.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_activity_types")
public class GdActivityTypes extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "activity_type_id")
	private Integer activityTypeId;

	@ManyToOne
	@JoinColumn(name = "activity_obj_id")
	private GdActivityObjects gdActivityObjects;

	@Column(name = "short_desc")
	private String shortDesc;

	@Column(name = "description")
	private String description;

	@Column(name = "message_template")
	private String messageTemplate;

	@Column(name = "mark_as_delete")
	private boolean markAsDelete;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	@OneToMany(mappedBy = "gdActivityTypes", fetch = FetchType.LAZY)
	private Set<AmActivityLog> amActivityHistory;

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
	 * @return the shortDesc
	 */
	public String getShortDesc() {
		return shortDesc;
	}

	/**
	 * @param shortDesc
	 *            the shortDesc to set
	 */
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the messageTemplate
	 */
	public String getMessageTemplate() {
		return messageTemplate;
	}

	/**
	 * @param messageTemplate
	 *            the messageTemplate to set
	 */
	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
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

	/**
	 * @return the amActivityHistory
	 */
	public Set<AmActivityLog> getAmActivityHistory() {
		return amActivityHistory;
	}

	/**
	 * @param amActivityHistory
	 *            the amActivityHistory to set
	 */
	public void setAmActivityHistory(Set<AmActivityLog> amActivityHistory) {
		this.amActivityHistory = amActivityHistory;
	}
}
