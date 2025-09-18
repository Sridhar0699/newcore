package com.portal.org.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.portal.basedao.IBaseDao;
import com.portal.constants.GeneralConstants;
import com.portal.doc.entity.Attachments;
import com.portal.gd.entities.BookingUnits;
import com.portal.gd.entities.GdAccessObjects;
import com.portal.gd.entities.GdUserTypes;
import com.portal.org.entities.OmOrgBusinessPartners;
import com.portal.org.entities.OmOrganizations;
import com.portal.org.entities.UmCategoryUsers;
import com.portal.org.to.AddRoleTo;
import com.portal.org.to.BusinessPartnerTo;
import com.portal.org.to.ChildOjectTo;
import com.portal.org.to.OrganizationTo;
import com.portal.org.to.ParentObjectTo;
import com.portal.org.to.RolesTo;
import com.portal.repository.AttachmentsRepo;
import com.portal.repository.BookingUnitsRepo;
import com.portal.repository.OmOrganizationsRepo;
import com.portal.repository.UmOrgUsersRepository;
import com.portal.repository.UmUsersRepository;
import com.portal.user.dao.UserDao;
import com.portal.user.entities.UmOrgRoles;
import com.portal.user.entities.UmOrgRolesPermissions;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.entities.UmUserPwdReset;
import com.portal.user.entities.UmUsers;
import com.portal.user.to.UserTo;

