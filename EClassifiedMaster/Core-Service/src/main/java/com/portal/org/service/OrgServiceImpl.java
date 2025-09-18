package com.portal.org.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.constants.GeneralConstants;
import com.portal.gd.entities.GdUserTypes;
import com.portal.gd.models.ListOfChildObjs;
import com.portal.gd.models.ListOfParentObjects;
import com.portal.hm.model.UserTypeModel;
import com.portal.nm.dao.NotificationDaoImpl;
import com.portal.nm.model.Notifications;
import com.portal.org.dao.OrgDao;
import com.portal.org.models.AddRoleRequest;
import com.portal.org.models.BusinessPartnerDetails;
import com.portal.org.models.DeleteBusinessPartnerRequest;
import com.portal.org.models.ListOfBusinessPartners;
import com.portal.org.models.ListOfOrgPlants;
import com.portal.org.models.ListOfOrgUsers;
import com.portal.org.models.ListOfRoles;
import com.portal.org.models.OrgDetails;
import com.portal.org.models.OrgUserDetails;
import com.portal.org.models.PlantsModel;
import com.portal.org.models.RoleDetails;
import com.portal.org.to.AddRoleTo;
import com.portal.org.to.BusinessPartnerTo;
import com.portal.org.to.ChildOjectTo;
import com.portal.org.to.OrganizationTo;
import com.portal.org.to.ParentObjectTo;
import com.portal.org.to.RolesTo;
import com.portal.repository.GdUserTypesRepo;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;
import com.portal.send.models.EmailsTo;
import com.portal.send.service.SendMessageService;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;
import com.portal.user.dao.UserDao;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.to.UserTo;

