package com.portal.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Rms", description = "Rms Documents API")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface DocumentApi {

	@ApiOperation(value = "Upload Rms Documents", notes = "Upload Rms Documents", response = GenericApiResponse.class, tags = {
	"Documents" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
	@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
	@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
@RequestMapping(value = "/rms/docupload", produces = { "application/json" }, method = RequestMethod.POST)
ResponseEntity<?> uploadRmsDocUpload(HttpServletRequest request);
	
	@ApiOperation(value = "Audit Documents View", notes = "Audit Documents View", response = Void.class, tags = {
	"Documents" })
@ApiResponses(value = {
	@ApiResponse(code = 200, message = "Successful Operation"),
	@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
	@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
	@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/am/docview", produces = { "application/json" }, method = RequestMethod.GET)
public ResponseEntity<?> downloadView(
	@NotNull @ApiParam(value = "Document Id", required = true) @RequestParam(value = "doc_id" , required = true) String docId,
	HttpServletRequest request,
	HttpServletResponse response);
	
	

	@ApiOperation(value = "Audit Documents View", notes = "Audit Documents View", response = Void.class, tags = {
	"Documents" })
@ApiResponses(value = {
	@ApiResponse(code = 200, message = "Successful Operation"),
	@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
	@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
	@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/am/docpdfview", produces = { "application/json" }, method = RequestMethod.GET)
public ResponseEntity<?> downloadPdfDocView(
	@NotNull @ApiParam(value = "Document Id", required = true) @RequestParam(value = "doc_id" , required = true) String docId,
	HttpServletRequest request,
	HttpServletResponse response);
	
	@ApiOperation(value = "Audit Documents Download", notes = "Audit Documents Download", response = Void.class, tags = {
	"Documents" })
@ApiResponses(value = {
	@ApiResponse(code = 200, message = "Successful Operation"),
	@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
	@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
	@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/am/docdownload", produces = { "application/json" }, method = RequestMethod.GET)
public ResponseEntity<?> downloadAttachments(
	@NotNull @ApiParam(value = "Document Id", required = true) @RequestParam(value = "doc_id" , required = true) List<String> docIds,
	HttpServletRequest request,
	HttpServletResponse response);

}
