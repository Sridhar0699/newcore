package com.portal.hm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;
import com.portal.hm.model.HelpManualDetails;
import com.portal.hm.model.HelpManuals;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author Incresol
 *
 */
@Api(value = "Help Manual", description = "Help Manual Managing API")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface HMApi {

	/**
	 * Create/update Help Manual details
	 * 
	 * @param payload
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Help Manual Create or Update", notes = "Create/update Help Manual details", response = Void.class, tags = {
			"Help Manual" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/hm/create-manual/{org_id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> createOrUpdateHelpManualDetails(
			@ApiParam(value = "Help Manual Details", required = true) @RequestBody HelpManualDetails payload,
			HttpServletRequest request);

	/**
	 * Get Help Manual details
	 * 
	 * @param payload
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Get Help Manual List", notes = "Help Manual List by Type", response = HelpManuals.class, tags = {
			"Help Manual", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = HelpManuals.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/hm/read-manual/{org_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getHelpManualDetails(
			@NotNull @ApiParam(value = "Manual Type", required = true) @RequestParam("manual_type") String manual_type,
			@NotNull @ApiParam(value = "Manual Id", required = false) @RequestParam(value = "manual_id", required = false) String manual_id,
			@NotNull @ApiParam(value = "Role Id", required = false) @RequestParam(value = "role_id", required = false) String role_id);

	/**
	 * Download Help Manual
	 * 
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Get Help Manual List", notes = "Help Manual List by Type", response = Void.class, tags = {
			"Help Manual", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = Void.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@GetMapping(value = "/hm/download-manual/{org_id}")
	public ResponseEntity<?> downloadHelpManualDetails(
			@NotNull @ApiParam(value = "Manual Type", required = true) @RequestParam("manual_type") String manual_type,
			@NotNull @ApiParam(value = "File Name", required = true) @RequestParam(value = "file_name", required = true) String file_name,
			HttpServletRequest request, HttpServletResponse response);
}
