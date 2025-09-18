package com.portal.security.service;

import com.portal.security.model.UserTo;
import com.portal.user.entities.UmUsers;

/**
 * User DAO
 * 
 * @author Sathish Babu D
 *
 */
public interface AuthUserDao {

	/**
	 * Find user details by logonid
	 * 
	 * @param loginId
	 * @return
	 */
	public UserTo findByLogonId(String loginId);

	/**
	 * Update logon retries
	 * 
	 * @param logonId
	 * @param action
	 */
	public void updateLogonRetries(String logonId, String action);

	public UmUsers findByLogonIdUmUsers(String logonId);

}
