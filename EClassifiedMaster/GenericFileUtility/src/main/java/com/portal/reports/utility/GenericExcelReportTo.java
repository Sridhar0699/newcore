package com.portal.reports.utility;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenericExcelReportTo {

	private List<Map<String, Object>> dataList;
	private List<String> resultFields;
	private Map<String, Object> bodyParamsData;
	private String templatePath;
	private Map<String,Map<String,Object>> dataToBeTransformed;
	private LinkedHashMap<String, List<Map<String, Object>>> sheetDataMap;
	private Map<String,Map<String,String>> dataToBeCopied = new HashMap<String,Map<String,String>>();
	private Map<String, DataTransformParamsTo> dataTransformParams;
	private Map<String, List<ConditionalFormattingTo>> conditionalFormatting;
	private Map<String,Map<String,Object>> sheetWiseQueryParams=new HashMap<String,Map<String,Object>>();
	private String extTemplatePath;
	
	public Map<String, Map<String, Object>> getSheetWiseQueryParams() {
		return sheetWiseQueryParams;
	}

	public void setSheetWiseQueryParams(Map<String, Map<String, Object>> sheetWiseQueryParams) {
		this.sheetWiseQueryParams = sheetWiseQueryParams;
	}

	public List<String> getResultFields() {
		return resultFields;
	}

	public void setResultFields(List<String> resultFields) {
		this.resultFields = resultFields;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public Map<String, Object> getBodyParamsData() {
		return bodyParamsData;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public void setBodyParamsData(Map<String, Object> bodyParamsData) {
		this.bodyParamsData = bodyParamsData;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map<String, Map<String, Object>> getDataToBeTransformed() {
		return dataToBeTransformed;
	}

	public void setDataToBeTransformed(Map<String, Map<String, Object>> dataToBeTransform) {
		this.dataToBeTransformed = dataToBeTransform;
	}

	public LinkedHashMap<String, List<Map<String, Object>>> getSheetDataMap() {
		return sheetDataMap;
	}

	public void setSheetDataMap(LinkedHashMap<String, List<Map<String, Object>>> sheetDataMap) {
		this.sheetDataMap = sheetDataMap;
	}

	public Map<String, Map<String, String>> getDataToBeCopied() {
		return dataToBeCopied;
	}

	public void setDataToBeCopied(Map<String, Map<String, String>> dataToBeCopied) {
		this.dataToBeCopied = dataToBeCopied;
	}

	public Map<String, DataTransformParamsTo> getDataTransformParams() {
		return dataTransformParams;
	}

	public void setDataTransformParams(Map<String, DataTransformParamsTo> dataTransformParams) {
		this.dataTransformParams = dataTransformParams;
	}

	public Map<String, List<ConditionalFormattingTo>> getConditionalFormatting() {
		return conditionalFormatting;
	}

	public void setConditionalFormatting(Map<String, List<ConditionalFormattingTo>> conditionalFormatting) {
		this.conditionalFormatting = conditionalFormatting;
	}

	public String getExtTemplatePath() {
		return extTemplatePath;
	}

	public void setExtTemplatePath(String extTemplatePath) {
		this.extTemplatePath = extTemplatePath;
	}

}
