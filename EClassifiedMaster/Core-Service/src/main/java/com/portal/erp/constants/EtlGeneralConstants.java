package com.portal.erp.constants;

public class EtlGeneralConstants {

	//Action Types
		public static final String PO_CREATE = "PO_GEN";
		public static final String PO_UPDATE = "PO_UPD";
		
		public static final String ADMIN_USER = "admin@pcollab.com";
		
		// Setting Group
		public static final String BJ_SETTING_GROUP = "BJ";
			
		// Setting Group Fields
		public static final String BG_JOB_ENABLE = "BG_JOB_ENABLE";
		public static final String BJ_SKIP_ACTIONS = "BJ_SKIP_ACTIONS";
		public static final String BG_JOB_TRIGGER = "BG_JOB_TRIGGER";
		public static final String GENERIC_BG_JOBS_30 = "GENERIC_BG_JOBS_30";
		
		
		//Job Status
		public static final String ACTIVE = "ACTIVE";
		public static final String IN_ACTIVE = "IN_ACTIVE";
		public static final String IN_PROGRESS = "IN_PROGRESS";
		public static final String SUCCESS ="SUCCESS";
		public static final String ERROR = "ERROR";
		public static final String UNABLE_TO_PROCESS = "UNABLE_TO_PROCESS";
		
		//Data Load Types Short ID's
		public static final String PUR_ORDERS = "PUR_ORDERS";
		public static final String PUR_INVOICES = "PUR_INVOICES";
		public static final String VENDOR_ONBOARD = "VENDOR_ONBOARD";
		public static final String MATERIAL_MASTER = "MATERIAL_MASTER";
		public static final String PUR_REQUISION = "PUR_REQUISION";
		public static final String GRN_DL_TEMPLATE = "GRN_DL_TEMPLATE";
		
		//Workflow Types Short ID's
		public static final String WF_PUR_ORDER = "PUR_ORDER";
		public static final String WF_QTY_INSPECTION = "QTY_INSPECTION";
		public static final String WF_RFQ = "RFQ";
		public static final String WF_VENDOR = "VENDOR";
		
		//Purchase Orders Data load key fields  
		public static final String PO_FORM_TEMP_GROUP = "PURCHASE_ORDERS";
		public static final String PO_ORDERS_NODE = "orderDetails";
		public static final String QTY_INSPECTION_NODE = "requestData";
		public static final String CALC_TAX_IND = "calcTaxIndicator";
		public static final String INVOICE_IND = "invoiceIndicator";
		public static final String PO_ORDERS_HEADER_NODE = "poDetails";
		
		// DB Collection key column's
		public static final String ORG_Id = "orgId";
		public static final String ORG_OPU_ID = "orgOpuId";
		public static final String FORM_TEMPLATE_ID = "templateId";
		public static final String OMORG_FORM_TEMPLATE_ID = "omOrgFormId";
		public static final String OMORG_FORM_VAL_ID = "omOrgFormValId";
		public static final String VENDOR_ID = "vendorId";
		public static final String PO_NUMBER = "poNum";
		public static final String INSPECTIONLOT_NO = "inspectionlotNo";
		
