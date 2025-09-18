package com.portal.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.portal.clf.service.ClassifiedService;
import com.portal.clf.service.PaymentService;
import com.portal.constants.GeneralConstants.SettingType;
import com.portal.da.service.DisplayService;
import com.portal.erp.constants.EtlGeneralConstants;
import com.portal.erp.to.ApiResponse;
import com.portal.ftp.FTPUtilService;
import com.portal.rms.service.RmsService;
import com.portal.setting.dao.SettingDao;

@Component
@EnableScheduling
public class SchedulerServiceUtil {
	
	@Autowired
	private FTPUtilService ftpUtilService;
	
	@Autowired(required = true)
	private SettingDao settingDao;
	
	@Autowired
	private ClassifiedService classifiedService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private RmsService rmsService;
	
	@Autowired
	private DisplayService displayService;
	
//	@Scheduled(cron = "0 0/2 * * * *")
	@Scheduled(cron = "0 */2 * * * *")
	public void runCronJob() {
		Map<String, Object> params = new HashMap<>();
		params.put("stype", SettingType.APP_SETTING.getValue());
		params.put("grps", Arrays.asList(EtlGeneralConstants.FTP));
		Map<String, String> ftpConfigs = settingDao.getSMTPSettingValues(params).getSettings();
		ApiResponse apiResponse = ftpUtilService.ftpApiCall(ftpConfigs.get(EtlGeneralConstants.FTP_SERVER_FOLDER_PATH));
		System.out.println(apiResponse.getMessage());
	}
	
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 0 6 * * *")
	public void publishAddsForCurrentDate() {
		 classifiedService.getAddsForCurrentDate();
		System.out.println("Cron job executed for every 2 Mins.");
	}
	
//	@Scheduled(cron = "0 0/2 * * * *")
	public void processPendingPaymentTransactions() {
		System.out.println("processPendingPaymentTransactions started");
		paymentService.updatePendingPaymentStatuses();
		System.out.println("processPendingPaymentTransactions completed");
	}
	
	@Scheduled(cron = "0 0/2 * * * *")
	public void runCronJoBForRms() {
		Map<String, Object> params = new HashMap<>();
		params.put("stype", SettingType.APP_SETTING.getValue());
		params.put("grps", Arrays.asList(EtlGeneralConstants.FTP));
		Map<String, String> ftpConfigs = settingDao.getSMTPSettingValues(params).getSettings();
		ApiResponse apiResponse = ftpUtilService.ftpRmsApiCall(ftpConfigs.get(EtlGeneralConstants.FTP_RMS_SERVER_FOLDER_PATH));
		System.out.println(apiResponse.getMessage());
	}
	
//	@Scheduled(cron = "0 0/2 * * * *")
	public void getRefund() {
		System.out.println("getRefund started");
		paymentService.getRefundDetails();
		System.out.println("getRefund completed");
	}
	
	@Scheduled(cron = "0 0/1 * * * *")
	public void createOrUpdateCustomerDetails() {
		Map<String, Object> params = new HashMap<>();
		params.put("stype", SettingType.APP_SETTING.getValue());
		params.put("grps", Arrays.asList(EtlGeneralConstants.FTP));
		Map<String, String> ftpConfigs = settingDao.getSMTPSettingValues(params).getSettings();
		ApiResponse apiResponse = ftpUtilService.ftpRmsCustomerApiCall(ftpConfigs.get(EtlGeneralConstants.FTP_RMS_CUSTOMER_FOLDER_PATH));
		System.out.println(apiResponse.getMessage());
	}
	
	@Scheduled(cron = "0 0/10 * * * *")
	public void deleteRmsDraftOrders() {
		rmsService.deleteRmsDraftOrders();
	}
	
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 0 6 * * *")
	public void publishDisplayAddsForCurrentDate() {
		displayService.getDisplayAddsForCurrentDate();
		System.out.println("Cron job executed for every 2 Mins.");
	}
}
