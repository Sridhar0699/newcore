package com.portal.user.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.portal.common.models.GenericApiResponse;
import com.portal.security.model.LoggedUser;
import com.portal.user.models.PasswordDetails;
import com.portal.user.models.PasswordRestRequest;
import com.portal.user.models.UserDetails;
import com.portal.user.to.UserTo;

/**
 * User service implementation
 * 
 * @author Sathish Babu D
 *
 */
public interface UserService {

	/**
	 * Get the user details of given userId
	 * 
	 * @param logonId
	 * @return
	 */
	public GenericApiResponse getUserDetails(String logonId);

	/**
	 * Add the new user details
	 * 
	 * @param payload
	 * @return
	 */
	public GenericApiResponse addUserDetails(UserDetails payload);

	/**
	 * Update the existed user details
	 * 
	 * @param payload
	 * @return
	 */
	public GenericApiResponse updateUserDetails(UserDetails payload);

	/**
	 * Send the reset password link for forgot password
	 * 
	 * @param logonId
	 * @return
	 */
	public GenericApiResponse sendRestPasswordLink(PasswordRestRequest payload);

	/**
	 * Validate the password reset key
	 * 
	 * @param resetKey
	 * @return
	 */
	public GenericApiResponse validatePasswordResetKey(String resetKey);

	/**
	 * Update the user password details
	 * 
	 * @param payload
	 * @return
	 */
	public GenericApiResponse updateUserPassword(PasswordDetails payload);

	/**
	 * Get the user status
	 * 
	 * @param logonId
	 * @return
	 */
	public GenericApiResponse getUserStatus(String logonId, String bpId, String selectedBpIds);

	/**
	 * Get the list of business partners by user
	 * 
	 * @param user_id
	 * @return
	 */
	public GenericApiResponse getBusinessPartnersByUser(Integer user_id);

	/**
	 * Lock or Unlock user account
	 * 
	 * @param user_id
	 * @param action
	 * @return
	 */
	public GenericApiResponse lockOrUnlockUser(Integer user_id, String action);

	/**
	 * Get online users
	 * 
	 * @return
	 */
	public GenericApiResponse getOnlineUsers();
	
	/**
	 * Get users losgin history 
	 * 
	 * @return
	 */
	public GenericApiResponse getUserLoginHistory(Integer loginId,String frmDate,String toDate);
	
	public GenericApiResponse getApprovers();

	public GenericApiResponse selfRegister(UserTo userTo);
	
	public GenericApiResponse generateAndValidateCaptcha();

	public GenericApiResponse getUserLoggedHistory();

	public GenericApiResponse createOrUpdateCustomer(@NotNull @NotNull List<UserTo> payload);

	public GenericApiResponse createSSPCustomer(@NotNull UserTo payload);

	public GenericApiResponse getUsersByBookingCode(@NotNull UserTo payload);

	public GenericApiResponse getRmsApprovers();

	public GenericApiResponse getExecutiveCreatedListOfUsers(LoggedUser loggedUser);
	
}
