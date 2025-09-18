package com.portal.gd.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.basedao.IBaseDao;
import com.portal.constants.MasterDataSortingColumns;
import com.portal.da.entities.DaSizes;
import com.portal.gd.entities.BookingUnits;
import com.portal.gd.entities.GdAccessObjects;
import com.portal.gd.entities.GdCity;
import com.portal.gd.entities.GdConfigGroup;
import com.portal.gd.entities.GdConfigValues;
import com.portal.gd.entities.GdDaClassifiedCategory;
import com.portal.gd.entities.GdSettingsDefinitions;
import com.portal.gd.entities.GdState;
import com.portal.gd.to.GdSettingConfigsTo;
import com.portal.gd.to.GlobalDataTo;
import com.portal.gd.to.ListOfConfigGroupTo;
import com.portal.gd.to.ListOfConfigValuesTo;
import com.portal.org.to.ChildOjectTo;
import com.portal.org.to.ParentObjectTo;
import com.portal.repository.DaSizeRepo;
import com.portal.repository.GdDaClassifiedCategoryRepo;
import com.portal.security.model.LoggedUser;

/**
 * Global data DAO implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service("globalDataDao")
@Transactional
@PropertySource(value = { "classpath:/com/portal/queries/global_data_db.properties" })
public class GlobalDataDaoImpl implements GlobalDataDao {

	private static final Logger logger = LogManager.getLogger(GlobalDataDaoImpl.class);

	@Autowired(required = true)
	private IBaseDao baseDao;

	@Autowired(required = true)
	private Environment prop;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private GdDaClassifiedCategoryRepo categoryRepo;
	
	@Autowired
	private DaSizeRepo daSizeRepo;

	/**
	 * Get List object values
	 * 
	 * @param objName
	 * @param objId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getListObjValues(String objName, String objId) {

		String METHOD_NAME = "getListObjValues";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<Object[]> list = null;

		try {

			String table = null, id = null, value = null, qurey = null, val = null;

			if (prop.containsProperty(objName)) {

				table = prop.getRequiredProperty(objName);
				id = prop.getRequiredProperty(objName + "_id");
				value = prop.getRequiredProperty(objName + "_value");
			}

			String dFilter = prop.getRequiredProperty("default_filter");

			if (objId != null && !objId.trim().isEmpty()) {

				String filter = null;

				if (prop.containsProperty(objName + "_filter")) {

					filter = prop.getRequiredProperty(objName + "_filter");

					try {

						Integer.parseInt(objId);

						if ("sap_states".equalsIgnoreCase(objName)) {
							val = "state_id";
							qurey = "select " + id + ", " + value + ", " + val + " from " + table + " where " + filter
									+ " = " + objId + " and " + dFilter + "=0";
						} else if ("vmParty".equalsIgnoreCase(objName)) {
							qurey = "select " + id + ", " + value + " from " + table + " where " + filter + " = "
									+ objId + " and " + dFilter + "=0";
						} else {
							qurey = "select " + id + ", " + value + " from " + table + " where " + filter + " like '%"
									+ objId + "%' and " + dFilter + "=0";
						}

					} catch (Exception e) {

						qurey = "select " + id + ", " + value + " from " + table + " where " + filter + " = '" + objId
								+ "' and " + dFilter + "=0";
					}
				}

			} else if (table != null && !table.trim().isEmpty()) {

				qurey = "select " + id + ", " + value + " from " + table + " where " + dFilter + "=0";
			}

			if (qurey != null) {

				list = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(qurey);
			}

		} catch (Exception e) {
			logger.error("Error while getting list object values: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return list;
	}

	/**
	 * Get the left side menu of application
	 * 
	 * @param globalDataTo
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public GlobalDataTo getAccessObjects2(GlobalDataTo globalDataTo) {
	 * 
	 * String METHOD_NAME = "getAccessObjects";
	 * 
	 * logger.info("Entered into the method: " + METHOD_NAME);
	 * 
	 * try {
	 * 
	 * List<GdAccessObjects> gdAccessObjects = new ArrayList<GdAccessObjects>();
	 * 
	 * List<UmOrgRoles> umOrgRoles = (List<UmOrgRoles>)
	 * baseDao.findByHQLQueryWithIndexedParams(
	 * prop.getRequiredProperty("USER_ROLE_ACCESS"), new Object[] {
	 * globalDataTo.getOrgId(), globalDataTo.getLoggedUser(), globalDataTo.getBpId()
	 * });
	 * 
	 * if (!umOrgRoles.isEmpty()) {
	 * 
	 * globalDataTo.setRoleName(umOrgRoles.get(0).getRoleDesc());
	 * 
	 * List<Object[]> umOrgRolesPermissions = (List<Object[]>)
	 * baseDao.findByHQLQueryWithIndexedParams(
	 * prop.getRequiredProperty("ROLE_PERMISSIONS"), new Object[] {
	 * umOrgRoles.get(0).getRoleId(), false });
	 * 
	 * String hqlQuery = "from GdAccessObjects gd where gd.markAsDelete =?";
	 * 
	 * Integer deviceId = globalDataTo.getDeviceId();
	 * 
	 * if (deviceId != null && deviceId != 0) {
	 * 
	 * switch (deviceId) {
	 * 
	 * case GeneralConstants.MOB_IOS_APP:
	 * 
	 * hqlQuery = hqlQuery + " and gd.mobileIos=?"; break;
	 * 
	 * case GeneralConstants.MOB_ANDRIOD_APP:
	 * 
	 * hqlQuery = hqlQuery + " and gd.mobileAndroid=?"; break;
	 * 
	 * case GeneralConstants.TAB_ANDRIOD_APP:
	 * 
	 * hqlQuery = hqlQuery + " and gd.tabletAndroid=?"; break;
	 * 
	 * case GeneralConstants.TAB_IOS_APP:
	 * 
	 * hqlQuery = hqlQuery + " and gd.tabletIos=?"; break; }
	 * 
	 * gdAccessObjects = (List<GdAccessObjects>)
	 * baseDao.findByHQLQueryWithIndexedParams(hqlQuery, new Object[] { false, true
	 * });
	 * 
	 * } else {
	 * 
	 * gdAccessObjects = (List<GdAccessObjects>)
	 * baseDao.findByHQLQueryWithIndexedParams(hqlQuery, new Object[] { false }); }
	 * 
	 * List<String> finalAccessObjs = new ArrayList<String>(); List<ParentObjectTo>
	 * finalAccessObjsPerm = new ArrayList<ParentObjectTo>();
	 * 
	 * for (Object[] str : umOrgRolesPermissions) {
	 * 
	 * if (str[0] != null) finalAccessObjs.add((String) str[0]); if (str[1] != null)
	 * finalAccessObjs.add((String) str[1]); }
	 * 
	 * globalDataTo.setFinalAccessObjs(finalAccessObjs);
	 * globalDataTo.setGdAccessObjects(gdAccessObjects); }
	 * 
	 * } catch (Exception e) { logger.error("Error while getting access objects: " +
	 * ExceptionUtils.getStackTrace(e)); }
	 * 
	 * logger.info("Exit from the method: " + METHOD_NAME);
	 * 
	 * return globalDataTo; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<ParentObjectTo> getAccessObjects(GlobalDataTo globalDataTo) {

		List<ParentObjectTo> parentObjects = new ArrayList<ParentObjectTo>();
		
		List<Integer> roleId = new ArrayList<>();
		int id = globalDataTo.getRoleId();
		roleId.add(id);
		if(globalDataTo.getSecondaryRoles() != null && !globalDataTo.getSecondaryRoles().isEmpty()
				&& globalDataTo.getSecondaryRoles().size() != 0){
			for(String sid : globalDataTo.getSecondaryRoles()){
				if(sid.length() != 0){
					roleId.add(Integer.parseInt(sid));
				}
			}
		}

		String hqlQuery = prop.getRequiredProperty("GET_ACCESS_PERMISSIONS");

		List<Object[]> objects = (List<Object[]>) baseDao.findByHQLQueryWithIndexedParams(hqlQuery,
				new Object[] { roleId, false });

		Map<String, Integer> accessLevelMap = new HashMap<String, Integer>();
		Map<String, Integer> readaccessLevelMap = new HashMap<String, Integer>();
		Map<String, Integer> writeaccessLevelMap = new HashMap<String, Integer>();
		String roleDesc = "";
		String roleType = "";
		List<Object> pobjs = new ArrayList<Object>();
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
			roleType = obj[6].toString();
			roleDesc = obj[6].toString();
			if (obj[1] == null) {
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

				parentObj.setMenuIcon(objs.getMenuIcon());
				parentObj.setSeqNum(objs.getSeqNo());
				parentObj.setNavLink(objs.getNavLink());
				parentObj.setParentObjId(accessObjId);
				parentObj.setParentObjName(accessObjDesc);

				parentObjs.put(accessObjId, parentObj);

			} else {
				if (null != permissionLevel && permissionLevel == 1) {
					ChildOjectTo childObj = new ChildOjectTo();
					childObj.setObjId(accessObjId);
					childObj.setObjName(accessObjDesc);
					childObj.setMenuIcon(objs.getMenuIcon());
					childObj.setSeqNum(objs.getSeqNo());
					childObj.setNavLink(objs.getNavLink());
					childObj.setPermissionLevel(permissionLevel);
					childObj.setReadPermission(readPermission);
					childObj.setWritePermission(writePermission);
					Map<String, Object> params = new HashMap<>();
					params.put("accessObjId", accessObjId);

					childObj.setRoleType(roleType);
					childObj.setRoleDesc(roleDesc);

					if (childObjs.get(parentObjId) != null) {

						List<ChildOjectTo> childObjsList = childObjs.get(parentObjId);
						childObjsList.add(childObj);
						Comparator<ChildOjectTo> compareById = (ChildOjectTo o1, ChildOjectTo o2) -> o1.getSeqNum()
								.compareTo(o2.getSeqNum());

						Collections.sort(childObjsList, compareById);
						childObjs.put(parentObjId, childObjsList);

					} else {

						List<ChildOjectTo> childObjsList = new ArrayList<ChildOjectTo>();
						childObjsList.add(childObj);
						Comparator<ChildOjectTo> compareById = (ChildOjectTo o1, ChildOjectTo o2) -> o1.getSeqNum()
								.compareTo(o2.getSeqNum());

						Collections.sort(childObjsList, compareById);
						childObjs.put(parentObjId, childObjsList);
					}
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
				parentObjects.add(obj.getValue());
			} else {
				// obj.getValue().setPermissionLeve	l(0);
				// obj.getValue().setReadPermission(0);
				// obj.getValue().setWritePermission(0);
				if (pobjs.contains(obj.getKey())) {
					obj.getValue().setPermissionLevel(1);
					obj.getValue().setReadPermission(1);
					obj.getValue().setWritePermission(1);
					parentObjects.add(obj.getValue());
				}
			}
		}

		return parentObjects;
	}

	/**
	 * Get Configuration values
	 * 
	 * @param orgId
	 * @param bpId
	 * @param group
	 * @param parentGroup
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ListOfConfigValuesTo> getConfigValues(ListOfConfigValuesTo configValTo) {

		String METHOD_NAME = "getConfigValues";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<ListOfConfigValuesTo> list = null;

		int ttlCnt = 0;

		try {

			List<Object[]> gdConfigValues = null;

			String query = "Select distinct gd.configGroupId ,gd.parentValId ,gd.key ,gd.value ,gd.addField1 ,gd.addField2 ,gd.addField3 "
					+ ",gd.addField4  ";

			if (configValTo.getPgNum() != null && configValTo.getPgNum() == -1)
				query = query + " ,gd.orgBpId ,configValId ";

			query = query + "from GdConfigValues gd where gd.markAsDelete = false";

			if (configValTo.getOrgId() != null && !configValTo.getOrgId().trim().isEmpty()) {
				query = query + " and gd.orgId = '" + configValTo.getOrgId() + "'";
			}

			if (configValTo.getBpId() != null && !configValTo.getBpId().trim().isEmpty()) {
				query = query + " and gd.orgBpId = '" + configValTo.getBpId() + "'";
			}

			if (configValTo.getConfigGroupId() != null && !configValTo.getConfigGroupId().trim().isEmpty()) {
				query = query + " and gd.configGroupId = '" + configValTo.getConfigGroupId() + "'";
			}

			if (configValTo.getParentValId() != null && !configValTo.getParentValId().trim().isEmpty()) {
				query = query + " and gd.parentValId = '" + configValTo.getParentValId() + "'";
			}
			if (configValTo.getAddField3() != null && !configValTo.getAddField3().trim().isEmpty()) {
				query = query + " and gd.addField3 = '" + configValTo.getAddField3() + "'";

			}
			if (configValTo.getAddField7() != null && !configValTo.getAddField7().trim().isEmpty()) {
				query = query + " and gd.addField7 = '" + configValTo.getAddField7() + "'";

			}
			if (configValTo.getAddField2() != null && !configValTo.getAddField2().trim().isEmpty()) {
				query = query + " and gd.addField2 = '" + configValTo.getAddField2() + "'";

			}
			if (configValTo.getMultiBpIds() != null && !configValTo.getMultiBpIds().trim().isEmpty()) {
				query = query + " and gd.orgBpId in (" + configValTo.getMultiBpIds() + ")";
			}
			if (configValTo.getAddField1() != null && !configValTo.getAddField1().trim().isEmpty()) {
				query = query + " and gd.addField1 ='" + configValTo.getAddField1() + "'";
			}

			if (configValTo.getPgNum() != null && configValTo.getPgSize() != null) {

				List<GdConfigValues> configValuesList = (List<GdConfigValues>) baseDao
						.findByHQLQueryWithoutParams(query);

				ttlCnt = configValuesList.size();

				if (ttlCnt > 0)
					gdConfigValues = (List<Object[]>) baseDao.findByHQLQueryWithoutParamsAndLimits(query,
							configValTo.getPgSize(), configValTo.getPgNum());

			} else {

				gdConfigValues = (List<Object[]>) baseDao.findByHQLQueryWithoutParams(query);
			}

			if (!gdConfigValues.isEmpty()) {

				list = new ArrayList<ListOfConfigValuesTo>();

				for (Object[] gcv : gdConfigValues) {

					ListOfConfigValuesTo configValuesTo = new ListOfConfigValuesTo();

					configValuesTo.setConfigGroupId(gcv[0].toString());
					configValuesTo.setParentValId(gcv[1] == null ? "" : gcv[1].toString());
					configValuesTo.setKey(gcv[2] == null ? "" : gcv[2].toString());
					configValuesTo.setValue(gcv[3] == null ? "" : gcv[3].toString());
					configValuesTo.setAddField1(gcv[4] == null ? "" : gcv[4].toString());
					configValuesTo.setAddField2(gcv[5] == null ? "" : gcv[5].toString());
					configValuesTo.setAddField3(gcv[6] == null ? "" : gcv[6].toString());
					configValuesTo.setAddField4(gcv[7] == null ? "" : gcv[7].toString());

					if (configValTo.getPgNum() != null && configValTo.getPgNum() == -1) {
						configValuesTo.setBpId(gcv[14] == null ? "" : gcv[14].toString());
						if (gcv[15] != null)
							configValuesTo.setConfigValId((int) gcv[15]);
					}
					configValuesTo.setTtlCnt(ttlCnt);
					list.add(configValuesTo);
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting list object values: " + ExceptionUtils.getStackTrace(e));e.printStackTrace();
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return list;
	}

	/**
	 * Get Configuration Group Details
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ListOfConfigGroupTo> getConfigGroup() {

		String METHOD_NAME = "getConfigGroup";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<ListOfConfigGroupTo> listOfConfigGroupTos = null;

		try {

			List<GdConfigGroup> gdConfigGroups = (List<GdConfigGroup>) baseDao
					.findByHQLQueryWithoutParams(prop.getRequiredProperty("GET_CONF_GROUP"));

			if (!gdConfigGroups.isEmpty()) {

				listOfConfigGroupTos = new ArrayList<ListOfConfigGroupTo>();

				for (GdConfigGroup gcg : gdConfigGroups) {

					ListOfConfigGroupTo lcgTo = new ListOfConfigGroupTo();

					BeanUtils.copyProperties(gcg, lcgTo);

					listOfConfigGroupTos.add(lcgTo);
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting Config Group details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return listOfConfigGroupTos;
	}

	/**
	 * Create or Update Configuration Values
	 * 
	 * @param listOfConfVals
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public String createOrUpdateConfigValues(List<ListOfConfigValuesTo> listOfConfVals, Integer loggedUser) {

		String METHOD_NAME = "createOrUpdateConfigValues";

		logger.info("Entered into the method: " + METHOD_NAME);

		String retMessage = null;

		try {

			if (listOfConfVals != null) {
				for (ListOfConfigValuesTo lcvt : listOfConfVals) {
					GdConfigValues gdconfValues = null;
					if (lcvt.getConfigValId() != null && lcvt.getConfigValId() != 0) {

						gdconfValues = (GdConfigValues) baseDao.findByPK(GdConfigValues.class, lcvt.getConfigValId());

						if (gdconfValues != null) {
							BeanUtils.copyProperties(lcvt, gdconfValues);
							gdconfValues.setChangedBy(loggedUser);
							gdconfValues.setChangedTs(new Date());
							retMessage = "SUCCESS_UPDATE";
						}
					}

					if (gdconfValues == null) {
						gdconfValues = new GdConfigValues();
						BeanUtils.copyProperties(lcvt, gdconfValues);
						gdconfValues.setCreatedBy(loggedUser);
						gdconfValues.setCreatedTs(new Date());
						retMessage = "SUCCESS_CREATE";
					}

					if (gdconfValues != null) {
						baseDao.saveOrUpdate(gdconfValues);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Error while create or update config values: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);

		return retMessage;
	}

	/**
	 * Create or Update Configuration Group
	 * 
	 * @param listOfConfGroups
	 * @return
	 */
	public String createOrUpdateConfigGroup(List<ListOfConfigGroupTo> listOfConfGroups, Integer loggedUser) {

		String METHOD_NAME = "createOrUpdateConfigGroup";

		logger.info("Entered into the method: " + METHOD_NAME);

		String retrunMsg = null;

		try {

			for (ListOfConfigGroupTo lcvt : listOfConfGroups) {

				GdConfigGroup gdconfGroups = null;

				if (lcvt.getGroupName() != null && !lcvt.getGroupName().trim().isEmpty()) {

					gdconfGroups = (GdConfigGroup) baseDao.findByPK(GdConfigGroup.class, lcvt.getGroupName());

					if (gdconfGroups != null) {

						BeanUtils.copyProperties(lcvt, gdconfGroups);

						gdconfGroups.setChangedBy(loggedUser);
						gdconfGroups.setChangedTs(new Date());

						retrunMsg = "SUCCESS_UPDATE";

					} else {

						gdconfGroups = new GdConfigGroup();

						BeanUtils.copyProperties(lcvt, gdconfGroups);

						gdconfGroups.setManageble(false);
						gdconfGroups.setCreatedBy(loggedUser);
						gdconfGroups.setCreatedTs(new Date());

						retrunMsg = "SUCCESS_CREATE";
					}

					baseDao.saveOrUpdate(gdconfGroups);
				}
			}

		} catch (Exception e) {
			logger.error("Error while create or update config group details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return retrunMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getTablelist(String tableName, String filterKey, String filterValue,
			boolean isMapping) {

		String METHOD_NAME = "getTablelist";
		logger.info("Entered into the method: " + METHOD_NAME);
		List<Map<String, Object>> list = null;
		try {
			String query = "";
			if (tableName != null) {
				String[] ver = tableName.split("_");
				List<String> strings = Arrays.asList(ver);
				if (isMapping) {

					if ("GD_RC_MAPPING".equalsIgnoreCase(tableName))
						query = "select RC_ID from " + tableName + " where " + filterKey + "='" + filterValue
								+ "' and MARK_AS_DELETE=0";

					if ("GD_COUNTRY_MAPPING".equalsIgnoreCase(tableName))
						query = "select COUNTRY_ID from " + tableName + " where " + filterKey + " in(" + filterValue
								+ ") and MARK_AS_DELETE=0";

					List<Object> mappingId = (List<Object>) baseDao.findBySQLQueryWithoutParams(query);
					String ids = "";
					for (Object obj : mappingId) {
						if (ids.isEmpty()) {
							ids = obj + "";
						} else {
							ids = ids + "," + obj + "";
						}
					}

					if ("GD_RC_MAPPING".equalsIgnoreCase(tableName))
						query = "select * from GD_REGIONCENTER where RCM_ID in ( " + ids + " ) and MARK_AS_DELETE=0";

					if ("GD_COUNTRY_MAPPING".equalsIgnoreCase(tableName))
						query = "select * from GD_COUNTRIES where COUNTRY_ID in ( " + ids + " ) and MARK_AS_DELETE=0";

				} else {
					query = "select * from " + tableName;
					if (filterKey != null && filterValue != null) {
						query = query + " where " + filterKey + "='" + filterValue + "' and MARK_AS_DELETE=0";
					} else {
						if("GD_REGIONCENTER".equalsIgnoreCase(tableName))
							query = query + " where MARK_AS_DELETE=0 order by REGIONCENTER_NAME";
						else							
						query = query + " where MARK_AS_DELETE=0";
					}
				}

				//list = (List<Map<String, Object>>) baseDao.findByNativeSQLQueryByMap(query, new Object[] {}); need to change sathish 20 may 2023
			}

		} catch (Exception e) {
			logger.error("Error while get details: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);

		return list;
	}

	@Override
	public List<ListOfConfigValuesTo> getConfigValuesWithFilter(String orgId, String bpId, String key, String value,
			String group, String parentGroup, String addField1, String addField2, String addField3, String addField4,
			String addField5, String addField6, String addField7, String addField8) {

		String METHOD_NAME = "getConfigValuesWithFilter";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<ListOfConfigValuesTo> list = null;

		try {

			List<GdConfigValues> gdConfigValues = null;

			String query = "from GdConfigValues where markAsDelete = 0";

			if (orgId != null && !orgId.trim().isEmpty()) {
				query = query + " and orgId = '" + orgId + "'";
			}

			if (bpId != null && !bpId.trim().isEmpty()) {
				query = query + " and orgBpId = '" + bpId + "'";
			}
			if (key != null && !key.trim().isEmpty()) {
				query = query + " and key like '%" + key + "%'";

			}
			if (value != null && !value.trim().isEmpty()) {
				query = query + " and value = '" + value + "'";
			}
			if (group != null && !group.trim().isEmpty()) {
				query = query + " and configGroupId = '" + group + "'";
			}

			if (parentGroup != null && !parentGroup.trim().isEmpty()) {
				query = query + " and parentValId = '" + parentGroup + "'";
			}
			if (addField1 != null && !addField1.trim().isEmpty()) {
				query = query + " and addField1 = '" + addField1 + "'";

			}
			if (addField2 != null && !addField2.trim().isEmpty()) {
				query = query + " and addField2 = '" + addField2 + "'";

			}
			if (addField3 != null && !addField3.trim().isEmpty()) {
				query = query + " and addField3 like '%" + addField3 + "%'";

			}
			if (addField4 != null && !addField4.trim().isEmpty()) {
				query = query + " and addField4 like '%" + addField4 + "%'";

			}
			if (addField5 != null && !addField5.trim().isEmpty()) {
				query = query + " and addField5 = '" + addField5 + "'";

			}
			if (addField6 != null && !addField6.trim().isEmpty()) {
				query = query + " and addField6 = '" + addField6 + "'";

			}
			if (addField7 != null && !addField7.trim().isEmpty()) {
				query = query + " and addField7 like '%" + addField7 + "%'";

			}
			if (addField8 != null && !addField8.trim().isEmpty()) {
				query = query + " and addField8 like '%" + addField8 + "%'";

			}

			gdConfigValues = (List<GdConfigValues>) baseDao.findByHQLQueryWithoutParams(query);

			if (!gdConfigValues.isEmpty()) {

				list = new ArrayList<ListOfConfigValuesTo>();

				for (GdConfigValues gcv : gdConfigValues) {

					ListOfConfigValuesTo configValuesTo = new ListOfConfigValuesTo();

					BeanUtils.copyProperties(gcv, configValuesTo);
					list.add(configValuesTo);
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting list object values: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return list;

	}

	@Override
	public List<Map<String, Object>> getMasterData(String masterTypesArray, LoggedUser loggedUser, Map<String, Object> filter, String masterTypeEnum) {
		String METHOD_NAME = "getRegionalCenters";

		logger.info("Entered into the method: " + METHOD_NAME);
		// build filter query
		StringBuilder paramSb = new StringBuilder();
		String filterParams = "";
		if (filter != null) {
			for (Map.Entry<String, Object> param : filter.entrySet()) {
				if (paramSb.toString().isEmpty())
					paramSb.append(param.getKey() + "='" + param.getValue() + "'");
				else
					paramSb.append(" and " + param.getKey() + "='" + param.getValue() + "'");
			}
		}
		if (!paramSb.toString().isEmpty()) {
			filterParams = paramSb.toString() + " and ";
		}
		String orderBy = "";
		if(MasterDataSortingColumns.valueOf(masterTypeEnum) != null){
			orderBy = " "+MasterDataSortingColumns.valueOf(masterTypeEnum).getValue()+" asc";
		}
		Query query = entityManager.createNativeQuery("select * from "+masterTypesArray + " where "+filterParams+" mark_as_delete = false order by "+orderBy+" , created_ts asc ");
		if("gd_rms_schemes".equalsIgnoreCase(masterTypesArray)) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM ").append(masterTypesArray).append(" WHERE ");
			sql.append(filterParams).append(" mark_as_delete = false ");
			sql.append(" ORDER BY ");
			sql.append(" CASE ");
			sql.append("   WHEN scheme = '1 Pack' THEN 0 ");
			sql.append("   WHEN scheme = '1+1' THEN 1 ");
			sql.append("   ELSE 2 ");
			sql.append(" END, ");
			sql.append(" CASE WHEN scheme LIKE '%+%' THEN CAST(SPLIT_PART(scheme, '+', 1) AS INTEGER) ELSE NULL END, ");
			sql.append(" CASE WHEN scheme LIKE '%+%' THEN CAST(SPLIT_PART(scheme, '+', 2) AS INTEGER) ELSE NULL END");
			query = entityManager.createNativeQuery(sql.toString());
		}
		else if(loggedUser.getRegion() != null && loggedUser.getRegion() != 0 && "gd_dealer_master".equalsIgnoreCase(masterTypesArray)){
			query = entityManager.createNativeQuery("select gdm.dealer_id,gdm.dealer_name, gdm.mark_as_delete,gdm.created_by,gdm.created_ts,gdm.changed_by,gdm.changed_ts,gdm.status,gdm.description,gdm.region_id from "+masterTypesArray + " gdm inner join gd_dealer_locations gdl on gdl.dealer_id  = gdm.dealer_id  where gdm.status = true and gdl.status = true  and region_id = "+loggedUser.getRegion()+" and gdm.mark_as_delete = false group by gdm.dealer_id order by "+orderBy+" , gdm.created_ts asc ");
		} else if ("gd_dealer_master".equalsIgnoreCase(masterTypesArray)){
			query = entityManager.createNativeQuery("select gdm.dealer_id,gdm.dealer_name, gdm.mark_as_delete,gdm.created_by,gdm.created_ts,gdm.changed_by,gdm.changed_ts,gdm.status,gdm.description,gdm.region_id from "+masterTypesArray + " gdm inner join gd_dealer_locations gdl on gdl.dealer_id  = gdm.dealer_id  where gdm.status = true and gdl.status = true and gdm.mark_as_delete = false group by gdm.dealer_id order by "+orderBy+" ,gdm.created_ts asc ");
		} else if ("gd_settings_definitions".equalsIgnoreCase(masterTypesArray)){
			query = entityManager.createNativeQuery("select setting_group_name,group_description from "+masterTypesArray + " where mark_as_delete = false group by setting_group_name,group_description order by "+orderBy+" ,setting_group_name asc ");
		} else if ("gd_classified_editions".equalsIgnoreCase(masterTypesArray)){
			query = entityManager.createNativeQuery("select gce.edition_name,gce.erp_edition,gce.created_by,gce.created_ts,gce.changed_by,gce.changed_ts,gce.mark_as_delete,gce.id,gce.erp_ref_id,get.edition_type,gce.edition_type as gce_edition_id from "+masterTypesArray + " gce inner join gd_edition_type get on gce.edition_type = get.id where gce.mark_as_delete = false order by " +orderBy+"");
		}else if ("gd_classified_schemes".equalsIgnoreCase(masterTypesArray)){
			query = entityManager.createNativeQuery("select gcs.scheme,gcs.erp_scheme,gcs.created_by,gcs.created_ts,gcs.changed_by,gcs.changed_ts,gcs.mark_as_delete,gcs.id,gcs.description,gcs.no_days,gcs.allowed_days,gcs.erp_ref_id,gcs.billable_days,get.edition_type,gcs.edition_type as gce_edition_id from "+masterTypesArray + " gcs inner join gd_edition_type get on gcs.edition_type = get.id where gcs.mark_as_delete = false order by " +orderBy+"");
		}else if ("gd_rms_editions".equalsIgnoreCase(masterTypesArray)){
			query = entityManager.createNativeQuery("select gce.edition_name,gce.erp_edition,gce.created_by,gce.created_ts,gce.changed_by,gce.changed_ts,gce.mark_as_delete,gce.id,gce.erp_ref_id,get.edition_type,gce.edition_type as gce_edition_id from "+masterTypesArray + " gce inner join gd_rms_edition_type get on gce.edition_type = get.id where gce.mark_as_delete = false order by " +orderBy+"");
		}else if("gd_rms_positioning_discount".equalsIgnoreCase(masterTypesArray)){
			query=entityManager.createNativeQuery("select get.edition_type,gpd.positioning_type,gpd.positioning_desc,gpd.erp_ref_id,gpd.mark_as_delete,gpd.id,gpd.edition_type as gpd_edition_id from "+masterTypesArray + " gpd inner join gd_rms_edition_type get on gpd.edition_type = get.id where gpd.mark_as_delete = false order by " +orderBy+"");
			query=entityManager.createNativeQuery("select get.edition_type,gpd.positioning_type,gpd.positioning_desc,gpd.discount,gpd.erp_ref_id,gpd.mark_as_delete,gpd.id,gpd.edition_type as gpd_edition_id from "+masterTypesArray + " gpd inner join gd_rms_edition_type get on gpd.edition_type = get.id where gpd.mark_as_delete = false order by " +orderBy+"");
		}else if("gd_rms_page_positions".equalsIgnoreCase(masterTypesArray)) {
			query=entityManager.createNativeQuery("select get.edition_type,gpp.page_name,gpp.page_description,gpp.erp_ref_code,gpp.mark_as_delete,gpp.id,gpp.edition_type as gpp_edition_id,gpp.percentage from "+masterTypesArray + " gpp inner join gd_rms_edition_type get on gpp.edition_type = get.id where gpp.mark_as_delete = false order by " +orderBy+"");
		}else if("gd_rms_schemes".equalsIgnoreCase(masterTypesArray)) {
			query=entityManager.createNativeQuery("select get.edition_type,gpp.scheme,gpp.erp_scheme,gpp.erp_ref_id,gpp.mark_as_delete,gpp.id,gpp.edition_type as gpp_edition_id,gpp.no_days,gpp.billable_days from "+masterTypesArray + " gpp inner join gd_rms_edition_type get on gpp.edition_type = get.id where gpp.mark_as_delete = false order by " +orderBy+"");
		}else if("gd_rms_annexure".equalsIgnoreCase(masterTypesArray)) {
			query=entityManager.createNativeQuery("select get.edition_type,gpp.scheme,gpp.erp_scheme,gpp.erp_edition_code,gpp.mark_as_delete,gpp.id,gpp.edition_type as gpp_edition_id from "+masterTypesArray + " gpp inner join gd_rms_edition_type get on gpp.edition_type = get.id where gpp.mark_as_delete = false order by " +orderBy+"");
		}else if("gd_da_editions".equalsIgnoreCase(masterTypesArray)) {
			query = entityManager.createNativeQuery("select gce.edition_name,gce.erp_edition,gce.created_by,gce.created_ts,gce.changed_by,gce.changed_ts,gce.mark_as_delete,gce.id,gce.erp_ref_id,get.edition_type,gce.edition_type as gce_edition_id from "+masterTypesArray + " gce inner join gd_da_edition_type get on gce.edition_type = get.id where gce.mark_as_delete = false order by " +orderBy+"");
		}
		
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		logger.info("Exit from the method: " + METHOD_NAME);

		return nativeQuery.getResultList();
	}

	@Override
	public List<ListOfConfigValuesTo> getConfigValuesByParentName(String parentName) {
		String METHOD_NAME = "getConfigValuesByParentName";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<ListOfConfigValuesTo> list = new ArrayList<>();

		try {
			String query = "FROM GdConfigValues where configGroupId='" + parentName + "'";
			List<GdConfigValues> configList = (List<GdConfigValues>) baseDao.findByHQLQueryWithoutParams(query);
			if (configList != null) {
				for (GdConfigValues gdConfigValues : configList) {
					ListOfConfigValuesTo listOfConfigValuesTo = new ListOfConfigValuesTo();
					BeanUtils.copyProperties(gdConfigValues, listOfConfigValuesTo);
					list.add(listOfConfigValuesTo);
				}
			}
		} catch (Exception e) {
			logger.error("Error while getting list object values: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return list;
	}

	/**
	 * Get Smtp Configuration Details
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GdSettingConfigsTo> getGdConfigDetails(Map<String, Object> params) {

		String METHOD_NAME = "getSmtpConfigDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<GdSettingConfigsTo> listOfSmtpGroupTos = null;

		try {
			List<GdSettingsDefinitions> settings = (List<GdSettingsDefinitions>) baseDao
					.findByHQLQueryWithNamedParams(prop.getProperty("GET_APP_SETTINGS_SMTP"), params);

			if (!settings.isEmpty()) {

				listOfSmtpGroupTos = new ArrayList<GdSettingConfigsTo>();

				for (GdSettingsDefinitions gcg : settings) {

					GdSettingConfigsTo lcgTo = new GdSettingConfigsTo();

					BeanUtils.copyProperties(gcg, lcgTo);

					listOfSmtpGroupTos.add(lcgTo);
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting smtp Config Group details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return listOfSmtpGroupTos;
	}
	
	/**
	 * Get Smtp Configuration Details
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, GdSettingConfigsTo> getGdConfigDetailsMap(Map<String, Object> params) {

		String METHOD_NAME = "getSmtpConfigDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		Map<String, GdSettingConfigsTo> listOfSmtpGroupTos = null;

		try {
			List<GdSettingsDefinitions> settings = (List<GdSettingsDefinitions>) baseDao
					.findByHQLQueryWithNamedParams(prop.getProperty("GET_APP_SETTINGS_SMTP"), params);

			if (!settings.isEmpty()) {

				listOfSmtpGroupTos = new HashMap<>();

				for (GdSettingsDefinitions gcg : settings) {

					GdSettingConfigsTo lcgTo = new GdSettingConfigsTo();

					BeanUtils.copyProperties(gcg, lcgTo);

					listOfSmtpGroupTos.put(gcg.getSettingShortId(), lcgTo);
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting smtp Config Group details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return listOfSmtpGroupTos;
	}

	@Override
	public boolean updateSmtpConfigDetails(GdSettingConfigsTo smtpConfigsTo, Integer loginId) {
		String METHOD_NAME = "updateSmtpConfigDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean updated = false;
		try {
			String sql = "From GdSettingsDefinitions where settingId=" + smtpConfigsTo.getSettingId();
			List<GdSettingsDefinitions> gdSettingsDefinitionsList = (List<GdSettingsDefinitions>) baseDao
					.findByHQLQueryWithoutParams(sql);
			if(gdSettingsDefinitionsList!=null&&gdSettingsDefinitionsList.size()>0) {
				GdSettingsDefinitions gdSettingsDefinitions = gdSettingsDefinitionsList.get(0);
				if (smtpConfigsTo.getGroupDescription() != null) {
					gdSettingsDefinitions.setGroupDescription(smtpConfigsTo.getGroupDescription());
				}
				if (smtpConfigsTo.getSettingDefaultValue() != null) {
					gdSettingsDefinitions.setSettingDefaultValue(smtpConfigsTo.getSettingDefaultValue());
				}

				if (smtpConfigsTo.getSettingDesc() != null) {
					gdSettingsDefinitions.setSettingDesc(smtpConfigsTo.getSettingDesc());
				}
				if (smtpConfigsTo.getSettingGroupName() != null) {
					gdSettingsDefinitions.setSettingGroupName(smtpConfigsTo.getSettingGroupName());
				}

				if (smtpConfigsTo.getSettingSeqNo() != null) {
					gdSettingsDefinitions.setSettingSeqNo(smtpConfigsTo.getSettingSeqNo());
				}
				if (smtpConfigsTo.getSettingShortId() != null) {
					gdSettingsDefinitions.setSettingShortId(smtpConfigsTo.getSettingShortId());
				}
				if (smtpConfigsTo.getSettingTypeFormat() != null) {
					gdSettingsDefinitions.setSettingTypeFormat(smtpConfigsTo.getSettingTypeFormat());
				}
				gdSettingsDefinitions.setChangedBy(loginId);
				gdSettingsDefinitions.setChangedTs(new Date());
				baseDao.saveOrUpdate(gdSettingsDefinitions);
				updated = true;
			}
		} catch (Exception e) {
			logger.error("Error while getting smtp Config Group details: " + ExceptionUtils.getStackTrace(e));
			updated = false;
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return updated;
	}

	@Override
	public List<GdState> getGdStates() {
		String METHOD_NAME = "getGdStates";

		logger.info("Entered into the method: " + METHOD_NAME);
		List<GdState> gdState = new ArrayList<GdState>();
		try {
			String query = "from GdState gs where gs.markAsDelete = false";
			gdState = (List<GdState>) baseDao.findByHQLQueryWithoutParams(query);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return gdState;
	}

	@Override
	public List<GdCity> getGdCity() {
		String METHOD_NAME = "getGdCity";

		logger.info("Entered into the method: " + METHOD_NAME);
		List<GdCity> gdCity = new ArrayList<GdCity>();
		try {
			String query = "from GdCity gc where gc.markAsDelete = false";
			gdCity = (List<GdCity>) baseDao.findByHQLQueryWithoutParams(query);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return gdCity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingUnits> getBookingUnits() {
		String METHOD_NAME = "getBookingUnits";

		logger.info("Entered into the method: " + METHOD_NAME);
		List<BookingUnits> bookingUnits = new ArrayList<BookingUnits>();
		try {
			String query = "from BookingUnits bu where bu.markAsDelete = false";
			bookingUnits = (List<BookingUnits>) baseDao.findByHQLQueryWithoutParams(query);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bookingUnits;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getClfRatesList() {
		String METHOD_NAME = "getClfRatesList";

		logger.info("Entered into the method: " + METHOD_NAME);
		List<Object[]> clfRates = new ArrayList<Object[]>();
		try {
			String query = "select cr.classified_ads_type,cr.classified_ads_subtype,cr.edition_id,cr.rate,cr.extra_line_amount,cr.min_lines,cr.char_count_per_line,gcat.ads_type,gcast.ads_sub_type,gce.edition_name,cr.rate_id from clf_rates cr inner join gd_classified_ads_types gcat on cr.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on cr.classified_ads_subtype  = gcast.id inner join gd_classified_editions gce on gce.id = cr.edition_id where cr.mark_as_delete = false";
			clfRates = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			System.out.println(clfRates);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return clfRates;
	}

	@Override
	public List<Object[]> getGdRmsEditions() {
		List<Object[]> editions = new ArrayList<Object[]>();
		String query=" select gce.edition_name,gce.erp_edition,gce.created_by,gce.created_ts,gce.changed_by,gce.changed_ts,gce.erp_ref_id,get.edition_type,gce.edition_type as gd_editions,gce.id,gce.add_type,gs.state,grat.add_desc,gce.state_code  from gd_rms_editions gce inner join gd_rms_edition_type get on gce.edition_type = get.id inner join gd_state gs on gs.state_code = gce.state_code inner join gd_rms_ads_type grat  on grat.id = gce.add_type  where gce.mark_as_delete = false ";
		editions = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return editions;
	}

	@Override
	public List<Object[]> getGdRmsAnnexure() {
		List<Object[]> annexure = new ArrayList<Object[]>();
		String query = "select get.edition_name,gpp.scheme,gpp.erp_scheme,gpp.erp_edition_code,gpp.mark_as_delete,gpp.id,gpp.rate,gca.ads_sub_type,gpp.edition_id,gpp.scheme_id,gpp.position_inst_erp_ref_id,gpp.classified_ads_subtype from gd_rms_annexure gpp inner join gd_rms_editions get on gpp.edition_id = get.id inner join gd_classified_ads_sub_types gca on gpp.classified_ads_subtype = gca.id where gpp.mark_as_delete = false";
		annexure = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return annexure;
	}

	@Override
	public List<Object[]> getGdRmsMultipleDiscountVal() {
		List<Object[]> multiDiscVal = new ArrayList<Object[]>();
		String query = "select grd.edition_name,grm.erp_edition_code,grm.erp_position_inst_code,grs.scheme,grm.edition_id,grm.scheme_id,grm.id from gd_rms_multiple_discount_val grm inner join gd_rms_editions grd on grd.id = grm.edition_id inner join gd_rms_schemes grs on grs.id = grm.scheme_id where grm.mark_as_delete = false";
		multiDiscVal = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return multiDiscVal;
	}

	@Override
	public List<Object[]> getGdRmsPagePositions() {
		List<Object[]> pagePositions = new ArrayList<Object[]>();
		String query = "select get.edition_type,gpp.page_name,gpp.page_description,gpp.erp_ref_code,gpp.mark_as_delete,gpp.id,gpp.edition_type as gpp_edition_id,gpp.percentage,gpp.id from gd_rms_page_positions gpp inner join gd_rms_edition_type get on gpp.edition_type = get.id where gpp.mark_as_delete = false";
		pagePositions = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return pagePositions;
	}

	@Override
	public List<Object[]> getGdRmsPositioningDiscount() {
		List<Object[]> positionDisc = new ArrayList<Object[]>();
		String query = "select get.edition_type,gpd.positioning_type,gpd.positioning_desc,gpd.erp_ref_id,gpd.discount,gpd.type_of_position,gpd.edition_type as edition_type_id,gpd.id  from gd_rms_positioning_discount gpd inner join gd_rms_edition_type get on gpd.edition_type = get.id where gpd.mark_as_delete = false";
		positionDisc = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return positionDisc;
	}

	@Override
	public List<Object[]> getGdRmsPageRate() {
		List<Object[]> rmsPageRate = new ArrayList<Object[]>();
		String query = "select gre.edition_name,grp.erp_ref_code,grp.percentage,grp.page_erp_ref_code,grp.edition_id  from gd_rms_page_rate grp inner join gd_rms_editions gre on gre.id = grp.edition_id where grp.mark_as_delete = false";
		rmsPageRate = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return rmsPageRate;
	}

	@Override
	public List<Object[]> getGdRmsSchemes() {
		List<Object[]> rmsSchemes = new ArrayList<Object[]>();
		String query = "select get.edition_type,gpp.scheme,gpp.erp_scheme,gpp.erp_ref_id,gpp.no_days,gpp.billable_days,gpp.edition_type as scheme_edition_id,gpp.id from gd_rms_schemes gpp inner join gd_rms_edition_type get on gpp.edition_type = get.id where gpp.mark_as_delete = false";
		rmsSchemes = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return rmsSchemes;
	}

	@Override
	public List<Object[]> getRmsRatesList() {
		List<Object[]> rmsRatesList = new ArrayList<Object[]>();
		String query = "select rr.classified_ads_subtype,gcast.ads_sub_type,rr.edition_id,gre.edition_name,rr.rate,rr.fixed_format,rr.rate_id,grff.size,rr.edition_type,gret.edition_type as gret_edition_type from rms_rates rr inner join gd_rms_fixed_formats grff on rr.fixed_format = grff.id inner join gd_classified_ads_sub_types gcast on rr.classified_ads_subtype  = gcast.id inner join gd_rms_editions gre on rr.edition_id = gre.id inner join gd_rms_edition_type gret on rr.edition_type = gret.id where rr.mark_as_delete = false";
		rmsRatesList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return rmsRatesList;
	}

	@Override
	public List<Object[]> getApprovalMatrix() {
		List<Object[]> rmsApprovalList = new ArrayList<Object[]>();
		String query = "select ram.id,ram.booking_office,ram.range_from,ram.range_to,ram.approver_user_id,ram.approver_cc_user_id,ram.level,bu.booking_location,um.email,um1.email as cc_mail from rms_approval_matrix ram inner join booking_units bu on ram.booking_office = bu.booking_code left join um_users um on ram.approver_user_id = um.user_id left join um_users um1 on ram.approver_cc_user_id = um1.user_id where ram.mark_as_delete = false order by ram.level desc";
		rmsApprovalList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return rmsApprovalList;
	}

	@Override
	public GdDaClassifiedCategory getDaCategory(String categoryType) {
		GdDaClassifiedCategory category= categoryRepo.getDaCategoryType(categoryType);
		return category;
	}

	@Override
	public GdDaClassifiedCategory getDaCategoryById(Integer id) {
		GdDaClassifiedCategory daCategoryById = categoryRepo.getDaCategoryById(id);
		return daCategoryById;
	}

	@Override
	public DaSizes getDaSizes(String size, Integer categoryTypeId) {
		DaSizes daSizes = daSizeRepo.getDaSizes(size,categoryTypeId);
		return daSizes;
	}

	@Override
	public DaSizes getDaSizesById(Integer sizeId) {
		DaSizes daSizes = daSizeRepo.getDaSizesById(sizeId);
		return daSizes;
	}

	@Override
	public List<Object[]> getAllDaSizes() {
		List<Object[]> getAllDaSizes = new ArrayList<Object[]>();
		String query = "select ds.size_id,gdcc.category_type,ds.size from da_sizes ds inner join gd_da_classified_category gdcc on gdcc.id = ds.category_type where ds.mark_as_delete = false and gdcc.mark_as_delete = false";
		getAllDaSizes = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		return getAllDaSizes;
	}

	@Override
	public List<Object[]> getDaRatesList() {
		String METHOD_NAME = "getDaRatesList";

		logger.info("Entered into the method: " + METHOD_NAME);
		List<Object[]> clfRates = new ArrayList<Object[]>();
		try {
			String query = "select dr.classified_ads_type,dr.classified_ads_subtype,dr.edition_id,dr.rate,gcat.ads_type,gcast.ads_sub_type,gde.edition_name,dr.rate_id,ds.size,gdcc.category_type,gdet.edition_type,dr.size_id,dr.category_id from da_rates dr inner join gd_classified_ads_types gcat on gcat.id = dr.classified_ads_type inner join gd_classified_ads_sub_types gcast on gcast.id = dr.classified_ads_subtype inner join gd_da_editions gde on gde.id = dr.edition_id inner join da_sizes ds on ds.size_id = dr.size_id inner join gd_da_classified_category gdcc on gdcc.id = dr.category_id left join gd_da_edition_type gdet on gdet.id = gde.edition_type where dr.mark_as_delete =false and ds.mark_as_delete =false";
			clfRates = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			System.out.println(clfRates);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return clfRates;
	}
}
