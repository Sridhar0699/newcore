package com.portal.workflow;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.basedao.IBaseDao;
import com.portal.clf.models.ErpClassifieds;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.constants.GeneralConstants;
import com.portal.reports.utility.CommonUtils;
import com.portal.repository.UmOrgRolesRepo;
import com.portal.repository.UmUsersRepository;
import com.portal.repository.WfInboxMasterDetailsRepo;
import com.portal.repository.WfInboxMasterRepo;
import com.portal.repository.WfMasterRepo;
import com.portal.repository.WfRefToTypeRepo;
import com.portal.repository.WfRefToUsersRepo;
import com.portal.repository.WfRefrenceDataRepo;
import com.portal.repository.WfUserInboxRepo;
import com.portal.rms.model.ApprovalDetailsModel;
import com.portal.rms.model.RmsApproveModel;
import com.portal.rms.service.RmsService;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;
import com.portal.send.models.EmailsTo;
import com.portal.send.service.SendMessageService;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;
import com.portal.user.entities.UmOrgRoles;
import com.portal.user.entities.UmUsers;
import com.portal.wf.entity.WfInboxMaster;
import com.portal.wf.entity.WfInboxMasterDetails;
import com.portal.wf.entity.WfMaster;
import com.portal.wf.entity.WfRefToType;
import com.portal.wf.entity.WfRefToUsers;
import com.portal.wf.entity.WfUserInbox;
import com.portal.workflow.model.DocumentWfStatus;
import com.portal.workflow.model.RuleFields;
import com.portal.workflow.model.RuleList;
import com.portal.workflow.model.WfCommonModel;
import com.portal.workflow.model.WfConstants;
import com.portal.workflow.model.WfDetails;
import com.portal.workflow.model.WfEvent;
import com.portal.workflow.model.WfInboxDetails;
import com.portal.workflow.model.WfInboxMasterDetailsModel;
import com.portal.workflow.model.WfInboxResponse;
import com.portal.workflow.model.WfListViewModel;
import com.portal.workflow.model.WfResponse;
import com.portal.workflow.model.WfStatusModel;

/**
 * <p>
 * This class will manage all work flow related activities , will start the work
 * flow, take the user actions and update the corresponding work flow inbox
 * entries.
 * </p>
 * 
 * @author Sathish
 *
 */
@Transactional
@Service("WorkFlowService")
public class WorkFlowServiceImpl implements WorkFlowService {

//	@Autowired
//	private MongoTemplate mongoTemplate;

	@Autowired
	private Environment prop;

	@Autowired
	private ApplicationContext _appContext;

	@Autowired
	private WfDao wfDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IBaseDao baseDao;

	@Autowired
	private WfUserInboxRepo wfUserInboxRepo;

	@Autowired
	private WfInboxMasterDetailsRepo wfInboxMasterDetailsRepo;
	
	@Autowired
	private WfInboxMasterRepo wfInboxMasterRepo;

	@Autowired
	private WfRefToTypeRepo wfRefToTypeRepo;

	@Autowired
	private WfRefrenceDataRepo refrenceDataRepo;
	
	@Autowired
	private UmOrgRolesRepo umOrgRolesRepo;
	
	@Autowired
	private WfMasterRepo wfMasterRepo;
	
	@Autowired
	private UmUsersRepository umUsersRepository;
	
	@Autowired
	private WfRefToUsersRepo wfRefToUsersRepo;
	
	@Autowired
	private SendMessageService messageService;
	
	@Autowired(required = true)
	private SettingDao settingDao;
	
	@Autowired
	private LoggedUserContext userContext;	
	
	@Autowired
	private WorkflowApprovalHandler workflowApprovalHandler;
	
	@Autowired
	private RmsService rmsService;
	
	
//	@Autowired
//	private DMSDocumentMasterRepo dmsDocumentMasterRepo;	
	
//	@Autowired
//	private ApplicationContext _appContext;
//
//	@Autowired
//	private WfDao wfDao;
//	
//	@Autowired
//	private CommonService commonService;
//	
//	@Autowired
//	private WorkflowApprovalHandler wfHandler;
//	
//	@Autowired
//	private IBaseDao baseDao;
//	

////	@Autowired
////	private NotesService notesService;
//	
////	@Autowired
////	private KypVerificationDao kypVerificationDao;
//	
////	@Autowired
////	private FormTemplateDao formTemplateDao;
//	
////	@Autowired
////	private FormTemplateService formTemplateService;
////	
////	@Autowired
////	private FormTemplateDaoImpl formTemplateDaoImpl;
////	
////	@Autowired
////	private FormTemplateProcessingServiceImpl formTemplateProcSerImpl;
////	
////	@Autowired
////	private NotificationDaoImpl notificationDaoImpl;
//	
//	
//	
//	@SuppressWarnings("unused")
//	private static final Logger logger = Logger.getLogger(WorkFlowServiceImpl.class);
//
//	/**
//	 * This method will return the pending and completed work flow in-box
//	 * entries of a particular user or requested user.
//	 * 
//	 * @param WfCommonModel
//	 *            workFlowCommonModel
//	 */
	@Override
	public GenericApiResponse getInbox(WfCommonModel workFlowCommonModel, ApprovalListView payload)
			throws JsonMappingException, JsonProcessingException {
		GenericApiResponse apiResponse = new GenericApiResponse();
		WfInboxResponse reposnse = wfDao.getWfInboxByUser(workFlowCommonModel, payload);
		List<WfInboxDetails> wfInboxs = reposnse.getWfInboxData();
//		Map<String, String> statusMap = 
//				commonService.getFormStatusByListViewShortId(GeneralConstants.APPROVAL_INBOX_LIST_VIEW);
		List<Integer> userIds = wfInboxs.stream().map(data -> data.getUpdatedBy()).collect(Collectors.toList());
		Map<Integer, UmUsers> usersMap = commonService.getUsersByUserIds(userIds);
		wfInboxs.forEach(wfInbox -> {
			if (wfInbox.getUserInbox() != null && !wfInbox.getUserInbox().isEmpty()) {
				wfInbox.getUserInbox().forEach(userInb -> {
					if (workFlowCommonModel.getLoggedUser().getUserId().equals(userInb.get("userId") + "")) {
						wfInbox.setStatus(userInb.get("status") + "");
					}
				});
			}
//			String statusDesc = statusMap.containsKey(wfInbox.getStatus()) ? 
//					statusMap.get(wfInbox.getStatus()) :  wfInbox.getStatus();
//			wfInbox.setStatusDesc(statusDesc);
			if (!WfConstants.PENDING.equalsIgnoreCase(wfInbox.getStatus())
					&& usersMap.get(wfInbox.getUpdatedBy()) != null) {
				wfInbox.setUpdatedUser(CommonUtils.formattedName(usersMap.get(wfInbox.getUpdatedBy()).getFirstName(),
						usersMap.get(wfInbox.getUpdatedBy()).getLastName()));
			}
			if (wfInbox.getUpdatedUser() == null)
				wfInbox.setUpdatedUser("");

		});
		reposnse.setWfInboxData(wfInboxs);
		if (wfInboxs.isEmpty()) {
			apiResponse.setStatus(1);
			apiResponse.setMessage("No records found");
		} else {
			apiResponse.setStatus(0);
			apiResponse.setData(reposnse);
		}
		return apiResponse;
	}

//
	/**
	 * This method will update the work flow event by taking the candidateGroup /
	 * assignee action and will check for next work flow event or close the current
	 * work flow of a particular user or requested user.
	 * 
	 * @param WfCommonModel
	 * 
	 */
	@SuppressWarnings("unused")
	public GenericApiResponse updateInboxEvent(WfCommonModel workFlowCommonModel) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<WfInboxMaster> wfInboxMap1 = new ArrayList<>();
		List<WfInboxMasterDetails> wfInboxMap = new ArrayList<>();
		if (workFlowCommonModel.getWorkFlowEvent().getExtObjectRefId() == null
				|| workFlowCommonModel.getWorkFlowEvent().getExtObjectRefId().isEmpty())
//			wfInboxMap = wfDao.getWfInboxByInboxId(workFlowCommonModel);
			wfInboxMap = wfDao.getWfInboxByInboxId(workFlowCommonModel);
		if (wfInboxMap.isEmpty()) {
			wfInboxMap1 = wfDao.getWfInboxByExtId(workFlowCommonModel);
		}
		RmsApproveModel payload = new RmsApproveModel();
		payload.setOrderId(workFlowCommonModel.getOrderId());
		payload.setItemId(workFlowCommonModel.getItemId());
		if("REJECTED".equalsIgnoreCase(workFlowCommonModel.getStatus())){
			payload.setStatus(workFlowCommonModel.getWorkFlowEvent().getActStatus());
			rmsService.updateOrderDetailsonStatus(payload);
		}
		
