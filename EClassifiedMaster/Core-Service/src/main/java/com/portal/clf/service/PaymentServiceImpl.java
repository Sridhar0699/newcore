package com.portal.clf.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.portal.basedao.IBaseDao;
import com.portal.clf.entities.BillDeskWebhookLogs;
import com.portal.clf.entities.ClfPaymentResponseTracking;
import com.portal.clf.entities.PaymentsRefund;
import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.CartDetails;
import com.portal.clf.models.ClfPaymentsRefund;
import com.portal.clf.models.ErpClassifieds;
import com.portal.clf.models.PaymentGatewayDetails;
import com.portal.clf.models.PaymentsAndRefund;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.constants.GeneralConstants;
import com.portal.da.service.DisplayService;
import com.portal.gd.dao.GlobalDataDao;
import com.portal.repository.BillDeskWebhookLogsRepo;
import com.portal.repository.ClfOrderItemsRepo;
import com.portal.repository.ClfOrdersRepo;
import com.portal.repository.ClfPaymentResponseTrackingRepo;
import com.portal.repository.PaymentRefundRepo;
import com.portal.security.model.LoggedUser;
import com.portal.send.models.EmailsTo;
import com.portal.send.service.SendMessageService;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;
import com.portal.user.dao.UserDao;
import com.portal.utils.JWTUtils;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;


