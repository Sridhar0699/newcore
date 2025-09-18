package com.portal.gd.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.gd.entities.GdSettingsDefinitions;

@Entity
@Table(name = "gd_setting_types")
public class GdSettingTypes extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "setting_type_id")
	private Integer settingTypeId;

	@Column(name = "setting_type_short_id")
	private String settingTypeShortId;

	@Column(name = "setting_type_desc")
	private String settingTypeDesc;

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

	@OneToMany(mappedBy = "gdSettingTypes", fetch = FetchType.LAZY)
	private Set<GdSettingsDefinitions> gdSettingsDefinitions;

	/**
	 * @return the settingTypeId
	 */
	public Integer getSettingTypeId() {
		return settingTypeId;
	}

	/**
	 * @param settingTypeId
	 *            the settingTypeId to set
	 */
	public void setSettingTypeId(Integer settingTypeId) {
		this.settingTypeId = settingTypeId;
	}

	/**
	 * @return the settingTypeShortId
	 */
	public String getSettingTypeShortId() {
		return settingTypeShortId;
	}

	/**
	 * @param settingTypeShortId
	 *            the settingTypeShortId to set
	 */
	public void setSettingTypeShortId(String settingTypeShortId) {
		this.settingTypeShortId = settingTypeShortId;
	}

	/**
	 * @return the settingTypeDesc
	 */
	public String getSettingTypeDesc() {
		return settingTypeDesc;
	}

	/**
	 * @param settingTypeDesc
	 *            the settingTypeDesc to set
	 */
	public void setSettingTypeDesc(String settingTypeDesc) {
		this.settingTypeDesc = settingTypeDesc;
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
	 * @return the gdSettingsDefinitions
	 */
	public Set<GdSettingsDefinitions> getGdSettingsDefinitions() {
		return gdSettingsDefinitions;
	}

	/**
	 * @param gdSettingsDefinitions
	 *            the gdSettingsDefinitions to set
	 */
	public void setGdSettingsDefinitions(Set<GdSettingsDefinitions> gdSettingsDefinitions) {
		this.gdSettingsDefinitions = gdSettingsDefinitions;
	}
}
