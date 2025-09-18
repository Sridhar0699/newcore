package com.portal.user.dao;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.portal.org.to.ListOfOrganizationsTo;
import com.portal.security.model.LoggedUser;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.entities.UmUsers;
import com.portal.user.to.UserTo;

/**
 * User DAO
 * 
 * @author Sathish Babu D
 *
 */
public interface UserDao {

	/**
	 * Find user details by logonid
	 * 
	 * @param loginId
	 * @return
	 */	
	public UserTo findByLogonId(String loginId);

	/**
	 * Get the user details of given userId
	 * 
	 * @param logonId
	 * @return
	 */
	public UserTo getUserDetails(String logonId);


	/**
	 * Update existed user details
	 * 
	 * @param user
	 * @return
	 */
	public boolean updateUserDetails(UserTo user);

	/**
	 * Send the reset password link for forgot password
	 * 
	 * @param logonId
	 * @param emailId 
	 * @param loggedUser
	 * @return
	 */
	public List<UserTo> generateRestPasswordLink(String action, String logonId, List<Integer> users, String emailId);

	/**
	 * Validate the password reset key status
	 * 
	 * @param resetKey
	 * @return
	 */
	public UserTo validatePasswordResetKey(String resetKey);

	/**
	 * Update the user password details
	 * 
	 * @param userPwd
	 * @return
	 */
	public UserTo updateUserPassword(UserTo userPwd, String action);

	/**
	 * Update logon retries
	 * 
	 * @param logonId
	 * @param action
	 */
	public void updateLogonRetries(String logonId, String action);

	/**
	 * Get the list of business partners by user
	 * 
	 * @param userId
	 * @return
	 */
	public ListOfOrganizationsTo getBusinessPartnersByUser(Integer userId);

	/**
	 * Lock or Unlock user account
	 * 
	 * @param user_id
	 * @param action
	 * @return
	 */
	public boolean lockOrUnlockUser(Integer user_id, String action);

	/**
	 * Get Online users
	 * 
	 * @return
	 */
	public List<UserTo> getOnlineUsers();
	
	/**
	 * Get Online users
	 * 
	 * @return
	 */
	public List<UserTo> getUserLoginHistory(Integer loginId, String frmDate, String toDate);

	public UmUsers getUserById(Integer userId);

	public List<UmOrgUsers> getAdminAndHqUsers();
	public Boolean selfRegister(UserTo user);
	public boolean saveLoggedUserDetails(UserTo user,String action);

	public boolean sspSelfRegister(UserTo userTo);

	public List<UserTo> getUserByBookingCode(@NotNull UserTo payload);

	public List<UserTo> getRmsApprovers();

	public Map<Integer, UserTo> getExecutiveCreatedUsers(LoggedUser loggedUser);
	
}