		public static final String PO_NUMBERS = "poNums";
		public static final String MARK_AS_DEL = "markAsDelete";
		public static final String STATUS = "status";
		public static final String INV_NUMBER = "invNum";
		public static final String FORM_TEMP_WF_SHORTID = "wfShortId";
		public static final String WF_OBJREFID = "objectRefId";
		public static final String WF_REQBY= "requestRaisedBy";
		public static final String WF_REF_DOC_DATA= "refDocData";
		public static final String ERP_SYNC_STATUS = "erpSyncStatus";
		public static final String ERP_FILE_PROCESS_FROMPORTALTOERP = "fileProcessFromPortalToERP";
		public static final String ERP_ACTIONS_SYNC_STATUS = "erpActSyncStatus";
		public static final String CHANGE_HIS_CREATED_TS = "changeHistory.createdTs";
		public static final String CHANGE_HIS_CREATED_BY = "changeHistory.createdBy";
		public static final String CHANGE_HIS_CHANGED_TS = "changeHistory.changedTs";
		public static final String CHANGE_HIS_CHANGED_BY = "changeHistory.changedBy";
		public static final String DOC_DL_JOB_ID = "dlJobId";
		public static final String DOC_SOURCE = "docSource";
		public static final String ERP_SYNC_MSG = "erpSyncMsg";
		public static final String ERP_SYNC_VENDOR_REF_ID = "vendorRefId";
		public static final String ERP_SYNC_INV_REF_ID = "invRefId";
		public static final String ERP_SYNC_RFQ_REF_ID = "rfqRefId";
		public static final String VENDOR_REG_TYPE = "regType";
		public static final String ERP_SYNC_PO_REF_ID = "poRefId";
		public static final String ERP_ACTION_SYNC_STATUS = "erpActSyncStatus";
		public static final String ERP_INV_STATUS_CODE = "erpInvStatusCode";
		public static final String ERP_INV_STATUS_DESC = "erpInvStatusDesc";
		public static final String ERP_SYNC_PARTNER_REF_ID = "docRefId";
		public static final String ERP_SYNC_PARTNER_TYPE = "partnerType";
		public static final String PARTNER_ID = "partnerId";

		
		//Purchase Invoice Data load key fields  
		public static final String PI_FORM_TEMP_GROUP = "PURCHASE_INVOICES";
		public static final String PUR_INV_NODE = "invoiceDetails";
		
		//Vendor Onboard Data load key fields  
		public static final String VEND_ONBOARD_FORM_TEMP_GROUP = "VENDOR_REGISTRATION";
		public static final String CUSTOMER_ONBOARD_FORM_TEMP_GROUP = "CUSTOMER_REGISTRATION";
		public static final String VEND_DATA_NODE = "vendorDetails";
		public static final String VENDOR_CODE = "vendorCode";
		public static final String COMPANY_NAME = "companyName";
		public static final String COMPANY_CODE = "companyCode";
		public static final String PARTNER_CODE = "partnerCode";
		public static final String PARTNER_DATA_NODE = "partnerDetails";
		
		
		//Material Master Data load key fields  
		public static final String MATERIAL_DATA = "materialData";
		public static final String MATERIAL_DETAILS = "materialDetails";
		public static final String MATERIAL_CODE = "material";
		public static final String MATERIAL_ID =  "materialId";
		public static final String MATERIAL_DATA_NODE = "materialData";
		
		public enum SettingType {

			APP_SETTING(1), ORG_SETTING(2), BP_SETTING(3), ORG_BP_SETTING(4);

			private int value;

			SettingType(int value) {
				this.value = value;
			}

			public int getValue() {
				return value;
			}
		}
		
		public static final String APP_CORE_SERVICE_URL = "API_CORE_SERVICE_URL";
		
		/* settings groups */
		public static final String EMAIL_SET_GP = "SMTP";
		public static final String IPW_SETTINGS = "IPW";
		public static final String USER_SETTINGS = "USER";
		public static final String LOGON_RETRIES = "LOGON_RETRIES";
		public static final String ACCOUNT_UNLOCK_HOURS = "ACCOUNT_UNLOCK_HOURS";
		public static final String IP_DESKTOP_APPS = "IP_DESKTOP_APPS";
		public static final String PWD_EXPIRE_DAYS = "PWD_EXPIRE_DAYS";
		public static final String ENV_SET_GP = "ENV";
		public static final String AWS_SET_GP = "AWS_S3";
		
