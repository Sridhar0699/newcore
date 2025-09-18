package com.portal.workflow;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.basedao.IBaseDao;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.constants.GeneralConstants;
import com.portal.gd.entities.BookingUnits;
import com.portal.reports.utility.CommonUtils;
import com.portal.repository.BookingUnitsRepo;
import com.portal.repository.UmUsersRepository;
import com.portal.repository.WfDocTypesRepo;
import com.portal.repository.WfLocationsRepo;
import com.portal.repository.WfMasterDocTypesRepo;
import com.portal.repository.WfMasterRepo;
import com.portal.user.entities.UmUsers;
import com.portal.wf.entity.GdWfTypes;
import com.portal.wf.entity.WfDocTypes;
import com.portal.wf.entity.WfLocations;
import com.portal.wf.entity.WfMasterDocTypes;
import com.portal.wf.entity.WfServiceTasks;
import com.portal.workflow.model.RuleList;
import com.portal.workflow.model.WfConstants;
import com.portal.workflow.model.WfCreateRequest;
import com.portal.workflow.model.WfListViewModel;
import com.portal.workflow.model.WfStages;
import com.portal.workflow.model.WorkFlowTypesModel;

@Transactional
@Service
public class WorkFlowProcessServiceImpl implements WorkFlowProcessService {

	private static final Logger logger = Logger.getLogger(WorkFlowProcessServiceImpl.class);

	@Autowired
	private Environment environment;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private WfDocTypesRepo wfDocTypesRepo;

	@Autowired
	private WfDao wfDao;

	@Autowired
	private IBaseDao baseDao;

	@Autowired
	private UmUsersRepository umUsersRepository;

	@Autowired
	private WfMasterRepo wfMasterRepo;

	@Autowired
	private WfMasterDocTypesRepo docTypesRepo;
	
//	@Autowired
//	private GdDocumentTypeRepo documentTypeRepo;
	
	@Autowired
	private WfLocationsRepo locationsRepo;
	
	@Autowired
	private BookingUnitsRepo bookingUnitsRepo;
	
//	@Autowired
//	private GdLocationsRepo  gdLocationsRepo;

