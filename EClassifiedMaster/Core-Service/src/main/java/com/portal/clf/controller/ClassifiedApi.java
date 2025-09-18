package com.portal.clf.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.clf.models.AddToCartRequest;
import com.portal.clf.models.ClassifiedStatus;
import com.portal.clf.models.ClassifiedsOrderItemDetails;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Classifieds", description = "Manage Classifieds API")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface ClassifiedApi {

	/**
	 * Get classifieds
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get classifieds", notes = "Get classifieds", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/dashboard/list", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDashboardList(@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);

	/**
	 * Get classifieds
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Add classifieds to cart or order", notes = "Add classifieds to cart or order", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/addtocart", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> addClassifiedOrderToCart(
			@ApiParam(value = "Order Item Details", required = true) @RequestBody AddToCartRequest payload);
	
	/**
	 * Get classifieds
	 * 
	 * @return
	 */
	@ApiOperation(value = "Checkout Order", notes = "Checkout Order", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/cartitems", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getCartItems();
	
	/**
	 * Get classifieds types
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get classifieds types", notes = "Get classifieds", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/types", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getClassifiedTypes();
	
	/**
	 * Get classified Templates
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get classified Templates", notes = "Get classified Templates", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/templates", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getClassifiedTemplates(@NotNull @ApiParam(value = "id") @RequestParam String id);
	
	/**
	 * Get classified Templates
	 * 
	 * @param customerMobileNo
	 * @return
	 */
	@ApiOperation(value = "Get Customer Details", notes = "Get Customer Details", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/customer", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getCustomerDetails(
			@NotNull @ApiParam(value = "mobileNo") @RequestParam String mobileNo);
	
	/**
	 * Get Dashboard Counts
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get Dashboard Counts", notes = "Get Dashboard Counts", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/dashboard/counts", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDashboardCounts(@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);
	
	/**
	 * Add Classified Rate And lines calculation
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Add Classified Rate And lines calculation", notes = "Add Classified Rate And lines calculation", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/rates", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getRatesAndLines(@ApiParam(value = "Dashboard Request", required = true) @RequestBody ClassifiedsOrderItemDetails payload);
	
	/**
	 * Get Payment History
	 * 
	 * @param customerMobileNo
	 * @return
	 */
	@ApiOperation(value = "Get Payment History", notes = "Get Payment History", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/payments", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getPaymentHistory();
	
	/**
	 * Delete Classified
	 */
	@ApiOperation(value = "Delete Classifieds", notes = "Delete Classifieds", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/delete", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> deleteClassified(
			@NotNull @ApiParam(value = "itemId") @RequestParam String itemId);
	
	/**
	 * view classifieds by item id
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "View classifieds by item id", notes = "View classifieds by item id", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/viewClfItem", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> viewClassifiedsByItemId(
			@NotNull @ApiParam(value = "itemId") @RequestParam String itemId);
	
	/**
	 * Get Cart Count
	 * @return
	 */
	@ApiOperation(value = "Get Cart Count", notes = "Get Cart Count", response = Void.class, tags = {
			"Classifieds"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/clf/getcartcount", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getPendingCartCount();
	
	/**
	 * approve classifieds
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "approve classifieds", notes = "Approve classifieds", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/approveclf", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> approveClassifieds(
			@ApiParam(value = "Classified Status", required = true) @RequestBody ClassifiedStatus payload);
	
	
	/**
	 * SAP Syncronization
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Syncronize to  SAP", notes = "Syncronize to SAP", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/dataload/sync/sap", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> syncronizeSAPData(
			@NotNull @ApiParam(value = "Syncronize to SAP") @RequestBody ClassifiedStatus payload);
	
	/**
	 * Download Ads PDF Document
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Syncronize to  SAP", notes = "Syncronize to SAP", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/adspdf", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> downloadAdsDocument(
			@NotNull @ApiParam(value = "Download Ads payload") @RequestBody DashboardFilterTo payload);
	
	/**
	 * Get classifieds download status
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get classifieds downalod list", notes = "Get classifieds download list", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/download/status/list", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDownloadStatusList(
			@NotNull @ApiParam(value = "Download status payload") @RequestBody DashboardFilterTo payload);
	
	/**
	 * Get classifieds payment pending list
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get classifieds payment pending list", notes = "Get classifieds payment pending list", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/payment/list", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getPendingPaymentList(
			@NotNull @ApiParam(value = "payment pending payload") @RequestBody DashboardFilterTo payload);
	
	
	/**
	 * Get classifieds download status excel download
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get classifieds downalod list", notes = "Get classifieds download list", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/clf/download/status/excelList", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDownloadStatusListExcelDownload(
			@NotNull @ApiParam(value = "Download status payload") @RequestBody DashboardFilterTo payload);
	
	@ApiOperation(value = "Get Encoded String", notes = "Get Encoded String", response = Void.class, tags = { "Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/clf/getencodedstring", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getEncodedString(@NotNull @ApiParam(value = "orderId") @RequestParam String orderId);
	
	@ApiOperation(value = "Send Email ", notes = "Send Email", response = Void.class, tags = { "Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/clf/sendemailtoschedulingteam", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> sendEmailToSchedulingTeam(@NotNull @ApiParam(value = "orderId") @RequestParam String orderId);
	
	
	@ApiOperation(value = "Payment Success and Pending in Order Table", notes = "Payment Success and Pending in Order Table", response = Void.class, tags = { "Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/clf/paymentsuccesspending", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getPaymentSuccessAndPendingInOrder(@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);
}