		/* Types Of Data load Requested Data */
		public static final String CSV_DATA = "CSV_DATA";
		public static final String ERP_DATA = "ERP_DATA";
		public static final String ERP_API_DATA = "ERP_API_DATA";
		
		
		/* FTP */
		public static final String FTP = "FTP";
		public static final String FTP_ORG_CONNECTION = "FTP_ORG_CONNECTION";
		public static final String FTP_S3_STORAGE = "FTP_S3_STORAGE";
		public static final String FTP_LOCAL = "FTP_LOCAL";
		public static final String FTP_SERVER = "FTP_SERVER";
		public static final String FTP_DL_USER_TOKEN = "FTP_DL_USER_TOKEN";
		public static final String PURORDER_DATA = "PURORDER_DATA";
		public static final String PURINV_DATA = "PURINV_DATA";
		public static final String VEND_REG_DATA = "VEND_REG_DATA";
		public static final String ATTACHMENTS = "attachments";
		
		public static final String CLIENTID = "inc";
		public static final String CLIENTPASSWORD = "inc";
		
		public static final String NON_PO_INVOICE = "NON-PO-INVOICE";
		
		/* Document Status */
		public static final String DOC_PENDING = "PENDING";
		public static final String DOC_SAVE_AS_DRAFT = "SAVE_AS_DRAFT";
		public static final String DOC_SUBMITTED = "SUBMITTED";
		public static final String DOC_APPROVED = "APPROVED";
		public static final String DOC_PUBLISH = "PUBLISH";
		
		/* FTP Actions  */
		public static final String VEND_REG_DATA_CREATE = "VEND_REG_DATA_CREATE";
		public static final String PURORDER_DATA_CREATE = "PURORDER_DATA_CREATE";
		public static final String PURORDER_INV_DATA_CREATE_OVERRIDE = "PURORDER_INV_DATA_CREATE_OVERRIDE";
		public static final String QTY_INSPECTION_DATA_CREATE = "QTY_INSPECTION_DATA_CREATE";
		public static final String CREATE_ERP_VENDOR = "CREATE_ERP_VENDOR";
		public static final String MATERIAL_MASTER_CREATE = "MATERIAL_MASTER_CREATE";
		public static final String SERVICE_MASTER_UPLOAD = "SERVICE_MASTER_UPLOAD";
		public static final String CREATE_ERP_PURINVOICE = "CREATE_ERP_PURINVOICE";
		public static final String CREATE_ERP_VENDOR_RESP = "CREATE_ERP_VENDOR_RESP";
		public static final String CREATE_ERP_PURINVOICE_RESP = "CREATE_ERP_PURINVOICE_RESP";
		public static final String CREATE_ERP_NON_PO_INVOICE = "CREATE_ERP_NON_PO_INVOICE";
		public static final String CREATE_ERP_NON_PO_INVOICE_RESP = "CREATE_ERP_NON_PO_INVOICE_RESP";
		public static final String GRN_DATA_CREATE = "GRN_DATA_CREATE";
		public static final String PURORDER_UPDT_ATTCHMENTS = "PURORDER_UPDT_ATTCHMENTS";
		public static final String PUR_REQUISITION_CREATE = "PUR_REQUISITION_CREATE";
		public static final String SER_REQUISITION_CREATE = "SER_REQUISITION_CREATE";
		public static final String RFQ_CREATE = "RFQ_CREATE";
		public static final String CREATE_ERP_RFQ_RESP = "CREATE_ERP_RFQ_RESP";
		public static final String PUR_ORDER_STATUS = "PUR_ORDER_STATUS";
		public static final String PUR_ORDER_STATUS_RESP = "PUR_ORDER_STATUS_RESP";
		public static final String ERP_PUR_INV_STATUS = "ERP_PUR_INV_STATUS";
		public static final String CREATE_ERP_CUSTOMER = "CREATE_ERP_CUSTOMER";
		public static final String PARTNER_REG_DATA_CREATE = "PARTNER_REG_DATA_CREATE";
		public static final String CREATE_ERP_PARTNER_RESP = "CREATE_ERP_PARTNER_RESP";
		
