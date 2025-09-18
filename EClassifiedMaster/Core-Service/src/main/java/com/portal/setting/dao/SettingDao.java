package com.portal.setting.dao;

import java.util.List;
import java.util.Map;

import com.portal.setting.to.SettingTo;

/**
 * Setting DAO
 * 
 * @author Sathish Babu D
 *
 */
public interface SettingDao {

	/**
	 * Get setting values
	 * 
	 * @param params
	 * @return
	 */
	public SettingTo getSettingValues(Map<String, Object> params);

	public SettingTo getSMTPSettingValues(Map<String, Object> params);

	/**
	 * Get Settings definition and values
	 * 
	 * @param settingType
	 * @param refObjId
	 * @return
	 */
	public List<Object[]> getSettingsDetails(String settingType, String refObjId, String settingName);

	/**
	 * Create/Update setting details
	 * 
	 * @param settingTo
	 * @param accessObjId
	 * @return
	 */
	public boolean updateSettingDetails(SettingTo settingTo, Integer loggedUser);

	/**
	 * Get settings for list of business partners
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, String> getSettingsByListOfBp(Map<String, Object> params);
}
