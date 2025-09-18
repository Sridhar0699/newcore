package com.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.user.dao.UserDao;
import com.portal.user.to.UserTo;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/")
public class CommonController {

	static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Lazy
	@Autowired
	private TokenStore tokenStore;

	@SuppressWarnings("unused")
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired(required = true)
	private UserDao userDao;
	
	@Autowired(required = true)
	private Environment prop;

	

	@ApiOperation(value = "Logout Service", notes = "Logout user from Vendor Portal", response = Void.class, tags = {
			"Oauth" })
	@RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void logout(HttpServletRequest request) {
		
		try {
			String tokenValue = null;
			if (request.getParameter("access_token") != null) {
				tokenValue = request.getParameter("access_token").trim();
			} else if (commonService.getRequestHeaders().getAccessToken() != null
					&& !commonService.getRequestHeaders().getAccessToken().isEmpty()) {
				tokenValue = commonService.getRequestHeaders().getAccessToken();
			}
			if(commonService.getRequestHeaders().getLoggedUser()!=null) {
				UserTo user = userDao.getUserDetails(commonService.getRequestHeaders().getLoggedUser().getLogonId());
				this.loggedOutUserDetails(user,"LOGOUT");
			}
			if (tokenValue != null) {
				logger.info("Entered in logout for the access token of:" + tokenValue);
				OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
				try {
					tokenStore
					.removeRefreshToken(accessToken.getRefreshToken());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				tokenStore.removeAccessToken(accessToken);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void loggedOutUserDetails(UserTo user, String action) {
		String METHOD_NAME = "loggedOutUserDetails";
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResponse=new GenericApiResponse();
		try {
			boolean logout=userDao.saveLoggedUserDetails(user,  action);
			if(logout) {
				apiResponse.setStatus(0);
                apiResponse.setMessage("SUCCESS");
			}
		} catch (Exception e) {
			apiResponse.setMessage(prop.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
			logger.error("Error while getting logged user details: " + ExceptionUtils.getStackTrace(e));	
		}
		
		logger.info("Exit from the method: " + METHOD_NAME);
		
	}
}