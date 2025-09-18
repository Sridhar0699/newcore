package com.portal.erp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.clf.models.ErpClassifieds;
import com.portal.erp.to.ErpDataRequestPayload;
import com.portal.ftp.FTPUtilService;

@Service
public class ErpService {
	
	private static final Logger logger = Logger.getLogger(ErpService.class);
	
	@Autowired
	private FTPUtilService ftpUtilService;
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public boolean processClassifiedCreationFtpFiles(Map<String, Object> payloadJson,
			Map<String, ErpClassifieds> erpClassifieds) {
		logger.info(":: START SyncToErp Vendor Data :: ");
		String METHOD_NAME = "processClassifiedCreationFtpFiles";
		logger.info("Entered into the method :: " + METHOD_NAME);
		boolean flag = true;
		try {
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			List<String> orgIds = new ArrayList<String>();
				List<ErpDataRequestPayload> erpDataList = new ArrayList<ErpDataRequestPayload>();
				erpClassifieds.entrySet().forEach(orgVendorData -> {
					ErpDataRequestPayload payload = new ErpDataRequestPayload();
					payload.setOrgId(payloadJson.get("orgId")+"");
					payload.setOrgOpuId(payloadJson.containsKey("orgOpuId")  && payloadJson.get("orgOpuId") != null ? payloadJson.get("orgOpuId")+"" : "");
					payload.setRequestData(orgVendorData.getValue());
					payload.setAction(payloadJson.get("action")+"");
					payload.setServiceType(payloadJson.get("serviceType")+"");
					payload.setUserId(payloadJson.get("userId")+"");
					erpDataList.add(payload);
				});
				flag = ftpUtilService.processPortalDataToErp(erpDataList);
				if (flag) {
					System.out.println("Success");
				}
				else {
					System.out.println("Error");
				}
				
		} catch (Exception e) {
			flag = false;
			logger.error("Error while processing vendor creation FTP files data :: " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		logger.info("Exit from the method :: " + METHOD_NAME);
		return flag;
	}

	public boolean processRmsCreationFtpFiles(Map<String, Object> payloadJson,
			Map<String, ErpClassifieds> erpClassifieds) {
		logger.info(":: START SyncToErp Vendor Data :: ");
		String METHOD_NAME = "processRmsCreationFtpFiles";
		logger.info("Entered into the method :: " + METHOD_NAME);
		boolean flag = true;
		try {
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			List<String> orgIds = new ArrayList<String>();
				List<ErpDataRequestPayload> erpDataList = new ArrayList<ErpDataRequestPayload>();
				erpClassifieds.entrySet().forEach(orgVendorData -> {
					ErpDataRequestPayload payload = new ErpDataRequestPayload();
					payload.setOrgId(payloadJson.get("orgId")+"");
					payload.setOrgOpuId(payloadJson.containsKey("orgOpuId")  && payloadJson.get("orgOpuId") != null ? payloadJson.get("orgOpuId")+"" : "");
					payload.setRequestData(orgVendorData.getValue());
					payload.setAction(payloadJson.get("action")+"");
					payload.setServiceType(payloadJson.get("serviceType")+"");
					payload.setUserId(payloadJson.get("userId")+"");
					erpDataList.add(payload);
				});
				flag = ftpUtilService.processRmsDataToErp(erpDataList);
				if (flag) {
					System.out.println("Success");
				}
				else {
					System.out.println("Error");
				}
				
		} catch (Exception e) {
			flag = false;
			logger.error("Error while processing vendor creation FTP files data :: " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		logger.info("Exit from the method :: " + METHOD_NAME);
		return flag;
	}
}
