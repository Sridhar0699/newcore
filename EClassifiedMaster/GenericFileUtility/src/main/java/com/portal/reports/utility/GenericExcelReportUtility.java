package com.portal.reports.utility;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenericExcelReportUtility {
	
	public String getColumnNameByNumber(Integer colNum) {
		int Base = 26;
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String colName = "";
		colNum = colNum + 1;
		while (colNum > 0) {
			int position = colNum % Base;
			colName = (position == 0 ? 'Z' : chars.charAt(position > 0 ? position - 1 : 0)) + colName;
			colNum = (colNum - 1) / Base;
		}
		return colName;

	}
	
	public XSSFWorkbook getExcel(GenericExcelReportTo genericExcelReportTo) {
		XSSFWorkbook latestWb = new XSSFWorkbook();
		try {
			List<CellRangeAddress> mergedRegions = new ArrayList<>();
			LinkedHashMap<String,List<CellRangeAddress>> mergedRegionsMap = new LinkedHashMap<>();
			LinkedHashMap<String, SheetConditionalFormatting> sheetCf = new LinkedHashMap<>();
			Map<String, ReportTemplateDataTo> templateMap = new HashMap<String, ReportTemplateDataTo>();
			List<String> lockedSheets = new ArrayList<>();
			XSSFWorkbook wb = null;
			try {
				wb = new XSSFWorkbook(new FileInputStream(genericExcelReportTo.getExtTemplatePath()));
			} catch (Exception e) {
				wb = new XSSFWorkbook(
						this.getClass().getClassLoader().getResourceAsStream(genericExcelReportTo.getTemplatePath()));
			}
			for (int st = 0; st < wb.getNumberOfSheets(); st++) {
				XSSFSheet sheet = wb.getSheetAt(st);
				if(sheet.getProtect())
					lockedSheets.add(sheet.getSheetName());
				for(int i = 0; i < sheet.getNumMergedRegions(); ++i)
		        {
					mergedRegions.add(sheet.getMergedRegion(i));
		        }
				mergedRegionsMap.put(sheet.getSheetName(), mergedRegions);
				mergedRegions = new ArrayList<>();
				sheetCf.put(sheet.getSheetName(), sheet.getSheetConditionalFormatting());
				List<LinkedHashMap<Integer, String>> rowItemsList = new ArrayList<LinkedHashMap<Integer, String>>();
				LinkedHashMap<Integer, String> rowItems = new LinkedHashMap<>();
				LinkedHashMap<Integer, String> colItemsFormula = new LinkedHashMap<>();
				LinkedHashMap<Integer, String> colFormulaRange = new LinkedHashMap<>();
				LinkedHashMap<Integer, String> rowItemsFormula = new LinkedHashMap<>();
				LinkedHashMap<Integer, String> rowFormulaRange = new LinkedHashMap<>();
				LinkedHashMap<Integer, String> secHeaderLabels = new LinkedHashMap<>();
				LinkedHashMap<Integer, String> bodyParamsLabels = new LinkedHashMap<Integer, String>();
				LinkedHashMap<Integer, LinkedHashMap<Integer, String>> bodyParamsLabelsMap = new LinkedHashMap<>();
				LinkedHashMap<Integer, CellStyle> rowItemsBgClr = new LinkedHashMap<>();
				LinkedHashMap<Integer, CellStyle> secHeaderLabelsBgClrs = new LinkedHashMap<>();
				LinkedHashMap<Integer, CellStyle> rowItemsFormulaBgClrs = new LinkedHashMap<>();
				LinkedHashMap<Integer, CellStyle> colItemsFormulaBgClrs = new LinkedHashMap<>();
				LinkedHashMap<Integer, CellStyle> bodyParamLabelsBgClrs = new LinkedHashMap<>();
				LinkedHashMap<String, Integer> clmIndex = new LinkedHashMap<>();
				for (int j = 0; j <= sheet.getLastRowNum(); j++) {
					XSSFRow row = sheet.getRow(j);
					for (int i = 0; row != null && i < row.getLastCellNum(); i++) {
						Cell cell = row.getCell(i);
						if (cell == null)
							continue;
						if (cell.getStringCellValue() != null && cell.getStringCellValue().contains("{{")) {
							rowItems.put(cell.getColumnIndex(),
									cell.getStringCellValue().replace("{{", "").replace("}}", ""));
							clmIndex.put(cell.getStringCellValue().replace("{{", "").replace("}}", ""),cell.getColumnIndex());
							rowItemsBgClr.put(cell.getColumnIndex(), cloneCellStyle(latestWb,(XSSFCellStyle)cell.getCellStyle()));
						} else if (!rowItems.isEmpty() && cell.getStringCellValue() != null
								&& cell.getStringCellValue().contains("${")) {
							rowItemsFormula.put(cell.getColumnIndex(),
									cell.getStringCellValue().replace("${", "").replace("}", ""));
							rowItemsFormulaBgClrs.put(cell.getColumnIndex(), cloneCellStyle(latestWb,(XSSFCellStyle)cell.getCellStyle()));
						} else if (rowItems.isEmpty() && cell.getStringCellValue() != null
								&& cell.getStringCellValue().contains("${")) {
							colItemsFormula.put(cell.getColumnIndex(),
									cell.getStringCellValue().replace("${", "").replace("}", ""));
							colItemsFormulaBgClrs.put(cell.getColumnIndex(), cloneCellStyle(latestWb,(XSSFCellStyle)cell.getCellStyle()));
						} else if (rowItems.isEmpty() && cell.getStringCellValue() != null
								&& cell.getStringCellValue().contains("#{")) {
							secHeaderLabels.put(cell.getColumnIndex(),
									cell.getStringCellValue().replace("#{", "").replace("}", ""));
							secHeaderLabelsBgClrs.put(cell.getColumnIndex(), cloneCellStyle(latestWb,(XSSFCellStyle)cell.getCellStyle()));
						} else if (rowItems.isEmpty() && cell.getStringCellValue() != null
								&& cell.getStringCellValue().contains("$L{")) {
							bodyParamsLabels.put(cell.getColumnIndex(),
									cell.getStringCellValue().replace("$L{", "").replace("}", ""));
							bodyParamLabelsBgClrs.put(cell.getColumnIndex(), cloneCellStyle(latestWb,(XSSFCellStyle)cell.getCellStyle()));
						}
					}
					if (rowItemsList.isEmpty() && !bodyParamsLabels.isEmpty())
						bodyParamsLabelsMap.put(j, bodyParamsLabels);
					bodyParamsLabels = new LinkedHashMap<Integer, String>();
					if (!rowItems.isEmpty())
						rowItemsList.add(rowItems);
					rowItems = new LinkedHashMap<>();
				}
				ReportTemplateDataTo genericReportTemplateData = new ReportTemplateDataTo();
				genericReportTemplateData.setBodyParamsLabels(bodyParamsLabels);
				genericReportTemplateData.setBodyParamsLabelsMap(bodyParamsLabelsMap);
				genericReportTemplateData.setColFormulaRange(colFormulaRange);
				genericReportTemplateData.setColItemsFormula(colItemsFormula);
				genericReportTemplateData.setRowFormulaRange(rowFormulaRange);
				genericReportTemplateData.setRowItems(rowItemsFormula);
				genericReportTemplateData.setRowItemsFormula(rowItemsFormula);
				genericReportTemplateData.setRowItemsList(rowItemsList);
				genericReportTemplateData.setSecHeaderLabels(secHeaderLabels);
				genericReportTemplateData.setRowItemsBgClr(rowItemsBgClr);
				genericReportTemplateData.setColItemsFormulaBgClrs(colItemsFormulaBgClrs);
				genericReportTemplateData.setRowItemsFormulaBgClrs(rowItemsFormulaBgClrs);
				genericReportTemplateData.setSecHeaderLabelsBgClrs(secHeaderLabelsBgClrs);
				genericReportTemplateData.setBodyParamLabelsBgClrs(bodyParamLabelsBgClrs);
				genericReportTemplateData.setClmIndex(clmIndex);
				templateMap.put(sheet.getSheetName(), genericReportTemplateData);
			}
			//Data Transformation starts
			LinkedHashMap<String, List<Map<String, Object>>> newSheetData = new LinkedHashMap<String, List<Map<String, Object>>>();
			for(Map.Entry<String, List<Map<String, Object>>> sheetMap : genericExcelReportTo.getSheetDataMap().entrySet()) {
				try {
				newSheetData.put(sheetMap.getKey(), dataTranform(sheetMap.getValue(), genericExcelReportTo.getDataTransformParams().get(sheetMap.getKey())));
				}catch(Exception e) {
					newSheetData.put(sheetMap.getKey(), sheetMap.getValue());
				}
			}
			genericExcelReportTo.setSheetDataMap(newSheetData);
			//Data trasnformation end
			//Sheet preparation starts
			List<String> sheetList = new ArrayList<String>();
			for(Map.Entry<String, List<Map<String, Object>>> sheetMap : genericExcelReportTo.getSheetDataMap().entrySet()) {
				String sheetKey = sheetMap.getKey().split("_")[0];
				String sheetName = sheetMap.getKey().split("_").length > 1 ? sheetMap.getKey().split("_")[1] : sheetMap.getKey().split("_")[0];
				sheetName = sheetList.contains(sheetName) ? (sheetName+"_"+new Date().getTime()) : sheetName;
				sheetList.add(sheetName);
				XSSFSheet newSheet = null;
				try{
					newSheet = latestWb.createSheet(sheetName);
					if(lockedSheets.contains(sheetName))
						newSheet.protectSheet("");
				}catch(Exception se){
					continue;
				}
				Font font = newSheet.getWorkbook().createFont();
				//font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				CellStyle headerLabelStyle = latestWb.createCellStyle();
				headerLabelStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				headerLabelStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				headerLabelStyle.setAlignment(HorizontalAlignment.CENTER);
				headerLabelStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				headerLabelStyle.setFont(font);
				
				CellStyle sumCellStyle = latestWb.createCellStyle();
				sumCellStyle.setAlignment(HorizontalAlignment.RIGHT);
				sumCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				sumCellStyle.setFont(font);
				
				for (Map.Entry<Integer, LinkedHashMap<Integer, String>> bodyMap : templateMap.get(sheetKey).getBodyParamsLabelsMap().entrySet()) {
					XSSFRow paramRow = null;
					if (newSheet.getRow(bodyMap.getKey()) != null) {
						paramRow = newSheet.getRow(bodyMap.getKey());
					} else {
						paramRow = newSheet.createRow(bodyMap.getKey());
					}
					for (Map.Entry<Integer, String> bodyParams : bodyMap.getValue().entrySet()) {
						Cell paramRowCell = paramRow.createCell(bodyParams.getKey());
						try{
							if (templateMap.get(sheetKey).getBodyParamLabelsBgClrs()
									.containsKey(paramRowCell.getColumnIndex())) {
								paramRowCell.setCellStyle(templateMap.get(sheetKey).getBodyParamLabelsBgClrs()
										.get(paramRowCell.getColumnIndex()));
							}
						}catch(Exception csty){}
						/*if(genericExcelReportTo.getBodyParamsData()!=null)*/
							paramRowCell.setCellValue(
								(String) genericExcelReportTo.getBodyParamsData().get(bodyParams.getValue()));
					}
				}
				XSSFRow headRow = newSheet.createRow(templateMap.get(sheetKey).getBodyParamsLabelsMap().size());

				for (Map.Entry<Integer, String> rowItem : templateMap.get(sheetKey).getRowItemsList().get(0).entrySet()) {
					Cell rowCell = headRow.createCell(rowItem.getKey());
					rowCell.setCellStyle(headerLabelStyle);
					rowCell.setCellValue((String) (templateMap.get(sheetKey).getSecHeaderLabels().containsKey(rowItem.getKey())
							? templateMap.get(sheetKey).getSecHeaderLabels().get(rowItem.getKey())
							: rowItem.getValue()));
					try {
						if (templateMap.get(sheetKey).getSecHeaderLabelsBgClrs()
								.containsKey(rowCell.getColumnIndex())) {
							rowCell.setCellStyle(templateMap.get(sheetKey).getSecHeaderLabelsBgClrs()
									.get(rowCell.getColumnIndex()));
						}
					}catch(Exception e) {
						e.getMessage();
					}
				}
				int rowIndex = headRow.getRowNum() + 1;
				int colRowStartIndex = rowIndex + 1;
				int colRowStartEnd = 0;
				List<Map<String, Object>> dataList = sheetMap.getValue();
				Map<String, String> sheetDataToBeCopiedMap = new HashMap<String, String>();
				sheetDataToBeCopiedMap = genericExcelReportTo.getDataToBeCopied().get(newSheet.getSheetName());
				if(dataList.isEmpty())
					continue;
				for (Map<String, Object> rowData : dataList) {
					XSSFRow tempRow = newSheet.createRow(rowIndex);
					for (Map.Entry<Integer, String> rowItem : templateMap.get(sheetKey).getRowItemsList().get(0)
							.entrySet()) {
						Cell rowCell = tempRow.createCell(rowItem.getKey());
						try{
							if (templateMap.get(sheetKey).getRowItemsBgClr()
									.containsKey(rowCell.getColumnIndex())) {
								rowCell.setCellStyle(templateMap.get(sheetKey).getRowItemsBgClr()
										.get(rowCell.getColumnIndex()));
							}
						}catch(Exception csty){}
						try {
							rowCell.setCellValue(((BigDecimal) rowData.get(rowItem.getValue())).doubleValue());
						} catch (Exception bd) {
						try {
							rowCell.setCellValue((Long) rowData.get(rowItem.getValue()));
						} catch (Exception lng) {
							try {
									rowCell.setCellValue((Integer) (rowData.get(rowItem.getValue()) != null
											? rowData.get(rowItem.getValue())
											: rowData.get(sheetDataToBeCopiedMap.get(rowItem.getValue()))));
							} catch (Exception e) {
								try {
										rowCell.setCellValue((Double) (rowData.get(rowItem.getValue()) != null
												? rowData.get(rowItem.getValue())
												: rowData.get(sheetDataToBeCopiedMap.get(rowItem.getValue()))));
								} catch (Exception ee) {
									try {
										rowCell.setCellValue(CommonUtils
												.dateFormatter((Date) rowData.get(rowItem.getValue()), "dd-MM-yyyy"));
									} catch (Exception timeSt) {
										try {
											rowCell.setCellValue((String) genericExcelReportTo.getDataToBeTransformed()
													.get(rowItem.getValue())
													.get((String) rowData.get(rowItem.getValue())));
										} catch (Exception strTrm) {
											try {
												rowCell.setCellValue((String) rowData.get(rowItem.getValue()));
											} catch (Exception str) {
												try {
													rowCell.setCellValue((String) genericExcelReportTo
															.getDataToBeTransformed().get(rowItem.getValue())
															.get((Character) rowData.get(rowItem.getValue()) + ""));
												} catch (Exception charT) {
													try {
														rowCell.setCellValue(
																(Character) rowData.get(rowItem.getValue()) + "");
													} catch (Exception cr) {
													}
												}
											}
										}
									}
								}
							}
						}
					}
					}
					rowIndex++;
					colRowStartEnd = rowIndex;
				}
				for (Map.Entry<Integer, String> itemFormula : templateMap.get(sheetKey).getColItemsFormula().entrySet()) {
					templateMap.get(sheetKey).getColFormulaRange().put(itemFormula.getKey(),
							itemFormula.getValue() + "(" + getColumnNameByNumber(itemFormula.getKey()) + ""
									+ colRowStartIndex + ":" + getColumnNameByNumber(itemFormula.getKey()) + ""
									+ colRowStartEnd + ")");
				}
				for (Map.Entry<Integer, String> itemFormula : templateMap.get(sheetKey).getColItemsFormula().entrySet()) {
					templateMap.get(sheetKey).getRowFormulaRange().put(itemFormula.getKey(),
							itemFormula.getValue() + "(" + getColumnNameByNumber(0) + "" + 1 + ":"
									+ getColumnNameByNumber(itemFormula.getKey() - 1) + "" + (itemFormula.getKey() - 1)
									+ ")");
				}
				XSSFRow colSum = newSheet.createRow(rowIndex);
				for (Map.Entry<Integer, String> itemFormula : templateMap.get(sheetKey).getColFormulaRange().entrySet()) {
					Cell rowCell = colSum.createCell(itemFormula.getKey());
					rowCell.setCellStyle(sumCellStyle);
					try {
						if (templateMap.get(sheetKey).getColItemsFormulaBgClrs()
								.containsKey(rowCell.getColumnIndex())) {
							rowCell.setCellStyle(templateMap.get(sheetKey).getColItemsFormulaBgClrs()
									.get(rowCell.getColumnIndex()));
						}
					}catch(Exception e) {
						e.getMessage();
					}
					rowCell.setCellFormula(itemFormula.getValue());
				}
				for (Map.Entry<Integer, String> rowItem : templateMap.get(sheetKey).getRowItemsFormula().entrySet()) {
					XSSFRow rowSum = newSheet.getRow(colRowStartIndex - 2);
					Cell rowCell = rowSum.createCell(rowItem.getKey());
					rowCell.setCellValue("Total");
					rowCell.setCellStyle(headerLabelStyle);
					try {
						if (templateMap.get(sheetKey).getRowItemsFormulaBgClrs()
								.containsKey(rowCell.getColumnIndex())) {
							rowCell.setCellStyle(templateMap.get(sheetKey).getRowItemsFormulaBgClrs()
									.get(rowCell.getColumnIndex()));
						}
					}catch(Exception e) {
						e.getMessage();
					}
					for (int k = colRowStartIndex - 1; k < colRowStartEnd; k++) {
						rowSum = newSheet.getRow(k);
						rowCell = rowSum.createCell(rowItem.getKey());
						rowCell.setCellStyle(sumCellStyle);
						rowCell.setCellFormula(rowItem.getValue() + "(" + getColumnNameByNumber(0) + "" + (k + 1) + ":"
								+ getColumnNameByNumber(rowItem.getKey() - 1) + "" + (k + 1) + ")");
					}
				}
				copyConditionalFormatting(newSheet, sheetCf.get(newSheet.getSheetName()),colRowStartIndex,colRowStartEnd);
				//Merging regions if available
				for(CellRangeAddress cra : mergedRegionsMap.get(newSheet.getSheetName())) {
					newSheet.addMergedRegion(cra);  
				}
			}
			
			wb.close();
		} catch (Exception e) {
			
		}
		return latestWb;
	}
	public List<Map<String, Object>> dataTranform(List<Map<String, Object>> sheetData,
			DataTransformParamsTo dataTransformParamsTo) {
		LinkedHashMap<String, Map<String, Object>> finalData = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> compFldMap = new HashMap<String, Map<String, Object>>();
		String uniqKey = "";
		for (Map<String, Object> data : sheetData) {
			Map<String, Object> rMap = new HashMap<String, Object>();
			Map<String, Object> rowComMap = new HashMap<String, Object>();
			data.forEach((k,v)->{
				if(!dataTransformParamsTo.getCompareFiled().contains(k))
					rMap.put(k, v);
			});
			for (String commFiled : dataTransformParamsTo.getKeyFileds()) {
				if (uniqKey.isEmpty())
					uniqKey = uniqKey + data.get(commFiled);
				else
					uniqKey = uniqKey + ":" + data.get(commFiled) + "";
			}
			for (String compFlds : dataTransformParamsTo.getCompareFiled()) {
				rowComMap.put((data.get(dataTransformParamsTo.getTypeFld())+"_"+compFlds).toLowerCase(), data.get(compFlds));
			}
			compFldMap.put(uniqKey+":"+data.get(dataTransformParamsTo.getTypeFld()),rowComMap);
			finalData.put(uniqKey, rMap);
			uniqKey = "";
		}
		for (Map.Entry<String, Map<String, Object>> finalMap : finalData.entrySet()) {
			for(String sourceType : dataTransformParamsTo.getSourceTypes()) {
				if(compFldMap.containsKey(finalMap.getKey()+":"+sourceType))
					finalMap.getValue().putAll(compFldMap.get(finalMap.getKey()+":"+sourceType));
			}
		}
		System.out.println(finalData);
		return new ArrayList<>(finalData.values());
	}
	
	public XSSFCellStyle cloneCellStyle(XSSFWorkbook latestWb,XSSFCellStyle source) {
		try {
			if (source instanceof XSSFCellStyle) {
				XSSFCellStyle newStyle = (XSSFCellStyle) latestWb.createCellStyle();
				newStyle.cloneStyleFrom(source);
				return newStyle;
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return source;
	}
	

	public void copyConditionalFormatting(XSSFSheet destSheet, SheetConditionalFormatting cf,Integer startIndex, Integer endIndex) {
		int numCF = cf.getNumConditionalFormattings();
		SheetConditionalFormatting sheetCF = destSheet.getSheetConditionalFormatting();
		for (int i = 0; i < numCF; i++) {
			ConditionalFormattingRule ruleFailed2 = sheetCF.createConditionalFormattingRule(cf.getConditionalFormattingAt(i).getRule(0).getFormula1());
			ruleFailed2.createPatternFormatting().setFillBackgroundColor(cf.getConditionalFormattingAt(i).getRule(0).getPatternFormatting().getFillBackgroundColor());
			ruleFailed2.getPatternFormatting().setFillPattern(cf.getConditionalFormattingAt(i).getRule(0).getPatternFormatting().getFillPattern());
			ConditionalFormattingRule[] cfRules = new ConditionalFormattingRule[] {ruleFailed2};
			CellRangeAddress[] regions =  changeRangeFromAndTo(cf.getConditionalFormattingAt(i).getFormattingRanges(), startIndex, endIndex);
			sheetCF.addConditionalFormatting(regions,cfRules);
		}
	}
	
	public CellRangeAddress[] changeRangeFromAndTo(CellRangeAddress[] crds, Integer startIndex, Integer endIndex) {
		CellRangeAddress[] regions = new CellRangeAddress[crds.length];
		for (int i = 0; i < crds.length; i++) {
			crds[i].setFirstRow(1);
			crds[i].setLastRow(endIndex-1);
			regions[i] = crds[i];
		}
		return regions;
	}
	
}
