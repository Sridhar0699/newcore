package com.portal.workflow;


import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.workflow.model.WfCommonModel;
import com.portal.workflow.model.WfEvent;
import com.portal.workflow.model.WfListViewModel;
import com.portal.common.models.GenericApiResponse;
import com.portal.workflow.model.WfCommonModel;

public interface WorkFlowService {

	public GenericApiResponse getInbox(WfCommonModel workFlowCommonModel,ApprovalListView payload) throws JsonMappingException, JsonProcessingException;
	public GenericApiResponse updateInboxEvent(WfCommonModel workFlowCommonModel);
//	public void startWorkFlow(WfCommonModel workFlowCommonModel, WfDetails wfDetails);
//	public WfDetails parseWorkflow(List<Map> wfMap, WfCommonModel workFlowCommonModel);
//	public WfResponse startEvent(WfCommonModel workFlowCommonModel);
	public Map<String, Object> getWorkFlowInboxObjRefDetails(WfCommonModel workFlowCommonModel, GenericRequestHeaders requestHeaders);
	public boolean updateRevisedFormWf(WfEvent wfEvent, GenericRequestHeaders requestHeaders, String fromStatus);
	public boolean intiateFormWorkFlow(WfEvent wfEvent, GenericRequestHeaders requestHeaders, String formStatus);
//	public boolean updateInboxByObjectRef(GenericRequestHeaders requestHeaders, String objectRefId, String formStatus);
//	public GenericApiResponse getWfListByModule(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders);
//	public GenericApiResponse updateWorkFlow(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders);
//	public GenericApiResponse updateWorkItem(@NotNull Map payload, GenericRequestHeaders requestHeaders);
	public GenericApiResponse getWfStatus(GenericRequestHeaders requestHeaders, @NotNull WfListViewModel payload) throws JsonMappingException, JsonProcessingException;
	Map<String, Object> getWorkFlowInboxObjRefDetails1(WfCommonModel workFlowCommonModel,
			GenericRequestHeaders requestHeaders);
}
