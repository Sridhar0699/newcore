package com.portal.gd.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portal.gd.entities.GdSettingTypes;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SmtpConfigs {

	@JsonProperty("setting_id")
	private Integer settingId;

	@JsonProperty("setting_group_name")
	private String settingGroupName;

	@JsonProperty("group_description")
	private String groupDescription;

	@JsonProperty("setting_type_format")
	private String settingTypeFormat;

	@JsonProperty("setting_type")
	private GdSettingTypes gdSettingTypes;

	@JsonProperty("setting_short_id")
	private String settingShortId;

	@JsonProperty("setting_desc")
	private String settingDesc;

	@JsonProperty("setting_seq_no")
	private Integer settingSeqNo;

	@JsonProperty("setting_default_value")
	private String settingDefaultValue;

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

	public GdSettingTypes getGdSettingTypes() {
		return gdSettingTypes;
	}

	public void setGdSettingTypes(GdSettingTypes gdSettingTypes) {
		this.gdSettingTypes = gdSettingTypes;
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

}
