package com.portal.gd.models;

import java.util.List;
import java.util.Map;

public class GdMasterResponse {

	private String displayColumns;
	private List<Map<String, Object>> data;
	private String keyColumn;

	public String getDisplayColumns() {
		return displayColumns;
	}

	public void setDisplayColumns(String displayColumns) {
		this.displayColumns = displayColumns;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

}
