package com.portal.workflow;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.portal.basedao.IBaseDao;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.gd.entities.BookingUnits;
import com.portal.org.dao.OrgDao;
import com.portal.org.to.RolesTo;
import com.portal.reports.utility.CommonUtils;
import com.portal.repository.BookingUnitsRepo;
import com.portal.repository.ClfEditionsRepo;
import com.portal.repository.ClfPublishDatesRepo;
import com.portal.repository.GdWfTypesRepo;
import com.portal.repository.UmUsersRepository;
import com.portal.repository.WfDataRepo;
import com.portal.repository.WfDocTypesRepo;
import com.portal.repository.WfInboxMasterDetailsRepo;
import com.portal.repository.WfInboxMasterRepo;
import com.portal.repository.WfLocationsRepo;
import com.portal.repository.WfMasterDocTypesRepo;
import com.portal.repository.WfMasterRepo;
import com.portal.repository.WfRefrenceDataRepo;
import com.portal.repository.WfServiceTasksRepo;
import com.portal.security.repo.UmOrgUsersRepo;
import com.portal.user.dao.UserDao;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.entities.UmUsers;
import com.portal.user.to.UserTo;
import com.portal.wf.entity.GdWfTypes;
import com.portal.wf.entity.WfCandidateGroups;
import com.portal.wf.entity.WfCandidateUsers;
import com.portal.wf.entity.WfDocTypes;
import com.portal.wf.entity.WfInboxMaster;
import com.portal.wf.entity.WfInboxMasterDetails;
import com.portal.wf.entity.WfLocations;
import com.portal.wf.entity.WfMaster;
import com.portal.wf.entity.WfMasterDocTypes;
import com.portal.wf.entity.WfObjectRef;
import com.portal.wf.entity.WfRefTo;
import com.portal.wf.entity.WfRefToType;
import com.portal.wf.entity.WfRefToUsers;
import com.portal.wf.entity.WfRefrenceData;
import com.portal.wf.entity.WfServiceTasks;
import com.portal.wf.entity.WfUserInbox;
import com.portal.workflow.model.WfCommonModel;
import com.portal.workflow.model.WfConstants;
import com.portal.workflow.model.WfCreateRequest;
import com.portal.workflow.model.WfDetails;
import com.portal.workflow.model.WfInboxDetails;
import com.portal.workflow.model.WfInboxMasterDetailsModel;
import com.portal.workflow.model.WfInboxResponse;
import com.portal.workflow.model.WfStages;

@Transactional
@Component
public class WfDaoImpl implements WfDao {

//	@Autowired
//	private MongoTemplate mongoTemplate;
//	
//	@Autowired
//	private SettingService settingService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private WfDocTypesRepo docTypesRepo;
	
	@Autowired
	private WfMasterRepo wfMasterRepo;
	
	@Autowired
	private WfLocationsRepo locationsRepo;
	
	@Autowired
	private WfInboxMasterRepo wfInboxMasterRepo;
	
	@Autowired(required = true)
	private IBaseDao baseDao;
	
	@Autowired
	private WfInboxMasterDetailsRepo wfInboxMasterDetailsRepo;
	
	@Autowired
	private GdWfTypesRepo gdWfTypesRepo;
	
	@Autowired
	private WfServiceTasksRepo wfServiceTasksRepo;
	
	@Autowired
	private WfRefrenceDataRepo wfRefrenceDataRepo;
	
	@Autowired
	private WfDataRepo wfDataRepo;
	
//	@Autowired
//	private GdDocumentTypeRepo documentTypeRepo;
	
	@Autowired
	private UmOrgUsersRepo umOrgUsersRepo;
	
//	@Autowired
//	private GdLocationsRepo gdLocationsRepo;
	
	@Autowired
	private WfMasterDocTypesRepo wfMasterDocTypesRepo;
	
	@Autowired
	private WfRefrenceDataRepo refrenceDataRepo;
	
	@Autowired
	private BookingUnitsRepo bookingUnitsRepo;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UmUsersRepository umUsersRepository;
	
	@Autowired
	private ClfPublishDatesRepo clfPublishDatesRepo;

	@Autowired
	private ClfEditionsRepo clfEditionsRepo;
	
//	@Autowired
//	private DMSDocumentMasterRepo dmsDocumentMasterRepo;
	
