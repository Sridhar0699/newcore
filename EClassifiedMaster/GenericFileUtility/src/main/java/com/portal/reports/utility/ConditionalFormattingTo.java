package com.portal.reports.utility;

import org.apache.poi.ss.util.CellRangeAddress;

public class ConditionalFormattingTo {

	private String keyField;
	private String rule;
	private String valueField;
	private boolean valueFieldIndClr;
	private String value;
	private Short indexedColors;
	private Short patternFormatting;
	private CellRangeAddress[] cellRangeAddress;
	private Integer startIndex;
	private Integer endIndex;

	public String getRule() {
		return rule;
	}

	public Short getIndexedColors() {
		return indexedColors;
	}

	public Short getPatternFormatting() {
		return patternFormatting;
	}

	public CellRangeAddress[] getCellRangeAddress() {
		return cellRangeAddress;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public void setIndexedColors(Short indexedColors) {
		this.indexedColors = indexedColors;
	}

	public void setPatternFormatting(Short patternFormatting) {
		this.patternFormatting = patternFormatting;
	}

	public void setCellRangeAddress(CellRangeAddress[] cellRangeAddress) {
		this.cellRangeAddress = cellRangeAddress;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	public boolean isValueFieldIndClr() {
		return valueFieldIndClr;
	}

	public void setValueFieldIndClr(boolean valueFieldIndClr) {
		this.valueFieldIndClr = valueFieldIndClr;
	}

}
