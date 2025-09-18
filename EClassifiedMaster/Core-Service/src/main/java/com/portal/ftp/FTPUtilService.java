package com.portal.ftp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.basedao.IBaseDao;
import com.portal.clf.models.ErpClassifieds;
import com.portal.constants.GeneralConstants.SettingType;
import com.portal.erp.constants.EtlGeneralConstants;
import com.portal.erp.entities.FTPFilesLogs;
import com.portal.erp.to.ApiResponse;
import com.portal.erp.to.ErpDataRequestPayload;
import com.portal.erp.to.FTPConnectionDetails;
import com.portal.reports.utility.CommonUtils;
import com.portal.setting.dao.SettingDao;

@Service
@Transactional
public class FTPUtilService {

	private static final Logger logger = Logger.getLogger(FTPUtilService.class);

	@Autowired
	private FTPService ftpService;

	@Autowired(required = true)
	private SettingDao settingDao;

//	@Autowired
//	private AwsS3StorageService s3StorageService;
//		
	@Autowired(required = true)
	private IBaseDao baseDao;

	private XSSFWorkbook workbook;

	private static final String source_file_path = "/inbound";
	private static final String success_file_path = "/outbound";
	private static final String error_file_path = "error";
	private static final String archive_file_path = "/archive";

	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@SuppressWarnings({ "unused", "unchecked" })
	public boolean processPortalDataToErp(List<ErpDataRequestPayload> erpDataList) {
		boolean processFlag = true;
		String METHOD_NAME = "processPortalDataToErp";
		logger.info("Entered into the method: " + METHOD_NAME);
		FTPConnectionDetails ftpConnectionDetails = new FTPConnectionDetails();
		try {
			Map<String, String> ftpSettings = this.getFTPSettings();
			for (ErpDataRequestPayload erpData : erpDataList) {
				String path = "";
				if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftpSettings.get(EtlGeneralConstants.FTP_TYPE)))
					path = ftpSettings.get(EtlGeneralConstants.FTP_SERVER_FOLDER_PATH);
				else
					path = ftpSettings.get(EtlGeneralConstants.FTP_FOLDER_PATH);
//				path = path.replace(":"+EtlGeneralConstants.FTP_FOLDER_TYPE, EtlGeneralConstants.FTP_PORTAL_FOLDER);
				path = path + source_file_path;
				String fileName = erpData.getAction().toLowerCase() + "_"
						+ CommonUtils.dateFormatter(new Date(), "yyyyMMddHHmmss") + ".xlsx";
				ftpConnectionDetails.setFtpFilePath(path + "/" + fileName);
				erpData.setFileTrackingId(RandomStringUtils.randomAlphanumeric(8));
				Map<String, Object> erpDataMap = mapper.convertValue(erpData, new TypeReference<Map<String, Object>>() {
				});
				erpDataMap.values().removeIf(Objects::isNull);
				byte[] excelData = this.convertData(erpDataMap);
				String requestData = new ObjectMapper().writeValueAsString(excelData);
				String requestData1 = new ObjectMapper().writeValueAsString(erpDataMap);
				processFlag = this.uploadExcelToFTP(path, fileName, excelData, ftpSettings);
//					processFlag = this.uploadFTPProcessFile(path, fileName, requestData1,ftpSettings);
				Map<String, String> docDetailsMap = new HashMap<>();
				if (processFlag) {
					docDetailsMap.put(this.getDocNameForFTPLog(erpData), "SUCCESS - File Processed from Portal to FTP");
				} else {
					docDetailsMap.put(this.getDocNameForFTPLog(erpData),
							"ERROR - File does not Processed from Portal to FTP");
				}
				this.createFTPFilesLogs(new JSONObject(requestData1), docDetailsMap, ftpConnectionDetails,
						erpData.getFileTrackingId(), path);
			}
		} catch (Exception e) {
			processFlag = false;
			Map<String, String> docDetailsMap = new HashMap<>();
			docDetailsMap.put("FILE", "File not found");
//			this.createFTPFilesLogs(new JSONObject((Map) erpDataList) ,docDetailsMap,  ftpConnectionDetails, "");
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return processFlag;
	}

	private boolean uploadExcelToFTP(String path, String fileName, byte[] excelData, Map<String, String> ftpSettings) {
		String METHOD_NAME = "uploadFTPProcessFile";
		logger.info("Entered into the method: " + METHOD_NAME);
		boolean isUploaded = false;
		try {
			FTPConnectionDetails ftpConDetails = new FTPConnectionDetails();
			ftpConDetails.setFtpType(ftpSettings.get(EtlGeneralConstants.FTP_TYPE));
			ftpService.setFtpServer(ftpSettings, ftpConDetails);
			if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftpConDetails.getFtpType()))
				isUploaded = ftpService.ftpServerUploading(path, fileName, excelData, ftpConDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return isUploaded;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private byte[] convertData(Map<String, Object> erpDataMap) throws IOException {
		String METHOD_NAME = "convertData";
		logger.info("Entered into the method: " + METHOD_NAME);
		List<Map<String, Object>> objData = new ArrayList<Map<String, Object>>();
		Map<String, Object> orderDetails = new HashMap<String, Object>();
		Map<String, Object> itemDetails = new HashMap<String, Object>();
		Map<String, Object> editionDetails = new HashMap<String, Object>();
		Map<String, Object> publishDates = new HashMap<String, Object>();
		Map<String, Object> requestData = (Map<String, Object>) erpDataMap.get("requestData");
		try {
			orderDetails.put("orgId", erpDataMap.get("orgId"));
			orderDetails.put("orderId", requestData.get("adId"));
			orderDetails.put("itemId", requestData.get("itemId"));
			orderDetails.put("createdTs", requestData.get("createdTs"));
//			orderDetails.put("createdBy", requestData.get("createdBy"));
//			orderDetails.put("createdBy", "800034");
			orderDetails.put("soldToParty", requestData.get("soldToParty"));
			orderDetails.put("userTypeId", requestData.get("userTypeId"));
			orderDetails.put("userTypeIdErpRefId", requestData.get("userTypeIdErpRefId"));
			orderDetails.put("adsTypeErpRefId", requestData.get("adsTypeErpRefId"));
			orderDetails.put("classifiedAdsType", requestData.get("classifiedAdsType"));
			orderDetails.put("adsType", requestData.get("adsType"));
			orderDetails.put("customerName", requestData.get("customerName"));
			orderDetails.put("customerName2", requestData.get("customerName2"));
			orderDetails.put("houseNo", requestData.get("houseNo"));
			orderDetails.put("address1", requestData.get("address1"));
			orderDetails.put("address2", requestData.get("address2"));
			orderDetails.put("address3", requestData.get("address3"));
//			orderDetails.put("city", requestData.get("city"));
			orderDetails.put("city", requestData.get("bookingUnitStr"));
			orderDetails.put("state", requestData.get("state"));
			orderDetails.put("mobileNumber", requestData.get("mobileNumber"));
			orderDetails.put("emailId", requestData.get("emailId"));
			orderDetails.put("aadharNumber", requestData.get("aadharNumber"));
			orderDetails.put("panNumber", requestData.get("panNaumber"));
			orderDetails.put("gstNo", requestData.get("gstNo"));
			orderDetails.put("orderCreatedBy", requestData.get("createdBy"));
			orderDetails.put("changedBy", requestData.get("changedBy"));
			orderDetails.put("changedTs", requestData.get("changedTs"));
			orderDetails.put("adId", requestData.get("adId"));
			orderDetails.put("customerId", requestData.get("customerId"));
			orderDetails.put("officeLocation", requestData.get("officeLocation"));
			orderDetails.put("pinCode", requestData.get("pinCode"));
			orderDetails.put("bookingUnit", requestData.get("bookingUnit"));
			orderDetails.put("createdTime", requestData.get("createdTime"));
			orderDetails.put("orderCreatedTs", requestData.get("createdDate"));
			orderDetails.put("orderCreatedDate", requestData.get("createdDate"));
			orderDetails.put("orderIdentification", requestData.get("orderIdentification"));
			orderDetails.put("totalAmount", requestData.get("paidAmount"));
			orderDetails.put("empCode", requestData.get("empCode"));
			orderDetails.put("salesOffice", requestData.get("salesOffice"));
			orderDetails.put("roOrderId", requestData.get("roOrderId"));
			objData.add(orderDetails);

			itemDetails.put("portalId", requestData.get("adId"));
			itemDetails.put("itemId", requestData.get("itemId"));
			itemDetails.put("lineCount", requestData.get("lineCount1"));
			itemDetails.put("units", requestData.get("units"));
			itemDetails.put("classifiedAdsSubType", requestData.get("classifiedAdsSubType"));
			itemDetails.put("adsSubType", requestData.get("adsSubType"));
			itemDetails.put("adsSubTypeErpRefId", requestData.get("adsSubTypeErpRefId"));
			itemDetails.put("schemeStr", requestData.get("schemeStr"));
			itemDetails.put("scheme", requestData.get("scheme"));
			itemDetails.put("schemeErpRefId", requestData.get("schemeErpRefId"));
			itemDetails.put("keyword", requestData.get("keyword"));
			itemDetails.put("categoryStr", requestData.get("categoryStr"));
			itemDetails.put("category", requestData.get("category"));
			itemDetails.put("categoryErpRefId", requestData.get("categoryErpRefId"));
			itemDetails.put("subCategoryStr", requestData.get("subCategoryStr"));
			itemDetails.put("subCategory", requestData.get("subCategory"));
			itemDetails.put("subCategoryErpRefId", requestData.get("subCategoryErpRefId"));
			itemDetails.put("childCategoryStr", requestData.get("childCategoryStr"));
			itemDetails.put("childCategory", requestData.get("childCategory"));
			itemDetails.put("childCategoryErpRefId", requestData.get("childCategoryErpRefId"));
			itemDetails.put("categoryId",
					requestData.get("categoryErpRefId") + "" + requestData.get("subCategoryErpRefId"));
			itemDetails.put("createdBy", requestData.get("createdBy"));
			itemDetails.put("changedBy", requestData.get("changedBy"));
			itemDetails.put("createdTs", requestData.get("createdDate"));
			itemDetails.put("changedTs", requestData.get("changedTs"));
//			itemDetails.put("group", requestData.get("group"));
//			itemDetails.put("subGroup", requestData.get("subGroup"));
//			itemDetails.put("childGroup", requestData.get("childGroup"));
//			itemDetails.put("groupStr", requestData.get("groupStr"));
//			itemDetails.put("subGroupStr", requestData.get("subGroupStr"));
//			itemDetails.put("childGroupStr", requestData.get("childGroupStr"));
//			itemDetails.put("groupErpRefId", requestData.get("groupErpRefId"));
//			itemDetails.put("subGroupErpRefId", requestData.get("subGroupErpRefId"));
//			itemDetails.put("childGroupErpRefId", requestData.get("childGroupErpRefId"));
//			itemDetails.put("productHierarchy", "G0002S0004A0000010");
			itemDetails.put("productHierarchy", requestData.get("productHierarchy"));
			itemDetails.put("createdTime", requestData.get("createdTime"));

			objData.add(itemDetails);

			publishDates.put("orderId", requestData.get("adId"));
			publishDates.put("itemId", requestData.get("itemId"));
			publishDates.put("publishDates", requestData.get("publishDates"));
			objData.add(publishDates);

			editionDetails.put("orderId", requestData.get("adId"));
			editionDetails.put("itemId", requestData.get("itemId"));
			editionDetails.put("editions", requestData.get("editions"));
			editionDetails.put("editionsErpRefId", requestData.get("editionsErpRefId"));
			objData.add(editionDetails);

			workbook = this.exportToExcel(orderDetails, itemDetails, publishDates, editionDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return convertWorkbookToByteArray(workbook);
	}

	private byte[] convertWorkbookToByteArray(XSSFWorkbook workbook2) throws IOException {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			workbook.write(byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		}
	}

	private XSSFWorkbook exportToExcel(Map<String, Object> orderDetails, Map<String, Object> itemDetails,
			Map<String, Object> publishDates, Map<String, Object> editionDetails) {
		workbook = new XSSFWorkbook();
		createSheetWithData(workbook, "Header Table", orderDetails, "header");
		createSheetWithData(workbook, "Item Table", itemDetails, "item");
		createSheetWithData(workbook, "Editions", editionDetails, "edition");
		createSheetWithData(workbook, "Planned Days", publishDates, "dates");

//		try (FileOutputStream fileOut = new FileOutputStream("E:\\output.xlsx")) {
//			workbook.write(fileOut);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return workbook;

	}

	@SuppressWarnings("unchecked")
	private void createSheetWithData(XSSFWorkbook workbook2, String sheetName, Map<String, Object> orderDetails,
			String type) {
		Sheet sheet = workbook.createSheet(sheetName);

		Map<String, String> fixedHeaderFilds = new HashMap<String, String>();
		if (type.equalsIgnoreCase("header")) {
			fixedHeaderFilds = this.getHeaderFields();
		} else if (type.equalsIgnoreCase("item")) {
			fixedHeaderFilds = this.getItemFields();
		} else if (type.equalsIgnoreCase("edition")) {
			fixedHeaderFilds = this.getEditionFields();
		} else {
			fixedHeaderFilds = this.getPublishDatesFields();
		}
		Row headerRow = sheet.createRow(0);
		int colIndex = 0;
		for (String key : fixedHeaderFilds.keySet()) {
			Cell headerCell = headerRow.createCell(colIndex);
			headerCell.setCellValue(fixedHeaderFilds.get(key));
			colIndex++;
		}

		// Assuming orderDetails map contains the corresponding data
		Row dataRow = sheet.createRow(1);
		int rowIndex = sheet.getLastRowNum();
		colIndex = 0;
		if (type.equalsIgnoreCase("dates")) {
			List<String> pDates = (List<String>) orderDetails.get("publishDates");

			for (String publishDate : pDates) {
				Row newRow = sheet.createRow(rowIndex); // Create a new row for each publish date
				colIndex = 0;
				for (String key : fixedHeaderFilds.keySet()) {
					Cell dataCell = newRow.createCell(colIndex);
					Object value = orderDetails.get(key);
					if (value != null) {
						if (key.equalsIgnoreCase("publishDates")) {
							dataCell.setCellValue(publishDate.toString());
						} else {
							dataCell.setCellValue(value.toString());
						}
					}
					colIndex++;
				}

				rowIndex++; // Move to the next row for the next publish date
			}
		} else if (type.equalsIgnoreCase("edition")) {
			List<String> editions = (List<String>) orderDetails.get("editionsErpRefId");

			for (String edition : editions) {
				Row newRow = sheet.createRow(rowIndex); // Create a new row for each publish date
				colIndex = 0;
				for (String key : fixedHeaderFilds.keySet()) {
					Cell dataCell = newRow.createCell(colIndex);
					Object value = orderDetails.get(key);
					if (value != null) {
						if (key.equalsIgnoreCase("editionsErpRefId")) {
							dataCell.setCellValue(edition.toString());
						} else {
							dataCell.setCellValue(value.toString());
						}
					}
					colIndex++;
				}

				rowIndex++; // Move to the next row for the next publish date
			}
		} else {
			for (String key : fixedHeaderFilds.keySet()) {
				Cell dataCell = dataRow.createCell(colIndex);
				Object value = orderDetails.get(key);
				if (value != null) {
					dataCell.setCellValue(value.toString());
				}
				colIndex++;
			}
		}
	}

	private Map<String, String> getPublishDatesFields() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		try {
			headers.put("orderId", "Portal_id");
			headers.put("itemId", "Portal_item");
			headers.put("publishDates", "ERSCH_T");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return headers;
	}

	private Map<String, String> getEditionFields() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		try {
			headers.put("orderId", "ZPOID");
			headers.put("itemId", "ZITEM");
			headers.put("editionsErpRefId", "BELEGEINH");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return headers;
	}

	private Map<String, String> getItemFields() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		try {
			headers.put("portalId", "PORTAL_ID");
			headers.put("itemId", "PORTAL_ITEM");
			headers.put("subCategoryErpRefId", "INHK_TECH1");
			headers.put("TERM_TYP", "TERM_TYP");
			headers.put("TERM_ZYKL", "TERM_ZYKL");
			headers.put("lineCount", "BREITE_S_C");
			headers.put("BREITE_SEH_CH", "BREITE_SEH_CH");
			headers.put("HOEHE_S_CH", "HOEHE_S_CH");
			headers.put("HOEHE_S_EH_CH", "HOEHE_S_EH_CH");
			headers.put("adsSubTypeErpRefId", "AZART_FARB");
			headers.put("schemeErpRefId", "KONDA");
//			headers.put("keyword","STICHW");
			headers.put("subCategoryStr", "STICHW");
//			headers.put("childGroupErpRefId","PRODH");
			headers.put("productHierarchy", "PRODH");
			headers.put("createdTs", "ZORON");
			headers.put("createdTime", "ZORAT");
			headers.put("createdBy", "ZORBY");
			headers.put("changedBy", "ZCHBY");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return headers;
	}

	private Map<String, String> getHeaderFields() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		try {
			headers.put("orderId", "Portal_id");
			headers.put("orderIdentification", "ZORD_ID");
			headers.put("createdTs", "TIMESTAMP");
//			headers.put("createdBy", "GPAG");
			headers.put("soldToParty", "GPAG");
			headers.put("orgId", "VKORG");
//			headers.put("userTypeId", "VTWEG");
			headers.put("userTypeIdErpRefId", "VTWEG");
			headers.put("adsTypeErpRefId", "SPART");//
			headers.put("customerName", "NAME1");
			headers.put("customerName2", "NAME2");
			headers.put("houseNo", "HOUSE_NUM1");
			headers.put("address1", "STREET");
			headers.put("pinCode", "POST_CODE1");
			headers.put("city", "CITY1");//
			headers.put("CITY2", "CITY2");
			headers.put("state", "REGION");
			headers.put("mobileNo", "TEL_NUMBER");
			headers.put("emailId", "SMTP_ADDR");
			headers.put("aadharNumber", "ZADHAR");
			headers.put("panNumber", "ZPAN");
			headers.put("bookingUnit", "MAN_VKBUR");
			headers.put("adId", "BSTNK");
//			headers.put("roOrderId", "BSTNK");
			headers.put("orderCreatedDate", "BSTDK");
			headers.put("typeOfCustomer", "ZCUST_TYPE");//
			headers.put("orderCreatedTs", "ZORON");
			headers.put("createdTime", "ZORAT");
			headers.put("orderCreatedBy", "ZORBY");
			headers.put("changedBy", "ZCHBY");
			headers.put("totalAmount", "ZORD_PRICE");
			headers.put("empCode", "ZZPERNR");
			headers.put("salesOffice", "VKBUR");
			headers.put("gstNo", "ZGST_NUMBER");
			headers.put("roOrderId", "ZBILL_DESK_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return headers;
	}

	private Map<String, String> getFTPSettings() {

		String METHOD_NAME = "getFTPSettings";
		logger.info("Entered into the method: " + METHOD_NAME);
		Map<String, Object> params = new HashMap<>();
		params.put("stype", SettingType.APP_SETTING.getValue());
		params.put("grps", Arrays.asList(EtlGeneralConstants.FTP));
		Map<String, String> ftpConfigs = settingDao.getSMTPSettingValues(params).getSettings();
		if (ftpConfigs.get(EtlGeneralConstants.FTP_S3_STORAGE) != null
				&& Boolean.valueOf(ftpConfigs.get(EtlGeneralConstants.FTP_S3_STORAGE))) {
			ftpConfigs.put(EtlGeneralConstants.FTP_TYPE, EtlGeneralConstants.FTP_S3_STORAGE);
		} else if (ftpConfigs.get(EtlGeneralConstants.FTP_LOCAL) != null
				&& Boolean.valueOf(ftpConfigs.get(EtlGeneralConstants.FTP_LOCAL))) {
			ftpConfigs.put(EtlGeneralConstants.FTP_TYPE, EtlGeneralConstants.FTP_LOCAL);
		} else if (ftpConfigs.get(EtlGeneralConstants.FTP_SERVER) != null) {
			ftpConfigs.put(EtlGeneralConstants.FTP_TYPE, EtlGeneralConstants.FTP_SERVER);
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return ftpConfigs;
	}

	@SuppressWarnings("unused")
	private boolean uploadFTPProcessFile(String path, String fileName, String requestData,
			Map<String, String> ftpSettings) {

		String METHOD_NAME = "uploadFTPProcessFile";
		logger.info("Entered into the method: " + METHOD_NAME);
		boolean isUploaded = false;
		try {
			FTPConnectionDetails ftpConDetails = new FTPConnectionDetails();
			ftpConDetails.setFtpType(ftpSettings.get(EtlGeneralConstants.FTP_TYPE));
			ftpService.setFtpServer(ftpSettings, ftpConDetails);
			if (EtlGeneralConstants.FTP_S3_STORAGE.equalsIgnoreCase(ftpConDetails.getFtpType())) {
//				isUploaded = s3StorageService.uploadFileWitoutEncryption(path, fileName, requestData.getBytes());
			} else if (EtlGeneralConstants.FTP_LOCAL.equalsIgnoreCase(ftpConDetails.getFtpType()))
				isUploaded = ftpService.uploadFile(path, fileName, requestData);
			else if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftpConDetails.getFtpType()))
				isUploaded = ftpService.ftpServerUploading(path, fileName, requestData.getBytes(), ftpConDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return isUploaded;
	}

	public void createFTPFilesLogs(JSONObject jsonObject, Map<String, String> docDetails,
			FTPConnectionDetails ftpConDetails, String trackingId, String ftpFilePath) {

		try {
			if (!docDetails.isEmpty()) {
				docDetails.forEach((docNo, status) -> {
					FTPFilesLogs fTPFilesLogs = new FTPFilesLogs();
					fTPFilesLogs.setFileTrackingId(trackingId);
					fTPFilesLogs.setFtpFileLogId(UUID.randomUUID().toString());
					fTPFilesLogs.setFilePath(ftpConDetails.getFtpFilePath());
					fTPFilesLogs.setDocNo(docNo);
					fTPFilesLogs.setStatus(
							"SUCCESS".equalsIgnoreCase(status) || status.contains("SUCCESS") ? "SUCCESS" : "ERROR");
					fTPFilesLogs.setMessage(status);
					try {
						if(jsonObject != null) {
							fTPFilesLogs.setCreatedBy(jsonObject.getInt("userId"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fTPFilesLogs.setCreatedTs(new Date());
					fTPFilesLogs.setMarkAsDelete(false);
//					ftpFilesLogsList.add(fTPFilesLogs);
					baseDao.save(fTPFilesLogs);
				});
			} else {
				if (jsonObject.has("portalId") && !jsonObject.optString("portalId").isEmpty()) {
					FTPFilesLogs fTPFilesLogs = new FTPFilesLogs();
//					fTPFilesLogs.setFileTrackingId(trackingId);
					fTPFilesLogs.setFtpFileLogId(UUID.randomUUID().toString());
					fTPFilesLogs.setFilePath(ftpFilePath);
					fTPFilesLogs.setStatus("S".equalsIgnoreCase(jsonObject.get("type") + "") ? "SUCCESS" : "ERROR");
					fTPFilesLogs.setMessage(jsonObject.getString("orderStatus"));
					fTPFilesLogs.setDocNo(jsonObject.getString("portalId"));
					fTPFilesLogs.setCreatedTs(new Date());
					fTPFilesLogs.setMarkAsDelete(false);
					baseDao.save(fTPFilesLogs);
				}
				if (jsonObject.has("clientcode") && !jsonObject.optString("clientcode").isEmpty()) {
					FTPFilesLogs fTPFilesLogs = new FTPFilesLogs();
//					fTPFilesLogs.setFileTrackingId(trackingId);
					fTPFilesLogs.setFtpFileLogId(UUID.randomUUID().toString());
					fTPFilesLogs.setFilePath(ftpFilePath);
//					fTPFilesLogs.setStatus("S".equalsIgnoreCase(jsonObject.get("type") + "") ? "SUCCESS" : "ERROR");
//					fTPFilesLogs.setMessage(jsonObject.getString("orderStatus"));
					fTPFilesLogs.setDocNo(jsonObject.getString("clientcode"));
					fTPFilesLogs.setCreatedTs(new Date());
					fTPFilesLogs.setMarkAsDelete(false);
					baseDao.save(fTPFilesLogs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getDocNameForFTPLog(ErpDataRequestPayload erpData) {
		String docNum = "";
		try {
			ErpClassifieds reqDataList = erpData.getRequestData();

			if (reqDataList.getAdId() != null) {
				docNum = reqDataList.getAdId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !"".equalsIgnoreCase(docNum) ? docNum : "FILE";
	}

	public ApiResponse ftpApiCall(String filePath) {

		String METHOD_NAME = "ftpApiCall";
		logger.info("Entered into the method: " + METHOD_NAME);
		ApiResponse apiResponse = new ApiResponse();
		try {
			Map<String, String> ftp = this.getFTPSettings();
			FTPConnectionDetails ftpConDetails = new FTPConnectionDetails();
			ftpConDetails.setFtpType(ftp.get(EtlGeneralConstants.FTP_TYPE));
			ftpConDetails.setFtpFilePath(filePath);
			InputStream fStream = null;
			if (EtlGeneralConstants.FTP_S3_STORAGE.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {
//				fStream = s3StorageService.readFile(filePath);
			} else if (EtlGeneralConstants.FTP_LOCAL.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {
//				fStream = ftpService.readFile(filePath);
			} else if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {

//				filePath = filePath.replace(ftp.get(EtlGeneralConstants.FTP_PARENT_PATH), "");
				filePath = filePath + success_file_path;
				ftpConDetails.setFtpFilePath(filePath);

				ftpService.setFtpServer(ftp, ftpConDetails);
				// ftpService.setFtpEncriptConnection(ftpDet);
				apiResponse = ftpService.ftpServerFile(ftpConDetails);
			}

		} catch (Exception e) {
			logger.error("Error while connecting FTP/ File Fetching : " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResponse;
	}
	
	public ApiResponse ftpRmsApiCall(String filePath) {

		String METHOD_NAME = "ftpApiCall";
		logger.info("Entered into the method: " + METHOD_NAME);
		ApiResponse apiResponse = new ApiResponse();
		try {
			Map<String, String> ftp = this.getFTPSettings();
			FTPConnectionDetails ftpConDetails = new FTPConnectionDetails();
			ftpConDetails.setFtpType(ftp.get(EtlGeneralConstants.FTP_TYPE));
			ftpConDetails.setFtpFilePath(filePath);
			InputStream fStream = null;
			if (EtlGeneralConstants.FTP_S3_STORAGE.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {
//				fStream = s3StorageService.readFile(filePath);
			} else if (EtlGeneralConstants.FTP_LOCAL.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {
//				fStream = ftpService.readFile(filePath);
			} else if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {

//				filePath = filePath.replace(ftp.get(EtlGeneralConstants.FTP_PARENT_PATH), "");
				filePath = filePath + success_file_path;
				ftpConDetails.setFtpFilePath(filePath);

				ftpService.setFtpServer(ftp, ftpConDetails);
				// ftpService.setFtpEncriptConnection(ftpDet);
				apiResponse = ftpService.ftpRmsServerFile(ftpConDetails);
			}

		} catch (Exception e) {
			logger.error("Error while connecting FTP/ File Fetching : " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResponse;
	}


	@SuppressWarnings("unused")
	public Boolean deleteSourceFile(FTPConnectionDetails ftpConDetails, FTPFile file) {
		String METHOD_NAME = "deleteSourceFile";
		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isDeleted = false;
		if (EtlGeneralConstants.FTP_S3_STORAGE.equalsIgnoreCase(ftpConDetails.getFtpType())) {

//			isDeleted = s3StorageService.deleteFile(ftpConDetails.getFtpFilePath());

		} else if (EtlGeneralConstants.FTP_LOCAL.equalsIgnoreCase(ftpConDetails.getFtpType())) {

//			isDeleted = ftpService.deleteFile(ftpConDetails.getFtpFilePath());

		} else if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftpConDetails.getFtpType())) {

			isDeleted = ftpService.ftpServerDelete(ftpConDetails, file);
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return isDeleted;
	}

	public boolean uploadApiResponse(InputStream inputSteam, FTPConnectionDetails ftpConDetails, FTPFile file)
			throws IOException {

		String METHOD_NAME = "uploadApiResponse";
		logger.info("Entered into the method: " + METHOD_NAME);
		byte[] byteArray = IOUtils.toByteArray(inputSteam);
		boolean isUploaded = false;
		String path = ftpConDetails.getFtpFilePath();
		if (ftpConDetails.getFtpFilePath().contains(source_file_path))
			path = ftpConDetails.getFtpFilePath() + archive_file_path;
		else if (ftpConDetails.getFtpFilePath().contains(success_file_path))
			path = ftpConDetails.getFtpFilePath().replace("/outbound", "/archive");
//			path = ftpConDetails.getFtpFilePath() + archive_file_path;
		else if (ftpConDetails.getFtpFilePath().contains(error_file_path))
			path = ftpConDetails.getFtpFilePath() + archive_file_path;
		String fileName = "Archive" + file.getName();
		if (EtlGeneralConstants.FTP_S3_STORAGE.equalsIgnoreCase(ftpConDetails.getFtpType())) {

//			isUploaded = s3StorageService.uploadFileWitoutEncryption(path, fileName, response.getBytes());

		} else if (EtlGeneralConstants.FTP_LOCAL.equalsIgnoreCase(ftpConDetails.getFtpType())) {

//			isUploaded = ftpService.uploadFile(path, fileName, response);

		} else if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftpConDetails.getFtpType())) {

			isUploaded = ftpService.ftpServerUploading(path, fileName, byteArray, ftpConDetails);
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return isUploaded;
	}

	public boolean sendTransactionDetailsToErp(List<Object[]> transactionDetails) throws IOException {
		String METHOD_NAME = "sendTransactionDetailsToErp";
		logger.info("Entered into the method: " + METHOD_NAME);
		Map<String, Object> paymentDetails = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
		boolean processFlag = false;
		try {
			FTPConnectionDetails ftpConnectionDetails = new FTPConnectionDetails();
			Map<String, String> ftpSettings = this.getFTPSettings();
			for (Object[] obj : transactionDetails) {
				workbook = new XSSFWorkbook();
				String path = "";
				if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftpSettings.get(EtlGeneralConstants.FTP_TYPE)))
					path = ftpSettings.get("FTP_PAYMENT_SERVER_FOLDER_PATH");
				else
					path = ftpSettings.get(EtlGeneralConstants.FTP_FOLDER_PATH);
				path = path + source_file_path;
				String fileName = "Payments" + "_" + CommonUtils.dateFormatter(new Date(), "yyyyMMddHHmmss") + ".xlsx";
				ftpConnectionDetails.setFtpFilePath(path + "/" + fileName);
				paymentDetails.put("secOrderId", obj[0]);
				paymentDetails.put("gateWayId", obj[1]);
				paymentDetails.put("orderId", obj[3]);
				Date date = sdf.parse(obj[4]+"");
				String formatedDate = dateformat.format(date);
				String formatedTime = timeFormat.format(date);
//				Date time = timeFormat.parse(obj[4]+"");
				paymentDetails.put("transactionDate", formatedDate);
				paymentDetails.put("transactionTime", formatedTime);
//				paymentDetails.put("transactionDate", obj[4]);
				paymentDetails.put("paymentMethodType", obj[5]);
				paymentDetails.put("amount", obj[6]);
				paymentDetails.put("paymentStatus", obj[7]);
				paymentDetails.put("bankRefNo", obj[8]);
				paymentDetails.put("transactionId", obj[9]);
				paymentDetails.put("txnProcessType", obj[10]);
				paymentDetails.put("profitCenter", obj[12]);
				paymentDetails.put("giAccount", obj[13]);
				paymentDetails.put("roOrderId", obj[3]);

				workbook = this.generateExcelForPaymentDetails(workbook, paymentDetails);
//				try (FileOutputStream fileOut = new FileOutputStream("E:\\payment.xlsx")) {
//					workbook.write(fileOut);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				byte[] byteArray = convertWorkbookToByteArray(workbook);
				processFlag = this.uploadExcelToFTP(path, fileName, byteArray, ftpSettings);
				
				Map<String, String> docDetailsMap = new HashMap<>();
				ErpDataRequestPayload erpData = new ErpDataRequestPayload();
				ErpClassifieds erpClf = new ErpClassifieds();
				erpClf.setAdId((String) obj[0]);
				erpData.setRequestData(erpClf);
				if (processFlag) {
					docDetailsMap.put(this.getDocNameForFTPLog(erpData), "SUCCESS - File Processed from Portal to FTP");
				} else {
					docDetailsMap.put(this.getDocNameForFTPLog(erpData),
							"ERROR - File does not Processed from Portal to FTP");
				}
				this.createFTPFilesLogs(null, docDetailsMap, ftpConnectionDetails,
						"", path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return processFlag;
	}

	private XSSFWorkbook generateExcelForPaymentDetails(XSSFWorkbook workbook, Map<String, Object> paymentDetails) {
		LinkedHashMap<String, String> paymentMap = new LinkedHashMap<String, String>();
		try {
			Sheet sheet = workbook.createSheet("payment");

			paymentMap.put("secOrderId", "sec_order_id");
			paymentMap.put("transactionDate", "transaction_date");
			paymentMap.put("transactionTime", "transaction_time");
			paymentMap.put("gateWayId", "gate_way_id");
			paymentMap.put("amount", "amount");
			paymentMap.put("paymentMethodType", "payment_method_type");
			paymentMap.put("paymentStatus", "payment_status");
			paymentMap.put("transactionId", "transactionid");
			paymentMap.put("txnProcessType", "txn_process_type");
			paymentMap.put("bankRefNo", "bank_ref_no");
			paymentMap.put("profitCenter", "PRCTR");
			paymentMap.put("giAccount", "HKONT");
//			paymentMap.put("roOrderId", "BSTNK");
			paymentMap.put("roOrderId", "ZBILL_DESK_ID");

			Row headerRow = sheet.createRow(0);
			int colIndex = 0;
			for (String key : paymentMap.keySet()) {
				Cell headerCell = headerRow.createCell(colIndex);
				headerCell.setCellValue(paymentMap.get(key));
				colIndex++;
			}
			Row dataRow = sheet.createRow(1);
			colIndex = 0;
			for (String key : paymentMap.keySet()) {
				Cell dataCell = dataRow.createCell(colIndex);
				Object value = paymentDetails.get(key);
				if (value != null) {
					dataCell.setCellValue(value.toString());
				}
				colIndex++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public boolean processRmsDataToErp(List<ErpDataRequestPayload> erpDataList) {
		boolean processFlag = true;
		String METHOD_NAME = "processPortalDataToErp";
		logger.info("Entered into the method: " + METHOD_NAME);
		FTPConnectionDetails ftpConnectionDetails = new FTPConnectionDetails();
		try {
			Map<String, String> ftpSettings = this.getFTPSettings();
			for (ErpDataRequestPayload erpData : erpDataList) {
				String path = "";
				if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftpSettings.get(EtlGeneralConstants.FTP_TYPE)))
					path = ftpSettings.get(EtlGeneralConstants.FTP_RMS_SERVER_FOLDER_PATH);
				else
					path = ftpSettings.get(EtlGeneralConstants.FTP_FOLDER_PATH);
//				path = path.replace(":"+EtlGeneralConstants.FTP_FOLDER_TYPE, EtlGeneralConstants.FTP_PORTAL_FOLDER);
				path = path + source_file_path;
				String fileName = erpData.getAction().toLowerCase() + "_"
						+ CommonUtils.dateFormatter(new Date(), "yyyyMMddHHmmss") + ".json";
				ftpConnectionDetails.setFtpFilePath(path + "/" + fileName);
				erpData.setFileTrackingId(RandomStringUtils.randomAlphanumeric(8));
				Map<String, Object> erpDataMap = mapper.convertValue(erpData, new TypeReference<Map<String, Object>>() {
				});
				erpDataMap.values().removeIf(Objects::isNull);
				Map<String,Object> requestDetails = this.convertRmsData(erpDataMap);
				String requestData = new ObjectMapper().writeValueAsString(requestDetails);
				processFlag = this.uploadFTPProcessFile(path, fileName, requestData, ftpSettings);
				Map<String, String> docDetailsMap = new HashMap<>();
				if (processFlag) {
					docDetailsMap.put(this.getDocNameForFTPLog(erpData),
							"SUCCESS - File Processed from Rms Portal to FTP");
				} else {
					docDetailsMap.put(this.getDocNameForFTPLog(erpData),
							"ERROR - File does not Processed from Rms Portal to FTP");
				}
				this.createFTPFilesLogs(new JSONObject(requestData), docDetailsMap, ftpConnectionDetails,
						erpData.getFileTrackingId(), path);
			}
		} catch (Exception e) {
			processFlag = false;
			Map<String, String> docDetailsMap = new HashMap<>();
			docDetailsMap.put("FILE", "File not found");
//			this.createFTPFilesLogs(new JSONObject((Map) erpDataList) ,docDetailsMap,  ftpConnectionDetails, "");
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return processFlag;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> convertRmsData(Map<String, Object> erpDataMap) {
		String METHOD_NAME = "convertRmsData";
		logger.info("Entered into the method: " + METHOD_NAME);
		Map<String, Object> itemDetails = new HashMap<String, Object>();
		Map<String, Object> headerDetails = new HashMap<String,Object>();
		Map<String, Object> editions = new HashMap<String, Object>();
		Map<String, Object> publishDates = new HashMap<String, Object>();
		Map<String, Object> requestData = new HashMap<String, Object>();
		Map<String, Object> requestDataDetails = new HashMap<>();
		List<Map<String, Object>> requestDataList = new ArrayList<>();
		Map<String, Object> request = (Map<String, Object>) erpDataMap.get("requestData");
		try {
			headerDetails.put("portalid", request.get("adId"));
			headerDetails.put("ordid", request.get("orderIdentification"));
			headerDetails.put("timestamp", request.get("createdTs"));
			headerDetails.put("customer", request.get("soldToParty"));
			headerDetails.put("salesorg", erpDataMap.get("orgId"));
			headerDetails.put("distchannel", request.get("userTypeIdErpRefId"));
			headerDetails.put("divison", request.get("adsTypeErpRefId"));
			headerDetails.put("name", request.get("customerName"));
			headerDetails.put("houseno", request.get("houseNo"));
			headerDetails.put("street", request.get("address1"));
			headerDetails.put("postcode", request.get("pinCode"));
			headerDetails.put("city1", request.get("city"));
			headerDetails.put("region", request.get("state"));
			headerDetails.put("telnum", request.get("mobileNo"));
			headerDetails.put("mobalt", request.get("mobileAlt"));
			headerDetails.put("mail", request.get("emailId"));
			headerDetails.put("adhar", request.get("aadharNumber"));
			headerDetails.put("pan", request.get("panNumber"));
			headerDetails.put("bookofc", request.get("bookingCode"));
			headerDetails.put("ronum", request.get("adId"));
			headerDetails.put("rodate", request.get("createdDate"));
			headerDetails.put("custtype", request.get("typeOfCustomer"));
			headerDetails.put("createdon", request.get("createdDate"));
			headerDetails.put("createdat", request.get("createdTime"));
			headerDetails.put("createdby", request.get("createdBy"));
			headerDetails.put("changedby", request.get("changedBy"));
			headerDetails.put("ordprice", request.get("paidAmount"));
			headerDetails.put("persno", request.get("empCode"));
			headerDetails.put("salesofc", request.get("salesOffice"));
			headerDetails.put("gstno", request.get("gstNo"));
			headerDetails.put("portalitem", request.get("itemId"));
			headerDetails.put("statecode", request.get("stateCode"));
			headerDetails.put("customer", request.get("isCustomerNew"));
			requestData.put("headerdetails", headerDetails);
			
			itemDetails.put("portalid", request.get("adId"));
			itemDetails.put("portalitem", request.get("itemId"));
			itemDetails.put("techcc", request.get("pageNumberErpRefId"));
			itemDetails.put("adwidth", request.get("sizeWidth"));
			itemDetails.put("adheight", request.get("sizeHeight"));
			itemDetails.put("cscheme", request.get("adsSubTypeErpRefId"));
			itemDetails.put("konda", request.get("schemeErpRefId"));
			itemDetails.put("keyword", request.get("content"));
			itemDetails.put("prodhr", request.get("childGroupErpRefId"));
			itemDetails.put("fixformat", request.get("fixedFormatErpRefId"));
//			itemDetails.put("posin", request.get("pagePosition"));
			itemDetails.put("posin", request.get("pagePositionErpRefId"));
			itemDetails.put("perdisc", request.get("additionalDiscount"));
			itemDetails.put("persur", request.get("premiumDiscount"));
			itemDetails.put("createdon", request.get("createdDate"));
			itemDetails.put("createdat", request.get("createdTime"));
			itemDetails.put("createdby", request.get("createdBy"));
			itemDetails.put("changedby", request.get("changedBy"));
			itemDetails.put("multidisedicount", request.get("multiDiscountEdiCount"));
			itemDetails.put("multidisper", request.get("multiDiscount"));
			itemDetails.put("adtypeerpcode", request.get("adTypeErpRefId"));
			requestData.put("itemdetails", itemDetails);

			editions.put("bookunit", request.get("editionsErpRefId"));
			editions.put("portalid", request.get("adId"));
			editions.put("portalitem", request.get("itemId"));
//			editions.put("editions", request.get("editions"));
			requestData.put("editions", editions);

			publishDates.put("pubdate", request.get("publishDates"));
			publishDates.put("portalid", request.get("adId"));
			publishDates.put("portalitem", request.get("itemId"));
			requestData.put("pubdays", publishDates);
			
			requestDataDetails.put("orgId", erpDataMap.get("orgId"));
			requestDataDetails.put("userId", erpDataMap.get("userId"));
			requestDataDetails.put("action", erpDataMap.get("erpDataMap"));
			requestDataDetails.put("fileTrackingId", erpDataMap.get("fileTrackingId"));
//			requestDataDetails.put("requestdata", requestData);

			requestDataList.add(requestData);
			requestDataDetails.put("requestdata", requestDataList);
			System.out.println(requestDataDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestDataDetails;
	}
	
	public boolean sendRmsTransactionDetailsToErp(List<Object[]> transactionDetails,boolean paymentFlag) throws IOException {
		String METHOD_NAME = "sendTransactionDetailsToErp";
		logger.info("Entered into the method: " + METHOD_NAME);
		Map<String, Object> paymentDetails = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
		boolean processFlag = false;
		try {
			FTPConnectionDetails ftpConnectionDetails = new FTPConnectionDetails();
			Map<String, String> ftpSettings = this.getFTPSettings();
			for (Object[] obj : transactionDetails) {
				String path = "";
				if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftpSettings.get(EtlGeneralConstants.FTP_TYPE)))
					path = ftpSettings.get("FTP_RMS_PAYMENT_SERVER_FOLDER_PATH");
				else
					path = ftpSettings.get(EtlGeneralConstants.FTP_FOLDER_PATH);
				path = path + source_file_path;
				String fileName = "Payments" + "_" + CommonUtils.dateFormatter(new Date(), "yyyyMMddHHmmss") + ".json";
				ftpConnectionDetails.setFtpFilePath(path + "/" + fileName);
				
				if (paymentFlag) {
					
					paymentDetails.put("secOrderId", obj[0]);
					paymentDetails.put("gateWayId", obj[1]);
					paymentDetails.put("orderId", obj[3]);
					Date date = sdf.parse(obj[4]+"");
					String formatedDate = dateformat.format(date);
					String formatedTime = timeFormat.format(date);
//					Date time = timeFormat.parse(obj[4]+"");
					paymentDetails.put("transactionDate", formatedDate);
					paymentDetails.put("transactionTime", formatedTime);
//					paymentDetails.put("transactionDate", obj[4]);
					paymentDetails.put("paymentMethodType", obj[5]);
					paymentDetails.put("amount", obj[6]);
					paymentDetails.put("paymentStatus", obj[7]);
					paymentDetails.put("bankRefNo", obj[8]);
					paymentDetails.put("transactionId", obj[9]);
					paymentDetails.put("txnProcessType", obj[10]);
					paymentDetails.put("profitCenter", obj[12]);
					paymentDetails.put("giAccount", obj[13]);
					paymentDetails.put("roOrderId", obj[3]);
					
					
					
//					paymentDetails.put("ad_id", obj[0]);
//					paymentDetails.put("gate_way_id", obj[1]);
//					paymentDetails.put("order_id", obj[2]);
//					
//					paymentDetails.put("bank_branch", obj[4]);
//					paymentDetails.put("bank_ref_id", obj[5]);
//					paymentDetails.put("bank_or_upi", obj[6]);
//					paymentDetails.put("cash_receipt_no", obj[7]);
//					paymentDetails.put("other_details", obj[8]);
//					paymentDetails.put("payment_mode", obj[9]);
//					paymentDetails.put("payment_method", obj[10]);
//					paymentDetails.put("booking_unit", obj[12]);
//					paymentDetails.put("profit_center", obj[13]);
//					paymentDetails.put("gi_account", obj[14]);
				} else {
					paymentDetails.put("ad_id", obj[0]);
					paymentDetails.put("order_id", obj[1]);
					paymentDetails.put("item_id", obj[2]);
//					paymentDetails.put("payment_mode", obj[3]);
//					paymentDetails.put("payment_method", obj[4]);
					paymentDetails.put("booking_unit", obj[5]);
					paymentDetails.put("profit_center", obj[6]);
					paymentDetails.put("gi_account", obj[7]);
					paymentDetails.put("paymentMode", obj[8]);
					paymentDetails.put("paymentMethod", obj[9]);
				}
				
//				paymentDetails.values().removeIf(Objects::isNull);
				String requestData = new ObjectMapper().writeValueAsString(paymentDetails);
				processFlag = this.uploadFTPProcessFile(path, fileName, requestData, ftpSettings);

//				workbook = this.generateExcelForPaymentDetails(workbook, paymentDetails);
//				byte[] byteArray = convertWorkbookToByteArray(workbook);
//				processFlag = this.uploadExcelToFTP(path, fileName, byteArray, ftpSettings);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return processFlag;
	}
	
	public ApiResponse ftpRmsCustomerApiCall(String filePath) {

		String METHOD_NAME = "ftpApiCall";
		logger.info("Entered into the method: " + METHOD_NAME);
		ApiResponse apiResponse = new ApiResponse();
		try {
			Map<String, String> ftp = this.getFTPSettings();
			FTPConnectionDetails ftpConDetails = new FTPConnectionDetails();
			ftpConDetails.setFtpType(ftp.get(EtlGeneralConstants.FTP_TYPE));
			ftpConDetails.setFtpFilePath(filePath);
			InputStream fStream = null;
			if (EtlGeneralConstants.FTP_S3_STORAGE.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {
//				fStream = s3StorageService.readFile(filePath);
			} else if (EtlGeneralConstants.FTP_LOCAL.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {
//				fStream = ftpService.readFile(filePath);
			} else if (EtlGeneralConstants.FTP_SERVER.equalsIgnoreCase(ftp.get(EtlGeneralConstants.FTP_TYPE))) {

//				filePath = filePath.replace(ftp.get(EtlGeneralConstants.FTP_PARENT_PATH), "");
				filePath = filePath + success_file_path;
				ftpConDetails.setFtpFilePath(filePath);

				ftpService.setFtpServer(ftp, ftpConDetails);
				// ftpService.setFtpEncriptConnection(ftpDet);
				apiResponse = ftpService.ftpRmsCustomerServerFile(ftpConDetails);
			}

		} catch (Exception e) {
			logger.error("Error while connecting FTP/ File Fetching : " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResponse;
	}

}
