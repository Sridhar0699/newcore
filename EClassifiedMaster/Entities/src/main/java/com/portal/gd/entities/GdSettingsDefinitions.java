package com.portal.gd.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.setting.entities.OmApplSettings;

@Entity
@Table(name = "gd_settings_definitions")
public class GdSettingsDefinitions extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "setting_id")
	private Integer settingId;

	@Column(name = "setting_group_name")
	private String settingGroupName;

	@Column(name = "group_description")
	private String groupDescription;

	@Column(name = "setting_type_format")
	private String settingTypeFormat;

	@ManyToOne
	@JoinColumn(name = "setting_type")
	private GdSettingTypes gdSettingTypes;

	@Column(name = "setting_short_id")
	private String settingShortId;

	@Column(name = "setting_desc")
	private String settingDesc;

	@Column(name = "setting_seq_no")
	private Integer settingSeqNo;

	@Column(name = "setting_default_value")
	private String settingDefaultValue;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	@Column(name = "mark_as_delete")
	private boolean markAsDelete;

	@OneToMany(mappedBy = "gdSettingsDefinitions", fetch = FetchType.LAZY)
	private Set<OmApplSettings> omApplSettings;

	/**
	 * @return the settingId
	 */
	public Integer getSettingId() {
		return settingId;
	}

	/**
	 * @param settingId
	 *            the settingId to set
	 */
	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}

	/**
	 * @return the settingGroupName
	 */
	public String getSettingGroupName() {
		return settingGroupName;
	}

	/**
	 * @param settingGroupName
	 *            the settingGroupName to set
	 */
	public void setSettingGroupName(String settingGroupName) {
		this.settingGroupName = settingGroupName;
	}

	/**
	 * @return the groupDescription
	 */
	public String getGroupDescription() {
		return groupDescription;
	}

	/**
	 * @param groupDescription
	 *            the groupDescription to set
	 */
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	/**
	 * @return the settingTypeFormat
	 */
	public String getSettingTypeFormat() {
		return settingTypeFormat;
	}

	/**
	 * @param settingTypeFormat
	 *            the settingTypeFormat to set
	 */
	public void setSettingTypeFormat(String settingTypeFormat) {
		this.settingTypeFormat = settingTypeFormat;
	}

	/**
	 * @return the gdSettingTypes
	 */
	public GdSettingTypes getGdSettingTypes() {
		return gdSettingTypes;
	}

	/**
	 * @param gdSettingTypes
	 *            the gdSettingTypes to set
	 */
	public void setGdSettingTypes(GdSettingTypes gdSettingTypes) {
		this.gdSettingTypes = gdSettingTypes;
	}

	/**
	 * @return the settingShortId
	 */
	public String getSettingShortId() {
		return settingShortId;
	}

	/**
	 * @param settingShortId
	 *            the settingShortId to set
	 */
	public void setSettingShortId(String settingShortId) {
		this.settingShortId = settingShortId;
	}

	/**
	 * @return the settingDesc
	 */
	public String getSettingDesc() {
		return settingDesc;
	}

	/**
	 * @param settingDesc
	 *            the settingDesc to set
	 */
	public void setSettingDesc(String settingDesc) {
		this.settingDesc = settingDesc;
	}

	/**
	 * @return the settingSeqNo
	 */
	public Integer getSettingSeqNo() {
		return settingSeqNo;
	}

	/**
	 * @param settingSeqNo
	 *            the settingSeqNo to set
	 */
	public void setSettingSeqNo(Integer settingSeqNo) {
		this.settingSeqNo = settingSeqNo;
	}

	/**
	 * @return the settingDefaultValue
	 */
	public String getSettingDefaultValue() {
		return settingDefaultValue;
	}

	/**
	 * @param settingDefaultValue
	 *            the settingDefaultValue to set
	 */
	public void setSettingDefaultValue(String settingDefaultValue) {
		this.settingDefaultValue = settingDefaultValue;
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

	/**
	 * @return the omApplSettings
	 */
	public Set<OmApplSettings> getOmApplSettings() {
		return omApplSettings;
	}

	/**
	 * @param omApplSettings
	 *            the omApplSettings to set
	 */
	public void setOmApplSettings(Set<OmApplSettings> omApplSettings) {
		this.omApplSettings = omApplSettings;
	}
}
