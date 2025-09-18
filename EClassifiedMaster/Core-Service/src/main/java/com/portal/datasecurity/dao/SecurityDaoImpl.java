package com.portal.datasecurity.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.portal.constants.GeneralConstants;
import com.portal.constants.GeneralConstants.SettingType;
import com.portal.org.entities.OmOrgBusinessPartners;
import com.portal.repository.OmOrgBusinessPartnersRepository;
import com.portal.repository.UmOrgRolesPermissionsRepo;
import com.portal.repository.UmOrgUsersRepository;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.user.entities.UmOrgRolesPermissions;
import com.portal.user.entities.UmOrgUsers;

/**
 * Security DAO implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service("securityDao")
@PropertySource(value = { "classpath:/com/portal/queries/user_db.properties" })
public class SecurityDaoImpl implements SecurityDao {

	private static final Logger logger = LogManager.getLogger(SecurityDaoImpl.class);

	@Autowired
	private OmOrgBusinessPartnersRepository orgBpRepository;
	
	@Autowired
	private UmOrgUsersRepository umOrgUsersRepo;
	
	@Autowired
	private UmOrgRolesPermissionsRepo rolesPermissionsRepo;
	
	@Autowired
	private OmOrgBusinessPartnersRepository businessPartnerRepo;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private SettingDao settingDao;

	/**
	 * Get access object permission
	 * 
	 * @param orgId
	 * @param userId
	 * @param accessObjId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean getAccessObjPermission(String orgId, String bpId, Integer userId, String accessObjId) {

		String METHOD_NAME = "getAccessObjPermission";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isPermission = false;

		try {

			if (orgId == null) {

				Optional<OmOrgBusinessPartners> omBusinessPlaces = orgBpRepository
						.findById(bpId);
				orgId = omBusinessPlaces.get().getOmOrganizations().getOrgId();
			}

			/*List<UmOrgUsers> umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
					prop.getProperty("GET_USER_ROLES"), new Object[] { userId, orgId, false });*/
			List<UmOrgUsers> umOrgUsers = umOrgUsersRepo.getOrgUsersByUserOrgId(userId, orgId, false);

			for (UmOrgUsers uOrgUser : umOrgUsers) {

				/*List<UmOrgRolesPermissions> permissions = (List<UmOrgRolesPermissions>) baseDao
						.findByHQLQueryWithIndexedParams(prop.getProperty("GET_USER_PERMISIONS"),
								new Object[] { uOrgUser.getUmOrgRoles().getRoleId(), accessObjId, false });*/
				List<UmOrgRolesPermissions> permissions = rolesPermissionsRepo.getOrgRolePermissions(uOrgUser.getUmOrgRoles().getRoleId(), accessObjId, false);

				for (UmOrgRolesPermissions permission : permissions) {

					isPermission = permission.isPermissionLevel();
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting access object permissions: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isPermission;
	}

	/**
	 * Get organization permission
	 * 
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean getOrgPermission(String orgId, Integer loggedUser, String bpId) {

		String METHOD_NAME = "getOrgPermission";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isPermission = false;

		try {

			if (orgId == null) {

				Optional<OmOrgBusinessPartners> businessPartners = businessPartnerRepo
						.findById(bpId);

				orgId = businessPartners.get().getOmOrganizations().getOrgId();
			}

			/*List<UmOrgUsers> umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
					prop.getProperty("GET_USER_ORG"), new Object[] { loggedUser, orgId, false });*/

			List<UmOrgUsers> umOrgUsers = umOrgUsersRepo.getOrgUsersByUserOrgId(loggedUser, orgId, false);
			if (!umOrgUsers.isEmpty()) {
				isPermission = true;
			}

		} catch (Exception e) {
			logger.error("Error while getting access organization permissions: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isPermission;
	}

	/**
	 * Get business partner permission
	 * 
	 * @param bpId
	 * @param loggedUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean getBpPermission(String bpId, Integer loggedUser) {

		String METHOD_NAME = "getOrgPermission";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isPermission = false;

		try {

			/*List<UmOrgUsers> umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
					prop.getProperty("GET_USER_BP"), new Object[] { loggedUser, bpId, false });*/
			List<UmOrgUsers> umOrgUsers = umOrgUsersRepo.getOrgUsersBps(loggedUser, bpId, false);
			if (!umOrgUsers.isEmpty()) {
				isPermission = true;
			}

		} catch (Exception e) {
			logger.error("Error while getting access organization permissions: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isPermission;
	}

	/**
	 * Get IP addresses
	 * 
	 * @param orgId
	 * @param bpId
	 * @return
	 */
	@Override
	public String getIpAddresses(String orgId, String bpId) {

		String METHOD_NAME = "getIpAddresses";

		logger.info("Entered into the method: " + METHOD_NAME);

		String ipAddresses = null;

		try {

			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.ORG_BP_SETTING.getValue());
			params.put("refObjId", Arrays.asList(bpId, orgId));
			params.put("grps", Arrays.asList(GeneralConstants.IPW_SETTINGS));

			Map<String, String> ipConfigs = ((SettingTo) settingDao.getSettingValues(params)).getSettings();

			ipAddresses = ipConfigs.get(GeneralConstants.IP_DESKTOP_APPS);

		} catch (Exception e) {
			logger.error("Error while getting IP addresses: " + ExceptionUtils.getStackTrace(e));
		}

		return ipAddresses;
	}
}
