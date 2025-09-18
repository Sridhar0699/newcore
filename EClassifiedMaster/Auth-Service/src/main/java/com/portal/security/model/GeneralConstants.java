package com.portal.security.model;

public class GeneralConstants {

	/* Device Types for App's */
	public static final int MOB_IOS_APP = 1;
	public static final int MOB_ANDRIOD_APP = 2;
	public static final int TAB_IOS_APP = 3;
	public static final int TAB_ANDRIOD_APP = 4;

	/* settings groups */
	public static final String EMAIL_SET_GP = "SMTP";
	public static final String ENV_SET_GP = "ENV";
	public static final String IPW_SETTINGS = "IPW";
	public static final String USER_SETTINGS = "USER";
	public static final String RAW_DATA_EXTRCT_RPT_ROW_LMT = "RAW_DATA_EXTRCT_RPT_ROW_LMT";
	public static final String LOGON_RETRIES = "LOGON_RETRIES";
	public static final String ACCOUNT_UNLOCK_HOURS = "ACCOUNT_UNLOCK_HOURS";
	public static final String IP_DESKTOP_APPS = "IP_DESKTOP_APPS";
	public static final String PWD_EXPIRE_DAYS = "PWD_EXPIRE_DAYS";

	/* Template names */
	public static final String EMAIL_FP_TEM_NAME = "FORGOT_PASSWORD";
	public static final String EMAIL_RESET_PWD_TEM_NAME = "RESET_PASSWORD";

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
}
