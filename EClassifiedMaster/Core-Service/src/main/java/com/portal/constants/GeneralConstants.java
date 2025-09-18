package com.portal.constants;

public class GeneralConstants {

	/* SSPortal API version */
	public static final String API_VERSION = "v1";

	/* Login retries */
	public static final int MAX_RETRIES = 3;

	/* Device Types for App's */
	public static final int MOB_IOS_APP = 1;
	public static final int MOB_ANDRIOD_APP = 2;
	public static final int TAB_IOS_APP = 3;
	public static final int TAB_ANDRIOD_APP = 4;

	/* Template names */
	public static final String EMAIL_FP_TEM_NAME = "FORGOT_PASSWORD";
	public static final String EMAIL_RESET_PWD_TEM_NAME = "RESET_PASSWORD";
	public static final String EMAIL_ADDUSER_TEM_NAME = "USER_ADDITION";
	public static final String EMAIL_ORG_USER_ADDITION = "ORG_USER_ADDITION";
	public static final String EMAIL_ORG_EXT_USER_ADD = "ORG_EXT_USER_ADD";
	public static final String EMAIL_ADDBP_TEM_NAME = "BP_ADDITION";
	
	
	public static final String USER_ADDITION = "USER_ADDITION";
	public static final String SSP_REG = "SSP_REG";
	
	public static final String USER_ACTIVE = "USER_ACTIVE";
	public static final String SSP_USER_ACTIVE = "SSP_USER_ACTIVE";
	public static final String USER_DE_ACTIVE = "USER_DE_ACTIVE";
	public static final String SSP_VENDOR_REJECTED = "SSP_VENDOR_REJECTED";
	public static final String DEALER_MAS_ACTIVE = "DEALER_MAS_ACTIVE";
	public static final String DEALER_MAS_INACTIVE = "DEALER_MAS_INACTIVE";
	public static final String DEALER_ADDITION = "DEALER_ADDITION";
	public static final String DEALER_GEO_LOCATION_ENABLED = "DEALER_GEO_LOCATION_ENABLED";
	public static final String DEALER_GEO_LOCATION_DISABLED = "DEALER_GEO_LOCATION_DISABLED";
	
	public static final String USER_ADDITION_MAIL_TO_ADMIN = "USER_ADDITION_MAIL_TO_ADMIN";
	public static final String SSP_USER_ADDITION_MAIL_TO_ADMIN = "SSP_USER_ADDITION_MAIL_TO_ADMIN";
	public static final String PAYMENT_APPROVE = "PAYMENT_APPROVE";
	public static final String PAYMENT_REJECT = "PAYMENT_REJECT";
	public static final String CONTENT_REJECT = "CONTENT_REJECT";
	public static final String PAYMENT = "PAYMENT";
	public static final String PAYMENT_AGENCY = "PAYMENT_AGENCY";
	public static final String PUBLISH_AD = "PUBLISH_AD";
	public static final String DISPLAY_PUBLISH_AD = "DISPLAY_PUBLISH_AD";
	public static final String CLF_ORDER_APPROVED = "CLF_ORDER_APPROVED";
	public static final String CLF_ORDER_REJECTED = "CLF_ORDER_REJECTED";
	public static final String CLF_ORDER_APPROVED_AGENCY = "CLF_ORDER_APPROVED_AGENCY";
	public static final String CLF_ORDER_REJECTED_AGENCY = "CLF_ORDER_REJECTED_AGNECY";
	public static final String RMS_RO = "RMS_RO";
	public static final String SSP_RO = "SSP_RO";
	public static final String GENERATE_PAYMENT_LINK = "GENERATE_PAYMENT_LINK";
	public static final String RMS_PAYMENT_INVOICE = "RMS_PAYMENT_INVOICE";
	public static final String ENCODED_STRING = "ENCODED_STRING";
	public static final String DISPLAY_ORDER_APPROVED = "DISPLAY_ORDER_APPROVED";
	public static final String DISPLAY_ORDER_REJECTED = "DISPLAY_ORDER_REJECTED";
	public static final String DISPLAY_PAYMENT = "DISPLAY_PAYMENT";

	/* Inventory status */
	public static final String DOWNLOADED = "D";
	public static final String UPLOADED = "U";
	public static final String LOCKED = "L";

	/* Application modules */
	public static final int ORG = 1;
	public static final int USER = 2;
	public static final int SETTING = 3;
	public static final int GLOBAL = 4;
	public static final int FF = 5;
	public static final int PRINT = 6;
	public static final int ACTIVITY = 7;
	public static final int REPORT = 8;
	public static final int DASHBOARD_OPERATIONS = 9;

	public static final String CNF_GRP_MTRLS = "MTRLS";
	public static final String CNF_GRP_SALESPERSONS = "SALESPERSONS";
	public static final String CNF_GRP_STKSTMAP = "STKSTMAP";

	public static final String AWS_SET_GP = "AWS_S3";
	public static final String FTP = "FTP";
	public static final String FTP_S3_STORAGE = "FTP_S3_STORAGE";
	public static final String FTP_LOCAL = "FTP_LOCAL";
	public static final String FTP_SERVER = "FTP_SERVER";
	public static final String FTP_DL_USER_TOKEN = "FTP_DL_USER_TOKEN";
	public static final String API_URL = "ENV";
	public static final String DATA_MIGRATION_CONFIG_VAL = "DataMigration";

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
	