		if (wfInboxMap.isEmpty()) {
			genericApiResponse.setStatus(0);
			genericApiResponse.setMessage("Unable to find the workflow inbox entry");
			return genericApiResponse;
		}
		WfInboxMasterDetails wfInboxData = null;
		for (WfInboxMasterDetails wfInb : wfInboxMap) {
			if (wfInb.getWfInboxId().equalsIgnoreCase(workFlowCommonModel.getWorkFlowEvent().getWfInboxId())) {
				if (wfInb.getTargetRef() != null)
					workFlowCommonModel.getWorkFlowEvent().setSourceRefId(wfInb.getTargetRef());
				workFlowCommonModel.getWorkFlowEvent().setRequestRaisedBy(wfInb.getRequestRaisedBy());
//				workFlowCommonModel.getWorkFlowEvent().setHistoryKey(wfInb.getHistorykey());
				workFlowCommonModel.getWorkFlowEvent().setRefDocData(wfInb.getRefDocData());
				wfInboxData = wfInb;
				break;
			}
		}
//		workFlowCommonModel.getGenericRequestHeaders().setDataStructureInfo(
//				commonService.getAppDataStructureSettings(workFlowCommonModel.getGenericRequestHeaders()));
		checkForTargetEvent(workFlowCommonModel, wfInboxData);
		wfInboxMasterDetailsRepo.updateInboxMasterDetails(workFlowCommonModel.getWorkFlowEvent().getActionComments(),
				workFlowCommonModel.getWorkFlowEvent().getActStatus(), workFlowCommonModel.getLoggedUser().getUserId(),
				new Date(), workFlowCommonModel.getWorkFlowEvent().getWfInboxId());
		String wfStatus="";
		Map<String,WfCommonModel> map = new HashMap<String,WfCommonModel>();
		WfCommonModel commonModel = new WfCommonModel();
//		DMSDocumentMaster docMasterOnMasterId=new DMSDocumentMaster();
		List<Integer> userIds = new ArrayList<Integer>();
		List<UmUsers> umUsersList = new ArrayList<UmUsers>();
		if(workFlowCommonModel.getWorkFlowEvent().getObjectRefId()!= null) {
//			 docMasterOnMasterId = dmsDocumentMasterRepo.getDocMasterOnMasterId(workFlowCommonModel.getWorkFlowEvent().getObjectRefId());
//			 commonModel.setDocumentNumber(docMasterOnMasterId.getDocumentNumder());
        }
		List<WfInboxMasterDetails> inboxMasterDetails = wfInboxMasterDetailsRepo.getInboxMasterDetails(workFlowCommonModel.getWorkFlowEvent().getWfInboxMasterId());
		if(inboxMasterDetails != null && !inboxMasterDetails.isEmpty()) {
			for(WfInboxMasterDetails wfInboxMasterDetails : inboxMasterDetails) {
				if(wfInboxMasterDetails != null && wfInboxMasterDetails.getApprovalLevels() != null && wfInboxMasterDetails.getStatus() != null) {
					if(wfInboxMasterDetails.getApprovalLevels().equalsIgnoreCase("ANY_ONE") && !wfInboxMasterDetails.getStatus().equalsIgnoreCase("APPROVED")) {
						commonModel.setFormStatus(workFlowCommonModel.getWorkFlowEvent().getActStatus());
						commonModel.setChangedBy(userContext.getLoggedUser().getUserId());
						commonModel.setChangeTs(new Date());
						
						userIds.add(wfInboxMasterDetails.getCreatedBy());
					}
					if(wfInboxMasterDetails.getApprovalLevels().equalsIgnoreCase("ALL") && !wfInboxMasterDetails.getStatus().equalsIgnoreCase("APPROVED")) {
						 List<WfUserInbox> wfUserInboxListByInboxMasterId = wfUserInboxRepo.getWfUserInboxListByInboxMasterId(workFlowCommonModel.getWorkFlowEvent().getWfInboxMasterId());
						commonModel.setFormStatus(workFlowCommonModel.getWorkFlowEvent().getActStatus());
						commonModel.setChangedBy(userContext.getLoggedUser().getUserId());
						commonModel.setChangeTs(new Date());
						for(WfUserInbox inbox:wfUserInboxListByInboxMasterId) {
							userIds.add(inbox.getUserId());
						}					
						
					}
				}
				
			}
			if(userIds != null && !userIds.isEmpty()) {
			 umUsersList = umUsersRepository.getUmUsersList(userIds);
				String umUsers2 = umUsersList.get(0).getFirstName()+" "+umUsersList.get(0).getLastName();
				commonModel.setUserName(umUsers2);
			map.put("wfStatus", commonModel);
			}
			
//			this.sendApprovalWfMailToCustomer(map, umUsersList, userContext.getLoggedUser());
		}
//		this.addNotes(new Date() + " Document " + workFlowCommonModel.getDocumentNumber() + " was "+ workFlowCommonModel.getStatus()+ " by "
//				+ commonService.getRequestHeaders().getLoggedUser().getFirstName(), commonService.getRequestHeaders(), workFlowCommonModel.getDocumentMasterId());
		genericApiResponse.setStatus(0);
//		genericApiResponse.setMessage(MessageFormat.format(prop.getProperty("APPROVAL_STATUS"),  
//						 workFlowCommonModel.getWorkFlowEvent().getActStatus().toLowerCase()));
		genericApiResponse.setMessage("Status updated Successfully");
		return genericApiResponse;
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
//	private void addNotes(String note, GenericRequestHeaders requestHeaders, @NotNull String documentId) {
//		try {
//			AuditTrail auditTrail = new AuditTrail();
//
//			auditTrail.setAuditTrailId(UUID.randomUUID().toString());
//			auditTrail.setCreatedBy(requestHeaders.getLoggedUser().getUserId());
//			auditTrail.setDocumentId(documentId);
//			auditTrail.setUserName(requestHeaders.getLoggedUser().getFirstName());
//			auditTrail.setUserId(requestHeaders.getLoggedUser().getUserId());
//			auditTrail.setNotes(note);
//			auditTrail.setCreatedTs(new Date());
//			auditTrail.setMarkAsDelete(false);
//
//			baseDao.save(auditTrail);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	
	private void sendApprovalWfMailToCustomer(Map<String, WfCommonModel> erpClassifiedsMap, List<UmUsers> umUsers,LoggedUser loggedUser) {
		try {

			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
			 String[] ccEmails = umUsers.stream()
                     .map(UmUsers::getEmail)  // Assuming UmUsers has a getEmail() method
                     .toArray(String[]::new);

			 emailTo.setCc(ccEmails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
//			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
//			mapProperties.put("userId", loggedUser.getLogonId());// new userName
			erpClassifiedsMap.entrySet().forEach(erpData -> {
//				 String firstEmail = umUsers.stream()
//                         .findFirst()
//                         .map(UmUsers::getEmail)
//                         .orElse(null); 
//				emailTo.setTo(firstEmail);
//				mapProperties.put("orderId", erpData.getValue().getAdId());
				
				emailTo.setTo(loggedUser.getEmail());
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				mapProperties.put("documentNumber", erpData.getValue().getDocumentNumber());
				mapProperties.put("approvalStatus", erpData.getValue().getFormStatus());
				mapProperties.put("updatedBy", loggedUser.getFirstName()+" "+loggedUser.getLastName());
				mapProperties.put("receiverName", loggedUser.getFirstName()+" "+loggedUser.getLastName());
				
				String formattedDate = dateFormat.format(erpData.getValue().getChangeTs());
				mapProperties.put("updatedTs", formattedDate);
				
					emailTo.setTemplateName("");
				
				
				emailTo.setTemplateProps(mapProperties);


//				emailTo.setDataSource(multiAttachments);
//	            emailTo.setTemplateProps(mapProperties);
				messageService.sendCommunicationMail(emailTo, emailConfigs);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will initiate the work flow and will get the required details
	 * based on the work flow id from work flow master. After populating the
	 * required details will start the work flow
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public WfResponse startEvent(WfCommonModel workFlowCommonModel)
			throws JsonMappingException, JsonProcessingException {
		WfResponse wfResponse = new WfResponse();
		workFlowCommonModel.getWorkFlowEvent().setStartEvent(true);
		List<Map> wfMap = wfDao.getWfMaster(workFlowCommonModel);
		if (wfMap.isEmpty()) {
			wfResponse.setStatus(0);
			wfResponse.setMessage("Unable to find the workflow master");
			return wfResponse;
		}
		WfDetails wfDetails = parseWorkflow(wfMap, workFlowCommonModel);
		populateTargetRefDetails(wfDetails, workFlowCommonModel);
		workFlowCommonModel.setWfDetails(wfDetails);
		workFlowCommonModel.setWfTitle(wfMap.get(0).get("wfTitle") + "");
		if (wfDetails.getServiceTasks().containsKey(workFlowCommonModel.getWorkFlowEvent().getSourceRefId()))
			callWorkflowHandler(workFlowCommonModel, wfDetails);
//		else if("PUR_ORDER".equalsIgnoreCase(wfDetails.getWfType()))
//			wfHandler.handler(workFlowCommonModel);
		if (!"true".equalsIgnoreCase(wfMap.get(0).get(WfConstants.WF_INITIATOR_FLAG) + "")) {
			startWorkFlow(workFlowCommonModel, wfDetails);
		}
//		wfHandler.sendWorkFlowStagesMail(workFlowCommonModel);
		wfResponse.setStatus(1);
		wfResponse.setMessage("Successfully completed");
		return wfResponse;
	}

	/**
	 * This method will do the post in-box update activities like checking the work
	 * flow next event and the required details, will create the work flow in-box
	 * entry for next level user/group
	 * 
	 * @param workFlowCommonModel
	 */
	@SuppressWarnings("rawtypes")
	public void checkForTargetEvent(WfCommonModel workFlowCommonModel, WfInboxMasterDetails wfInboxData) {
		try {
			List<WfInboxMaster> wfInbMasterMap = wfDao.getWfMasterById(workFlowCommonModel);
			workFlowCommonModel.getWorkFlowEvent().setObjectRefId(wfInbMasterMap.get(0).getObjectRefId());
			workFlowCommonModel.getWorkFlowEvent().setWfShortId(wfInbMasterMap.get(0).getWfShortId());
			List<Map> wfMap = wfDao.getWfMaster(workFlowCommonModel);
			WfDetails wfDetails = parseWorkflow(wfMap, workFlowCommonModel);
			boolean isApproved = checkAllApprovals(workFlowCommonModel, wfDetails, wfInboxData);
//		FromTemplateSubmitPayload payload = new FromTemplateSubmitPayload();//Gatepass creation
			if (isApproved) {
				/* Fetching all The Previous Level Approvers Email's */
				workFlowCommonModel.setWfDetails(wfDetails);
//			commonService.getAllApproversEmails(workFlowCommonModel);			
				wfDao.updateInbox(workFlowCommonModel);
				populateTargetRefDetails(wfDetails, workFlowCommonModel);
				
				if (wfDetails.getServiceTasks().containsKey(workFlowCommonModel.getWorkFlowEvent().getSourceRefId())) {
					//remove the accesskey
					wfDao.removeAccesskey(workFlowCommonModel);
					callWorkflowHandler(workFlowCommonModel, wfDetails);
					workFlowCommonModel.getWorkFlowEvent().setTargetRefId(
							wfDetails.getSeqFlowMap().get(workFlowCommonModel.getWorkFlowEvent().getSourceRefId()));
				}
				if(workFlowCommonModel.getWorkFlowEvent().getActStatus().equalsIgnoreCase("REVISED")|| workFlowCommonModel.getWorkFlowEvent().getActStatus().equalsIgnoreCase("REJECTED")) {
//					DMSDocumentMaster docMasterOnMasterId = dmsDocumentMasterRepo.getDocMasterOnMasterId(workFlowCommonModel.getWorkFlowEvent().getObjectRefId());
//					docMasterOnMasterId.setDocumentStatus(workFlowCommonModel.getWorkFlowEvent().getActStatus());
//					baseDao.saveOrUpdate(docMasterOnMasterId);
					if(workFlowCommonModel.getWorkFlowEvent().getActStatus().equalsIgnoreCase("REVISED")) {
						wfInboxMasterRepo.updateWfInboxMasterDataByMasterId(workFlowCommonModel.getWorkFlowEvent().getWfInboxMasterId(),workFlowCommonModel.getWorkFlowEvent().getActStatus(),userContext.getLoggedUser().getUserId(),new Date());
					}
					if(workFlowCommonModel.getWorkFlowEvent().getActStatus().equalsIgnoreCase("REJECTED")) {
						wfInboxMasterRepo.updateWfInboxMasterDataByMasterIdRejectCase(workFlowCommonModel.getWorkFlowEvent().getWfInboxMasterId(),workFlowCommonModel.getWorkFlowEvent().getActStatus(),userContext.getLoggedUser().getUserId(),new Date());
					}
					wfDao.removeAccesskey(workFlowCommonModel);
					callWorkflowHandler(workFlowCommonModel, wfDetails);
				}
//			wfHandler.sendWorkFlowStagesMail(workFlowCommonModel);
				if (wfDetails.getEndEvents().contains(workFlowCommonModel.getWorkFlowEvent().getTargetRefId())) {
					wfDao.updateWfInboxMaster(workFlowCommonModel, wfDetails);
					return;
				}
				createInbox(workFlowCommonModel, wfDetails, workFlowCommonModel.getWorkFlowEvent().getWfRefId());
				wfDao.updateWfInboxMaster(workFlowCommonModel, wfDetails);
			}
			workFlowCommonModel.setWfDetails(wfDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	//Create gatepass method call
//	private void setFromTemplateData(WfCommonModel workFlowCommonModel,FromTemplateSubmitPayload payload) {
//		// TODO Auto-generated method stub
//		payload.setReqFrom("GATEPASS_FORM");
////		payload.setReqAction(GeneralConstants.APPROVAL_INBOX);
//		payload.setOmOrgFormId(workFlowCommonModel.getOmOrgFormId());
//		payload.setAsnId(workFlowCommonModel.getAsnId());
//		payload.setAsnGatepassDetails(workFlowCommonModel.getAsnGatepassDetails());
////		formTemplateService.saveFormTemplateSubmitData(payload);
//	}//
//
	public void startWorkFlow(WfCommonModel workFlowCommonModel, WfDetails wfDetails) {
		WfInboxMaster inboxMaster = new WfInboxMaster();
		inboxMaster.setCurrentStatus(/*
										 * wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().
										 * getTargetRefId()) + " " +
										 */ WfConstants.PENDING);
		inboxMaster.setOrgId(workFlowCommonModel.getGenericRequestHeaders().getOrgId());
		inboxMaster.setOrgOpuId(workFlowCommonModel.getGenericRequestHeaders().getOrgOpuId());
		inboxMaster.setWfShortId(workFlowCommonModel.getWorkFlowEvent().getWfShortId());
		inboxMaster.setObjectRefId(workFlowCommonModel.getWorkFlowEvent().getObjectRefId());
		inboxMaster.setInboxMasterId(UUID.randomUUID().toString());
		inboxMaster.setWfDesc(wfDetails.getWfDesc());
		inboxMaster.setWfTypeId(workFlowCommonModel.getWorkFlowEvent().getWfId());
		inboxMaster.setWfTitle(workFlowCommonModel.getWfTitle());
		if (wfDetails != null)
			inboxMaster.setWfType(wfDetails.getWfType());
		baseDao.save(inboxMaster);
//		mongoTemplate.save(inboxMaster);
		// for getting the details of formData on id
		workFlowCommonModel.getWorkFlowEvent().setWfRefId(inboxMaster.getInboxMasterId());
		createInbox(workFlowCommonModel, wfDetails, inboxMaster.getInboxMasterId());
	}

//
//	/**
//	 * This method will create the work flow in-box entry for the next level or
//	 * start event entry for the required approvals
//	 * 
//	 * @param workFlowCommonModel
//	 * @param wfDetails
//	 * @param inboxMasterId
//	 */
	
//	public void createInbox1(WfCommonModel workFlowCommonModel, WfDetails wfDetails, String inboxMasterId) {
//		WfInboxMasterDetailsModel wfInbox = new WfInboxMasterDetailsModel();
//		wfInbox.setWfInboxId(UUID.randomUUID().toString());
//		wfInbox.setExtObjRefId(workFlowCommonModel.getWorkFlowEvent().getExtObjectRefId());
////		wfInbox.setOrgId(workFlowCommonModel.getGenericRequestHeaders().getOrgId());
////		wfInbox.setOrgOpuId(workFlowCommonModel.getGenericRequestHeaders().getOrgOpuId());
//		wfInbox.setTargetRef(workFlowCommonModel.getWorkFlowEvent().getTargetRefId());
//		wfInbox.setSourceStageRef(workFlowCommonModel.getWorkFlowEvent().getSourceRefId());
//		Map<String, Object> sourceRefMap1 = wfDetails.getSourceRefVsActor()
//				.get(workFlowCommonModel.getWorkFlowEvent().getTargetRefId());
//		if(sourceRefMap1 != null && sourceRefMap1.get("approvalType") != null &&
//				sourceRefMap1.get("approvalLevels") != null){
//		wfInbox.setApprovalType((String) sourceRefMap1.get("approvalType"));
//		wfInbox.setApprovalLevels((String) sourceRefMap1.get("approvalLevels"));
//		}
//		if(sourceRefMap1 != null && sourceRefMap1.get("candidateUsers") != null){
//			wfInbox.setCandidateUsers((List<String>) sourceRefMap1.get("candidateUsers"));
//		}
//		wfInbox.setFrmEditable(sourceRefMap1.get("frmEditable") != null ? Boolean.parseBoolean(sourceRefMap1.get("frmEditable")+""):false);
//		if(sourceRefMap1 != null && sourceRefMap1.get("candidateGroups") != null){
//			wfInbox.setCandidateGroups((List<String>) sourceRefMap1.get("candidateGroups"));
//		}
//		/*wfInbox.setRefFrom(
//				wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().getSourceRefId()));
//		wfInbox.setRefFromType(
//				wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().getSourceRefId()));
//		wfInbox.setRefToType(
//				wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().getTargetRefId()));
//		wfInbox.setRefTo(wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().getTargetRefId()));*/
//		this.processInboxRefrences(workFlowCommonModel, wfDetails, wfInbox);
//		wfInbox.setRequestedDate(new Date());
//		//rule result changes
//		boolean flag = false;
//			Map<String, Object> sourceRefMap = wfDetails.getSourceRefVsActor()
//					.get(workFlowCommonModel.getWorkFlowEvent().getTargetRefId());
//			if(sourceRefMap !=null && !sourceRefMap.isEmpty() && sourceRefMap.get("ruleCheckedFlag") != null){
//				 flag = (boolean) sourceRefMap.get("ruleCheckedFlag");
//			}
//		if(workFlowCommonModel.isCheckInboxForSkip() == true && flag == true){
//			wfInbox.setStatus(WfConstants.SKIPPED);
//		}else{
//			wfInbox.setStatus(WfConstants.PENDING);
//		}
//		workFlowCommonModel.getWorkFlowEvent().setWfRefId(null);
//		wfInbox.setCreatedBy(workFlowCommonModel.getLoggedUser().getUserId());
//		wfInbox.setRequestRaisedBy(workFlowCommonModel.getWorkFlowEvent().getRequestRaisedBy());
//		if(wfInbox.getRequestRaisedBy() == null) {
//			wfInbox.setRequestRaisedBy(workFlowCommonModel.getLoggedUser() != null ? 
//					CommonUtils.formattedName(workFlowCommonModel.getLoggedUser().getFirstName() , workFlowCommonModel.getLoggedUser().getLastName()) : "");
//		}
//		wfInbox.setCreatedTs(new Date());
//		wfInbox.setMarkAsDelete(false);
//		wfInbox.setHistorykey(workFlowCommonModel.getWorkFlowEvent().getHistoryKey() != null 
//				? workFlowCommonModel.getWorkFlowEvent().getHistoryKey() : UUID.randomUUID().toString());
//		wfInbox.setRefDocData(workFlowCommonModel.getWorkFlowEvent().getRefDocData());
//		if(workFlowCommonModel.getWorkFlowEvent().getVendorId() != null)
//			wfInbox.setVendorId(workFlowCommonModel.getWorkFlowEvent().getVendorId());
//		if(workFlowCommonModel.getWorkFlowEvent().getVendorCode() != null)
//			wfInbox.setVendorCode(workFlowCommonModel.getWorkFlowEvent().getVendorCode());		
//		//mongoTemplate.save(wfInbox, MongoEntityConstants.WF_WORKFLOW_INBOX);
//		/**Start To avoid Multiple Duplicate record in Inbox below code added.- Prod Issue! **/
//		List<WfInboxMaster> wfInboxRecord = wfDao.getWfMasterById(workFlowCommonModel);
//		List<WfInbox> inboxArrWithDuplicatePendingRec = new ArrayList<WfInbox>();
//		if (wfInboxRecord.size() != 0 && wfInboxRecord.get(0) != null
//				&& WfConstants.PENDING.equalsIgnoreCase(wfInboxRecord.get(0).getCurrentStatus())) {
//			inboxArrWithDuplicatePendingRec = wfInboxRecord.get(0).getWfInbox().stream().filter((WfInbox inbox) -> {
//				return inbox.getTargetRef().equalsIgnoreCase(wfInbox.getTargetRef())
//						&& WfConstants.PENDING.equalsIgnoreCase(inbox.getStatus());
//			}).collect(Collectors.toList());
//		}
//		/**END To avoid Multiple Duplicate record in Inbox below code added.- Prod Issue! **/
//		if(inboxArrWithDuplicatePendingRec.size() == 0)
//		wfDao.createInbox(wfInbox, workFlowCommonModel, inboxMasterId);
//		
//		//rule result change
//		if(WfConstants.SKIPPED.equalsIgnoreCase(wfInbox.getStatus())){
//			notesService.createNotesBasedOnFormActions(workFlowCommonModel);
//			WfEvent wfevent = new WfEvent();
//			wfevent.setActStatus(wfInbox.getStatus());
//			wfevent.setWfInboxId(wfInbox.getWfInboxId());
//			wfevent.setWfRefId(inboxMasterId);
//			wfevent.setSourceRefId(wfInbox.getTargetRef());
//			wfevent.setWfInboxMasterId(inboxMasterId);
//			wfevent.setObjectRefId(workFlowCommonModel.getWorkFlowEvent().getObjectRefId());
//			wfevent.setWfShortId(workFlowCommonModel.getWorkFlowEvent().getWfShortId());
//			wfevent.setRefDocData(workFlowCommonModel.getWorkFlowEvent().getRefDocData());
//			workFlowCommonModel.setWorkFlowEvent(wfevent);
//			if(workFlowCommonModel.getWorkFlowEvent().getActStatus().equalsIgnoreCase(WfConstants.SKIPPED)){
//				 populateTargetRefDetails(wfDetails, workFlowCommonModel);
//				 if(!"approveEnd".equalsIgnoreCase(workFlowCommonModel.getWorkFlowEvent().getTargetRefId())){
//					 createInbox(workFlowCommonModel, wfDetails, workFlowCommonModel.getWorkFlowEvent().getWfRefId());
//				 }
//				 if (wfDetails.getServiceTasks().containsKey(workFlowCommonModel.getWorkFlowEvent().getSourceRefId())) {
//					 if(!workFlowCommonModel.getWorkFlowEvent().getSourceRefId().contains("SKIPPED"))
//						callWorkflowHandler(workFlowCommonModel, wfDetails);
//						workFlowCommonModel.getWorkFlowEvent().setTargetRefId(
//								wfDetails.getSeqFlowMap().get(workFlowCommonModel.getWorkFlowEvent().getSourceRefId()));
//						wfDao.updateWfInboxMaster(workFlowCommonModel, wfDetails);
//					}
//			}
//		}
//
//	}
	
	public void createInbox(WfCommonModel workFlowCommonModel, WfDetails wfDetails, String inboxMasterId) {
//		WfInbox wfInbox = new WfInbox();
//		WfInboxMasterDetails wfInbox = new WfInboxMasterDetails();
		WfInboxMasterDetailsModel wfInbox = new WfInboxMasterDetailsModel();
		wfInbox.setWfInboxId(UUID.randomUUID().toString());
		wfInbox.setExtObjRefId(workFlowCommonModel.getWorkFlowEvent().getExtObjectRefId());
//		wfInbox.setOrgId(workFlowCommonModel.getGenericRequestHeaders().getOrgId());
//		wfInbox.setOrgOpuId(workFlowCommonModel.getGenericRequestHeaders().getOrgOpuId());
		wfInbox.setTargetRef(workFlowCommonModel.getWorkFlowEvent().getTargetRefId());
		wfInbox.setSourceStageRef(workFlowCommonModel.getWorkFlowEvent().getSourceRefId());
		Map<String, Object> sourceRefMap1 = wfDetails.getSourceRefVsActor()
				.get(workFlowCommonModel.getWorkFlowEvent().getTargetRefId());
		if (sourceRefMap1 != null && sourceRefMap1.get("approvalType") != null
				&& sourceRefMap1.get("approvalLevels") != null) {
			wfInbox.setApprovalType((String) sourceRefMap1.get("approvalType"));
			wfInbox.setApprovalLevels((String) sourceRefMap1.get("approvalLevels"));
		}
		if (sourceRefMap1 != null && sourceRefMap1.get("candidateUsers") != null) {
			wfInbox.setCandidateUsers((List<Integer>) sourceRefMap1.get("candidateUsers"));
		}
		if (sourceRefMap1 != null && sourceRefMap1.get("candidateGroups") != null) {
			wfInbox.setCandidateGroups((List<String>) sourceRefMap1.get("candidateGroups"));
		}
		/*
		 * wfInbox.setRefFrom(
		 * wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().
		 * getSourceRefId())); wfInbox.setRefFromType(
		 * wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().
		 * getSourceRefId())); wfInbox.setRefToType(
		 * wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().
		 * getTargetRefId()));
		 * wfInbox.setRefTo(wfDetails.getSourceRefVsActor().get(workFlowCommonModel.
		 * getWorkFlowEvent().getTargetRefId()));
		 */
		this.processInboxRefrences(workFlowCommonModel, wfDetails, wfInbox);
//		wfInbox.setRequestedDate(new Date());
		//rule result changes
				boolean flag = false;
					Map<String, Object> sourceRefMap = wfDetails.getSourceRefVsActor()
							.get(workFlowCommonModel.getWorkFlowEvent().getTargetRefId());
					if(sourceRefMap !=null && !sourceRefMap.isEmpty() && sourceRefMap.get("ruleCheckedFlag") != null){
						 flag = (boolean) sourceRefMap.get("ruleCheckedFlag");
					}
				if(workFlowCommonModel.isCheckInboxForSkip() == true && flag == true){
					wfInbox.setStatus(WfConstants.SKIPPED);
				}else if(workFlowCommonModel.isCheckWorkflowForSkip() == true && flag == true) {
					wfInbox.setStatus(WfConstants.WORKFLOW_SKIPPED);
				}else{
					wfInbox.setStatus(WfConstants.PENDING);
				}
//				else if(workFlowCommonModel.isCheckWorkflowForSkip() == true && flag == true) {
//					wfInbox.setStatus(WfConstants.WORKFLOW_SKIPPED);
//				}else {
//					wfInbox.setStatus(WfConstants.PENDING);
//				}
//		wfInbox.setStatus(WfConstants.PENDING);
		workFlowCommonModel.getWorkFlowEvent().setWfRefId(null);
		wfInbox.setCreatedBy(workFlowCommonModel.getLoggedUser().getUserId());
		wfInbox.setRequestRaisedBy(workFlowCommonModel.getWorkFlowEvent().getRequestRaisedBy());
		if (wfInbox.getRequestRaisedBy() == null) {
			wfInbox.setRequestRaisedBy(
					workFlowCommonModel.getLoggedUser() != null ? workFlowCommonModel.getLoggedUser().getFirstName()
							+ " " + workFlowCommonModel.getLoggedUser().getLastName() : "");
		}
		wfInbox.setCreatedTs(new Date());
		wfInbox.setMarkAsDelete(false);
//		wfInbox.setHistorykey(workFlowCommonModel.getWorkFlowEvent().getHistoryKey() != null 
//				? workFlowCommonModel.getWorkFlowEvent().getHistoryKey() : UUID.randomUUID().toString());
		wfInbox.setRefDocData(workFlowCommonModel.getWorkFlowEvent().getRefDocData());
//		if(workFlowCommonModel.getWorkFlowEvent().getVendorId() != null)
//			wfInbox.setVendorId(workFlowCommonModel.getWorkFlowEvent().getVendorId());
//		if(workFlowCommonModel.getWorkFlowEvent().getVendorCode() != null)
//			wfInbox.setVendorCode(workFlowCommonModel.getWorkFlowEvent().getVendorCode());		
		// mongoTemplate.save(wfInbox, MongoEntityConstants.WF_WORKFLOW_INBOX);
		/**
		 * Start To avoid Multiple Duplicate record in Inbox below code added.- Prod
		 * Issue!
		 **/
		List<WfInboxMaster> wfInboxRecord = wfDao.getWfMasterById(workFlowCommonModel);
		List<WfInboxMasterDetails> inboxArrWithDuplicatePendingRec = wfDao.getWfInboxDetails(workFlowCommonModel);
//		List<WfInbox> inboxArrWithDuplicatePendingRec = new ArrayList<WfInbox>();
		if(inboxArrWithDuplicatePendingRec != null && !inboxArrWithDuplicatePendingRec.isEmpty()) {
			inboxArrWithDuplicatePendingRec = inboxArrWithDuplicatePendingRec.stream().filter((WfInboxMasterDetails inbox) -> {
				return inbox.getTargetRef().equalsIgnoreCase(wfInbox.getTargetRef())
						&& WfConstants.PENDING.equalsIgnoreCase(inbox.getStatus());
			}).collect(Collectors.toList());
		}
		if (wfInboxRecord.size() != 0 && wfInboxRecord.get(0) != null
				&& WfConstants.PENDING.equalsIgnoreCase(wfInboxRecord.get(0).getCurrentStatus())) {
//			inboxArrWithDuplicatePendingRec = wfInboxRecord.get(0).getWfInbox().stream().filter((WfInbox inbox) -> {
//				return inbox.getTargetRef().equalsIgnoreCase(wfInbox.getTargetRef())
//						&& WfConstants.PENDING.equalsIgnoreCase(inbox.getStatus());
//			}).collect(Collectors.toList());
		}
		/**
		 * END To avoid Multiple Duplicate record in Inbox below code added.- Prod
		 * Issue!
		 **/
		if (inboxArrWithDuplicatePendingRec.size() == 0) {
			wfInbox.setAccessKey(UUID.randomUUID().toString());
			wfDao.createInbox(wfInbox, workFlowCommonModel, inboxMasterId);
			
			//remove the accesskey
//			wfDao.removeAccesskey(wfInbox.getWfInboxId());
			wfDao.removeAccesskey(workFlowCommonModel);
		}
		
		//rule result change
				if(WfConstants.SKIPPED.equalsIgnoreCase(wfInbox.getStatus())){
					WfEvent wfevent = new WfEvent();
					wfevent.setActStatus(wfInbox.getStatus());
					wfevent.setWfInboxId(wfInbox.getWfInboxId());
					wfevent.setWfRefId(inboxMasterId);
					wfevent.setSourceRefId(wfInbox.getTargetRef());
					wfevent.setWfInboxMasterId(inboxMasterId);
					wfevent.setObjectRefId(workFlowCommonModel.getWorkFlowEvent().getObjectRefId());
					wfevent.setWfShortId(workFlowCommonModel.getWorkFlowEvent().getWfShortId());
					wfevent.setRefDocData(workFlowCommonModel.getWorkFlowEvent().getRefDocData());
					workFlowCommonModel.setWorkFlowEvent(wfevent);
					if(workFlowCommonModel.getWorkFlowEvent().getActStatus().equalsIgnoreCase(WfConstants.SKIPPED)){
						 populateTargetRefDetails(wfDetails, workFlowCommonModel);
						 if(!"approveEnd".equalsIgnoreCase(workFlowCommonModel.getWorkFlowEvent().getTargetRefId())){
							 createInbox(workFlowCommonModel, wfDetails, workFlowCommonModel.getWorkFlowEvent().getWfRefId());
						 }
						 if (wfDetails.getServiceTasks().containsKey(workFlowCommonModel.getWorkFlowEvent().getSourceRefId())) {
							 if(!workFlowCommonModel.getWorkFlowEvent().getSourceRefId().contains("SKIPPED"))
								callWorkflowHandler(workFlowCommonModel, wfDetails);
								workFlowCommonModel.getWorkFlowEvent().setTargetRefId(
										wfDetails.getSeqFlowMap().get(workFlowCommonModel.getWorkFlowEvent().getSourceRefId()));
								wfDao.updateWfInboxMaster(workFlowCommonModel, wfDetails);
							}
					}
				}
				
				if(WfConstants.WORKFLOW_SKIPPED.equalsIgnoreCase(wfInbox.getStatus())) {
					WfEvent wfevent = new WfEvent();
					wfevent.setActStatus(wfInbox.getStatus());
					wfevent.setWfInboxId(wfInbox.getWfInboxId());
					wfevent.setWfRefId(inboxMasterId);
					wfevent.setSourceRefId(wfInbox.getTargetRef());
					wfevent.setWfInboxMasterId(inboxMasterId);
					wfevent.setObjectRefId(workFlowCommonModel.getWorkFlowEvent().getObjectRefId());
					wfevent.setWfShortId(workFlowCommonModel.getWorkFlowEvent().getWfShortId());
					wfevent.setRefDocData(workFlowCommonModel.getWorkFlowEvent().getRefDocData());
					workFlowCommonModel.setWorkFlowEvent(wfevent);
					wfDao.updateWfInboxMaster(workFlowCommonModel, wfDetails);
					
					workflowApprovalHandler.handler(workFlowCommonModel);
					
				}
				if (wfInbox.getRefToUsers() != null && WfConstants.PENDING.equalsIgnoreCase(wfInbox.getStatus())) {
					List<Integer> refToUsers = wfInbox.getRefToUsers(); // assuming refToUsers is already a
																		// List<Integer>
//					String refToUsersString = refToUsers.stream().map(String::valueOf) // Convert Integer to String
//							.collect(Collectors.joining(","));
					List<UmUsers> toEmails = umUsersRepository.getUmUsersList(refToUsers);
					Map<String, ErpClassifieds> erpData  = new HashMap<String, ErpClassifieds>();
					if(workFlowCommonModel.getWorkFlowEvent().getAction() != null && "TAKE_ACTION".equalsIgnoreCase(workFlowCommonModel.getWorkFlowEvent().getAction())) {
						ApprovalDetailsModel model = new ApprovalDetailsModel();
						model.setOrderId(Arrays.asList(workFlowCommonModel.getOrderId()));
						model.setUserId(Integer.parseInt(workFlowCommonModel.getWorkFlowEvent().getRefToUser()));
						model.setAction(workFlowCommonModel.getWorkFlowEvent().getAction());
						erpData = rmsService.getRmsOrderDetailsForErp(model);
					}else {
						 erpData = rmsService.getRmsOrderDetailsForErp(Arrays.asList(workFlowCommonModel.getOrderId()));
					}
					
					if (erpData != null && !erpData.isEmpty()) {
						ErpClassifieds erpClassifieds = erpData.get(workFlowCommonModel.getItemId());
						if (erpClassifieds != null) {
							erpClassifieds.setInboxId(wfInbox.getNewWfInboxId());
							erpClassifieds.setInboxMasterId(inboxMasterId);
							erpClassifieds.setObjectRefId(erpClassifieds.getItemId());
							erpClassifieds.setAccessKey(wfInbox.getAccessKey());
							erpClassifieds.setCurrentLevel(Integer.parseInt(wfInbox.getTargetRef()));
						}
					}
					if (toEmails != null && !toEmails.isEmpty()) {
						List<String> toMails = new ArrayList<>();
						for(UmUsers user : toEmails) {
							toMails.add(user.getEmail());
						}
//						toMails.add("shivkumar.yemulwar@incresol.com");
						String toMailsString = String.join(",", toMails);
						rmsService.sendMailToApprover(toMailsString, null, erpData);
					}
				}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WfDetails parseWorkflow(List<Map> wfMaster, WfCommonModel workFlowCommonModel)
			throws JsonMappingException, JsonProcessingException {
//		Map wfMap = (Map) wfMaster.get(0).get("process");
		String metadata = (String) wfMaster.get(0).get("processMetaData");

		byte[] decodedBytes = Base64.getDecoder().decode(metadata);
		String decodedJson = new String(decodedBytes);
		ObjectMapper objectMapper = new ObjectMapper();
		Map wfMap = objectMapper.readValue(decodedJson, new TypeReference<Map>() {
		});

		WfDetails wfDetails = new WfDetails();
		LinkedHashMap<String, String> seqFlowMap = new LinkedHashMap<>();
		LinkedHashMap<String, Map<String, Map>> condSeqFlowMap = new LinkedHashMap<>();
		LinkedHashMap<String, Map<String, Object>> sourceRefVsActor = new LinkedHashMap<String, Map<String, Object>>();
		LinkedHashMap<String, List<Map<String, Object>>> sourceRefVsServiceTask = new LinkedHashMap<>();
		List<String> endEvents = new ArrayList<>();
		wfDetails.setWfDesc(wfMaster.get(0).containsKey("wfName") ? (String) wfMaster.get(0).get("wfName") : "");
		wfDetails.setWfType(wfMaster.get(0).containsKey("wfType") ? (String) wfMaster.get(0).get("wfType") : "");
		// wfDetails.setInitiatorFlag(wfMaster.get(0).containsKey(WfConstants.WF_INITIATOR_FLAG)
		// ? wfMaster.get(0).get(WfConstants.WF_INITIATOR_FLAG)+"" : "");
		List<Map> userTask = (List<Map>) wfMap.get("userTask");
		if (userTask != null && !userTask.isEmpty() && userTask.get(0).get("approvalType") != null
				&& WfConstants.INITIATOR.equalsIgnoreCase(userTask.get(0).get("approvalType") + "")) {
			List<String> approverList = new ArrayList<String>();
			approverList.add(workFlowCommonModel.getWorkFlowEvent().getInitiatorId());
			Map userTaskMap = userTask.get(0);
			userTaskMap.put("candidateUsers", approverList);
			userTaskMap.put("candidateGroups", getRoleShortIdbyUserId(workFlowCommonModel));
		}

		List<Map> seqFlow = wfMap.containsKey(WfConstants.SEQUENCE_FLOW)
				? (List<Map>) wfMap.get(WfConstants.SEQUENCE_FLOW)
				: new ArrayList<>();
		List<Map> userTasks = wfMap.containsKey(WfConstants.USER_TASK) ? (List<Map>) wfMap.get(WfConstants.USER_TASK)
				: new ArrayList<>();
		LinkedHashMap gateWay = wfMap.containsKey(WfConstants.EXCLUSIVE_GATEWAY)
				? (LinkedHashMap) wfMap.get(WfConstants.EXCLUSIVE_GATEWAY)
				: new LinkedHashMap<>();
		List<Map> serviceTask = wfMap.containsKey(WfConstants.SERVICE_TASK)
				? (List<Map>) wfMap.get(WfConstants.SERVICE_TASK)
				: new ArrayList<>();
		List<Map> endEvent = wfMap.containsKey(WfConstants.END_EVENT) ? (List<Map>) wfMap.get(WfConstants.END_EVENT)
				: new ArrayList<>();
		for (Map map : seqFlow) {
			if (!map.containsKey(WfConstants.CONDITION_EXPRESSION))
				seqFlowMap.put((String) map.get(WfConstants.SOURCE_REF), (String) map.get(WfConstants.TARGET_REF));
		}
		for (Map map : seqFlow) {
			if (map.containsKey(WfConstants.CONDITION_EXPRESSION)) {
				if (!condSeqFlowMap.containsKey((String) map.get(WfConstants.SOURCE_REF))) {
					Map<String, Map> condMap = new HashMap<>();
					condMap.put((String) map.get(WfConstants.TARGET_REF),
							(Map) map.get(WfConstants.CONDITION_EXPRESSION));
					condSeqFlowMap.put((String) map.get(WfConstants.SOURCE_REF), condMap);
				} else {
					condSeqFlowMap.get((String) map.get(WfConstants.SOURCE_REF)).put(
							(String) map.get(WfConstants.TARGET_REF), (Map) map.get(WfConstants.CONDITION_EXPRESSION));
				}
			}
		}
		for (Map map : userTasks) {
			sourceRefVsActor.put((String) map.get(WfConstants.EXCLUSIVE_GATEWAY_ID), map);
		}
		for (Map<String, Object> serviceTaskMap : serviceTask) {
			/*
			 * Map<String, String> serviceClassMap = new HashMap<>();
			 * serviceClassMap.put(WfConstants.WF_SERVICE_TASK_CLASS, (String)
			 * map.get(WfConstants.WF_SERVICE_TASK_CLASS));
			 * serviceClassMap.put(WfConstants.WF_SERVICE_TASK_METHOD, (String)
			 * map.get(WfConstants.WF_SERVICE_TASK_METHOD));
			 */
			if (serviceTaskMap.get(WfConstants.SERVICE_TASKS_LIST) != null
					&& serviceTaskMap.get(WfConstants.SERVICE_TASKS_LIST) instanceof List) {
				List<Map<String, Object>> tasksList = (List<Map<String, Object>>) serviceTaskMap
						.get(WfConstants.SERVICE_TASKS_LIST);
				tasksList.sort(Comparator.comparing(
						taskMap -> Integer.parseInt(taskMap.get(WfConstants.SERVICE_TASKS_SERIAL_NUMBER) + ""),
						Comparator.nullsLast(Comparator.naturalOrder())));
				sourceRefVsServiceTask.put((String) serviceTaskMap.get(WfConstants.WF_SERVICE_TASK_ID), tasksList);
			}
		}
		for (Map map : endEvent) {
			endEvents.add((String) map.get(WfConstants.END_EVENT_ID));
		}
		if (!gateWay.isEmpty() && gateWay.containsKey(WfConstants.EXCLUSIVE_GATEWAY_ID))
			wfDetails.setExclusiveGateway((String) gateWay.get(WfConstants.EXCLUSIVE_GATEWAY_ID));
		wfDetails.setObjectRefMap(wfMaster.get(0).containsKey(WfConstants.WF_OBJECT_REF)
				? (Map) wfMaster.get(0).get(WfConstants.WF_OBJECT_REF)
				: new HashMap<>());
		if (wfMap.get(WfConstants.WF_MAIL_NOTIF) != null && wfMap.get(WfConstants.WF_MAIL_NOTIF) instanceof Map)
			wfDetails.setMailNotificationsMap((Map<String, Object>) wfMap.get(WfConstants.WF_MAIL_NOTIF));
		wfDetails.setSeqFlowMap(seqFlowMap);
		wfDetails.setCondSeqFlowMap(condSeqFlowMap);
		wfDetails.setEndEvents(endEvents);
		wfDetails.setSourceRefVsActor(sourceRefVsActor);
		wfDetails.setServiceTasks(sourceRefVsServiceTask);
		return wfDetails;
	}

//
	@SuppressWarnings("rawtypes")
	public void populateTargetRefDetails(WfDetails wfDetails, WfCommonModel workFlowCommonModel) {
		WfEvent wfEvent = workFlowCommonModel.getWorkFlowEvent();
		if (workFlowCommonModel.getWorkFlowEvent().isStartEvent()) {
			wfEvent.setSourceRefId(wfDetails.getSeqFlowMap().get(WfConstants.START_EVENT));
			wfEvent.setTargetRefId(wfDetails.getSeqFlowMap().get(wfEvent.getSourceRefId()));
		} else {
			if (wfDetails.getSeqFlowMap().containsKey(wfEvent.getSourceRefId())) {
				wfEvent.setTargetRefId(wfDetails.getSeqFlowMap().get(wfEvent.getSourceRefId()));
			}
			if (wfDetails.getCondSeqFlowMap().containsKey(wfEvent.getTargetRefId())) {
				for (Map.Entry<String, Map> map : wfDetails.getCondSeqFlowMap().get(wfEvent.getTargetRefId())
						.entrySet()) {
					if (map.getValue().containsKey(WfConstants.WF_SERVICE_TASK_TEXT)
							&& ((String) map.getValue().get(WfConstants.WF_SERVICE_TASK_TEXT))
									.equalsIgnoreCase(workFlowCommonModel.getWorkFlowEvent().getActStatus())) {
						wfEvent.setSourceRefId(map.getKey());
						wfEvent.setTargetRefId(wfDetails.getSeqFlowMap().get(wfEvent.getSourceRefId()));
					}
					if (map.getValue().get("sourceStage") != null) {
						if (wfDetails.getCondSeqSrc() == null) {
							Map<String, Object> condSeqSource = new HashMap<String, Object>();
							condSeqSource.put(map.getKey(), map.getValue().get("sourceStage"));
							wfDetails.setCondSeqSrc(condSeqSource);
						} else
							wfDetails.getCondSeqSrc().put(map.getKey(), map.getValue().get("sourceStage"));
					}
				}
			}
		}
	}

	public void callWorkflowHandler(WfCommonModel workFlowCommonModel, WfDetails wfDetails) {

		workFlowCommonModel.setWfDetails(wfDetails);
		List<Map<String, Object>> serviceTasksList = wfDetails.getServiceTasks()
				.get(workFlowCommonModel.getWorkFlowEvent().getSourceRefId());
		if (serviceTasksList != null)
			serviceTasksList.forEach(serviceClass -> {
				try {
					Object bean = _appContext.getBean(serviceClass.get(WfConstants.WF_SERVICE_TASK_CLASS) + "");
					Method method = bean.getClass().getMethod(serviceClass.get(WfConstants.WF_SERVICE_TASK_METHOD) + "",
							WfCommonModel.class);
					method.invoke(bean, workFlowCommonModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
	}

//
//	public void updateVendor(String inboxId, String objectRefId, LoggedUser loggedUser) {
//		System.out.println(inboxId + "," + objectRefId + "," + loggedUser);
//	}
//
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> getWorkFlowInboxObjRefDetails1(WfCommonModel workFlowCommonModel, GenericRequestHeaders requestHeaders) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			List<WfInboxMaster> wfInboxMaster = wfDao.getWfMasterById(workFlowCommonModel);
			if(!wfInboxMaster.isEmpty()) {
				workFlowCommonModel.getWorkFlowEvent().setWfShortId(wfInboxMaster.get(0).getWfShortId());
				List<Map> wfMaster = wfDao.getWfMaster(workFlowCommonModel);
				if(!wfMaster.isEmpty()){
					String metadata = (String) wfMaster.get(0).get("processMetaData");

					byte[] decodedBytes = Base64.getDecoder().decode(metadata);
					String decodedJson = new String(decodedBytes);
					ObjectMapper objectMapper = new ObjectMapper();
					Map wfMasterMap = objectMapper.readValue(decodedJson, new TypeReference<Map>() {
					});
//					Map<String, Object> wfMasterMap = wfMaster.get(0);
					
//					if(requestHeaders.getReqFrom() == null)
//						requestHeaders.setReqFrom(wfMasterMap.get(GeneralConstants.WFTYPE)+"");
//					if(wfMasterMap.get(WfConstants.WF_OBJECT_REF) instanceof Map) {
						Map<String, Object> wfObjRef = (Map<String, Object>) wfMasterMap.get(WfConstants.WF_OBJECT_REF);
						/* Setting based collection name processing */
//						wfObjRef.put(WfConstants.WF_OBJECT_REF_COLLECTION,
//								commonService.getSetingBasedCollectionName(
//										workFlowCommonModel.getGenericRequestHeaders(),
//										wfObjRef.get(WfConstants.WF_OBJECT_REF_COLLECTION) + ""));
//					}
					responseMap.put(WfConstants.WF_OBJECT_REF, wfMasterMap.get(WfConstants.WF_OBJECT_REF));
					responseMap.put(WfConstants.WF_OBJECT_REF_ID, wfInboxMaster.get(0).getObjectRefId());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return responseMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateRevisedFormWf(WfEvent workFlowEvent, GenericRequestHeaders requestHeaders, String fromStatus) {
		boolean flag = false;
		try {
			WfInboxMaster wfInboxMaster = wfDao.getWfInboxMasterByRefId(workFlowEvent.getObjectRefId(),
					requestHeaders.getOrgId());
			if (wfInboxMaster != null) {

				WfCommonModel workFlowCommonModel = new WfCommonModel();
				workFlowEvent.setWfShortId(wfInboxMaster.getWfShortId());
				workFlowCommonModel.setWorkFlowEvent(workFlowEvent);
				workFlowCommonModel.setLoggedUser(requestHeaders.getLoggedUser());
				workFlowCommonModel.setGenericRequestHeaders(requestHeaders);
				List<Map> wfMap = wfDao.getWfMaster(workFlowCommonModel);

				/* ======== Code Commented as per TRL Client Request ========= */

				/*
				 * workFlowCommonModel.getWorkFlowEvent().setStartEvent(true); WfDetails
				 * wfDetails = parseWorkflow(wfMap, workFlowCommonModel);
				 * workFlowCommonModel.setWfDetails(wfDetails);
				 */
				/* Updating existing wf records */
				/*
				 * workFlowEvent.setWfInboxMasterId(wfInboxMaster.getInboxMasterId());
				 * workFlowEvent.setHistoryFlag(true);
				 * wfDao.updateInboxHistoryFlag(workFlowCommonModel,
				 * wfInboxMaster.getWfInbox().stream().map(WfInbox::getWfInboxId).collect(
				 * Collectors.toList())); populateTargetRefDetails(wfDetails,
				 * workFlowCommonModel); if
				 * (wfDetails.getServiceTasks().containsKey(workFlowCommonModel.getWorkFlowEvent
				 * ().getSourceRefId())) callWorkflowHandler(workFlowCommonModel, wfDetails);
				 * if(!"true".equalsIgnoreCase(wfMap.get(0).get(WfConstants.WF_INITIATOR_FLAG)+
				 * "")){ createInbox(workFlowCommonModel, wfDetails,
				 * wfInboxMaster.getInboxMasterId()); }
				 * wfHandler.sendWorkFlowStagesMail(workFlowCommonModel); flag = true;
				 */

				/* WF Revised OLD code */
//				workFlowCommonModel.setWfDetails(parseWorkflow(wfMap, workFlowCommonModel));
//				workFlowEvent.setWfInboxMasterId(wfInboxMaster.getInboxMasterId());
//				workFlowEvent.setWfRefId(wfInboxMaster.getInboxMasterId());	
				List<WfInboxMasterDetails> wfInboxMasterDetailsList = wfInboxMasterDetailsRepo
						.getInboxMasterDetails(wfInboxMaster.getInboxMasterId());
				List<WfRefToType> wfRefToTypeList = wfRefToTypeRepo
						.getWfRefToTypeList(wfInboxMaster.getInboxMasterId());
				Map<String, WfRefToType> wfRefToTypeMap = new HashMap<>();
				for (WfRefToType wfRefToType : wfRefToTypeList) {
					wfRefToTypeMap.put(wfRefToType.getWfInboxId(), wfRefToType);
				}
				for (WfInboxMasterDetails inbox : wfInboxMasterDetailsList) {
					if ((WfConstants.REVISED.equalsIgnoreCase(inbox.getStatus())
							|| WfConstants.REVISED.equalsIgnoreCase(fromStatus))
							&& wfInboxMasterDetailsList.size() == wfInboxMasterDetailsList.indexOf(inbox) + 1) {

						List<String> wfRefList = (List<String>) wfRefToTypeMap.get(inbox.getWfInboxId());
						workFlowEvent.setTargetRefActorType(wfRefList.get(0));
						wfDao.updateWfInboxMaster(workFlowCommonModel, null);
						workFlowEvent.setWfInboxId(inbox.getWfInboxId());
						workFlowEvent.setHistoryFlag(true);
						workFlowEvent.setSourceRefId(inbox.getSourceStageRef());
						workFlowEvent.setTargetRefId(inbox.getTargetRef());
						wfDao.updateInboxHistoryFlag(workFlowCommonModel, Arrays.asList(inbox.getWfInboxId()));
						WfInboxMasterDetailsModel newInbox = new WfInboxMasterDetailsModel();
						BeanUtils.copyProperties(inbox, newInbox);
						newInbox.setWfInboxId(UUID.randomUUID().toString());
						newInbox.setCreatedBy(requestHeaders.getLoggedUser().getUserId());
						newInbox.setCreatedTs(new Date());
						newInbox.setRequestedDate(new Date());
						newInbox.setStatus(WfConstants.PENDING);
						newInbox.setRefDocData(workFlowEvent.getRefDocData());
						wfDao.createInbox(newInbox, workFlowCommonModel, wfInboxMaster.getInboxMasterId());
//						wfHandler.handler(workFlowCommonModel);
						if (WfConstants.REVISED.equalsIgnoreCase(fromStatus)) {
							workFlowCommonModel.setFormStatus(fromStatus);
						}
//						wfHandler.sendWorkFlowStagesMail(workFlowCommonModel);
						flag = true;
						break;
					}
				}
//				for (WfInbox inbox : wfInboxMaster.getWfInbox()) {			
//					if ((WfConstants.REVISED.equalsIgnoreCase(inbox.getStatus()) || WfConstants.REVISED.equalsIgnoreCase(fromStatus)) &&
//							wfInboxMaster.getWfInbox().size() == wfInboxMaster.getWfInbox().indexOf(inbox)+1) {
//						workFlowEvent.setTargetRefActorType(inbox.getRefToType().get(0));
//						wfDao.updateWfInboxMaster(workFlowCommonModel, null);
//						workFlowEvent.setWfInboxId(inbox.getWfInboxId());
//						workFlowEvent.setHistoryFlag(true);
//						workFlowEvent.setSourceRefId(inbox.getSourceStageRef());
//						workFlowEvent.setTargetRefId(inbox.getTargetRef());
//						wfDao.updateInboxHistoryFlag(workFlowCommonModel, Arrays.asList(inbox.getWfInboxId()));
//						WfInbox newInbox = new WfInbox();
//						BeanUtils.copyProperties(inbox, newInbox);
//						newInbox.setWfInboxId(UUID.randomUUID().toString());
//						newInbox.setCreatedBy(requestHeaders.getLoggedUser().getUserId());
//						newInbox.setCreatedTs(new Date());
//						newInbox.setRequestedDate(new Date());
//						newInbox.setStatus(WfConstants.PENDING);
//						newInbox.setRefDocData(workFlowEvent.getRefDocData());
//						wfDao.createInbox(newInbox, workFlowCommonModel, wfInboxMaster.getInboxMasterId());
//						wfHandler.handler(workFlowCommonModel);
//						if(WfConstants.REVISED.equalsIgnoreCase(fromStatus)){
//							workFlowCommonModel.setFormStatus(fromStatus);
//						}
//						wfHandler.sendWorkFlowStagesMail(workFlowCommonModel);
//						flag = true;
//						break;
//					}
//				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	
	@SuppressWarnings("unchecked")
	public WfInboxMasterDetailsModel processInboxRefrences(WfCommonModel wfCommonModel, WfDetails wfDetails, WfInboxMasterDetailsModel wfInbox) {
		try {

			/*wfInbox.setRefFrom(
			wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().getSourceRefId()));
	wfInbox.setRefFromType(
			wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().getSourceRefId()));*/
			
			Map<String, Object> sourceRefMap = wfDetails.getSourceRefVsActor()
					.get(wfCommonModel.getWorkFlowEvent().getTargetRefId());
			if (sourceRefMap != null) {
				List<String> roleShortIds = new ArrayList<String>();
//				List<String> userIds = new ArrayList<String>();
				List<Integer> userIds = new ArrayList<Integer>();
				boolean flag = false;
				if("RULERESULT".equalsIgnoreCase((String) sourceRefMap.get("approvalType"))){
					if(sourceRefMap.get("ruleList") != null){
						List<RuleList> list = (List<RuleList>) sourceRefMap.get("ruleList");
						 ObjectMapper objectMapper = new ObjectMapper();
						List<RuleList> ruleList = objectMapper.convertValue(list, new TypeReference<List<RuleList>>() {});
						for (RuleList rule : ruleList) {
							for(RuleFields ruleFields : rule.getRuleFields()){
								Map<String,Object> wfMasterMap = this.getTransactionDetails(wfCommonModel.getItemId());
//							Map<String, Object> wfMasterMap1 = this.getWfInboxRefObjTemplateDetails(
//									this.getWorkFlowInboxObjRefDetails1(wfCommonModel, wfCommonModel.getGenericRequestHeaders()), rule);
							Object value = null;
							
							if (wfMasterMap != null && !wfMasterMap.isEmpty()) {
								value = wfMasterMap.get(ruleFields.getFieldLocation());

							}
							 flag = this.executeExpression(ruleFields, value);
								if (rule.getRuleExpressionType().equalsIgnoreCase("AND")) {
									if (!flag) {
										break;
									}
								} else if (rule.getRuleExpressionType().equalsIgnoreCase("OR")) {
									if (flag) {
										break;
									}
								}
							}
							if (flag) {
								if("WORKFLOW_SKIP".equalsIgnoreCase(rule.getApprovedType())) {
									wfCommonModel.setCheckWorkflowForSkip(true);//For skip the level
									return wfInbox;
								}
								if (rule.getCandidateGroups() != null
										&& rule.getCandidateGroups() instanceof List){
								roleShortIds = rule.getCandidateGroups();
								}
								if (rule.getCandidateUsers() != null
										&& rule.getCandidateUsers() instanceof List){ 
								userIds = rule.getCandidateUsers();
								}
								if (rule.getApprovalLevels() != null
										&& "ALL".equalsIgnoreCase(rule.getApprovalLevels() + "")){
									List<Map<String, Object>>  userInboxList = new ArrayList<Map<String, Object>>();
									if(!userIds.isEmpty()) {
//										userIds.forEach(user->{
//											Map<String, Object> userInbox = new LinkedHashMap<String, Object>();
//											userInbox.put("userId", user);
//											userInbox.put("status", WfConstants.PENDING);
//											userInboxList.add(userInbox);
//										});
										for(Integer user:userIds) {
											Map<String, Object> userInbox = new LinkedHashMap<String, Object>();
				                            userInbox.put("userId", user);
				                            userInbox.put("status", WfConstants.PENDING);
				                            userInboxList.add(userInbox);
										}
									}					
									wfInbox.setUserInbox(userInboxList);
								}
								wfInbox.setRefTo(roleShortIds);
								wfInbox.setRefToType(roleShortIds);
								if (userIds != null && !userIds.isEmpty()) {
									wfInbox.setRefToUsers(userIds);
								}
								wfCommonModel.setCheckInboxForSkip(false);//For unskip the level
								return wfInbox;
							}
						}
					}
				}else{
				if (sourceRefMap.get(WfConstants.CANDIDATE_GROUPS) != null
						&& sourceRefMap.get(WfConstants.CANDIDATE_GROUPS) instanceof List) 
					roleShortIds = (List<String>) sourceRefMap.get(WfConstants.CANDIDATE_GROUPS);					
				if (sourceRefMap.get(WfConstants.CANDIDATE_USERS) != null
						&& sourceRefMap.get(WfConstants.CANDIDATE_USERS) instanceof List) 
					userIds = (List<Integer>) sourceRefMap.get(WfConstants.CANDIDATE_USERS);
				if (sourceRefMap.get(WfConstants.APPROVAL_LEVELS) != null
						&& "ALL".equalsIgnoreCase(sourceRefMap.get(WfConstants.APPROVAL_LEVELS) + "")) {
					/*if (userIds.isEmpty()) {
						Query query = new Query();
						query.addCriteria(Criteria.where("roleShortId").in(roleShortIds).and("markAsDelete").is(false));
						List<UmOrgRoles> umOrgRoles = mongoTemplate.find(query, UmOrgRoles.class);
						List<String> roleIds = umOrgRoles.stream().map(UmOrgRoles::getRoleId)
								.collect(Collectors.toList());
						query = new Query();
						query.addCriteria(
								Criteria.where("orgId").is(wfCommonModel.getGenericRequestHeaders().getOrgId())
										.and("roleId").in(roleIds).and("markAsDelete").is(false));
						List<UmOrgUsers> umOrgUserList = mongoTemplate.find(query, UmOrgUsers.class);
						userIds = umOrgUserList.stream().map(UmOrgUsers::getUserId)
								.collect(Collectors.toList());
					}*/
					//Need To Add Users wise Pending records
					List<Map<String, Object>>  userInboxList = new ArrayList<Map<String, Object>>();
					if(!userIds.isEmpty()) {
//						userIds.forEach(user->{
//							Map<String, Object> userInbox = new LinkedHashMap<String, Object>();
//							userInbox.put("userId", user);
//							userInbox.put("status", WfConstants.PENDING);
//							userInboxList.add(userInbox);
//						});
						for(Integer user:userIds) {
							Map<String, Object> userInbox = new LinkedHashMap<String, Object>();
                            userInbox.put("userId", user);
                            userInbox.put("status", WfConstants.PENDING);
                            userInboxList.add(userInbox);
						}
					}					
					wfInbox.setUserInbox(userInboxList);
				}}
				/*if(!userIds.isEmpty() && roleShortIds.isEmpty()) {
					Query  query = new Query();
					query.addCriteria(
							Criteria.where("orgId").is(wfCommonModel.getGenericRequestHeaders().getOrgId())
									.and("userId").in(userIds).and("markAsDelete").is(false));
					List<UmOrgUsers> umOrgUserList = mongoTemplate.find(query, UmOrgUsers.class);
					List<String> roleIds = umOrgUserList.stream().map(UmOrgUsers::getRoleId)
							.collect(Collectors.toList());
					query = new Query();
					query.addCriteria(Criteria.where("roleId").in(roleIds).and("markAsDelete").is(false));
					List<UmOrgRoles> umOrgRoles = mongoTemplate.find(query, UmOrgRoles.class);
					roleShortIds = umOrgRoles.stream().map(UmOrgRoles::getRoleShortId)
							.collect(Collectors.toList());
				}*/
				wfInbox.setRefTo(roleShortIds);
				wfInbox.setRefToType(roleShortIds);
				if(!userIds.isEmpty())
					wfInbox.setRefToUsers(userIds);
				
				if(wfInbox != null && wfInbox.getRefTo().isEmpty() &&
						 wfInbox.getRefToType().isEmpty()){
					wfCommonModel.setCheckInboxForSkip(true);
				}else {
					wfCommonModel.setCheckInboxForSkip(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wfInbox;
	}
	
	@SuppressWarnings("unchecked")
	public WfInboxMasterDetailsModel processInboxRefrences1(WfCommonModel wfCommonModel, WfDetails wfDetails,
			WfInboxMasterDetailsModel wfInbox) {
		try {

			/*
			 * wfInbox.setRefFrom(
			 * wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().
			 * getSourceRefId())); wfInbox.setRefFromType(
			 * wfDetails.getSourceRefVsActor().get(workFlowCommonModel.getWorkFlowEvent().
			 * getSourceRefId()));
			 */

			Map<String, Object> sourceRefMap = wfDetails.getSourceRefVsActor()
					.get(wfCommonModel.getWorkFlowEvent().getTargetRefId());
			if (sourceRefMap != null) {
				List<String> roleShortIds = new ArrayList<String>();
				List<Integer> userIds = new ArrayList<Integer>();
				if (sourceRefMap.get(WfConstants.CANDIDATE_GROUPS) != null
						&& sourceRefMap.get(WfConstants.CANDIDATE_GROUPS) instanceof List)
					roleShortIds = (List<String>) sourceRefMap.get(WfConstants.CANDIDATE_GROUPS);
				if (sourceRefMap.get(WfConstants.CANDIDATE_USERS) != null
						&& sourceRefMap.get(WfConstants.CANDIDATE_USERS) instanceof List)
					userIds = (List<Integer>) sourceRefMap.get(WfConstants.CANDIDATE_USERS);
				if (sourceRefMap.get(WfConstants.APPROVAL_LEVELS) != null
						&& "ALL".equalsIgnoreCase(sourceRefMap.get(WfConstants.APPROVAL_LEVELS) + "")) {
					/*
					 * if (userIds.isEmpty()) { Query query = new Query();
					 * query.addCriteria(Criteria.where("roleShortId").in(roleShortIds).and(
					 * "markAsDelete").is(false)); List<UmOrgRoles> umOrgRoles =
					 * mongoTemplate.find(query, UmOrgRoles.class); List<String> roleIds =
					 * umOrgRoles.stream().map(UmOrgRoles::getRoleId) .collect(Collectors.toList());
					 * query = new Query(); query.addCriteria(
					 * Criteria.where("orgId").is(wfCommonModel.getGenericRequestHeaders().getOrgId(
					 * )) .and("roleId").in(roleIds).and("markAsDelete").is(false));
					 * List<UmOrgUsers> umOrgUserList = mongoTemplate.find(query, UmOrgUsers.class);
					 * userIds = umOrgUserList.stream().map(UmOrgUsers::getUserId)
					 * .collect(Collectors.toList()); }
					 */
					// Need To Add Users wise Pending records
					List<Map<String, Object>> userInboxList = new ArrayList<Map<String, Object>>();
					if (!userIds.isEmpty()) {
//						userIds.forEach(user -> {
//							Map<String, Object> userInbox = new LinkedHashMap<String, Object>();
//							userInbox.put("userId", user);
//							userInbox.put("status", WfConstants.PENDING);
//							userInboxList.add(userInbox);
//						});
						

						for(Integer user:userIds) {
							Map<String, Object> userInbox = new LinkedHashMap<String, Object>();
                            userInbox.put("userId", user);
                            userInbox.put("status", WfConstants.PENDING);
                            userInboxList.add(userInbox);
						}
					}
					wfInbox.setUserInbox(userInboxList);
				}
				/*
				 * if(!userIds.isEmpty() && roleShortIds.isEmpty()) { Query query = new Query();
				 * query.addCriteria(
				 * Criteria.where("orgId").is(wfCommonModel.getGenericRequestHeaders().getOrgId(
				 * )) .and("userId").in(userIds).and("markAsDelete").is(false));
				 * List<UmOrgUsers> umOrgUserList = mongoTemplate.find(query, UmOrgUsers.class);
				 * List<String> roleIds = umOrgUserList.stream().map(UmOrgUsers::getRoleId)
				 * .collect(Collectors.toList()); query = new Query();
				 * query.addCriteria(Criteria.where("roleId").in(roleIds).and("markAsDelete").is
				 * (false)); List<UmOrgRoles> umOrgRoles = mongoTemplate.find(query,
				 * UmOrgRoles.class); roleShortIds =
				 * umOrgRoles.stream().map(UmOrgRoles::getRoleShortId)
				 * .collect(Collectors.toList()); }
				 */
				wfInbox.setRefTo(roleShortIds);
				wfInbox.setRefToType(roleShortIds);
				if (!userIds.isEmpty()) {
					wfInbox.setRefToUsers(userIds);
				}
//				Map<String, DmsModel> docData = new HashMap<String, DmsModel>();
//				DmsModel dmsModel=new DmsModel();
//				DMSDocumentMaster docMasterOnMasterId = dmsDocumentMasterRepo.getDocMasterOnMasterId(wfCommonModel.getWorkFlowEvent().getObjectRefId());
//				dmsModel.setDocumentNumder(docMasterOnMasterId.getDocumentNumder());
//				dmsModel.setCreatedTs(docMasterOnMasterId.getCreatedTs());
//				dmsModel.setValidTo(docMasterOnMasterId.getValidTo());
//				List<Integer> userId = new ArrayList<Integer>();
//				userId.add(docMasterOnMasterId.getCreatedBy());
//				List<UmUsers> umUsers = umUsersRepository.getUmUsersList(userId);
//				String umUsers2 = umUsers.get(0).getFirstName()+" "+umUsers.get(0).getLastName();
//				dmsModel.setCreatedUserName(umUsers2);
//				docData.put("documentMaster", dmsModel);
//				List<UmUsers> umUsersList = umUsersRepository.getUmUsersList(userIds);
//				this.sendInboxMailToCustomer(docData,umUsersList,userContext.getLoggedUser());
//				messageService.sendCommunicationMail(null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wfInbox;
	}
	
	
//	private void sendInboxMailToCustomer(Map<String, DmsModel> erpClassifiedsMap, List<UmUsers> umUsers,LoggedUser loggedUser) {
//		try {
//
//			Map<String, Object> params = new HashMap<>();
//			params.put("stype", SettingType.APP_SETTING.getValue());
//			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
//			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
//			Map<String, String> emailConfigs = settingTo.getSettings();
//
//			Map<String, Object> mapProperties = new HashMap<String, Object>();
//			EmailsTo emailTo = new EmailsTo();
//			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
//			 String[] ccEmails = umUsers.stream()
//                     .map(UmUsers::getEmail)  // Assuming UmUsers has a getEmail() method
//                     .toArray(String[]::new);
//
//			 emailTo.setCc(ccEmails);
//			emailTo.setOrgId("1000");
//			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
//			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
////			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
////			mapProperties.put("userId", loggedUser.getLogonId());// new userName
//			erpClassifiedsMap.entrySet().forEach(erpData -> {
////				 String firstEmail = umUsers.stream()
////                         .findFirst()
////                         .map(UmUsers::getEmail)
////                         .orElse(null); 
////				emailTo.setTo(firstEmail);
////				mapProperties.put("orderId", erpData.getValue().getAdId());
//				
//				emailTo.setTo(loggedUser.getEmail());
//				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//				String formatCreatedDate= dateFormat.format(erpData.getValue().getCreatedTs());
//				String formatValidTill= dateFormat.format(erpData.getValue().getValidTo());
//				mapProperties.put("documentNumber", erpData.getValue().getDocumentNumder());
//				mapProperties.put("createdDate", formatCreatedDate);
//				mapProperties.put("validTo", formatValidTill);
//				mapProperties.put("receiverName", loggedUser.getFirstName()+" "+loggedUser.getLastName());
//				mapProperties.put("userName", erpData.getValue().getCreatedUserName());
//				emailTo.setTemplateName(GeneralConstants.EMAIL_APPROVAL_REQUEST);
//				emailTo.setTemplateProps(mapProperties);
//
//
////				emailTo.setDataSource(multiAttachments);
////	            emailTo.setTemplateProps(mapProperties);
//				messageService.sendCommunicationMail(emailTo, emailConfigs);
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
	public boolean checkAllApprovals(WfCommonModel wfCommonModel, WfDetails wfDetails, WfInboxMasterDetails wfInbox) {
		boolean isApproved = false;
		GenericRequestHeaders requestHeaders = new GenericRequestHeaders();
		try {
			if (wfInbox != null && wfDetails != null) {
				Map<String, Object> sourceRefMap = wfDetails.getSourceRefVsActor().get(wfInbox.getTargetRef());
				
				if (sourceRefMap!=null && sourceRefMap.get(WfConstants.APPROVAL_LEVELS) != null
						&& "ALL".equalsIgnoreCase(sourceRefMap.get(WfConstants.APPROVAL_LEVELS) + "")) {
					isApproved = checkApprovals(wfCommonModel,wfInbox);				
				} else{
					isApproved = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isApproved;
	}
	
	private boolean checkApprovals(WfCommonModel wfCommonModel, WfInboxMasterDetails wfInbox) {
		// TODO Auto-generated method stub
		boolean isApproved = false;
		
		List<WfUserInbox> userInbox = wfUserInboxRepo.getWfUserInboxList(wfInbox.getInboxMasterId(),
				wfInbox.getWfInboxId());
		if(userInbox != null && !userInbox.isEmpty()){
			for (WfUserInbox inbox1 : userInbox) {
				if (wfCommonModel.getLoggedUser().getUserId().equals(inbox1.getUserId())) {
					inbox1.setStatus(wfCommonModel.getWorkFlowEvent().getActStatus());
					inbox1.setChangedTs(new Date());
					inbox1.setChangedBy(wfCommonModel.getLoggedUser().getUserId());
					baseDao.saveOrUpdate(inbox1);
				}
			}
//			wfDao.updateUserInbox(wfCommonModel,wfInbox.getUserInbox());
			isApproved = true;
			if (!Arrays.asList(WfConstants.REJECTED, WfConstants.REVISED)
					.contains(wfCommonModel.getWorkFlowEvent().getActStatus())) {
				for (WfUserInbox inbox : userInbox) {
					if (inbox.getStatus() != null
							&& WfConstants.PENDING.equalsIgnoreCase(inbox.getStatus())) {
						isApproved = false;
						break;
					}
				}
			}
		}
		return isApproved;
	}

	public boolean checkAllApprovals1(WfCommonModel wfCommonModel, WfDetails wfDetails, WfInboxMasterDetails wfInbox) {
		boolean isApproved = false;
		GenericRequestHeaders requestHeaders = new GenericRequestHeaders();
		try {
			if (wfInbox != null && wfDetails != null) {
				Map<String, Object> sourceRefMap = wfDetails.getSourceRefVsActor().get(wfInbox.getTargetRef());
				// start RuleResult - getting data from template for validation of rules
				// 02-12-2022
				if (sourceRefMap != null
						&& WfConstants.RULE_RESULT.equalsIgnoreCase(sourceRefMap.get("approvalType") + "")) {
					Object rule = sourceRefMap.get("ruleList");
					RuleList ruleData = null;
					List<RuleList> ruleList = (List<RuleList>)rule;
					
					if(ruleList != null && !ruleList.isEmpty()){
//					 ruleData = ruleList.get(0);
						 ObjectMapper objectMapper = new ObjectMapper();
						 ruleData = objectMapper.convertValue(ruleList.get(0), RuleList.class);
					}
					
					Map<String,Object> obj = this.getTransactionDetails(wfCommonModel.getItemId());
//					Map<String,Object> payload = null;
//					Map<String,Object> wfMasterMap = this.getWfInboxRefObjTemplateDetails(this.getWorkFlowInboxObjRefDetails(wfCommonModel, requestHeaders),ruleData);
//					Map<String,Object> res = workFlowDaoV2.getWfInboxRefObjTemplateDetailsData(wfMasterMap, ruleData);
					
					Object value = null;
					if(obj != null && !obj.isEmpty()) {
						value = obj.get(ruleData.getFieldLocation());
					}
//					if(wfMasterMap != null && !wfMasterMap.isEmpty()){
//						 
//						String[] fieldLocation = ruleData.getFieldLocation().split("[.]");
//						String field = ruleData.getFieldLocation();
//						String lastString = field.substring(field.lastIndexOf(".") + 1);
//						System.out.println(lastString);
//					for(String loc : fieldLocation){
//						if(loc.equalsIgnoreCase(lastString)){
//							if(wfMasterMap.get(loc)instanceof Double){
//							value =  (Double) wfMasterMap.get(loc);
//							}else if(wfMasterMap.get(loc) instanceof String){
//								value = (String) wfMasterMap.get(loc);
//							}else if(wfMasterMap.get(loc) instanceof Integer){
//								value = (Integer) wfMasterMap.get(loc);
//							}
//						}else{
//						   wfMasterMap =  (Map<String, Object>) wfMasterMap.get(loc);
//						}
//						
//					}
//					
//					}
//						boolean flag = this.executeExpression(ruleData.getRuleFields(),value);
//						if(flag){
//							
//							if(sourceRefMap!=null && sourceRefMap.get(WfConstants.APPROVAL_LEVELS)!=null
//									&&"ALL".equalsIgnoreCase(sourceRefMap.get(WfConstants.APPROVAL_LEVELS)+"")) {
//								List<WfUserInbox> userInbox = wfUserInboxRepo.getWfUserInboxList(wfInbox.getInboxMasterId(),
//										wfInbox.getWfInboxId());
//								if(userInbox != null && !userInbox.isEmpty()){
////									wfInbox.getUserInbox().forEach(action -> {
////										if(wfCommonModel.getLoggedUser().getUserId()
////												.equalsIgnoreCase(action.get("userId") + "")) {
////											action.put("status", wfCommonModel.getWorkFlowEvent().getActStatus());
////										}
////									});
//									for (WfUserInbox inbox1 : userInbox) {
//										if (wfCommonModel.getLoggedUser().getUserId().equals(inbox1.getUserId())) {
//											inbox1.setStatus(wfCommonModel.getWorkFlowEvent().getActStatus());
//											inbox1.setChangedTs(new Date());
//											inbox1.setChangedBy(wfCommonModel.getLoggedUser().getUserId());
//											baseDao.saveOrUpdate(inbox1);
//										}
//									}
////									wfDao.updateUserInbox(wfCommonModel,wfInbox.getUserInbox());
//									isApproved = true;
//									if (!Arrays.asList(WfConstants.REJECTED, WfConstants.REVISED)
//											.contains(wfCommonModel.getWorkFlowEvent().getActStatus())) {
//										for (WfUserInbox inbox : userInbox) {
//											if (inbox.getStatus() != null
//													&& WfConstants.PENDING.equalsIgnoreCase(inbox.getStatus())) {
//												isApproved = false;
//												break;
//											}
//										}
//									}
//								}
//							} else
//								isApproved = true;
//						}
				} // end
				else {

					if (sourceRefMap != null && sourceRefMap.get(WfConstants.APPROVAL_LEVELS) != null
							&& "ALL".equalsIgnoreCase(sourceRefMap.get(WfConstants.APPROVAL_LEVELS) + "")) {
						List<WfUserInbox> userInbox = wfUserInboxRepo.getWfUserInboxList(wfInbox.getInboxMasterId(),
								wfInbox.getWfInboxId());
						if (userInbox != null && !userInbox.isEmpty()) {
//							userInbox.forEach(userInb -> {
//								if (wfCommonModel.getLoggedUser().getUserId().equals(userInb.getUserId())) {
//									userInb.setStatus(wfCommonModel.getWorkFlowEvent().getActStatus());
//									userInb.setChangedTs(new Date());
//									userInb.setChangedBy(wfCommonModel.getLoggedUser().getUserId());
//
//									baseDao.saveOrUpdate(userInb);
//								}
//							});

							for (WfUserInbox inbox1 : userInbox) {
								if (wfCommonModel.getLoggedUser().getUserId().equals(inbox1.getUserId())) {
									inbox1.setStatus(wfCommonModel.getWorkFlowEvent().getActStatus());
									inbox1.setChangedTs(new Date());
									inbox1.setChangedBy(wfCommonModel.getLoggedUser().getUserId());
									baseDao.saveOrUpdate(inbox1);
								}
							}

							isApproved = true;
							if (!Arrays.asList(WfConstants.REJECTED, WfConstants.REVISED)
									.contains(wfCommonModel.getWorkFlowEvent().getActStatus())) {
								for (WfUserInbox inbox : userInbox) {
									if (inbox.getStatus() != null
											&& WfConstants.PENDING.equalsIgnoreCase(inbox.getStatus())) {
										isApproved = false;
										break;
									}
								}
							}
						}
					} else
						isApproved = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isApproved;
	}
	
	private Map<String, Object> getTransactionDetails(String itemId) {
		Map<String,Object> mapData = new HashMap<String,Object>();
	try {
		String query = "select roi.combined_discount,roi.item_id,roi.order_id from rms_order_items roi where roi.item_id = '" + itemId + "' and roi.mark_as_delete = false";
		List<Object[]> data = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		if(data != null && !data.isEmpty()) {
			for(Object[] obj : data) {
				mapData.put("combined_discount", obj[0]);
			}
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	return mapData;
}

	private boolean executeExpression(RuleFields ruleList, Object value) {
		boolean flag = false;
		double field = 0;
		String strField;
//		RuleFields ruleList = ruleFields.get(0);
		
		if(value instanceof Double){
			field = (double) value;
		}else if(value instanceof String){
			field = Double.parseDouble((String) value);
		}else if(value instanceof Integer) {
			field = (double) (Integer) value;
		}else if(value instanceof Float) {
			field = ((Float) value).doubleValue();
		}
		
		
		if (ruleList != null) {
			if("greterthan".equalsIgnoreCase(ruleList.getExpression())){
			    if (ruleList.getValue1() > field) { 	
				    return true;
				    } else {
				    	return false;
				    }
			    } else if("equalsto".equalsIgnoreCase(ruleList.getExpression())){
			    	if(ruleList.getValue1() == field){
			    		return true;
			    	}else {
			    		return false;
			    	}
			    } else if("lessthan".equalsIgnoreCase(ruleList.getExpression())){
			    	if(ruleList.getValue1() < field){
			    		return true;
			    	}else {
			    		return false;
			    	}
			    } else if("lessthanequalsto".equalsIgnoreCase(ruleList.getExpression())){
			    	if(ruleList.getValue1() <= field){
			    		return true;
			    	}else {
			    		return false; 
			    	}
			    }else if("greterthanequalto".equalsIgnoreCase(ruleList.getExpression())){
		    		if(ruleList.getValue1() >= field){
		    			return true;
		    		} else {
		    			return false;
		    		}
			    } else if("between".equalsIgnoreCase(ruleList.getExpression())){
			    	if(ruleList.getValue2() >= field && ruleList.getValue1() <= field){
			    		return true;
			    	}else {
			    		return false;
			    	}
			    } 
			}
		return flag;
}

	//start RuleResult - validation on rules 02-12-2022
		private boolean executeExpression1(RuleList ruleList,Object value) {
			// TODO Auto-generated method stub
			boolean flag = false;
			double field = 0;
			String strField;
			
			
			if(value instanceof Double){
				field = (double) value;
			}else if(value instanceof String){
				field = Double.parseDouble((String) value);
			}
			
			
			if (ruleList != null) {
				if("greterthan".equalsIgnoreCase(ruleList.getExpression())){
				    if (ruleList.getValue1() > field) { 	
					    return true;
					    } else {
					    	return false;
					    }
				    } else if("equalsto".equalsIgnoreCase(ruleList.getExpression())){
				    	if(ruleList.getValue1() == field){
				    		return true;
				    	}else {
				    		return false;
				    	}
				    } else if("lessthan".equalsIgnoreCase(ruleList.getExpression())){
				    	if(ruleList.getValue1() < field){
				    		return true;
				    	}else {
				    		return false;
				    	}
				    } else if("lessthanequalsto".equalsIgnoreCase(ruleList.getExpression())){
				    	if(ruleList.getValue1() <= field){
				    		return true;
				    	}else {
				    		return false; 
				    	}
				    }else if("greterthanequalto".equalsIgnoreCase(ruleList.getExpression())){
			    		if(ruleList.getValue1() >= field){
			    			return true;
			    		} else {
			    			return false;
			    		}
				    } else if("between".equalsIgnoreCase(ruleList.getExpression())){
				    	if(ruleList.getValue2() >= field && ruleList.getValue1() <= field){
				    		return true;
				    	}else {
				    		return false;
				    	}
				    } 
				}
			return flag;
		}//end
		
	//for RuleResult - getting template data for validation  02-12-2022
		public Map<String, Object> getWfInboxRefObjTemplateDetails(Map<String, Object> wfDetails, RuleList ruleData){
			Map<String, Object> reponseData = null;
			try{
				if(wfDetails.containsKey(WfConstants.WF_OBJECT_REF)){
					Map<String, Object> queryDetails = (Map<String, Object>) wfDetails.get(WfConstants.WF_OBJECT_REF);
					if(queryDetails.containsKey(WfConstants.WF_OBJECT_REF_COLLECTION)){
						queryDetails.put(WfConstants.WF_OBJECT_REF_ID, wfDetails.get(WfConstants.WF_OBJECT_REF_ID));
//						 reponseData = formTemplateDao.getWfInboxRefObjTemplateDetails(queryDetails);
						if(reponseData != null){
							return reponseData;
						}
					}
				}
			} catch (Exception e) { 
				e.printStackTrace();
			}
			return reponseData;
		}
		//end

//	/* Initiating Form WorkFlow Based on FORM Status*/
	public boolean intiateFormWorkFlow(WfEvent wfEvent, GenericRequestHeaders requestHeaders, String formStatus) {
		boolean flag = true;
		try {

			WfInboxMaster wfInboxMaster = wfDao.getWfInboxMasterByRefId(wfEvent.getObjectRefId(),
					requestHeaders.getOrgId());
			if (wfInboxMaster == null) {
				if (wfEvent != null && wfEvent.getWfId() != null)
					wfEvent.setWfShortId(wfDao.getWfShortIdByWfId(wfEvent.getWfId()));
				WfCommonModel workFlowCommonModel = new WfCommonModel();
				workFlowCommonModel.setLoggedUser(requestHeaders.getLoggedUser());
				workFlowCommonModel.setWorkFlowEvent(wfEvent);
				workFlowCommonModel.setItemId(wfEvent.getObjectRefId());
				if(wfEvent.getOrderId() != null) {
					workFlowCommonModel.setOrderId(wfEvent.getOrderId());
				}
				workFlowCommonModel.setGenericRequestHeaders(requestHeaders);
				WfResponse response = this.startEvent(workFlowCommonModel);
				if (response.getStatus() == 0)
					flag = false;
			} else if (wfInboxMaster != null)// && wfInboxMaster.getWfInbox() != null
				flag = this.updateRevisedFormWf(wfEvent, requestHeaders, formStatus);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

//	
//	public boolean updateInboxByObjectRef(GenericRequestHeaders requestHeaders, String objectRefId, String formStatus) {
//		boolean isUpdated = false;
//		try {
//			WfInboxMaster wfInboxMaster = wfDao.getWfInboxMasterByRefId(objectRefId, requestHeaders.getOrgId());
//			if (wfInboxMaster != null) {
//				for (WfInbox inbox : wfInboxMaster.getWfInbox()) {
//					if (Arrays.asList(WfConstants.PENDING, WfConstants.REVISED, WfConstants.REJECTED)
//							.contains(inbox.getStatus())) {
//						WfCommonModel wfModel = new WfCommonModel();
//						WfEvent wfEvent = new WfEvent();
//						wfEvent.setActStatus(formStatus);
//						wfEvent.setWfInboxMasterId(wfInboxMaster.getInboxMasterId());
//						wfEvent.setWfInboxId(inbox.getWfInboxId());
//						wfModel.setWorkFlowEvent(wfEvent);
//						wfModel.setLoggedUser(requestHeaders.getLoggedUser());
//						wfDao.updateInbox(wfModel);
//					}
//				}
//			}
//			isUpdated = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return isUpdated;
//	}
//	
	private List<String> getRoleShortIdbyUserId(WfCommonModel workFlowCommonModel) {
		String initiatorUserId = workFlowCommonModel.getWorkFlowEvent().getInitiatorId();
		List<String> userIdsList = new ArrayList<String>();
		userIdsList.add(initiatorUserId);
		return wfDao.getRoleShortIdsByUserIds(userIdsList, workFlowCommonModel.getGenericRequestHeaders());
	}
//	//for RuleResult - getting template data for validation  02-12-2022
//	public Map<String, Object> getWfInboxRefObjTemplateDetails(Map<String, Object> wfDetails, RuleList ruleData){
//		Map<String, Object> reponseData = null;
//		try{
//			if(wfDetails.containsKey(WfConstants.WF_OBJECT_REF)){
//				Map<String, Object> queryDetails = (Map<String, Object>) wfDetails.get(WfConstants.WF_OBJECT_REF);
//				if(queryDetails.containsKey(WfConstants.WF_OBJECT_REF_COLLECTION)){
//					queryDetails.put(WfConstants.WF_OBJECT_REF_ID, wfDetails.get(WfConstants.WF_OBJECT_REF_ID));
//					 reponseData = formTemplateDao.getWfInboxRefObjTemplateDetails(queryDetails);
//					if(reponseData != null){
//						return reponseData;
//					}
//				}
//			}
//		} catch (Exception e) { 
//			e.printStackTrace();
//		}
//		return reponseData;
//	}
//	//end
//	//start RuleResult - validation on rules 02-12-2022
//	private boolean executeExpression(RuleList ruleList,Object value) {
//		// TODO Auto-generated method stub
//		boolean flag = false;
//		double field = 0;
//		String strField;
//		
//		
//		if(value instanceof Double){
//			field = (double) value;
//		}else if(value instanceof String){
//			field = Double.parseDouble((String) value);
//		}
//		
//		
//		if (ruleList != null) {
//			if("greterthan".equalsIgnoreCase(ruleList.getExpression())){
//			    if (ruleList.getValue1() > field) { 	
//				    return true;
//				    } else {
//				    	return false;
//				    }
//			    } else if("equalsto".equalsIgnoreCase(ruleList.getExpression())){
//			    	if(ruleList.getValue1() == field){
//			    		return true;
//			    	}else {
//			    		return false;
//			    	}
//			    } else if("lessthan".equalsIgnoreCase(ruleList.getExpression())){
//			    	if(ruleList.getValue1() < field){
//			    		return true;
//			    	}else {
//			    		return false;
//			    	}
//			    } else if("lessthanequalsto".equalsIgnoreCase(ruleList.getExpression())){
//			    	if(ruleList.getValue1() <= field){
//			    		return true;
//			    	}else {
//			    		return false; 
//			    	}
//			    }else if("greterthanequalto".equalsIgnoreCase(ruleList.getExpression())){
//		    		if(ruleList.getValue1() >= field){
//		    			return true;
//		    		} else {
//		    			return false;
//		    		}
//			    } else if("between".equalsIgnoreCase(ruleList.getExpression())){
//			    	if(ruleList.getValue2() >= field && ruleList.getValue1() <= field){
//			    		return true;
//			    	}else {
//			    		return false;
//			    	}
//			    } 
//			}
//		return flag;
//	}//end
//	
//	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
//	@Override
//	public GenericApiResponse updateWorkFlow(@NotNull WfUpdatePayload payload,GenericRequestHeaders requestHeaders) {
//		String METHOD_NAME="wfInboxMasterUpdate";
//		logger.info("Enter into : "+METHOD_NAME);
//		GenericApiResponse apiResponse = new GenericApiResponse();
//		List<Map> wfDetails = new ArrayList<Map>();
//		List<String> objectRefIdsList = new ArrayList<String>();
//		Map wfData = new HashMap();
//		String note = null;
//		boolean flag = false;
//		try {
//			if (payload != null && requestHeaders != null) {
//				if (GeneralConstants.WF_CHANGE.equalsIgnoreCase(payload.getAction())) {
//					flag = wfDao.updateWfInboxMaster(payload, requestHeaders);
//					flag = this.changeWorkflow(payload, requestHeaders);
//					if (flag) {
//						note = MessageFormat.format(prop.getProperty("WF_CHANGE_001"),
//								payload.getWfTitle() != null ? payload.getWfTitle() : "");
//						this.addNotesAndNotifications(note, requestHeaders, payload);
//						apiResponse.setStatus(0);
//						apiResponse.setMessage(note);
//					} else {
//						apiResponse.setStatus(1);
//						apiResponse.setMessage(prop.getProperty("WF_CHANGE_002"));
//					}
//				} else if (GeneralConstants.WF_CHANGE_FOR_ALL.equalsIgnoreCase(payload.getAction())) {
//					wfDetails = wfDao.getWfDetails(payload, requestHeaders);
//					if (!wfDetails.isEmpty()) {
//						Map<String, Object> wfMasterData = wfDetails.get(0);
//						List<Map> getWfInboxList = wfDao.updateWfInboxListOfWfIds(payload, requestHeaders);
//						if (!getWfInboxList.isEmpty()) {
//							flag = wfDao.updateWfInboxMaster(payload, requestHeaders);
//							flag = this.changeWorkflow(payload, requestHeaders);
//							if (!wfMasterData.isEmpty()) {
//								payload.setWfShortId(wfMasterData.get(WfConstants.WF_SHORT_ID) + "");
//								payload.setWfTitle(wfMasterData.get(GeneralConstants.WF_TITLE) + "");
//							}
//							getWfInboxList.stream().forEach(eachwfInbox -> {
//								if (eachwfInbox.containsKey(WfConstants.WF_OBJECT_REF_ID))
//									payload.setOmOrgFormValId(eachwfInbox.get(WfConstants.WF_OBJECT_REF_ID) + "");
//								if (payload.getOmOrgFormValId() != null)
//									changeWorkflow(payload, requestHeaders);
//							});
//							flag = true;
//							if (flag) {
//								note = MessageFormat.format(prop.getProperty("WF_CHANGE_001"),
//										payload.getWfTitle() != null ? payload.getWfTitle() : "");
//								this.addNotesAndNotifications(note, requestHeaders, payload);
//							}
//						}
//					}
//				}
//				if (flag) {
//					apiResponse.setStatus(0);
//					apiResponse.setMessage(note);
//				} else {
//					apiResponse.setStatus(1);
//					apiResponse.setMessage(prop.getProperty("WF_CHANGE_002"));
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error while updating and getting WorkFlow Data");
//		}
//		logger.info("Exit from "+METHOD_NAME);
//		return apiResponse;
//	}
//
//	@SuppressWarnings({  "unchecked", "rawtypes" })
//	@Override
//	public GenericApiResponse getWfListByModule(@NotNull WfUpdatePayload payload,
//			GenericRequestHeaders requestHeaders) {
//		GenericApiResponse apiResponse = new GenericApiResponse();
//		List<Map> wfDetails = new ArrayList<Map>();
//		Map wfData = new HashMap();
//		try {
//			wfDetails = wfDao.getWfDetails(payload, requestHeaders);
//			if (!wfDetails.isEmpty()) {
//				long count = wfDetails.size();
//				wfData.put("wfMasterDetails", wfDetails);
//				wfData.put("wfCount", count);
//				apiResponse.setStatus(0);
//				apiResponse.setData(wfData);
//				apiResponse.setMessage(prop.getProperty("WF_GET_DATA_001"));
//			} else {
//				apiResponse.setStatus(1);
//				apiResponse.setMessage(prop.getProperty("WF_GET_DATA_002"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return apiResponse;
//	}
//	
//	@SuppressWarnings("rawtypes")
//	private boolean changeWorkflow(@NotNull WfUpdatePayload payload, GenericRequestHeaders requestHeaders) {
//		String METHOD_NAME = "changeWorkflow";
//		logger.info("Enter into : " + METHOD_NAME);
//		boolean flag = false;
//		Map<String, Object> vendorPartInviteDet = new HashMap<String, Object>();
//		FromTemplateSubmitPayload formSubmitPayload = new FromTemplateSubmitPayload();
//		Map<String, Object> formValuesMap = new HashMap<String, Object>();
//		Map<String, Object> formTemplate = new HashMap<String, Object>();
//		FormTempCommonModel formCommonModel = new FormTempCommonModel();
//		Map collectionData = new HashMap();
//		try {
//			if (payload != null) {
//				String invitationId = null;
//				collectionData = notificationDaoImpl.getDataByValId(payload.getOmOrgFormValId(), payload.getCollection());
//				if (!collectionData.isEmpty()) {
//					if (collectionData.containsKey(GeneralConstants.INVITATION_ID)) {
//						invitationId = collectionData.get(GeneralConstants.INVITATION_ID) + "";
//						requestHeaders.setPartnerType(payload.getModule());
//						vendorPartInviteDet = formTemplateDaoImpl.getVendorInviteDet(invitationId, requestHeaders);
//						if (!vendorPartInviteDet.isEmpty())
//							vendorPartInviteDet.put(GeneralConstants.WF_ID, payload.getWfId());
//						formSubmitPayload.setInviteInfoData(vendorPartInviteDet);
//						formSubmitPayload.setInvitationId(invitationId);
//					} else if (GeneralConstants.WF_RFQ.equalsIgnoreCase(payload.getModule())) {
//						formSubmitPayload.setReqFrom(GeneralConstants.CREATE_RFQ);
//						formValuesMap.put("rfqData", collectionData);
//					}
//					payload.setOmOrgFormId(collectionData.get(GeneralConstants.OMORG_FORM_TEMPLATE_ID) + "");
//				}
//				formSubmitPayload.setStatus(GeneralConstants.FORM_SUBMITTED);
//				formCommonModel.setRequestHeaders(requestHeaders);
//				formCommonModel.setFormTemplate(formValuesMap);
//				formTemplate.put(GeneralConstants.FORM_TEMP_WF_SHORTID, payload.getWfShortId());
//				formCommonModel.setFormTemplate(formTemplate);
//				formCommonModel.setFormSubmitPayload(formSubmitPayload);
//				WfEvent wfEvent = new WfEvent();
//				wfEvent.setWfId(payload.getWfId());
//				wfEvent.setWfShortId(payload.getWfShortId());
//				wfEvent.setWfType(payload.getWfType());
//				wfEvent.setObjectRefId(payload.getOmOrgFormValId());
//
//				flag = formTemplateProcSerImpl.assignWf(formCommonModel, wfEvent, formValuesMap);
//				if (flag) {
//					if (!vendorPartInviteDet.isEmpty())
//					flag = wfDao.updateInvitationData(vendorPartInviteDet, payload, collectionData);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error while changing WorkFlow");
//		}
//		logger.info("Exit from " + METHOD_NAME);
//		return flag;
//	}
//
//	@SuppressWarnings({ "unused", "rawtypes" })
//	private void addNotesAndNotifications(String note, GenericRequestHeaders requestHeaders,
//			@NotNull WfUpdatePayload payload) {
//		boolean flag=false;
//		
//		// Add Notes
//		flag = formTemplateDaoImpl.createNotes(requestHeaders,
//				payload.getOmOrgFormId(),
//				payload.getOmOrgFormValId(), note);
//
//		// Add Notification
//		Notifications notificationCommonModal = new Notifications();
//		notificationCommonModal.setOrgId(requestHeaders.getOrgId());
//		notificationCommonModal.setUserId(requestHeaders.getLoggedUser().getUserId());
//		notificationCommonModal.setOrgOpuId(requestHeaders.getOrgOpuId());
//		notificationCommonModal.setNotification_Message(note);
//		notificationCommonModal.setNotificationGroup(payload.getModule());
//		notificationCommonModal.setObjectRefId(payload.getOmOrgFormValId());
//		notificationCommonModal
//				.setOrgFormId(payload.getOmOrgFormId());
//		notificationDaoImpl.createNotifications(notificationCommonModal);
//	}
//
//	public GenericApiResponse updateWorkItem(Map payload, GenericRequestHeaders requestHeaders) {
//		GenericApiResponse apiResponse = new GenericApiResponse();
//		try{
//			List<WfInboxMaster> data = wfDao.updateWorkItem(payload,requestHeaders);
//			if(data  != null){
//				apiResponse.setData(data);
//				apiResponse.setStatus(0);
//				apiResponse.setMessage("Work item transferred successfully");
//			}else{
//				//apiResponse.setMessage("Work item could not be transferred");
//				apiResponse.setMessage(prop.getProperty("WF_005"));
//				apiResponse.setErrorcode("WF_005");
//				apiResponse.setStatus(1);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return apiResponse;
//	}

	@Override
	public Map<String, Object> getWorkFlowInboxObjRefDetails(WfCommonModel workFlowCommonModel,
			GenericRequestHeaders requestHeaders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericApiResponse getWfStatus(GenericRequestHeaders requestHeaders, @NotNull WfListViewModel payload) throws JsonMappingException, JsonProcessingException {
		GenericApiResponse response=new GenericApiResponse();
		WfStatusModel wfStatusModel = this.getWfRefData(payload.getWfId(),requestHeaders,payload.getDocumentMasterId());
		ObjectMapper objectMapper = new ObjectMapper();
//        List<WfStatuses> readValue = objectMapper.readValue(wfRefData, new TypeReference<List<WfStatuses>>() {});
//        response.setData(readValue);
		response.setData(wfStatusModel);
        response.setMessage("Requested operation completed successfully.");
        response.setStatus(0);
		return response;
	}
	
	private WfStatusModel getWfRefData(String wfId, GenericRequestHeaders requestHeaders, String objectRefId) {
		GenericApiResponse response=new GenericApiResponse();
		WfStatusModel wfStatusResponse = new WfStatusModel();
		try {
			
			WfCommonModel wfCommonModel = new WfCommonModel();
			wfCommonModel.setGenericRequestHeaders(commonService.getRequestHeaders());
			wfCommonModel.setLoggedUser(wfCommonModel.getGenericRequestHeaders().getLoggedUser());
//			wfCommonModel.setDetailView("detailView");
			WfEvent wfEvent = new WfEvent();
			wfEvent.setObjectRefId(objectRefId);
			wfCommonModel.setWorkFlowEvent(wfEvent);
//			WfInboxResponse wfInboxResponse = wfDao.getWfInboxByUser(wfCommonModel, null);
			WfInboxResponse wfInboxResponse = wfDao.getWfInboxByUserForApprover(wfId, objectRefId);
			List<WfInboxDetails> inboxList = wfInboxResponse.getWfInboxData();
			if (inboxList != null && !inboxList.isEmpty()) {
				for (WfInboxDetails inboxlevel : inboxList) {
					if (WfConstants.PENDING.equalsIgnoreCase(inboxlevel.getStatus())) {
						wfStatusResponse.setWfInboxDetails(inboxlevel);
						break;
					}
				}
			}
			
			
		WfInboxMaster wfInboxMaster = wfInboxMasterRepo.getWfInboxMasterDetailsOnWfId(wfId,objectRefId);
		if(wfInboxMaster != null) {
			List<WfInboxMasterDetails> wfInboxMasterDetailsList = wfInboxMasterDetailsRepo.getInboxMasterDetails(wfInboxMaster.getInboxMasterId());
			if(wfInboxMasterDetailsList != null && !wfInboxMasterDetailsList.isEmpty()) {
				
				List<DocumentWfStatus> wfStatusList = new ArrayList<DocumentWfStatus>();
				DocumentWfStatus docInfo = new DocumentWfStatus();
				
				Map<String, UmOrgRoles> rolesMap = new HashMap<String, UmOrgRoles>();
				/* Retrieving all the existing roles and processing to map */
				List<UmOrgRoles> umOrgRoles = umOrgRolesRepo.findAllUmOrgRoles();
				umOrgRoles.forEach(role -> {
					rolesMap.put(role.getRoleShortId(), role);
				});
				
				WfMaster wfMaster = wfMasterRepo.getWfMasterDataByWfId(wfId);
				String wfStatus="";
				if(wfMaster != null) {
					
					 String encodedString = wfMaster.getProcessMetaData(); // Base64 encoded string
					 
					 byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
						String decodedJson = new String(decodedBytes);
						ObjectMapper objectMapper = new ObjectMapper();
						Map wfMap = objectMapper.readValue(decodedJson, new TypeReference<Map>() {
						});
						
						List<Map<String, Object>> wfUserTasks = (List<Map<String, Object>>) wfMap.get("userTask");
						
						Map<String, WfInboxMasterDetails> wfInboxMap = new HashMap<String, WfInboxMasterDetails>();
						if (wfInboxMaster != null) {
							wfInboxMasterDetailsList.forEach((wfInboxMasterDetails) -> {
								wfInboxMap.put(wfInboxMasterDetails.getTargetRef(), wfInboxMasterDetails);
							});
							
							DocumentWfStatus wfStage = new DocumentWfStatus();
							wfStage.setWfStageDesc("Initial Submit");
							wfStage.setWfStatus(WfConstants.COMPLETED);
							if (!wfInboxMasterDetailsList.isEmpty()) {
								WfInboxMasterDetails wfInbox = wfInboxMasterDetailsList.get(0);
								//UmUsers umUser = customMongoTemplate.findByCusId(wfInbox.getCreatedBy(), UmUsers.class);
								//List<UmUsers> umUsers = users.stream().filter(userDet -> userDet.getUserId().equalsIgnoreCase(wfInbox.getCreatedBy())).collect(Collectors.toList());
								//if (umUsers != null && !umUsers.isEmpty() && umUsers.size() == 1)
								//	wfStage.setWfLevelInfo(CommonUtils.formattedName(umUsers.get(0).getFirstName(), umUsers.get(0).getLastName()));
								wfStage.setActionTakenDt(wfInbox.getCreatedTs());
							}
							wfStatusList.add(wfStage);
							
							/* Initiator Level Stage Adding in work flow status bar level */
							if ("true".equalsIgnoreCase(wfMaster.getInitiatorFlag() + "")) {
								wfStage = new DocumentWfStatus();
								wfStage.setWfStageDesc("Initiator Approval");
								if (wfInboxMap.get(WfConstants.INITIATOR) != null) {
									WfInboxMasterDetails wfInbox = wfInboxMap.get(WfConstants.INITIATOR);
									wfStage.setWfStatus(wfInbox.getStatus());
									wfStage.setActionTakenDt(wfInbox.getChangedTs());
									docInfo.setRefDocData(wfInbox.getRefDocData());
									if (!WfConstants.PENDING.equalsIgnoreCase(wfInbox.getStatus())) {
//										this.updatePoFormStatusDesc(requestHeaders, wfStage, docInfo, formCommonModel);
									} else {
										docInfo.setWfStageDesc("Waiting for Acceptence");
										docInfo.setWfStatus(wfStage.getWfStatus());
									}
									List<UmUsers> userData = umUsersRepository.getUserByUserId(Integer.parseInt(wfInbox.getRefToUsers()),false);

									if (userData != null && !userData.isEmpty()) {
										LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
										approverInfo.put("approver",
												CommonUtils.formattedName(userData.get(0).getFirstName(), userData.get(0).getLastName()));
										approverInfo.put("status", wfInbox.getStatus());
										List<LinkedHashMap<String, Object>> approversList = new ArrayList<LinkedHashMap<String, Object>>();
										approversList.add(approverInfo);
										wfStage.setWfLevelInfo(approverInfo.get("approver") + "");
										wfStage.setWfStageApprovers(approversList);
									}
								} else {
									wfStage.setWfStatus(WfConstants.PENDING);
									wfStage.setWfLevelInfo("");
								}
								wfStatusList.add(wfStage);
							}
							
							if(wfUserTasks!=null) {
								for (Map<String, Object> userTask : wfUserTasks) {
									if ("DEFAULT".equalsIgnoreCase(userTask.get("type") + ""))
										continue;
									wfStage = new DocumentWfStatus();
									wfStage.setRefLevelNo(userTask.get("refLevelNo")!=null?(String) userTask.get("refLevelNo"):userTask.get("name") + "");
									wfStage.setWfStageDesc(userTask.get("name") + "");
									wfStage.setApprovalLevel(userTask.get("approvalLevels") + "");
									wfStage.setApprovalType(userTask.get("approvalType") + "");
									wfStage.setWfLevelInfo(userTask.get("candidateGroups") != null && !((List<String>)userTask.get("candidateGroups")).isEmpty() && ((List<String>)userTask.get("candidateGroups")).size() ==1 ? rolesMap.get(((List<String>) userTask.get("candidateGroups")).get(0)).getRoleDesc():"");
									WfInboxMasterDetails wfInbox = wfInboxMap.get(userTask.get("id") + "");
									
									List<Integer> wfRefToUsersList = new ArrayList<>();
									if(wfInbox != null) {
									List<WfRefToUsers> refToUsersList = wfRefToUsersRepo.getRefToUsersList(wfInbox.getWfInboxId());
									if(!refToUsersList.isEmpty()) {
										for(WfRefToUsers ref : refToUsersList) {
											wfRefToUsersList.add(ref.getRefToUsers());
										}
									}
								}
									
									List<Integer> userInboxIdList = new ArrayList<>();
									List<WfUserInbox> wfUserInboxList = new ArrayList();
									if(wfInbox != null) {
									 wfUserInboxList = wfUserInboxRepo.getWfUserInboxOnWfInbId(wfInbox.getWfInboxId());
									}
									
									
									/** Adding delegation user to Work flow */
									if("USERID".equalsIgnoreCase(userTask.get("approvalType")+"")) {
//										this.assignDelegationUser(requestHeaders,userTask);
									}
									if (wfInbox != null) {
//										wfStage.setFrmEditable(wfInbox.isFrmEditable());
										wfStage.setWfStatus(wfInbox.getStatus());
										wfStage.setActionTakenDt(wfInbox.getChangedTs());
										/** Adding last approver Name to Work flow */
											if("APPROVED".equalsIgnoreCase(wfInbox.getStatus()) && wfInbox.getChangedBy() != null 
													&& ("ROLE".equalsIgnoreCase( wfInbox.getApprovalType()) || "USERID".equalsIgnoreCase(wfInbox.getApprovalType()))) {
												List<UmUsers> usersList = umUsersRepository.getUmUsersList(wfRefToUsersList);
//												List<UmUsers> usersList = userDao.getUsersByUserId(Arrays.asList(wfInbox.getUpdatedBy()));
												if(!usersList.isEmpty()) {
													String userName = usersList.get(0).getFirstName() != null ? usersList.get(0).getFirstName() : "" + usersList.get(0).getLastName() != null ? usersList.get(0).getLastName() : "";
													wfStage.setLastApprovedBy("by "+userName);
												}
											}
										docInfo.setRefDocData(wfInbox.getRefDocData());
										if (!WfConstants.PENDING.equalsIgnoreCase(wfInbox.getStatus())) {
//											this.updatePoFormStatusDesc(requestHeaders, wfStage, docInfo, formCommonModel);
										} else {
											docInfo.setWfStageDesc("Waiting for Acceptence");
											docInfo.setWfStatus(wfStage.getWfStatus());
											docInfo.setWfLevelInfo(wfStage.getWfLevelInfo());
										}
									} else
										wfStage.setWfStatus(WfConstants.PENDING);

									/* Approvers Data fetching */
									List<String> roleShortIds = new ArrayList<String>();
									List<Integer> userIds = new ArrayList<Integer>();
									if (userTask.get(WfConstants.CANDIDATE_GROUPS) != null
											&& userTask.get(WfConstants.CANDIDATE_GROUPS) instanceof List)
										roleShortIds = (List<String>) userTask.get(WfConstants.CANDIDATE_GROUPS);
									if (userTask.get(WfConstants.CANDIDATE_USERS) != null
											&& userTask.get(WfConstants.CANDIDATE_USERS) instanceof List)
										userIds = (List<Integer>) userTask.get(WfConstants.CANDIDATE_USERS);
									List<LinkedHashMap<String, Object>> approversList = new ArrayList<LinkedHashMap<String, Object>>();
									String approvalType = userTask.get(WfConstants.APPROVAL_TYPE) + "";
									String approvalLevels = userTask.get(WfConstants.APPROVAL_LEVELS) + "";
									/* Any One Need to approve approvers status */
									if (!"ALL".equalsIgnoreCase(approvalLevels)) {
										if (roleShortIds != null && !roleShortIds.isEmpty() && userIds.isEmpty())
											userIds = wfDao.getUserIdsByRoleShortIds(roleShortIds, requestHeaders);
										String roleShortId = null;
										if(!roleShortIds.isEmpty()) {
											 roleShortId = roleShortIds.get(0);
										}										
										if (userIds != null && !userIds.isEmpty()
												&& ("USERID".equalsIgnoreCase(approvalType)
														|| ("ROLE".equalsIgnoreCase(approvalType)
																&& (wfInbox!= null && "PENDING".equals(wfInbox.getStatus()))))) {
											List<UmUsers> usersList = umUsersRepository.getUmUsersList(userIds);
											for(UmUsers user : usersList) {
												LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
												approverInfo.put("approver", user.getFirstName()!=null?user.getFirstName():"" + " " + user.getLastName()!= null?user.getLastName():"");
												approverInfo.put("status", wfInbox != null ? wfInbox.getStatus() : WfConstants.PENDING);
												approverInfo.put("userId", user.getUserId() != null ? user.getUserId():"");
												approverInfo.put("approvalLevels", wfInbox != null ? wfInbox.getApprovalLevels():"");												
												approverInfo.put("roleShortId", roleShortId);
												approversList.add(approverInfo);
											}
//											usersList.forEach(user -> {
//												LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
//												approverInfo.put("approver", user.getFirstName()!=null?user.getFirstName():"" + " " + user.getLastName()!= null?user.getLastName():"");
//												approverInfo.put("status", wfInbox != null ? wfInbox.getStatus() : WfConstants.PENDING);
//												approverInfo.put("userId", user.getUserId() != null ? user.getUserId():"");
//												approverInfo.put("approvalLevels", wfInbox != null ? wfInbox.getApprovalLevels():"");
//												String roleShortId = roleShortIds.get(0);
//												approverInfo.put("roleShortId", roleShortId);
//												approversList.add(approverInfo);
//											});
										} else {
											if( wfInbox != null && wfInbox.getApprovalType() != null && "INITIATOR".equalsIgnoreCase( wfInbox.getApprovalType())) {
												List<UmUsers> usersList =  umUsersRepository.getUmUsersList(wfRefToUsersList);
												usersList.forEach(user -> {
													LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
													approverInfo.put("approver", user.getFirstName()!=null?user.getFirstName():"" + " " + user.getLastName()!=null?user.getLastName():"");
													approverInfo.put("status", wfInbox != null ? wfInbox.getStatus() : WfConstants.PENDING);
													approverInfo.put("userId", user.getUserId() != null ? user.getUserId():"");
													approverInfo.put("approvalLevels", wfInbox != null ? wfInbox.getApprovalLevels():"");
													approversList.add(approverInfo);
												});
											}else {
												for(String role :roleShortIds) {
													LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
													approverInfo.put("approver", rolesMap.get(role).getRoleDesc());
													approverInfo.put("status", wfInbox != null ? wfInbox.getStatus() : WfConstants.PENDING);
													approverInfo.put("approvalLevels", wfInbox != null ? wfInbox.getApprovalLevels():"");
													approverInfo.put("roleShortId", roleShortId);
													approversList.add(approverInfo);
												}
//												roleShortIds.forEach(role -> {
//													LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
//													approverInfo.put("approver", rolesMap.get(role).getRoleDesc());
//													approverInfo.put("status", wfInbox != null ? wfInbox.getStatus() : WfConstants.PENDING);
//													approverInfo.put("approvalLevels", wfInbox != null ? wfInbox.getApprovalLevels():"");
//													approverInfo.put("roleShortId", roleShortId);
//													approversList.add(approverInfo);
//												});
											}
										}
									} else if ("ALL".equalsIgnoreCase(approvalLevels) && userIds != null) {
										List<UmUsers> usersList = umUsersRepository.getUmUsersList(userIds);
										Map<Integer, UmUsers> usersMap = new HashMap<Integer, UmUsers>();
										usersList.forEach(user -> {
											usersMap.put(user.getUserId(), user);
										});
										if (wfInbox != null && wfUserInboxList != null) {
											wfUserInboxList.forEach(userInbox -> {
												if (usersMap.containsKey(userInbox.getUserId())) {
													LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
													approverInfo.put("approver", usersMap.get(userInbox.getUserId()).getFirstName()!=null?usersMap.get(userInbox.getUserId()).getFirstName():"" + " "	+ usersMap.get(userInbox.getUserId()).getLastName()!=null?usersMap.get(userInbox.getUserId()).getLastName():"");
													approverInfo.put("status", userInbox.getStatus());
													approverInfo.put("userId", userInbox.getUserId());
													approverInfo.put("approvalLevels", wfInbox != null ? wfInbox.getApprovalLevels():"");
													approversList.add(approverInfo);
												}else {
													if( wfInbox != null && wfInbox.getApprovalType() != null && "INITIATOR".equalsIgnoreCase( wfInbox.getApprovalType())) {
														List<UmUsers> initiatorUser =  umUsersRepository.getUmUsersList(wfRefToUsersList);
															LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
															approverInfo.put("approver", initiatorUser.get(0).getFirstName()!=null?initiatorUser.get(0).getFirstName():"" + " " + initiatorUser.get(0).getLastName()!=null?initiatorUser.get(0).getLastName():"");
															approverInfo.put("status", wfInbox != null ? wfInbox.getStatus() : WfConstants.PENDING);
															approverInfo.put("approvalLevels", wfInbox != null ? wfInbox.getApprovalLevels():"");
															approversList.add(approverInfo);
													}
												}
											});
										} 
										else {
											usersList.forEach(user -> {
												LinkedHashMap<String, Object> approverInfo = new LinkedHashMap<String, Object>();
												approverInfo.put("approver", user.getFirstName()!=null?user.getFirstName():"" + " " + user.getLastName()!=null?user.getLastName():"");
												approverInfo.put("status", WfConstants.PENDING);
												approverInfo.put("userId", user.getUserId() != null ? user.getUserId():"");
												approverInfo.put("approvalLevels", wfInbox != null ? wfInbox.getApprovalLevels():"");
												approversList.add(approverInfo);
											});
										}
									}
									wfStage.setWfStageApprovers(approversList);
									if (approversList != null && approversList.size() == 1)
										wfStage.setWfLevelInfo(approversList.get(0).get("approver") + "");
									else
										wfStage.setWfLevelInfo(wfStage.getWfLevelInfo()!=null ?wfStage.getWfLevelInfo():"");
									wfStatusList.add(wfStage);

									/*if (wfInboxMap.get(roleShortId) != null
											&& WfConstants.REJECTED.equalsIgnoreCase(wfInbox.getStatus()))
										break;*/

								}
								}
							
							wfStatusResponse.setWfId(wfMaster.getWfId());
							wfStatusResponse.setWfTitle(wfMaster.getWfTitle());
							wfStatusResponse.setWfShortId(wfMaster.getWfShortId());
							wfStatusResponse.setWfStatus(wfStatusList);
					 
//					    byte[] decodedBytes = Base64.getDecoder().decode(encodedString); // Decoding Base64
//					    wfStatus = new String(decodedBytes, StandardCharsets.UTF_8); // Convert to string using UTF-8 encoding
//					    System.out.println(wfStatus);
				}
			}
		}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
//		String wfStatus="";
//		WfRefrenceData wfRefDetails = refrenceDataRepo.getWfRefDetails(wfId);
//		if(wfRefDetails!=null) {
//			 String encodedString = wfRefDetails.getWfRefMetaData(); // Base64 encoded string
//			    byte[] decodedBytes = Base64.getDecoder().decode(encodedString); // Decoding Base64
//			    wfStatus = new String(decodedBytes, StandardCharsets.UTF_8); // Convert to string using UTF-8 encoding
//			    System.out.println(wfStatus);
//		}
//		return wfStatus;
	return wfStatusResponse;
	}
		
	
	
//	@Override
//	public GenericApiResponse getWfStatus(GenericRequestHeaders requestHeaders, @NotNull WfListViewModel payload) throws JsonMappingException, JsonProcessingException {
//		GenericApiResponse response=new GenericApiResponse();
//		List<String> wfStatus=new ArrayList<String>();
//		WfInboxMaster wfInboxMaster = wfInboxMasterRepo.getWfInboxMaster(payload.getInboxMasterId(),false);
//		List<WfInboxMasterDetails> inboxMasterDetails = wfInboxMasterDetailsRepo.getInboxMasterDetails(payload.getInboxMasterId());
//		for(WfInboxMasterDetails details: inboxMasterDetails) {
//			wfStatus.add(details.getStatus());
//		}
//		String wfRefData = this.getWfRefData(wfInboxMaster.getWfTypeId());
//		ObjectMapper objectMapper = new ObjectMapper();
//        List<WfStatuses> readValue = objectMapper.readValue(wfRefData, new TypeReference<List<WfStatuses>>() {});
//        
//        response.setData(readValue);
//        response.setMessage("Requested operation completed successfully.");
//        response.setStatus(0);
//		return response;
//	}
//	

}
