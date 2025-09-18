package com.portal.clf.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.CartDetails;
import com.portal.clf.models.ClfPaymentsRefund;
import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Payments")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface PaymentApi {

	/**
	 * BillDesk Payment Response Handler
	 * 
	 * @return
	 */
	@ApiOperation(value = "Bill Desk Payment API response Handler", notes = "Bill Desk Payment API response Handler", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/pmt/reshndlr", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> billdeskResponseHandler(
			@NotNull @ApiParam(value = "Response Payload") @RequestBody BillDeskPaymentResponseModel payload);
	

	/**
	 * BillDesk Payment Options
	 * 
	 * @return
	 */
	@ApiOperation(value = "Bill Desk Payment Options", notes = "Bill Desk Payment Options", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/pmt/options", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> showbilldeskModel(
			@NotNull @ApiParam(value = "Response Payload") @RequestParam String encToken);
	
	@ApiOperation(value = "Prepare Request", notes = "Prepare Request", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/prepare/request", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> prepareRequest(
			@NotNull @ApiParam(value = "Response Payload") @RequestBody CartDetails payload);
	
	@ApiOperation(value = "Bill Desk Callback", notes = "Bill Desk Callback", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/billdeskcallback", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> billDeskWebHookLogs(
			@NotNull @ApiParam(value = "Response Payload") @RequestBody BillDeskPaymentResponseModel payload);
	
	@ApiOperation(value = "Prepare Request For Refund", notes = "Prepare Request For Refund", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/prepare/request/refund", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> prepareRequestForCreateRefund(
			@NotNull @ApiParam(value = "Response Payload") @RequestBody ClfPaymentsRefund payload);
	
	
	
	
	@ApiOperation(value = "Get Refund", notes = "Get Refund", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/refund", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getRefund(
			 @RequestParam String mercId,@RequestParam String refundId);
	
	
	@ApiOperation(value = "Get Payment and Refund Details", notes = "Get Payment and Refund Details", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/payments/refund", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getPaymentsAndRefundDetails(
			 @RequestParam String orderId);
	
	@ApiOperation(value = "Get Transaction Details", notes = "Get Transaction Details", response = GenericApiResponse.class, tags = {
			"Classifieds", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/clf/get/transactiondetails", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getTransactionDetailsOnOrderId(
			 @RequestParam String orderId,@RequestParam Boolean linkFlag);
}
