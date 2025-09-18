package com.portal.gd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.portal.clf.models.ClfRatesModel;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.gd.models.AllowedMasterModel;
import com.portal.gd.models.ConfigValues;
import com.portal.gd.models.DaTemplateModel;
import com.portal.gd.models.GdDaClassifiedCategoryModel;
import com.portal.gd.models.GdDaSizesModel;
import com.portal.gd.models.GdMasterDependentModel;
import com.portal.gd.models.GdSettingsDetails;
import com.portal.gd.models.ListOfConfigGroup;
import com.portal.gd.models.ListOfConfigValues;
import com.portal.gd.models.MasterDataUpdateRequest;
import com.portal.gd.to.GdSettingConfigsTo;
import com.portal.reports.to.ReportsRequest;
import com.portal.security.model.LoggedUser;

/**
 * Global data service
 * 
 * @author Sathish Babu D
 *
 */
public interface GlobalDataService {

	/**
	 * Get List object values by criteria
	 * 
	 * @param listObj
	 * @param listObjId
	 * @return
	 */
	public GenericApiResponse getListValuesByCriteria(String listObj, String listObjId);

	/**
	 * Get the left side menu of application
	 * 
	 * @param orgId
	 * @param bpId
	 * @param deviceId
	 * @return
	 */
	public GenericApiResponse getAccessObjects(String orgId, String bpId, Integer deviceId);

	/**
	 * Get Configuration values
	 * 
	 * @param orgId
	 * @param bpId
	 * @param dataType
	 * @param parentGroup
	 * @param multiBpIds 
	 * @param addField1 
	 * @return
	 */
	public GenericApiResponse getConfigValues(String orgId, String bpId, String group, String parentGroup,Integer pgSize,Integer pgNum,String addField3,String addField7
			,String addField2, String multiBpIds, String addField1);

	/**
	 * Create or Update Configuration Values
	 * 
	 * @param payload
	 * @return
	 */
	public GenericApiResponse createOrUpdateConfigValues(ListOfConfigValues payload);

	/**
	 * Create or Update Configuration Group
	 * 
	 * @param payload
	 * @return
	 */
	public GenericApiResponse createOrUpdateConfigGroup(ListOfConfigGroup payload);

	/**
	 * Get Configuration Group Details
	 *
	 * @return
	 */
	public GenericApiResponse getConfigGroup();
	
	
	public GenericApiResponse generateMasterDetails(MultipartFile uploadfile, String plantId,String groupId);
	
	
	public GenericApiResponse getConfigValuesWithFilter(ConfigValues payload);
	
	public GenericApiResponse exportConfigGroupReport(String configGrpType,String orgId,String bp_id);
		
	public GenericApiResponse getListValues(String obj_name, String filterKey, String filterValue,boolean isMapping);

	public GenericApiResponse getMasterData(@NotNull String master_type, Map<String, Object> filter);

	public GenericApiResponse getSmtpConfigDetails();
	
	public GenericApiResponse updateSmtpConfigDetails(GdSettingConfigsTo smtpConfigsTo);
	
	public GenericApiResponse sendTestMail(String toMails,String ccMails,String bccMails,String orgId);

	public GenericApiResponse createOrUpdateMasterdata(MasterDataUpdateRequest masterDataUpdateRequest);

	public GenericApiResponse getSettingsData(List<String> sGroup);
	
	public GenericApiResponse createOrUpdateSettingsData(List<GdSettingsDetails> gdSettingsDetails);
	
	public GenericApiResponse deleteSettingData(List<Integer> settingIds);

	public GenericApiResponse getGdStates();

	public GenericApiResponse getGdCity();

	public GenericApiResponse getTemplateHeadersSettings();

	public GenericApiResponse genrateExcelForHeaders(ReportsRequest payload, HttpServletResponse response, LoggedUser loggedUser);

	public GenericApiResponse uploadMasterData(String type, String action, HttpServletRequest request);

	public GenericApiResponse getBookingUnits();

	public GenericApiResponse getClfRatesList();

	public GenericApiResponse addClfRates(ClfRatesModel payload);

	public GenericApiResponse DeleteClfRates(ClfRatesModel payload);

	public GenericApiResponse getRmsDependentMasterData(@NotNull AllowedMasterModel payload);

	public GenericApiResponse createOrUpdateDependatRmsMasterdata(GdMasterDependentModel payload);

	public GenericApiResponse createOrUpdateDaClassifiedCategory(@NotNull GdDaClassifiedCategoryModel payload);
	
	public GenericApiResponse getDisplayAdSizes(GenericRequestHeaders requestHeaders);
	
	public GenericApiResponse createOrUpdateGdDaSizes(@NotNull GdDaSizesModel payload);

	public GenericApiResponse getClassifiedCategory(GenericRequestHeaders requestHeaders);


	public GenericApiResponse createOrUpdateDaTemplates(@NotNull DaTemplateModel payload);

	public GenericApiResponse getDaTemplates(GenericRequestHeaders requestHeaders);

	public GenericApiResponse getAllDaSizes();

	public GenericApiResponse getDaRatesList();

	public GenericApiResponse addDaRates(ClfRatesModel payload);

	public GenericApiResponse deleteDaRates(ClfRatesModel payload);

}