	private static final Logger logger = Logger.getLogger(WfDaoImpl.class);
//	
//	@SuppressWarnings({ "deprecation", "rawtypes" })
//	public WfInboxResponse getWfInboxByUser1(WfCommonModel workFlowCommonModel,ApprovalListView payload){
//		
//		/* Get Settings Start*/
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("stype", GeneralConstants.ORG_SETTING);
//		param.put("grps", Arrays.asList(GeneralConstants.OTHER_SETTINGS));
//		param.put("refObjId", Arrays.asList(workFlowCommonModel.getGenericRequestHeaders().getOrgOpuId(),workFlowCommonModel.getGenericRequestHeaders().getOrgId()));
////		Map<String, String> settings = settingService.getSettingValues(param).getSettings();
////		logger.info("Settings at getWfInboxByUser()::  "+settings);
//		/* Get Settings End*/
//
//		GenericRequestHeaders requestHeaders = workFlowCommonModel.getGenericRequestHeaders();
//		WfInboxResponse response = new WfInboxResponse();
//		List<String> userPrimaryAndSecRoles = populateRoleNames(workFlowCommonModel, payload);
//		
//		Criteria masterInboxCriteria;
//		if(settings.containsKey(GeneralConstants.OPU_BASED_DATA_FILTER) && Boolean.parseBoolean(settings.get(GeneralConstants.OPU_BASED_DATA_FILTER)))
//			masterInboxCriteria = Criteria.where("markAsDelete").is(false).and("orgId").is(requestHeaders.getOrgId()).and("orgOpuId").is(requestHeaders.getOrgOpuId());
//		else
//			masterInboxCriteria = Criteria.where("markAsDelete").is(false).and("orgId").is(requestHeaders.getOrgId());
//		
//		if(workFlowCommonModel.getWorkFlowEvent() != null && workFlowCommonModel.getWorkFlowEvent().getObjectRefId() != null)
//			masterInboxCriteria.and(WfConstants.WF_OBJECT_REF_ID).is(workFlowCommonModel.getWorkFlowEvent().getObjectRefId());
//		
//		Criteria criteria = new Criteria();
//		Criteria criteria1 = Criteria.where(WfConstants.WF_INB_REF_TO_TYPE).in(userPrimaryAndSecRoles)
//				.and(WfConstants.WF_INB_REF_TO_USERS).is(null).and(WfConstants.WF_INB_USER_INBOX).is(null);
//		Criteria criteria2 = Criteria.where(WfConstants.WF_INB_REF_TO_TYPE).in(userPrimaryAndSecRoles)
//		.and(WfConstants.WF_INB_REF_TO_USERS).in(workFlowCommonModel.getLoggedUser().getUserId()).and(WfConstants.WF_INB_USER_INBOX).is(null);
//		Criteria criteria3 = Criteria.where(WfConstants.WF_INB_REF_TO_TYPE).in(userPrimaryAndSecRoles)
//				.and(WfConstants.WF_INB_REF_TO_USERS).in(workFlowCommonModel.getLoggedUser().getUserId()).and(WfConstants.WF_INB_USER_INBOX)
//				.elemMatch(Criteria.where("userId").is(workFlowCommonModel.getLoggedUser().getUserId()));
//			criteria.orOperator(criteria1, criteria2, criteria3).and(WfConstants.WF_INB_HISTORY_FLAG).is(false).and("wfInbox.markAsDelete").is(false);		
//		
//		if(requestHeaders.getVendorCode() != null)
//			criteria.and(WfConstants.WF_INB_VENDOR_CODE).is(requestHeaders.getVendorCode());
//		if(requestHeaders.getVendorId() != null)
//			criteria.and(WfConstants.WF_INB_VENDOR_ID).is(requestHeaders.getVendorId());
//		if(payload != null && payload.getAdvFilters()!=null) 
//			this.dataLoadAdvancedFilterData(payload.getAdvFilters(),criteria,masterInboxCriteria);
//		AggregationOperation match = Aggregation.match(masterInboxCriteria);
//		AggregationOperation inboxMatch = Aggregation.match(criteria);
//		AggregationOperation unwind = Aggregation.unwind("wfInbox");
//		Cond condOperation = ConditionalOperators
//				.when(ComparisonOperators.Eq.valueOf("$wfInbox.status").equalToValue(WfConstants.PENDING)).then(1)
//				.otherwise(2);
//		AddFieldsOperation addFields = new AddFieldsOperation(WfConstants.WF_INB_WF_DESC, "$wfDesc")
//				.addField("wfInbox.inboxMasterId", "$inboxMasterId").addField(WfConstants.WF_INB_REQUST_BY, "$requestedBy")
//				.addField("wfInbox.wfType", "$wfType").addField("wfInbox.objectRefId", "$objectRefId")
//				.addField("wfInbox.statusCode", condOperation);
//		AggregationOperation sort = Aggregation.sort(Direction.ASC, "wfInbox.statusCode").and(Direction.DESC, "wfInbox.requestedDate");
//		
//		AggregationOperation replaceRoot = Aggregation.replaceRoot("wfInbox");
//		List<AggregationOperation> aggregateOperation = new ArrayList<AggregationOperation>();
//		aggregateOperation.addAll(Arrays.asList(match, unwind, inboxMatch, addFields, sort, replaceRoot));
//		if (payload != null && payload.getPageNumber() != null && payload.getPageSize() != null) {
//			try {
//				JSONObject group = new JSONObject();
//				group.put("_id", 1);
//				group.put("count", new JSONObject().put("$sum", 1));
//				AggregationOperation groupBy = new CustomProjectAggregationOperation(
//						new JSONObject().put("$group", group).toString());
//				Aggregation countAggregation = Aggregation.newAggregation(match, unwind, inboxMatch, groupBy);
//				List<Map> countMap = mongoTemplate
//						.aggregate(countAggregation, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER, Map.class)
//						.getMappedResults();
//				Object totalCnt = 0;
//				if (!countMap.isEmpty())
//					totalCnt = countMap.get(0).get("count");
//				response.setTotalCnt(totalCnt);
//				int skip = (payload.getPageNumber() - 1) * payload.getPageSize();
//				aggregateOperation.add(Aggregation.skip(skip));
//				aggregateOperation.add(Aggregation.limit(payload.getPageSize()));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		Aggregation aggregation = Aggregation.newAggregation(aggregateOperation);
//		response.setWfInboxData(mongoTemplate
//				.aggregate(aggregation, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER, WfInboxDetails.class)
//				.getMappedResults());
//		return response;
//	}
//	
//	public WfInboxResponse getWfInboxByUser(WfCommonModel workFlowCommonModel, ApprovalListView payload)
//			throws JsonMappingException, JsonProcessingException {
////        
////        // Simulate fetching settings
////        Map<String, String> settings = getSettings(workFlowCommonModel);
////
//		WfInboxResponse wfInboxResponse = new WfInboxResponse();
//		List<WfInboxDetails> wfInboxData = wfInboxResponse.getWfInboxData();
//		LinkedHashMap<String, WfInboxDetails> approvalInboxModelMap = new LinkedHashMap<>();
//		List<String> itemIds = new ArrayList<>();
//		List<Integer> createdByIds = new ArrayList<>();
//		GenericRequestHeaders requestHeaders = workFlowCommonModel.getGenericRequestHeaders();
//		WfInboxResponse response = new WfInboxResponse();
//		List<String> userPrimaryAndSecRoles = populateRoleNames(workFlowCommonModel, payload);
//		String roles = userPrimaryAndSecRoles.stream().map(role -> "'" + role + "'").collect(Collectors.joining(", "));
//		String query = "";
//		if ("SUPER_ADMIN".equalsIgnoreCase(requestHeaders.getLoggedUser().getRoleType())) {
//			query = "select wfrt.inbox_master_id,wfrt.wf_inbox_id from wf_ref_to_type wfrt where wfrt.mark_as_delete=false order by wfrt.created_ts desc";
//		} else {
//			query = "select wfrt.inbox_master_id,wfrt.wf_inbox_id from wf_ref_to_type wfrt where wfrt.ref_to_type IN ("
//					+ roles + ") and wfrt.mark_as_delete=false order by wfrt.created_ts desc";
//		}
////        String query="select wfrt.inbox_master_id,wfrt.wf_inbox_id from wf_ref_to_type wfrt where wfrt.ref_to_type IN (" + roles + ") and wfrt.mark_as_delete=false order by wfrt.created_ts desc";
//		List<Object[]> data = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
//		String query1 = "select wr.inbox_master_id,wr.wf_inbox_id from wf_ref_to_users wr where wr.ref_to_users IN ("
//				+ workFlowCommonModel.getLoggedUser().getUserId()
//				+ ") and wr.mark_as_delete=false order by wr.created_ts desc";
//		List<Object[]> data1 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
//
//		String query2 = "select wui.inbox_master_id,wui.wf_inbox_id from wf_user_inbox wui where wui.user_id IN ("
//				+ workFlowCommonModel.getLoggedUser().getUserId()
//				+ ") and wui.mark_as_delete=false order by wui.created_ts desc";
//		List<Object[]> data2 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query2);
//
//		Set<String> inboxMasterIds = new HashSet<>();
//
//		for (Object[] row : data) {
//			if (row != null && row.length > 0) {
//				inboxMasterIds.add(row[0].toString()); // Assuming inbox_master_id is the first column
//			}
//		}
//		for (Object[] row : data1) {
//			if (row != null && row.length > 0) {
//				inboxMasterIds.add(row[0].toString());
//			}
//		}
//		for (Object[] row : data2) {
//			if (row != null && row.length > 0) {
//				inboxMasterIds.add(row[0].toString());
//			}
//		}
//		List<Object[]> data3 = new ArrayList<Object[]>();
//		List<Object[]> data4 = new ArrayList<Object[]>();
//		if (inboxMasterIds != null && !inboxMasterIds.isEmpty()) {
//			List<String> uniqueInboxMasterIds = new ArrayList<>(inboxMasterIds);
//			String masterIds = uniqueInboxMasterIds.stream().map(role -> "'" + role + "'")
//					.collect(Collectors.joining(", "));
//			String query3 = "select wim.inbox_master_id,wim.wf_inbox_id,wim.status,wim.created_ts,wim.created_by,wim.request_raised_by,wim.approval_type,wim.approval_levels,COUNT(*) OVER () AS total_count from wf_inbox_master_details wim where wim.inbox_master_id IN ("
//					+ masterIds
//					+ ") and wim.mark_as_delete=false and wim.status!='WORKFLOW_SKIPPED' order by wim.created_ts desc";
//			if (payload != null && payload.getPageNumber() != null && payload.getPageSize() != null) {
//				int skip = ((Integer) payload.getPageNumber() - 1) * ((Integer) payload.getPageSize());
//				query3 = query3 + " LIMIT " + payload.getPageSize() + " OFFSET " + skip;
//			}
//			data3 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query3);
//			Set<String> inboxMasterIds1 = new HashSet<>();
//			if(data3 != null && !data3.isEmpty()) {
//				for(Object[] obj : data3) {
//					inboxMasterIds1.add((String) obj[0]);
//				}
//			}
//			
//			String inboxMasterIds2 = inboxMasterIds1.stream().map(role -> "'" + role + "'")
//					.collect(Collectors.joining(", "));
//
////        String query4="select wm.wf_desc,wm.wf_short_id,wm.current_status,wm.wf_type,wm.wf_type_id,wm.wf_title,wm.object_ref_id,wm.inbox_master_id,dms.document_numder,wdt.wf_name,dms.document_type from wf_inbox_master wm inner join dms_document_master dms on wm.object_ref_id = dms.document_master_id inner join wf_master_doc_types wdt on wm.wf_type_id = wdt.wf_id where wm.inbox_master_id IN ("+ masterIds +") and wm.mark_as_delete=false order by dms.created_ts desc";
//			String query4 = "select wm.wf_desc,wm.wf_short_id,wm.current_status,wm.wf_type,wm.wf_type_id,wm.wf_title,wm.object_ref_id,wm.inbox_master_id,itm.item_id,wdt.wf_name,itm.order_id from wf_inbox_master wm inner join clf_order_items itm on wm.object_ref_id = itm.item_id  inner join wf_master_doc_types wdt on wm.wf_type_id = wdt.wf_id where wm.inbox_master_id IN ("
//					+ inboxMasterIds2 + ") and wm.mark_as_delete=false order by itm.created_ts desc";
//			data4 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query4);
//		}
//		List<String> objectRefId = new ArrayList<>();
//
//		List<WfInboxDetails> wfInboxDetailsList = new ArrayList<>();
//		if (data3 != null && !data3.isEmpty()) {
//			for (Object[] row : data3) {
//				WfInboxDetails details = new WfInboxDetails();
//				details.setInboxMasterId(row[0] != null ? row[0].toString() : null);
//				details.setWfInboxId(row[1] != null ? row[1].toString() : null);
//				details.setStatus(row[2] != null ? row[2].toString() : null);
//				details.setCreatedTs(row[3] != null ? (Date) row[3] : null);
//				details.setCreatedBy(row[4] != null ? row[4].toString() : null);
//				details.setRequestRaisedBy(row[5] != null ? row[5].toString() : null);
//				details.setApprovalType(row[6] != null ? row[6].toString() : null);
//				details.setApprovalLevels(row[7] != null ? row[7].toString() : null);
//
//				wfInboxDetailsList.add(details);
//			}
//		}
//
//		if (data4 != null && !data4.isEmpty()) {
//			for (Object[] row : data4) {
//				String inboxMasterId = row[7] != null ? row[7].toString() : null;
////				WfInboxDetails matchingDetails = wfInboxDetailsList.stream()
////						.filter(detail -> detail.getInboxMasterId().equals(inboxMasterId)).findFirst().orElse(null);
//				
//				List<WfInboxDetails> matchingDetailsList = wfInboxDetailsList.stream()
//				        .filter(detail -> detail.getInboxMasterId().equals(inboxMasterId))
//				        .collect(Collectors.toList());
//
//
//				if (matchingDetailsList != null && !matchingDetailsList.isEmpty()) {
//					for(WfInboxDetails matchingDetails : matchingDetailsList) {
//					matchingDetails.setWfDesc(row[0] != null ? row[0].toString() : null);
//					matchingDetails.setWfShortId(row[1] != null ? row[1].toString() : null);
//					matchingDetails.setCurrentStatus(row[2] != null ? row[2].toString() : null);
//					matchingDetails.setWfType(row[3] != null ? row[3].toString() : null);
//					matchingDetails.setWfTypeId(row[4] != null ? row[4].toString() : null);
//					matchingDetails.setWfTitle(row[5] != null ? row[5].toString() : null);
//					matchingDetails.setObjectRefId(row[6] != null ? row[6].toString() : null);
//					matchingDetails.setItemId(row[8] != null ? row[8].toString() : null);
//					matchingDetails.setWfType(row[9] != null ? row[9].toString() : null);
//					matchingDetails.setOrderId((String) row[10]);
//					objectRefId.add(row[6] != null ? row[6].toString() : null);
//					itemIds.add(row[8] != null ? row[8].toString() : null);
//					approvalInboxModelMap.put((String) row[8], matchingDetails);
//					}
//				}
//			}
//
//		}
//
//		if (itemIds != null && !itemIds.isEmpty()) {
//			String itemIds1 = String.join("','", itemIds);
//			String query5 = "select itm.item_id,itm.ad_id,uc.client_code,uc.customer_name,roi.additional_discount,roi.premium_discount,roi.grand_total,co.edition_type,gret.edition_type as gret_edition_type,roi.ad_type,grat.add_type,roi.caption,itm.created_by,co.booking_unit,co.no_of_levels,bu.booking_description,itm.created_ts,roi.aggred_premium_dis_per from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id inner join rms_order_items roi on itm.item_id = roi.item_id inner join gd_rms_edition_type gret on co.edition_type = gret.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join booking_units bu on bu.booking_code = co.booking_unit where itm.mark_as_delete = false and itm.item_id in ('"
//					+ itemIds1 + "')";
//			List<Object[]> data5 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query5);
//			if (data5 != null && !data5.isEmpty()) {
//				for (Object[] obj : data5) {
////			 WfInboxDetails matchingDetails = wfInboxDetailsList.stream()
////			            .filter(detail -> detail.getItemId().equals(obj[0]))
////			            .findFirst()
////			            .orElse(null);
////			 if(matchingDetails != null) {
////				 matchingDetails.setAdId((String) obj[1]);
////				 matchingDetails.setClientCode((String) obj[2]);
////				 matchingDetails.setCustomerName((String) obj[3]);
////				 matchingDetails.setAdditionalDiscPercent(obj[4] != null ? ((Float) obj[4]).doubleValue() : 0.0);
////				 matchingDetails.setPremiumDiscPercent(obj[5] != null ? ((Float) obj[5]).doubleValue() : 0.0);
////				 matchingDetails.setGrandTotal(obj[6] != null ? ((Float) obj[6]).doubleValue() : 0.0);
////				 matchingDetails.setEditionType((String) obj[8]);
////				 matchingDetails.setAddType((String) obj[10]);
////				 matchingDetails.setCaption((String) obj[11]);
//////				 matchingDetails.setCreatedBy((Integer) obj[12]);
////				 matchingDetails.setBookingOffice((Integer) obj[13]);
////				 matchingDetails.setNoOfLevels((Integer) obj[14]);
////				 matchingDetails.setEmployeeBookingOffice((String) obj[15]);;
//////				 matchingDetails.setCreatedTs(CommonUtils.dateFormatter((Date) obj[16], "dd-MM-yyyy HH:mm:ss"));
////				 matchingDetails.setAgreedPremiumDisPer(obj[17] != null ? ((Float) obj[17]).doubleValue() : 0.0);
////				 createdByIds.add((Integer) obj[12]);;
////			 }
//					if (approvalInboxModelMap.containsKey((String) obj[0])) {
//						WfInboxDetails wfInboxDetails = approvalInboxModelMap.get(obj[0]);
////				List<ApprovalInboxModel>  approvalModels = approvalInboxModelMap.get(obj[0]);
//						if (wfInboxDetails != null) {
////					for(ApprovalInboxModel approvalModel:approvalModels ) {
//							wfInboxDetails.setAdId((String) obj[1]);
//							wfInboxDetails.setClientCode((String) obj[2]);
//							wfInboxDetails.setCustomerName((String) obj[3]);
//							wfInboxDetails
//									.setAdditionalDiscPercent(obj[4] != null ? ((Float) obj[4]).doubleValue() : 0.0);
//							wfInboxDetails.setPremiumDiscPercent(obj[5] != null ? ((Float) obj[5]).doubleValue() : 0.0);
//							wfInboxDetails.setGrandTotal(obj[6] != null ? ((Float) obj[6]).doubleValue() : 0.0);
//							wfInboxDetails.setEditionType((String) obj[8]);
//							wfInboxDetails.setAddType((String) obj[10]);
//							wfInboxDetails.setCaption((String) obj[11]);
////					wfInboxDetails.setCreatedBy((Integer) obj[12]);
//							wfInboxDetails.setBookingOffice((Integer) obj[13]);
//							wfInboxDetails.setNoOfLevels((Integer) obj[14]);
//							wfInboxDetails.setEmployeeBookingOffice((String) obj[15]);
//							;
////					wfInboxDetails.setCreatedTs(CommonUtils.dateFormatter((Date) obj[16], "dd-MM-yyyy HH:mm:ss"));
//							wfInboxDetails
//									.setAgreedPremiumDisPer(obj[17] != null ? ((Float) obj[17]).doubleValue() : 0.0);
//							createdByIds.add((Integer) obj[12]);
////					}
//
//						}
//					}
//				}
//			}
//			List<Object[]> editionsList = clfEditionsRepo.getRmsEditionIdAndNameOnItemId(itemIds);
//
//			for (Object[] clObj : editionsList) {
//				String key = (String) clObj[0]; // Extract the key (itemId)
//				String edition = (String) clObj[2]; // Extract the edition (name)
//
//				// Check if the key exists in the map
//				if (approvalInboxModelMap.containsKey(key)) {
//					// Fetch the list of ApprovalInboxModel associated with this key
//					WfInboxDetails approvalInboxModels2 = approvalInboxModelMap.get(key);
//
//					if (approvalInboxModels2 != null) {
//						// Iterate over each ApprovalInboxModel and add the edition
////	        for (ApprovalInboxModel approvalInboxModel : approvalInboxModels2) {
//						// Ensure the editions list is initialized
//						if (approvalInboxModels2.getEditions() == null) {
//							approvalInboxModels2.setEditions(new ArrayList<>());
//						}
//						// Add the edition to the editions list
//						approvalInboxModels2.getEditions().add(edition);
////	        }
//					}
//				}
//			}
//
//			List<Object[]> publishDatesList = clfPublishDatesRepo.getPublishDatesForErpData(itemIds);
//			for (Object[] clObj : publishDatesList) {
//				String key = (String) clObj[0];
//				Date pDate = (Date) clObj[1];
//				if (approvalInboxModelMap.containsKey(key)) {
//					WfInboxDetails approvalInboxModels3 = approvalInboxModelMap.get(key);
//					if (approvalInboxModels3 != null) {
////			 for(ApprovalInboxModel approvalInboxModel : approvalInboxModels2) {
//						if (approvalInboxModels3.getPublishDates() == null) {
//							approvalInboxModels3.setPublishDates(new ArrayList<>());
//						}
//						approvalInboxModels3.getPublishDates()
//								.add(CommonUtils.dateFormatter((Date) pDate, "dd-MM-yyyy"));
////			 }
//					}
//
//				}
//			}
//
////	if (createdByIds != null && !createdByIds.isEmpty()) {
////	    List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByIds, false);
////	    if (!umUsers.isEmpty()) {
////	        approvalInboxModelMap.entrySet().forEach(entry -> {
////	            // `entry.getValue()` is the List<ApprovalInboxModel> for the current key
////	            List<ApprovalInboxModel> approvalInboxModels2 = entry.getValue();
////
////	            // Iterate over each ApprovalInboxModel in the list
////	            approvalInboxModels2.forEach(approvalInboxModel -> {
////	                // Find the corresponding UmUsers object by matching `createdBy`
////	                Optional<UmUsers> matchingUser = umUsers.stream()
////	                        .filter(user -> user.getUserId().equals(approvalInboxModel.getCreatedBy()))
////	                        .findFirst();
////
////	                // If a matching user is found, set the `createdByUser` field
////	                if (matchingUser.isPresent()) {
////	                    approvalInboxModel.setCreatedbyUser(matchingUser.get().getFirstName());
////	                    approvalInboxModel.setEmpCode(matchingUser.get().getEmpCode());
////	                    
////	                }
////	            });
////	        });
////	    }
////	}
//
//		}
//
////        List<DMSDocumentMaster> docMasterOnMasterIds = dmsDocumentMasterRepo.getDocMasterOnMasterIds(objectRefId);
////        List<String> formStatuses = new ArrayList<String>();
////        if(docMasterOnMasterIds !=null && !docMasterOnMasterIds.isEmpty()) {
////        	for(DMSDocumentMaster docMaster : docMasterOnMasterIds) {
////            	formStatuses.add(docMaster.getDocumentStatus());
////            }
////        }
//
//		if (data4 != null && !data4.isEmpty()) {
////        for (Object[] row : data4) {
////            String inboxMasterId = row[7] != null ? row[7].toString() : null;
////            WfInboxDetails matchingDetails = wfInboxDetailsList.stream()
////                .filter(detail -> detail.getInboxMasterId().equals(inboxMasterId))
////                .findFirst()
////                .orElse(null);
////            DMSDocumentMaster docMasterOnMasterId = dmsDocumentMasterRepo.getDocMasterOnMasterId(row[6] != null ? row[6].toString() : null);
////            String wfRefData = getWfRefData(row[4] != null ? row[4].toString() : null);
////            ObjectMapper objectMapper = new ObjectMapper();
////            List<WfStatuses> readValue = objectMapper.readValue(wfRefData, new TypeReference<List<WfStatuses>>() {});
////            if(matchingDetails!=null) {
////            	if(docMasterOnMasterId != null && docMasterOnMasterId.getDocumentStatus() != null) {
////            	matchingDetails.setWfStatus(docMasterOnMasterId.getDocumentStatus()); 
////            	}
////            	 matchingDetails.setWfStatuses(readValue);
////            }
////        }
//		}
//		// Prepare response
//		response.setWfInboxData(wfInboxDetailsList);
//		response.setTotalCnt(wfInboxDetailsList.size());
//
//		if (requestHeaders.getVendorCode() != null) {
//			query = query + " and vendor_code ='" + requestHeaders.getVendorCode() + "'";
//		}
//		if (requestHeaders.getVendorId() != null) {
//			query = query + " and vendor_id ='" + requestHeaders.getVendorId() + "'";
//		}
////
////        // Handle advanced filters
//		if (payload != null && payload.getAdvFilters() != null) {
////            applyAdvancedFilters(sqlQuery, params, payload.getAdvFilters());
//		}
//
////        if(payload != null && payload.getPageNumber() != null && payload.getPageSize() != null) {
////			int skip = ((Integer) payload.getPageNumber() - 1) * ((Integer) payload.getPageSize());
////			query = query + " LIMIT " + payload.getPageSize() + " OFFSET " + skip;
////		}
//
//		return response;
//	}
	
	
	public WfInboxResponse getWfInboxByUser(WfCommonModel workFlowCommonModel, ApprovalListView payload)
			throws JsonMappingException, JsonProcessingException {

		WfInboxResponse wfInboxResponse = new WfInboxResponse();
		List<WfInboxDetails> wfInboxData = wfInboxResponse.getWfInboxData();
		LinkedHashMap<String, WfInboxDetails> approvalInboxModelMap = new LinkedHashMap<>();
		List<String> itemIds = new ArrayList<>();
		List<Integer> createdByIds = new ArrayList<>();
		GenericRequestHeaders requestHeaders = workFlowCommonModel.getGenericRequestHeaders();
		WfInboxResponse response = new WfInboxResponse();
		List<String> userPrimaryAndSecRoles = populateRoleNames(workFlowCommonModel, payload);
		String roles = userPrimaryAndSecRoles.stream().map(role -> "'" + role + "'").collect(Collectors.joining(", "));
		String query = "";
		List<Object[]> data = new ArrayList<>();
		if ("SUPER_ADMIN".equalsIgnoreCase(requestHeaders.getLoggedUser().getRoleType())) {
			query = "select wfrt.inbox_master_id,wfrt.wf_inbox_id from wf_ref_to_type wfrt where wfrt.mark_as_delete=false order by wfrt.created_ts desc";
			data = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		}
//		else {
//			query = "select wfrt.inbox_master_id,wfrt.wf_inbox_id from wf_ref_to_type wfrt where wfrt.ref_to_type IN ("
//					+ roles + ") and wfrt.mark_as_delete=false order by wfrt.created_ts desc";
//		}
//        String query="select wfrt.inbox_master_id,wfrt.wf_inbox_id from wf_ref_to_type wfrt where wfrt.ref_to_type IN (" + roles + ") and wfrt.mark_as_delete=false order by wfrt.created_ts desc";
//		List<Object[]> data = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		String query1 = "select wr.inbox_master_id,wr.wf_inbox_id from wf_ref_to_users wr where wr.ref_to_users IN ("
				+ workFlowCommonModel.getLoggedUser().getUserId()
				+ ") and wr.mark_as_delete=false order by wr.created_ts desc";
		List<Object[]> data1 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);

