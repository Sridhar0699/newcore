package com.portal.common.service;

import java.util.List;
import java.util.Map;

import com.portal.clf.models.PaymentGatewayDetails;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.user.entities.UmUsers;

public interface CommonService {
	
	public GenericRequestHeaders getRequestHeaders();
	
	public String getReqHeader(String headerName);
	
	public PaymentGatewayDetails populatePaymentGatewayDetails(String env);
	
	public Map<Integer, UmUsers> getUsersByUserIds(List<Integer> userIds);
	
}
