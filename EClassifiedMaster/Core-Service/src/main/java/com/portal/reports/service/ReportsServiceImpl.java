/**
 * 
 */
package com.portal.reports.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.basedao.IBaseDao;
import com.portal.clf.models.Classifieds;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.clf.models.SSPSummaryModel;
import com.portal.clf.service.ClassifiedService;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.constants.GeneralConstants;
import com.portal.constants.MasterData;
import com.portal.da.models.DisplayAdsModel;
import com.portal.da.service.DisplayService;
import com.portal.gd.entities.GdEditionType;
import com.portal.gd.entities.GdReportsMaster;
import com.portal.gd.service.GlobalDataService;
import com.portal.org.models.ListOfOrgUsers;
import com.portal.org.models.OrgUserDetails;
import com.portal.org.service.OrgService;
import com.portal.reports.to.ReportDownloadModel;
import com.portal.reports.to.ReportsCommonModel;
import com.portal.reports.to.ReportsGaller;
import com.portal.reports.to.ReportsGalleryMaster;
import com.portal.reports.utility.GenericExcelReportTo;
import com.portal.reports.utility.GenericExcelReportUtility;
import com.portal.repository.GdClassifiedEditionTypeRepo;
import com.portal.repository.GdReportsMasterRepo;
import com.portal.rms.model.RmsListViewModel;
import com.portal.rms.service.RmsService;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;
import com.portal.user.service.UserService;
import com.portal.user.to.UserTo;

/**
 * @author Incresol
 *
 */
@Service
public class ReportsServiceImpl implements ReportsService {

	private static final Logger logger = LogManager.getLogger(ReportsServiceImpl.class);
	
	public static String DIR_PATH_DOCS =  "/SEC/DOCS/";
	public static String DIR_PATH_VIN_LIST =  "/SEC/VINERR/";
	public static String DIR_PATH_GD_UPD =  "/SEC/GDUPD/";
	public static String DIR_PATH_RPT =  "/SEC/RPT/";

	@Autowired(required = true)
	private Environment prop;

	@Autowired
	private GdReportsMasterRepo gdReportsMasterRepo;
	
	@Autowired
	private GlobalDataService globalDataService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private IBaseDao baseDao;
	
	private Path fileStorageLocation;
	
	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired
	private ClassifiedService classifiedService;
	
	@Autowired
	private DisplayService displayService;
	
	@Autowired
	private GdClassifiedEditionTypeRepo editionTypeRepo;
	
	@Autowired
	private RmsService rmsService;
	
	@Autowired
	private CommonService commonService;
	
	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private UserService userService;
	
