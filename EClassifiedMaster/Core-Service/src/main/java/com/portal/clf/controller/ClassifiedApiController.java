package com.portal.clf.controller;

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
import com.portal.clf.service.ClassifiedService;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.datasecurity.DataSecurityService;
import com.portal.nm.model.Notifications;
import com.portal.nm.websocket.WebSocketDao;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;

/**
 * Classified API controller
 * 
 * @author Sathish Babu D
 *
 */

@RestController
public class ClassifiedApiController implements ClassifiedApi {

	private static final Logger logger = LogManager.getLogger(ClassifiedApiController.class);

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private LoggedUserContext userContext;

	@Autowired(required = true)
	private DataSecurityService dataSecurityService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ClassifiedService classifiedService;
	
	@Autowired
	private WebSocketDao webSocketDao;

	@Override
	public ResponseEntity<?> getDashboardList(@RequestBody DashboardFilterTo payload) {
		String METHOD_NAME = "getClassifieds";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getClassifiedList(loggedUser,payload);

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
	public ResponseEntity<?> addClassifiedOrderToCart(AddToCartRequest payload) {
		String METHOD_NAME = "addClassifiedOrder";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();

		try {

			apiResp = classifiedService.addClassifiedItemToCart(payload,loggedUser);

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
	public ResponseEntity<?> getCartItems() {
		String METHOD_NAME = "addClassifiedOrder";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getCartItems(loggedUser);

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
	public ResponseEntity<?> getClassifiedTypes() {
		String METHOD_NAME = "getClassifiedTypes";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = classifiedService.getClassifiedTypes();

		} catch (Exception e) {

			logger.error("Error while getting classified types: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getClassifiedTemplates(String id) {
		String METHOD_NAME = "getClassifiedTypes";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = classifiedService.getClassifiedTemplates(id);

		} catch (Exception e) {

			logger.error("Error while getting classified templates: " + ExceptionUtils.getStackTrace(e));

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

			apiResp = classifiedService.getCustomerDetails(mobileNo);

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
	public ResponseEntity<?> getDashboardCounts(DashboardFilterTo payload) {
		
		String METHOD_NAME = "getDashboardCounts";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getDashboardCounts(loggedUser, payload);

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
	public ResponseEntity<?> getRatesAndLines(ClassifiedsOrderItemDetails payload) {
		String METHOD_NAME = "calculateRateAndLines";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getRatesAndLines(payload);

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
	public ResponseEntity<?> getPaymentHistory() {
		String METHOD_NAME = "getPaymentHistory";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getPaymentHistory(loggedUser);

		} catch (Exception e) {

			logger.error("Error while getting payment history: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> deleteClassified(@NotNull String itemId) {
		String METHOD_NAME = "deleteClassified";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.deleteClassified(loggedUser,itemId);

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
	public ResponseEntity<?> viewClassifiedsByItemId(@NotNull String itemId) {
		String METHOD_NAME = "viewClassifiedsByItemId";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.viewClfItem(loggedUser,itemId);

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
	public ResponseEntity<?> getPendingCartCount() {
		String METHOD_NAME = "getPendingCartCount";
		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {
			Notifications notification = new Notifications();
			notification.setUserId(loggedUser.getUserId());
			long count = webSocketDao.getPendingCartCount(notification);
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
	public ResponseEntity<?> approveClassifieds(ClassifiedStatus payload) {
		String METHOD_NAME = "approveClfByItemId";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.approveClassifieds(loggedUser,payload);

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
	public ResponseEntity<?> syncronizeSAPData(ClassifiedStatus payload) {
		@SuppressWarnings("unchecked")
		GenericApiResponse apiResp = classifiedService.syncronizeSAPData(commonService.getRequestHeaders(),payload);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> downloadAdsDocument(@RequestBody DashboardFilterTo payload) {
		String METHOD_NAME = "downloadAdsDocument";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.downloadAdsPdfDocument(payload);

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
	public ResponseEntity<?> getDownloadStatusList(@RequestBody DashboardFilterTo payload) {
		String METHOD_NAME = "getDownloadStatusList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getDownloadStatusList(loggedUser,payload);

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
	public ResponseEntity<?> getPendingPaymentList(@RequestBody DashboardFilterTo payload) {
		String METHOD_NAME = "getPendingPaymentList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getPendingPaymentList(loggedUser,payload);

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
	public ResponseEntity<?> getDownloadStatusListExcelDownload(@NotNull DashboardFilterTo payload) {
		String METHOD_NAME = "getDownloadStatusList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getDownloadStatusListExcelDownload(loggedUser,payload);

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
	public ResponseEntity<?> getEncodedString(String orderId) {
		String METHOD_NAME = "getEncodedString";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getEncodedString(orderId,loggedUser);

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
	public ResponseEntity<?> sendEmailToSchedulingTeam(@NotNull String orderId) {
		String METHOD_NAME = "sendEmailToSchedulingTeam";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.sendEmailToSchedulingTeam(orderId,loggedUser);

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
	public ResponseEntity<?> getPaymentSuccessAndPendingInOrder(@RequestBody DashboardFilterTo payload) {

		String METHOD_NAME = "getPaymentSuccessAndPendingInOrder";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = classifiedService.getPaymentSuccessOrderPendingData(payload);

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
	
	
}