		String query2 = "select wui.inbox_master_id,wui.wf_inbox_id from wf_user_inbox wui where wui.user_id IN ("
				+ workFlowCommonModel.getLoggedUser().getUserId()
				+ ") and wui.mark_as_delete=false order by wui.created_ts desc";
		List<Object[]> data2 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query2);

		Set<String> inboxMasterIds = new HashSet<>();

		if(data != null && !data.isEmpty()) {
		for (Object[] row : data) {
			if (row != null && row.length > 0) {
				inboxMasterIds.add(row[1].toString()); // Assuming inbox_master_id is the first column
			}
		}
		}
		for (Object[] row : data1) {
			if (row != null && row.length > 0) {
				inboxMasterIds.add(row[1].toString());
			}
		}
		for (Object[] row : data2) {
			if (row != null && row.length > 0) {
				inboxMasterIds.add(row[1].toString());
			}
		}
		List<Object[]> data3 = new ArrayList<Object[]>();
		List<Object[]> data4 = new ArrayList<Object[]>();
		if (inboxMasterIds != null && !inboxMasterIds.isEmpty()) {
			List<String> uniqueInboxMasterIds = new ArrayList<>(inboxMasterIds);
			String masterIds = uniqueInboxMasterIds.stream().map(role -> "'" + role + "'")
					.collect(Collectors.joining(", "));
			String query3 = "select wim.inbox_master_id,wim.wf_inbox_id,wim.status,wim.created_ts,wim.created_by,wim.request_raised_by,wim.approval_type,wim.approval_levels,wim.target_ref,COUNT(*) OVER () AS total_count from wf_inbox_master_details wim where wim.wf_inbox_id IN ("
					+ masterIds
					+ ") and wim.mark_as_delete=false and wim.status!='WORKFLOW_SKIPPED' order by wim.created_ts desc";
			if (payload != null && payload.getPageNumber() != null && payload.getPageSize() != null) {
				int skip = ((Integer) payload.getPageNumber() - 1) * ((Integer) payload.getPageSize());
				query3 = query3 + " LIMIT " + payload.getPageSize() + " OFFSET " + skip;
			}
			data3 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query3);
			Set<String> inboxMasterIds1 = new HashSet<>();
			if (data3 != null && !data3.isEmpty()) {
				for (Object[] obj : data3) {
					inboxMasterIds1.add((String) obj[0]);
				}
			}

			String inboxMasterIds2 = inboxMasterIds1.stream().map(role -> "'" + role + "'")
					.collect(Collectors.joining(", "));
			if(!"".equalsIgnoreCase(inboxMasterIds2)) {
				String query4 = "select wm.wf_desc,wm.wf_short_id,wm.current_status,wm.wf_type,wm.wf_type_id,wm.wf_title,wm.object_ref_id,wm.inbox_master_id,itm.item_id,wdt.wf_name,itm.order_id from wf_inbox_master wm inner join clf_order_items itm on wm.object_ref_id = itm.item_id  inner join wf_master_doc_types wdt on wm.wf_type_id = wdt.wf_id where wm.inbox_master_id IN ("
						+ inboxMasterIds2 + ") and wm.mark_as_delete=false order by itm.created_ts desc";
				data4 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query4);
			}
			//String query4="select wm.wf_desc,wm.wf_short_id,wm.current_status,wm.wf_type,wm.wf_type_id,wm.wf_title,wm.object_ref_id,wm.inbox_master_id,dms.document_numder,wdt.wf_name,dms.document_type from wf_inbox_master wm inner join dms_document_master dms on wm.object_ref_id = dms.document_master_id inner join wf_master_doc_types wdt on wm.wf_type_id = wdt.wf_id where wm.inbox_master_id IN ("+ masterIds +") and wm.mark_as_delete=false order by dms.created_ts desc";
			
		}
		List<String> objectRefId = new ArrayList<>();

		List<WfInboxDetails> wfInboxDetailsList = new ArrayList<>();
		if (data3 != null && !data3.isEmpty()) {
			for (Object[] row : data3) {
				WfInboxDetails details = new WfInboxDetails();
				details.setInboxMasterId(row[0] != null ? row[0].toString() : null);
				details.setWfInboxId(row[1] != null ? row[1].toString() : null);
				details.setStatus(row[2] != null ? row[2].toString() : null);
//				details.setCreatedTs(row[3] != null ? (Date) row[3] : null);
				details.setCreatedBy(row[4] != null ? row[4].toString() : null);
				details.setRequestRaisedBy(row[5] != null ? row[5].toString() : null);
				details.setApprovalType(row[6] != null ? row[6].toString() : null);
				details.setApprovalLevels(row[7] != null ? row[7].toString() : null);
				details.setCurrentLevel(row[8] != null ? Integer.parseInt((String) row[8]) : null);
				response.setTotalCnt(row[9]);
				wfInboxDetailsList.add(details);
			}
		}

		if (data4 != null && !data4.isEmpty()) {
			for (Object[] row : data4) {
				String inboxMasterId = row[7] != null ? row[7].toString() : null;
//				WfInboxDetails matchingDetails = wfInboxDetailsList.stream()
//						.filter(detail -> detail.getInboxMasterId().equals(inboxMasterId)).findFirst().orElse(null);

				List<WfInboxDetails> matchingDetailsList = wfInboxDetailsList.stream()
						.filter(detail -> detail.getInboxMasterId().equals(inboxMasterId)).collect(Collectors.toList());

				if (matchingDetailsList != null && !matchingDetailsList.isEmpty()) {
					for (WfInboxDetails matchingDetails : matchingDetailsList) {
						matchingDetails.setWfDesc(row[0] != null ? row[0].toString() : null);
						matchingDetails.setWfShortId(row[1] != null ? row[1].toString() : null);
						matchingDetails.setCurrentStatus(row[2] != null ? row[2].toString() : null);
						matchingDetails.setWfType(row[3] != null ? row[3].toString() : null);
						matchingDetails.setWfTypeId(row[4] != null ? row[4].toString() : null);
						matchingDetails.setWfTitle(row[5] != null ? row[5].toString() : null);
						matchingDetails.setObjectRefId(row[6] != null ? row[6].toString() : null);
						matchingDetails.setItemId(row[8] != null ? row[8].toString() : null);
						matchingDetails.setWfType(row[9] != null ? row[9].toString() : null);
						matchingDetails.setOrderId((String) row[10]);
						objectRefId.add(row[6] != null ? row[6].toString() : null);
						itemIds.add((row[8] != null && !"".equalsIgnoreCase((String) row[8])) ? row[8].toString() : null);
//						approvalInboxModelMap.put((String) row[8], matchingDetails);
					}
				}
			}

		}

		if (itemIds != null && !itemIds.isEmpty()) {
			String itemIds1 = String.join("','", itemIds);
			String query5 = "select itm.item_id,itm.ad_id,uc.client_code,uc.customer_name,roi.additional_discount,roi.premium_discount,roi.grand_total,co.edition_type,gret.edition_type as gret_edition_type,roi.ad_type,grat.add_type,roi.caption,itm.created_by,co.booking_unit,co.no_of_levels,bu.booking_description,itm.created_ts,roi.aggred_premium_dis_per from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id inner join rms_order_items roi on itm.item_id = roi.item_id inner join gd_rms_edition_type gret on co.edition_type = gret.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join booking_units bu on bu.booking_code = co.booking_unit where itm.mark_as_delete = false and itm.item_id in ('"
					+ itemIds1 + "')";
			List<Object[]> data5 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query5);
			if (data5 != null && !data5.isEmpty()) {
				for (Object[] obj : data5) {
//			 WfInboxDetails matchingDetails = wfInboxDetailsList.stream()
//			            .filter(detail -> detail.getItemId().equals(obj[0]))
//			            .findFirst()
//			            .orElse(null);

					List<WfInboxDetails> matchingDetailsList = wfInboxDetailsList.stream()
						    .filter(detail -> Objects.equals(detail.getItemId(), obj[0]))
						    .collect(Collectors.toList());


					if (matchingDetailsList != null && !matchingDetailsList.isEmpty()) {
						for (WfInboxDetails matchingDetails : matchingDetailsList) {
							matchingDetails.setAdId((String) obj[1]);
							matchingDetails.setClientCode((String) obj[2]);
							matchingDetails.setCustomerName((String) obj[3]);
							matchingDetails
									.setAdditionalDiscPercent(obj[4] != null ? ((Float) obj[4]).doubleValue() : 0.0);
							matchingDetails
									.setPremiumDiscPercent(obj[5] != null ? ((Float) obj[5]).doubleValue() : 0.0);
							matchingDetails.setGrandTotal(obj[6] != null ? ((Float) obj[6]).doubleValue() : 0.0);
							matchingDetails.setEditionType((String) obj[8]);
							matchingDetails.setAddType((String) obj[10]);
							matchingDetails.setCaption((String) obj[11]);
							matchingDetails.setCreatedBy(obj[12].toString());
							matchingDetails.setBookingOffice((Integer) obj[13]);
							matchingDetails.setNoOfLevels((Integer) obj[14]);
							matchingDetails.setEmployeeBookingOffice((String) obj[15]);
							matchingDetails.setCreatedTs(CommonUtils.dateFormatter((Date) obj[16], "dd-MM-yyyy HH:mm:ss"));
							matchingDetails
									.setAgreedPremiumDisPer(obj[17] != null ? ((Float) obj[17]).doubleValue() : 0.0);
							createdByIds.add((Integer) obj[12]);
						}
					}
				}
			}
			List<Object[]> editionsList = clfEditionsRepo.getRmsEditionIdAndNameOnItemId(itemIds);

			if (editionsList != null && !editionsList.isEmpty()) {
				for (Object[] obj : editionsList) {
					List<WfInboxDetails> matchingDetailsList = wfInboxDetailsList.stream()
						    .filter(detail -> Objects.equals(detail.getItemId(), obj[0]))
						    .collect(Collectors.toList());


					if (matchingDetailsList != null && !matchingDetailsList.isEmpty()) {
						for (WfInboxDetails matchingDetails : matchingDetailsList) {
							if (matchingDetails.getEditions() != null && !matchingDetails.getEditions().isEmpty()) {
								matchingDetails.getEditions().add((String) obj[2]);
							} else {
								matchingDetails.setEditions(new ArrayList<>());
								matchingDetails.getEditions().add((String) obj[2]);
							}
						}
					}
				}
			}

			List<Object[]> publishDatesList = clfPublishDatesRepo.getPublishDatesForErpData(itemIds);
			if (publishDatesList != null && !publishDatesList.isEmpty()) {
				for (Object[] clObj : publishDatesList) {
					Date pDate = (Date) clObj[1];
					List<WfInboxDetails> matchingDetailsList = wfInboxDetailsList.stream()
						    .filter(detail -> Objects.equals(detail.getItemId(), clObj[0]))
						    .collect(Collectors.toList());

					if (matchingDetailsList != null && !matchingDetailsList.isEmpty()) {
						for (WfInboxDetails matchingDetails : matchingDetailsList) {
							if (matchingDetails.getPublishDates() != null
									&& !matchingDetails.getPublishDates().isEmpty()) {
//								matchingDetails.getPublishDates().add((String) clObj[1]);
								matchingDetails.getPublishDates()
								.add(CommonUtils.dateFormatter((Date) pDate, "dd-MM-yyyy"));
							} else {
								matchingDetails.setPublishDates(new ArrayList<>());
//								matchingDetails.getPublishDates().add((String) clObj[1]);
								matchingDetails.getPublishDates()
								.add(CommonUtils.dateFormatter((Date) pDate, "dd-MM-yyyy"));
							}
						}
					}
				}

			}
			
			if (createdByIds != null && !createdByIds.isEmpty()) {
				List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByIds, false);
				if (umUsers != null && !umUsers.isEmpty()) {
					for (UmUsers umUser : umUsers) {
						List<WfInboxDetails> matchingDetailsList = wfInboxDetailsList.stream()
								.filter(detail -> detail.getCreatedBy().equals(umUser.getUserId() + ""))
								.collect(Collectors.toList());
						if (matchingDetailsList != null && !matchingDetailsList.isEmpty()) {
							for (WfInboxDetails matchingDetails : matchingDetailsList) {
								matchingDetails.setCreatedbyUser(umUser.getFirstName());
								matchingDetails.setEmpCode(umUser.getEmpCode());
							}
						}
					}
				}
			}
		}
		List<WfInboxDetails> filteredInboxDetailsList = wfInboxDetailsList.stream()
			    .filter(detail -> detail.getItemId() != null  && !"".equalsIgnoreCase(detail.getItemId()))
			    .collect(Collectors.toList());
