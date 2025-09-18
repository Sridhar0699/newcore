package com.portal.hm.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.basedao.IBaseDao;
import com.portal.hm.entities.HmManuals;
import com.portal.hm.model.HMDaoCommonModel;
import com.portal.user.entities.UmOrgRoles;
import com.portal.wf.entity.WfInbox;

/**
 * HM DAO implementation
 * 
 * @author Incresol
 *
 */
@Service("hmDao")
public class HelpManualDaoImpl implements HelpManualDao {

	private static final Logger logger = LogManager.getLogger(HelpManualDaoImpl.class);

	@Autowired(required = true)
	private IBaseDao baseDao;

	@Autowired(required = true)
	private Environment prop;

	@SuppressWarnings("unchecked")
	@Transactional
	public boolean createOrUpdateHelpManuals(HMDaoCommonModel hmDaoCommonModel) {
		logger.info("createOrUpdateHelpManuals started");
		boolean isSavedOrUpdated = false;
		try {
			List<String> roleId = new ArrayList<>();
			hmDaoCommonModel.getHelpManual().forEach(hmManuals -> {
				if (hmManuals.getRoleShortId() != null) {
					roleId.add(hmManuals.getRoleShortId());
					Map<String, Object> params = new HashMap<>();
					params.put("roleId", hmManuals.getRoleShortId());
					params.put("manualType", hmManuals.getManualType());
					List<HmManuals> hmManualsUpdate = (List<HmManuals>) baseDao.findByHQLQueryWithNamedParams(
							"SELECT hm FROM HmManuals hm WHERE hm.manualType=:manualType AND hm.roleShortId=:roleId AND hm.markAsDelete='0'",
							params);
					if (hmManualsUpdate != null && hmManualsUpdate.size() > 0) {
						hmManuals.setManualId(hmManualsUpdate.get(0).getManualId());
						BeanUtils.copyProperties(hmManuals, hmManualsUpdate.get(0));
						hmManualsUpdate.get(0).setChangedBy(hmDaoCommonModel.getLoggedUser().getUserId());
						hmManualsUpdate.get(0).setChangedTs(new Date());
						baseDao.saveOrUpdate(hmManualsUpdate.get(0));
					} else {
						hmManuals.setCreatedBy(hmDaoCommonModel.getLoggedUser().getUserId());
						hmManuals.setCreatedTs(new Date());
						baseDao.saveOrUpdate(hmManuals);
					}
					List<String> notification = null;
					if(hmDaoCommonModel.getNotificationRole()!=null){
						String[] notifiy = hmDaoCommonModel.getNotificationRole().split(",");
						notification = Arrays.asList(notifiy);
					}
					if(notification!=null && notification.contains(hmManuals.getRoleShortId()+"")){
						List<Map<String, Object>> data = baseDao.findByNativeSQLQueryByMap(
								"select usr.USER_ID,orgUsr.ROLE_ID,usr.REGION_CENTER from UM_USERS usr join um_org_users orgUsr on orgUsr.USER_ID = usr.USER_ID where orgUsr.ROLE_ID in('"+ hmManuals.getRoleShortId() +"')"
								, new Object[] { });
						for(Map<String, Object> id : data){
							WfInbox pushPayload = new WfInbox();
//							pushPayload.setReqFromType(hmDaoCommonModel.getLoggedUser().getRoleName());
//							pushPayload.setReqDate(new Date());
//							pushPayload.setObjectType("HELP_MANUAL");
//							pushPayload.setTags("HELP_MANUAL");
//							pushPayload.setSubject("HELP MANUAL UPDATE");
							if(id.get("REGION_CENTER")!=null){
//								pushPayload.setRefToId(Integer.parseInt(id.get("REGION_CENTER")+""));
							}
//							pushPayload.setReqToType(this.getRolDetailByShortId(hmManuals.getRoleShortId()).getRoleShortId());
//							pushPayload.setStatus(0);
//							pushPayload.setCreatedBy(hmDaoCommonModel.getLoggedUser().getUserId()+"");
//							pushPayload.setCreatedTs(new Date());
//							pushPayload.setRecUserId(Integer.parseInt(id.get("USER_ID")+""));
//							pushPayload.setMarkAsDelete(0);
//							pushPayload.setReadStatus("UNREAD");
//							pushPayload.setObjectFrom("HELP_MANUAL");
							baseDao.saveOrUpdate(pushPayload);
						}
					}
				}
			});
			if (hmDaoCommonModel.getHelpManual() != null && hmDaoCommonModel.getHelpManual().size() > 0) {
				Map<String, Object> params = new HashMap<>();
				params.put("roleId", roleId);
				params.put("manualType", hmDaoCommonModel.getHelpManual().get(0).getManualType());
				List<HmManuals> hmManuals = (List<HmManuals>) baseDao.findByHQLQueryWithNamedParams(
						"SELECT hm FROM HmManuals hm WHERE hm.manualType=:manualType AND hm.roleShortId NOT IN :roleId AND hm.markAsDelete='0'",
						params);
				if (hmManuals != null && hmManuals.size() > 0) {
					hmManuals.forEach(manualIt -> {
						manualIt.setMarkAsDelete(false);
						baseDao.saveOrUpdate(manualIt);
					});
				}
			}
			isSavedOrUpdated = true;
		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
		return isSavedOrUpdated;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public HMDaoCommonModel getHelpManuals(HMDaoCommonModel hmDaoCommonModel) {
		final List<HmManuals> hmManualsAll = new ArrayList<>();
		HMDaoCommonModel hmDaoCommonModelResp = new HMDaoCommonModel();
		hmDaoCommonModelResp.setHelpManual(hmManualsAll);
		List<String> allManualTypes = new ArrayList<>();
		hmDaoCommonModelResp.setUtilParams(allManualTypes);
		try {
			if (hmDaoCommonModel.getHelpManual() != null && hmDaoCommonModel.getHelpManual().size() > 0) {
				hmDaoCommonModel.getHelpManual().forEach(hmManualsIte -> {
					if (hmManualsIte.getManualId() != null) {
						HmManuals hmManuals = (HmManuals) baseDao.findByPK(HmManuals.class, hmManualsIte.getManualId());
						if (hmManuals != null) {
							hmManualsAll.add(hmManuals);
							allManualTypes.add(hmManuals.getManualType());
						}
					}
				});
			} else if ((hmDaoCommonModel.getManualType() != null
					&& "ALL".equalsIgnoreCase(hmDaoCommonModel.getManualType()))
					&& (hmDaoCommonModel.getRoleId() != null && !hmDaoCommonModel.getRoleId().isEmpty())) {
				List<UmOrgRoles> UmOrgRoles = (List<UmOrgRoles>) baseDao.findByHQLQueryWithIndexedParams(
						"SELECT uor FROM UmOrgRoles uor WHERE uor.roleShortId=?1 AND uor.markAsDelete='0'",
						new Object[] { hmDaoCommonModel.getRoleId() });
				List<HmManuals> helpManualsOnRole = (List<HmManuals>) baseDao.findByHQLQueryWithIndexedParams(
						"SELECT hm FROM HmManuals hm WHERE hm.markAsDelete='0' AND hm.roleShortId=?1 ORDER BY hm.manualType",
						new Object[] { !UmOrgRoles.isEmpty() ? UmOrgRoles.get(0).getRoleShortId() : 0 });
				if (helpManualsOnRole != null && helpManualsOnRole.size() > 0) {
					String manualTypeTemp = "";
					for (HmManuals hmManuals : helpManualsOnRole) {
						if (!manualTypeTemp.equalsIgnoreCase(hmManuals.getManualType())) {
							manualTypeTemp = hmManuals.getManualType();
							allManualTypes.add(hmManuals.getManualType());
						}
					}
					hmManualsAll.addAll(helpManualsOnRole);
				}

			} else if (hmDaoCommonModel.getManualType() != null
					&& "ALL".equalsIgnoreCase(hmDaoCommonModel.getManualType())) {
				List<HmManuals> allHelpManuals = (List<HmManuals>) baseDao.findByHQLQueryWithoutParams(
						"SELECT hm FROM HmManuals hm WHERE hm.markAsDelete='0' ORDER BY hm.manualType");
				if (allHelpManuals != null && allHelpManuals.size() > 0) {
					String manualTypeTemp = "";
					for (HmManuals hmManuals : allHelpManuals) {
						if (!manualTypeTemp.equalsIgnoreCase(hmManuals.getManualType())) {
							manualTypeTemp = hmManuals.getManualType();
							allManualTypes.add(hmManuals.getManualType());
						}
					}
					hmManualsAll.addAll(allHelpManuals);
				}

			} else {
				Map<String, Object> params = new HashMap<>();
				params.put("manualType", hmDaoCommonModel.getManualType());

				List<HmManuals> allHelpManuals = (List<HmManuals>) baseDao.findByHQLQueryWithNamedParams(
						"SELECT hm FROM HmManuals hm WHERE hm.manualType=:manualType AND hm.markAsDelete='0'  ORDER BY hm.manualType",
						params);
				if (allHelpManuals != null && allHelpManuals.size() > 0) {
					String manualTypeTemp = "";
					for (HmManuals hmManuals : allHelpManuals) {
						if (!manualTypeTemp.equalsIgnoreCase(hmManuals.getManualType())) {
							manualTypeTemp = hmManuals.getManualType();
							allManualTypes.add(hmManuals.getManualType());
						}
					}
					hmManualsAll.addAll(allHelpManuals);
				}
			}

		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
		return hmDaoCommonModelResp;
	}

	@Transactional
	public UmOrgRoles getRolDetail(int roleId) {
		UmOrgRoles umOrgRoles = new UmOrgRoles();
		try {
			if (roleId > 0) {
				UmOrgRoles umOrgRolesTemp = (UmOrgRoles) baseDao.findByPK(UmOrgRoles.class, roleId);
				if (umOrgRolesTemp != null) {
					BeanUtils.copyProperties(umOrgRolesTemp, umOrgRoles);
				}
			}
		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
		return umOrgRoles;
	}
	
	@Transactional
	public UmOrgRoles getRolDetailByShortId(String roleShortId) {
		UmOrgRoles umOrgRoles = new UmOrgRoles();
		try {
			if (roleShortId != null && !roleShortId.isEmpty()) {
				List<UmOrgRoles> umOrgRolesTemp = (List<UmOrgRoles>) baseDao.findByHQLQueryWithIndexedParams("from UmOrgRoles where roleShortId = ?1", new Object[]{roleShortId});
				if (!umOrgRolesTemp.isEmpty()) {
					BeanUtils.copyProperties(umOrgRolesTemp.get(0), umOrgRoles);
				}
			}
		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
		return umOrgRoles;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public HMDaoCommonModel downloadHelpManual(HMDaoCommonModel hmDaoCommonModel) {
		HMDaoCommonModel hmDaoCommonModelResp = new HMDaoCommonModel();
		try {
			String hqlQuery = "SELECT hm FROM HmManuals hm WHERE hm.markAsDelete='0' AND hm.manualType=:manualType AND hm.fileName LIKE '%"
					+ hmDaoCommonModel.getFileName() + "%'";
			Map<String, Object> params = new HashMap<>();
			params.put("manualType", hmDaoCommonModel.getManualType());
			// params.put("fileName", hmDaoCommonModel.getFileName());
			List<HmManuals> HmManualsList = (List<HmManuals>) baseDao.findByHQLQueryWithNamedParams(hqlQuery, params);
			if (!HmManualsList.isEmpty()) {
				for (HmManuals hmManuals : HmManualsList) {
					int counter = 0;
					for (String fileName : hmManuals.getFileName().split(",")) {
						if (fileName.equalsIgnoreCase(hmDaoCommonModel.getFileName())) {
							hmDaoCommonModelResp.setFileName(fileName);
							hmDaoCommonModelResp.setFileUrl(hmManuals.getFileUrl().split(",")[counter]);
						}
						counter++;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
		return hmDaoCommonModelResp;
	}

}
