package com.portal.gd.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.portal.clf.models.ClfRatesModel;
import com.portal.common.models.DocumentsModel;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.common.service.DocumentService;
import com.portal.constants.AllowedDependentRmsMasters;
import com.portal.constants.AllowedDisplayAdMasters;
import com.portal.constants.AllowedMasters;
import com.portal.constants.AllowedRmsMasters;
import com.portal.datasecurity.DataSecurityService;
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
import com.portal.gd.service.GlobalDataService;
import com.portal.gd.to.GdSettingConfigsTo;
import com.portal.org.service.OrgService;
import com.portal.reports.to.ReportsRequest;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;

import io.swagger.annotations.ApiParam;

/**
 * Global Data API controller
 * 
 * @author Sathish Babu D
 *
 */
@Controller
@PropertySource(value = { "classpath:/com/portal/messages/messages.properties" })
public class GlobalDataApiController implements GlobalDataApi {

	private static final Logger logger = LogManager.getLogger(GlobalDataApiController.class);

	@Autowired(required = true)
	private GlobalDataService gdService;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private LoggedUserContext userContext;

	@Autowired(required = true)
	private DataSecurityService dataSecurityService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired(required = true)
	private OrgService orgService;
	
	@Autowired 
	private DocumentService documentService;

	/**
	 * Get specific value from the list object in the form of key and value pair
	 * 
	 * @param obj_name
	 * @param obj_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getListObjValueById(
			@ApiParam(value = "List object name", required = true) @PathVariable(value = "obj_name", required = true) String obj_name,
			@ApiParam(value = "List object id", required = true) @PathVariable(value = "obj_id", required = true) String obj_id) {

		String METHOD_NAME = "getListObjValueById";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = gdService.getListValuesByCriteria(obj_name, obj_id);

		} catch (Exception e) {

			logger.error("Error while getting user details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get all values from the list object in the form of key and value pair
	 * 
	 * @param obj_name
	 * @return
	 */
	@Override
	public ResponseEntity<?> getListObjValues(
			@ApiParam(value = "List object name", required = true) @PathVariable(value = "obj_name", required = true) String obj_name,
			@ApiParam(value = "Filter Key", required = false) @RequestParam(value = "filterKey", required = false) String filterKey,
			@ApiParam(value = "Filter Value", required = false) @RequestParam(value = "filterValue", required = false) String filterValue,
			@ApiParam(value = "isMapping", required = false) @RequestParam(value = "isMapping", required = false) boolean isMapping) {

		String METHOD_NAME = "getListObjValueById";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			// apiResp = gdService.getListValuesByCriteria(obj_name, null);
			apiResp = gdService.getListValues(obj_name, filterKey, filterValue, isMapping);

		} catch (Exception e) {

			logger.error("Error while getting user details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get the left side menu of application
	 * 
	 * @param org_id
	 * @param bp_id
	 * @param device_id
	 * @return
	 */
	@Override
	public ResponseEntity<?> getAccessObjects(
			@ApiParam(value = "Device id", required = false) @RequestParam(value = "device_id", required = false) Integer device_id) {

		String METHOD_NAME = "getAccessObjects";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = gdService.getAccessObjects(commonService.getRequestHeaders().getOrgId(), commonService.getRequestHeaders().getOrgOpuId(), device_id);

		} catch (Exception e) {

			logger.error("Error while getting access objects details: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get Configuration values
	 * 
	 * @param org_id
	 * @param bp_id
	 * @param data_type
	 * @param parent_group
	 * @return
	 */
	@Override
	public ResponseEntity<?> getConfigValues(
			@ApiParam(value = "Config group", required = false) @RequestParam(value = "group", required = false) String group,
			@ApiParam(value = "Parent Group", required = false) @RequestParam(value = "parent_group", required = false) String parent_group,
			@ApiParam(value = "Page number", required = false) @RequestParam(value = "pg_num", required = false) Integer pg_num,
			@ApiParam(value = "Page size", required = false) @RequestParam(value = "pg_size", required = false) Integer pg_size,
			@ApiParam(value = "Add Field3", required = false) @RequestParam(value = "add_field3", required = false) String addField3,
			@ApiParam(value = "Add Field7", required = false) @RequestParam(value = "add_field7", required = false) String addField7,
			@ApiParam(value = "Add Field2", required = false) @RequestParam(value = "add_field2", required = false) String addField2,
			@ApiParam(value = "Add Field1", required = false) @RequestParam(value = "add_field1", required = false) String addField1,
			@ApiParam(value = "Multi BpIds", required = false) @RequestParam(value = "multiBpIds", required = false) String multiBpIds) {

		String METHOD_NAME = "getConfigValues";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {
			// apiResp = dataSecurityService.dataSecurityVerification(new
			// DataSecurity(org_id, null, access_obj_id));

			// if (apiResp.getStatus() == 0) {
			apiResp = gdService.getConfigValues(commonService.getRequestHeaders().getOrgId(), commonService.getRequestHeaders().getOrgOpuId(), group, parent_group, pg_size, pg_num, addField3,
					addField7, addField2, multiBpIds, addField1);
			// }

		} catch (Exception e) {

			logger.error("Error while getting Configuration values : " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Create or Update Configuration Values
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> createOrUpdateConfigValues(
			@ApiParam(value = "Config values", required = true) @RequestBody ListOfConfigValues payload) {

		String METHOD_NAME = "createOrUpdateConfigValues";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.createOrUpdateConfigValues(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Create or Update Configuration Group
	 * 
	 * @param payload
	 * @return
	 */
	@Override
	public ResponseEntity<?> createOrUpdateConfigGroup(
			@ApiParam(value = "Config values", required = true) @RequestBody ListOfConfigGroup payload) {

		String METHOD_NAME = "CreateOrUpdateConfigGroup";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.createOrUpdateConfigGroup(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	/**
	 * Get Configuration Group Details
	 *
	 * @return
	 */
	@Override
	public ResponseEntity<?> getConfigGroup() {

		String METHOD_NAME = "getConfigGroup";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = gdService.getConfigGroup();

		} catch (Exception e) {

			logger.error("Error while getting Configuration values : " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getConfigValuesWithFilter(
			@ApiParam(value = "Config values", required = true) @RequestBody ConfigValues payload) {

		String METHOD_NAME = "getConfigValuesWithFilter";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {

			apiResp = gdService.getConfigValuesWithFilter(payload);

		} catch (Exception e) {

			logger.error("Error while getting Configuration values : " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;

	}

	@Override
	public ResponseEntity<?> exportConfigGroupReport(
			@ApiParam(value = "Config group", required = true) @RequestParam(value = "group", required = true) String group,
			HttpServletResponse response) {

		String METHOD_NAME = "exportConfigGroupReport";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		try {
			apiResp = gdService.exportConfigGroupReport(group, commonService.getRequestHeaders().getOrgId(), commonService.getRequestHeaders().getOrgOpuId());

			if (apiResp.getStatus() == 0) {

				// XSSFWorkbook workbook = (XSSFWorkbook)apiResp.getData();
				byte[] fileDataBytes = (byte[]) apiResp.getData();
				// byte[] fileDataBytes = (byte[]) workbook.getBytes();

				String fileName = "Config value report " + new Date() + ".xlsx";

				response.setHeader("Content-type", " application/vnd.openxml; charset=utf-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName.replaceAll(" ", "_"));
				response.setHeader("Content-Length", String.valueOf(fileDataBytes.length));

				FileCopyUtils.copy(fileDataBytes, response.getOutputStream());
			}

		} catch (Exception e) {

			logger.error("Error while downloading Configuration values : " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> uploadConfigData(HttpServletRequest request,
			@ApiParam(value = "Plant ID", required = true) @PathVariable("plantId") String plantId,
			@ApiParam(value = "Group Id", required = true) @RequestParam("groupId") String groupId) {
		LoggedUser loggedUser = userContext.getLoggedUser();
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			if (loggedUser.getUserId() != null) {
				if (plantId != null) {
					// Data Access Validation Start

					// Data Access Validation End
					MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
					MultipartFile uploadfile = multipartHttpServletRequest.getFile("dlFile");

					apiResp = gdService.generateMasterDetails(uploadfile, plantId, groupId);
				}
			}
		} catch (Exception e) {

			logger.error("Error while updating DashBoard data request: " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		return respObj;
	}

	/**
	 * Get SMTP Configuration Details
	 *
	 * @return
	 */
	@Override
	public ResponseEntity<?> getSmtpConfig() {

		String METHOD_NAME = "getSmtpConfig";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.getSmtpConfigDetails();

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> updateSmtpConfig(
			@ApiParam(value = "Smtp config", required = true) @RequestBody GdSettingConfigsTo payload) {

		String METHOD_NAME = "updateSmtpConfig";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.updateSmtpConfigDetails(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> sendTestMail(
			@NotNull @ApiParam(value = "To mails", required = true) @RequestParam(value = "to_mails", required = true) String to_mails,
			@NotNull @ApiParam(value = "CC mails", required = false) @RequestParam(value = "cc_mails", required = false) String cc_mails,
			@NotNull @ApiParam(value = "BCC mails", required = false) @RequestParam(value = "bcc_mails", required = false) String bcc_mails) {
		String METHOD_NAME = "updateSmtpConfig";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.sendTestMail(to_mails, cc_mails, bcc_mails, commonService.getRequestHeaders().getOrgId());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> getMasterData(
			@NotNull @ApiParam(value = "Master type", required = true) @PathVariable(value = "master_type", required = true) String master_type){
		String METHOD_NAME = "getMasterData";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.getMasterData(master_type, null);
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> createOrUpdateMasterData(MasterDataUpdateRequest payload) {
		String METHOD_NAME = "createOrUpdateMasterData";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.createOrUpdateMasterdata(payload);
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getAllowedMasters() {
		String METHOD_NAME = "getDealerListView";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		apiResp.setStatus(0);
		apiResp.setData(Arrays.stream(AllowedMasters.values())
	      .collect(Collectors.toMap(Function.identity(),AllowedMasters::getValue)));
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	
	@Override
	public ResponseEntity<?> getAllowedMastersForRms(AllowedMasterModel masterType) {
		String METHOD_NAME = "getAllowedMasters";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		apiResp.setStatus(0);
			apiResp.setData(Arrays.stream(AllowedRmsMasters.values())
				      .collect(Collectors.toMap(Function.identity(),AllowedRmsMasters::getValue)));	
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	public static void main(String a[]){
		AllowedMasters[] aa =AllowedMasters.values();
		for(AllowedMasters aaa : aa){
			System.out.println (aaa.name()+"-"+ aaa.getValue());
		}
		

	}

	@Override
	public ResponseEntity<?> getSettings(List<String> group) {
		String METHOD_NAME = "getSettings";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.getSettingsData(group);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> createOrUpdateSettings(@NotNull GdSettingsDetails payload) {
		String METHOD_NAME = "createOrUpdateSettings";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		List<GdSettingsDetails> settingsDetails = new ArrayList<>();
		settingsDetails.add(payload);
		apiResp = gdService.createOrUpdateSettingsData(settingsDetails);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> deleteSettingData(@NotNull GdSettingsDetails payload) {
		String METHOD_NAME = "deleteSettingData";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		List<Integer> settingIds = new ArrayList<>();
		settingIds.add(payload.getSettingId());
		apiResp = gdService.deleteSettingData(settingIds);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getGdStates() {
		String METHOD_NAME = "getGdStates";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.getGdStates();
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getGdCity() {
		String METHOD_NAME = "getGdStates";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.getGdCity();
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> getMasterDataByFilter(@NotNull String master_type, Map<String, Object> payload) {
		String METHOD_NAME = "getMasterDataByFilter";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.getMasterData(master_type, payload);
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getTemplateHeadersSettings() {
		String METHOD_NAME = "getTemplateHeadersSettings";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.getTemplateHeadersSettings();
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> generateExcelForHeaders(ReportsRequest payload, HttpServletRequest request,
			HttpServletResponse response) {
		String METHOD_NAME = "generateExcelForHeaders";
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		if (payload.getHeadersData() != null && !payload.getHeadersData().isEmpty()) {
			apiResp = gdService.genrateExcelForHeaders(payload,response,loggedUser);
			apiResp.setStatus(0);
		} else {
			apiResp.setStatus(1);
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> uploadMasterData(String type,String action,HttpServletRequest request) {
		String METHOD_NAME = "uploadMasterData";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.uploadMasterData(type,action,request);
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getBookingUnits() {
		String METHOD_NAME = "getBookingUnits";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.getBookingUnits();
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getClfRatesList() {
		String METHOD_NAME = "getClfRatesList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.getClfRatesList();
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> addClfRates(ClfRatesModel payload) {
		String METHOD_NAME = "addClfRates";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.addClfRates(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> DeleteClfRates(ClfRatesModel payload) {
		String METHOD_NAME = "DeleteClfRates";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.DeleteClfRates(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getMastersForRmsDependency(@NotNull AllowedMasterModel payload) {
		String METHOD_NAME = "getAllowedMastersForRmsDependency";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		apiResp=gdService.getRmsDependentMasterData(payload);
		apiResp.setStatus(0);
//			apiResp.setData(Arrays.stream(AllowedDependentRmsMasters.values())
//				      .collect(Collectors.toMap(Function.identity(),AllowedDependentRmsMasters::getValue)));
		apiResp.setData(apiResp.getData());
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getAllowedMastersForRmsDependency() {

		String METHOD_NAME = "getAllowedMastersForRmsDependency";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		apiResp.setStatus(0);
			apiResp.setData(Arrays.stream(AllowedDependentRmsMasters.values())
				      .collect(Collectors.toMap(Function.identity(),AllowedDependentRmsMasters::getValue)));
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> createOrUpdateMasterDependatData(GdMasterDependentModel payload) {

		String METHOD_NAME = "createOrUpdateMasterDependatData";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.createOrUpdateDependatRmsMasterdata(payload);
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> createOrUpdateGdDaClassifiedCategory(@NotNull GdDaClassifiedCategoryModel payload) {

		String METHOD_NAME = "createOrUpdateSettings";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.createOrUpdateDaClassifiedCategory(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}
	
	@Override
	public ResponseEntity<?> getDisplayAdSizes() {

		String METHOD_NAME = "getDisplayAdSizes";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.getDisplayAdSizes(commonService.getRequestHeaders());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}
	
	@Override
	public ResponseEntity<?> createOrUpdateGdDaSizes(@NotNull GdDaSizesModel payload) {

		String METHOD_NAME = "createOrUpdateGdDaSizes";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.createOrUpdateGdDaSizes(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> getDisplayAdCategory() {

		String METHOD_NAME = "getDisplayAdCategory";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.getClassifiedCategory(commonService.getRequestHeaders());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> getAllowedMastersForDisplayAds() {
		String METHOD_NAME = "getAllowedMastersForDisplayAds";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		apiResp.setStatus(0);
			apiResp.setData(Arrays.stream(AllowedDisplayAdMasters.values())
				      .collect(Collectors.toMap(Function.identity(),AllowedDisplayAdMasters::getValue)));	
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> createOrUpdateGdDaTemplates(@NotNull DaTemplateModel payload) {

		String METHOD_NAME = "createOrUpdateGdDaSizes";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.createOrUpdateDaTemplates(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> getDisplayAdTemplates() {

		String METHOD_NAME = "getDisplayAdTemplates";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.getDaTemplates(commonService.getRequestHeaders());

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> uploadSSPDocUpload(HttpServletRequest request) {
		String METHOD_NAME = "amDocsUpload";

        logger.info("Entered into the method: " + METHOD_NAME);
        Date respFrmTs = new Date();
        ResponseEntity<GenericApiResponse> respObj = null;
        LoggedUser loggedUser = userContext.getLoggedUser();
        GenericApiResponse apiResp = new GenericApiResponse();
        DocumentsModel rmsDocumentsModel = new DocumentsModel();
//        rmsDocumentsModel.setDocIds(docId);
        rmsDocumentsModel.setLoggedUser(loggedUser);
//        rmsDocumentsModel.setDocType(attType);
//        rmsDocumentsModel.setDealerAchDataId(achievementId);
//        rmsDocumentsModel.setDealerAuditId(dealerAuditId);

                if (request instanceof MultipartHttpServletRequest) {
                    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                    // Taking the Multiple files from upload document request
                    List<MultipartFile> uploadMultipleFiles = multipartHttpServletRequest.getFiles("files");
                    logger.info("Number of documnets requested to upload: " + uploadMultipleFiles.size());
                    if (uploadMultipleFiles != null && !uploadMultipleFiles.isEmpty()) {
                    	rmsDocumentsModel.setMultipartFileAtachments(uploadMultipleFiles);
                    }else {
                    	apiResp.setMessage("File Not Found");
                    	respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
                    	return respObj;
                    }
                }
                apiResp = documentService.uploadAdditionalDocuments(rmsDocumentsModel);
//                if (action == 1) {
//                    apiResp = documentService.uploadAdditionalDocuments(auditDocumentsModel);
//                } else if (action == 2) {
//                    apiResp = auditDocumentService.updateUploadedDocuments(auditDocumentsModel);
//                } else if (action == 5) {
//                    apiResp = auditDocumentService.uploadUserProfile(auditDocumentsModel);
//                } else {
//                    apiResp = auditDocumentService.removeUploadedDocuments(auditDocumentsModel);
//                }

        respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

        logger.info("Exit from the method: " + METHOD_NAME);

        return respObj;
	}

	@Override
	public ResponseEntity<?> getUsers(@NotNull @ApiParam(value = "Action ", required = true) @RequestParam(value = "action", required = true) String action,
			@NotNull @ApiParam(value = "Type ", required = false) @RequestParam(value = "type", required = false) String type,
			@NotNull @ApiParam(value = "Active Or DeActive Users", required = false) @RequestParam(value = "act_deact", required = false) String act_deact) {


		String METHOD_NAME = "getUsers";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = orgService.getUsers(commonService.getRequestHeaders().getOrgOpuId(),
				"1000", action, type, act_deact);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> getDaSizes() {


		String METHOD_NAME = "getDaSizes";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.getAllDaSizes();

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	
	}

	@Override
	public ResponseEntity<?> getDaRatesList() {
		String METHOD_NAME = "getDaRatesList";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();
		
		apiResp = gdService.getDaRatesList();
		
		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> addDaRates(ClfRatesModel payload) {
		String METHOD_NAME = "addDaRates";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.addDaRates(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> deleteDaRates(ClfRatesModel payload) {
		String METHOD_NAME = "deleteDaRates";

		logger.info("Entered into the method: " + METHOD_NAME);

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		apiResp = gdService.deleteDaRates(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

}