		/* FTP Service Types  */
		public static final String VENDOR_SERVICE_TYPE = "VENDOR";
		public static final String PURORDER_SERVICE_TYPE = "PURORDER";
		public static final String PURORDER_INV_OVERRIDE_SERVICE_TYPE = "PURORDER_INV_OVERRIDE";
		public static final String QTY_INSPECT_CREATE_SERVICE_TYPE = "QTY_INSPECTION";
		public static final String PURINVOICE_SERVICE_TYPE = "PURINVOICE";
		public static final String GRN_SERVICE_TYPE = "GRN";
		public static final String PUR_REQUISITION_SERVICE_TYPE = "PUR_REQUISITION";
		public static final String SER_REQUISITION_SERVICE_TYPE = "SER_REQUISITION";
		public static final String RFQ_SERVICE_TYPE = "RFQ";
		public static final String MATERIAL_SERVICE_TYPE = "MATERIAL";
		public static final String CUSTOMER_SERVICE_TYPE = "CUSTOMER";
		
		public static final String FORM_TEMPLATE_VALUES_NODE = "formValuesNode";
		public static final String FORM_VALUES_QUERY_FIELDS = "formValuesQueryFields";
		public static final String FORM_ATTACHMENTS_LABELS_INFO = "attachmentsLabelsInfo";
		public static final String QUERY_TARGET_COLLECTION = "targetCollection";
		
		/* FTP General Constants */	
		public static final String FTP_TYPE = "ftpType";
		public static final String FTP_SERVICE_TYPE = "serviceType";
		public static final String FTP_SERVICE_ACTION = "action";
		public static final String FTP_SERVICE_REQ_DATA = "requestData";
		public static final String FTP_REQ_ID = "id";
		public static final String FTP_REQ_SECRET = "secret";
		public static final String FTP_FOLDER_PATH = "FTP_FOLDER_PATH";
		public static final String FTP_SERVER_FOLDER_PATH = "FTP_SERVER_FOLDER_PATH";
		public static final String FTP_PARENT_PATH = "FTP_PARENT_PATH";
		public static final String FTP_FOLDER_TYPE = "folderType";
		public static final String FTP_ERP_FOLDER = "erp";
		public static final String FTP_PORTAL_FOLDER = "portal";
		public static final String FTP_DL_KEY_ACT = "dlKeyAct";
		public static final String FTP_DL_FORMAT = "dataFormat";
		public static final String FTP_DL_BATCH_ID = "batchId";
		public static final String FTP_RMS_SERVER_FOLDER_PATH = "FTP_RMS_SERVER_FOLDER_PATH";
		public static final String FTP_RMS_PAYMENTS_SERVER_FOLDER_PATH = "FTP_RMS_PAYMENTS_SERVER_FOLDER_PATH";
		public static final String FTP_RMS_CUSTOMER_FOLDER_PATH = "FTP_RMS_CUSTOMER_FOLDER_PATH";
		
		/* Attachments upload by Info */
		
		public static final String ATTACHMENT_UPLD_BY = "uploadedBy";
		public static final String ATTACHMENT_UPLD_USERID = "userId";
		public static final String ATTACHMENT_UPLD_USERTYPE = "userType";
		public static final String ATTACHMENT_UPLD_USER_TYPE_ID = "userTypeId";
		public static final String ATTACHMENT_UPLD_ROLESHORT_ID = "roleShortId";

		public static final String CUSTOMER_ROLE_SHORTID = "CUSTOMER";
		public static final String VENDOR_ROLE_SHORTID = "VENDOR";
		public static final Integer CUSTOMER_ROLE_TYPEID = 1;
		public static final Integer VENDOR_ROLE_TYPEID = 3;
		public static final String VENDOR_INVITE_ID = "invitationId";
		
		public static final String DL_TYPE_SHORT_ID = "dlTypeShortId";
		
