package com.portal.gd.models;

import java.util.Date;

/**
 * OmSettingsDetails
 */

public class GdSettingsDetails {

	private Integer settingId;

	private String settingGroupName;

	private String groupDescription;

	private String settingTypeFormat;

	private String settingShortId;

	private String settingDesc;
	
	private Integer settingType;

	private Integer settingSeqNo;

	private String settingDefaultValue;

	private Integer createdBy;

	private Date createdTs;

	private Integer changedBy;

	private Date changedTs;

	public Integer getSettingId() {
		return settingId;
	}

	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}

	public String getSettingGroupName() {
		return settingGroupName;
	}

	public void setSettingGroupName(String settingGroupName) {
		this.settingGroupName = settingGroupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getSettingTypeFormat() {
		return settingTypeFormat;
	}

	public void setSettingTypeFormat(String settingTypeFormat) {
		this.settingTypeFormat = settingTypeFormat;
	}

	public String getSettingShortId() {
		return settingShortId;
	}

	public void setSettingShortId(String settingShortId) {
		this.settingShortId = settingShortId;
	}

	public String getSettingDesc() {
		return settingDesc;
	}

	public void setSettingDesc(String settingDesc) {
		this.settingDesc = settingDesc;
	}

	public Integer getSettingType() {
		return settingType;
	}

	public void setSettingType(Integer settingType) {
		this.settingType = settingType;
	}

	public Integer getSettingSeqNo() {
		return settingSeqNo;
	}

	public void setSettingSeqNo(Integer settingSeqNo) {
		this.settingSeqNo = settingSeqNo;
	}

	public String getSettingDefaultValue() {
		return settingDefaultValue;
	}

	public void setSettingDefaultValue(String settingDefaultValue) {
		this.settingDefaultValue = settingDefaultValue;
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

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	public Date getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

}