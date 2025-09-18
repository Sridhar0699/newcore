package com.portal.setting.to;

import java.util.Map;

public class SettingTo {

	private Map<String, String> settings = null;

	private Integer fieldId = null;

	private String formatType = null;

	private String desc = null;

	private String shortId = null;

	private String val = null;

	private String valId = null;

	private String defaultVal = null;

	private Integer seqNo = null;

	private String refObjId = null;

	private String refObjType = null;

	public enum SettingType {

		APP_SETTING(1), ORG_SETTING(2), BP_SETTING(3), ORG_BP_SETTING(4);

		private int value;

		SettingType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * @return the settings
	 */
	public Map<String, String> getSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

	/**
	 * @return the fieldId
	 */
	public Integer getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId
	 *            the fieldId to set
	 */
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the formatType
	 */
	public String getFormatType() {
		return formatType;
	}

	/**
	 * @param formatType
	 *            the formatType to set
	 */
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the shortId
	 */
	public String getShortId() {
		return shortId;
	}

	/**
	 * @param shortId
	 *            the shortId to set
	 */
	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

	/**
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val
	 *            the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}

	/**
	 * @return the valId
	 */
	public String getValId() {
		return valId;
	}

	/**
	 * @param valId
	 *            the valId to set
	 */
	public void setValId(String valId) {
		this.valId = valId;
	}

	/**
	 * @return the defaultVal
	 */
	public String getDefaultVal() {
		return defaultVal;
	}

	/**
	 * @param defaultVal
	 *            the defaultVal to set
	 */
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	/**
	 * @return the seqNo
	 */
	public Integer getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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
}
