package com.portal.workflow.model;

public class WfConstants {

	//WF status
	public static String PENDING = "PENDING";
	public static String COMPLETED = "COMPLETED";
	public static String APPROVED = "APPROVED";
	public static String REJECTED = "REJECTED";
	public static String REVISED = "REVISED";
	public static String INITIATE = "INITIATE";
	public static String PUBLISH = "PUBLISH";
	public static String SAVE_AS_DRAFT = "SAVE_AS_DRAFT";
	public static String APPROVAL_INPROGRESS = "APPROVAL_INPROGRESS";
	//for rule result adding new status
	public static String SKIPPED = "SKIPPED";
	public static String WORKFLOW_SKIPPED = "WORKFLOW_SKIPPED";
	
	public static String EXCLUSIVEGATEWAY= "decision";
	
	//WF Events
	public static String START_EVENT = "startEvent";
	public static String INITIATOR = "INITIATOR";
	
	public static String WF_SHORT_ID = "wfShortId";
	public static String SOURCE_REF = "sourceRef";
	public static String TARGET_REF = "targetRef";
	public static String CONDITION_EXPRESSION = "conditionExpression";
	public static String CANDIDATE_GROUPS = "candidateGroups";
	public static String SERVICE_TASK = "serviceTask";
	public static String EXCLUSIVE_GATEWAY = "exclusiveGateway";
	public static String EXCLUSIVE_GATEWAY_ID = "id";
	public static String END_EVENT = "endEvent";
	public static String MAIL_NOTIFICATIONS = "mailNotifications";
	public static String END_EVENT_ID = "id";
	public static String USER_TASK = "userTask";
	public static String SEQUENCE_FLOW = "sequenceFlow";
	public static String WF_SERVICE_TASK_ID = "id";
	public static String WF_SERVICE_TASK_TEXT = "text";
	public static String WF_SERVICE_TASK_CLASS= "class";
	public static String WF_SERVICE_TASK_METHOD= "method";
	public static String WF_OBJECT_REF= "objectRef";
	public static String WF_OBJECT_REF_KEY_FIELD= "objectKeyField";
	public static String WF_OBJECT_REF_KEY_STATUS_FIELD= "objectStatusField";
	public static String WF_OBJECT_REF_COLLECTION= "objectCollection";
	public static String WF_OBJECT_REF_ID = "objectRefId";
	public static String CANDIDATE_USERS = "candidateUsers";
	public static String APPROVAL_TYPE = "approvalType";
	public static String APPROVAL_LEVELS = "approvalLevels";
	public static String WF_MAIL_NOTIF = "mailNotifications";
	public static String WF_MAIL_NOTIF_PREV_LEVEL = "previousLevel";
	public static String WF_MAIL_NOTIF_NEXT_LEVEL = "nextLevel";
	public static String WF_MAIL_NOTIF_CURNT_LEVEL = "currentLevel";
	public static String WF_MAIL_NOTIF_STAGES = "stages";
	public static String WF_MAIL_NOTIF_MAIL_TEMP = "mailTemplate";
	public static String DEFAULT_SERVICE_TASK_TYPE = "DEFAULT";
	public static String DEFAULT_EXTENSION_SERVICE_TASK_TYPE = "DEFAULT_EXTENSION";
	public static String SERVICE_TASKS_LIST = "tasks";
	public static String SERVICE_TASKS_SERIAL_NUMBER = "serviceNo";
	
	//WF Collection Field Constants
	public static String WF_INBOX_MASTERID = "inboxMasterId";
	public static String WF_INBOX_ID = "wfInbox.wfInboxId";
	public static String WF_INBOX_EXT_OBJ_REF_ID = "wfInbox.extObjRefId";
	public static String WF_INB_REF_FROM_TYPE = "refFromType";
	public static String WF_INB_REF_FROM = "refFrom";
	public static String WF_INB_REF_TO_TYPE = "wfInbox.refToType";
	public static String WF_INB_REF_TO = "refTo";
	public static String WF_INB_HISTORY_FLAG = "wfInbox.historyFlag";
	public static String WF_INB_VENDOR_ID = "wfInbox.vendorId";
	public static String WF_INB_VENDOR_CODE = "wfInbox.vendorCode";
	public static String WF_INB_REF_TO_USERS = "wfInbox.refToUsers";
	public static String WF_INB_USER_INBOX = "wfInbox.userInbox";
	public static String WF_INB_USER_INBOX_USERID = "wfInbox.userInbox.userId";
	public static String WF_INB_STATUS="wfInbox.status";
	public static String WF_INB_REQUST_BY="wfInbox.requestedBy";
	public static String WF_INB_REF_DOC_DATA="wfInbox.refDocData";
	public static String WF_INB_CREATED_TS ="wfInbox.createdTs";
	public static String WF_INB_UPDATED_TS="wfInbox.updatedTs";
	public static String WF_INB_WF_DESC="wfInbox.wfDesc";
	public static String WF_INB_REQUST_RAISED_BY="wfInbox.requestRaisedBy";
	public static String WF_INITIATOR_FLAG="initiatorFlag";
	public static String WF_UPDATED_TS ="updatedTs";
	
	//RULE RESULT Constants
	public static String RULE_RESULT = "RULERESULT";
}
