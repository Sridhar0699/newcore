package com.portal.org.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;
import com.portal.org.models.AddRoleRequest;
import com.portal.org.models.BusinessPartnerDetails;
import com.portal.org.models.DeleteBusinessPartnerRequest;
import com.portal.org.models.ListOfBusinessPartners;
import com.portal.org.models.ListOfOrgPlants;
import com.portal.org.models.ListOfOrgUsers;
import com.portal.org.models.ListOfRoles;
import com.portal.org.models.OrgDetails;
import com.portal.org.models.OrgUserDetails;
import com.portal.org.models.RoleDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Organization", description = "Organization management APIs")
@RequestMapping(value = GeneralConstants.API_VERSION)
public interface OrgApi {

	/**
	 * Get specific organization details
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Get organization details", notes = "Get specific organization details", response = OrgDetails.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = OrgDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Orgnization data is not found") })
	@RequestMapping(value = "/org/{org_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getOrgDetails();

	/**
	 * Add the new organization details
	 * 
	 * @param access_obj_id
	 * @param payload
	 * @return
	 */
	@ApiOperation(value = "Add organization", notes = "Add new organization", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/org", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> addOrgDetails(
			@NotNull @ApiParam(value = "Organization details") @RequestBody OrgDetails payload);

	/**
	 * Update existed organization details
	 * 
	 * @param access_obj_id
	 * @param payload
	 * @return
	 */
	@ApiOperation(value = "Update organization", notes = "Update existed organization details", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/org", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> updateOrgDetials(
			@ApiParam(value = "Organization details") @RequestBody OrgDetails payload);

	/**
	 * Delete organization details
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Delete organization", notes = "Delete organization details", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Organization is not found") })
	@RequestMapping(value = "/org/{org_id}", produces = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<?> deleteOrgDetails();

	/**
	 * Get specific business partner details
	 * 
	 * @param bp_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Get business partner details", notes = "Get specific business partner details", response = BusinessPartnerDetails.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = BusinessPartnerDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Business partner data not found") })
	@RequestMapping(value = "/org/bp/{bp_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getBusinessPartnerDetails();

	/**
	 * Add the new business partner details
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @param payload
	 * @return
	 */
	@ApiOperation(value = "Add business partner", notes = "Add the new business partner details", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/org/{org_id}/bp", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> addBusinessPartnerDetails(
			@NotNull @ApiParam(value = "Business partner details") @RequestBody BusinessPartnerDetails payload);

	/**
	 * Update the existed business partner details
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @param payload
	 * @return
	 */
	@ApiOperation(value = "Update business partner", notes = "Update the existed business partner details", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/org/{org_id}/bp", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> updateBusinessPartnerDetails(
			@NotNull @ApiParam(value = "Business partner details") @RequestBody BusinessPartnerDetails payload);

