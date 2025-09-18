package com.portal.reports.utility;

import java.util.List;

public class DataTransformParamsTo {

	private List<String> keyFileds;
	private List<String> compareFiled;
	private List<String> sourceTypes;
	private String typeFld;

	public List<String> getKeyFileds() {
		return keyFileds;
	}

	public void setKeyFileds(List<String> keyFileds) {
		this.keyFileds = keyFileds;
	}

	public List<String> getCompareFiled() {
		return compareFiled;
	}

	public List<String> getSourceTypes() {
		return sourceTypes;
	}

	public void setCompareFiled(List<String> compareFiled) {
		this.compareFiled = compareFiled;
	}

	public void setSourceTypes(List<String> sourceTypes) {
		this.sourceTypes = sourceTypes;
	}

	public String getTypeFld() {
		return typeFld;
	}

	public void setTypeFld(String typeFld) {
		this.typeFld = typeFld;
	}

}
