package com.portal.rms.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.clf.models.DashboardFilterTo;
import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;
import com.portal.rms.model.CreateOrder;
import com.portal.rms.model.OtpModel;
import com.portal.rms.model.RmsApproveModel;
import com.portal.rms.model.RmsDashboardFilter;
import com.portal.rms.model.RmsModel;
import com.portal.rms.model.RmsPaymentLinkModel;
import com.portal.rms.model.RmsRateModel;
import com.portal.workflow.model.WfRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Rms Classifieds", description = "Manage Rms Classifieds API")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface RmsApi {

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
	@RequestMapping(value = "/rms/dashboard/counts", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDashboardCounts(@ApiParam(value = "Dashboard Request", required = true) @RequestBody RmsDashboardFilter payload);
	
	
	
	
	@ApiOperation(value = "Get Rms Classifieds", notes="Get Rms Classifieds", response=GenericApiResponse.class,tags= {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/dashboard/list", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getDashboardList(@ApiParam(value = "Dashboard Request", required = true) @RequestBody RmsDashboardFilter payload);
	
	
	@ApiOperation(value = "Get Rms classifieds", notes = "Get Rms classifieds", response = GenericApiResponse.class, tags = {
	"Get Rms Classifieds" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
	@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
	@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
@RequestMapping(value = "/rms/details", produces = { "application/json" }, method = RequestMethod.POST)
ResponseEntity<?> getDetailsById(@NotNull	@ApiParam(value="Rms Item details", required = true) @RequestParam String adId);
	
	
	/**
	 * Get classifieds
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Add Rms classifieds to cart or order", notes = "Add Rms classifieds to cart or order", response = GenericApiResponse.class, tags = {
			"Rms Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/addtocart", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> addRmsClassifiedOrderToCart(
			@ApiParam(value = "Order Item Details", required = true) @RequestBody CreateOrder payload);
	
	
	
	
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
	@RequestMapping(value = "/rms/customer", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getCustomerDetails(
			 @ApiParam(value = "clientCode") @RequestParam(required = false) String clientCode);
	
	
	/**
	 * Get RMS details by mobile No
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
@RequestMapping(value = "/rms/mobileno", produces = { "application/json" }, method = RequestMethod.GET)
ResponseEntity<?> getCustomerDetailsByMobileNumber(
	 @ApiParam(value = "mobileNo") @RequestParam(required = false) String mobileNo);
	
	/**
	 * Add RMS Rate And lines calculation
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Add RMS Rate And lines calculation", notes = "Add RMS Rate And lines calculation", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/rates", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getRmsRates(@ApiParam(value = "Dashboard Request", required = true) @RequestBody RmsRateModel payload);
	
	
	/**
	 * Genrate OTP
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Genrate OTP", notes = "Genrate OTP", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/genrateOtp", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> genrateOTP(@ApiParam(value = "Order Item Details", required = true) @RequestBody OtpModel payload);
	
	/**
	 * Validate OTP
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@ApiOperation(value = "Validate OTP", notes = "Validate OTP", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/validateOtp", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> validateOTP(@ApiParam(value = "Order Item Details", required = true) @RequestBody OtpModel payload);
	
	
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
	@RequestMapping(value = "/rms/sync/sap", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> syncronizeRmsSAPData(
			@NotNull @ApiParam(value = "Syncronize to SAP") @RequestBody RmsModel payload);
	
	
	@ApiOperation(value = "Generate Payment Link", notes = "Generate Payment Link", response = GenericApiResponse.class, tags = {
	"Classifieds" })
@ApiResponses(value = {
	@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
	@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
	@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
@RequestMapping(value = "/rms/generate/paymentlink", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> generateSendPaymentLink(
			@NotNull @ApiParam(value = "Order Details") @RequestBody RmsPaymentLinkModel payload);
	
	
	@ApiOperation(value = "Add RMS Rate And lines calculation", notes = "Add RMS Rate And lines calculation", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/fixedformats", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getFixedFormatsMasters(@ApiParam(value = "Dashboard Request", required = true) @RequestBody RmsRateModel payload);
	
	
	@ApiOperation(value = "Get District Editions", notes = "Get District Editions", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/editiontype/district", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getEditionTypeWiseDistricts(
			@NotNull @ApiParam(value = "editionType") @RequestParam(required = true) Integer editionType,
			@ApiParam(value = "formatType") @RequestParam(required = false) Integer formatType,
			@NotNull @ApiParam(value = "addType") @RequestParam(required = true) Integer addType,
			@ApiParam(value = "stateCode") @RequestParam(required = false) String StateCode);
	
	@ApiOperation(value = "Get Page Positions", notes = "Get Page Postions", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/editiontype/pagepositions", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getPagePositionsOnEditionType(
			@NotNull @ApiParam(value = "editionType") @RequestParam(required = true) Integer editionType);
	
	
	@ApiOperation(value = "Approve Or Reject Rms Order", notes = "Approve Or Reject Rms Order", response = GenericApiResponse.class, tags = {
			"Rms Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/approve/order", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> approveOrRejectRmsOrder(
			@ApiParam(value = "Order Details", required = true) @RequestBody RmsApproveModel payload);
	
	@ApiOperation(value = "Get Approval Inbox", notes = "Get Approval Inbox", response = GenericApiResponse.class, tags = {
			"Rms Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/inbox", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getApprovalInbox(@ApiParam(value = "Approval inbox", required = true) @RequestBody RmsDashboardFilter payload);
	
	@ApiOperation(value = "Get Draft Detail View ", notes = "Get Draft Detail View", response = GenericApiResponse.class, tags = {
			"Rms Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/draft/detailview", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getDraftDetailView(
			@NotNull @ApiParam(value = "orderId") @RequestParam(required = true) String orderId);
	
	
	@ApiOperation(value = "Get Rms List view", notes = "Get Rms List view", response = GenericApiResponse.class, tags = {
			"Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/listview", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getRmsListView(
			@ApiParam(value = "Dashboard Request", required = true) @RequestBody DashboardFilterTo payload);
	
	@ApiOperation(value = "Delete Draft Details ", notes = "Delete Draft Details", response = GenericApiResponse.class, tags = {
			"Rms Classifieds" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/draft/delete", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> deleteDraftOrders(
			@NotNull @ApiParam(value = "orderId") @RequestParam(required = true) String orderId);
	
	
	
	@ApiOperation(value = "Delete Draft Details ", notes = "Delete Draft Details", response = GenericApiResponse.class, tags = {
	"Rms Classifieds" })
@ApiResponses(value = {
	@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
	@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
	@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
@RequestMapping(value = "/rms/downloadpdf", produces = { "application/json" }, method = RequestMethod.GET)
ResponseEntity<?> downloadPdf(
	@NotNull @ApiParam(value = "orderId") @RequestParam(required = true) String orderId);
	
	
@ApiOperation(value = "Check Mobile No Duplicate", notes = "Check Mobile No Duplicate", response = GenericApiResponse.class, tags = {
		"Rms Classifieds" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
		@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
@RequestMapping(value = "/rms/validatemobileno", produces = { "application/json" }, method = RequestMethod.GET)
ResponseEntity<?> isMobileNumberDuplicate(
		@ApiParam(value = "mobileNo") @RequestParam(required = false) String mobileNo);

	@ApiOperation(value = "Validate Access key", notes = "Validate Access Key", response = GenericApiResponse.class, tags = {
			"Rms Classifieds" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/rms/validate/accesskey", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> validateAccessKey(
			@ApiParam(value = "Validate Key", required = true) @RequestBody WfRequest payload);

}