//		response.setWfInboxData(filteredInboxDetailsList);
		response.setWfInboxData(filteredInboxDetailsList);
//		response.setTotalCnt(wfInboxDetailsList.size());
		return response;
	}
	
private String getWfRefData(String wfId) {
	String wfStatus="";
	WfRefrenceData wfRefDetails = refrenceDataRepo.getWfRefDetails(wfId).get(0);
	if(wfRefDetails!=null) {
		 String encodedString = wfRefDetails.getWfRefMetaData(); // Base64 encoded string
		    byte[] decodedBytes = Base64.getDecoder().decode(encodedString); // Decoding Base64
		    wfStatus = new String(decodedBytes, StandardCharsets.UTF_8); // Convert to string using UTF-8 encoding
		    System.out.println(wfStatus);
	}
	return wfStatus;
}
//	
	@SuppressWarnings("rawtypes")
	public List<Map> getWfMaster(WfCommonModel workFlowCommonModel) {
		
		List<WfMaster> wfMaster = wfMasterRepo.getWfMasterDetails(workFlowCommonModel.getWorkFlowEvent().getWfShortId(), false);
		// Convert List<WfMaster> to List<Map<String, Object>>
	    ObjectMapper objectMapper = new ObjectMapper();
	    return wfMaster.stream()
	                   .map(wf -> objectMapper.convertValue(wf, Map.class))
	                   .collect(Collectors.toList());
	}
	
	public List<WfInboxMaster> getWfMasterById(WfCommonModel workFlowCommonModel) {
		
		List<WfInboxMaster> wfInboxMaster = wfInboxMasterRepo.getWfInboxMasterDetails(workFlowCommonModel.getWorkFlowEvent().getWfInboxMasterId(), false);
		return wfInboxMaster;
	}
	
	public List<WfInboxMasterDetails> getWfInboxByInboxId(WfCommonModel workFlowCommonModel){
		
		 List<WfInboxMasterDetails> wfInboxMasterDetails = wfInboxMasterDetailsRepo.getWfInboxMasterDetailsData(workFlowCommonModel.getWorkFlowEvent().getWfInboxId(), false);
		 return wfInboxMasterDetails;
	}
	
	public List<WfInboxMaster> getWfInboxByExtId(WfCommonModel workFlowCommonModel){
//		Query findQuery = new Query();
//		findQuery.addCriteria(Criteria.where("markAsDelete").is(false).and(WfConstants.WF_INBOX_EXT_OBJ_REF_ID)
//				.is(workFlowCommonModel.getWorkFlowEvent().getExtObjectRefId()));
//		return mongoTemplate.find(findQuery, WfInboxMaster.class, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
		return null;
	}
	
	public void updateInbox(WfCommonModel workFlowCommonModel){
		
		wfInboxMasterDetailsRepo.updateInboxMasterDetails(workFlowCommonModel.getWorkFlowEvent().getActionComments(),
				workFlowCommonModel.getWorkFlowEvent().getActStatus(), workFlowCommonModel.getLoggedUser().getUserId(),
				new Date(), workFlowCommonModel.getWorkFlowEvent().getWfInboxId());
	}
	
	public void updateWfInboxMaster(WfCommonModel workFlowCommonModel, WfDetails wfDetails) {

		String currentStatus;
		if (wfDetails != null
				&& (wfDetails.getEndEvents().contains(workFlowCommonModel.getWorkFlowEvent().getTargetRefId()) || "WORKFLOW_SKIPPED".equalsIgnoreCase(workFlowCommonModel.getWorkFlowEvent().getActStatus()))) {
			currentStatus = WfConstants.COMPLETED;
		} else if (wfDetails != null) {
			currentStatus = WfConstants.PENDING;
		} else {
			currentStatus = WfConstants.PENDING;
		}
		wfInboxMasterRepo.updateWfInboxMasterData(workFlowCommonModel.getWorkFlowEvent().getWfRefId(), currentStatus,
				workFlowCommonModel.getLoggedUser().getUserId(), new Date());
	}
	
	public long createInbox(WfInboxMasterDetailsModel wfInbox, WfCommonModel workFlowCommonModel, String inboxMasterId){
		Long count = (long) 1;
		WfInboxMasterDetails inboxMasterDetails = new WfInboxMasterDetails();
		inboxMasterDetails.setWfInboxId(UUID.randomUUID().toString());
		wfInbox.setNewWfInboxId(inboxMasterDetails.getWfInboxId());
		inboxMasterDetails.setTargetRef(wfInbox.getTargetRef());
		inboxMasterDetails.setRequestedDate(wfInbox.getRequestedDate());
		inboxMasterDetails.setStatus(wfInbox.getStatus());
		inboxMasterDetails.setCreatedBy(wfInbox.getCreatedBy());
		inboxMasterDetails.setRequestRaisedBy(wfInbox.getRequestRaisedBy());
		inboxMasterDetails.setCreatedTs(wfInbox.getCreatedTs());
		inboxMasterDetails.setMarkAsDelete(false);
		inboxMasterDetails.setRefDocData(wfInbox.getRefDocData());
		inboxMasterDetails.setSourceStageRef(wfInbox.getSourceStageRef());
		inboxMasterDetails.setComments(wfInbox.getComments());
		inboxMasterDetails.setInboxMasterId(inboxMasterId);
		inboxMasterDetails.setHistoryFlag(wfInbox.getHistoryFlag());
		inboxMasterDetails.setExtObjRefId(wfInbox.getExtObjRefId());
		inboxMasterDetails.setApprovalType(wfInbox.getApprovalType());
		inboxMasterDetails.setApprovalLevels(wfInbox.getApprovalLevels());
		if(wfInbox.getRefToUsers() != null && !wfInbox.getRefToUsers().isEmpty()) {
			inboxMasterDetails.setRefToUsers(wfInbox.getRefToUsers().get(0)+"");
			inboxMasterDetails.setCandidateUsers(wfInbox.getRefToUsers().get(0)+"");
		}
		inboxMasterDetails.setAccessKey(wfInbox.getAccessKey());
		
		baseDao.save(inboxMasterDetails);
		
		if("ALL".equalsIgnoreCase(wfInbox.getApprovalLevels()) && wfInbox.getUserInbox().size() != 0) {
			List<Map<String, Object>> inbox = wfInbox.getUserInbox();
			if(inbox != null && !inbox.isEmpty()) {
				for(Map<String,Object> obj : inbox) {
					WfUserInbox userInbox = new WfUserInbox();
					userInbox.setWfUserInboxId(UUID.randomUUID().toString());
					userInbox.setUserId((Integer) obj.get("userId"));
					userInbox.setStatus((String) obj.get("status"));
					userInbox.setMarkAsDelete(false);
					userInbox.setWfInboxId(inboxMasterDetails.getWfInboxId());
					userInbox.setInboxMasterId(inboxMasterId);
					userInbox.setCreatedTs(new Date());
					userInbox.setCreatedBy(wfInbox.getCreatedBy());
					
					baseDao.save(userInbox);
				}
			}		
		}
		
		if(wfInbox.getRefToType() != null && !wfInbox.getRefToType().isEmpty()) {
			List<String> refToTypeList = wfInbox.getRefToType();
			for(String refToType : refToTypeList) {
				WfRefToType wfRefToType = new WfRefToType();
				wfRefToType.setRefToTypeId(UUID.randomUUID().toString());
				wfRefToType.setInboxMasterId(inboxMasterId);
				wfRefToType.setWfInboxId(inboxMasterDetails.getWfInboxId());
				wfRefToType.setRefToType(refToType);
				wfRefToType.setCreatedBy(null);
				wfRefToType.setCreatedTs(new Date());
				wfRefToType.setMarkAsDelete(false);
				wfRefToType.setCreatedBy(wfInbox.getCreatedBy());
				
				baseDao.save(wfRefToType);
			}
		}
		
		if(wfInbox.getRefTo() != null && !wfInbox.getRefTo().isEmpty()) {
			List<String> refToList = wfInbox.getRefTo();
			for(String refTo : refToList) {
				WfRefTo wfRefTo = new WfRefTo();
				wfRefTo.setRefToId(UUID.randomUUID().toString());
				wfRefTo.setInboxMasterId(inboxMasterId);
				wfRefTo.setWfInboxId(inboxMasterDetails.getWfInboxId());
				wfRefTo.setRefTo(refTo);
				wfRefTo.setCreatedBy(null);
				wfRefTo.setCreatedTs(new Date());
				wfRefTo.setMarkAsDelete(false);
				wfRefTo.setCreatedBy(wfInbox.getCreatedBy());
				
				baseDao.save(wfRefTo);
			}
		}
		
		if(wfInbox.getRefToUsers() != null && !wfInbox.getRefToUsers().isEmpty()) {
			List<Integer> refToUsersList = wfInbox.getRefToUsers();
			for(Integer refToUsers : refToUsersList) {
				WfRefToUsers wfRefToUsers = new WfRefToUsers();
				wfRefToUsers.setRefToUserId(UUID.randomUUID().toString());
				wfRefToUsers.setInboxMasterId(inboxMasterId);
				wfRefToUsers.setWfInboxId(inboxMasterDetails.getWfInboxId());
				wfRefToUsers.setRefToUsers(refToUsers);
				wfRefToUsers.setCreatedBy(null);
				wfRefToUsers.setCreatedTs(new Date());
				wfRefToUsers.setMarkAsDelete(false);
				wfRefToUsers.setCreatedBy(wfInbox.getCreatedBy());
				
				baseDao.save(wfRefToUsers);
			}
		}
		
		if(wfInbox.getCandidateGroups() != null && !wfInbox.getCandidateGroups().isEmpty()) {
			List<String> candidateGroupsList = wfInbox.getCandidateGroups();
			for(String candidateGrp : candidateGroupsList) {
				WfCandidateGroups wfCandidateGroups = new WfCandidateGroups();
				wfCandidateGroups.setCandidateGroupId(UUID.randomUUID().toString());
				wfCandidateGroups.setInboxMasterId(inboxMasterId);
				wfCandidateGroups.setWfInboxId(inboxMasterDetails.getWfInboxId());
				wfCandidateGroups.setCandidateGroups(candidateGrp);
				wfCandidateGroups.setCreatedBy(null);
				wfCandidateGroups.setCreatedTs(new Date());
				wfCandidateGroups.setMarkAsDelete(false);
				wfCandidateGroups.setCreatedBy(wfInbox.getCreatedBy());
				
				baseDao.save(wfCandidateGroups);
			}
		}
		
		if(wfInbox.getCandidateUsers() != null && !wfInbox.getCandidateUsers().isEmpty()) {
			List<Integer> candidateUsersList = wfInbox.getCandidateUsers();
			for(Integer candidateUsr : candidateUsersList) {
				WfCandidateUsers wfCandidateUsers = new WfCandidateUsers();
				wfCandidateUsers.setCandidateId(UUID.randomUUID().toString());
				wfCandidateUsers.setInboxMasterId(inboxMasterId);
				wfCandidateUsers.setWfInboxId(inboxMasterDetails.getWfInboxId());
				wfCandidateUsers.setCandidateUsers(candidateUsr);
				wfCandidateUsers.setCreatedBy(null);
				wfCandidateUsers.setCreatedTs(new Date());
				wfCandidateUsers.setMarkAsDelete(false);
				wfCandidateUsers.setCreatedBy(wfInbox.getCreatedBy());
				
				baseDao.save(wfCandidateUsers);
			}
		}
		
		return count;
		
		
		
		
//		Query findQuery = new Query();
//		findQuery.addCriteria(Criteria.where("markAsDelete").is(false).and(WfConstants.WF_INBOX_MASTERID)
//				.is(inboxMasterId));
//		Update update = new Update();
//		update.push("wfInbox", wfInbox);
//		UpdateResult ur = mongoTemplate.updateFirst(findQuery, update, WfInboxMaster.class);
//		return ur.getModifiedCount();
	}

	@Override
	public WfInboxMaster getWfInboxMasterByRefId(String objRefId, String orgId) {
		
		WfInboxMaster wfInboxMaster = wfInboxMasterRepo.getWfInboxMaster(objRefId, false);
		return wfInboxMaster;
		
//		Query findQuery = new Query().addCriteria(Criteria.where("markAsDelete").is(false).and("orgId").is(orgId)
//				.and(WfConstants.WF_OBJECT_REF_ID).is(objRefId));
//		return mongoTemplate.findOne(findQuery, WfInboxMaster.class, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
	}
	
	public void updateInboxHistoryFlag(WfCommonModel workFlowCommonModel, List<String> inboxIds) {

		wfInboxMasterDetailsRepo.updateWfInboxHistoryFlag(workFlowCommonModel.getWorkFlowEvent().isHistoryFlag(),
				workFlowCommonModel.getWorkFlowEvent().getWfInboxMasterId(), inboxIds);

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean createWorkFlow(Map<String, Object> wfDataMap, WfCreateRequest payload) {
		boolean flag = false;
		try {
			if (payload.getWfId() != null && !payload.getWfId().isEmpty()) {
				WfMaster wfMaster = wfMasterRepo.getWfMasterDataByWfId(payload.getWfId());
				wfMaster.setWfId(wfDataMap.get("wfId")+"");
				wfMaster.setWfTitle(wfDataMap.get("wfTitle")+"");
				wfMaster.setWfName(wfDataMap.get("wfName")+"");
				wfMaster.setWfTypeId(wfDataMap.get("wfTypeId")+"");
				wfMaster.setWfType(wfDataMap.get("wfType")+"");
				wfMaster.setWfShortId(wfDataMap.get("wfShortId")+"");
				wfMaster.setStatus(wfDataMap.get("status")+"");
				wfMaster.setInitiatorFlag((Boolean) wfDataMap.get("initiatorFlag"));
				wfMaster.setChangedBy((Integer) wfDataMap.get("changedBy"));
				wfMaster.setChangedTs((Date) wfDataMap.get("changedTs"));
				wfMaster.setMarkAsDelete(false);
				wfMaster.setIsDefault(false);
				
				JSONObject jsonObject = new JSONObject((Map) wfDataMap.get("process"));
				String jsonString = jsonObject.toString();
				String base64EncodedString = Base64.getEncoder().encodeToString(jsonString.getBytes());
				wfMaster.setProcessMetaData(base64EncodedString);
				
				baseDao.saveOrUpdate(wfMaster);
				
				
				Map<String,String> refObj = (Map<String, String>) wfDataMap.get("objectRef");
				if(refObj != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
					WfObjectRef wfObjectRef = new WfObjectRef();
					wfObjectRef.setObjectRefId(UUID.randomUUID().toString());
					wfObjectRef.setObjectCollection(refObj.get("objectCollection"));
					wfObjectRef.setObjectKeyField(refObj.get("objectKeyField"));
					wfObjectRef.setObjectStatusField(refObj.get("objectStatusField"));
					wfObjectRef.setWfId(wfDataMap.get("wfId")+"");
					wfObjectRef.setCreatedBy((Integer) wfDataMap.get("createdBy"));
					wfObjectRef.setCreatedTs(sdf.parse( (String) wfDataMap.get("createdTs")));
					wfObjectRef.setMarkAsDelete(false);
					
					baseDao.save(wfObjectRef);
					
				}
				
				
				
//				Document dbDoc = new Document();
//				mongoTemplate.getConverter().write(wfDataMap, dbDoc);
//				Update update = Update.fromDocument(dbDoc);
//				Query query = new Query(Criteria.where("markAsDelete").is(false).and("wfId").is(payload.getWfId()));
//				mongoTemplate.upsert(query, update, MongoEntityConstants.WF_WORKFLOW_MASTER);
			} else {
				if(payload.getDocumentTypeId() != null && !payload.getDocumentTypeId().isEmpty() && payload.getLocationId() != null && !payload.getLocationId().isEmpty()) {
					List<Object[]> wfDocTypesandLocationsByIds = docTypesRepo.getWfDocTypesandLocationsByIds(payload.getDocumentTypeId(), payload.getLocationId());
					if(wfDocTypesandLocationsByIds != null && !wfDocTypesandLocationsByIds.isEmpty()) {
						return false;
					}
				}
				WfMaster wfMaster = new WfMaster();
				wfMaster.setWfId(wfDataMap.get("wfId")+"");
				wfMaster.setWfTitle(wfDataMap.get("wfTitle")+"");
				wfMaster.setWfName(wfDataMap.get("wfName")+"");
				wfMaster.setWfTypeId(wfDataMap.get("wfTypeId")+"");
				wfMaster.setWfType(wfDataMap.get("wfType")+"");
				wfMaster.setWfShortId(wfDataMap.get("wfShortId")+"");
				wfMaster.setStatus(wfDataMap.get("status")+"");
				wfMaster.setInitiatorFlag((Boolean) wfDataMap.get("initiatorFlag"));
				wfMaster.setCreatedBy((Integer) wfDataMap.get("createdBy"));
				wfMaster.setCreatedTs((Date) wfDataMap.get("createdTs"));
				wfMaster.setMarkAsDelete(false);
				wfMaster.setIsDefault(false);
				
				JSONObject jsonObject = new JSONObject((Map) wfDataMap.get("process"));
				String jsonString = jsonObject.toString();
				String base64EncodedString = Base64.getEncoder().encodeToString(jsonString.getBytes());
				wfMaster.setProcessMetaData(base64EncodedString);
				
				baseDao.save(wfMaster);
				
				
				Map<String,String> refObj = (Map<String, String>) wfDataMap.get("objectRef");
				if(refObj != null) {
					Date date = CommonUtils.dateFormatter(wfDataMap.get("createdTs").toString());
					WfObjectRef wfObjectRef = new WfObjectRef();
					wfObjectRef.setObjectRefId(UUID.randomUUID().toString());
					wfObjectRef.setObjectCollection(refObj.get("objectCollection"));
					wfObjectRef.setObjectKeyField(refObj.get("objectKeyField"));
					wfObjectRef.setObjectStatusField(refObj.get("objectStatusField"));
					wfObjectRef.setWfId(wfDataMap.get("wfId")+"");
					wfObjectRef.setCreatedBy((Integer) wfDataMap.get("createdBy"));
					wfObjectRef.setCreatedTs((Date) wfDataMap.get("createdTs"));
					wfObjectRef.setMarkAsDelete(false);
					
					baseDao.save(wfObjectRef);
					
				}
				if(payload.getDocumentTypeId() != null && !payload.getDocumentTypeId().isEmpty()) {
					for(Integer docType : payload.getDocumentTypeId()) {
						Date date = CommonUtils.dateFormatter(wfDataMap.get("createdTs").toString());
						WfDocTypes docTypes = new WfDocTypes();
						docTypes.setWfDocId(UUID.randomUUID().toString());
						docTypes.setWfId(wfDataMap.get("wfId")+"");
						docTypes.setDocumentType(docType);
						docTypes.setCreatedTs((Date) wfDataMap.get("createdTs"));
						docTypes.setCreatedBy((Integer) wfDataMap.get("createdBy"));
						docTypes.setMarkAsDelete(false);
						baseDao.save(docTypes);
					}
					
				}
				if(payload.getLocationId() != null && !payload.getLocationId().isEmpty()) {
					for(Integer location : payload.getLocationId()) {
						WfLocations locations = new WfLocations();
						locations.setWfLocId(UUID.randomUUID().toString());
						locations.setLocation(location);
						locations.setWfId(wfDataMap.get("wfId")+"");
						locations.setCreatedTs((Date) wfDataMap.get("createdTs"));
						locations.setCreatedBy((Integer) wfDataMap.get("createdBy"));
						locations.setMarkAsDelete(false);
						baseDao.save(locations);
					}
				}
				
//				Document dbDoc = new Document();
//				mongoTemplate.getConverter().write(wfDataMap, dbDoc);
//				mongoTemplate.save(dbDoc, MongoEntityConstants.WF_WORKFLOW_MASTER);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
//	
//	public long updateUserInbox(WfCommonModel workFlowCommonModel, List<Map<String, Object>> userInbox) {
//		Query findQuery = new Query();
//		findQuery.addCriteria(Criteria.where("markAsDelete").is(false).and(WfConstants.WF_INBOX_MASTERID).is(workFlowCommonModel.getWorkFlowEvent().getWfInboxMasterId()).and(WfConstants.WF_INBOX_ID)
//				.is(workFlowCommonModel.getWorkFlowEvent().getWfInboxId()).and(WfConstants.WF_INB_USER_INBOX_USERID).is(workFlowCommonModel.getLoggedUser().getUserId()));
//		Update update = new Update();
//		update.set("wfInbox.$.userInbox", userInbox);
//		UpdateResult ur = mongoTemplate.upsert(findQuery, update, WfInboxMaster.class);
//		return ur.getModifiedCount();
//	}
//
	@Override
	public List<GdWfTypes> getWfTypes(GenericRequestHeaders requestHeader) {
		List<GdWfTypes> gdWfTypesList = gdWfTypesRepo.getWfTypesList();
		return gdWfTypesList;
	}

	@Override
	public List<WfServiceTasks> getWfServiceTasks(GenericRequestHeaders requestHeader, String wfTypeId) {
		
		List<WfServiceTasks> wfServiceTasksList = wfServiceTasksRepo.getWfServiceTypesList1(wfTypeId);
		return wfServiceTasksList;
	}
	
	@Override
	public WfServiceTasks getWfServiceTaskById(String serviceId) {
		
		WfServiceTasks wfServiceTasks = wfServiceTasksRepo.getWfServiceTaskDataById(serviceId);
		return wfServiceTasks;
	}

	@Override
	public GdWfTypes getWfTypeById(GenericRequestHeaders requestHeader, String wfTypeId) {
		
		GdWfTypes gdWfTypes = gdWfTypesRepo.getWfTypeDataById(wfTypeId);
		return gdWfTypes;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getWfDataByWfId(GenericRequestHeaders requestHeader, String wfId) {
		Map wfData = null;
		Map map = new HashMap();
		try {
			
			WfRefrenceData wfRefrenceData = wfRefrenceDataRepo.getWfRefDetails(wfId).get(0);
			List<WfMasterDocTypes> wfMasterDocTypes = wfMasterDocTypesRepo.getWfMasterDocByWfId(wfId);
			List<WfDocTypes> wfMasterDocByWfId = docTypesRepo.getWfMasterDocByWfId(wfId);
			List<WfLocations> locationsByWfId = locationsRepo.getLocationsByWfId(wfId);
//			Criteria criteria = Criteria.where("wfId").is(wfId)
//					.and("orgId").is(requestHeader.getOrgId()).and("markAsDelete").is(false);
//			Query query = new Query();
//			Map map = mongoTemplate.findOne(query.addCriteria(criteria), Map.class, MongoEntityConstants.WF_WORKFLOW_REF_DATA);
			
			if(wfRefrenceData!=null){
				String wfRefMetaData = wfRefrenceData.getWfRefMetaData();
				byte[] decodedBytes = Base64.getDecoder().decode(wfRefMetaData);
		        String decodedJson = new String(decodedBytes);
//				JSONObject decodedRes = new JSONObject(new String(Base64Utils.decode(wfRefMetaData.getBytes())));
				
				ObjectMapper objectMapper = new ObjectMapper();
		        List<WfStages> wfRefList = objectMapper.readValue(decodedJson, new TypeReference<List<WfStages>>() {});
		        
//				ObjectMapper objectMapper = new ObjectMapper();
		        map = objectMapper.convertValue(wfRefrenceData, Map.class);
				List<GdWfTypes> wfTypes = getWfTypes(requestHeader);

				Map<String,GdWfTypes> gdWfTypesMap = new HashMap<>();
				if (!wfTypes.isEmpty()) {
					for (GdWfTypes types : wfTypes) {
						gdWfTypesMap.put(types.getWfTypeId(), types);
					}
				}
				
				List<String> gdWfTypeIds = new ArrayList<>();
				String wfTypeDesc = null;
				for(WfMasterDocTypes masterDocType : wfMasterDocTypes) {
					GdWfTypes gdWfType = gdWfTypesMap.get(masterDocType.getWfTypeId());
					if(gdWfType != null) {
						gdWfTypeIds.add(gdWfType.getWfTypeId());
						wfTypeDesc = gdWfType.getWfTypeDesc();
					}
				}
//				List<Integer> docTypes = new ArrayList<Integer>();
//				List<GdDocumentTypes> documentTypesOnIds = new ArrayList<GdDocumentTypes>();
//				if(wfMasterDocByWfId != null && !wfMasterDocByWfId.isEmpty()) {
//					for(WfDocTypes docType : wfMasterDocByWfId ) {
//						docTypes.add(docType.getDocumentType());
//					}
//					 documentTypesOnIds = documentTypeRepo.getDocumentTypesOnIds(docTypes);
//				}
				List<Integer> locationId = new ArrayList<Integer>();
				List<BookingUnits> gdLocations = new ArrayList<BookingUnits>();
				List<UserTo> userByBookingCode = new ArrayList<UserTo>();
				if(locationsByWfId != null && !locationsByWfId.isEmpty()) {
					for(WfLocations locations : locationsByWfId) {
						locationId.add(locations.getLocation());
						UserTo user = new UserTo();//No need for multiselect Locations dropdown
						String string = Integer.toString(locations.getLocation());//No need for multiselect Locations dropdown
						user.setBookingOffice(string);//No need for multiselect Locations dropdown
//						 userByBookingCode = userDao.getUserByBookingCode(user);//No need for multiselect Locations dropdown
						 userByBookingCode = userDao.getRmsApprovers();
					}
					gdLocations = bookingUnitsRepo.getBookingUnitsByBookingCode(locationId);
				}
				
//				List<GdWfTypes> data = wfTypes.stream().filter(id-> id.getWfTypeId().equalsIgnoreCase(wfMasterDocTypes.get(0).getWfTypeId())).collect(Collectors.toList());
//				List<GdWfTypes> data = wfTypes.stream().filter(id-> id.getWfTypeId().equalsIgnoreCase(wfRefrenceData.getWfTypeId())).collect(Collectors.toList());
//				map.put("wfTypeName", data.isEmpty() ? null : data.get(0).getWfTypeDesc());
//				List<String> wfTypeIds = new ArrayList<>();
//				if(!data.isEmpty()) {
//					for(GdWfTypes typeData : data) {
//					wfTypeIds.add(typeData.getWfTypeId());
//					}
//				}
				if(gdLocations !=  null && !gdLocations.isEmpty()) {
					map.put("locations", gdLocations);
				}
				if(userByBookingCode != null && !userByBookingCode.isEmpty()) {//No need for multiselect Locations dropdown
					map.put("usersByLoc", userByBookingCode);//No need for multiselect Locations dropdown
				}//No need for multiselect Locations dropdown
				map.put("wfTypeName", wfTypeDesc);
				map.put("wfTypeId", gdWfTypeIds);
				map.put("wfRefData", wfRefList);
//				map.put("documentType", documentTypesOnIds);
			
			}
			wfData = map;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return wfData;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getWfTitleSearchList(GenericRequestHeaders requestHeader, List<String> wfTypeId, String wfTypeName, String wfTitle, boolean masterWf) {
		List<Map> list = null;
		List<WfMaster> wfMasterList1 = new ArrayList<WfMaster>();
		try {
			
			String query = "select wm.wf_id,wm.wf_title from wf_master wm where wm.mark_as_delete = false";
			
//			Criteria criteria = Criteria.where("markAsDelete").is(false);
			if(wfTypeId!=null){
				String ids = String.join(",", wfTypeId.stream()
		                .map(id -> "'" + id + "'") // Quote each ID for SQL
		                .collect(Collectors.toList()));
				query = query + " and wm.wf_type_id IN (" + ids + ")";
//				criteria.and("wfTypeId").is(wfTypeId);
			}
			if(wfTypeName!=null){
				query = query + " and wm.wf_type ='" + wfTypeName + "'";
//				criteria.and("wfType").is(wfTypeName);
			}
			if(wfTitle!=null){
				query = query + " and wm.wf_title ='" + wfTitle + "'";
//				criteria.and("wfTitle").regex(wfTitle,"i");
			}
			if(masterWf){
				query = query + " and wm.status = 'PUBLISH' ";
//				criteria.orOperator(Criteria.where("orgId").is(requestHeader.getOrgId()), Criteria.where("orgId").is(null))
//						.and("status").is("PUBLISH");
			}
//			else{
//				criteria.and("orgId").is(requestHeader.getOrgId());	
//			}
			
			List<Object[]> wfMasterList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			if(wfMasterList != null && !wfMasterList.isEmpty()) {
				for (Object[] obj : wfMasterList) {
					WfMaster wfMaster = new WfMaster();
					wfMaster.setWfId((String) obj[0]);
					wfMaster.setWfTitle((String) obj[1]);
					
					wfMasterList1.add(wfMaster);
				}
				
				ObjectMapper objectMapper = new ObjectMapper();
				// Convert List<WfMaster> to List<Map<String, Object>>
	            list = objectMapper.convertValue(wfMasterList1, new TypeReference<List<Map>>() {});
//				list = (List<Map>) objectMapper.convertValue(wfMasterList1, Map.class);
			}
//			Query query = new Query();
//			query.fields().include("wfTitle").include("wfId").include("isDefault");
//			list = mongoTemplate.find(query.addCriteria(criteria), Map.class, MongoEntityConstants.WF_WORKFLOW_MASTER);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean createWfRefData(Map<String, Object> wfRefDataMap, String wfId) {
		boolean flag = false;
		try {
			WfRefrenceData wfRef = wfRefrenceDataRepo.getWfRefDataOnRefId(wfRefDataMap.get("wfRefId")+"");
			if (wfRef != null) {
				wfRef.setWfRefId(wfRefDataMap.get("wfRefId") + "");
				wfRef.setWfId(wfRefDataMap.get("wfId") + "");
				wfRef.setWfTitle(wfRefDataMap.get("wfTitle") + "");
				wfRef.setWfName(wfRefDataMap.get("wfName") + "");
				wfRef.setWfTypeId(wfRefDataMap.get("wfTypeId") + "");
//				wfRef.setWfType(wfRefDataMap.get("wfType")+"");
//				wfRef.setWfShortId(wfRefDataMap.get("wfShortId")+"");
				wfRef.setStatus(wfRefDataMap.get("status") + "");
				wfRef.setCreatedBy((Integer) wfRefDataMap.get("createdBy"));
				wfRef.setCreatedTs((Date) wfRefDataMap.get("createdTs"));
//				wfRef.setInitiatorFlag((Boolean) wfRefDataMap.get("initiatorFlag"));
				wfRef.setWfName(wfRefDataMap.get("wfName")+"");
				wfRef.setMarkAsDelete(false);
				
				List<WfStages> wfRefDataList = (List<WfStages>) wfRefDataMap.get("wfRefData");
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = objectMapper.writeValueAsString(wfRefDataList);
				String base64EncodedString = Base64.getEncoder().encodeToString(jsonString.getBytes());
				wfRef.setWfRefMetaData(base64EncodedString);
				
				baseDao.saveOrUpdate(wfRef);
			} else {
				WfRefrenceData wfRef1 = new WfRefrenceData();
				wfRef1.setWfRefId(wfRefDataMap.get("wfRefId") + "");
				wfRef1.setWfId(wfRefDataMap.get("wfId") + "");
				wfRef1.setWfTitle(wfRefDataMap.get("wfTitle") + "");
				wfRef1.setWfName(wfRefDataMap.get("wfName") + "");
				wfRef1.setWfTypeId(wfRefDataMap.get("wfTypeId") + "");
//			wfRef1.setWfType(wfRefDataMap.get("wfType")+"");
//			wfRef1.setWfShortId(wfRefDataMap.get("wfShortId")+"");
				wfRef1.setStatus(wfRefDataMap.get("status") + "");
				wfRef1.setCreatedBy((Integer) wfRefDataMap.get("createdBy"));
				wfRef1.setCreatedTs((Date) wfRefDataMap.get("createdTs"));
//			wfRef1.setInitiatorFlag((Boolean) wfRefDataMap.get("initiatorFlag"));
				wfRef1.setWfName(wfRefDataMap.get("wfName")+"");
				wfRef1.setMarkAsDelete(false);
				
				
				List<WfStages> wfRefDataList = (List<WfStages>) wfRefDataMap.get("wfRefData");
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = objectMapper.writeValueAsString(wfRefDataList);
				String base64EncodedString = Base64.getEncoder().encodeToString(jsonString.getBytes());
				wfRef1.setWfRefMetaData(base64EncodedString);
				
				baseDao.save(wfRef1);

			}

//			Document dbDoc = new Document();
//			mongoTemplate.getConverter().write(wfRefDataMap, dbDoc);
//			Update update = Update.fromDocument(dbDoc);
//			Query query = new Query(Criteria.where("markAsDelete").is(false).and("wfId").is(wfId));
//			mongoTemplate.upsert(query, update, MongoEntityConstants.WF_WORKFLOW_REF_DATA);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
//	public void dataLoadAdvancedFilterData(DocumentFilters advancedFilters,Criteria inboxCriteria,Criteria matchCriteria) {
////		try {
////			if(advancedFilters.getWfDesc()!=null && !advancedFilters.getWfDesc().isEmpty())
////				matchCriteria.and("wfDesc").in(advancedFilters.getWfDesc());
////			if(advancedFilters.getRequestRaisedBy()!=null &&!advancedFilters.getRequestRaisedBy().isEmpty())				
////				inboxCriteria.and(WfConstants.WF_INB_REQUST_RAISED_BY).in(advancedFilters.getRequestRaisedBy());			
////			if(advancedFilters.getRefDocData()!=null && !advancedFilters.getRefDocData().isEmpty())				
////					inboxCriteria.and(WfConstants.WF_INB_REF_DOC_DATA).in(advancedFilters.getRefDocData());	
////			if(advancedFilters.getRequestedTs()!=null && !advancedFilters.getRequestedTs().isEmpty())			
////				inboxCriteria.and(WfConstants.WF_INB_CREATED_TS).gte(CommonUtils.dateFormatter(advancedFilters.getRequestedTs()+" 00:00:00"))
////				.lte(CommonUtils.dateFormatter(advancedFilters.getRequestedTs()+" 23:59:59"));
////			if(advancedFilters.getUpdatedTs()!=null && !advancedFilters.getUpdatedTs().isEmpty())			
////				inboxCriteria.and(WfConstants.WF_INB_UPDATED_TS).gte(CommonUtils.dateFormatter(advancedFilters.getUpdatedTs()+" 00:00:00"))
////				.lte(CommonUtils.dateFormatter(advancedFilters.getUpdatedTs()+" 23:59:59"));
////			
////			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
////			
////			
////				if (advancedFilters.getFromDate() != null && !advancedFilters.getFromDate().isEmpty()
////						&& advancedFilters.getToDate() != null && !advancedFilters.getToDate().isEmpty()) {
////					Date dateFrom = inputFormat.parse(advancedFilters.getFromDate()+ " 00:00:00");
////					String fromDates = outputFormat.format(dateFrom);
////					Date dateTo = inputFormat.parse(advancedFilters.getToDate()+ " 23:59:59");
////				    String toDates = outputFormat.format(dateTo);
////
////					inboxCriteria.and(WfConstants.WF_UPDATED_TS).gte(com.portal.validation.utils.CommonUtils.IsoDateFormatters(fromDates))
////							.lte(com.portal.validation.utils.CommonUtils.IsoDateFormatters(toDates));
////				}
////				 else {
////				if (advancedFilters.getFromDate() != null && !advancedFilters.getFromDate().isEmpty()) {
////					Date dateFrom = inputFormat.parse(advancedFilters.getFromDate()+ " 00:00:00");
////					String fromDates = outputFormat.format(dateFrom);
////					inboxCriteria.and(WfConstants.WF_INB_UPDATED_TS).gte(com.portal.validation.utils.CommonUtils.IsoDateFormatters(fromDates))
////							.lte(new Date());
////					}
////				if (advancedFilters.getToDate() != null && !advancedFilters.getToDate().isEmpty()) {
////					Date dateTo = inputFormat.parse(advancedFilters.getToDate()+ "  00:00:00");
////				    String toDates = outputFormat.format(dateTo);
////				    Date fromDate = DateUtils.addMinutes(DateUtils.addHours(com.portal.validation.utils.CommonUtils.IsoDateFormatters(toDates),-23),-59 );
////					inboxCriteria.and(WfConstants.WF_INB_UPDATED_TS).gte(fromDate)
////							.lte(com.portal.validation.utils.CommonUtils.IsoDateFormatters(toDates));
////			    	}
////				}
////			if(advancedFilters.getStatus()!=null && !advancedFilters.getStatus().isEmpty())			
////				inboxCriteria.and(WfConstants.WF_INB_STATUS).is(advancedFilters.getStatus());
////			
////			if(advancedFilters.getWfType()!=null && !advancedFilters.getWfType().isEmpty())			
////				inboxCriteria.and("wfType").is(advancedFilters.getWfType());
////				
////			
////			} catch (Exception e) {
////			e.printStackTrace();
////		}		
//	}
//
	@SuppressWarnings("unchecked")
	@Override
	public String getWfShortIdByWfId(String wfId) {
		try {
			
			WfMaster wfMaster = wfMasterRepo.getWfShortIdDetailsByWfRefId(wfId);
			
//			Query findQuery = new Query();
//			findQuery.addCriteria(Criteria.where("wfId").is(wfId).and("markAsDelete").is(false));
//			Map<String, Object> wf = mongoTemplate.findOne(findQuery, Map.class,
//					MongoEntityConstants.WF_WORKFLOW_MASTER);
			if (wfMaster != null) 
				return (wfMaster.getWfShortId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<WfServiceTasks> getDefaultWfServiceTasks(String wfTypeId) {
		
		List<WfServiceTasks> wfServiceTasksList = wfServiceTasksRepo.getDefaultWfServiceTaksDetails(wfTypeId,Arrays.asList(WfConstants.DEFAULT_SERVICE_TASK_TYPE, WfConstants.DEFAULT_EXTENSION_SERVICE_TASK_TYPE));
		return wfServiceTasksList;
		
//		Query findQuery = new Query();
//		findQuery.addCriteria(Criteria.where("wfTypeId").is(wfTypeId).and("serviceType").in(
//				Arrays.asList(WfConstants.DEFAULT_SERVICE_TASK_TYPE, WfConstants.DEFAULT_EXTENSION_SERVICE_TASK_TYPE))
//				.and("markAsDelete").is(false));
//		return mongoTemplate.find(findQuery, WfServiceTasks.class);
	}
//	
//	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getWfMasterByWfId(String wfId) {
		Map<String, Object> wf = new HashMap<>();
		try {
			
			WfMaster wfMaster = wfMasterRepo.getWfMasterDataByWfId(wfId);
			if(wfMaster != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				wf = (Map<String, Object>) objectMapper.convertValue(wfMaster, Map.class);
				return wf;
			}
			
//			Query findQuery = new Query();
//			findQuery.addCriteria(Criteria.where("wfId").is(wfId).and("markAsDelete").is(false));
//			Map<String, Object> wf = mongoTemplate.findOne(findQuery, Map.class,
//					MongoEntityConstants.WF_WORKFLOW_MASTER);
//				return wf;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public Map<String, Object> getMasterWF(String wfType) {
//		logger.info("Entered into the method: getMasterWF");
//		Map wfMaster = new HashMap<>();
//		WfMaster wfMasterData = wfMasterRepo.getWfDetailsByWfType(wfType);
//		if(wfMaster != null) {
//			ObjectMapper objectMapper = new ObjectMapper();
//			wfMaster = (Map) objectMapper.convertValue(wfMasterData, Map.class);
//		}
//		
////		Query findQuery = new Query();
////		findQuery.addCriteria(
////				Criteria.where("orgId").is(null).and("wfType").is(wfType).and("markAsDelete").is(false));
////		
////		Map wfMaster = mongoTemplate.findOne(findQuery, Map.class, MongoEntityConstants.WF_WORKFLOW_MASTER);
//		
//		logger.info("Exit from the method: getMasterWF");
//		return wfMaster;
//	}
//	
//	@SuppressWarnings("rawtypes")
//	public String getDefaultWfId(String orgId, String wfType){
//		logger.info("Entered into the method: getDefaultWfId");
//		String wfId = null;
////		try{
////
////			Query findQuery = new Query();
////			
////			Criteria criteria = Criteria.where("markAsDelete").is(false).and("orgId").is(orgId)
////					.and("isDefault").is(true).and("wfType").is(wfType);
////			List<Map> defaultWf = mongoTemplate.find(findQuery.addCriteria(criteria), Map.class, MongoEntityConstants.WF_WORKFLOW_MASTER);
////			if(defaultWf.isEmpty()){
////				findQuery = new Query();
////				findQuery.addCriteria(
////						Criteria.where("orgId").is(null).and("wfType").is(wfType)
////						.and("markAsDelete").is(false));
////				
////				Map wfMaster = mongoTemplate.findOne(findQuery, Map.class, MongoEntityConstants.WF_WORKFLOW_MASTER);
////				if(wfMaster != null)
////					wfId = wfMaster.get("wfId")+"";
////			}else{
////				wfId = defaultWf.get(0).get("wfId")+"";
////			}
////			
////		}catch(Exception e){
////			e.printStackTrace();
////		}
//		logger.info("Exit from the method: getDefaultWfId");
//		return wfId;
//	}
//	
//	@SuppressWarnings("rawtypes")
//	@Override
//	public List<Map> getEmailTemplateDet(WfCommonModel wfModel) {
//		logger.info("Entered into the method: getEmailTemplateDet");
//		List<Map> templateDet = null;
////		try {
////			
////			Query findQuery = new Query();
////			findQuery.addCriteria(Criteria.where("type").is(wfModel.getEmailTempType()).and("markAsDelete").is(false)
////					.orOperator(Criteria.where("orgId").is(null), Criteria.where("orgId").is(wfModel.getGenericRequestHeaders().getOrgId())));
////			templateDet = mongoTemplate.find(findQuery, Map.class,MongoEntityConstants.NM_EMAIL_TEMPLATES);
////			
////			List<Map> orgTemp = templateDet.stream().filter(i ->  i.get("orgId") != null ).collect(Collectors.toList());
////			for(Map temp : orgTemp){
////				List<Map> delete =  templateDet.stream().filter(t -> t.get("templateShortId").equals(temp.get("templateShortId")) && t.get("orgId") == null).collect(Collectors.toList());
////				templateDet.removeAll(delete);
////			}
////			
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		logger.info("Exit from the method: getEmailTemplateDet");
//		return templateDet;
//	}
//	
	public List<String> getRoleShortIdsByUserIds(List<String> userIds, GenericRequestHeaders requestHeader) {
		List<String> roleShortIds = new ArrayList<String>();
		List<Integer> roleIds = new ArrayList<>();
		List<String> secondRoleIds = new ArrayList<>();
		try {
			List<Integer> userIdsList = userIds.stream().map(Integer::parseInt).collect(Collectors.toList());
			List<UmOrgUsers> umOrgUserList = umOrgUsersRepo.geUserDetailsByUserIds(userIdsList);
			if(umOrgUserList != null && !umOrgUserList.isEmpty()) {
				for(UmOrgUsers umOrg : umOrgUserList) {
					roleIds.add(umOrg.getUmOrgRoles().getRoleId());
					secondRoleIds.add(umOrg.getSecondaryRoles());
				}
			}
			if(roleIds != null && !roleIds.isEmpty()) {
				String oriRoleIds = roleIds.stream().map(String::valueOf)
		        .collect(Collectors.joining(","));

				String query = "select ur.role_id,ur.role_short_id from um_org_roles ur where ur.mark_as_delete = false";
				query = query + " and ur.role_id in (" +oriRoleIds+ ")";
				
				List<Object[]> umOrgRoles = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
				if(umOrgRoles != null && !umOrgRoles.isEmpty()) {
					for(Object[] roles : umOrgRoles) {
						roleShortIds.add((String) roles[1]);
					}
//					roleShortIds = umOrgRoles.stream().map(UmOrgRoles::getRoleShortId).collect(Collectors.toList());
				}
			}
//			Query query = new Query();
//			query.addCriteria(Criteria.where("orgId").is(requestHeader.getOrgId()).and("userId").in(userIds)
//					.and("markAsDelete").is(false));
//			List<UmOrgUsers> umOrgUserList = mongoTemplate.find(query, UmOrgUsers.class);
//			List<Integer> roleIds = umOrgUserList.stream().map(UmOrgRoles::getRoleId).collect(Collectors.toList());
//			List<String> secondRoleIds = umOrgUserList.stream().flatMap(myObject -> myObject.getSecondaryRoles() != null ? myObject.getSecondaryRoles().stream() : (new ArrayList<String>()).stream()).collect(Collectors.toList());
//			roleIds.addAll(secondRoleIds);
//			query = new Query();
//			query.addCriteria(Criteria.where("roleId").in(roleIds).and("markAsDelete").is(false));
//			List<UmOrgRoles> umOrgRoles = mongoTemplate.find(query, UmOrgRoles.class);
//			roleShortIds = umOrgRoles.stream().map(UmOrgRoles::getRoleShortId).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleShortIds;
	}
	
	public List<Integer> getUserIdsByRoleShortIds(List<String> roleShortIds, GenericRequestHeaders requestHeader) {
		List<Integer> userIds = new ArrayList<Integer>();
		List<String> userIdsList = new ArrayList<>();
		List<Integer> roleIds = new ArrayList<>();
		try {
			String roleShortId = String.join("','", roleShortIds);
			String query = "select ur.role_id from um_org_roles ur where ur.mark_as_delete = false and ur.role_short_id in ('" +roleShortId+ "')";
			List<Integer> umOrgRoles = (List<Integer>) baseDao.findBySQLQueryWithoutParams(query);
			if(umOrgRoles != null && !umOrgRoles.isEmpty()) {
				roleIds.addAll(umOrgRoles);
//				for(Integer umOrg : umOrgRoles) {
//					roleIds.add((Integer) umOrg[0]);
//				}
			}
//			List<Integer> roleIds = umOrgRoles.stream().map(UmOrgRoles::getRoleId).collect(Collectors.toList());
			
			List<UmOrgUsers> umOrgUserList = umOrgUsersRepo.getUsersByRoleIds(roleIds);
			if(umOrgUserList != null && !umOrgUserList.isEmpty()) {
				for(UmOrgUsers umOrg : umOrgUserList) {
					userIds.add(umOrg.getUmUsers().getUserId());
				}
//				userIdsList = userIds.stream().map(String::valueOf).collect(Collectors.toList());
			}
			
//			Query query = new Query();
//			query.addCriteria(Criteria.where("roleShortId").in(roleShortIds).and("markAsDelete").is(false));
//			List<UmOrgRoles> umOrgRoles = mongoTemplate.find(query, UmOrgRoles.class);
//			List<Integer> roleIds = umOrgRoles.stream().map(UmOrgRoles::getRoleId).collect(Collectors.toList());
//			query = new Query();
//			query.addCriteria(Criteria.where("orgId").is(requestHeader.getOrgId()).and("roleId").in(roleIds)
//					.and("markAsDelete").is(false));
//			List<UmOrgUsers> umOrgUserList = mongoTemplate.find(query, UmOrgUsers.class);
//			userIds = umOrgUserList.stream().map(UmOrgUsers::getUserId).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userIds;
	}
//
//	@Override
//	public List<Map> getWfMasterDetails(OrgUserDetails payload, UmOrgRoles umOrgRoles, GenericRequestHeaders requestHeader) {
//		logger.info("Entered into the method: getPendingInboxDetails");
//		List<Map> wfInbox = new ArrayList<Map>();
////		try{
////			String status = "INACTIVE";
////			Query query = new Query();
////			query.addCriteria(Criteria.where("markAsDelete").is(false).and("status").ne(status).and("orgId").is(requestHeader.getOrgId())
////					.orOperator(Criteria.where("process.userTask.candidateGroups").is(umOrgRoles.getRoleShortId())
////							.and("process.userTask.candidateUsers").is(payload.getUserId()),
////							Criteria.where("process.userTask.candidateGroups").is(umOrgRoles.getRoleShortId())));
////			
////			List<Map> wfMaster = mongoTemplate.find(query, Map.class, MongoEntityConstants.WF_WORKFLOW_MASTER);
////			if(wfMaster != null && !wfMaster.isEmpty()){
////				wfInbox = wfMaster;
////			}
////		}catch(Exception e){
////			e.printStackTrace();
////		}
//		return wfInbox;
//		
//	}
//
//	@Override
//	public Map<String, Object> getDefaultWfDetails(String wfType) {
//		Query findQuery = new Query();
//		findQuery = new Query();
//		findQuery.addCriteria(Criteria.where(GeneralConstants.ORG_ID)
//				.is(commonService.getRequestHeaders().getOrgId())
//				.and(GeneralConstants.IS_DEFAULT).is(true)
//				.and(GeneralConstants.WFTYPE).is(wfType)
//				.and(GeneralConstants.MARK_AS_DEL).is(false));
//		Map<String, Object> wfDetails = mongoTemplate.findOne(findQuery, Map.class, MongoEntityConstants.WF_WORKFLOW_MASTER);
//		
//		return wfDetails;
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	public List<Map> getWfDetails(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders) {
//		String METHOD_NAME = "getWfDetails";
//		logger.info("Enter into : " + METHOD_NAME);
//		List<Map> wfList = new ArrayList<Map>();
//		try {
//			
//			String query = "select * from wf_master wm where wm.wf_type '" +payload.getModule()+ "' and wm.status '" +payload.getStatus()+ "' and wm.mark_as_delete = false";
//			if (payload.getWfId() != null) {
//				query = query + " an wm.wf_id '" + payload.getWfId()+ "'";
//			}
//			
//			List<WfMaster> wfMasterList = (List<WfMaster>) baseDao.findBySQLQueryWithoutParams(query);
//			if(wfMasterList != null && wfMasterList.isEmpty()) {
//				ObjectMapper objectMapper = new ObjectMapper();
//				wfList = (List<Map>) objectMapper.convertValue(wfMasterList, Map.class);
//			}
////			Query query = new Query();
////			Criteria criteria = new Criteria();
////			criteria = (Criteria.where(GeneralConstants.MARK_AS_DEL).is(false).and(GeneralConstants.ORG_ID)
////					.is(requestHeaders.getOrgId()).and(GeneralConstants.WFTYPE).is(payload.getModule())
////					.and(GeneralConstants.FORM_STATUS).is(payload.getStatus()));
////			if (payload.getWfId() != null) {
////				criteria.and(GeneralConstants.WF_ID).is(payload.getWfId());
////			}
////			query.addCriteria(criteria);
////			wfList = mongoTemplate.find(query, Map.class, MongoEntityConstants.WF_WORKFLOW_MASTER);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error while getting wf List : " + METHOD_NAME);
//		}
//		logger.info("Exit from :" + METHOD_NAME);
//		return wfList;
//	}
//
//	@SuppressWarnings({ "unchecked", "null", "unused" })
//	@Override
//	public boolean updateWfInboxMaster(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders) {
//		String METHOD_NAME = "updateNewWfInboxMaster";
//		logger.info("Enter into : " + METHOD_NAME);
//		Map<String, Object> wfInboxMasterDetails = new HashMap<String, Object>();
//		boolean flag = false;
//		try {
//			Query query = new Query();
//			Criteria criteria = new Criteria();
//			query.addCriteria(Criteria.where(GeneralConstants.MARK_AS_DEL).is(false).and(GeneralConstants.ORG_ID)
//					.is(requestHeaders.getOrgId()).and("currentStatus").is(GeneralConstants.FORM_DATA_PENDING)
//					.and("objectRefId").is(payload.getOmOrgFormValId()));
//			wfInboxMasterDetails = (Map<String, Object>) mongoTemplate.findOne(query, Map.class,
//					MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
//			if (!wfInboxMasterDetails.isEmpty()) {
//				wfInboxMasterDetails.put("markAsDelete", true);
//				Document dbDoc = new Document();
//				mongoTemplate.getConverter().write(wfInboxMasterDetails, dbDoc);
//				Update update = Update.fromDocument(dbDoc);
//				query = new Query(Criteria.where("inboxMasterId").is(wfInboxMasterDetails.get("inboxMasterId"))
//						.and("markAsDelete").is(false));
//				mongoTemplate.upsert(query, update, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
//				flag = true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error while update wfInboxMaster : " + METHOD_NAME);
//		}
//		logger.info("Exit from :" + METHOD_NAME);
//		return flag;
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	public boolean updateInvitationData(Map<String, Object> vendorPartInviteDet, @NotNull WfUpdatePayload payload,
//			Map collectionData) {
//		String METHOD_NAME = "updateInvitationData";
//		logger.info("Enter into : " + METHOD_NAME);
//		boolean flag = false;
////		try {
////			if (!vendorPartInviteDet.isEmpty()) {
////				vendorPartInviteDet.put("wfId", payload.getWfId());
////				Document dbDoc = new Document();
////				mongoTemplate.getConverter().write(vendorPartInviteDet, dbDoc);
////				Update update = Update.fromDocument(dbDoc);
////				Query query = new Query();
////				query.addCriteria(Criteria.where("invitationId").is(collectionData.get("invitationId"))
////						.and("markAsDelete").is(false));
////				if (GeneralConstants.VENDOR.equalsIgnoreCase(payload.getModule())) {
////					mongoTemplate.upsert(query, update, MongoEntityConstants.OM_ORG_VENDOR_INVITATION);
////				} else {
////					mongoTemplate.upsert(query, update, MongoEntityConstants.PARTNER_INVITATION);
////				}
////				flag = true;
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////			logger.error("Error while updating wfId : " + METHOD_NAME);
////		}
//		logger.info("Exit from :" + METHOD_NAME);
//		return flag;
//	}
//
//	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
//	@Override
//	public List<Map> updateWfInboxListOfWfIds(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders) {
//		String METHOD_NAME = "updateWfInboxListByWfId";
//		logger.info("Enter into : " + METHOD_NAME);
//		List<Map> getWfInboxList = new ArrayList<Map>();
//		try {
//			Query query = new Query();
//			query.addCriteria(Criteria.where(GeneralConstants.MARK_AS_DEL).is(false).and(GeneralConstants.ORG_ID)
//					.is(requestHeaders.getOrgId()).and("currentStatus").is(GeneralConstants.FORM_DATA_PENDING)
//					.and("wfTypeId").is(payload.getWfId()));
//			getWfInboxList = mongoTemplate.find(query, Map.class, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
//			
//			if(!getWfInboxList.isEmpty()) {
//			List<String> omorgFormValIdsList = new ArrayList<String>();
//			if (getWfInboxList != null && getWfInboxList.size() != 0)
//				getWfInboxList.stream().forEach(ven -> {
//					omorgFormValIdsList.add(ven.get("objectRefId") + "");
//				});
//			Query findQuery1 = new Query();
//			Criteria criteria1 = Criteria.where("objectRefId").in(omorgFormValIdsList).and(GeneralConstants.ORG_ID)
//					.is(requestHeaders.getOrgId()).and(GeneralConstants.MARK_AS_DEL).is(false);
//			findQuery1.addCriteria(criteria1);
//
//			Update update = new Update();
//			update.set(GeneralConstants.MARK_AS_DEL, true);
//			mongoTemplate.updateMulti(findQuery1, update, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error while updating InvitationData : " + METHOD_NAME);
//		}
//		logger.info("Exit from :" + METHOD_NAME);
//		return getWfInboxList;
//	}
//	
	public List<String> populateRoleNames(WfCommonModel workFlowCommonModel,ApprovalListView payload){
		List<String> roleNames = new ArrayList<>();
		if (workFlowCommonModel.getLoggedUser() != null){
			Map<Integer,RolesTo> rolesToMap = new HashMap<>();
			String query = "select ur.role_id,ur.role_desc,ur.role_short_id from um_org_roles ur where ur.role_id = " + Integer.parseInt(workFlowCommonModel.getLoggedUser().getRoleId()) + " and ur.mark_as_delete = false";
			List<Object[]> umOrgRoles = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			if(umOrgRoles != null && !umOrgRoles.isEmpty()) {
				
//					umOrgRoles.forEach(orgRole -> {
//						if (orgRole != null && !orgRole.isMarkAsDelete()) {
//							RolesTo rolesTo = new RolesTo();
//							rolesTo.setRoleId(orgRole.getRoleId());
//							rolesTo.setRoleName(orgRole.getRoleDesc());
//							rolesTo.setRoleShortId(orgRole.getRoleShortId());
//							rolesToMap.put(orgRole.getRoleId(), rolesTo);
//							roleNames.add(orgRole.getRoleShortId());
//						}
//					});
				
				for(Object[] orgRole:umOrgRoles) {
					if(orgRole!=null) {
						RolesTo rolesTo = new RolesTo();
						rolesTo.setRoleId((Integer) orgRole[0]);
						rolesTo.setRoleName((String) orgRole[1]);
						rolesTo.setRoleShortId((String) orgRole[2]);
						rolesToMap.put((Integer) orgRole[0], rolesTo);
						roleNames.add((String) orgRole[2]);
					}
				}
			}
			
//			Map<String, RolesTo> roles = orgDao.getRolesByRoleIds(workFlowCommonModel.getLoggedUser().getSecondaryRoles());
//			roleNames = umOrgRoles.values().stream().map(RolesTo::getRoleShortId).collect(Collectors.toList());
		}
//		roleNames.add(workFlowCommonModel.getLoggedUser().getRoleName());
		return roleNames;
	}
//	
//	@Override
//	public List<WfInboxMaster> updateWorkItem(Map payload, GenericRequestHeaders requestHeaders) {
////		// TODO Auto-generated method stub
////		String METHOD_NAME = "updateWorkItem";
////		logger.info("Enter into : " + METHOD_NAME);
//		List<WfInboxMaster> map = new ArrayList<WfInboxMaster>();
////		Update update = new Update();
////		Update update2 = new Update();
////		Query query = new Query();
////		ObjectMapper objM = new ObjectMapper();
////		WfInbox copyPayload = new WfInbox();
////		WfInbox copyToNewNode = new WfInbox();
////		List<WfInbox> copyInboxMas = new ArrayList<WfInbox>();
////		List<String> inboxMasterIds = new ArrayList<>();
////		List<String> wfInboxIds = new ArrayList<>();
////		List<Map> pay = new ArrayList<Map>();
////		try {
////			if (payload.get("list") != null) {
////				pay = (List<Map>) payload.get("list");
////				if (pay != null && !pay.isEmpty()) {
////					for (Map data : pay) {
////						String inboxMasterId = (String) data.get("inboxMasterId");
////						String wfInboxId = (String) data.get("wfInboxId");
////						inboxMasterIds.add(inboxMasterId);
////						wfInboxIds.add(wfInboxId);
////					}
////				}
////			}
////			query.addCriteria(Criteria.where(GeneralConstants.MARK_AS_DEL).is(false).and(GeneralConstants.ORG_ID)
////					.is(requestHeaders.getOrgId()).and("wfInbox.wfInboxId").in(wfInboxIds).and("inboxMasterId")
////					.in(inboxMasterIds));
////			List<WfInboxMaster> wfInboxMaster = mongoTemplate.find(query, WfInboxMaster.class,
////					MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
////			if (wfInboxMaster != null && !wfInboxMaster.isEmpty()) {
////				for (WfInboxMaster wfInboxMas : wfInboxMaster) {
////					List<WfInbox> inboxDetails = wfInboxMas.getWfInbox();
////					for (WfInbox e : inboxDetails) {
////						if (e.getStatus().equalsIgnoreCase("PENDING")) {
////							copyPayload = objM.readValue(objM.writeValueAsString(e), WfInbox.class);
////							copyToNewNode = objM.readValue(objM.writeValueAsString(e), WfInbox.class);;
////							e.setMarkAsDelete(true);
////						} else {
////							copyInboxMas.add(e);
////						}
////					}
////
//////					update2.addToSet("deletedInboxEntry", copyPayload);
//////					mongoTemplate.upsert(query, update2, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
////
////					if (copyPayload.getUserInbox() != null) {
////						List<Map<String, Object>> userInbox = copyPayload.getUserInbox();
////						for (int i = 0; i < userInbox.size(); i++) {
////							if (userInbox.get(i).get("userId").toString()
////									.equalsIgnoreCase((String) payload.get("transferFrom"))) {
////								List<String> ids = (List<String>) payload.get("ids");
////								for (String id : ids) {
////									Map<String, Object> map1 = new HashMap<String, Object>();
////									if (userInbox.get(i).get("userId").toString()
////											.equalsIgnoreCase((String) payload.get("transferFrom"))) {
////										userInbox.remove(i);
////										map1.put("userId", id);
////										map1.put("status", "PENDING");
////									} else {
////										map1.put("userId", id);
////										map1.put("status", "PENDING");
////									}
////									userInbox.add(map1);
////								}
////								break;
////							}
////						}
////					}
////					if (("USER".equalsIgnoreCase((String) payload.get("approvalType"))
////							&& "ALL".equalsIgnoreCase((String) payload.get("approvalLevels")))
////							|| ("ROLE".equalsIgnoreCase((String) payload.get("approvalType"))
////									&& "ALL".equalsIgnoreCase((String) payload.get("approvalLevels"))) ||
////							("INITIATOR".equalsIgnoreCase((String) payload.get("approvalType"))
////									&& "ALL".equalsIgnoreCase((String) payload.get("approvalLevels"))) ||
////							("RULERESULT".equalsIgnoreCase((String) payload.get("approvalType"))
////									&& "ALL".equalsIgnoreCase((String) payload.get("approvalLevels")))) {
////						List<String> refToUsers = copyPayload.getRefToUsers();
////						if (refToUsers != null && !refToUsers.isEmpty()) {
////							for (int i = 0; i < refToUsers.size(); i++) {
////								if (refToUsers.get(i).equalsIgnoreCase((String) payload.get("transferFrom"))) {
////									refToUsers.remove(refToUsers.get(i));
////									List<String> ids = (List<String>) payload.get("ids");
////									for (String id : ids) {
////										refToUsers.add(id);
////										copyPayload.setRefToUsers(refToUsers);
////									}
////								}
////							}
////
////						}
////						List<String> shortIds = this.getRoleShortIdsByUserIds((List<String>) payload.get("ids"),
////								requestHeaders);
////						List<String> refTo = copyPayload.getRefTo();
////						if (!refTo.toString().equalsIgnoreCase(shortIds.toString())) {
////							refTo.addAll(shortIds);
////						}
////						copyPayload.setRefTo(refTo);
////
////						List<String> refToType = copyPayload.getRefToType();
////						if (!refToType.toString().equalsIgnoreCase(shortIds.toString())) {
////							refToType.addAll(shortIds);
////						}
////						copyPayload.setRefToType(refToType);
////
////					} else {
////						List<String> shortIds = this.getRoleShortIdsByUserIds((List<String>) payload.get("ids"),
////								requestHeaders);
////						List<String> refTo = copyPayload.getRefTo();
////						if (!refTo.toString().equalsIgnoreCase(shortIds.toString())) {
////							refTo.addAll(shortIds);
////						}
////						copyPayload.setRefTo(refTo);
////						
////						List<String> refToType = copyPayload.getRefToType();
////						if (!refToType.toString().equalsIgnoreCase(shortIds.toString())) {
////							refToType.addAll(shortIds);
////						}
////						copyPayload.setRefToType(refTo);
////						
////						copyPayload.setRefToUsers((List<String>) payload.get("ids"));
////					}
////
////					copyInboxMas.add(copyPayload);
////					update.set("wfInbox", copyInboxMas);
////
////					mongoTemplate.upsert(query, update, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
////					
////					update2.addToSet("deletedInboxEntry", copyToNewNode);
////					mongoTemplate.upsert(query, update2, MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
////
////				}
////			}
////			List<WfInboxMaster> wfInboxMaster1 = mongoTemplate.find(query, WfInboxMaster.class,
////					MongoEntityConstants.WF_WORKFLOW_INBOX_MASTER);
////			if (wfInboxMaster != null && !wfInboxMaster.isEmpty()) {
////				map = wfInboxMaster1;
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		return map;
//	}
	@Override
	public List<GdWfTypes> getWfTypesByIds(GenericRequestHeaders requestHeader, List<String> wfTypeId) {
		List<GdWfTypes> gdWfTypes = gdWfTypesRepo.getWfTypeDataByIds(wfTypeId);
		return gdWfTypes;
	}

	@Override
	public String getDefaultWfId(String type,Integer location) {
		logger.info("Entered into the method: getDefaultWfId");
		String wfId = null;
		try {
//			WfMasterDocTypes wfMasterDocTypes = wfMasterDocTypesRepo.getWfTypeOnType(wfType);
			List<Object[]> wfDocTypesandLocations = wfMasterDocTypesRepo.getDefaultWfIdOnLocation(type,location);
			if(wfDocTypesandLocations != null && !wfDocTypesandLocations.isEmpty()) {
				for(Object[] obj :wfDocTypesandLocations ) {
					wfId = (String) obj[0];
				}
			}
//			WfMasterDocTypes wfMasterDocTypes = wfMasterDocTypesRepo.getWfTypeOnType(null);
//			if(wfMasterDocTypes != null) {
//				wfId = wfMasterDocTypes.getWfId();
//			}
//			WfMaster wfMaster = wfMasterRepo.getDefaultWfDetails(wfType);
//			if(wfMaster != null) {
//				wfId = wfMaster.getWfId();
//			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return wfId;
	}

	@Override
	public List<WfInboxMasterDetails> getWfInboxDetails(WfCommonModel workFlowCommonModel) {
		List<WfInboxMasterDetails> wfInboxMaster = wfInboxMasterDetailsRepo.getInboxMasterDetails(workFlowCommonModel.getWorkFlowEvent().getWfInboxMasterId()+"");
		return wfInboxMaster;
	}
	@Override
	public WfInboxResponse getWfInboxByUserForApprover(String wfId, String objRefId) {
		List<Object[]> data = new ArrayList<>();
		List<Object[]> data2 = new ArrayList<>();
		List<String> objectRefId = new ArrayList<>();
		String inboxMasterId = null;
		WfInboxDetails wfInboxDetails = new WfInboxDetails();
		List<WfInboxDetails> wfInboxDetailsList = new ArrayList<>();
		WfInboxResponse response = new WfInboxResponse();
		try {
			if (wfId != null && objRefId != null) {
				String query = "select wm.wf_desc,wm.wf_short_id,wm.current_status,wm.wf_type,wm.wf_type_id,wm.wf_title,wm.object_ref_id,wm.inbox_master_id,itm.item_id,wdt.wf_name from wf_inbox_master wm inner join clf_order_items itm on wm.object_ref_id = itm.item_id inner join wf_master_doc_types wdt on wm.wf_type_id = wdt.wf_id where wm.object_ref_id = '"
						+ objRefId + "' and wm.wf_type_id = '" + wfId + "' and wm.mark_as_delete=false";
				data = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

				if (data != null && !data.isEmpty()) {
					for (Object[] row : data) {

						inboxMasterId = row[7] != null ? row[7].toString() : null;
						wfInboxDetails.setWfDesc(row[0] != null ? row[0].toString() : null);
						wfInboxDetails.setWfShortId(row[1] != null ? row[1].toString() : null);
						wfInboxDetails.setCurrentStatus(row[2] != null ? row[2].toString() : null);
						wfInboxDetails.setWfType(row[3] != null ? row[3].toString() : null);
						wfInboxDetails.setWfTypeId(row[4] != null ? row[4].toString() : null);
						wfInboxDetails.setWfTitle(row[5] != null ? row[5].toString() : null);
						wfInboxDetails.setObjectRefId(row[6] != null ? row[6].toString() : null);
						wfInboxDetails.setDocumentNumber(row[8] != null ? row[8].toString() : null);
						wfInboxDetails.setWfType(row[9] != null ? row[9].toString() : null);
						objectRefId.add(row[6] != null ? row[6].toString() : null);
					}
				}
				String query1 = "select wim.inbox_master_id,wim.wf_inbox_id,wim.status,wim.created_ts,wim.created_by,wim.request_raised_by,wim.approval_type,wim.approval_levels from wf_inbox_master_details wim where wim.inbox_master_id = '"
						+ inboxMasterId + "' and wim.mark_as_delete=false and wim.status='PENDING'";
				data2 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
				if (data2 != null && !data2.isEmpty()) {
					for (Object[] row : data2) {
						wfInboxDetails.setInboxMasterId(row[0] != null ? row[0].toString() : null);
						wfInboxDetails.setWfInboxId(row[1] != null ? row[1].toString() : null);
						wfInboxDetails.setStatus(row[2] != null ? row[2].toString() : null);
						wfInboxDetails.setCreatedTs(CommonUtils.dateFormatter((Date) row[3], "dd-MM-yyyy HH:mm:ss"));
						wfInboxDetails.setCreatedBy(row[4] != null ? row[4].toString() : null);
						wfInboxDetails.setRequestRaisedBy(row[5] != null ? row[5].toString() : null);
						wfInboxDetails.setApprovalType(row[6] != null ? row[6].toString() : null);
						wfInboxDetails.setApprovalLevels(row[7] != null ? row[7].toString() : null);
					}
				}

//				List<DMSDocumentMaster> docMasterOnMasterIds = dmsDocumentMasterRepo
//						.getDocMasterOnMasterIds(objectRefId);
//				List<String> formStatuses = new ArrayList<String>();
//				if (docMasterOnMasterIds != null && !docMasterOnMasterIds.isEmpty()) {
//					for (DMSDocumentMaster docMaster : docMasterOnMasterIds) {
//						formStatuses.add(docMaster.getDocumentStatus());
//					}
//				}
				if (data != null && !data.isEmpty()) {
					for (Object[] row : data) {
						inboxMasterId = row[7] != null ? row[7].toString() : null;
//							WfInboxDetails matchingDetails = wfInboxDetailsList.stream()
//									.filter(detail -> detail.getInboxMasterId().equals(inboxMasterId)).findFirst()
//									.orElse(null);
//						DMSDocumentMaster docMasterOnMasterId = dmsDocumentMasterRepo
//								.getDocMasterOnMasterId(row[6] != null ? row[6].toString() : null);
//						String wfRefData = getWfRefData(row[4] != null ? row[4].toString() : null);
//						ObjectMapper objectMapper = new ObjectMapper();
//						List<WfStatuses> readValue = objectMapper.readValue(wfRefData,
//								new TypeReference<List<WfStatuses>>() {
//								});
//						if (wfInboxDetails != null) {
//							if (docMasterOnMasterId != null && docMasterOnMasterId.getDocumentStatus() != null) {
//								wfInboxDetails.setWfStatus(docMasterOnMasterId.getDocumentStatus());
//							}
//							wfInboxDetails.setWfStatuses(readValue);
//						}
					}
				}
			}

			wfInboxDetailsList.add(wfInboxDetails);
			response.setWfInboxData(wfInboxDetailsList);
			response.setTotalCnt(wfInboxDetailsList.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public void removeAccesskey(WfCommonModel workFlowCommonModel) {
		List<WfInboxMasterDetails> wfInboxMaster = wfInboxMasterDetailsRepo.getWfInboxMasterDetailsData(workFlowCommonModel.getWorkFlowEvent().getWfInboxId(),false);
		if(wfInboxMaster != null && !wfInboxMaster.isEmpty()) {
			for(WfInboxMasterDetails master : wfInboxMaster) {
				master.setAccessKey(null);
				master.setStatus(workFlowCommonModel.getWorkFlowEvent().getActStatus());
				baseDao.save(master);
			}
		}
		
	}

}
