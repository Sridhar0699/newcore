package com.portal.da.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portal.clf.models.AddToCartRequest;
import com.portal.clf.models.ClassifiedStatus;
import com.portal.clf.models.ClassifiedsOrderItemDetails;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.clf.models.ErpClassifieds;
import com.portal.common.models.GenericApiResponse;
import com.portal.da.service.DisplayService;
import com.portal.nm.model.Notifications;
import com.portal.nm.websocket.WebSocketDao;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;

@RestController
public class DisplayApiController implements DisplayApi{

	private static final Logger logger = LogManager.getLogger(DisplayApiController.class);
	
	@Autowired
	private DisplayService displayService;
	
	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired
	private WebSocketDao webSocketDao;
	
	@Override
	public ResponseEntity<?> addDisplayOrderToCart(AddToCartRequest payload) {
		String METHOD_NAME = "addDisplayOrderToCart";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();

		try {

			apiResp = displayService.addDisplayItemToCart(payload,loggedUser);

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
	public ResponseEntity<?> getDisplayAdsList(@RequestBody DashboardFilterTo payload) {
		String METHOD_NAME = "getDisplayAdsList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getDisplayAdsList(loggedUser,payload);

		} catch (Exception e) {

			logger.error("Error while getting dashboard list: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> getDisplayAdsDashboardCounts(DashboardFilterTo payload) {
		
		String METHOD_NAME = "getDashboardCounts";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getDisplayAdsDashboardCounts(loggedUser, payload);

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
	public ResponseEntity<?> getDownloadStatusList(@RequestBody DashboardFilterTo payload) {
		String METHOD_NAME = "getDownloadStatusList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getDownloadStatusList(loggedUser,payload);

		} catch (Exception e) {

			logger.error("Error while getting dashboard list: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> getDisplayAdsPendingPaymentList(@RequestBody DashboardFilterTo payload) {
		String METHOD_NAME = "getPendingPaymentList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getDisplayAdsPendingPaymentList(loggedUser,payload);

		} catch (Exception e) {

			logger.error("Error while getting payment pending list: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> getDisplatRates(ClassifiedsOrderItemDetails payload) {
		String METHOD_NAME = "getDisplatRates";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getDisplatRates(payload);

		} catch (Exception e) {

			logger.error("Error while getting classified rates: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> getDisplayCartItems() {
		String METHOD_NAME = "getDisplayCartItems";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getDisplayCartItems(loggedUser);

		} catch (Exception e) {

			logger.error("Error while getting cart items: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> getDisplayPendingCartCount() {
		String METHOD_NAME = "getDisplayPendingCartCount";
		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {
			Notifications notification = new Notifications();
			notification.setUserId(loggedUser.getUserId());
			long count = webSocketDao.getDisplayPendingCartCount(notification);
			apiResp.setStatus(0);
			apiResp.setMessage("Success");
			apiResp.setData(count);
			return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error while Items Cart Count: " + ExceptionUtils.getStackTrace(e));
			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}


	@Override
	public ResponseEntity<?> viewDisplayAdByItemId(@NotNull String itemId) {

		String METHOD_NAME = "viewClassifiedsByItemId";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.viewDaItem(loggedUser,itemId);

		} catch (Exception e) {

			logger.error("Error while getting Cart Details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}


	@Override
	public ResponseEntity<?> deleteDisplayAd(@NotNull String itemId) {

		String METHOD_NAME = "deleteDisplayAd";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.deleteClassified(loggedUser,itemId);

		} catch (Exception e) {

			logger.error("Error while removing classifieds: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}
	
	@Override
	public ResponseEntity<?> getDisplayAdEncodedString(String orderId) {
		String METHOD_NAME = "getDisplayAdEncodedString";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getDisplayAdEncodedString(orderId,loggedUser);

		} catch (Exception e) {

			logger.error("Error while getting Encoded String Details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> sendDisplayEmailToSchedulingTeam(@NotNull String orderId) {
		String METHOD_NAME = "sendDisplayEmailToSchedulingTeam";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.sendDisplayEmailToSchedulingTeam(orderId,loggedUser);

		} catch (Exception e) {

			logger.error("Error while Sending Email to Scheduling Team: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> approveDisplayAds(ClassifiedStatus payload) {
		String METHOD_NAME = "approveDisplayAds";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.approveDisplayAds(loggedUser,payload);

		} catch (Exception e) {

			logger.error("Error while approving classifieds Details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> downloadAdsDisplayAds(@RequestBody DashboardFilterTo payload) {
		String METHOD_NAME = "downloadAdsDocument";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.downloadAdsPdfDocument(payload);

		} catch (Exception e) {

			logger.error("Error while approving classifieds Details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}


	@Override
	public ResponseEntity<?> getCustomerDetails(@NotNull String mobileNo) {
		String METHOD_NAME = "getCustomerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = displayService.getCustomerDetails(mobileNo);

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
	public ResponseEntity<?> getVendorCommission(DashboardFilterTo payload) {
		String METHOD_NAME = "getVendorCommission";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getVendorCommission(loggedUser,payload);

		} catch (Exception e) {

			logger.error("Error while getting vendor commission: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}


	@Override
	public ResponseEntity<?> updateDownloadStatus(@NotNull String itemId) {
		String METHOD_NAME = "updateDownloadStatus";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.updateDownloadStatus(loggedUser,itemId);

		} catch (Exception e) {

			logger.error("Error while updating download status: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}


	@Override
	public ResponseEntity<?> uploadVendorCommision(HttpServletRequest request) {
		String METHOD_NAME = "uploadVendorCommision";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = displayService.uploadVendorCommision(request);
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}


	@Override
	public ResponseEntity<?> downloadTemplateHeaders(HttpServletRequest request, HttpServletResponse response) {
		String METHOD_NAME = "generateExcelForHeaders";
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		apiResp = displayService.downloadTemplateHeaders(response);
		logger.info("Exit from the method: " + METHOD_NAME);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<?> getSSPSummaryReports(DashboardFilterTo payload) {
		String METHOD_NAME = "getSSPSummaryReports";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = displayService.getSSPSummaryReports(loggedUser,payload);

		} catch (Exception e) {

			logger.error("Error while getting vendor commission: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}


	@Override
	public ResponseEntity<?> getClassifiedErpData(DashboardFilterTo payload) {
		String METHOD_NAME = "getClassifiedErpData";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			Map<String, ErpClassifieds> classifiedOrderDetailsForSSPErp = displayService.getDisplayOrderDetailsForErp(payload.getOrderIds());
			displayService.sendDisplayMailToCustomer(classifiedOrderDetailsForSSPErp, null, loggedUser, null);
			apiResp.setData(classifiedOrderDetailsForSSPErp.values());

		} catch (Exception e) {

			logger.error("Error while getting vendor commission: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}



}
