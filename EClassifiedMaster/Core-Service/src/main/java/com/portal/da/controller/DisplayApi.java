package com.portal.da.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

@Api(value = "Manage Display Ads", description = "Manage Display API")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface DisplayApi {

	/**
	 * Add Display classifieds
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Add Display Ads to cart or order", notes = "Add Display Ads to cart or order", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/addtocart", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> addDisplayOrderToCart(
			@ApiParam(value = "Order Item Details", required = true) @RequestBody AddToCartRequest payload);
	
	
	/**
	 * Get Display Ads Counts
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get Ads Counts", notes = "Get Ads Counts", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/dashboard/list", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDisplayAdsList(@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);
	
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
	@RequestMapping(value = "/da/dashboard/counts", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDisplayAdsDashboardCounts(@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);
	
	
	/**
	 * Get Display Ads download status
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get Display Ads downalod list", notes = "Get Display Ads download list", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/download/status/list", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDownloadStatusList(
			@NotNull @ApiParam(value = "Download status payload") @RequestBody DashboardFilterTo payload);
	
	
	/**
	 * Get Display Ads payment pending list
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Get Display Ads payment pending list", notes = "Get Display Ads payment pending list", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/payment/list", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDisplayAdsPendingPaymentList(
			@NotNull @ApiParam(value = "payment pending payload") @RequestBody DashboardFilterTo payload);
	
	/**
	 * Add Display Rate And lines calculation
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Add Display Rate And lines calculation", notes = "Add Display Rate And lines calculation", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/rates", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDisplatRates(@ApiParam(value = "Dashboard Request", required = true) @RequestBody ClassifiedsOrderItemDetails payload);
	
	
	/**
	 * Get Display Cart
	 * 
	 * @return
	 */
	@ApiOperation(value = "Display Checkout Order", notes = "Display Checkout Order", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/cartitems", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getDisplayCartItems();
	
	
	/**
	 * Get Display Cart Count
	 * @return
	 */
	@ApiOperation(value = "Get Display Cart Count", notes = "Get Display Cart Count", response = Void.class, tags = {
			"Display"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/da/getcartcount", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getDisplayPendingCartCount();
	
	
	@ApiOperation(value = "View classifieds by item id", notes = "View classifieds by item id", response = GenericApiResponse.class, tags = {
	"Display" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
	@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
	@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
@RequestMapping(value = "/da/viewdaItem", produces = { "application/json" }, method = RequestMethod.POST)
ResponseEntity<?> viewDisplayAdByItemId(
	@NotNull @ApiParam(value = "itemId") @RequestParam String itemId);
	
	
	/**
	 * Delete Display Ad
	 */
	@ApiOperation(value = "Delete Display Ad", notes = "Delete Display Ad", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/delete", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> deleteDisplayAd(
			@NotNull @ApiParam(value = "itemId") @RequestParam String itemId);
	
	
	@ApiOperation(value = "Get Display ad Encoded String", notes = "Get Display ad Encoded String", response = Void.class, tags = { "Display" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/da/getencodedstring", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getDisplayAdEncodedString(@NotNull @ApiParam(value = "orderId") @RequestParam String orderId);
	
	
	@ApiOperation(value = "Send Email ", notes = "Send Email", response = Void.class, tags = { "Display" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/da/sendemailtoschedulingteam", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> sendDisplayEmailToSchedulingTeam(@NotNull @ApiParam(value = "orderId") @RequestParam String orderId);
	
	/**
	 * approve display Ads
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "approve display ads", notes = "Approve display ads", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/approvedisplayads", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> approveDisplayAds(
			@ApiParam(value = "Classified Status", required = true) @RequestBody ClassifiedStatus payload);
	
	
	/**
	 * Download Ads PDF Document
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Download Display Ads", notes = "Download Display Ads", response = GenericApiResponse.class, tags = {
			"Display", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/da/adspdf", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> downloadAdsDisplayAds(
			@NotNull @ApiParam(value = "Download Ads payload") @RequestBody DashboardFilterTo payload);
	
	@ApiOperation(value = "Get Customer Details", notes = "Get Customer Details", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/customer", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getCustomerDetails(@NotNull @ApiParam(value = "mobileNo") @RequestParam String mobileNo);
	
	
	@ApiOperation(value = "Get Vendor commission", notes = "Get Vendor commission", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/vendor/commission", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getVendorCommission(
			@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);
	
	
	@ApiOperation(value = "Upload Download Status", notes = "Upload Download Status", response = GenericApiResponse.class, tags = {
			"Display" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/da/uploaddownload/staus", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> updateDownloadStatus(@NotNull @ApiParam(value = "itemId") @RequestParam String itemId);


	@ApiOperation(value = "Upload Vendor Commission", notes = "Upload Vendor Commission", response = Void.class, tags = {
			"Display" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/da/upload/commission")
	public ResponseEntity<?> uploadVendorCommision(HttpServletRequest request);
	
	@ApiOperation(value = "Download Template", notes = "Download Template", response = Void.class, tags = { "Display", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = Void.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@PostMapping(value = "/da/download/template")
	public ResponseEntity<?> downloadTemplateHeaders(HttpServletRequest request, HttpServletResponse response);
	
	
	@ApiOperation(value = "Get SSP Summary Reports", notes = "Get SSP Summary Reports", response = GenericApiResponse.class, tags = {
	"Display" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
	@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
	@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
@RequestMapping(value = "/da/ssp/summary", produces = { "application/json" }, method = RequestMethod.POST)
ResponseEntity<?> getSSPSummaryReports(
	@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);
	
	
	@ApiOperation(value = "Get SSP Summary Reports", notes = "Get SSP Summary Reports", response = GenericApiResponse.class, tags = {
	"Display" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
	@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
	@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
@RequestMapping(value = "/da/erp", produces = { "application/json" }, method = RequestMethod.POST)
ResponseEntity<?> getClassifiedErpData(
	@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);
}