		public static final String ERP_STATUS_INFO = "statusInfo";	
		
		public static final String FROM_COLLECTION_INFO = "collectionInfo";
		public static final String FORM_TEMP_GROUP = "templateGroup";
		public static final String IS_SEQUENCE_GENERATE_ENABLE = "isSequenceGenerateEnable";
		public static final String CARD_SPECFIC_COLL = "cardSpecificCollection";
		public static final String DATA_AUTO_POPULATION_ENABLE = "dataAutoPopulationEnable";
		
		public static final String DUPLICATE_RECORD = "Record is already available";
		public static final String DUPLICATE_RECORD_VENDORUSER = "Record is already available with Requested EmailId";
		public static final String GRN_DUPLICATE_RECORD = "Record is already available and used in Invoice";
		public static final String GRN_VS_PO_RECORD = "PO is not available in the Portal";
		public static final String PO_VS_INV_DUPLICATE_RECORD = "Record is already available and used in Invoice";
		
		/*public static final String VENDOR_TYPE = "vendorType";	
		public static final String VENDOR_TYPE_DESC = "vendorTypeDesc";*/
		
		/* Vendor FTP Sync */
		public static final String DOC_IDS = "docIds";
		public static final String IS_ERP_ORG = "isErpOrg";
		public static final String VENDOR_DETAILS = "vendorDetails";
		public static final String BASIC_DETAILS = "basicDetails";
		public static final String REF_VENDOR_CODE = "refVendorCode";
		public static final String ERP_VENDOR_CODE = "erpVendorCode";
		public static final String ITEM_DETAILS = "itemDetails";
		public static final String PARTNER_DETAILS = "partnerDetails";
		public static final String REF_PARTNER_CODE = "docRefNum";
		
		/* Invoice ERP Sync Fields */
		public static final String INV_MIR7_NUM = "mir7number";
		public static final String INV_MIR7_YEAR = "mir7year";
		public static final String INV_MIR7_DATE = "mir7date";
		
		/* Attachment Data Fields  */
		public static final String FILE_DATA = "fileData";
		public static final String FILE_NAME = "fileName";
		public static final String FILE_TYPE = "fileType";
		public static final String FILE_PATH = "filePath";
		
		/* Attachment File Hosting Types  */
		public static final String ONE_DRIVE = "ONE_DRIVE";
		public static final String BASE64_BYTES = "BASE64_BYTES";
		
		//GRN Data load key fields  
		public static final String GRN_FORM_TEMP_GROUP = "GRN";
		public static final String GR_NUMBER = "grNum";
		public static final String GRN_DETAILS = "grnDetails";
		public static final String GR_DETAILS = "grDetails";
		public static final String GR_NUMBERS = "grNums";
		
		//Data load CSV/FTP Data Actions
		public static final Integer DL_SKIP_UPDATE = 1;
		public static final Integer DL_UPDATE_MODE = 2;
		
		// PR DB Key Columns
		public static final String PR_NUMBER = "prNum";
		public static final String PUR_REQUISITION_NODE = "requisitionDetails";
		public static final String PR_DETAILS = "prDetails";
		public static final String PR_FORM_TEMP_GROUP = "PUR_REQUISITION";
		public static final String SR_FORM_TEMP_GROUP = "SER_REQUISITION";
		
		/* RFQ FTP Sync Key Fields*/
		public static final String RFQ_DATA_NODE = "rfqData";
		public static final String RFQ_DETAILS_NODE = "rfqDetails";
		
		public static final String UNREG_VENDOR = "UNREG_VENDOR";
		
		public static final String UNREG_PARTNER = "UNREG_PARTNER";
		
		public static final String DL_NOTES_CREATEDBY_TEXT = "ERP Sync Process";
		
		/* Application Data Structure Setting ShortIds  */	
		public static final String LINEAR_DATA = "LINEAR";
		public static final String NODES_DATA = "NODES";
		public static final String ALL_STRUCTURE_DATA = "ALL";
		
