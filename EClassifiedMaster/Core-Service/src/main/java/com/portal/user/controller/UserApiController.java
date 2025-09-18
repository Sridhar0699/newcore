package com.portal.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.datasecurity.DataSecurityService;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;
import com.portal.user.models.PasswordDetails;
import com.portal.user.models.PasswordRestRequest;
import com.portal.user.models.UserDetails;
import com.portal.user.service.UserService;
import com.portal.user.to.UserTo;

import io.swagger.annotations.ApiParam;

/**
 * User API controller
 * 
 * @author Sathish Babu D
 *
 */
@Controller
@ComponentScan({ "com.portal.messages" })
@PropertySource(value = { "classpath:/com/portal/messages/messages.properties" })
public class UserApiController implements UserApi {

	private static final Logger logger = LogManager.getLogger(UserApiController.class);

	@Autowired(required = true)
	private UserService userService;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private DataSecurityService dataSecurityService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired(required = true)
	private LoggedUserContext userContext;

	/**
	 * Get user details
	 * 
	 * @param logon_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getUserDetails(
			@NotNull @ApiParam(value = "Logon id", required = true) @RequestParam(value = "logon_id", required = true) String logon_id) {

		String METHOD_NAME = "getUserDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		respObj = new ResponseEntity<GenericApiResponse>(userService.getUserDetails(logon_id), HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Add new user details
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> addUserDetails(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody UserDetails payload) {

		String METHOD_NAME = "addUserDetails";
		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;
		respObj = new ResponseEntity<GenericApiResponse>(userService.addUserDetails(payload), HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	/**
	 * Update existed user details
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> updateUserDetails(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody UserDetails payload) {

		String METHOD_NAME = "updateUserDetails";
		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;
		respObj = new ResponseEntity<GenericApiResponse>(userService.updateUserDetails(payload), HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	/**
	 * Send the reset password link for forgot password
	 * 
	 * @param logon_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> sendRestPasswordLink(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody PasswordRestRequest payload) {

		String METHOD_NAME = "sendRestPasswordLink";
		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;
		respObj = new ResponseEntity<GenericApiResponse>(userService.sendRestPasswordLink(payload), HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	/**
	 * Validate the forgot password reset key
	 * 
	 * @param reset_key
	 * @return
	 */
	@Override
	public ResponseEntity<?> validatePasswordResetKey(
			@NotNull @ApiParam(value = "Password reset key", required = true) @PathVariable(value = "reset_key", required = true) String reset_key) {

		String METHOD_NAME = "validatePasswordResetKey";
		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;
		respObj = new ResponseEntity<GenericApiResponse>(userService.validatePasswordResetKey(reset_key),
				HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	/**
	 * Update the user password details
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> updateUserPassword(
			@NotNull @ApiParam(value = "User password details", required = true) @RequestBody PasswordDetails payload) {

		String METHOD_NAME = "updateUserPassword";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		boolean isValidReq = true;

		String action = payload.getAction();

		if ("FORGOTPWD".equals(action) || "CREATEPWD".equals(action)) {

			if (payload.getResetKey() == null || payload.getResetKey().trim().isEmpty()) {

				isValidReq = false;

				apiResp.setMessage(prop.getProperty("USR_008"));
				apiResp.setErrorcode("USR_008");

			} else {

				apiResp = userService.validatePasswordResetKey(payload.getResetKey());

				if (apiResp.getStatus() != 0) {

					isValidReq = false;
				}
			}
		}

		if (isValidReq) {

			apiResp = userService.updateUserPassword(payload);
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	/**
	 * Find the user status of given logon id
	 * 
	 * @param logonId
	 * @return
	 */
	@Override
	public ResponseEntity<?> getUserStatus(
			@NotNull @ApiParam(value = "User logon id", required = true) @RequestParam(value = "logon_id", required = true) Integer logon_id,
			@NotNull @ApiParam(value = "Selected Business place ids", required = true) @RequestParam(value = "selectedBpIds", required = true) String selectedBpIds) {

		String METHOD_NAME = "getUserStatus";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = userService.getUserStatus(logon_id + "", commonService.getRequestHeaders().getOrgId(), selectedBpIds);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	/**
	 * Get the list of business partners by user
	 * 
	 * @param user_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getBusinessPartnersByUser(
			@NotNull @ApiParam(value = "User id", required = true) @PathVariable(value = "user_id", required = true) Integer user_id) {

		String METHOD_NAME = "getBusinessPartnersByUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		respObj = new ResponseEntity<GenericApiResponse>(userService.getBusinessPartnersByUser(user_id), HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	/**
	 * Lock or Unlock user account
	 * 
	 * @param user_id
	 * @param action
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> lockOrUnlockUser(
			@NotNull @ApiParam(value = "User id", required = true) @RequestParam(value = "user_id", required = true) Integer user_id,
			@NotNull @ApiParam(value = "Action(LOCK/UNLOCK)", required = true) @PathVariable(value = "action", required = true) String action) {

		String METHOD_NAME = "lockOrUnlockUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = userService.lockOrUnlockUser(user_id, action);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get online user details
	 * 
	 * @param org_id
	 * @param bp_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getOnlineUsers() {

		String METHOD_NAME = "getOnlineUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = userService.getOnlineUsers();

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);
		return respObj;
	}

	@Override
	public ResponseEntity<?> getUserLoginHistory(
			@NotNull @ApiParam(value = "Login id", required = true) @RequestParam(value = "login_id", required = true) Integer login_id,
			@NotNull @ApiParam(value = "From Date", required = true) @RequestParam(value = "frm_date", required = true) String frm_date,
			@NotNull @ApiParam(value = "To Date", required = true) @RequestParam(value = "to_date", required = true) String to_date) {

		String METHOD_NAME = "getUserLoginHistory";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = userService.getUserLoginHistory(login_id, frm_date, to_date);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getApprovers() {
		String METHOD_NAME = "getApprovers";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = userService.getApprovers();

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> selfRegister(UserTo payload) {
		String METHOD_NAME = "selfRegister";
		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = userService.selfRegister(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> generateAndVerifyCaptha(HttpServletRequest request, HttpServletResponse response) {
		GenericApiResponse apiResp = userService.generateAndValidateCaptcha();
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getUserLoggedHistory() {
		
		String METHOD_NAME = "getUserDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		respObj = new ResponseEntity<GenericApiResponse>(userService.getUserLoggedHistory(), HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;

	}

	@Override
	public ResponseEntity<?> createOrUpdateCustomer(@NotNull List<UserTo> payload) {
		String METHOD_NAME = "selfRegister";
		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = userService.createOrUpdateCustomer(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> createSSPCustomer(@NotNull UserTo payload) {
		String METHOD_NAME = "createSSPCustomer";
		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = userService.createSSPCustomer(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getRmsApprovers() 
	{
		String METHOD_NAME = "getRmsApprovers";
	logger.info("Entered into the method: " + METHOD_NAME);

	ResponseEntity<GenericApiResponse> respObj = null;

	GenericApiResponse apiResp = new GenericApiResponse();

	apiResp = userService.getRmsApprovers();

	respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

	logger.info("Exit from the method: " + METHOD_NAME);

	return respObj;
	}

	@Override
	public ResponseEntity<?> getExecutiveCreatedUsersList() {
		String METHOD_NAME = "getExecutiveCreatedUsersList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		try {

			apiResp = userService.getExecutiveCreatedListOfUsers(loggedUser);

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

//	@Override
//	public ResponseEntity<?> getUsersByBookingCode(@NotNull UserTo payload) {
//		String METHOD_NAME = "getUsersByBookingCode";
//		logger.info("Entered into the method: " + METHOD_NAME);
//
//		ResponseEntity<GenericApiResponse> respObj = null;
//
//		GenericApiResponse apiResp = new GenericApiResponse();
//
//		apiResp = userService.getUsersByBookingCode(payload);
//
//		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
//
//		logger.info("Exit from the method: " + METHOD_NAME);
//
//		return respObj;
//	}
}