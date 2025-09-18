 package com.portal.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.basedao.IBaseDao;
import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.ErpClassifieds;
import com.portal.common.service.CommonService;
import com.portal.common.service.DocumentService;
import com.portal.rms.model.RmsApproveModel;
import com.portal.rms.model.RmsModel;
import com.portal.rms.service.RmsService;
import com.portal.security.util.LoggedUserContext;
import com.portal.workflow.model.WfCommonModel;

@Component("WorkflowApprovalHandler")
public class WorkflowApprovalHandler {

//	@Autowired
//	private MongoTemplate mongoTemplate;

//	@Autowired
//	private SettingDao settingDao;
//	
//	@Autowired
//	private SendMessageService sendService;
//	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private RmsService rmsService;
	
	@Autowired
	private LoggedUserContext userContext;
	
//	@Autowired
//	private DMSDocumentMasterRepo dmsDocumentMasterRepo;
	
	@Autowired
	private IBaseDao baseDao;
	
//	@Autowired
//	private DmsService dmsService;
//	
////	@Autowired
////	private CustomMongoTemplate customMongoTemplate;
//	
//	@Autowired
//	private WfDao wfDao;
//	
////	@Autowired
////	private CreateNotificationServiceImpl createNotifiApprovals;
//
//	
//	public boolean handler(WfCommonModel wfCommonModel) {
//		WfDetails wfDetails = wfCommonModel.getWfDetails();
//		if (wfDetails.getObjectRefMap() != null && !wfDetails.getObjectRefMap().isEmpty()
//				&& wfCommonModel.getWorkFlowEvent().getActStatus() != null) {
//			Query query = new Query();
//			query.addCriteria(
//					Criteria.where((String) wfDetails.getObjectRefMap().get(WfConstants.WF_OBJECT_REF_KEY_FIELD))
//							.is(wfCommonModel.getWorkFlowEvent().getObjectRefId()));
//			Update update = new Update();
//			String actionStatus = wfCommonModel.getWorkFlowEvent().getActStatus();
//			if(WfConstants.APPROVED.equalsIgnoreCase(actionStatus) && !wfDetails.getEndEvents().contains(
//					wfDetails.getSeqFlowMap().get(wfCommonModel.getWorkFlowEvent().getSourceRefId())))
//				actionStatus = null; //WfConstants.APPROVAL_INPROGRESS;
//			if (actionStatus != null) {
//				update.set((String) wfDetails.getObjectRefMap().get(WfConstants.WF_OBJECT_REF_KEY_STATUS_FIELD),
//						actionStatus);
//				if(GeneralConstants.PO_SENT_FOR_APPROVAL.equalsIgnoreCase(actionStatus)){
//					update.set(GeneralConstants.CH_SUBMITTEDBY, wfCommonModel.getLoggedUser().getUserId());
//				}
//				update.set(GeneralConstants.CH_CHANGEDBY, wfCommonModel.getLoggedUser().getUserId());
//				update.set(GeneralConstants.CH_CHANGEDTS, new Date());
//				
//				if(Arrays.asList("PUR_ORDER","ASN_FORM").contains(wfDetails.getWfType())) {
//					update.set(GeneralConstants.ERP_ACTIONS_SYNC_STATUS, false);
//				}
//				//Identify is this LAST APPROVAL or not
//				if(Arrays.asList("ASN_FORM").contains(wfDetails.getWfType())) 
//					wfCommonModel.setLastStageApprover(true);
//				UpdateReqModel updtModel = new UpdateReqModel();
//				updtModel.setRequestHeaders(wfCommonModel.getGenericRequestHeaders());
//				updtModel.setQuery(query);
//				updtModel.setUpdate(update);
//				updtModel.setCollectionName(wfDetails.getObjectRefMap().get(WfConstants.WF_OBJECT_REF_COLLECTION)+"");
//				commonService.updateDataBasedOnSetng(updtModel);
//			}
//		}
//		return true;
//	}
//
//	public boolean checkForInitiatorApprovalFlow(WfCommonModel wfCommonModel){
//		boolean flag = false;
//		try{
//			
//			if(wfCommonModel.getWfDetails().getInitiatorFlag().equals("true") && wfCommonModel.getWorkFlowEvent().getInitiatorId() != null){
//				
//				WfInboxMaster inboxMaster = null;
//				inboxMaster = wfDao.getWfInboxMasterByRefId(wfCommonModel.getWorkFlowEvent().getObjectRefId(), wfCommonModel.getGenericRequestHeaders().getOrgId());
//				if(inboxMaster == null) {
//					inboxMaster = new WfInboxMaster();
//					inboxMaster.setCurrentStatus(WfConstants.PENDING);
//					inboxMaster.setOrgId(wfCommonModel.getGenericRequestHeaders().getOrgId());
//					inboxMaster.setOrgOpuId(wfCommonModel.getGenericRequestHeaders().getOrgOpuId());
//					inboxMaster.setWfShortId(wfCommonModel.getWorkFlowEvent().getWfShortId());
//					inboxMaster.setObjectRefId(wfCommonModel.getWorkFlowEvent().getObjectRefId());
//					inboxMaster.setInboxMasterId(UUID.randomUUID().toString());
//					inboxMaster.setWfDesc(wfCommonModel.getWfDetails().getWfDesc());
//					if(wfCommonModel.getWfDetails() != null)
//						inboxMaster.setWfType(wfCommonModel.getWfDetails().getWfType());
//					mongoTemplate.save(inboxMaster);
//				}
//				
//				WfInbox wfInbox = new WfInbox();
//				wfInbox.setWfInboxId(UUID.randomUUID().toString());
//				wfInbox.setExtObjRefId(wfCommonModel.getWorkFlowEvent().getExtObjectRefId());
//				wfInbox.setOrgId(wfCommonModel.getGenericRequestHeaders().getOrgId());
//				wfInbox.setOrgOpuId(wfCommonModel.getGenericRequestHeaders().getOrgOpuId());
//				wfInbox.setTargetRef(wfCommonModel.getWorkFlowEvent().getTargetRefId());
//				wfInbox.setSourceStageRef(wfCommonModel.getWorkFlowEvent().getSourceRefId());
//				
//				
//				Query query = new Query();
//				query.addCriteria(Criteria.where("orgId").is(wfCommonModel.getGenericRequestHeaders().getOrgId())
//						.and("userId").is(wfCommonModel.getWorkFlowEvent().getInitiatorId())
//						.and("markAsDelete").is(false));
//				List<UmOrgUsers> umOrgUserList = mongoTemplate.find(query, UmOrgUsers.class);
//				String roleId = umOrgUserList.get(0).getRoleId();
//				query = new Query();
//				query.addCriteria(Criteria.where("roleId").is(roleId).and("markAsDelete").is(false));
//				List<UmOrgRoles> umOrgRoles = mongoTemplate.find(query, UmOrgRoles.class);
//				List<String> roleShortIds = umOrgRoles.stream().map(UmOrgRoles::getRoleShortId).collect(Collectors.toList());
//				
////				List<Map<String, Object>>  userInboxList = new ArrayList<Map<String, Object>>();
////				Map<String, Object> userInbox = new LinkedHashMap<String, Object>();
////				userInbox.put("userId", wfCommonModel.getWorkFlowEvent().getInitiatorId());
////				userInbox.put("status", WfConstants.PENDING);
////				userInboxList.add(userInbox);		
////
////				wfInbox.setUserInbox(userInboxList);
//				wfInbox.setRefTo(roleShortIds);
//				wfInbox.setRefToType(roleShortIds);
//				wfInbox.setRefToUsers(Arrays.asList(wfCommonModel.getWorkFlowEvent().getInitiatorId()));
//				
//				wfInbox.setRequestedDate(new Date());
//				wfInbox.setStatus(WfConstants.PENDING);
//				wfInbox.setCreatedBy(wfCommonModel.getLoggedUser().getUserId());
//				wfInbox.setRequestRaisedBy(wfCommonModel.getWorkFlowEvent().getRequestRaisedBy());
//				if(wfInbox.getRequestRaisedBy() == null) {
//					wfInbox.setRequestRaisedBy(wfCommonModel.getLoggedUser() != null ?
//							wfCommonModel.getLoggedUser().getFirstName() + " " + wfCommonModel.getLoggedUser().getLastName() : "");
//				}
//				wfInbox.setCreatedTs(new Date());
//				wfInbox.setMarkAsDelete(false);
//				wfInbox.setHistorykey(wfCommonModel.getWorkFlowEvent().getHistoryKey() != null 
//						? wfCommonModel.getWorkFlowEvent().getHistoryKey() : UUID.randomUUID().toString());
//				wfInbox.setRefDocData(wfCommonModel.getWorkFlowEvent().getRefDocData());
//				if(wfCommonModel.getWorkFlowEvent().getVendorId() != null)
//					wfInbox.setVendorId(wfCommonModel.getWorkFlowEvent().getVendorId());
//				if(wfCommonModel.getWorkFlowEvent().getVendorCode() != null)
//					wfInbox.setVendorCode(wfCommonModel.getWorkFlowEvent().getVendorCode());		
//				
//				Query findQuery = new Query();
//				findQuery.addCriteria(Criteria.where("markAsDelete").is(false).and(WfConstants.WF_INBOX_MASTERID)
//						.is(inboxMaster.getInboxMasterId()));
//				Update update = new Update();
//				update.push("wfInbox", wfInbox);
//				mongoTemplate.updateFirst(findQuery, update, WfInboxMaster.class);
//				
//			}
//			
//			flag = true;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	@Async
//	public boolean sendWorkFlowStagesMail(WfCommonModel wfCommonModel) {
//		boolean mailFlag = false;
//		try {			
//			WfEvent wfEvent = wfCommonModel.getWorkFlowEvent();
//			WfDetails wfDetails = wfCommonModel.getWfDetails();
//			Map<String, Object> templateProps = new HashMap<String, Object>();
//			GenericRequestHeaders requestHeaders = wfCommonModel.getGenericRequestHeaders();
//			this.getMailCustomData(wfCommonModel, templateProps);
//			
//			/* Vendor Data Fetching */
//			String vendorCode = wfEvent.getVendorCode() != null ? wfEvent.getVendorCode()
//					: requestHeaders.getVendorCode();
//			if (vendorCode == null || vendorCode.isEmpty()) {
//				vendorCode = templateProps.get(GeneralConstants.VENDOR_CODE) != null
//						? templateProps.get(GeneralConstants.VENDOR_CODE) + "" : null;
//			}
//			if (vendorCode != null && !vendorCode.isEmpty()) {
//				List<VendorDetailsResponse> vendorDataList = commonService.getVendorInfoByVendorCode(requestHeaders,
//						Arrays.asList(vendorCode));
//				if (vendorDataList != null && !vendorDataList.isEmpty() && vendorDataList.get(0).getEmail() != null) {
//					templateProps.put("vendor_contact_name", vendorDataList.get(0).getVendorName());
//					templateProps.put("vendor_email", vendorDataList.get(0).getEmail());
//					templateProps.put("vendor_name", vendorDataList.get(0).getVendorCmpnyName());
//					templateProps.put("vendor_contact_number", vendorDataList.get(0).getMobileNumber());
//				}
//			}
//
//			/* Customer Data Fetching */
//			OmOrganizations orgData = mongoTemplate.findOne(
//					new Query().addCriteria(Criteria.where(GeneralConstants.ORG_ID).is(requestHeaders.getOrgId())
//							.and(GeneralConstants.MARK_AS_DEL).is(false)), OmOrganizations.class);
//			if (orgData != null)
//				templateProps.put("customer_name", orgData.getOrgName());
//			if (requestHeaders.getLoggedUser() != null) {
//				templateProps.put("customer_email_id", requestHeaders.getLoggedUser().getEmail());
//				templateProps.put("customer_contact_number", requestHeaders.getLoggedUser().getMobile());
//				templateProps.put("pre_approver_name", requestHeaders.getLoggedUser().getFirstName());
//			}
//			templateProps.put("ref_data", wfEvent.getRefDocData());
//			if("REVISED".equalsIgnoreCase(wfCommonModel.getFormStatus())){
//				templateProps.put("actStatus", wfCommonModel.getWorkFlowEvent().getActStatus());
//			}
//			templateProps.put("comments",
//					(wfEvent.getActionComments() != null && !wfEvent.getActionComments().isEmpty())
//							? wfEvent.getActionComments() : null);
//			
//			/* Stage wise Configured Mail Templates Fetching and based on stage required data processing*/
//			Map<String, Object> emailTemplates = this.getEmailTemplatesStageWise(wfCommonModel);
//			if (emailTemplates != null && !emailTemplates.isEmpty()) {
//				LinkedHashMap<String, Map<String, Object>> sourceRefVsActor = wfCommonModel.getWfDetails()
//						.getSourceRefVsActor();
//				emailTemplates.entrySet().forEach(template -> {
//					EmailsTo emailTo = new EmailsTo();
//					boolean isApproverOnlyMail = false;
//					emailTo.setOrgId(requestHeaders.getOrgId());
//					emailTo.setBpId(requestHeaders.getOrgOpuId());
//					emailTo.setWfType(wfCommonModel.getWfDetails().getWfType());
//					List<String> userIds = new ArrayList<String>();
//					if (sourceRefVsActor != null && (sourceRefVsActor.get(template.getKey()) != null
//							|| (wfDetails.getCondSeqSrc() != null && sourceRefVsActor
//									.get(wfDetails.getCondSeqSrc().get(template.getKey()) + "") != null))) {
//						
//						String stageKey = sourceRefVsActor.containsKey(template.getKey()) ? template.getKey()
//								: wfDetails.getCondSeqSrc().get(template.getKey()) + "";
//						Map<String, Object> refMap = sourceRefVsActor.get(stageKey);
//						if (refMap.get(WfConstants.CANDIDATE_GROUPS) != null
//								&& refMap.get(WfConstants.CANDIDATE_GROUPS) instanceof List) {
//							List<String> roleShortIds = (List<String>) refMap.get(WfConstants.CANDIDATE_GROUPS);
//							if (roleShortIds.contains(GeneralConstants.DEFAULT_VENDOR_ROLE_SHORT_ID)) {
//								emailTo.setTo(templateProps.get("vendor_email") + "");
//							} else {
//								Query query = new Query();
//								query.addCriteria(
//										Criteria.where("roleShortId").in(roleShortIds).and("markAsDelete").is(false));
//								List<UmOrgRoles> umOrgRoles = mongoTemplate.find(query, UmOrgRoles.class);
//								List<String> roleIds = umOrgRoles.stream().map(UmOrgRoles::getRoleId)
//										.collect(Collectors.toList());
//								query = new Query();
//								query.addCriteria(
//										Criteria.where(GeneralConstants.ORG_ID).is(wfCommonModel.getGenericRequestHeaders().getOrgId())
//												.and("roleId").in(roleIds).and("markAsDelete").is(false));
//								List<UmOrgUsers> umOrgUserList = mongoTemplate.find(query, UmOrgUsers.class);
//								userIds = umOrgUserList.stream().map(UmOrgUsers::getUserId)
//										.collect(Collectors.toList());
//							}
//						} 
//						if (refMap.get(WfConstants.CANDIDATE_USERS) != null
//								&& refMap.get(WfConstants.CANDIDATE_USERS) instanceof List)
//							userIds = (List<String>) refMap.get(WfConstants.CANDIDATE_USERS);
//						if (!userIds.isEmpty()) {
//							isApproverOnlyMail = this.sendEmailToUsers(userIds, orgData, emailTo, template, requestHeaders, wfCommonModel, templateProps);
//						}
//					}else if(template.getKey().equals(WfConstants.INITIATOR)){
//						userIds.add(wfCommonModel.getWorkFlowEvent().getInitiatorId());
//						isApproverOnlyMail = this.sendEmailToUsers(userIds, orgData, emailTo, template, requestHeaders, wfCommonModel, templateProps);
//					}
//					if(!isApproverOnlyMail)
//						this.sendWfEmailCommunication(emailTo, templateProps, template.getValue() + "");	
//					if (VendorANDCustomerANDPurOrderGroupShortIds.getStatushortId(wfCommonModel.getWfDetails().getWfType())!=null &&
//							VendorANDCustomerANDPurOrderStatusShortIds.getStatushortId(wfCommonModel.getWorkFlowEvent().getActStatus())!=null) {
//						templateProps.put("userTo", emailTo.getTo());
//						createNotifiApprovals.createNotificationBasedOnApprovals(wfCommonModel, templateProps);
//					}
//				});
//			}
//			String actionStatus = wfCommonModel.getWorkFlowEvent().getActStatus();
//			if (templateProps.get(GeneralConstants.SUBMITTED_BY) != null && (Arrays
//					.asList(WfConstants.REJECTED, WfConstants.REVISED).contains(actionStatus)
//					|| (WfConstants.APPROVED.equalsIgnoreCase(actionStatus) && wfDetails.getEndEvents().contains(
//							wfDetails.getSeqFlowMap().get(wfCommonModel.getWorkFlowEvent().getSourceRefId()))))
//					&& !Arrays.asList("VENDOR","PUR_ORDER").contains(wfDetails.getWfType())) {
//				
//				EmailsTo emailTo = new EmailsTo();
//				emailTo.setOrgId(requestHeaders.getOrgId());
//				emailTo.setBpId(requestHeaders.getOrgOpuId());
//				UmUsers umUsers = customMongoTemplate.findByCusId(templateProps.get(GeneralConstants.SUBMITTED_BY), UmUsers.class);
//				if(umUsers != null) {
//					if(umUsers.getEmail() != null && !umUsers.getEmail().isEmpty())
//						emailTo.setTo(umUsers.getEmail());
//					else if(!umUsers.getUserTypeIds().contains(GeneralConstants.VEND_USERS)) {
//						emailTo.setTo(orgData.getEmail());
//					}
//					templateProps.put("ref_name", umUsers.getFirstName()+" "+ umUsers.getLastName());
//					templateProps.put("action", actionStatus);
//					String custOrVendorName = orgData.getOrgName();
//					if(GeneralConstants.VENDOR_ROLE_TYPEID == wfCommonModel.getLoggedUser().getUserTypeId())
//						custOrVendorName = templateProps.get("vendor_name")+"";
//					templateProps.put("ref_name_2", custOrVendorName);
//					templateProps.put("type_desc", wfDetails.getWfDesc());
//					templateProps.put("type", wfDetails.getWfType());
//				}
//				if(emailTo.getTo() != null && !emailTo.getTo().isEmpty()) {
//					emailTo.setWfType(wfCommonModel.getWfDetails().getWfType());
//					this.sendWfEmailCommunication(emailTo, templateProps, "INBOX_ACTIONS_DEFAULT_TEMPLATE");
//				}
//				// CREATE NOTIFICATIONS
//				if(wfCommonModel.getWorkFlowEvent().getActStatus() != null) {
//					templateProps.put("userTo", emailTo.getTo());
//					createNotifiApprovals.createNotificationBasedOnApprovals(wfCommonModel,templateProps);
//					}
//			}else {
//				if(WfConstants.APPROVED.equalsIgnoreCase(actionStatus) && wfDetails.getWfType().equalsIgnoreCase("RFQ")) {
//					UmUsers umUsers = customMongoTemplate.findByCusId(templateProps.get(GeneralConstants.SUBMITTED_BY), UmUsers.class);
//					templateProps.put("userTo", umUsers.getEmail());
//					createNotifiApprovals.createNotificationBasedOnApprovals(wfCommonModel,templateProps);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mailFlag;
//	}
//	
//	public boolean sendEmailToUsers(List<String> userIds, OmOrganizations orgData, EmailsTo emailTo,
//			Entry<String, Object> template, GenericRequestHeaders requestHeaders, WfCommonModel wfCommonModel,
//			Map<String, Object> templateProps){
//
//		boolean isApproverOnlyMail = false;
//		/* Commented due to removal of Org email notification. Commented By Harika kancharla 25/8/2022 
//		 * if (orgData != null)
//			emailTo.setTo(orgData.getEmail());   */		
//		List<UmUsers> umUsers = mongoTemplate.find(
//				new Query().addCriteria(
//						Criteria.where("userId").in(userIds).and("markAsDelete").is(false)),
//				UmUsers.class);
//		String[] emails = umUsers.stream().map(UmUsers::getEmail).toArray(n -> new String[n]);
//		if(emails != null){
//			 String toemails = String.join(",", emails);
//			 emailTo.setTo(toemails);  
//		}
//		//emailTo.setCc(emails); Commented due to removal of Org email notification. Commented By Harika kancharla 25/8/2022
//		if(wfCommonModel.getWfDetails().getPreviousWfApproversMails() != null && !wfCommonModel.getWfDetails().getPreviousWfApproversMails().isEmpty())
//			emailTo.setCc(wfCommonModel.getWfDetails().getPreviousWfApproversMails().stream().toArray(String[]::new));
//		
//		// Only Next Approver sending Mail changes
//		if (template.getValue() != null) {
//			Criteria criteria = new Criteria();
//			criteria.orOperator(Criteria.where("orgId").is(null),
//					Criteria.where("orgId").is(requestHeaders.getOrgId())).and("templateShortId")
//					.is(template.getValue());
//			Query templateQuery = new Query().addCriteria(criteria);
//			List<NmEmailTemplates> mailTemplates = mongoTemplate.find(templateQuery,
//					NmEmailTemplates.class);
//			if (mailTemplates != null && !mailTemplates.isEmpty()) {
//				NmEmailTemplates nmtemplate = null;
//				for (NmEmailTemplates nmMailTemplate : mailTemplates) {
//					if (requestHeaders.getOrgId().equalsIgnoreCase(nmMailTemplate.getOrgId()))
//						nmtemplate = nmMailTemplate;
//					if (nmMailTemplate.getOrgId() == null && nmtemplate == null)
//						nmtemplate = nmMailTemplate;
//				}
//				if (nmtemplate != null && nmtemplate.isWfApproversMail()) {
//					isApproverOnlyMail = true;
//					Map<String, UmUsers> usersMap = umUsers.stream()
//							.collect(Collectors.toMap(UmUsers::getEmail, UmUsers -> UmUsers));
//					usersMap.entrySet().forEach(userData -> {
//						emailTo.setTo(userData.getKey());
//						templateProps.put("next_approver_name",
//								userData.getValue().getFirstName() + " "
//										+ (userData.getValue().getLastName() != null
//												? userData.getValue().getLastName() : ""));
//						if (wfCommonModel.getWfDetails().getPreviousWfApproversMails() != null
//								&& !wfCommonModel.getWfDetails().getPreviousWfApproversMails().isEmpty())
//							emailTo.setCc(wfCommonModel.getWfDetails().getPreviousWfApproversMails().stream()
//									.toArray(String[]::new));
//						else
//							emailTo.setCc(null);
//						emailTo.setBcc(null);
//						this.sendWfEmailCommunication(emailTo, templateProps,
//								template.getValue() + "");
//					});
//				}
//				// Submitted User Mail Sending 
//				if (templateProps.get(GeneralConstants.SUBMITTED_BY) != null
//						&& (wfCommonModel.getWfDetails().getEndEvents().contains(wfCommonModel.getWorkFlowEvent().getTargetRefId())
//								|| "SUBMITTED_USER".equalsIgnoreCase(nmtemplate.getMailSendTo()))
//						&& Arrays.asList("PUR_ORDER").contains(wfCommonModel.getWfDetails().getWfType())) {
//					UmUsers userData = mongoTemplate.findOne(
//							new Query().addCriteria(Criteria.where("userId")
//									.is(templateProps.get(GeneralConstants.SUBMITTED_BY) + "").and("markAsDelete").is(false)),
//							UmUsers.class);
//					if(userData != null) 
//						if(userData.getEmail().contains(".")){
//							emailTo.setTo(userData.getEmail());
//						}else{
//							isApproverOnlyMail = true;
//						}
//					emailTo.setCc(null);
//					emailTo.setBcc(null);
//				}
//
//			}
//		}
//		
//		return isApproverOnlyMail;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> getEmailTemplatesStageWise(WfCommonModel wfCommonModel) {
//		Map<String, Object> templates = new HashMap<String, Object>();
//		try {
//			if (wfCommonModel.getWfDetails() != null
//					&& wfCommonModel.getWfDetails().getMailNotificationsMap() != null) {
//				WfEvent wfEvent = wfCommonModel.getWorkFlowEvent();
//				Map<String, Object> notifInfo = wfCommonModel.getWfDetails().getMailNotificationsMap();
//				if (notifInfo.get(WfConstants.WF_MAIL_NOTIF_NEXT_LEVEL) != null
//						&& notifInfo.get(WfConstants.WF_MAIL_NOTIF_NEXT_LEVEL) instanceof List) {
//					List<Map<String, Object>> nextLevelInfo = (List<Map<String, Object>>) notifInfo
//							.get(WfConstants.WF_MAIL_NOTIF_NEXT_LEVEL);
//					nextLevelInfo.forEach(levelInfo -> {
//						if (levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES) != null
//								&& levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES) instanceof List) {
//							List<String> stages = (List<String>) levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES);
//							if (stages != null && stages.contains(wfEvent.getTargetRefId())) {
//								templates.put(wfEvent.getTargetRefId(),
//										levelInfo.get(WfConstants.WF_MAIL_NOTIF_MAIL_TEMP) + "");
//							}
//						}
//					});
//				}
//				if (notifInfo.get(WfConstants.WF_MAIL_NOTIF_CURNT_LEVEL) != null
//						&& notifInfo.get(WfConstants.WF_MAIL_NOTIF_CURNT_LEVEL) instanceof List) {
//					List<Map<String, Object>> nextLevelInfo = (List<Map<String, Object>>) notifInfo
//							.get(WfConstants.WF_MAIL_NOTIF_CURNT_LEVEL);
//					nextLevelInfo.forEach(levelInfo -> {
//						if (levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES) != null
//								&& levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES) instanceof List) {
//							List<String> stages = (List<String>) levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES);
//							if (stages != null && stages.contains(wfEvent.getTargetRefId())) {
//								templates.put(wfEvent.getTargetRefId(),
//										levelInfo.get(WfConstants.WF_MAIL_NOTIF_MAIL_TEMP) + "");
//							}
//						}
//					});
//				}
//				if (notifInfo.get(WfConstants.WF_MAIL_NOTIF_PREV_LEVEL) != null
//						&& notifInfo.get(WfConstants.WF_MAIL_NOTIF_PREV_LEVEL) instanceof List) {
//					List<Map<String, Object>> nextLevelInfo = (List<Map<String, Object>>) notifInfo
//							.get(WfConstants.WF_MAIL_NOTIF_PREV_LEVEL);
//					nextLevelInfo.forEach(levelInfo -> {
//						if (levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES) != null
//								&& levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES) instanceof List) {
//							List<String> stages = (List<String>) levelInfo.get(WfConstants.WF_MAIL_NOTIF_STAGES);
//							if (stages != null && stages.contains(wfEvent.getSourceRefId())) {
//								templates.put(wfEvent.getSourceRefId(),
//										levelInfo.get(WfConstants.WF_MAIL_NOTIF_MAIL_TEMP) + "");
//							}
//						}
//					});
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return templates;
//	}
//	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void getMailCustomData(WfCommonModel wfCommonModel, Map<String, Object> mailTemplateProps) {
//		try {
//			if (wfCommonModel.getWfDetails() != null) {
//				WfDetails wfDetails = wfCommonModel.getWfDetails();
//				if (wfDetails.getObjectRefMap() != null && !wfDetails.getObjectRefMap().isEmpty()) {
//					Query query = new Query();
//					query.addCriteria(Criteria.where(GeneralConstants.ORG_ID).is(wfCommonModel.getGenericRequestHeaders().getOrgId())
//							.and((String) wfDetails.getObjectRefMap().get(WfConstants.WF_OBJECT_REF_KEY_FIELD))
//							.is(wfCommonModel.getWorkFlowEvent().getObjectRefId()).and(GeneralConstants.MARK_AS_DEL).is(false));
//					String collectionName = commonService.getSetingBasedCollectionName(wfCommonModel.getGenericRequestHeaders(), 
//							wfDetails.getObjectRefMap().get(WfConstants.WF_OBJECT_REF_COLLECTION)+"");
//					Map dataMap = mongoTemplate.findOne(query, Map.class, collectionName);
//					if (dataMap != null) {
//						if (dataMap.get(GeneralConstants.VENDOR_CODE) != null)
//							mailTemplateProps.put(GeneralConstants.VENDOR_CODE,
//									dataMap.get(GeneralConstants.VENDOR_CODE) + "");
//						if (wfDetails.getWfType() != null
//								&& "PUR_INVOICE".equalsIgnoreCase(wfCommonModel.getWfDetails().getWfType())) {
//							if (dataMap.get("poNums") != null)
//								mailTemplateProps.put("invPoNums", ((List<String>) dataMap.get("poNums")).stream()
//										.collect(Collectors.joining(", ")));
//						}
//						if(dataMap.get("changeHistory") != null) {
//							mailTemplateProps.put(GeneralConstants.SUBMITTED_BY, 
//									((Map<String, Object>)dataMap.get("changeHistory")).get(GeneralConstants.SUBMITTED_BY));
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void sendWfEmailCommunication(EmailsTo emailTo, Map<String, Object> templateProps,
//			String mailTemplateShortId) {
//		try {
//			Map<String, Object> params = new HashMap<>();
//			params.put("stype", SettingType.APP_SETTING.getValue());
//			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP,
//					GeneralConstants.AWS_SET_GP, GeneralConstants.AZURE_SET_GP, GeneralConstants.STORAGE_SET_GP));
//			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
//			Map<String, String> emailConfigs = settingTo.getSettings();
//			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));			
//			templateProps.put(GeneralConstants.EMAIL_SUBJ_EDIT, true);
//			templateProps.put("pcollab_login_url", emailConfigs.get("WEB_URL"));
//			emailTo.setTemplateProps(templateProps);
//			emailTo.setTemplateName(mailTemplateShortId);
////			sendService.sendCommunicationMail(emailTo, emailConfigs);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public boolean handler(WfCommonModel wfCommonModel) {
		boolean flag = false;
		try {
//			String itemId = wfCommonModel.getWorkFlowEvent().getObjectRefId();
////			DMSDocumentMaster docMasterOnMasterId = dmsDocumentMasterRepo.getDocMasterOnMasterId(documentMasterId);
////			docMasterOnMasterId.setDocumentStatus(wfCommonModel.getWorkFlowEvent().getActStatus());
////			baseDao.saveOrUpdate(docMasterOnMasterId);
//			if("APPROVED".equalsIgnoreCase(wfCommonModel.getWorkFlowEvent().getActStatus())) {
//				WfSyncModel wfModel = new WfSyncModel();
//				List<String> itemIds = new ArrayList<>();
//				itemIds.add(itemId);
//				wfModel.setDocumentMasterIds(itemIds);
////				wfModel.setDocumentNumber(docMasterOnMasterId.getDocumentNumder());
////				GenericApiResponse apiResp = dmsService.syncronizeSAPData(commonService.getRequestHeaders(), wfModel);
//			}
//			System.out.println(wfCommonModel);
			
			RmsApproveModel payload = new RmsApproveModel();
			payload.setOrderId(wfCommonModel.getOrderId());
			payload.setItemId(wfCommonModel.getItemId());
			if("WORKFLOW_SKIPPED".equalsIgnoreCase(wfCommonModel.getWorkFlowEvent().getActStatus())){
				payload.setStatus("APPROVED");
			}else {
				payload.setStatus(wfCommonModel.getWorkFlowEvent().getActStatus());
			}			
			rmsService.updateOrderDetailsonStatus(payload);
			RmsModel rmsModel = new RmsModel();
			List<String> orderIds = new ArrayList<>();
			orderIds.add(payload.getOrderId());
			rmsModel.setOrderId(orderIds);
			wfCommonModel.getGenericRequestHeaders().setLoggedUser(wfCommonModel.getLoggedUser());
			rmsService.syncronizeRmsSAPData(wfCommonModel.getGenericRequestHeaders(), rmsModel);
			
//			List<String> orderIds = new ArrayList<String>();
//			orderIds.add(payload.getOrderId());
			BillDeskPaymentResponseModel billDeskPaymentResponseModel = new BillDeskPaymentResponseModel();
			billDeskPaymentResponseModel.setOrderid("SCHEDULE");
			Map<String, ErpClassifieds> rmsOrderDetailsForErp = rmsService.getRmsOrderDetailsForErp(orderIds);
			rmsService.sendRmsMailToCustomer(rmsOrderDetailsForErp, billDeskPaymentResponseModel, userContext.getLoggedUser(), null);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
		
	}
}
