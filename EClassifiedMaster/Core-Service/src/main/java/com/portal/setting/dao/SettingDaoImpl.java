package com.portal.setting.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.gd.entities.GdSettingsDefinitions;
import com.portal.repository.GdSettingsDefinitionsRepository;
import com.portal.repository.OmApplSettingsRepository;
import com.portal.setting.entities.OmApplSettings;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;

/**
 * Setting DAO implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service("settingDao")
public class SettingDaoImpl implements SettingDao {

	private static final Logger logger = LogManager.getLogger(SettingDaoImpl.class);

	@Autowired
	private GdSettingsDefinitionsRepository settingRepo;
	
	@Autowired
	private OmApplSettingsRepository omApplSettingsRepository;

	@Autowired(required = true)
	private Environment prop;

	/**
	 * Get setting values
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SettingTo getSettingValues(Map<String, Object> params) {

		String METHOD_NAME = "getSettingValues";

		logger.info("Entered into the method: " + METHOD_NAME);

		SettingTo settingTo = new SettingTo();

		try {

			int sType = Integer.parseInt(params.get("stype").toString());

			List<Object[]> settings = null;

			if (SettingType.APP_SETTING.getValue() == sType) {

				/*settings = (List<Object[]>) baseDao.findByHQLQueryWithNamedParams(prop.getProperty("GET_APP_SETTINGS"),
						params);*/
				settings = settingRepo.getAppSettings(params.get("stype").toString(), new ArrayList<String>((Collection<String>) params.get("grps")));

			} else {

				/*settings = (List<Object[]>) baseDao
						.findByHQLQueryWithNamedParams(prop.getProperty("GET_OTHER_SETTINGS"), params);*/
				
				settings = settingRepo.getOtherSettings( new ArrayList<String>((Collection<String>)params.get("refObjId")),(Integer) params.get("stype"), new ArrayList<String>((Collection<String>) params.get("grps")));
			}

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

	@SuppressWarnings("unchecked")
	public SettingTo getSMTPSettingValues(Map<String, Object> params) {

		String METHOD_NAME = "getSMTPSettingValues";

		logger.info("Entered into the method: " + METHOD_NAME);

		SettingTo settingTo = new SettingTo();

		try {

			/*List<GdSettingsDefinitions> settings = (List<GdSettingsDefinitions>) baseDao
					.findByHQLQueryWithNamedParams(prop.getProperty("GET_APP_SETTINGS_SMTP"), params);*/
			List<GdSettingsDefinitions> settings = settingRepo.getSettingsBySTypeGrps((Integer)params.get("stype"), new ArrayList<String>((Collection<String>) params.get("grps")));

			Map<String, String> map = new HashMap<String, String>();

			for (GdSettingsDefinitions obj : settings) {
				if (obj != null && obj.getSettingShortId() != null) {
					map.put(obj.getSettingShortId(), obj.getSettingDefaultValue());
				}
			}
			settingTo.setSettings(map);
		} catch (Exception e) {
			logger.error("Error while getting setting values: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return settingTo;
	}

	/**
	 * Get specific Setting definition and values
	 * 
	 * @param settingType
	 * @param refObjId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getSettingsDetails(String settingType, String refObjId, String settingName) {

		String METHOD_NAME = "getSettingsDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		List<Object[]> settings = null;

		try {

			Integer value = null;

			Object[] type = getSettingType(settingType);

			if (type[0] != null && type[1] != null) {

				value = (int) type[1];
			}
			ArrayList<Integer> settingTypeIds = new ArrayList<>();
			settingTypeIds.add(value);
			if (settingName == null || settingName.trim().isEmpty()) {

				if (SettingType.APP_SETTING.name().equals(settingType)) {

					/*settings = (List<Object[]>) this.baseDao.findByHQLQueryWithIndexedParams(
							prop.getRequiredProperty("GET_APPL_SETTINGS"), new Object[] { value });*/
					
					settings = settingRepo.getSettingsBySTypeId(settingTypeIds);
					
				} else {

					/*settings = (List<Object[]>) this.baseDao.findByHQLQueryWithIndexedParams(
							prop.getRequiredProperty("GET_SETTINGS_DETAILS"), new Object[] { refObjId, value });*/
					
					settings = settingRepo.getSettingsBySTypeIdAndObjRefId(refObjId, settingTypeIds);
				}

			} else {

				/*settings = (List<Object[]>) this.baseDao.findByHQLQueryWithIndexedParams(
						prop.getRequiredProperty("GET_SPECIFIC_SETTING_DETAILS"), new Object[] { refObjId, value });*/
				
				settings = settingRepo.getSettingsBySTypeIdAndObjRefId(refObjId, settingTypeIds);
			}

		} catch (Exception e) {
			logger.error("Error while getting setting definitions and values: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return settings;
	}

	/**
	 * Get Setting type value
	 * 
	 * @param settingType
	 * @return
	 */
	private Object[] getSettingType(String settingType) {

		Integer value = null;

		String name = null;

		Object[] obj = new Object[2];

		if (SettingType.ORG_SETTING.name().equals(settingType)) {

			value = SettingType.ORG_SETTING.getValue();
			name = SettingType.ORG_SETTING.name();

		} else if (SettingType.BP_SETTING.name().equals(settingType)) {

			value = SettingType.BP_SETTING.getValue();
			name = SettingType.BP_SETTING.name();

		} else if (SettingType.APP_SETTING.name().equals(settingType)) {

			value = SettingType.APP_SETTING.getValue();
			name = SettingType.APP_SETTING.name();

		} else if (SettingType.ORG_BP_SETTING.name().equals(settingType)) {

			value = SettingType.ORG_BP_SETTING.getValue();
			name = SettingType.ORG_BP_SETTING.name();
		}

		obj[0] = name;
		obj[1] = value;

		return obj;
	}

	/**
	 * Create/Update setting details
	 * 
	 * @param settingTo
	 * @param accessObjId
	 * @return
	 */
	@Override
	public boolean updateSettingDetails(SettingTo settingTo, Integer loggedUser) {

		String METHOD_NAME = "updateSettingDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isUpdated = false;

		try {

			if (settingTo.getFieldId() != null) {

				String refObjId = "APP".equals(settingTo.getRefObjType()) ? null : settingTo.getRefObjId();

				Optional<GdSettingsDefinitions> gsd = settingRepo.findById(settingTo.getFieldId());

				OmApplSettings setting = new OmApplSettings();

				if (settingTo.getValId() != null && !settingTo.getValId().trim().isEmpty()) {

					Optional<OmApplSettings> omAppSettingsOrgl = omApplSettingsRepository.findById(settingTo.getValId());
					setting = omAppSettingsOrgl.get();
					setting.setApplSettingId(settingTo.getValId());
					setting.setChangedBy(loggedUser);
					setting.setChangedTs(new Date());

				} else if (settingTo.getVal() != null && !settingTo.getVal().trim().isEmpty()) {

					setting.setApplSettingId(UUID.randomUUID().toString());
					setting.setGdSettingsDefinitions(gsd.get());
					setting.setCreatedBy(loggedUser);
					setting.setCreatedTs(new Date());
				}

				setting.setMarkAsDelete(false);
				setting.setRefObjId(refObjId);
				setting.setRefObjType(settingTo.getRefObjType());
				setting.setSettingValue(settingTo.getVal());

				if (setting != null && setting.getApplSettingId() != null
						&& !setting.getApplSettingId().trim().isEmpty() && setting.getSettingValue() != null
						&& !setting.getSettingValue().trim().isEmpty()) {

					omApplSettingsRepository.save(setting);

					isUpdated = true;
				}
			}

		} catch (Exception e) {
			logger.error("Error while updating setting details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return isUpdated;
	}

	/**
	 * Get settings for list of business partners
	 * 
	 * @param params
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getSettingsByListOfBp(Map<String, Object> params) {

		String METHOD_NAME = "getSettingsByListOfBp";

		logger.info("Entered into the method: " + METHOD_NAME);

		Map<String, String> settings = new HashMap<String, String>();

		try {

			/*List<Object> lgsd = (List<Object>) baseDao
					.findByHQLQueryWithNamedParams(prop.getRequiredProperty("GET_SETTINGS_BY_BPS"), params);*/
			List<Object> lgsd = settingRepo.getSettingsByRefIdSTypeSgrp(new ArrayList<String>((Collection<String>)params.get("refObjId")), (Integer)params.get("stype"), new ArrayList<String>((Collection<String>) params.get("grps")));
			for (Object obj : lgsd) {

				OmApplSettings oap = (OmApplSettings) obj;

				if (oap != null && oap.getSettingValue() != null) {
					settings.put(oap.getRefObjId(), oap.getSettingValue());
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting settings by business partners : " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return settings;
	}
}
