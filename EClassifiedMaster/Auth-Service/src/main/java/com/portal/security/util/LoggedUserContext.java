package com.portal.security.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.portal.gd.entities.GdUserTypes;
import com.portal.security.model.LoggedUser;
import com.portal.security.model.UserTo;
import com.portal.security.repo.UmAuthCustomersRepo;
import com.portal.security.repo.UmOrgUsersRepo;
import com.portal.security.repo.UserRepository;
import com.portal.user.entities.UmCustomers;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.entities.UmUsers;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * This call contains all related security components
 * 
 * @author Sathish
 *
 */
@Service("loggedUserContext")
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoggedUserContext {

	private static final Logger logger = LogManager.getLogger(LoggedUserContext.class);

	@Autowired(required = true)
	private UserRepository userRepository;

	@Autowired
	private UmOrgUsersRepo umOrgUsersRepo;

	@Autowired(required = true)
	private HttpServletRequest request;

	@Autowired(required = true)
	private HttpServletResponse response;

	private LoggedUser loggedUser = null;
	
	@Autowired
	private UmAuthCustomersRepo authCustomersRepo;

	/**
	 * Get logged in user from context by access token
	 * 
	 * @return loginId String
	 */
	public String getLogonId() {

		String logonId = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null)
			logonId = auth.getName();

		return logonId;
	}

	/**
	 * Get logged user context details
	 * 
	 * @return
	 */
	public LoggedUser getLoggedUser() {

		try {

			if (loggedUser == null) {

				loggedUser = new LoggedUser();

				UserTo user = findByLogonId(getLogonId());

				if (user != null) {
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

				loggedUser.setIp(this.getRemoteAddress(request));
				loggedUser.setReqHeader(getHeaders().toString() + getParames().toString());
				// loggedUser.setResHeader(response.getHeaderNames().toString());
				loggedUser.setReqUrl(request.getRequestURL().toString());
				loggedUser.setReqMethod(request.getMethod());
				loggedUser.setBrowserName(userAgent.getBrowser().getName());
				loggedUser.setBrowserVersion(
						userAgent.getBrowserVersion() != null ? userAgent.getBrowserVersion().getVersion() : "");
			}

		} catch (Exception e) {
			logger.error("Erros while getting logged user context: " + ExceptionUtils.getStackTrace(e));
		}

		return loggedUser;
	}

	/**
	 * Get request headers
	 * 
	 * @return
	 */
	private Map<String, String> getHeaders() {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {

			String key = (String) headerNames.nextElement();

			String value = request.getHeader(key);

			map.put(key, value);
		}

		return map;
	}

	/**
	 * Get request Parameters
	 * 
	 * @return
	 */
	private Map<String, String> getParames() {

		Map<String, String> map = new HashMap<String, String>();

		Map<String, String[]> allMap = request.getParameterMap();

		for (String key : allMap.keySet()) {

			String[] strArr = (String[]) allMap.get(key);

			map.put(key, Arrays.toString(strArr));
		}

		return map;
	}

	/**
	 * Get Remote address
	 * 
	 * @param request
	 * @return
	 */
	private String getRemoteAddress(HttpServletRequest request) {

		String remoteAddr = null;

		if (request != null) {

			remoteAddr = request.getHeader("x-forwarded-for");

			if (remoteAddr == null || "".equals(remoteAddr)) {

				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}

	/**
	 * Find by logon id
	 * 
	 * @param Class,String
	 * 
	 * @return the BaseEntity
	 */
	@SuppressWarnings("unchecked")
	public UserTo findByLogonId(String logonId) {

		String METHOD_NAME = "findByLogonId";

		logger.info("Entered into the method: " + METHOD_NAME);

		UserTo user = null;

		try {

			List<UmUsers> users = (List<UmUsers>) userRepository.findByLogonIdCaseSensitive(logonId, false);

			if (users != null && !users.isEmpty()) {

				UmUsers umUsers = users.get(0);
				user = new UserTo();

				BeanUtils.copyProperties(umUsers, user);

				GdUserTypes userTypes = umUsers.getGdUserTypes();

				user.setUserType(userTypes.getTypeShortId());
				user.setUserTypeId(userTypes.getUserTypeId());
				user.setUserLocked(umUsers.getUserLocked());
				user.setPwd(umUsers.getPassword());
				user.setCreated_ts(umUsers.getCreatedTs());

				List<String> bpIds = new ArrayList<String>();
				List<UmOrgUsers> umOrgUsers = umOrgUsersRepo.getOrgByUser(umUsers.getUserId(), false);
				for (UmOrgUsers umOrgUser : umOrgUsers) {

					if (!umOrgUser.isMarkAsDelete()) {

						bpIds.add(umOrgUser.getOmOrgBusinessPartners().getOrgBpId());
						user.setRole(umOrgUser.getUmOrgRoles().getRoleType());
						user.setRoleId(umOrgUser.getUmOrgRoles().getRoleId().toString());
						user.setRoleDesc(umOrgUser.getUmOrgRoles().getRoleDesc());
						user.setRoleType(umOrgUser.getUmOrgRoles().getRoleShortId());
						user.setSecondaryRoles(umOrgUser.getSecondaryRoles());
					}
				}

				user.setBpIds(bpIds);
				List<UmCustomers> umCustomers = authCustomersRepo.getCustomerDetailsByUser(umUsers.getUserId());
				if(!umCustomers.isEmpty())
					user.setCustomerId(umCustomers.get(0).getCustomerId());
			}
			

		} catch (Exception e) {
			logger.error("Error while finding user detais by Logonid: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return user;
	}
}
