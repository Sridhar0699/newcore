package com.portal.workflow;


import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;
import com.portal.workflow.model.WfCreateRequest;
import com.portal.workflow.model.WfListViewModel;
import com.portal.workflow.model.WfRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "WorkFlow", description = "Workflow management APIs")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface WorkFlowApi {

	
	
	
	/**
	 * Get Inbox Details by requested user and user type
	 * 
	 * @param type
	 * @param ref_obj_id
	 * @param access_obj_id
	 * @return
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@ApiOperation(value = "Get workflow Inbox", notes = "Get workflow Inbox", response = GenericApiResponse.class, tags = {
			"WorkFlow" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@PostMapping(value = "/wf/inbox")
	ResponseEntity<?> getWfInbox(
			@NotNull @ApiParam(value = "Workflow Update Request", required = false) @RequestBody ApprovalListView payload) throws JsonMappingException, JsonProcessingException;
//	
//	
	/**
	 * Will take and update the user action like APPROVED/REJECTED or any other actions, post update will check the next workflow event and completed the job
	 * 
	 * @param type
	 * @param ref_obj_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Update Inbox Event", notes = "Update Inbox Event", response = GenericApiResponse.class, tags = {
			"WorkFlow" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@PostMapping(value = "/wf/updateEvent")
	ResponseEntity<?> updateInboxEvent(
			@NotNull @ApiParam(value = "Workflow Update Request", required = false) @RequestBody WfRequest payload);
	
	
	@ApiOperation(value = "Update Inbox Event", notes = "Update Inbox Event", response = GenericApiResponse.class, tags = {
			"WorkFlow" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@PostMapping(value = "/wf/updateEvent/byemail")
	ResponseEntity<?> updateInboxEventByMail(
			@NotNull @ApiParam(value = "Workflow Update Request", required = false) @RequestBody WfRequest payload);
	
//	
//	/**
//	 * Temporary API need to remove
//	 * @param wfShortId
//	 * @param orgId
//	 * @return
//	 */
//	@PostMapping(value = "/template/wf/{orgId}/{wfShortId}")
//	ResponseEntity<?> startEvent(
//			@ApiParam(value = "wfShortId", required = true) @PathVariable(value = "wfShortId", required = true) String wfShortId,
//			@ApiParam(value = "Organisation Id", required = true) @PathVariable(value = "orgId", required = true) String orgId);
//	
//	/**
//	 * Temporary API need to remove
//	 * @param key
//	 * @return
//	 */
//	@PostMapping(value = "/unregVend/{key}")
//	ResponseEntity<?> updateEvent(
//			@ApiParam(value = "key", required = true) @PathVariable(value = "key", required = true) String key);
//	
//	/**
//	 * Temporary API need to remove
//	 * @param key
//	 * @param action
//	 * @param orgId
//	 * @return
//	 */
//	@PostMapping(value = "/template/wf/update/{orgId}")
//	ResponseEntity<?> updateInbox(
//			@ApiParam(value = "Inbox Id", required = true) @RequestParam(value = "key", required = true) String key,
//			@ApiParam(value = "Action", required = true) @RequestParam(value = "action", required = true) String action,
//			@ApiParam(value = "Organisation Id", required = true) @PathVariable(value = "orgId", required = true) String orgId);
//	
//	/**
//	 * Create Custom Work Flow
//	 */
//	
	@ApiOperation(value = "Create Custom Work Flow", notes = "Create Custom Work Flow", response = GenericApiResponse.class, tags = {
			"WorkFlow" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@PostMapping(value = "/wf/create")
	ResponseEntity<?> createCustomWorkFlow(
			@NotNull @ApiParam(value = "Workflow Create Payload", required = false) @RequestBody WfCreateRequest payload);
//	
//	/**Get Work Flow Types */
//	
//	@ApiOperation(value = "Get Work Flow Types", notes = "Get Work Flow Types", response = GenericApiResponse.class, tags = {
//			"WorkFlow" })
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
//			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
//			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
//	@GetMapping(value = "/wf/types")
//	ResponseEntity<?> getWfTypes();
//	
//	/**Get Work Flow Service Tasks */
//	
	@ApiOperation(value = "Get Work Flow Service Tasks", notes = "Get Work Flow Service Tasks", response = GenericApiResponse.class, tags = {
			"WorkFlow" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@GetMapping(value = "/wf/service/tasks")
	ResponseEntity<?> getWfServiceTasks(
			@NotNull @ApiParam(value = "Workflow Type", required = false) @RequestParam(value = "wfTypeId", required = false) String wfTypeId);
//	
//	
//	/**Get Work Flow Data */
	@ApiOperation(value = "Get Work Flow Data", notes = "Get Work Flow Data", response = GenericApiResponse.class, tags = {
			"WorkFlow" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@GetMapping(value = "/wf/getWfData")
	ResponseEntity<?> getWfData(
			@NotNull @ApiParam(value = "Workflow Title", required = true) @RequestParam String wfId);
//	
	/**Get Work Flow Title Dropdown */
	@ApiOperation(value = "Get Work Flow Title Dropdown", notes = "Get Work Flow Title Dropdown", response = GenericApiResponse.class, tags = {
			"WorkFlow" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@GetMapping(value = "/wf/getTiltes")
	ResponseEntity<?> getWfTitlesDropdown(
			@NotNull @ApiParam(value = "Workflow Type ID", required = false) @RequestParam(value = "wfTypeIdList", required = false) List<String> wfTypeId,
			@NotNull @ApiParam(value = "Workflow Type Name", required = false) @RequestParam(value = "wfTypeName", required = false) String wfTypeName,
			@NotNull @ApiParam(value = "Get master WF", required = false) @RequestParam(value = "masterWf", required = false) boolean masterWf,
			@NotNull @ApiParam(value = "Workflow Title", required = false) @RequestParam(value = "title", required = false) String title);
//	
//	/**Get Work Flow Email Templates */
//	@ApiOperation(value = "Get Work Flow Email Templates", notes = "Get Work Flow Email Templates", response = GenericApiResponse.class, tags = {
//			"WorkFlow" })
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
//			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
//			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
//	@GetMapping(value = "/wf/getEmailTempDet")
//	ResponseEntity<?> getEmailTempDet(
//			@NotNull @ApiParam(value = "Template Type", required = false) @RequestParam(value = "tempType", required = false) String tempType);
//	
//	@ApiOperation(value = "Update Work Flow User Role", notes = "Update Work Flow User Role", response = GenericApiResponse.class, tags = {
//			"WorkFlow" })
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
//			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
//			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
//	@PostMapping(value = "/wf/getWfList")
//	ResponseEntity<?> getWfList(
//			@NotNull @ApiParam(value = "Update Work Flow User Role", required = false) @RequestBody WfUpdatePayload payload);
//
//	@ApiOperation(value = "Update Work Flow", notes = "Update Work Flow", response = GenericApiResponse.class, tags = {
//			"WorkFlow" })
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
//			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
//			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
//	@PostMapping(value = "/wf/update/workFlow")
//	ResponseEntity<?> updateWorkFlow(
//			@NotNull @ApiParam(value = "Update Work Flow", required = false) @RequestBody WfUpdatePayload payload);
//	
//	@ApiOperation(value = "Update Work Flow Inbox for work Item", notes = "Update Work Flow Inbox for work Item", response = GenericApiResponse.class, tags = {
//			"WorkFlow" })
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
//			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
//			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
//	@PostMapping(value = "/wf/update/workItem")
//	ResponseEntity<?> updateWorkItem(
//			@NotNull @ApiParam(value = "Update Work Flow for work item", required = false) @RequestBody Map payload);
	
	
	@ApiOperation(value = "Workflow List View", notes = "Workflow List View", response = GenericApiResponse.class, tags = {
			"WorkFlow", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/wf/listview", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getWorlFlowListView(
			@NotNull @ApiParam(value = "Get Workflow List view", required = true) @RequestBody WfListViewModel payload);
	
	@ApiOperation(value = "Workflow Set As Default", notes = "Workflow Set As Default", response = GenericApiResponse.class, tags = {
			"WorkFlow", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/wf/setasdefault", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> setWfAsDefault(
			@NotNull @ApiParam(value = "Get Workflow List view", required = true) @RequestBody Map payload);
	
	
	@ApiOperation(value = "Workflow List View", notes = "Workflow List View", response = GenericApiResponse.class, tags = {
			"WorkFlow", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Data is not found") })
	@RequestMapping(value = "/wf/getwfstatus", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getWorkFlowStatus(
			@NotNull @ApiParam(value = "Get Workflow List view", required = true) @RequestBody WfListViewModel payload) throws JsonMappingException, JsonProcessingException;
}