	/**
	 * Delete the multiple business partners details
	 * 
	 * @param org_id
	 * @param payload
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Delete business partners", notes = "Delete the multiple business partners details", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = Void.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Void.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
			@ApiResponse(code = 403, message = "Forbidden", response = Void.class),
			@ApiResponse(code = 404, message = "User data is not found", response = Void.class) })
	@RequestMapping(value = "/org/delete/{org_id}/bp", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> deleteBusinessPartnersDetails(
			@ApiParam(value = "Delete businesspartners details", required = true) @RequestBody DeleteBusinessPartnerRequest payload);

	/**
	 * Get business partners of specific organization
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Get business partners of organization", notes = "Get business partners of specific organization", response = ListOfBusinessPartners.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = ListOfBusinessPartners.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "No branches are found") })
	@RequestMapping(value = "/org/bps/{org_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getOrgBusinessPartners(
			@NotNull @ApiParam(value = "All business partners", required = false) @RequestParam(value = "all_bp", required = false) String all_bp);

	@ApiOperation(value = "Get plants of organization", notes = "Get plants of specific organization", response = ListOfOrgPlants.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = ListOfOrgPlants.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "No branches are found") })
	@RequestMapping(value = "/orgs/plants/{org_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getOrgPlantDetails(
			@NotNull @ApiParam(value = "All business partners", required = false) @RequestParam(value = "all_bp", required = false) String all_bp);

	/**
	 * Get users of specific Organization/Business partners
	 * 
	 * @param access_obj_id
	 * @param bp_id
	 * @return
	 * ===
	 */
	@ApiOperation(value = "Get users of Organization/Business partners", notes = "Get users of specific Organization/Business partners", response = ListOfOrgUsers.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = ListOfOrgUsers.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/org/users/{org_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getUsers(
			@NotNull @ApiParam(value = "Action ", required = true) @RequestParam(value = "action", required = true) String action,
			@NotNull @ApiParam(value = "Type ", required = false) @RequestParam(value = "type", required = false) String type,
			@NotNull @ApiParam(value = "Active Or DeActive Users", required = false) @RequestParam(value = "act_deact", required = false) String act_deact);

	/**
	 * Add user to business partner
	 * 
	 * @param payload
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Add user", notes = "Add user", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/org/bp/users", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> addBusinessPartnerUser(
			@ApiParam(value = "User information", required = true) @RequestBody OrgUserDetails payload);

	/**
	 * Update user to business partner
	 * 
	 * @param payload
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Update user to business partner", notes = "Update user to business partner", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/org/bp/users", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> updateBusinessPartnerUser(
			@ApiParam(value = "User information", required = true) @RequestBody OrgUserDetails payload);

	/**
	 * Disable the user vs business partner mapping
	 * 
	 * @param bp_id
	 * @param payload
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Disable business partner users", notes = "Disable the user vs business partner mapping", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class) })
	@RequestMapping(value = "/org/users/delete/{bp_id}", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> disableBusinessPartnerUsers(
			@ApiParam(value = "Deleted user ids", required = true) @RequestBody List<Integer> payload);

	/**
	 * Get organization role list
	 * 
	 * @param accessObjId
	 * @param orgId
	 * @return
	 * ===
	 */
	@ApiOperation(value = "Get roles", notes = "Get roles list of specific organisation", response = ListOfRoles.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = ListOfRoles.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/org/roles", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getOrgRoles(
			@NotNull @ApiParam(value = "Action(DETAILS/LIST)", required = true) @RequestParam(value = "action", required = true) String action,
			@NotNull @ApiParam(value = "Role type(RMS/SS/KAD/ALL)", required = true) @RequestParam(value = "role_type", required = true) String role_type);

	/**
	 * Get organization role details
	 * 
	 * @param role_id
	 * @param access_obj_id
	 * @param org_id
	 * @return
	 */
	@ApiOperation(value = "Get role details", notes = "Get given role details", response = RoleDetails.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = RoleDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/org/roles/{role_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getOrgRoleDetails(
			@ApiParam(value = "Role id", required = true) @PathVariable("role_id") Integer role_id);

	/**
	 * Add new role for specific organization
	 * 
	 * @param payload
	 * @param access_obj_id
	 * @return
	 * ===
	 */
	@ApiOperation(value = "Add new role", notes = "Add new role for specific organisation", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/org/roles", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> addOrgRoleDetails(
			@ApiParam(value = "Role information", required = true) @RequestBody AddRoleRequest payload);

	/**
	 * Delete the role of specific organization
	 * 
	 * @param role_id
	 * @param default_role_id
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 * ===
	 */
	@ApiOperation(value = "Delete the role", notes = "Delete the role of specific organisation", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Role data not found") })
	@RequestMapping(value = "/org/roles/{role_id}", produces = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<?> deleteOrgRoleDetails(
			@ApiParam(value = "Role id", required = true) @PathVariable("role_id") Integer role_id);

	/**
	 * Update the role permissions
	 * 
	 * @param payload
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Update the role permissions", notes = "Update the role permissions for specific organisation", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/org/roles", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> updateOrgRoleDetails(
			@ApiParam(value = "Role and Permission information") @RequestBody RoleDetails payload);

	/**
	 * Rename the role for specific organization
	 * 
	 * @param role_id
	 * @param payload
	 * @param access_obj_id
	 * @return
	 */
	@ApiOperation(value = "Rename the role for specific organization", notes = "Rename the role for specific organisation", response = Void.class, tags = {
			"Organization", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/org/roles/{role_id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> updateOrgRoleName(
			@ApiParam(value = "Role id", required = true) @PathVariable("role_id") Integer role_id,
			@ApiParam(value = "Role information", required = true) @RequestBody AddRoleRequest payload);
	
	/**
	 * Get existing users of specific Organization
	 * 
	 * @param access_obj_id
	 * @param opu_id
	 * @return
	 * ===
	 */
	@ApiOperation(value = "Get existing users of Organization", notes = "Get existing users of specific Organization", response = ListOfOrgUsers.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = ListOfOrgUsers.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/org/isexisting/orguser", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> checkIsExistingOrgUser(
			@NotNull @ApiParam(value = "email Id", required = true) @RequestParam(value = "emailId", required = true) String emailId);
	
	/**
	 * Get User Types
	 * 
	 * ===
	 */
	@ApiOperation(value = "Get User Types", notes = "Get user Types", response = ListOfOrgUsers.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = ListOfOrgUsers.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/org/usertypes", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getUserTypes();
	
	
	
	/**
	 * Get users of specific Organization/Business partners
	 * 
	 * @param access_obj_id
	 * @param bp_id
	 * @return
	 * ===
	 */
	@ApiOperation(value = "Get users of Organization/Business partners", notes = "Get users of specific Organization/Business partners", response = ListOfOrgUsers.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = ListOfOrgUsers.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/org/ssp/users/{org_id}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getSSPUsers(
			@NotNull @ApiParam(value = "Action ", required = true) @RequestParam(value = "action", required = true) String action,
			@NotNull @ApiParam(value = "Type ", required = false) @RequestParam(value = "type", required = false) String type,
			@NotNull @ApiParam(value = "Active Or DeActive Users", required = false) @RequestParam(value = "act_deact", required = false) String act_deact);
	
	
	/**
	 * Get users of specific Organization/Business partners
	 * 
	 * @param access_obj_id
	 * @param bp_id
	 * @return
	 * ===
	 */
	@ApiOperation(value = "Get users of Organization/Business partners", notes = "Get users of specific Organization/Business partners", response = ListOfOrgUsers.class, tags = {
			"Organization", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Operation", response = ListOfOrgUsers.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "No data found") })
	@RequestMapping(value = "/org/activesspusers", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> getActiveSSPUsers();

}