		public static final String DOC_REF_ID = "docRefId";
		public static final String DOC_REF_NUM = "docNum";
		public static final String PURCHASE_ORDER = "PURCHASE_ORDER";
		public static final String SELECTED_VENDORS = "selectVendors";
		
		public static final String USER_ID = "userId";
		public static final String WF_INVOICE = "INVOICE";
		public static final String CUSTOMER = "CUSTOMER";
		public static final String CLOSURETYPE = "closureType";
		public static final String RFQ_WITH_AWARDING = "RFQ_SYNC_WITH_AWARD";
		
		public static final String[] AWARD_LIST= {"WITHOUTAWARDED","AWARDED","ITEMWISEAWARDED"};
		public static final String VENDOR_ADDRESS_DETAILS = "vendorAddressDetails";
		public static final String PRODUCT_DETAILS = "productDetails";
		public static final String PARTNER_ADDRESS_DETAILS = "partnerAddressDetails";
		public static final String SERVICE_MASTER = "SERVICE_MASTER";
		public static final String RFQ_SYNC_AWARDED_VENDOR = "RFQ_SYNC_AWARDED_VENDOR";
		public static final String GRN_TEMPLATE_GROUP = "GRN";
		
		public static final String QTY_INSPECTION_FORM_TEMP_GROUP = "QTY_INSPECTION";
		public static final String VENDOR_RATING_CREATE = "VEND_RATING_CREATE";
		public static final String VEND_RATING_TEMP_GROUP = "VENDOR_RATINGS";
		public static final String VENDOR_RATING_SERVICE_TYPE = "VENDORRATING";
		public static final String VENODR_RATING_NUMBER = "vendor";
		public static final String VENDOR_RATING_DATA = "vendorRatingDetails";
		public static final String RATING_PERIOD ="ratingPeriod";

		public static final String PAYMENT_HISTORY_SERVICE_TYPE = "PAYMENT_HISTORY";
		public static final String PAYMENT_HISTORY_DATA_CREATE = "PAYMENT_HISTORY_DATA_CREATE";
		public static final String PAYMENT_HISTORY = "PAYMENT_HISTORY";
		public static final String PAYMENT_DOCUMENT_NO = "payDocNum";
		public static final String PAYMENT_HISTORY_DATA = "paymentHistoryDetails";
		
		public static final String PASSWORD_EXP_REM_BEFORE_DAYS = "PASSWORD_EXPIRY_REMINDER_BEFORE_DAYS";
		public static final String USER_PASSWORD_EXPIRES_REMINDER_MAIL = "USER_PASSWORD_EXPIRES_REMINDER_MAIL";
		
		public static final String USER_REGD_DT_ATSAP = "userRegdDateAtSAP";
		public static final String PAY_DOC_DATE = "payDate";
		public static final String PAY_DOC_YEAR = "payYear";
		public static final String PAYMENT_REF_NO = "payRefNum";
		
		//FTP Details
//		public static final String FTP_USERNAME = "eclass";
//		public static final String FTP_PASSWORD = "Jagati@321#";
//		public static final String FTP_HOST = "117.252.80.229";
//		public static final String FTP_HOST_LOCAL = "192.168.1.145";
//		public static final String FTP_PORT = "12";
		//New FTP Details
		public static final String FTP_USERNAME = "sakshiqaftpuser";
		public static final String FTP_PASSWORD = "q8uZYz238gOf";
		public static final String FTP_HOST = "97.74.94.194";
		public static final String FTP_HOST_LOCAL = "192.168.1.145";
		public static final String FTP_PORT = "21";
		
		public static final String PORTAL_ID = "Portal Id";
		public static final String ORDER_NUMBER = "Order Number";
		public static final String TYPE = "Type";
		public static final String ORDER_STATUS = "Order Status";

}
