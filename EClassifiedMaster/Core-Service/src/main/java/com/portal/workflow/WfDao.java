package com.portal.workflow;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.wf.entity.GdWfTypes;
import com.portal.wf.entity.WfInbox;
import com.portal.wf.entity.WfInboxMaster;
import com.portal.wf.entity.WfInboxMasterDetails;
import com.portal.wf.entity.WfServiceTasks;
import com.portal.workflow.model.WfCommonModel;
import com.portal.workflow.model.WfCreateRequest;

import com.portal.workflow.model.WfDetails;
import com.portal.workflow.model.WfInboxMasterDetailsModel;

import com.portal.workflow.model.WfInboxResponse;


public interface WfDao {

	public WfInboxResponse getWfInboxByUser(WfCommonModel workFlowCommonModel,ApprovalListView payload) throws JsonMappingException, JsonProcessingException;
	public List<Map> getWfMaster(WfCommonModel workFlowCommonModel);
	public List<WfInboxMaster> getWfMasterById(WfCommonModel workFlowCommonModel);
	public List<WfInboxMasterDetails> getWfInboxByInboxId(WfCommonModel workFlowCommonModel);
	public List<WfInboxMaster> getWfInboxByExtId(WfCommonModel workFlowCommonModel);
	public void updateInbox(WfCommonModel workFlowCommonModel);
	public void updateWfInboxMaster(WfCommonModel workFlowCommonModel, WfDetails wfDetails);
	public long createInbox(WfInboxMasterDetailsModel wfInbox, WfCommonModel workFlowCommonModel, String inboxMasterId);
	public WfInboxMaster getWfInboxMasterByRefId(String objRefId, String orgId);
	public void updateInboxHistoryFlag(WfCommonModel workFlowCommonModel, List<String> inboxIds);
	public boolean createWorkFlow(Map<String, Object> wfDataMap, WfCreateRequest payload);
//	public long updateUserInbox(WfCommonModel workFlowCommonModel, List<Map<String, Object>> userInbox);
	public List<GdWfTypes> getWfTypes(GenericRequestHeaders requestHeader);
	public List<WfServiceTasks> getWfServiceTasks(GenericRequestHeaders requestHeader, String wfTypeId);
	public GdWfTypes getWfTypeById(GenericRequestHeaders requestHeader, String wfTypeId);
	public List<GdWfTypes> getWfTypesByIds(GenericRequestHeaders requestHeader, List<String> wfTypeId);
	public Map getWfDataByWfId(GenericRequestHeaders requestHeader, String wfId);
	public List<Map> getWfTitleSearchList(GenericRequestHeaders requestHeader, List<String> wfType, String wfTypeName, String wftitle, boolean masterWf);
	public boolean createWfRefData(Map<String, Object> wfRefDataMap, String wfId);
	public WfServiceTasks getWfServiceTaskById(String serviceId);
	public String getWfShortIdByWfId(String wfId);
	public List<WfServiceTasks> getDefaultWfServiceTasks(String wfTypeId);
	public Map<String, Object> getWfMasterByWfId(String wfId);
//	public List<Map> getEmailTemplateDet(WfCommonModel wfModel);
//	public Map getMasterWF(String string);
//	public String getDefaultWfId(String orgId, String wfNonPoInvioce);
	public List<String> getRoleShortIdsByUserIds(List<String> userIds, GenericRequestHeaders requestHeader);
	public List<Integer> getUserIdsByRoleShortIds(List<String> roleShortIds, GenericRequestHeaders requestHeader);
//	public List<Map> getWfMasterDetails(OrgUserDetails payload, UmOrgRoles umOrgRoles, GenericRequestHeaders requestHeader);
//	public Map<String, Object> getDefaultWfDetails(String wfType);
//	public boolean updateWfInboxMaster(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders);
//	public List<Map> getWfDetails(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders);
//	public boolean updateInvitationData(Map<String, Object> vendorPartInviteDet, @NotNull WfUpdatePayload payload,
//			Map collectionData);
//	List<Map> updateWfInboxListOfWfIds(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders);
//	public List<WfInboxMaster> updateWorkItem(@NotNull Map payload, GenericRequestHeaders requestHeaders);
	public String getDefaultWfId(String type,Integer location);
	public List<WfInboxMasterDetails> getWfInboxDetails(WfCommonModel workFlowCommonModel);
	public WfInboxResponse getWfInboxByUserForApprover(String wfId, String objRefId);
	public void removeAccesskey(WfCommonModel workFlowCommonModel);
}