/**
 * Organization service implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service("orgService")
@Transactional
@PropertySource(value = { "classpath:/com/portal/messages/messages.properties" })
public class OrgServiceImpl implements OrgService {

	private static final Logger logger = LogManager.getLogger(OrgServiceImpl.class);

	@Autowired(required = true)
	private OrgDao orgDao;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private LoggedUserContext userContext;

	@Autowired(required = true)
	private SettingDao settingDao;

	@Autowired(required = true)
	private SendMessageService sendService;
	
	@Autowired(required = true)
	private NotificationDaoImpl notificationDaoImpl;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GdUserTypesRepo gdUserTypesRepo;	

	/**
	 * Get specific organization details
	 * 
	 * @param org_id
	 * @return
	 */
	@Override
	public GenericApiResponse getOrgDetails(String org_id) {

		String METHOD_NAME = "getOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			OrganizationTo orgTo = orgDao.getOrgDetails(org_id);

			if (orgTo != null) {

				OrgDetails orgDetails = new OrgDetails();

				BeanUtils.copyProperties(orgTo, orgDetails);

				apiResp.setStatus(0);
				apiResp.setData(orgDetails);

			} else {

				apiResp.setMessage(prop.getProperty("ORG_001"));
				apiResp.setErrorcode("ORG_001");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Add organization details
	 * 
	 * @param orgDetails
	 * @return
	 */
	@Override
	public GenericApiResponse addOrgDetails(OrgDetails orgDetails) {

		String METHOD_NAME = "addOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			OrganizationTo orgTo = new OrganizationTo();

			BeanUtils.copyProperties(orgDetails, orgTo);

			orgTo.setUserId(userContext.getLoggedUser().getUserId());

			boolean status = orgDao.addOrgDetails(orgTo);

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("ORG_002"));

			} else {

				apiResp.setMessage(prop.getProperty("ORG_003"));
				apiResp.setErrorcode("ORG_003");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while adding organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Update organization details
	 * 
	 * @param orgDetails
	 * @return
	 */
	@Override
	public GenericApiResponse updateOrgDetails(OrgDetails orgDetails) {

		String METHOD_NAME = "updateOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			OrganizationTo orgTo = new OrganizationTo();

			BeanUtils.copyProperties(orgDetails, orgTo);

			orgTo.setUserId(userContext.getLoggedUser().getUserId());

			boolean status = orgDao.updateOrgDetails(orgTo);

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("ORG_004"));

			} else {

				apiResp.setMessage(prop.getProperty("ORG_003"));
				apiResp.setErrorcode("ORG_003");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while adding organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Delete organization details
	 * 
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	@Override
	public GenericApiResponse deleteOrgDetails(String orgId) {

		String METHOD_NAME = "deleteOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			boolean status = orgDao.deleteOrgDetails(orgId, userContext.getLoggedUser().getUserId());

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("ORG_005"));

			} else {

				apiResp.setMessage(prop.getProperty("ORG_003"));
				apiResp.setErrorcode("ORG_003");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while adding organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get organization roles
	 * 
	 * @param org_id
	 * @return
	 */
	@Override
	public GenericApiResponse getOrgRoles(String org_id, String action, String role_type) {

		String METHOD_NAME = "getOrgRoles";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			List<RolesTo> rolesTo = orgDao.getOrgRoles(org_id, role_type, action);

			if (!rolesTo.isEmpty()) {

				ListOfRoles roles = new ListOfRoles();

				List<RoleDetails> rolesDetails = new ArrayList<RoleDetails>();

				for (RolesTo role : rolesTo) {

					RoleDetails details = this.getDetailsOfRole(role);

					rolesDetails.add(details);
				}

				roles.setOrgId(org_id);
				
				//sorting on role name 22-07-2023
				List<RoleDetails> sortedRoleDetails = rolesDetails.stream()
						.sorted(Comparator.comparing(RoleDetails::getRoleName))
						.collect(Collectors.toList()); 
				
				roles.setRoles(sortedRoleDetails);

				apiResp.setStatus(0);
				apiResp.setData(roles);

			} else {

				apiResp.setMessage(prop.getProperty("ORG_006"));
				apiResp.setErrorcode("ORG_006");
			}

		} catch (Exception e) {
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting organization roles: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get role details
	 * 
	 * @param role_id
	 * @return
	 */
	@Override
	public GenericApiResponse getRoleDetails(Integer role_id) {

		String METHOD_NAME = "getRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			RolesTo role = orgDao.getRoleDetails(role_id);

			if (role != null) {

				RoleDetails details = this.getDetailsOfRole(role);

				apiResp.setStatus(0);
				apiResp.setData(details);

			} else {

				apiResp.setMessage(prop.getProperty("ORG_006"));
				apiResp.setErrorcode("ORG_006");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting role details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get role details
	 * 
	 * @param role
	 * @return
	 */
	private RoleDetails getDetailsOfRole(RolesTo role) {

		RoleDetails roleDetails = new RoleDetails();

		roleDetails.setRoleId(role.getRoleId());
		roleDetails.setRoleName(role.getRoleName());
		roleDetails.setRoleShortId(role.getRoleShortId());
		roleDetails.setRoleType(role.getRoleType());
		roleDetails.setUserTypeId(role.getUserTypeId());
		
		List<ListOfParentObjects> accessObjs = new ArrayList<ListOfParentObjects>();

		for (ParentObjectTo parentObj : role.getAccessObjs()) {

			ListOfParentObjects parent = new ListOfParentObjects();

			BeanUtils.copyProperties(parentObj, parent);

			List<ListOfChildObjs> childList = new ArrayList<ListOfChildObjs>();

			if (parentObj.getChildObjs() != null) {

				for (ChildOjectTo childObj : parentObj.getChildObjs()) {

					ListOfChildObjs child = new ListOfChildObjs();

					BeanUtils.copyProperties(childObj, child);

					childList.add(child);
				}
			}

			parent.setChildObjs(childList);

			accessObjs.add(parent);
		}

		roleDetails.setAccessObjs(accessObjs);

		return roleDetails;
	}

	/**
	 * Get specific business partner details
	 * 
	 * @param bp_id
	 * @return
	 */
	@Override
	public GenericApiResponse getBusinessPartnerDetails(String bp_id) {

		String METHOD_NAME = "getBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			BusinessPartnerTo bpTo = orgDao.getBusinessPartnerDetails(bp_id);

			if (bpTo != null) {

				BusinessPartnerDetails bpDetails = new BusinessPartnerDetails();

				BeanUtils.copyProperties(bpTo, bpDetails);

				apiResp.setStatus(0);
				apiResp.setData(bpDetails);

			} else {

				apiResp.setMessage(prop.getProperty("BP_001"));
				apiResp.setErrorcode("BP_001");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Add the new business partner details
	 * 
	 * @param org_id
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse addBusinessPartnerDetails(String org_id, BusinessPartnerDetails payload) {

		String METHOD_NAME = "addBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			BusinessPartnerTo bp = new BusinessPartnerTo();

			BeanUtils.copyProperties(payload, bp);

			BusinessPartnerTo bpTo = orgDao.addBusinessPartnerDetails(bp, org_id,
					userContext.getLoggedUser().getUserId());

			if (bpTo.isSuccess()) {

				try {

					LoggedUser loggedUser = userContext.getLoggedUser();

					/**
					 * Send email to user with business details
					 */
					if (loggedUser != null && loggedUser.getLogonId() != null) {

						Map<String, String> emailConf = null;//settingService.getEmailSettings();

						EmailsTo emailTo = new EmailsTo();
						emailTo.setFrom(emailConf != null ? emailConf.get("EMAIL_FROM") : null);
						emailTo.setTo(loggedUser.getEmail());

						if (bpTo.getEmails() != null && bpTo.getEmails().contains(loggedUser.getLogonId())) {
							bpTo.getEmails().remove(loggedUser.getLogonId());
						}

						if (bpTo.getBpId() != null) {
							emailTo.setBpId(bpTo.getBpId());
						}

						if (null != bpTo.getEmails() && !bpTo.getEmails().isEmpty()) {
							emailTo.setCc(StringUtils.join(bpTo.getEmails().toArray(), ',').split(","));
						}

						emailTo.setOrgId(org_id);
						emailTo.setTemplateName(GeneralConstants.EMAIL_ADDBP_TEM_NAME);

						Map<String, Object> maprProperties = new HashMap<String, Object>();

						if (loggedUser != null && loggedUser.getFirstName() != null
								&& loggedUser.getFirstName().trim() != "") {
							maprProperties.put("name", loggedUser.getFirstName());
						}

						maprProperties.put("bPlaceName", bpTo.getName());
						maprProperties.put("orgName", bpTo.getOrgName());
						maprProperties.put("accUserID", loggedUser.getLogonId());
						emailTo.setTemplateProps(maprProperties);

						sendService.sendCommunicationMail(emailTo, emailConf);
					}

				} catch (Exception e) {
					logger.error("Error while sending email for add business partner details: "
							+ ExceptionUtils.getStackTrace(e));
				}

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("BP_002"));

			} else if (bpTo.isExisted()) {

				apiResp.setMessage(MessageFormat.format(prop.getProperty("BP_009"), payload.getBpGstinNumber()));
				apiResp.setErrorcode("BP_009");

			} else if (!bpTo.isValidGstn()) {

				apiResp.setMessage(MessageFormat.format(prop.getProperty("BP_004"), payload.getBpGstinNumber()));
				apiResp.setErrorcode("BP_004");

			} else {

				apiResp.setMessage(prop.getProperty("BP_003"));
				apiResp.setErrorcode("BP_003");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while adding business partner details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Update business partner details
	 * 
	 * @param org_id
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse updateBusinessPartnerDetails(String org_id, BusinessPartnerDetails payload) {

		String METHOD_NAME = "updateBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			BusinessPartnerTo bp = new BusinessPartnerTo();

			BeanUtils.copyProperties(payload, bp);

			BusinessPartnerTo status = orgDao.updateBusinessPartnerDetails(bp, org_id,
					userContext.getLoggedUser().getUserId());

			if (!status.isSuccess()) {

				apiResp.setMessage(prop.getProperty("BP_003"));
				apiResp.setErrorcode("BP_003");

			} else {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("BP_005"));
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while updating business partner details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Delete business partner details
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse deleteBusinessPartnersDetails(DeleteBusinessPartnerRequest payload) {

		String METHOD_NAME = "deleteBusinessPartnersDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			boolean status = orgDao.deleteBusinessPartnersDetails(payload.getBpList(), payload.getOrgId(),
					userContext.getLoggedUser().getUserId());

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("BP_006"));

			} else {

				apiResp.setMessage(prop.getProperty("BP_003"));
				apiResp.setErrorcode("ORG_003");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while deleting business partner details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Add new role for specific organization
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse addOrgRoleDetails(AddRoleRequest payload) {

		String METHOD_NAME = "addOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			AddRoleTo addRoleTo = new AddRoleTo();

			BeanUtils.copyProperties(payload, addRoleTo);

			UserTo userTo = new UserTo();

			BeanUtils.copyProperties(userContext.getLoggedUser(), userTo);

			boolean status = orgDao.addOrgRoleDetails(addRoleTo, userTo);

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("ORG_008"));

			} else {

				apiResp.setMessage(prop.getProperty("ORG_009"));
				apiResp.setErrorcode("ORG_009");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while adding new role for organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Delete the role of specific organization
	 * 
	 * @param role_id
	 * @param default_role_id
	 * @return
	 */
	@Override
	public GenericApiResponse deleteOrgRoleDetails(Integer role_id) {

		String METHOD_NAME = "deleteOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			boolean status = orgDao.deleteOrgRoleDetails(role_id);

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("ORG_010"));

			} else {

				apiResp.setMessage(prop.getProperty("ORG_009"));
				apiResp.setErrorcode("ORG_009");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while deleting role in organization : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get business partners of specific organization
	 * 
	 * @param org_id
	 * @return
	 */
	@Override
	public GenericApiResponse getOrgBusinessPartners(String org_id, String all_bp) {

		String METHOD_NAME = "getOrgBusinessPartners";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {
			List<BusinessPartnerTo> bpToList = orgDao.getOrgBusinessPartners(org_id,
					userContext.getLoggedUser().getUserId(), all_bp);

			if (bpToList != null && !bpToList.isEmpty()) {

				ListOfBusinessPartners listOfBusinessPartners = new ListOfBusinessPartners();

				List<BusinessPartnerDetails> bps = new ArrayList<BusinessPartnerDetails>();

				for (BusinessPartnerTo bpTo : bpToList) {

					BusinessPartnerDetails bpDetails = new BusinessPartnerDetails();

					BeanUtils.copyProperties(bpTo, bpDetails);

					bps.add(bpDetails);
				}

				listOfBusinessPartners.setBps(bps);

				apiResp.setStatus(0);
				apiResp.setData(listOfBusinessPartners);

			} else {

				apiResp.setMessage(prop.getProperty("BP_001"));
				apiResp.setErrorcode("BP_001");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting the business partners details of specific organization : "
					+ ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Disable the user vs business partner mapping
	 * 
	 * @param bp_id
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse disableOrgUsers(String bp_id, List<Integer> payload) {

		String METHOD_NAME = "disableOrgUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			boolean status = orgDao.disableOrgUsers(bp_id, payload, userContext.getLoggedUser().getUserId());

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("BP_007"));

			} else {

				apiResp.setMessage(prop.getProperty("BP_008"));
				apiResp.setErrorcode("BP_008");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while disable organization users : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get users of specific Organization/Business partners
	 * 
	 * @param bp_id
	 * @param org_id
	 * @param action
	 * @return
	 */
	@Override
	public GenericApiResponse getUsers(String bp_id, String org_id, String action,String type,String activeOrDeActive) {

		String METHOD_NAME = "getUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			Integer loggedUser = userContext.getLoggedUser().getUserId();

			ListOfOrgUsers listOfOrgUsers = new ListOfOrgUsers();

			List<UserTo> userToList = orgDao.getUsers(bp_id, org_id, action, loggedUser,type,activeOrDeActive);

			if (userToList != null && !userToList.isEmpty()) {

				List<OrgUserDetails> users = new ArrayList<OrgUserDetails>();

				for (UserTo userTo : userToList) {

					OrgUserDetails userDetails = new OrgUserDetails();

					BeanUtils.copyProperties(userTo, userDetails);

					userDetails.setUserLocked(userTo.isUserLocked());

					users.add(userDetails);
				}

				listOfOrgUsers.setUsers(users);

				apiResp.setStatus(0);
				apiResp.setData(listOfOrgUsers);

			} else {

				apiResp.setMessage(prop.getProperty("USR_003"));
				apiResp.setErrorcode("USR_003");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting the users of specific Organization/Business partners : "
					+ ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Assign user to business partner
	 * 
	 * @param payload
	 * @param org_id
	 * @return
	 */
	@Override
	public GenericApiResponse addBusinessPartnerUser(OrgUserDetails payload, String org_id) {

		String METHOD_NAME = "addBusinessPartnerUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {
			List<UserTo> userTo = new ArrayList<>();
			UserTo user = new UserTo();
			Random rand = new Random();
			BeanUtils.copyProperties(payload, user);				
//			user.setNewPwd(BCrypt.hashpw(GeneralConstants.DEFAULT_PASSWORD+""+rand.nextInt(10000), BCrypt.gensalt()));
			user.setNewPwd(BCrypt.hashpw(GeneralConstants.DEFAULT_PASSWORD, BCrypt.gensalt()));
			userTo.add(user);

			LoggedUser loggedUser = userContext.getLoggedUser();
			
			user = orgDao.addBusinessPartnerUser(userTo, org_id, loggedUser.getUserId(),
					loggedUser.getLogonId());

			if ((user.getMessage() != null && user.getMessage().equals("VALID") && user.isUpdated())
					||( user.getMessage() != null && payload.getActDeactAction() != null && !payload.getActDeactAction().isEmpty())) {
				
				//notifications
				this.createNotification(payload,loggedUser,user);
				try {
					/**
					 * Send mail to new added user
					 */
					if (loggedUser.getLogonId() != null) {
						
						//get Admin and HQ Emails
//						List<String> adminEmail = new ArrayList<String>();
//						List<UmOrgUsers> umOrgU = userDao.getAdminAndHqUsers();
//						for(UmOrgUsers umOrg : umOrgU) {
//							if("ADMIN".equalsIgnoreCase(umOrg.getUmOrgRoles().getRoleShortId()))
//							adminEmail.add(umOrg.getUmUsers().getEmail());
//						}
//						
//						String adminEmails = adminEmail.stream().map(Object::toString)
//								.collect(Collectors.joining(","));

						Map<String, Object> params = new HashMap<>();
						params.put("stype", SettingType.APP_SETTING.getValue());
						params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
						SettingTo settingTo = settingDao.getSMTPSettingValues(params);
						Map<String, String> emailConfigs = settingTo.getSettings();

						Map<String, Object> maprProperties = new HashMap<String, Object>();
						EmailsTo emailTo = new EmailsTo();
						emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
						emailTo.setTo(user.getEmail());
						if(payload.getActDeactAction() != null){
							if("ACTIVE".equalsIgnoreCase(payload.getActDeactAction())){
								maprProperties.put("msg", "Your Account is Activated by " + loggedUser.getFirstName() + " " + loggedUser.getLastName());
								if(payload.getRoleType().equalsIgnoreCase("SSP_USER")) {
									emailTo.setTemplateName(GeneralConstants.SSP_USER_ACTIVE);
									emailTo.setFrom(emailConfigs.get("SSP_EMAIL_FROM"));
								}else {
									emailTo.setTemplateName(GeneralConstants.USER_ACTIVE);
								}								
//								String[] adminAndHqEmails = {adminEmails};
//								emailTo.setBcc(adminAndHqEmails);
							}else if("DEACTIVE".equalsIgnoreCase(payload.getActDeactAction())) {
								maprProperties.put("msg", payload.getComments());
								if(payload.getRoleType().equalsIgnoreCase("SSP_USER")) {
									emailTo.setTemplateName(GeneralConstants.SSP_VENDOR_REJECTED);
									emailTo.setFrom(emailConfigs.get("SSP_EMAIL_FROM"));
								}
							}else{
								maprProperties.put("msg","Your Account is Deactivated by " + loggedUser.getFirstName() + " " + loggedUser.getLastName());
								emailTo.setTemplateName(GeneralConstants.USER_DE_ACTIVE);
//								String[] adminAndHqEmails = {adminEmails};
//								emailTo.setBcc(adminAndHqEmails);
							}
						}else{
							emailTo.setTemplateName(GeneralConstants.EMAIL_ADDUSER_TEM_NAME);
						}
						emailTo.setOrgId(org_id);

						maprProperties.put("action_url",emailConfigs.get("WEB_URL"));
						maprProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
						maprProperties.put("userName", loggedUser.getLogonId());//created by userName
						maprProperties.put("userId", payload.getLogonId());//new userName
						emailTo.setTemplateProps(maprProperties);

						if (user.isResetPwd()) {
							maprProperties.put("action_url",
									emailConfigs.get("WEB_URL") + "resetPassword?orgky=" + user.getResetKey());
							emailTo.setTemplateProps(maprProperties);
							emailTo.setTemplateName(GeneralConstants.USER_ADDITION);
						}
						
						if(payload.getRoleType().equalsIgnoreCase("SSP_USER")) {
							sendService.sendCommunicationToSSPMail(emailTo, emailConfigs);
						}else {
							sendService.sendCommunicationMail(emailTo, emailConfigs);
						}
//						if(GeneralConstants.USER_ADDITION.equalsIgnoreCase(emailTo.getTemplateName())) {
//							this.sendMailToAdmin(payload,user,adminEmails);
//						}
					}

				} catch (Exception e) {
					logger.error("Error while sending email for Assign user to business partner : "
							+ ExceptionUtils.getStackTrace(e));
				}
				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("USR_001"));

			} else if (user.getMessage() != null) {
				if (user.getMessage().equals("INVALID")) {
					apiResp.setMessage(prop.getProperty("USR_014"));	
					apiResp.setErrorcode("USR_014");

				} else if(user.getMessage().equals("Updated")){
					if(payload.isDeactivated()){
						apiResp.setMessage("User Deactivated Successfully");
					}else if(payload.isActiveUser()){
						apiResp.setMessage("User Activated Successfully");
					}else{
					apiResp.setMessage("Updated Successfully");
					}
					apiResp.setStatus(0);
					
				}else if (user.getMessage().equals("INVALID_GSTIN")) {

					apiResp.setMessage(MessageFormat.format(prop.getProperty("USR_015"), "GSTIN"));
					apiResp.setErrorcode("USR_015");

				} else if (user.getMessage().equals("INVALID_DLNO")) {

					apiResp.setMessage(MessageFormat.format(prop.getProperty("USR_015"), "DL.NO"));
					apiResp.setErrorcode("USR_015");

				} else if (user.getMessage().equals("EXIST")) {

					apiResp.setMessage(prop.getProperty("USR_013"));
					apiResp.setErrorcode("USR_013");

				} else {

					apiResp.setMessage(prop.getProperty("USR_002"));
					apiResp.setErrorcode("USR_002");
				}

			} else {

				apiResp.setMessage(prop.getProperty("USR_002"));
				apiResp.setErrorcode("USR_002");
			}
			 if(user.getMessage().equals("Updated")){
				if(payload.isDeactivated()){
					apiResp.setMessage("User Deactivated Successfully");
				}else if(payload.isActiveUser()){
					apiResp.setMessage("User Activated Successfully");
				}
			 }

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while Assigning user to business partner : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	private void sendMailToAdmin(OrgUserDetails payload, UserTo user, String adminEmails) {
		// TODO Auto-generated method stub
		try {
			LoggedUser loggedUser = userContext.getLoggedUser();
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> maprProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
			emailTo.setTemplateName(GeneralConstants.USER_ADDITION_MAIL_TO_ADMIN);
			emailTo.setTo(loggedUser.getEmail());
			
			//Admin Emails
			String[] adminAndHqEmails = {adminEmails};
			emailTo.setCc(adminAndHqEmails);
			
			emailTo.setOrgId(commonService.getRequestHeaders().getOrgId());
			
			maprProperties.put("action_url",emailConfigs.get("WEB_URL"));
			maprProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			maprProperties.put("userName", payload.getLogonId());// userName
			maprProperties.put("userId", payload.getLogonId());//new userName
			emailTo.setTemplateProps(maprProperties);
			
			sendService.sendCommunicationMail(emailTo, emailConfigs);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void createNotification(OrgUserDetails payload, LoggedUser loggedUser, UserTo user) {
		String METHOD_NAME = "createNotification";

		logger.info("Entered into the method: " + METHOD_NAME);
		String note = null;
		try{
		if(payload.getActDeactAction() != null && !payload.getActDeactAction().isEmpty()){
		if(payload.getActDeactAction() != null && "ACTIVE".equalsIgnoreCase(payload.getActDeactAction())){
			note = loggedUser.getLogonId() + " has Activated the user " + payload.getLogonId();
		}else{
			 note = loggedUser.getLogonId() + " has Deactivated the user " + payload.getLogonId();
		}
		}else{
			note = loggedUser.getLogonId() + " has added the user " + payload.getLogonId();
		}

		Map<String, String> mapPayload = new HashMap<String, String>();
		mapPayload.put("objectRefId", payload.getUserId() != null ? payload.getUserId()+"" : user.getUserId()+"");
		mapPayload.put("type", "User");
		mapPayload.put("actionStatus", "User");
		this.addNotification(loggedUser, note, mapPayload);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error while creating notification: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
	}

	private void addNotification(LoggedUser loggedUser, String note, Map<String, String> mapPayload) {
		String METHOD_NAME = "addNotification";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			Notifications notificationCommonModal = new Notifications();
			notificationCommonModal.setUserId(loggedUser.getUserId());
			notificationCommonModal.setUserName(loggedUser.getFirstName() + " " + loggedUser.getLastName());
			notificationCommonModal.setNotificationMessage(note);
			notificationCommonModal.setObjectRefId(mapPayload.get("objectRefId"));
			notificationCommonModal.setActionStatus(mapPayload.get("actionStatus"));
			notificationCommonModal.setType(mapPayload.get("type"));
			notificationCommonModal.setOrgId(commonService.getRequestHeaders().getOrgId());
			notificationDaoImpl.createNotifications(notificationCommonModal);
		} catch (Exception e) {
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while adding notification: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);
		
	}

	/**
	 * Update user to business partner
	 * 
	 * @param payload
	 * @param org_id
	 * @return
	 */
	@Override
	public GenericApiResponse updateBusinessPartnerUser(OrgUserDetails payload, String org_id) {

		String METHOD_NAME = "updateBusinessPartnerUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			UserTo userTo = new UserTo();

			BeanUtils.copyProperties(payload, userTo);

			boolean status = orgDao.updateBusinessPartnerUser(org_id, userTo, userContext.getLoggedUser().getUserId());

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("USR_004"));

			} else {

				apiResp.setMessage(prop.getProperty("USR_005"));
				apiResp.setErrorcode("USR_005");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while updating user to business partner : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Rename the role for specific organization
	 * 
	 * @param role_id
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse updateOrgRoleName(Integer role_id, AddRoleRequest payload) {

		String METHOD_NAME = "updateOrgRoleName";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			AddRoleTo addRoleTo = new AddRoleTo();

			BeanUtils.copyProperties(payload, addRoleTo);

			boolean status = orgDao.updateOrgRoleName(role_id, addRoleTo, userContext.getLoggedUser().getUserId());

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("GEN_001"));

			} else {

				apiResp.setMessage(prop.getProperty("ORG_009"));
				apiResp.setErrorcode("ORG_009");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while updating organization role Name : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Update the role permissions
	 * 
	 * @param org_id
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse updateOrgRoleDetails(String org_id, RoleDetails payload) {

		String METHOD_NAME = "updateOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			boolean status = orgDao.updateOrgRoleDetails(org_id, this.populateRoleTo(payload),
					userContext.getLoggedUser().getUserId());

			if (status) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("ORG_011"));

			} else {

				apiResp.setMessage(prop.getProperty("ORG_009"));
				apiResp.setErrorcode("ORG_009");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while updating organization role details : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get Roles To
	 * 
	 * @param role
	 * @return
	 */
	private RolesTo populateRoleTo(RoleDetails role) {

		RolesTo roleDetails = new RolesTo();
		roleDetails.setRoleId(role.getRoleId());
		roleDetails.setRoleName(role.getRoleName());

		List<ParentObjectTo> accessObjs = new ArrayList<ParentObjectTo>();

		for (ListOfParentObjects parentObj : role.getAccessObjs()) {

			ParentObjectTo parent = new ParentObjectTo();

			BeanUtils.copyProperties(parentObj, parent);

			List<ChildOjectTo> childList = new ArrayList<ChildOjectTo>();

			if (parentObj.getChildObjs() != null) {

				for (ListOfChildObjs childObj : parentObj.getChildObjs()) {

					ChildOjectTo child = new ChildOjectTo();

					BeanUtils.copyProperties(childObj, child);

					childList.add(child);
				}
			}

			parent.setChildObjs(childList);

			accessObjs.add(parent);
		}

		roleDetails.setAccessObjs(accessObjs);

		return roleDetails;
	}

	@Override
	public GenericApiResponse getOrgPlants(String org_id, String all_bp) {

		String METHOD_NAME = "getOrgPlants";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {
			List<BusinessPartnerTo> bpToList = orgDao.getOrgPlants(org_id, all_bp);

			if (bpToList != null && !bpToList.isEmpty()) {

				ListOfOrgPlants listOfBusinessPartners = new ListOfOrgPlants();

				List<PlantsModel> bps = new ArrayList<PlantsModel>();

				for (BusinessPartnerTo bpTo : bpToList) {

					PlantsModel bpDetails = new PlantsModel();

					BeanUtils.copyProperties(bpTo, bpDetails);

					bps.add(bpDetails);
				}

				listOfBusinessPartners.setBps(bps);

				apiResp.setStatus(0);
				apiResp.setData(listOfBusinessPartners);

			} else {

				apiResp.setMessage(prop.getProperty("BP_001"));
				apiResp.setErrorcode("BP_001");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting the business partners details of specific organization : "
					+ ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}
	
	@Override
	public GenericApiResponse checkIsExistingOrgUser(String org_id, String opu_id, String emailId) {
		
		String METHOD_NAME = "checkIsExistingOrgUser";
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			boolean isExistingOrgUser = orgDao.checkIsExistingOrgUser(org_id, opu_id, emailId);
			apiResp.setData(isExistingOrgUser);
			apiResp.setStatus(0);
			if(isExistingOrgUser)
				apiResp.setMessage(prop.getProperty("ORG_012"));
		} catch (Exception e) {
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
			logger.error("Error while checking existing user for specific organization : "
					+ ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResp;
	}

	@Override
	public GenericApiResponse getUserTypes(String orgId, String orgOpuId) {
		String METHOD_NAME = "getUserTypes";
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			List<UserTypeModel> modelList = new ArrayList<UserTypeModel>();
			List<GdUserTypes> gdUserTypes = gdUserTypesRepo.getUserTypes();
			if (gdUserTypes != null) {
				for(GdUserTypes types : gdUserTypes) {
					UserTypeModel model = new UserTypeModel();
					model.setTypeDesc(types.getTypeDesc());
					model.setTypeShortId(types.getTypeShortId());
					model.setUserTypeId(types.getUserTypeId());
					modelList.add(model);
				}
				apiResp.setData(modelList);
				apiResp.setStatus(0);
			}
		} catch (Exception e) {
			logger.error("Error while getting user types details : " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResp;
	}

	@Override
	public GenericApiResponse getSSPUsers(String bp_id, String org_id, String action,String type,String activeOrDeActive) {

		String METHOD_NAME = "getUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			Integer loggedUser = userContext.getLoggedUser().getUserId();

			ListOfOrgUsers listOfOrgUsers = new ListOfOrgUsers();

			List<UserTo> userToList = orgDao.getSSPUsers(bp_id, org_id, action, loggedUser,type,activeOrDeActive);

			if (userToList != null && !userToList.isEmpty()) {


				List<OrgUserDetails> users = new ArrayList<OrgUserDetails>();
				for (UserTo userTo : userToList) {
					if(userTo.getAadharAttachment() != null && userTo.getAttachmentId() != null && userTo.getSignature() != null) {
						OrgUserDetails userDetails = new OrgUserDetails();

						BeanUtils.copyProperties(userTo, userDetails);

						userDetails.setUserLocked(userTo.isUserLocked());

						users.add(userDetails);
					}					
				}

				listOfOrgUsers.setUsers(users);

				apiResp.setStatus(0);
				apiResp.setData(listOfOrgUsers);

			} else {

				apiResp.setMessage(prop.getProperty("USR_003"));
				apiResp.setErrorcode("USR_003");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting the users of specific Organization/Business partners : "
					+ ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	@Override
	public GenericApiResponse getActiveSspUsers(String orgOpuId, String orgId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		return null;
	}
}
