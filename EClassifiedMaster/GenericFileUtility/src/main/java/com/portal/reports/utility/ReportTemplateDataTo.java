package com.portal.reports.utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;

public class ReportTemplateDataTo {

	List<LinkedHashMap<Integer, String>> rowItemsList = new ArrayList<LinkedHashMap<Integer, String>>();
	LinkedHashMap<Integer, String> rowItems = new LinkedHashMap<>();
	LinkedHashMap<Integer, String> colItemsFormula = new LinkedHashMap<>();
	LinkedHashMap<Integer, String> colFormulaRange = new LinkedHashMap<>();
	LinkedHashMap<Integer, String> rowItemsFormula = new LinkedHashMap<>();
	LinkedHashMap<Integer, String> rowFormulaRange = new LinkedHashMap<>();
	LinkedHashMap<Integer, String> secHeaderLabels = new LinkedHashMap<>();
	LinkedHashMap<Integer, String> bodyParamsLabels = new LinkedHashMap<Integer, String>();
	LinkedHashMap<Integer, LinkedHashMap<Integer, String>> bodyParamsLabelsMap = new LinkedHashMap<>();
	private LinkedHashMap<Integer, CellStyle> rowItemsBgClr;
	private LinkedHashMap<Integer, CellStyle> colItemsFormulaBgClrs;
	private LinkedHashMap<Integer, CellStyle> rowItemsFormulaBgClrs;
	private LinkedHashMap<Integer, CellStyle> secHeaderLabelsBgClrs;
	private LinkedHashMap<Integer, CellStyle> bodyParamLabelsBgClrs;
	private LinkedHashMap<String, Integer> clmIndex;
	
	public List<LinkedHashMap<Integer, String>> getRowItemsList() {
		return rowItemsList;
	}

	public LinkedHashMap<Integer, String> getRowItems() {
		return rowItems;
	}

	public LinkedHashMap<Integer, String> getColItemsFormula() {
		return colItemsFormula;
	}

	public LinkedHashMap<Integer, String> getColFormulaRange() {
		return colFormulaRange;
	}

	public LinkedHashMap<Integer, String> getRowItemsFormula() {
		return rowItemsFormula;
	}

	public LinkedHashMap<Integer, String> getRowFormulaRange() {
		return rowFormulaRange;
	}

	public LinkedHashMap<Integer, String> getSecHeaderLabels() {
		return secHeaderLabels;
	}

	public LinkedHashMap<Integer, String> getBodyParamsLabels() {
		return bodyParamsLabels;
	}

	public LinkedHashMap<Integer, LinkedHashMap<Integer, String>> getBodyParamsLabelsMap() {
		return bodyParamsLabelsMap;
	}

	public void setRowItemsList(List<LinkedHashMap<Integer, String>> rowItemsList) {
		this.rowItemsList = rowItemsList;
	}

	public void setRowItems(LinkedHashMap<Integer, String> rowItems) {
		this.rowItems = rowItems;
	}

	public void setColItemsFormula(LinkedHashMap<Integer, String> colItemsFormula) {
		this.colItemsFormula = colItemsFormula;
	}

	public void setColFormulaRange(LinkedHashMap<Integer, String> colFormulaRange) {
		this.colFormulaRange = colFormulaRange;
	}

	public void setRowItemsFormula(LinkedHashMap<Integer, String> rowItemsFormula) {
		this.rowItemsFormula = rowItemsFormula;
	}

	public void setRowFormulaRange(LinkedHashMap<Integer, String> rowFormulaRange) {
		this.rowFormulaRange = rowFormulaRange;
	}

	public void setSecHeaderLabels(LinkedHashMap<Integer, String> secHeaderLabels) {
		this.secHeaderLabels = secHeaderLabels;
	}

	public void setBodyParamsLabels(LinkedHashMap<Integer, String> bodyParamsLabels) {
		this.bodyParamsLabels = bodyParamsLabels;
	}

	public void setBodyParamsLabelsMap(LinkedHashMap<Integer, LinkedHashMap<Integer, String>> bodyParamsLabelsMap) {
		this.bodyParamsLabelsMap = bodyParamsLabelsMap;
	}

	public LinkedHashMap<Integer, CellStyle> getRowItemsBgClr() {
		return rowItemsBgClr;
	}

	public LinkedHashMap<Integer, CellStyle> getColItemsFormulaBgClrs() {
		return colItemsFormulaBgClrs;
	}

	public LinkedHashMap<Integer, CellStyle> getRowItemsFormulaBgClrs() {
		return rowItemsFormulaBgClrs;
	}

	public LinkedHashMap<Integer, CellStyle> getSecHeaderLabelsBgClrs() {
		return secHeaderLabelsBgClrs;
	}

	public void setRowItemsBgClr(LinkedHashMap<Integer, CellStyle> rowItemsBgClr) {
		this.rowItemsBgClr = rowItemsBgClr;
	}

	public void setColItemsFormulaBgClrs(LinkedHashMap<Integer, CellStyle> colItemsFormulaBgClrs) {
		this.colItemsFormulaBgClrs = colItemsFormulaBgClrs;
	}

	public void setRowItemsFormulaBgClrs(LinkedHashMap<Integer, CellStyle> rowItemsFormulaBgClrs) {
		this.rowItemsFormulaBgClrs = rowItemsFormulaBgClrs;
	}

	public void setSecHeaderLabelsBgClrs(LinkedHashMap<Integer, CellStyle> secHeaderLabelsBgClrs) {
		this.secHeaderLabelsBgClrs = secHeaderLabelsBgClrs;
	}

	public LinkedHashMap<String, Integer> getClmIndex() {
		return clmIndex;
	}

	public void setClmIndex(LinkedHashMap<String, Integer> clmIndex) {
		this.clmIndex = clmIndex;
	}

	public LinkedHashMap<Integer, CellStyle> getBodyParamLabelsBgClrs() {
		return bodyParamLabelsBgClrs;
	}

	public void setBodyParamLabelsBgClrs(LinkedHashMap<Integer, CellStyle> bodyParamLabelsBgClrs) {
		this.bodyParamLabelsBgClrs = bodyParamLabelsBgClrs;
	}

}
