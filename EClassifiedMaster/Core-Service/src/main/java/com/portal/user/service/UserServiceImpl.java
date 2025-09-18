package com.portal.user.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portal.basedao.IBaseDao;
import com.portal.common.models.DocumentsModel;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.common.service.DocumentService;
import com.portal.constants.ActivityConstants;
import com.portal.constants.GeneralConstants;
import com.portal.gd.dao.GlobalDataDao;
import com.portal.gd.entities.BookingUnits;
import com.portal.gd.entities.GdNumberSeries;
import com.portal.gd.entities.GdUserTypes;
import com.portal.gd.to.GdSettingConfigsTo;
import com.portal.internal.models.CaptchaValidation;
import com.portal.org.models.ListOfOrganization;
import com.portal.org.models.OrgBusinessPartners;
import com.portal.org.models.OrganizationDetails;
import com.portal.org.to.BusinessPartnerTo;
import com.portal.org.to.ListOfOrganizationsTo;
import com.portal.org.to.OrganizationTo;
import com.portal.reports.utility.CommonUtils;
import com.portal.repository.BookingUnitsRepo;
import com.portal.repository.GdNumberSeriesRepo;
import com.portal.repository.UmCustomersRepo;
import com.portal.repository.UmOrgUsersRepository;
import com.portal.repository.UmUsersRepository;
import com.portal.rms.entity.RmsVersions;
import com.portal.rms.entity.UserLoginHistory;
import com.portal.rms.model.RmsUserLoginHistoryDetails;
import com.portal.rms.repository.RmsVersionsRepo;
import com.portal.rms.repository.UserLoginHistoryRepo;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;
import com.portal.send.models.EmailsTo;
import com.portal.send.service.SendMessageService;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;
import com.portal.user.dao.UserDao;
import com.portal.user.entities.UmCustomers;
import com.portal.user.entities.UmUsers;
import com.portal.user.models.ListOfOnlineUsers;
import com.portal.user.models.OnlineUserDetails;
import com.portal.user.models.PasswordDetails;
import com.portal.user.models.PasswordRestRequest;
import com.portal.user.models.ResetKeyStatus;
import com.portal.user.models.UserDetails;
import com.portal.user.models.UserStatus;
import com.portal.user.to.ApprovalUserTo;
import com.portal.user.to.UserTo;
import com.portal.utils.DataCryptoUtil;

