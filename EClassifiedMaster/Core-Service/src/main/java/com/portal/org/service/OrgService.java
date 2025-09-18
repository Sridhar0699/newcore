package com.portal.org.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.portal.common.models.GenericApiResponse;
import com.portal.org.models.AddRoleRequest;
import com.portal.org.models.BusinessPartnerDetails;
import com.portal.org.models.DeleteBusinessPartnerRequest;
import com.portal.org.models.OrgDetails;
import com.portal.org.models.OrgUserDetails;
import com.portal.org.models.RoleDetails;

/**
 * Organization service
 * 
 * @author Sathish Babu D
 *
 */
public interface OrgService {

	/**
	 * Get specific organization details
	 * 
	 * @param org_id
	 * @return
	 */
	public GenericApiResponse getOrgDetails(String org_id);

	/**
	 * Add organization details
	 * 
	 * @param orgDetails
	 * @return
	 */
	public GenericApiResponse addOrgDetails(OrgDetails orgDetails);

	/**
	 * Update organization details
	 * 
	 * @param orgDetails
	 * @return
	 */
	public GenericApiResponse updateOrgDetails(OrgDetails orgDetails);

	/**
	 * Delete organization details
	 * 
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	public GenericApiResponse deleteOrgDetails(String org_id);

	/**
	 * Get organization roles
	 * 
	 * @param org_id
	 * @return
	 */
	public GenericApiResponse getOrgRoles(String org_id, String action, String role_type);

	/**
	 * Get role details
	 * 
	 * @param role_id
	 * @return
	 */
	public GenericApiResponse getRoleDetails(Integer role_id);

	/**
	 * Get specific business partner details
	 * 
	 * @param bp_id
	 * @return
	 */
	public GenericApiResponse getBusinessPartnerDetails(String bp_id);

	/**
	 * Add the new business partner details
	 * 
	 * @param org_id
	 * @param payload
	 * @return
	 */
	public GenericApiResponse addBusinessPartnerDetails(String org_id, BusinessPartnerDetails payload);

	/**
	 * Update business partner details
	 * 
	 * @param org_id
	 * @param payload
	 * @return
	 */
	public GenericApiResponse updateBusinessPartnerDetails(String org_id, BusinessPartnerDetails payload);

	/**
	 * Delete business partner details
	 * 
	 * @param payload
	 * @return
	 */
	public GenericApiResponse deleteBusinessPartnersDetails(DeleteBusinessPartnerRequest payload);

	/**
	 * Add new role for specific organization
	 * 
	 * @param payload
	 * @return
	 */
	public GenericApiResponse addOrgRoleDetails(AddRoleRequest payload);

	/**
	 * Delete the role of specific organization
	 * 
	 * @param role_id
	 * @param default_role_id
	 * @return
	 */
	public GenericApiResponse deleteOrgRoleDetails(Integer role_id);

	/**
	 * Get business partners of specific organization
	 * 
	 * @param org_id
	 * @return
	 */
	public GenericApiResponse getOrgBusinessPartners(String org_id, String all_bp);

	public GenericApiResponse getOrgPlants(String org_id, String all_bp);

	/**
	 * Disable the user vs business partner mapping
	 * 
	 * @param bp_id
	 * @param payload
	 * @return
	 */
	public GenericApiResponse disableOrgUsers(String bp_id, List<Integer> payload);

	/**
	 * Get users of specific Organization/Business partners
	 * 
	 * @param bp_id
	 * @param org_id
	 * @param action
	 * @return
	 */
	public GenericApiResponse getUsers(String bp_id, String org_id, String action,String type,String activeOrDeActive);

	/**
	 * Assign user to business partner
	 * 
	 * @param payload
	 * @param org_id
	 * @return
	 */
	public GenericApiResponse addBusinessPartnerUser(OrgUserDetails payload, String org_id);

	/**
	 * Update user to business partner
	 * 
	 * @param payload
	 * @param org_id
	 * @return
	 */
	public GenericApiResponse updateBusinessPartnerUser(OrgUserDetails payload, String org_id);

	/**
	 * Rename the role for specific organization
	 * 
	 * @param role_id
	 * @param payload
	 * @return
	 */
	public GenericApiResponse updateOrgRoleName(Integer role_id, AddRoleRequest payload);

	/**
	 * Update the role permissions
	 * 
	 * @param payload
	 * @param org_id
	 * @return
	 */
	public GenericApiResponse updateOrgRoleDetails(String org_id, RoleDetails payload);
	
	public GenericApiResponse checkIsExistingOrgUser(String org_id, String opu_id, String emailId);

	public GenericApiResponse getUserTypes(String orgId, String orgOpuId);

	public GenericApiResponse getSSPUsers(String orgOpuId, String orgId, @NotNull String action, @NotNull String type,
			@NotNull String act_deact);

	public GenericApiResponse getActiveSspUsers(String orgOpuId, String orgId);
}
