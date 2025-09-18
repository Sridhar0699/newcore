package com.portal.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;
import com.portal.org.models.ListOfOrganization;
import com.portal.user.models.ListOfOnlineUsers;
import com.portal.user.models.PasswordDetails;
import com.portal.user.models.PasswordRestRequest;
import com.portal.user.models.ResetKeyStatus;
import com.portal.user.models.UserDetails;
import com.portal.user.models.UserStatus;
import com.portal.user.to.UserTo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * User API
 * 
 * @author Sathish Babu D
 *
 */
@Api(value = "Users", description = "User management APIs")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface UserApi {

	/**
	 * Get user details
	 * 
	 * @param user_id
	 * @return
	 */
	@ApiOperation(value = "Get the user details", notes = "Get the specific user details", response = UserDetails.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getUserDetails(
			@NotNull @ApiParam(value = "Logon id", required = true) @RequestParam(value = "logon_id", required = true) String logon_id);

	/**
	 * Add new user details
	 * 
	 * @param payload
	 * @return
	 */
	@ApiOperation(value = "Add new user details", notes = "Add the new user details", response = GenericApiResponse.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/user", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> addUserDetails(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody UserDetails payload);

	/**
	 * Update existed user details
	 * 
	 * @param payload
	 * @return
	 */
	@ApiOperation(value = "Update user details", notes = "Update the existed user details", response = GenericApiResponse.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/user", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> updateUserDetails(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody UserDetails payload);

	/**
	 * Send the reset password link to users
	 * 
	 * @param logon_id
	 * @return
	 * ===============using this api
	 */
	@ApiOperation(value = "Reset password link", notes = "Send the reset password link to users", response = Void.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Reset request submitted successfully", response = Void.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/users/reset/link", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> sendRestPasswordLink(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody PasswordRestRequest payload);

	/**
	 * Validate the forgot password reset key
	 * 
	 * @param reset_key
	 * @return
	 * ==========using this api
	 */
	@ApiOperation(value = "Validate the reset key", notes = "Validate the forgot password reset key", response = ResetKeyStatus.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = ResetKeyStatus.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/users/resetpwd/{reset_key}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> validatePasswordResetKey(
			@NotNull @ApiParam(value = "Password reset key", required = true) @PathVariable(value = "reset_key", required = true) String reset_key);

	/**
	 * Update the user password details
	 * 
	 * @param payload
	 * @return
	 * ==========using this api
	 */
	@ApiOperation(value = "Update the user password details", notes = "Update the user password details", response = Void.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/users/resetpwd", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> updateUserPassword(
			@NotNull @ApiParam(value = "User password details", required = true) @RequestBody PasswordDetails payload);

	/**
	 * Find the user status of given logon id
	 * 
	 * @param logonId
	 * @return
	 */
	@ApiOperation(value = "Find the user status", notes = "Find the user status of given logon id", response = UserStatus.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserStatus.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user/status", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getUserStatus(
			@NotNull @ApiParam(value = "User logon id", required = true) @RequestParam(value = "logon_id", required = true) Integer logon_id,
			@NotNull @ApiParam(value = "Selected Business place ids", required = true) @RequestParam(value = "selectedBpIds", required = true) String selectedBpIds);

	/**
	 * Get the list of business partners by user
	 * 
	 * @param user_id
	 * @return
	 */
	@ApiOperation(value = "Get business partners", notes = "Get the list of business partners by user", response = ListOfOrganization.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = ListOfOrganization.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user/bp/{user_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getBusinessPartnersByUser(
			@NotNull @ApiParam(value = "User id", required = true) @PathVariable(value = "user_id", required = true) Integer user_id);

	/**
	 * Lock or Unlock user account
	 * 
	 * @param user_id
	 * @param action
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Lock or Unlock user account", notes = "Lock or Unlock user account", response = GenericApiResponse.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Reset request submitted successfully", response = Void.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/user/{action}/status", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> lockOrUnlockUser(
			@NotNull @ApiParam(value = "User id", required = true) @RequestParam(value = "user_id", required = true) Integer user_id,
			@NotNull @ApiParam(value = "Action(LOCK/UNLOCK)", required = true) @PathVariable(value = "action", required = true) String action);

	/**
	 * Get online user details
	 * 
	 * @param org_id
	 * @param bp_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Get online user details", notes = "Get online user details", response = ListOfOnlineUsers.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = ListOfOnlineUsers.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user/online", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getOnlineUsers();


	@ApiOperation(value = "Get user login  details", notes = "Get user login  details", response = ListOfOnlineUsers.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = ListOfOnlineUsers.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user/hstry", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getUserLoginHistory(
			@NotNull @ApiParam(value = "Login id", required = true) @RequestParam(value = "login_id", required = true) Integer login_id,
			@NotNull @ApiParam(value = "From Date", required = true) @RequestParam(value = "frm_date", required = true) String frm_date,
			@NotNull @ApiParam(value = "To Date", required = true) @RequestParam(value = "to_date", required = true) String to_date);

	@ApiOperation(value = "Get approver users", notes = "Get approver users", response = ListOfOnlineUsers.class, tags = {
			"Users", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = ListOfOnlineUsers.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user/approvers", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getApprovers();
	
	
	/**
	 * User Registration
	 */
	@ApiOperation(value = "Register user", notes = "Register new user", response = UserDetails.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user/registration", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> selfRegister(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody UserTo payload);

	@ApiOperation(value = "Generate Captcha", notes = "Return captcha", response = Void.class, tags = {
			"process", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = Void.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/process/captcha", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> generateAndVerifyCaptha(HttpServletRequest request, HttpServletResponse response);
	
	
	@ApiOperation(value = "Get the user logged in history", notes = "Get the specific user details logged history", response = UserDetails.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user/loginhistory", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getUserLoggedHistory();
	
	@ApiOperation(value = "Create Or Update Customer", notes = "Create Or Update Customer", response = UserDetails.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/user/create/customer", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> createOrUpdateCustomer(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody List<UserTo> payload);
	
	/**
	 * User Registration
	 */
	@ApiOperation(value = "Create SSP user", notes = "Create SSP user", response = UserDetails.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/create/ssp", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> createSSPCustomer(
			@NotNull @ApiParam(value = "User details", required = true) @RequestBody UserTo payload);
	
	
//	@ApiOperation(value = "Create SSP user", notes = "Create SSP user", response = UserDetails.class, tags = {
//			"Users", })
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserDetails.class),
//			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
//			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
//	@RequestMapping(value = "/get/usersbybookingcode", produces = { "application/json" }, method = RequestMethod.POST)
//	ResponseEntity<?> getUsersByBookingCode(
//			@NotNull @ApiParam(value = "User details", required = true) @RequestBody UserTo payload);
	
	
	@ApiOperation(value = "Get Rms Approver user", notes = "Get Rms Approver user", response = UserDetails.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/get/usersbybookingcode", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getRmsApprovers();
	
	
	@ApiOperation(value = "Get Executive Created Vendors", notes = "Get Executive Created Vendors", response = UserDetails.class, tags = {
			"Users", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = UserDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/get/usersbyexecutive", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getExecutiveCreatedUsersList();

	
}