/**
 * User service implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service("userService")
@Transactional
@PropertySource(value = { "classpath:/com/portal/messages/messages.properties" })
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private static final String ACTIVE = "Active";
	private static final String INACTIVE = "In Active";

	@Autowired(required = true)
	private UserDao userDao;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private LoggedUserContext userContext;

	@Autowired(required = true)
	private SettingDao settingDao;
	
	@Autowired(required = true)
	private SendMessageService sendService;
	
	@Autowired(required = true)
	private GlobalDataDao globalDataDao;
	
	@Autowired
	private UmOrgUsersRepository umOrgUsersRepo;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private UmUsersRepository umUsersRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserLoginHistoryRepo loginHistoryRepo;
	
	@Autowired
	private RmsVersionsRepo rmsVersionsRepo;
	
	@Autowired
	private UmCustomersRepo umCustomersRepo;
	
	@Autowired
	private GdNumberSeriesRepo gdNumberSeriesRepo;
	

	@Autowired
	private BookingUnitsRepo bookingUnitsRepo;
	
	@Autowired(required = true)
	private IBaseDao baseDao;
	
	@Autowired
	private PasswordEncoder encoder;

	/**
	 * Get the user details of given userId
	 * 
	 * @param logonId
	 * @return
	 */
	@Override
	public GenericApiResponse getUserDetails(String logonId) {

		String METHOD_NAME = "getUserDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			UserTo user = userDao.getUserDetails(logonId);

			if (user != null) {

				UserDetails userDetails = new UserDetails();

				BeanUtils.copyProperties(user, userDetails);
				userDetails.setIsSubmitted(user.isSubmitted());
				//User profile (image)
				if (userDetails.getProfileId() != null && !userDetails.getProfileId().isEmpty()) {
					DocumentsModel docModel = documentService.downloadUserProfile(userDetails.getProfileId());
					if (docModel != null && docModel.getResource() != null) {
						byte[] sourceBytes = IOUtils.toByteArray(docModel.getResource().getInputStream());
						userDetails.setImageUrl(Base64.getEncoder().encodeToString(sourceBytes));
					}
				}
				
				userDetails.setRoleShortId(user.getRoleType());
				if(user.getCategoryId()!=null && !user.getCategoryId().isEmpty())
					userDetails.setClassification(user.getCategoryId());
				
				if(commonService.getRequestHeaders().getReqFrom() != null && "RMS".equalsIgnoreCase(commonService.getRequestHeaders().getReqFrom())) {
					List<RmsVersions> rmsVersionsList = rmsVersionsRepo.getRmsVersions();
					if(rmsVersionsList != null && !rmsVersionsList.isEmpty()) {
						userDetails.setAppVersion(rmsVersionsList.get(0).getAppVersion());
					}
				}
				apiResp.setStatus(0);
				apiResp.setData(userDetails);
				
				this.loggedUserDetails(user, "LOGIN");			    	

			} else {

				apiResp.setMessage(prop.getProperty("USR_003"));
				apiResp.setErrorcode("USR_003");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting user details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	private void loggedUserDetails(UserTo user, String action) {
		String METHOD_NAME = "loggedUserDetails";
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResponse=new GenericApiResponse();
		try {
			boolean login=userDao.saveLoggedUserDetails(user,  action);
			if(login) {
				apiResponse.setStatus(0);
                apiResponse.setMessage("SUCCESS");
			}
		} catch (Exception e) {
			apiResponse.setMessage(prop.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
			logger.error("Error while getting logged user details: " + ExceptionUtils.getStackTrace(e));	
		}
		
		logger.info("Exit from the method: " + METHOD_NAME);
		
	}

	/**
	 * Add the new user details
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse addUserDetails(UserDetails payload) {

		String METHOD_NAME = "addUserDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			UserTo user = new UserTo();

			BeanUtils.copyProperties(payload, user);

			user.setLoggedUser(userContext.getLoggedUser().getUserId());

			if (payload.getPassword() != null)
				user.setPwd(BCrypt.hashpw(payload.getPassword(), BCrypt.gensalt()));

			//user = userDao.addUserDetails(user); // need to verify 19 may sathish

			if (user.isExisted()) {

				apiResp.setMessage(prop.getProperty("USR_011"));
				apiResp.setErrorcode("USR_011");

			} else if (user.isUpdated()) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("USR_001"));

			} else {

				apiResp.setMessage(prop.getProperty("USR_002"));
				apiResp.setErrorcode("USR_002");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while adding user details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Update the existed user details
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse updateUserDetails(UserDetails payload) {

		String METHOD_NAME = "updateUserDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			UserTo user = new UserTo();

			BeanUtils.copyProperties(payload, user);

			user.setLoggedUser(userContext.getLoggedUser().getUserId());

			if (payload.getPassword() != null)
				user.setPwd(BCrypt.hashpw(payload.getPassword(), BCrypt.gensalt()));

			boolean status = userDao.updateUserDetails(user);

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

			logger.error("Error while adding user details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Send the reset password link for forgot password
	 * 
	 * @param logonId
	 * @return
	 */
	@Override
	public GenericApiResponse sendRestPasswordLink(PasswordRestRequest payload) {

		String METHOD_NAME = "sendRestPasswordLink";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			String action = payload.getAction();

			List<UserTo> users = userDao.generateRestPasswordLink(action, payload.getLogonId(), payload.getUserIds(), payload.getEmailId());

			if (!users.isEmpty()) {

				Map<String, Object> params = new HashMap<>();
				params.put("stype", SettingType.APP_SETTING.getValue());
				params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
				SettingTo settingTo = settingDao.getSMTPSettingValues(params);
				Map<String, String> emailConfigs = settingTo.getSettings();
				
				for (UserTo userTo : users) {

					EmailsTo emailTo = new EmailsTo();
					emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
					emailTo.setTo(userTo.getEmail());
					emailTo.setOrgId(userTo.getCurrentOrgId());

					Map<String, Object> templateProps = new HashMap<String, Object>();
					templateProps.put("resetPwd_url",
							emailConfigs.get("WEB_URL") + "resetPassword?ky=" + userTo.getResetKey());

					emailTo.setTemplateProps(templateProps);

					if ("RESETPWD".equals(action)) {

						emailTo.setTemplateName(GeneralConstants.EMAIL_RESET_PWD_TEM_NAME);

					} else if ("FORGOTPWD".equals(action)) {

						emailTo.setTemplateName(GeneralConstants.EMAIL_FP_TEM_NAME);
					}

					Map<String, String> msgProps = new HashMap<String, String>();
					msgProps.put(ActivityConstants.user_name_p1, userTo.getFirstName());

					sendService.sendCommunicationMail(emailTo, emailConfigs);
				}

				apiResp.setStatus(0);

				if ("RESETPWD".equals(action)) {

					apiResp.setMessage(prop.getProperty("USR_010"));

				} else if ("FORGOTPWD".equals(action)) {

					apiResp.setMessage(MessageFormat.format(prop.getProperty("USR_009"), payload.getLogonId()));
				}

			} else {

				apiResp.setMessage(prop.getProperty("USR_006"));
				apiResp.setErrorcode("USR_006");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while sending reset password link: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Validate the password reset key
	 * 
	 * @param resetKey
	 * @return
	 */
	@Override
	public GenericApiResponse validatePasswordResetKey(String resetKey) {

		String METHOD_NAME = "validatePasswordResetKey";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			UserTo userPwd = userDao.validatePasswordResetKey(resetKey);

			ResetKeyStatus resetKeyStatus = new ResetKeyStatus();
			resetKeyStatus.setKeyStatus(INACTIVE);

			if (userPwd != null && userPwd.getKeyValidTime() != null) {

				Long validDt = userPwd.getKeyValidTime().getTime();
				Long currentDt = new Date().getTime();

				if (currentDt <= validDt) {

					resetKeyStatus.setKeyStatus(ACTIVE);
					resetKeyStatus.setUserId(userPwd.getUserId());
				}
			}

			if (ACTIVE.equals(resetKeyStatus.getKeyStatus())) {

				apiResp.setStatus(0);
				apiResp.setData(resetKeyStatus);

			} else {

				apiResp.setMessage(prop.getProperty("USR_008"));
				apiResp.setErrorcode("USR_008");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while vaidating password reset key: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Update the user password details
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public GenericApiResponse updateUserPassword(PasswordDetails payload) {

		String METHOD_NAME = "updateUserPassword";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			boolean isUpdated = false;

			String action = payload.getAction();

			UserTo userPwd = new UserTo();

			BeanUtils.copyProperties(payload, userPwd);

			UserTo userTo = new UserTo();

			//userPwd.setEncryOldPwd(BCrypt.hashpw(userPwd.getOldPwd(), BCrypt.gensalt()));
			userPwd.setEncryNewPwd(BCrypt.hashpw(userPwd.getNewPwd(), BCrypt.gensalt()));

			//userTo = userDao.findByLogonId(payload.getLogonId());
			if ("RESETPWD".equals(action)) {
				userPwd.setPwdMatched(BCrypt.checkpw(userPwd.getOldPwd(), userTo.getPwd()));
			}
			
			userTo = userDao.updateUserPassword(userPwd, action);
			isUpdated = userTo.isUpdated();

			if ("RESETPWD".equals(action)) {

				if (!userTo.isPwdMatched()) {

					apiResp.setMessage(prop.getProperty("USR_012"));
					apiResp.setErrorcode("USR_012");
				}
			}

			if (!userTo.isExisted()) {

				apiResp.setMessage(prop.getProperty("USR_005"));
				apiResp.setErrorcode("USR_005");
			}

			if (isUpdated) {

				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("USR_007"));
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while updating the password details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get the user status
	 * 
	 * @param logonId
	 * @return
	 */
	@Override
	public GenericApiResponse getUserStatus(String logonId, String bpId, String selectedBpIds) {

		String METHOD_NAME = "getUserStatus";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			UserStatus userStatus = new UserStatus();

			UserTo user = userDao.findByLogonId(logonId);

			if (user != null) {

				userStatus.setExisted(true);
				userStatus.setUserId(user.getUserId());
				userStatus.setLogonId(user.getLogonId());
				userStatus.setStatus(user.isMarkAsDelete() ? INACTIVE : ACTIVE);
				userStatus.setAssigned(false);
				String[] ids = selectedBpIds.split(",");
				for(String id : ids){
					if(user.getBpIds().contains(id)){
						userStatus.setAssigned(true);
						break;
					}
				}
			}

			apiResp.setStatus(0);
			apiResp.setData(userStatus);

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting the user status: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get the list of business partners by user
	 * 
	 * @param user_id
	 * @return
	 */
	@Override
	public GenericApiResponse getBusinessPartnersByUser(Integer user_id) {

		String METHOD_NAME = "getBusinessPartnersByUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			ListOfOrganization listOfOrganization = new ListOfOrganization();

			ListOfOrganizationsTo listofOrgTo = userDao.getBusinessPartnersByUser(user_id);

			if (listofOrgTo != null) {

				listOfOrganization.setRedirect(listofOrgTo.isRedirect());

				List<OrganizationTo> orgtolist = listofOrgTo.getOrgs();

				List<OrganizationDetails> orgs = new ArrayList<OrganizationDetails>();

				for (OrganizationTo orgTo : orgtolist) {

					OrganizationDetails org = new OrganizationDetails();

					BeanUtils.copyProperties(orgTo, org);

					org.setName(orgTo.getOrgName());

					if (!orgTo.getBusinessPartners().isEmpty()) {

						List<OrgBusinessPartners> businessPartners = new ArrayList<OrgBusinessPartners>();

						for (BusinessPartnerTo bpTo : orgTo.getBusinessPartners()) {

							OrgBusinessPartners bp = new OrgBusinessPartners();

							BeanUtils.copyProperties(bpTo, bp);

							bp.setBpLegalName(bpTo.getName());
							bp.setCity(bpTo.getLocation());
							bp.setGstin(bpTo.getBpGstinNumber());
							bp.setPlantCode(bpTo.getErpRefId());
							bp.setOpBalStatus(bpTo.getOpBalStatus() == null ? "U" : bpTo.getOpBalStatus());

							businessPartners.add(bp);
						}

						org.setBusinessPartners(businessPartners);
					}

					orgs.add(org);
				}

				listOfOrganization.setOrgs(orgs);

				apiResp.setStatus(0);
				apiResp.setData(listOfOrganization);

			} else {

				apiResp.setMessage(prop.getProperty("ORG_001"));
				apiResp.setErrorcode("ORG_001");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting business partners by user: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Lock or Unlock user account
	 * 
	 * @param user_id
	 * @param action
	 * @return
	 */
	@Override
	public GenericApiResponse lockOrUnlockUser(Integer user_id, String action) {

		String METHOD_NAME = "lockOrUnlockUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			boolean isLockedOrUnlocked = userDao.lockOrUnlockUser(user_id, action);

			if (isLockedOrUnlocked) {

				String message = null;

				apiResp.setStatus(0);

				if ("LOCK".equals(action)) {

					message = MessageFormat.format(prop.getProperty("USR_016"), "locked");
				}

				if ("UNLOCK".equals(action)) {

					message = MessageFormat.format(prop.getProperty("USR_016"), "unlocked");
				}

				apiResp.setMessage(message);

			} else {

				apiResp.setMessage(prop.getProperty("GEN_003"));
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while locking or unlocking user account: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	/**
	 * Get online users
	 * 
	 * @return
	 */
	@Override
	public GenericApiResponse getOnlineUsers() {

		String METHOD_NAME = "getOnlineUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			List<UserTo> users = userDao.getOnlineUsers();

			if (!users.isEmpty()) {

				List<OnlineUserDetails> onlineUsers = new ArrayList<OnlineUserDetails>();

				ListOfOnlineUsers onlineUsersList = new ListOfOnlineUsers();

				for (UserTo user : users) {

					OnlineUserDetails onlineUserDetails = new OnlineUserDetails();

					/*
					 * String[] logonTime=user.getLogonTs().split(" "); String[] date=
					 * logonTime[0].split("-"); String date2 =date[2]+"-"+date[1]+"-"+date[0];
					 * user.setLogonTs(date2+" "+logonTime[1]);
					 */

					BeanUtils.copyProperties(user, onlineUserDetails);

					onlineUsers.add(onlineUserDetails);
				}

				onlineUsersList.setOnlineUsers(onlineUsers);

				apiResp.setStatus(0);
				apiResp.setData(onlineUsersList);

			} else {

				apiResp.setMessage(prop.getProperty("USR_017"));
				apiResp.setErrorcode("USR_017");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting online users: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	@Override
	public GenericApiResponse getUserLoginHistory(Integer loginId, String frmDate, String toDate) {

		String METHOD_NAME = "getUserLoginHistory";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			List<UserTo> users = userDao.getUserLoginHistory(loginId, frmDate, toDate);

			if (!users.isEmpty()) {

				List<OnlineUserDetails> onlineUsers = new ArrayList<OnlineUserDetails>();

				ListOfOnlineUsers onlineUsersList = new ListOfOnlineUsers();

				for (UserTo user : users) {

					OnlineUserDetails onlineUserDetails = new OnlineUserDetails();

					BeanUtils.copyProperties(user, onlineUserDetails);

					onlineUsers.add(onlineUserDetails);
				}

				onlineUsersList.setOnlineUsers(onlineUsers);

				apiResp.setStatus(0);
				apiResp.setData(onlineUsersList);

			} else {

				apiResp.setMessage(prop.getProperty("USR_017"));
				apiResp.setErrorcode("USR_017");
			}

		} catch (Exception e) {

			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");

			logger.error("Error while getting online users: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}
	
	public GenericApiResponse getApprovers() {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		Map<String, Object> params = new HashMap<>();
		params.put("stype", com.portal.constants.GeneralConstants.SettingType.APP_SETTING.getValue());
		params.put("grps", Arrays.asList(GeneralConstants.MASTERDATA));
		Map<String, GdSettingConfigsTo> configsTos = globalDataDao.getGdConfigDetailsMap(params);
		if (!configsTos.containsKey(GeneralConstants.MASTERDATA_APR_ROLES)
				|| configsTos.get(GeneralConstants.MASTERDATA_APR_ROLES).getSettingDefaultValue() == null
				|| configsTos.get(GeneralConstants.MASTERDATA_APR_ROLES).getSettingDefaultValue().isEmpty()) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("No Data Found");
			return genericApiResponse;
		}
		String aprRoles = configsTos.get(GeneralConstants.MASTERDATA_APR_ROLES).getSettingDefaultValue();
		
		List<String> roles = Stream.of(aprRoles.split(",")).map(String::trim)
				.collect(Collectors.toList());
		List<UmUsers> umUsers = umOrgUsersRepo.getOrgUsersByUserRoles(roles, userContext.getLoggedUser().getRegion());
		List<ApprovalUserTo> approversList = new ArrayList<>();
		for (UmUsers obj : umUsers) {
			ApprovalUserTo userTo = new ApprovalUserTo();
			userTo.setUserId(obj.getUserId());
			userTo.setLogonId(obj.getLogonId());
			userTo.setFirstName(obj.getFirstName());
			userTo.setMiddleName(obj.getMiddleName());
			userTo.setLastName(obj.getLastName());
			userTo.setEmail(obj.getEmail());
			approversList.add(userTo);
		}
		if (approversList.isEmpty()) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("No Data Found");
		} else {
			genericApiResponse.setStatus(0);
			genericApiResponse.setData(approversList);
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse selfRegister(UserTo userTo) {
		String METHOD_NAME = "selfRegister";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();
		if(userTo.getSspAction() != null) {
			if(!userTo.getSspAction().equalsIgnoreCase("UPDATE")) {
				if (umUsersRepository.existsByLogonId(userTo.getLogonId())) {
					apiResp.setStatus(1);
					apiResp.setMessage(prop.getProperty("USR_018"));
					return apiResp;

				}
				if (umUsersRepository.existsByMobile(userTo.getMobile())) {
					apiResp.setStatus(1);
					apiResp.setMessage(prop.getProperty("USR_019"));
					return apiResp;

				}
			}
		}else {
			if (umUsersRepository.existsByLogonId(userTo.getLogonId())) {
				apiResp.setStatus(1);
				apiResp.setMessage(prop.getProperty("USR_018"));
				return apiResp;

			}
			if (umUsersRepository.existsByMobile(userTo.getMobile())) {
				apiResp.setStatus(1);
				apiResp.setMessage(prop.getProperty("USR_019"));
				return apiResp;

			}
		}
		
		
		
		/*
		 * Check if mobile number exists in database
		 */

		
		
		boolean status = false;
		
		if ("SSP".equalsIgnoreCase(userTo.getAction())) {
			if(!userTo.getSspAction().equalsIgnoreCase("UPDATE")) {
				userTo.setNewPwd(GeneralConstants.DEFAULT_PASSWORD);
			}
			userTo.setRoleShortId(GeneralConstants.ROLE_SSP_USER);
			userTo.setCurrentOrgId(GeneralConstants.ORGID);
			userTo.setBpId(GeneralConstants.BPID);
			userTo.setUserTypeId(GeneralConstants.GD_SSP_USER_TYPE_ID);
			status = userDao.sspSelfRegister(userTo);
		} else {
			userTo.setRoleShortId(GeneralConstants.ROLE_EXTERNAL_USER);
			userTo.setUserTypeId(GeneralConstants.GD_USER_TYPE_ID);
			userTo.setCurrentOrgId(GeneralConstants.ORGID);
			userTo.setBpId(GeneralConstants.BPID);
			userTo.setNewPwd(GeneralConstants.DEFAULT_PASSWORD);

			status = userDao.selfRegister(userTo);
		}

			/*
			 * Mail
			 *
			 */

			if (status) {
				Map<String, Object> params = new HashMap<>();
				params.put("stype", SettingType.APP_SETTING.getValue());
				params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));

				SettingTo settingTo = settingDao.getSMTPSettingValues(params);
				Map<String, String> emailConfigs = settingTo.getSettings();

				EmailsTo email = new EmailsTo();
				email.setBpId(GeneralConstants.BPID);
				email.setOrgId(GeneralConstants.ORGID);
				
//				if ("SSP".equalsIgnoreCase(userTo.getAction())) {
//					email.setTemplateName(GeneralConstants.SSP_REG);
//				}else {
//				email.setTemplateName(GeneralConstants.USER_ADDITION);
//				}

				if ("ADD".equalsIgnoreCase(userTo.getSspAction())) {
//					email.setTemplateName(GeneralConstants.USER_ADDITION_MAIL_TO_ADMIN);
					email.setTemplateName(GeneralConstants.SSP_USER_ADDITION_MAIL_TO_ADMIN);
					email.setFrom(emailConfigs.get("SSP_EMAIL_FROM"));
					if(userTo.getEmail() != null) {
						String ccMail[] = {userTo.getEmail()};
						email.setCc(ccMail);
					}
				}else if ("SSP".equalsIgnoreCase(userTo.getAction())) {
					email.setTemplateName(GeneralConstants.SSP_REG);
					email.setFrom(emailConfigs.get("SSP_EMAIL_FROM"));
					List<UmUsers> userLoginId = umUsersRepository.getUserLoginId(userTo.getLogonId(), false);
					if(userLoginId != null && !userLoginId.isEmpty()) {
						UmUsers umUsers = userLoginId.get(0);
						UmUsers approverEmails = umUsersRepository.getApproverEmails(umUsers.getCreatedBy());
						if(approverEmails != null) {
							String[] ccMails = {approverEmails.getEmail()};
							email.setCc(ccMails);
						}					
					}
				}
				else {
				email.setTemplateName(GeneralConstants.USER_ADDITION);
				email.setFrom(emailConfigs.get("EMAIL_FROM"));
				}
//				email.setFrom(emailConfigs.get("EMAIL_FROM"));
				if("ADD".equalsIgnoreCase(userTo.getSspAction())) {
					email.setTo(userTo.getLogonEmail());
				}else {
					email.setTo(userTo.getEmail());
				}				
				email.setOrgId(userTo.getCurrentOrgId());

				Map<String, Object> templateProps = new HashMap<String, Object>();
				templateProps.put("logonId", userTo.getLogonId());
				templateProps.put("action_url",emailConfigs.get("WEB_URL"));
				templateProps.put("app_url", emailConfigs.get("WEB_URL"));
				templateProps.put("logo_url", emailConfigs.get("LOGO_URL"));
				templateProps.put("userId", userTo.getLogonId());//new userName
				templateProps.put("userName", userTo.getLogonId());
				templateProps.put("password", GeneralConstants.DEFAULT_PASSWORD);
				if (userTo.isResetPwd()) {
					templateProps.put("action_url",
							emailConfigs.get("WEB_URL") + "resetPassword?orgky=" + userTo.getResetKey());
				}
				email.setTemplateProps(templateProps);

				Map<String, String> msgProps = new HashMap<String, String>();
				msgProps.put(ActivityConstants.user_name_p1, userTo.getFirstName());

				if("SSP".equalsIgnoreCase(userTo.getAction())) {
					sendService.sendCommunicationToSSPMail(email, emailConfigs);
				}else {
					sendService.sendCommunicationMail(email, emailConfigs);
				}

				apiResp.setStatus(0);
				if("ADD".equalsIgnoreCase(userTo.getSspAction())) {
					apiResp.setMessage("Vendor added successfully with vendor code "+userTo.getLogonId());
				}else {
					apiResp.setMessage(prop.getProperty("USR_001"));
				}				

			} else {

				apiResp.setStatus(1);
				apiResp.setMessage(prop.getProperty("GEN_002"));
				apiResp.setErrorcode("GEN_002");
			}

		return apiResp;
	}


	@Override
	public GenericApiResponse generateAndValidateCaptcha( ) {
		String captchaStr = "";
		GenericApiResponse gApiR = new GenericApiResponse();
		if (request.getHeader(GeneralConstants.ORIGINAL_CAPTCHA) == null
				&& (request.getHeader(GeneralConstants.INPUT_CAPTCHA) == null)) {
			try {

				captchaStr = generateCaptchaTextMethod2(6);
				System.out.println("captcha::"+captchaStr);
				CaptchaValidation captchaValidation = new CaptchaValidation();
				captchaValidation
						.setCaptchaString(DataCryptoUtil.encrypt(captchaStr, prop.getProperty("CAPTCHA_SECRET_KEY")));
				int width = 100;
				int height = 40;

				Color bg = new Color(120, 212, 241);
				Color fg = new Color(31, 33, 33);

				// Font font = new Font("Comic Sans MS", Font.ITALIC,20);
				BufferedImage cpimg = new BufferedImage(width, height, BufferedImage.OPAQUE);
				Graphics g = cpimg.createGraphics();
				g.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
				g.setColor(bg);
				g.fillRect(0, 0, width, height);
				g.setColor(fg);
				g.drawString(captchaStr, 10, 25);

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ImageIO.write(cpimg, GeneralConstants.JPEG, outputStream);

				byte[] image = outputStream.toByteArray();
				captchaValidation.setImage(image);
				captchaValidation.setIsCaptchaEnabled(true);

				outputStream.close();
				if (ImageIO.write(cpimg, GeneralConstants.JPEG, outputStream)) {
					gApiR.setStatus(0);
					gApiR.setData(captchaValidation);
					gApiR.setMessage(MessageFormat.format(prop.getProperty("SEC_MSG_0008"), "Captha"));

				} else {
					gApiR.setStatus(1);
					gApiR.setMessage(MessageFormat.format(prop.getProperty("SEC_MSG_0011"), "Enter", "captcha"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {

				String ActualCaptcha = DataCryptoUtil.decrypt(request.getHeader(GeneralConstants.ORIGINAL_CAPTCHA),
						prop.getProperty("CAPTCHA_SECRET_KEY"));
				String inputCaptcha = request.getHeader(GeneralConstants.INPUT_CAPTCHA);
				int compareCaptcha = ActualCaptcha.compareTo(inputCaptcha);
				if (compareCaptcha == 0) {
					gApiR.setStatus(0);
					gApiR.setMessage(MessageFormat.format(prop.getProperty("SEC_MSG_0008"), "Captcha"));

				} else {
					gApiR.setStatus(1);
					gApiR.setMessage(prop.getProperty("SEC_MSG_0011"));

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return gApiR;
	}
	
	public String generateCaptchaTextMethod2(int captchaLength) {

//		String saltChars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
		String saltChars = "0123456789";
		StringBuffer captchaStrBuffer = new StringBuffer();
		java.util.Random rnd = new java.util.Random();

		// build a random captchaLength chars salt
		while (captchaStrBuffer.length() < captchaLength) {
			int index = (int) (rnd.nextFloat() * saltChars.length());
			captchaStrBuffer.append(saltChars.substring(index, index + 1));
		}
		return captchaStrBuffer.toString();
	}

	@Override
	public GenericApiResponse getUserLoggedHistory() {
		String METHOD_NAME = "getUserLoggedHistory";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			List<UserLoginHistory> userLoginHistory = loginHistoryRepo.getUserLoginHistory();
			if(!userLoginHistory.isEmpty()&&userLoginHistory!=null) {
				List<RmsUserLoginHistoryDetails> details=new ArrayList<RmsUserLoginHistoryDetails>();
				
				for(UserLoginHistory userHistory : userLoginHistory) {
					RmsUserLoginHistoryDetails rmsUserLoginHistoryDetails = new RmsUserLoginHistoryDetails();
					rmsUserLoginHistoryDetails.setUserId(userHistory.getUserId());
					rmsUserLoginHistoryDetails.setLogonId(userHistory.getLogonId());
					rmsUserLoginHistoryDetails.setUserTypeId(userHistory.getUserTypeId());
					rmsUserLoginHistoryDetails.setAction(userHistory.getAction());
					Date entryTime = userHistory.getEntryTime();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					String formattedDateTime = dateFormat.format(entryTime);
					rmsUserLoginHistoryDetails.setEntryDate(formattedDateTime);
					details.add(rmsUserLoginHistoryDetails);
				}
				apiResp.setStatus(0);
				apiResp.setData(details);
				apiResp.setMessage("User Logged Details fetched sucessfully");			
				
			}
			else {
				apiResp.setStatus(1);
                apiResp.setMessage("No users logged details available");
			}
			
		} catch (Exception e) {
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
			logger.error("Error while getting online users: " + ExceptionUtils.getStackTrace(e));
		}
		
		return apiResp;
	}

	@Override
	public GenericApiResponse createOrUpdateCustomer(@NotNull List<UserTo> payload) {
		String METHOD_NAME = "createOrUpdateCustomer";
		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			List<String> clientCodes = new ArrayList<>();
			if (payload != null && !payload.isEmpty()) {
				for (UserTo userTo : payload) {
					clientCodes.add(userTo.getClientCode());
				}

				if (clientCodes != null && !clientCodes.isEmpty()) {
					List<UmCustomers> umCustomers = umCustomersRepo.getCustomerDataOnClientCode(clientCodes);
					if(umCustomers != null && !umCustomers.isEmpty()) {
						Map<String,UmCustomers> umCustomersMap = new HashMap<>();
						for(UmCustomers um : umCustomers) {
							umCustomersMap.put(um.getClientCode(), um);
						}
						for (UserTo userTo : payload) {
//							List<UmCustomers> umCus = umCustomers.stream()
//									.filter(f -> f.getClientCode().equals(userTo.getClientCode()))
//									.collect(Collectors.toList());
							UmCustomers umCust = umCustomersMap.get(userTo.getClientCode());
							if (umCust != null) {
								umCust.setAddress1(userTo.getAddress1());
								umCust.setOfficeLocation(userTo.getOfficeLocation());
								umCust.setPinCode(userTo.getPinCode());
								umCust.setCity(userTo.getCity());
								umCust.setState(userTo.getState());
								umCust.setPanNumber(userTo.getPanNumber());
								umCust.setGstNo(userTo.getGstNo());
								umCust.setHouseNo(userTo.getHouseNo());
								umCust.setEmailId(userTo.getEmail());
								umCust.setAadharNumber(userTo.getAadharNumber());
								String clientCode = userTo.getClientCode();
								if (clientCode != null && clientCode.matches("^0+.*")) {
								    clientCode = clientCode.replaceAll("^0+(?!$)", "");
								}
								umCust.setClientCode(clientCode);
								umCust.setCustomerName(userTo.getCustomerName());
								umCust.setMobileNo(userTo.getMobile());
								umCust.setMarkAsDelete(false);
								umCust.setChangedTs(new Date());
								umCust.setEmpId(userTo.getEmpId());
								umCust.setIsMaster(true);
								
								//empId,isMasterFlag
								
								baseDao.saveOrUpdate(umCust);
							} else {
								UmCustomers umCustomer = new UmCustomers();
								umCustomer.setCustomerId(UUID.randomUUID().toString());
								umCustomer.setAddress1(userTo.getAddress1());
								umCustomer.setOfficeLocation(userTo.getOfficeLocation());
								umCustomer.setPinCode(userTo.getPinCode());
								umCustomer.setCity(userTo.getCity());
								umCustomer.setState(userTo.getState());
								umCustomer.setPanNumber(userTo.getPanNumber());
								umCustomer.setGstNo(userTo.getGstNo());
								umCustomer.setHouseNo(userTo.getHouseNo());
								umCustomer.setEmailId(userTo.getEmail());
								umCustomer.setAadharNumber(userTo.getAadharNumber());
								String clientCode = userTo.getClientCode();
								if (clientCode != null && clientCode.matches("^0+.*")) {
								    clientCode = clientCode.replaceAll("^0+(?!$)", "");
								}
								umCustomer.setClientCode(clientCode);
								umCustomer.setCustomerName(userTo.getCustomerName());
								umCustomer.setMobileNo(userTo.getMobile());
								umCustomer.setMarkAsDelete(false);
								umCustomer.setCreatedTs(new Date());
								umCustomer.setEmpId(userTo.getEmpId());
								umCustomer.setIsMaster(true);

								baseDao.save(umCustomer);
							}
						}
						
					}else {
						for(UserTo userTo : payload) {
							UmCustomers umCustomer = new UmCustomers();
							umCustomer.setCustomerId(UUID.randomUUID().toString());
							umCustomer.setAddress1(userTo.getAddress1());
							umCustomer.setOfficeLocation(userTo.getOfficeLocation());
							umCustomer.setPinCode(userTo.getPinCode());
							umCustomer.setCity(userTo.getCity());
							umCustomer.setState(userTo.getState());
							umCustomer.setPanNumber(userTo.getPanNumber());
							umCustomer.setGstNo(userTo.getGstNo());
							umCustomer.setHouseNo(userTo.getHouseNo());
							umCustomer.setEmailId(userTo.getEmail());
							umCustomer.setAadharNumber(userTo.getAadharNumber());
							String clientCode = userTo.getClientCode();
							if (clientCode != null && clientCode.matches("^0+.*")) {
							    clientCode = clientCode.replaceAll("^0+(?!$)", "");
							}
							umCustomer.setClientCode(clientCode);
							umCustomer.setCustomerName(userTo.getCustomerName());
							umCustomer.setMobileNo(userTo.getMobile());
							umCustomer.setMarkAsDelete(false);
							umCustomer.setCreatedTs(new Date());
							umCustomer.setEmpId(userTo.getEmpId());
							umCustomer.setIsMaster(true);
							
							
							baseDao.save(umCustomer);
						}
					}
				}
			}else {
				apiResp.setMessage(prop.getProperty("GEN_002"));
				apiResp.setErrorcode("GEN_002");
			}
			
		}catch(Exception e) {
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
			logger.error("Error while getting online users: " + ExceptionUtils.getStackTrace(e));;
		}
		

		return apiResp;
	}

	@SuppressWarnings("unused")
	@Override
	public GenericApiResponse createSSPCustomer(@NotNull UserTo payload) {
		GenericApiResponse apiResp = new GenericApiResponse();
		if (umUsersRepository.existsByMobile(payload.getMobile())) {
			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("USR_019"));
			return apiResp;
		}
		if(payload != null) {
			UmUsers umUsers = new UmUsers();
			umUsers.setState(payload.getState());
			umUsers.setBookingOffice(payload.getBookingOffice());
			umUsers.setFirstName(payload.getFirstName());
			umUsers.setMobile(payload.getMobile());
			umUsers.setEmail(payload.getEmail() != null ? payload.getEmail() : null);
			String generateSeries = this.generateSeries("LOGON_ID", payload.getBookingOffice());
			umUsers.setLogonId(generateSeries);
			umUsers.setIsAdmin(false);
			umUsers.setDeactivated(false);
			umUsers.setMarkAsDelete(false);
			umUsers.setCreatedBy(userContext.getLoggedUser().getUserId());
			umUsers.setCreatedTs(new Date());
			GdUserTypes gdUserTypes = new GdUserTypes();
			gdUserTypes.setUserTypeId(GeneralConstants.GD_SSP_USER_TYPE_ID);
			umUsers.setPassword(encoder.encode(GeneralConstants.DEFAULT_PASSWORD));
			umUsers.setGdUserTypes(gdUserTypes);
			umUsersRepository.save(umUsers);
			apiResp.setStatus(0);
			apiResp.setMessage("SSP Vendor Created Successfully");
		}else {
			apiResp.setStatus(1);
			apiResp.setMessage("Invalid Data");
		}
		return apiResp;
	}
	
	public String generateSeries(String type,String bookingUnit) {
		StringBuilder sb = new StringBuilder();
		try {
			List<GdNumberSeries> gdNumberSeriesList = gdNumberSeriesRepo.getNumberSeriesByType(type);
			Integer bookingUnit1 = Integer.parseInt(bookingUnit);
			BookingUnits bookingUnitById = bookingUnitsRepo.getBookingUnitById(bookingUnit1);
			
			boolean currentYearSeriesFlag = false;
			String currentYear = CommonUtils.dateFormatter(new Date(), "Y");
			String currentYearSeriesFormat = CommonUtils.dateFormatter(new Date(), "yy");
			if (!gdNumberSeriesList.isEmpty()) {
//				currentYearSeriesFormat = CommonUtils.dateFormatter(new Date(),
//						gdNumberSeriesList.get(0).getYearFormat());
				GdNumberSeries gdCurrentYearNumberSeries = null;
				GdNumberSeries gdNumberSeries = new GdNumberSeries();
				for (GdNumberSeries gns : gdNumberSeriesList) {
					if (gns.getYear() != null && gns.getYear().equalsIgnoreCase(currentYear)) {
						currentYearSeriesFlag = true;
						gdCurrentYearNumberSeries = gns;
					} else {
						gdNumberSeries = gns;
					}
				}
				if (gdCurrentYearNumberSeries == null) {
					gdCurrentYearNumberSeries = gdNumberSeries;
				}
				sb.append(gdCurrentYearNumberSeries.getPrefix());
				if(bookingUnitById != null) {
					String location = bookingUnitById.getBookingLocation();
					sb.append(location.substring(0, 3).toUpperCase());
				}
//				sb.append(currentYearSeriesFormat);
				BigDecimal upComingSeries = gdCurrentYearNumberSeries.getCurrentSeries().add(new BigDecimal(1));
				for (int i = 0; i < gdCurrentYearNumberSeries.getSeriesLength() - (upComingSeries + "").length(); i++) {
					sb.append("0");
				}
				sb.append(upComingSeries);
				System.out.println(sb.toString());
				if (!currentYearSeriesFlag) {
//					gdCurrentYearNumberSeries.setId(UUID.randomUUID().toString());
					gdCurrentYearNumberSeries.setYear(currentYear);
				}
				gdCurrentYearNumberSeries.setCurrentSeries(upComingSeries);
				gdNumberSeriesRepo.save(gdCurrentYearNumberSeries);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public GenericApiResponse getUsersByBookingCode(@NotNull UserTo payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		List<UserTo> userByBookingCode = userDao.getUserByBookingCode(payload);
		if(userByBookingCode != null && !userByBookingCode.isEmpty()) {
			apiResponse.setStatus(0);
			apiResponse.setData(userByBookingCode);
			apiResponse.setMessage("Users with bookingcode fetched successfully");
		}else {
			apiResponse.setStatus(1);
			apiResponse.setMessage("No users found with bookingcode");
		}
		return apiResponse;
	}

	@Override
	public GenericApiResponse getRmsApprovers() {
		GenericApiResponse apiResponse = new GenericApiResponse();
		List<UserTo> allRmsApproverUsers = userDao.getRmsApprovers();
		if(allRmsApproverUsers != null && !allRmsApproverUsers.isEmpty()) {
			apiResponse.setStatus(0);
			apiResponse.setData(allRmsApproverUsers);
			apiResponse.setMessage("Rms Approver Users fetched successfully");
		}else {
			apiResponse.setStatus(1);
			apiResponse.setMessage("No users found ");
		}
		return apiResponse;
	}

	@Override
	public GenericApiResponse getExecutiveCreatedListOfUsers(LoggedUser loggedUser) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		Map<Integer, UserTo> executiveCreatedUsers = userDao.getExecutiveCreatedUsers(loggedUser);
		if(executiveCreatedUsers != null && !executiveCreatedUsers.isEmpty()) {
			apiResponse.setStatus(0);
			apiResponse.setData(executiveCreatedUsers.values());
			apiResponse.setMessage("SSP Vendors Fetched Successfully");
		}else {
			apiResponse.setStatus(1);
			apiResponse.setMessage("No Vendors Created");
		}
		return apiResponse;
	}


}
