package com.portal.workflow;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.repository.UmUsersRepository;
import com.portal.security.model.LoggedUser;
import com.portal.security.model.UserTo;
import com.portal.security.util.LoggedUserContext;
import com.portal.user.entities.UmUsers;
import com.portal.workflow.model.WfCommonModel;
import com.portal.workflow.model.WfCreateRequest;
import com.portal.workflow.model.WfEvent;
import com.portal.workflow.model.WfListViewModel;
import com.portal.workflow.model.WfRequest;

import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.ApiParam;

/**
 * This class will process all work flow related request from end users and will do the required actions.
 * 
 * @author Sathish
 *
 */

@RestController
public class WorkFlowApiController implements WorkFlowApi {

	@Autowired
	private WorkFlowService workFlowService;

	@Autowired(required = true)
	private LoggedUserContext userContext;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private WorkFlowProcessService wfProcessService;
	
	@Autowired
	private UmUsersRepository umUsersRepository;
	
	private LoggedUser loggedUser = null;
	
	@Autowired(required = true)
	private HttpServletRequest request;
//
	@Override
	public ResponseEntity<?> getWfInbox(
			@NotNull @ApiParam(value = "Workflow Update Request", required = false) @RequestBody ApprovalListView payload) throws JsonMappingException, JsonProcessingException {
		LoggedUser loggedUser = userContext.getLoggedUser();
		WfCommonModel workFlowCommonModel = new WfCommonModel();
		workFlowCommonModel.setLoggedUser(loggedUser);
		workFlowCommonModel.setGenericRequestHeaders(commonService.getRequestHeaders());
		return new ResponseEntity<GenericApiResponse>(workFlowService.getInbox(workFlowCommonModel,payload), HttpStatus.OK);
	}
//
	@Override
	public ResponseEntity<?> updateInboxEvent(@NotNull WfRequest payload) {
		LoggedUser loggedUser = userContext.getLoggedUser();
		WfCommonModel workFlowCommonModel = new WfCommonModel();
		WfEvent workFlowEvent = new WfEvent();
		workFlowEvent.setWfInboxId(payload.getInboxId());
		workFlowEvent.setWfInboxMasterId(payload.getInboxMasterId());
		workFlowEvent.setWfRefId(payload.getInboxMasterId());
		workFlowEvent.setActStatus(payload.getAction());
		workFlowEvent.setRefDocData(payload.getRefDocData());
		workFlowEvent.setActionComments(payload.getActionComments());
		workFlowEvent.setWfDesc(payload.getWfDesc());
		if(payload.getAsnGatepassDetails() != null && !payload.getAsnGatepassDetails().isEmpty()){
			workFlowCommonModel.setAsnGatepassDetails(payload.getAsnGatepassDetails());
		}
		if(payload.getOmOrgFormId() != null && !payload.getOmOrgFormId().isEmpty()){
			workFlowCommonModel.setOmOrgFormId(payload.getOmOrgFormId());
		}
		if(payload.getAsnId() != null && !payload.getAsnId().isEmpty()){
			workFlowCommonModel.setAsnId(payload.getAsnId());
		}
		workFlowCommonModel.setWorkFlowEvent(workFlowEvent);
		workFlowCommonModel.setLoggedUser(loggedUser);
//		workFlowCommonModel.setDocumentNumber(payload.getDocumentNumber());
		workFlowCommonModel.setStatus(payload.getAction());
//		workFlowCommonModel.setDocumentMasterId(payload.getDocumentMasterId());
		workFlowCommonModel.setItemId(payload.getItemId());
		workFlowCommonModel.setOrderId(payload.getOrderId());
		workFlowCommonModel.setAgreedPremiumDisPer(payload.getAgreedPremiumDisPer());
		workFlowCommonModel.setGenericRequestHeaders(commonService.getRequestHeaders());
		return new ResponseEntity<GenericApiResponse>(workFlowService.updateInboxEvent(workFlowCommonModel),
				HttpStatus.OK);
	}

//	@Override
//	public ResponseEntity<?> startEvent(String wfShortId, String orgId) {
//		LoggedUser loggedUser = userContext.getLoggedUser();
//		WfCommonModel workFlowCommonModel = new WfCommonModel();
//		WfEvent workFlowEvent = new WfEvent();
//		workFlowEvent.setWfShortId(wfShortId);// VENDOR_ONBOARD
//		workFlowEvent.setObjectRefId(UUID.randomUUID().toString());
//		workFlowEvent.setRequestRaisedBy("Sathish");
//		workFlowCommonModel.setLoggedUser(loggedUser);
//		workFlowCommonModel.setWorkFlowEvent(workFlowEvent);
//		workFlowCommonModel.setGenericRequestHeaders(commonService.getRequestHeaders());
//		workFlowService.startEvent(workFlowCommonModel);
//		return null;
//	}
//
//	@Override
//	public ResponseEntity<?> updateEvent(String key) {
//		LoggedUser loggedUser = userContext.getLoggedUser();
//		WfCommonModel workFlowCommonModel = new WfCommonModel();
//		WfEvent workFlowEvent = new WfEvent();
//		workFlowEvent.setObjectRefId(UUID.randomUUID().toString());
//		workFlowEvent.setExtObjectRefId(key);
//		workFlowCommonModel.setLoggedUser(loggedUser);
//		workFlowCommonModel.setWorkFlowEvent(workFlowEvent);
//		workFlowCommonModel.setGenericRequestHeaders(commonService.getRequestHeaders());
//		workFlowService.updateInboxEvent(workFlowCommonModel);
//		return null;
//	}
//
//	@Override
//	public ResponseEntity<?> updateInbox(String key, String action, String orgId) {
//		LoggedUser loggedUser = userContext.getLoggedUser();
//		WfCommonModel workFlowCommonModel = new WfCommonModel();
//		WfEvent workFlowEvent = new WfEvent();
//		workFlowEvent.setWfInboxId(key);
//		workFlowEvent.setActStatus(action);
//		workFlowCommonModel.setLoggedUser(loggedUser);
//		workFlowCommonModel.setWorkFlowEvent(workFlowEvent);
//		workFlowCommonModel.setGenericRequestHeaders(commonService.getRequestHeaders());
//		workFlowService.updateInboxEvent(workFlowCommonModel);
//		return null;
//	}
//
	@Override
	public ResponseEntity<?> createCustomWorkFlow(WfCreateRequest payload) {
		GenericApiResponse response = wfProcessService.createCustomWorkFlow(commonService.getRequestHeaders(), payload);
		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
	}
//	
//	@Override
//	public ResponseEntity<?> getWfTypes() {
//		GenericApiResponse response = wfProcessService.getWfTypes(commonService.getRequestHeaders());
//		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
//	}
//	
	@Override
	public ResponseEntity<?> getWfServiceTasks(
			@NotNull @ApiParam(value = "Workflow Type", required = false) @RequestParam(value = "wfTypeId", required = false) String wfTypeId) {
		GenericApiResponse response = wfProcessService.getWfServiceTasks(commonService.getRequestHeaders(), wfTypeId);
		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getWfData(
			@NotNull @ApiParam(value = "Workflow Title", required = true) @RequestParam String wfId) {
		GenericApiResponse response = wfProcessService.getWfDataByWfId(commonService.getRequestHeaders(), wfId);
		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getWfTitlesDropdown(
			@NotNull @ApiParam(value = "Workflow Type ID", required = false) @RequestParam(value = "wfTypeIdList", required = false) List<String> wfTypeId,
			@NotNull @ApiParam(value = "Workflow Type Name", required = false) @RequestParam(value = "wfTypeName", required = false) String wfTypeName,
			@NotNull @ApiParam(value = "Get master WF", required = false) @RequestParam(value = "masterWf", required = false) boolean masterWf,
			@NotNull @ApiParam(value = "Workflow Title", required = false) @RequestParam(value = "title", required = false) String title) {
		GenericApiResponse response = wfProcessService.getWfTitlesDropdown(commonService.getRequestHeaders(), wfTypeId, wfTypeName, title, masterWf);
		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
	}
	@Override
	public ResponseEntity<?> getWorlFlowListView(@NotNull WfListViewModel payload) {
		GenericApiResponse response = wfProcessService.getWfListView(commonService.getRequestHeaders(), payload);
		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
	}
	@Override
	public ResponseEntity<?> setWfAsDefault(@NotNull Map payload) {
		GenericApiResponse response = wfProcessService.setAsDefaultWf(commonService.getRequestHeaders(), payload);
		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
	}
	@Override
	public ResponseEntity<?> getWorkFlowStatus(@NotNull WfListViewModel payload) throws JsonMappingException, JsonProcessingException {
		GenericApiResponse response = workFlowService.getWfStatus(commonService.getRequestHeaders(), payload);
		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
	}
	@Override
	public ResponseEntity<?> updateInboxEventByMail(@NotNull WfRequest payload) {
//		LoggedUser loggedUser = userContext.getLoggedUser();
//		if(loggedUser.getUserId() == null) {
			 List<UmUsers> userList = umUsersRepository.getUserByUserId(Integer.parseInt(payload.getRefToUser()),false);
			 if(userList != null && !userList.isEmpty()) {
				  UserTo userTo = userContext.findByLogonId(userList.get(0).getLogonId());
				  if(userTo != null) {
					  this.setLoggedUserDetails(userTo);
				  }
			 }
//		}
		WfCommonModel workFlowCommonModel = new WfCommonModel();
		WfEvent workFlowEvent = new WfEvent();
		workFlowEvent.setWfInboxId(payload.getInboxId());
		workFlowEvent.setWfInboxMasterId(payload.getInboxMasterId());
		workFlowEvent.setWfRefId(payload.getInboxMasterId());
		workFlowEvent.setActStatus(payload.getAction());
		workFlowEvent.setRefDocData(payload.getRefDocData());
		workFlowEvent.setActionComments(payload.getActionComments());
		workFlowEvent.setWfDesc(payload.getWfDesc());
		workFlowEvent.setRefToUser(payload.getRefToUser());
		workFlowEvent.setAction("TAKE_ACTION");
		if(payload.getAsnGatepassDetails() != null && !payload.getAsnGatepassDetails().isEmpty()){
			workFlowCommonModel.setAsnGatepassDetails(payload.getAsnGatepassDetails());
		}
		if(payload.getOmOrgFormId() != null && !payload.getOmOrgFormId().isEmpty()){
			workFlowCommonModel.setOmOrgFormId(payload.getOmOrgFormId());
		}
		if(payload.getAsnId() != null && !payload.getAsnId().isEmpty()){
			workFlowCommonModel.setAsnId(payload.getAsnId());
		}
		workFlowCommonModel.setWorkFlowEvent(workFlowEvent);
		workFlowCommonModel.setLoggedUser(loggedUser);
		workFlowCommonModel.setStatus(payload.getAction());
		workFlowCommonModel.setItemId(payload.getItemId());
		workFlowCommonModel.setOrderId(payload.getOrderId());
		workFlowCommonModel.setAgreedPremiumDisPer(payload.getAgreedPremiumDisPer());
		workFlowCommonModel.setGenericRequestHeaders(commonService.getRequestHeaders());
		return new ResponseEntity<GenericApiResponse>(workFlowService.updateInboxEvent(workFlowCommonModel),
				HttpStatus.OK);
	}
	public void setLoggedUserDetails(UserTo user) {
		try {
			if (user != null) {
				loggedUser = new LoggedUser();
				BeanUtils.copyProperties(user, loggedUser);
				loggedUser.setRoleName(user.getRole());
				loggedUser.setRoleId(user.getRoleId());
				loggedUser.setRoleDesc(user.getRoleDesc());
				loggedUser.setRoleType(user.getRoleType());
				loggedUser.setRegCenter(user.getRegCenter());
				loggedUser.setCountry(user.getCountry());
				loggedUser.setGdCode(user.getGdCode());
				loggedUser.setSecondaryRoles(user.getSecondaryRoles());
				loggedUser.setRegion(user.getRegion());
				loggedUser.setUserId(user.getUserId());
			}
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

			loggedUser.setIp(this.getRemoteAddress1(request));
			loggedUser.setReqHeader(getHeaders1().toString() + getParames1().toString());
			// loggedUser.setResHeader(response.getHeaderNames().toString());
			loggedUser.setReqUrl(request.getRequestURL().toString());
			loggedUser.setReqMethod(request.getMethod());
			loggedUser.setBrowserName(userAgent.getBrowser().getName());
			loggedUser.setBrowserVersion(
					userAgent.getBrowserVersion() != null ? userAgent.getBrowserVersion().getVersion() : "");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	private Object getParames1() {
		Map<String, String> map = new HashMap<String, String>();

		Map<String, String[]> allMap = request.getParameterMap();

		for (String key : allMap.keySet()) {

			String[] strArr = (String[]) allMap.get(key);

			map.put(key, Arrays.toString(strArr));
		}

		return map;
	}
	private Object getHeaders1() {
		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();

			String value = request.getHeader(key);

			map.put(key, value);
		}

		return map;
	}
	private String getRemoteAddress1(HttpServletRequest request2) {
		String remoteAddr = null;

		if (request != null) {

			remoteAddr = request.getHeader("x-forwarded-for");

			if (remoteAddr == null || "".equals(remoteAddr)) {

				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}
	
//	@Override
//	public ResponseEntity<?> getEmailTempDet(
//			@NotNull @ApiParam(value = "Template Type", required = false) @RequestParam(value = "tempType", required = false) String tempType) {
//		WfCommonModel wfModel = new WfCommonModel();
//		wfModel.setEmailTempType(tempType);
//		wfModel.setGenericRequestHeaders(commonService.getRequestHeaders());
//		GenericApiResponse response = wfProcessService.getEmailTemplates(wfModel);
//		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
//	}
//	
//	@Override
//	public ResponseEntity<?> getWfList(@NotNull WfUpdatePayload payload) {
//		GenericRequestHeaders requestHeaders=commonService.getRequestHeaders();
//		GenericApiResponse response = workFlowService.getWfListByModule(payload,requestHeaders);
//		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<?> updateWorkFlow(@NotNull WfUpdatePayload payload) {
//		GenericRequestHeaders requestHeaders=commonService.getRequestHeaders();
//		GenericApiResponse response = workFlowService.updateWorkFlow(payload,requestHeaders);
//		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<?> updateWorkItem(@NotNull Map payload) {
//		GenericRequestHeaders requestHeaders=commonService.getRequestHeaders();
//		GenericApiResponse response = workFlowService.updateWorkItem(payload,requestHeaders);
//		return new ResponseEntity<GenericApiResponse>(response, HttpStatus.OK);
//	}
	
}
