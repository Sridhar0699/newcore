package com.portal.gd.dao;

import java.util.List;
import java.util.Map;

import com.portal.da.entities.DaSizes;
import com.portal.gd.entities.BookingUnits;
import com.portal.gd.entities.GdCity;
import com.portal.gd.entities.GdDaClassifiedCategory;
import com.portal.gd.entities.GdState;
import com.portal.gd.to.GdSettingConfigsTo;
import com.portal.gd.to.GlobalDataTo;
import com.portal.gd.to.ListOfConfigGroupTo;
import com.portal.gd.to.ListOfConfigValuesTo;
import com.portal.org.to.ParentObjectTo;
import com.portal.security.model.LoggedUser;

/**
 * Global data DAO
 * 
 * @author Sathish Babu D
 *
 */
public interface GlobalDataDao {

	/**
	 * Get List object values
	 * 
	 * @param objName
	 * @param objId
	 * @return
	 */
	public List<Object[]> getListObjValues(String objName, String objId);

	/**
	 * Get the left side menu of application
	 * 
	 * @param globalDataTo
	 * @return
	 */
	public List<ParentObjectTo> getAccessObjects(GlobalDataTo globalDataTo);

	/**
	 * Get Configuration values
	 * 
	 * @param orgId
	 * @param bpId
	 * @param dataType
	 * @param parentGroup
	 * @param pgSize
	 * @param pgNum
	 * @param multiBpIds 
	 * @param addField1 
	 * @return
	 */
	public List<ListOfConfigValuesTo> getConfigValues(ListOfConfigValuesTo configval);

	/**
	 * Get Configuration Group Details
	 *
	 * @return
	 */
	public List<ListOfConfigGroupTo> getConfigGroup();

	/**
	 * Create or Update Configuration Values
	 * 
	 * @param listOfConfVals
	 * @param loggedUser
	 * @return
	 */
	public String createOrUpdateConfigValues(List<ListOfConfigValuesTo> listOfConfVals, Integer loggedUser);

	/**
	 * Create or Update Configuration Group
	 * 
	 * @param listOfConfGroups
	 * @param loggedUser
	 * @return
	 */
	public String createOrUpdateConfigGroup(List<ListOfConfigGroupTo> listOfConfGroups, Integer loggedUser);
	
	public List<ListOfConfigValuesTo> getConfigValuesWithFilter(String orgId, String bpId,String key, String value, String group, String parentGroup,
			String addField1, String addField2,String addField3, String addField4,String addField5, String addField6,
			String addField7, String addField8);

	public List<Map<String, Object>> getTablelist(String obj_name, String filterKey, String filterValue,boolean isMapping);



	//GlobalDataTo getAccessObjects2(GlobalDataTo globalDataTo);
	
	/**
	 * Get the regional centers of application
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMasterData(String masterType, LoggedUser loggedUser, Map<String, Object> filter, String masterTypeEnum);
	
	public List<ListOfConfigValuesTo> getConfigValuesByParentName(String parentName);
	
	public List<GdSettingConfigsTo> getGdConfigDetails(Map<String, Object> params);
	
	public boolean updateSmtpConfigDetails(GdSettingConfigsTo smtpConfigsTo,Integer loginId);

	public Map<String, GdSettingConfigsTo> getGdConfigDetailsMap(Map<String, Object> params);

	public List<GdState> getGdStates();

	public List<GdCity> getGdCity();

	public List<BookingUnits> getBookingUnits();

	public List<Object[]> getClfRatesList();
	
	public List<Object[]> getGdRmsEditions();
	
	public List<Object[]> getGdRmsAnnexure();

	public List<Object[]> getGdRmsMultipleDiscountVal();

	public List<Object[]> getGdRmsPagePositions();

	public List<Object[]> getGdRmsPositioningDiscount();

	public List<Object[]> getGdRmsPageRate();

	public List<Object[]> getGdRmsSchemes();

	public List<Object[]> getRmsRatesList();

	public List<Object[]> getApprovalMatrix();
	
	public GdDaClassifiedCategory getDaCategory(String categoryType);
	
	public GdDaClassifiedCategory getDaCategoryById(Integer id);

	public DaSizes getDaSizes(String size, Integer categoryType);

	public DaSizes getDaSizesById(Integer sizeId);

	public List<Object[]> getAllDaSizes();

	public List<Object[]> getDaRatesList();


}
