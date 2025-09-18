package com.portal.datasecurity;

import java.text.MessageFormat;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.common.models.GenericApiResponse;
import com.portal.datasecurity.dao.SecurityDao;
import com.portal.datasecurity.model.DataSecurity;
import com.portal.security.util.LoggedUserContext;

/**
 * Data security service implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service("DataSecurityUtil")
public class DataSecurityServiceImpl implements DataSecurityService {

	private static final Logger logger = LogManager.getLogger(DataSecurityServiceImpl.class);

	@Autowired(required = true)
	private SecurityDao securityDao;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private LoggedUserContext userContext;

	@Autowired(required = true)
	private HttpServletRequest request;

	/**
	 * Check the accessing object permissions
	 * 
	 * @param dataSecurity
	 * @return
	 */
	@Override
	public boolean verifyAccessObjectPermissions(DataSecurity dataSecurity) {

		String METHOD_NAME = "accessObjVerification";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isVerified = false;

		try {

			Integer userId = dataSecurity.getUserId();
			String accessObjId = dataSecurity.getObjId();
			String orgId = dataSecurity.getOrgId();
			String bpId = dataSecurity.getBpId();
			if(orgId == null || userId == null || accessObjId == null ){
				isVerified = true;
			} 
			else 
				isVerified = securityDao.getAccessObjPermission(orgId, bpId, userId, accessObjId);

		} catch (Exception e) {
			logger.error("Error while getting access object permissions: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isVerified;
	}

	/**
	 * Check the organization permissions
	 * 
	 * @param dataSecurity
	 * @return
	 */
	@Override
	public boolean verifyOrgPermissions(DataSecurity dataSecurity) {

		String METHOD_NAME = "accessOrgVerification";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isVerified = false;

		try {

			Integer userId = dataSecurity.getUserId();
			String orgId = dataSecurity.getOrgId();
			String bpId = dataSecurity.getBpId();
			if(orgId == null || userId == null){
				isVerified = true;
			} 
			else
				isVerified = securityDao.getOrgPermission(orgId, userId, bpId);

		} catch (Exception e) {
			logger.error("Error while getting access organization permissions: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isVerified;
	}

	/**
	 * Check the business partner permissions
	 * 
	 * @param dataSecurity
	 * @return
	 */
	@Override
	public boolean verifyBpPermissions(DataSecurity dataSecurity) {

		String METHOD_NAME = "accessBpVerification";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isVerified = false;

		try {

			Integer userId = dataSecurity.getUserId();
			String bpId = dataSecurity.getBpId();

			isVerified = bpId != null ? securityDao.getBpPermission(bpId, userId) : true;

		} catch (Exception e) {
			logger.error("Error while getting access organization permissions: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isVerified;
	}

	/**
	 * Check the IP address permissions
	 * 
	 * @param dataSecurity
	 * @return
	 */
	@Override
	public boolean verifyIpAddressPermissions(DataSecurity dataSecurity) {

		String METHOD_NAME = "verifyIpAddressPermissions";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isVerified = false;

		try {

			String orgId = dataSecurity.getOrgId();
			String bpId = dataSecurity.getBpId();

			String ipAddresses = securityDao.getIpAddresses(orgId, bpId);

			if (ipAddresses == null) {

				isVerified = true;

			} else {

				String[] split = ipAddresses.split(",");

				isVerified = Arrays.asList(split).contains(request.getRemoteAddr());
			}

		} catch (Exception e) {
			logger.error("Error while getting access organization permissions: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isVerified;
	}

	/**
	 * Data security verification
	 * 
	 * @param dataSecurity
	 * @return
	 */
	@Override
	public GenericApiResponse dataSecurityVerification(DataSecurity dataSecurity) {

		String METHOD_NAME = "dataSecurityVerification";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp.setStatus(0);

		try {

			dataSecurity.setUserId(userContext.getLoggedUser().getUserId());
			

			if (!verifyAccessObjectPermissions(dataSecurity)) {

				apiResp.setStatus(1);
				apiResp.setMessage(prop.getRequiredProperty("SEC_001"));
				apiResp.setErrorcode("SEC_001");

			} else if (!verifyOrgPermissions(dataSecurity)) {

				apiResp.setStatus(1);
				apiResp.setMessage(prop.getRequiredProperty("SEC_002"));
				apiResp.setErrorcode("SEC_002");

			} else if (!verifyBpPermissions(dataSecurity)) {

				apiResp.setStatus(1);
				apiResp.setMessage(prop.getRequiredProperty("SEC_003"));
				apiResp.setErrorcode("SEC_003");

			} else if (!verifyIpAddressPermissions(dataSecurity)) {

				apiResp.setStatus(1);
				apiResp.setMessage(MessageFormat.format(prop.getRequiredProperty("SEC_004"), request.getRemoteAddr()));
				apiResp.setErrorcode("SEC_004");
			}

		} catch (Exception e) {
			logger.error("Error while data security verification: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}
}
