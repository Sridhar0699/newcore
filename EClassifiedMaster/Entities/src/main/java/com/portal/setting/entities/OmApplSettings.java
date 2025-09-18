package com.portal.setting.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.gd.entities.GdSettingsDefinitions;

@Entity
@Table(name = "om_appl_settings")
public class OmApplSettings extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "appl_setting_id")
	private String applSettingId;

	@ManyToOne
	@JoinColumn(name = "setting_id")
	private GdSettingsDefinitions gdSettingsDefinitions;

	@Column(name = "ref_obj_id")
	private String refObjId;

	@Column(name = "ref_obj_type")
	private String refObjType;

	@Column(name = "setting_value")
	private String settingValue;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;

	/**
	 * @return the applSettingId
	 */
	public String getApplSettingId() {
		return applSettingId;
	}

	/**
	 * @param applSettingId
	 *            the applSettingId to set
	 */
	public void setApplSettingId(String applSettingId) {
		this.applSettingId = applSettingId;
	}

	/**
	 * @return the gdSettingsDefinitions
	 */
	public GdSettingsDefinitions getGdSettingsDefinitions() {
		return gdSettingsDefinitions;
	}

	/**
	 * @param gdSettingsDefinitions
	 *            the gdSettingsDefinitions to set
	 */
	public void setGdSettingsDefinitions(GdSettingsDefinitions gdSettingsDefinitions) {
		this.gdSettingsDefinitions = gdSettingsDefinitions;
	}

	/**
	 * @return the refObjId
	 */
	public String getRefObjId() {
		return refObjId;
	}

	/**
	 * @param refObjId
	 *            the refObjId to set
	 */
	public void setRefObjId(String refObjId) {
		this.refObjId = refObjId;
	}

	/**
	 * @return the refObjType
	 */
	public String getRefObjType() {
		return refObjType;
	}

	/**
	 * @param refObjType
	 *            the refObjType to set
	 */
	public void setRefObjType(String refObjType) {
		this.refObjType = refObjType;
	}

	/**
	 * @return the settingValue
	 */
	public String getSettingValue() {
		return settingValue;
	}

	/**
	 * @param settingValue
	 *            the settingValue to set
	 */
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
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
	 * @return the markAsDelete
	 */
	public Boolean isMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @param markAsDelete
	 *            the markAsDelete to set
	 */
	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}
}
