package com.portal.rms.controller;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.portal.clf.controller.ClassifiedApiController;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.rms.model.CreateOrder;
import com.portal.rms.model.OtpModel;
import com.portal.rms.model.RmsApproveModel;
import com.portal.rms.model.RmsDashboardFilter;
import com.portal.rms.model.RmsModel;
import com.portal.rms.model.RmsPaymentLinkModel;
import com.portal.rms.model.RmsRateModel;
import com.portal.rms.service.RmsService;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;
import com.portal.workflow.model.WfRequest;

@RestController
public class RmsApiController implements RmsApi {

private static final Logger logger = LogManager.getLogger(ClassifiedApiController.class);
	
	@Autowired
	private RmsService rmsService;
	
	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired(required = true)
	private Environment prop;
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public ResponseEntity<?> getDashboardCounts(RmsDashboardFilter payload) {
		String METHOD_NAME = "getDashboardCounts";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = rmsService.getDashboardCounts(loggedUser, payload);

		} catch (Exception e) {

			logger.error("Error while getting dashboard counts: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;

	}

	@Override
	public ResponseEntity<?> getDashboardList(RmsDashboardFilter payload) {
		String METHOD_NAME ="getDashboardList";
		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {
			apiResp=rmsService.getRmsClassifiedList(loggedUser,payload);
		} catch (Exception e) {
			logger.error("Error while getting dashboard list "+ExceptionUtils.getStackTrace(e));
			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}
		respObj=new ResponseEntity<GenericApiResponse>(apiResp,HttpStatus.OK);
		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}
	
	
	@Override
	public ResponseEntity<?> getDetailsById(@NotNull String adId) {
		String METHOD_NAME ="getDetailsById";
		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj=null;
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		
		try {
			apiResp=rmsService.getRmsClassifiedsByAdId(loggedUser,adId);
			
			} catch (Exception e) {
			
				logger.error("Error while getting rms classified items "+ExceptionUtils.getStackTrace(e));
				apiResp.setStatus(1);
				apiResp.setMessage(prop.getProperty("GEN_002"));
				apiResp.setErrorcode("GEN_002");
		}
		
		respObj=new ResponseEntity<GenericApiResponse>(apiResp,HttpStatus.OK);
		logger.info("Exit from the method: " + METHOD_NAME);
		
		return respObj;
	}

	@Override
	public ResponseEntity<?> addRmsClassifiedOrderToCart(CreateOrder payload) {
		String METHOD_NAME = "addRmsClassifiedOrderToCart";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();

		try {

			apiResp = rmsService.addRmsClassifiedItemToCart(payload,loggedUser);

		} catch (Exception e) {

			logger.error("Error while getting add order to cart: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getCustomerDetails(String clientCode) {
		
		String METHOD_NAME = "getCustomerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();

		try {

			apiResp = rmsService.getCustomerDetails(clientCode,loggedUser);

		} catch (Exception e) {

			logger.error("Error while getting customer details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getRmsRates(RmsRateModel payload) {
		String METHOD_NAME = "getRatesAndLines";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = rmsService.getRmsRates(payload);

		} catch (Exception e) {

			logger.error("Error while getting Rms rates: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> genrateOTP(OtpModel payload) {
		String METHOD_NAME = "genrateOTP";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = rmsService.genrateOTP(payload);

		} catch (Exception e) {

			logger.error("Error while genrate OTP: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> validateOTP(OtpModel payload) {
		String METHOD_NAME = "validateOTP";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = rmsService.validateOTP(payload);

		} catch (Exception e) {

			logger.error("Error while genrate OTP: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> syncronizeRmsSAPData(@NotNull RmsModel payload) {
		@SuppressWarnings("unchecked")
		GenericApiResponse apiResp = rmsService.syncronizeRmsSAPData(commonService.getRequestHeaders(),payload);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> generateSendPaymentLink(RmsPaymentLinkModel payload) {
		String METHOD_NAME = "generateSendPaymentLink";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			apiResp = rmsService.generateSendPaymentLink(payload);
			
		} catch (Exception e) {
			logger.error("Error while genrating Link: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	@Override
	public ResponseEntity<?> getFixedFormatsMasters(RmsRateModel payload) {
		String METHOD_NAME = "getFixedFormatsMasters";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		try {

			apiResp = rmsService.getFixedFormatsMasters(payload);

		} catch (Exception e) {

			logger.error("Error while getting Rms rates: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getEditionTypeWiseDistricts(@NotNull Integer editionType, Integer formatType,Integer addType,String stateCode) {
		String METHOD_NAME = "getEditionTypeWiseDistricts";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		try {

			apiResp = rmsService.getEditionTypeWiseDistricts(editionType,formatType,addType,stateCode);

		} catch (Exception e) {

			logger.error("Error while getting Editions Edition type and Fixed format type wise Districts: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getPagePositionsOnEditionType(@NotNull Integer editionType) {
		String METHOD_NAME = "getPagePositionsOnEditionType";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		try {

			apiResp = rmsService.getPagePositionsOnEditionType(editionType);

		} catch (Exception e) {

			logger.error("Error while getting Page Positions on Edition Type: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> approveOrRejectRmsOrder(RmsApproveModel payload) {
		String METHOD_NAME = "approveOrRejectRmsOrder";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		try {

			apiResp = rmsService.approveOrRejectRmsOrder(payload);

		} catch (Exception e) {

			logger.error("Error while approve/reject the order: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getApprovalInbox(RmsDashboardFilter payload) {
		String METHOD_NAME = "getApprovalInbox";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			
			apiResp = rmsService.getApprovalInbox(userContext.getLoggedUser(),payload);

		} catch (Exception e) {

			logger.error("Error while getting approval inbox: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getDraftDetailView(@NotNull String orderId) {
		String METHOD_NAME = "getDraftDetailView";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			
			apiResp = rmsService.getDraftDetailView(orderId);

		} catch (Exception e) {

			logger.error("Error while getting Draft detail view: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}


	@Override
	public ResponseEntity<?> getRmsListView(DashboardFilterTo payload) {
		String METHOD_NAME ="getRmsListView";
		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {
			apiResp=rmsService.getRmsListView(loggedUser,payload);
		} catch (Exception e) {
			logger.error("Error while getting dashboard list "+ExceptionUtils.getStackTrace(e));
			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}
		respObj=new ResponseEntity<GenericApiResponse>(apiResp,HttpStatus.OK);
		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	@Override
	public ResponseEntity<?> deleteDraftOrders(String orderId) {
		String METHOD_NAME = "deleteDraftOrders";
		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {
			apiResp = rmsService.deleteDraftOrders(loggedUser, orderId);
		} catch (Exception e) {
			logger.error("Error while Deleting Draft Details " + ExceptionUtils.getStackTrace(e));
			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	@Override
	public ResponseEntity<?> downloadPdf(@NotNull String orderId) {
		String METHOD_NAME ="getDetailsById";
		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj=null;
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		
		try {
			apiResp=rmsService.downloadPdf(loggedUser,orderId);
			
			} catch (Exception e) {
			
				logger.error("Error while getting rms classified items "+ExceptionUtils.getStackTrace(e));
				apiResp.setStatus(1);
				apiResp.setMessage(prop.getProperty("GEN_002"));
				apiResp.setErrorcode("GEN_002");
		}
		
		respObj=new ResponseEntity<GenericApiResponse>(apiResp,HttpStatus.OK);
		logger.info("Exit from the method: " + METHOD_NAME);
		
		return respObj;
	}

	@Override
	public ResponseEntity<?> getCustomerDetailsByMobileNumber(String mobileNo) {

		
		String METHOD_NAME = "getCustomerDetailsByMobileNumber";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = rmsService.getCustomerDetailsByMobileNo(mobileNo);

		} catch (Exception e) {

			logger.error("Error while getting customer details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> isMobileNumberDuplicate(String mobileNo) {

		String METHOD_NAME = "isMobileNumberDuplicate";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = rmsService.isMobileNumberDuplicate(mobileNo);

		} catch (Exception e) {

			logger.error("Error while validating duplicate mobile numbers: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> validateAccessKey(@NotNull WfRequest payload) {
		String METHOD_NAME = "validateAccessKey";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = rmsService.validateAccessKey(payload);

		} catch (Exception e) {

			logger.error("Error while validating duplicate mobile numbers: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	

}
