package com.portal.reports.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.constants.GeneralConstants;
import com.portal.org.models.OrgDetails;
import com.portal.reports.to.ReportsRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Reports", description = "Reports APIs")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface ReportsApi {

	/**
	 * Get Reports Gallery
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Get Reports Gallery", notes = "Get Reports Gallery from Master", response = OrgDetails.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = OrgDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Orgnization data is not found") })
	@RequestMapping(value = "/reports", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getReportsMaster();
	
	/**
	 * Download Reports
	 * 
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Reports Download", notes = "Reports Download", response = Void.class, tags = { "Template", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = Void.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@PostMapping(value = "/reports/download")
	public ResponseEntity<?> reportDownload(
			@ApiParam(value = "Reports Request Body", required = false) @RequestBody(required = false) ReportsRequest payload,
			HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Report File Download
	 * 
	 * @param vinlist
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Report File Download", notes = "Report File Download", response = Void.class, tags = {
			"Field Fix" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation"),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@GetMapping(value = "/reports/fileDownload")
	public ResponseEntity<?> downloadReportFile(
			@NotNull @ApiParam(value = "Short Id", required = true) @RequestParam(value = "shortId", required = true) String shortId,
			HttpServletRequest request,
			HttpServletResponse response);
	
}