@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private ClfPaymentResponseTrackingRepo clfPaymentResponseTrackingRepo;
	
	@Autowired
	private ClfOrdersRepo clfOrdersRepo;
	
	@Autowired
	private ClassifiedService classifiedService;
	
	@Autowired	
	private CommonService commonService;
	
	@Autowired(required = true)
	private SettingDao settingDao;
	
	@Autowired(required = true)
	private SendMessageService sendService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GlobalDataDao globalDataDao;
	
	@Autowired
	private JWTUtils jWTUtils;
	
	@Autowired
	private Environment prop;
	
	@Autowired
	private BillDeskWebhookLogsRepo billDeskWebhookLogsRepo;
	
	@Autowired
	private PaymentRefundRepo paymentRefundRepo;
	
	@Autowired
	private IBaseDao baseDao;
	
	@Autowired
	private ClfOrderItemsRepo clfOrderItemsRepo;
	
	@Autowired
	private DisplayService displayService;
	
	@Override
	public GenericApiResponse updatePaymentResponse(BillDeskPaymentResponseModel payload, LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		genericApiResponse.setMessage(GeneralConstants.SUCCESS);
		try {
			if (payload.getTransaction_response() != null && !payload.getTransaction_response().isEmpty()) {
				//verify encToken
				PaymentGatewayDetails paymentGatewayDetails = commonService.populatePaymentGatewayDetails("PRD");
				if(paymentGatewayDetails.getClientId() != null && paymentGatewayDetails.getClientSecret() != null && !paymentGatewayDetails.getClientId().isEmpty() && !paymentGatewayDetails.getClientSecret().isEmpty()){
					if(!jWTUtils.verifyJwtToken(payload.getTransaction_response(), paymentGatewayDetails.getClientSecret()))
					{
						genericApiResponse.setStatus(1);
						genericApiResponse.setMessage("Signature verification failed");
						return genericApiResponse;
					}
				}
				String[] split_string = payload.getTransaction_response().split("\\.");
				if (split_string.length > 1) {
					String base64EncodedBody = split_string[1];

					JSONObject decodedRes = new JSONObject(
							new String(Base64Utils.decode(base64EncodedBody.getBytes())));
//					ClfPaymentResponseTracking clfPaymentResponseTracking = new ClfPaymentResponseTracking();
					ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
							.getPaymentsByOrderId(decodedRes.getString("orderid"));
					payload.setClfPaymentResponseTracking(clfPaymentResponseTracking);
					if(payload.getClfPaymentResponseTracking() != null){
						BeanUtils.copyProperties(payload.getClfPaymentResponseTracking(), clfPaymentResponseTracking);
					} else {
						clfPaymentResponseTracking = new ClfPaymentResponseTracking();
						clfPaymentResponseTracking.setId(UUID.randomUUID().toString());
					}
					clfPaymentResponseTracking.setSecOrderId(payload.getSecOrderId());
					clfPaymentResponseTracking.setOrderId(payload.getOrderid());
					clfPaymentResponseTracking.setAmount(decodedRes.getString("amount"));
					clfPaymentResponseTracking.setBankRefNo(decodedRes.getString("bank_ref_no"));
					clfPaymentResponseTracking.setGateWayId(decodedRes.getString("bank_ref_no"));
					clfPaymentResponseTracking.setPaymentMethodType(decodedRes.getString("payment_method_type"));
					clfPaymentResponseTracking.setPaymentStatus(decodedRes.getString("transaction_error_type"));
					clfPaymentResponseTracking.setAuthStatus(decodedRes.getString("auth_status"));
					clfPaymentResponseTracking.setTransactionDate(decodedRes.getString("transaction_date"));
					clfPaymentResponseTracking.setTransactionId(decodedRes.getString("transactionid"));
					clfPaymentResponseTracking.setTxnProcessType(
							decodedRes.has("txn_process_type") ? decodedRes.getString("txn_process_type") : null);
					clfPaymentResponseTracking.setResponse(payload.getTransaction_response());
					clfPaymentResponseTracking.setCreatedBy(loggedUser.getUserId());
					clfPaymentResponseTracking.setCreatedTs(new Date());
					clfPaymentResponseTracking.setTransactionErrorDesc(decodedRes.getString("transaction_error_desc"));
					clfPaymentResponseTrackingRepo.save(clfPaymentResponseTracking);
					genericApiResponse.setData(clfPaymentResponseTracking);
					if("0399".equalsIgnoreCase(decodedRes.get("auth_status")+"")){
						genericApiResponse.setStatus(1);
						genericApiResponse.setMessage(decodedRes.getString("transaction_error_type"));
						return genericApiResponse;
					}
					if (clfPaymentResponseTracking.getPaymentStatus() != null
							&& !clfPaymentResponseTracking.getPaymentStatus().isEmpty()
							&& clfPaymentResponseTracking.getSecOrderId() != null
							&& !clfPaymentResponseTracking.getSecOrderId().isEmpty()
							&& "success".equalsIgnoreCase(clfPaymentResponseTracking.getPaymentStatus())) {
						List<String> orderIds = new ArrayList<>();
						orderIds.add(clfPaymentResponseTracking.getSecOrderId());
						clfOrdersRepo.updateClfOrdersPaymentStatus(
								"CLOSED", loggedUser.getUserId(),
								new Date(), orderIds);
						if("success".equalsIgnoreCase(clfPaymentResponseTracking.getPaymentStatus()) && clfPaymentResponseTracking.getOrderType() != null && (clfPaymentResponseTracking.getOrderType() == 2 || clfPaymentResponseTracking.getOrderType() == 3)) {
							if (clfPaymentResponseTracking.getOrderType() == 2 && !commonService.getRequestHeaders().getLoggedUser().getRoleName().equalsIgnoreCase("SSP_USER")) {
								Map<String, ErpClassifieds> erpClassifiedsMap = classifiedService
										.getOrderDetailsForErp(orderIds);
								sendMailToCustomer(erpClassifiedsMap, payload, loggedUser, clfPaymentResponseTracking);
							} else if (clfPaymentResponseTracking.getOrderType() == 3) {
								Map<String, ErpClassifieds> erpClassifiedsMap = displayService
										.getDisplayOrderDetailsForErp(orderIds);
								displayService.sendDisplayMailToCustomer(erpClassifiedsMap, payload, loggedUser, clfPaymentResponseTracking);
							}else if(clfPaymentResponseTracking.getOrderType() == 2 && commonService.getRequestHeaders().getLoggedUser().getRoleName().equalsIgnoreCase("SSP_USER")) {
								
								Map<String, ErpClassifieds> erpClassifiedsMap = displayService
										.getClassifiedOrderDetailsForSSPErp(orderIds);
								displayService.sendMailToCustomerForSSPVendor(erpClassifiedsMap, payload, loggedUser, clfPaymentResponseTracking);
							}
						}else {
							sendPaymentDetailsToCustomer(loggedUser,decodedRes,payload.getSecOrderId());
						}
						
//						ClassifiedStatus classifiedStatus = new ClassifiedStatus();
//						classifiedStatus.setOrderId(orderIds);
//						classifiedService.syncronizeSAPData(commonService.getRequestHeaders(), classifiedStatus);
					}
				}
			}else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("Error while updating payment status update");
			}
		} catch (Exception e) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Error while updating payment status update:"+e.getMessage());
		}
		return genericApiResponse;
	}

	private void sendPaymentDetailsToCustomer(LoggedUser loggedUser, JSONObject decodedRes, String orderId) {
		try {
			
			Object[] obj = clfOrdersRepo.getCustomerEmail(orderId);
			
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
			emailTo.setOrgId("1000");
//			emailTo.setTo(loggedUser.getEmail());
			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
			mapProperties.put("userId", loggedUser.getLogonId());// new userName
			mapProperties.put("transactionId", decodedRes.get("transactionid"));
			mapProperties.put("orderId", decodedRes.get("orderid"));
			mapProperties.put("amount", decodedRes.get("amount"));
			mapProperties.put("authStatus", decodedRes.get("auth_status"));
			mapProperties.put("paymentStatus", decodedRes.get("transaction_error_type"));
			mapProperties.put("transactionDate", decodedRes.get("transaction_date"));
			
			if(obj.length > 0 && obj[0] != null) {
				emailTo.setTo(obj[0]+"");
				String [] ccMails = {loggedUser.getEmail()};
				emailTo.setBcc(ccMails);
			}else {
				emailTo.setTo(loggedUser.getEmail());
			}

			emailTo.setTemplateName(GeneralConstants.RMS_PAYMENT_INVOICE);
			emailTo.setTemplateProps(mapProperties);

			sendService.sendCommunicationMail(emailTo, emailConfigs);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap, BillDeskPaymentResponseModel payload,
			LoggedUser loggedUser, ClfPaymentResponseTracking clfPaymentResponseTracking) {
		// TODO Auto-generated method stub
		try {
			
//			List<String> adminEmail = new ArrayList<String>();
//			List<UmOrgUsers> umOrgU = userDao.getAdminAndHqUsers();
//			for(UmOrgUsers umOrg : umOrgU) {
//				if("ADMIN".equalsIgnoreCase(umOrg.getUmOrgRoles().getRoleShortId()))
//				adminEmail.add(umOrg.getUmUsers().getEmail());
//			}
			
//			String adminEmails = adminEmail.stream().map(Object::toString)
//					.collect(Collectors.joining(","));
//			String[] adminCcMails = {adminEmails};
			
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();
			
			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
//			emailTo.setBcc(adminCcMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url",emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());//created by userName
			mapProperties.put("userId", loggedUser.getLogonId());//new userName
			if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				mapProperties.put("agencyUsername", loggedUser.getFirstName());
				mapProperties.put("agencyMobileNo", loggedUser.getMobile());
				mapProperties.put("agencyEmail", loggedUser.getEmail());
				mapProperties.put("isAgencyUser", "inline-block");
				mapProperties.put("isCustomerUser", "none");
				mapProperties.put("isAgencyUserCommition", true);
			}else {
				mapProperties.put("isAgencyUser", "none");
				mapProperties.put("isCustomerUser", "inline-block");
			}
			erpClassifiedsMap.entrySet().forEach(erpData -> {
				emailTo.setTo(erpData.getValue().getEmailId());
				mapProperties.put("adId", erpData.getValue().getAdId());
				mapProperties.put("adType", erpData.getValue().getAdsType());
				mapProperties.put("adSubType", erpData.getValue().getAdsSubType());
				mapProperties.put("pack", erpData.getValue().getSchemeStr());
				mapProperties.put("lineCount", erpData.getValue().getLineCount());
				mapProperties.put("amount", erpData.getValue().getPaidAmount());
				mapProperties.put("langStr", erpData.getValue().getLangStr());
				mapProperties.put("category", erpData.getValue().getCategoryStr());
				mapProperties.put("subCategory", erpData.getValue().getSubCategoryStr());
				mapProperties.put("printAdMatter", erpData.getValue().getContent());
				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
					mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
				} else {
					mapProperties.put("approvalStatus", "PENDING");
				}
				mapProperties.put("customerName", erpData.getValue().getCustomerName());
				mapProperties.put("customerEmail", erpData.getValue().getEmailId());
				mapProperties.put("customerMobile", erpData.getValue().getMobileNumber());
				mapProperties.put("transactionDate", clfPaymentResponseTracking.getTransactionDate());
				mapProperties.put("transactionId", clfPaymentResponseTracking.getTransactionId());
				mapProperties.put("transactionType", clfPaymentResponseTracking.getTxnProcessType());
				mapProperties.put("paymentStatus", clfPaymentResponseTracking.getPaymentStatus());
				mapProperties.put("bankRefId", clfPaymentResponseTracking.getBankRefNo());
				mapProperties.put("bookingDate", erpData.getValue().getBookingDate());
				mapProperties.put("gstAmount", erpData.getValue().getGstTotalAmount());
				mapProperties.put("rate", erpData.getValue().getRate());
				mapProperties.put("extraLineAmount", erpData.getValue().getExtraLineRateAmount());
				mapProperties.put("agencyCommition", erpData.getValue().getAgencyCommition());
				mapProperties.put("subject_edit", true);
				
				if(loggedUser.getEmail() == null) {
					loggedUser.setEmail(erpData.getValue().getCreatedByEmail());
				}
				
				String [] ccMails = {loggedUser.getEmail() ,erpData.getValue().getBookingUnitEmail()};
				emailTo.setBcc(ccMails);
				
				mapProperties.put("rate", erpData.getValue().getRate() + erpData.getValue().getExtraLineRateAmount());
				
				if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
					Double totValue = erpData.getValue().getRate() + erpData.getValue().getExtraLineRateAmount();
					totValue = totValue - erpData.getValue().getAgencyCommition();
					totValue = totValue + erpData.getValue().getGstTotalAmount();
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				} else {
					Double totValue = erpData.getValue().getRate() + erpData.getValue().getExtraLineRateAmount()
							+ erpData.getValue().getGstTotalAmount();
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				}
				
				if("11".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isTelangana", "inline-block");
				}else {
					mapProperties.put("isTelangana", "none");
				}
				if("25".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isAndhraPradesh", "inline-block");
				}else {
					mapProperties.put("isAndhraPradesh", "none");
				}
				List<String> pubDates = erpData.getValue().getPublishDates();
				List<String> pubDatesList = new ArrayList<String>();
				for(String pubD : pubDates) {
					 Date date;
					 String formattedDate = null;
					try {
						date = new SimpleDateFormat("yyyyMMdd").parse(pubD);
						formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					pubDatesList.add(formattedDate);
				}
				
				//sorting Publish dates in acending order
				pubDatesList = pubDatesList.stream()
			            .sorted((date1, date2) -> {
			                try {
			                    return new SimpleDateFormat("dd-MM-yyyy").parse(date1)
			                        .compareTo(new SimpleDateFormat("dd-MM-yyyy").parse(date2));
			                } catch (ParseException e) {
			                    throw new RuntimeException(e);
			                }
			            })
			            .collect(Collectors.toList());
				
				String publishDates = pubDatesList.stream().map(Object::toString)
						.collect(Collectors.joining(","));
				mapProperties.put("pubDates", publishDates);
				List<String> editionsList = erpData.getValue().getEditions();
				String editions = editionsList.stream().map(Object::toString)
						.collect(Collectors.joining(","));
				mapProperties.put("editions", editions);
				if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
					emailTo.setTemplateName(GeneralConstants.PAYMENT_AGENCY);
				}else {
					emailTo.setTemplateName(GeneralConstants.PAYMENT);
				}
				emailTo.setTemplateProps(mapProperties);
				
				sendService.sendCommunicationMail(emailTo, emailConfigs);
			});	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void updatePendingPaymentStatuses() {
		List<String> transactionIds = new ArrayList<>();
		List<String> orderIds = new ArrayList<>();
		Map<String, ClfPaymentResponseTracking> transactionMap = new HashMap<>();
		LoggedUser loggedUser = new LoggedUser();
		PaymentGatewayDetails paymentGatewayDetails = commonService.populatePaymentGatewayDetails("PRD");
		if (paymentGatewayDetails.getClientId() != null && !paymentGatewayDetails.getClientId().isEmpty()
				&& paymentGatewayDetails.getClientSecret() != null
				&& !paymentGatewayDetails.getClientSecret().isEmpty()) {
			Map<String, Object> header = new HashMap<>();
			header.put("alg", "HS256");
			header.put("typ", "JWT");
			header.put("clientid", paymentGatewayDetails.getClientId());
			header.put("kid", "HMAC");
			List<String> statusList = new ArrayList<>();
			statusList.add("success");
			statusList.add("payment_processing_error");
			List<Object[]> transactionList = clfPaymentResponseTrackingRepo.getPendingStatusUpdateOrders(statusList);
			JSONObject retrievePayload = new JSONObject();
			for (Object[] obj : transactionList) {
//				transactionIds.add((String) obj[0]);
				orderIds.add((String) obj[2]);
			}
			if (!orderIds.isEmpty()) {
				List<ClfPaymentResponseTracking> clfPaymentResponseTrackingList = clfPaymentResponseTrackingRepo
						.getPaymentsByOrderIds(orderIds);
				for (ClfPaymentResponseTracking prt : clfPaymentResponseTrackingList) {
//					transactionMap.put(prt.getTransactionId(), prt);
					transactionMap.put(prt.getOrderId(), prt);
				}
			}
			for (Object[] obj : transactionList) {
				try {
					long timeStamp = new Date().getTime();
					Map<String, String> headerMap = new HashMap<>();
					headerMap.put("content-Type", "application/jose");
					headerMap.put("Bd-Timestamp", timeStamp + "");
					headerMap.put("Bd-Traceid", timeStamp + "ABD1K");
					headerMap.put("accept", "application/jose");
					retrievePayload.put("mercid", paymentGatewayDetails.getMercId());
//					retrievePayload.put("transactionid", (String) obj[0]);
					retrievePayload.put("orderid", (String) obj[2]);
					String encToken = jWTUtils.generateJwtToken(retrievePayload, header,
							paymentGatewayDetails.getClientSecret());
					HttpResponse<String> res = Unirest
							.post(prop.getProperty("BILLDESK_HOST") + "" + prop.getProperty("BILLDESK_RETRIEVE"))
							.headers(headerMap).body(encToken).asString();
					BillDeskPaymentResponseModel billDeskPaymentResponseModel = new BillDeskPaymentResponseModel();
					billDeskPaymentResponseModel.setMercid(paymentGatewayDetails.getMercId());
					billDeskPaymentResponseModel.setSecOrderId((String) obj[1]);
					billDeskPaymentResponseModel.setOrderid((String) obj[2]);
					billDeskPaymentResponseModel.setTransaction_response(res.getBody());
//					billDeskPaymentResponseModel.setClfPaymentResponseTracking(transactionMap.get((String) obj[0]));
					billDeskPaymentResponseModel.setClfPaymentResponseTracking(transactionMap.get((String) obj[2]));
					loggedUser.setUserId(((Short) obj[4]).intValue());
					updatePaymentResponse(billDeskPaymentResponseModel, loggedUser);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public GenericApiResponse prepareRequest(@NotNull CartDetails payload,LoggedUser loggedUser) {
		GenericApiResponse apiResp = new GenericApiResponse();
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		JSONObject additionalInfo = new JSONObject();
		JSONObject device = new JSONObject();
		JSONObject splitPayment = new JSONObject();
		JSONArray splitPaymentArray = new JSONArray();
		Map<String, Object> header = new HashMap<>();
		ResponseEntity<?> resp = null;
		String ecomOrderId = "";
		String orderId = "";
		try {
			apiResp = this.prepareRequestForEcomOrder(payload,loggedUser);
			if(apiResp != null && apiResp.getStatus() == 0 && apiResp.getData() != null) {
				String encToken = apiResp.getData()+"";
				long timeStamp = new Date().getTime();
				Map<String, String> headerMap = new HashMap<>();
				headerMap.put("content-Type", "application/jose");
				headerMap.put("Bd-Timestamp", timeStamp + "");
				headerMap.put("Bd-Traceid", timeStamp + "ABD1K");
				headerMap.put("accept", "application/jose");
				String rsType = "text";
				
				JSONObject jsonObject1 = new JSONObject(headerMap);
				String jsonString = jsonObject1.toString();
				String base64EncodedString = Base64.getEncoder().encodeToString(jsonString.getBytes());
				String[] enc_split_string = encToken.split("\\.");
				String encBase64EncodedBody = enc_split_string[1];
				JSONObject encDecodedRes = new JSONObject(new String(Base64Utils.decode(encBase64EncodedBody.getBytes())));
				this.saveEcomEncodedHeaders(base64EncodedString, encDecodedRes.getString("order_ref_no"));
				ecomOrderId = encDecodedRes.getString("order_ref_no");
				
				HttpResponse<String> res = Unirest.post(prop.getProperty("BILLDESK_ECOM_ORDER_CREATION")).headers(headerMap).body(encToken).asString();
				System.out.println("Billdesk res: "+res.getBody());
				if (res.getBody() != null && ("200".equalsIgnoreCase(res.getStatus() + "") || "409".equalsIgnoreCase(res.getStatus() +""))) {
					String[] split_string = res.getBody().split("\\.");
					if (split_string.length > 1) {
						String base64EncodedBody = split_string[1];
						JSONObject decodedRes = new JSONObject(new String(Base64Utils.decode(base64EncodedBody.getBytes())));
						orderId = decodedRes.getString("ecom_orderid");
						this.saveEcomEncodedRequest(res.getBody(), encDecodedRes.getString("order_ref_no"), null, null,null,null,orderId);
					}
				}else {
					apiResp.setMessage(res.getBody());
					apiResp.setStatus(1);
					return apiResp;
				}
			}else {
				return apiResp;
			}
			PaymentGatewayDetails paymentGatewayDetails = commonService.populatePaymentGatewayDetails("PRD");
			if (paymentGatewayDetails.getClientId() != null && !paymentGatewayDetails.getClientId().isEmpty()
					&& paymentGatewayDetails.getClientSecret() != null
					&& !paymentGatewayDetails.getClientSecret().isEmpty()) {

				header.put("alg", "HS256");
				header.put("typ", "JWT");
				header.put("clientid", paymentGatewayDetails.getClientId());
				header.put("kid", "HMAC");

				Date currentDateTime = new Date();
				String formattedDate = formatter.format(currentDateTime);
//				String orderId = classifiedService.generateSeries("ORDER");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate1 = dateFormat.format(currentDateTime);

				List<Object[]> bookingUnitDetails = clfOrdersRepo.getBookingDetailsForPrepareReq(payload.getOrderDetails().getOrderId());
				if(bookingUnitDetails.isEmpty()) {
					bookingUnitDetails=clfOrdersRepo.getBookingDetailsForPrepareRequest(payload.getOrderDetails().getOrderId());
				}
                
                additionalInfo.put("additional_info1", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[0] != null ? bookingUnitDetails.get(0)[0] : "NA");
                additionalInfo.put("additional_info2", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[1] != null ? bookingUnitDetails.get(0)[1] : "NA");
                additionalInfo.put("additional_info3", formattedDate1);
                additionalInfo.put("additional_info4", String.format("%.2f",payload.getOrderDetails().getGrandTotal()));
                additionalInfo.put("additional_info5", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[2] != null ? bookingUnitDetails.get(0)[2] : "NA");
                additionalInfo.put("additional_info6", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[3] != null ? bookingUnitDetails.get(0)[3] : "NA");
                additionalInfo.put("additional_info7", payload.getItems().get(0).getAdId());

				device.put("init_channel", prop.getProperty("INIT_CHANNEL"));
				device.put("ip", prop.getProperty("IP"));
				device.put("user_agent", prop.getProperty("USER_AGENT"));
				device.put("accept_header", prop.getProperty("ACCEPT_HEADER"));
//				device.put("fingerprintid", prop.getProperty("FINGER_PRINT_ID"));
//				device.put("browser_tz", prop.getProperty("BROWSER_TZ"));
//				device.put("browser_color_depth", prop.getProperty("BROWSER_COLOR_DEPTH"));
//				device.put("browser_java_enabled", prop.getProperty("BROWSER_JAVA_ENABLED"));
//				device.put("browser_screen_height", prop.getProperty("BROWSER_SCREEN_HEIGHT"));
//				device.put("browser_screen_width", prop.getProperty("BROWSER_SCREEN_WIDTH"));
//				device.put("browser_language", prop.getProperty("BROWSER_LANGUAGE"));
//				device.put("browser_javascript_enabled", prop.getProperty("BROWSER_JAVASCRIPT_ENABLED"));

//				splitPayment.put("mercid", payload.getOrderDetails().getPaymentChildId());
//				splitPayment.put("amount", String.format("%.2f",payload.getOrderDetails().getGrandTotal()));
//				splitPaymentArray.put(splitPayment);

//				jsonObject.put("mercid", payload.getMercid());
				jsonObject.put("mercid", paymentGatewayDetails.getMercId());
//				jsonObject.put("orderid", orderId);
				jsonObject.put("orderid", orderId);
				jsonObject.put("amount", String.format("%.2f",payload.getOrderDetails().getGrandTotal()));
				jsonObject.put("order_date", formattedDate);
				jsonObject.put("currency", prop.getProperty("CURRENCY"));
				jsonObject.put("ru", prop.getProperty("RU"));
				jsonObject.put("additional_info", additionalInfo);
//				jsonObject.put("split_payment", splitPaymentArray);
				jsonObject.put("itemcode", prop.getProperty("ITEM_CODE"));
				jsonObject.put("device", device);

				String encToken1 = jWTUtils.generateJwtToken(jsonObject, header,
						paymentGatewayDetails.getClientSecret());
				this.saveEncodedRequest(encToken1,ecomOrderId,payload.getOrderDetails().getOrderId(),loggedUser,payload.getOrderType(),payload.getLinkFlag(),true);		
				if (encToken1 != null) {
					resp = this.showbilldeskModel(encToken1,ecomOrderId);
					apiResp = (GenericApiResponse) resp.getBody();
//					apiResp.setData(encToken);
					apiResp.setStatus(0);
				} else {
					apiResp.setStatus(1);
					apiResp.setMessage("Something went wrong");
				}
			}
		} catch (Exception e) {
			apiResp.setStatus(1);
			apiResp.setMessage("Something went wrong");
			e.printStackTrace();
		}
		return apiResp;
	}

	
	public ResponseEntity<?> showbilldeskModel(@NotNull String encToken, String ecomOrderId) {
		String METHOD_NAME = "showbilldeskModel";

//		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		try {
//			String urlPath = "https://uat1.billdesk.com/u2/payments/ve1_2/orders/create";//UAT
//			String urlPath = "https://pguat.billdesk.io/payments/ve1_2/orders/create"//UAT
//			String urlPath = "https://api.billdesk.com/payments/ve1_2/orders/create";//PRD
			long timeStamp = new Date().getTime();
			Map<String, String> headerMap = new HashMap<>();
			headerMap.put("content-Type", "application/jose");
			headerMap.put("Bd-Timestamp", timeStamp + "");
			headerMap.put("Bd-Traceid", timeStamp + "ABD1K");
			headerMap.put("accept", "application/jose");
			String rsType = "text";
			
			JSONObject jsonObject = new JSONObject(headerMap);
			String jsonString = jsonObject.toString();
			String base64EncodedString = Base64.getEncoder().encodeToString(jsonString.getBytes());
			String[] enc_split_string = encToken.split("\\.");
			String encBase64EncodedBody = enc_split_string[1];
			JSONObject encDecodedRes = new JSONObject(new String(Base64Utils.decode(encBase64EncodedBody.getBytes())));
			this.saveEncodedHeaders(base64EncodedString, ecomOrderId);
			
			HttpResponse<String> res = Unirest.post(prop.getProperty("BILLDESK_CREATE_ORDER_API")).headers(headerMap).body(encToken).asString();
			System.out.println("Billdesk res: "+res.getBody());
			if (res.getBody() != null && ("200".equalsIgnoreCase(res.getStatus() + "") || "409".equalsIgnoreCase(res.getStatus() +""))) {
				String[] split_string = res.getBody().split("\\.");
				if (split_string.length > 1) {
					String base64EncodedBody = split_string[1];
					JSONObject decodedRes = new JSONObject(new String(Base64Utils.decode(base64EncodedBody.getBytes())));
					this.saveEncodedRequest(res.getBody(), ecomOrderId, null, null,null,null,false);
				}

				apiResp.setData(res.getBody());
				apiResp.setStatus(0);
			}else {
				apiResp.setMessage(res.getBody());
				apiResp.setStatus(1);
			}
		} catch (Exception e) {
//			logger.error("Error while getting Billdesk Payment Options: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}
		
		/*
		 * RestTemplate restTemplate = new RestTemplate();
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON);
         * headers.set("content-Type", "application/jose");
		 * headers.set("Bd-Timestamp", timeStamp + "");
		 * headers.set("Bd-Traceid", timeStamp + "ABD1K");
		 * headers.set("accept", "application/jose");
		 * HttpEntity<String> httpEntity = new HttpEntity<>(encToken, headers);
		 * ResponseEntity<?> res = restTemplate.exchange(urlPath, HttpMethod.POST, httpEntity, String.class);
		 * if (res.getBody() != null && "200".equalsIgnoreCase(res.getStatusCodeValue()
		 * + "")) { apiResp.setStatus(0); apiResp.setData(res); } else if
		 * (res.getStatusCode() == HttpStatus.UNAUTHORIZED) {
		 * apiResp.setMessage(prop.getProperty("GEN_002"));
		 * apiResp.setErrorcode("GEN_002"); }
		 */
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

//		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	private void saveEcomEncodedHeaders(String headers, String ecomOrderId) {
		try {
			ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
					.getPaymentsByEcomOrderId(ecomOrderId);
			if (clfPaymentResponseTracking != null) {
				clfPaymentResponseTracking.setEcomHeaders(headers);
				clfPaymentResponseTrackingRepo.save(clfPaymentResponseTracking);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private GenericApiResponse prepareRequestForEcomOrder(@NotNull CartDetails payload, LoggedUser loggedUser) {
		GenericApiResponse apiResp = new GenericApiResponse();
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		JSONObject additionalInfo = new JSONObject();
		JSONObject device = new JSONObject();
		JSONObject splitPayment = new JSONObject();
		JSONArray splitPaymentArray = new JSONArray();
		Map<String, Object> header = new HashMap<>();
		try {
			PaymentGatewayDetails paymentGatewayDetails = commonService.populatePaymentGatewayDetails("PRD");
			if (paymentGatewayDetails.getClientId() != null && !paymentGatewayDetails.getClientId().isEmpty()
					&& paymentGatewayDetails.getClientSecret() != null
					&& !paymentGatewayDetails.getClientSecret().isEmpty()) {

				header.put("alg", "HS256");
				header.put("typ", "JWT");
				header.put("clientid", paymentGatewayDetails.getClientId());
				header.put("kid", "HMAC");

				Date currentDateTime = new Date();
				String formattedDate = formatter.format(currentDateTime);
				String ecomOrderId = classifiedService.generateSeries("ORDER");
				String customerRefId = classifiedService.generateSeries("CUSTOMER");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate1 = dateFormat.format(currentDateTime);

				List<Object[]> bookingUnitDetails = clfOrdersRepo.getBookingDetailsForPrepareReq(payload.getOrderDetails().getOrderId());
				if(bookingUnitDetails.isEmpty()) {
					bookingUnitDetails=clfOrdersRepo.getBookingDetailsForPrepareRequest(payload.getOrderDetails().getOrderId());
				}
                
                additionalInfo.put("additional_info1", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[0] != null ? bookingUnitDetails.get(0)[0] : "NA");
                additionalInfo.put("additional_info2", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[1] != null ? bookingUnitDetails.get(0)[1] : "NA");
                additionalInfo.put("additional_info3", formattedDate1);
                additionalInfo.put("additional_info4", String.format("%.2f",payload.getOrderDetails().getGrandTotal()));
                additionalInfo.put("additional_info5", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[2] != null ? bookingUnitDetails.get(0)[2] : "NA");
                additionalInfo.put("additional_info6", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[3] != null ? bookingUnitDetails.get(0)[3] : "NA");
                additionalInfo.put("additional_info7", payload.getItems().get(0).getAdId());

				device.put("init_channel", prop.getProperty("INIT_CHANNEL"));
				device.put("ip", prop.getProperty("IP"));
				device.put("user_agent", prop.getProperty("USER_AGENT"));
				device.put("accept_header", prop.getProperty("ACCEPT_HEADER"));
				device.put("fingerprintid", prop.getProperty("FINGER_PRINT_ID"));
				device.put("browser_tz", prop.getProperty("BROWSER_TZ"));
				device.put("browser_color_depth", prop.getProperty("BROWSER_COLOR_DEPTH"));
				device.put("browser_java_enabled", prop.getProperty("BROWSER_JAVA_ENABLED"));
				device.put("browser_screen_height", prop.getProperty("BROWSER_SCREEN_HEIGHT"));
				device.put("browser_screen_width", prop.getProperty("BROWSER_SCREEN_WIDTH"));
				device.put("browser_language", prop.getProperty("BROWSER_LANGUAGE"));
				device.put("browser_javascript_enabled", prop.getProperty("BROWSER_JAVASCRIPT_ENABLED"));

				splitPayment.put("mercid", payload.getOrderDetails().getPaymentChildId());
				splitPayment.put("amount", String.format("%.2f",payload.getOrderDetails().getGrandTotal()));
				splitPayment.put("customer_refid", customerRefId);
//				splitPayment.put("additional_info1", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[0] != null ? bookingUnitDetails.get(0)[0] : "NA");
				splitPayment.put("additional_info1", ecomOrderId != null ? ecomOrderId : "NA");
				splitPayment.put("additional_info2", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[1] != null ? bookingUnitDetails.get(0)[1] : "NA");
				splitPayment.put("additional_info3", formattedDate1);
				splitPayment.put("additional_info4", String.format("%.2f",payload.getOrderDetails().getGrandTotal()));
				splitPayment.put("additional_info5", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[2] != null ? bookingUnitDetails.get(0)[2] : "NA");
				splitPayment.put("additional_info6", !bookingUnitDetails.isEmpty() && bookingUnitDetails.get(0) != null && bookingUnitDetails.get(0)[3] != null ? bookingUnitDetails.get(0)[3] : "NA");
				splitPayment.put("additional_info7", payload.getItems().get(0).getAdId());
				splitPaymentArray.put(splitPayment);

//				jsonObject.put("mercid", payload.getMercid());
				jsonObject.put("mercid", paymentGatewayDetails.getMercId());
				jsonObject.put("order_ref_no", ecomOrderId);
				jsonObject.put("amount", String.format("%.2f",payload.getOrderDetails().getGrandTotal()));
				jsonObject.put("ecom_order_date", formattedDate);
				jsonObject.put("currency", prop.getProperty("CURRENCY"));
				jsonObject.put("ru", prop.getProperty("RU"));
				jsonObject.put("additional_info", additionalInfo);
				jsonObject.put("split_payment", splitPaymentArray);
				jsonObject.put("itemcode", prop.getProperty("ITEM_CODE"));
				jsonObject.put("device", device);

				String encToken = jWTUtils.generateJwtToken(jsonObject, header,
						paymentGatewayDetails.getClientSecret());
				this.saveEcomEncodedRequest(encToken,ecomOrderId,payload.getOrderDetails().getOrderId(),loggedUser,payload.getOrderType(),payload.getLinkFlag(),null);		
				if (encToken != null) {
					apiResp.setData(encToken);
					apiResp.setStatus(0);
				} else {
					apiResp.setStatus(1);
					apiResp.setMessage("Something went wrong");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return apiResp;
		
	}

	private void saveEcomEncodedRequest(String encToken, String ecomOrderId, String portalOrderId, LoggedUser loggedUser, Integer orderType, Boolean linkFlag, String orderId) {
		try {
			ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
					.getPaymentsByEcomOrderId(ecomOrderId);
			if (clfPaymentResponseTracking != null) {
				clfPaymentResponseTracking.setEcomEncodedResponse(encToken);
				if(orderId != null) {
					clfPaymentResponseTracking.setOrderId(orderId);
				}
			} else {
				clfPaymentResponseTracking = new ClfPaymentResponseTracking();
				clfPaymentResponseTracking.setId(UUID.randomUUID().toString());
				clfPaymentResponseTracking.setPaymentStatus("pending");
				clfPaymentResponseTracking.setSecOrderId(portalOrderId);
//				clfPaymentResponseTracking.setOrderId(orderId);
				clfPaymentResponseTracking.setEcomOrderId(ecomOrderId);
				clfPaymentResponseTracking.setEcomEncodedRequest(encToken);
				clfPaymentResponseTracking.setCreatedBy(loggedUser.getUserId());
				clfPaymentResponseTracking.setCreatedTs(new Date());
				clfPaymentResponseTracking.setOrderType(orderType);
				clfPaymentResponseTracking.setMarkAsDelete(false);
				clfPaymentResponseTracking.setLinkFlag(true);
			}
			clfPaymentResponseTrackingRepo.save(clfPaymentResponseTracking);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	@SuppressWarnings("null")
	public void saveEncodedRequest(String encToken, String ecomOrderId, String portalOrderId, LoggedUser loggedUser, Integer orderType, Boolean linkFlag, boolean encodedRequest) {
		try {
			ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
					.getPaymentsByEcomOrderId(ecomOrderId);
			if(clfPaymentResponseTracking != null && encodedRequest) {
				clfPaymentResponseTracking.setEncodedRequest(encToken);
			}
			if(clfPaymentResponseTracking != null && !encodedRequest) {
				clfPaymentResponseTracking.setEncodedResponse(encToken);
			}
//			if (clfPaymentResponseTracking != null) {
//				clfPaymentResponseTracking.setEncodedResponse(encToken);
//			} else {
//				clfPaymentResponseTracking = new ClfPaymentResponseTracking();
//				clfPaymentResponseTracking.setId(UUID.randomUUID().toString());
//				clfPaymentResponseTracking.setPaymentStatus("pending");
//				clfPaymentResponseTracking.setSecOrderId(portalOrderId);
//				clfPaymentResponseTracking.setOrderId(orderId);
//				clfPaymentResponseTracking.setEncodedRequest(encToken);
//				clfPaymentResponseTracking.setCreatedBy(loggedUser.getUserId());
//				clfPaymentResponseTracking.setCreatedTs(new Date());
//				clfPaymentResponseTracking.setOrderType(orderType);
//				clfPaymentResponseTracking.setMarkAsDelete(false);
//				clfPaymentResponseTracking.setLinkFlag(true);
//			}
			clfPaymentResponseTrackingRepo.save(clfPaymentResponseTracking);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void saveEncodedHeaders(String headers, String ecomOrderId) {
		try {
			ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
					.getPaymentsByEcomOrderId(ecomOrderId);
			if (clfPaymentResponseTracking != null) {
				clfPaymentResponseTracking.setHeaders(headers);
				clfPaymentResponseTrackingRepo.save(clfPaymentResponseTracking);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

}
	@Override
	public GenericApiResponse billDeskWebHookLogs(@NotNull BillDeskPaymentResponseModel payload, LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		genericApiResponse.setMessage(GeneralConstants.SUCCESS);
		try {
				BillDeskWebhookLogs billDeskWebhookLogs=new BillDeskWebhookLogs();
				billDeskWebhookLogs.setId(UUID.randomUUID().toString());
				billDeskWebhookLogs.setEncodedRequest(payload.getTransaction_response());
				billDeskWebhookLogs.setCreatedTs(new Date());
				billDeskWebhookLogsRepo.save(billDeskWebhookLogs);
			
				this.updatePaymentResponse(payload, loggedUser);
		} catch (Exception e) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Error while  payment status update:"+e.getMessage());
		}
		return genericApiResponse;
	}
	
	
	@Override
	public GenericApiResponse prepareRequestForCreateRefund(@NotNull ClfPaymentsRefund payload,LoggedUser loggedUser) {
		GenericApiResponse apiResp = new GenericApiResponse();
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		Map<String, Object> header = new HashMap<>();
		JSONObject splitPayment = new JSONObject();
		JSONArray splitPaymentArray = new JSONArray();
		try {
			if(payload.getTransactionId()==null) {
				 payload = getPayloadDetails(payload.getOrderId());
			}

			PaymentGatewayDetails paymentGatewayDetails = commonService.populatePaymentGatewayDetails("PRD");
			if (paymentGatewayDetails.getClientId() != null && !paymentGatewayDetails.getClientId().isEmpty()
					&& paymentGatewayDetails.getClientSecret() != null
					&& !paymentGatewayDetails.getClientSecret().isEmpty()) {

				header.put("alg", "HS256");
				header.put("typ", "JWT");
				header.put("clientid", paymentGatewayDetails.getClientId());
				header.put("kid", "HMAC");

//				Date currentDateTime = new Date();
				Date currentDateTime = formatter.parse(payload.getTransactionDate());
				String formattedDate = formatter.format(currentDateTime);
				
				
					splitPayment.put("mercid", payload.getPaymentChildId());
					splitPayment.put("txn_amount", String.format("%.2f",payload.getAmount()));
					splitPayment.put("refund_amount", String.format("%.2f",payload.getAmount()));
					splitPaymentArray.put(splitPayment);
					
                	String mercRefundRefno = classifiedService.generateSeries("REFUND");
                	jsonObject.put("mercid", paymentGatewayDetails.getMercId());
    				jsonObject.put("orderid", payload.getOrderId());
    				jsonObject.put("txn_amount", String.format("%.2f",payload.getAmount()));
    				jsonObject.put("transaction_date", formattedDate);
    				jsonObject.put("currency", prop.getProperty("CURRENCY"));
    				jsonObject.put("transactionid", payload.getTransactionId());
    				jsonObject.put("refund_amount",String.format("%.2f",payload.getAmount()));
    				jsonObject.put("merc_refund_ref_no", mercRefundRefno);	
    				jsonObject.put("split_refund", splitPaymentArray);
    				
    				String encToken = jWTUtils.generateJwtToken(jsonObject, header,
    						paymentGatewayDetails.getClientSecret());
    				PaymentsRefund paymentsByOrderId = paymentRefundRepo.getPaymentsByOrderId(payload.getOrderId());
    				if(paymentsByOrderId==null) {
    				this.saveEncodedRefundRequestAndResponse(encToken,loggedUser);
    				}
    				if (encToken != null) {
    					apiResp.setData(encToken);
    					apiResp.setStatus(0);
    				} else {
    					apiResp.setStatus(1);
    					apiResp.setMessage("Something went wrong");
    				}
                }			
		} catch (Exception e) {
			apiResp.setStatus(1);
			apiResp.setMessage("Something went wrong");
			e.printStackTrace();
		}
		return apiResp;
	}
	
	
	private ClfPaymentsRefund getPayloadDetails(@NotNull String orderId)  {
		List<Object[]> paymentsDetailsByOrderId = clfPaymentResponseTrackingRepo.getPaymentsDetailsUsingOrderId(orderId);
		ClfPaymentsRefund payload=new ClfPaymentsRefund();
		for(Object[] obj: paymentsDetailsByOrderId) {
			payload.setTransactionId((String) obj[0]);
			payload.setOrderId((String) obj[1]);
			 String stringValue = (String) obj[2];
		        Double doubleValue = Double.parseDouble(stringValue);
		        payload.setAmount(doubleValue);
			payload.setPaymentChildId((String) obj[3]);
			payload.setTransactionDate((String) obj[4]);
		}		
		return payload;
		
	}

	public void saveEncodedRefundRequestAndResponse(String encToken,LoggedUser loggedUser) {
		try {
			
			String[] split_string = encToken.split("\\.");
			JSONObject decodedRes=null;
			if (split_string.length > 1) {
				String base64EncodedBody = split_string[1];
				 decodedRes = new JSONObject(new String(Base64Utils.decode(base64EncodedBody.getBytes())));				
			}
			PaymentsRefund paymentsRefund=paymentRefundRepo.getPaymentsByOrderId(decodedRes.getString("orderid"));
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			 formatter.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			  SimpleDateFormat desiredFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		        desiredFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			if (paymentsRefund != null) {
				paymentsRefund.setEncodedResponse(encToken);
				paymentsRefund.setRefundId(decodedRes.getString("refundid"));
				paymentsRefund.setRefundStatus(decodedRes.getString("refund_status"));
				paymentsRefund.setObjectId(decodedRes.getString("objectid"));
				Date refundDate = formatter.parse(decodedRes.getString("refund_date"));
				java.sql.Timestamp timestampforRefund = new java.sql.Timestamp(refundDate.getTime());
				paymentsRefund.setRefundDate(timestampforRefund);
			} else {
				paymentsRefund = new PaymentsRefund();
				paymentsRefund.setId(UUID.randomUUID().toString());
				paymentsRefund.setTransaction_id(decodedRes.getString("transactionid"));
				paymentsRefund.setOrderId(decodedRes.getString("orderid"));
				paymentsRefund.setMercId(decodedRes.getString("mercid"));
				paymentsRefund.setCurrency(decodedRes.getString("currency"));
				Date transactionDate = formatter.parse(decodedRes.getString("transaction_date"));
		        java.sql.Timestamp timestamp = new java.sql.Timestamp(transactionDate.getTime());
		        paymentsRefund.setTransaction_date(timestamp);
				paymentsRefund.setRefundAmount(decodedRes.getDouble("txn_amount"));
				paymentsRefund.setEncodedRequest(encToken);
				paymentsRefund.setMercRefundRefno(decodedRes.getString("merc_refund_ref_no"));
				paymentsRefund.setTxnAmount(decodedRes.getDouble("refund_amount"));
				paymentsRefund.setMarkAsDelete(false);
			}
			paymentRefundRepo.save(paymentsRefund);
//			baseDao.saveOrUpdate(paymentsRefund);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ResponseEntity<?> saveEncodedResponse(@NotNull String encToken,LoggedUser loggedUser) {
		
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		
		try {
			long timeStamp = new Date().getTime();
			Map<String, String> headerMap = new HashMap<>();
			headerMap.put("content-Type", "application/jose");
			headerMap.put("Bd-Timestamp", timeStamp + "");
			headerMap.put("Bd-Traceid", timeStamp + "ABD1K");
			headerMap.put("accept", "application/jose");
						
			HttpResponse<String> res = Unirest.post(prop.getProperty("BILLDESK_REFUND_ORDER_API")).headers(headerMap).body(encToken).asString();
			System.out.println("Billdesk res: "+res.getBody());
			if (res.getBody() != null && ("200".equalsIgnoreCase(res.getStatus() + "") || "409".equalsIgnoreCase(res.getStatus() +""))) {
				String[] split_string = res.getBody().split("\\.");
				if (split_string.length > 1) {
					String base64EncodedBody = split_string[1];
					JSONObject decodedRes = new JSONObject(new String(Base64Utils.decode(base64EncodedBody.getBytes())));
					System.out.println(decodedRes);	
					this.saveEncodedRefundRequestAndResponse(res.getBody(), loggedUser);
				}

				apiResp.setData(res.getBody());
				apiResp.setStatus(0);
				apiResp.setMessage("Order Cancelled and Refund Initiated");
			}else {
				apiResp.setMessage(res.getBody());
				apiResp.setStatus(1);
			}
		} catch (Exception e) {
			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}
		
		
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		
		return respObj;
	}
	
	public GenericApiResponse prepareRequestForGetRefund(String mercId,String refundId) {
	    GenericApiResponse apiResp = new GenericApiResponse();
	    JSONObject jsonObject = new JSONObject();
	    Map<String, Object> header = new HashMap<>();
	    try {
	        PaymentGatewayDetails paymentGatewayDetails = commonService.populatePaymentGatewayDetails("PRD");
	        if (paymentGatewayDetails.getClientId() != null && !paymentGatewayDetails.getClientId().isEmpty()
	                && paymentGatewayDetails.getClientSecret() != null
	                && !paymentGatewayDetails.getClientSecret().isEmpty()) {

	            header.put("alg", "HS256");
	            header.put("typ", "JWT");
	            header.put("clientid", paymentGatewayDetails.getClientId());
	            header.put("kid", "HMAC");

	            jsonObject.put("mercid", mercId);
	            jsonObject.put("refundid", refundId);

	            String encToken = jWTUtils.generateJwtToken(jsonObject, header, paymentGatewayDetails.getClientSecret());
	            if (encToken != null) {
	                apiResp.setData(encToken);
	                apiResp.setStatus(0);
	            } else {
	                apiResp.setStatus(1);
	                apiResp.setMessage("Something went wrong");
	            }
	        }
	    } catch (Exception e) {
	        apiResp.setStatus(1);
	        apiResp.setMessage("Something went wrong");
	        e.printStackTrace();
	    }
	    return apiResp;
	}
	
	@Override
	public GenericApiResponse getRefund(String mercId,String refundId) {
		GenericApiResponse apiResp = new GenericApiResponse();
        try {
        	   GenericApiResponse prepareRefundRequest = prepareRequestForGetRefund(mercId, refundId);
                 if(prepareRefundRequest!=null&&prepareRefundRequest.getStatus()==0&&prepareRefundRequest.getData()!=null);{
               	  ResponseEntity<?> encodedResponse = getAndUpdateEncodedResponse(prepareRefundRequest.getData()+"");
               	  prepareRefundRequest= (GenericApiResponse) encodedResponse.getBody();
                 }      
        }catch (Exception e) {
        	apiResp.setStatus(1);
            apiResp.setMessage("Something went wrong");
            e.printStackTrace();
        }
        return apiResp;
	}
	public ResponseEntity<?> getAndUpdateEncodedResponse(@NotNull String encToken){
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		
		try {
			long timeStamp = new Date().getTime();
			Map<String, String> headerMap = new HashMap<>();
			headerMap.put("content-Type", "application/jose");
			headerMap.put("Bd-Timestamp", timeStamp + "");
			headerMap.put("Bd-Traceid", timeStamp + "ABD1K");
			headerMap.put("accept", "application/jose");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the pattern as needed
			Date transactionDate = null;
			JSONObject jsonObject = new JSONObject(headerMap);
			String jsonString = jsonObject.toString();
			String base64EncodedString = Base64.getEncoder().encodeToString(jsonString.getBytes());
			String[] enc_split_string = encToken.split("\\.");
			String encBase64EncodedBody = enc_split_string[1];
			JSONObject encDecodedRes = new JSONObject(new String(Base64Utils.decode(encBase64EncodedBody.getBytes())));
			
			HttpResponse<String> res = Unirest.post(prop.getProperty("BILLDESK_REFUND_RETRIEVE_API")).headers(headerMap).body(encToken).asString();
			System.out.println("Billdesk res: "+res.getBody());
			if (res.getBody() != null && ("200".equalsIgnoreCase(res.getStatus() + "") || "409".equalsIgnoreCase(res.getStatus() +""))) {
				String[] split_string = res.getBody().split("\\.");
				if (split_string.length > 1) {
					String base64EncodedBody = split_string[1];
					JSONObject decodedRes = new JSONObject(new String(Base64Utils.decode(base64EncodedBody.getBytes())));
					System.out.println(decodedRes);
					
					
					ClfPaymentsRefund clfPaymentsRefund=new  ClfPaymentsRefund();
					clfPaymentsRefund.setTransactionResponse(res.getBody());
					clfPaymentsRefund.setOrderId(decodedRes.getString("orderid"));
					clfPaymentsRefund.setRefundStatus(decodedRes.getString("refund_status"));
					clfPaymentsRefund.setRefundid(decodedRes.getString("refundid"));
					clfPaymentsRefund.setObjectid(decodedRes.getString("objectid"));
					clfPaymentsRefund.setRefundAmount(decodedRes.getDouble("refund_amount"));
					clfPaymentsRefund.setTransactionDate(decodedRes.getString("refund_date"));
					clfPaymentsRefund.setCurrency(decodedRes.getString("currency"));
					clfPaymentsRefund.setMercId(decodedRes.getString("mercid"));
					clfPaymentsRefund.setAmount(decodedRes.getDouble("txn_amount"));
					clfPaymentsRefund.setMercRefundRefNo(decodedRes.getString("merc_refund_ref_no"));
					clfPaymentsRefund.setTransactionId(decodedRes.getString("transactionid"));
					
					updateGetRefundResponse(clfPaymentsRefund);
				}

				apiResp.setData(res.getBody());
				apiResp.setStatus(0);
			}else {
				apiResp.setMessage(res.getBody());
				apiResp.setStatus(1);
			}
		} catch (Exception e) {
			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
		return respObj;
	}

	@Override
	public void getRefundDetails() {
		List<String> merchantIds = new ArrayList<String>();
		List<String> refundIds = new ArrayList<String>();
		PaymentGatewayDetails paymentGatewayDetails = commonService.populatePaymentGatewayDetails("PRD");
		if (paymentGatewayDetails.getClientId() != null && !paymentGatewayDetails.getClientId().isEmpty()
				&& paymentGatewayDetails.getClientSecret() != null
				&& !paymentGatewayDetails.getClientSecret().isEmpty()) {
			Map<String, Object> header = new HashMap<>();
			header.put("alg", "HS256");
			header.put("typ", "JWT");
			header.put("clientid", paymentGatewayDetails.getClientId());
			header.put("kid", "HMAC");
			List<String> statusList = new ArrayList<>();
			statusList.add("0699");
			 List<Object[]> payments = paymentRefundRepo.getPayments(statusList);
			 for(Object[] obj:payments) {
				 merchantIds.add((String) obj[0]);
                 refundIds.add((String) obj[1]);
			 }
			
			JSONObject retrievePayload = new JSONObject();
			for (Object[] obj : payments) {
				try {
					long timeStamp = new Date().getTime();
					Map<String, String> headerMap = new HashMap<>();
					headerMap.put("content-Type", "application/jose");
					headerMap.put("Bd-Timestamp", timeStamp + "");
					headerMap.put("Bd-Traceid", timeStamp + "ABD1K");
					headerMap.put("accept", "application/jose");
					retrievePayload.put("mercid", (String)obj[0]);
					retrievePayload.put("refundid", (String) obj[1]);
					String encToken = jWTUtils.generateJwtToken(retrievePayload, header,
							paymentGatewayDetails.getClientSecret());
					getAndUpdateEncodedResponse(encToken);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public GenericApiResponse updateGetRefundResponse(ClfPaymentsRefund payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		genericApiResponse.setMessage(GeneralConstants.SUCCESS);
		try {
			if (payload.getTransactionResponse() != null && !payload.getTransactionResponse().isEmpty()) {
				//verify encToken
				PaymentGatewayDetails paymentGatewayDetails = commonService.populatePaymentGatewayDetails("PRD");
				if(paymentGatewayDetails.getClientId() != null && paymentGatewayDetails.getClientSecret() != null && !paymentGatewayDetails.getClientId().isEmpty() && !paymentGatewayDetails.getClientSecret().isEmpty()){
					if(!jWTUtils.verifyJwtToken(payload.getTransactionResponse(), paymentGatewayDetails.getClientSecret()))
					{
						genericApiResponse.setStatus(1);
						genericApiResponse.setMessage("Signature verification failed");
						return genericApiResponse;
					}
				}
				String[] split_string = payload.getTransactionResponse().split("\\.");
				if (split_string.length > 1) {
					String base64EncodedBody = split_string[1];

					JSONObject decodedRes = new JSONObject(
							new String(Base64Utils.decode(base64EncodedBody.getBytes())));
					PaymentsRefund paymentsRefund = paymentRefundRepo.
							getPaymentDetailsOnRefundId(decodedRes.getString("refundid"));
					if(payload!=null) {
						BeanUtils.copyProperties(payload, paymentsRefund);
					}else {
						paymentsRefund = new PaymentsRefund();
						paymentsRefund.setId(UUID.randomUUID().toString());
					}
					String dateString = (String) decodedRes.get("transaction_date");
					String refundDateString = (String) decodedRes.get("refund_date");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Date transactionDate = dateFormat.parse(dateString);
					Date refundDate=dateFormat.parse(refundDateString);
					paymentsRefund.setOrderId(decodedRes.getString("orderid"));
					paymentsRefund.setRefundStatus(decodedRes.getString("refund_status"));
					paymentsRefund.setRefundId(decodedRes.getString("refundid"));
					paymentsRefund.setObjectId(decodedRes.getString("objectid"));
					paymentsRefund.setRefundAmount(decodedRes.getDouble("refund_amount"));
					paymentsRefund.setTransaction_date(transactionDate);
					paymentsRefund.setRefundDate(refundDate);
					paymentsRefund.setCurrency(decodedRes.getString("currency"));
					paymentsRefund.setMercId(decodedRes.getString("mercid"));
					paymentsRefund.setTxnAmount(decodedRes.getDouble("txn_amount"));
					paymentsRefund.setMercRefundRefno(decodedRes.getString("merc_refund_ref_no"));
					paymentsRefund.setTransaction_id(decodedRes.getString("transactionid"));
					paymentsRefund.setEncodedResponse(payload.getTransactionResponse());
					paymentRefundRepo.save(paymentsRefund);
//					baseDao.saveOrUpdate(paymentsRefund);
					genericApiResponse.setData(paymentsRefund);
					
				}
			}else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("Error while updating payment status update");
			}
		} catch (Exception e) {
			e.printStackTrace();
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Error while updating payment status update:"+e.getMessage());
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getPaymentsAndRefundDetails(String orderId, LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		PaymentsAndRefund paymentsAndRefund=new PaymentsAndRefund();
		List<PaymentsAndRefund> paymentAndRefundList=new ArrayList<PaymentsAndRefund>();
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			List<Object[]> paymentDetailByOrderId = clfPaymentResponseTrackingRepo.getPaymentDetailByOrderId(orderId);
			if(!paymentDetailByOrderId.isEmpty()) {
				for(Object[] obj:paymentDetailByOrderId) {
						paymentsAndRefund.setTransactionId((String) obj[0]);						
						paymentsAndRefund.setOrderId((String) obj[1]);
						paymentsAndRefund.setAmount((String) obj[2]);
						Date date = originalFormat.parse((String) obj[3]);
						paymentsAndRefund.setTransactionDate(targetFormat.format(date));						
						paymentsAndRefund.setBankRefNo((String) obj[4]);
						paymentsAndRefund.setTransactionErrorDesc((String) obj[5]);
						paymentsAndRefund.setBookingLocation((String) obj[6]);
						List<PaymentsRefund> paymentsRefund = paymentRefundRepo.getPaymentsDetailsByOrderId((String) obj[1]);
						if(!paymentsRefund.isEmpty()) {
							for(PaymentsRefund paymentRefundDetails:paymentsRefund) {
								paymentsAndRefund.setMercRefundRefNo(paymentRefundDetails.getMercRefundRefno());
								paymentsAndRefund.setRefundDate(paymentRefundDetails.getRefundDate());
								paymentsAndRefund.setRefundStatus(paymentRefundDetails.getRefundStatus());
								paymentsAndRefund.setRefundId(paymentRefundDetails.getRefundId());
								paymentsAndRefund.setRefundAmount(paymentRefundDetails.getRefundAmount());
								paymentsAndRefund.setCurrency(paymentRefundDetails.getCurrency());
								paymentsAndRefund.setMercId(paymentRefundDetails.getMercId());
								paymentsAndRefund.setObjectId(paymentRefundDetails.getObjectId());
								paymentsAndRefund.setRefundDetailsPresent(true);								
							}							
						}
						paymentAndRefundList.add(paymentsAndRefund);				
				}
			}				
			if(!paymentAndRefundList.isEmpty()) {
				genericApiResponse.setStatus(0);
                genericApiResponse.setMessage("Payment Details Fetched Successfully");
                genericApiResponse.setData(paymentAndRefundList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			genericApiResponse.setStatus(1);
            genericApiResponse.setErrorcode("Error While fetching details");
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getTransactionDetailsOnOrderId(String orderId, LoggedUser loggedUser,Boolean linkFlag) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			if(orderId != null && !orderId.isEmpty()) {
				ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo.getTransactionDetOnSecOrderId(orderId);
				if(clfPaymentResponseTracking != null) {
					
					if("success".equalsIgnoreCase(clfPaymentResponseTracking.getPaymentStatus())){
						genericApiResponse.setStatus(1);
						genericApiResponse.setMessage("Your transaction is Completed");
						return genericApiResponse;
					}
					if(linkFlag && clfPaymentResponseTracking.getLinkFlag() != null && "true".equalsIgnoreCase(clfPaymentResponseTracking.getLinkFlag()+"")) {
//						clfPaymentResponseTracking.setMarkAsDelete(true);
//						clfPaymentResponseTrackingRepo.save(clfPaymentResponseTracking);
						genericApiResponse.setMessage("Payment Link got Expired.");
						genericApiResponse.setStatus(1);
						return genericApiResponse;
					}
					Date createdTs = clfPaymentResponseTracking.getCreatedTs();
					if (createdTs != null) {
						Date now = new Date();
//						long tenMinutesInMillis = 10 * 60 * 1000;
						long tenMinutesInMillis = 5 * 60 * 1000;//5 minutesInMillis
						long differenceInMillis = now.getTime() - createdTs.getTime();
						boolean flag = differenceInMillis > tenMinutesInMillis;
						System.out.println(flag);
						if(flag) {
							clfPaymentResponseTracking.setMarkAsDelete(true);
							clfPaymentResponseTrackingRepo.save(clfPaymentResponseTracking);
//							baseDao.saveOrUpdate(clfPaymentResponseTracking);
							genericApiResponse.setStatus(0);
						}else {
							genericApiResponse.setMessage("Your transaction is in progress. Please try again after some time.");
							genericApiResponse.setStatus(1);
						}
					}
				}else {
						genericApiResponse.setStatus(0);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return genericApiResponse;
	}

	
}
