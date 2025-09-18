package com.portal.common.service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.portal.basedao.IBaseDao;
import com.portal.clf.models.PaymentGatewayDetails;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.constants.MasterData;
import com.portal.gd.dao.GlobalDataDao;
import com.portal.repository.UmUsersRepository;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;
import com.portal.user.entities.UmUsers;

@Service
public class CommonServiceImpl implements CommonService {

	private static final Logger logger = Logger.getLogger(CommonServiceImpl.class);

	@Autowired(required = true)
	private HttpServletRequest request;
	
	@Autowired
	private LoggedUserContext userContext;
	
	@Autowired
	private GlobalDataDao globalDataDao;
	
	@Autowired
	private IBaseDao baseDao; 
	
	@Autowired
	private UmUsersRepository umUsersRepository;

	@Override
	public GenericRequestHeaders getRequestHeaders() {
		GenericRequestHeaders reqHeaders = new GenericRequestHeaders();
		try {
			for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements();) {
				String nextHeaderName = (String) e.nextElement();
				if ("access_obj_id".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setAccessObjId(this.getReqHeader(nextHeaderName));
				if ("org_id".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setOrgId(this.getReqHeader(nextHeaderName));
				if ("opu_id".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setOrgOpuId(this.getReqHeader(nextHeaderName));
				if ("vendor_code".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setVendorCode(this.getReqHeader(nextHeaderName));
				if ("vendor_id".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setVendorId(this.getReqHeader(nextHeaderName));
				if ("userTypeId".equalsIgnoreCase(nextHeaderName)) {
					String userTypeId = this.getReqHeader(nextHeaderName);
					reqHeaders.setUserTypeId(userTypeId != null ? Integer.parseInt(userTypeId) : null);
				}
				if ("partner_code".equalsIgnoreCase(nextHeaderName) || "partnerCode".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setPartnerCode(this.getReqHeader(nextHeaderName));
				if ("partner_id".equalsIgnoreCase(nextHeaderName) || "partnerId".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setPartnerId(this.getReqHeader(nextHeaderName));
				if ("userTypeShortId".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setUserTypeShortId(this.getReqHeader(nextHeaderName));
				if ("Authorization".equalsIgnoreCase(nextHeaderName)){
					reqHeaders.setAccessToken(this.getReqHeader(nextHeaderName));
					if(reqHeaders.getAccessToken() != null && !reqHeaders.getAccessToken().isEmpty()){
						reqHeaders.setAccessToken(reqHeaders.getAccessToken().replace("bearer ", "").trim());
					}
				}
				if ("req_from".equalsIgnoreCase(nextHeaderName))
					reqHeaders.setReqFrom(this.getReqHeader(nextHeaderName));

			}
			reqHeaders.setLoggedUser(userContext.getLoggedUser());
		} catch (Exception e) {
			logger.error("Erros while getting Request Headers: " + ExceptionUtils.getStackTrace(e));
		}
		return reqHeaders;
	}

	@Override
	public String getReqHeader(String headerName) {
		try {
			return (request.getHeader(headerName) != null && !request.getHeader(headerName).isEmpty())
					? request.getHeader(headerName) : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PaymentGatewayDetails populatePaymentGatewayDetails(String env) {
		PaymentGatewayDetails paymentGatewayDetails = new PaymentGatewayDetails();
		Map<String, Object> payload = new HashMap<>();
		payload.put("env", env);
		LoggedUser loggedUser = new LoggedUser();
		List<Map<String, Object>> masterData = globalDataDao.getMasterData(
				MasterData.valueOf("GDPAYMENTGATEWAYCONFIG").getValue(), loggedUser, payload, "GDPAYMENTGATEWAYCONFIG");
		if (masterData != null && !masterData.isEmpty()) {
			Map<String, Object> configData = masterData.get(0);
			String base64Config = (String) configData.get("config_params");
			String configStr = new String(Base64Utils.decodeFromString(base64Config));
			try {
				JSONObject configObj = new JSONObject(configStr);
				paymentGatewayDetails.setClientSecret(configObj.getString("secret_key"));
				paymentGatewayDetails.setClientId(configObj.getString("billdeskClientId"));
				paymentGatewayDetails.setMercId(configObj.getString("merchantId"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return paymentGatewayDetails;
	}

	public Map<Integer, UmUsers> getUsersByUserIds(List<Integer> userIds) {
		Map<Integer, UmUsers> usersMap = new HashMap<Integer, UmUsers>();
		try {
			
			List<UmUsers> usersList = umUsersRepository.getUmUsersList(userIds);
			usersList.stream().forEach(user -> {
				usersMap.put(user.getUserId(), user);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usersMap;
	}
}