	@Override
	public GenericApiResponse createCustomWorkFlow(GenericRequestHeaders requestHeader, WfCreateRequest payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		WfCreateRequest copyPayload;
		try {
			if (validateWfLevels(payload, apiResponse, requestHeader)) {
				// for RuleResult copying object and pass in processWorkflowRefrenceData
				// 02-12-2022
				ObjectMapper objM = new ObjectMapper();
				copyPayload = objM.readValue(objM.writeValueAsString(payload), WfCreateRequest.class);
				// end
				Map<String, Object> wfData = this.processWorkFlowSaveData(requestHeader,
						processWorkFlowCreation(payload, requestHeader), payload);
				boolean status = wfDao.createWorkFlow(wfData, payload);
				if (status) {
					this.processWorkflowRefrenceData(requestHeader, copyPayload, wfData.get("wfId") + "");
					if (WfConstants.SAVE_AS_DRAFT.equalsIgnoreCase(payload.getStatus())) {
						apiResponse.setMessage(environment.getProperty("SAVE_WORKFLOW"));
					} else {
						apiResponse.setMessage(environment.getProperty("PUBLISH_WORKFLOW"));
					}
					apiResponse.setData(wfData.get("wfId"));
					apiResponse.setStatus(0);
				} else {
					apiResponse.setMessage(environment.getProperty("GEN_002"));
					apiResponse.setErrorcode("GEN_002");
				}
			}

		} catch (Exception e) {
			apiResponse.setMessage(environment.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
			logger.error("Error while getting organization details: " + ExceptionUtils.getStackTrace(e));
		}
		return apiResponse;
	}

	private boolean validateWfLevels(WfCreateRequest payload, GenericApiResponse apiResponse,
			GenericRequestHeaders requestHeader) {

		boolean finalResult = true;
		try {
			boolean isApprovalTypeEmpty = false;
			if (payload.getStatus() != null
					&& !payload.getStatus().equalsIgnoreCase(GeneralConstants.FORM_SAVE_AS_DRAFT)) {
				List<String> serviceTaskIdsList = new ArrayList<String>();
				for (WfStages wfDet : payload.getWfStages()) {
					wfDet.getServiceTaskIds().stream().forEach(serTaskId -> {
						serviceTaskIdsList.add(serTaskId);
					});

					if (wfDet.getApprovalType().equalsIgnoreCase(""))
						isApprovalTypeEmpty = true;
				}

				// remove null values
				serviceTaskIdsList.removeIf(Objects::isNull);

				// remove empty values
				serviceTaskIdsList.removeIf(String::isEmpty);

				if (serviceTaskIdsList.size() != 0)
					finalResult = true;
				else {

//		        	 GdConfigGroup configData = commonService.getConfigDataByGroupId(requestHeader, GeneralConstants.GDCONFIG_WFSERVICE_TASK_SKIP);
//		        	 if(configData != null) {
//		        		List<Map<String, Object>> values = configData.getValues(); 
//		        		List<Map<String, Object>> matchedModuleFrmGDCongif = values.stream()
//		                .filter(module -> module.get("key").toString().equalsIgnoreCase(payload.getWfType())).collect(Collectors.toList());
//		        		if(matchedModuleFrmGDCongif.size() == 0) {
//		        			finalResult = false;
//				        	apiResponse.setStatus(1);
//				    		apiResponse.setMessage(environment.getProperty("WF_001"));
//							apiResponse.setErrorcode("WF_001");
//		        		}
//		        	 }else {
//		        		 	finalResult = false;
//				        	apiResponse.setStatus(1);
//				    		apiResponse.setMessage(environment.getProperty("WF_001"));
//							apiResponse.setErrorcode("WF_001"); 
//		        	 }
				}

				if (isApprovalTypeEmpty) {
					finalResult = false;
					apiResponse.setStatus(1);
					apiResponse.setMessage(environment.getProperty("WF_002"));
					apiResponse.setErrorcode("WF_002");
				}
			}

		} catch (Exception e) {
			finalResult = false;
			apiResponse.setMessage(environment.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
			logger.error("Error while getting organization details: " + ExceptionUtils.getStackTrace(e));
		}

		return finalResult;
	}
	
	public Map<String, Object> processWorkFlowCreation(WfCreateRequest payload,
			GenericRequestHeaders requestHeader) {
		Map<String, Object> wfMap = new LinkedHashMap<String, Object>();
		try {
			List<Map<String, Object>> userTasks = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> sequenceFlow = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> serviceTasks = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> previousMailNotifications = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> nextMailNotifications = new LinkedList<Map<String, Object>>();
			Map<String, Map<String, String>> wfStagesInfo = this.getWfStagesInfo(payload);
//			List<WfServiceTasks> defaultServiceTasks = wfDao.getDefaultWfServiceTasks(payload.getWfTypeId());
			
			List<WorkFlowTypesModel> wfTypes = payload.getWfTypes();
            List<WfServiceTasks> defaultServiceTasks = new ArrayList<WfServiceTasks>();
            for (WorkFlowTypesModel wfType : wfTypes) {
                defaultServiceTasks = wfDao.getDefaultWfServiceTasks(wfType.getWfTypeId());
            }

			for (WfStages wfData : payload.getWfStages()) {
//				this.getMailNotifications(previousMailNotifications, nextMailNotifications, wfData, wfStagesInfo,
//						payload.isInitiatorFlag());
				String stageId = this.getStageName(wfData.getStageId());
				String approvalType = wfData.getApprovalType();
				Map<String, Object> userTaskMap = new LinkedHashMap<String, Object>();
				Map<String, Object> userTaskMap1 = new LinkedHashMap<String, Object>();
				double no = 0.1;
				List<RuleList> acendingList = new ArrayList<RuleList>();
				userTaskMap.put("id", stageId);
				userTaskMap.put("refLevelNo", wfData.getRefLevelNo());
				userTaskMap.put("name", wfData.getStageDesc());
//				userTaskMap.put(WfConstants.FORM_EDITABLE, wfData.isFrmEditable());
				userTaskMap.put(WfConstants.APPROVAL_TYPE, wfData.getApprovalType());
				userTaskMap.put(WfConstants.APPROVAL_LEVELS, wfData.getApprovalLevels());
				if (WfConstants.RULE_RESULT.equalsIgnoreCase(approvalType)) {
					if (wfData.getRuleList() != null && !wfData.getRuleList().isEmpty()) {
						for (RuleList list : wfData.getRuleList()) {
							acendingList.add(list);
						}
						acendingList.sort(Comparator.comparing(obj -> obj.getOrder(),
								Comparator.nullsLast(Comparator.naturalOrder())));
//			     acendingList.sort(Comparator.comparing(RuleList::getOrder));
						for (RuleList ruleList : acendingList) {
							double d = Double.valueOf(stageId);
							double d1 = d + no;
							String stageId1 = Double.toString(d1);
							ruleList.setRefLevelNo(wfData.getRefLevelNo());
							ruleList.setId(stageId1);
							ruleList.setApprovalLevels(wfData.getApprovalLevels());
							no += 0.1;
							
//							RuleList rule = wfData.getRuleList().get(0);
							
							List<Integer> iUsers = new ArrayList<>();
							if (ruleList.getApprovers() != null) {
								List<String> candUsers = ruleList.getApprovers();
								for (String us : candUsers) {
									iUsers.add(Integer.parseInt(us));
								}
							}
							
							if ("USERID".equalsIgnoreCase(ruleList.getApprovedType())) {
								userTaskMap1.put(WfConstants.CANDIDATE_USERS, iUsers);
								userTaskMap1.put(WfConstants.CANDIDATE_GROUPS,
										wfDao.getRoleShortIdsByUserIds(ruleList.getApprovers(), requestHeader));
								ruleList.setCandidateGroups((List) userTaskMap1.get("candidateGroups"));
								ruleList.setCandidateUsers((List) userTaskMap1.get("candidateUsers"));
								userTaskMap1.clear();
							} else {
								userTaskMap1.put(WfConstants.CANDIDATE_GROUPS, ruleList.getApprovers());
								if ("ALL".equalsIgnoreCase(wfData.getApprovalLevels()))
									userTaskMap1.put(WfConstants.CANDIDATE_USERS,
											wfDao.getUserIdsByRoleShortIds(ruleList.getApprovers(), requestHeader));
								ruleList.setCandidateGroups((List) userTaskMap1.get("candidateGroups"));
								ruleList.setCandidateUsers((List) userTaskMap1.get("candidateUsers"));
								userTaskMap1.clear();
							}
//							if ("USERID".equalsIgnoreCase(rule.getApprovedType())) {
////								userTaskMap.put(WfConstants.CANDIDATE_USERS, rule.getApprovers());
//								userTaskMap.put(WfConstants.CANDIDATE_USERS, iUsers);
//								userTaskMap.put(WfConstants.CANDIDATE_GROUPS,
//										wfDao.getRoleShortIdsByUserIds(rule.getApprovers(), requestHeader));
//							} else {
//								userTaskMap.put(WfConstants.CANDIDATE_GROUPS, rule.getApprovers());
//								if ("ALL".equalsIgnoreCase(wfData.getApprovalLevels()))
//									userTaskMap.put(WfConstants.CANDIDATE_USERS,
//											wfDao.getUserIdsByRoleShortIds(rule.getApprovers(), requestHeader));
//							}
						}
					}
//					userTaskMap.put("ruleList", acendingList);
//					List<RuleList> ruleList = wfData.getRuleList();
					ObjectMapper objectMapper = new ObjectMapper();
					String ruleListJson = objectMapper.writeValueAsString(acendingList);
					userTaskMap.put("ruleList", new JSONArray(ruleListJson));
					userTaskMap.put("ruleCheckedFlag", wfData.isRuleCheckedFlag());
				}

				else {
					if ("USERID".equalsIgnoreCase(wfData.getApprovalType())) {
						userTaskMap.put(WfConstants.CANDIDATE_USERS, wfData.getApprovers());
						userTaskMap.put(WfConstants.CANDIDATE_GROUPS,
								wfDao.getRoleShortIdsByUserIds(wfData.getApprovers(), requestHeader));
					} else {
						userTaskMap.put(WfConstants.CANDIDATE_GROUPS, wfData.getApprovers());
						if ("ALL".equalsIgnoreCase(wfData.getApprovalLevels()))
							userTaskMap.put(WfConstants.CANDIDATE_USERS,
									wfDao.getUserIdsByRoleShortIds(wfData.getApprovers(), requestHeader));
					}
				}
				userTaskMap.put("description", wfData.getStageDesc());
				userTasks.add(userTaskMap);
				Map<String, String> stageInfo = wfStagesInfo.get(stageId);
				Map<String, Object> sequenceMap = new LinkedHashMap<String, Object>();
				if (sequenceFlow.isEmpty()) {
					sequenceMap.put(WfConstants.SOURCE_REF, WfConstants.START_EVENT);
					sequenceMap.put(WfConstants.TARGET_REF, payload.getWfType());
					sequenceFlow.add(sequenceMap);
					/* initiator */
					if (payload.isInitiatorFlag()) {
						sequenceMap.put(WfConstants.TARGET_REF, payload.getWfType() + "_" + WfConstants.INITIATOR);

						sequenceMap = new LinkedHashMap<String, Object>();
						sequenceMap.put(WfConstants.SOURCE_REF, payload.getWfType() + "_" + WfConstants.INITIATOR);
						sequenceMap.put(WfConstants.TARGET_REF, WfConstants.INITIATOR);
						sequenceFlow.add(sequenceMap);

						sequenceMap = new LinkedHashMap<String, Object>();
						sequenceMap.put(WfConstants.SOURCE_REF, WfConstants.INITIATOR);
						sequenceMap.put(WfConstants.TARGET_REF, WfConstants.INITIATOR + WfConstants.EXCLUSIVEGATEWAY);
						sequenceFlow.add(sequenceMap);

						processFlowAndServiceTask(sequenceMap, WfConstants.INITIATOR, sequenceFlow, stageInfo,
								defaultServiceTasks, serviceTasks, wfData, payload);

					} else {
						sequenceMap = new LinkedHashMap<String, Object>();
						sequenceMap.put(WfConstants.SOURCE_REF, payload.getWfType());
						sequenceMap.put(WfConstants.TARGET_REF, stageId);
						sequenceFlow.add(sequenceMap);
					}
				}
				sequenceMap = new LinkedHashMap<String, Object>();
				String decisionStage = stageId + WfConstants.EXCLUSIVEGATEWAY;
				sequenceMap.put(WfConstants.SOURCE_REF, stageId);
				sequenceMap.put(WfConstants.TARGET_REF, decisionStage);
				sequenceFlow.add(sequenceMap);

				processFlowAndServiceTask(sequenceMap, stageId, sequenceFlow, stageInfo, defaultServiceTasks,
						serviceTasks, wfData, payload);

			}
			wfMap.put("isExecutable", true);
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put(WfConstants.EXCLUSIVE_GATEWAY_ID, WfConstants.START_EVENT);
			wfMap.put(WfConstants.START_EVENT, data);
			wfMap.put(WfConstants.USER_TASK, userTasks);
			wfMap.put(WfConstants.SEQUENCE_FLOW, sequenceFlow);
			wfMap.put(WfConstants.SERVICE_TASK, serviceTasks);
			data = new LinkedHashMap<String, Object>();
			data.put(WfConstants.EXCLUSIVE_GATEWAY_ID, WfConstants.EXCLUSIVEGATEWAY);
			wfMap.put(WfConstants.EXCLUSIVE_GATEWAY, data);
			wfMap.put(WfConstants.END_EVENT, getEndEvents());
			Map<String, Object> emailNotifications = new LinkedHashMap<String, Object>();
			emailNotifications.put(WfConstants.WF_MAIL_NOTIF_NEXT_LEVEL, nextMailNotifications);
			emailNotifications.put(WfConstants.WF_MAIL_NOTIF_CURNT_LEVEL, previousMailNotifications);
			wfMap.put(WfConstants.MAIL_NOTIFICATIONS, emailNotifications);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wfMap;
	}

	public Map<String, Object> processWorkFlowCreation1(WfCreateRequest payload, GenericRequestHeaders requestHeader) {
		Map<String, Object> wfMap = new LinkedHashMap<String, Object>();
		try {
			List<WfStages> wfFinalList = new ArrayList<WfStages>();
			List<Map<String, Object>> userTasks = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> sequenceFlow = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> serviceTasks = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> previousMailNotifications = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> nextMailNotifications = new LinkedList<Map<String, Object>>();
//			Map<String, Map<String, String>> wfStagesInfo = this.getWfStagesInfo(payload);
			List<WorkFlowTypesModel> wfTypes = payload.getWfTypes();
			List<WfServiceTasks> defaultServiceTasks = new ArrayList<WfServiceTasks>();
			for (WorkFlowTypesModel wfType : wfTypes) {
				defaultServiceTasks = wfDao.getDefaultWfServiceTasks(wfType.getWfTypeId());
			}

			// start RuleResult - creating each rule to new stage 02-12-2022
			for (WfStages wfData1 : payload.getWfStages()) {
				WfStages tempData = null;
				tempData = wfData1;
				WfStages newObj = null;
				double no = 0.1;
				boolean flag = false;
				String approvalType = wfData1.getApprovalType();
				String refLevelNo = wfData1.getStageId();
				if (approvalType.equalsIgnoreCase(WfConstants.RULE_RESULT)) {
					if (wfData1.getRuleList() != null && !wfData1.getRuleList().isEmpty()) {
						String stageId = wfData1.getStageId();
						List<RuleList> acendingList = new ArrayList<RuleList>();
						for (RuleList list : wfData1.getRuleList()) {

							acendingList.add(list);
						}
						acendingList.sort(Comparator.comparing(RuleList::getOrder));
//				    	 for(RuleList rule : wfData1.getRuleList())
						for (RuleList rule : acendingList) {
							List<RuleList> ruleList = new ArrayList<RuleList>();
							RuleList ruleData = new RuleList();
							ruleData.setSequenceNo(rule.getSequenceNo());
							ruleData.setDescription(rule.getDescription());
							ruleData.setExpression(rule.getExpression());
							ruleData.setApprovedType(rule.getApprovedType());
							ruleData.setActive(rule.isActive());
							ruleData.setRuleType(rule.getRuleType());
							ruleData.setDataField(rule.getDataField());
							ruleData.setValue1(rule.getValue1());
							ruleData.setValue2(rule.getValue2());
							ruleData.setOrder(rule.getOrder());
							ruleData.setRoles(rule.getRoles());
							ruleData.setUserIds(rule.getUserIds());
							ruleData.setApprovers(rule.getApprovers());
							ruleData.setFieldLocation(rule.getFieldLocation());
							ruleData.setRuleFields(rule.getRuleFields());
							ruleList.add(ruleData);

							if (!"1".equalsIgnoreCase(rule.getOrder())) {
								ObjectMapper objM = new ObjectMapper();
								newObj = objM.readValue(objM.writeValueAsString(tempData), WfStages.class);
								newObj.setRuleList(ruleList);

							} else {
								tempData.setRuleList(ruleList);

							}
							if (!"1".equalsIgnoreCase(rule.getOrder())) {
								ObjectMapper objectMapper = new ObjectMapper();
								newObj = objectMapper.readValue(objectMapper.writeValueAsString(tempData),
										WfStages.class);
								newObj.setRuleList(ruleList);
								double d = Double.valueOf(stageId);
								double d1 = d + no;
								String stageId1 = Double.toString(d1);
								newObj.setStageId(stageId1);
								no += 0.1;
								newObj.setRefLevelNo(stageId);
								wfFinalList.add(newObj);
							} else {
								wfFinalList.add(tempData);
							}

							flag = true;

						}
					}
				}
				if (!flag)
					wfFinalList.add(tempData);
				flag = false;

			}
			payload.setWfStages(wfFinalList);
			Map<String, Map<String, String>> wfStagesInfo = this.getWfStagesInfo(payload);
			// end
			for (WfStages wfData : wfFinalList) {
//				this.getMailNotifications(previousMailNotifications, nextMailNotifications, wfData, wfStagesInfo, payload.isInitiatorFlag());
				String stageId = this.getStageName(wfData.getStageId());
				String approvalType = wfData.getApprovalType();
				Map<String, Object> userTaskMap = new LinkedHashMap<String, Object>();
				userTaskMap.put("id", stageId);
				userTaskMap.put("refLevelNo", wfData.getRefLevelNo());
				userTaskMap.put("name", wfData.getStageDesc());
				userTaskMap.put("schedulingValue", wfData.getSchedulingValue());
				userTaskMap.put(WfConstants.APPROVAL_TYPE, wfData.getApprovalType());
				userTaskMap.put(WfConstants.APPROVAL_LEVELS, wfData.getApprovalLevels());
				
				List<RuleList> ruleList = wfData.getRuleList();
				ObjectMapper objectMapper = new ObjectMapper();
				String ruleListJson = objectMapper.writeValueAsString(ruleList);
				userTaskMap.put("ruleList", new JSONArray(ruleListJson));
				
//				userTaskMap.put("ruleList", wfData.getRuleList());

				// start RuleResult - setting role in userTaskMap 02-12-2022
				if (approvalType.equalsIgnoreCase(WfConstants.RULE_RESULT)) {
					if (wfData.getRuleList() != null && !wfData.getRuleList().isEmpty()) {
						RuleList rule = wfData.getRuleList().get(0);
						
						List<Integer> iUsers = new ArrayList<>();
						if (rule.getApprovers() != null) {
							List<String> candUsers = rule.getApprovers();
							for (String us : candUsers) {
								iUsers.add(Integer.parseInt(us));
							}
						}
						
//					for(RuleList rule : wfData.getRuleList()){
						if ("USERID".equalsIgnoreCase(rule.getApprovedType())) {
//							userTaskMap.put(WfConstants.CANDIDATE_USERS, rule.getApprovers());
							userTaskMap.put(WfConstants.CANDIDATE_USERS, iUsers);
							userTaskMap.put(WfConstants.CANDIDATE_GROUPS,
									wfDao.getRoleShortIdsByUserIds(rule.getApprovers(), requestHeader));
						} else {
							userTaskMap.put(WfConstants.CANDIDATE_GROUPS, rule.getApprovers());
							if ("ALL".equalsIgnoreCase(wfData.getApprovalLevels()))
								userTaskMap.put(WfConstants.CANDIDATE_USERS,
										wfDao.getUserIdsByRoleShortIds(rule.getApprovers(), requestHeader));
						}
					}
				} // end
				else {					
					if ("USERID".equalsIgnoreCase(wfData.getApprovalType())) {
						List<Integer> iUsers = new ArrayList<>();
						if (wfData.getApprovers() != null) {
							List<String> candUsers = wfData.getApprovers();
							for (String us : candUsers) {
								iUsers.add(Integer.parseInt(us));
							}
						}
//						userTaskMap.put(WfConstants.CANDIDATE_USERS, wfData.getApprovers());
						userTaskMap.put(WfConstants.CANDIDATE_USERS, iUsers);
						userTaskMap.put(WfConstants.CANDIDATE_GROUPS,
								wfDao.getRoleShortIdsByUserIds(wfData.getApprovers(), requestHeader));
					} else {
						userTaskMap.put(WfConstants.CANDIDATE_GROUPS, wfData.getApprovers());
						if ("ALL".equalsIgnoreCase(wfData.getApprovalLevels()))
							userTaskMap.put(WfConstants.CANDIDATE_USERS,
									wfDao.getUserIdsByRoleShortIds(wfData.getApprovers(), requestHeader));
					}
				}
				userTaskMap.put("description", wfData.getStageDesc());
				userTasks.add(userTaskMap);
				Map<String, String> stageInfo = wfStagesInfo.get(stageId);
				Map<String, Object> sequenceMap = new LinkedHashMap<String, Object>();
				if (sequenceFlow.isEmpty()) {
					sequenceMap.put(WfConstants.SOURCE_REF, WfConstants.START_EVENT);
					sequenceMap.put(WfConstants.TARGET_REF, payload.getWfType());
					sequenceFlow.add(sequenceMap);
					/* initiator */
					if (payload.isInitiatorFlag()) {
						sequenceMap.put(WfConstants.TARGET_REF, payload.getWfType() + "_" + WfConstants.INITIATOR);

						sequenceMap = new LinkedHashMap<String, Object>();
						sequenceMap.put(WfConstants.SOURCE_REF, payload.getWfType() + "_" + WfConstants.INITIATOR);
						sequenceMap.put(WfConstants.TARGET_REF, WfConstants.INITIATOR);
						sequenceFlow.add(sequenceMap);

						sequenceMap = new LinkedHashMap<String, Object>();
						sequenceMap.put(WfConstants.SOURCE_REF, WfConstants.INITIATOR);
						sequenceMap.put(WfConstants.TARGET_REF, WfConstants.INITIATOR + WfConstants.EXCLUSIVEGATEWAY);
						sequenceFlow.add(sequenceMap);

						processFlowAndServiceTask(sequenceMap, WfConstants.INITIATOR, sequenceFlow, stageInfo,
								defaultServiceTasks, serviceTasks, wfData, payload);

					} else {
						sequenceMap = new LinkedHashMap<String, Object>();
						sequenceMap.put(WfConstants.SOURCE_REF, payload.getWfType());
						sequenceMap.put(WfConstants.TARGET_REF, stageId);
						sequenceFlow.add(sequenceMap);
					}
				}
				sequenceMap = new LinkedHashMap<String, Object>();
				String decisionStage = stageId + WfConstants.EXCLUSIVEGATEWAY;
				sequenceMap.put(WfConstants.SOURCE_REF, stageId);
				sequenceMap.put(WfConstants.TARGET_REF, decisionStage);
				sequenceFlow.add(sequenceMap);

				processFlowAndServiceTask(sequenceMap, stageId, sequenceFlow, stageInfo, defaultServiceTasks,
						serviceTasks, wfData, payload);

			}
			wfMap.put("isExecutable", true);
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put(WfConstants.EXCLUSIVE_GATEWAY_ID, WfConstants.START_EVENT);
			wfMap.put(WfConstants.START_EVENT, data);
			wfMap.put(WfConstants.USER_TASK, userTasks);
			wfMap.put(WfConstants.SEQUENCE_FLOW, sequenceFlow);
			wfMap.put(WfConstants.SERVICE_TASK, serviceTasks);
			data = new LinkedHashMap<String, Object>();
			data.put(WfConstants.EXCLUSIVE_GATEWAY_ID, WfConstants.EXCLUSIVEGATEWAY);
			wfMap.put(WfConstants.EXCLUSIVE_GATEWAY, data);
			wfMap.put(WfConstants.END_EVENT, getEndEvents());
			Map<String, Object> emailNotifications = new LinkedHashMap<String, Object>();
			emailNotifications.put(WfConstants.WF_MAIL_NOTIF_NEXT_LEVEL, nextMailNotifications);
			emailNotifications.put(WfConstants.WF_MAIL_NOTIF_CURNT_LEVEL, previousMailNotifications);
			wfMap.put(WfConstants.MAIL_NOTIFICATIONS, emailNotifications);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wfMap;
	}

	public void processFlowAndServiceTask(Map<String, Object> sequenceMap, String stageId,
			List<Map<String, Object>> sequenceFlow, Map<String, String> stageInfo,
			List<WfServiceTasks> defaultServiceTasks, List<Map<String, Object>> serviceTasks, WfStages wfData,
			WfCreateRequest payload) {

		List<String> actions = Arrays.asList(WfConstants.APPROVED, WfConstants.REJECTED, WfConstants.REVISED);
		for (String action : actions) {
			sequenceMap = new LinkedHashMap<String, Object>();
			sequenceMap.put(WfConstants.SOURCE_REF, stageId + WfConstants.EXCLUSIVEGATEWAY);
			sequenceMap.put(WfConstants.TARGET_REF, stageId + action);
			Map<String, Object> conExpr = new LinkedHashMap<String, Object>();
			// start RuleResult - setting type as RuleResultExpression and metadata as
			// ruleList record 02-12-2022
			if (wfData.getApprovalType().equalsIgnoreCase(WfConstants.RULE_RESULT)) {
				if (wfData.getRuleList() != null && !wfData.getRuleList().isEmpty()) {
					RuleList rule = wfData.getRuleList().get(0);
					conExpr.put("type", "RuleResultExpression");
					conExpr.put(WfConstants.WF_SERVICE_TASK_TEXT, action);
					conExpr.put("metaData", rule);
					sequenceMap.put(WfConstants.CONDITION_EXPRESSION, conExpr);
					sequenceFlow.add(sequenceMap);
				}
			} // end
			else {
				conExpr.put("type", "tFormalExpression");
				conExpr.put(WfConstants.WF_SERVICE_TASK_TEXT, action);
				sequenceMap.put(WfConstants.CONDITION_EXPRESSION, conExpr);
				sequenceFlow.add(sequenceMap);
			}

			sequenceMap = new LinkedHashMap<String, Object>();
			sequenceMap.put(WfConstants.SOURCE_REF, stageId + action);

			if (stageId.equalsIgnoreCase("1")) {
				if (WfConstants.APPROVED.equalsIgnoreCase(action)) {
					sequenceMap.put(WfConstants.TARGET_REF, "2");

					List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
//					for (WfServiceTasks defaultTask : defaultServiceTasks) {
//						if (WfConstants.DEFAULT_EXTENSION_SERVICE_TASK_TYPE
//								.equalsIgnoreCase(defaultTask.getServiceType()) && defaultTask.getAllowedActions() != null
//								&&  defaultTask.getAllowedActions().contains(WfConstants.INITIATE)) {
//							tasks.add(this.processServiceTaskData(defaultTask, 1));
//						}
//					}
					Map<String, Object> serviceTaskMap = new LinkedHashMap<>();
					serviceTaskMap.put(WfConstants.WF_SERVICE_TASK_ID,
							payload.getWfType() + "_" + WfConstants.INITIATOR);
					serviceTaskMap.put("name", WfConstants.INITIATOR);
					serviceTaskMap.put(WfConstants.SERVICE_TASKS_LIST, tasks);
					serviceTasks.add(serviceTaskMap);

				} else if (WfConstants.REJECTED.equalsIgnoreCase(action)) {
					sequenceMap.put(WfConstants.TARGET_REF, "rejectEnd");
				} else if (WfConstants.REVISED.equalsIgnoreCase(action)) {
					sequenceMap.put(WfConstants.TARGET_REF, "reviseEnd");
				}
			} else {
				if (WfConstants.APPROVED.equalsIgnoreCase(action))
					sequenceMap.put(WfConstants.TARGET_REF,
							(stageInfo.get("nextStage") != null && !stageInfo.get("nextStage").isEmpty())
									? stageInfo.get("nextStage")
									: "approveEnd");
				else if (WfConstants.REJECTED.equalsIgnoreCase(action))
					sequenceMap.put(WfConstants.TARGET_REF, "rejectEnd");
				else if (WfConstants.REVISED.equalsIgnoreCase(action))
					sequenceMap.put(WfConstants.TARGET_REF,
							(stageInfo.get("reviseStage") != null && !stageInfo.get("reviseStage").isEmpty())
									? stageInfo.get("reviseStage")
									: "reviseEnd");
			}
			sequenceFlow.add(sequenceMap);

			/* Service Tasks adding in work flow */
			int serialNumber = 1;
			List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
			for (WfServiceTasks defaultTask : defaultServiceTasks) {
				if (WfConstants.DEFAULT_SERVICE_TASK_TYPE.equalsIgnoreCase(defaultTask.getServiceType())) {
					tasks.add(this.processServiceTaskData(defaultTask, serialNumber));
					serialNumber++;
				}
//				else if (WfConstants.DEFAULT_EXTENSION_SERVICE_TASK_TYPE
//						.equalsIgnoreCase(defaultTask.getServiceType()) && defaultTask.getAllowedActions() != null
//						&&  defaultTask.getAllowedActions().contains(action)) {
//					tasks.add(this.processServiceTaskData(defaultTask, serialNumber));
//					serialNumber++;
//				}
			}
			if (WfConstants.APPROVED.equalsIgnoreCase(action)) {
				for (String task : wfData.getServiceTaskIds()) {
					WfServiceTasks serviceTask = wfDao.getWfServiceTaskById(task);
					if (serviceTask != null) {
						tasks.add(this.processServiceTaskData(serviceTask, serialNumber));
						serialNumber++;
					}
				}
			}
			if (tasks != null && !tasks.isEmpty()) {
				Map<String, Object> serviceTaskMap = new LinkedHashMap<String, Object>();
				serviceTaskMap.put(WfConstants.WF_SERVICE_TASK_ID, stageId + action);
				serviceTaskMap.put("name", wfData.getStageDesc());
				serviceTaskMap.put(WfConstants.SERVICE_TASKS_LIST, tasks);
				serviceTasks.add(serviceTaskMap);
			}
		}

	}

	public Map<String, Map<String, String>> getWfStagesInfo(WfCreateRequest payload) {
		Map<String, Map<String, String>> stagesMap = new HashMap<String, Map<String, String>>();
		try {
			String lastStage = "";
			for (WfStages wfStage : payload.getWfStages()) {
				String stageId = this.getStageName(wfStage.getStageId());
				Map<String, String> stageInfoMap = new LinkedHashMap<String, String>();
				stageInfoMap.put("currentStage", stageId);
				stageInfoMap.put("previousStage", lastStage);
				if (wfStage.getOnRejectStageId() != null && wfStage.getOnRejectStageDesc() != null)
					stageInfoMap.put("reviseStage", this.getStageName(wfStage.getOnRejectStageDesc()));
				stagesMap.put(stageId, stageInfoMap);
				if (stagesMap.containsKey(lastStage)) {
					Map<String, String> dataMap = stagesMap.get(lastStage);
					dataMap.put("nextStage", stageId);
				}
				lastStage = stageId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stagesMap;
	}
	
	public List<Map<String, Object>> getEndEvents() {
		List<Map<String, Object>> endEventsList  =  new LinkedList<Map<String, Object>>();
		try {
			Arrays.asList("approveEnd","rejectEnd","reviseEnd").forEach(event->{
				Map<String, Object> endEvent = new LinkedHashMap<String, Object>();
				endEvent.put("id", event);
				endEventsList.add(endEvent);
			});			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return endEventsList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> processWorkFlowSaveData(GenericRequestHeaders requestHeader, Map<String, Object> wfMap,
			WfCreateRequest payload) {
		Map<String, Object> wfDataMap = new LinkedHashMap<String, Object>();
		try {
			List<WorkFlowTypesModel> wfTypes = payload.getWfTypes();
			List<String> wfIds = new ArrayList<String>();
			for (WorkFlowTypesModel wfType : wfTypes) {
				wfIds.add(wfType.getWfTypeId());
			}
			List<GdWfTypes> wfTypesByIds = wfDao.getWfTypesByIds(requestHeader, wfIds);
			if (wfTypesByIds != null && !wfTypesByIds.isEmpty()) {
				if (payload.getWfId() != null)
					wfDataMap = wfDao.getWfMasterByWfId(payload.getWfId());

				if (wfDataMap == null || wfDataMap.isEmpty()) {
					wfDataMap = new LinkedHashMap<String, Object>();
					wfDataMap.put("createdBy", requestHeader.getLoggedUser().getUserId());
					wfDataMap.put("createdTs", new Date());
				} else {
					wfDataMap.put("changedBy", requestHeader.getLoggedUser().getUserId());
					wfDataMap.put("changedTs", new Date());
				}
				wfDataMap.put("wfId", payload.getWfId() != null ? payload.getWfId() : UUID.randomUUID().toString());
				wfDataMap.put("orgId", requestHeader.getOrgId());
				wfDataMap.put("wfTitle", payload.getWfTitle());
//					wfDataMap.put("wfName", payload.get());
				wfDataMap.put("wfTypeId", payload.getWfTypeId() != null ? payload.getWfTypeId() : null);
//					wfDataMap.put("wfType", gdWfTypes.getWfType());
				wfDataMap.put(WfConstants.WF_INITIATOR_FLAG, payload.isInitiatorFlag());
				wfDataMap.put("process", wfMap);
				Random r = new Random(System.currentTimeMillis());
				wfDataMap.put("wfShortId", "V1" + (10000 + r.nextInt(20000)));
					wfDataMap.put(WfConstants.WF_OBJECT_REF, this.processWfObjectRefData(wfTypesByIds.get(0)));
				wfDataMap.put("markAsDelete", false);
				wfDataMap.put("status", payload.getStatus());
				WfMasterDocTypes docTypes = new WfMasterDocTypes();
				for (GdWfTypes gdWfTypes : wfTypesByIds) {
					docTypes = new WfMasterDocTypes();
					
					docTypes.setWfMasterDocId(UUID.randomUUID().toString());
					docTypes.setWfId(wfDataMap.get("wfId").toString());
					docTypes.setWfTitle(payload.getWfTitle());
					docTypes.setWfTypeId(gdWfTypes.getWfTypeId());
					docTypes.setWfType(gdWfTypes.getWfType());
					docTypes.setWfName(gdWfTypes.getWfType());
					docTypes.setIsDefault(false);
					docTypes.setMarkAsDelete(false);
					docTypes.setCreatedBy(requestHeader.getLoggedUser().getUserId());
					docTypes.setCreatedTs(new Date());
					List<WfMasterDocTypes> wfDocTypes = (List<WfMasterDocTypes>)docTypesRepo.findByWfIdAndWfTypeId(wfDataMap.get("wfId").toString(),gdWfTypes.getWfTypeId());
					if(wfDocTypes.isEmpty())
						docTypesRepo.save(docTypes);
				}

			}
//			GdWfTypes wfType = wfDao.getWfTypeById(requestHeader, payload.getWfTypeId());
//			if (wfType != null) {
//				if(payload.getWfId() != null)
//					wfDataMap = wfDao.getWfMasterByWfId(payload.getWfId());
//				if(wfDataMap == null || wfDataMap.isEmpty()){
//					wfDataMap = new LinkedHashMap<String, Object>();
//					wfDataMap.put("createdBy", requestHeader.getLoggedUser().getUserId());
//					wfDataMap.put("createdTs", new Date());
////					Map<String, Object> changeHistory = new HashMap<String, Object>();
////					changeHistory.put("createdBy", requestHeader.getLoggedUser().getUserId());
////					changeHistory.put("createdTs", new Date());
////					wfDataMap.put("changeHistory", changeHistory);
//				} else {
//					wfDataMap.put("changedBy", requestHeader.getLoggedUser().getUserId());
//					wfDataMap.put("changedTs", new Date());
////					Map<String, Object> changeHistory = (Map<String, Object>)wfDataMap.get("changeHistory");
////					changeHistory.put("changedBy", requestHeader.getLoggedUser().getUserId());
////					changeHistory.put("changedTs", new Date());
////					wfDataMap.put("changeHistory", changeHistory);
//				}
//				wfDataMap.put("wfId", payload.getWfId() != null ? payload.getWfId() : UUID.randomUUID().toString());
//				wfDataMap.put("orgId", requestHeader.getOrgId());
//				wfDataMap.put("wfTitle", payload.getWfTitle());
//				wfDataMap.put("wfName", wfType.getWfTypeDesc());
//				wfDataMap.put("wfTypeId", payload.getWfTypeId());
//				wfDataMap.put("wfType", wfType.getWfType());
//				wfDataMap.put(WfConstants.WF_INITIATOR_FLAG, payload.isInitiatorFlag());
//				wfDataMap.put("process", wfMap);
//				Random r = new Random(System.currentTimeMillis());
//				wfDataMap.put("wfShortId", wfType.getWfType() + "_V1"+ (10000 + r.nextInt(20000)));
//				wfDataMap.put(WfConstants.WF_OBJECT_REF, this.processWfObjectRefData(wfType));
//				wfDataMap.put("markAsDelete", false);
//				wfDataMap.put("status", payload.getStatus());
//				//Need To Handle The Versions
//				
//				
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wfDataMap;
	}

	public String getStageName(String stageInfo) {
		String stageName = stageInfo.replace(" ", "_").toLowerCase().trim();
		// Random r = new Random(System.currentTimeMillis());
		// stageName = stageName + (10000 + r.nextInt(20000));
		return stageName;
	}

//
//	@Override
//	public GenericApiResponse getWfTypes(GenericRequestHeaders requestHeader) {
//		GenericApiResponse apiResponse = new GenericApiResponse();
//		try {
//			List<GdWfTypes> wfTypes = wfDao.getWfTypes(requestHeader);
//			if (wfTypes != null) {
//				apiResponse.setData(wfTypes);
//				apiResponse.setMessage(environment.getProperty("GEN_001"));
//				apiResponse.setErrorcode("GEN_001");
//				apiResponse.setStatus(0);
//			} else {
//				apiResponse.setMessage(environment.getProperty("GEN_002"));
//				apiResponse.setErrorcode("GEN_002");
//			}
//		} catch (Exception e) {
//			apiResponse.setMessage(environment.getProperty("GEN_002"));
//			apiResponse.setErrorcode("GEN_002");
//			logger.error("Error while getting organization details: " + ExceptionUtils.getStackTrace(e));
//		}
//		return apiResponse;
//	}
//
	@Override
	public GenericApiResponse getWfServiceTasks(GenericRequestHeaders requestHeader, String wfTypeId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			List<WfServiceTasks> serviceTasks = wfDao.getWfServiceTasks(requestHeader, wfTypeId);
			if (serviceTasks != null) {
				apiResponse.setData(serviceTasks);
				apiResponse.setMessage(environment.getProperty("GEN_001"));
				apiResponse.setErrorcode("GEN_001");
				apiResponse.setStatus(0);
			} else {
				apiResponse.setMessage(environment.getProperty("GEN_002"));
				apiResponse.setErrorcode("GEN_002");
			}
		} catch (Exception e) {
			apiResponse.setMessage(environment.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
			logger.error("Error while getting organization details: " + ExceptionUtils.getStackTrace(e));
		}
		return apiResponse;
	}

	public Map<String, Object> processWfObjectRefData(GdWfTypes wfType) {
		Map<String, Object> objectRefMap = new HashMap<String, Object>();
		try {
//			if(GeneralConstants.KYP.equalsIgnoreCase(wfType.getWfType()))
//				wfType.setTargetCollection(MongoEntityConstants.OMORGKYPDETAILS);
			if (wfType != null) {
				objectRefMap.put(WfConstants.WF_OBJECT_REF_COLLECTION, wfType.getTargetCollection());
				objectRefMap.put(WfConstants.WF_OBJECT_REF_KEY_FIELD, wfType.getKeyFieldName());
				objectRefMap.put(WfConstants.WF_OBJECT_REF_KEY_STATUS_FIELD, wfType.getStatusField());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectRefMap;
	}

//	
	@SuppressWarnings("rawtypes")
	@Override
	public GenericApiResponse getWfDataByWfId(GenericRequestHeaders requestHeader, String wfId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			Map map = wfDao.getWfDataByWfId(requestHeader, wfId);
			if (map != null) {
				apiResponse.setData(map);
				apiResponse.setStatus(0);
			} else {
				apiResponse.setMessage(environment.getProperty("GD_003"));
				apiResponse.setErrorcode("GD_003");
			}
		} catch (Exception e) {
			apiResponse.setMessage(environment.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
			logger.error("Error while getting workflow title list: " + ExceptionUtils.getStackTrace(e));
		}
		return apiResponse;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GenericApiResponse getWfTitlesDropdown(GenericRequestHeaders requestHeader, List<String> wfTypeId,
			String wfTypeName, String title, boolean masterWf) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			List<Map> list = wfDao.getWfTitleSearchList(requestHeader, wfTypeId, wfTypeName, title, masterWf);
			if (list != null) {
				apiResponse.setData(list);
				apiResponse.setStatus(0);
			} else {
				apiResponse.setMessage(environment.getProperty("GD_003"));
				apiResponse.setErrorcode("GD_003");
			}
		} catch (Exception e) {
			apiResponse.setMessage(environment.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
			logger.error("Error while getting workflow title list: " + ExceptionUtils.getStackTrace(e));
		}
		return apiResponse;
	}

	public boolean processWorkflowRefrenceData(GenericRequestHeaders requestHeader, WfCreateRequest payload,
			String wfId) {
		boolean flag = false;
		try {
			Map<String, Object> wfRefData = new HashMap<String, Object>();
			wfRefData.put("wfRefId", payload.getWfId() != null ? payload.getWfId() : UUID.randomUUID().toString());
			wfRefData.put("orgId", requestHeader.getOrgId());
			wfRefData.put("wfId", wfId);
			wfRefData.put("wfTitle", payload.getWfTitle());
			wfRefData.put("status", payload.getStatus());
			wfRefData.put("wfRefData", payload.getWfStages());
			wfRefData.put("wfName", payload.getWfType());
			wfRefData.put("wfTypeId", payload.getWfTypeId());
			wfRefData.put("markAsDelete", false);
			wfRefData.put(WfConstants.WF_INITIATOR_FLAG, payload.isInitiatorFlag());
			flag = wfDao.createWfRefData(wfRefData, wfId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
//	
//	@SuppressWarnings("rawtypes")
//	@Override
//	public GenericApiResponse getEmailTemplates(WfCommonModel wfModel) {
//		GenericApiResponse apiResponse = new GenericApiResponse();
//		try {
//			List<Map> list = wfDao.getEmailTemplateDet(wfModel);
//			if (list != null) {
//				apiResponse.setData(list);
//				apiResponse.setStatus(0);
//			} else {
//				apiResponse.setMessage(environment.getProperty("GD_003"));
//				apiResponse.setErrorcode("GD_003");
//			}
//		} catch (Exception e) {
//			apiResponse.setMessage(environment.getProperty("GEN_002"));
//			apiResponse.setErrorcode("GEN_002");
//			logger.error("Error while getting email template list: " + ExceptionUtils.getStackTrace(e));
//		}
//		return apiResponse;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public void getMailNotifications(List<Map<String, Object>> previousMailNotificationsList,
//			List<Map<String, Object>> nextMailNotificationsList, WfStages wfData, 
//			Map<String, Map<String, String>> wfStagesInfo, boolean initiator) {
//		String stageId = this.getStageName(wfData.getStageId());
//		String nextStageId = wfStagesInfo.get(stageId).get("nextStage");
//		//String prevStageId = wfStagesInfo.get(stageId).get("previousStage");
//		String currentStageId = wfStagesInfo.get(stageId).get("currentStage");
// 		if (wfData.getNextEmailTemplateID() != null && !wfData.getNextEmailTemplateID().isEmpty()
//				&& nextStageId != null&& !nextStageId.isEmpty()) {
//			if (nextMailNotificationsList.isEmpty()) {
//				Map<String, Object> nextNotification = new LinkedHashMap<String, Object>();
//				nextNotification.put("mailTemplate", wfData.getNextEmailTemplateID());
//				List<String> nextStagesList = new ArrayList<String>();
//				nextStagesList.add(nextStageId);
//				nextNotification.put("stages", nextStagesList);
//				nextMailNotificationsList.add(nextNotification);
//			} else {
//				Map<String, Object> nextMap = new LinkedHashMap<String, Object>();
//				nextMailNotificationsList.stream().forEach(map -> {
//					nextMap.put((String) map.get("mailTemplate"), map.get("stages"));
//				});
//				if (nextMap.containsKey(wfData.getNextEmailTemplateID())) {
//					List<String> nextStagesList = new ArrayList<String>();
//					nextStagesList = (List<String>) nextMap.get(wfData.getNextEmailTemplateID());
//					nextStagesList.add(nextStageId);
//				} else {
//					Map<String, Object> nextNotification = new LinkedHashMap<String, Object>();
//					nextNotification.put("mailTemplate", wfData.getNextEmailTemplateID());
//					List<String> nextStagesList = new ArrayList<String>();
//					nextStagesList.add(nextStageId);
//					nextNotification.put("stages", nextStagesList);
//					nextMailNotificationsList.add(nextNotification);
//				}
//			}
//		}
//		if (wfData.getPreviousEmailTemplateID() != null && !wfData.getPreviousEmailTemplateID().isEmpty()
//				&& currentStageId != null && !currentStageId.isEmpty()) {
//			if (previousMailNotificationsList.isEmpty()) {
//				Map<String, Object> previousNotification = new LinkedHashMap<String, Object>();
//				previousNotification.put("mailTemplate", wfData.getPreviousEmailTemplateID());
//				List<String> previousStagesList = new ArrayList<String>();
//				previousStagesList.add(currentStageId);
//				if(initiator){
//					previousStagesList.add(WfConstants.INITIATOR);	
//				}
//				previousNotification.put("stages", previousStagesList);
//				previousMailNotificationsList.add(previousNotification);
//			} else {
//				Map<String, Object> previousMap = new LinkedHashMap<String, Object>();
//				previousMailNotificationsList.stream().forEach(map -> {
//					previousMap.put((String) map.get("mailTemplate"), map.get("stages"));
//				});
//				if (previousMap.containsKey(wfData.getPreviousEmailTemplateID())) {
//					List<String> StagesList = new ArrayList<String>();
//					StagesList = (List<String>) previousMap.get(wfData.getPreviousEmailTemplateID());
//					StagesList.add(currentStageId);
//				} else {
//					Map<String, Object> previousNotification = new LinkedHashMap<String, Object>();
//					previousNotification.put("mailTemplate", wfData.getPreviousEmailTemplateID());
//					List<String> previousStagesList = new ArrayList<String>();
//					previousStagesList.add(currentStageId);
//					previousNotification.put("stages", previousStagesList);
//					previousMailNotificationsList.add(previousNotification);
//				}
//			}
//		}
//	}

	public Map<String, Object> processServiceTaskData(WfServiceTasks serviceTask, int serialNumber) {

		Map<String, Object> taskMap = new LinkedHashMap<String, Object>();
		taskMap.put(WfConstants.SERVICE_TASKS_SERIAL_NUMBER, serialNumber);
		taskMap.put(WfConstants.WF_SERVICE_TASK_CLASS, serviceTask.getServiceClass());
		taskMap.put(WfConstants.WF_SERVICE_TASK_METHOD, serviceTask.getMethod());
		return taskMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getWfListView(GenericRequestHeaders requestHeaders, @NotNull WfListViewModel payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		Map<String, WfListViewModel> wfListViewModelMap = new HashMap<>();
		List<Integer> userIdsList = new ArrayList<>();
		List<String> docNames = new ArrayList<String>();
		List<WfListViewModel> wfListViewModelList = new ArrayList<>();
		try {

			String query = "select wm.wf_id,wm.wf_title,wm.wf_name,wm.wf_type_id,wm.wf_type,wm.wf_short_id,wm.status,wm.created_by,wm.created_ts,wm.process_meta_data,wm.isDefault,COUNT(*) OVER () AS total_count from wf_master wm where wm.mark_as_delete = false";

			List<Object[]> obj = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			List<String> wfIds = new ArrayList<String>();
			List<Integer> documentTypes = new ArrayList<Integer>();
			if (obj != null && !obj.isEmpty()) {
				for (Object[] data : obj) {
					WfListViewModel wfListViewModel = new WfListViewModel();

					wfListViewModel.setWfId((String) data[0]);
					wfListViewModel.setWfTitle((String) data[1]);
					wfListViewModel.setWfName((String) data[2]);
					wfListViewModel.setWfTypeId((String) data[3]);
					wfListViewModel.setWfType((String) data[4]);
					wfListViewModel.setWfShortId((String) data[5]);
					wfListViewModel.setStatus((String) data[6]);
					wfListViewModel.setCreatedBy((Integer) data[7]);
					wfListViewModel.setCreatedTs(CommonUtils.dateFormatter((Date) data[8], "yyyy-MM-dd"));
					wfListViewModel.setProcessMetaData((String) data[9]);
//					wfListViewModel.setIsDefault((Boolean) data[10]);
					JSONObject decodedRes = new JSONObject(
							new String(Base64Utils.decode(((String) data[9]).getBytes())));
					JSONArray approverLevels = (JSONArray) decodedRes.get("userTask");
					if (approverLevels.length() == 1) {
						wfListViewModel.setApproverLevels(GeneralConstants.SINGLE_LEVEL);
					} else {
						wfListViewModel.setApproverLevels(GeneralConstants.MULTI_LEVEL);
					}
					wfListViewModel.setTotalCnt((BigInteger) data[11]);

					wfListViewModelList.add(wfListViewModel);
					userIdsList.add((Integer) data[7]);
					wfIds.add((String) data[0]);
					wfListViewModelMap.put((String) data[0], wfListViewModel);
				}

				if (userIdsList != null && !userIdsList.isEmpty()) {
					List<UmUsers> umUsersList = umUsersRepository.getUmUsersList(userIdsList);
					if (!umUsersList.isEmpty()) {
						for (WfListViewModel wfModel : wfListViewModelList) {
							UmUsers user = umUsersList.stream()
									.filter(f -> (f.getUserId()).equals(wfModel.getCreatedBy())).findFirst()
									.orElse(null);
							if (user != null) {
								wfModel.setCreatedUserName(user.getFirstName() + " " + user.getLastName());
							}
						}
					}
				}

				if (wfIds != null && !wfIds.isEmpty()) {
					List<Object[]> docTypes = docTypesRepo.getWfTypedByTypeId(wfIds);
					for (Object[] obj1 : docTypes) {
						if (wfListViewModelMap.containsKey((String) obj1[0])) {
							if (wfListViewModelMap.get((String) obj1[0]).getWfTypes() != null) {
								wfListViewModelMap.get((String) obj1[0]).getWfTypes().add((String) obj1[1]);
							} else {
								List<String> wfTypes = new ArrayList<String>();
								wfTypes.add((String) obj1[1]);
								WfListViewModel listViewModel = wfListViewModelMap.get((String) obj1[0]);
								listViewModel.setIsDefault((Boolean) obj1[2]);
								listViewModel.setWfTypes(wfTypes);
								listViewModel.setWfType((String) obj1[1]);
								wfListViewModelMap.put((String) obj1[0], listViewModel);
							}
						}
					}

				}
				
//				if (wfIds != null && !wfIds.isEmpty()) {
//					List<Object[]> docTypes = wfDocTypesRepo.getWfTypedByTypeId(wfIds);
//					for (Object[] obj1 : docTypes) {
//						if (wfListViewModelMap.containsKey((String) obj1[0])) {
//							if (wfListViewModelMap.get((String) obj1[0]).getWfTypes() != null) {
//								wfListViewModelMap.get((String) obj1[0]).getWfTypes().add((String) obj1[1]);
//							} else {
//								List<Integer> wfTypes = new ArrayList<Integer>();
//								
//								wfTypes.add((Integer) obj1[1]);
//								WfListViewModel listViewModel = wfListViewModelMap.get((String) obj1[0]);
//								listViewModel.setIsDefault((Boolean) obj1[2]);
//								listViewModel.setDocumentTypes(wfTypes);
////								documentTypes.add((Integer) obj1[1]);
//								listViewModel.setDocumentType((Integer) obj1[1]);
////								listViewModel.setDocumentTypes(documentTypes); ;
//								//listViewModel.setWfType((String) obj1[1]);
//								wfListViewModelMap.put((String) obj1[0], listViewModel);
//							}
//						}
//					}
//
//				}
				
				if (wfIds != null && !wfIds.isEmpty()) {
					List<Object[]> docTypes = locationsRepo.getWfTypedByTypeId(wfIds);
					for (Object[] obj1 : docTypes) {
						if (wfListViewModelMap.containsKey((String) obj1[0])) {
//							if (wfListViewModelMap.get((String) obj1[0]).getWfTypes() != null) {
//								wfListViewModelMap.get((String) obj1[0]).getWfTypes().add((String) obj1[1]);
//							} else {
								List<Integer> wfTypes = new ArrayList<Integer>();
								
								wfTypes.add((Integer) obj1[1]);
								WfListViewModel listViewModel = wfListViewModelMap.get((String) obj1[0]);								
								listViewModel.setLocation(wfTypes);
								wfListViewModelMap.put((String) obj1[0], listViewModel);
//							}
						}
					}

				}
				for(Entry<String, WfListViewModel> map:wfListViewModelMap.entrySet()) {
					WfListViewModel value = map.getValue();
		
					List<WfDocTypes> wfMasterDocByWfId = wfDocTypesRepo.getWfMasterDocByWfId(value.getWfId());
					List<WfLocations> locationsByWfId = locationsRepo.getLocationsByWfId(value.getWfId());
					if(wfMasterDocByWfId != null && !wfMasterDocByWfId.isEmpty()) {
						for(WfDocTypes docTypes : wfMasterDocByWfId) {
							documentTypes.add(docTypes.getDocumentType());
						}
//						if(documentTypes != null && !documentTypes.isEmpty()) {
//							List<GdDocumentTypes> documentTypesOnIds = documentTypeRepo.getDocumentTypesOnIds(documentTypes);
//							for(GdDocumentTypes document : documentTypesOnIds) {
//								docNames.add(document.getDocumentType());
//							}
//							
//							value.setDocumentNames(docNames);
//							value.setDocumentTypes(documentTypes);
//						}	
//						documentTypes = new ArrayList<Integer>();
//						docNames = new ArrayList<String>();
					}
					if(locationsByWfId != null && !locationsByWfId.isEmpty()) {
						List<Integer> locationIds = new ArrayList<Integer>();
						for(WfLocations wfLocations : locationsByWfId) {
							
							locationIds.add(wfLocations.getLocation());
						}
						if(locationIds != null && !locationIds.isEmpty()) {
							List<String> locNames = new ArrayList<String>();
							List<BookingUnits> locationsByIds = bookingUnitsRepo.getBookingUnitsByBookingCode(locationIds);
							if(locationsByIds != null && !locationsByIds.isEmpty()) {
								for(BookingUnits locations : locationsByIds) {
									locNames.add(locations.getBookingLocation());
								}
								value.setLocNames(locNames);
								value.setLocation(locationIds);
							}
						}
					}
					WfMasterDocTypes wfTypeByWfId = docTypesRepo.getWfTypeByWfId(value.getWfId());
					if(wfTypeByWfId != null) {
						value.setWfType(wfTypeByWfId.getWfType());
					}
								
					wfListViewModelMap.put(value.getWfId(), value);
				}

				genericApiResponse.setData(wfListViewModelList);
				genericApiResponse.setStatus(0);
			} else {
				genericApiResponse.setMessage("");
				genericApiResponse.setStatus(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse setAsDefaultWf(GenericRequestHeaders requestHeaders, @NotNull Map payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			String action = (String) payload.get("action");
			if (payload != null && action.equalsIgnoreCase("SET_AS_DEFAULT")) {
				List<String> wfTypes = (List<String>) payload.get("wfType");
				List<Integer> docs = (List<Integer>)payload.get("docType");
				List<Integer> location = (List<Integer>)payload.get("locIds");
				List<Object[]> wfLocations = docTypesRepo.getWflocationsOnLocation(location);
				if(wfLocations != null && !wfLocations.isEmpty()) {
					for(Object[] obj: wfLocations) {
						String wfId =(String) obj[0];
						List<WfMasterDocTypes> wfMasterDocByWfId = docTypesRepo.getWfMasterDocByWfId(wfId);
						for(WfMasterDocTypes docTypes :wfMasterDocByWfId ) {
							docTypes.setIsDefault(false);
							baseDao.saveOrUpdate(docTypes);
						}
					}
				}
//				if(wfDocTypesandLocations != null && !wfDocTypesandLocations.isEmpty()) {
//					for(Object[] obj :wfDocTypesandLocations) {
//						String wfId =(String) obj[0];
//						List<WfDocTypes> wfMasterDocByWfId = wfDocTypesRepo.getWfMasterDocByWfId(wfId);
//						for(WfDocTypes docTypes :wfMasterDocByWfId) {
//							docTypes.setIsDefault(false);
//							baseDao.saveOrUpdate(docTypes);
//						}
//						
//					}
//				}
//				List<WfMasterDocTypes> wfMasterDoctypes = docTypesRepo.getDefaultWfDataList(wfTypes);
////				List<WfDocTypes> defaultWfDocumentTypeList = wfDocTypesRepo.getWfMasterDocByWfId(wfTypes);
//				if(wfMasterDoctypes != null && !wfMasterDoctypes.isEmpty()) {
//					for(WfMasterDocTypes wfDoc : wfMasterDoctypes) {
//						wfDoc.setIsDefault(false);
//						wfDoc.setChangedBy(requestHeaders.getLoggedUser().getUserId());
//						wfDoc.setChangedTs(new Date());
//						
//						baseDao.saveOrUpdate(wfDoc);
//					}
//				}
//				if(defaultWfDocumentTypeList != null && !defaultWfDocumentTypeList.isEmpty()) {
//					for(WfDocTypes docTypes2:defaultWfDocumentTypeList) {
//						docTypes2.setIsDefault(false);
//						docTypes2.setChangedBy(requestHeaders.getLoggedUser().getUserId());
//						docTypes2.setChangedTs(new Date());
//						baseDao.saveOrUpdate(docTypes2);
//						
//					}
//				}
				
				List<WfMasterDocTypes> wfMaster = new ArrayList<>();
				wfMaster = docTypesRepo.getWfMasterDocByWfId(payload.get("wfId") + "");
				if (wfMaster != null && !wfMaster.isEmpty()) {
					for(WfMasterDocTypes docTypes : wfMaster) {
						docTypes.setIsDefault(true);
						docTypes.setChangedBy(requestHeaders.getLoggedUser().getUserId());
						docTypes.setChangedTs(new Date());
						
						baseDao.saveOrUpdate(docTypes);
					}
				}
//				List<WfDocTypes> list = new ArrayList<WfDocTypes>();
//				list = wfDocTypesRepo.getWfMasterDocByWfId(payload.get("wfId") + "");
//				if(list != null && !list.isEmpty()) {
//					for(WfDocTypes wfDocTypes : list) {
//						wfDocTypes.setIsDefault(true);
//						wfDocTypes.setChangedBy(requestHeaders.getLoggedUser().getUserId());
//						wfDocTypes.setChangedTs(new Date());
//						
//						baseDao.saveOrUpdate(wfDocTypes);
//					}
//				}
				
//				WfMaster wfMaster = wfMasterRepo.getDefaultWfDetails(payload.get("wfType") + "");
//				if (wfMaster != null) {
//					wfMaster.setIsDefault(false);
//					wfMaster.setChangedBy(requestHeaders.getLoggedUser().getUserId());
//					wfMaster.setChangedTs(new Date());
//
//					baseDao.saveOrUpdate(wfMaster);
//				}
//				WfMaster wfMaster = new WfMaster();
//				wfMaster = wfMasterRepo.getWfMasterDataByWfId(payload.get("wfId") + "");
//				if (wfMaster != null) {
//					wfMaster.setIsDefault(true);
//					wfMaster.setChangedBy(requestHeaders.getLoggedUser().getUserId());
//					wfMaster.setChangedTs(new Date());
//
//					baseDao.saveOrUpdate(wfMaster);
//				}

				apiResponse.setStatus(0);
				apiResponse.setMessage("updated successfully.");
			}else if(payload != null && action.equalsIgnoreCase("REMOVE_DEFAULT")) {

				List<String> wfTypes = (List<String>) payload.get("wfType");
				List<Integer> docs = (List<Integer>)payload.get("docType");
				List<WfMasterDocTypes> wfMasterDoctypes = docTypesRepo.getDefaultWfDataList(wfTypes);
				List<WfDocTypes> defaultWfDocumentTypeList = wfDocTypesRepo.getWfMasterDocByWfId(wfTypes);
				if(wfMasterDoctypes != null && !wfMasterDoctypes.isEmpty()) {
					for(WfMasterDocTypes wfDoc : wfMasterDoctypes) {
						wfDoc.setIsDefault(false);
						wfDoc.setChangedBy(requestHeaders.getLoggedUser().getUserId());
						wfDoc.setChangedTs(new Date());
						
						baseDao.saveOrUpdate(wfDoc);
					}
				}
				if(defaultWfDocumentTypeList != null && !defaultWfDocumentTypeList.isEmpty()) {
					for(WfDocTypes docTypes2:defaultWfDocumentTypeList) {
						docTypes2.setIsDefault(false);
						docTypes2.setChangedBy(requestHeaders.getLoggedUser().getUserId());
						docTypes2.setChangedTs(new Date());
						baseDao.saveOrUpdate(docTypes2);
						
					}
				}
				
				List<WfMasterDocTypes> wfMaster = new ArrayList<>();
				wfMaster = docTypesRepo.getWfMasterDocByWfId(payload.get("wfId") + "");
				if (wfMaster != null && !wfMaster.isEmpty()) {
					for(WfMasterDocTypes docTypes : wfMaster) {
						docTypes.setIsDefault(false);
						docTypes.setChangedBy(requestHeaders.getLoggedUser().getUserId());
						docTypes.setChangedTs(new Date());
						
						baseDao.saveOrUpdate(docTypes);
					}
				}
				List<WfDocTypes> list = new ArrayList<WfDocTypes>();
				list = wfDocTypesRepo.getWfMasterDocByWfId(payload.get("wfId") + "");
				if(list != null && !list.isEmpty()) {
					for(WfDocTypes wfDocTypes : list) {
						wfDocTypes.setIsDefault(false);
						wfDocTypes.setChangedBy(requestHeaders.getLoggedUser().getUserId());
						wfDocTypes.setChangedTs(new Date());
						
						baseDao.saveOrUpdate(wfDocTypes);
					}
				}
//				WfMaster wfMaster = wfMasterRepo.getDefaultWfDetails(payload.get("wfType") + "");
//				if (wfMaster != null) {
//					wfMaster.setIsDefault(false);
//					wfMaster.setChangedBy(requestHeaders.getLoggedUser().getUserId());
//					wfMaster.setChangedTs(new Date());
//
//					baseDao.saveOrUpdate(wfMaster);
//				}
//				WfMaster wfMaster = new WfMaster();
//				wfMaster = wfMasterRepo.getWfMasterDataByWfId(payload.get("wfId") + "");
//				if (wfMaster != null) {
//					wfMaster.setIsDefault(true);
//					wfMaster.setChangedBy(requestHeaders.getLoggedUser().getUserId());
//					wfMaster.setChangedTs(new Date());
//
//					baseDao.saveOrUpdate(wfMaster);
//				}

				apiResponse.setStatus(0);
				apiResponse.setMessage("updated successfully.");
			
			}else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("Something went wrong. Please contact our administrator.,");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}
}
