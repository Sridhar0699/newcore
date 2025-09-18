package com.portal.org.controller;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.datasecurity.DataSecurityService;
import com.portal.datasecurity.model.DataSecurity;
import com.portal.org.models.AddRoleRequest;
import com.portal.org.models.BusinessPartnerDetails;
import com.portal.org.models.DeleteBusinessPartnerRequest;
import com.portal.org.models.OrgDetails;
import com.portal.org.models.OrgUserDetails;
import com.portal.org.models.RoleDetails;
import com.portal.org.service.OrgService;

import io.swagger.annotations.ApiParam;

@Controller
@PropertySource(value = { "classpath:/com/portal/messages/messages.properties" })
public class OrgApiController implements OrgApi {

	private static final Logger logger = LogManager.getLogger(OrgApiController.class);

	@Autowired(required = true)
	private OrgService orgService;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private DataSecurityService dataSecurityService;
	
	@Autowired
	private CommonService commonService;

	/**
	 * Get specific organization details
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getOrgDetails() {

		String METHOD_NAME = "getOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		Date respFrmTs = new Date();

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getOrgDetails(commonService.getRequestHeaders().getOrgId());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Add the new organization details
	 * 
	 * @param access_obj_id
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> addOrgDetails(
			@NotNull @ApiParam(value = "Organization details") @RequestBody OrgDetails payload) {

		String METHOD_NAME = "addOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();


			apiResp = orgService.addOrgDetails(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Update existed organization details
	 * 
	 * @param access_obj_id
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> updateOrgDetials(
			@ApiParam(value = "Organization details") @RequestBody OrgDetails payload) {

		String METHOD_NAME = "updateOrgDetials";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

			apiResp = orgService.updateOrgDetails(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Delete organization details
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> deleteOrgDetails() {

		String METHOD_NAME = "deleteOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);


		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

			apiResp = orgService.deleteOrgDetails(commonService.getRequestHeaders().getOrgId());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get specific business partner details
	 * 
	 * @param bp_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getBusinessPartnerDetails() {

		String METHOD_NAME = "getBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getBusinessPartnerDetails(commonService.getRequestHeaders().getOrgOpuId());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Add the new business partner details
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> addBusinessPartnerDetails(
			@NotNull @ApiParam(value = "Business partner details") @RequestBody BusinessPartnerDetails payload) {

		String METHOD_NAME = "addBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.addBusinessPartnerDetails(commonService.getRequestHeaders().getOrgId(), payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Update the existed business partner details
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> updateBusinessPartnerDetails(
			@NotNull @ApiParam(value = "Business partner details") @RequestBody BusinessPartnerDetails payload) {

		String METHOD_NAME = "updateBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.updateBusinessPartnerDetails(commonService.getRequestHeaders().getOrgId(), payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Delete the multiple business partners details
	 * 
	 * @param org_id
	 * @param payload
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> deleteBusinessPartnersDetails(
			@ApiParam(value = "Deleted businesspartners details", required = true) @RequestBody DeleteBusinessPartnerRequest payload) {

		String METHOD_NAME = "deleteBusinessPartnersDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.deleteBusinessPartnersDetails(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get business partners of specific organization
	 * 
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getOrgBusinessPartners(
			@NotNull @ApiParam(value = "All business partners", required = false) @RequestParam(value = "all_bp", required = false) String all_bp) {

		String METHOD_NAME = "getOrgBusinessPartners";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getOrgBusinessPartners(commonService.getRequestHeaders().getOrgId(), all_bp);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get users of specific Organization/Business partners
	 * 
	 * @param access_obj_id
	 * @param bp_id
	 * @param org_id
	 * @param action
	 * @return
	 */
	@Override
	public ResponseEntity<?> getUsers(
			@NotNull @ApiParam(value = "Action ", required = true) @RequestParam(value = "action", required = true) String action,
			@NotNull @ApiParam(value = "Type ", required = false) @RequestParam(value = "type", required = false) String type,
			@NotNull @ApiParam(value = "Active Or DeActive Users", required = false) @RequestParam(value = "act_deact", required = false) String act_deact) {

		String METHOD_NAME = "getUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getUsers(commonService.getRequestHeaders().getOrgOpuId(),
				commonService.getRequestHeaders().getOrgId(), action, type, act_deact);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Assign user to business partner
	 * 
	 * @param payload
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> addBusinessPartnerUser(
			@ApiParam(value = "User information", required = true) @RequestBody OrgUserDetails payload) {

		String METHOD_NAME = "addBusinessPartnerUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		String bpId = commonService.getRequestHeaders().getOrgOpuId();
		payload.setBpId(bpId);
		apiResp = orgService.addBusinessPartnerUser(payload, commonService.getRequestHeaders().getOrgId());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Update user to business partner
	 * 
	 * @param payload
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> updateBusinessPartnerUser(
			@ApiParam(value = "User information", required = true) @RequestBody OrgUserDetails payload) {

		String METHOD_NAME = "updateBusinessPartnerUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.updateBusinessPartnerUser(payload, commonService.getRequestHeaders().getOrgId());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Disable the user vs business partner mapping
	 * 
	 * @param bp_id
	 * @param payload
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> disableBusinessPartnerUsers(
			@ApiParam(value = "Deleted user ids", required = true) @RequestBody List<Integer> payload) {

		String METHOD_NAME = "disableBusinessPartnerUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		Date respFrmTs = new Date();

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.disableOrgUsers(commonService.getRequestHeaders().getOrgOpuId(), payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get organization role list
	 * 
	 * @param accessObjId
	 * @param orgId
	 * @return
	 */
	@Override
	public ResponseEntity<?> getOrgRoles(
			@NotNull @ApiParam(value = "Action(DETAILS/LIST)", required = true) @RequestParam(value = "action", required = true) String action,
			@NotNull @ApiParam(value = "Role type(SADM/PADM/OADM/SPV/ALL)", required = true) @RequestParam(value = "role_type", required = true) String role_type) {

		String METHOD_NAME = "getOrgRoles";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getOrgRoles(commonService.getRequestHeaders().getOrgId(), action, role_type);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get organization role details
	 * 
	 * @param role_id
	 * @param access_obj_id
	 * @param org_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getOrgRoleDetails(
			@ApiParam(value = "Role id", required = true) @PathVariable("role_id") Integer role_id) {

		String METHOD_NAME = "getOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getRoleDetails(role_id);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Add new role for specific organization
	 * 
	 * @param payload
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> addOrgRoleDetails(
			@ApiParam(value = "Role Details", required = true) @RequestBody AddRoleRequest payload) {

		String METHOD_NAME = "addOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.addOrgRoleDetails(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Delete the role of specific organization
	 * 
	 * @param role_id
	 * @param default_role_id
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> deleteOrgRoleDetails(
			@ApiParam(value = "Role id", required = true) @PathVariable("role_id") Integer role_id) {

		String METHOD_NAME = "deleteOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.deleteOrgRoleDetails(role_id);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Update the role permissions
	 * 
	 * @param payload
	 * @param org_id
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> updateOrgRoleDetails(
			@ApiParam(value = "Role and Permission information") @RequestBody RoleDetails payload) {

		String METHOD_NAME = "updateOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.updateOrgRoleDetails(commonService.getRequestHeaders().getOrgId(), payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Rename the role for specific organization
	 * 
	 * @param role_id
	 * @param payload
	 * @param access_obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> updateOrgRoleName(
			@ApiParam(value = "Role id", required = true) @PathVariable("role_id") Integer role_id,
			@ApiParam(value = "Role information", required = true) @RequestBody AddRoleRequest payload) {

		String METHOD_NAME = "updateOrgRoleName";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.updateOrgRoleName(role_id, payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getOrgPlantDetails(
			@NotNull @ApiParam(value = "All business partners", required = false) @RequestParam(value = "all_bp", required = false) String all_bp) {

		String METHOD_NAME = "getOrgPlantDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

			apiResp.setStatus(0);
			if (apiResp.getStatus() == 0) {

				apiResp = orgService.getOrgPlants(commonService.getRequestHeaders().getOrgId(), all_bp);
			}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	/**
	 * Get existing users of specific Organization
	 * 
	 * @param access_obj_id
	 * @param opu_id
	 * @return
	 * ===
	 */
	public ResponseEntity<?> checkIsExistingOrgUser(
			@NotNull @ApiParam(value = "email Id", required = true) @RequestParam(value = "emailId", required = true) String emailId) {
		GenericRequestHeaders requestHeaders = commonService.getRequestHeaders();
		GenericApiResponse apiResp = orgService.checkIsExistingOrgUser(requestHeaders.getOrgId(),
				requestHeaders.getOrgOpuId(), emailId);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getUserTypes() {
		GenericRequestHeaders requestHeaders = commonService.getRequestHeaders();
		GenericApiResponse apiResp = orgService.getUserTypes(requestHeaders.getOrgId(),
				requestHeaders.getOrgOpuId());
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getSSPUsers(@NotNull @ApiParam(value = "Action ", required = true) @RequestParam(value = "action", required = true) String action,
			@NotNull @ApiParam(value = "Type ", required = false) @RequestParam(value = "type", required = false) String type,
			@NotNull @ApiParam(value = "Active Or DeActive Users", required = false) @RequestParam(value = "act_deact", required = false) String act_deact) {

		String METHOD_NAME = "getSSPUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getSSPUsers(commonService.getRequestHeaders().getOrgOpuId(),
				commonService.getRequestHeaders().getOrgId(), action, type, act_deact);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getActiveSSPUsers() {

		String METHOD_NAME = "getActiveSSPUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getActiveSspUsers(commonService.getRequestHeaders().getOrgOpuId(),
				commonService.getRequestHeaders().getOrgId());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
}