/**
 * Organization DAO implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service("orgDao")
@Transactional
@PropertySource(value = { "classpath:/com/portal/queries/org_db.properties" })
public class OrgDaoImpl implements OrgDao {

	private static final Logger logger = LogManager.getLogger(OrgDaoImpl.class);

	@Autowired(required = true)
	private IBaseDao baseDao;

	@Autowired(required = true)
	private Environment prop;
	
	@Autowired
	private OmOrganizationsRepo omOrganizationsRepo;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UmOrgUsersRepository umOrgUsersRepository;
	
	@Autowired
	private AttachmentsRepo attachmentsRepo;
	

	@Autowired
	private BookingUnitsRepo bookingUnitsRepo;

	@Autowired
	private UmUsersRepository umUsersRepository;
	/**
	 * Get organization details
	 * 
	 * @param orgId
	 * @return
	 */
	@Override
	public OrganizationTo getOrgDetails(String orgId) {

		String METHOD_NAME = "getOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		OrganizationTo organizationTo = null;

		try {

			OmOrganizations orgDetails = (OmOrganizations) baseDao.findByPK(OmOrganizations.class, orgId);

			if (orgDetails != null) {

				organizationTo = new OrganizationTo();

				BeanUtils.copyProperties(orgDetails, organizationTo);

				organizationTo.setUserId(orgDetails.getCreatedBy());
				//GdCountries countries = orgDetails.getGdCountries();
				organizationTo.setCountry("");
				organizationTo.setCountryId(orgDetails.getCountryId());
			}

		} catch (Exception e) {
			logger.error("Error while getting organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return organizationTo;
	}

	/**
	 * Add the new organization details
	 * 
	 * @param organizationTo
	 * @return
	 */
	@Override
	public boolean addOrgDetails(OrganizationTo organizationTo) {

		String METHOD_NAME = "addOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isAdded = false;

		try {

			OmOrganizations omOrganizations = new OmOrganizations();

			BeanUtils.copyProperties(organizationTo, omOrganizations);

			omOrganizations.setOrgId(UUID.randomUUID().toString());
			//omOrganizations.setGdCountries(countries); Removed Country 27-Jan-2021
			omOrganizations.setCreatedBy(organizationTo.getUserId());
			omOrganizations.setCreatedTs(new Date());
			omOrganizations.setMarkAsDelete(false);

			baseDao.save(omOrganizations);

			isAdded = true;

		} catch (Exception e) {
			logger.error("Error while adding organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isAdded;
	}

	/**
	 * Update the existed organization details
	 * 
	 * @param organizationTo
	 * @return
	 */
	@Override
	public boolean updateOrgDetails(OrganizationTo organizationTo) {

		String METHOD_NAME = "updateOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isUpdated = false;

		try {

			OmOrganizations omOrganizations = (OmOrganizations) baseDao.findByPK(OmOrganizations.class,
					organizationTo.getOrgId());

			BeanUtils.copyProperties(organizationTo, omOrganizations);


			//omOrganizations.setGdCountries(countries); Removed Country 27-Jan-2021
			omOrganizations.setChangedBy(organizationTo.getUserId());
			omOrganizations.setChangedTs(new Date());
			omOrganizations.setMarkAsDelete(false);

			baseDao.update(omOrganizations);

			isUpdated = true;

		} catch (Exception e) {
			logger.error("Error while adding organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isUpdated;
	}

	/**
	 * Delete organization details
	 * 
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	@Override
	public boolean deleteOrgDetails(String orgId, Integer loggedUser) {

		String METHOD_NAME = "deleteOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isDeleted = false;

		try {

			OmOrganizations omOrganizations = (OmOrganizations) baseDao.findByPK(OmOrganizations.class, orgId);

			omOrganizations.setChangedBy(loggedUser);
			omOrganizations.setChangedTs(new Date());
			omOrganizations.setMarkAsDelete(true);

			baseDao.update(omOrganizations);

			isDeleted = true;

		} catch (Exception e) {
			logger.error("Error while deleteing organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isDeleted;
	}

	/**
	 * Get organization roles
	 * 
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RolesTo> getOrgRoles(String orgId, String roleType, String action) {

		String METHOD_NAME = "getOrgRoles";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<RolesTo> roles = new ArrayList<RolesTo>();

		try {

			// Map<String, List<String>> rTypeMap = new HashMap<String, List<String>>();
			// rTypeMap.put(roleType, Arrays.asList(roleType));
			// rTypeMap.put("ALL", Arrays.asList("SADM", "PADM", "OADM","PEMP"));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orgId", orgId);
			List<UmOrgRoles> umOrgRoles;
			if (roleType.equals("ALL")) {
				umOrgRoles = (List<UmOrgRoles>) baseDao.findByHQLQueryWithNamedParams(prop.getProperty("GET_ALL_ROLES"),
						params);
			} else {
				params.put("roleTypes", roleType);
				umOrgRoles = (List<UmOrgRoles>) baseDao.findByHQLQueryWithNamedParams(prop.getProperty("GET_ROLES"),
						params);
			}

			for (UmOrgRoles umOrgRole : umOrgRoles) {

				List<ParentObjectTo> parentObjs = this.getAccessObjets(umOrgRole, action);

				RolesTo rolesTo = new RolesTo();
				rolesTo.setRoleId(umOrgRole.getRoleId());
				rolesTo.setRoleName(umOrgRole.getRoleDesc());
				rolesTo.setRoleShortId(umOrgRole.getRoleShortId());
				rolesTo.setAccessObjs(parentObjs);
				rolesTo.setRoleType(umOrgRole.getRoleType());
				rolesTo.setUserTypeId(umOrgRole.getUserTypeId());
				roles.add(rolesTo);
			}

		} catch (Exception e) {
			logger.error("Error while getting organization roles: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return roles;
	}

	@SuppressWarnings("unchecked")
	private List<ParentObjectTo> getAccessObjets(UmOrgRoles umOrgRole, String action) throws Exception {

		List<ParentObjectTo> parentObjects = new ArrayList<ParentObjectTo>();
		List<Object> pobjs = new ArrayList<Object>();

		if ("DETAILS".equals(action)) {

			String hqlQuery = prop.getRequiredProperty("ORG_ACCESSMENU_BYROLE");

			List<Object[]> objects = (List<Object[]>) baseDao.findByHQLQueryWithIndexedParams(hqlQuery,
					new Object[] { umOrgRole, false });

			Map<String, Integer> accessLevelMap = new HashMap<String, Integer>();
			Map<String, Integer> readaccessLevelMap = new HashMap<String, Integer>();
			Map<String, Integer> writeaccessLevelMap = new HashMap<String, Integer>();
			for (Object[] obj : objects) {

				if ((boolean) obj[3]) {
					accessLevelMap.put((String) obj[0], 1);
					if ((boolean) obj[4]) {
						readaccessLevelMap.put((String) obj[0], 1);
					} else {
						readaccessLevelMap.put((String) obj[0], 0);
					}
					if ((boolean) obj[5]) {
						writeaccessLevelMap.put((String) obj[0], 1);
					} else {
						writeaccessLevelMap.put((String) obj[0], 0);
					}
				} else {
					accessLevelMap.put((String) obj[0], 0);
					readaccessLevelMap.put((String) obj[0], 0);
					writeaccessLevelMap.put((String) obj[0], 0);
				}
				if(obj[1] == null) {
					pobjs.add(obj[0]);
				}
			}
			LinkedHashMap<String, ParentObjectTo> parentObjs = new LinkedHashMap<String, ParentObjectTo>();
			LinkedHashMap<String, List<ChildOjectTo>> childObjs = new LinkedHashMap<String, List<ChildOjectTo>>();

			List<GdAccessObjects> gdAccessObjects = (List<GdAccessObjects>) baseDao
					.findByHQLQueryWithoutParams(prop.getProperty("ORG_GET_ACCESS_OBJS"));

			for (GdAccessObjects objs : gdAccessObjects) {

				String accessObjId = objs.getAccessObjId();
				String parentObjId = objs.getParentObjId();
				String accessObjDesc = objs.getAccessObjDesc();
				Integer permissionLevel = accessLevelMap.get(objs.getAccessObjId());
				Integer readPermission = readaccessLevelMap.get(objs.getAccessObjId());
				Integer writePermission = writeaccessLevelMap.get(objs.getAccessObjId());

				if (parentObjId == null || parentObjId.isEmpty()) {

					ParentObjectTo parentObj = new ParentObjectTo();
					parentObj.setParentObjId(accessObjId);
					parentObj.setParentObjName(accessObjDesc);
					parentObjs.put(accessObjId, parentObj);

				} else {

					ChildOjectTo childObj = new ChildOjectTo();
					childObj.setObjId(accessObjId);
					childObj.setObjName(accessObjDesc);
					childObj.setPermissionLevel(permissionLevel);
					childObj.setReadPermission(readPermission);
					childObj.setWritePermission(writePermission);
					if (childObjs.get(parentObjId) != null) {

						List<ChildOjectTo> childObjsList = childObjs.get(parentObjId);
						childObjsList.add(childObj);
						childObjs.put(parentObjId, childObjsList);

					} else {

						List<ChildOjectTo> childObjsList = new ArrayList<ChildOjectTo>();
						childObjsList.add(childObj);
						childObjs.put(parentObjId, childObjsList);
					}
				}
			}

			for (Map.Entry<String, ParentObjectTo> obj : parentObjs.entrySet()) {

				int count = 0;

				int rcount = 0;

				int wcount = 0;

				obj.getValue().setChildObjs(childObjs.get(obj.getKey()));

				if (childObjs.get(obj.getKey()) != null) {

					for (ChildOjectTo cobj : childObjs.get(obj.getKey())) {

						if (cobj.getPermissionLevel() != null && cobj.getPermissionLevel() == 1) {
							count++;
						}
						if (cobj.getReadPermission() != null && cobj.getReadPermission() == 1) {
							rcount++;
						}
						if (cobj.getWritePermission() != null && cobj.getWritePermission() == 1) {
							wcount++;
						}
					}

					if (count == obj.getValue().getChildObjs().size()) {
						obj.getValue().setPermissionLevel(1);
						if (rcount == obj.getValue().getChildObjs().size()) {
							obj.getValue().setReadPermission(1);
						} else {
							obj.getValue().setReadPermission(0);
						}
						if (wcount == obj.getValue().getChildObjs().size()) {
							obj.getValue().setWritePermission(1);
						} else {
							obj.getValue().setWritePermission(0);
						}
					} else {
						obj.getValue().setPermissionLevel(0);
						obj.getValue().setReadPermission(0);
						obj.getValue().setWritePermission(0);
					}

				} else {
					if (pobjs.contains(obj.getKey())) {
						obj.getValue().setPermissionLevel(accessLevelMap.get(obj.getKey()));
						obj.getValue().setReadPermission(readaccessLevelMap.get(obj.getKey()));
						obj.getValue().setWritePermission(writeaccessLevelMap.get(obj.getKey()));
					}
					else {
						obj.getValue().setPermissionLevel(0);
						obj.getValue().setReadPermission(0);
						obj.getValue().setWritePermission(0);
					}
				}

				parentObjects.add(obj.getValue());
			}
		}

		return parentObjects;
	}

	/**
	 * Get role details
	 * 
	 * @param roleId
	 * @return
	 */
	@Override
	public RolesTo getRoleDetails(Integer roleId) {

		String METHOD_NAME = "getRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		RolesTo rolesTo = null;

		try {

			UmOrgRoles umOrgRole = (UmOrgRoles) baseDao.findByPK(UmOrgRoles.class, roleId);

			if (umOrgRole != null && !umOrgRole.isMarkAsDelete()) {

				rolesTo = new RolesTo();

				List<ParentObjectTo> parentObjs = this.getAccessObjets(umOrgRole, "DETAILS");

				rolesTo.setRoleId(umOrgRole.getRoleId());
				rolesTo.setRoleName(umOrgRole.getRoleDesc());
				rolesTo.setRoleShortId(umOrgRole.getRoleShortId());
				rolesTo.setAccessObjs(parentObjs);
			}

		} catch (Exception e) {
			logger.error("Error while getting role details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return rolesTo;
	}

	/**
	 * Get business partner details
	 * 
	 * @param bpId
	 * @return
	 */
	@Override
	public BusinessPartnerTo getBusinessPartnerDetails(String bpId) {

		String METHOD_NAME = "getBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		BusinessPartnerTo businessPartner = null;

		try {

			OmOrgBusinessPartners orgBranch = (OmOrgBusinessPartners) baseDao.findByPK(OmOrgBusinessPartners.class,
					bpId);

			businessPartner = getBusinessPartnerTo(orgBranch);

		} catch (Exception e) {
			logger.error("Error while getting business partner details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return businessPartner;
	}

	/**
	 * Get business partner details
	 * 
	 * @param orgBranch
	 * @return
	 */
	private BusinessPartnerTo getBusinessPartnerTo(OmOrgBusinessPartners orgBranch) {

		String METHOD_NAME = "getBusinessPartnerTo";

		logger.info("Entered into the method: " + METHOD_NAME);

		BusinessPartnerTo businessPartner = null;

		try {

			if (orgBranch != null && !orgBranch.getMarkAsDelete()) {

				businessPartner = new BusinessPartnerTo();

				BeanUtils.copyProperties(orgBranch, businessPartner);

				//GdCountries countries = orgBranch.getGdCountries(); Removed Country 27-Jan-20201

				//businessPartner.setCountry(countries.getCountryName()); Removed Country 27-Jan-20201
				businessPartner.setCountryId(orgBranch.getCountryId());
				businessPartner.setBpId(orgBranch.getOrgBpId());
				businessPartner.setName(orgBranch.getBpLegalName());
			}

		} catch (Exception e) {
			logger.error("Error while getting business partner details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return businessPartner;
	}

	/**
	 * Add new business partner details
	 * 
	 * @param bp
	 * @param orgId
	 * @return
	 */
	@Override
	public BusinessPartnerTo addBusinessPartnerDetails(BusinessPartnerTo bp, String orgId, Integer userId) {

		String METHOD_NAME = "addBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		BusinessPartnerTo bpTo = null;

		try {

			bpTo = saveOrUpdateBusinessPartnerDetails(bp, orgId, userId);

		} catch (Exception e) {
			logger.error("Error while adding business partner details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return bpTo;
	}

	/**
	 * Update business partner details
	 * 
	 * @param bp
	 * @param orgId
	 * @param userId
	 * @return
	 */
	@Override
	public BusinessPartnerTo updateBusinessPartnerDetails(BusinessPartnerTo bp, String orgId, Integer userId) {

		String METHOD_NAME = "updateBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		BusinessPartnerTo bpTo = saveOrUpdateBusinessPartnerDetails(bp, orgId, userId);

		logger.info("Exit from the method: " + METHOD_NAME);

		return bpTo;
	}

	@SuppressWarnings("unchecked")
	private BusinessPartnerTo saveOrUpdateBusinessPartnerDetails(BusinessPartnerTo bpTo, String orgId, Integer userId) {

		String METHOD_NAME = "saveOrUpdateBusinessPartnerDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		try {

			OmOrgBusinessPartners omOrgBusinessPartners = null;
			OmOrganizations omOrganizations = null;

			if (bpTo.getBpId() != null) {

				omOrgBusinessPartners = (OmOrgBusinessPartners) baseDao.findByPK(OmOrgBusinessPartners.class,
						bpTo.getBpId());

				omOrganizations = omOrgBusinessPartners.getOmOrganizations();

			} else {

				omOrgBusinessPartners = new OmOrgBusinessPartners();
				final String sqlQuery = "SELECT max( i.org_bp_id ) FROM om_org_business_partners i where org_id =?";
				List<? extends Object> lastId = baseDao.findBySQLQueryWithIndexedParams(sqlQuery,
						new Object[] { orgId });
				Integer maxbpId = Integer.valueOf(lastId.get(0).toString()) + 1;
				omOrgBusinessPartners.setOrgBpId(maxbpId.toString());
				// omOrgBusinessPartners.setOrgBpId(UUID.randomUUID().toString());
				omOrganizations = (OmOrganizations) baseDao.findByPK(OmOrganizations.class, orgId);

				bpTo.setOrgName(omOrganizations.getOrgName());
			}

			/*
			 * boolean isValidGSTIN = validateGSTIN(bpTo.getBpGstinNumber(),
			 * bpTo.getBpPan());
			 * 
			 * if (isValidGSTIN) {
			 * 
			 * List<OmOrgBusinessPartners> OmOrgBusinessPartnersList =
			 * (List<OmOrgBusinessPartners>) baseDao
			 * .findByHQLQueryWithIndexedParams(prop.getRequiredProperty(
			 * "GET_ORG_BP_WITH_GSTIN"), new Object[] { bpTo.getBpGstinNumber(), "Active",
			 * omOrganizations.getOrgId(), bpTo.getBpType(), false });
			 * 
			 * if (OmOrgBusinessPartnersList.isEmpty() ||
			 * "Y".equalsIgnoreCase(bpTo.getDuplicateGstin())) {
			 * 
			 * isValidGSTIN = true;
			 * 
			 * } else {
			 * 
			 * isValidGSTIN = false;
			 * 
			 * bpTo.setExisted(true); } }
			 */

			if (omOrganizations != null) {

				BeanUtils.copyProperties(bpTo, omOrgBusinessPartners);
				omOrgBusinessPartners.setBpLegalName(bpTo.getName());
				omOrgBusinessPartners.setOmOrganizations(omOrganizations);
				//omOrgBusinessPartners.setGdCountries(countries); Removed Country 27-Jan-20201
				omOrgBusinessPartners.setMarkAsDelete(false);
			}

			if (bpTo.getBpId() != null) {

				omOrgBusinessPartners.setChangedBy(userId);
				omOrgBusinessPartners.setChangedTs(new Date());

				baseDao.update(omOrgBusinessPartners);

			} else {

				omOrgBusinessPartners.setCreatedBy(userId);
				omOrgBusinessPartners.setCreatedTs(new Date());

				baseDao.save(omOrgBusinessPartners);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("roleShortId", new ArrayList<String>() {
					{
						add("SADM");
						add("OADM");
						add("CT");
					}
				});
				param.put("orgId", omOrganizations.getOrgId());
				List<UmOrgRoles> umOrgRoles = (List<UmOrgRoles>) baseDao
						.findByHQLQueryWithNamedParams(prop.getRequiredProperty("GET_ROLE_BY_ORGID"), param);

				if (umOrgRoles != null && !umOrgRoles.isEmpty()) {
					for (UmOrgRoles umOrgRole : umOrgRoles) {

						// UmOrgRoles umOrgRole = umOrgRoles.get(0);

						List<UmUsers> usersList = (List<UmUsers>) baseDao.findByHQLQueryWithIndexedParams(
								prop.getRequiredProperty("GET_ORG_USERS_BY_ROLE"),
								new Object[] { omOrganizations, umOrgRole, false });

						Set<String> setEmails = new HashSet<String>();

						for (UmUsers user : usersList) {

							UmOrgUsers orgUser = new UmOrgUsers();

							orgUser.setOmOrganizations(omOrganizations);
							orgUser.setOmOrgBusinessPartners(omOrgBusinessPartners);
							orgUser.setUmUsers(user);
							orgUser.setUmOrgRoles(umOrgRole);
							orgUser.setMarkAsDelete(false);
							orgUser.setCreatedBy(userId);
							orgUser.setCreatedTs(new Date());
							setEmails.add(user.getEmail());

							baseDao.save(orgUser);
						}

						bpTo.setEmails(setEmails);
						bpTo.setBpId(omOrgBusinessPartners.getOrgBpId());

					}
				}
			}

			// bpTo.setValidGstn(true);
			bpTo.setSuccess(true);

		} catch (Exception e) {
			logger.error("Error while save or update business partner detais: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return bpTo;
	}

	/**
	 * Delete business partner details
	 * 
	 * @param bpids
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	@Override
	public boolean deleteBusinessPartnersDetails(List<String> bpIds, String orgId, Integer loggedUser) {

		String METHOD_NAME = "deleteBusinessPartnersDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean flag = false;

		if (bpIds != null && !bpIds.isEmpty()) {

			for (String id : bpIds) {

				OmOrgBusinessPartners omOrgBusinessPartners = (OmOrgBusinessPartners) baseDao
						.findByPK(OmOrgBusinessPartners.class, id);

				if (omOrgBusinessPartners != null) {

					omOrgBusinessPartners.setMarkAsDelete(true);

					baseDao.update(omOrgBusinessPartners);

					Set<String> setEmails = new HashSet<String>();

					for (UmOrgUsers umOrgUsers : omOrgBusinessPartners.getUmOrgUsers()) {

						umOrgUsers.setMarkAsDelete(true);
						umOrgUsers.setChangedBy(loggedUser);
						umOrgUsers.setChangedTs(new Date());

						setEmails.add(umOrgUsers.getUmUsers().getEmail());

						baseDao.update(umOrgUsers);
					}
				}

				flag = true;
			}
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return flag;
	}

	/**
	 * Add new role for specific organization
	 * 
	 * @param addRoleTo
	 * @param userTo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addOrgRoleDetails(AddRoleTo addRoleTo, UserTo userTo) {

		final String METHOD_NAME = "addOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isAdded = false;

		try {

			String shortName = null, roleShortId = null;

			String[] split = addRoleTo.getRoleName().split(" ");

			for (String string : split) {

				if (shortName == null)
					shortName = string.substring(0, 1);
				else
					shortName = shortName + string.substring(0, 1);
			}

			UmOrgRoles umOrgRole = (UmOrgRoles) baseDao.findByPK(UmOrgRoles.class, addRoleTo.getRoleRef());

			String roleShortName = umOrgRole.getRoleShortId();

			String[] roleShortNames = roleShortName.split("_");

			for (int i = 0; i < roleShortNames.length; i++) {

				if (i == 0) {

					shortName = shortName.toUpperCase();
					roleShortId = roleShortNames[i] + "_" + shortName;

				} else {

					if (i == (roleShortNames.length - 1))
						roleShortId = roleShortId + roleShortNames[i];
					else
						roleShortId = roleShortId + roleShortNames[i];
				}
			}

			OmOrganizations omOrganizations = (OmOrganizations) baseDao.findByPK(OmOrganizations.class,
					addRoleTo.getOrgId());

			if (omOrganizations != null) {

				UmOrgRoles umOrgRoles = new UmOrgRoles();
				umOrgRoles.setCreatedBy(userTo.getUserId());
				umOrgRoles.setCreatedTs(new Date());
				umOrgRoles.setMarkAsDelete(false);
				umOrgRoles.setRoleShortId(roleShortId);
				umOrgRoles.setRoleType(umOrgRole.getRoleType());
				umOrgRoles.setOrgId(addRoleTo.getOrgId());
				umOrgRoles.setRoleDesc(addRoleTo.getRoleName());

				baseDao.save(umOrgRoles);

//				List<UmOrgRolesPermissions> permissions = (List<UmOrgRolesPermissions>) baseDao
//						.findByHQLQueryWithIndexedParams(prop.getProperty("GET_ROLE_PERMISSIONS"),
//								new Object[] { addRoleTo.getRoleRef() });
//
//				for (UmOrgRolesPermissions permission : permissions) {
//
//					UmOrgRolesPermissions rolePermission = new UmOrgRolesPermissions();
//
//					rolePermission.setUmOrgRoles(umOrgRoles);
//					rolePermission.setGdAccessObjects(permission.getGdAccessObjects());
//					rolePermission.setPermissionLevel(permission.isPermissionLevel());
//					rolePermission.setCreatedBy(userTo.getUserId());
//					rolePermission.setCreatedTs(new Date());
//					rolePermission.setMarkAsDelete(false);
//					rolePermission.setReadPermission(permission.getReadPermission());
//					rolePermission.setWritePermission(permission.getWritePermission());
//					baseDao.save(rolePermission);
//				}

				isAdded = true;
			}

		} catch (Exception e) {
			logger.error("Error while adding organization role: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isAdded;
	}

	/**
	 * Delete the role of specific organization
	 * 
	 * @param roleId
	 * @param defaultRoleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteOrgRoleDetails(Integer roleId) {

		String METHOD_NAME = "deleteOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isDeleted = false;

		try {

			UmOrgRoles umOrgRoles = (UmOrgRoles) baseDao.findByPK(UmOrgRoles.class, roleId);

			if (umOrgRoles != null) {

				umOrgRoles.setMarkAsDelete(true);

				List<UmOrgRoles> roles = (List<UmOrgRoles>) baseDao.findByHQLQueryWithIndexedParams(
						"from UmOrgRoles r where r.roleType=?1 and r.roleShortId=?2 and r.markAsDelete=false", 
						new Object[] { umOrgRoles.getRoleType(),  umOrgRoles.getRoleType() });
				
				if(!roles.isEmpty()){
					baseDao.update(umOrgRoles);
					List<UmOrgUsers> umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
							prop.getRequiredProperty("ORG_USER_BY_ROLE"), new Object[] { roleId });
	
					if (!umOrgUsers.isEmpty()) {
	
						UmOrgRoles umOrgRolesNew = (UmOrgRoles) baseDao.findByPK(UmOrgRoles.class, roles.get(0).getRoleId());//defaultRoleId
	
						for (UmOrgUsers orgUser : umOrgUsers) {
	
							orgUser.setUmOrgRoles(umOrgRolesNew);
	
							baseDao.update(orgUser);
						}
					}
					isDeleted = true;
				}
			}

		} catch (Exception e) {
			logger.error("Error while deleting organization roles: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isDeleted;

	}

	/**
	 * Get business partners of specific organization
	 * 
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<BusinessPartnerTo> getOrgBusinessPartners(String orgId, Integer userId, String all_bp) {

		String METHOD_NAME = "getOrgBusinessPartners";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<BusinessPartnerTo> bpToList = new ArrayList<BusinessPartnerTo>();

		try {

			BusinessPartnerTo businessPartner = null;

			String hqlQuery = null;
			List<OmOrgBusinessPartners> bpS = null;
			if (all_bp == null) {
				hqlQuery = prop.getRequiredProperty("ORG_BP_DETAILS");
				bpS = (List<OmOrgBusinessPartners>) baseDao.findByHQLQueryWithIndexedParams(hqlQuery,
						new Object[] { orgId, userId });
			} else {
				hqlQuery = prop.getRequiredProperty("GET_ORG_BP_DETAILS");
				bpS = (List<OmOrgBusinessPartners>) baseDao.findByHQLQueryWithIndexedParams(hqlQuery,
						new Object[] { orgId });
			}

			for (OmOrgBusinessPartners orgBranch : bpS) {

				businessPartner = getBusinessPartnerTo(orgBranch);

				if (businessPartner != null) {

					bpToList.add(businessPartner);
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting business partner: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return bpToList;
	}

	/**
	 * Disable the user vs business partner mapping
	 * 
	 * @param bpId
	 * @param payload
	 * @param loggedUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean disableOrgUsers(String bpId, List<Integer> payload, Integer loggedUser) {

		String METHOD_NAME = "disableOrgUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isDisabled = false;

		try {

			for (Integer userId : payload) {

				UmUsers umUser = (UmUsers) baseDao.findByPK(UmUsers.class, userId);

				List<UmOrgUsers> umOrgUserList = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
						prop.getRequiredProperty("ORG_USERS_BY_USER_ORGBPID"), new Object[] { umUser, bpId });

				if (!umOrgUserList.isEmpty()) {

					UmOrgUsers umOrgUsers = umOrgUserList.get(0);

//					if (umOrgUsers.getUmOrgRoles() != null
//							&& (GeneralConstants.ROLE_ORG_ADMIN.equals(umOrgUsers.getUmOrgRoles().getRoleShortId()))) {
//						List<UmOrgUsers> assignedBps = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
//								prop.getRequiredProperty("GET_ORG_USERS_BY_USER_ORG"),
//								new Object[] { umUser, umOrgUsers.getOmOrganizations() });
//						for (UmOrgUsers assignedBp : assignedBps) {
//							assignedBp.setMarkAsDelete(true);
//							assignedBp.setChangedBy(loggedUser);
//							assignedBp.setChangedTs(new Date());
//
//							baseDao.saveOrUpdate(assignedBp);
//						}
//					} else {

						umOrgUsers.setMarkAsDelete(true);
						umOrgUsers.setChangedBy(loggedUser);
						umOrgUsers.setChangedTs(new Date());
						baseDao.saveOrUpdate(umOrgUsers);
						
						umUser.setMarkAsDelete(true);
						baseDao.saveOrUpdate(umUser);
//					}

					isDisabled = true;
				}
			}

		} catch (Exception e) {

			logger.error(
					"Error while disabling the user vs business partner mapping : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isDisabled;
	}

	/**
	 * Get users of specific Organization/Business partners
	 * 
	 * @param bpId
	 * @param orgId
	 * @param action
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserTo> getUsers(String bpId, String orgId, String action, Integer loggedUser,String type,String activeOrDeActive) {

		String METHOD_NAME = "getUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<UserTo> userToList = null;

		try {

			List<UmOrgUsers> umOrgUsers = null;

			if (GeneralConstants.BP_ACTION.equalsIgnoreCase(action)) {

				umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
						prop.getProperty("x"),
						new Object[] { bpId, loggedUser, GeneralConstants.ROLE_BP_ADMIN });

			} else if (GeneralConstants.ORG_ACTION.equalsIgnoreCase(action)) {

				umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
						prop.getProperty("GET_USERS_BY_ORGID"),
						new Object[] { orgId, loggedUser, GeneralConstants.ROLE_ORG_ADMIN });
			} else if (GeneralConstants.ALL_ACTION.equalsIgnoreCase(action)) {
				String usersQuery= prop.getProperty("GET_ALL_USERS");
				if("true".equalsIgnoreCase(activeOrDeActive)) {
					usersQuery=usersQuery +" um.umUsers.isDeactivated= true and um.umOrgRoles.userTypeId != 4 order by um.umUsers.firstName";
					umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
							usersQuery,
							new Object[] { orgId });

				}else {
					if(type==null) {
						usersQuery=usersQuery +" um.umUsers.isDeactivated= false  order by um.umUsers.firstName";	
						umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
								usersQuery,
								new Object[] { orgId});
					}else {
						usersQuery=usersQuery +" um.umUsers.isDeactivated= false and um.umOrgRoles.roleId = ?2 order by um.umUsers.firstName";	
						umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
								usersQuery,
								new Object[] { orgId, Integer.parseInt(type) });
					}
					

				}
			}

			if (umOrgUsers != null && !umOrgUsers.isEmpty()) {
				
				userToList = new ArrayList<UserTo>();

				for (UmOrgUsers umOrgUser : umOrgUsers) {
					UserTo orgUser = new UserTo();
					if(umOrgUser.getUmUsers().getIsSspActive() != null) {
						if(umOrgUser.getUmUsers().getIsSspActive()) {
							

							UmUsers umUsers = umOrgUser.getUmUsers();

							OmOrgBusinessPartners omOrgBusinessPlaces = umOrgUser.getOmOrgBusinessPartners();

							UmOrgRoles umOrgRoles = umOrgUser.getUmOrgRoles();

							orgUser.setUserId(umUsers.getUserId());
							orgUser.setLogonId(umUsers.getLogonId());
							orgUser.setEmail(umUsers.getEmail());
							orgUser.setFirstName(umUsers.getFirstName());
							orgUser.setLastName(umUsers.getLastName());
							orgUser.setMiddleName(umUsers.getMiddleName());
							orgUser.setUserLocked(umUsers.getUserLocked());
							orgUser.setMobile(umUsers.getMobile());
							orgUser.setBpId(omOrgBusinessPlaces.getOrgBpId());
							orgUser.setAddress(umUsers.getAddress());
							orgUser.setDeactivated(umUsers.isDeactivated());
							orgUser.setRegion(umUsers.getRegion());
							orgUser.setRegCenter(umUsers.getRegCenter());
							orgUser.setCountry(umUsers.getCountry());
							orgUser.setEmailIds(umUsers.getEmailIds());
							orgUser.setUserTypeId(umUsers.getGdUserTypes().getUserTypeId());
							orgUser.setTypeDesc(umUsers.getGdUserTypes().getTypeDesc());
							orgUser.setTypeShortId(umUsers.getGdUserTypes().getTypeShortId());
							orgUser.setUserFullName(umUsers.getFirstName());
							orgUser.setAddress(umUsers.getAddress());
							orgUser.setCity(umUsers.getBookingOffice());
							orgUser.setEmpCode(umUsers.getEmpCode());
							orgUser.setState(umUsers.getState());
							
							orgUser.setGstNo(umUsers.getGstNo() != null ? umUsers.getGstNo() : null);
							if(umUsers.getDaAdsTypes() != null) {
								List<String> asList = Arrays.asList(umUsers.getDaAdsTypes().split(","));
								if(asList != null && !asList.isEmpty()) {
									orgUser.setClassifiedTypeIds(asList);
								}						
							}					
							if(umUsers.getMiddleName() != null && umUsers.getLastName() != null && umUsers.getMiddleName().isEmpty() && !umUsers.getLastName().isEmpty())
								orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getMiddleName()+" "+umUsers.getLastName());
							else if(umUsers.getMiddleName() != null && !umUsers.getMiddleName().isEmpty())
								orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getMiddleName());
							else if(umUsers.getLastName() != null && !umUsers.getLastName().isEmpty())
								orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getLastName());
							
							if(umOrgUser.getSecondaryRoles() != null && !umOrgUser.getSecondaryRoles().isEmpty()){
							List<String> sIds = new ArrayList<String>(Arrays.asList(umOrgUser.getSecondaryRoles().split(",")));
							orgUser.setSecondaryRoles(sIds);
							}
							
							if (umOrgRoles != null) {
								orgUser.setRoleType(umOrgRoles.getRoleType());
								orgUser.setRole(umOrgRoles.getRoleDesc());
								orgUser.setRoleId(umOrgRoles.getRoleId().toString());
								orgUser.setRoleShortId(umOrgRoles.getRoleShortId());
							}
						}
					}else {
						UmUsers umUsers = umOrgUser.getUmUsers();

						OmOrgBusinessPartners omOrgBusinessPlaces = umOrgUser.getOmOrgBusinessPartners();

						UmOrgRoles umOrgRoles = umOrgUser.getUmOrgRoles();

						orgUser.setUserId(umUsers.getUserId());
						orgUser.setLogonId(umUsers.getLogonId());
						orgUser.setEmail(umUsers.getEmail());
						orgUser.setFirstName(umUsers.getFirstName());
						orgUser.setLastName(umUsers.getLastName());
						orgUser.setMiddleName(umUsers.getMiddleName());
						orgUser.setUserLocked(umUsers.getUserLocked());
						orgUser.setMobile(umUsers.getMobile());
						orgUser.setBpId(omOrgBusinessPlaces.getOrgBpId());
						orgUser.setAddress(umUsers.getAddress());
						orgUser.setDeactivated(umUsers.isDeactivated());
						orgUser.setRegion(umUsers.getRegion());
						orgUser.setRegCenter(umUsers.getRegCenter());
						orgUser.setCountry(umUsers.getCountry());
						orgUser.setEmailIds(umUsers.getEmailIds());
						orgUser.setUserTypeId(umUsers.getGdUserTypes().getUserTypeId());
						orgUser.setTypeDesc(umUsers.getGdUserTypes().getTypeDesc());
						orgUser.setTypeShortId(umUsers.getGdUserTypes().getTypeShortId());
						orgUser.setUserFullName(umUsers.getFirstName());
						orgUser.setAddress(umUsers.getAddress());
						orgUser.setCity(umUsers.getBookingOffice());
						orgUser.setEmpCode(umUsers.getEmpCode());
						orgUser.setState(umUsers.getState());
						orgUser.setGstNo(umUsers.getGstNo() != null ? umUsers.getGstNo() : null);
						if(umUsers.getIsRmsApprover() != null) {
							orgUser.setIsRmsApprover(umUsers.getIsRmsApprover());
						}
						if(umUsers.getDaAdsTypes() != null) {
							List<String> asList = Arrays.asList(umUsers.getDaAdsTypes().split(","));
							if(asList != null && !asList.isEmpty()) {
								orgUser.setClassifiedTypeIds(asList);
							}						
						}					
						if(umUsers.getMiddleName() != null && umUsers.getLastName() != null && umUsers.getMiddleName().isEmpty() && !umUsers.getLastName().isEmpty())
							orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getMiddleName()+" "+umUsers.getLastName());
						else if(umUsers.getMiddleName() != null && !umUsers.getMiddleName().isEmpty())
							orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getMiddleName());
						else if(umUsers.getLastName() != null && !umUsers.getLastName().isEmpty())
							orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getLastName());
						
						if(umOrgUser.getSecondaryRoles() != null && !umOrgUser.getSecondaryRoles().isEmpty()){
						List<String> sIds = new ArrayList<String>(Arrays.asList(umOrgUser.getSecondaryRoles().split(",")));
						orgUser.setSecondaryRoles(sIds);
						}
						
						if (umOrgRoles != null) {
							orgUser.setRoleType(umOrgRoles.getRoleType());
							orgUser.setRole(umOrgRoles.getRoleDesc());
							orgUser.setRoleId(umOrgRoles.getRoleId().toString());
							orgUser.setRoleShortId(umOrgRoles.getRoleShortId());
						}
					}
				
					if(orgUser.getUserId() != null) {
						userToList.add(orgUser);
					}					
				}
			}

		} catch (Exception e) {
			logger.error("Error while finding user detais by user id: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return userToList;
	}

	/**
	 * Assign user to business partner
	 * 
	 * @param userTo
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserTo addBusinessPartnerUser(List<UserTo> users, String orgId, Integer loggedUser, String logonId) {

		String METHOD_NAME = "addBusinessPartnerUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		UserTo userToRetObj = new UserTo();
		String bpLegalName = "";
		try {
			for(UserTo userTo : users){
			String message = null;

			boolean flag = true, isExist = true, isOrgAdmin = false;

			OmOrganizations omOrganizations = (OmOrganizations) baseDao.findByPK(OmOrganizations.class, orgId);

			UmUsers umUser = null;

			if (userTo.getUserId() != null) {

				umUser = (UmUsers) baseDao.findByPK(UmUsers.class, userTo.getUserId());
				umUser.setFirstName(userTo.getFirstName());
				umUser.setMiddleName(userTo.getMiddleName() != null ? userTo.getMiddleName() : "");
				umUser.setLastName(userTo.getLastName() != null ? userTo.getLastName() : "");
				umUser.setMobile(userTo.getMobile());
				umUser.setAddress(userTo.getAddress());
				umUser.setRegion(userTo.getRegion());
				umUser.setRegCenter(userTo.getRegCenter());
				umUser.setCountry(userTo.getCountry());
				umUser.setDeactivated(userTo.isDeactivated());
				umUser.setIsSspActive(userTo.getIsSspActive() != null ? userTo.getIsSspActive() : null);
				if(userTo.getIsSspActive() != null) {
					if(userTo.getIsSspActive()) {
						umUser.setApprovedBy(loggedUser);
						umUser.setApprovedTime(new Date());
						umUser.setStatus(userTo.getActDeactAction());
						umUser.setComments(userTo.getComments() != null ? userTo.getComments() : null);
					}
					if(!userTo.getIsSspActive()) {
						umUser.setStatus(userTo.getActDeactAction());
						umUser.setIsSubmitted(false);
						umUser.setComments(userTo.getComments() != null ? userTo.getComments() : null);
					}
				}
				
				umUser.setEmail(userTo.getEmail());
				umUser.setEmailIds(userTo.getEmailIds());
				GdUserTypes gdUserTypes = new GdUserTypes();
				gdUserTypes.setUserTypeId(userTo.getUserTypeId());
				umUser.setGdUserTypes(gdUserTypes);
				umUser.setBookingOffice(userTo.getCity());
				umUser.setEmpCode(userTo.getEmpCode());
				umUser.setState(userTo.getState());
				if(userTo.getIsRmsApprover() != null) {
					umUser.setIsRmsApprover(userTo.getIsRmsApprover());
				}else {
					umUser.setIsRmsApprover(false);
				}
				if(userTo.getClassifiedTypeIds() != null && !userTo.getClassifiedTypeIds().isEmpty()) {
					String cTypeIds = userTo.getClassifiedTypeIds().stream().map(Object::toString)
							.collect(Collectors.joining(","));
					umUser.setDaAdsTypes(cTypeIds);
				}else {
					umUser.setDaAdsTypes(null);
				}
				isExist = false;
				try {
					baseDao.save(umUser);
					message = "Updated";
				} catch (Exception e) {
					flag = false;
					logger.error("Error while updating user detais : " + ExceptionUtils.getStackTrace(e));
				}

			} else {

				List<UmUsers> umUserList = (List<UmUsers>) baseDao.findByHQLQueryWithIndexedParams(
						prop.getRequiredProperty("ORG_USER_DETAILS_BYLOGONID"),
						new Object[] { userTo.getLogonId(), false });

				if (umUserList.isEmpty()) {

					umUser = new UmUsers();
//					final String sqlQuery = "SELECT max( i.user_id ) FROM um_users i";
//					List<? extends Object> lastId = baseDao.findBySQLQueryWithoutParams(sqlQuery);
//					Integer userId = Integer.valueOf(lastId.get(0).toString()) + 1;
//					umUser.setUserId(userId);
					umUser.setLogonId(userTo.getLogonId());
					umUser.setLogonRetries(0);
					umUser.setUserLocked(false);
					umUser.setPassword(userTo.getNewPwd());
					GdUserTypes gdUserTypes = new GdUserTypes();
					gdUserTypes.setUserTypeId(userTo.getUserTypeId());
//					gdUserTypes.setUserTypeId(GeneralConstants.RMS_USERS);
					umUser.setGdUserTypes(gdUserTypes);
					if("1".equalsIgnoreCase(userTo.getRoleId())){
						umUser.setAdmin(true);
					}else{
						umUser.setAdmin(false);
					}
					umUser.setEmail(userTo.getEmail());
					umUser.setFirstName(userTo.getFirstName());
					umUser.setMiddleName(userTo.getMiddleName() != null ? userTo.getMiddleName() : "");
					umUser.setLastName(userTo.getLastName() != null ? userTo.getLastName() : "");
					umUser.setMobile(userTo.getMobile());
					umUser.setCreatedBy(loggedUser);
					umUser.setCreatedTs(new Date());
					umUser.setChangedBy(loggedUser);
					umUser.setChangedTs(new Date());
					umUser.setMarkAsDelete(false);
					umUser.setDeactivated(false);
					umUser.setRegion(userTo.getRegion()==null ? 0 : userTo.getRegion());
					umUser.setRegCenter(userTo.getRegCenter()==null ? 0 : userTo.getRegCenter());
					umUser.setCountry(userTo.getCountry()==null ? 0 : userTo.getCountry());
					umUser.setAddress(userTo.getAddress());
					umUser.setBookingOffice(userTo.getCity());
					umUser.setEmpCode(userTo.getEmpCode());
					umUser.setState(userTo.getState());
					if(userTo.getIsRmsApprover() != null) {
						umUser.setIsRmsApprover(userTo.getIsRmsApprover());
					}else {
						umUser.setIsRmsApprover(false);
					}
					try {
						baseDao.save(umUser);
						message = "VALID";
					} catch (Exception e) {
						flag = false;
						logger.error("Error while saving user detais : " + ExceptionUtils.getStackTrace(e));
					}
					
				} else {
					umUser = umUserList.get(0);
					userToRetObj.setExisted(true);
					userTo.setUserId(umUser.getUserId());
					if (userTo.getBpId() != null) {
						message = validateUser(userTo);
					}
				}
			}

			if (message != null && (message.equals("VALID") || message.equals("Updated"))) {

				UmOrgUsers umOrgUsers = null;
				if (flag) {
					UmOrgRoles umOrgRoles = (UmOrgRoles) baseDao.findByPK(UmOrgRoles.class,
							Integer.parseInt(userTo.getRoleId()));

					if (umOrgRoles != null) {
						userToRetObj.setRole(umOrgRoles.getRoleDesc());
					}

					if (umOrgRoles != null) {
						List<UmOrgUsers> umOrgUserList = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
								prop.getRequiredProperty("ORG_CHEK_USER"),
								new Object[] { omOrganizations, umUser, false });
						
						if (umOrgUserList.isEmpty()) {
							for (OmOrgBusinessPartners businessPlaces : omOrganizations.getOmOrgBusinessPartners()) {
//								Integer Id;
//								String sqlQuery = "SELECT max( i.ORG_USER_REL_ID ) FROM UM_ORG_USERS i";
//								List<? extends Object> lastId = baseDao.findBySQLQueryWithoutParams(sqlQuery);
//								if(lastId.get(0)!=null){
//								Id = Integer.valueOf(lastId.get(0)+"") + 1;
//								}else {
//									Id = 1;
//								}
								umOrgUsers = new UmOrgUsers();
//								umOrgUsers.setOrgUserRelId(Id);
								umOrgUsers.setOmOrganizations(omOrganizations);
								umOrgUsers.setUmUsers(umUser);
								umOrgUsers.setUmOrgRoles(umOrgRoles);
								umOrgUsers.setOmOrgBusinessPartners(businessPlaces);

									if (userTo.getSecondaryRoles() != null && !userTo.getSecondaryRoles().isEmpty()) {
										String sId = userTo.getSecondaryRoles().stream().map(Object::toString)
												.collect(Collectors.joining(","));
										umOrgUsers.setSecondaryRoles(sId);
									}
								
								umOrgUsers.setMarkAsDelete(false);
								umOrgUsers.setCreatedBy(loggedUser);
								umOrgUsers.setCreatedTs(new Date());
								umOrgUsers.setChangedBy(loggedUser);
								umOrgUsers.setChangedTs(new Date());

								try {
									baseDao.save(umOrgUsers);
								} catch (Exception e) {
									flag = false;
									logger.error("Error while saving um org user detais : "
											+ ExceptionUtils.getStackTrace(e));
								}
							}
							if(userTo.getClassification()!=null && !userTo.getClassification().isEmpty()){
								UmCategoryUsers umCategoryUsers=new UmCategoryUsers();
								umCategoryUsers.setCategoryId(Integer.parseInt(userTo.getClassification()));
								umCategoryUsers.setUserId(umUser.getUserId());
								
								try {
									baseDao.save(umCategoryUsers);
								} catch (Exception e) {
									flag = false;
									logger.error("Error while saving UmCategoryUsers detais : "
											+ ExceptionUtils.getStackTrace(e));
								}
							}
						} else {
							
							for (OmOrgBusinessPartners businessPlaces : omOrganizations.getOmOrgBusinessPartners()) {
//								Integer Id;
//								String sqlQuery = "SELECT max( i.ORG_USER_REL_ID ) FROM UM_ORG_USERS i";
//								List<? extends Object> lastId = baseDao.findBySQLQueryWithoutParams(sqlQuery);
//								if(lastId.get(0)!=null){
//								Id = Integer.valueOf(lastId.get(0)+"") + 1;
//								}else {
//									Id = 1;
//								}
								umOrgUsers = umOrgUserList.get(0);
//								umOrgUsers = new UmOrgUsers();
//								umOrgUsers.setOrgUserRelId(Id);
								umOrgUsers.setOmOrganizations(omOrganizations);
								umOrgUsers.setUmUsers(umUser);
								umOrgUsers.setUmOrgRoles(umOrgRoles);
								umOrgUsers.setOmOrgBusinessPartners(businessPlaces);

									if (userTo.getSecondaryRoles() != null && !userTo.getSecondaryRoles().isEmpty()) {
										String sId = userTo.getSecondaryRoles().stream().map(Object::toString)
												.collect(Collectors.joining(","));
										umOrgUsers.setSecondaryRoles(sId);
									}
								
								umOrgUsers.setMarkAsDelete(false);
								umOrgUsers.setCreatedBy(loggedUser);
								umOrgUsers.setCreatedTs(new Date());
								umOrgUsers.setChangedBy(loggedUser);
								umOrgUsers.setChangedTs(new Date());

								try {
									baseDao.saveOrUpdate(umOrgUsers);
								} catch (Exception e) {
									flag = false;
									logger.error("Error while saving um org user detais : "
											+ ExceptionUtils.getStackTrace(e));
								}
							}
							
							flag = false;
							userToRetObj.setExisted(true);
						}
					}

					if (flag) {
						userToRetObj.setUpdated(true);
					}
				}

				if (flag) {
					if (isExist) {
						
						Calendar c = Calendar.getInstance();
						c.add(Calendar.DAY_OF_YEAR, 3);

						UmUserPwdReset umUserPwdReset = new UmUserPwdReset();
						umUserPwdReset.setResetKey(String.valueOf(UUID.randomUUID()));
						umUserPwdReset.setUmUsers(umUser);
						umUserPwdReset.setValidTill(c.getTime());
						umUserPwdReset.setCreatedBy(loggedUser);
						umUserPwdReset.setCreatedTs(new Date());
						umUserPwdReset.setChangedBy(loggedUser);
						umUserPwdReset.setChangedTs(new Date());
						umUserPwdReset.setMarkAsDelete(false);

						try {
							baseDao.save(umUserPwdReset);
							userToRetObj.setResetKey(umUserPwdReset.getResetKey());
							userToRetObj.setResetPwd(true);

						} catch (Exception e) {
							flag = false;
							logger.error("Error while saving user reset password detais : "
									+ ExceptionUtils.getStackTrace(e));
						}

					} else {
						userToRetObj.setExisted(true);
					}
				}

				userToRetObj.setEmail(umUser.getEmail());
				userToRetObj.setUpdated(flag);
				userToRetObj.setMessage(message);

			} else if (message != null) {
				userToRetObj.setMessage(message);
			}
			}
		} catch (Exception e) {
			logger.error("Error while adding business partner user:" + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return userToRetObj;
	}

	/**
	 * Validate user
	 * 
	 * @param userTo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String validateUser(UserTo userTo) {

		String METHOD_NAME = "validateUser";
		logger.info("Entered into the method: " + METHOD_NAME);

		try {
			OmOrgBusinessPartners currentBp = (OmOrgBusinessPartners) baseDao.findByPK(OmOrgBusinessPartners.class,
					userTo.getBpId());

			if (currentBp != null) {
				List<Object> umOrgUserList = (List<Object>) baseDao.findByHQLQueryWithIndexedParams(
						prop.getRequiredProperty("GET_BPS_BY_USER"), new Object[] { userTo.getUserId() });

				if (umOrgUserList.isEmpty()) {
					return "VALID";
				}

				for (Object obj : umOrgUserList) {
					OmOrgBusinessPartners orgBp = (OmOrgBusinessPartners) obj;
					return orgBp.getOrgBpId().equals(currentBp.getOrgBpId()) ? "EXIST" : "VALID";
				}
			}

		} catch (Exception e) {
			logger.error("Error while adding business partner user:" + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);

		return null;
	}

	/**
	 * Update user to business partner
	 * 
	 * @param userTo
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateBusinessPartnerUser(String orgId, UserTo userTo, Integer userId) {

		final String METHOD_NAME = "updateBusinessPartnerUser";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isUpdated = false;

		try {

			OmOrganizations omOrganizations = (OmOrganizations) baseDao.findByPK(OmOrganizations.class, orgId);

			UmUsers umUser = (UmUsers) baseDao.findByPK(UmUsers.class, userTo.getUserId());

			if (omOrganizations != null && umUser != null) {

				umUser.setLogonId(userTo.getLogonId());
				umUser.setEmail(userTo.getEmail());
				umUser.setFirstName(userTo.getFirstName());
				umUser.setMiddleName(userTo.getMiddleName());
				umUser.setLastName(userTo.getLastName());
				umUser.setMobile(userTo.getMobile());

				if (userTo.getBpType() != null) {

					GdUserTypes gdUserTypes = new GdUserTypes();

					if (userTo.getBpType().equalsIgnoreCase(GeneralConstants.SS_BP_TYPE)
							|| userTo.getBpType().equalsIgnoreCase(GeneralConstants.KAD_BP_TYPE)) {

						gdUserTypes.setUserTypeId(GeneralConstants.SKD_USERS);

					} else {

						gdUserTypes.setUserTypeId(GeneralConstants.RMS_USERS);
					}

					umUser.setGdUserTypes(gdUserTypes);
				}

				umUser.setCreatedBy(userTo.getUserId());
				umUser.setCreatedTs(new Date());
				umUser.setChangedBy(userTo.getUserId());
				umUser.setChangedTs(new Date());
				umUser.setMarkAsDelete(false);

				try {

					baseDao.update(umUser);

					isUpdated = true;

				} catch (Exception e) {

					logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
				}

				if (isUpdated) {

					List<Integer> userIds = new ArrayList<Integer>();

					userIds.add(userTo.getUserId());

					UmOrgUsers umOrgUsers = null;

					UmOrgRoles umOrgRoles = (UmOrgRoles) baseDao.findByPK(UmOrgRoles.class,
							Integer.parseInt(userTo.getRoleId()));

					this.disableOrgUsers(userTo.getBpId(), userIds, userId);

					if (umOrgRoles != null && GeneralConstants.ROLE_ORG_ADMIN.equals(umOrgRoles.getRoleShortId())) {

						List<UmOrgUsers> umOrgUserList = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
								prop.getRequiredProperty("ORG_CHEK_USER"),
								new Object[] { omOrganizations, umUser, false });

						if (umOrgUserList.isEmpty()) {

							for (OmOrgBusinessPartners businessPlaces : omOrganizations.getOmOrgBusinessPartners()) {

								umOrgUsers = new UmOrgUsers();
								umOrgUsers.setOmOrganizations(omOrganizations);
								umOrgUsers.setUmUsers(umUser);
								umOrgUsers.setUmOrgRoles(umOrgRoles);
								umOrgUsers.setOmOrgBusinessPartners(businessPlaces);
								umOrgUsers.setMarkAsDelete(false);
								umOrgUsers.setCreatedBy(userId);
								umOrgUsers.setCreatedTs(new Date());
								umOrgUsers.setChangedBy(userId);
								umOrgUsers.setChangedTs(new Date());

								try {

									baseDao.save(umOrgUsers);

									isUpdated = true;

								} catch (Exception e) {

									logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
								}
							}
						}

					} else {

						List<UmOrgUsers> umOrgUserList = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
								prop.getRequiredProperty("ORG_USERS_BY_USER_ORGBPID"),
								new Object[] { umUser, userTo.getBpId() });

						if (!umOrgUserList.isEmpty()) {

							umOrgUsers = umOrgUserList.get(0);
							umOrgUsers.setChangedBy(userId);
							umOrgUsers.setChangedTs(new Date());

						} else {

							umOrgUsers = new UmOrgUsers();
							umOrgUsers.setCreatedBy(userId);
							umOrgUsers.setCreatedTs(new Date());
						}

						OmOrgBusinessPartners omOrgBusinessPlace = (OmOrgBusinessPartners) baseDao
								.findByPK(OmOrgBusinessPartners.class, userTo.getBpId());

						umOrgUsers.setOmOrganizations(omOrganizations);
						umOrgUsers.setUmUsers(umUser);
						umOrgUsers.setUmOrgRoles(umOrgRoles);
						umOrgUsers.setMarkAsDelete(false);
						umOrgUsers.setOmOrgBusinessPartners(omOrgBusinessPlace);

						try {

							baseDao.saveOrUpdate(umOrgUsers);

						} catch (Exception e) {
							logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("Error while updating business partner user: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isUpdated;
	}

	/**
	 * Rename the role for specific organization
	 * 
	 * @param roleId
	 * @param roleTo
	 * @param loggedUser
	 * @return
	 */
	@Override
	public boolean updateOrgRoleName(Integer roleId, AddRoleTo roleTo, Integer loggedUser) {

		String METHOD_NAME = "updateOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isUpdated = false;

		try {

			UmOrgRoles umOrgRoles = (UmOrgRoles) baseDao.findByPK(UmOrgRoles.class, roleId);
			umOrgRoles.setRoleDesc(roleTo.getRoleName());
			umOrgRoles.setChangedBy(loggedUser);
			umOrgRoles.setChangedTs(new Date());

			baseDao.update(umOrgRoles);

			isUpdated = true;

		} catch (Exception e) {
			logger.error("Error while updating organization role name : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isUpdated;
	}

	/**
	 * Update the role permissions
	 * 
	 * @param orgId
	 * @param rolesTo
	 * @param loggedUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateOrgRoleDetails(String orgId, RolesTo rolesTo, Integer loggedUser) {

		String METHOD_NAME = "updateOrgRoleDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isUpdated = false;

		try {

			UmOrgRolesPermissions umOrgRolesPermission = null;

			Integer roleId = rolesTo.getRoleId();

			List<ParentObjectTo> accessObjs = rolesTo.getAccessObjs();

			for (ParentObjectTo parentObjs : accessObjs) {

				List<ChildOjectTo> childObjs = parentObjs.getChildObjs();

				for (ChildOjectTo childObj : childObjs) {

					GdAccessObjects gdAccessObjects = (GdAccessObjects) baseDao.findByPK(GdAccessObjects.class,
							childObj.getObjId());

					List<UmOrgRolesPermissions> umOrgRolesPermissions = (List<UmOrgRolesPermissions>) baseDao
							.findByHQLQueryWithIndexedParams(prop.getRequiredProperty("UM_ORG_ROLE_PERMISSIONS"),
									new Object[] { roleId, gdAccessObjects });

					if (umOrgRolesPermissions != null && !umOrgRolesPermissions.isEmpty()) {

						umOrgRolesPermission = umOrgRolesPermissions.get(0);
						umOrgRolesPermission.setChangedBy(loggedUser);
						umOrgRolesPermission.setChangedTs(new Date());

					} else {

						umOrgRolesPermission = new UmOrgRolesPermissions();
						umOrgRolesPermission.setCreatedBy(loggedUser);
						umOrgRolesPermission.setCreatedTs(new Date());
					}

					UmOrgRoles umOrgRoles = new UmOrgRoles();

					umOrgRoles.setRoleId(roleId);
					umOrgRolesPermission.setUmOrgRoles(umOrgRoles);
					umOrgRolesPermission.setGdAccessObjects(gdAccessObjects);
					if (childObj.getPermissionLevel() != null) {
						umOrgRolesPermission.setPermissionLevel(childObj.getPermissionLevel() == 1 ? true : false);
					}
					if (childObj.getReadPermission() != null) {
						umOrgRolesPermission.setReadPermission(childObj.getReadPermission() == 1 ? true : false);
					}
					if (childObj.getWritePermission() != null) {
						umOrgRolesPermission.setWritePermission(childObj.getWritePermission() == 1 ? true : false);
					}

					umOrgRolesPermission.setMarkAsDelete(false);

					baseDao.saveOrUpdate(umOrgRolesPermission);
				}
				
				GdAccessObjects gdAccessObjects = (GdAccessObjects) baseDao.findByPK(GdAccessObjects.class,
						parentObjs.getParentObjId());
				List<UmOrgRolesPermissions> umOrgRolesPermissions = (List<UmOrgRolesPermissions>) baseDao
						.findByHQLQueryWithIndexedParams(prop.getRequiredProperty("UM_ORG_ROLE_PERMISSIONS"),
								new Object[] { roleId, gdAccessObjects });

				if (umOrgRolesPermissions != null && !umOrgRolesPermissions.isEmpty()) {
					umOrgRolesPermission = umOrgRolesPermissions.get(0);
					umOrgRolesPermission.setChangedBy(loggedUser);
					umOrgRolesPermission.setChangedTs(new Date());
				} else {
					umOrgRolesPermission = new UmOrgRolesPermissions();
					umOrgRolesPermission.setCreatedBy(loggedUser);
					umOrgRolesPermission.setCreatedTs(new Date());
				}
				UmOrgRoles umOrgRoles = new UmOrgRoles();
				umOrgRoles.setRoleId(roleId);
				umOrgRolesPermission.setUmOrgRoles(umOrgRoles);
				umOrgRolesPermission.setGdAccessObjects(gdAccessObjects);
				if (parentObjs.getPermissionLevel() != null) {
					umOrgRolesPermission.setPermissionLevel(parentObjs.getPermissionLevel() == 1 ? true : false);
				}
				if (parentObjs.getReadPermission() != null) {
					umOrgRolesPermission.setReadPermission(parentObjs.getReadPermission() == 1 ? true : false);
				}
				if (parentObjs.getWritePermission() != null) {
					umOrgRolesPermission.setWritePermission(parentObjs.getWritePermission() == 1 ? true : false);
				}
				umOrgRolesPermission.setMarkAsDelete(false);
				baseDao.saveOrUpdate(umOrgRolesPermission);
				
			}

			isUpdated = true;

		} catch (Exception e) {
			logger.error("Error while updating organization role details : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isUpdated;
	}

	/**
	 * Get Organizations
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getportalOrganization() {

		String METHOD_NAME = "getportalOrganization";

		logger.info("Entered into the method: " + METHOD_NAME);

		String rms = null;

		try {

			List<OmOrganizations> organizations = (List<OmOrganizations>) omOrganizationsRepo.findAll();

			if (!organizations.isEmpty()) {

				rms = organizations.get(0).getOrgId();
			}

		} catch (Exception e) {
			logger.error("Error while getting organization details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return rms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessPartnerTo> getOrgPlants(String orgId, String all_bp) {

		String METHOD_NAME = "getOrgPlants";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<BusinessPartnerTo> bpToList = new ArrayList<BusinessPartnerTo>();

		try {

			BusinessPartnerTo businessPartner = null;

			String hqlQuery = null;
			List<OmOrgBusinessPartners> plants = null;
			if (all_bp == null) {
				hqlQuery = prop.getRequiredProperty("ORG_PLANT_DETAILS");
				plants = (List<OmOrgBusinessPartners>) baseDao.findByHQLQueryWithIndexedParams(hqlQuery,
						new Object[] { orgId });
			} else {
				hqlQuery = prop.getRequiredProperty("GET_ORG_PLANT_DETAILS");
				plants = (List<OmOrgBusinessPartners>) baseDao.findByHQLQueryWithIndexedParams(hqlQuery,
						new Object[] { orgId });
			}

			for (OmOrgBusinessPartners orgBranch : plants) {

				businessPartner = getBusinessPartnerTo(orgBranch);

				if (businessPartner != null) {

					bpToList.add(businessPartner);
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting plant details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return bpToList;
	}
	
	@Override
	public boolean checkIsExistingOrgUser(String org_id, String opu_id, String emailId) {
		String METHOD_NAME = "checkIsExistingOrgUser";
		logger.info("Entered into the method: " + METHOD_NAME);
		boolean isExistingUser = false;
		try {
			UserTo user = userDao.findByLogonId(emailId);
			if (user != null) {
				List<UmOrgUsers> umOrgUserList = umOrgUsersRepository.getOrgUsersByUserOrgId(user.getUserId(), org_id, opu_id);
				isExistingUser = !umOrgUserList.isEmpty() ? true : false;
			}
		} catch (Exception e) {
			logger.error("Error while getting plant details: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return isExistingUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserTo> getSSPUsers(String bpId, String orgId, String action, Integer loggedUser,String type,String activeOrDeActive) {

		String METHOD_NAME = "getSSPUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<UserTo> userToList = null;

		try {

			List<UmOrgUsers> umOrgUsers = null;

			if (GeneralConstants.BP_ACTION.equalsIgnoreCase(action)) {

				umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
						prop.getProperty("x"),
						new Object[] { bpId, loggedUser, GeneralConstants.ROLE_BP_ADMIN });

			} else if (GeneralConstants.ORG_ACTION.equalsIgnoreCase(action)) {

				umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
						prop.getProperty("GET_USERS_BY_ORGID"),
						new Object[] { orgId, loggedUser, GeneralConstants.ROLE_ORG_ADMIN });
			} else if (GeneralConstants.ALL_ACTION.equalsIgnoreCase(action)) {
				String usersQuery= prop.getProperty("GET_ALL_USERS");
				if("true".equalsIgnoreCase(activeOrDeActive)) {
					usersQuery=usersQuery +" um.umUsers.isSspActive= false and um.umUsers.status is null and um.umOrgRoles.userTypeId = 4  order by um.umUsers.createdTs desc";
					umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
							usersQuery,
							new Object[] { orgId });

				}else if ("false".equalsIgnoreCase(activeOrDeActive)) {
					usersQuery=usersQuery +" um.umUsers.isSspActive is not null and um.umUsers.status is not null and um.umOrgRoles.userTypeId = 4  order by um.umUsers.firstName";
					umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
							usersQuery,
							new Object[] { orgId });
				}
				else {
					if(type==null) {
						usersQuery=usersQuery +" um.umUsers.isDeactivated= false order by um.umUsers.firstName";	
						umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
								usersQuery,
								new Object[] { orgId});
					}else {
						usersQuery=usersQuery +" um.umUsers.isDeactivated= false and um.umOrgRoles.roleId = ?2 order by um.umUsers.firstName";	
						umOrgUsers = (List<UmOrgUsers>) baseDao.findByHQLQueryWithIndexedParams(
								usersQuery,
								new Object[] { orgId, Integer.parseInt(type) });
					}
					

				}
			}
			
			if (umOrgUsers != null && !umOrgUsers.isEmpty()) {
				
				userToList = new ArrayList<UserTo>();

				for (UmOrgUsers umOrgUser : umOrgUsers) {

					UserTo orgUser = new UserTo();

					UmUsers umUsers = umOrgUser.getUmUsers();

					OmOrgBusinessPartners omOrgBusinessPlaces = umOrgUser.getOmOrgBusinessPartners();

					UmOrgRoles umOrgRoles = umOrgUser.getUmOrgRoles();
					List<Attachments> allAttachmentsOnUserId = attachmentsRepo.getAllAttachmentsOnUserId(umUsers.getUserId());
					if(allAttachmentsOnUserId != null && !allAttachmentsOnUserId.isEmpty()) {
						for(Attachments attachments : allAttachmentsOnUserId) {
							if(attachments.getAttachName().startsWith("AADHAR")) {
								orgUser.setAadharAttachmentName(attachments.getAttachName());
								orgUser.setAadharAttachment(attachments.getAttachId());
							}
							if(attachments.getAttachName().startsWith("PAN")) {
								orgUser.setPanAttachmentName(attachments.getAttachName());
								orgUser.setPanAttachments(attachments.getAttachId());
							}
							if(attachments.getAttachName().startsWith("OTHER")) {
								orgUser.setOtherAttachmentName(attachments.getAttachName());
								orgUser.setAttachmentId(attachments.getAttachId());
							}
							if(attachments.getAttachName().startsWith("sig")) {
								orgUser.setSignatureName(attachments.getAttachName());
								orgUser.setSignature(attachments.getAttachId());
							}
							if(attachments.getAttachName().startsWith("GST")) {
								orgUser.setGstAttachment(attachments.getAttachId());
								orgUser.setGstAttachmentName(attachments.getAttachName());
							}
						}
					}
					orgUser.setUserId(umUsers.getUserId());
					orgUser.setLogonId(umUsers.getLogonId());
					orgUser.setEmail(umUsers.getEmail());
					orgUser.setFirstName(umUsers.getFirstName());
					orgUser.setLastName(umUsers.getLastName());
					orgUser.setMiddleName(umUsers.getMiddleName());
					orgUser.setUserLocked(umUsers.getUserLocked());
					orgUser.setMobile(umUsers.getMobile());
					orgUser.setBpId(omOrgBusinessPlaces.getOrgBpId());
					orgUser.setAddress(umUsers.getAddress());
					orgUser.setDeactivated(umUsers.isDeactivated());
					orgUser.setIsSspActive(umUsers.getIsSspActive());
					orgUser.setRegion(umUsers.getRegion());
					orgUser.setRegCenter(umUsers.getRegCenter());
					orgUser.setCountry(umUsers.getCountry());
					orgUser.setEmailIds(umUsers.getEmailIds());
					orgUser.setUserTypeId(umUsers.getGdUserTypes().getUserTypeId());
					orgUser.setTypeDesc(umUsers.getGdUserTypes().getTypeDesc());
					orgUser.setTypeShortId(umUsers.getGdUserTypes().getTypeShortId());
					orgUser.setUserFullName(umUsers.getFirstName());
					orgUser.setAddress(umUsers.getHouseNo()+", "+umUsers.getAddress()+", "+umUsers.getPinCode());
					orgUser.setCreatedBy(umUsers.getCreatedBy() != null ? umUsers.getCreatedBy():null);
					UmUsers approverEmails = umUsersRepository.getApproverEmails(umUsers.getCreatedBy());
					orgUser.setCreatedByName(approverEmails.getFirstName()+" "+(approverEmails.getLastName()!= null ? approverEmails.getLastName():""));
					orgUser.setCreatedEmpCode(approverEmails.getEmpCode());
					orgUser.setExecutiveMobile(approverEmails.getMobile());
					Integer bookingoffice = Integer.parseInt(umUsers.getBookingOffice());
					BookingUnits bookingUnitById = bookingUnitsRepo.getBookingUnitById(bookingoffice);
					orgUser.setCityDesc(bookingUnitById.getBookingLocation());
					orgUser.setCity(umUsers.getBookingOffice());
					orgUser.setEmpCode(umUsers.getEmpCode());
					orgUser.setBankName(umUsers.getBankName() != null ? umUsers.getBankName() : null );
					orgUser.setBranch(umUsers.getBranchName() != null ? umUsers.getBranchName():null);
					orgUser.setIfsc(umUsers.getIfscCode() != null ? umUsers.getIfscCode() : null);
					orgUser.setPanNumber(umUsers.getPan() != null ? umUsers.getPan() : null);
					orgUser.setAadharNumber(umUsers.getAadhar() != null ? umUsers.getAadhar() : null);
					orgUser.setHolderName(umUsers.getAccountHolderName() != null ? umUsers.getAccountHolderName() : null);
					orgUser.setGstNo(umUsers.getGstNo() != null ? umUsers.getGstNo() : null);
					if(umUsers.getIsRmsApprover() != null) {
						orgUser.setIsRmsApprover(umUsers.getIsRmsApprover());
					}
					if(umUsers.getApprovedBy() != null) {
						UmUsers approverEmails2 = umUsersRepository.getApproverEmails(umUsers.getApprovedBy());
						orgUser.setApprovedBy(approverEmails2.getFirstName());
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
						String formattedDate = formatter.format(umUsers.getApprovedTime());						 	
						orgUser.setApprovedTime(formattedDate);
					}
					
					orgUser.setState(umUsers.getState());
					if(umUsers.getStatus() != null) {
						orgUser.setStatus(umUsers.getStatus());
					}
					if(umUsers.getMiddleName() != null && umUsers.getLastName() != null && umUsers.getMiddleName().isEmpty() && !umUsers.getLastName().isEmpty())
						orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getMiddleName()+" "+umUsers.getLastName());
					else if(umUsers.getMiddleName() != null && !umUsers.getMiddleName().isEmpty())
						orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getMiddleName());
					else if(umUsers.getLastName() != null && !umUsers.getLastName().isEmpty())
						orgUser.setUserFullName(umUsers.getFirstName()+" "+umUsers.getLastName());
					
					if(umOrgUser.getSecondaryRoles() != null && !umOrgUser.getSecondaryRoles().isEmpty()){
					List<String> sIds = new ArrayList<String>(Arrays.asList(umOrgUser.getSecondaryRoles().split(",")));
					orgUser.setSecondaryRoles(sIds);
					}
					
					if (umOrgRoles != null) {
						orgUser.setRoleType(umOrgRoles.getRoleType());
						orgUser.setRole(umOrgRoles.getRoleDesc());
						orgUser.setRoleId(umOrgRoles.getRoleId().toString());
						orgUser.setRoleShortId(umOrgRoles.getRoleShortId());
					}
					
					userToList.add(orgUser);
				}
			}

		} catch (Exception e) {
			logger.error("Error while finding user detais by user id: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return userToList;
	}

}