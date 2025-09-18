
package com.portal.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.gd.entities.GdSettingsDefinitions;
import com.portal.gd.entities.GdUserTypes;
import com.portal.security.model.GeneralConstants;
import com.portal.security.model.SettingTo;
import com.portal.security.model.SettingTo.SettingType;
import com.portal.security.model.UserTo;
import com.portal.security.repo.SettingsRepository;
import com.portal.security.repo.UserRepository;
import com.portal.security.util.CommonUtils;
import com.portal.setting.entities.OmApplSettings;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.entities.UmUsers;

/**
 * Auth User DAO implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service
public class AuthUserDaoImpl implements AuthUserDao {

	private static final Logger logger = LogManager.getLogger(AuthUserDaoImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SettingsRepository settingsRepository;

	/**
	 * Find by logon id
	 * 
	 * @param Class,String
	 * 
	 * @return the BaseEntity
	 */
	public UmUsers findByLogonIdUmUsers(String logonId) {

		String METHOD_NAME = "findByLogonId";

		logger.info("Entered into the method: " + METHOD_NAME);

		try {
			return userRepository.findByLogonId(logonId, Arrays.asList(CommonUtils.getUserTypeId()));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Find by logon id
	 * 
	 * @param Class,String
	 * 
	 * @return the BaseEntity
	 */
	public UserTo findByLogonId(String logonId) {

		String METHOD_NAME = "findByLogonId";

		logger.info("Entered into the method: " + METHOD_NAME);

		UserTo user = null;

		try {

			UmUsers umUsers = userRepository.findByLogonId(logonId, Arrays.asList(CommonUtils.getUserTypeId()));

			if (umUsers != null) {

				user = new UserTo();
				BeanUtils.copyProperties(umUsers, user);

				GdUserTypes userTypes = umUsers.getGdUserTypes();

				user.setUserType(userTypes.getTypeShortId());
				user.setUserTypeId(userTypes.getUserTypeId());
				user.setUserLocked(umUsers.getUserLocked());
				user.setPwd(umUsers.getPassword());
				user.setCreated_ts(umUsers.getCreatedTs());

				Set<UmOrgUsers> orgUser = umUsers.getUmOrgUsers();
				Iterator<UmOrgUsers> userIterator = orgUser.iterator();

				List<String> bpIds = new ArrayList<String>();

				while (userIterator.hasNext()) {

					UmOrgUsers umOrgUser = (UmOrgUsers) userIterator.next();

					if (!umOrgUser.isMarkAsDelete()) {

						bpIds.add(umOrgUser.getOmOrgBusinessPartners().getOrgBpId());
						user.setRole(umOrgUser.getUmOrgRoles().getRoleType());
						user.setRoleId(umOrgUser.getUmOrgRoles().getRoleId().toString());
						user.setRoleDesc(umOrgUser.getUmOrgRoles().getRoleDesc());
						user.setRoleType(umOrgUser.getUmOrgRoles().getRoleShortId());
					}
				}

				user.setBpIds(bpIds);
			}

		} catch (Exception e) {
			logger.error("Error while finding user detais by Logonid: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return user;
	}

	/**
	 * Update logon retries
	 * 
	 * @param logonId
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateLogonRetries(String logonId, String action) {

		String METHOD_NAME = "updateLogonRetries";

		logger.info("Entered into the method: " + METHOD_NAME);

		try {

			UmUsers umUsers = userRepository.findByLogonId(logonId, Arrays.asList(CommonUtils.getUserTypeId()));

			if (umUsers != null) {

				int max_retries = 3;

				Map<String, String> userConfigs = this.getUserApplicationSettings();

				if (userConfigs != null && userConfigs.get(GeneralConstants.LOGON_RETRIES) != null)
					max_retries = Integer.parseInt(userConfigs.get(GeneralConstants.LOGON_RETRIES));

				boolean isRetriesUpdate = false;

				int retriesCnt = umUsers.getLogonRetries();

				int userRetries = 0;

				if ("FAILED".equals(action)) {

					userRetries = retriesCnt + 1;

					isRetriesUpdate = userRetries <= max_retries ? true : false;

				} else if ("RESET".equals(action)) {

					isRetriesUpdate = retriesCnt != 0 ? true : false;
				}

				if (isRetriesUpdate) {

					if (max_retries == userRetries) {

						umUsers.setUserLocked(true);
						umUsers.setUserLockedTs(new Date());
					}

					umUsers.setLogonRetries(userRetries);
					umUsers.setChangedBy(umUsers.getUserId());
					umUsers.setChangedTs(new Date());

					userRepository.save(umUsers);
				}
			}

		} catch (Exception e) {
			logger.error("Error while updating logon retries: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);
	}

	/**
	 * Get user settings
	 * 
	 * @return
	 */
	private Map<String, String> getUserApplicationSettings() {

		Map<String, Object> params = new HashMap<>();
		params.put("stype", SettingType.APP_SETTING.getValue());
		params.put("grps", Arrays.asList(GeneralConstants.USER_SETTINGS));

		Map<String, String> userConfigs = ((SettingTo) getSettingValues(params)).getSettings();

		return userConfigs;
	}

	/**
	 * Get setting values
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SettingTo getSettingValues(Map<String, Object> params) {

		String METHOD_NAME = "getSettingValues";

		logger.info("Entered into the method: " + METHOD_NAME);

		SettingTo settingTo = new SettingTo();

		try {

			int sType = Integer.parseInt(params.get("stype").toString());

			List<Object[]> settings = null;

			settings = (List<Object[]>) settingsRepository.getAppSettings((Integer) params.get("stype"),
					(List) params.get("grps"));

			Map<String, String> map = new HashMap<String, String>();

			for (Object[] obj : settings) {

				GdSettingsDefinitions gsd = (GdSettingsDefinitions) obj[0];

				OmApplSettings oap = (OmApplSettings) obj[1];

				if (gsd != null && gsd.getSettingShortId() != null && oap != null && oap.getSettingValue() != null) {

					if (map.containsKey(gsd.getSettingShortId()) && SettingType.ORG_BP_SETTING.getValue() == sType) {

						if (oap.getRefObjType().equals("BP")) {
							map.put(gsd.getSettingShortId(), oap.getSettingValue());
						}

					} else {
						map.put(gsd.getSettingShortId(), oap.getSettingValue());
					}
				}
			}

			settingTo.setSettings(map);

		} catch (Exception e) {
			logger.error("Error while getting setting values: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return settingTo;
	}
}