	public String getDIR_PATH_DOCS() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_DOCS;
	}

	public String getDIR_PATH_VIN_LIST() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_VIN_LIST;
	}

	public String getDIR_PATH_GD_UPD() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_GD_UPD;
	}

	public String getDIR_PATH_RPT() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_RPT;
	}

	public GenericApiResponse reportDownload(ReportsCommonModel reportsCommonModel, HttpServletResponse response) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			GenericExcelReportTo genericExcelReportTo = new GenericExcelReportTo();
			LinkedHashMap<String, List<Map<String, Object>>> sheetDataMap = new LinkedHashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> data = new ArrayList<>();
			data = populateReportDataAndTemplateName(reportsCommonModel, genericExcelReportTo, data);
			sheetDataMap.put("Data", data);
			if (!sheetDataMap.isEmpty()) {
				genericExcelReportTo.setSheetDataMap(sheetDataMap);
				if (reportsCommonModel.getReportsRequest() != null && "SAMPLE".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getReportShortId())){
					excelReportCreator(genericExcelReportTo, reportsCommonModel);
					System.out.println("Excel report generation starts");
				}else{
					excelByteArray(genericExcelReportTo, response, "REPORT", genericApiResponse);
				}
				genericApiResponse.setStatus(0);
			} else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setErrorcode("GEN_004");
				genericApiResponse.setMessage(prop.getProperty("GEN_004"));
				System.out.println("Sheet Data empty");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting reports" + e.getMessage());
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
			genericApiResponse.setStatus(0);
			genericApiResponse.setMessage("Unable to process your request");
			updateReportMasterWhenEmptyData(reportsCommonModel);
		}
		return genericApiResponse;
	}

	public void excelDownload(GenericExcelReportTo genericExcelReportTo, HttpServletResponse response,
			String fileName) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			GenericExcelReportUtility utility = new GenericExcelReportUtility();
			XSSFWorkbook hssfWorkbook = utility.getExcel(genericExcelReportTo);
			hssfWorkbook.write(out);
			byte[] fileDataBytes = out.toByteArray();
			out.close();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
			FileCopyUtils.copy(fileDataBytes, response.getOutputStream());
			out.close();
			hssfWorkbook.close();
		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void excelByteArray(GenericExcelReportTo genericExcelReportTo, HttpServletResponse response,
			String fileName,GenericApiResponse genericApiResponse) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			GenericExcelReportUtility utility = new GenericExcelReportUtility();
			XSSFWorkbook hssfWorkbook = utility.getExcel(genericExcelReportTo);
			hssfWorkbook.write(out);
			byte[] fileDataBytes = out.toByteArray();
			out.close();
			genericApiResponse.setData(fileDataBytes);
			genericApiResponse.setMessage(fileName + ".xls");
			out.close();
			hssfWorkbook.close();
		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
	}
	
	public GenericApiResponse getReportsMaster(ReportsCommonModel reportsCommonModel){
		GenericApiResponse apiResp = new GenericApiResponse();
		ReportsCommonModel reportsCommonModel2 = new ReportsCommonModel();
		BeanUtils.copyProperties(reportsCommonModel, reportsCommonModel2);
		List<GdReportsMaster> reportsMasters = gdReportsMasterRepo.getReportsMaster();
		Map<String, List<ReportsGaller>> reportsGroup = new HashMap<>();
		if(reportsMasters != null){
			for(GdReportsMaster repMs : reportsMasters){
				if(reportsGroup.containsKey(repMs.getRptGroup())){
					ReportsGaller reportsGaller = new ReportsGaller();
					reportsGaller.setReportName(repMs.getRptName());
					reportsGaller.setReportDesc(repMs.getRptDesc());
					reportsGaller.setReportShortId(repMs.getRptShortId());
					reportsGaller.setAccessObjId(repMs.getAccessObjId());
					reportsGaller.setReportGenDate(repMs.getRptGenDate());
					reportsGaller.setReportGenStatus(repMs.getRptStatus());
					reportsGroup.get(repMs.getRptGroup()).add(reportsGaller);
				}else{
					List<ReportsGaller> rg = new ArrayList<>();
					ReportsGaller reportsGaller = new ReportsGaller();
					reportsGaller.setReportName(repMs.getRptName());
					reportsGaller.setReportDesc(repMs.getRptDesc());
					reportsGaller.setReportShortId(repMs.getRptShortId());
					reportsGaller.setAccessObjId(repMs.getAccessObjId());
					reportsGaller.setReportGenDate(repMs.getRptGenDate());
					reportsGaller.setReportGenStatus(repMs.getRptStatus());
					rg.add(reportsGaller);
					reportsGroup.put(repMs.getRptGroup(), rg);
				}
			}
		}
		List<ReportsGalleryMaster> galleryMasters = new ArrayList<>();
		for(Map.Entry<String, List<ReportsGaller>> map : reportsGroup.entrySet()){
			ReportsGalleryMaster galleryMaster = new ReportsGalleryMaster(); 
			galleryMaster.setGroupName(map.getKey());
			galleryMaster.setReportsGallers(map.getValue());
			galleryMasters.add(galleryMaster);
		}
		apiResp.setData(galleryMasters);
		apiResp.setStatus(1);
		return apiResp;
	}
	
	public void excelReportCreator(GenericExcelReportTo genericExcelReportTo, ReportsCommonModel reportsCommonModel) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GenericExcelReportUtility utility = new GenericExcelReportUtility();
			XSSFWorkbook hssfWorkbook = utility.getExcel(genericExcelReportTo);
			System.out.println("Report Generation Completed");
			hssfWorkbook.write(bos);
			byte[] barray = bos.toByteArray();
			InputStream is = new ByteArrayInputStream(barray);
			String fileUrl = uploadReportFile(is, "RAW_DATA_EXT_REPORT.xls");
			System.out.println("Report File Upload Completed");
			if (fileUrl != null) {
				ReportsCommonModel reportsCommonModel2 = new ReportsCommonModel();
				reportsCommonModel2.setRptShortId(reportsCommonModel.getReportsRequest().getReportShortId());
				reportsCommonModel2.setRptStatus(GeneralConstants.COMPLETED);
				reportsCommonModel2.setRptUrl(fileUrl);
				boolean flag = updateReportsMasterStatusByShortId(reportsCommonModel2);
				System.out.println("Report Master Status Update Completed");
			}
			bos.close();
			is.close();
			hssfWorkbook.close();
		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
	}
	@Async
	public void reportDownloadAsync(ReportsCommonModel reportsCommonModel, HttpServletResponse response){
		CompletableFuture.runAsync(() -> {
			reportDownload(reportsCommonModel, response);
		});
		ReportsCommonModel reportsCommonModel2 = new ReportsCommonModel();
		reportsCommonModel2.setRptShortId(reportsCommonModel.getReportsRequest().getReportShortId());
		reportsCommonModel2.setRptStatus(GeneralConstants.PENDING);
		updateReportsMasterStatusByShortId(reportsCommonModel2);
	}
	
	public ReportDownloadModel downloadGeneratedReport(String rptShortId, GenericApiResponse genericApiResponse) {
		ReportsCommonModel reportsCommonModel2 = new ReportsCommonModel();
		reportsCommonModel2.setRptShortId(rptShortId);
		List<GdReportsMaster> rptMasterList = gdReportsMasterRepo.getReportsMasterByShortId(reportsCommonModel2.getRptShortId());
		ReportDownloadModel ReportDownloadModel = new ReportDownloadModel();
		if (!rptMasterList.isEmpty()) {
			Resource resource = downloadReportFileResource(rptMasterList.get(0).getRptUrl());
			// Try to determine file's content type
			String contentType = "application/octet-stream";
			ReportDownloadModel.setContentType(contentType);
			ReportDownloadModel.setResource(resource);
			ReportDownloadModel.setFileName("REPORT.xls");
		}
		return ReportDownloadModel;
	}
	
	public boolean updateReportMasterWhenEmptyData(ReportsCommonModel reportsCommonModel) {
		ReportsCommonModel reportsCommonModel2 = new ReportsCommonModel();
		reportsCommonModel2.setRptShortId(reportsCommonModel.getReportsRequest().getReportShortId());
		reportsCommonModel2.setRptStatus("ERROR");
		reportsCommonModel2.setRptUrl(null);
		return updateReportsMasterStatusByShortId(reportsCommonModel2);
	}
	
	/**
	 * Download generated report from file system
	 * 
	 * @param file path
	 * 
	 */
	public Resource downloadReportFileResource(String fileUrl) {
		this.fileStorageLocation = Paths.get(getDIR_PATH_RPT()).toAbsolutePath().normalize();
		Path filePath = this.fileStorageLocation.resolve(fileUrl).normalize();
		try {
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Download generated report from file system
	 * 
	 * @param file path
	 * 
	 */
	public byte[] downloadReportFile(String fileUrl) {
		this.fileStorageLocation = Paths.get(getDIR_PATH_RPT()).toAbsolutePath().normalize();
		Path filePath = this.fileStorageLocation.resolve(fileUrl).normalize();
		try {
			Resource resource = new UrlResource(filePath.toUri());
			return IOUtils.toByteArray(resource.getInputStream());
		} catch (Exception e) {
			return null;
		}

	}
	
	public boolean updateReportsMasterStatusByShortId(ReportsCommonModel reportsCommonModel){
		List<GdReportsMaster> rptList = gdReportsMasterRepo.getReportsMasterByShortIdAndStatus(reportsCommonModel.getRptShortId());
		rptList.get(0).setRptStatus(reportsCommonModel.rptStatus);
		rptList.get(0).setRptUrl(reportsCommonModel.getRptUrl());
		if(reportsCommonModel.getRptUrl() != null)
			rptList.get(0).setRptGenDate(new Date());
		else
			rptList.get(0).setRptGenDate(null);
		gdReportsMasterRepo.save(rptList.get(0));
		return true;
	}
	
	public String uploadReportFile(InputStream inputStream, String fileName) {
		String fileUrl = "REPORT_"+UUID.randomUUID().toString()+".xls";
		File fileToSave = new File(getDIR_PATH_RPT() + fileUrl);
		try {
			Files.copy(inputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			return null;
		}
		return fileUrl;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> populateReportDataAndTemplateName(ReportsCommonModel reportsCommonModel,
			GenericExcelReportTo genericExcelReportTo, List<Map<String, Object>> data) {
		LoggedUser loggedUser = userContext.getLoggedUser();
		List<GdReportsMaster> gdReportsMasters = gdReportsMasterRepo
				.getReportsMasterByShortId(reportsCommonModel.getReportsRequest().getReportShortId());
		if (!gdReportsMasters.isEmpty()) {
			genericExcelReportTo.setTemplatePath("genExcelTemp/" + gdReportsMasters.get(0).getRptName() + ".xlsx");
			if("DA_UMUSERS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())){
				Query query = entityManager.createNativeQuery(
						"select uu.logon_id , uu.first_name , uu.middle_name , uu.last_name, uu.email, uu.mobile, case when (is_deactivated )  THEN 'Deactivated' else 'Active' END, gr.region_name, uor.role_desc, UPPER(gdm.dealer_name) as dealer_name from um_users uu left join gd_region gr on uu.region = gr.id inner join um_org_users uou on uou.user_id = uu.user_id left join gd_dealer_master gdm on gdm.dealer_id = uou.dealer_id and gdm.mark_as_delete = false inner join um_org_roles uor on uor.role_id = uou.role_id where uu.mark_as_delete = false and is_deactivated  = true");
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				return nativeQuery.getResultList();
			} else if("UMUSERS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())){
				Query query = entityManager.createNativeQuery(
						"select uu.logon_id , uu.first_name , uu.middle_name , uu.last_name, uu.email, uu.mobile, case when (is_deactivated )  THEN 'Deactivated' else 'Active' END, uor.role_desc from um_users uu inner join um_org_users uou on uou.user_id = uu.user_id  inner join um_org_roles uor on uor.role_id = uou.role_id where uu.mark_as_delete = false  and is_deactivated  = false");
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				return nativeQuery.getResultList();
			} if("DASHBOARD_SUMMARY".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())){
				return getDashboardData(reportsCommonModel);
			} if("MY_CLASSIFIEDS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())){
				return getMyClassifiedsList(loggedUser, reportsCommonModel);
			} if("DOWNLOAD_STATUS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())){
				return getDownloadStatusList(loggedUser, reportsCommonModel);
			}
			if("RMS_LIST".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())){
				return getRmsList(loggedUser, reportsCommonModel);
			} 
			if("MY_DISPLAY_ADS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())){
				return getDisplayAdsList(loggedUser, reportsCommonModel);
			} 
			if("VENDOR_COMMISSION".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
				return getVendorCommission(loggedUser , reportsCommonModel);
			}
			if("SSP_PENDING_USERS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
				return getSspPendingUsers(commonService.getRequestHeaders(),loggedUser);
			}
			if("SSP_ALL_VENDORS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
				return getAllSSPVendors(commonService.getRequestHeaders(),loggedUser);
			}
			if("SSP_SUMMARY".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
				return getSSPSummary(commonService.getRequestHeaders(),loggedUser,reportsCommonModel);
			}
			if("SSP_VENDORS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
				return getSSPAllVendors(commonService.getRequestHeaders(),loggedUser,reportsCommonModel);
			}
			else if (MasterData.valueOf(reportsCommonModel.getReportsRequest().getMasterDataId()) != null) {
				Query query = null;
				String orderBy = null;
				if ("GDCLASSIFIEDADSSUBTYPES".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "ads_sub_type";
				}
				if ("GDCLASSIFIEDADSTYPES".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "ads_type";
				}
				if ("GDCLASSIFIEDCATEGORY".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "classified_category";
				}
				if ("GDSTATES".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "state";
				}
				if ("GDHELPMANUALTYPES".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "manual_type";
				}
				if ("GDCLASSIFIEDEDITIONS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "edition_name";
				}
				if ("GDCLASSIFIEDTYPES".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "type";
				}
				if ("GDCLASSIFIEDSUBCATEGORY".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "classified_subcategory";
				}
				if ("GDCLASSIFIEDLANGUAGES".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "language";
				}
				if ("GDCLASSIFIEDSCHEMES".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					orderBy = "scheme";
				}
				query = entityManager.createNativeQuery("select * from "
						+ MasterData.valueOf(reportsCommonModel.getReportsRequest().getMasterDataId()).getValue()
						+ " where mark_as_delete = false order by " + orderBy + "");
				NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
				nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
				
				if ("GDCLASSIFIEDSCHEMES".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					 List<Map<String, Object>> resultList = nativeQuery.getResultList();
					 List<GdEditionType> findAll = editionTypeRepo.findAll();
					 List<Map<String, Object>> res=new ArrayList<>();
					 for (Map<String, Object> result : resultList) {
						 GdEditionType matchedEditionType = findAll.stream()
			                        .filter(f -> f.getId().shortValue() == (Short) result.get("edition_type"))
			                        .findFirst()
			                        .orElse(null);
						  if (matchedEditionType != null) {
			                    result.put("edition_type", matchedEditionType.getEditionType());
			                }
						 result.put("no_days", String.valueOf((Short) result.get("no_days")));
				         result.put("billable_days", String.valueOf((Short) result.get("billable_days")));
				         res.add(result);
					 }
					 return res;
				}
				if ("GDCLASSIFIEDEDITIONS".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getMasterDataId())) {
					 List<Map<String, Object>> resultList = nativeQuery.getResultList();
					 List<GdEditionType> findAll = editionTypeRepo.findAll();
					 List<Map<String, Object>> res=new ArrayList<>();
					 for (Map<String, Object> result : resultList) {
						 GdEditionType matchedEditionType = findAll.stream()
			                        .filter(f -> f.getId().shortValue() == (Short) result.get("edition_type"))
			                        .findFirst()
			                        .orElse(null);
						  if (matchedEditionType != null) {
			                    result.put("edition_type", matchedEditionType.getEditionType());
			                }
				         res.add(result);
					 }
					 return res;
				}
				
				return nativeQuery.getResultList();
			}
			return null;
		}
		return null;
	}
	
	private List<Map<String, Object>> getSSPAllVendors(GenericRequestHeaders requestHeaders, LoggedUser loggedUser,
			ReportsCommonModel reportsCommonModel) {
		GenericApiResponse apiResponse = userService.getExecutiveCreatedListOfUsers(loggedUser);
		List<UserTo> usersList = new ArrayList<>((Collection) apiResponse.getData());
		List<Map<String, Object>> tmpList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (UserTo cls : usersList) {
			tmpList.add(mapper.convertValue(cls, Map.class));
		}
		    return tmpList;
		
	}

	private List<Map<String, Object>> getSSPSummary(GenericRequestHeaders requestHeaders, LoggedUser loggedUser, ReportsCommonModel reportCommonModel) {
		DashboardFilterTo filterTo = new DashboardFilterTo();
	if(reportCommonModel.getReportsRequest().getFilter().getRequestedDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedDate().isEmpty()) {
		filterTo.setRequestedDate(reportCommonModel.getReportsRequest().getFilter().getRequestedDate());
	}
	if(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedToDate().isEmpty()) {
		filterTo.setRequestedToDate(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate());
	}
	GenericApiResponse sspReports = displayService.getSSPSummaryReports(loggedUser, filterTo);
	 List<SSPSummaryModel> sspSummaryModels = (List<SSPSummaryModel>) sspReports.getData();
	 List<Map<String, Object>> sspUsersList = new ArrayList<>();
	 ObjectMapper mapper = new ObjectMapper();
	 for (SSPSummaryModel cls : sspSummaryModels) {
			sspUsersList.add(mapper.convertValue(cls, Map.class));
		}
	return sspUsersList;
	}

	private List<Map<String, Object>> getAllSSPVendors(GenericRequestHeaders requestHeaders, LoggedUser loggedUser) {
		GenericApiResponse sspUsers = orgService.getSSPUsers(requestHeaders.getOrgOpuId(), requestHeaders.getOrgId(), "ALL", "1", "false");
		 ListOfOrgUsers listOfOrgUsers = (ListOfOrgUsers) sspUsers.getData();
		 List<OrgUserDetails> orgUserDetails = listOfOrgUsers.getUsers(); 
		List<Map<String, Object>> sspUsersList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (OrgUserDetails cls : orgUserDetails) {
			sspUsersList.add(mapper.convertValue(cls, Map.class));
		}		
		return sspUsersList;
	}

	private List<Map<String, Object>> getSspPendingUsers(GenericRequestHeaders requestHeaders, LoggedUser loggedUser) {
		GenericApiResponse sspUsers = orgService.getSSPUsers(requestHeaders.getOrgOpuId(), requestHeaders.getOrgId(), "ALL", "1", "true");
		 ListOfOrgUsers listOfOrgUsers = (ListOfOrgUsers) sspUsers.getData();
		 List<OrgUserDetails> orgUserDetails = listOfOrgUsers.getUsers(); 
		List<Map<String, Object>> sspUsersList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (OrgUserDetails cls : orgUserDetails) {
			sspUsersList.add(mapper.convertValue(cls, Map.class));
		}		
		return sspUsersList;
	}

	private List<Map<String, Object>> getVendorCommission(LoggedUser loggedUser,
			ReportsCommonModel reportCommonModel) {
		DashboardFilterTo filterTo = new DashboardFilterTo();
		if(reportCommonModel.getReportsRequest().getFilter().getRequestedDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedDate().isEmpty()) {
			filterTo.setRequestedDate(reportCommonModel.getReportsRequest().getFilter().getRequestedDate());
		}
		if(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedToDate().isEmpty()) {
			filterTo.setRequestedToDate(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate());
		}
		GenericApiResponse vendorCommission = displayService.getVendorCommission(loggedUser, filterTo);
		List<DisplayAdsModel> displayAdsModel = new ArrayList<>((Collection) vendorCommission.getData());
		List<Map<String, Object>> tmpList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (DisplayAdsModel cls : displayAdsModel) {
			tmpList.add(mapper.convertValue(cls, Map.class));
		}
		return tmpList;

	}

	private List<Map<String, Object>> getDisplayAdsList(LoggedUser loggedUser, ReportsCommonModel reportCommonModel) {
		DashboardFilterTo filterTo = new DashboardFilterTo();
		if (reportCommonModel.getReportsRequest().getFilter() != null) {
			filterTo.setClassifiedType(reportCommonModel.getReportsRequest().getFilter().getClassifiedType());
			if(reportCommonModel.getReportsRequest().getFilter().getCategoryId() != null) {
				filterTo.setCategoryId(reportCommonModel.getReportsRequest().getFilter().getCategoryId());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getRequestedDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedDate().isEmpty()) {
				filterTo.setRequestedDate(reportCommonModel.getReportsRequest().getFilter().getRequestedDate());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedToDate().isEmpty()) {
				filterTo.setRequestedToDate(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getContentStatus() != null) {
				filterTo.setContentStatus(reportCommonModel.getReportsRequest().getFilter().getContentStatus());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getPaymentStatus() != null) {
				filterTo.setPaymentStatus(reportCommonModel.getReportsRequest().getFilter().getPaymentStatus());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getBookingUnit() != null) {
				filterTo.setBookingUnit(reportCommonModel.getReportsRequest().getFilter().getBookingUnit());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getUserId() != null) {
				filterTo.setUserId(reportCommonModel.getReportsRequest().getFilter().getUserId());
			}
		}
		GenericApiResponse apiResponse = displayService.getDisplayAdsList(loggedUser, filterTo);
		List<Classifieds> myClassifieds = new ArrayList<>((Collection) apiResponse.getData());
		List<Map<String, Object>> tmpList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (Classifieds cls : myClassifieds) {
			List<String> editions = cls.getEditions();
			cls.setEdition(String.join(", ", editions));
			tmpList.add(mapper.convertValue(cls, Map.class));
		}
		return tmpList;
	}

	public List<Map<String, Object>> getDashboardData(ReportsCommonModel reportsCommonModel) {
		Map<String, Object> filterMap = new HashMap<>();
		List<Map<String, Object>> dataList = new ArrayList<>();
		
		return dataList;
	}
	
	public List<Map<String, Object>> getMyClassifiedsList(LoggedUser loggedUser, ReportsCommonModel reportCommonModel) {
		DashboardFilterTo filterTo = new DashboardFilterTo();
		if (reportCommonModel.getReportsRequest().getFilter() != null) {
			filterTo.setClassifiedType(reportCommonModel.getReportsRequest().getFilter().getClassifiedType());
			if(reportCommonModel.getReportsRequest().getFilter().getCategoryId() != null) {
				filterTo.setCategoryId(reportCommonModel.getReportsRequest().getFilter().getCategoryId());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getRequestedDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedDate().isEmpty()) {
				filterTo.setRequestedDate(reportCommonModel.getReportsRequest().getFilter().getRequestedDate());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedToDate().isEmpty()) {
				filterTo.setRequestedToDate(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getContentStatus() != null) {
				filterTo.setContentStatus(reportCommonModel.getReportsRequest().getFilter().getContentStatus());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getPaymentStatus() != null) {
				filterTo.setPaymentStatus(reportCommonModel.getReportsRequest().getFilter().getPaymentStatus());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getBookingUnit() != null) {
				filterTo.setBookingUnit(reportCommonModel.getReportsRequest().getFilter().getBookingUnit());
			}
			
		}
		GenericApiResponse apiResponse = classifiedService.getClassifiedList(loggedUser, filterTo);
		List<Classifieds> myClassifieds = new ArrayList<>((Collection) apiResponse.getData());
		List<Map<String, Object>> tmpList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (Classifieds cls : myClassifieds) {
			List<String> editions = cls.getEditions();
			cls.setEdition(String.join(", ", editions));
			tmpList.add(mapper.convertValue(cls, Map.class));
		}
		return tmpList;
	}
	
	public List<Map<String, Object>> getDownloadStatusList(LoggedUser loggedUser, ReportsCommonModel reportCommonModel) {
		DashboardFilterTo filterTo = new DashboardFilterTo();
		if (reportCommonModel.getReportsRequest().getFilter() != null) {
			if(reportCommonModel.getReportsRequest().getFilter().getCategoryId() != null) {
				filterTo.setCategoryId(reportCommonModel.getReportsRequest().getFilter().getCategoryId());
			}
			 if (reportCommonModel.getReportsRequest().getFilter().getRequestedDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedDate().isEmpty()) {
		            filterTo.setRequestedDate(reportCommonModel.getReportsRequest().getFilter().getRequestedDate());
		        }
		        if (reportCommonModel.getReportsRequest().getFilter().getRequestedToDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedToDate().isEmpty()) {
		            filterTo.setRequestedToDate(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate());
		        }
			if(reportCommonModel.getReportsRequest().getFilter().getDownloadStatus()!=null) {
				filterTo.setDownloadStatus(reportCommonModel.getReportsRequest().getFilter().getDownloadStatus());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getPaymentStatus() != null) {
				filterTo.setPaymentStatus(reportCommonModel.getReportsRequest().getFilter().getPaymentStatus());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getBookingUnit() != null) {
				filterTo.setBookingUnit(reportCommonModel.getReportsRequest().getFilter().getBookingUnit());
			}
			
		}
		GenericApiResponse apiResponse = classifiedService.getDownloadStatusListExcelDownload(loggedUser, filterTo);
		List<Classifieds> myClassifieds = new ArrayList<>((Collection) apiResponse.getData());
		List<Map<String, Object>> tmpList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		
		for (Classifieds cls : myClassifieds) {
			List<String> editions = cls.getEditions();
			String editionsString = String.join(", ", editions);
			cls.setEdition(editionsString);
			String dStatus = Boolean.toString(cls.isDownloadStatus());
			cls.setDownloadStatues(dStatus);
			tmpList.add(mapper.convertValue(cls, Map.class));
		}
		return tmpList;
	}
	
	public List<Map<String, Object>> getRmsList(LoggedUser loggedUser, ReportsCommonModel reportCommonModel) {
		DashboardFilterTo filterTo = new DashboardFilterTo();
		if (reportCommonModel.getReportsRequest().getFilter() != null) {
			if(reportCommonModel.getReportsRequest().getFilter().getCategoryId() != null) {
				filterTo.setCategoryId(reportCommonModel.getReportsRequest().getFilter().getCategoryId());
			}
			 if (reportCommonModel.getReportsRequest().getFilter().getRequestedDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedDate().isEmpty()) {
		            filterTo.setRequestedDate(reportCommonModel.getReportsRequest().getFilter().getRequestedDate());
		        }
		        if (reportCommonModel.getReportsRequest().getFilter().getRequestedToDate() != null && !reportCommonModel.getReportsRequest().getFilter().getRequestedToDate().isEmpty()) {
		            filterTo.setRequestedToDate(reportCommonModel.getReportsRequest().getFilter().getRequestedToDate());
		        }
			if(reportCommonModel.getReportsRequest().getFilter().getDownloadStatus()!=null) {
				filterTo.setDownloadStatus(reportCommonModel.getReportsRequest().getFilter().getDownloadStatus());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getPaymentStatus() != null) {
				filterTo.setPaymentStatus(reportCommonModel.getReportsRequest().getFilter().getPaymentStatus());
			}
			if(reportCommonModel.getReportsRequest().getFilter().getBookingUnit() != null) {
				filterTo.setBookingUnit(reportCommonModel.getReportsRequest().getFilter().getBookingUnit());
			}
			
		}
		GenericApiResponse apiResponse = rmsService.getRmsListView(loggedUser, filterTo);
		List<RmsListViewModel> myClassifieds = new ArrayList<>((Collection) apiResponse.getData());
		List<Map<String, Object>> tmpList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		
		for (RmsListViewModel cls : myClassifieds) {
			List<String> editions = cls.getEditions();
			String editionsString = String.join(", ", editions);
			cls.setEdition(editionsString);
			List<String> publishDates = cls.getPublishDates();
			String publishDatesString = String.join(", ", publishDates);
			cls.setPublishDate(publishDatesString);
//			String dStatus = Boolean.toString(cls.isDownloadStatus());
//			cls.setDownloadStatues(dStatus);
			tmpList.add(mapper.convertValue(cls, Map.class));
		}
		return tmpList;
	}
}
