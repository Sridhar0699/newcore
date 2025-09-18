package com.portal.org.dao;

import java.util.List;

import com.portal.org.to.AddRoleTo;
import com.portal.org.to.BusinessPartnerTo;
import com.portal.org.to.OrganizationTo;
import com.portal.org.to.RolesTo;
import com.portal.user.to.UserTo;

/**
 * Organization DAO
 * 
 * @author Sathish Babu D
 *
 */
public interface OrgDao {

	/**
	 * Get organization details
	 * 
	 * @param orgId
	 * @return
	 */
	public OrganizationTo getOrgDetails(String orgId);

	/**
	 * Add the new organization details
	 * 
	 * @param organizationTo
	 * @return
	 */
	public boolean addOrgDetails(OrganizationTo organizationTo);

	/**
	 * Update the existed organization details
	 * 
	 * @param organizationTo
	 * @return
	 */
	public boolean updateOrgDetails(OrganizationTo organizationTo);

	/**
	 * Delete organization details
	 * 
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	public boolean deleteOrgDetails(String orgId, Integer loggedUser);

	/**
	 * Get organization roles
	 * 
	 * @param orgId
	 * @return
	 */
	public List<RolesTo> getOrgRoles(String orgId, String roleType, String action);

	/**
	 * Get role details
	 * 
	 * @param roleId
	 * @return
	 */
	public RolesTo getRoleDetails(Integer roleId);

	/**
	 * Get business partner details
	 * 
	 * @param bpId
	 * @return
	 */
	public BusinessPartnerTo getBusinessPartnerDetails(String bpId);

	/**
	 * Add new business partner details
	 * 
	 * @param bp
	 * @param orgId
	 * @param userId
	 * @return
	 */
	public BusinessPartnerTo addBusinessPartnerDetails(BusinessPartnerTo bp, String orgId, Integer userId);

	/**
	 * Update business partner details
	 * 
	 * @param bp
	 * @param orgId
	 * @param userId
	 * @return
	 */
	public BusinessPartnerTo updateBusinessPartnerDetails(BusinessPartnerTo bp, String orgId, Integer userId);

	/**
	 * Delete business partner details
	 * 
	 * @param bpids
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	public boolean deleteBusinessPartnersDetails(List<String> bpids, String orgId, Integer loggedUser);

	/**
	 * Add new role for specific organization
	 * 
	 * @param addRoleTo
	 * @param userTo
	 * @return
	 */
	public boolean addOrgRoleDetails(AddRoleTo addRoleTo, UserTo userTo);

	/**
	 * Delete the role of specific organization
	 * 
	 * @param roleId
	 * @param defaultRoleId
	 * @return
	 */
	public boolean deleteOrgRoleDetails(Integer roleId);

	/**
	 * Get business partners of specific organization
	 * 
	 * @param orgId
	 * @return
	 */
	public List<BusinessPartnerTo> getOrgBusinessPartners(String orgId, Integer userId, String all_bp);

	public List<BusinessPartnerTo> getOrgPlants(String orgId, String all_bp);

	/**
	 * Disable the user vs business partner mapping
	 * 
	 * @param bpId
	 * @param payload
	 * @param loggedUser
	 * @return
	 */
	public boolean disableOrgUsers(String bpId, List<Integer> payload, Integer loggedUser);

	/**
	 * Get users of specific Organization/Business partners
	 * 
	 * @param bpId
	 * @param orgId
	 * @param action
	 * @return
	 */
	public List<UserTo> getUsers(String bpId, String orgId, String action, Integer loggedUser,String type,String activeOrDeActive);

	/**
	 * Assign user to business partner
	 * 
	 * @param userTo
	 * @param orgId
	 * @param loggedUser
	 * @param logonId
	 * @return
	 */
	public UserTo addBusinessPartnerUser(List<UserTo> userTo, String orgId, Integer loggedUser, String logonId);

	/**
	 * Update user to business partner
	 * 
	 * @param userTo
	 * @param orgId
	 * @return
	 */
	public boolean updateBusinessPartnerUser(String orgId, UserTo userTo, Integer userId);

	/**
	 * Rename the role for specific organization
	 * 
	 * @param roleId
	 * @param roleTo
	 * @param loggedUser
	 * @return
	 */
	public boolean updateOrgRoleName(Integer roleId, AddRoleTo roleTo, Integer loggedUser);

	/**
	 * Update the role permissions
	 * 
	 * @param orgId
	 * @param rolesTo
	 * @param loggedUser
	 * @return
	 */
	public boolean updateOrgRoleDetails(String orgId, RolesTo rolesTo, Integer loggedUser);

	/**
	 * Get Organizations
	 * 
	 * @return
	 */
	public String getportalOrganization();
	
	public boolean checkIsExistingOrgUser(String org_id, String opu_id, String emailId);

	public List<UserTo> getSSPUsers(String bp_id, String org_id, String action, Integer loggedUser, String type,
			String activeOrDeActive);
}