	public static final String PENDING = "PENDING";
	public static final String COMPLETED = "COMPLETED";
	

	/* settings groups */
	public static final String EMAIL_SET_GP = "SMTP";
	public static final String VENDOR_COMMISSION = "VC";
	public static final String ENV_SET_GP = "ENV";
	public static final String IPW_SETTINGS = "IPW";
	public static final String USER_SETTINGS = "USER";
	public static final String LOGON_RETRIES = "LOGON_RETRIES";
	public static final String ACCOUNT_UNLOCK_HOURS = "ACCOUNT_UNLOCK_HOURS";
	public static final String IP_DESKTOP_APPS = "IP_DESKTOP_APPS";
	public static final String PWD_EXPIRE_DAYS = "PWD_EXPIRE_DAYS";
	public static final String MASTERDATA = "MASTERDATA";
	public static final String TEMPLATESDATA = "TEMPLATESDATA";
	public static final String TEMPLATES_HEADERS = "TEMPLATES_HEADERS";
	public static final String AM = "AM";
	public static final String CLF = "CLF";
	public static final String DA = "DA";
	
//	Setting short ids
	
	public static final String MASTERDATA_CLMDS = "MASTERDATA_CLMDS";
	public static final String MASTERDATA_APR_ROLES = "MASTERDATA_APR_ROLES";


	/* Roles short id */
	public static final String ROLE_BP_ADMIN = "PADM,OADM,SADM";
	public static final String ROLE_ORG_ADMIN = "OADM,SADM";
	public static final String ROLE_SUP_ADMIN = "SADM";

	public static final String ROLE_ADMIN = "ADMIN";
	
	/* Action to get users */
	public static final String BP_ACTION = "BP";
	public static final String ORG_ACTION = "ORG";
	public static final String ALL_ACTION = "ALL";

	/* User types */
	public static final int RMS_USERS = 1;
	public static final int SKD_USERS = 2;
	public static final String RMS_USER = "RMS";
	public static final String SKD_USER = "SKD";

	/* BP types */
	public static final String SS_BP_TYPE = "SS";
	public static final String KAD_BP_TYPE = "KAD";
	public static final String RMS_BP_TYPE = "RMS";

	/* Dash board constants */
	public static final String DASH_BOARDS = "DSHBRD";
	public static final String WIDGETS = "WIDGETS";

	/* config values */
	public static final String MATERIAL_CODE = "MATERIAL_CODE";
	public static final String CYLINDER = "CYLINDER";
	public static final String VEH_APR_DELAY_RSN = "VEH_APR_DELAY_RSN";
	public static final String VEH_APR_REMARKS = "VEH_APR_REMARKS";
	public static final String SCHEDULER = "SCHEDULER";
	public static final String SCHEDULER_FLAG = "SCHEDULER_FLAG";

	public static final String GD_REGION = "RM";
	public static final String Gd_REGION_CENTER = "RCM";
	public static final String GD_MODEL = "MM";
	public static final String GD_COUNTRIES = "CM";
	public static final String GD_AGGREGATE = "AM";
	public static final String GD_SUB_AGGREGATE = "SAM";
	public static final String GD_SM_TYPE = "SMTM";
	
//	public static String DEFAULT_PASSWORD = "BharatBenz@";
	public static String DEFAULT_PASSWORD = "Sakshi@123";
	public static final String EXCEL_SHEET_NAME = "Sheet1";
	
	public static final String CREATE_ACTION = "CREATE_ACTION";
	public static final String UPDATE_ACTION = "UPDATE_ACTION";
	public static final String DELETE_ACTION = "DELETE_ACTION";
	
	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error";
	public static String ALLOWED_DOC_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/pdf,text/plain,application/vnd.ms-powerpoint,application/msword,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.wordprocessingml.document,image/jpeg,image/png,text/csv,video/mp4,video/quicktime,video/mp4";
	
	public static final String PEOPLE = "PEOPLE";
	public static final String PROCESS = "PROCESS";
	public static final String PERFORMANCE = "PERFORMANCE";
	
//	public static final String ORG_ID = "org_id";
//	public static final String BP_ID = "bp_id";
	public static final String ROLE_EXTERNAL_USER = "EXTERNAL_USER";
	public static final Integer GD_USER_TYPE_ID = 2;
	
	public static final String ROLE_SSP_USER = "SSP_USER";
	public static final Integer GD_SSP_USER_TYPE_ID = 4;
	
	public static final String ORGID = "1000";
	public static final String BPID = "1100";

	// security
	public static final String ORIGINAL_CAPTCHA = "oCaptcha";
	public static final String INPUT_CAPTCHA = "iCaptcha";

	// File Formats
	public static final String JPEG = "jpeg";
	
	public static final String SINGLE_LEVEL="Single Level";
	public static final String MULTI_LEVEL="Multi-Level";
	public static final String FORM_SAVE_AS_DRAFT = "SAVE_AS_DRAFT";
	
	public static final String DIVISION = "5";
	public static final String LEGAL = "16";
	public static final String BIRTHDAY ="";
	
	public static final String SSP_CLF_PAYMENT = "SSP_CLF_PAYMENT";
}
