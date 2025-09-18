package com.portal.workflow;


import java.util.Map;


import java.util.List;


import javax.validation.constraints.NotNull;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.workflow.model.WfCreateRequest;
import com.portal.workflow.model.WfListViewModel;

public interface WorkFlowProcessService {

	public GenericApiResponse createCustomWorkFlow(GenericRequestHeaders requestHeader, WfCreateRequest payload);
//	
//	public GenericApiResponse getWfTypes(GenericRequestHeaders requestHeader);
//	
	public GenericApiResponse getWfServiceTasks(GenericRequestHeaders requestHeader, String wfTypeId);
//
	public GenericApiResponse getWfDataByWfId(GenericRequestHeaders requestHeaders, String wfId);
	
	public GenericApiResponse getWfTitlesDropdown(GenericRequestHeaders requestHeaders, @NotNull List<String> wfTypeId, String wfTypeName, String title, boolean masterWf);
//
//	public GenericApiResponse getEmailTemplates(WfCommonModel wfCommonModel);
	public GenericApiResponse getWfListView(GenericRequestHeaders requestHeaders, @NotNull WfListViewModel payload);
	public GenericApiResponse setAsDefaultWf(GenericRequestHeaders requestHeaders, @NotNull Map payload);
	
}
