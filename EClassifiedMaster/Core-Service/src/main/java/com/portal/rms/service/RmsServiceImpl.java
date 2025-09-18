package com.portal.rms.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.activation.FileDataSource;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.IOException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.portal.basedao.IBaseDao;
import com.portal.clf.entities.ClfEditions;
import com.portal.clf.entities.ClfOrderItems;
import com.portal.clf.entities.ClfOrders;
import com.portal.clf.entities.ClfPaymentResponseTracking;
import com.portal.clf.entities.ClfPublishDates;
import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.ClassifiedConstants;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.clf.models.ErpClassifieds;
import com.portal.clf.models.RmsApprovalDetails;
import com.portal.common.models.DocumentsModel;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.common.service.DocumentService;
import com.portal.constants.GeneralConstants;
import com.portal.doc.entity.Attachments;
import com.portal.erp.service.ErpService;
import com.portal.gd.dao.GlobalDataDao;
import com.portal.gd.entities.GdCity;
import com.portal.gd.entities.GdFixedFormatCodes;
import com.portal.gd.entities.GdNumberSeries;
import com.portal.gd.entities.GdRmsAnnexure;
import com.portal.gd.entities.GdRmsEditions;
import com.portal.gd.entities.GdRmsFixedFormats;
import com.portal.gd.entities.GdRmsMultipleDiscountVal;
import com.portal.gd.entities.GdRmsNumberSeries;
import com.portal.gd.entities.GdRmsPagePositions;
import com.portal.gd.entities.GdRmsPositioningDiscount;
import com.portal.gd.entities.GdRmsSchemes;
import com.portal.gd.entities.GdUserTypes;
import com.portal.gd.entities.RmsApprovalMatrix;
import com.portal.gd.to.GdSettingConfigsTo;
import com.portal.reports.utility.CommonUtils;
import com.portal.repository.AttachmentsRepo;
import com.portal.repository.ClfEditionsRepo;
import com.portal.repository.ClfOrderItemRatesRepo;
import com.portal.repository.ClfOrderItemsRepo;
import com.portal.repository.ClfOrdersRepo;
import com.portal.repository.ClfPaymentResponseTrackingRepo;
import com.portal.repository.ClfPublishDatesRepo;
import com.portal.repository.GdCityRepo;
import com.portal.repository.GdNumberSeriesRepo;
import com.portal.repository.GdSettingsDefinitionsRepository;
import com.portal.repository.GdUserTypesRepo;
import com.portal.repository.UmCustomersRepo;
import com.portal.repository.UmUsersRepository;
import com.portal.repository.WfInboxMasterDetailsRepo;
import com.portal.repository.WfInboxMasterRepo;
import com.portal.repository.WfMasterRepo;
import com.portal.rms.entity.ApprovalInbox;
import com.portal.rms.entity.OtpVerification;
import com.portal.rms.entity.RmsOrderDiscountTypes;
import com.portal.rms.entity.RmsOrderItems;
import com.portal.rms.entity.RmsPaymentsResponse;
import com.portal.rms.entity.RmsRates;
import com.portal.rms.model.ApprovalDetailsModel;
import com.portal.rms.model.ApprovalInboxModel;
import com.portal.rms.model.ClientPo;
import com.portal.rms.model.CreateOrder;
import com.portal.rms.model.CustomerObjectDisplay;
import com.portal.rms.model.InsertionObjectDisplay;
import com.portal.rms.model.InsertionPo;
import com.portal.rms.model.OtpModel;
import com.portal.rms.model.PaymentDetails;
import com.portal.rms.model.PaymentObjectDisplay;
import com.portal.rms.model.RmsApproveModel;
import com.portal.rms.model.RmsConstants;
import com.portal.rms.model.RmsCustomerDetails;
import com.portal.rms.model.RmsDashboardFilter;
import com.portal.rms.model.RmsDiscountModel;
import com.portal.rms.model.RmsKycAttatchments;
import com.portal.rms.model.RmsListViewModel;
import com.portal.rms.model.RmsModel;
import com.portal.rms.model.RmsOrderList;
import com.portal.rms.model.RmsPaymentLinkModel;
import com.portal.rms.model.RmsPremiumModel;
import com.portal.rms.model.RmsRateModel;
import com.portal.rms.model.RmsRatesResponse;
import com.portal.rms.model.RmsTaxModel;
import com.portal.rms.model.RmsViewDetails;
import com.portal.rms.repository.ApprovalInboxRepo;
import com.portal.rms.repository.GdFixedFormatCodesRepo;
import com.portal.rms.repository.GdRmsAnnexureRepo;
import com.portal.rms.repository.GdRmsEditionsRepo;
import com.portal.rms.repository.GdRmsFixedFormatsRepo;
import com.portal.rms.repository.GdRmsMultipleDiscountValRepo;
import com.portal.rms.repository.GdRmsNumberSeriesRepo;
import com.portal.rms.repository.GdRmsPagePositionsRepo;
import com.portal.rms.repository.GdRmsPageRateRepo;
import com.portal.rms.repository.GdRmsPositioningDiscountRepo;
import com.portal.rms.repository.GdRmsSchemesRepo;
import com.portal.rms.repository.OtpVerificationRepo;
import com.portal.rms.repository.RmsApprovalMatrixRepo;
import com.portal.rms.repository.RmsAttachmentsRepo;
import com.portal.rms.repository.RmsClfEditionsRepo;
import com.portal.rms.repository.RmsClfOrderItemsRatesRepo;
import com.portal.rms.repository.RmsClfOrderItemsRepo;
import com.portal.rms.repository.RmsClfOrdersRepo;
import com.portal.rms.repository.RmsClfPublishDates;
import com.portal.rms.repository.RmsOrderDiscountTypesRepo;
import com.portal.rms.repository.RmsOrderItemsRepo;
import com.portal.rms.repository.RmsPaymentResponseTrackingRepo;
import com.portal.rms.repository.RmsPaymentsRepository;
import com.portal.rms.repository.RmsRatesRepo;
import com.portal.rms.repository.RmsUmCustomersRepo;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;
import com.portal.send.models.EmailsTo;
import com.portal.send.service.SendMessageService;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;
import com.portal.user.dao.UserDao;
import com.portal.user.entities.UmCustomers;
import com.portal.user.entities.UmUsers;
import com.portal.utils.HelperUtil;
import com.portal.wf.entity.WfInboxMaster;
import com.portal.wf.entity.WfInboxMasterDetails;
import com.portal.workflow.WfDao;
import com.portal.workflow.WorkFlowService;
import com.portal.workflow.model.WfEvent;
import com.portal.workflow.model.WfRequest;

@Transactional
@Service
public class RmsServiceImpl implements RmsService {

	public static final String DIR_PATH_DOCS = "/SEC/DOCS/";
	public static final String DIR_PATH_PDF_DOCS = "/SEC/PDFS/";
	public static final String DIR_PATH_TO_DOWNLOAD = "/SEC/PDF/";

	@Autowired
	private RmsClfOrderItemsRepo rmsOrderItemsRepo;
	
	@Autowired RmsOrderItemsRepo rmsOrdersItemsRepo;

	@Autowired(required = true)
	private IBaseDao baseDao;

	@Autowired
	private GdNumberSeriesRepo gdNumberSeriesRepo;

	@Autowired
	private RmsClfEditionsRepo rmsClfEditionsRepo;

	@Autowired
	private RmsClfPublishDates rmsClfPublishDates;

	@Autowired
	private RmsUmCustomersRepo rmsUmCustomersRepo;

	@Autowired
	private RmsClfOrdersRepo rmsClfOrdersRepo;

	@Autowired
	private RmsClfOrderItemsRatesRepo rmsClfOrderItemsRatesRepo;

	@Autowired
	private GdCityRepo gdCityRepo;

	@Autowired
	private RmsOrderItemsRepo orderItemsRepo;

	@Autowired
	private RmsPaymentsRepository paymentsRepository;

	@Autowired
	private GdSettingsDefinitionsRepository settingRepo;

	@Autowired
	private RmsRatesRepo rmsRatesRepo;

	@Autowired
	private SendMessageService sendMessageService;

	@Autowired
	private OtpVerificationRepo otpVerificationRepo;

	@Autowired
	private Environment prop;

	@Autowired
	private RmsAttachmentsRepo rmsAttachmentsRepo;

	@Autowired
	private GdRmsNumberSeriesRepo gdRmsNumberSeriesRepo;
	@Autowired
	private ErpService erpService;

	@Autowired
	private ClfPublishDatesRepo clfPublishDatesRepo;

	@Autowired
	private ClfEditionsRepo clfEditionsRepo;

	@Autowired
	private UmCustomersRepo umCustomersRepo;

	@Autowired
	private GdUserTypesRepo gdUserTypesRepo;

	@Autowired
	private UmUsersRepository umUsersRepository;

	@Autowired
	private UserDao userDao;

	@Autowired(required = true)
	private SendMessageService sendService;

	@Autowired(required = true)
	private SettingDao settingDao;

	@Autowired
	private LoggedUserContext userContext;
	
	@Autowired
	private GdRmsMultipleDiscountValRepo gdRmsMultipleDiscountValRepo;
	
	@Autowired
	private RmsPaymentResponseTrackingRepo paymentsRepo;

	@Autowired
    private ApprovalInboxRepo inboxRepo;
	
	@Autowired
	private AttachmentsRepo attachmentsRepo;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private WfDao wfDao;
	
	@Autowired
	private WfMasterRepo wfMasterRepo;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private WfInboxMasterRepo wfInboxMasterRepo;
	
	@Autowired
	private WfInboxMasterDetailsRepo wfInboxMasterDetailsRepo;
	
	public String getDIR_PATH_DOCS() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_DOCS;
	}

	public String getDIR_PATH_PDF_DOCS() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_PDF_DOCS;
	}
	
	public String getDIR_PATH_TO_DOWNLOAD() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_TO_DOWNLOAD;
	}
	
	public static String TOMCAT_PATH = "/staticresources/docs/";
	public String getTOMCAT_PATH() {
	        return prop.getProperty("ROOT_DIR") + TOMCAT_PATH;
	    }

	@Autowired
	private ClfOrdersRepo clfOrdersRepo;
	
	@Autowired
	private ClfOrderItemRatesRepo clfOrderItemRatesRepo;
	
	@Autowired
	private ClfOrderItemsRepo clfOrderItemsRepo;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private GdFixedFormatCodesRepo gdFixedFormatCodesRepo;
	
	@Autowired
	private GdRmsEditionsRepo gdRmsEditionsRepo;
	
	@Autowired
	private GdRmsFixedFormatsRepo gdRmsFixedFormatsRepo;
	
	@Autowired(required = true)
	private GlobalDataDao globalDataDao;
	
	@Autowired
	private GdRmsPositioningDiscountRepo gdRmsPositioningDiscountRepo;
	
	@Autowired
	private GdRmsPagePositionsRepo gdRmsPagePositionsRepo;
	
	@Autowired
	private GdRmsPageRateRepo gdRmsPageRateRepo;
	
	@Autowired
	private GdRmsSchemesRepo gdRmsSchemesRepo;
	
	@Autowired
	private GdRmsAnnexureRepo gdRmsAnnexureRepo;
	
	@Autowired
	private RmsApprovalMatrixRepo rmsApprovalMatrixRepo;
	
	@Autowired
	private ApprovalInboxRepo approvalInboxRepo;
	
	@Autowired
	private RmsOrderDiscountTypesRepo rmsOrderDiscountTypesRepo;
	
	@Autowired
	private ClfPaymentResponseTrackingRepo clfPaymentResponseTrackingRepo;
	
	private static final Logger logger = LogManager.getLogger(RmsServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getDashboardCounts(LoggedUser loggedUser, RmsDashboardFilter payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fromDateStr = null;
		String toDateStr = null;
		Map<String, Integer> countsMap = new HashMap<>();
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_PENDING, 0);
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_APPROVED, 0);
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_REJECT, 0);
		countsMap.put(ClassifiedConstants.SAVE_AS_DRAFT, 0);
		countsMap.put(ClassifiedConstants.APPROVAL_INBOX_PENDING, 0);
		String query = "select status,count(*)  from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join  um_customers uc on co.customer_id = uc.customer_id inner join rms_payments_response rps ON itm.order_id =rps.order_id";
		if (payload.getBookingCode() != null) {
			query = query + " and co.booking_unit = " + payload.getBookingCode() + "";
		}
		if (payload.getPublishedDate() != null && !payload.getPublishedDate().isEmpty()) {
			query = query
					+ " inner join clf_publish_dates cpd on cpd.item_id = itm.item_id and to_char(cpd.publish_date,'YYYY-MM-dd') = '"
					+ payload.getPublishedDate() + "'";
		}
		if(loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN")) {
			query = query + " where  itm.mark_as_delete = false and co.order_type=1 and co.order_status='CLOSED' ";
		}else {
			query = query + " where  itm.mark_as_delete = false and co.order_type=1 and co.order_status='CLOSED' and itm.created_by ="
					+ loggedUser.getUserId();
		}
		

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30); // Subtract 30 days from current date
		Date thirtyDaysAgo = calendar.getTime();
		fromDateStr = sdf.format(thirtyDaysAgo);
		toDateStr = sdf.format(new Date()); // Current date
		query += " AND to_char(rps.created_ts,'YYYY-MM-DD') >= '" + fromDateStr
				+ "' AND to_char(rps.created_ts,'YYYY-MM-DD') <= '" + toDateStr + "'";
		query += " AND rps.mark_as_delete=false group by status";
		List<Object[]> dashboardStatus = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		if (dashboardStatus != null && !dashboardStatus.isEmpty()) {
			for (Object[] obj : dashboardStatus) {
				countsMap.put((String) obj[0], ((BigInteger) obj[1]).intValue());
			}
		}
		
//		String approvalQuery = "select status,count(*) from approval_inbox ai inner join clf_orders co on co.order_id = ai.order_id left join rms_payments_response rps ON co.order_id =rps.order_id ";
//		if (payload.getBookingCode() != null) {
//			approvalQuery = approvalQuery + " and co.booking_unit = " + payload.getBookingCode() + "";
//		}
//		if (payload.getPublishedDate() != null && !payload.getPublishedDate().isEmpty()) {
//			approvalQuery = approvalQuery
//					+ " inner join clf_publish_dates cpd on co.order_id = co.order_id and to_char(cpd.publish_date,'YYYY-MM-dd') = '"
//					+ payload.getPublishedDate() + "'";
//		}
//		if(loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN")) {
//			approvalQuery = approvalQuery + " where ai.status = 'PENDING' and ai.mark_as_delete = false ";	
//		}else {
//			approvalQuery = approvalQuery + " where ai.status = 'PENDING' and ai.mark_as_delete = false  and ai.approver_user_id ="
//					+ loggedUser.getUserId();	
//		}
//			
//		approvalQuery += " AND to_char(rps.created_ts,'YYYY-MM-DD') >= '" + fromDateStr
//				+ "' AND to_char(rps.created_ts,'YYYY-MM-DD') <= '" + toDateStr + "'";
//		approvalQuery += " AND rps.mark_as_delete=false group by status";
//		
//		List<Object[]> approvalStatus = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(approvalQuery);
//		if(approvalStatus != null && !approvalStatus.isEmpty()) {
//			for(Object[] obj : approvalStatus) {
//				countsMap.put(ClassifiedConstants.APPROVAL_INBOX_PENDING, ((BigInteger) obj[1]).intValue());
//			}
//		}
		
		List<Object[]> data = new ArrayList<>();
		if ("SUPER_ADMIN".equalsIgnoreCase(loggedUser.getRoleType())) {
			query = "select wfrt.inbox_master_id,wfrt.wf_inbox_id from wf_ref_to_type wfrt where wfrt.mark_as_delete=false order by wfrt.created_ts desc";
			data = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		}

		String query1 = "select wr.inbox_master_id,wr.wf_inbox_id from wf_ref_to_users wr where wr.ref_to_users IN ("
				+ loggedUser.getUserId()
				+ ") and wr.mark_as_delete=false order by wr.created_ts desc";
		List<Object[]> data1 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);

		String query2 = "select wui.inbox_master_id,wui.wf_inbox_id from wf_user_inbox wui where wui.user_id IN ("
				+ loggedUser.getUserId()
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
		if (inboxMasterIds != null && !inboxMasterIds.isEmpty()) {
			List<String> uniqueInboxMasterIds = new ArrayList<>(inboxMasterIds);
			String masterIds = uniqueInboxMasterIds.stream().map(role -> "'" + role + "'")
					.collect(Collectors.joining(", "));
			String query3 = "select wim.status,COUNT(*) OVER () AS total_count from wf_inbox_master_details wim where wim.wf_inbox_id IN ("
					+ masterIds
					+ ") and wim.mark_as_delete=false and wim.status!='WORKFLOW_SKIPPED' order by wim.created_ts desc";
			
			data3 = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query3);
			if(data3 != null && !data3.isEmpty()) {
				for(Object[] obj : data3) {
					countsMap.put(ClassifiedConstants.APPROVAL_INBOX_PENDING, ((BigInteger) obj[1]).intValue());
				}
			}
		}
		
		
		String saveAsDraftQuery = "select rms_order_status,count(*) from clf_orders co inner join  um_customers uc on co.customer_id = uc.customer_id";
		if (payload.getBookingCode() != null) {
			saveAsDraftQuery = saveAsDraftQuery + " and co.booking_unit = " + payload.getBookingCode() + "";
		}
		if (payload.getPublishedDate() != null && !payload.getPublishedDate().isEmpty()) {
			saveAsDraftQuery = saveAsDraftQuery
					+ " inner join clf_publish_dates cpd on co.order_id = co.order_id and to_char(cpd.publish_date,'YYYY-MM-dd') = '"
					+ payload.getPublishedDate() + "'";
		}
		if(loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN")) {
			saveAsDraftQuery = saveAsDraftQuery + " where co.rms_order_status = 'SAVE_AS_DRAFT' and co.mark_as_delete = false and co.order_type = 1 and uc.client_code is not null AND TRIM(uc.client_code) != ''";	
		}else {
			saveAsDraftQuery = saveAsDraftQuery + " where co.rms_order_status = 'SAVE_AS_DRAFT' and co.mark_as_delete = false  and co.created_by ="
					+ loggedUser.getUserId() + " and co.order_type = 1 and uc.client_code is not null AND TRIM(uc.client_code) != ''";	
		}
			
		saveAsDraftQuery += " AND to_char(co.created_ts,'YYYY-MM-DD') >= '" + fromDateStr
				+ "' AND to_char(co.created_ts,'YYYY-MM-DD') <= '" + toDateStr + "'";
		saveAsDraftQuery += " AND co.mark_as_delete=false group by rms_order_status";
		
		List<Object[]> saveAsDraftData = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(saveAsDraftQuery);
		if(saveAsDraftData != null && !saveAsDraftData.isEmpty()) {
			for(Object[] obj : saveAsDraftData) {
				countsMap.put((String) obj[0], ((BigInteger) obj[1]).intValue());
			}
		}
		
		if (!countsMap.isEmpty()) {
			genericApiResponse.setStatus(0);
		}
		genericApiResponse.setData(countsMap);
		genericApiResponse.setMessage("Dash Board Counts");
		return genericApiResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getRmsClassifiedList(LoggedUser loggedUser, RmsDashboardFilter payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = null;
			String toDateStr = null;
			Date fromDate = payload.getFromDate();
			Date toDate = payload.getToDate();
			String query = "";
			if(payload != null && !"Save_AS_DRAFT".equalsIgnoreCase(payload.getOrderStatus()) && !loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN")) {
				/*
				 * query =
				 * "select itm.created_ts ,itm.item_id ,itm.ad_id ,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,bu.booking_description,uc.customer_name,roi.grand_total,itm.order_id,rps.payment_mode,gpm.payment_mode as gpm_payment_mode,itm.status from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join  um_customers uc on co.customer_id = uc.customer_id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) inner join rms_payments_response rps ON itm.order_id =rps.order_id inner join gd_payment_mode gpm ON rps.payment_mode = gpm.id inner join rms_order_items roi on roi.order_id = co.order_id where itm.created_by = "
				 * + loggedUser.getUserId() +
				 * " and itm.mark_As_delete = false and co.order_type=1 and co.order_status='CLOSED' and co.mark_as_delete = false"
				 * ;
				 */
				query = "select itm.created_ts ,itm.item_id ,itm.ad_id ,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,uc.customer_name,roi.grand_total,itm.order_id,rps.payment_mode,gpm.payment_mode as gpm_payment_mode,itm.status from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join  um_customers uc on co.customer_id = uc.customer_id inner join rms_payments_response rps ON itm.order_id =rps.order_id inner join gd_payment_mode gpm ON rps.payment_mode = gpm.id inner join rms_order_items roi on roi.order_id = co.order_id where itm.created_by = "
						+ loggedUser.getUserId()
						+ " and itm.mark_As_delete = false and co.order_type=1 and co.order_status='CLOSED' and co.mark_as_delete = false";
			}else if(payload != null && !"Save_AS_DRAFT".equalsIgnoreCase(payload.getOrderStatus()) && loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN")) {
				/*
				 * query =
				 * "select itm.created_ts ,itm.item_id ,itm.ad_id ,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,bu.booking_description,uc.customer_name,roi.grand_total,itm.order_id,rps.payment_mode,gpm.payment_mode as gpm_payment_mode,itm.status from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join  um_customers uc on co.customer_id = uc.customer_id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) inner join rms_payments_response rps ON itm.order_id =rps.order_id inner join gd_payment_mode gpm ON rps.payment_mode = gpm.id inner join rms_order_items roi on roi.order_id = co.order_id where "
				 * +
				 * " itm.mark_As_delete = false and co.order_type=1 and co.order_status='CLOSED' and co.mark_as_delete = false"
				 * ;
				 */
				query = "select itm.created_ts ,itm.item_id ,itm.ad_id ,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,uc.customer_name,roi.grand_total,itm.order_id,rps.payment_mode,gpm.payment_mode as gpm_payment_mode,itm.status from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join  um_customers uc on co.customer_id = uc.customer_id inner join rms_payments_response rps ON itm.order_id =rps.order_id inner join gd_payment_mode gpm ON rps.payment_mode = gpm.id inner join rms_order_items roi on roi.order_id = co.order_id where "
						+ " itm.mark_As_delete = false and co.order_type=1 and co.order_status='CLOSED' and co.mark_as_delete = false";
			}
			if("Save_AS_DRAFT".equalsIgnoreCase(payload.getOrderStatus())) {
				
				genericApiResponse = this.getSaveAsDraftOrders(payload,loggedUser);
				return genericApiResponse;
//				query = "co.order_id,co.created_ts,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,bu.booking_description from clf_orders co inner join  um_customers uc on co.customer_id = uc.customer_id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) where co.created_by = "
//						+ loggedUser.getUserId() + " and co.mark_as_delete = false and co.order_type = 1 and co.order_status = 'OPEN' and co.rms_order_status = 'SAVE_AS_DRAFT'";
				
//				query = "select itm.created_ts ,itm.item_id ,itm.ad_id ,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,bu.booking_description from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join  um_customers uc on co.customer_id = uc.customer_id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) inner join rms_payments_response rps ON itm.order_id =rps.order_id where itm.created_by = "
//						+ loggedUser.getUserId()
//						+ " and itm.mark_As_delete = false and co.order_type=1 and co.order_status='OPEN'";
			}
			genericApiResponse.setStatus(1);
			LinkedHashMap<String, Object> orderObject = new LinkedHashMap<String, Object>();
			List<Object[]> classifiedList = new ArrayList<Object[]>();
			
			  if (payload.getBookingCode() != null) {
				query = query + " and co.booking_unit = " + payload.getBookingCode();
			}
			 
			if (payload.getPublishedDate() != null && !payload.getPublishedDate().isEmpty()) {
				query = query + " and to_char(itm.created_ts,'YYYY-MM-DD') = '" + payload.getPublishedDate() + "'";
			}
			if(payload.getStatus() != null && !payload.getStatus().isEmpty()) {
				query = query + " and itm.status = '" + payload.getStatus() + "'";
			}
			if(payload.getOrderStatus() != null && !payload.getOrderStatus().isEmpty()) {
				query = query + " and itm.status = '" + payload.getOrderStatus() + "'";
			}
//			if(!"Save_AS_DRAFT".equalsIgnoreCase(payload.getOrderStatus())) {
//				if(payload.getOrderStatus() != null && !payload.getOrderStatus().isEmpty()) {
//					query = query + " and co.rms_order_status = '" + payload.getOrderStatus() + "'";
//				}
//				if(payload.getStatus() != null && !payload.getStatus().isEmpty()) {
//					query = query + " and itm.status = '" + payload.getStatus() + "'";
//				}
//			}

			if (fromDate != null && toDate != null) {
				if (fromDate.before(toDate)) {
					fromDateStr = sdf.format(fromDate);
					toDateStr = sdf.format(toDate);
				} else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("To Date should be greater than From Date");
					return genericApiResponse;
				}
			} else if (fromDate != null) {
				Calendar calendar = Calendar.getInstance();
				fromDateStr = sdf.format(fromDate);
				calendar.setTime(fromDate);
				calendar.add(Calendar.DATE, 30);
				toDateStr = sdf.format(calendar.getTime());
			} else if (toDate != null) {
				Calendar calendar = Calendar.getInstance();
				toDateStr = sdf.format(toDate);
				calendar.setTime(toDate);
				calendar.add(Calendar.DATE, -30);
				fromDateStr = sdf.format(calendar.getTime());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -30); // Subtract 30 days from current date
				Date thirtyDaysAgo = calendar.getTime();
				fromDateStr = sdf.format(thirtyDaysAgo);
				toDateStr = sdf.format(new Date()); // Current date
			}

			query += " AND to_char(itm.created_ts,'YYYY-MM-DD') >= '" + fromDateStr
					+ "' AND to_char(itm.created_ts,'YYYY-MM-DD') <= '" + toDateStr
					+ "' AND rps.mark_as_delete=false  ORDER BY itm.created_ts DESC ";

			if (payload.getPageNumber() != null && payload.getPageSize() != null) {
				int skip = ((Integer) payload.getPageNumber() - 1) * ((Integer) payload.getPageSize());
				query = query + " LIMIT " + payload.getPageSize() + " OFFSET " + skip;
			}

			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

			LinkedHashMap<String, RmsOrderList> classifiedsMap = new LinkedHashMap<String, RmsOrderList>();
			List<String> itemIds = new ArrayList<String>();
			List<Integer> cityIds = new ArrayList<Integer>();
			BigInteger totalCounts = null;
			for (Object[] obj : classifiedList) {
				RmsOrderList classifieds = new RmsOrderList();
				//classifieds.setOrderId((String) obj[9]);
				classifieds.setOrderId((String) obj[8]);
				//classifieds.setCity((String) obj[6]);
				classifieds.setCity((String) obj[3]);
				classifieds.setOrderDate(CommonUtils.dateFormatter((Date) obj[0], "dd-MM-yyyy HH:mm:ss"));
				classifieds.setOrderNo((String) obj[2]);
				classifieds.setItemId((String) obj[1]);
				classifieds.setClientCode((String) obj[5]);
				//classifieds.setCustomerName((String) obj[7]);
				classifieds.setCustomerName((String) obj[6]);
				//classifieds.setMode((String) obj[11]);
				classifieds.setMode((String) obj[10]);
				//classifieds.setCurrentStatus((String) obj[12]);
				classifieds.setCurrentStatus((String) obj[11]);
				//Float grandTotal = (Float)obj[8] != null ? (Float)obj[8] :null;
				Float grandTotal = (Float)obj[7] != null ? (Float)obj[7] :null;
				if(grandTotal != null) {
					classifieds.setAmount(grandTotal.doubleValue());
				}	
				itemIds.add((String) obj[1]);
				//cityIds.add(Integer.parseInt((String) obj[3]));
				classifiedsMap.put((String) obj[1], classifieds);
				totalCounts = ((BigInteger) obj[4]);
			}
			List<Object[]> publishDatesList = rmsClfPublishDates.getPublishDatesForErpData(itemIds);
			for (Object[] clObj : publishDatesList) {
				if (classifiedsMap.containsKey((String) clObj[0])) {
					if (classifiedsMap.get((String) clObj[0]).getPublishDates() != null) {
						classifiedsMap.get((String) clObj[0]).getPublishDates()
								.add(CommonUtils.dateFormatter((Date) clObj[1], "dd-MM-yyyy"));
					} else {
						List<String> publishDates = new ArrayList<>();
						publishDates.add(CommonUtils.dateFormatter((Date) clObj[1], "dd-MM-yyyy"));
						RmsOrderList classified = classifiedsMap.get((String) clObj[0]);
						classified.setPublishDates(publishDates);
//						classifiedsMap.put((String) clObj[0], classified);
					}
				}
			}
			/*
			 * if (!cityIds.isEmpty()) { List<GdCity> gdCityDetails =
			 * gdCityRepo.getCityDetails(cityIds); if (!gdCityDetails.isEmpty()) {
			 * classifiedsMap.entrySet().forEach(erpData -> { Optional<GdCity> gd =
			 * gdCityDetails.stream() .filter(f ->
			 * String.valueOf(f.getId()).equals(erpData.getValue().getCity())) .findFirst();
			 * if (gd.isPresent()) { GdCity gdCity = gd.get();
			 * erpData.getValue().setCity(gdCity.getCity()); } }); } }
			 */
			
//			if(itemIds != null && !itemIds.isEmpty()) {
//				List<Object[]> approvalInboxList = approvalInboxRepo.getCurrentLevelOfApprovalInbox(itemIds);				
//				for (Object[] aiObj : approvalInboxList) {
//					if (classifiedsMap.containsKey((String) aiObj[0])) {
//						RmsOrderList rmsOrderList = classifiedsMap.get(aiObj[0]);
//						if(rmsOrderList != null && rmsOrderList.getCurrentStatus() == null) {
//							rmsOrderList.setCurrentStatus((String) aiObj[2] + "-"+ (Integer) aiObj[3]);
//							if(aiObj[4] != null) {
//								rmsOrderList.setApprovedTs(CommonUtils.dateFormatter((Date) aiObj[4], "dd-MM-yyyy HH:mm:ss"));
//							}
//							if(aiObj[8] != null) {
//								rmsOrderList.setApprovarName(((String) aiObj[7]) + " " + ((String) aiObj[8]));
//							}else {
//								rmsOrderList.setApprovarName((String) aiObj[7]);
//							}
//						}
//					}
//				}
//			}

			if (!classifiedsMap.isEmpty()) {
				genericApiResponse.setStatus(0);
				orderObject.put("ordersList", classifiedsMap.values());
				orderObject.put("totalCount", totalCounts);
				genericApiResponse.setData(orderObject);
			} else {
				if("1".equalsIgnoreCase(payload.getPageNumber()+"")) {
					genericApiResponse.setMessage("Oops! There are currently no orders to display.");
				}else {
					genericApiResponse.setMessage("Orders loaded Successfully.");
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return genericApiResponse;

	}

	public GenericApiResponse getSaveAsDraftOrders(RmsDashboardFilter payload, LoggedUser loggedUser) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = null;
			String toDateStr = null;
			Date fromDate = payload.getFromDate();
			Date toDate = payload.getToDate();
			String query = "";
			if(loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN") ) {
//				 query = "select co.order_id,co.created_ts,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,bu.booking_description,uc.customer_name,co.created_by,um.first_name from clf_orders co inner join  um_customers uc on co.customer_id = uc.customer_id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) inner join um_users um on co.created_by = um.user_id where  co.mark_as_delete = false and co.order_type = 1 and co.rms_order_status = 'SAVE_AS_DRAFT' and uc.client_code is not null AND TRIM(uc.client_code) != ''";
				 query = "select co.order_id,co.created_ts,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,uc.customer_name,co.created_by,um.first_name from clf_orders co inner join  um_customers uc on co.customer_id = uc.customer_id inner join um_users um on co.created_by = um.user_id where  co.mark_as_delete = false and co.order_type = 1 and co.rms_order_status = 'SAVE_AS_DRAFT' and uc.client_code is not null AND TRIM(uc.client_code) != ''";
			}else {	
//				 query = "select co.order_id,co.created_ts,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,bu.booking_description,uc.customer_name,co.created_by,um.first_name from clf_orders co inner join  um_customers uc on co.customer_id = uc.customer_id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) inner join um_users um on co.created_by = um.user_id where co.created_by = "
//						+ loggedUser.getUserId() + " and co.mark_as_delete = false and co.order_type = 1 and co.rms_order_status = 'SAVE_AS_DRAFT' and uc.client_code is not null AND TRIM(uc.client_code) != ''";
				query = "select co.order_id,co.created_ts,uc.city,COUNT(*) OVER () AS total_count,uc.client_code,uc.customer_name,co.created_by,um.first_name from clf_orders co inner join  um_customers uc on co.customer_id = uc.customer_id inner join um_users um on co.created_by = um.user_id where co.created_by = "
						+ loggedUser.getUserId() + " and co.mark_as_delete = false and co.order_type = 1 and co.rms_order_status = 'SAVE_AS_DRAFT' and uc.client_code is not null AND TRIM(uc.client_code) != ''";
			}
			
			LinkedHashMap<String, Object> orderObject = new LinkedHashMap<String, Object>();
			List<Object[]> classifiedList = new ArrayList<Object[]>();
			if (payload.getBookingCode() != null) {
				query = query + " and co.booking_unit = " + payload.getBookingCode();
			}
			
			if (fromDate != null && toDate != null) {
				if (fromDate.before(toDate)) {
					fromDateStr = sdf.format(fromDate);
					toDateStr = sdf.format(toDate);
				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage("To Date should be greater than From Date");
					return apiResponse;
				}
			} else if (fromDate != null) {
				Calendar calendar = Calendar.getInstance();
				fromDateStr = sdf.format(fromDate);
				calendar.setTime(fromDate);
				calendar.add(Calendar.DATE, 30);
				toDateStr = sdf.format(calendar.getTime());
			} else if (toDate != null) {
				Calendar calendar = Calendar.getInstance();
				toDateStr = sdf.format(toDate);
				calendar.setTime(toDate);
				calendar.add(Calendar.DATE, -30);
				fromDateStr = sdf.format(calendar.getTime());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -30); // Subtract 30 days from current date
				Date thirtyDaysAgo = calendar.getTime();
				fromDateStr = sdf.format(thirtyDaysAgo);
				toDateStr = sdf.format(new Date()); // Current date
			}

			query += " AND to_char(co.created_ts,'YYYY-MM-DD') >= '" + fromDateStr
					+ "' AND to_char(co.created_ts,'YYYY-MM-DD') <= '" + toDateStr + "'";
			
			query = query + " order by co.created_ts desc";
			if (payload.getPageNumber() != null && payload.getPageSize() != null) {
				int skip = ((Integer) payload.getPageNumber() - 1) * ((Integer) payload.getPageSize());
				query = query + " LIMIT " + payload.getPageSize() + " OFFSET " + skip;
			}
			
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

			LinkedHashMap<String, RmsOrderList> classifiedsMap = new LinkedHashMap<String, RmsOrderList>();
			BigInteger totalCounts = null;
			for (Object[] obj : classifiedList) {
				RmsOrderList classifieds = new RmsOrderList();
				classifieds.setOrderId((String) obj[0]);
				ClfOrderItems openOrderItemsByOrderId = clfOrderItemsRepo.getOpenOrderItemsByOrderId((String) obj[0]);
				if(openOrderItemsByOrderId != null) {
					classifieds.setItemId(openOrderItemsByOrderId.getItemId());
					List<Object[]> publishDatesOnItemId = clfPublishDatesRepo.getPublishDatesOnItemId(openOrderItemsByOrderId.getItemId());
					if(publishDatesOnItemId != null && !publishDatesOnItemId.isEmpty()) {
						List<String> publishDates = new ArrayList<>();
                        for(Object[] dateObj : publishDatesOnItemId) {
                            publishDates.add(CommonUtils.dateFormatter((Date) dateObj[0], "dd-MM-yyyy"));
                        }
                        classifieds.setPublishDates(publishDates);
					}
					
					RmsOrderItems rmsOrderItems = rmsOrdersItemsRepo.getRmsItemsDetailsOnOrderId((String) obj[0]);
					if(rmsOrderItems != null) {
						classifieds.setAmount(rmsOrderItems.getGrandTotal());
					}
				}
				
				classifieds.setOrderDate(CommonUtils.dateFormatter((Date) obj[1], "dd-MM-yyyy HH:mm:ss"));
//				classifieds.setCity((String) obj[5]);
				classifieds.setCity((String) obj[2]);
				totalCounts = ((BigInteger) obj[3]);;
				classifieds.setClientCode((String) obj[4]);
//				classifieds.setCustomerName((String) obj[6]);
				classifieds.setCustomerName((String) obj[5]);
//				Float grandTotal = (Float)obj[7] != null ? (Float)obj[7] :null;
//				if(grandTotal != null) {
//					classifieds.setAmount(grandTotal.doubleValue());
//				}
//				classifieds.setCreatedById((Integer) obj[7]);
				classifieds.setCreatedById((Integer) obj[6]);
//				classifieds.setCreatedByName((String) obj[8]);
				classifieds.setCreatedByName((String) obj[7]);
				classifiedsMap.put((String) obj[0], classifieds);
			}
			
			if (!classifiedsMap.isEmpty()) {
				apiResponse.setStatus(0);
				orderObject.put("ordersList", classifiedsMap.values());
				orderObject.put("totalCount", totalCounts);
				apiResponse.setData(orderObject);
			} else {
				if("1".equalsIgnoreCase(payload.getPageNumber()+"")) {
					apiResponse.setMessage("Oops! There are currently no draft orders to display.");
				}else {
					apiResponse.setMessage("Orders loaded Successfully.");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return apiResponse;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public GenericApiResponse getRmsClassifiedsByAdId(LoggedUser loggedUser, @NotNull String adId) {
//		GenericApiResponse apiResponse = new GenericApiResponse();
//		try {
//			
//			
////			String query1 = "select uc.mobile_no,co.order_id,co.customer_id,co.order_status,co.payment_status as co_payment_status,co.booking_unit,co.edition_type,co.comments,co.rms_order_status,itm.item_id,itm.classified_type,itm.classified_ads_type,itm.scheme,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.classified_ads_sub_type,itm.status as itm_status,itm.ad_id,itm.category_group,itm.sub_group,itm.child_group,roi.no_of_insertions,roi.size_width,roi.size_height,roi.page_position,roi.format_type,roi.fixed_format,roi.page_number,roi.category_discount,roi.multi_discount,roi.additional_discount,roi.multi_discount_amount,roi.additional_discount_amount,roi.category_discount_amount,roi.base_amount,roi.grand_total,roi.discount_total,roi.gst_total,roi.premium_discount,roi.premium_discount_amount";
//			
//			
//			List<Object[]> classifiedList = new ArrayList<Object[]>();
//			String query = "select uc.mobile_no,itm.created_ts,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id ,itm.order_id, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id ,bu.booking_description,uc.gst_no,uc.customer_name,uc.email_id,uc.address_1,uc.address_2,uc.address_3,uc.pin_code,gs.state,rpr.bank_or_upi,cp.payment_method_type,itm.created_by,itm.changed_by,itm.changed_ts,uc.customer_id,co.booking_unit,coir.rate,roi.no_of_insertions,roi.size_width,roi.size_height,roi.space_width,roi.space_height,roi.page_position,itm.classified_ads_sub_type,gcast.ads_sub_type,uc.client_code,gcg.classified_group,gcsg.classified_sub_group,gcc.classified_child_group,gt.edition_type,grff.size,grft.format_type,grpp.page_name,grmd.discount,gpm.payment_method,gpm2.payment_mode,rpr.signature_id,rpr.cash_receipt_no,rpr.other_details,coir.cgst,coir.sgst,coir.igst,coir.gst_total,coir.total_discount,roi.category_discount,roi.additional_discount,roi.surcharge_rate,uc.city AS city_code,uc.state as state_code,rpr.bank_ref_id,rpr.payment_mode as pMode,rpr.payment_method as pMethod,gct.customer_type,uc.customer_type_id,itm.category_group,itm.sub_group,itm.child_group,co.edition_type as editionId,roi.fixed_format as fixedFormatId,roi.format_type as formatTypeId,roi.page_number as pageNumberId,roi.multi_discount as multiDiscountId,gpd.discount as categoryAmount,gpd.positioning_desc,roi.multi_discount_amount,roi.surcharge_amount,roi.additional_discount_amount,coir.cgst_value,coir.sgst_value,coir.igst_value,coir.amount as coirAmount,coir.rate_per_square_cms,roi.category_discount_amount from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join um_customers uc on co.customer_id = uc.customer_id  inner join gd_rms_schemes gcs on itm.scheme =gcs.id  inner join rms_order_items roi on itm.item_id=roi.item_id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type =  gcast.id inner join gd_classified_group gcg on itm.category_group=gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group=gcsg.id inner join gd_classified_child_group gcc on itm.child_group=gcc.id left join gd_rms_edition_type gt on co.edition_type=gt.id left join gd_rms_fixed_formats grff on roi.fixed_format=grff.id left join gd_rms_multi_discount grmd on roi.multi_discount=grmd.id  left join gd_rms_format_types grft on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number=grpp.id inner join rms_payments_response rpr on itm.item_id=rpr.item_id left join gd_payment_method gpm on rpr.payment_method= gpm.id left join gd_payment_mode gpm2 on rpr.payment_mode = gpm2.id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) left join gd_state gs ON uc.state = gs.state_code left join gd_customer_types gct on uc.customer_type_id=gct.id left join gd_rms_positioning_discount gpd on roi.category_discount=gpd.id where itm.created_by = "
//					+ loggedUser.getUserId() + " and itm.mark_As_delete = false and co.order_type=1  and itm.ad_id = '"
//					+ adId + "' ";
//			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
//			if (classifiedList == null || classifiedList.size() == 0) {
//				apiResponse.setStatus(1);
//				apiResponse.setMessage("No Rms orders found.");
//				return apiResponse;
//			}
//
//			List<String> itemIds = new ArrayList<String>();
//			RmsViewDetails classifieds = null;
//			InsertionObjectDisplay insertionObjectDisplay = new InsertionObjectDisplay();
//			insertionObjectDisplay.setEditions(new ArrayList<String>());
//			insertionObjectDisplay.setPublishDates(new ArrayList<String>());
//			CustomerObjectDisplay customerObjectDisplay = new CustomerObjectDisplay();
//			PaymentObjectDisplay paymentObjectDisplay = new PaymentObjectDisplay();
//
//			RmsKycAttatchments attatchments = new RmsKycAttatchments();
//			RmsRatesResponse ratesResponse = new RmsRatesResponse();
//			ratesResponse.setDiscounts(new ArrayList<RmsDiscountModel>());
//			ratesResponse.setTax(new ArrayList<RmsTaxModel>());
//			String customerId="";
//			String paymentId="";
//			String chequeId="";
//			String signId="";
//			Double additionalDiscount=0.0;
//			Double additionalDiscountAmount=0.0;
//			Double multiDiscount=0.0;
//			Double multiDiscountAmount=0.0;
//			Double categoryDiscount=0.0;
//			Double categoryDiscountAmount=0.0;
//			Double premiumDiscount=0.0;			
//			Double premiumDiscountAmount=0.0;
//
//			for (Object[] obj : classifiedList) {
//				customerObjectDisplay.setMobileNo((String) obj[0]);
//				insertionObjectDisplay.setContent((String) obj[6]);
//				insertionObjectDisplay.setItemId((String) obj[7]);
//				float floatValue = (Float) obj[2];
//				BigDecimal paidAmount = new BigDecimal(floatValue).setScale(2, RoundingMode.HALF_UP);
//				insertionObjectDisplay.setPaidAmount(paidAmount);
////				classifieds.setPaidAmount(new BigDecimal((Float) obj[2]));
//				customerObjectDisplay.setCityDesc((String) obj[13]);
//				customerObjectDisplay.setGstNo((String) obj[14]);
//				customerObjectDisplay.setClientName((String) obj[15]);
//				customerObjectDisplay.setEmailId((String) obj[16]);
//				customerObjectDisplay.setAddress1((String) obj[17]);
//				customerObjectDisplay.setPinCode((String) obj[20]);
//				customerObjectDisplay.setStateDesc((String) obj[21]);
//				paymentObjectDisplay.setReferenceId((String) obj[62]);
//				paymentObjectDisplay.setBankOrUpi((String) obj[22]);
//				insertionObjectDisplay.setFixedRate(new BigDecimal((Float) obj[29]));
//				insertionObjectDisplay.setClassifiedAdsSubTypeStr((String) obj[37]);
//				insertionObjectDisplay.setSchemeStr((String) obj[11]);
//				customerObjectDisplay.setCustomerDetails((String) obj[65]);
//				float spaceWidth = (obj[33] == null) ? 0.0f : (float) obj[33];
//				float spaceHeight = (obj[34] == null) ? 0.0f : (float) obj[34];
//				double formattedSpaceWidth = HelperUtil.parseDoubleValue((spaceWidth));
//				String spaceW = String.valueOf(formattedSpaceWidth);
//				double formattedSpaceHeight = HelperUtil.parseDoubleValue((spaceHeight));
//				String spaceH = String.valueOf(formattedSpaceHeight);
//				insertionObjectDisplay.setSpaceWidth(spaceW);
//				insertionObjectDisplay.setSpaceHeight(spaceH);
//				insertionObjectDisplay.setPagePosition((Integer) obj[35]);
//				customerObjectDisplay.setClientCode((String) obj[38]);
//				insertionObjectDisplay.setClassifiedGroupDesc((String) obj[39]);
//				insertionObjectDisplay.setClassifiedSubgroupDesc((String) obj[40]);
//				insertionObjectDisplay.setClassifiedChildgroupDesc((String) obj[41]);
//				insertionObjectDisplay.setEditionTypeDesc((String) obj[42]);
//				insertionObjectDisplay.setFixedFormatsDesc((String) obj[43]);
//				insertionObjectDisplay.setFormatTypeDesc((String) obj[44]);
//				insertionObjectDisplay.setPageNumberDesc((String) obj[45]);
//				paymentObjectDisplay.setPaymentMethodDesc((String) obj[47]);
//				paymentObjectDisplay.setPaymentModeDesc((String) obj[48]);
//				paymentObjectDisplay.setSignatureId((String) obj[49]);
//				paymentObjectDisplay.setCashReceiptNo((String) obj[50]);
//				paymentObjectDisplay.setOtherDetails((String) obj[51]);
//				float cgst = (obj[52] == null) ? 0.0f : (float) obj[52];
//				float sgst = (obj[53] == null) ? 0.0f : (float) obj[53];
//				float igst = (obj[54] == null) ? 0.0f : (float) obj[54];
//				float additional = (obj[58] == null) ? 0.0f : (float) obj[58];
//				float surChargeRate = (obj[59] == null) ? 0.0f : (float) obj[59];
////				float multiDiscount = (obj[74] == null) ? 0.0f : (float) obj[74];
////				float amount = (obj[75] == null) ? 0.0f : (float) obj[75];
////				float multiDA = (obj[77] == null) ? 0.0f : (float) obj[77];
//				float surchargeA = (obj[78] == null) ? 0.0f : (float) obj[78];
//				float addDA = (obj[79] == null) ? 0.0f : (float) obj[79];
//				float cgstVal = (obj[80] == null) ? 0.0f : (float) obj[80];
//				float sgstVal = (obj[81] == null) ? 0.0f : (float) obj[81];
//				float igstVal = (obj[82] == null) ? 0.0f : (float) obj[82];
//				float totalAmount = (obj[2] == null) ? 0.0f : (float) obj[2];
//				float amountFinal = (obj[83] == null) ? 0.0f : (float) obj[83];
//				float ratePerSC = (obj[84] == null) ? 0.0f : (float) obj[84];
//				float categoryDiscAmount = (obj[85] == null) ? 0.0f : (float) obj[85];
//				Double cGst = HelperUtil.parseDoubleValue((cgst));
//				String cgstValAsString = (cGst == null || cGst == 0.0) ? "" : String.valueOf(cGst);
//				Double sGst = HelperUtil.parseDoubleValue((sgst));
//				String sgstValAsString = (sGst == null || sGst == 0.0) ? "" : String.valueOf(sGst);
//				Double iGst = HelperUtil.parseDoubleValue((igst));
//				String igstValAsString = (iGst == null || iGst == 0.0) ? "" : String.valueOf(iGst);
//				Double additionalDiscount = HelperUtil.parseDoubleValue((additional));
//				String adAsString = (additionalDiscount == null || additionalDiscount == 0.0) ? ""
//						: String.valueOf(additionalDiscount);
//				Double surCharge = HelperUtil.parseDoubleValue((surChargeRate));
//				String surchargeValAsString = (surCharge == null || surCharge == 0.0) ? "" : String.valueOf(surCharge);
////				Double multiDisc = HelperUtil.parseDoubleValue((multiDiscount));
////				String multiDiscString = (multiDisc == null || multiDisc == 0.0) ? "" : String.valueOf(multiDisc);
////				Double amount1 = HelperUtil.parseDoubleValue((amount));
////				String amountStr = (amount1 == null || amount1 == 0.0) ? "" : String.valueOf(amount1);
////				Double multiDA1 = HelperUtil.parseDoubleValue((multiDA));
////				String multiString = (multiDA1 == null || multiDA1 == 0.0) ? "" : String.valueOf(multiDA1);
//				Double surchargeA1 = HelperUtil.parseDoubleValue((surchargeA));
//				String surchargeA2 = (surchargeA1 == null || surchargeA1 == 0.0) ? "" : String.valueOf(surchargeA1);
//				Double addDA1 = HelperUtil.parseDoubleValue((addDA));
//				String addDA2 = (addDA1 == null || addDA1 == 0.0) ? "" : String.valueOf(addDA1);
//				Double cgstVal1 = HelperUtil.parseDoubleValue((cgstVal));
//				String cgstVal2 = (cgstVal1 == null || cgstVal1 == 0.0) ? "" : String.valueOf(cgstVal1);
//				Double sgstVal1 = HelperUtil.parseDoubleValue((sgstVal));
//				String sgstVal2 = (sgstVal1 == null || sgstVal1 == 0.0) ? "" : String.valueOf(sgstVal1);
//				Double igstVal1 = HelperUtil.parseDoubleValue((igstVal));
//				String igstVal2 = (igstVal1 == null || igstVal1 == 0.0) ? "" : String.valueOf(igstVal1);
//				Double totalAmount1 = HelperUtil.parseDoubleValue((totalAmount));
//				String totalAmountStr = (totalAmount1 == null || totalAmount1 == 0.0) ? ""
//						: String.valueOf(totalAmount1);
//				Double amountFinal1 = HelperUtil.parseDoubleValue((amountFinal));
//				String finalAmount = (amountFinal1 == null || amountFinal1 == 0.0) ? "" : String.valueOf(amountFinal1);
//				Double ratePerSC1 = HelperUtil.parseDoubleValue((ratePerSC));
//				String ratePers = (ratePerSC1 == null || ratePerSC1 == 0.0) ? "" : String.valueOf(ratePerSC1);
//				Double categoryDiscAmount1 = HelperUtil.parseDoubleValue((categoryDiscAmount));
//				String categoryDiscAmount2 = (categoryDiscAmount1 == null || categoryDiscAmount1 == 0.0) ? ""
//						: String.valueOf(categoryDiscAmount1);
//
//				insertionObjectDisplay.setCgst(cgstValAsString);
//				insertionObjectDisplay.setSgst(sgstValAsString);
//				insertionObjectDisplay.setIgst(igstValAsString);
//				insertionObjectDisplay.setCategoryDiscountId((Integer) obj[57]);
//				insertionObjectDisplay.setAdditionalDiscount(adAsString);
//				insertionObjectDisplay.setSurchargeRate(surchargeValAsString);
//				customerObjectDisplay.setCityCode((String) obj[60]);
//				customerObjectDisplay.setStateCode((String) obj[61]);
//				insertionObjectDisplay.setBookingCode((Integer) obj[28]);
//				paymentObjectDisplay.setPaymentMode((Short) obj[63]);
//				paymentObjectDisplay.setPaymentMethod((Short) obj[64]);
//				insertionObjectDisplay.setCustomerTypeId((Integer) obj[66]);
//				insertionObjectDisplay.setCategoryGroup((Integer) obj[67]);
//				insertionObjectDisplay.setCategorySubGroup((Integer) obj[68]);
//				insertionObjectDisplay.setCategoryChildGroup((Integer) obj[69]);
//				insertionObjectDisplay.setEditionType((Integer) obj[70]);
//				insertionObjectDisplay.setFixedFormat((Integer) obj[71]);
//				insertionObjectDisplay.setFormatType((Integer) obj[72]);
//				insertionObjectDisplay.setPageNumber((Integer) obj[73]);
////				insertionObjectDisplay.setMultiDiscount(multiDiscString);
////				insertionObjectDisplay.setCategoryDiscount(amountStr);
//				insertionObjectDisplay.setPositioningDesc((String) obj[76]);
////				insertionObjectDisplay.setMultiDiscountAmount(multiString);
//				insertionObjectDisplay.setSurchargeAmount(surchargeA2);
//				insertionObjectDisplay.setAdditionalDiscountAmount(addDA2);
//				insertionObjectDisplay.setScheme((Integer) obj[10]);
//				insertionObjectDisplay.setClassifiedAdsSubType((Integer) obj[36]);
//				insertionObjectDisplay.setCgstValue(cgstVal2);
//				insertionObjectDisplay.setSgstValue(sgstVal2);
//				insertionObjectDisplay.setIgstValue(igstVal2);
//				insertionObjectDisplay.setTotalAmount(totalAmountStr);
//				insertionObjectDisplay.setAmount(finalAmount);
//				insertionObjectDisplay.setRatePerSquareCms(ratePers);
//				insertionObjectDisplay.setCategoryDiscountAmount(categoryDiscAmount2);
//				itemIds.add((String) obj[7]);
//			}
//
//			if (itemIds != null && !itemIds.isEmpty()) {
//				List<Object[]> editionsList = rmsClfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
//				for (Object[] clObj : editionsList) {
//					insertionObjectDisplay.getEditions().add((String) clObj[2]);
//				}
//				List<Object[]> publishDatesList = rmsClfPublishDates.getPublishDatesForErpData(itemIds);
//				for (Object[] clObj : publishDatesList) {
//					insertionObjectDisplay.getPublishDates()
//							.add(CommonUtils.dateFormatter((Date) clObj[1], "yyyy-MM-dd"));
//				}
//			}
//			classifieds = new RmsViewDetails();
//			classifieds.setInsertionObjectDisplay(insertionObjectDisplay);
//			classifieds.setCustomerObjectDisplay(customerObjectDisplay);
//			classifieds.setPaymentObjectDisplay(paymentObjectDisplay);
//
//			apiResponse.setStatus(0);
//			apiResponse.setData(classifieds);
//		} catch (NumberFormatException e) {
//			logger.error("Error while getting  order by adID:" + e.getMessage());
//			apiResponse.setStatus(1);
//			apiResponse.setErrorcode("GEN_002");
//		}
//		return apiResponse;
//
//	}
	
	
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public GenericApiResponse getRmsClassifiedsByAdId(LoggedUser loggedUser, @NotNull String adId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			
			
//			String query1 = "select uc.mobile_no,co.order_id,co.customer_id,co.order_status,co.payment_status as co_payment_status,co.booking_unit,co.edition_type,co.comments,co.rms_order_status,itm.item_id,itm.classified_type,itm.classified_ads_type,itm.scheme,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.classified_ads_sub_type,itm.status as itm_status,itm.ad_id,itm.category_group,itm.sub_group,itm.child_group,roi.no_of_insertions,roi.size_width,roi.size_height,roi.page_position,roi.format_type,roi.fixed_format,roi.page_number,roi.category_discount,roi.multi_discount,roi.additional_discount,roi.multi_discount_amount,roi.additional_discount_amount,roi.category_discount_amount,roi.base_amount,roi.grand_total,roi.discount_total,roi.gst_total,roi.premium_discount,roi.premium_discount_amount";
			
			
			List<Object[]> classifiedList = new ArrayList<Object[]>();
			String query = "";
//			String query = "select uc.mobile_no,itm.created_ts,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id ,itm.order_id, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id ,bu.booking_description,uc.gst_no,uc.customer_name,uc.email_id,uc.address_1,uc.address_2,uc.address_3,uc.pin_code,gs.state,rpr.bank_or_upi,cp.payment_method_type,itm.created_by,itm.changed_by,itm.changed_ts,uc.customer_id,co.booking_unit,coir.rate,roi.no_of_insertions,roi.size_width,roi.size_height,roi.space_width,roi.space_height,roi.page_position,itm.classified_ads_sub_type,gcast.ads_sub_type,uc.client_code,gcg.classified_group,gcsg.classified_sub_group,gcc.classified_child_group,gt.edition_type,grff.size,grft.format_type,grpp.page_name,grmd.discount,gpm.payment_method,gpm2.payment_mode,rpr.signature_id,rpr.cash_receipt_no,rpr.other_details,coir.cgst,coir.sgst,coir.igst,coir.gst_total,coir.total_discount,roi.category_discount,roi.additional_discount,roi.surcharge_rate,uc.city AS city_code,uc.state as state_code,rpr.bank_ref_id,rpr.payment_mode as pMode,rpr.payment_method as pMethod,gct.customer_type,uc.customer_type_id,itm.category_group,itm.sub_group,itm.child_group,co.edition_type as editionId,roi.fixed_format as fixedFormatId,roi.format_type as formatTypeId,roi.page_number as pageNumberId,roi.multi_discount as multiDiscountId,gpd.discount as categoryAmount,gpd.positioning_desc,roi.multi_discount_amount,roi.surcharge_amount,roi.additional_discount_amount,coir.cgst_value,coir.sgst_value,coir.igst_value,coir.amount as coirAmount,coir.rate_per_square_cms,roi.category_discount_amount from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join um_customers uc on co.customer_id = uc.customer_id  inner join gd_rms_schemes gcs on itm.scheme =gcs.id  inner join rms_order_items roi on itm.item_id=roi.item_id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type =  gcast.id inner join gd_classified_group gcg on itm.category_group=gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group=gcsg.id inner join gd_classified_child_group gcc on itm.child_group=gcc.id left join gd_rms_edition_type gt on co.edition_type=gt.id left join gd_rms_fixed_formats grff on roi.fixed_format=grff.id left join gd_rms_multi_discount grmd on roi.multi_discount=grmd.id  left join gd_rms_format_types grft on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number=grpp.id inner join rms_payments_response rpr on itm.item_id=rpr.item_id left join gd_payment_method gpm on rpr.payment_method= gpm.id left join gd_payment_mode gpm2 on rpr.payment_mode = gpm2.id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) left join gd_state gs ON uc.state = gs.state_code left join gd_customer_types gct on uc.customer_type_id=gct.id left join gd_rms_positioning_discount gpd on roi.category_discount=gpd.id"
//					+ " where itm.created_by = "
//					+ loggedUser.getUserId() + " and itm.mark_As_delete = false and co.order_type=1  and itm.ad_id = '"
//					+ adId + "' ";
			if(!loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN") ) {
				/*
				 * query =
				 * "select	uc.mobile_no,itm.created_ts,itm.status,itm.download_status ,itm.clf_content,itm.item_id ,itm.order_id,co.payment_status AS co_payment_status,itm.scheme AS itm_scheme,gcs.scheme AS gcs_scheme,itm.ad_id ,bu.booking_description,uc.gst_no,uc.customer_name,uc.email_id,uc.address_1,uc.address_2,uc.address_3,uc.pin_code,gs.state,rpr.bank_or_upi,itm.created_by,itm.changed_by,itm.changed_ts,uc.customer_id,co.booking_unit,roi.no_of_insertions,roi.size_width,roi.size_height,roi.space_width,roi.space_height,roi.page_position,itm.classified_ads_sub_type,	gcast.ads_sub_type,uc.client_code,gcg.classified_group,gcsg.classified_sub_group,gcc.classified_child_group,gt.edition_type,grff.size,grft.format_type,grpp.page_name,grmd.discount,gpm.payment_method,gpm2.payment_mode,rpr.signature_id,rpr.cash_receipt_no,rpr.other_details,roi.category_discount,roi.additional_discount,roi.surcharge_rate,uc.city AS city_code,uc.state as state_code,rpr.bank_ref_id,rpr.payment_mode as pMode,rpr.payment_method as pMethod,gct.customer_type,uc.customer_type_id,itm.category_group,itm.sub_group,itm.child_group,co.edition_type as editionId,roi.fixed_format as fixedFormatId,roi.format_type as formatTypeId,roi.page_number as pageNumberId,roi.multi_discount as multiDiscountId,gpd.discount as categoryAmount,gpd.positioning_desc,roi.multi_discount_amount,roi.surcharge_amount,roi.additional_discount_amount,roi.category_discount_amount,itm.classified_type ,gct2.type,roi.rms_item_id,roi.base_amount ,roi.grand_total ,roi.discount_total ,roi.gst_total ,roi.premium_discount,roi.premium_discount_amount ,rpr.payment_att_id ,rpr.cheque_att_id ,gpd.type_of_position,co.customer_id as order_cus_id,roi.ad_type,roi.caption,grat.add_type as grat_add_type,uc.mobile_alt,uc.house_no,roi.igst_amount,roi.sgst_amount,roi.cgst_amount,co.lat,co.lang,roi.billable_days,roi.aggred_premium_dis_per,roi.master_premium_per,roi.premium_amount,co.wf_id from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc  on co.customer_id = uc.customer_id  inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join rms_order_items roi on itm.item_id=roi.item_id inner join gd_classified_ads_sub_types gcast on  itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_edition_type gt on co.edition_type=gt.id left join gd_rms_fixed_formats grff on roi.fixed_format = grff.id left join gd_rms_multi_discount grmd on roi.multi_discount=grmd.id left join gd_rms_format_types grft  on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join rms_payments_response rpr on itm.item_id=rpr.item_id left join gd_payment_method gpm on rpr.payment_method= gpm.id left join gd_payment_mode gpm2 on rpr.payment_mode = gpm2.id left join booking_units bu  ON  uc.city = CAST(bu.booking_code AS VARCHAR) left join gd_state gs ON uc.state = gs.state_code left join gd_customer_types gct on uc.customer_type_id=gct.id  left join gd_rms_positioning_discount gpd on roi.page_position=gpd.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join gd_classified_types gct2 on itm.classified_type = gct2.id"
				 * + " where itm.created_by = " + loggedUser.getUserId() +
				 * " and itm.mark_As_delete = false and co.order_type=1  and itm.ad_id = '" +
				 * adId + "' ";
				 */
				query = "select	uc.mobile_no,itm.created_ts,itm.status,itm.download_status ,itm.clf_content,itm.item_id ,itm.order_id,co.payment_status AS co_payment_status,itm.scheme AS itm_scheme,gcs.scheme AS gcs_scheme,itm.ad_id ,uc.gst_no,uc.customer_name,uc.email_id,uc.address_1,uc.address_2,uc.address_3,uc.pin_code,gs.state,rpr.bank_or_upi,itm.created_by,itm.changed_by,itm.changed_ts,uc.customer_id,co.booking_unit,roi.no_of_insertions,roi.size_width,roi.size_height,roi.space_width,roi.space_height,roi.page_position,itm.classified_ads_sub_type,	gcast.ads_sub_type,uc.client_code,gcg.classified_group,gcsg.classified_sub_group,gcc.classified_child_group,gt.edition_type,grff.size,grft.format_type,grpp.page_name,grmd.discount,gpm.payment_method,gpm2.payment_mode,rpr.signature_id,rpr.cash_receipt_no,rpr.other_details,roi.category_discount,roi.additional_discount,roi.surcharge_rate,uc.city AS city_code,uc.state as state_code,rpr.bank_ref_id,rpr.payment_mode as pMode,rpr.payment_method as pMethod,gct.customer_type,uc.customer_type_id,itm.category_group,itm.sub_group,itm.child_group,co.edition_type as editionId,roi.fixed_format as fixedFormatId,roi.format_type as formatTypeId,roi.page_number as pageNumberId,roi.multi_discount as multiDiscountId,gpd.discount as categoryAmount,gpd.positioning_desc,roi.multi_discount_amount,roi.surcharge_amount,roi.additional_discount_amount,roi.category_discount_amount,itm.classified_type ,gct2.type,roi.rms_item_id,roi.base_amount ,roi.grand_total ,roi.discount_total ,roi.gst_total ,roi.premium_discount,roi.premium_discount_amount ,rpr.payment_att_id ,rpr.cheque_att_id ,gpd.type_of_position,co.customer_id as order_cus_id,roi.ad_type,roi.caption,grat.add_type as grat_add_type,uc.mobile_alt,uc.house_no,roi.igst_amount,roi.sgst_amount,roi.cgst_amount,co.lat,co.lang,roi.billable_days,roi.aggred_premium_dis_per,roi.master_premium_per,roi.premium_amount,co.wf_id from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc  on co.customer_id = uc.customer_id  inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join rms_order_items roi on itm.item_id=roi.item_id inner join gd_classified_ads_sub_types gcast on  itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_edition_type gt on co.edition_type=gt.id left join gd_rms_fixed_formats grff on roi.fixed_format = grff.id left join gd_rms_multi_discount grmd on roi.multi_discount=grmd.id left join gd_rms_format_types grft  on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join rms_payments_response rpr on itm.item_id=rpr.item_id left join gd_payment_method gpm on rpr.payment_method= gpm.id left join gd_payment_mode gpm2 on rpr.payment_mode = gpm2.id left join gd_state gs ON uc.state = gs.state_code left join gd_customer_types gct on uc.customer_type_id=gct.id  left join gd_rms_positioning_discount gpd on roi.page_position=gpd.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join gd_classified_types gct2 on itm.classified_type = gct2.id"
						+ " where itm.created_by = "
						+ loggedUser.getUserId() + " and itm.mark_As_delete = false and co.order_type=1  and itm.ad_id = '"
						+ adId + "' ";
			}else if(loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN")) {
				/*
				 * query =
				 * "select	uc.mobile_no,itm.created_ts,itm.status,itm.download_status ,itm.clf_content,itm.item_id ,itm.order_id,co.payment_status AS co_payment_status,itm.scheme AS itm_scheme,gcs.scheme AS gcs_scheme,itm.ad_id ,bu.booking_description,uc.gst_no,uc.customer_name,uc.email_id,uc.address_1,uc.address_2,uc.address_3,uc.pin_code,gs.state,rpr.bank_or_upi,itm.created_by,itm.changed_by,itm.changed_ts,uc.customer_id,co.booking_unit,roi.no_of_insertions,roi.size_width,roi.size_height,roi.space_width,roi.space_height,roi.page_position,itm.classified_ads_sub_type,	gcast.ads_sub_type,uc.client_code,gcg.classified_group,gcsg.classified_sub_group,gcc.classified_child_group,gt.edition_type,grff.size,grft.format_type,grpp.page_name,grmd.discount,gpm.payment_method,gpm2.payment_mode,rpr.signature_id,rpr.cash_receipt_no,rpr.other_details,roi.category_discount,roi.additional_discount,roi.surcharge_rate,uc.city AS city_code,uc.state as state_code,rpr.bank_ref_id,rpr.payment_mode as pMode,rpr.payment_method as pMethod,gct.customer_type,uc.customer_type_id,itm.category_group,itm.sub_group,itm.child_group,co.edition_type as editionId,roi.fixed_format as fixedFormatId,roi.format_type as formatTypeId,roi.page_number as pageNumberId,roi.multi_discount as multiDiscountId,gpd.discount as categoryAmount,gpd.positioning_desc,roi.multi_discount_amount,roi.surcharge_amount,roi.additional_discount_amount,roi.category_discount_amount,itm.classified_type ,gct2.type,roi.rms_item_id,roi.base_amount ,roi.grand_total ,roi.discount_total ,roi.gst_total ,roi.premium_discount,roi.premium_discount_amount ,rpr.payment_att_id ,rpr.cheque_att_id ,gpd.type_of_position,co.customer_id as order_cus_id,roi.ad_type,roi.caption,grat.add_type as grat_add_type,uc.mobile_alt,uc.house_no,roi.igst_amount,roi.sgst_amount,roi.cgst_amount,co.lat,co.lang,roi.billable_days,roi.aggred_premium_dis_per,roi.master_premium_per,roi.premium_amount,co.wf_id from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc  on co.customer_id = uc.customer_id  inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join rms_order_items roi on itm.item_id=roi.item_id inner join gd_classified_ads_sub_types gcast on  itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_edition_type gt on co.edition_type=gt.id left join gd_rms_fixed_formats grff on roi.fixed_format = grff.id left join gd_rms_multi_discount grmd on roi.multi_discount=grmd.id left join gd_rms_format_types grft  on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join rms_payments_response rpr on itm.item_id=rpr.item_id left join gd_payment_method gpm on rpr.payment_method= gpm.id left join gd_payment_mode gpm2 on rpr.payment_mode = gpm2.id left join booking_units bu  ON  uc.city = CAST(bu.booking_code AS VARCHAR) left join gd_state gs ON uc.state = gs.state_code left join gd_customer_types gct on uc.customer_type_id=gct.id  left join gd_rms_positioning_discount gpd on roi.page_position=gpd.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join gd_classified_types gct2 on itm.classified_type = gct2.id and itm.mark_As_delete = false and co.order_type=1  and itm.ad_id = '"
				 * + adId + "' ";
				 */
				query = "select	uc.mobile_no,itm.created_ts,itm.status,itm.download_status ,itm.clf_content,itm.item_id ,itm.order_id,co.payment_status AS co_payment_status,itm.scheme AS itm_scheme,gcs.scheme AS gcs_scheme,itm.ad_id ,uc.gst_no,uc.customer_name,uc.email_id,uc.address_1,uc.address_2,uc.address_3,uc.pin_code,gs.state,rpr.bank_or_upi,itm.created_by,itm.changed_by,itm.changed_ts,uc.customer_id,co.booking_unit,roi.no_of_insertions,roi.size_width,roi.size_height,roi.space_width,roi.space_height,roi.page_position,itm.classified_ads_sub_type,	gcast.ads_sub_type,uc.client_code,gcg.classified_group,gcsg.classified_sub_group,gcc.classified_child_group,gt.edition_type,grff.size,grft.format_type,grpp.page_name,grmd.discount,gpm.payment_method,gpm2.payment_mode,rpr.signature_id,rpr.cash_receipt_no,rpr.other_details,roi.category_discount,roi.additional_discount,roi.surcharge_rate,uc.city AS city_code,uc.state as state_code,rpr.bank_ref_id,rpr.payment_mode as pMode,rpr.payment_method as pMethod,gct.customer_type,uc.customer_type_id,itm.category_group,itm.sub_group,itm.child_group,co.edition_type as editionId,roi.fixed_format as fixedFormatId,roi.format_type as formatTypeId,roi.page_number as pageNumberId,roi.multi_discount as multiDiscountId,gpd.discount as categoryAmount,gpd.positioning_desc,roi.multi_discount_amount,roi.surcharge_amount,roi.additional_discount_amount,roi.category_discount_amount,itm.classified_type ,gct2.type,roi.rms_item_id,roi.base_amount ,roi.grand_total ,roi.discount_total ,roi.gst_total ,roi.premium_discount,roi.premium_discount_amount ,rpr.payment_att_id ,rpr.cheque_att_id ,gpd.type_of_position,co.customer_id as order_cus_id,roi.ad_type,roi.caption,grat.add_type as grat_add_type,uc.mobile_alt,uc.house_no,roi.igst_amount,roi.sgst_amount,roi.cgst_amount,co.lat,co.lang,roi.billable_days,roi.aggred_premium_dis_per,roi.master_premium_per,roi.premium_amount,co.wf_id from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc  on co.customer_id = uc.customer_id  inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join rms_order_items roi on itm.item_id=roi.item_id inner join gd_classified_ads_sub_types gcast on  itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_edition_type gt on co.edition_type=gt.id left join gd_rms_fixed_formats grff on roi.fixed_format = grff.id left join gd_rms_multi_discount grmd on roi.multi_discount=grmd.id left join gd_rms_format_types grft  on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join rms_payments_response rpr on itm.item_id=rpr.item_id left join gd_payment_method gpm on rpr.payment_method= gpm.id left join gd_payment_mode gpm2 on rpr.payment_mode = gpm2.id left join gd_state gs ON uc.state = gs.state_code left join gd_customer_types gct on uc.customer_type_id=gct.id  left join gd_rms_positioning_discount gpd on roi.page_position=gpd.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join gd_classified_types gct2 on itm.classified_type = gct2.id and itm.mark_As_delete = false and co.order_type=1  and itm.ad_id = '"
						+ adId + "' ";
			}
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			if (classifiedList == null || classifiedList.size() == 0) {
				apiResponse.setStatus(1);
				apiResponse.setMessage("No Rms orders found.");
				return apiResponse;
			}

			List<String> itemIds = new ArrayList<String>();
			RmsViewDetails classifieds = null;
			InsertionObjectDisplay insertionObjectDisplay = new InsertionObjectDisplay();
			insertionObjectDisplay.setEditions(new ArrayList<String>());
			insertionObjectDisplay.setEditionIds(new ArrayList<Integer>());
			insertionObjectDisplay.setPublishDates(new ArrayList<String>());
			insertionObjectDisplay.setDiscounts(new ArrayList<Integer>());
			insertionObjectDisplay.setDiscountsDesc(new ArrayList<String>());
			CustomerObjectDisplay customerObjectDisplay = new CustomerObjectDisplay();
			PaymentObjectDisplay paymentObjectDisplay = new PaymentObjectDisplay();
			RmsKycAttatchments attatchments = new RmsKycAttatchments();
			RmsRatesResponse ratesResponse = new RmsRatesResponse();
			ratesResponse.setDiscounts(new ArrayList<RmsDiscountModel>());
			ratesResponse.setTax(new ArrayList<RmsTaxModel>());
			ratesResponse.setPremium(new ArrayList<RmsPremiumModel>());
			String customerId="";
			String paymentId="";
			String chequeId="";
			String signId="";
			String orderId="";
			String lat="";
			String lang="";
			Integer bookingCode = 0;
			String itemId="";
			Double additionalDiscount=0.0;
			Double additionalDiscountAmount=0.0;
			Double multiDiscount=0.0;
			Double multiDiscountAmount=0.0;
			Double categoryDiscount=0.0;
			Double categoryDiscountAmount=0.0;
			Double premiumDiscount=0.0;
			Double igstAmount=0.0;
			Double sgstAmount=0.0;
			Double cgstAmount=0.0;
			Double formattedSpaceWidth=0.0;
			Double formattedSpaceHeight=0.0;
			Double agreedPremiumDisPer=0.0;
			Double masterPremiumPer=0.0;
			Double premiumAmount=0.0;
			Double additionalD=0.0;
			
			
			Double premiumDiscountAmount=0.0;
			for (Object[] obj : classifiedList) {
				customerObjectDisplay.setMobileNo((String) obj[0]);
				insertionObjectDisplay.setContent((String) obj[4]);
				insertionObjectDisplay.setItemId((String) obj[5]);
				itemId=(String) obj[5];
				insertionObjectDisplay.setStatus((String) obj[2]);
				insertionObjectDisplay.setOrderId((String) obj[6]);;
				orderId = (String) obj[6];
				insertionObjectDisplay.setPaymentStatus((String) obj[7]);	
				//customerObjectDisplay.setCustomerTypeId((Integer) obj[57]);
				customerObjectDisplay.setCustomerTypeId((Integer) obj[56]);
//				float floatValue = (Float) obj[2];
//				BigDecimal paidAmount = new BigDecimal(floatValue).setScale(2, RoundingMode.HALF_UP);
//				insertionObjectDisplay.setPaidAmount(paidAmount);
//				classifieds.setPaidAmount(new BigDecimal((Float) obj[2]));
				//customerObjectDisplay.setCityDesc((String) obj[11]);
				customerObjectDisplay.setCityDesc((String) obj[50]);
				//customerObjectDisplay.setGstNo((String) obj[12]);
				customerObjectDisplay.setGstNo((String) obj[11]);
				//customerObjectDisplay.setClientName((String) obj[13]);
				customerObjectDisplay.setClientName((String) obj[12]);
				//customerObjectDisplay.setEmailId((String) obj[14]);//upto date
				customerObjectDisplay.setEmailId((String) obj[13]);
				//customerObjectDisplay.setAddress1((String) obj[15]);
				customerObjectDisplay.setAddress1((String) obj[14]);
				//customerObjectDisplay.setPinCode((String) obj[18]);
				customerObjectDisplay.setPinCode((String) obj[17]);
				//customerObjectDisplay.setStateDesc((String) obj[19]);
				customerObjectDisplay.setStateDesc((String) obj[18]);
				//paymentObjectDisplay.setReferenceId((String) obj[53]);
				paymentObjectDisplay.setReferenceId((String) obj[52]);
				//paymentObjectDisplay.setBankOrUpi((String) obj[20]);
				paymentObjectDisplay.setBankOrUpi((String) obj[19]);
//				insertionObjectDisplay.setFixedRate(new BigDecimal((Float) obj[29]));
				//insertionObjectDisplay.setClassifiedAdsSubTypeStr((String) obj[33]);
				insertionObjectDisplay.setClassifiedAdsSubTypeStr((String) obj[32]);
				insertionObjectDisplay.setSchemeStr((String) obj[9]);
				//customerObjectDisplay.setCustomerDetails((String) obj[56]);
				customerObjectDisplay.setCustomerDetails((String) obj[55]);
				//float spaceWidth = (obj[27] == null) ? 0.0f : (float) obj[27];
				float spaceWidth = (obj[26] == null) ? 0.0f : (float) obj[26];
				//float spaceHeight = (obj[28] == null) ? 0.0f : (float) obj[28];
				float spaceHeight = (obj[27] == null) ? 0.0f : (float) obj[27];
				formattedSpaceWidth = HelperUtil.parseDoubleValue((spaceWidth));
				String spaceW = String.valueOf(formattedSpaceWidth);
				formattedSpaceHeight = HelperUtil.parseDoubleValue((spaceHeight));
				String spaceH = String.valueOf(formattedSpaceHeight);
				insertionObjectDisplay.setSpaceWidth(spaceW);
				insertionObjectDisplay.setSpaceHeight(spaceH);
				//insertionObjectDisplay.setPagePosition((Integer) obj[31]);
				insertionObjectDisplay.setPagePosition((Integer) obj[30]);
				//customerObjectDisplay.setClientCode((String) obj[34]);
				customerObjectDisplay.setClientCode((String) obj[33]);
				//insertionObjectDisplay.setCategoryGroupDesc((String) obj[35]);
				insertionObjectDisplay.setCategoryGroupDesc((String) obj[34]);
				//insertionObjectDisplay.setCategorySubgroupDesc((String) obj[36]);
				insertionObjectDisplay.setCategorySubgroupDesc((String) obj[35]);
				//insertionObjectDisplay.setCategoryChildgroupDesc((String) obj[37]);
				insertionObjectDisplay.setCategoryChildgroupDesc((String) obj[36]);
				//insertionObjectDisplay.setEditionTypeDesc((String) obj[38]);
				insertionObjectDisplay.setEditionTypeDesc((String) obj[37]);
				//insertionObjectDisplay.setFixedFormatsDesc((String) obj[39]);
				insertionObjectDisplay.setFixedFormatsDesc((String) obj[38]);
				//insertionObjectDisplay.setFormatTypeDesc((String) obj[40]);
				insertionObjectDisplay.setFormatTypeDesc((String) obj[39]);
				//insertionObjectDisplay.setPageNumberDesc((String) obj[41]);
				insertionObjectDisplay.setPageNumberDesc((String) obj[40]);
				//paymentObjectDisplay.setPaymentMethodDesc((String) obj[43]);
				paymentObjectDisplay.setPaymentMethodDesc((String) obj[42]);
				//paymentObjectDisplay.setPaymentModeDesc((String) obj[44]);
				paymentObjectDisplay.setPaymentModeDesc((String) obj[43]);
//				paymentObjectDisplay.setSignatureId((String) obj[45]);
				//paymentObjectDisplay.setCashReceiptNo((String) obj[46]);
				paymentObjectDisplay.setCashReceiptNo((String) obj[45]);
				//paymentObjectDisplay.setOtherDetails((String) obj[47]);
				paymentObjectDisplay.setOtherDetails((String) obj[46]);
//				float cgst = (obj[52] == null) ? 0.0f : (float) obj[52];
//				float sgst = (obj[53] == null) ? 0.0f : (float) obj[53];
//				float igst = (obj[54] == null) ? 0.0f : (float) obj[54];
				//float additional = (obj[49] == null) ? 0.0f : (float) obj[49];
				float additional = (obj[48] == null) ? 0.0f : (float) obj[48];
				//float surChargeRate = (obj[50] == null) ? 0.0f : (float) obj[50];
				float surChargeRate = (obj[49] == null) ? 0.0f : (float) obj[49];
//				float multiDiscount = (obj[74] == null) ? 0.0f : (float) obj[74];
//				float amount = (obj[75] == null) ? 0.0f : (float) obj[75];
//				float multiDA = (obj[77] == null) ? 0.0f : (float) obj[77];
				//float surchargeA = (obj[69] == null) ? 0.0f : (float) obj[69];
				float surchargeA = (obj[68] == null) ? 0.0f : (float) obj[68];
				//float addDA = (obj[70] == null) ? 0.0f : (float) obj[70];
				float addDA = (obj[69] == null) ? 0.0f : (float) obj[69];
//				float cgstVal = (obj[80] == null) ? 0.0f : (float) obj[80];
//				float sgstVal = (obj[81] == null) ? 0.0f : (float) obj[81];
//				float igstVal = (obj[82] == null) ? 0.0f : (float) obj[82];
//				float totalAmount = (obj[2] == null) ? 0.0f : (float) obj[2];
//				float amountFinal = (obj[83] == null) ? 0.0f : (float) obj[83];
//				float ratePerSC = (obj[84] == null) ? 0.0f : (float) obj[84];
				//float categoryDiscAmount = (obj[71] == null) ? 0.0f : (float) obj[71];
				float categoryDiscAmount = (obj[70] == null) ? 0.0f : (float) obj[70];
//				Double cGst = HelperUtil.parseDoubleValue((cgst));
//				String cgstValAsString = (cGst == null || cGst == 0.0) ? "" : String.valueOf(cGst);
//				Double sGst = HelperUtil.parseDoubleValue((sgst));
//				String sgstValAsString = (sGst == null || sGst == 0.0) ? "" : String.valueOf(sGst);
//				Double iGst = HelperUtil.parseDoubleValue((igst));
//				String igstValAsString = (iGst == null || iGst == 0.0) ? "" : String.valueOf(iGst);
				additionalD = HelperUtil.parseDoubleValue((additional));
				String adAsString = (additionalD == null || additionalD == 0.0) ? ""
						: String.valueOf(additionalD);
				Double surCharge = HelperUtil.parseDoubleValue((surChargeRate));
				String surchargeValAsString = (surCharge == null || surCharge == 0.0) ? "" : String.valueOf(surCharge);
//				Double multiDisc = HelperUtil.parseDoubleValue((multiDiscount));
//				String multiDiscString = (multiDisc == null || multiDisc == 0.0) ? "" : String.valueOf(multiDisc);
//				Double amount1 = HelperUtil.parseDoubleValue((amount));
//				String amountStr = (amount1 == null || amount1 == 0.0) ? "" : String.valueOf(amount1);
//				Double multiDA1 = HelperUtil.parseDoubleValue((multiDA));
//				String multiString = (multiDA1 == null || multiDA1 == 0.0) ? "" : String.valueOf(multiDA1);
				Double surchargeA1 = HelperUtil.parseDoubleValue((surchargeA));
				String surchargeA2 = (surchargeA1 == null || surchargeA1 == 0.0) ? "" : String.valueOf(surchargeA1);
				Double addDA1 = HelperUtil.parseDoubleValue((addDA));
				String addDA2 = (addDA1 == null || addDA1 == 0.0) ? "" : String.valueOf(addDA1);
//				Double cgstVal1 = HelperUtil.parseDoubleValue((cgstVal));
//				String cgstVal2 = (cgstVal1 == null || cgstVal1 == 0.0) ? "" : String.valueOf(cgstVal1);
//				Double sgstVal1 = HelperUtil.parseDoubleValue((sgstVal));
//				String sgstVal2 = (sgstVal1 == null || sgstVal1 == 0.0) ? "" : String.valueOf(sgstVal1);
//				Double igstVal1 = HelperUtil.parseDoubleValue((igstVal));
//				String igstVal2 = (igstVal1 == null || igstVal1 == 0.0) ? "" : String.valueOf(igstVal1);
//				Double totalAmount1 = HelperUtil.parseDoubleValue((totalAmount));
//				String totalAmountStr = (totalAmount1 == null || totalAmount1 == 0.0) ? ""
//						: String.valueOf(totalAmount1);
//				Double amountFinal1 = HelperUtil.parseDoubleValue((amountFinal));
//				String finalAmount = (amountFinal1 == null || amountFinal1 == 0.0) ? "" : String.valueOf(amountFinal1);
//				Double ratePerSC1 = HelperUtil.parseDoubleValue((ratePerSC));
//				String ratePers = (ratePerSC1 == null || ratePerSC1 == 0.0) ? "" : String.valueOf(ratePerSC1);
				Double categoryDiscAmount1 = HelperUtil.parseDoubleValue((categoryDiscAmount));
				String categoryDiscAmount2 = (categoryDiscAmount1 == null || categoryDiscAmount1 == 0.0) ? ""
						: String.valueOf(categoryDiscAmount1);

//				insertionObjectDisplay.setCgst(cgstValAsString);
//				insertionObjectDisplay.setSgst(sgstValAsString);
//				insertionObjectDisplay.setIgst(igstValAsString);
				//float cD = (obj[48] == null) ? 0.0f : (float) obj[48];
				float cD = (obj[47] == null) ? 0.0f : (float) obj[47];
				double categoryDisc = HelperUtil.parseDoubleValue((cD));
				String cDiscId = String.valueOf(categoryDisc);
				insertionObjectDisplay.setCategoryDiscountId(cDiscId);
				insertionObjectDisplay.setAdditionalDiscount(adAsString);
//				insertionObjectDisplay.setSurchargeRate(surchargeValAsString);
				//customerObjectDisplay.setCityCode((String) obj[51]);
				customerObjectDisplay.setCityCode((String) obj[50]);
				//customerObjectDisplay.setStateCode((String) obj[52]);
				customerObjectDisplay.setStateCode((String) obj[51]);
				//insertionObjectDisplay.setBookingCode((Integer) obj[25]);
				insertionObjectDisplay.setBookingCode((Integer) obj[24]);
				bookingCode = (Integer) obj[24];
				//paymentObjectDisplay.setPaymentMode((Short) obj[54]);
				paymentObjectDisplay.setPaymentMode((Short) obj[53]);
				//paymentObjectDisplay.setPaymentMethod((Short) obj[55]);
				paymentObjectDisplay.setPaymentMethod((Short) obj[54]);
				//insertionObjectDisplay.setCustomerTypeId((Integer) obj[57]);
				insertionObjectDisplay.setCustomerTypeId((Integer) obj[56]);
				//insertionObjectDisplay.setCategoryGroup((Integer) obj[58]);
				insertionObjectDisplay.setCategoryGroup((Integer) obj[57]);
				//insertionObjectDisplay.setCategorySubGroup((Integer) obj[59]);
				insertionObjectDisplay.setCategorySubGroup((Integer) obj[58]);
				//insertionObjectDisplay.setCategoryChildGroup((Integer) obj[60]);
				insertionObjectDisplay.setCategoryChildGroup((Integer) obj[59]);
				//insertionObjectDisplay.setEditionType((Integer) obj[61]);
				insertionObjectDisplay.setEditionType((Integer) obj[60]);
				//insertionObjectDisplay.setFixedFormat((Integer) obj[62]);
				insertionObjectDisplay.setFixedFormat((Integer) obj[61]);
				//insertionObjectDisplay.setFormatType((Integer) obj[63]);
				insertionObjectDisplay.setFormatType((Integer) obj[62]);
				//insertionObjectDisplay.setPageNumber((Integer) obj[64]);
				insertionObjectDisplay.setPageNumber((Integer) obj[63]);
//				insertionObjectDisplay.setMultiDiscount(multiDiscString);
//				insertionObjectDisplay.setCategoryDiscount(amountStr);
//				insertionObjectDisplay.setPositioningDesc((String) obj[67]);
				insertionObjectDisplay.setPositioningDesc((String) obj[66]);
//				insertionObjectDisplay.setMultiDiscountAmount(multiString);
//				insertionObjectDisplay.setSurchargeAmount(surchargeA2);
				insertionObjectDisplay.setAdditionalDiscountAmount(addDA2);
				insertionObjectDisplay.setScheme((Integer) obj[8]);
				//insertionObjectDisplay.setClassifiedAdsSubType((Integer) obj[32]);
				insertionObjectDisplay.setClassifiedAdsSubType((Integer) obj[31]);
//				insertionObjectDisplay.setCgstValue(cgstVal2);
//				insertionObjectDisplay.setSgstValue(sgstVal2);
//				insertionObjectDisplay.setIgstValue(igstVal2);
//				insertionObjectDisplay.setTotalAmount(totalAmountStr);
//				insertionObjectDisplay.setAmount(finalAmount);
//				insertionObjectDisplay.setRatePerSquareCms(ratePers);
				insertionObjectDisplay.setCategoryDiscountAmount(categoryDiscAmount2);
				//insertionObjectDisplay.setClassifiedType((Integer) obj[72]);
				insertionObjectDisplay.setClassifiedType((Integer) obj[71]);
				//insertionObjectDisplay.setClassifiedTypeDesc((String) obj[73]);
				insertionObjectDisplay.setClassifiedTypeDesc((String) obj[72]);
				//insertionObjectDisplay.setRmsItemId((String) obj[74]);
				insertionObjectDisplay.setRmsItemId((String) obj[73]);
				//float base = (obj[75] == null) ? 0.0f : (float) obj[75];
				float base = (obj[74] == null) ? 0.0f : (float) obj[74];
				double baseAmoun = HelperUtil.parseDoubleValue((base));
				String baseAmount = String.valueOf(baseAmoun);
//				float gT = (obj[76] == null) ? 0.0f : (float) obj[76];
				float gT = (obj[75] == null) ? 0.0f : (float) obj[75];
				double grand = HelperUtil.parseDoubleValue((gT));
				String gTotal = String.valueOf(grand);
				insertionObjectDisplay.setBaseAmount(baseAmount);
				insertionObjectDisplay.setGrandTotal(gTotal);
//				float dT = (obj[77] == null) ? 0.0f : (float) obj[77];
				float dT = (obj[76] == null) ? 0.0f : (float) obj[76];
				double dTotal = HelperUtil.parseDoubleValue((dT));
				String discountTotal = String.valueOf(dTotal);
				insertionObjectDisplay.setDiscountTotal(discountTotal);
				//float gst = (obj[78] == null) ? 0.0f : (float) obj[78];
				float gst = (obj[77] == null) ? 0.0f : (float) obj[77];
				double gstTotal = HelperUtil.parseDoubleValue((gst));
				String gstTot = String.valueOf(gstTotal);
				insertionObjectDisplay.setGstTotal(gstTot);
				//float pD = (obj[79] == null) ? 0.0f : (float) obj[79];
				float pD = (obj[78] == null) ? 0.0f : (float) obj[78];
				double premium = HelperUtil.parseDoubleValue((pD));
				String pDiscount = String.valueOf(premium);
				insertionObjectDisplay.setPremiumTotal(pDiscount);
//				float pDAmount = (obj[78] == null) ? 0.0f : (float) obj[80];
				float pDAmount = (obj[79] == null) ? 0.0f : (float) obj[79];
				double pDA = HelperUtil.parseDoubleValue((pDAmount));
				String pDsicuntAmount = String.valueOf(pDA);
				insertionObjectDisplay.setPremiumDiscountAmount(pDsicuntAmount);
//				insertionObjectDisplay.setPaymentAttachmentid((String) obj[81]);
//				insertionObjectDisplay.setChequeAttachmentid((String) obj[82]);
//				insertionObjectDisplay.setPagePositionDesc((String) obj[83]);
				insertionObjectDisplay.setPagePositionDesc((String) obj[82]);
//				ratesResponse.setAmount(((Float) obj[75]) !=null?((Float) obj[75]).doubleValue():null);
				ratesResponse.setAmount(((Float) obj[74]) !=null?((Float) obj[74]).doubleValue():null);
				ratesResponse.setAmountString(String.format("%.2f", ratesResponse.getAmount()));
//				ratesResponse.setGrandTotal(((Float) obj[76]) != null?((Float) obj[76]).doubleValue():null);
				ratesResponse.setGrandTotal(((Float) obj[75]) != null?((Float) obj[75]).doubleValue():null);
				ratesResponse.setGrandTotalString(String.format("%.2f", ratesResponse.getGrandTotal()));
//				ratesResponse.setGstTotal(((Float) obj[78]) != null?((Float) obj[78]).doubleValue():null);
				ratesResponse.setGstTotal(((Float) obj[77]) != null?((Float) obj[77]).doubleValue():null);
				ratesResponse.setGstTotalString(String.format("%.2f", ratesResponse.getGstTotal()));
//				ratesResponse.setDiscountTotal(((Float) obj[77]) != null ?((Float) obj[77]).doubleValue():null);
				ratesResponse.setDiscountTotal(((Float) obj[76]) != null ?((Float) obj[76]).doubleValue():null);
				ratesResponse.setDiscountTotalString(String.format("%.2f", ratesResponse.getDiscountTotal()));;
//				customerId=(String) obj[84];
				customerId=(String) obj[83];
//				paymentId=(String) obj[81];
				paymentId=(String) obj[80];
//				chequeId=(String) obj[82];
				chequeId=(String) obj[81];
				//signId=(String) obj[45];
				signId=(String) obj[44];
//				additionalDiscount=((Float) obj[49]) != null?((Float) obj[49]).doubleValue():null;
				additionalDiscount=((Float) obj[48]) != null?((Float) obj[48]).doubleValue():null;
//				additionalDiscountAmount=((Float) obj[70]) != null ?((Float) obj[70]).doubleValue():null;
				additionalDiscountAmount=((Float) obj[69]) != null ?((Float) obj[69]).doubleValue():null;
//				multiDiscount=((Float) obj[65]) != null?((Float) obj[65]).doubleValue():null;
				multiDiscount=((Float) obj[64]) != null?((Float) obj[64]).doubleValue():null;
//				multiDiscountAmount =((Float) obj[68]) != null?((Float) obj[68]).doubleValue():null;
				multiDiscountAmount =((Float) obj[67]) != null?((Float) obj[67]).doubleValue():null;
//				categoryDiscount=((Float) obj[48]) != null?((Float) obj[48]).doubleValue():null;
				categoryDiscount=((Float) obj[47]) != null?((Float) obj[47]).doubleValue():null;
//				categoryDiscountAmount =((Float) obj[71]) != null ?((Float) obj[71]).doubleValue():null;
				categoryDiscountAmount =((Float) obj[70]) != null ?((Float) obj[70]).doubleValue():null;
//				premiumDiscount = ((Float) obj[79]) != null?((Float) obj[79]).doubleValue():null;
				premiumDiscount = ((Float) obj[78]) != null?((Float) obj[78]).doubleValue():null;
//				premiumDiscountAmount =((Float) obj[80]) != null?((Float) obj[80]).doubleValue():null;
				premiumDiscountAmount =((Float) obj[79]) != null?((Float) obj[79]).doubleValue():null;
				itemIds.add((String) obj[5]);
				
//				insertionObjectDisplay.setAddType((Integer) obj[85]);
				insertionObjectDisplay.setAddType((Integer) obj[84]);
//				insertionObjectDisplay.setAddTypeDesc((String) obj[87]);
				insertionObjectDisplay.setAddTypeDesc((String) obj[86]);
//				insertionObjectDisplay.setCaption((String) obj[86]);
				insertionObjectDisplay.setCaption((String) obj[85]);
//				customerObjectDisplay.setMobileAlt((String) obj[88]);
				customerObjectDisplay.setMobileAlt((String) obj[87]);
//				customerObjectDisplay.setHouseNo((String) obj[89]);
				customerObjectDisplay.setHouseNo((String) obj[88]);
				
				/*
				 * igstAmount = ((Float) obj[90]) != null?((Float) obj[90]).doubleValue():null;
				 * sgstAmount = ((Float) obj[91]) != null?((Float) obj[91]).doubleValue():null;
				 * cgstAmount = ((Float) obj[92]) != null?((Float) obj[92]).doubleValue():null;
				 */
				
				igstAmount = ((Float) obj[89]) != null?((Float) obj[89]).doubleValue():null;
				sgstAmount = ((Float) obj[90]) != null?((Float) obj[90]).doubleValue():null;
				cgstAmount = ((Float) obj[91]) != null?((Float) obj[91]).doubleValue():null;
				
//				lat = obj[93] != null ? (String) obj[93] : null;
				lat = obj[92] != null ? (String) obj[93] : null;
//				lang = obj[94] != null ? (String) obj[94] : null;
				lang = obj[93] != null ? (String) obj[94] : null;
//				insertionObjectDisplay.setNoOfDays((Integer) obj[25]);
				insertionObjectDisplay.setNoOfDays((Integer) obj[24]);
//				insertionObjectDisplay.setBillableDays((Integer) obj[95]);
				insertionObjectDisplay.setBillableDays((Integer) obj[94]);
//				agreedPremiumDisPer = ((Float) obj[96]) != null?((Float) obj[96]).doubleValue():null;
				agreedPremiumDisPer = ((Float) obj[95]) != null?((Float) obj[95]).doubleValue():null;
				if(agreedPremiumDisPer != null) {
					insertionObjectDisplay.setAgreedPremiumDisPer(String.valueOf(agreedPremiumDisPer));
				}
//				masterPremiumPer = ((Float) obj[97]) != null?((Float) obj[97]).doubleValue():null;
//				premiumAmount = ((Float) obj[98]) != null?((Float) obj[98]).doubleValue():0;
				masterPremiumPer = ((Float) obj[96]) != null?((Float) obj[96]).doubleValue():null;
				premiumAmount = ((Float) obj[97]) != null?((Float) obj[97]).doubleValue():0;
				ratesResponse.setPremiumTotal(premiumAmount);
				insertionObjectDisplay.setWfId((String) obj[98]);
				
			}
			if(categoryDiscount != null) {
				RmsDiscountModel discountModel=new RmsDiscountModel();
				discountModel.setType("Position Instruction Discount");
				discountModel.setAmount(categoryDiscountAmount);
				discountModel.setAmountString(String.format("%.2f", categoryDiscountAmount));
				discountModel.setPercent(categoryDiscount);
				discountModel.setPercentString(String.format("%.2f", categoryDiscount));	
				ratesResponse.getDiscounts().add(discountModel);
			}
			if(additionalDiscount != null) {
				RmsDiscountModel discountModel=new RmsDiscountModel();
				discountModel.setType("Additional Discount");
				discountModel.setAmount(additionalDiscountAmount);
				discountModel.setAmountString(String.format("%.2f", additionalDiscountAmount));
				discountModel.setPercent(additionalDiscount);
				discountModel.setPercentString(String.format("%.2f", additionalDiscount));	
				ratesResponse.getDiscounts().add(discountModel);
			}
			if(multiDiscount != null) {
				RmsDiscountModel discountModel=new RmsDiscountModel();
				discountModel.setType("Multi Discount");
				discountModel.setAmount(multiDiscountAmount);
				discountModel.setAmountString(String.format("%.2f", multiDiscountAmount));
				discountModel.setPercent(multiDiscount);
				discountModel.setPercentString(String.format("%.2f", multiDiscount));	
				ratesResponse.getDiscounts().add(discountModel);
				
			}
//			if(premiumDiscount != null) {
//				RmsDiscountModel discountModel=new RmsDiscountModel();
//				discountModel.setType("Premium Discount");
//				discountModel.setAmount(premiumDiscountAmount);
//				discountModel.setAmountString(String.format("%.2f", premiumDiscountAmount));
//				discountModel.setPercent(premiumDiscount);
//				discountModel.setPercentString(String.format("%.2f", premiumDiscount));	
//				ratesResponse.getDiscounts().add(discountModel);
//			}
			if(sgstAmount != null) {
				RmsTaxModel rmsTaxModel = new RmsTaxModel();
				rmsTaxModel.setType("SGST");
				rmsTaxModel.setAmount(sgstAmount);
				rmsTaxModel.setAmountString(String.format("%.2f", sgstAmount));
				rmsTaxModel.setPercent(2.5);
				rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
				ratesResponse.getTax().add(rmsTaxModel);
			}

			if(igstAmount != null) {
				RmsTaxModel rmsTaxModel = new RmsTaxModel();
				rmsTaxModel.setType("IGST");
				rmsTaxModel.setAmount(igstAmount);
				rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
				rmsTaxModel.setPercent(5.0);
				rmsTaxModel.setPercentString(String.format("%.2f", 5.0));
				ratesResponse.getTax().add(rmsTaxModel);
			}

			if(cgstAmount != null) {
				RmsTaxModel rmsTaxModel = new RmsTaxModel();
				rmsTaxModel.setType("CGST");
				rmsTaxModel.setAmount(cgstAmount);
				rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
				rmsTaxModel.setPercent(2.5);
				rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
				ratesResponse.getTax().add(rmsTaxModel);
			}
			
			if(premiumDiscount != null) {
				RmsPremiumModel rmsPremiumModel = new RmsPremiumModel();
				rmsPremiumModel.setType("Agreed Premium");
				rmsPremiumModel.setAmount(premiumDiscountAmount);
				rmsPremiumModel.setPercent(premiumDiscount);
				rmsPremiumModel.setAmountString(String.format("%.2f",premiumDiscountAmount));
				rmsPremiumModel.setPercentString(String.format("%.2f",premiumDiscountAmount));
				ratesResponse.getPremium().add(rmsPremiumModel);
			}else if(masterPremiumPer != null){
				RmsPremiumModel rmsPremiumModel = new RmsPremiumModel();
				rmsPremiumModel.setType("Premium");
				rmsPremiumModel.setAmount(premiumAmount);
				rmsPremiumModel.setPercent(masterPremiumPer);
				ratesResponse.getPremium().add(rmsPremiumModel);
			}

			if(ratesResponse.getDiscountTotal() != null) {
				ratesResponse.setAfterDiscountTotal(ratesResponse.getAmount() - ratesResponse.getDiscountTotal());
			}
			if(ratesResponse.getPremiumTotal() != null && ratesResponse.getAfterDiscountTotal() != null) {
				ratesResponse.setAfterPremiumTotal(ratesResponse.getAfterDiscountTotal() + ratesResponse.getPremiumTotal());
			}else {
				if(ratesResponse.getPremiumTotal() != null) {
					ratesResponse.setAfterPremiumTotal(ratesResponse.getAmount() + ratesResponse.getPremiumTotal());
				}
			}

			if (itemIds != null && !itemIds.isEmpty()) {
				List<Object[]> editionsList = rmsClfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
				for (Object[] clObj : editionsList) {
					insertionObjectDisplay.getEditions().add((String) clObj[2]);
					insertionObjectDisplay.getEditionIds().add((Integer) (clObj[1]));
				}
				List<Object[]> publishDatesList = rmsClfPublishDates.getPublishDatesForErpData(itemIds);
				for (Object[] clObj : publishDatesList) {
					insertionObjectDisplay.getPublishDates()
							.add(CommonUtils.dateFormatter((Date) clObj[1], "dd-MM-yyyy"));
				}
				
				List<Object[]> discountTypes = rmsOrderDiscountTypesRepo.getDiscountTypes(itemIds);
				if(discountTypes != null && !discountTypes.isEmpty()) {
					for (Object[] obj : discountTypes) {
						insertionObjectDisplay.getDiscounts().add((Integer) obj[2]);
						insertionObjectDisplay.getDiscountsDesc().add((String) obj[3]);
					}
				}
			}
//			if(customerId != "") {
//				List<UmCustomers> customerDetailsOnOrderId = umCustomersRepo.getCustomerDetailsOnOrderId(customerId);
//				List<String> attachmentIds = new ArrayList<String>();
//				if(customerDetailsOnOrderId != null && !customerDetailsOnOrderId.isEmpty()){
//					for(UmCustomers customers:customerDetailsOnOrderId) {
//						if(customers.getAttatchId() != null) {
//							 attachmentIds = new ArrayList<>(Arrays.asList(customers.getAttatchId().split(",")));
//						}
//					}
//					if(attachmentIds != null && !attachmentIds.isEmpty()) {						
//						List<Attachments> allAttachmentsByAttachmentId = rmsAttachmentsRepo.getAllAttachmentsByAttachmentId(attachmentIds);
//						if(allAttachmentsByAttachmentId !=null && !allAttachmentsByAttachmentId.isEmpty()) {
//							
//							for(Attachments attachments:allAttachmentsByAttachmentId) {
//								List<String> attachmentUrls = new ArrayList<String>();
//								if(attachments.getAttachName().startsWith("GST")) {
//									attachmentUrls.add(prop.getProperty("TOMCAT_SERVER")+TOMCAT_PATH+attachments.getAttachUrl());
//									attatchments.setGstAttatchments(attachmentUrls);
//								}
//								if(attachments.getAttachName().startsWith("PAN")) {
//									attachmentUrls.add(prop.getProperty("TOMCAT_SERVER")+TOMCAT_PATH+attachments.getAttachUrl());
//									attatchments.setPanAttatchments(attachmentUrls);
//								}
//								if(attachments.getAttachName().startsWith("AADHAR")) {
//									attachmentUrls.add(prop.getProperty("TOMCAT_SERVER")+TOMCAT_PATH+attachments.getAttachUrl());
//									attatchments.setAadharAttatchments(attachmentUrls);
//								}
//								if(attachments.getAttachName().startsWith("OTHERS")) {
//									attachmentUrls.add(prop.getProperty("TOMCAT_SERVER")+TOMCAT_PATH+attachments.getAttachUrl());
//									attatchments.setOtheAttatchments(attachmentUrls);
//								}
//
//							}
//							paymentObjectDisplay.setAttatchments(attatchments);
//						}
//					}
//				}
//				List<Attachments> allAttachmentsByCustomerId = rmsAttachmentsRepo.getAllAttachmentsByCustomerId(customerId);
//				if(allAttachmentsByCustomerId != null && !allAttachmentsByCustomerId.isEmpty()) {
//					List<String> attachmentUrls = new ArrayList<String>();
//					for(Attachments attachments:allAttachmentsByCustomerId) {
//						attachmentUrls.add(prop.getProperty("TOMCAT_SERVER")+TOMCAT_PATH+attachments.getAttachUrl());
//					}
//					if(!attachmentUrls.isEmpty()) {
//						paymentObjectDisplay.setAttachmentUrls(attachmentUrls);
//					}
//				}
//			}
			
			List<Attachments> attachmentList = rmsAttachmentsRepo.getAllAttachmentsByOrderId(orderId);
			if(attachmentList != null && !attachmentList.isEmpty()) {
				List<String> otherattachmentUrls = new ArrayList<String>();
				List<String> gstattachmentUrls = new ArrayList<String>();
				List<String> panattachmentUrls = new ArrayList<String>();
				List<String> aadharattachmentUrls = new ArrayList<String>();
				for(Attachments attachments:attachmentList) {
//					List<String> attachmentUrls = new ArrayList<String>();
					if(attachments.getAttachName().startsWith("GST")) {
						gstattachmentUrls.add(prop.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
						attatchments.setGstAttatchments(gstattachmentUrls);
					}
					if(attachments.getAttachName().startsWith("PAN")) {
						panattachmentUrls.add(prop.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
						attatchments.setPanAttatchments(panattachmentUrls);
					}
					if(attachments.getAttachName().startsWith("AADHAR")) {
						aadharattachmentUrls.add(prop.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
						attatchments.setAadharAttatchments(aadharattachmentUrls);
					}
					if(attachments.getAttachName().startsWith("OTHER")) {
						otherattachmentUrls.add(prop.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
						attatchments.setOtheAttatchments(otherattachmentUrls);
					}

				}
				paymentObjectDisplay.setAttatchments(attatchments);
			}
			
			if(paymentId != "") {
				Attachments allAttachmentByAttachmentId = rmsAttachmentsRepo.getAllAttachmentByAttachmentId(paymentId);
				if(allAttachmentByAttachmentId != null) {
					if(allAttachmentByAttachmentId.getAttachName().startsWith("PAYMENT")) {
						paymentObjectDisplay.setPaymentId(prop.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+allAttachmentByAttachmentId.getAttachUrl());
					}
				}
			}
			if(signId != "") {
				Attachments allAttachmentByAttachmentId = rmsAttachmentsRepo.getAllAttachmentByAttachmentId(signId);
				if(allAttachmentByAttachmentId != null) {
					if(allAttachmentByAttachmentId.getAttachName().startsWith("SIGN")) {
						paymentObjectDisplay.setSignatureId(prop.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+allAttachmentByAttachmentId.getAttachUrl());
					}
				}
			}
			if(chequeId != "") {
				Attachments allAttachmentByAttachmentId = rmsAttachmentsRepo.getAllAttachmentByAttachmentId(chequeId);
				if(allAttachmentByAttachmentId != null) {
					if(allAttachmentByAttachmentId.getAttachName().startsWith("CHEQUE")) {
						paymentObjectDisplay.setChequeId(prop.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+allAttachmentByAttachmentId.getAttachUrl());
					}
				}
			}
			
			//get approval list
//			Double discount = 0.0;
//			if(additionalD != null && additionalD != 0.0) {
//				discount = discount + additionalD;
//			}
//			if(agreedPremiumDisPer != null && agreedPremiumDisPer != 0.0) {
//				discount = discount + agreedPremiumDisPer;
//			}
			List<ApprovalDetailsModel> approvalDetailsModel = new ArrayList<>();
//			if(discount != null && discount != 0.0) {
//				 approvalDetailsModel = this.getApprovalList(discount,orderId,bookingCode);
//			}//end approval list
			
			if(insertionObjectDisplay.getWfId() != null) {
				WfInboxMaster wfInboxMaster = wfInboxMasterRepo.getWfInboxMasterDetailsOnWfId(insertionObjectDisplay.getWfId(),insertionObjectDisplay.getItemId());
				if(wfInboxMaster != null) {
					String query2 = "select wimd.target_ref,wimd.status,wimd.changed_ts,wimd.ref_to_users,um.first_name from wf_inbox_master_details wimd INNER JOIN um_users um ON um.user_id = CAST(wimd.ref_to_users AS bigint) where wimd.inbox_master_id ='" + wfInboxMaster.getInboxMasterId() + "' and wimd.mark_as_delete = false ORDER BY wimd.target_ref ASC";
					List<Object[]> inboxMasterDetailsList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query2);
					if(inboxMasterDetailsList != null && !inboxMasterDetailsList.isEmpty()) {
						for(Object[] details : inboxMasterDetailsList) {
							ApprovalDetailsModel approvalModel = new ApprovalDetailsModel();
							approvalModel.setApprovalLevels(Integer.parseInt((String) details[0]));
							approvalModel.setApprovalStatus((String) details[1]);
							approvalModel.setApproverName((String) details[4]);
							if (!"PENDING".equalsIgnoreCase((String) details[1])) {
								if((Date) details[2] != null) {
									approvalModel.setApprovalTs(
											CommonUtils.dateFormatter((Date) details[2], "dd-MM-yyyy HH:mm:ss"));
								}								
							}
							
							approvalDetailsModel.add(approvalModel);
						}
					}
//					List<WfInboxMasterDetails> wfInboxMasterDetailsList = wfInboxMasterDetailsRepo.getInboxMasterDetails(wfInboxMaster.getInboxMasterId());
//					if(wfInboxMasterDetailsList != null && !wfInboxMasterDetailsList.isEmpty()) {
//						for(WfInboxMasterDetails details : wfInboxMasterDetailsList) {
//							ApprovalDetailsModel approvalModel = new ApprovalDetailsModel();
//							approvalModel.setApprovalLevels(Integer.parseInt(details.getTargetRef()));
//							approvalModel.setApprovalStatus(details.getStatus());
//							
//							approvalDetailsModel.add(approvalModel);
//						}
//					}
				}
			}
			
			classifieds = new RmsViewDetails();
			classifieds.setInsertionObjectDisplay(insertionObjectDisplay);
			classifieds.setCustomerObjectDisplay(customerObjectDisplay);
			classifieds.setPaymentObjectDisplay(paymentObjectDisplay);
			classifieds.setRatesResponse(ratesResponse);
			classifieds.setApprovalList(approvalDetailsModel);
			classifieds.setAdId(adId);
			classifieds.setItemId(itemId);
			classifieds.setOrderId(orderId);
			classifieds.setLat(lat);
			classifieds.setLang(lang);
			apiResponse.setStatus(0);
			apiResponse.setData(classifieds);
		} catch (NumberFormatException e) {
			logger.error("Error while getting  order by adID:" + e.getMessage());
			apiResponse.setStatus(1);
			apiResponse.setErrorcode("GEN_002");
		}
		return apiResponse;

	}
	
//	 private static String addQuotesToKeys(String input) {
//	        // Use regex to add quotes around keys (alphanumeric strings before the colon)
//	        String jsonWithQuotedKeys = input.replaceAll("(\\w+):", "\"$1\":");
//	        String jsonWithQuotedValues = jsonWithQuotedKeys.replaceAll("([:,\\[{\\s])([A-Za-z\\s]+)(?=[,\\]}\\s:])", "$1\"$2\"");
//	        return jsonWithQuotedValues;
//
//	    }
	
	public List<ApprovalDetailsModel> getApprovalList(Double discount, String orderId, Integer bookingCode) {
		List<ApprovalDetailsModel> approvalDetailsModelList = new ArrayList<>();
		try {
			List<Integer> userIds = new ArrayList<>();
			List<RmsApprovalMatrix> matrixCountList = new ArrayList<>();
			List<RmsApprovalMatrix> rmsApprovalMatrixList = rmsApprovalMatrixRepo.getApprovalBookingUnit(bookingCode);
			if (rmsApprovalMatrixList != null && !rmsApprovalMatrixList.isEmpty()) {
				for (RmsApprovalMatrix approvalMatrix : rmsApprovalMatrixList) {
					Double rangeFrom = approvalMatrix.getRangeFrom();
					Double rangeTo = approvalMatrix.getRangeTo();
					if ((discount >= rangeFrom && discount <= rangeTo)) {
						matrixCountList.add(approvalMatrix);
						userIds.add(approvalMatrix.getApproverUserId());
						break;
					} else {
						matrixCountList.add(approvalMatrix);
						userIds.add(approvalMatrix.getApproverUserId());
					}
				}

				Map<Integer,UmUsers> umUsersMap = new HashMap<>();
				List<UmUsers> umUsersList = umUsersRepository.getUsersByCreatedId(userIds, false);
				for(UmUsers umUser : umUsersList) {
					umUsersMap.put(umUser.getUserId(), umUser);
				}
				
				List<ApprovalInbox> approvalInboxList = approvalInboxRepo.getApprovalListByOrderId(orderId);
				if (approvalInboxList != null && !approvalInboxList.isEmpty()) {
					for (ApprovalInbox approvalInbox : approvalInboxList) {
						ApprovalDetailsModel approvalDetailsModel = new ApprovalDetailsModel();
						if(!"PENDING".equalsIgnoreCase(approvalInbox.getStatus())){
							approvalDetailsModel.setApprovalTs(CommonUtils
								.dateFormatter((Date) approvalInbox.getApprovalTimestamp(), "dd-MM-yyyy HH:mm:ss"));
						}
						approvalDetailsModel.setApprovalComments(approvalInbox.getComments());
						approvalDetailsModel.setApprovalLevels(approvalInbox.getCurrentLevel());
						approvalDetailsModel.setApprovalStatus(approvalInbox.getStatus());
						approvalDetailsModel.setItemId(approvalInbox.getItemId());
						UmUsers umUsers = umUsersMap.get(approvalInbox.getApproverUserId());
						if(umUsers != null) {
							approvalDetailsModel.setApproverName(umUsers.getFirstName());
						}
						approvalDetailsModelList.add(approvalDetailsModel);
					}
				}

				if (matrixCountList != null && !matrixCountList.isEmpty()) {
					for (RmsApprovalMatrix matrixCount : matrixCountList) {
						ApprovalDetailsModel approvalDetailsModel = new ApprovalDetailsModel();
						List<ApprovalInbox> inbox = approvalInboxList.stream()
								.filter(f -> f.getCurrentLevel().equals(matrixCount.getLevel()))
								.collect(Collectors.toList());

						if (inbox == null || inbox.isEmpty()) {
							approvalDetailsModel.setApprovalLevels(matrixCount.getLevel());
							approvalDetailsModel.setApprovalStatus("PENDING");
							
							UmUsers umUsers = umUsersMap.get(matrixCount.getApproverUserId());
							if(umUsers != null) {
								approvalDetailsModel.setApproverName(umUsers.getFirstName());
							}

							approvalDetailsModelList.add(approvalDetailsModel);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvalDetailsModelList;
	}

	@SuppressWarnings("unused")
	@Override
	public GenericApiResponse addRmsClassifiedItemToCart(CreateOrder payload, LoggedUser loggedUser) {

		GenericApiResponse response = new GenericApiResponse();
		try {
			response.setStatus(0);
			response.setMessage("Successfully added");
			Map<String, Object> details = new HashMap<String, Object>();
			ClfOrders clfOrders = new ClfOrders();

			if(payload.getOrderId() != null && payload.getOrderId() != ""){
				clfOrders.setOrderId(payload.getOrderId());
			}
			
//			clfOrders = getOpenCartDetails(loggedUser);
			List<ClfOrderItems> itemDetailsOnOrderId = new ArrayList<ClfOrderItems>();
			if (clfOrders != null && clfOrders.getOrderId() != null) {
				itemDetailsOnOrderId = rmsOrderItemsRepo.getOpenOrderItems(clfOrders.getOrderId());
				if (itemDetailsOnOrderId.size() > 0) {
					details.put("itemId", itemDetailsOnOrderId.get(0).getItemId());
				}
			}

			if ("CREATE".equalsIgnoreCase(payload.getAction())) {
				if (clfOrders.getOrderId() == null) {
					clfOrders = new ClfOrders();
					clfOrders.setOrderId(UUID.randomUUID().toString());
//					clfOrders.setCustomerId(loggedUser.getCustomerId() == null ? "" : null);
					clfOrders.setUserTypeId(loggedUser.getUserTypeId());
					clfOrders.setOrderStatus(RmsConstants.ORDER_OPEN);
					clfOrders.setPaymentStatus(RmsConstants.ORDER_PAYMENT_PENDING);
					clfOrders.setCreatedBy(loggedUser.getUserId());
					clfOrders.setCreatedTs(new Date());
					clfOrders.setMarkAsDelete(false);
					clfOrders.setOrderType(01);
					clfOrders.setBookingUnit(payload.getBookingOffice());
					clfOrders.setRmsOrderStatus("SAVE_AS_DRAFT");
					clfOrders.setLat(payload.getLat());
					clfOrders.setLang(payload.getLang());
					rmsClfOrdersRepo.save(clfOrders);
				} else {
					clfOrders = rmsClfOrdersRepo.getOrderDetails(payload.getOrderId());
					clfOrders.setUserTypeId(loggedUser.getUserTypeId());
					clfOrders.setOrderStatus(RmsConstants.ORDER_OPEN);
					clfOrders.setPaymentStatus(RmsConstants.ORDER_PAYMENT_PENDING);
					clfOrders.setCreatedBy(loggedUser.getUserId());
					clfOrders.setCreatedTs(new Date());
					clfOrders.setMarkAsDelete(false);
					clfOrders.setOrderType(01);
					clfOrders.setBookingUnit(payload.getBookingOffice());
					clfOrders.setRmsOrderStatus("SAVE_AS_DRAFT");
					clfOrders.setLat(payload.getLat());
					clfOrders.setLang(payload.getLang());
					baseDao.saveOrUpdate(clfOrders);
				}
				//else {
//					ClientPo customerDetail = this.getCustomerDetail(clfOrders.getCustomerId());
//					details.put("customer", customerDetail);
//					if (itemDetailsOnOrderId.size() > 0) {
//						InsertionPo insertionDetails = this.getInsertionDetails(clfOrders.getOrderId());
//						details.put("insertion", insertionDetails);
//					}
				//}
				details.put("orderId", clfOrders.getOrderId());
				response.setData(details);

			}
			if ("CUSTOMER".equalsIgnoreCase(payload.getAction())) {
				ClientPo populateRmsCustomerDetails = populateRmsCustomerDetails(payload.getClientPo(), loggedUser);
				if (populateRmsCustomerDetails != null && populateRmsCustomerDetails.getCustomerId() == null) {
					response.setStatus(1);
					response.setMessage("Customer Details Not found");
					return response;
				}
				clfOrders = rmsClfOrdersRepo.getOrderDetails(payload.getOrderId());
				clfOrders.setCustomerId(populateRmsCustomerDetails.getCustomerId() == null ? loggedUser.getCustomerId()
						: populateRmsCustomerDetails.getCustomerId());
				baseDao.saveOrUpdate(clfOrders);
				details.put("orderId", clfOrders.getOrderId());
				response.setData(details);

			}
			ClfOrderItems clfOrderItems = null;
			if (itemDetailsOnOrderId.size() > 0) {
				clfOrderItems = itemDetailsOnOrderId.get(0);
			}

			if ("INSERTION".equalsIgnoreCase(payload.getAction())) {
				if (clfOrderItems != null) {
					payload.setItemId(clfOrderItems.getItemId());
					this.removeOldCardDet(payload, loggedUser);
				}
				InsertionPo insertionPo = payload.getInsertionPo();
				if (insertionPo != null) {
					if (clfOrderItems == null) {
						clfOrderItems = new ClfOrderItems();
						clfOrderItems.setItemId(UUID.randomUUID().toString());
						clfOrderItems.setCreatedBy(loggedUser.getUserId());
						clfOrderItems.setCreatedTs(new Date());
					} else {
						clfOrderItems.setChangedBy(loggedUser.getUserId());
						clfOrderItems.setChangedTs(new Date());
					}
					clfOrderItems.setClfOrders(clfOrders);
					clfOrderItems.setClassifiedAdsSubType(insertionPo.getClassifiedAdsSubtype());
					clfOrderItems.setScheme(insertionPo.getScheme());
					clfOrderItems.setClfContent(insertionPo.getCaption());
					clfOrderItems.setMarkAsDelete(false);
					clfOrderItems.setDownloadStatus(false);
					clfOrderItems.setStatus(RmsConstants.RMS_APPROVAL_PENDING);
					if (clfOrderItems.getAdId() == null) {
						String adId = this.generateRmsSeries(payload.getBookingOffice());
						if (adId != null) {
							clfOrderItems.setAdId(adId);
						}
					}
					clfOrderItems.setGroup(insertionPo.getProductGroup());
					clfOrderItems.setSubGroup(insertionPo.getProductSubGroup());
					clfOrderItems.setChildGroup(insertionPo.getProductChildGroup());
					clfOrderItems.setClassifiedType(01);
					clfOrderItems.setClassifiedAdsType(02);

					baseDao.saveOrUpdate(clfOrderItems);
					
					clfOrders = rmsClfOrdersRepo.getOrderDetails(payload.getOrderId());
					if(clfOrders != null) {
						clfOrders.setEditionType(insertionPo.getEditionType());
						baseDao.saveOrUpdate(clfOrders);
					}

					for (String pdates : insertionPo.getPublishDates()) {
						ClfPublishDates clfPublishDate = new ClfPublishDates();
						clfPublishDate.setPublishDateId(UUID.randomUUID().toString());
						clfPublishDate.setClfOrderItems(clfOrderItems);
						clfPublishDate.setPublishDate(dateFormatter(pdates));
						clfPublishDate.setCreatedBy(loggedUser.getUserId());
						clfPublishDate.setCreatedTs(new Date());
						clfPublishDate.setMarkAsDelete(false);
						clfPublishDate.setOrderId(clfOrders.getOrderId());
						clfPublishDate.setDownloadStatus(false);
						baseDao.saveOrUpdate(clfPublishDate);

					}

					for (Integer editionId : insertionPo.getEditions()) {
						ClfEditions clfEditions = new ClfEditions();
						clfEditions.setOrderEditionId(UUID.randomUUID().toString());
						clfEditions.setEditionId(editionId);
						clfEditions.setClfOrderItems(clfOrderItems);
						clfEditions.setCreatedBy(loggedUser.getUserId());
						clfEditions.setCreatedTs(new Date());
						clfEditions.setMarkAsDelete(false);
						clfEditions.setOrderId(clfOrders.getOrderId());
						baseDao.saveOrUpdate(clfEditions);
					}
					
					if(insertionPo.getDiscounts() != null && !insertionPo.getDiscounts().isEmpty()) {
						for(Integer discountTypeId : insertionPo.getDiscounts()) {
							RmsOrderDiscountTypes discountTypes = new RmsOrderDiscountTypes();
							discountTypes.setId(UUID.randomUUID().toString());
							discountTypes.setDiscountId(discountTypeId);
							discountTypes.setOrderId(clfOrders.getOrderId());
							discountTypes.setItemId(clfOrderItems.getItemId());
							discountTypes.setCreatedBy(loggedUser.getUserId());
							discountTypes.setCreatedTs(new Date());
							discountTypes.setMarkAsDelete(false);
							
							baseDao.saveOrUpdate(discountTypes);
						}
					}
					
					List<RmsOrderItems> rmsOrderItem = orderItemsRepo
							.getRmsOrderItemsOnItemId(clfOrderItems.getItemId());
					if (rmsOrderItem.size() == 0) {
						RmsOrderItems newRmsOrderItem = new RmsOrderItems();
						newRmsOrderItem.setRmsItemId(UUID.randomUUID().toString());
						newRmsOrderItem.setItemId(clfOrderItems.getItemId());
						newRmsOrderItem.setOrderId(clfOrders.getOrderId());
						newRmsOrderItem.setSizeWidth(insertionPo.getWidth());
						newRmsOrderItem.setSizeHeight(insertionPo.getHeight());
//						newRmsOrderItem.setSpaceWidth(
//								(insertionPo.getSpaceWidth() != null && insertionPo.getSpaceWidth().length() > 0)
//										? new BigDecimal(insertionPo.getSpaceWidth()).doubleValue()
//										: null);
//						newRmsOrderItem.setSpaceHeight(
//								(insertionPo.getSpaceHeight() != null && insertionPo.getSpaceHeight().length() > 0)
//										? new BigDecimal(insertionPo.getSpaceHeight()).doubleValue()
//										: null);
						newRmsOrderItem.setPagePosition(insertionPo.getPositioningDiscount());
						newRmsOrderItem.setCreatedTs(new Date());
						newRmsOrderItem.setCreatedBy(loggedUser.getUserId());
						newRmsOrderItem.setMarkAsDelete(false);
						newRmsOrderItem.setChangedBy(loggedUser.getUserId());
						newRmsOrderItem.setChangedTs(new Date());
						newRmsOrderItem.setFormatType(insertionPo.getFormatType());
						newRmsOrderItem.setFixedFormat(insertionPo.getFixedFormat());
						newRmsOrderItem.setPageNumber(insertionPo.getPageNumber());
						newRmsOrderItem.setAdType(insertionPo.getAddType());
						newRmsOrderItem.setCaption(insertionPo.getCaption());
						newRmsOrderItem.setNoOfInsertions(insertionPo.getNoOfInsertions());
						//calculation logic
						GenericApiResponse genericApiResponse = this.setPayloadForCalculation(insertionPo);
						if(genericApiResponse != null) {
							RmsRatesResponse ratesResponse = (RmsRatesResponse) genericApiResponse.getData();
							if(ratesResponse != null) {
								newRmsOrderItem.setCardRate(ratesResponse.getCardRate());
								newRmsOrderItem.setMultiDiscountEdiCount(ratesResponse.getMultiDiscountEditionCount());
								newRmsOrderItem.setGrandTotal(ratesResponse.getGrandTotal());
								newRmsOrderItem.setAmount(ratesResponse.getAmount());
								newRmsOrderItem.setDiscountTotal(ratesResponse.getDiscountTotal());
								newRmsOrderItem.setGstTotal(ratesResponse.getGstTotal());
								newRmsOrderItem.setBillableDays(ratesResponse.getBillableDays());
								newRmsOrderItem.setAggredPremiumDisPer(ratesResponse.getAggredPremium() != null ? ratesResponse.getAggredPremium() : 0);
								if (ratesResponse.getDiscounts() != null && !ratesResponse.getDiscounts().isEmpty()) {
									List<RmsDiscountModel> discountModelList = ratesResponse.getDiscounts();
									if (discountModelList != null && !discountModelList.isEmpty()) {
										for (RmsDiscountModel discountModel : discountModelList) {
//											if ("Premium Discount".equalsIgnoreCase(discountModel.getType())) {
//												newRmsOrderItem.setPremiumDiscount(discountModel.getPercent());
//												newRmsOrderItem.setPremiumDiscountAmount(discountModel.getAmount());
//											} 
											if ("Position Instruction Discount"
													.equalsIgnoreCase(discountModel.getType())) {
												newRmsOrderItem.setCategoryDiscount(discountModel.getPercent());
												newRmsOrderItem.setCategoryDiscountAmount(discountModel.getAmount());
												
											} else if ("Additional Discount"
													.equalsIgnoreCase(discountModel.getType())) {
												newRmsOrderItem.setAdditionalDiscount(discountModel.getPercent());
												newRmsOrderItem.setAdditionalDiscountAmount(discountModel.getAmount());
											} else if("multi Discount".equalsIgnoreCase(discountModel.getType())){
												newRmsOrderItem.setMultiDiscount(discountModel.getPercent());
												newRmsOrderItem.setMultiDiscountAmount(discountModel.getAmount());
											}
										}
										if(newRmsOrderItem.getAdditionalDiscount() == null) {
											newRmsOrderItem.setAdditionalDiscount(0.0);
										}
										newRmsOrderItem.setCombinedDiscount(newRmsOrderItem.getAdditionalDiscount() + newRmsOrderItem.getAggredPremiumDisPer());
									}
								}
								if(ratesResponse.getTax() != null && !ratesResponse.getTax().isEmpty()) {
									List<RmsTaxModel> rmsTaxModelList = ratesResponse.getTax();
									if(rmsTaxModelList != null && !rmsTaxModelList.isEmpty()) {
										for(RmsTaxModel rmsTaxModel : rmsTaxModelList) {
											if ("SGST".equalsIgnoreCase(rmsTaxModel.getType())) {
												newRmsOrderItem.setSgstAmount(rmsTaxModel.getAmount());
											}
											if ("IGST".equalsIgnoreCase(rmsTaxModel.getType())) {
												newRmsOrderItem.setIgstAmount(rmsTaxModel.getAmount());
											}
											if ("CGST".equalsIgnoreCase(rmsTaxModel.getType())) {
												newRmsOrderItem.setCgstAmount(rmsTaxModel.getAmount());
											}
										}
									}
								}
								if(ratesResponse.getPremium() != null && !ratesResponse.getPremium().isEmpty()) {
									List<RmsPremiumModel> rmsPremiumList = ratesResponse.getPremium();
									if(rmsPremiumList != null && !rmsPremiumList.isEmpty()) {
										for(RmsPremiumModel rmsPremiumModel : rmsPremiumList) {
											if("Premium".equalsIgnoreCase(rmsPremiumModel.getType())) {
												newRmsOrderItem.setMasterPremiumPer(rmsPremiumModel.getPercent());
												newRmsOrderItem.setPremiumAmount(rmsPremiumModel.getAmount());
											}else if("Agreed Premium".equalsIgnoreCase(rmsPremiumModel.getType())) {
												newRmsOrderItem.setPremiumDiscount(rmsPremiumModel.getPercent());
												newRmsOrderItem.setPremiumDiscountAmount(rmsPremiumModel.getAmount());
											}
										}
									}
								}
							}
						}					
						
//						String rateCard = insertionPo.getRateCard();
//						String validJson = this.addQuotesToKeys(rateCard);
//						ObjectMapper objectMapper = new ObjectMapper();
//						Map<String, Object> map = objectMapper.readValue(validJson, Map.class);
//						
////						Map rateCardMap = objectMapper.readValue(rateCard,
////								new com.fasterxml.jackson.core.type.TypeReference<Map>() {
////								});	
//						if(validJson != null && !validJson.isEmpty()) {
//							
//							newRmsOrderItem.setAmount((Double) map.get("amount"));
//							newRmsOrderItem.setGrandTotal((Double) map.get("grandTotal"));
//							newRmsOrderItem.setGstTotal((Double) map.get("gstTotal"));
//							newRmsOrderItem.setDiscountTotal((Double) map.get("discountTotal"));
//							
//							if(map.get("discounts") != null) {
//								List<Map> discountsList = (List<Map>) map.get("discounts");
//								if(discountsList != null && !discountsList.isEmpty()) {
//									
//								}
//							}
//						}
//						newRmsOrderItem.setCategoryDiscount(Integer.parseInt(insertionPo.getCategoryDiscount()));
//						newRmsOrderItem.setMultiDiscount(
//								(insertionPo.getMultiDiscount() != null && insertionPo.getMultiDiscount().length() > 0)
//										? new BigDecimal(insertionPo.getMultiDiscount()).doubleValue()
//										: null);
//						newRmsOrderItem.setAdditionalDiscount((insertionPo.getAdditionalDiscount() != null
//								&& insertionPo.getAdditionalDiscount().length() > 0)
//										? new BigDecimal(insertionPo.getAdditionalDiscount()).doubleValue()
//										: null);
//						newRmsOrderItem.setSurchargeRate(
//								(insertionPo.getSurchargeRate() != null && insertionPo.getSurchargeRate().length() > 0)
//										? new BigDecimal(insertionPo.getSurchargeRate()).doubleValue()
//										: null);
//						newRmsOrderItem.setAdditionalDiscountAmount((insertionPo.getAdditionalDiscountAmount() != null
//								&& insertionPo.getAdditionalDiscountAmount().length() > 0)
//										? new BigDecimal(insertionPo.getAdditionalDiscountAmount()).doubleValue()
//										: null);
//						newRmsOrderItem.setSurchargeAmount((insertionPo.getSurchargeAmount() != null
//								&& insertionPo.getSurchargeAmount().length() > 0)
//										? new BigDecimal(insertionPo.getSurchargeAmount()).doubleValue()
//										: null);
//						newRmsOrderItem.setMultiDiscountAmount((insertionPo.getMultiDiscountAmount() != null
//								&& insertionPo.getMultiDiscountAmount().length() > 0)
//										? new BigDecimal(insertionPo.getMultiDiscountAmount()).doubleValue()
//										: null);
//						newRmsOrderItem.setCategoryDiscountAmount((insertionPo.getCategoryDiscountAmount() != null
//								&& insertionPo.getCategoryDiscountAmount().length() > 0)
//										? new BigDecimal(insertionPo.getCategoryDiscountAmount()).doubleValue()
//										: null);
						newRmsOrderItem.setNoOfInsertions(insertionPo.getNoOfDays());
//						newRmsOrderItem.setBillableDays(insertionPo.getBillableDays());
						baseDao.saveOrUpdate(newRmsOrderItem);
					} else {
						for (RmsOrderItems rmsOrderItems : rmsOrderItem) {
							rmsOrderItems.setItemId(clfOrderItems.getItemId());
							rmsOrderItems.setOrderId(clfOrders.getOrderId());
							rmsOrderItems.setSizeWidth(insertionPo.getWidth());
							rmsOrderItems.setSizeHeight(insertionPo.getHeight());
//							rmsOrderItems.setSpaceWidth(
//									(insertionPo.getSpaceWidth() != null && insertionPo.getSpaceWidth().length() > 0)
//											? new BigDecimal(insertionPo.getSpaceWidth()).doubleValue()
//											: null);
//							rmsOrderItems.setSpaceHeight(
//									(insertionPo.getSpaceHeight() != null && insertionPo.getSpaceHeight().length() > 0)
//											? new BigDecimal(insertionPo.getSpaceHeight()).doubleValue()
//											: null);

//							rmsOrderItems.setPagePosition(insertionPo.getPagePosition());
							rmsOrderItems.setPagePosition(insertionPo.getPositioningDiscount());
							rmsOrderItems.setChangedTs(new Date());
							rmsOrderItems.setChangedBy(loggedUser.getUserId());
							rmsOrderItems.setMarkAsDelete(false);
							rmsOrderItems.setChangedBy(loggedUser.getUserId());
							rmsOrderItems.setChangedTs(new Date());
							rmsOrderItems.setFormatType(insertionPo.getFormatType());
							rmsOrderItems.setFixedFormat(insertionPo.getFixedFormat());
							rmsOrderItems.setPageNumber(insertionPo.getPageNumber());
							rmsOrderItems.setAdType(insertionPo.getAddType());
							rmsOrderItems.setCaption(insertionPo.getCaption());
							rmsOrderItems.setNoOfInsertions(insertionPo.getNoOfInsertions());;
							
							
//							rmsOrderItems.setCategoryDiscount(Integer.parseInt(insertionPo.getCategoryDiscount()));
							
							//calculation logic
							GenericApiResponse genericApiResponse = this.setPayloadForCalculation(insertionPo);
							if(genericApiResponse != null) {
								RmsRatesResponse ratesResponse = (RmsRatesResponse) genericApiResponse.getData();
								if(ratesResponse != null) {
									rmsOrderItems.setCardRate(ratesResponse.getCardRate());
									rmsOrderItems.setMultiDiscountEdiCount(ratesResponse.getMultiDiscountEditionCount());
									rmsOrderItems.setGrandTotal(ratesResponse.getGrandTotal());
									rmsOrderItems.setAmount(ratesResponse.getAmount());
									rmsOrderItems.setDiscountTotal(ratesResponse.getDiscountTotal());
									rmsOrderItems.setGstTotal(ratesResponse.getGstTotal());
									rmsOrderItems.setBillableDays(ratesResponse.getBillableDays());
									rmsOrderItems.setAggredPremiumDisPer(ratesResponse.getAggredPremium() != null ? ratesResponse.getAggredPremium() : 0);
									if (ratesResponse.getDiscounts() != null && !ratesResponse.getDiscounts().isEmpty()) {
										List<RmsDiscountModel> discountModelList = ratesResponse.getDiscounts();
										if (discountModelList != null && !discountModelList.isEmpty()) {
											List<String> types = new ArrayList<>();
											for (RmsDiscountModel discountModel : discountModelList) {
//												if ("Premium Discount".equalsIgnoreCase(discountModel.getType())) {
//													rmsOrderItems.setPremiumDiscount(discountModel.getPercent());
//													rmsOrderItems.setPremiumDiscountAmount(discountModel.getAmount());
//												} 
												if ("Position Instruction Discount"
														.equalsIgnoreCase(discountModel.getType())) {
													rmsOrderItems.setCategoryDiscount(discountModel.getPercent());
													rmsOrderItems.setCategoryDiscountAmount(discountModel.getAmount());
													
												} else if ("Additional Discount"
														.equalsIgnoreCase(discountModel.getType())) {
													rmsOrderItems.setAdditionalDiscount(discountModel.getPercent());
													rmsOrderItems.setAdditionalDiscountAmount(discountModel.getAmount());
												} else if("multi Discount".equalsIgnoreCase(discountModel.getType())){
													rmsOrderItems.setMultiDiscount(discountModel.getPercent());
													rmsOrderItems.setMultiDiscountAmount(discountModel.getAmount());
												}
												types.add(discountModel.getType());
											}
											rmsOrderItems.setCombinedDiscount(rmsOrderItems.getAdditionalDiscount() + rmsOrderItems.getAggredPremiumDisPer());
											if(!types.contains("Position Instruction Discount")) {
												rmsOrderItems.setCategoryDiscount(null);
												rmsOrderItems.setCategoryDiscountAmount(null);
											}
											if(!types.contains("Additional Discount")) {
												rmsOrderItems.setAdditionalDiscount(null);
												rmsOrderItems.setAdditionalDiscountAmount(null);
											}
											if(!types.contains("multi Discount")) {
												rmsOrderItems.setMultiDiscount(null);
												rmsOrderItems.setMultiDiscountAmount(null);
											}
										}else {
											rmsOrderItems.setCategoryDiscount(null);
											rmsOrderItems.setCategoryDiscountAmount(null);
											rmsOrderItems.setAdditionalDiscount(null);
											rmsOrderItems.setAdditionalDiscountAmount(null);
											rmsOrderItems.setMultiDiscount(null);
											rmsOrderItems.setMultiDiscountAmount(null);
										}
									}else {
										rmsOrderItems.setCategoryDiscount(null);
										rmsOrderItems.setCategoryDiscountAmount(null);
										rmsOrderItems.setAdditionalDiscount(null);
										rmsOrderItems.setAdditionalDiscountAmount(null);
										rmsOrderItems.setMultiDiscount(null);
										rmsOrderItems.setMultiDiscountAmount(null);
									}
									
									if(ratesResponse.getTax() != null && !ratesResponse.getTax().isEmpty()) {
										List<RmsTaxModel> rmsTaxModelList = ratesResponse.getTax();
										if(rmsTaxModelList != null && !rmsTaxModelList.isEmpty()) {
											List<String> types = new ArrayList<>();
											for(RmsTaxModel rmsTaxModel : rmsTaxModelList) {
												if ("SGST".equalsIgnoreCase(rmsTaxModel.getType())) {
													rmsOrderItems.setSgstAmount(rmsTaxModel.getAmount());
												}
												if ("IGST".equalsIgnoreCase(rmsTaxModel.getType())) {
													rmsOrderItems.setIgstAmount(rmsTaxModel.getAmount());
												}
												if ("CGST".equalsIgnoreCase(rmsTaxModel.getType())) {
													rmsOrderItems.setCgstAmount(rmsTaxModel.getAmount());
												}
												types.add(rmsTaxModel.getType());
											}
											if(!types.contains("SGST")) {
												rmsOrderItems.setSgstAmount(null);
											}
											if(!types.contains("IGST")) {
												rmsOrderItems.setIgstAmount(null);
											}
											if(!types.contains("CGST")) {
												rmsOrderItems.setCgstAmount(null);
											}
										}
									}
									if(ratesResponse.getPremium() != null && !ratesResponse.getPremium().isEmpty()) {
										List<RmsPremiumModel> rmsPremiumList = ratesResponse.getPremium();
										if(rmsPremiumList != null && !rmsPremiumList.isEmpty()) {
											List<String> types = new ArrayList<>();
											for(RmsPremiumModel rmsPremiumModel : rmsPremiumList) {
												if("Premium".equalsIgnoreCase(rmsPremiumModel.getType())) {
													rmsOrderItems.setMasterPremiumPer(rmsPremiumModel.getPercent());
													rmsOrderItems.setPremiumAmount(rmsPremiumModel.getAmount());
												}else if("Agreed Premium".equalsIgnoreCase(rmsPremiumModel.getType())) {
													rmsOrderItems.setPremiumDiscount(rmsPremiumModel.getPercent());
//													rmsOrderItems.setPremiumAmount(rmsPremiumModel.getAmount());
													rmsOrderItems.setPremiumDiscountAmount(rmsPremiumModel.getAmount());
												}
												types.add(rmsPremiumModel.getType());
											}
											if(!types.contains("Premium")) {
												rmsOrderItems.setMasterPremiumPer(null);
											}
											if(!types.contains("Agreed Premium")) {
												rmsOrderItems.setPremiumDiscount(null);
											}
										}else {
											rmsOrderItems.setMasterPremiumPer(null);
											rmsOrderItems.setPremiumDiscount(null);
											rmsOrderItems.setPremiumAmount(null);
										}
									}else {
										rmsOrderItems.setMasterPremiumPer(null);
										rmsOrderItems.setPremiumDiscount(null);
										rmsOrderItems.setPremiumAmount(null);
									}
								}
							}
							
							
//							rmsOrderItems.setMultiDiscount((insertionPo.getMultiDiscount() != null
//									&& insertionPo.getMultiDiscount().length() > 0)
//											? new BigDecimal(insertionPo.getMultiDiscount()).doubleValue()
//											: null);
//							rmsOrderItems.setAdditionalDiscount((insertionPo.getAdditionalDiscount() != null
//									&& insertionPo.getAdditionalDiscount().length() > 0)
//											? new BigDecimal(insertionPo.getAdditionalDiscount()).doubleValue()
//											: null);
//							rmsOrderItems.setSurchargeRate((insertionPo.getSurchargeRate() != null
//									&& insertionPo.getSurchargeRate().length() > 0)
//											? new BigDecimal(insertionPo.getSurchargeRate()).doubleValue()
//											: null);
//							rmsOrderItems.setAdditionalDiscountAmount((insertionPo.getAdditionalDiscountAmount() != null
//									&& insertionPo.getAdditionalDiscountAmount().length() > 0)
//											? new BigDecimal(insertionPo.getAdditionalDiscountAmount()).doubleValue()
//											: null);
//							rmsOrderItems.setSurchargeAmount((insertionPo.getSurchargeAmount() != null
//									&& insertionPo.getSurchargeAmount().length() > 0)
//											? new BigDecimal(insertionPo.getSurchargeAmount()).doubleValue()
//											: null);
//							rmsOrderItems.setMultiDiscountAmount((insertionPo.getMultiDiscountAmount() != null
//									&& insertionPo.getMultiDiscountAmount().length() > 0)
//											? new BigDecimal(insertionPo.getMultiDiscountAmount()).doubleValue()
//											: null);
//							rmsOrderItems.setCategoryDiscountAmount((insertionPo.getCategoryDiscountAmount() != null
//									&& insertionPo.getCategoryDiscountAmount().length() > 0)
//											? new BigDecimal(insertionPo.getCategoryDiscountAmount()).doubleValue()
//											: null);
							rmsOrderItems.setNoOfInsertions(insertionPo.getNoOfDays());
//							rmsOrderItems.setBillableDays(insertionPo.getBillableDays());
							baseDao.saveOrUpdate(rmsOrderItems);
						}
					}

//					List<ClfOrderItemRates> clfOrderItemRate = rmsClfOrderItemsRatesRepo
//							.getRmsOrderItemsByItemId(clfOrderItems.getItemId());

//					if (clfOrderItemRate.size() == 0) {
//						ClfOrderItemRates newClfOrderItemRate = new ClfOrderItemRates();
//						newClfOrderItemRate.setItemRateId(UUID.randomUUID().toString());
//						newClfOrderItemRate.setOrderId(clfOrders.getOrderId());
//						newClfOrderItemRate.setItemId(clfOrderItems.getItemId());
////						newClfOrderItemRate
////								.setRate((insertionPo.getRate() != null && insertionPo.getRate().length() > 0)
////										? new BigDecimal(insertionPo.getRate()).doubleValue()
////										: null);
////						newClfOrderItemRate.setTotalAmount(
////								(insertionPo.getTotalAmount() != null && insertionPo.getTotalAmount().length() > 0)
////										? new BigDecimal(insertionPo.getTotalAmount()).doubleValue()
////										: null);
//						newClfOrderItemRate.setCreatedBy(loggedUser.getUserId());
//						newClfOrderItemRate.setCreatedTs(new Date());
//						newClfOrderItemRate.setMarkAsDelete(false);
//						newClfOrderItemRate
//								.setCgst((insertionPo.getCgst() != null && insertionPo.getCgst().length() > 0)
//										? new BigDecimal(insertionPo.getCgst()).doubleValue()
//										: null);
////						newClfOrderItemRate
////								.setSgst((insertionPo.getSgst() != null && insertionPo.getSgst().length() > 0)
////										? new BigDecimal(insertionPo.getSgst()).doubleValue()
////										: null);
//						newClfOrderItemRate
//								.setIgst((insertionPo.getIgst() != null && insertionPo.getIgst().length() > 0)
//										? new BigDecimal(insertionPo.getIgst()).doubleValue()
//										: null);
//						newClfOrderItemRate.setCgstValue(
//								(insertionPo.getCgstValue() != null && insertionPo.getCgstValue().length() > 0)
//										? new BigDecimal(insertionPo.getCgstValue()).doubleValue()
//										: null);
//						newClfOrderItemRate.setSgstValue(
//								(insertionPo.getSgstValue() != null && insertionPo.getSgstValue().length() > 0)
//										? new BigDecimal(insertionPo.getSgstValue()).doubleValue()
//										: null);
//						newClfOrderItemRate.setIgstValue(
//								(insertionPo.getIgstValue() != null && insertionPo.getIgstValue().length() > 0)
//										? new BigDecimal(insertionPo.getIgstValue()).doubleValue()
//										: null);
//						newClfOrderItemRate.setRatePerSquareCms((insertionPo.getRatePerSquareCms() != null
//								&& insertionPo.getRatePerSquareCms().length() > 0)
//										? new BigDecimal(insertionPo.getRatePerSquareCms()).doubleValue()
//										: null);
////						newClfOrderItemRate
////								.setAmount((insertionPo.getAmount() != null && insertionPo.getAmount().length() > 0)
////										? new BigDecimal(insertionPo.getAmount()).doubleValue()
////										: null);
//						baseDao.saveOrUpdate(newClfOrderItemRate);
//					} else {
//						for (ClfOrderItemRates clfOrderItemRates : clfOrderItemRate) {
//							clfOrderItemRates.setOrderId(clfOrders.getOrderId());
//							clfOrderItemRates.setItemId(clfOrderItems.getItemId());
////							clfOrderItemRates
////									.setRate((insertionPo.getRate() != null && insertionPo.getRate().length() > 0)
////											? new BigDecimal(insertionPo.getRate()).doubleValue()
////											: null);
//							clfOrderItemRates.setTotalAmount(
//									(insertionPo.getTotalAmount() != null && insertionPo.getTotalAmount().length() > 0)
//											? new BigDecimal(insertionPo.getTotalAmount()).doubleValue()
//											: null);
//							clfOrderItemRates.setMarkAsDelete(false);
//							clfOrderItemRates.setChangedBy(loggedUser.getUserId());
//							clfOrderItemRates.setChangedTs(new Date());
//							System.out.println(insertionPo.getCgst());
//							clfOrderItemRates
//									.setCgst((insertionPo.getCgst() != null && insertionPo.getCgst().length() > 0)
//											? new BigDecimal(insertionPo.getCgst()).doubleValue()
//											: null);
////							clfOrderItemRates
////									.setSgst((insertionPo.getSgst() != null && insertionPo.getSgst().length() > 0)
////											? new BigDecimal(insertionPo.getSgst()).doubleValue()
////											: null);
//							clfOrderItemRates
//									.setIgst((insertionPo.getIgst() != null && insertionPo.getIgst().length() > 0)
//											? new BigDecimal(insertionPo.getIgst()).doubleValue()
//											: null);
//							clfOrderItemRates.setCgstValue(
//									(insertionPo.getCgstValue() != null && insertionPo.getCgstValue().length() > 0)
//											? new BigDecimal(insertionPo.getCgstValue()).doubleValue()
//											: null);
//							clfOrderItemRates.setSgstValue(
//									(insertionPo.getSgstValue() != null && insertionPo.getSgstValue().length() > 0)
//											? new BigDecimal(insertionPo.getSgstValue()).doubleValue()
//											: null);
//							clfOrderItemRates.setIgstValue(
//									(insertionPo.getIgstValue() != null && insertionPo.getIgstValue().length() > 0)
//											? new BigDecimal(insertionPo.getIgstValue()).doubleValue()
//											: null);
//							clfOrderItemRates.setRatePerSquareCms((insertionPo.getRatePerSquareCms() != null
//									&& insertionPo.getRatePerSquareCms().length() > 0)
//											? new BigDecimal(insertionPo.getRatePerSquareCms()).doubleValue()
//											: null);
////							clfOrderItemRates
////									.setAmount((insertionPo.getAmount() != null && insertionPo.getAmount().length() > 0)
////											? new BigDecimal(insertionPo.getAmount()).doubleValue()
////											: null);
//							baseDao.saveOrUpdate(clfOrderItemRates);
//						}
//					}
				}
				clfOrders.setEditionType(insertionPo.getEditionType());
				details.put("orderId", clfOrders.getOrderId());
				details.put("itemId", clfOrderItems.getItemId());
				response.setData(details);

			}
//			if ("KYC".equalsIgnoreCase(payload.getAction())) {
//				if (payload.getKycAttatchments() != null) {
//					this.kycAttatchments(payload, loggedUser);
//				}
//				details.put("orderId", clfOrders.getOrderId());
//				details.put("itemId", payload.getItemId());
//				response.setData(details);
//			}

			if ("PAYMENTS".equalsIgnoreCase(payload.getAction())) {
				PaymentDetails paymentDetails = payload.getPaymentDetails();
				List<RmsPaymentsResponse> paymentsResponses = paymentsRepository
						.getPaymentsByItemId(clfOrderItems.getItemId());
				RmsPaymentsResponse payments = new RmsPaymentsResponse();
				if (paymentsResponses.size() == 0) {
					payments.setPaymentsId(UUID.randomUUID().toString());
					payments.setOrderId(clfOrders.getOrderId());
					payments.setItemId(clfOrderItems.getItemId());
					payments.setCreatedBy(loggedUser.getUserId());
					payments.setCreatedTs(new Date());
					payments.setPaymentMode(paymentDetails.getPaymentMode());
					payments.setBankBranch(paymentDetails.getBankOrBranch());
					payments.setBankRefId(paymentDetails.getReferenceId());
					payments.setMarkAsDelete(false);
					payments.setBankOrUpi(paymentDetails.getBankOrUpi());
					payments.setPaymentMethod(paymentDetails.getPaymentMethod());
					payments.setCashReceiptNo(paymentDetails.getCashReceiptNo());
					payments.setOtherDetails(paymentDetails.getOtherDetails());
					payments.setSignatureId(paymentDetails.getSignatureId());
					payments.setChequeAttId(
							paymentDetails.getChequeId() != null ? paymentDetails.getChequeId()
									: null);
					payments.setPaymentAttId(
							paymentDetails.getPaymentId() != null ? paymentDetails.getPaymentId()
									: null);
					baseDao.saveOrUpdate(payments);
				} else {
					for (RmsPaymentsResponse paymentsResponse : paymentsResponses) {
						paymentsResponse.setOrderId(clfOrders.getOrderId());
						paymentsResponse.setItemId(clfOrderItems.getItemId());
						paymentsResponse.setPaymentMode(paymentDetails.getPaymentMode());
						paymentsResponse.setBankBranch(paymentDetails.getBankOrBranch());
						paymentsResponse.setBankRefId(paymentDetails.getReferenceId());
						paymentsResponse.setMarkAsDelete(false);
						paymentsResponse.setChangedBy(loggedUser.getUserId());
						paymentsResponse.setChangedTs(new Date());
						paymentsResponse.setBankOrUpi(paymentDetails.getBankOrUpi());
						paymentsResponse.setPaymentMethod(paymentDetails.getPaymentMethod());
						paymentsResponse.setCashReceiptNo(paymentDetails.getCashReceiptNo());
						paymentsResponse.setOtherDetails(paymentDetails.getOtherDetails());
						paymentsResponse.setSignatureId(paymentDetails.getSignatureId());
						paymentsResponse.setChequeAttId(
								paymentDetails.getChequeId() != null ? paymentDetails.getChequeId()
										: null);
						paymentsResponse.setPaymentAttId(paymentDetails.getPaymentId() != null
								? paymentDetails.getPaymentId()
								: null);

						baseDao.saveOrUpdate(paymentsResponse);
						
//						deleteApprovalInboxForPrevious(payload.getOrderId(),loggedUser.getUserId());
					}
				}
//				if (payload.getPaymentDetails().getKycAttatchments() != null) {
//					this.kycAttatchments(payload, loggedUser);
//				}
				if (payload.getPaymentDetails().getKycAttatchments() != null
						|| payload.getPaymentDetails().getSignatureId() != null
						|| payload.getPaymentDetails().getChequeId() != null
						|| payload.getPaymentDetails().getPaymentId() != null) {
					this.saveAttachments(payload, loggedUser);
				}
				if(clfOrders.getOrderId() != null) {
					clfOrders = rmsClfOrdersRepo.getOrderDetails(payload.getOrderId());
					if(clfOrders != null) {
						clfOrders.setRmsOrderStatus("PENDING");
						clfOrders.setOrderStatus("CLOSED");
					}
					List<RmsOrderItems> rmsOrderItem = orderItemsRepo
							.getRmsOrderItemsOnItemId(payload.getItemId());
					Double per = 0.0;
					if(payload.getInsertionPo() != null) {
						if(payload.getInsertionPo().getAdditionalDiscountEdit() != null) {
							per = per + payload.getInsertionPo().getAdditionalDiscountEdit();
						}
//						if(payload.getInsertionPo().getPremiumDiscountEdit() != null) {
//							per = per + payload.getInsertionPo().getPremiumDiscountEdit();
//						}
					}
					if (rmsOrderItem != null && !rmsOrderItem.isEmpty()) {
						RmsOrderItems rmsOrder = rmsOrderItem.get(0);
						if (rmsOrder != null && rmsOrder.getAggredPremiumDisPer() != null
								&& rmsOrder.getAggredPremiumDisPer() != 0.0) {
							per = per + rmsOrder.getAggredPremiumDisPer();
						}
					}
					if(per != null) {
						this.assigeWorkflow(per,payload);
						clfOrders = rmsClfOrdersRepo.getOrderDetails(payload.getOrderId());
						if(clfOrders != null) {
							clfOrders.setWfId(payload.getWfId());
							baseDao.save(clfOrders);;
						}
//						this.assigneApprovalMatrix(per,payload.getOrderId(),payload.getItemId(),payload.getBookingOffice(),0);
					}else {
						this.updateOrderStatus(payload,loggedUser.getUserId());
						RmsModel rmsModel = new RmsModel();
						List<String> orderIds = new ArrayList<>();
						orderIds.add(payload.getOrderId());
						rmsModel.setOrderId(orderIds);
						this.syncronizeRmsSAPData(commonService.getRequestHeaders(), rmsModel);
					}
//					else {
//						List<String> orderIds = new ArrayList<String>();
//						orderIds.add(payload.getOrderId());
//						Map<String, ErpClassifieds> rmsOrderDetailsForErp = this.getRmsOrderDetailsForErp(orderIds);
//						this.sendRmsMailToCustomer(rmsOrderDetailsForErp, null, userContext.getLoggedUser(), null);
//					}
					List<String> orderIds = new ArrayList<String>();
					orderIds.add(clfOrders.getOrderId());
					Map<String, ErpClassifieds> rmsOrderDetailsForErp = this.getRmsOrderDetailsForErp(orderIds);
					this.sendOrderBookedDetailsViaSMS(rmsOrderDetailsForErp,payload);
					this.sendRmsMailToCustomer(rmsOrderDetailsForErp, null, userContext.getLoggedUser(), null);
					
				}
				details.put("orderId", clfOrders.getOrderId());
				details.put("itemId", clfOrderItems.getItemId());
				response.setData(details);
			}
		} catch (Exception e) {
			logger.error("Error while adding order to cart:" + e.getMessage());
			response.setStatus(1);
			response.setErrorcode("GEN_002");
		}
		return response;
	}
	
	

	private void assigeWorkflow(Double per, CreateOrder payload) {
		try {
			GenericRequestHeaders requestHeaders = commonService.getRequestHeaders();
			boolean flag = false;
			WfEvent wfEvent = new WfEvent();			
			wfEvent.setObjectRefId(payload.getItemId());
			wfEvent.setItemId(payload.getItemId());
			wfEvent.setOrderId(payload.getOrderId());
			wfEvent.setRequestRaisedBy(requestHeaders.getLoggedUser().getFirstName());
			wfEvent.setWfId(wfDao.getDefaultWfId("RMS",payload.getBookingOffice()));
			
			if (wfEvent.getWfId() != null) {
				payload.setWfId(wfEvent.getWfId());
				flag = workFlowService.intiateFormWorkFlow(wfEvent, requestHeaders, "SUBMITTED");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void updateOrderStatus(CreateOrder payload, Integer userId) {
		try {
			ClfOrders orderDetails = rmsClfOrdersRepo.getOrderDetails(payload.getOrderId());
			if (orderDetails != null) {
				orderDetails.setOrderStatus("CLOSED");
				orderDetails.setPaymentStatus("APPROVED");
				orderDetails.setRmsOrderStatus("APPROVED");
				orderDetails.setChangedBy(userId);
				orderDetails.setChangedTs(new Date());
				baseDao.save(orderDetails);
			}
			ClfOrderItems orderItems = rmsOrderItemsRepo.getItemDetailsOnItemId(payload.getItemId());
			if (orderItems != null) {
				orderItems.setStatus("APPROVED");
				orderItems.setChangedBy(userId);
				orderItems.setChangedTs(new Date());
				baseDao.save(orderItems);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendOrderBookedDetailsViaSMS(Map<String, ErpClassifieds> rmsOrderDetailsForErp, CreateOrder payload) {
		// TODO Auto-generated method stub
		try {
			rmsOrderDetailsForErp.entrySet().forEach(erpData -> {
				payload.setAdId(erpData.getValue().getAdId());
				payload.setEditionsErpId(erpData.getValue().getEditionsErpRefId()+"");
				payload.setEditionsStr(erpData.getValue().getEditions()+"");
				payload.setAmount(erpData.getValue().getGrandTotal()+"");
			});
			sendMessageService.sendSms1(payload);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void deleteApprovalInboxForPrevious(String orderId, Integer userId) {
		try {
			List<ApprovalInbox> approvalInbox = approvalInboxRepo.getApprovalListByOrderId(orderId);
			if(approvalInbox != null && !approvalInbox.isEmpty()){
				for(ApprovalInbox approval : approvalInbox) {
					approval.setMarkAsDelete(true);
					approval.setChangedBy(userId);
					approval.setChangedTs(new Date());
					
					baseDao.save(approval);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void assigneApprovalMatrix(Double discount, String orderId, String itemId, Integer bookingOffice,Integer currentLevel) {
		try {
			
			List<RmsApprovalMatrix> matrixCountList = new ArrayList<>();
			List<RmsApprovalMatrix> rmsApprovalMatrixList = rmsApprovalMatrixRepo.getApprovalBookingUnit(bookingOffice);
			if(rmsApprovalMatrixList != null && !rmsApprovalMatrixList.isEmpty()) {
				for(RmsApprovalMatrix matrix : rmsApprovalMatrixList) {
					Double rangeFrom = matrix.getRangeFrom();
					Double rangeTo = matrix.getRangeTo();
					if((discount >= rangeFrom && discount <= rangeTo)) {
						matrixCountList.add(matrix);
						break;
					} else {
						matrixCountList.add(matrix);
					}
				}
				if(matrixCountList != null && !matrixCountList.isEmpty()) {
					currentLevel = currentLevel + 1;
					for(RmsApprovalMatrix matrixCount : matrixCountList) {
						if(matrixCount.getLevel().equals(currentLevel)) {
							this.addApprovalInbox(matrixCount,orderId,itemId);
							break;
						}
					}
					
					ClfOrders clfOrders = rmsClfOrdersRepo.getOrderDetails(orderId);
					if(clfOrders != null) {
						clfOrders.setNoOfLevels(matrixCountList.size());
						baseDao.saveOrUpdate(clfOrders);
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void addApprovalInbox(RmsApprovalMatrix matrixCount,String orderId,String itemId) {
		try {
//			ApprovalInbox approvalInbox = approvalInboxRepo.getApprovalLevel(matrixCount.getLevel(),orderId);
//			if(approvalInbox != null) {
//				approvalInbox.setCurrentLevel(matrixCount.getLevel());
//				approvalInbox.setApprovalTimestamp(new Date());
//				approvalInbox.setStatus("PENDING");
//				approvalInbox.setApproverUserId(matrixCount.getApproverUserId());
//				approvalInbox.setCreatedBy(userContext.getLoggedUser().getUserId());
//				approvalInbox.setCreatedTs(new Date());
//				
//			}else {
				ApprovalInbox approvalInbox = new ApprovalInbox();
				approvalInbox.setInboxId(UUID.randomUUID().toString());
				approvalInbox.setOrderId(orderId);
				approvalInbox.setItemId(itemId);
				approvalInbox.setCurrentLevel(matrixCount.getLevel());
//				approvalInbox.setApprovalTimestamp(new Date());
				approvalInbox.setStatus("PENDING");
				approvalInbox.setApproverUserId(matrixCount.getApproverUserId());
				approvalInbox.setCreatedBy(userContext.getLoggedUser().getUserId());
				approvalInbox.setCreatedTs(new Date());
				approvalInbox.setMarkAsDelete(false);
				
				baseDao.save(approvalInbox);
				
				//need to add levels in clfOrders.
				
				UmUsers toEmails = umUsersRepository.getApproverEmails(matrixCount.getApproverUserId());
				UmUsers ccEmails = umUsersRepository.getApproverEmails(matrixCount.getApproverCcUserId());
				Map<String, ErpClassifieds> erpData = this.getRmsOrderDetailsForErp(Arrays.asList(orderId));
				if(ccEmails != null) {
					this.sendMailToApprover(toEmails.getEmail(),ccEmails.getEmail(),erpData);
				}else {
					this.sendMailToApprover(toEmails.getEmail(),null,erpData);
				}
//			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

//	@Override
//	public void sendMailToApprover(String toMail, String ccMail, Map<String, ErpClassifieds> erpMap) {
//		// TODO Auto-generated method stub
//		try {
//			LoggedUser loggedUser = userContext.getLoggedUser();
//			String accessObjectId = commonService.getRequestHeaders().getAccessObjId();
//			Map<String, Object> params = new HashMap<>();
//			params.put("stype", SettingType.APP_SETTING.getValue());
//			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
//			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
//			Map<String, String> emailConfigs = settingTo.getSettings();
//			
//			Map<String, Object> mapProperties = new HashMap<String, Object>();
//			EmailsTo emailTo = new EmailsTo();
//			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
//			emailTo.setTo(toMail);
//			if(ccMail != null) {
//				String [] ccMails = {ccMail};
//				emailTo.setBcc(ccMails);
//			}
//			emailTo.setOrgId("1000");
//			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
//			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
//			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
//			mapProperties.put("userId", loggedUser.getLogonId());// new userName
//			emailTo.setTemplateName("RMS_APPROVE_REQUEST");
//			
//			erpMap.entrySet().forEach(erpData -> {
//				mapProperties.put("adId", erpData.getValue().getAdId());
//				mapProperties.put("size",
//						erpData.getValue().getSizeWidth() + " * " + erpData.getValue().getSizeHeight());
//				mapProperties.put("editions", erpData.getValue().getEditions() + "");
//				mapProperties.put("additionalDisPer", erpData.getValue().getAdditionalDiscount());
//				mapProperties.put("premiumDisPer", erpData.getValue().getAggredPremiumDisPer());
//				mapProperties.put("noOfInsertions", erpData.getValue().getNoOfInsertions());
//				mapProperties.put("scheme", erpData.getValue().getSchemeStr());
//				mapProperties.put("currentLevel", erpData.getValue().getCurrentLevel()+"");
//				mapProperties.put("amount", erpData.getValue().getGrandTotal());
//				mapProperties.put("adType", erpData.getValue().getAddTypeDesc());
//				mapProperties.put("editionType", erpData.getValue().getEditionTypeDesc());
//				mapProperties.put("formatType", erpData.getValue().getFormatTypeDesc());
//				mapProperties.put("pinCode", erpData.getValue().getPinCode());
//				mapProperties.put("street", erpData.getValue().getHouseNo());
//				mapProperties.put("clientCode", erpData.getValue().getClientCode());
//				mapProperties.put("city", erpData.getValue().getCityDesc());
//				mapProperties.put("state", erpData.getValue().getStateDesc());
//				mapProperties.put("clientName", erpData.getValue().getCustomerName());
//				mapProperties.put("phone", erpData.getValue().getMobileNo());
//				mapProperties.put("address1",erpData.getValue().getAddress1());
//				mapProperties.put("subject_edit", true);
//				
//				mapProperties.put("action_url",
//						prop.getProperty("PORTAL_URL") + "rmswfinbox?orderId=" + erpData.getValue().getOrderId() + "&itemId=" + erpData.getValue().getItemId()
//								+ "&inboxId=" + erpData.getValue().getInboxId() + "&inboxMasterId=" + erpData.getValue().getInboxMasterId() + "&objectRefId=" + erpData.getValue().getObjectRefId()
//								+ "&accessKey=" + erpData.getValue().getAccessKey() + "&accessObjectId=" + accessObjectId);
//			});
//			
//			emailTo.setTemplateProps(mapProperties);
//			sendService.sendCommunicationMail(emailTo, emailConfigs);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	
	@Override
	public void sendMailToApprover(String toMail, String ccMail, Map<String, ErpClassifieds> erpMap) {
		// TODO Auto-generated method stub
		try {
			LoggedUser loggedUser = userContext.getLoggedUser();
			String accessObjectId = commonService.getRequestHeaders().getAccessObjId();
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();
			
			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
//			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
			emailTo.setFrom(emailConfigs.get("RMS_EMAIL_FROM"));

			emailTo.setTo(toMail);
			if(ccMail != null) {
				String [] ccMails = {ccMail};
				emailTo.setBcc(ccMails);
			}
			emailTo.setOrgId("1000");
			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
			mapProperties.put("userId", loggedUser.getLogonId());// new userName
			emailTo.setTemplateName("RMS_APPROVE_REQUEST");
			
			erpMap.entrySet().forEach(erpData -> {
				if(erpData.getValue().getCurrentLevel() == 1) {
					mapProperties.put("msg", "You have received an approval request for the following RMS Order");
				}else {
					StringBuilder msgBuilder = new StringBuilder("You have received an approval request for the following RMS Order<br><b>Previous approval details :</b><br>");
					List<RmsApprovalDetails> approvalDetailsList = new ArrayList<>(erpData.getValue().getApprovalDetails());
					approvalDetailsList.sort(Comparator.comparingInt(RmsApprovalDetails::getLevel));

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

					for (RmsApprovalDetails approvalDetails : approvalDetailsList) {
					    String formattedDateTime = approvalDetails.getApprovedTime() != null
					        ? sdf.format(approvalDetails.getApprovedTime())
					        : "";
					    int currentLevel = approvalDetails.getLevel();

					    msgBuilder.append("<b>Level ").append(currentLevel)
					              .append("</b> Approved by <b>")
					              .append(approvalDetails.getApprovedBy())
					              .append("</b> at <b>")
					              .append(formattedDateTime)
					              .append("</b><br>");
					}

					mapProperties.put("msg", msgBuilder.toString());					    
				}

//				emailTo.setTo(erpData.getValue().getEmailId());
//				emailTo.setTo(erpData.getValue().getSchedulingMail());
//				String [] ccMails = {erpData.getValue().getEmailId(),erpData.getValue().getCreatedByEmail()};
//				emailTo.setBcc(ccMails);
				mapProperties.put("currentLevel", erpData.getValue().getCurrentLevel()+"");
				mapProperties.put("orderId", erpData.getValue().getAdId());
				mapProperties.put("clientCode", erpData.getValue().getClientCode());
				mapProperties.put("city", erpData.getValue().getCityDesc());
				mapProperties.put("state", erpData.getValue().getStateDesc());
				mapProperties.put("clientName", erpData.getValue().getClientName());
				mapProperties.put("amount", erpData.getValue().getGrandTotal());
				mapProperties.put("nameOfDiscount", erpData.getValue().getPageNumberDesc());
//				mapProperties.put("discountValue", erpData.getValue().getDiscountValue());
//				mapProperties.put("iGst", erpData.getValue().getIgst());
				mapProperties.put("caption", erpData.getValue().getCaption());
				mapProperties.put("approvalStatus", "PENDING");
//				mapProperties.put("cGst", erpData.getValue().getCgst());
//				mapProperties.put("sGst", erpData.getValue().getSgst());
				mapProperties.put("phone", erpData.getValue().getMobileNo());
				mapProperties.put("Code", erpData.getValue().getAdId());
				mapProperties.put("categoryType", erpData.getValue().getAdsSubType());
				mapProperties.put("address",erpData.getValue().getOfficeAddress());
				mapProperties.put("address1",erpData.getValue().getAddress1());
				mapProperties.put("gstIn", erpData.getValue().getGstIn() != null ? erpData.getValue().getGstIn() : "");
				mapProperties.put("mobileNo", erpData.getValue().getPhoneNumber());
				
				mapProperties.put("editions", erpData.getValue().getEditions() + "");
				mapProperties.put("additionalDisPer", erpData.getValue().getAdditionalDiscount());
				mapProperties.put("premiumDisPer", erpData.getValue().getAggredPremiumDisPer());
				mapProperties.put("noOfInsertions", erpData.getValue().getNoOfInsertions());
				mapProperties.put("scheme", erpData.getValue().getSchemeStr());
				mapProperties.put("currentLevel", erpData.getValue().getCurrentLevel()+"");
				mapProperties.put("amount", erpData.getValue().getGrandTotal());
				mapProperties.put("adType", erpData.getValue().getAddTypeDesc());
				mapProperties.put("editionType", erpData.getValue().getEditionTypeDesc());
				mapProperties.put("formatType", erpData.getValue().getFormatTypeDesc());
//				String gst = null;
//				if (erpData.getValue().getIgst() != null) {
//					gst = "<tr><th colspan=4>GST- IGST (" + erpData.getValue().getIgst()
//							+ "%)</th><th style=\"text-align: right;\">" + erpData.getValue().getIgstValue()
//							+ "</th></tr>";
//					mapProperties.put("gst", gst);
//				} else {
//					gst = "</th></tr>" + "<tr><th colspan=4>GST- CGST(" + erpData.getValue().getCgst()
//							+ "%)</th><th style=\"text-align: right;\">" + erpData.getValue().getCgstValue()
//							+ "</th></tr>" + "<tr><th colspan=4>GST- SGST(" + erpData.getValue().getSgst()
//							+ "%)</th><th style=\"text-align: right;\">" + erpData.getValue().getSgstValue()
//							+ "</th></tr>";
//					mapProperties.put("gst", gst);
//				}
//				mapProperties.put("totalValue", erpData.getValue().getTotalValue());
				mapProperties.put("bankOrBranch", erpData.getValue().getBankOrUpi());
				mapProperties.put("cashReceiptNo", erpData.getValue().getCashReceiptNo());
				mapProperties.put("scheme", erpData.getValue().getSchemeStr());

				mapProperties.put("street", erpData.getValue().getHouseNo());
				mapProperties.put("gstNo", erpData.getValue().getGstNo() != null ? erpData.getValue().getGstNo():"");
				mapProperties.put("pinCode", erpData.getValue().getPinCode());
				mapProperties.put("noOfInsertion", erpData.getValue().getNoOfInsertions());
				mapProperties.put("createdTs", erpData.getValue().getCreatedTs());
				mapProperties.put("sizeHeight", erpData.getValue().getSizeHeightD());
				mapProperties.put("sizeWidth", erpData.getValue().getSizeWidthD());
				mapProperties.put("Position", erpData.getValue().getPageNumberDesc());
				mapProperties.put("employeeHrCode", erpData.getValue().getEmpCode());
				mapProperties.put("employee", erpData.getValue().getCustomerName());
				mapProperties.put("totalPremium", erpData.getValue().getPremiumTotal() != null ? erpData.getValue().getPremiumTotal() :"-");
				mapProperties.put("afterTotalPremium", erpData.getValue().getAfterPremiumTotal() != null? erpData.getValue().getAfterPremiumTotal():"-");
				mapProperties.put("totalAfterDiscount", erpData.getValue().getAfterDiscountTotal());
				mapProperties.put("withSchemeTotalAmount", erpData.getValue().getWithSchemeTotalAmount());
				if (erpData.getValue().getSizeWidth() != null && erpData.getValue().getSizeHeight() != null) {
					mapProperties.put("space",
							erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight());
				}
				mapProperties.put("cardRate", erpData.getValue().getCardRate());
				mapProperties.put("rate", erpData.getValue().getAmount());
				List<String> editionsList = erpData.getValue().getEditions();
				String editions = editionsList.stream().map(Object::toString).collect(Collectors.joining(","));
//				mapProperties.put("editionName", editions);
				List<String> pubDates = erpData.getValue().getPublishDates();
				List<String> pubDatesList = new ArrayList<String>();
				StringBuilder dynamicTableRows = new StringBuilder();
				for (String pubD : pubDates) {
					Date date;
					String formattedDate = null;
					try {
						date = new SimpleDateFormat("yyyyMMdd").parse(pubD);
						formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					pubDatesList.add(formattedDate);
					String cardRate = formatToIndianCurrency(erpData.getValue().getCardRate());
					String formatToIndianCurrency = formatToIndianCurrency(erpData.getValue().getAmount());
				    dynamicTableRows.append("<tr>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(formattedDate).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(editions).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeHeightD()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeWidthD()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(cardRate).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getPageNumberDesc()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(formatToIndianCurrency).append("</td>")
		            .append("</tr>");
		
				}
				mapProperties.put("dynamicTableRows", dynamicTableRows.toString());
				mapProperties.put("totalAmount",formatToIndianCurrency(erpData.getValue().getGrandTotal()));
				
				String publishDates = pubDatesList.stream().map(Object::toString).collect(Collectors.joining(","));
//				mapProperties.put("date", publishDates);
			

				// Put the formatted date into the map
				mapProperties.put("date", erpData.getValue().getCreatedTs());
				
				 StringBuilder discountRows = new StringBuilder();
				 List<Double> discountAmount = new ArrayList<Double>();
				 if(!erpData.getValue().getDiscounts().isEmpty()) {
					 for(RmsDiscountModel dM:erpData.getValue().getDiscounts()) {
						 String type = dM.getType();
						 if(type.equalsIgnoreCase("Additional Discount")) {
//							 Double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
							 Double discountedAmount = dM.getAmount();
							 discountAmount.add(discountedAmount);
							 discountRows.append("<tr>")
					            .append("<td>Additional Discount</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
//					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
					            .append("</tr>"); 
						 }
						 if(type.equalsIgnoreCase("Multi Discount")) {
//							 Double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
							 Double discountedAmount = dM.getAmount();
							 discountAmount.add(discountedAmount);
							 discountRows.append("<tr>")
					            .append("<td>Multi Discount</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
//					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
					            .append("</tr>");
						 }
						 if(type.equalsIgnoreCase("Category Discount")) {
//							 double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
							 Double discountedAmount = dM.getAmount();
							 discountAmount.add(discountedAmount);
							 discountRows.append("<tr>")
					            .append("<td>Category Discount - " + dM.getCategoryType() + "</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
//					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
					            .append("</tr>");
						 }
//						 if(type.equalsIgnoreCase("Premium Discount")) {
//							 double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
//							 discountAmount.add(discountedAmount);
//							 discountRows.append("<tr>")
//					            .append("<td>Premium Discount</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
//					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
//					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
////					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
//					            .append("</tr>");
//						 }
//						 if(type.equalsIgnoreCase("Premium Discount")) {
//							 double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
//							 discountAmount.add(discountedAmount);
//							 discountRows.append("<tr>")
//					            .append("<td>Premium Discount</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
//					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
//					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
//					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
//					            .append("</tr>");
//						 }
						 
					 }
					
				 }
				 mapProperties.put("discountTableRows", discountRows.length() >0 ? discountRows.toString() : "");
				 Double disc = 0.0;
				 for(Double discount:discountAmount) {
					 disc = disc + discount;
				 }
				
				 mapProperties.put("totalDiscountValue", formatToIndianCurrency(disc));
				 
				 StringBuilder premiumRows = new StringBuilder();
				 if(!erpData.getValue().getPremium().isEmpty()) {
					 for(RmsPremiumModel model : erpData.getValue().getPremium()) {
						 String type = model.getType();
						 if(type.equalsIgnoreCase("Premium")) {
							 Double discountedAmount = model.getAmount();
							 discountAmount.add(discountedAmount);
							 premiumRows.append("<tr>")
					            .append("<td>Premium</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
						 if(type.equalsIgnoreCase("Agreed Premium")) {
							 Double discountedAmount = model.getAmount();
							 discountAmount.add(discountedAmount);
							 premiumRows.append("<tr>")
					            .append("<td>Agreed Premium</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
						 
					 }
					 
				 }
				 mapProperties.put("premiumTableRows",premiumRows.length() > 0 ? premiumRows.toString() : "-");
				 
				 StringBuilder gstRows = new StringBuilder();
				 Double totalGstValue = 0.0;
				 if(!erpData.getValue().getTax().isEmpty()) {
					 for(RmsTaxModel model : erpData.getValue().getTax()) {
						 String type = model.getType();
						 if(type.equalsIgnoreCase("IGST")) {
							 Double discountedAmount = model.getAmount();
							 totalGstValue = totalGstValue + model.getAmount();
							 discountAmount.add(discountedAmount);
							 gstRows.append("<tr>")
					            .append("<td>IGST</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
						 if(type.equalsIgnoreCase("SGST")) {
							 Double discountedAmount = model.getAmount();
							 totalGstValue = totalGstValue + model.getAmount();
							 discountAmount.add(discountedAmount);
							 gstRows.append("<tr>")
					            .append("<td>SGST</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
						 if(type.equalsIgnoreCase("CGST")) {
							 Double discountedAmount = model.getAmount();
							 totalGstValue = totalGstValue + (model.getAmount() != null ? model.getAmount():0.0) ;
							 discountAmount.add(discountedAmount);
							 gstRows.append("<tr>")
					            .append("<td>CGST</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
					 }
					 mapProperties.put("gstTableRows", gstRows.length() > 0 ? gstRows.toString():"-");
					 mapProperties.put("totalGstValue", totalGstValue);
				 }

//		            


//				 Map<String, Object> mapData1 = new HashMap<>();
//				 List<Attachments> attachmentsByOrderId = rmsAttachmentsRepo.getAllAttachmentsByOrderId(erpData.getValue().getOrderId());
//				
//				 for(Attachments attachments : attachmentsByOrderId) {
//					 if(attachments.getAttachName().startsWith("SIGN_")) {
//						 String cid = UUID.randomUUID().toString(); // Unique Content-ID
//						 mapProperties.put("signature", prop.getProperty("TOMCAT_SERVER")+TOMCAT_PATH+attachments.getAttachUrl());
////						 mapProperties.put("signature", "http://97.74.94.194:8080/staticresources/docs/SIGN_0_1678051b-9acb-481c-abb5-d1c132c1d203SIGN_0.png");
//					        mapData1.put(cid, new FileDataSource(getDIR_PATH_DOCS() + attachments.getAttachUrl()));
//					 }
//				 }
//				 List<Object> pdfFileNames1 = new ArrayList<Object>(mapData1.values());
//		            List<String> fileNames1 = new ArrayList<>();
//		            for(Object fileName : pdfFileNames1) {
//		            	  if (fileName instanceof String) {
//		                      fileNames1.add(getDIR_PATH_DOCS() + fileName.toString());
//		                  } else if (fileName instanceof javax.activation.FileDataSource) {
//		                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
//		                      fileNames1.add(getDIR_PATH_DOCS() + fileDataSource.getName());
//		                  } else {
//		                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
//		                  }
//		            }
//
//		            List<String> cids = new ArrayList<>(mapData1.keySet());
//		            
//		         
//				emailTo.setTemplateProps(mapProperties);
//
//
//
//				List<Map<String, Object>> multiAttachments = new ArrayList<Map<String, Object>>();
//				Map<String, Object> mapData = new HashMap<>();
//				
//				List<Attachments> allAttachmentsByOrderId = rmsAttachmentsRepo.getAllAttachmentsByOrderId(erpData.getValue().getOrderId());
//				if(allAttachmentsByOrderId!=null && !allAttachmentsByOrderId.isEmpty()) {
//					for(Attachments attach:allAttachmentsByOrderId) {
//	                    mapData.put(attach.getAttachName()+".png", new FileDataSource(getDIR_PATH_DOCS()+attach.getAttachUrl()));
//					}
//				}
//				
//				List<Object> pdfFileNames = new ArrayList<Object>(mapData.values());
//	            List<String> fileNames = new ArrayList<>();
//	            for(Object fileName : pdfFileNames) {
//	            	  if (fileName instanceof String) {
//	                      fileNames.add(getDIR_PATH_DOCS() + fileName.toString());
//	                  } else if (fileName instanceof javax.activation.FileDataSource) {
//	                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
//	                      fileNames.add(getDIR_PATH_DOCS() + fileDataSource.getName());
//	                  } else {
//	                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
//	                  }
//	            }
//	            mapProperties.put("pdf_download", fileNames);
//				try {
//					this.genratePDF(erpMap,fileNames);
//				} catch (IOException | DocumentException | java.io.IOException e) {
//					e.printStackTrace();
//				}
//				 String pdfFilePath =  getDIR_PATH_PDF_DOCS()+erpData.getValue().getAdId()+".pdf";
//		         mapData.put(erpData.getValue().getAdId()+".pdf", new FileDataSource(pdfFilePath));
//		
//				multiAttachments.add(mapData);
//				
//				emailTo.setDataSource(multiAttachments);

				 mapProperties.put("subject_edit", true);
				
				mapProperties.put("action_url",
						prop.getProperty("PORTAL_URL") + "rmswfinbox?orderId=" + erpData.getValue().getOrderId() + "&itemId=" + erpData.getValue().getItemId()
								+ "&inboxId=" + erpData.getValue().getInboxId() + "&inboxMasterId=" + erpData.getValue().getInboxMasterId() + "&objectRefId=" + erpData.getValue().getObjectRefId()
								+ "&accessKey=" + erpData.getValue().getAccessKey() + "&accessObjectId=" + accessObjectId);
			});
			
			emailTo.setTemplateProps(mapProperties);
			sendService.sendCommunicationMail(emailTo, emailConfigs);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private GenericApiResponse setPayloadForCalculation(InsertionPo insertionPo) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			RmsRateModel rateModel = new RmsRateModel();
			rateModel.setAdditionalDiscountPercentage(insertionPo.getAdditionalDiscountEdit());
			rateModel.setAddType(insertionPo.getAddType());
			rateModel.setAdsSubType(insertionPo.getClassifiedAdsSubtype());
			rateModel.setCustomerIdStateCode(insertionPo.getCustomerIdStateCode());
			rateModel.setEmployeeState(insertionPo.getEmployeeState());
			rateModel.setFixedFormat(insertionPo.getFixedFormat());
			rateModel.setFormatType(insertionPo.getFormatType());
			rateModel.setFormatType(insertionPo.getFormatType());
			rateModel.setHeight(insertionPo.getHeight());
			rateModel.setWidth(insertionPo.getWidth());
			rateModel.setPageInstructions(insertionPo.getPositioningDiscount());
			rateModel.setPageNumber(insertionPo.getPageNumber());
			rateModel.setPremiumDiscPercent(insertionPo.getPremiumDiscountEdit());
			rateModel.setSchemeId(insertionPo.getScheme());
			rateModel.setEditions(insertionPo.getEditions());
			rateModel.setBillableDays(insertionPo.getBillableDays());
			rateModel.setEditionType(insertionPo.getEditionType());
			if (rateModel != null) {
				apiResponse = this.getRmsRates(rateModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

	private ClientPo getCustomerDetail(String customerId) {
		ClientPo clientPo = new ClientPo();
		List<Object[]> customerdetails = rmsUmCustomersRepo.getCustomerByOrderId(customerId, "OPEN");
		try {
			if (customerdetails != null && !customerdetails.isEmpty()) {
				for (Object[] obj : customerdetails) {
					clientPo.setState((String) obj[0]);
					clientPo.setCity((String) obj[1]);
					clientPo.setCustomerId((String) obj[2]);
					clientPo.setCustomerName((String) obj[3]);
					clientPo.setMobileNo((String) obj[4]);
					clientPo.setEmailId((String) obj[5]);
					clientPo.setAddress1((String) obj[6]);
					clientPo.setPinCode((String) obj[7]);
					clientPo.setGstNo((String) obj[8]);
					clientPo.setClientCode((String) obj[9]);
					clientPo.setCustomerDetails((Integer) obj[10]);
					clientPo.setUserId((Integer) obj[11]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientPo;
	}
	
	private InsertionPo getInsertionDetails(String orderId) {
		InsertionPo insertionPo=new InsertionPo();
		insertionPo.setPublishDates(new ArrayList<String>());
		insertionPo.setEditions(new ArrayList<Integer>());
		List<Object[]> insertionDetailsOnOrderId = rmsOrderItemsRepo.getInsertionDetailsOnOrderId(orderId);
		try {
			if(!insertionDetailsOnOrderId.isEmpty()&&insertionDetailsOnOrderId!=null) {
				for(Object[] obj : insertionDetailsOnOrderId) {
					insertionPo.setScheme((Integer) obj[0]);
					insertionPo.setCaption((String) obj[1]);
					insertionPo.setClassifiedAdsSubtype((Integer) obj[2]);
					insertionPo.setProductGroup((Integer) obj[3]);
					insertionPo.setProductSubGroup((Integer) obj[4]);
					insertionPo.setProductChildGroup((Integer) obj[5]);
					insertionPo.setNoOfInsertions((Integer) obj[6]);
					float spaceWidthFloat = (float) obj[7];
					float spaceHeightFloat = (float) obj[8];
					String spaceWidthString = String.valueOf(spaceWidthFloat);
					String spaceHeightString = String.valueOf(spaceHeightFloat);
					insertionPo.setSpaceWidth(spaceWidthString);
					insertionPo.setSpaceHeight(spaceHeightString);
					insertionPo.setPagePosition((Integer) obj[9]);
					insertionPo.setFormatType((Integer) obj[10]);
					insertionPo.setFixedFormat((Integer) obj[11]);	
					insertionPo.setPageNumber((Integer) obj[12]);
					String categoryDiscountString = Integer.toString((int) obj[13]);
					insertionPo.setCategoryDiscount(categoryDiscountString);
					String multiDiscountString = Float.toString((float) obj[14]);
					String additionalDiscountString = Float.toString((float) obj[15]);
					String surchargeRateString = Float.toString((float) obj[16]);
					String multiDiscountAmountString = Float.toString((float) obj[17]);
					String surchargeAmountString = Float.toString((float) obj[18]);
					String additionalDiscountAmountString = Float.toString((float) obj[19]);
					String categoryDiscountAmountString = Float.toString((float) obj[30]);
					insertionPo.setMultiDiscount(multiDiscountString);
					insertionPo.setAdditionalDiscount(additionalDiscountString);
					insertionPo.setSurchargeRate(surchargeRateString);
					insertionPo.setMultiDiscountAmount(multiDiscountAmountString);
					insertionPo.setSurchargeAmount(surchargeAmountString);
					insertionPo.setAdditionalDiscountAmount(additionalDiscountAmountString);
					insertionPo.setCategoryDiscountAmount(categoryDiscountAmountString);
					String rateString = Float.toString((float) obj[20]);
					String totalAmountString = Float.toString((float) obj[21]);
					String cgstString = (obj[22] != null) ? Float.toString((float) obj[22]) : "";
					String sgstString = (obj[23] != null) ? Float.toString((float) obj[23]) : "";
					String igstString = (obj[24] != null) ? Float.toString((float) obj[24]) : "";
					String cgstValueString = (obj[25] != null) ? Float.toString((float) obj[25]) : "";
					String sgstValueString = (obj[26] != null) ? Float.toString((float) obj[26]) : "";
					String igstValueString = (obj[27] != null) ? Float.toString((float) obj[27]) : "";
					String amountString = Float.toString((float) obj[28]);
					String ratePerSquareCmsString = Float.toString((float) obj[29]);
//					insertionPo.setRate(rateString);
					insertionPo.setTotalAmount(totalAmountString);
					insertionPo.setCgst(cgstString);
					insertionPo.setSgst(sgstString);
					insertionPo.setIgst(igstString);
					insertionPo.setCgstValue(cgstValueString);
					insertionPo.setSgstValue(sgstValueString);
					insertionPo.setIgstValue(igstValueString);
//					insertionPo.setAmount(amountString);
					insertionPo.setRatePerSquareCms(ratePerSquareCmsString);
				}
			}
			List<Object[]> publishDatesOnOrderId = rmsClfPublishDates.getPublishDatesOnOrderId(orderId);
			if(!publishDatesOnOrderId.isEmpty()&&publishDatesOnOrderId!=null) {
				for (Object[] clObj : publishDatesOnOrderId) {
					insertionPo.getPublishDates()
							.add(CommonUtils.dateFormatter((Date) clObj[0], "dd-MM-yyyy"));
				}	
			}
			List<Object[]> editionIdAndNameOnOrderId = rmsClfEditionsRepo.getEditionIdAndNameOnOrderId(orderId);
			if(!editionIdAndNameOnOrderId.isEmpty()&&editionIdAndNameOnOrderId!=null) {
				for (Object[] clObj : editionIdAndNameOnOrderId) {
					insertionPo.getEditions().add((Integer) clObj[0]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insertionPo;
	}
	
	private void saveAttachments(CreateOrder payload, LoggedUser loggedUser) {
		RmsKycAttatchments kycAttatchments = payload.getPaymentDetails().getKycAttatchments();
		List<String> aadharAttatchments = kycAttatchments.getAadharAttatchments();
		List<String> gstAttatchments = kycAttatchments.getGstAttatchments();
		List<String> otherAttatchments = kycAttatchments.getOtherAttatchments();
		List<String> panAttatchments = kycAttatchments.getPanAttatchments();
		if(aadharAttatchments != null && !aadharAttatchments.isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getListOfAttachmentDetails(aadharAttatchments);
			if(listOfAttachmentDetails != null && !listOfAttachmentDetails.isEmpty()) {
				for(Attachments attachments : listOfAttachmentDetails) {					
					attachments.setOrderId(payload.getOrderId());
					baseDao.saveOrUpdate(attachments);
				}
			}
		}
		if(gstAttatchments != null && !gstAttatchments.isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getListOfAttachmentDetails(gstAttatchments);
			if(listOfAttachmentDetails != null && !listOfAttachmentDetails.isEmpty()) {
				for(Attachments attachments : listOfAttachmentDetails) {					
					attachments.setOrderId(payload.getOrderId());
					baseDao.saveOrUpdate(attachments);
				}
			}
		}
		if(panAttatchments != null && !panAttatchments.isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getListOfAttachmentDetails(panAttatchments);
			if(listOfAttachmentDetails != null && !listOfAttachmentDetails.isEmpty()) {
				for(Attachments attachments : listOfAttachmentDetails) {					
					attachments.setOrderId(payload.getOrderId());
					baseDao.saveOrUpdate(attachments);
				}
			}
		}
		if(otherAttatchments != null && !otherAttatchments.isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getListOfAttachmentDetails(otherAttatchments);
			if(listOfAttachmentDetails != null && !listOfAttachmentDetails.isEmpty()) {
				for(Attachments attachments : listOfAttachmentDetails) {					
					attachments.setOrderId(payload.getOrderId());
					baseDao.saveOrUpdate(attachments);
				}
			}
		}
		if(payload.getPaymentDetails() != null && payload.getPaymentDetails().getSignatureId() != null && !payload.getPaymentDetails().getSignatureId().isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getAttachmentDetails(payload.getPaymentDetails().getSignatureId());
			if(listOfAttachmentDetails != null && !listOfAttachmentDetails.isEmpty()) {
				for(Attachments attachments : listOfAttachmentDetails) {					
					attachments.setOrderId(payload.getOrderId());
					baseDao.saveOrUpdate(attachments);
				}
			}
		}
		
		if(payload.getPaymentDetails() != null && payload.getPaymentDetails().getChequeId() != null && !payload.getPaymentDetails().getChequeId().isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getAttachmentDetails(payload.getPaymentDetails().getChequeId());
			if(listOfAttachmentDetails != null && !listOfAttachmentDetails.isEmpty()) {
				for(Attachments attachments : listOfAttachmentDetails) {					
					attachments.setOrderId(payload.getOrderId());
					baseDao.saveOrUpdate(attachments);
				}
			}
		}
		
		if(payload.getPaymentDetails() != null && payload.getPaymentDetails().getPaymentId() != null && !payload.getPaymentDetails().getPaymentId().isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getAttachmentDetails(payload.getPaymentDetails().getPaymentId());
			if(listOfAttachmentDetails != null && !listOfAttachmentDetails.isEmpty()) {
				for(Attachments attachments : listOfAttachmentDetails) {					
					attachments.setOrderId(payload.getOrderId());
					baseDao.saveOrUpdate(attachments);
				}
			}
		}
	}

	public void kycAttatchments(CreateOrder payload, LoggedUser loggedUser) {
		try {
			String aadhar = "";
			String gst = "";
			String pan = "";
			String other = "";
			
			RmsKycAttatchments kycAttatchments = payload.getPaymentDetails().getKycAttatchments();
			List<String> aadharAttatchments = kycAttatchments.getAadharAttatchments();
			List<String> gstAttatchments = kycAttatchments.getGstAttatchments();
			List<String> otherAttatchments = kycAttatchments.getOtherAttatchments();
			List<String> panAttatchments = kycAttatchments.getPanAttatchments();

			if(aadharAttatchments != null && !aadharAttatchments.isEmpty()) {
				aadhar = aadharAttatchments.stream().map(Object::toString).collect(Collectors.joining(","));
			}
			if(gstAttatchments != null && !gstAttatchments.isEmpty()) {
				gst = gstAttatchments.stream().map(Object::toString).collect(Collectors.joining(","));
			}
			if(panAttatchments != null && !panAttatchments.isEmpty()) {
				pan = panAttatchments.stream().map(Object::toString).collect(Collectors.joining(","));
			}
			if(otherAttatchments != null && !otherAttatchments.isEmpty()) {
				other = otherAttatchments.stream().map(Object::toString).collect(Collectors.joining(","));
			}
//			String aadhar = aadharAttatchments.stream().map(Object::toString).collect(Collectors.joining(","));
//			String gst = gstAttatchments.stream().map(Object::toString).collect(Collectors.joining(","));
//			String pan = panAttatchments.stream().map(Object::toString).collect(Collectors.joining(","));
//			String other = otherAttatchments.stream().map(Object::toString).collect(Collectors.joining(","));

			String result = aadhar + "," + gst + "," + pan + "," + other;
			System.out.println(result);
			String customerId = payload.getClientPo().getCustomerId();

			Optional<UmCustomers> findById = rmsUmCustomersRepo.findById(customerId);
			UmCustomers umCustomers = findById.get();
			umCustomers.setAttatchId(result);
			umCustomers.setKycRequired(false);
			rmsUmCustomersRepo.save(umCustomers);

			List<String> allAttachments = new ArrayList<>();
			allAttachments.addAll(kycAttatchments.getAadharAttatchments());
			allAttachments.addAll(kycAttatchments.getGstAttatchments());
			allAttachments.addAll(kycAttatchments.getOtherAttatchments());
			allAttachments.addAll(kycAttatchments.getPanAttatchments());

			List<Attachments> attachments = rmsAttachmentsRepo.getAllAttachmentsByCustomerId(customerId);
			List<String> attIds = new ArrayList<String>();
			if (attachments != null && !attachments.isEmpty()) {
				for (Attachments a : attachments) {
					attIds.add(a.getAttachId());
				}
				rmsAttachmentsRepo.removeAttachmentsByCustomerId(true, loggedUser.getUserId(), new Date(), attIds);
			}
			rmsAttachmentsRepo.updateAttachemets(customerId, loggedUser.getUserId(), new Date(), allAttachments);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public GenericApiResponse removeOldCardDet(CreateOrder payload, LoggedUser loggedUser) {

		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		genericApiResponse.setMessage("Successfully Updated");
		try {
			rmsClfPublishDates.removeClfPublishDatesOnItemId(true, loggedUser.getUserId(), new Date(),
					payload.getItemId());

			rmsClfEditionsRepo.removeClfEditionsOnItemId(true, loggedUser.getUserId(), new Date(), payload.getItemId());
			
			rmsOrderDiscountTypesRepo.removeDiscounTypes(true,loggedUser.getUserId(),new Date(), payload.getOrderId());

		} catch (Exception e) {
			e.printStackTrace();
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Something went wrong. Please contact our administrator.");
		}
		return genericApiResponse;

	}

	public ClientPo populateRmsCustomerDetails(ClientPo customerDetails, LoggedUser loggedUser) {
		if (customerDetails != null && customerDetails.getCustomerId() != null
				&& !customerDetails.getCustomerId().isEmpty()) {
			Optional<UmCustomers> customerDetailsFromDb = rmsUmCustomersRepo.findById(customerDetails.getCustomerId());
			if (customerDetailsFromDb.isPresent()) {
				customerDetailsFromDb.get()
						.setAddress1(customerDetails.getAddress1() != null ? customerDetails.getAddress1()
								: customerDetailsFromDb.get().getAddress1());
				customerDetailsFromDb.get()
						.setAddress2(customerDetails.getAddress2() != null ? customerDetails.getAddress2()
								: customerDetailsFromDb.get().getAddress2());
				customerDetailsFromDb.get()
						.setAddress3(customerDetails.getAddress3() != null ? customerDetails.getAddress3()
								: customerDetailsFromDb.get().getAddress3());
				customerDetailsFromDb.get()
						.setEmailId(customerDetails.getEmailId() != null ? customerDetails.getEmailId()
								: customerDetailsFromDb.get().getEmailId());
				customerDetailsFromDb.get().setCity(customerDetails.getCity() != null ? customerDetails.getCity()
						: customerDetailsFromDb.get().getCity());
				customerDetailsFromDb.get().setState(customerDetails.getState() != null ? customerDetails.getState()
						: customerDetailsFromDb.get().getState());
				customerDetailsFromDb.get()
						.setMobileNo(customerDetails.getMobileNo() != null ? customerDetails.getMobileNo()
								: customerDetailsFromDb.get().getMobileNo());
				customerDetailsFromDb.get().setGstNo(customerDetails.getGstNo() != null ? customerDetails.getGstNo()
						: customerDetailsFromDb.get().getGstNo());
				customerDetailsFromDb.get()
						.setPinCode(customerDetails.getPinCode() != null ? customerDetails.getPinCode()
								: customerDetailsFromDb.get().getPinCode());
				customerDetailsFromDb.get()
						.setCustomerName(customerDetails.getCustomerName() != null ? customerDetails.getCustomerName()
								: customerDetailsFromDb.get().getCustomerName());
				customerDetailsFromDb.get()
						.setClientCode(customerDetails.getClientCode() != null ? customerDetails.getClientCode()
								: customerDetailsFromDb.get().getClientCode());
				customerDetailsFromDb.get().setCustomerTypeId(1);
				customerDetailsFromDb.get()
				.setMobileAlt(customerDetails.getMobileAlt() != null ? customerDetails.getMobileAlt()
						: customerDetailsFromDb.get().getMobileAlt());
				
				customerDetailsFromDb.get()
				.setHouseNo(customerDetails.getHouseNo() != null ? customerDetails.getHouseNo()
						: customerDetailsFromDb.get().getHouseNo());
				rmsUmCustomersRepo.save(customerDetailsFromDb.get());
			} else {
				addRmsCustomer(customerDetails, loggedUser);
			}

		} else {
			addRmsCustomer(customerDetails, loggedUser);
		}
		return customerDetails;
	}

	public boolean addRmsCustomer(ClientPo customerDetails, LoggedUser loggedUser) {
		try {
			List<UmCustomers> customerDetailsList = rmsUmCustomersRepo
					.getCustomerDetails(customerDetails.getMobileNo());
			UmCustomers umCustomers = new UmCustomers();
			BeanUtils.copyProperties(customerDetails, umCustomers);
			if (!customerDetailsList.isEmpty()) {
				umCustomers = customerDetailsList.get(0);
				umCustomers.setChangedBy(loggedUser.getUserId());
				umCustomers.setChangedTs(new Date());
				customerDetails.setCustomerId(umCustomers.getCustomerId());
			} else {
				umCustomers.setCustomerId(UUID.randomUUID().toString());
				umCustomers.setCreatedBy(loggedUser.getUserId());
				umCustomers.setCreatedTs(new Date());
				umCustomers.setMarkAsDelete(false);
				umCustomers.setEmailId(customerDetails.getEmailId());
				umCustomers.setAddress1(customerDetails.getAddress1());
				umCustomers.setAddress2(customerDetails.getAddress2());
				umCustomers.setAddress3(customerDetails.getAddress3());
				umCustomers.setPinCode(customerDetails.getPinCode());
				umCustomers.setCity(customerDetails.getCity());
				umCustomers.setState(customerDetails.getState());
				umCustomers.setMobileNo(customerDetails.getMobileNo());
				umCustomers.setGstNo(customerDetails.getGstNo());
				umCustomers.setClientCode(customerDetails.getClientCode());
				umCustomers.setCustomerTypeId(customerDetails.getCustomerDetails());
				umCustomers.setKycRequired(true);
				umCustomers.setMobileAlt(customerDetails.getMobileAlt());
				umCustomers.setHouseNo(customerDetails.getHouseNo());
				umCustomers.setIsMaster(false);
				if("36".equalsIgnoreCase(customerDetails.getState())) {
					String clientCode = this.generateSeries("TG");
					umCustomers.setClientCode(clientCode);
				}else {
					String clientCode = this.generateSeries("AP");
					umCustomers.setClientCode(clientCode);
				}
			}
			umCustomers.setUserId(customerDetails.getUserId());
			rmsUmCustomersRepo.save(umCustomers);
			customerDetails.setCustomerId(umCustomers.getCustomerId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public ClfOrders getOpenCartDetails(LoggedUser loggedUser) {
		List<ClfOrders> openOrderDetails = rmsClfOrdersRepo.getOpenOrderDetails(loggedUser.getUserId(),
				RmsConstants.ORDER_OPEN);
		if (!openOrderDetails.isEmpty()) {
			if (openOrderDetails.get(0).getOrderStatus().equalsIgnoreCase(RmsConstants.ORDER_OPEN)) {
				return openOrderDetails.get(0);
			} else {
				openOrderDetails.get(0).setMarkAsDelete(true);
				rmsClfOrdersRepo.save(openOrderDetails.get(0));
				return null;
			}
		}
		return null;
	}

	public String generateSeries(String type) {
		StringBuilder sb = new StringBuilder();
		try {
			List<GdNumberSeries> gdNumberSeriesList = gdNumberSeriesRepo.getNumberSeriesByType(type);
			boolean currentYearSeriesFlag = false;
			String currentYear = CommonUtils.dateFormatter(new Date(), "Y");
			String currentYearSeriesFormat = CommonUtils.dateFormatter(new Date(), "yy");
			if (!gdNumberSeriesList.isEmpty()) {
				currentYearSeriesFormat = CommonUtils.dateFormatter(new Date(),
						gdNumberSeriesList.get(0).getYearFormat());
				GdNumberSeries gdCurrentYearNumberSeries = null;
				GdNumberSeries gdNumberSeries = new GdNumberSeries();
				for (GdNumberSeries gns : gdNumberSeriesList) {
					if (gns.getYear() != null && gns.getYear().equalsIgnoreCase(currentYear)) {
						currentYearSeriesFlag = true;
						gdCurrentYearNumberSeries = gns;
					} else {
						gdNumberSeries = gns;
					}
				}
				if (gdCurrentYearNumberSeries == null) {
					gdCurrentYearNumberSeries = gdNumberSeries;
				}
				sb.append(gdCurrentYearNumberSeries.getPrefix());
				sb.append(currentYearSeriesFormat);
				BigDecimal upComingSeries = gdCurrentYearNumberSeries.getCurrentSeries().add(new BigDecimal(1));
				for (int i = 0; i < gdCurrentYearNumberSeries.getSeriesLength() - (upComingSeries + "").length(); i++) {
					sb.append("0");
				}
				sb.append(upComingSeries);
				System.out.println(sb.toString());
				if (!currentYearSeriesFlag) {
//					gdCurrentYearNumberSeries.setId(UUID.randomUUID().toString());
					gdCurrentYearNumberSeries.setYear(currentYear);
				}
				gdCurrentYearNumberSeries.setCurrentSeries(upComingSeries);
				gdNumberSeriesRepo.save(gdCurrentYearNumberSeries);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public GenericApiResponse getCustomerDetails(String clientCode,LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			List<Object[]> customerClientCodeOrNameorMobileNumber = new ArrayList<Object[]>();
			if(loggedUser.getRoleType().equalsIgnoreCase("SUPER_ADMIN")) {
				if(clientCode!=null &&!clientCode.isEmpty()) {
					clientCode = clientCode.toLowerCase();
					customerClientCodeOrNameorMobileNumber = rmsUmCustomersRepo.getCustomerDetailsOnClientCode(clientCode);
				} 
			}else {
				if(clientCode!=null &&!clientCode.isEmpty()) {
					UmUsers approverEmails = umUsersRepository.getApproverEmails(loggedUser.getUserId());
					if(approverEmails!= null) {
						if(approverEmails.getEmpCode() != null) {
							clientCode = clientCode.toLowerCase();
							Integer empCode = Integer.parseInt(approverEmails.getEmpCode());
							 customerClientCodeOrNameorMobileNumber = rmsUmCustomersRepo.getCustomerDetailsOnClientCodeAndLoggedUser(clientCode, empCode);
						}
					}
				} 
			}
//			if(clientCode!=null &&!clientCode.isEmpty()) {
//				clientCode = clientCode.toLowerCase();
//				customerClientCodeOrNameorMobileNumber = rmsUmCustomersRepo.getCustomerDetailsOnClientCode(clientCode);
//			} 
//			else {
//				customerClientCodeOrNameorMobileNumber = rmsUmCustomersRepo.getCustomerDetailsOnName(customerName);
//			}

			if (customerClientCodeOrNameorMobileNumber == null || customerClientCodeOrNameorMobileNumber.size() == 0) {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("No customer found");
				return genericApiResponse;
			}
			
//			Map<String, RmsCustomerDetails> classifiedsMap = new HashMap<String, RmsCustomerDetails>();
			List<RmsCustomerDetails> customerDetailsList = new ArrayList<>();

			for (Object[] obj : customerClientCodeOrNameorMobileNumber) {
				RmsCustomerDetails customerDetails = new RmsCustomerDetails();
				
				/*
				 * customerDetails.setState((String) obj[0]); customerDetails.setCity((String)
				 * obj[1]); //customerDetails.setCityDesc((String) obj[2]);
				 * customerDetails.setStateDesc((String) obj[2]);
				 * customerDetails.setCustomerId((String) obj[3]);
				 * customerDetails.setCustomerName((String) obj[4]);
				 * customerDetails.setMobileNo((String) obj[5]);
				 * customerDetails.setClientCode((String) obj[6]);
				 * customerDetails.setAttatchId((String) obj[7]);
				 * customerDetails.setHouseNo((String) obj[8]);
				 * customerDetails.setChangedTs((Date) obj[9]);
				 * customerDetails.setChangedBy((Integer) obj[10]);
				 * customerDetails.setCreatedTs((Date) obj[11]);
				 * customerDetails.setCreatedBy((Integer) obj[12]);
				 * customerDetails.setUserId((Integer) obj[13]);
				 * customerDetails.setErpRefId((String) obj[14]);
				 * customerDetails.setAadharNumber((String) obj[15]);
				 * customerDetails.setPanNumber((String) obj[16]);
				 * customerDetails.setGstNo((String) obj[17]);
				 * customerDetails.setOfficeLocation((String) obj[18]);
				 * customerDetails.setPinCode((String) obj[19]);
				 * customerDetails.setAddress3((String) obj[20]);
				 * customerDetails.setAddress2((String) obj[21]);
				 * customerDetails.setAddress1((String) obj[22]);
				 * customerDetails.setEmailId((String) obj[23]); if(obj[25] != null) {
				 * customerDetails.seteKycRequired((boolean) obj[24]); }
				 * customerDetails.setMobileAlt((String) obj[25]);;
				 */

				customerDetails.setState(obj[0] != null ? (String) obj[0] : null);
				customerDetails.setCity(obj[1] != null ? (String) obj[1] : null);
				// customerDetails.setCityDesc(obj[2] != null ? (String) obj[2] : null);
				customerDetails.setStateDesc(obj[2] != null ? (String) obj[2] : null);
				customerDetails.setCustomerId(obj[3] != null ? (String) obj[3] : null);
				customerDetails.setCustomerName(obj[4] != null ? (String) obj[4] : null);
				customerDetails.setMobileNo(obj[5] != null ? (String) obj[5] : null);
				customerDetails.setClientCode(obj[6] != null ? (String) obj[6] : null);
				customerDetails.setAttatchId(obj[7] != null ? (String) obj[7] : null);
				customerDetails.setHouseNo(obj[8] != null ? (String) obj[8] : null);
				customerDetails.setChangedTs(obj[9] != null ? (Date) obj[9] : null);
				customerDetails.setChangedBy(obj[10] != null ? (Integer) obj[10] : null);
				customerDetails.setCreatedTs(obj[11] != null ? (Date) obj[11] : null);
				customerDetails.setCreatedBy(obj[12] != null ? (Integer) obj[12] : null);
				customerDetails.setUserId(obj[13] != null ? (Integer) obj[13] : null);
				customerDetails.setErpRefId(obj[14] != null ? (String) obj[14] : null);
				customerDetails.setAadharNumber(obj[15] != null ? (String) obj[15] : null);
				customerDetails.setPanNumber(obj[16] != null ? (String) obj[16] : null);
				customerDetails.setGstNo(obj[17] != null ? (String) obj[17] : null);
				customerDetails.setOfficeLocation(obj[18] != null ? (String) obj[18] : null);
				customerDetails.setPinCode(obj[19] != null ? (String) obj[19] : null);
				customerDetails.setAddress3(obj[20] != null ? (String) obj[20] : null);
				customerDetails.setAddress2(obj[21] != null ? (String) obj[21] : null);
				customerDetails.setAddress1(obj[22] != null ? (String) obj[22] : null);
				customerDetails.setEmailId(obj[23] != null ? (String) obj[23] : null);
				customerDetails.seteKycRequired(obj[24] != null ? (boolean) obj[24] : false);
				customerDetails.setMobileAlt(obj[25] != null ? (String) obj[25] : null);

				if(customerDetails.getClientCode() != null && (!customerDetails.getClientCode().startsWith("AP-01") || !customerDetails.getClientCode().startsWith("TG-36"))) {
					customerDetailsList.add(customerDetails);
				}
//				classifiedsMap.put(customerDetails.getClientCode(), customerDetails);
			}

			genericApiResponse.setStatus(0);
			genericApiResponse.setData(customerDetailsList);

		} catch (Exception e) {
			logger.error("Error while uploading additional documents file" + e.getMessage());
			genericApiResponse.setStatus(1);
			genericApiResponse.setErrorcode(prop.getProperty("GEN_002"));
		}

		return genericApiResponse;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public GenericApiResponse getRmsRates(RmsRateModel payload) {
//		GenericApiResponse genericApiResponse = new GenericApiResponse();
//		genericApiResponse.setStatus(0);
//		Double rate = 0.0;
//		Double igst = 0.0;
//		Double sgst = 0.0;
//		Double cgst = 0.0;
//		
//		if ("1".equalsIgnoreCase(payload.getFormatType() + "")) {
//			if (payload != null && payload.getEditions() != null && payload.getFixedFormat() != null
//					&& payload.getPageInstructions() != null && payload.getAdsSubType() != null
//					&& payload.getPageNumber() != null) {
//				// need to create new master table for rate card.
//
//			} else {
//				List<RmsRates> ratesList = rmsRatesRepo.getRates(2, payload.getEditions(), payload.getAdsSubType(),
//						payload.getFixedFormat());
//				if (ratesList != null && !ratesList.isEmpty()) {
//					for (RmsRates objs : ratesList) {
//						rate = rate + (double) (objs.getRate());
//					}
//					
//				} else {
//					genericApiResponse.setStatus(1);
//					genericApiResponse.setMessage("There is no rate card available with the selected details.");
//					return genericApiResponse;
//				}
//			}
//		} else {
//			List<RmsRates> ratesList = rmsRatesRepo.getRates(2, payload.getEditions(), payload.getAdsSubType(),
//					payload.getFixedFormat());
//
//			if (ratesList != null && !ratesList.isEmpty()) {
//				for (RmsRates objs : ratesList) {
//					rate = rate + (double) (objs.getRate());
//				}
//
//				if (payload.getPageInstructions() != null) {
//					GdRmsPositioningDiscount gdRmsPositionDiscount = gdRmsPositioningDiscountRepo
//							.getPositioningDiscount(payload.getPageInstructions());
//					if (gdRmsPositionDiscount != null) {
//						if ("Discount".equalsIgnoreCase(gdRmsPositionDiscount.getPositioningType())) {
//							double poInstDiscount = rate * gdRmsPositionDiscount.getDiscount() / 100;
//							rate = rate - poInstDiscount;
//						} else {
//							// need to write special rate logic.
//							
//						}
//					}
//				}
//
//				if (payload.getPageNumber() != null) {
//					GdRmsPagePositions gdRmsPagePositions = gdRmsPagePositionsRepo
//							.getPagePosition(payload.getPageNumber());
//					if (gdRmsPagePositions != null) {
//						String pagePostionErpCode = gdRmsPagePositions.getErpRefCode();
//					}
//				}
//			} else {
//				genericApiResponse.setStatus(1);
//				genericApiResponse.setMessage("There is no rate card available with the selected details.");
//				return genericApiResponse;
//			}
//		}
//		
////		List<RmsRates> ratesList = rmsRatesRepo.getRates(2, payload.getEditions(), payload.getAdsSubType(),
////				payload.getFixedFormat());
////
////		if (ratesList != null && !ratesList.isEmpty()) {
////			for (RmsRates objs : ratesList) {
////				rate = rate + (double) (objs.getRate());
////			}
////		}
//			
//			//GST IGST
//			if(payload.getEmployeeState().equals(payload.getCustomerIdStateCode())) {
//				//this is IGST with 5%
//				igst = rate * 5 / 100 ;
//				rate = rate + igst;
//			}else {
//				//this is CGST and SGST with 2.5 and 2.5
//				cgst = rate * 2.5 / 100 ;
//				sgst = rate * 2.5 / 100 ;
//				rate = rate + cgst + sgst;
//			}
//			
//			payload.setRate(rate);
//			genericApiResponse.setData(payload);
////		} else {
////			genericApiResponse.setStatus(1);
////			genericApiResponse.setMessage("There is no rate card available with the selected details.");
////		}
//		return genericApiResponse;
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getRmsRates(RmsRateModel payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		RmsRatesResponse ratesResponse = new RmsRatesResponse();
		genericApiResponse.setStatus(0);
		Double rate = 0.0;
		Double igst = 0.0;
		Double sgst = 0.0;
		Double cgst = 0.0;
		Integer billableDays = 1;
		Double perSq;
		boolean withSchemeFlag = false;
		double baseAmount = 0.0;
		double cardRate = 0.0;

		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat df1 = new DecimalFormat("#.###");
		df1.setRoundingMode(java.math.RoundingMode.FLOOR);
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		payload.setWithSchemeFlag(false);
		GdRmsSchemes gdRmsSchems = new GdRmsSchemes();
		if (payload.getSchemeId() != null) {
			gdRmsSchems = gdRmsSchemesRepo.getSchemeDetails(payload.getSchemeId());
			if (gdRmsSchems != null && gdRmsSchems.getBillableDays() != null) {
				billableDays = gdRmsSchems.getBillableDays();
			}else {
				billableDays = payload.getBillableDays();
			}
		}

		if ("1".equalsIgnoreCase(payload.getFormatType() + "")) {
			if (payload != null && payload.getEditions() != null && payload.getFixedFormat() != null
					&& payload.getPageInstructions() != null && payload.getAdsSubType() != null
					&& payload.getPageNumber() != null) {
				// need to create new master table for rate card.

			} else {
				List<RmsRates> ratesList = rmsRatesRepo.getRates(2, payload.getEditions(), payload.getAdsSubType(),
						payload.getFixedFormat());
				if (ratesList != null && !ratesList.isEmpty()) {
					for (RmsRates objs : ratesList) {
//						rate = rate + (double) (objs.getRate());
						double amount = objs.getRate();
						cardRate = cardRate + amount;
						if (billableDays != null) {
							amount = amount * billableDays;
						}else {
							amount = amount * payload.getBillableDays();
						}
						baseAmount = baseAmount + amount;

						ratesResponse = this.calculateTaxValue(payload, amount,ratesResponse);
//						
						rate = rate + ratesResponse.getAmount();
//						if (payload.getEmployeeState().equals(payload.getCustomerIdStateCode())) {
//							igst = igst + ratesResponse.getIgst();
//						} else {
//							cgst = cgst + ratesResponse.getCgst();
//							sgst = sgst + ratesResponse.getSgst();
//						}

					}
					if (rate - Math.floor(rate) >= 0.50) {
						rate = Math.ceil(rate);
					} else {
						rate = Math.floor(rate);
					}
					
					ratesResponse.setCardRate(cardRate);
					ratesResponse.setGrandTotal(rate);
					ratesResponse.setGrandTotalString(String.format("%.2f", rate));
					ratesResponse.setAmount(baseAmount);
					ratesResponse.setAmountString(String.format("%.2f", ratesResponse.getAmount()));
					if(billableDays != null) {
						ratesResponse.setBillableDays(billableDays);
					}else {
						ratesResponse.setBillableDays(payload.getBillableDays());
					}
//					ratesResponse.setTotalAmount(rate);
//					ratesResponse.setCgst(cgst);
//					ratesResponse.setIgst(igst);
//					ratesResponse.setSgst(sgst);

				} else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("There is no rate card available with the selected details.");
					return genericApiResponse;
				}
			}
		} else {
			List<RmsRates> ratesList = rmsRatesRepo.getRates(2, payload.getEditions(), payload.getAdsSubType(),
					payload.getFixedFormat());

			if (ratesList != null && !ratesList.isEmpty()) {
				
				if ("2".equalsIgnoreCase(payload.getAddType() + "")) { // multiple discount applicable only for
																		// Commercial ad type
					ratesResponse = this.calculateMultiDiscount(ratesResponse.getAmount(), payload, ratesResponse,
							gdRmsSchems, ratesList);
				}
				
				for (RmsRates objs : ratesList) {
					double amount = objs.getRate();
					cardRate = cardRate + amount;
					perSq = payload.getWidth() * payload.getHeight();
					amount = amount * perSq;
					baseAmount = baseAmount + amount;
					ratesResponse.setAmount(amount);
					
					//write multiple discount logic
//					if (payload.getAdditionalDiscountPercentage() != null) {
//						ratesResponse = this.calculateMultiDiscount(ratesResponse.getAmount(), payload, ratesResponse,
//								objs,gdRmsSchems);
						if("2".equalsIgnoreCase(payload.getAddType() + "") && payload.getEditionCountForMutiDiscount() != null && payload.getEditionCountForMutiDiscount() > 1 && payload.getValidEditionsForMultipleDiscount().contains(objs.getEditionId())) {
							ratesResponse = this.calculateMulDisOnEditions(ratesResponse.getAmount(),payload,ratesResponse,objs,gdRmsSchems);
						}
//					}
					
					
					//Page Position discount (Category discount)
					if ("2".equalsIgnoreCase(payload.getAddType() + "") && payload.getPageInstructions() != null) {
						
						ratesResponse  = this.calculateCategoryDiscount(payload,ratesResponse,ratesResponse.getAmount(),objs);
						
//						GdRmsPositioningDiscount gdRmsPositionDiscount = gdRmsPositioningDiscountRepo
//								.getPositioningDiscount(payload.getPageInstructions());
//						if (gdRmsPositionDiscount != null) {
//							if ("Discount".equalsIgnoreCase(gdRmsPositionDiscount.getTypeOfPosition())) {
//								double poInstDiscount = amount * gdRmsPositionDiscount.getDiscount() / 100;
//
//								poInstDiscount = Double.valueOf(df1.format(poInstDiscount));
//								BigDecimal poInstDiscountAmt = BigDecimal.valueOf(poInstDiscount);
//								poInstDiscountAmt = poInstDiscountAmt.setScale(2, RoundingMode.HALF_UP);
//								double poInstAmt = poInstDiscountAmt.doubleValue();
//
//								amount = amount - poInstAmt;
//							} else {
//								// need to write special rate logic.
//								GdRmsAnnexure gdRmsAnnexure = gdRmsAnnexureRepo.getSpecialRate(objs.getEditionId(),
//										payload.getAdsSubType(), payload.getSchemeId(),gdRmsPositionDiscount.getErpRefId());
//								if (gdRmsAnnexure != null) {
//									amount = gdRmsAnnexure.getRate();
//									perSq = payload.getWidth() * payload.getHeight();
//									amount = amount * perSq;
//									withSchemeFlag = true;
//								} else {
//									GdRmsAnnexure gdRmsAnnexure1 = gdRmsAnnexureRepo
//											.getSpecialRateWithoutScheme(objs.getEditionId(), payload.getAdsSubType(),gdRmsPositionDiscount.getErpRefId());
//									if (gdRmsAnnexure1 != null) {
//										amount = gdRmsAnnexure1.getRate();
//										perSq = payload.getWidth() * payload.getHeight();
//										amount = amount * perSq;
//									}
//								}
//							}
//						}
					}
					//end of page position discount (Category discount)
					
					
					//Additional discount logic
					if(payload.getAdditionalDiscountPercentage() != null) {
						
						ratesResponse = this.calculateAdditinalDiscount(ratesResponse.getAmount(),payload,ratesResponse);
						
						
//						double additionalDiscount = amount * payload.getAdditionalDiscountPercentage() / 100;
//						additionalDiscount = Double.valueOf(df1.format(additionalDiscount));
//						BigDecimal additionalDiscountAmt = BigDecimal.valueOf(additionalDiscount);
//						additionalDiscountAmt = additionalDiscountAmt.setScale(2, RoundingMode.HALF_UP);
//						double addDiscountAmt = additionalDiscountAmt.doubleValue();
//						
//						amount = amount - addDiscountAmt;
					}
					
					//Premium
					if ("1".equalsIgnoreCase(payload.getEditionType()+"") && !"5".equalsIgnoreCase(payload.getAddType() + "") && payload.getPageNumber() != null && !payload.getWithSchemeFlag()) {
						
						ratesResponse = this.calculatePageWiseRate(ratesResponse,ratesResponse.getAmount(),payload,objs);
						
//						GdRmsPagePositions gdRmsPagePositions = gdRmsPagePositionsRepo
//								.getPagePosition(payload.getPageNumber());
//						if (gdRmsPagePositions != null) {
//							String pagePostionErpCode = gdRmsPagePositions.getErpRefCode();
//							if (pagePostionErpCode != null) {
//								GdRmsPageRate gdRmsPageRate = gdRmsPageRateRepo.getPagePercentage(objs.getEditionId(),
//										pagePostionErpCode);
//								if (gdRmsPageRate != null) {
//									double pageRate = amount * gdRmsPageRate.getPercentage() / 100;
//									amount = amount + pageRate;
//								}
//							}
//						}
					}
					
					//premium discount (disocunt will applly on page rate)
//					if(payload.getAdditionalDiscountPercentage() != null){
//						
//					}
					
					// need to write special rate validation (scheme)
					if (!payload.getWithSchemeFlag()) {
						Double amt = 0.0;
						if(billableDays != null) {
							amt = ratesResponse.getAmount() * billableDays;
							ratesResponse.setBillableDays(billableDays);
						}else {
							amt = ratesResponse.getAmount() * payload.getBillableDays();
							ratesResponse.setBillableDays(payload.getBillableDays());
						}
						ratesResponse.setAmount(amt);
//						amount = amount * billableDays;
					}

					ratesResponse = this.calculateTaxValue(payload, ratesResponse.getAmount(),ratesResponse);
//					
					rate = rate + ratesResponse.getAmount();
//					if (payload.getEmployeeState().equals(payload.getCustomerIdStateCode())) {
//						igst = igst + ratesResponse.getIgst();
//					} else {
//						cgst = cgst + ratesResponse.getCgst();
//						sgst = sgst + ratesResponse.getSgst();
//					}

				}
				if (rate - Math.floor(rate) >= 0.50) {
					rate = Math.ceil(rate);
				} else {
					rate = Math.floor(rate);
				}
				
				ratesResponse.setCardRate(cardRate);
				ratesResponse.setGrandTotal(rate);
				ratesResponse.setGrandTotalString(String.format("%.2f", rate));
				ratesResponse.setAmount(baseAmount);
				ratesResponse.setAmountString(String.format("%.2f", baseAmount));
				if(ratesResponse.getDiscountTotal() != null) {
					ratesResponse.setAfterDiscountTotal(ratesResponse.getAmount() - ratesResponse.getDiscountTotal());
				}
				if(ratesResponse.getPremiumTotal() != null && ratesResponse.getAfterDiscountTotal() != null) {
					ratesResponse.setAfterPremiumTotal(ratesResponse.getAfterDiscountTotal() + ratesResponse.getPremiumTotal());
				}else {
					if(ratesResponse.getPremiumTotal() != null) {
						ratesResponse.setAfterPremiumTotal(ratesResponse.getAmount() + ratesResponse.getPremiumTotal());
					}
				}
//				ratesResponse.setTotalAmount(rate);
//				ratesResponse.setCgst(cgst);
//				ratesResponse.setIgst(igst);
//				ratesResponse.setSgst(sgst);

			} else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("There is no rate card available with the selected details.");
				return genericApiResponse;
			}
		}
//		payload.setRate(rate);
		genericApiResponse.setData(ratesResponse);
		return genericApiResponse;
	}

	private RmsRatesResponse calculateMulDisOnEditions(Double amount, RmsRateModel payload,
			RmsRatesResponse ratesResponse, RmsRates objs, GdRmsSchemes gdRmsSchems) {
		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat df1 = new DecimalFormat("#.###");
		df1.setRoundingMode(java.math.RoundingMode.FLOOR);
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		Double multipleDiscount = 0.0;
		Double multiDiscountPercentage = 0.0;
		try {
			if ("2".equalsIgnoreCase(payload.getEditionType() + "")) { // district edition validation
				if (payload.getEditionCountForMutiDiscount() >= 3) {
					multiDiscountPercentage = 20.0;
					multipleDiscount = amount * 20 / 100;
				} else {
					multiDiscountPercentage = 15.0;
					multipleDiscount = amount * 15 / 100;
				}
			} else if ("3".equalsIgnoreCase(payload.getEditionType() + "")) { // division edition validation
				multiDiscountPercentage = 15.0;
				multipleDiscount = amount * 15 / 100;
			} else if ("7".equalsIgnoreCase(payload.getEditionType() + "")) { // region edition validation
				multiDiscountPercentage = 10.0;
				multipleDiscount = amount * 10 / 100;
			}

			multipleDiscount = Double.valueOf(df1.format(multipleDiscount));
			BigDecimal multipleDiscountAmt = BigDecimal.valueOf(multipleDiscount);
			multipleDiscountAmt = multipleDiscountAmt.setScale(2, RoundingMode.HALF_UP);
			double mulDiscountAmt = multipleDiscountAmt.doubleValue();

			amount = amount - mulDiscountAmt;
			ratesResponse.setAmount(amount);
			ratesResponse.setDiscountTotal(
					ratesResponse.getDiscountTotal() != null ? ratesResponse.getDiscountTotal() + mulDiscountAmt
							: mulDiscountAmt);
			
			ratesResponse.setDiscountTotalString(String.format("%.2f", ratesResponse.getDiscountTotal()));

			List<RmsDiscountModel> discountModelList = ratesResponse.getDiscounts();
			if (discountModelList != null && !discountModelList.isEmpty()) {
				boolean flag = false;
				for (RmsDiscountModel discountModel : discountModelList) {
					if (discountModel.getType() != null && "Multi Discount".equalsIgnoreCase(discountModel.getType())) {
						flag = true;
						discountModel.setAmount(discountModel.getAmount() + mulDiscountAmt);
						discountModel.setAmountString(String.format("%.2f", discountModel.getAmount()));
					} 
					
					if(!flag) {
						discountModel.setType("Multi Discount");
						discountModel.setPercent(multiDiscountPercentage);
						discountModel.setPercentString(String.format("%.2f",discountModel.getPercentString()));
						discountModel.setAmount(mulDiscountAmt);
						discountModel.setAmountString(String.format("%.2f", discountModel.getAmount()));
					}
					
//					else {
//						discountModel.setType("Multi Discount");
//						discountModel.setPercent(payload.getAdditionalDiscountPercentage());
//						discountModel.setAmount(mulDiscountAmt);
//
//					}
				}
			} else {
				RmsDiscountModel discountModel = new RmsDiscountModel();
				List<RmsDiscountModel> discountModelList1 = new ArrayList<RmsDiscountModel>();
				discountModel.setType("Multi Discount");
				discountModel.setPercent(multiDiscountPercentage);
				discountModel.setPercentString(String.format("%.2f",discountModel.getPercentString()));
				discountModel.setAmount(mulDiscountAmt);
				discountModel.setAmountString(String.format("%.2f", discountModel.getAmount()));
				discountModelList1.add(discountModel);
				ratesResponse.setDiscounts(discountModelList1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratesResponse;
	}

//	@SuppressWarnings("null")
//	private RmsRatesResponse calculateMultiDiscount(Double amount, RmsRateModel payload, RmsRatesResponse ratesResponse,
//			RmsRates objs, GdRmsSchemes gdRmsSchems) {
//		payload.setWithSchemeFlag(false);
//		try {
//			if ("2".equalsIgnoreCase(payload.getAddType() + "")) { // Skip for government ads.
//				if (!"4".equalsIgnoreCase(payload.getEditionType() + "")) { // Skip multiple discount for combo rates
//																			// i.e combo editiontype
//					if (payload.getPageInstructions() != null) {
//						GdRmsEditions rmsEditions = gdRmsEditionsRepo.getRmsEditionsOnEditionId(objs.getEditionId());
//						if (rmsEditions != null) {
//							if (!"HYC".equalsIgnoreCase(rmsEditions.getErpEdition())) { // skip multiple discount for
//																						// HYC edition
//								GdRmsPositioningDiscount gdRmsPositionDiscount = gdRmsPositioningDiscountRepo
//										.getPositioningDiscount(payload.getPageInstructions());
//								if (gdRmsPositionDiscount != null) {
//									List<GdRmsMultipleDiscountVal> gdRmsMultipleDiscountValList = gdRmsMultipleDiscountValRepo
//											.getMultiDiscountValidationWithScheme(rmsEditions.getErpRefId(),
//													gdRmsPositionDiscount.getErpRefId(), gdRmsSchems.getErpRefId());
//									if (gdRmsMultipleDiscountValList == null
//											&& gdRmsMultipleDiscountValList.isEmpty()) {
//										List<GdRmsMultipleDiscountVal> gdRmsMultipleDiscountValList1 = gdRmsMultipleDiscountValRepo
//												.getMultiDiscountValidationWithoutScheme(rmsEditions.getErpRefId(),
//														gdRmsPositionDiscount.getErpRefId());
//										if (gdRmsMultipleDiscountValList1 == null
//												&& gdRmsMultipleDiscountValList1.isEmpty()) {
//											payload.setEditionCountForMutiDiscount(
//													payload.getEditionCountForMutiDiscount() != null
//															? payload.getEditionCountForMutiDiscount() + 1
//															: 1);
//										}
//
//									}
//								} 
//							}
//						}
//
//					} else {
//							payload.setEditionCountForMutiDiscount(
//									payload.getEditionCountForMutiDiscount() != null
//											? payload.getEditionCountForMutiDiscount() + 1
//											: 1);
//						}
//						
//					}
//				}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ratesResponse;
//	}
	
	@SuppressWarnings("null")
	private RmsRatesResponse calculateMultiDiscount(Double amount, RmsRateModel payload, RmsRatesResponse ratesResponse,
			GdRmsSchemes gdRmsSchems, List<RmsRates> ratesList) {
		payload.setWithSchemeFlag(false);
		try {
			for (RmsRates objs : ratesList) {
				GdRmsEditions rmsEditions = new GdRmsEditions();
				if ("2".equalsIgnoreCase(payload.getAddType() + "")) { // Skip for government ads.
					if ("2".equalsIgnoreCase(payload.getEditionType() + "")) { // only for distric edition validation
						if (!"4".equalsIgnoreCase(payload.getEditionType() + "")) { // Skip multiple discount for combo
																					// rates
																					// i.e combo editiontype
							rmsEditions = gdRmsEditionsRepo.getRmsEditionsOnEditionId(objs.getEditionId());
							if (!"HYC".equalsIgnoreCase(rmsEditions.getErpRefId())) {
							if (payload.getPageInstructions() != null) {
//								rmsEditions = gdRmsEditionsRepo.getRmsEditionsOnEditionId(objs.getEditionId());
								if (rmsEditions != null) {
//									if (!"HYC".equalsIgnoreCase(rmsEditions.getErpEdition())) { // skip multiple
																								// discount
																								// for
																								// HYC edition
										GdRmsPositioningDiscount gdRmsPositionDiscount = gdRmsPositioningDiscountRepo
												.getPositioningDiscount(payload.getPageInstructions());
										if (gdRmsPositionDiscount != null) {
											List<GdRmsMultipleDiscountVal> gdRmsMultipleDiscountValList = gdRmsMultipleDiscountValRepo
													.getMultiDiscountValidationWithScheme(rmsEditions.getErpRefId(),
															gdRmsPositionDiscount.getErpRefId(),
															gdRmsSchems.getErpRefId());
											if (gdRmsMultipleDiscountValList == null
													&& gdRmsMultipleDiscountValList.isEmpty()) {
												List<GdRmsMultipleDiscountVal> gdRmsMultipleDiscountValList1 = gdRmsMultipleDiscountValRepo
														.getMultiDiscountValidationWithoutScheme(
																rmsEditions.getErpRefId(),
																gdRmsPositionDiscount.getErpRefId());
												if (gdRmsMultipleDiscountValList1 == null
														&& gdRmsMultipleDiscountValList1.isEmpty()) {
													payload.setEditionCountForMutiDiscount(
															payload.getEditionCountForMutiDiscount() != null
																	? payload.getEditionCountForMutiDiscount() + 1
																	: 1);
													List<Integer> validEditions = payload
															.getValidEditionsForMultipleDiscount();
													validEditions.add(objs.getEditionId());
													payload.setValidEditionsForMultipleDiscount(validEditions);
												}

											}
										}
//									}
								}

							} else {
								payload.setEditionCountForMutiDiscount(payload.getEditionCountForMutiDiscount() != null
										? payload.getEditionCountForMutiDiscount() + 1
										: 1);
								List<Integer> validEditions = payload.getValidEditionsForMultipleDiscount();
								if(validEditions != null && !validEditions.isEmpty()) {
								validEditions.add(objs.getEditionId());
								payload.setValidEditionsForMultipleDiscount(validEditions);
								}else {
									List<Integer> validEditionss = new ArrayList<>();
									validEditionss.add(objs.getEditionId());
									payload.setValidEditionsForMultipleDiscount(validEditionss);
								}
							}
							}

						}
					} else if ("3".equalsIgnoreCase(payload.getEditionType() + "")) { // Division edition validation
						payload.setEditionCountForMutiDiscount(payload.getEditionCountForMutiDiscount() != null
								? payload.getEditionCountForMutiDiscount() + 1
								: 1);

						List<Integer> validEditions = payload.getValidEditionsForMultipleDiscount();
						if(validEditions != null && !validEditions.isEmpty()) {
						validEditions.add(objs.getEditionId());
						payload.setValidEditionsForMultipleDiscount(validEditions);
						}else {
							List<Integer> validEditionss = new ArrayList<>();
							validEditionss.add(objs.getEditionId());
							payload.setValidEditionsForMultipleDiscount(validEditionss);
						}

					} else if ("7".equalsIgnoreCase(payload.getEditionType() + "")) { // Region edition validation

						payload.setEditionCountForMutiDiscount(payload.getEditionCountForMutiDiscount() != null
								? payload.getEditionCountForMutiDiscount() + 1
								: 1);

						List<Integer> validEditions = payload.getValidEditionsForMultipleDiscount();
						if(validEditions != null && !validEditions.isEmpty()) {
						validEditions.add(objs.getEditionId());
						payload.setValidEditionsForMultipleDiscount(validEditions);
						}else {
							List<Integer> validEditionss = new ArrayList<>();
							validEditionss.add(objs.getEditionId());
							payload.setValidEditionsForMultipleDiscount(validEditionss);
						}
					}
				}
			}
			ratesResponse.setMultiDiscountEditionCount(payload.getEditionCountForMutiDiscount() != null ? payload.getEditionCountForMutiDiscount() : null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratesResponse;
	}

	@SuppressWarnings("unused")
	private RmsRatesResponse calculatePageWiseRate(RmsRatesResponse ratesResponse, double amount, RmsRateModel payload,
			RmsRates objs) {
		double pageRate = 0;
		double aggredPremium = 0;
		try {
			
			DecimalFormat df = new DecimalFormat("#.##");
			DecimalFormat df1 = new DecimalFormat("#.###");
			df1.setRoundingMode(java.math.RoundingMode.FLOOR);
			df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);

			GdRmsPagePositions gdRmsPagePositions = gdRmsPagePositionsRepo.getPagePosition(payload.getPageNumber());
			if (payload.getPremiumDiscPercent() != null) {
				aggredPremium = amount * payload.getPremiumDiscPercent() / 100;
				aggredPremium = Double.valueOf(df1.format(aggredPremium));
				BigDecimal aggredPremiumAmt = BigDecimal.valueOf(aggredPremium);
				aggredPremiumAmt = aggredPremiumAmt.setScale(2, RoundingMode.HALF_UP);
				double aggPreAmt = aggredPremiumAmt.doubleValue();
				
				amount = amount + aggPreAmt;
				ratesResponse.setAmount(amount);
				ratesResponse.setAmountString(String.format("%.2f", amount));
				
				ratesResponse.setPremiumTotal(ratesResponse.getPremiumTotal() != null ? ratesResponse.getPremiumTotal() + aggPreAmt : aggPreAmt);
				ratesResponse.setPremiumTotalString(String.format("%.2f", ratesResponse.getPremiumTotal()));
				
				List<RmsPremiumModel> premiumModelList = ratesResponse.getPremium();
				if(premiumModelList != null && !premiumModelList.isEmpty()) {
					boolean flag = false;
					for(RmsPremiumModel premiumModel : premiumModelList) {
						if(premiumModel.getType() != null && "Agreed Premium".equalsIgnoreCase(premiumModel.getType())) {
							flag = true;
							premiumModel.setAmount(premiumModel.getAmount() + aggPreAmt);
							premiumModel.setAmountString(String.format("%.2f",premiumModel.getAmount()));
						}
					}
					if(!flag) {
						RmsPremiumModel premiumModel = new RmsPremiumModel();
						premiumModel.setType("Agreed Premium");
						premiumModel.setPercent(payload.getPremiumDiscPercent());
						premiumModel.setPercentString(String.format("%.2f",premiumModel.getPercent()));
						premiumModel.setAmount(aggPreAmt);
						premiumModel.setAmountString(String.format("%.2f",premiumModel.getAmount()));
						premiumModelList.add(premiumModel);
					}
				}else {
					RmsPremiumModel premiumModel = new RmsPremiumModel();
					List<RmsPremiumModel> premiumModelList1 = new ArrayList<RmsPremiumModel>();
					premiumModel.setType("Agreed Premium");
					premiumModel.setPercent(payload.getPremiumDiscPercent());
					premiumModel.setPercentString(String.format("%.2f",premiumModel.getPercent()));
					premiumModel.setAmount(aggPreAmt);
					premiumModel.setAmountString(String.format("%.2f",premiumModel.getAmount()));
					premiumModelList1.add(premiumModel);
					ratesResponse.setPremium(premiumModelList1);
				}
				
//				aggredPremium = amount * payload.getPremiumDiscPercent() / 100;
//				amount = amount + aggredPremium;
//				ratesResponse.setAmount(amount);
//				ratesResponse.setAmountString(String.format("%.2f", amount));
				
				double aggreadPremiumDiscountPer = gdRmsPagePositions.getPercentage() - payload.getPremiumDiscPercent();
				ratesResponse.setAggredPremium(aggreadPremiumDiscountPer);
				
			} else {

//				GdRmsPagePositions gdRmsPagePositions = gdRmsPagePositionsRepo.getPagePosition(payload.getPageNumber());
				if (gdRmsPagePositions != null) {
					String pagePostionErpCode = gdRmsPagePositions.getErpRefCode();
					pageRate = amount * gdRmsPagePositions.getPercentage() / 100;
					amount = amount + pageRate;
					ratesResponse.setAmount(amount);
					ratesResponse.setAmountString(String.format("%.2f", amount));
					
					ratesResponse.setPremiumTotal(ratesResponse.getPremiumTotal() != null ? ratesResponse.getPremiumTotal() + pageRate : pageRate);
					ratesResponse.setPremiumTotalString(String.format("%.2f", ratesResponse.getPremiumTotal()));
					
					
					List<RmsPremiumModel> premiumModelList = ratesResponse.getPremium();
					if(premiumModelList != null && !premiumModelList.isEmpty()) {
						boolean flag = false;
						for(RmsPremiumModel premiumModel : premiumModelList) {
							if(premiumModel.getType() != null && "Premium".equalsIgnoreCase(premiumModel.getType())) {
								flag = true;
								premiumModel.setAmount(premiumModel.getAmount() + pageRate);
								premiumModel.setAmountString(String.format("%.2f",premiumModel.getAmount()));
							}
						}
						if(!flag) {
							RmsPremiumModel premiumModel = new RmsPremiumModel();
							premiumModel.setType("Premium");
							premiumModel.setPercent(gdRmsPagePositions.getPercentage().doubleValue());
							premiumModel.setPercentString(String.format("%.2f",premiumModel.getPercent()));
							premiumModel.setAmount(pageRate);
							premiumModel.setAmountString(String.format("%.2f",premiumModel.getAmount()));
							premiumModelList.add(premiumModel);
						}
					}else {
						RmsPremiumModel premiumModel = new RmsPremiumModel();
						List<RmsPremiumModel> premiumModelList1 = new ArrayList<RmsPremiumModel>();
						premiumModel.setType("Premium");
						premiumModel.setPercent(gdRmsPagePositions.getPercentage().doubleValue());
						premiumModel.setPercentString(String.format("%.2f",premiumModel.getPercent()));
						premiumModel.setAmount(pageRate);
						premiumModel.setAmountString(String.format("%.2f",premiumModel.getAmount()));
						premiumModelList1.add(premiumModel);
						ratesResponse.setPremium(premiumModelList1);
					}

//				if (pagePostionErpCode != null) {
//					GdRmsPageRate gdRmsPageRate = gdRmsPageRateRepo.getPagePercentage(objs.getEditionId(),
//							pagePostionErpCode);
//					if (gdRmsPageRate != null) {
//						pageRate = amount * gdRmsPageRate.getPercentage() / 100;
//						amount = amount + pageRate;
//						ratesResponse.setAmount(amount);
//						ratesResponse.setAmountString(String.format("%.2f", amount));
//					}
//					
//					
//				}
				}
			}

//			if (payload.getPremiumDiscPercent() != null) {
//				ratesResponse = this.calculatePremiumDiscount(amount, pageRate, ratesResponse, payload);
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratesResponse;
	}

	private RmsRatesResponse calculatePremiumDiscount(double amount, double pageRate, RmsRatesResponse ratesResponse,
			RmsRateModel payload) {
		try {
			
			DecimalFormat df = new DecimalFormat("#.##");
			DecimalFormat df1 = new DecimalFormat("#.###");
			df1.setRoundingMode(java.math.RoundingMode.FLOOR);
			df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
			
			Double premiumDiscount = pageRate * payload.getPremiumDiscPercent() / 100;
			
			premiumDiscount = Double.valueOf(df1.format(premiumDiscount));
			BigDecimal premiumDiscountAmt = BigDecimal.valueOf(premiumDiscount);
			premiumDiscountAmt = premiumDiscountAmt.setScale(2, RoundingMode.HALF_UP);
			double preDiscountAmt = premiumDiscountAmt.doubleValue();
			
			amount = amount - preDiscountAmt;
			ratesResponse.setAmount(amount);
			ratesResponse.setDiscountTotal(ratesResponse.getDiscountTotal() != null ? ratesResponse.getDiscountTotal() + preDiscountAmt : preDiscountAmt);
			
			ratesResponse.setDiscountTotalString(String.format("%.2f", ratesResponse.getDiscountTotal()));
			
			List<RmsDiscountModel> discountModelList = ratesResponse.getDiscounts();
			if(discountModelList != null && !discountModelList.isEmpty()) {
				boolean flag = false;
				for(RmsDiscountModel discountModel : discountModelList) {
					if(discountModel.getType() != null && "Premium Discount".equalsIgnoreCase(discountModel.getType())) {
						flag = true;
						discountModel.setAmount(discountModel.getAmount() + preDiscountAmt);
						discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
					}
//					else {
//						discountModel.setType("Premium Discount");
//						discountModel.setPercent(payload.getAdditionalDiscountPercentage());
//						discountModel.setAmount(preDiscountAmt);
//						
//					}
				}
				if(!flag) {
					RmsDiscountModel discountModel = new RmsDiscountModel();
					discountModel.setType("Premium Discount");
					discountModel.setPercent(payload.getPremiumDiscPercent());
					discountModel.setPercentString(String.format("%.2f",discountModel.getPercent()));
					discountModel.setAmount(preDiscountAmt);
					discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
					discountModelList.add(discountModel);
				}
			}else {
				RmsDiscountModel discountModel = new RmsDiscountModel();
				List<RmsDiscountModel> discountModelList1 = new ArrayList<RmsDiscountModel>();
				discountModel.setType("Premium Discount");
				discountModel.setPercent(payload.getPremiumDiscPercent());
				discountModel.setPercentString(String.format("%.2f",discountModel.getPercent()));
				discountModel.setAmount(preDiscountAmt);
				discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
				discountModelList1.add(discountModel);
				ratesResponse.setDiscounts(discountModelList1);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ratesResponse;
	}

	private RmsRatesResponse calculateCategoryDiscount(RmsRateModel payload, RmsRatesResponse ratesResponse,
			double amount, RmsRates objs) {
		Double perSq;
		boolean withSchemeFlag = false;
		try {
			DecimalFormat df = new DecimalFormat("#.##");
			DecimalFormat df1 = new DecimalFormat("#.###");
			df1.setRoundingMode(java.math.RoundingMode.FLOOR);
			df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
			payload.setWithSchemeFlag(false);

			GdRmsPositioningDiscount gdRmsPositionDiscount = gdRmsPositioningDiscountRepo
					.getPositioningDiscount(payload.getPageInstructions());
			if (gdRmsPositionDiscount != null) {
				if ("Discount".equalsIgnoreCase(gdRmsPositionDiscount.getTypeOfPosition())) {
					double poInstDiscount = amount * gdRmsPositionDiscount.getDiscount() / 100;
					
					if(poInstDiscount != 0.0) {
					poInstDiscount = Double.valueOf(df1.format(poInstDiscount));
					BigDecimal poInstDiscountAmt = BigDecimal.valueOf(poInstDiscount);
					poInstDiscountAmt = poInstDiscountAmt.setScale(2, RoundingMode.HALF_UP);
					double poInstAmt = poInstDiscountAmt.doubleValue();

					amount = amount - poInstAmt;
					ratesResponse.setAmount(amount);
					ratesResponse.setDiscountTotal(ratesResponse.getDiscountTotal() != null ? ratesResponse.getDiscountTotal() + poInstAmt : poInstAmt);
					
					ratesResponse.setDiscountTotalString(String.format("%.2f", ratesResponse.getDiscountTotal()));
					
					List<RmsDiscountModel> discountModelList = ratesResponse.getDiscounts();
					if(discountModelList != null && !discountModelList.isEmpty()) {
						boolean flag = false;
						for(RmsDiscountModel discountModel : discountModelList) {
							if(discountModel.getType() != null && "Position Instruction Discount".equalsIgnoreCase(discountModel.getType())) {
								flag = true;
								discountModel.setAmount(discountModel.getAmount() + poInstAmt);
								discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
							}
//							else {
//								discountModel.setType("Position Instruction Discount");
//								discountModel.setPercent(payload.getAdditionalDiscountPercentage());
//								discountModel.setAmount(poInstAmt);
//								
//							}
						}
						if(!flag) {
							RmsDiscountModel discountModel = new RmsDiscountModel();
							discountModel.setType("Position Instruction Discount");
							discountModel.setPercent((double)gdRmsPositionDiscount.getDiscount());
							discountModel.setPercentString(String.format("%.2f",discountModel.getPercentString()));
							discountModel.setAmount(poInstAmt);
							discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
							discountModelList.add(discountModel);
						}
					}else {
						RmsDiscountModel discountModel = new RmsDiscountModel();
						List<RmsDiscountModel> discountModelList1 = new ArrayList<RmsDiscountModel>();
						discountModel.setType("Position Instruction Discount");
						discountModel.setPercent((double)gdRmsPositionDiscount.getDiscount());
						discountModel.setPercentString(String.format("%.2f",discountModel.getPercentString()));
						discountModel.setAmount(poInstAmt);
						discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
						discountModelList1.add(discountModel);
						ratesResponse.setDiscounts(discountModelList1);
					}
				}
					
					
				} else {
					// need to write special rate logic.
					GdRmsAnnexure gdRmsAnnexure = gdRmsAnnexureRepo.getSpecialRate(objs.getEditionId(),
							payload.getAdsSubType(), payload.getSchemeId(), gdRmsPositionDiscount.getErpRefId());
					if (gdRmsAnnexure != null) {
						amount = gdRmsAnnexure.getRate();
						perSq = payload.getWidth() * payload.getHeight();
						amount = amount * perSq;
						ratesResponse.setAmount(amount);
						withSchemeFlag = true;
						payload.setWithSchemeFlag(true);
					} else {
						GdRmsAnnexure gdRmsAnnexure1 = gdRmsAnnexureRepo.getSpecialRateWithoutScheme(
								objs.getEditionId(), payload.getAdsSubType(), gdRmsPositionDiscount.getErpRefId());
						if (gdRmsAnnexure1 != null) {
							amount = gdRmsAnnexure1.getRate();
							perSq = payload.getWidth() * payload.getHeight();
							amount = amount * perSq;
							ratesResponse.setAmount(amount);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratesResponse;
	}

	private RmsRatesResponse calculateAdditinalDiscount(double amount, RmsRateModel payload, RmsRatesResponse ratesResponse) {
		try {

			DecimalFormat df = new DecimalFormat("#.##");
			DecimalFormat df1 = new DecimalFormat("#.###");
			df1.setRoundingMode(java.math.RoundingMode.FLOOR);
			df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
			
			double additionalDiscount = amount * payload.getAdditionalDiscountPercentage() / 100;
			additionalDiscount = Double.valueOf(df1.format(additionalDiscount));
			BigDecimal additionalDiscountAmt = BigDecimal.valueOf(additionalDiscount);
			additionalDiscountAmt = additionalDiscountAmt.setScale(2, RoundingMode.HALF_UP);
			double addDiscountAmt = additionalDiscountAmt.doubleValue();

			amount = amount - addDiscountAmt;
			ratesResponse.setAmount(amount);
			ratesResponse.setDiscountTotal(ratesResponse.getDiscountTotal() != null ? ratesResponse.getDiscountTotal() + addDiscountAmt : addDiscountAmt);
			
			ratesResponse.setDiscountTotalString(String.format("%.2f", ratesResponse.getDiscountTotal()));
			
						
			List<RmsDiscountModel> discountModelList = ratesResponse.getDiscounts();
			if(discountModelList != null && !discountModelList.isEmpty()) {
				boolean flag = false;
				for(RmsDiscountModel discountModel : discountModelList) {
					if(discountModel.getType() != null && "Additional Discount".equalsIgnoreCase(discountModel.getType())) {
						flag = true;
						discountModel.setAmount(discountModel.getAmount() + addDiscountAmt);
						discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
					}
//					else {
//						discountModel.setType("Additional Discount");
//						discountModel.setPercent(payload.getAdditionalDiscountPercentage());
//						discountModel.setAmount(addDiscountAmt);
//						
//					}
				}
				if(!flag) {
					RmsDiscountModel discountModel = new RmsDiscountModel();
					discountModel.setType("Additional Discount");
					discountModel.setPercent(payload.getAdditionalDiscountPercentage());
					discountModel.setPercentString(String.format("%.2f",discountModel.getPercentString()));
					discountModel.setAmount(addDiscountAmt);
					discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
					discountModelList.add(discountModel);
				}
			}else {
				RmsDiscountModel discountModel = new RmsDiscountModel();
				List<RmsDiscountModel> discountModelList1 = new ArrayList<RmsDiscountModel>();
				discountModel.setType("Additional Discount");
				discountModel.setPercent(payload.getAdditionalDiscountPercentage());
				discountModel.setPercentString(String.format("%.2f",discountModel.getPercent()));
				discountModel.setAmount(addDiscountAmt);
				discountModel.setAmountString(String.format("%.2f",discountModel.getAmount()));
				discountModelList1.add(discountModel);
				ratesResponse.setDiscounts(discountModelList1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratesResponse;
	}

	private RmsRatesResponse calculateTaxValue(RmsRateModel payload, double amount, RmsRatesResponse ratesResponse) {
//		RmsRatesResponse ratesResponse = new RmsRatesResponse();
		try {
			Double rate = 0.0;
			Double igst = 0.0;
			Double sgst = 0.0;
			Double cgst = 0.0;
			
			DecimalFormat df = new DecimalFormat("#.##");
			DecimalFormat df1 = new DecimalFormat("#.###");
			df1.setRoundingMode(java.math.RoundingMode.FLOOR);
			df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
			
			if (payload.getEmployeeState().equals(payload.getCustomerIdStateCode())) {
				// this is IGST with 5%
				igst = amount * 5 / 100;
				
				igst = Double.valueOf(df1.format(igst));
				BigDecimal igstAmt = BigDecimal.valueOf(igst);
				igstAmt = igstAmt.setScale(2, RoundingMode.HALF_UP);
				double roundedIgstAmt = igstAmt.doubleValue();
				
//				amount = amount + igst;
				amount = amount + roundedIgstAmt;
//				ratesResponse.setIgst(roundedIgstAmt);
				ratesResponse.setAmount(amount);
				ratesResponse.setGstTotal(ratesResponse.getGstTotal() != null ? ratesResponse.getGstTotal() + roundedIgstAmt : 0.0 + roundedIgstAmt);
				ratesResponse.setGstTotalString(String.format("%.2f", ratesResponse.getGstTotal()));
				
				List<RmsTaxModel> rmsTaxList = ratesResponse.getTax();
				
				if(rmsTaxList != null && !rmsTaxList.isEmpty()) {
					for(RmsTaxModel taxModel : rmsTaxList) {
						if(taxModel.getType() != null && "IGST".equalsIgnoreCase(taxModel.getType())) {
							taxModel.setAmount(taxModel.getAmount() + roundedIgstAmt);
							taxModel.setAmountString(String.format("%.2f", taxModel.getAmount()));
						}else {
							taxModel.setType("IGST");
							taxModel.setPercent(5.0);
							taxModel.setPercentString(String.format("%.2f", taxModel.getPercent()));
							taxModel.setAmount(roundedIgstAmt);
							taxModel.setAmountString(String.format("%.2f", taxModel.getAmount()));
							
						}
					}
				}else {
					RmsTaxModel taxModel = new RmsTaxModel();
					List<RmsTaxModel> taxModelList1 = new ArrayList<RmsTaxModel>();
					taxModel.setType("IGST");
					taxModel.setPercent(5.0);
					taxModel.setPercentString(String.format("%.2f", taxModel.getPercent()));
					taxModel.setAmount(roundedIgstAmt);
					taxModel.setAmountString(String.format("%.2f", taxModel.getAmount()));
					taxModelList1.add(taxModel);
					ratesResponse.setTax(taxModelList1);
				}
			} else {
				// this is CGST and SGST with 2.5 and 2.5
				cgst = amount * 2.5 / 100;
				sgst = amount * 2.5 / 100;
				
				cgst = Double.valueOf(df1.format(cgst));
				BigDecimal cgstAmt = BigDecimal.valueOf(cgst);
				cgstAmt = cgstAmt.setScale(2, RoundingMode.HALF_UP);
				double roundedCgstAmt = cgstAmt.doubleValue();
				
				sgst = Double.valueOf(df1.format(sgst));
				BigDecimal sgstAmt = BigDecimal.valueOf(sgst);
				sgstAmt = sgstAmt.setScale(2, RoundingMode.HALF_UP);
				double roundedSgstAmt = sgstAmt.doubleValue();
				
//				amount = amount + cgst + sgst;
				amount = amount + roundedCgstAmt + roundedSgstAmt;
//				ratesResponse.setCgst(roundedCgstAmt);
//				ratesResponse.setSgst(roundedSgstAmt);
				ratesResponse.setAmount(amount);
				ratesResponse.setGstTotal(ratesResponse.getGstTotal() != null ? ratesResponse.getGstTotal() + roundedCgstAmt + roundedSgstAmt : 0.0 + roundedCgstAmt + roundedSgstAmt);
				ratesResponse.setGstTotalString(String.format("%.2f", ratesResponse.getGstTotal()));
				
				List<RmsTaxModel> rmsTaxList = ratesResponse.getTax();
				
				if(rmsTaxList != null && !rmsTaxList.isEmpty()) {
					for(RmsTaxModel taxModel : rmsTaxList) {
						if(taxModel.getType() != null && "CGST".equalsIgnoreCase(taxModel.getType())) {
							taxModel.setAmount(taxModel.getAmount() + roundedCgstAmt);
							taxModel.setAmountString(String.format("%.2f", taxModel.getAmount()));
						}else {
							taxModel.setAmount(taxModel.getAmount() + roundedSgstAmt);
							taxModel.setAmountString(String.format("%.2f", taxModel.getAmount()));
//							discountModel.setType("SGST");
//							discountModel.setPercent(2.5);
//							discountModel.setAmount(roundedCgstAmt);
							
						}
					}
				}else {
					RmsTaxModel taxModel = new RmsTaxModel();
					List<RmsTaxModel> taxModelList1 = new ArrayList<RmsTaxModel>();
					taxModel.setType("CGST");
					taxModel.setPercent(2.5);
					taxModel.setPercentString(String.format("%.2f", taxModel.getPercent()));
					taxModel.setAmount(roundedCgstAmt);
					taxModel.setAmountString(String.format("%.2f", taxModel.getAmount()));
					taxModelList1.add(taxModel);
					
					taxModel = new RmsTaxModel();
					taxModel.setType("SGST");
					taxModel.setPercent(2.5);
					taxModel.setPercentString(String.format("%.2f", taxModel.getPercent()));
					taxModel.setAmount(roundedSgstAmt);
					taxModel.setAmountString(String.format("%.2f", taxModel.getAmount()));
					taxModelList1.add(taxModel);
					
					ratesResponse.setTax(taxModelList1);
				}
			}
//			rate = rate + amount;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ratesResponse;
	}

	@Override
	public GenericApiResponse genrateOTP(OtpModel payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);

		if (payload != null && payload.getMobileNo() != null && payload.getCustomerName() != null) {
			String mobileNo = payload.getMobileNo();
			String customerName = payload.getCustomerName();
			String otp = RandomStringUtils.randomNumeric(4);
			Map<String, String> smsDetails = new HashMap<>();
//			if ("false".equalsIgnoreCase(prop.getProperty("ORP_VERIFY_STUB"))
//					|| "0".equalsIgnoreCase(prop.getProperty("ORP_VERIFY_STUB"))) {
				smsDetails = sendMessageService.sendSms(otp, mobileNo,customerName);
//			} else {
//				smsDetails.put("status", "Success");
//			}
			if ("Success".equalsIgnoreCase(smsDetails.get("status"))) {
				OtpVerification otpVerification = new OtpVerification();
				otpVerification.setId(UUID.randomUUID().toString());
				otpVerification.setMobileNo(mobileNo);
				otpVerification.setOtpNum(otp);
//				otpVerification.setOtpStatus("Success");
				otpVerification.setOtpStatus(smsDetails.get("status"));
				otpVerification.setOtpAttempts(0);
				otpVerification.setCreatedTs(new Date());
				otpVerification.setOrderId(payload.getOrderId());
				otpVerification.setOtpValidityTime(new Date());
				otpVerification.setMarkAsDelete(false);
				baseDao.save(otpVerification);
				genericApiResponse.setMessage("OTP Successfully genrated");
			}else {
				genericApiResponse.setMessage(smsDetails.get("statusText"));
				genericApiResponse.setStatus(Integer.parseInt(smsDetails.get("statusCode")));
			}
		} else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Mobile No or Customer Name Not Found");
		}

//		if (payload != null && payload.getOrderId() != null) {
//			ClfOrders clfOrders = rmsClfOrdersRepo.getOrderDetails(payload.getOrderId());
//			if (clfOrders != null && clfOrders.getCustomerId() != null) {
//				UmCustomers umCustomers = rmsUmCustomersRepo.getCustomerDetailsByOrderId(clfOrders.getCustomerId());
//				if (umCustomers != null) {
//					String mobileNo = umCustomers.getMobileNo();
////					String otp = RandomStringUtils.randomNumeric(4);
//					String otp = "1234";
//					Map<String, String> smsDetails = new HashMap<>();
//					if ("false".equalsIgnoreCase(prop.getProperty("ORP_VERIFY_STUB"))
//							|| "0".equalsIgnoreCase(prop.getProperty("ORP_VERIFY_STUB"))) {
//						smsDetails = sendMessageService.sendSms(otp, mobileNo);
//					} else {
//						smsDetails.put("status", "Success");
//					}
//					if ("Success".equalsIgnoreCase(smsDetails.get("status"))) {
//						OtpVerification otpVerification = new OtpVerification();
//						otpVerification.setId(UUID.randomUUID().toString());
//						otpVerification.setMobileNo(mobileNo);
//						otpVerification.setOtpNum(otp);
////						otpVerification.setOtpStatus("Success");
//						otpVerification.setOtpStatus(smsDetails.get("status"));
//						otpVerification.setOtpAttempts(0);
//						otpVerification.setCreatedTs(new Date());
//						otpVerification.setOrderId(payload.getOrderId());
//						otpVerification.setOtpValidityTime(new Date());
//						otpVerification.setMarkAsDelete(false);
//						baseDao.save(otpVerification);
//						genericApiResponse.setMessage("OTP Successfully genrated");
//					}
//				}
//			} else {
//				genericApiResponse.setStatus(1);
//				genericApiResponse.setMessage("Customer Details Not Found");
//			}
//		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse validateOTP(OtpModel payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		OtpVerification otpDetails = new OtpVerification();

		if (payload != null && payload.getMobileNo() != null) {
			String mobileNo = payload.getMobileNo();
			List<OtpVerification> otpDetailsList = otpVerificationRepo.getOtpVerificationDetails(mobileNo,
					payload.getOtp(), payload.getOrderId());
			if (!otpDetailsList.isEmpty()) {
				otpDetails = otpDetailsList.get(0);
			} else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("OTP Incorrect.");
				return genericApiResponse;
			}
			if (otpDetails != null) {
				Date dateFromDB = otpDetails.getOtpValidityTime();
				Date CurrentDate = new Date();
				long diff = CurrentDate.getTime() - dateFromDB.getTime();
				long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
				if (minutes <= 2) {
					if (otpDetails.getOtpNum().equals(payload.getOtp())) {
						genericApiResponse.setStatus(0);
						genericApiResponse.setMessage("OTP Success");
						rmsClfOrdersRepo.updateRmsOnOrderIds("CLOSED", new Date(), payload.getOrderId());
//						List<String> orderIds = new ArrayList<String>();
//						orderIds.add(payload.getOrderId());
//						Map<String, ErpClassifieds> rmsOrderDetailsForErp = this.getRmsOrderDetailsForErp(orderIds);
//						this.sendRmsMailToCustomer(rmsOrderDetailsForErp, null, userContext.getLoggedUser(), null);
					} else {
						genericApiResponse.setStatus(1);
						genericApiResponse.setMessage("OTP Incorrect.");
						return genericApiResponse;
					}
				} else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("OTP Expired");
				}
			} else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("OTP Incorrect.");
			}
		} else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Mobile no not found");
		}

//		if (payload != null && payload.getOrderId() != null) {
//			ClfOrders clfOrders = rmsClfOrdersRepo.getOrderDetails(payload.getOrderId());
//			if (clfOrders != null && clfOrders.getCustomerId() != null) {
//				UmCustomers umCustomers = rmsUmCustomersRepo.getCustomerDetailsByOrderId(clfOrders.getCustomerId());
//				if (umCustomers != null) {
//					String mobileNo = umCustomers.getMobileNo();
//					List<OtpVerification> otpDetailsList = otpVerificationRepo.getOtpVerificationDetails(mobileNo,
//							payload.getOtp(), payload.getOrderId());
//					if (!otpDetailsList.isEmpty()) {
//						otpDetails = otpDetailsList.get(0);
//					} else {
//						genericApiResponse.setStatus(1);
//						genericApiResponse.setMessage("OTP Incorrect.");
//						return genericApiResponse;
//					}
//					if (otpDetails != null) {
//						Date dateFromDB = otpDetails.getOtpValidityTime();
//						Date CurrentDate = new Date();
//						long diff = CurrentDate.getTime() - dateFromDB.getTime();
//						long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
//						if (minutes <= 2) {
//							if (otpDetails.getOtpNum().equals(payload.getOtp())) {
//								genericApiResponse.setStatus(0);
//								genericApiResponse.setMessage("OTP Success");
//								rmsClfOrdersRepo.updateRmsOnOrderIds("CLOSED", new Date(), payload.getOrderId());
//								List<String> orderIds = new ArrayList<String>();
//								orderIds.add(payload.getOrderId());
//								Map<String, ErpClassifieds> rmsOrderDetailsForErp = this
//										.getRmsOrderDetailsForErp(orderIds);
//								this.sendRmsMailToCustomer(rmsOrderDetailsForErp, null, userContext.getLoggedUser(),
//										null);
//							} else {
//								genericApiResponse.setStatus(1);
//								genericApiResponse.setMessage("OTP Incorrect.");
//								return genericApiResponse;
//							}
//						} else {
//							genericApiResponse.setStatus(1);
//							genericApiResponse.setMessage("OTP Expired");
//						}
//					} else {
//						genericApiResponse.setStatus(1);
//						genericApiResponse.setMessage("OTP Incorrect.");
//					}
//				}
//			} else {
//				genericApiResponse.setStatus(1);
//				genericApiResponse.setMessage("Customer Details Not Found");
//			}
//		}
		return genericApiResponse;
	}

	public static Date dateFormatter(String stringDate) {
		Date date = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			date = simpleDateFormat.parse(stringDate);
		} catch (ParseException e) {
			logger.error("Error parsing date: " + stringDate, e);
		}

		return date;
	}

	@SuppressWarnings("null")
	public String generateRmsSeries(Integer bookingCode) {
		StringBuilder sb = new StringBuilder();
		try {
			List<GdRmsNumberSeries> gdNumberSeriesList = gdRmsNumberSeriesRepo
					.getNumberSeriesByBookingCode(bookingCode);
			boolean currentYearSeriesFlag = false;
			String currentYear = CommonUtils.dateFormatter(new Date(), "Y");
			String currentYearSeriesFormat = CommonUtils.dateFormatter(new Date(), "yyyy");
			if (!gdNumberSeriesList.isEmpty()) {
				currentYearSeriesFormat = CommonUtils.dateFormatter(new Date(),
						gdNumberSeriesList.get(0).getYearFormat());
				GdRmsNumberSeries gdCurrentYearNumberSeries = null;
				GdRmsNumberSeries gdNumberSeries = new GdRmsNumberSeries();
				for (GdRmsNumberSeries gns : gdNumberSeriesList) {
					if (gns.getBookingCode() != null && gns.getYear() != null
							&& gns.getYear().equalsIgnoreCase(currentYear)) {
						currentYearSeriesFlag = true;
						gdCurrentYearNumberSeries = gns;
					} else {
						gdNumberSeries = gns;
					}
				}
				if (gdCurrentYearNumberSeries == null) {
					gdCurrentYearNumberSeries = gdNumberSeries;
				}
				sb.append("RMS");
				sb.append(gdCurrentYearNumberSeries.getBookingCode());
				sb.append(currentYearSeriesFormat);
				BigDecimal upComingSeries = gdNumberSeriesList.get(0).getCurrentSeries().add(new BigDecimal(1));
				for (int i = 0; i < gdCurrentYearNumberSeries.getSeriesLength() - (upComingSeries + "").length(); i++) {
					sb.append("0");
				}
				sb.append(upComingSeries);
				System.out.println(sb.toString());
				if (!currentYearSeriesFlag) {
//					gdCurrentYearNumberSeries.setId(UUID.randomUUID().toString());
					gdCurrentYearNumberSeries.setYear(currentYear);
				}
				gdCurrentYearNumberSeries.setCurrentSeries(upComingSeries);
				gdRmsNumberSeriesRepo.save(gdCurrentYearNumberSeries);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public GenericApiResponse syncronizeRmsSAPData(GenericRequestHeaders requestHeaders, @NotNull RmsModel payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			if (payload != null) {
				Map<String, ErpClassifieds> erpClassifieds = this.getRmsOrderDetailsForErp(payload.getOrderId());
				if (!erpClassifieds.isEmpty()) {
					Map<String, Object> payloadJson = new HashMap<String, Object>();
					payloadJson.put("userId", requestHeaders.getLoggedUser().getUserId());
					payloadJson.put("orderId", payload.getOrderId());
					payloadJson.put("orgId", requestHeaders.getOrgId());
					payloadJson.put("data", erpClassifieds);
					payloadJson.put("orgOpuId", requestHeaders.getOrgOpuId());
					payloadJson.put("action", "Rms_order");
					boolean flag = erpService.processRmsCreationFtpFiles(payloadJson, erpClassifieds);
					if (flag) {
						apiResponse.setStatus(0);
						apiResponse.setMessage("Success");
					} else {
						apiResponse.setStatus(1);
						apiResponse.setMessage("Failed");
					}
				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage("Details Not Found For Selected Ads");
				}
			}
		} catch (Exception e) {
//			apiResponse.setMessage(properties.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
		}
		return apiResponse;
	}

//	@SuppressWarnings("unchecked")
//	private Map<String, ErpClassifieds> getRmsOrderDetailsForErp(List<String> orderIds) {
//		List<Object[]> classifiedList = new ArrayList<Object[]>();
//		Map<String, ErpClassifieds> classifiedsMap = new HashMap<>();
//		List<String> itemIds = new ArrayList<String>();
//		List<String> customerIds = new ArrayList<String>();
//		List<Integer> createdByIds = new ArrayList<Integer>();
//		DecimalFormat df = new DecimalFormat("#.###");
//		DecimalFormat df1 = new DecimalFormat("#.##");
//		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
//		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
//		try {
//			String joinedOrderIds = String.join("','", orderIds);
//			// String query = "select itm.item_id, itm.order_id, itm.classified_type,
//			// itm.classified_ads_type, itm.scheme, itm.clf_content, itm.created_by,
//			// itm.created_ts,itm.changed_by, itm.changed_ts, itm.classified_ads_sub_type,
//			// itm.status, itm.ad_id, itm.category_group, itm.sub_group, itm.child_group,
//			// co.customer_id, co.user_type_id, co.booking_unit, coir.total_amount,
//			// gct.type, gct.erp_ref_id as gct_erp_ref_id, gcat.ads_type, gcat.erp_ref_id as
//			// gcat_erp_ref_id, gcast.ads_sub_type, gcast.erp_ref_id as gcast_erp_ref_id,
//			// grs.scheme as grs_scheme, grs.erp_ref_id as grs_erp_ref_id,
//			// gg.classified_group, gg.erp_ref_id as gg_erp_ref_id,
//			// gsg.classified_sub_group, gsg.erp_ref_id as gsg_erp_ref_id,
//			// gcg.classified_child_group, gcg.erp_ref_id as gcg_erp_ref_id,
//			// bu.sales_office, bu.booking_location, bu.sold_to_party,
//			// bu.booking_unit_email, roi.no_of_insertions, roi.size_width, roi.size_height,
//			// roi.page_position, roi.format_type as roi_format_type, roi.fixed_format,
//			// roi.page_number , roi.category_discount , roi.multi_discount ,
//			// roi.additional_discount , roi.surcharge_rate, grff.size, grff.erp_ref_code as
//			// grff_erp_ref_code, grft.format_type as grft_format_type, grmd.discount,
//			// grpp.page_name, grpp.erp_ref_code as grpp_erp_ref_code from clf_order_items
//			// itm inner join gd_classified_types gct on itm.classified_type = gct.id inner
//			// join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner
//			// join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type =
//			// gcast.id inner join gd_rms_schemes grs on itm.scheme = grs.id inner join
//			// gd_classified_group gg on itm.category_group = gg.id inner join
//			// gd_classified_sub_group gsg on itm.sub_group = gsg.id inner join
//			// gd_classified_child_group gcg on itm.child_group = gcg.id inner join
//			// clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders
//			// co on itm.order_id = co.order_id left join booking_units bu on
//			// co.booking_unit = bu.booking_code inner join rms_order_items roi on
//			// itm.item_id = roi.item_id left join gd_rms_fixed_formats grff on
//			// roi.fixed_format = grff.id left join gd_rms_format_types grft on
//			// roi.format_type = grft.id left join gd_rms_multi_discount grmd on
//			// roi.multi_discount = grmd.id left join gd_rms_page_positions grpp on
//			// roi.page_number = grpp.id where itm.order_id in ('"+ joinedOrderIds + "') and
//			// itm.mark_as_delete = false";
//			String query = "select itm.item_id, itm.order_id, itm.classified_type, itm.classified_ads_type, itm.scheme, itm.clf_content, itm.created_by, itm.created_ts,itm.changed_by, itm.changed_ts, itm.classified_ads_sub_type, itm.status, itm.ad_id, itm.category_group, itm.sub_group, itm.child_group, co.customer_id, co.user_type_id, co.booking_unit, coir.total_amount, gct.type, gct.erp_ref_id as gct_erp_ref_id, gcat.ads_type, gcat.erp_ref_id as gcat_erp_ref_id, gcast.ads_sub_type, gcast.erp_ref_id as gcast_erp_ref_id, grs.scheme as grs_scheme, grs.erp_ref_id as grs_erp_ref_id, gg.classified_group, gg.erp_ref_id as gg_erp_ref_id, gsg.classified_sub_group, gsg.erp_ref_id as gsg_erp_ref_id, gcg.classified_child_group, gcg.erp_ref_id as gcg_erp_ref_id, bu.sales_office, bu.booking_location, bu.sold_to_party, bu.booking_unit_email, roi.no_of_insertions, roi.size_width, roi.size_height, roi.page_position, roi.format_type as roi_format_type, roi.fixed_format, roi.page_number , roi.category_discount , roi.multi_discount , roi.additional_discount , roi.surcharge_rate, grff.size, grff.erp_ref_code as grff_erp_ref_code, grft.format_type as grft_format_type, grmd.discount, grpp.page_name, grpp.erp_ref_code as grpp_erp_ref_code,uc.customer_name,uc.client_code,bu.booking_description,gs.state,uc.mobile_no,uc.house_no,uc.gst_no,uc.pin_code,coir.rate,grpd.positioning_desc,grpd.discount as grpd_discount,coir.igst ,coir.cgst,coir.sgst,coir.cgst_value,coir.sgst_value ,coir.igst_value,coir.amount as total_value,rps.bank_branch,rps.cash_receipt_no,roi.space_width,roi.space_height  from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_rms_schemes grs on itm.scheme = grs.id inner join gd_classified_group gg on itm.category_group = gg.id inner join gd_classified_sub_group gsg on itm.sub_group = gsg.id inner join gd_classified_child_group gcg on itm.child_group = gcg.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on itm.order_id = co.order_id left join booking_units bu on co.booking_unit = bu.booking_code inner join rms_order_items roi on itm.item_id = roi.item_id left join gd_rms_fixed_formats grff on roi.fixed_format = grff.id left join gd_rms_format_types grft on roi.format_type = grft.id left join gd_rms_multi_discount grmd on roi.multi_discount = grmd.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join um_customers uc  left join gd_state gs ON uc.state = gs.state_code on co.customer_id = uc.customer_id  left join gd_rms_positioning_discount grpd on roi.page_position=grpd.id inner join rms_payments_response rps on rps.item_id=itm.item_id where itm.order_id in ('"
//					+ joinedOrderIds + "') and itm.mark_as_delete = false";
//			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
//
//			for (Object[] objs : classifiedList) {
//				ErpClassifieds classified = new ErpClassifieds();
//				classified.setItemId((String) objs[0]);
//				classified.setOrderId((String) objs[1]);
//				classified.setClassifiedType((Integer) objs[2]);
//				classified.setClassifiedAdsType((Integer) objs[3]);
//				classified.setScheme((Integer) objs[4]);
//				classified.setContent((String) objs[5]);
//				classified.setCreatedBy((Integer) objs[6]);
//				classified.setCreatedTs(CommonUtils.dateFormatter((Date) objs[7], "yyyy-MM-dd"));
//				classified.setCreatedDate(CommonUtils.dateFormatter((Date) objs[7], "yyyyMMdd"));
//				classified.setBookingDate(CommonUtils.dateFormatter((Date) objs[7], "yyyy-MM-dd HH:mm:ss"));
//				classified.setChangedBy((Integer) objs[8]);
//				classified.setChangedTs(objs[9] != null ? CommonUtils.dateFormatter((Date) objs[9], "ddMMyyyy") : "");
//				classified.setClassifiedAdsSubType((Integer) objs[10]);
//				classified.setContentStatus((String) objs[11]);
//				classified.setAdId((String) objs[12]);
//				classified.setGroup((Integer) objs[13]);
//				classified.setSubGroup((Integer) objs[14]);
//				classified.setChildGroup((Integer) objs[15]);
//				classified.setCustomerId((String) objs[16]);
//				classified.setUserTypeId((Integer) objs[17]);
//				classified.setBookingUnit((Integer) objs[18]);
//				Double val = (Double.valueOf(df.format(objs[19])));
//				classified.setPaidAmount(new BigDecimal(df1.format(val)));
//				classified.setClassifiedTypeStr((String) objs[20]);
//				classified.setClassifiedTypeErpRefId((String) objs[21]);
//				classified.setAdsType((String) objs[22]);
//				classified.setAdsTypeErpRefId((String) objs[23]);
//				classified.setAdsSubType((String) objs[24]);
//				classified.setAdsSubTypeErpRefId((String) objs[25]);
//				classified.setSchemeStr((String) objs[26]);
//				classified.setSchemeErpRefId((String) objs[27]);
//				classified.setGroupStr((String) objs[28]);
//				classified.setGroupErpRefId((String) objs[29]);
//				classified.setSubGroupStr((String) objs[30]);
//				classified.setSubGroupErpRefId((String) objs[31]);
//				classified.setChildGroupStr((String) objs[32]);
//				classified.setChildGroupErpRefId((String) objs[33]);
//				classified.setSalesOffice((String) objs[34]);
//				classified.setBookingUnitStr((String) objs[35]);
//				classified.setSoldToParty((String) objs[36]);
//				classified.setBookingUnitEmail((String) objs[37]);
//				classified.setNoOfInsertions((Integer) objs[38]);
//				classified.setPagePosition((Integer) objs[41]);
//				classified.setFormatType((Integer) objs[42]);
//				classified.setFixedFormat((Integer) objs[43]);
//				classified.setPageNumber((Integer) objs[44]);
//				classified.setCategoryDiscount((Integer) objs[45]);
//				Double mD = (Double.valueOf(df.format(objs[46])));
//				classified.setMultiDiscount(new BigDecimal(df1.format(mD)));
//				Double aD = (Double.valueOf(df.format(objs[47])));
//				classified.setAdditionalDiscount(new BigDecimal(df1.format(aD)));
//				Double sR = (Double.valueOf(df.format(objs[48])));
//				classified.setSurchargeRate(new BigDecimal(df1.format(sR)));
//				classified.setFixedFormatsize((String) objs[49]);
//				classified.setFixedFormatErpRefId((String) objs[50]);
//				classified.setFormatTypeStr((String) objs[51]);
//				classified.setMultiDiscountStr((String) objs[52]);
//				classified.setPagePositionpageName((String) objs[53]);
//				classified.setPagePositionErpRefId((String) objs[54]);
//
//				classified.setCityDisc((String) objs[57]);
//				classified.setStateDisc((String) objs[58]);
//				classified.setRate(new Double((Float) objs[63]));
//				classified.setPositioningDesc((String) objs[64]);
//				classified.setDiscountValue(new Double((Float) objs[65]));
//				if (objs[39] != null) {
//					classified.setSizeWidth(new Double((Float) objs[39]));
//				}
//				if (objs[40] != null) {
//					classified.setSizeHeight(new Double((Float) objs[40]));
//				}
//				if (objs[66] != null) {
//					classified.setIgst(new Double((Float) objs[66]));
//				}
//				if (objs[67] != null) {
//					classified.setCgst(new Double((Float) objs[67]));
//				}
//				if (objs[68] != null) {
//					classified.setSgst(new Double((Float) objs[68]));
//				}
//				if (objs[69] != null) {
//					Double cVal = (Double.valueOf(df.format(objs[69])));
//					classified.setCgstValue(new BigDecimal(df1.format(cVal)));
//				}
//				if (objs[70] != null) {
//					Double sVal = (Double.valueOf(df.format(objs[70])));
//					classified.setSgstValue(new BigDecimal(df1.format(sVal)));
//				}
//				if (objs[71] != null) {
//					Double sVal = (Double.valueOf(df.format(objs[71])));
//					classified.setIgstValue(new BigDecimal(df1.format(sVal)));
//				}
//				Double tVal = (Double.valueOf(df.format(objs[72])));
//				classified.setTotalValue(new BigDecimal(df1.format(tVal)));
//				classified.setBankOrBranch((String) objs[73]);
//				classified.setCashReceiptNo((String) objs[74]);
//				if (objs[75] != null) {
//					classified.setSpaceWidth(new Double((Float) objs[75]));
//				}
//				if (objs[76] != null) {
//					classified.setSpaceHeight(new Double((Float) objs[76]));
//				}
//
//				classified.setKeyword("Rms Order");
//				classified.setTypeOfCustomer("01");
//				classified.setCreatedTime(CommonUtils.dateFormatter((Date)objs[7],"HHmmss"));
//				classified.setOrderIdentification("01");
//
//				itemIds.add((String) objs[0]);
//				customerIds.add((String) objs[16]);
//				createdByIds.add((Integer) objs[6]);
//				classifiedsMap.put((String) objs[0], classified);
//			}
//
//			if (itemIds != null && !itemIds.isEmpty()) {
//				List<Object[]> editionsList = clfEditionsRepo.getRmsEditionIdAndNameOnItemId(itemIds);
//				for (Object[] clObj : editionsList) {
//					if (classifiedsMap.containsKey((String) clObj[0])) {
//						if (classifiedsMap.get((String) clObj[0]).getEditions() != null) {
//							classifiedsMap.get((String) clObj[0]).getEditions().add((String) clObj[2]);
//							classifiedsMap.get((String) clObj[0]).getEditionsErpRefId().add((String) clObj[3]);
//						} else {
//							List<String> edditions = new ArrayList<>();
//							List<String> edditionsErpRefIds = new ArrayList<>();
//							edditions.add((String) clObj[2]);
//							edditionsErpRefIds.add((String) clObj[3]);
//							ErpClassifieds classified = classifiedsMap.get((String) clObj[0]);
//							classified.setEditions(edditions);
//							classified.setEditionsErpRefId(edditionsErpRefIds);
//							classifiedsMap.put((String) clObj[0], classified);
//						}
//					}
//				}
//
//				List<Object[]> publishDatesList = clfPublishDatesRepo.getPublishDatesForErpData(itemIds);
//				for (Object[] clObj : publishDatesList) {
//					if (classifiedsMap.containsKey((String) clObj[0])) {
//						if (classifiedsMap.get((String) clObj[0]).getPublishDates() != null) {
//							classifiedsMap.get((String) clObj[0]).getPublishDates()
//									.add(CommonUtils.dateFormatter((Date) clObj[1], "yyyyMMdd"));
//						} else {
//							List<String> publishDates = new ArrayList<>();
//							publishDates.add(CommonUtils.dateFormatter((Date) clObj[1], "yyyyMMdd"));
//							ErpClassifieds classified = classifiedsMap.get((String) clObj[0]);
//							classified.setPublishDates(publishDates);
//							classifiedsMap.put((String) clObj[0], classified);
//						}
//					}
//				}
//
//				if (customerIds != null) {
//					List<UmCustomers> umCustomersList = umCustomersRepo.getMulCustomerDetails(customerIds);
//					List<Integer> cityIds = new ArrayList<Integer>();
//					if (!umCustomersList.isEmpty()) {
//						classifiedsMap.entrySet().forEach(erpData -> {
//							Optional<UmCustomers> umCus = umCustomersList.stream()
//									.filter(f -> f.getCustomerId().equals(erpData.getValue().getCustomerId()))
//									.findFirst();
//							if (umCus.isPresent()) {
//								UmCustomers umCustom = umCus.get();
//								erpData.getValue().setCustomerName(umCustom.getCustomerName());
//								erpData.getValue().setMobileNumber(umCustom.getMobileNo());
//								erpData.getValue().setEmailId(umCustom.getEmailId());
//								erpData.getValue().setAddress1(umCustom.getAddress1());
//								erpData.getValue().setAddress2(umCustom.getAddress2());
//								erpData.getValue().setAddress3(umCustom.getAddress3());
//								erpData.getValue().setPinCode(umCustom.getPinCode());
//								erpData.getValue().setOfficeLocation(umCustom.getOfficeLocation());
//								erpData.getValue().setGstNo(umCustom.getGstNo());
//								erpData.getValue().setPanNumber(umCustom.getPanNumber());
//								erpData.getValue().setAadharNumber(umCustom.getAadharNumber());
//								erpData.getValue().setErpRefId(umCustom.getErpRefId());
//								erpData.getValue().setState(umCustom.getState());
//								erpData.getValue().setCity(umCustom.getCity());
//								erpData.getValue().setHouseNo(umCustom.getHouseNo());
//								erpData.getValue().setClientCode(umCustom.getClientCode());
//								if (umCustom != null && !umCustom.getCity().isEmpty()) {
//									cityIds.add(Integer.parseInt(umCustom.getCity()));
//								}
//							}
//
//						});
//					}
//				}
//
//				if (!createdByIds.isEmpty()) {
//					List<Integer> userTypes = new ArrayList<Integer>();
//					List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByIds, false);
//					if (!umUsers.isEmpty()) {
//						classifiedsMap.entrySet().forEach(erpData -> {
//							Optional<UmUsers> gd = umUsers.stream()
//									.filter(f -> (f.getUserId()).equals(erpData.getValue().getCreatedBy())).findFirst();
//							if (gd.isPresent()) {
//								UmUsers umUser = gd.get();
//								erpData.getValue().setEmpCode(umUser.getEmpCode());
//								erpData.getValue().setCustomerName(umUser.getFirstName());
//								if (!"2".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
//									erpData.getValue().setSoldToParty(umUser.getSoldToParty());
//								}
//								if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
//									erpData.getValue().setCustomerName(umUser.getFirstName());
//									erpData.getValue().setMobileNumber(umUser.getMobile());
//									erpData.getValue().setEmailId(umUser.getEmail());
//									erpData.getValue().setAddress1(umUser.getAddress());
//									erpData.getValue().setState(umUser.getState());
//									erpData.getValue().setSoldToParty(umUser.getLogonId());
//								}
//								userTypes.add(umUser.getGdUserTypes().getUserTypeId());
//							}
//						});
//					}
//
//					if (!userTypes.isEmpty()) {
//						List<GdUserTypes> gdUserTypes = gdUserTypesRepo.getUserTypesList(userTypes);
//						classifiedsMap.entrySet().forEach(erpData -> {
//							Optional<GdUserTypes> gd = gdUserTypes.stream()
//									.filter(f -> (f.getUserTypeId()).equals(erpData.getValue().getUserTypeId()))
//									.findFirst();
//							if (gd.isPresent()) {
//								GdUserTypes gdUserType = gd.get();
//								erpData.getValue().setUserTypeIdErpRefId(gdUserType.getErpRefId());
//							}
//						});
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return classifiedsMap;
//	}
	public String encodeImageToBase64(String filePath) throws IOException, java.io.IOException {
	    File file = new File(filePath);
	    if (!file.exists()) {
	        throw new FileNotFoundException("File not found: " + filePath);
	    }
	    byte[] fileContent = Files.readAllBytes(file.toPath());
	    return Base64.getEncoder().encodeToString(fileContent);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, ErpClassifieds> getRmsOrderDetailsForErp(List<String> orderIds) {
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		Map<String, ErpClassifieds> classifiedsMap = new HashMap<>();
		List<String> itemIds = new ArrayList<String>();
		List<String> customerIds = new ArrayList<String>();
		List<Integer> createdByIds = new ArrayList<Integer>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		try {
			String joinedOrderIds = String.join("','", orderIds);
			
			String query="select itm.item_id, itm.order_id, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme, itm.classified_ads_sub_type, gcast.ads_sub_type, itm.status, itm.ad_id, itm.category_group, itm.sub_group, itm.child_group, gcg.classified_group, gcsg.classified_sub_group, gcc.classified_child_group, roi.size_width, roi.size_height, roi.page_position, grpd.positioning_type, roi.format_type, grft.format_type as grft_format_type, roi.page_number, grpp.page_name, roi.category_discount, roi.multi_discount, roi.additional_discount, roi.multi_discount_amount, roi.additional_discount_amount, roi.category_discount_amount, roi.base_amount, roi.grand_total, roi.discount_total, roi.gst_total, roi.premium_discount, roi.premium_discount_amount, roi.ad_type, grat.add_type, roi.caption, roi.igst_amount, roi.sgst_amount, roi.cgst_amount, co.booking_unit, bu.booking_location, co.edition_type, gret.edition_type as gret_edition_type, uc.client_code, uc.gst_no, uc.customer_name, uc.email_id, uc.mobile_no, uc.address_1, uc.pin_code, uc.state, uc.city, uc.customer_type_id, uc.mobile_alt, uc.house_no, bu.booking_description, gs.state as gs_state, gpm.payment_method, gpm2.payment_mode, rpr.bank_or_upi, rpr.cash_receipt_no,itm.created_by,co.customer_id,bu.booking_location as city_name,itm.created_by as creater_name,itm.created_ts,gcs.erp_ref_id as gcs_erp_ref_id,gcast.erp_ref_id as gcast_erp_ref_id,gcc.erp_ref_id as gcc_erp_ref_id,grpd.erp_ref_id as grpd_erp_ref_id,grpp.erp_ref_code as grpp_erp_ref_code,roi.fixed_format,grff.erp_ref_code as grff_erp_ref_code,co.user_type_id,bu.sales_office,bu.sold_to_party,uc.aadhar_number,uc.pan_number,itm.classified_ads_type,gcat.ads_type as gcat_ads_type,gcat.erp_ref_id as gcat_erp_ref_id,roi.multi_discount_edi_count,grbu.office_address,grbu.gst_in,grbu.phone_no,roi.no_of_insertions,roi.master_premium_per,roi.premium_amount,roi.billable_days,roi.aggred_premium_dis_per,grbu.scheduling_mail,roi.card_rate from clf_order_items itm inner join clf_orders co on itm.order_id = co.order_id inner join rms_order_items roi on itm.item_id = roi.item_id inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_positioning_discount grpd on roi.page_position = grpd.id left join gd_rms_format_types grft on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join booking_units bu on co.booking_unit = bu.booking_code inner join gd_rms_edition_type gret on co.edition_type = gret.id left join um_customers uc on co.customer_id = uc.customer_id left join gd_state gs ON uc.state = gs.state_code inner join rms_payments_response rpr on rpr.item_id = itm.item_id left join gd_payment_method gpm on gpm.id = rpr.payment_method left join gd_payment_mode gpm2 on gpm2.id = rpr.payment_mode inner join gd_rms_fixed_formats grff on roi.fixed_format = grff.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id left join gd_rms_booking_units grbu on grbu.booking_unit = co.booking_unit where co.order_type = 1 and co.order_status = 'CLOSED' and itm.mark_as_delete = false and co.mark_as_delete = false and itm.order_id in ('"+joinedOrderIds+ "')";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			Double additionalDiscount=0.0;
			Double additionalDiscountAmount=0.0;
			Double multiDiscount=0.0;
			Double multiDiscountAmount=0.0;
			Double categoryDiscount=0.0;
			Double categoryDiscountAmount=0.0;
			Double premiumDiscount=0.0;
			Double igstAmount=0.0;
			Double sgstAmount=0.0;
			Double cgstAmount=0.0;
			Double premiumDiscountAmount=0.0;
			Double premiumAmount =0.0;
			Double masterPremiumPer = 0.0;
//			for (Object[] obj : classifiedList) {
			if(!classifiedList.isEmpty()) {
				 Object[] obj = classifiedList.get(0);
				ErpClassifieds classified = new ErpClassifieds();
				classified.setDiscounts(new ArrayList<RmsDiscountModel>());
				classified.setTax(new ArrayList<RmsTaxModel>());
				classified.setPremium(new ArrayList<RmsPremiumModel>());
//				classified.setEditions(new ArrayList<String>());
//				classified.setEditionIds(new ArrayList<Integer>());
//				classified.setPublishDates(new ArrayList<String>());
//				insertionObjectDisplay.setDiscounts(new ArrayList<Integer>());
//				classified.setDiscountsDesc(new ArrayList<String>());
				classified.setItemId((String) obj[0]);
				classified.setOrderId((String) obj[1]);
				classified.setScheme((Integer) obj[2]);
				classified.setSchemeStr((String) obj[3]);
				classified.setClassifiedAdsSubType((Integer) obj[4]);
				classified.setAdsSubType((String) obj[5]);
				classified.setContentStatus((String) obj[6]);
				classified.setAdId((String) obj[7]);
				classified.setGroup((Integer) obj[8]);
				classified.setSubGroup((Integer) obj[9]);
				classified.setChildGroup((Integer) obj[10]);
				classified.setGroupStr((String) obj[11]);
				classified.setSubGroupStr((String) obj[12]);
				classified.setChildGroupStr((String) obj[13]);
				
				
				float sizeWidth = (obj[14] == null) ? 0.0f : (float) obj[14];
				float sizeHeight = (obj[15] == null) ? 0.0f : (float) obj[15];
				double formattedSizeWidth = HelperUtil.parseDoubleValue((sizeWidth));
				classified.setSizeWidth(formattedSizeWidth);
				String sizeW = String.valueOf(formattedSizeWidth);
				double formattedSizeHeight = HelperUtil.parseDoubleValue((sizeHeight));
				classified.setSizeHeight(formattedSizeHeight);
				String sizeH = String.valueOf(formattedSizeHeight);
				classified.setSizeWidthD(sizeW);
				classified.setSizeHeightD(sizeH);
				
				classified.setPagePosition((Integer) obj[16]);
				classified.setPositioningDesc((String) obj[17]);
				classified.setFormatType((Integer) obj[18]);
				classified.setFormatTypeDesc((String) obj[19]);						
				classified.setPageNumber((Integer) obj[20]);
				classified.setPageNumberDesc((String) obj[21]);
				
				categoryDiscount = ((Float) obj[22]) != null?((Float) obj[22]).doubleValue():null;
				multiDiscount = ((Float) obj[23]) != null?((Float) obj[23]).doubleValue():null;
				additionalDiscount = ((Float) obj[24]) != null?((Float) obj[24]).doubleValue():null;
				
				multiDiscountAmount = ((Float) obj[25]) != null?((Float) obj[25]).doubleValue():null;
				additionalDiscountAmount = ((Float) obj[26]) != null?((Float) obj[26]).doubleValue():null;
				categoryDiscountAmount = ((Float) obj[27]) != null?((Float) obj[27]).doubleValue():null;
				
				
				classified.setCategoryDiscount(categoryDiscount != null ? (String.valueOf(categoryDiscount)) : null);
				classified.setMultiDiscount(multiDiscount != null ? (String.valueOf(multiDiscount)) : null);
				classified.setAdditionalDiscount(additionalDiscount != null ? (String.valueOf(additionalDiscount)) : null);
				classified.setMultiDiscountAmount(multiDiscountAmount != null ? (String.valueOf(multiDiscountAmount)) : null);
				classified.setAdditionalDiscountAmount(additionalDiscountAmount != null ? (String.valueOf(additionalDiscountAmount)) : null);
				classified.setCategoryDiscountAmount(categoryDiscountAmount != null ? (String.valueOf(categoryDiscountAmount)) : null);
				
				classified.setAmount(((Float) obj[28]) != null ? ((Float) obj[28]).doubleValue() : 0.0);
				classified.setGrandTotal(((Float) obj[29]) != null ? ((Float) obj[29]).doubleValue() : 0.0);
				classified.setDiscountTotal(((Float) obj[30]) != null ? ((Float) obj[30]).doubleValue() : 0.0);
				classified.setGstTotal(((Float) obj[31]) != null ? ((Float) obj[31]).doubleValue() : 0.0);
				
				classified.setAmountString(String.format("%.2f", classified.getAmount()));
				classified.setGrandTotalString(String.format("%.2f", classified.getGrandTotal()));
				classified.setDiscountTotalString(String.format("%.2f", classified.getDiscountTotal()));
				classified.setGstTotalString(String.format("%.2f", classified.getGstTotal()));
										
				premiumDiscount = ((Float) obj[32]) != null?((Float) obj[32]).doubleValue():null;
				premiumDiscountAmount = ((Float) obj[33]) != null?((Float) obj[33]).doubleValue():null;
				
				classified.setPremiumDiscount(premiumDiscount != null ? (String.valueOf(premiumDiscount)) : null);
				classified.setPremiumDiscountAmount(premiumDiscountAmount != null ? (String.valueOf(premiumDiscountAmount)) : null);
				
				classified.setAddType((Integer) obj[34]);
				classified.setAddTypeDesc((String) obj[35]);
				classified.setCaption((String) obj[36]);
				
				igstAmount = ((Float) obj[37]) != null?((Float) obj[37]).doubleValue():null;
				sgstAmount = ((Float) obj[38]) != null?((Float) obj[38]).doubleValue():null;
				cgstAmount = ((Float) obj[39]) != null?((Float) obj[39]).doubleValue():null;
				
				if (igstAmount != null) {
					igstAmount = Double.valueOf(df1.format(igstAmount));
					BigDecimal igstAmt = BigDecimal.valueOf(igstAmount);
					igstAmt = igstAmt.setScale(2, RoundingMode.HALF_UP);
					igstAmount = igstAmt.doubleValue();
				}
				
				if (sgstAmount != null) {
					sgstAmount = Double.valueOf(df1.format(sgstAmount));
					BigDecimal sgstAmt = BigDecimal.valueOf(sgstAmount);
					sgstAmt = sgstAmt.setScale(2, RoundingMode.HALF_UP);
					sgstAmount = sgstAmount.doubleValue();
				}
				if (cgstAmount != null) {
					cgstAmount = Double.valueOf(df1.format(cgstAmount));
					BigDecimal cgstAmt = BigDecimal.valueOf(cgstAmount);
					cgstAmt = cgstAmt.setScale(2, RoundingMode.HALF_UP);
					cgstAmount = cgstAmount.doubleValue();
				}
				classified.setBookingCode((Integer) obj[40]);
				classified.setCityDesc((String) obj[41]);
				classified.setEditionType((Integer) obj[42]);
				classified.setEditionTypeDesc((String) obj[43]);
				classified.setClientCode((String) obj[44]);
				if(classified.getClientCode() != null && classified.getClientCode().startsWith("AP-01") || classified.getClientCode().startsWith("TG-36")) {
					classified.setIsCustomerNew("New");
				}else {
					classified.setIsCustomerNew("");
				}
				classified.setGstNo((String) obj[45]);
				classified.setClientName((String) obj[46]);
				classified.setEmailId((String) obj[47]);
				classified.setMobileNo((String) obj[48]);
				classified.setAddress1((String) obj[49]);
				classified.setPinCode((String) obj[50]);
				if(!((String) obj[48]).equalsIgnoreCase((String) obj[54])) {
					classified.setMobileAlt((String) obj[54]);
				}
				classified.setStateCode((String) obj[51]);
				classified.setHouseNo((String) obj[55]);
				classified.setBookingDescription((String) obj[56]);
				classified.setStateDesc((String) obj[57]);
				classified.setPaymentMethodDesc((String) obj[58]);
				classified.setPaymentModeDesc((String) obj[59]);
				classified.setBankOrUpi((String) obj[60]);
				classified.setCashReceiptNo((String) obj[61]);;
				classified.setCustomerId((String) obj[63]);
				classified.setCreatedBy((Integer) obj[65]);
				Timestamp timestamp = (Timestamp) obj[66]; // Assuming obj[66] contains the Timestamp
				if (timestamp != null) {
					classified.setCreatedTs(CommonUtils.dateFormatter((Date) obj[66], "yyyy-MM-dd HH:mm"));
					classified.setCreatedDate(CommonUtils.dateFormatter((Date) obj[66], "yyyyMMdd"));
					classified.setBookingDate(CommonUtils.dateFormatter((Date) obj[66], "yyyy-MM-dd HH:mm:ss"));
//				    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//				    String formattedDate = dateFormat.format(timestamp);
//				    classified.setCreatedTs(formattedDate);
				} else {
				    classified.setCreatedTs(null); // Handle null case if required
				}
				classified.setOfficeAddress((String) obj[83]);
				classified.setGstIn((String) obj[84]);
				classified.setPhoneNumber((String) obj[85]);
				classified.setNoOfInsertions((Integer) obj[86]);;
				classified.setSchemeErpRefId((String) obj[67]);
				classified.setAdsSubTypeErpRefId((String) obj[68]);
				classified.setChildGroupErpRefId((String) obj[69]);
				classified.setPagePositionErpRefId((String) obj[70]);
				classified.setPageNumberErpRefId((String) obj[71]);
				classified.setFixedFormatErpRefId((String) obj[73]);
				classified.setUserTypeId((Integer) obj[74]);
				classified.setSalesOffice((String) obj[75]);
				classified.setSoldToParty((String) obj[76]);
				classified.setAadharNumber((String) obj[77]);
				classified.setPanNumber((String) obj[78]);
				classified.setAdsType((String) obj[80]);
				classified.setAdsTypeErpRefId((String) obj[81]);
				classified.setMultiDiscountEdiCount((Integer) obj[82]);
				classified.setAdTypeErpRefId((String) obj[83]);
				classified.setPaidAmount((Float) obj[29] != null?((Float) obj[29]).doubleValue():null);
				classified.setKeyword("Rms Order");
				classified.setTypeOfCustomer("01");
				classified.setCreatedTime(CommonUtils.dateFormatter((Date) obj[66], "HHmmss"));
				classified.setOrderIdentification("01");
				
				masterPremiumPer = ((Float) obj[87]) != null?((Float) obj[87]).doubleValue():null;
				premiumAmount = ((Float) obj[88]) != null?((Float) obj[88]).doubleValue():null;
				if (premiumAmount != null) {
					premiumAmount = Double.valueOf(df1.format(premiumAmount));
					BigDecimal premiumDiscountAmt = BigDecimal.valueOf(premiumAmount);
					premiumDiscountAmt = premiumDiscountAmt.setScale(2, RoundingMode.HALF_UP);
					premiumAmount = premiumDiscountAmt.doubleValue();
				}
				
				classified.setPremiumTotal(premiumAmount);
				Integer billableDays = obj[89] != null ? (Integer) obj[89] :1;
				Double withSchemeTotalAmt = classified.getAmount() * billableDays;
				classified.setWithSchemeTotalAmount(withSchemeTotalAmt);
				classified.setAggredPremiumDisPer(((Float) obj[90]).doubleValue());
				classified.setSchedulingMail((String) obj[91]);
				classified.setCardRate(((Float) obj[92]).doubleValue());

				if(additionalDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Additional Discount");
					discountModel.setAmount(additionalDiscountAmount);
					discountModel.setAmountt(formatToIndianCurrency(additionalDiscountAmount));;
					discountModel.setAmountString(String.format("%.2f", additionalDiscountAmount));
					discountModel.setPercent(additionalDiscount);
					discountModel.setPercentString(String.format("%.2f", additionalDiscount));	
					discountModel.setCategoryType((String) obj[17]);
					classified.getDiscounts().add(discountModel);
				}
				if(multiDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Multi Discount");
					discountModel.setAmount(multiDiscountAmount);
					discountModel.setAmountt(formatToIndianCurrency(multiDiscountAmount));
					discountModel.setAmountString(String.format("%.2f", multiDiscountAmount));
					discountModel.setPercent(multiDiscount);
					discountModel.setPercentString(String.format("%.2f", multiDiscount));
					discountModel.setCategoryType((String) obj[17]);
					classified.getDiscounts().add(discountModel);
					
				}
				if(categoryDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Category Discount");
					discountModel.setAmount(categoryDiscountAmount);
					discountModel.setAmountt(formatToIndianCurrency(categoryDiscountAmount));
					discountModel.setAmountString(String.format("%.2f", categoryDiscountAmount));
					discountModel.setPercent(categoryDiscount);
					discountModel.setPercentString(String.format("%.2f", categoryDiscount));
					discountModel.setCategoryType((String) obj[17]);
					classified.getDiscounts().add(discountModel);
				}
//				if(premiumDiscount != null) {
//					RmsDiscountModel discountModel=new RmsDiscountModel();
//					discountModel.setType("Premium Discount");
//					discountModel.setAmount(premiumDiscountAmount);
////					discountModel.setAmountt(formatToIndianCurrency(premiumDiscountAmount));
//					discountModel.setAmountString(String.format("%.2f", premiumDiscountAmount));
//					discountModel.setPercent(premiumDiscount);
//					discountModel.setPercentString(String.format("%.2f", premiumDiscount));	
//					discountModel.setCategoryType((String) obj[17]);
//					classified.getDiscounts().add(discountModel);
//				}
				if(sgstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("SGST");
					rmsTaxModel.setAmount(sgstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", sgstAmount));
					rmsTaxModel.setPercent(2.5);
					rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
					classified.getTax().add(rmsTaxModel);
				}

				if(igstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("IGST");
					rmsTaxModel.setAmount(igstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
					rmsTaxModel.setPercent(5.0);
					rmsTaxModel.setPercentString(String.format("%.2f", 5.0));
					classified.getTax().add(rmsTaxModel);
				}

				if(cgstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("CGST");
					rmsTaxModel.setAmount(igstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
					rmsTaxModel.setPercent(2.5);
					rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
					classified.getTax().add(rmsTaxModel);
				}
				
				if(premiumDiscount != null) {
					RmsPremiumModel rmsPremiumModel = new RmsPremiumModel();
					rmsPremiumModel.setType("Agreed Premium");
					rmsPremiumModel.setAmount(premiumAmount);
					rmsPremiumModel.setPercent(premiumDiscount);
					classified.getPremium().add(rmsPremiumModel);
				}else if(masterPremiumPer != null){
					RmsPremiumModel rmsPremiumModel = new RmsPremiumModel();
					rmsPremiumModel.setType("Premium");
					rmsPremiumModel.setAmount(premiumAmount);
					rmsPremiumModel.setPercent(masterPremiumPer);
					classified.getPremium().add(rmsPremiumModel);
				}
				
				if(classified.getDiscountTotal() != null) {
					classified.setAfterDiscountTotal(classified.getAmount() - classified.getDiscountTotal());
				}
				if(classified.getPremiumTotal() != null && classified.getAfterDiscountTotal() != null) {
					classified.setAfterPremiumTotal(classified.getAfterDiscountTotal() + classified.getPremiumTotal());
				}else {
					if(classified.getPremiumTotal() != null) {
						classified.setAfterPremiumTotal(classified.getAmount() + classified.getPremiumTotal());
					}
				}
//
				itemIds.add((String) obj[0]);
				createdByIds.add((Integer) obj[62]);
				customerIds.add((String) obj[63]);
				classifiedsMap.put((String) obj[0], classified);
//			}

			if (itemIds != null && !itemIds.isEmpty()) {
				List<Object[]> editionsList = clfEditionsRepo.getRmsEditionIdAndNameOnItemId(itemIds);
				for (Object[] clObj : editionsList) {
					if (classifiedsMap.containsKey((String) clObj[0])) {
						if (classifiedsMap.get((String) clObj[0]).getEditions() != null) {
							classifiedsMap.get((String) clObj[0]).getEditions().add((String) clObj[2]);
							classifiedsMap.get((String) clObj[0]).getEditionsErpRefId().add((String) clObj[3]);
						} else {
							List<String> edditions = new ArrayList<>();
							List<String> edditionsErpRefIds = new ArrayList<>();
							edditions.add((String) clObj[2]);
							edditionsErpRefIds.add((String) clObj[3]);
							ErpClassifieds classifieds = classifiedsMap.get((String) clObj[0]);
							classifieds.setEditions(edditions);
							classifieds.setEditionsErpRefId(edditionsErpRefIds);
							classifiedsMap.put((String) clObj[0], classifieds);
						}
					}
				}

				List<Object[]> publishDatesList = clfPublishDatesRepo.getPublishDatesForErpData(itemIds);
				for (Object[] clObj : publishDatesList) {
					if (classifiedsMap.containsKey((String) clObj[0])) {
						if (classifiedsMap.get((String) clObj[0]).getPublishDates() != null) {
							classifiedsMap.get((String) clObj[0]).getPublishDates()
									.add(CommonUtils.dateFormatter((Date) clObj[1], "yyyyMMdd"));
						} else {
							List<String> publishDates = new ArrayList<>();
							publishDates.add(CommonUtils.dateFormatter((Date) clObj[1], "yyyyMMdd"));
							ErpClassifieds classifieds = classifiedsMap.get((String) clObj[0]);
							classifieds.setPublishDates(publishDates);
							classifiedsMap.put((String) clObj[0], classifieds);
						}
					}
				}
				
				List<ApprovalInbox> approvalInboxList = approvalInboxRepo.getPendingInboxForCurrentLevel(itemIds);
				if (approvalInboxList != null && !approvalInboxList.isEmpty()) {
					for (ApprovalInbox approvalInbox : approvalInboxList) {
						ErpClassifieds erpClassifieds = classifiedsMap.get(approvalInbox.getItemId());
						erpClassifieds.setCurrentLevel(approvalInbox.getCurrentLevel());
					}
				}
				WfInboxMaster wfInboxMasterDetailsOnObjectRefId = wfInboxMasterRepo.getWfInboxMasterDetailsOnObjectRefId(itemIds.get(0));
				if(wfInboxMasterDetailsOnObjectRefId != null) {
					ErpClassifieds erpClassifieds = classifiedsMap.get(wfInboxMasterDetailsOnObjectRefId.getObjectRefId());
					List<WfInboxMasterDetails> inboxMasterDetails = wfInboxMasterDetailsRepo.getInboxMasterDetails(wfInboxMasterDetailsOnObjectRefId.getInboxMasterId());
					List<RmsApprovalDetails> approvalDetails = new ArrayList<RmsApprovalDetails>();
					if(inboxMasterDetails != null && !inboxMasterDetails.isEmpty()) {
						for(WfInboxMasterDetails details : inboxMasterDetails) {
							if(details.getStatus().equalsIgnoreCase("PENDING")) {
								erpClassifieds.setCurrentLevel(Integer.parseInt(details.getTargetRef()));
							}
							if(details.getStatus().equalsIgnoreCase("APPROVED") ) {
								RmsApprovalDetails approveModel = new RmsApprovalDetails();
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								if(details.getChangedBy() == null) {
									UmUsers approverEmails = umUsersRepository.getApproverEmails(userContext.getLoggedUser().getUserId());
									approveModel.setApprovedBy(approverEmails.getFirstName() != null ? approverEmails.getFirstName() : "");
									approveModel.setApprovedTime(new Date());
									approveModel.setLevel(Integer.parseInt(details.getTargetRef()));
								}else {
									UmUsers approverEmails = umUsersRepository.getApproverEmails(details.getChangedBy());
									approveModel.setApprovedBy(approverEmails.getFirstName() != null ? approverEmails.getFirstName() : "");
									approveModel.setApprovedTime(details.getChangedTs());
									approveModel.setLevel(Integer.parseInt(details.getTargetRef()));
								}		
								approvalDetails.add(approveModel);
							}
							
						}
						erpClassifieds.setApprovalDetails(approvalDetails);
					}
				}
				if (customerIds != null) {
					List<UmCustomers> umCustomersList = umCustomersRepo.getMulCustomerDetails(customerIds);
					List<Integer> cityIds = new ArrayList<Integer>();
					if (!umCustomersList.isEmpty()) {
						classifiedsMap.entrySet().forEach(erpData -> {
							Optional<UmCustomers> umCus = umCustomersList.stream()
									.filter(f -> f.getCustomerId().equals(erpData.getValue().getCustomerId()))
									.findFirst();
							if (umCus.isPresent()) {
								UmCustomers umCustom = umCus.get();
								erpData.getValue().setCustomerName(umCustom.getCustomerName());
								erpData.getValue().setMobileNumber(umCustom.getMobileNo());
								erpData.getValue().setEmailId(umCustom.getEmailId());
								erpData.getValue().setAddress1(umCustom.getAddress1());
								erpData.getValue().setAddress2(umCustom.getAddress2());
								erpData.getValue().setAddress3(umCustom.getAddress3());
								erpData.getValue().setPinCode(umCustom.getPinCode());
								erpData.getValue().setOfficeLocation(umCustom.getOfficeLocation());
								erpData.getValue().setGstNo(umCustom.getGstNo());
								erpData.getValue().setPanNumber(umCustom.getPanNumber());
								erpData.getValue().setAadharNumber(umCustom.getAadharNumber());
								erpData.getValue().setErpRefId(umCustom.getErpRefId());
								erpData.getValue().setState(umCustom.getState());
								erpData.getValue().setCity(umCustom.getCity());
								erpData.getValue().setHouseNo(umCustom.getHouseNo());
								erpData.getValue().setClientCode(umCustom.getClientCode());
								if(!umCustom.getMobileNo().equalsIgnoreCase(umCustom.getMobileAlt())) {
									erpData.getValue().setMobileAlt(umCustom.getMobileAlt());
								}
								if (umCustom != null && !umCustom.getCity().isEmpty()) {
									if(umCustom.getCity() != null && umCustom.getCity().matches("\\d+"))
										cityIds.add(Integer.parseInt(umCustom.getCity()));
									}
							}

						});
					}
				}

				if (!createdByIds.isEmpty()) {
					List<Integer> userTypes = new ArrayList<Integer>();
					List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByIds, false);
					if (!umUsers.isEmpty()) {
						classifiedsMap.entrySet().forEach(erpData -> {
							Optional<UmUsers> gd = umUsers.stream()
									.filter(f -> (f.getUserId()).equals(erpData.getValue().getCreatedBy())).findFirst();
							if (gd.isPresent()) {
								UmUsers umUser = gd.get();
								erpData.getValue().setEmpCode(umUser.getEmpCode());
								erpData.getValue().setCustomerName(umUser.getFirstName());
								erpData.getValue().setCreatedByEmail(umUser.getEmail());
								if (!"2".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
									erpData.getValue().setSoldToParty(umUser.getSoldToParty());
								}
								if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
									erpData.getValue().setCustomerName(umUser.getFirstName());
									erpData.getValue().setMobileNumber(umUser.getMobile());
									erpData.getValue().setEmailId(umUser.getEmail());
									erpData.getValue().setAddress1(umUser.getAddress());
									erpData.getValue().setState(umUser.getState());
									erpData.getValue().setSoldToParty(umUser.getLogonId());
								}
								if("1".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")){
									erpData.getValue().setCreatedByEmail(umUser.getEmail());
								}
								userTypes.add(umUser.getGdUserTypes().getUserTypeId());
							}
						});
					}

					if (!userTypes.isEmpty()) {
						List<GdUserTypes> gdUserTypes = gdUserTypesRepo.getUserTypesList(userTypes);
						classifiedsMap.entrySet().forEach(erpData -> {
							Optional<GdUserTypes> gd = gdUserTypes.stream()
									.filter(f -> (f.getUserTypeId()).equals(erpData.getValue().getUserTypeId()))
									.findFirst();
							if (gd.isPresent()) {
								GdUserTypes gdUserType = gd.get();
								erpData.getValue().setUserTypeIdErpRefId(gdUserType.getErpRefId());
							}
						});
					}
				}
			}
		} }catch (Exception e) {
			e.printStackTrace();
		}
		return classifiedsMap;
	}
	
	@SuppressWarnings("unused")
	public void sendRmsMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap,
			BillDeskPaymentResponseModel payload, LoggedUser loggedUser,
			ClfPaymentResponseTracking clfPaymentResponseTracking) {
		try {

			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
//			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
			emailTo.setFrom(emailConfigs.get("RMS_EMAIL_FROM"));
//			String [] ccMails = {loggedUser.getEmail()};
//			emailTo.setBcc(ccMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
			mapProperties.put("userId", loggedUser.getLogonId());// new userName
			erpClassifiedsMap.entrySet().forEach(erpData -> {
//				emailTo.setTo(erpData.getValue().getEmailId());
				if(payload != null) {
						emailTo.setTo(erpData.getValue().getSchedulingMail());
						String[] ccMails = {erpData.getValue().getEmailId(),erpData.getValue().getCreatedByEmail()};
						emailTo.setBcc(ccMails);
				}else {
					String [] ccMails = {userContext.getLoggedUser().getEmail()};
					emailTo.setBcc(ccMails);
					emailTo.setTo(erpData.getValue().getEmailId());
				}			
				
				mapProperties.put("orderId", erpData.getValue().getAdId());
				mapProperties.put("clientCode", erpData.getValue().getClientCode());
				mapProperties.put("city", erpData.getValue().getCityDesc());
				mapProperties.put("state", erpData.getValue().getStateDesc());
				mapProperties.put("clientName", erpData.getValue().getClientName());
				mapProperties.put("amount", erpData.getValue().getGrandTotal());
				mapProperties.put("nameOfDiscount", erpData.getValue().getPageNumberDesc());
//				mapProperties.put("discountValue", erpData.getValue().getDiscountValue());
//				mapProperties.put("iGst", erpData.getValue().getIgst());
				mapProperties.put("caption", erpData.getValue().getCaption());
				mapProperties.put("approvalStatus", "PENDING");
//				mapProperties.put("cGst", erpData.getValue().getCgst());
//				mapProperties.put("sGst", erpData.getValue().getSgst());
				mapProperties.put("phone", erpData.getValue().getMobileNo());
				mapProperties.put("Code", erpData.getValue().getAdId());
				mapProperties.put("categoryType", erpData.getValue().getAdsSubType());
				mapProperties.put("address",erpData.getValue().getOfficeAddress());
				mapProperties.put("address1",erpData.getValue().getAddress1());
				mapProperties.put("gstIn", erpData.getValue().getGstIn() != null ? erpData.getValue().getGstIn() :"");
				mapProperties.put("mobileNo", erpData.getValue().getPhoneNumber());
//				String gst = null;
//				if (erpData.getValue().getIgst() != null) {
//					gst = "<tr><th colspan=4>GST- IGST (" + erpData.getValue().getIgst()
//							+ "%)</th><th style=\"text-align: right;\">" + erpData.getValue().getIgstValue()
//							+ "</th></tr>";
//					mapProperties.put("gst", gst);
//				} else {
//					gst = "</th></tr>" + "<tr><th colspan=4>GST- CGST(" + erpData.getValue().getCgst()
//							+ "%)</th><th style=\"text-align: right;\">" + erpData.getValue().getCgstValue()
//							+ "</th></tr>" + "<tr><th colspan=4>GST- SGST(" + erpData.getValue().getSgst()
//							+ "%)</th><th style=\"text-align: right;\">" + erpData.getValue().getSgstValue()
//							+ "</th></tr>";
//					mapProperties.put("gst", gst);
//				}
//				mapProperties.put("totalValue", erpData.getValue().getTotalValue());
				mapProperties.put("bankOrBranch", erpData.getValue().getBankOrUpi());
				mapProperties.put("cashReceiptNo", erpData.getValue().getCashReceiptNo());
				mapProperties.put("scheme", erpData.getValue().getSchemeStr());

				mapProperties.put("street", erpData.getValue().getHouseNo());
				mapProperties.put("gstNo", erpData.getValue().getGstNo() != null ? erpData.getValue().getGstNo() :"");
				mapProperties.put("pinCode", erpData.getValue().getPinCode());
				mapProperties.put("noOfInsertion", erpData.getValue().getNoOfInsertions());
				mapProperties.put("createdTs", erpData.getValue().getCreatedTs());
				mapProperties.put("sizeHeight", erpData.getValue().getSizeHeightD());
				mapProperties.put("sizeWidth", erpData.getValue().getSizeWidthD());
				mapProperties.put("Position", erpData.getValue().getPageNumberDesc());
				if(erpData.getValue().getCustomerName()  == null) {
					ClfOrderItems itemDetailsOnadId = clfOrderItemsRepo.getItemDetailsOnadId(erpData.getValue().getAdId());
					UmUsers approverEmails = umUsersRepository.getApproverEmails(itemDetailsOnadId.getCreatedBy());
					if(approverEmails!= null) {
						mapProperties.put("employeeHrCode", approverEmails.getEmpCode());
						mapProperties.put("employee", approverEmails.getFirstName());
					}					
				}else {
					mapProperties.put("employeeHrCode", erpData.getValue().getEmpCode() != null ?erpData.getValue().getEmpCode():"");
					mapProperties.put("employee", erpData.getValue().getCustomerName() != null ? erpData.getValue().getCustomerName():"");
				}				
				mapProperties.put("totalPremium", erpData.getValue().getPremiumTotal() != null ? erpData.getValue().getPremiumTotal() : "");
				mapProperties.put("afterTotalPremium", erpData.getValue().getAfterPremiumTotal() != null ? erpData.getValue().getAfterPremiumTotal() : "");
				mapProperties.put("totalAfterDiscount", erpData.getValue().getAfterDiscountTotal());
				mapProperties.put("withSchemeTotalAmount", erpData.getValue().getWithSchemeTotalAmount());
				if (erpData.getValue().getSizeWidth() != null && erpData.getValue().getSizeHeight() != null) {
					mapProperties.put("space",
							erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight());
				}
				mapProperties.put("cardRate", erpData.getValue().getCardRate());
				mapProperties.put("rate", erpData.getValue().getAmount());
				List<String> editionsList = erpData.getValue().getEditions();
				String editions = editionsList.stream().map(Object::toString).collect(Collectors.joining(","));
//				mapProperties.put("editionName", editions);
				List<String> pubDates = erpData.getValue().getPublishDates();
				List<String> pubDatesList = new ArrayList<String>();
				StringBuilder dynamicTableRows = new StringBuilder();
				for (String pubD : pubDates) {
					Date date;
					String formattedDate = null;
					try {
						date = new SimpleDateFormat("yyyyMMdd").parse(pubD);
						formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					pubDatesList.add(formattedDate);
					String cardRate = formatToIndianCurrency(erpData.getValue().getCardRate());
					String formatToIndianCurrency = formatToIndianCurrency(erpData.getValue().getAmount());
				    dynamicTableRows.append("<tr>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(formattedDate).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(editions).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeHeightD()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeWidthD()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(cardRate).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getPageNumberDesc()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(formatToIndianCurrency).append("</td>")
		            .append("</tr>");
		
				}
				mapProperties.put("dynamicTableRows", dynamicTableRows.toString());
				mapProperties.put("totalAmount",formatToIndianCurrency(erpData.getValue().getGrandTotal()));
				
				String publishDates = pubDatesList.stream().map(Object::toString).collect(Collectors.joining(","));
//				mapProperties.put("date", publishDates);
			

				// Put the formatted date into the map
				mapProperties.put("date", erpData.getValue().getCreatedTs());
				
				 StringBuilder discountRows = new StringBuilder();
				 List<Double> discountAmount = new ArrayList<Double>();
				 if(!erpData.getValue().getDiscounts().isEmpty()) {
					 for(RmsDiscountModel dM:erpData.getValue().getDiscounts()) {
						 String type = dM.getType();
						 if(type.equalsIgnoreCase("Additional Discount")) {
//							 Double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
							 Double discountedAmount = dM.getAmount();
							 discountAmount.add(discountedAmount);
							 discountRows.append("<tr>")
					            .append("<td>Additional Discount</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
//					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
					            .append("</tr>"); 
						 }
						 if(type.equalsIgnoreCase("Multi Discount")) {
//							 Double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
							 Double discountedAmount = dM.getAmount();
							 discountAmount.add(discountedAmount);
							 discountRows.append("<tr>")
					            .append("<td>Multi Discount</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
//					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
					            .append("</tr>");
						 }
						 if(type.equalsIgnoreCase("Category Discount")) {
//							 double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
							 Double discountedAmount = dM.getAmount();
							 discountAmount.add(discountedAmount);
							 discountRows.append("<tr>")
					            .append("<td>Category Discount - " + dM.getCategoryType() + "</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
//					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
					            .append("</tr>");
						 }
//						 if(type.equalsIgnoreCase("Premium Discount")) {
//							 double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
//							 discountAmount.add(discountedAmount);
//							 discountRows.append("<tr>")
//					            .append("<td>Premium Discount</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
//					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
//					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
////					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
//					            .append("</tr>");
//						 }
//						 if(type.equalsIgnoreCase("Premium Discount")) {
//							 double discountedAmount = dM.getAmount() - (dM.getAmount() * dM.getPercent() / 100);
//							 discountAmount.add(discountedAmount);
//							 discountRows.append("<tr>")
//					            .append("<td>Premium Discount</td>")
//					            .append("<td>").append(dM.getCategoryType()).append("</td>")
//					            .append("<td style='text-align:right'>").append(String.format("%.2f", dM.getPercent())).append("</td>")
//					            .append("<td style='text-align:right'>").append(dM.getAmountt()).append("</td>")
//					            .append("<td style='text-align:right'>").append(formatToIndianCurrency(discountedAmount)).append("</td>")
//					            .append("</tr>");
//						 }
						
					 }
					
				 }
				 mapProperties.put("discountTableRows", discountRows.length() > 0 ? discountRows.toString():"-");
				 Double disc = 0.0;
				 for(Double discount:discountAmount) {
					 disc = disc + discount;
				 }
				
				 mapProperties.put("totalDiscountValue", formatToIndianCurrency(disc));
				 
				 StringBuilder premiumRows = new StringBuilder();
				 if(!erpData.getValue().getPremium().isEmpty()) {
					 for(RmsPremiumModel model : erpData.getValue().getPremium()) {
						 String type = model.getType();
						 if(type.equalsIgnoreCase("Premium")) {
							 Double discountedAmount = model.getAmount();
							 discountAmount.add(discountedAmount);
							 premiumRows.append("<tr>")
					            .append("<td>Premium</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
						 if(type.equalsIgnoreCase("Agreed Premium")) {
							 Double discountedAmount = model.getAmount();
							 discountAmount.add(discountedAmount);
							 premiumRows.append("<tr>")
					            .append("<td>Agreed Premium</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
					 }
					 
				 }
				 mapProperties.put("premiumTableRows", premiumRows.length()>0 ?premiumRows.toString():"-");
				 StringBuilder gstRows = new StringBuilder();
				 Double totalGstValue = 0.0;
				 if(!erpData.getValue().getTax().isEmpty()) {
					 for(RmsTaxModel model : erpData.getValue().getTax()) {
						 String type = model.getType();
						 if(type.equalsIgnoreCase("IGST")) {
							 Double discountedAmount = model.getAmount();
							 totalGstValue = totalGstValue + model.getAmount();
							 discountAmount.add(discountedAmount);
							 gstRows.append("<tr>")
					            .append("<td>IGST</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
						 if(type.equalsIgnoreCase("SGST")) {
							 Double discountedAmount = model.getAmount();
							 totalGstValue = totalGstValue + model.getAmount();
							 discountAmount.add(discountedAmount);
							 gstRows.append("<tr>")
					            .append("<td>SGST</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
						 if(type.equalsIgnoreCase("CGST")) {
							 Double discountedAmount = model.getAmount();
							 totalGstValue = totalGstValue + (model.getAmount() != null ? model.getAmount():0.0);
							 discountAmount.add(discountedAmount);
							 gstRows.append("<tr>")
					            .append("<td>CGST</td>")
					            .append("<td style='text-align:right'>").append(String.format("%.2f", model.getPercent())).append("</td>")
					            .append("<td style='text-align:right'>").append(model.getAmount()).append("</td>")
					            .append("</tr>"); 
						 }
					 }
					 mapProperties.put("gstTableRows", gstRows.length() > 0 ? gstRows.toString():"-");
					 mapProperties.put("totalGstValue", totalGstValue);
				 }
//				 Map<String, Object> mapData1 = new HashMap<>();
//				 List<Attachments> attachmentsByOrderId = rmsAttachmentsRepo.getAllAttachmentsByOrderId(erpData.getValue().getOrderId());
//				
//				 for(Attachments attachments : attachmentsByOrderId) {
//					 if(attachments.getAttachName().startsWith("SIGN_")) {
//						 String cid = UUID.randomUUID().toString(); // Unique Content-ID
//					        mapData1.put(cid, new FileDataSource(getDIR_PATH_DOCS() + attachments.getAttachUrl()));
//					 }
//				 }
//				 List<Object> pdfFileNames1 = new ArrayList<Object>(mapData1.values());
//		            List<String> fileNames1 = new ArrayList<>();
//		            for(Object fileName : pdfFileNames1) {
//		            	  if (fileName instanceof String) {
//		                      fileNames1.add(getDIR_PATH_DOCS() + fileName.toString());
//		                  } else if (fileName instanceof javax.activation.FileDataSource) {
//		                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
//		                      fileNames1.add(getDIR_PATH_DOCS() + fileDataSource.getName());
//		                  } else {
//		                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
//		                  }
//		            }
//		            try {
//						String encodeImageToBase64 = this.encodeImageToBase64(fileNames1.get(0));
//						String base64DataUrl = "data:image/jpg;base64," + encodeImageToBase64;
//						mapProperties.put("signature", base64DataUrl); 
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		            List<String> cids = new ArrayList<>(mapData1.keySet());
//		            


				 Map<String, Object> mapData1 = new HashMap<>();
				 List<Attachments> attachmentsByOrderId = rmsAttachmentsRepo.getAllAttachmentsByOrderId(erpData.getValue().getOrderId());
				
				 for(Attachments attachments : attachmentsByOrderId) {
					 if(attachments.getAttachName().startsWith("SIGN_")) {
						 String cid = UUID.randomUUID().toString(); // Unique Content-ID
						 mapProperties.put("signature", prop.getProperty("TOMCAT_SERVER")+TOMCAT_PATH+attachments.getAttachUrl());
//						 mapProperties.put("signature", "http://97.74.94.194:8080/staticresources/docs/SIGN_0_1678051b-9acb-481c-abb5-d1c132c1d203SIGN_0.png");
					        mapData1.put(cid, new FileDataSource(getDIR_PATH_DOCS() + attachments.getAttachUrl()));
					 }
				 }
				 List<Object> pdfFileNames1 = new ArrayList<Object>(mapData1.values());
		            List<String> fileNames1 = new ArrayList<>();
		            for(Object fileName : pdfFileNames1) {
		            	  if (fileName instanceof String) {
		                      fileNames1.add(getDIR_PATH_DOCS() + fileName.toString());
		                  } else if (fileName instanceof javax.activation.FileDataSource) {
		                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
		                      fileNames1.add(getDIR_PATH_DOCS() + fileDataSource.getName());
		                  } else {
		                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
		                  }
		            }

		            mapProperties.put("subject_edit", true);
		            List<String> cids = new ArrayList<>(mapData1.keySet());
		            mapProperties.put("subject_edit", true);
				emailTo.setTemplateName(GeneralConstants.RMS_RO);
				emailTo.setTemplateProps(mapProperties);



				List<Map<String, Object>> multiAttachments = new ArrayList<Map<String, Object>>();
				Map<String, Object> mapData = new HashMap<>();
				
//				List<Attachments> allAttachmentsByOrderId = rmsAttachmentsRepo.getAllAttachmentsByOrderId(erpData.getValue().getOrderId());
//				if(allAttachmentsByOrderId!=null && !allAttachmentsByOrderId.isEmpty()) {
//					for(Attachments attach:allAttachmentsByOrderId) {
//	                    mapData.put(attach.getAttachName()+".png", new FileDataSource(getDIR_PATH_DOCS()+attach.getAttachUrl()));
//					}
//				}
				
				List<Object> pdfFileNames = new ArrayList<Object>(mapData1.values());
	            List<String> fileNames = new ArrayList<>();
	            for(Object fileName : pdfFileNames) {
	            	  if (fileName instanceof String) {
	                      fileNames.add(getDIR_PATH_DOCS() + fileName.toString());
	                  } else if (fileName instanceof javax.activation.FileDataSource) {
	                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
	                      fileNames.add(getDIR_PATH_DOCS() + fileDataSource.getName());
	                  } else {
	                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
	                  }
	            }
	            mapProperties.put("pdf_download", fileNames);
				try {
					this.genratePDF(erpClassifiedsMap,fileNames);
				} catch (IOException | DocumentException | java.io.IOException e) {
					e.printStackTrace();
				}
				 String pdfFilePath =  getDIR_PATH_PDF_DOCS()+erpData.getValue().getAdId()+".pdf";
		         mapData.put(erpData.getValue().getAdId()+".pdf", new FileDataSource(pdfFilePath));
		
				multiAttachments.add(mapData);
				
				emailTo.setDataSource(multiAttachments);
	            emailTo.setTemplateProps(mapProperties);
				sendService.sendCommunicationMail(emailTo, emailConfigs);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private File convertImageToPdf(String imageFile) throws IOException, DocumentException, java.io.IOException {
		BufferedImage image = javax.imageio.ImageIO.read(new File(imageFile));

		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		// Create a new Document with A4 page size
		Document document = new Document(PageSize.A4);

		// Create a temporary file for the PDF
		File pdfFile = File.createTempFile("image_to_pdf", ".pdf");

		// Create PdfWriter to write the document to the temporary PDF file
		PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

		int newWidth = 1400; // Adjust as needed
		int newHeight = 1200; // Adjust as needed

		// Resize the image
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, newWidth, newHeight, null);
		g.dispose();
		document.open();
		Image pdfImage = Image.getInstance(image, null);
		float scaleWidth = PageSize.A4.getWidth() / imageWidth;
		float scaleHeight = PageSize.A4.getHeight() / imageHeight;
		float scaleFactor = Math.min(scaleWidth, scaleHeight);
		pdfImage.scaleToFit(imageWidth * scaleFactor, imageHeight * scaleFactor);
		document.add(pdfImage);
		document.close();

		return pdfFile;
	}
	
	private static PdfPCell noBorderCell(Paragraph p) {
	    PdfPCell cell = new PdfPCell(p);
	    cell.setBorder(0);
	    return cell;
	}

	public void genratePDF(Map<String, ErpClassifieds> erpClassifiedsMap,List<String> fileNames)
			throws DocumentException, IOException, java.io.IOException {
		Document document = new Document(PageSize.A4_LANDSCAPE);
		LineSeparator line = new LineSeparator();
		erpClassifiedsMap.entrySet().forEach(erpData -> {

			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream( getDIR_PATH_PDF_DOCS()+erpData.getValue().getAdId()+".pdf"));
				document.open();

				// Creating a table of the columns
				com.itextpdf.text.Font fontHeader = new com.itextpdf.text.Font();// text font
				fontHeader.setSize(11);
				com.itextpdf.text.Font fontSubHeader = new com.itextpdf.text.Font();
				fontSubHeader.setSize(10);
				com.itextpdf.text.Font fontColumn = new com.itextpdf.text.Font();
				fontColumn.setSize(8);

				line.setLineColor(BaseColor.BLACK);

//			PdfPTable header = new PdfPTable(1);
//			header.setWidthPercentage(100);
//			PdfPCell headerCell = new PdfPCell(new com.itextpdf.text.Paragraph("Welcome to Sakshi e-Classifieds!"));
//			headerCell.setBorder(0);
//			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			header.addCell(headerCell);
//			document.add(header);
				//
//			document.add(new Paragraph(" "));
//			document.add(new Chunk(line));
				Map<String, Object> params = new HashMap<>();
				params.put("stype", SettingType.APP_SETTING.getValue());
				params.put("grps", Arrays.asList(GeneralConstants.ENV_SET_GP, GeneralConstants.ENV_SET_GP));
				SettingTo smtpSettingValues = settingDao.getSMTPSettingValues(params);
				Map<String, String> settings = smtpSettingValues.getSettings();
				PdfPTable table = new PdfPTable(3);
				table.setWidthPercentage(100);
				
				PdfPTable leftTable = new PdfPTable(1);
				leftTable.setWidthPercentage(100);
				

				
				Paragraph company = new Paragraph("JAGATI PUBLICATIONS LIMITED", fontHeader);
				Paragraph address = new Paragraph(erpData.getValue().getOfficeAddress(), fontColumn);
				Paragraph gstn = new Paragraph("GSTIN : "+ erpData.getValue().getGstIn(), fontColumn);
				Paragraph phone = new Paragraph("Ph : "+ erpData.getValue().getPhoneNumber(),fontColumn);
				
				leftTable.addCell(noBorderCell(company));
				leftTable.addCell(noBorderCell(address));
				leftTable.addCell(noBorderCell(gstn));
				leftTable.addCell(noBorderCell(phone));
				
				PdfPCell cell1 = new PdfPCell(leftTable);
				cell1.setBorder(0);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				Image	image1;
				image1 = Image
							.getInstance(new URL(settings.get("LOGO_URL")));
					image1.scaleAbsolute(80, 40);
//		            image1.scalePercent(20);			
				
					PdfPCell cell2 = new PdfPCell(image1);
					cell2.setBorder(0);
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			
					
					PdfPTable rightTable = new PdfPTable(1);
					rightTable.setWidthPercentage(100);
					
					Paragraph heading = new Paragraph("Casual Insertion Order", fontHeader);
					Paragraph code = new Paragraph("HSN/SAC Code :" + erpData.getValue().getAdId(), fontColumn);
					
					rightTable.addCell(noBorderCell(heading));
					rightTable.addCell(noBorderCell(code));
					
					PdfPCell cell3 = new PdfPCell(rightTable);
					cell3.setBorder(0);
					cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					table.addCell(cell1);
					table.addCell(cell2);
					table.addCell(cell3);
				

//				PdfPCell cell3 = new PdfPCell(new com.itextpdf.text.Paragraph("Casual Insertion Order"));
//				cell3.setBorder(0);
//				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//				table.addCell(cell1);
//				table.addCell(cell2);
//				table.addCell(cell3);

				document.add(table);

//				PdfPTable child = new PdfPTable(2);
//				child.setWidthPercentage(100);
//				Paragraph address = new Paragraph(erpData.getValue().getOfficeAddress(), fontColumn);
//				PdfPCell child1 = new PdfPCell(address);
//				child1.setBorder(0);
//				child1.setHorizontalAlignment(Element.ALIGN_LEFT);

//				Paragraph id = new Paragraph("HSN/SAC Code :" + erpData.getValue().getAdId(), fontColumn);
//
//				PdfPCell child2 = new PdfPCell(id);
//				child1.setBorder(0);
//				child2.setBorder(0);
//				child2.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//				child.addCell(child1);
//				child.addCell(child2);
//				document.add(child);

//				PdfPTable childG = new PdfPTable(1);
//				childG.setWidthPercentage(100);
//				Paragraph gstn = new Paragraph("GSTIN : "+ erpData.getValue().getGstIn(), fontColumn);
//				PdfPCell child3 = new PdfPCell(gstn);
//
//				child3.setBorder(0);
//				child3.setHorizontalAlignment(Element.ALIGN_LEFT);
//
//				Paragraph mobile = new Paragraph("Ph : "+ erpData.getValue().getPhoneNumber(),fontColumn);
//				PdfPCell child4 = new PdfPCell(mobile);
//
//				child4.setBorder(0);
//				child4.setHorizontalAlignment(Element.ALIGN_LEFT);
//				childG.addCell(child3);
//				childG.addCell(child4);
//				document.add(childG);
				document.add(new Paragraph(" "));
//				document.add(new Chunk(line));
				PdfPTable table2 = new PdfPTable(4);
				table2.addCell(createCell("Order No", BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(createCell(erpData.getValue().getAdId(), BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(createCell("State", BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(
						createCell(erpData.getValue().getStateDesc(), BaseColor.WHITE, fontColumn, false, false));
				table2.setWidthPercentage(100);
				document.add(table2);
				PdfPTable table4 = new PdfPTable(4);
				table4.addCell(createCell("Date", BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(
						createCell(erpData.getValue().getCreatedTs(), BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(createCell("Phone", BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(
						createCell(erpData.getValue().getMobileNo(), BaseColor.WHITE, fontColumn, false, false));
				table4.setWidthPercentage(100);
				document.add(table4);
				PdfPTable table1 = new PdfPTable(4);
				table1.addCell(createCell("Client Code", BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(
						createCell(erpData.getValue().getClientCode(), BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(createCell("City", BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(
						createCell(erpData.getValue().getCityDesc(), BaseColor.WHITE, fontColumn, false, false));
				table1.setWidthPercentage(100);
				document.add(table1);
				

				PdfPTable table3 = new PdfPTable(4);
				table3.addCell(createCell("Client Name ", BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(
						createCell(erpData.getValue().getClientName(), BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(createCell("Generated by", BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(createCell("Others", BaseColor.WHITE, fontColumn, false, false));
				table3.setWidthPercentage(100);
				document.add(table3);

				

				PdfPTable table5 = new PdfPTable(4);
				table5.addCell(createCell("Street/House No/Locality", BaseColor.WHITE, fontColumn, false, false));
				table5.addCell(
						createCell(erpData.getValue().getHouseNo(), BaseColor.WHITE, fontColumn, false, false));
				table5.addCell(createCell("Client GSTN", BaseColor.WHITE, fontColumn, false, false));
				table5.addCell(createCell(erpData.getValue().getGstNo(), BaseColor.WHITE, fontColumn, false, false));
				table5.setWidthPercentage(100);
				document.add(table5);
				PdfPTable table7 = new PdfPTable(4);
				table7.addCell(createCell("PinCode", BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(
						createCell(erpData.getValue().getPinCode(), BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(createCell("No of Insertions", BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(createCell(erpData.getValue().getNoOfInsertions().toString(), BaseColor.WHITE,
						fontColumn, false, false));
				table7.setWidthPercentage(100);
				document.add(table7);
				String empCode = "";
				String empName = "";
				if(erpData.getValue().getCustomerName()  == null) {
					ClfOrderItems itemDetailsOnadId = clfOrderItemsRepo.getItemDetailsOnadId(erpData.getValue().getAdId());
					UmUsers approverEmails = umUsersRepository.getApproverEmails(itemDetailsOnadId.getCreatedBy());
					if(approverEmails!= null) {
						empCode = approverEmails.getEmpCode();
						empName = approverEmails.getFirstName();
					}					
				}else {
					empCode = erpData.getValue().getEmpCode();
					empName = erpData.getValue().getCustomerName();
				}	
				PdfPTable table6 = new PdfPTable(4);
				table6.addCell(createCell("Employee / HR Code", BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(
						createCell(empCode, BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(createCell("Advertiesement Caption", BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(
						createCell(erpData.getValue().getCaption(), BaseColor.WHITE, fontColumn, false, false));
				table6.setWidthPercentage(100);
				document.add(table6);

				

				PdfPTable table8 = new PdfPTable(4);
				table8.addCell(createCell("Employee", BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(
						createCell(empName, BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(createCell("Scheme", BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(
						createCell(erpData.getValue().getSchemeStr(), BaseColor.WHITE, fontColumn, false, false));
				table8.setWidthPercentage(100);
				document.add(table8);

				document.add(new Paragraph(" "));

				PdfPTable tableF = new PdfPTable(8);
				BaseColor silverColor = new BaseColor(192, 192, 192);
				tableF.addCell(createCell("Date of Insertion", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("District/Edition", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Size Width(W)", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Size Height(H)", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Space(WxH)", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Card Rate", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Position", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Total Amount", silverColor, fontColumn, false, false));
				tableF.setWidthPercentage(100);
				document.add(tableF);

//				PdfPTable tableG = new PdfPTable(8);
//				tableG.addCell(
//						createCell(erpData.getValue().getCreatedTs(), BaseColor.WHITE, fontColumn, false, false));
//				List<String> editions = erpData.getValue().getEditions();
//				String editionsString = String.join(", ", editions);
//				tableG.addCell(createCell(editionsString, BaseColor.WHITE, fontColumn, false, false));
//				tableG.addCell(createCell(Double.toString(erpData.getValue().getSizeWidth()), BaseColor.WHITE,
//						fontColumn, false, false));
//				tableG.addCell(createCell(Double.toString(erpData.getValue().getSizeHeight()), BaseColor.WHITE,
//						fontColumn, false, false));
//				tableG.addCell(createCell(
//						Double.toString(erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight()),
//						BaseColor.WHITE, fontColumn, false, false));
//				tableG.addCell(createCell(Double.toString(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn,
//						false, false));
//				tableG.addCell(createCell(null, BaseColor.WHITE,
//						fontColumn, false, false));
//				tableG.addCell(createCell(erpData.getValue().getAmount().toString(), BaseColor.WHITE, fontColumn,
//						false, false));
//				tableG.setWidthPercentage(100);
//				document.add(tableG);

				PdfPTable tableG = new PdfPTable(8);
				tableG.setWidthPercentage(100);
				tableG.setWidths(new float[]{2, 2, 2, 2, 2, 2, 2, 2}); // Column widths

				// Get list of publish dates from erpData
				List<String> publishDates = erpData.getValue().getPublishDates();  // Assuming this is a list of publish dates

				// Loop through each publish date and create a row
				for (String publishDate : publishDates) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				    // Add Date of Insertion (Publish Date)
					Date date = new SimpleDateFormat("yyyyMMdd").parse(publishDate);
					String formattedDate = dateFormat.format(date);
				    tableG.addCell(createCell(formattedDate, BaseColor.WHITE, fontColumn, false, false));
				    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
			        String formattedAmount = currencyFormat.format(erpData.getValue().getAmount());
				    // Add other fields that are the same for each row (e.g., District/Edition, Size, etc.)
				    List<String> editions = erpData.getValue().getEditions();
				    String editionsString = String.join(", ", editions);
				    tableG.addCell(createCell(editionsString, BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(Double.toString(erpData.getValue().getSizeWidth()), BaseColor.WHITE, fontColumn, false, false));
				    tableG.addCell(createCell(Double.toString(erpData.getValue().getSizeHeight()), BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(Double.toString(erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight()), BaseColor.WHITE, fontColumn, false, false));

//				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, false));
				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getCardRate()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
//				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(erpData.getValue().getPageNumberDesc(), BaseColor.WHITE, fontColumn, false, false));  // Position (null or can be adjusted later)

//				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, false));
				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
//				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, false));
//				    tableG.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align Balance after discount to the right

				}

				// Add tableG to document
				document.add(tableG);
				PdfPTable totalAmount = new PdfPTable(8);
				PdfPCell totalLabelCell = createCell("Total", BaseColor.WHITE, fontColumn, false, false);
				totalLabelCell.setColspan(7);
				totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				totalAmount.addCell(totalLabelCell);
				totalAmount.addCell(createCell(formatToIndianCurrency(erpData.getValue().getWithSchemeTotalAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
//				totalAmount.addCell(createCell(formatToIndianCurrency(erpData.getValue().getGrandTotal()), BaseColor.WHITE,
//						fontSubHeader, false, false));
				totalAmount.setWidthPercentage(100);
				document.add(totalAmount);

				document.add(new Paragraph(" "));

//				PdfPTable discount = new PdfPTable(5);
//				discount.addCell(createCell("Name of discount", silverColor, fontSubHeader, false, false));
//				discount.addCell(createCell("Category type", silverColor, fontSubHeader, false, false));
//				discount.addCell(createCell("Discount %", silverColor, fontSubHeader, false, false));
//				discount.addCell(createCell("Discount Value", silverColor, fontSubHeader, false, false));
//				discount.addCell(createCell("Balance after discount (to be hide only for calculation)", silverColor,
//						fontSubHeader, false, false));
//				discount.setWidthPercentage(100);
//				document.add(discount);
				  PdfPTable discount = new PdfPTable(3); // 3 columns
		            discount.setWidthPercentage(100);
		            discount.setWidths(new float[]{3, 2, 2  }); // Column widths

		            discount.addCell(createCell("Name of Discount", silverColor, fontColumn, false, false));
//		            discount.addCell(createCell("Category type", silverColor, fontSubHeader, false, false));
		            discount.addCell(createCell("Discount (%)", silverColor, fontColumn, false, false));
		            discount.addCell(createCell("Discount Value", silverColor, fontColumn, false, false));
//		            discount.addCell(createCell("Balance after discount (to be hide only for calculation)", silverColor, fontSubHeader, false, false));

		            document.add(discount);
		            PdfPTable discountValuesTable = new PdfPTable(3); // 3 columns
		            discountValuesTable.setWidthPercentage(100);
		            discountValuesTable.setWidths(new float[]{3, 2,2});
		            List<RmsDiscountModel> discounts = erpData.getValue().getDiscounts();
		            List<Double> amountAfterDisc = new ArrayList<Double>();
		            for(RmsDiscountModel disc:discounts) {
		            	if (disc.getType() != null && !disc.getType().isEmpty()) {
//		            		String formattedAmount1 = String.format("%.2f", disc.getAmount());
		            		Double discoun = disc.getAmount() ;
		            		amountAfterDisc.add(discoun);
		            		if(disc.getType().equalsIgnoreCase("Category Discount")) {
		            			discountValuesTable.addCell(createCell(disc.getType()+" - "+disc.getCategoryType(), BaseColor.WHITE, fontColumn, false, false));
		            		}else {
		            			 discountValuesTable.addCell(createCell(disc.getType(), BaseColor.WHITE, fontColumn, false, false));
		            		}		                   
//		                    discountValuesTable.addCell(createCell(disc.getCategoryType(), BaseColor.WHITE, fontColumn, false, false));
		                    discountValuesTable.addCell(createCell(disc.getPercent() + "", BaseColor.WHITE, fontColumn, false, false));
		                    discountValuesTable.addCell(createCell(disc.getAmountt() != null ? disc.getAmountt() : "", BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
//		                    discountValuesTable.addCell(createCell(formatToIndianCurrency(discoun), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
//		                    PdfPCell balanceAfterDiscountCell = createCell(formatToIndianCurrency(discoun), BaseColor.WHITE, fontColumn, false, false);
//		                    balanceAfterDiscountCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align Balance after discount to the right
//		                    discountValuesTable.addCell(balanceAfterDiscountCell);
		                }
		            }
		            document.add(discountValuesTable);
		            Double amt = 0.0;
		            for(Double amount : amountAfterDisc) {
		            	amt = amt + amount;
		            }
		          
//		            String formattedAmount = String.format("%.2f", amt);
		            
		            PdfPTable totalAmountAfterDisc = new PdfPTable(3); // Ensure it has 5 columns like the previous table
		            totalAmountAfterDisc.setWidths(new float[]{3, 2, 2}); // Match column widths
		            totalAmountAfterDisc.setWidthPercentage(100); // Match width percentage

		            // Create and add the "Total" label cell
		            PdfPCell totalLabelCellDisc = createCell("Total Discount Value", BaseColor.WHITE, fontColumn, false, false);
		            totalLabelCellDisc.setColspan(2); // Span across the first 4 columns
		            totalLabelCellDisc.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            totalAmountAfterDisc.addCell(totalLabelCellDisc);

		            // Add the total amount cell
		            
//		            discountValuesTable.addCell(createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            PdfPCell totalAmountCell = createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT);
		            totalAmountAfterDisc.addCell(totalAmountCell);

		            // Add the total table to the document
		            document.add(totalAmountAfterDisc);		            
		            
		            PdfPTable totalAmountAfterDisc2 = new PdfPTable(3); // Ensure it has 5 columns like the previous table
		            totalAmountAfterDisc2.setWidths(new float[]{3, 2, 2}); // Match column widths
		            totalAmountAfterDisc2.setWidthPercentage(100); // Match width percentage

		            // Create and add the "Total" label cell
		            PdfPCell totalLabelCellDisc2 = createCell("Total After Discount", BaseColor.WHITE, fontColumn, false, false);
		            totalLabelCellDisc2.setColspan(2); // Span across the first 4 columns
		            totalLabelCellDisc2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            totalAmountAfterDisc2.addCell(totalLabelCellDisc2);

		            // Add the total amount cell
		            
//		            discountValuesTable.addCell(createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            PdfPCell totalAmountCell2 = createCell(formatToIndianCurrency(erpData.getValue().getAfterDiscountTotal()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT);
		            totalAmountAfterDisc2.addCell(totalAmountCell2);

		            // Add the total table to the document
		            document.add(totalAmountAfterDisc2);

		            
		            
//		            PdfPTable totalAmountAfterDisc = new PdfPTable(5);
//					PdfPCell totalLabelCellDisc = createCell("Total", BaseColor.WHITE, fontSubHeader, false, false);
//					totalLabelCellDisc.setColspan(4);
//					totalLabelCellDisc.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					totalAmountAfterDisc.addCell(totalLabelCellDisc);
//					PdfPCell totalValueCell = createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontSubHeader, false, false);
//					totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align Total value to the right
//					totalAmountAfterDisc.addCell(totalValueCell);
//					totalAmountAfterDisc.setWidthPercentage(100);
//					document.add(totalAmountAfterDisc);

					document.add(new Paragraph(" "));				
					
					
					PdfPTable premium = new PdfPTable(3); // 3 columns
					premium.setWidthPercentage(100);
					premium.setWidths(new float[]{3, 2, 2  }); // Column widths

					premium.addCell(createCell("Name of Premium", silverColor, fontColumn, false, false));
//		            discount.addCell(createCell("Category type", silverColor, fontSubHeader, false, false));
					premium.addCell(createCell("Percentage (%)", silverColor, fontColumn, false, false));
					premium.addCell(createCell("Value", silverColor, fontColumn, false, false));
//		            discount.addCell(createCell("Balance after discount (to be hide only for calculation)", silverColor, fontSubHeader, false, false));

		            document.add(premium);
		            PdfPTable discountValuesTable1 = new PdfPTable(3); // 3 columns
		            discountValuesTable1.setWidthPercentage(100);
		            discountValuesTable1.setWidths(new float[]{3, 2,2});
		            List<RmsPremiumModel> premium2 = erpData.getValue().getPremium();
		            List<Double> discounted = new ArrayList<Double>();
		            for(RmsPremiumModel disc:premium2) {
		            	if(disc.getType().equalsIgnoreCase("Premium")) {
		            		 discountValuesTable1.addCell(createCell(disc.getType(), BaseColor.WHITE, fontColumn, false, false));
		            		 discountValuesTable1.addCell(createCell(disc.getPercent() + "", BaseColor.WHITE, fontColumn, false, false));
		            		 discountValuesTable1.addCell(createCell(disc.getAmount()+"", BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            	}
		            	if(disc.getType().equalsIgnoreCase("Agreed Premium")) {
		            		 discountValuesTable1.addCell(createCell(disc.getType(), BaseColor.WHITE, fontColumn, false, false));
		            		 discountValuesTable1.addCell(createCell(disc.getPercent() + "", BaseColor.WHITE, fontColumn, false, false));
		            		 discountValuesTable1.addCell(createCell(disc.getAmount()+"", BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            	}
		            }
		            document.add(discountValuesTable1);
		            
//		            String formattedAmount = String.format("%.2f", amt);
		            
		            PdfPTable totalAmountAfterDisc1 = new PdfPTable(3); // Ensure it has 5 columns like the previous table
		            totalAmountAfterDisc1.setWidths(new float[]{3, 2, 2}); // Match column widths
		            totalAmountAfterDisc1.setWidthPercentage(100); // Match width percentage

		            // Create and add the "Total" label cell
		            PdfPCell totalLabelCellDisc1 = createCell("Total Premium Value", BaseColor.WHITE, fontColumn, false, false);
		            totalLabelCellDisc1.setColspan(2); // Span across the first 4 columns
		            totalLabelCellDisc1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            totalAmountAfterDisc1.addCell(totalLabelCellDisc1);

		            // Add the total amount cell
		            
//		            discountValuesTable.addCell(createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            PdfPCell totalAmountCell1 = createCell(formatToIndianCurrency(erpData.getValue().getPremiumTotal()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT);
		            totalAmountAfterDisc1.addCell(totalAmountCell1);

		            // Add the total table to the document
		            document.add(totalAmountAfterDisc1);		            
		            
		            PdfPTable totalAmountAfterDisc3 = new PdfPTable(3); // Ensure it has 5 columns like the previous table
		            totalAmountAfterDisc3.setWidths(new float[]{3, 2, 2}); // Match column widths
		            totalAmountAfterDisc3.setWidthPercentage(100); // Match width percentage

		            // Create and add the "Total" label cell
		            PdfPCell totalLabelCellDisc3 = createCell("Total After Premium", BaseColor.WHITE, fontColumn, false, false);
		            totalLabelCellDisc3.setColspan(2); // Span across the first 4 columns
		            totalLabelCellDisc3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            totalAmountAfterDisc3.addCell(totalLabelCellDisc3);

		            // Add the total amount cell
		            
//		            discountValuesTable.addCell(createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            PdfPCell totalAmountCell3 = createCell(formatToIndianCurrency(erpData.getValue().getAfterPremiumTotal()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT);
		            totalAmountAfterDisc3.addCell(totalAmountCell3);

		            // Add the total table to the document
		            document.add(totalAmountAfterDisc3);

		            
		            
//		            PdfPTable totalAmountAfterDisc = new PdfPTable(5);
//					PdfPCell totalLabelCellDisc = createCell("Total", BaseColor.WHITE, fontSubHeader, false, false);
//					totalLabelCellDisc.setColspan(4);
//					totalLabelCellDisc.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					totalAmountAfterDisc.addCell(totalLabelCellDisc);
//					PdfPCell totalValueCell = createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontSubHeader, false, false);
//					totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align Total value to the right
//					totalAmountAfterDisc.addCell(totalValueCell);
//					totalAmountAfterDisc.setWidthPercentage(100);
//					document.add(totalAmountAfterDisc);

					document.add(new Paragraph(" "));				
					
					
					PdfPTable premium3 = new PdfPTable(3); // 3 columns
					premium3.setWidthPercentage(100);
					premium3.setWidths(new float[]{3, 2, 2  }); // Column widths

					premium3.addCell(createCell("Tax", silverColor, fontColumn, false, false));
//		            discount.addCell(createCell("Category type", silverColor, fontSubHeader, false, false));
					premium3.addCell(createCell("Percentage (%)", silverColor, fontColumn, false, false));
					premium3.addCell(createCell("Value", silverColor, fontColumn, false, false));
//		            discount.addCell(createCell("Balance after discount (to be hide only for calculation)", silverColor, fontSubHeader, false, false));

		            document.add(premium3);
		            PdfPTable discountValuesTable3 = new PdfPTable(3); // 3 columns
		            discountValuesTable3.setWidthPercentage(100);
		            discountValuesTable3.setWidths(new float[]{3, 2,2});
		             List<RmsTaxModel> tax = erpData.getValue().getTax();
		             Double totalGstValue = 0.0;
		             for(RmsTaxModel disc:tax) {
		            	if(disc.getType().equalsIgnoreCase("IGST")) {
		            		totalGstValue = totalGstValue + disc.getAmount();
		            		discountValuesTable3.addCell(createCell(disc.getType(), BaseColor.WHITE, fontColumn, false, false));
		            		discountValuesTable3.addCell(createCell(disc.getPercent() + "", BaseColor.WHITE, fontColumn, false, false));
		            		discountValuesTable3.addCell(createCell(disc.getAmount()+"", BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            	}
		            	if(disc.getType().equalsIgnoreCase("SGST")) {
		            		totalGstValue = totalGstValue + disc.getAmount();
		            		discountValuesTable3.addCell(createCell(disc.getType(), BaseColor.WHITE, fontColumn, false, false));
		            		discountValuesTable3.addCell(createCell(disc.getPercent() + "", BaseColor.WHITE, fontColumn, false, false));
		            		discountValuesTable3.addCell(createCell(disc.getAmount()+"", BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            	}
		            	if(disc.getType().equalsIgnoreCase("CGST")) {
		            		totalGstValue = totalGstValue + (disc.getAmount() != null ? disc.getAmount() : 0.0);
		            		discountValuesTable3.addCell(createCell(disc.getType(), BaseColor.WHITE, fontColumn, false, false));
		            		discountValuesTable3.addCell(createCell(disc.getPercent() + "", BaseColor.WHITE, fontColumn, false, false));
		            		discountValuesTable3.addCell(createCell(disc.getAmount()+"", BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            	}
		            }
		            document.add(discountValuesTable3);
		            
//		            String formattedAmount = String.format("%.2f", amt);
		            
		            PdfPTable totalAmountAfterDisc4 = new PdfPTable(3); // Ensure it has 5 columns like the previous table
		            totalAmountAfterDisc4.setWidths(new float[]{3, 2, 2}); // Match column widths
		            totalAmountAfterDisc4.setWidthPercentage(100); // Match width percentage

		            // Create and add the "Total" label cell
		            PdfPCell totalLabelCellDisc4 = createCell("Total GST Value", BaseColor.WHITE, fontColumn, false, false);
		            totalLabelCellDisc4.setColspan(2); // Span across the first 4 columns
		            totalLabelCellDisc4.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            totalAmountAfterDisc4.addCell(totalLabelCellDisc4);

		            // Add the total amount cell
		            
//		            discountValuesTable.addCell(createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            PdfPCell totalAmountCell4 = createCell(formatToIndianCurrency(totalGstValue), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT);
		            totalAmountAfterDisc4.addCell(totalAmountCell4);

		            // Add the total table to the document
		            document.add(totalAmountAfterDisc4);		            
		            
		            PdfPTable totalAmountAfterDisc5 = new PdfPTable(3); // Ensure it has 5 columns like the previous table
		            totalAmountAfterDisc5.setWidths(new float[]{3, 2, 2}); // Match column widths
		            totalAmountAfterDisc5.setWidthPercentage(100); // Match width percentage

		            // Create and add the "Total" label cell
		            PdfPCell totalLabelCellDisc5 = createCell("Grand Total", BaseColor.WHITE, fontColumn, false, false);
		            totalLabelCellDisc5.setColspan(2); // Span across the first 4 columns
		            totalLabelCellDisc5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            totalAmountAfterDisc5.addCell(totalLabelCellDisc5);

		            // Add the total amount cell
		            
//		            discountValuesTable.addCell(createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            PdfPCell totalAmountCell5 = createCell(formatToIndianCurrency(erpData.getValue().getGrandTotal()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT);
		            totalAmountAfterDisc5.addCell(totalAmountCell5);

		            // Add the total table to the document
		            document.add(totalAmountAfterDisc5);

				document.add(new Paragraph(" "));
				Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 8f);
				Paragraph mainContent = new Paragraph(
						"Advertisement may be released as per the above details. Subject to your Terms & Conditions mentioned in the Rate Card. All Advertisements are accepted on advance payment only",smallFont);
				document.add(mainContent);


//				Paragraph signatureLines = new Paragraph("*All Advertisements are accepted on advance payment only");
//				document.add(signatureLines);
//
//				document.add(new Paragraph(" "));

				PdfPTable sig = new PdfPTable(1);
				sig.setWidthPercentage(100);


				PdfPCell sigA2 = new PdfPCell();
				sigA2.setBorder(0); // No border
				sigA2.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align to the right
				
				
				for (String fileName : fileNames) {
					String fileNameAfterDocs = fileName.substring(fileName.indexOf("DOCS/") + "DOCS/".length());
				    if (fileNameAfterDocs.toLowerCase().startsWith("sign_0") && 
				        (fileName.toLowerCase().endsWith(".jpg") || 
				        fileName.toLowerCase().endsWith(".jpeg") || 
				        fileName.toLowerCase().endsWith(".png"))) {
				        
				        try {
				            // Load the signature image
				            Image signatureImage = Image.getInstance(fileName);
//				            signatureImage.scaleAbsolute(200, 100); // Adjust the size as necessary
//				            signatureImage.setAlignment(Image.ALIGN_RIGHT);
				            
				            signatureImage.scaleToFit(100, 50); // Scale the image to fit
				            signatureImage.setAlignment(Image.ALIGN_RIGHT);
				            
				            // Add the signature image to the client signature cell
				            sigA2.addElement(signatureImage); // Add image inside the cell
				        } catch (Exception e) {
				            System.out.println("Error processing signature image file: " + fileName + " - " + e.getMessage());
				        }
				    }
				}
				
				com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);
				Paragraph signatureText = new Paragraph("(E-Signature of the client)", fontColumn);
				signatureText.setAlignment(Element.ALIGN_RIGHT); // Align the text to the right
				sigA2.addElement(signatureText);
				
//				sig.addCell(sig1);
				sig.addCell(sigA2);
				document.add(sig);
//				document.add(new Chunk(line));

				document.add(new Paragraph(" "));
				PdfPTable sigAdvt = new PdfPTable(2);
				sigAdvt.setWidthPercentage(100);


				PdfPCell sigAdv = new PdfPCell(new com.itextpdf.text.Paragraph("Signature of the Advertiser"));
				sigAdv.setBorder(0);
				sigAdv.setHorizontalAlignment(Element.ALIGN_RIGHT);


				sigAdvt.addCell(sigAdv);
				document.add(sigAdvt);
				document.close();				
				System.out.println("PDF generated successfully.");
				
			} catch (java.io.IOException | DocumentException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	private static PdfPCell createCell(String text, BaseColor backgroundColor, com.itextpdf.text.Font font,
			boolean textColor, boolean border) {
		PdfPCell cell = new PdfPCell();
		cell.addElement(new com.itextpdf.text.Paragraph(text, font));
		if (textColor) {
			font.setColor(BaseColor.WHITE);
		}
		// cell.setHorizontalAlignment(PdfPCell.ALIGN_MIDDLE);
		// cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(backgroundColor);
		if (border) {
			cell.setPaddingLeft(5);
			cell.setPaddingRight(5);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(0);
		}
		return cell;
	}
	
	
	private static PdfPCell createCell(String text, BaseColor backgroundColor, com.itextpdf.text.Font font,
			boolean textColor, boolean border, int alignment) {
		PdfPCell cell = new PdfPCell();

// Create a Paragraph with the specified alignment
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(alignment); // Set the alignment for the text

		cell.addElement(paragraph);

		if (textColor) {
			font.setColor(BaseColor.WHITE);
		}

		cell.setBackgroundColor(backgroundColor);

		if (border) {
			cell.setPaddingLeft(5);
			cell.setPaddingRight(5);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(0);
		}

		return cell;
	}


	@Override
	public GenericApiResponse generateSendPaymentLink(RmsPaymentLinkModel payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			if (payload.getOrderId() != null && payload.getItemId() != null) {
				List<Object[]> data = clfOrderItemsRepo.getOrderDetailsOnItemId(payload.getItemId());
				if (data != null && !data.isEmpty()) {
					for (Object[] obj : data) {
						Map<String, Object> params = new HashMap<>();
						params.put("stype", SettingType.APP_SETTING.getValue());
						params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
						SettingTo settingTo = settingDao.getSMTPSettingValues(params);
						Map<String, String> emailConfigs = settingTo.getSettings();
						Map<String, Object> mapProperties = new HashMap<String, Object>();
						EmailsTo emailTo = new EmailsTo();
						emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
						if (obj[0] != null) {
							emailTo.setTo((String) obj[0]);
							String[] cc = { userContext.getLoggedUser().getEmail() };
							emailTo.setBcc(cc);
						} else {
							emailTo.setTo(userContext.getLoggedUser().getEmail());
						}
						emailTo.setOrgId("1000");
						mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
						mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
						emailTo.setTemplateName(GeneralConstants.GENERATE_PAYMENT_LINK);

						String amount = obj[2] + "";
						String paymentChildId = (String) obj[3];
						String adId = (String) obj[4];
						String accessObjectId = commonService.getRequestHeaders().getAccessObjId();

						mapProperties.put("link",
								prop.getProperty("PORTAL_URL") + "rmscheckout?orderId=" + payload.getOrderId() + "&itemId=" + payload.getItemId()
										+ "&amount=" + amount + "&paymentChildId=" + paymentChildId + "&adId=" + adId
										+ "&tokenObject=" + payload.getTokenObject() + "&accessObjectId=" + accessObjectId);
						emailTo.setTemplateProps(mapProperties);
						if("SEND_EMAIL".equalsIgnoreCase(payload.getAction())) {
							sendService.sendCommunicationMail(emailTo, emailConfigs);
							apiResponse.setMessage("Payment link sent to registered email, please proceed with payment and attach the relavent documents.");
						}else {
							apiResponse.setData(mapProperties.get("link"));
							apiResponse.setMessage("Payment link genrated successfully");
						}
						
						apiResponse.setStatus(0);
					}
				}
			}else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("");
			}
		} catch (Exception e) {
			apiResponse.setStatus(1);
			apiResponse.setMessage("Link not generated");
			e.printStackTrace();
		}
		return apiResponse;
	}

	@Override
	public GenericApiResponse getFixedFormatsMasters(RmsRateModel payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		List<String> editionList = new ArrayList<>();
		List<String> formatCodes = new ArrayList<>();
		try {

			if (payload != null && payload.getEditions() != null && !payload.getEditions().isEmpty()) {

				List<GdRmsEditions> gdRmsEditions = gdRmsEditionsRepo.getRmsEditionsList(payload.getEditions());
				if (!gdRmsEditions.isEmpty()) {
					for (GdRmsEditions editons : gdRmsEditions) {
						editionList.add(editons.getErpRefId());
					}
				}

				List<GdFixedFormatCodes> fixedFormats = gdFixedFormatCodesRepo.getFixedFormatCodeList();
				if (!fixedFormats.isEmpty()) {
					for (GdFixedFormatCodes codes : fixedFormats) {
						formatCodes.add(codes.getFormatcode());
					}

					for (String edi : editionList) {
						if (!formatCodes.contains(edi)) {
							genericApiResponse.setStatus(1);
							genericApiResponse.setMessage("Above selction is not avilable");
							return genericApiResponse;
						}

						if (editionList.size() == 1 && editionList.get(0).equalsIgnoreCase("HYC")) {
							List<GdRmsFixedFormats> gdRmsFixedFormats = gdRmsFixedFormatsRepo.getAllFixedFormats();
							genericApiResponse.setStatus(0);
							genericApiResponse.setData(gdRmsFixedFormats);
							return genericApiResponse;
						} else {
							
							Map<String, Object> params = new HashMap<>();
							params.put("stype", com.portal.constants.GeneralConstants.SettingType.APP_SETTING.getValue());
							params.put("grps", Arrays.asList("FIXEDFORMATS"));
							Map<String, GdSettingConfigsTo> configsTos = globalDataDao.getGdConfigDetailsMap(params);
							String displayClm = configsTos.get("FIXED_FORMATS").getSettingDefaultValue();
							byte[] decodedBytes = Base64.getDecoder().decode(displayClm);
							String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
							ObjectMapper objectMapper = new ObjectMapper();
							List<String> erpRefCode = objectMapper.readValue(decodedString,
									new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {
									});							
							
//							List<String> erpRefCode = Arrays.asList("15", "31", "34", "36", "38", "40");
							List<GdRmsFixedFormats> gdRmsFixedFormats = gdRmsFixedFormatsRepo.getFixedFormatsOnErpRefCode(erpRefCode);
							genericApiResponse.setStatus(0);
							genericApiResponse.setData(gdRmsFixedFormats);
							return genericApiResponse;
						}
					}

				} else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("Fixed Formats not avilable.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getEditionTypeWiseDistricts(@NotNull Integer editionType, Integer formatType,Integer addType,String stateCode) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {

			if("1".equalsIgnoreCase(addType + "") && stateCode != null) { // Govt.state add type
				List<GdRmsEditions> rmsEditionsList = gdRmsEditionsRepo.getGovtStateEditionsonStateCode(addType,stateCode);
				if(rmsEditionsList != null && !rmsEditionsList.isEmpty()) {
					genericApiResponse.setData(rmsEditionsList);
					genericApiResponse.setStatus(0);
				}else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("Not Found Govt.State Editions");
				}
				return genericApiResponse;
			}
			if("5".equalsIgnoreCase(addType + "")) { // Govt.central add type
				List<GdRmsEditions> rmsEditionList = gdRmsEditionsRepo.getRmsCentralEditions(addType);
				if(rmsEditionList != null && !rmsEditionList.isEmpty()) {
					genericApiResponse.setData(rmsEditionList);
					genericApiResponse.setStatus(0);
				}else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("Not Found Govt.Central editions");
				}
				return genericApiResponse;
			}
			if (editionType != null && formatType != null) {
				if ("1".equalsIgnoreCase(formatType + "")) {
					List<GdRmsEditions> rmsEditionList = gdRmsEditionsRepo.getRmsEditionsOnEditionType(editionType);
					if (rmsEditionList != null && !rmsEditionList.isEmpty()) {
						genericApiResponse.setData(rmsEditionList);
						genericApiResponse.setStatus(0);
					} else {
						genericApiResponse.setStatus(1);
						genericApiResponse.setMessage("There are no editions with the selected edition type.");
					}
				} else {
					List<GdRmsEditions> rmsEditionList = gdRmsEditionsRepo.getRmsDistrictEditionse(editionType);
					if (rmsEditionList != null && !rmsEditionList.isEmpty()) {
						genericApiResponse.setData(rmsEditionList);
						genericApiResponse.setStatus(0);
					} else {
						genericApiResponse.setStatus(1);
						genericApiResponse.setMessage("There are no editions with the selected edition type.");
					}
				}
			} else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("Edition Type OR Format type are null");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getPagePositionsOnEditionType(@NotNull Integer editionType) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			if (editionType != null) {
				List<GdRmsPagePositions> rmsPagePositionsList = gdRmsPagePositionsRepo
						.getPagePosionOnEditionType(editionType);
				if (rmsPagePositionsList != null && !rmsPagePositionsList.isEmpty()) {
					genericApiResponse.setData(rmsPagePositionsList);
					genericApiResponse.setStatus(0);
				} else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("No Data Found");
				}
			} else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("Edition Type Not Found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse approveOrRejectRmsOrder(RmsApproveModel payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		Integer userId = userContext.getLoggedUser().getUserId();
		try {
			if (payload != null && payload.getInboxId() != null) {
				ApprovalInbox approvalInbox = approvalInboxRepo.getApprovalInboxOnId(payload.getInboxId());
				if (approvalInbox != null) {
					approvalInbox.setStatus(payload.getStatus());
					approvalInbox.setApprovalTimestamp(new Date());
					approvalInbox.setChangedBy(userId);
					approvalInbox.setChangedTs(new Date());
					approvalInbox.setComments(payload.getComments());

					baseDao.save(approvalInbox);

					if ("APPROVED".equalsIgnoreCase(payload.getStatus())) {
						Integer noOfLevels = 0;
						ClfOrders clfOrders = rmsClfOrdersRepo.getOrderDetailsOnOrderId(payload.getOrderId());
						if (clfOrders != null) {
							noOfLevels = clfOrders.getNoOfLevels();
						}
						if (!noOfLevels.equals(payload.getCurrentLevel())) {
							Double discount = 0.0;
							if (payload.getAdditionalDiscountEdit() != null
									&& payload.getAdditionalDiscountEdit() != 0.0) {
								discount = discount + payload.getAdditionalDiscountEdit();
							}
//							if (payload.getPremiumDiscountEdit() != null && payload.getPremiumDiscountEdit() != 0.0) {
//								discount = discount + payload.getPremiumDiscountEdit();
//							}
							if(payload.getAgreedPremiumDisPer() != null && payload.getAgreedPremiumDisPer() != 0.0) {
								discount = discount + payload.getAgreedPremiumDisPer();
							}
							if (discount != null && discount != 0.0) {
								this.assigneApprovalMatrix(discount, payload.getOrderId(), payload.getItemId(),
										payload.getBookingOffice(), payload.getCurrentLevel());
							}
						}else {
							this.updateOrderDetailsonStatus(payload);
							RmsModel rmsModel = new RmsModel();
							List<String> orderIds = new ArrayList<>();
							orderIds.add(payload.getOrderId());
							rmsModel.setOrderId(orderIds);
							this.syncronizeRmsSAPData(commonService.getRequestHeaders(), rmsModel);
						}
						List<String> orderIds = new ArrayList<String>();
						orderIds.add(payload.getOrderId());
						Map<String, ErpClassifieds> rmsOrderDetailsForErp = this.getRmsOrderDetailsForErp(orderIds);
						this.sendRmsMailToCustomer(rmsOrderDetailsForErp, null, userContext.getLoggedUser(), null);
					} else {
						this.updateOrderDetailsonStatus(payload);
					}
					
					apiResponse.setMessage(payload.getStatus() + " Successfully");
					apiResponse.setStatus(0);

				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage("Something went wrong. Please contact our administrator.");
				}
			} else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("Something went wrong. Please contact our administrator.");
				;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

	public void updateOrderDetailsonStatus(RmsApproveModel payload) {
		Integer userId = userContext.getLoggedUser().getUserId();
		try {

			ClfOrders clfOrders = rmsClfOrdersRepo.getOrderDetailsOnOrderId(payload.getOrderId());
			if (clfOrders != null) {
				clfOrders.setRmsOrderStatus(payload.getStatus());
				clfOrders.setPaymentStatus(payload.getStatus());
				clfOrders.setChangedTs(new Date());
				clfOrders.setChangedBy(userId);

				baseDao.saveOrUpdate(clfOrders);
			}
			ClfOrderItems clfOrderItems = clfOrderItemsRepo.getItemDetailsOnItemId(payload.getItemId());
			if (clfOrderItems != null) {
				clfOrderItems.setStatus(payload.getStatus());
				clfOrderItems.setChangedTs(new Date());
				clfOrderItems.setChangedBy(userId);

				baseDao.saveOrUpdate(clfOrderItems);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getApprovalInbox(LoggedUser loggedUser,RmsDashboardFilter payload) {
		

		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			List<ApprovalInbox> approvalInboxList = new ArrayList<>();
			LinkedHashMap<String, List<ApprovalInboxModel>> approvalInboxModelMap = new LinkedHashMap<>();
			List<String> itemIds = new ArrayList<String>();
			List<Integer> createdByIds = new ArrayList<>();
			List<ApprovalInboxModel> approvalInboxModels = new ArrayList<ApprovalInboxModel>();
			if (!"SUPER_ADMIN".equalsIgnoreCase(loggedUser.getRoleType())) {
				if (payload != null && payload.getPageNumber() != null && payload.getPageSize() != null) {
					Pageable pageable = PageRequest.of(payload.getPageNumber() - 1, payload.getPageSize());
					approvalInboxList = approvalInboxRepo
							.getInboxListOnLoginUserIdWithPagination(loggedUser.getUserId(), pageable);
				} else {
					approvalInboxList = approvalInboxRepo.getInboxListOnLoginUserId(loggedUser.getUserId());
				}
			} else {
				if (payload != null && payload.getPageNumber() != null && payload.getPageSize() != null) {
					Pageable pageable = PageRequest.of(payload.getPageNumber() - 1, payload.getPageSize());
					approvalInboxList = approvalInboxRepo.getInboxListForSuperAdminWithPagination(pageable);
				} else {
					approvalInboxList = approvalInboxRepo.getInboxListForSuperAdmin();
				}
			}
			if (approvalInboxList != null && !approvalInboxList.isEmpty()) {
				
				for(ApprovalInbox approvalInbox : approvalInboxList) {
//					if(approvalInbox.getApproverUserId().equals(userContext.getLoggedUser().getUserId())) {
						ApprovalInboxModel approvalInboxModel = new ApprovalInboxModel();
						approvalInboxModel.setInboxId(approvalInbox.getInboxId());
						approvalInboxModel.setOrderId(approvalInbox.getOrderId());
						approvalInboxModel.setItemId(approvalInbox.getItemId());
						approvalInboxModel.setCurrentLevel(approvalInbox.getCurrentLevel());
						approvalInboxModel.setApprovalTimestamp(approvalInbox.getApprovalTimestamp());
						approvalInboxModel.setStatus(approvalInbox.getStatus());
						approvalInboxModel.setApproverUserId(approvalInbox.getApproverUserId());
						approvalInboxModel.setComments(approvalInbox.getComments());
						approvalInboxModel.setLoggedUserId(userContext.getLoggedUser().getUserId());
						itemIds.add(approvalInbox.getItemId());
						List<ApprovalInboxModel> modelList = approvalInboxModelMap.getOrDefault(approvalInbox.getItemId(), new ArrayList<>());
						modelList.add(approvalInboxModel);
						approvalInboxModelMap.put(approvalInbox.getItemId(), modelList);
				}
				
				if(itemIds != null && !itemIds.isEmpty()) {
					String itemIds1 = String.join("','", itemIds);
					String query = "select itm.item_id,itm.ad_id,uc.client_code,uc.customer_name,roi.additional_discount,roi.premium_discount,roi.grand_total,co.edition_type,gret.edition_type as gret_edition_type,roi.ad_type,grat.add_type,roi.caption,itm.created_by,co.booking_unit,co.no_of_levels,bu.booking_description,itm.created_ts,roi.aggred_premium_dis_per from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id inner join rms_order_items roi on itm.item_id = roi.item_id inner join gd_rms_edition_type gret on co.edition_type = gret.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join booking_units bu on bu.booking_code = co.booking_unit where itm.mark_as_delete = false and itm.item_id in ('"
							+ itemIds1 + "')";
					List<Object[]> data = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
					if(data != null && !data.isEmpty()) {
						for (Object[] obj : data) {
							if (approvalInboxModelMap.containsKey((String) obj[0])) {
								List<ApprovalInboxModel>  approvalModels = approvalInboxModelMap.get(obj[0]);
								if(approvalModels != null && !approvalModels.isEmpty()) {
									for(ApprovalInboxModel approvalModel:approvalModels ) {
										approvalModel.setAdId((String) obj[1]);
										approvalModel.setClientCode((String) obj[2]);
										approvalModel.setCustomerName((String) obj[3]);
										approvalModel.setAdditionalDiscPercent(obj[4] != null ? ((Float) obj[4]).doubleValue() : 0.0);
										approvalModel.setPremiumDiscPercent(obj[5] != null ? ((Float) obj[5]).doubleValue() : 0.0);
										approvalModel.setGrandTotal(obj[6] != null ? ((Float) obj[6]).doubleValue() : 0.0);
										approvalModel.setEditionType((String) obj[8]);
										approvalModel.setAddType((String) obj[10]);
										approvalModel.setCaption((String) obj[11]);
										approvalModel.setCreatedBy((Integer) obj[12]);
										approvalModel.setBookingOffice((Integer) obj[13]);
										approvalModel.setNoOfLevels((Integer) obj[14]);
										approvalModel.setEmployeeBookingOffice((String) obj[15]);;
										approvalModel.setCreatedTs(CommonUtils.dateFormatter((Date) obj[16], "dd-MM-yyyy HH:mm:ss"));
										approvalModel.setAgreedPremiumDisPer(obj[17] != null ? ((Float) obj[17]).doubleValue() : 0.0);
										createdByIds.add((Integer) obj[12]);;
									}

								}
							}
						}
					}					
					List<Object[]> editionsList = clfEditionsRepo.getRmsEditionIdAndNameOnItemId(itemIds);

					for (Object[] clObj : editionsList) {
					    String key = (String) clObj[0]; // Extract the key (itemId)
					    String edition = (String) clObj[2]; // Extract the edition (name)

					    // Check if the key exists in the map
					    if (approvalInboxModelMap.containsKey(key)) {
					        // Fetch the list of ApprovalInboxModel associated with this key
					        List<ApprovalInboxModel> approvalInboxModels2 = approvalInboxModelMap.get(key);

					        // Iterate over each ApprovalInboxModel and add the edition
					        for (ApprovalInboxModel approvalInboxModel : approvalInboxModels2) {
					            // Ensure the editions list is initialized
					            if (approvalInboxModel.getEditions() == null) {
					                approvalInboxModel.setEditions(new ArrayList<>());
					            }
					            // Add the edition to the editions list
					            approvalInboxModel.getEditions().add(edition);
					        }
					    }
					}

					List<Object[]> publishDatesList = clfPublishDatesRepo.getPublishDatesForErpData(itemIds);
					for (Object[] clObj : publishDatesList) {
						String key = (String) clObj[0];
						Date pDate = (Date) clObj[1];
						if (approvalInboxModelMap.containsKey(key) ){
							 List<ApprovalInboxModel> approvalInboxModels2 = approvalInboxModelMap.get(key);
							 for(ApprovalInboxModel approvalInboxModel : approvalInboxModels2) {
								 if(approvalInboxModel.getPublishDates() == null) {
									 approvalInboxModel.setPublishDates(new ArrayList<>());
								 }
								 approvalInboxModel.getPublishDates().add(CommonUtils.dateFormatter((Date) pDate, "dd-MM-yyyy"));
							 }
							
						}
					}

					if (createdByIds != null && !createdByIds.isEmpty()) {
					    List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByIds, false);
					    if (!umUsers.isEmpty()) {
					        approvalInboxModelMap.entrySet().forEach(entry -> {
					            // `entry.getValue()` is the List<ApprovalInboxModel> for the current key
					            List<ApprovalInboxModel> approvalInboxModels2 = entry.getValue();

					            // Iterate over each ApprovalInboxModel in the list
					            approvalInboxModels2.forEach(approvalInboxModel -> {
					                // Find the corresponding UmUsers object by matching `createdBy`
					                Optional<UmUsers> matchingUser = umUsers.stream()
					                        .filter(user -> user.getUserId().equals(approvalInboxModel.getCreatedBy()))
					                        .findFirst();

					                // If a matching user is found, set the `createdByUser` field
					                if (matchingUser.isPresent()) {
					                    approvalInboxModel.setCreatedbyUser(matchingUser.get().getFirstName());
					                    approvalInboxModel.setEmpCode(matchingUser.get().getEmpCode());
					                    
					                }
					            });
					        });
					    }
					}

				}
				
				// Flattening the list of models to a single list
	            List<ApprovalInboxModel> flattenedApprovalModels = new ArrayList<>();
	            for (Map.Entry<String, List<ApprovalInboxModel>> entry : approvalInboxModelMap.entrySet()) {
	                flattenedApprovalModels.addAll(entry.getValue());
	            }
	            
	         // Sorting the list by status (pending, approved, rejected)
	            flattenedApprovalModels.sort((model1, model2) -> {
	                // Custom sorting order: "pending" > "approved" > "rejected"
	                String status1 = model1.getStatus() != null ? model1.getStatus() : "";
	                String status2 = model2.getStatus() != null ? model2.getStatus() : "";
	                return statusPriority(status1) - statusPriority(status2);
	            });

								
				apiResponse.setData(flattenedApprovalModels);
				apiResponse.setStatus(0);
			} else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("Currently,there are no orders for approval");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	
	}
	
	private int statusPriority(String status) {
	    switch (status) {
	        case "PENDING":
	            return 0;
	        case "APPROVED":
	            return 1;
	        case "REJECTED":
	            return 2;
	        default:
	            return Integer.MAX_VALUE; // Other statuses can be placed at the end
	    }
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getDraftDetailView(String orderId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		RmsViewDetails rmsViewDetails = new RmsViewDetails();
		List<String> itemIds = new ArrayList<>();
		try {
			
			//String query = "select co.order_id,uc.client_code,uc.gst_no,uc.customer_name,uc.email_id,uc.mobile_no,uc.address_1,uc.pin_code,uc.state,uc.city,uc.customer_type_id,uc.mobile_alt,uc.house_no,bu.booking_description,gs.state as gs_state,gct.customer_type_description,co.customer_id from clf_orders co inner join um_customers uc on co.customer_id = uc.customer_id left join booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) left join gd_state gs ON uc.state = gs.state_code left join gd_customer_types gct on gct.id = uc.customer_type_id where co.order_id = '" + orderId +"' and co.mark_as_delete = false";
			
			String query = "select co.order_id,uc.client_code,uc.gst_no,uc.customer_name,uc.email_id,uc.mobile_no,uc.address_1,uc.pin_code,uc.state,uc.city,uc.customer_type_id,uc.mobile_alt,uc.house_no,gs.state as gs_state,gct.customer_type_description,co.customer_id from clf_orders co inner join um_customers uc on co.customer_id = uc.customer_id left join gd_state gs ON uc.state = gs.state_code left join gd_customer_types gct on gct.id = uc.customer_type_id where co.order_id = '" + orderId +"' and co.mark_as_delete = false";
			
			List<Object[]> customerDetailsList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			if (customerDetailsList != null && !customerDetailsList.isEmpty()) {
				for (Object[] cusObj : customerDetailsList) {
					CustomerObjectDisplay customerObjectDisplay = new CustomerObjectDisplay();
					rmsViewDetails.setOrderId((String) cusObj[0]);
					customerObjectDisplay.setClientCode((String) cusObj[1]);
					customerObjectDisplay.setGstNo((String) cusObj[2]);
					customerObjectDisplay.setClientName((String) cusObj[3]);
					customerObjectDisplay.setEmailId((String) cusObj[4]);
					customerObjectDisplay.setMobileNo((String) cusObj[5]);
					customerObjectDisplay.setAddress1((String) cusObj[6]);
					customerObjectDisplay.setPinCode((String) cusObj[7]);
					customerObjectDisplay.setStateCode((String) cusObj[8]);
					customerObjectDisplay.setCityCode((String) cusObj[9]);
					customerObjectDisplay.setCustomerTypeId((Integer) cusObj[10]);
					customerObjectDisplay.setMobileAlt((String) cusObj[11]);
					customerObjectDisplay.setHouseNo((String) cusObj[12]);
					customerObjectDisplay.setCityDesc((String) cusObj[9]/* (String) cusObj[13] */);
//					customerObjectDisplay.setStateDesc((String) cusObj[14]);
					customerObjectDisplay.setStateDesc((String) cusObj[13]);
//					customerObjectDisplay.setCustomerTypeDesc((String) cusObj[15]);
					customerObjectDisplay.setCustomerTypeDesc((String) cusObj[14]);
//					customerObjectDisplay.setCustomerId((String) cusObj[16]);
					customerObjectDisplay.setCustomerId((String) cusObj[15]);
					rmsViewDetails.setCustomerObjectDisplay(customerObjectDisplay);
				}
				
				RmsViewDetails classifieds = null;
				InsertionObjectDisplay insertionObjectDisplay = new InsertionObjectDisplay();
				insertionObjectDisplay.setEditions(new ArrayList<String>());
				insertionObjectDisplay.setEditionIds(new ArrayList<Integer>());
				insertionObjectDisplay.setPublishDates(new ArrayList<String>());
				insertionObjectDisplay.setDiscounts(new ArrayList<Integer>());
				insertionObjectDisplay.setDiscountsDesc(new ArrayList<String>());
				RmsRatesResponse ratesResponse = new RmsRatesResponse();
				ratesResponse.setDiscounts(new ArrayList<RmsDiscountModel>());
				ratesResponse.setTax(new ArrayList<RmsTaxModel>());
				ratesResponse.setPremium(new ArrayList<RmsPremiumModel>());
				Double additionalDiscount=0.0;
				Double additionalDiscountAmount=0.0;
				Double multiDiscount=0.0;
				Double multiDiscountAmount=0.0;
				Double categoryDiscount=0.0;
				Double categoryDiscountAmount=0.0;
				Double premiumDiscount=0.0;
				Double igstAmount=0.0;
				Double sgstAmount=0.0;
				Double cgstAmount=0.0;
				Double premiumDiscountAmount=0.0;
				Double agreedPremiumDisPer=0.0;
				Double masterPremiumPer=0.0;
				Double premiumAmount=0.0;
				
				
				String query1 = "select itm.item_id,itm.order_id,itm.scheme AS itm_scheme,gcs.scheme AS gcs_scheme,itm.classified_ads_sub_type,gcast.ads_sub_type,itm.status,itm.ad_id,itm.category_group,itm.sub_group,itm.child_group,gcg.classified_group,gcsg.classified_sub_group,gcc.classified_child_group,roi.size_width,roi.size_height,roi.page_position,grpd.positioning_type,roi.format_type,grft.format_type as grft_format_type,roi.page_number,grpp.page_name,roi.category_discount,roi.multi_discount,roi.additional_discount,roi.multi_discount_amount,roi.additional_discount_amount,roi.category_discount_amount,roi.base_amount,roi.grand_total,roi.discount_total,roi.gst_total,roi.premium_discount,roi.premium_discount_amount,roi.ad_type,grat.add_type,roi.caption,roi.igst_amount,roi.sgst_amount,roi.cgst_amount,co.booking_unit,bu.booking_location,co.edition_type,gret.edition_type as gret_edition_type,roi.no_of_insertions,roi.billable_days,roi.aggred_premium_dis_per,roi.master_premium_per,roi.premium_amount,roi.fixed_format,grff.size from clf_order_items itm inner join clf_orders co on itm.order_id = co.order_id inner join rms_order_items roi on itm.item_id = roi.item_id inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join gd_classified_ads_sub_types gcast on  itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_positioning_discount grpd on roi.page_position = grpd.id left join gd_rms_format_types grft  on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join booking_units bu on co.booking_unit = bu.booking_code inner join gd_rms_fixed_formats grff on roi.fixed_format = grff.id inner join gd_rms_edition_type gret on co.edition_type = gret.id where itm.order_id = '" + orderId + "' and itm.mark_as_delete = false";
				
				List<Object[]> itemDetails = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
				if(itemDetails != null && !itemDetails.isEmpty()) {
					for(Object[] obj : itemDetails) {
						itemIds.add((String) obj[0]);
						insertionObjectDisplay.setItemId((String) obj[0]);
						rmsViewDetails.setItemId((String) obj[0]);
						insertionObjectDisplay.setOrderId((String) obj[1]);
						insertionObjectDisplay.setScheme((Integer) obj[2]);
						insertionObjectDisplay.setSchemeStr((String) obj[3]);
						insertionObjectDisplay.setClassifiedAdsSubType((Integer) obj[4]);
						insertionObjectDisplay.setClassifiedAdsSubTypeStr((String) obj[5]);
						insertionObjectDisplay.setStatus((String) obj[6]);
						insertionObjectDisplay.setAdId((String) obj[7]);
						rmsViewDetails.setAdId((String) obj[7]);
						insertionObjectDisplay.setCategoryGroup((Integer) obj[8]);
						insertionObjectDisplay.setCategorySubGroup((Integer) obj[9]);
						insertionObjectDisplay.setCategoryChildGroup((Integer) obj[10]);
						
						insertionObjectDisplay.setCategoryGroupDesc((String) obj[11]);
						insertionObjectDisplay.setCategorySubgroupDesc((String) obj[12]);
						insertionObjectDisplay.setCategoryChildgroupDesc((String) obj[13]);
						
						float spaceWidth = (obj[14] == null) ? 0.0f : (float) obj[14];
						float spaceHeight = (obj[15] == null) ? 0.0f : (float) obj[15];
						double formattedSpaceWidth = HelperUtil.parseDoubleValue((spaceWidth));
						String spaceW = String.valueOf(formattedSpaceWidth);
						double formattedSpaceHeight = HelperUtil.parseDoubleValue((spaceHeight));
						String spaceH = String.valueOf(formattedSpaceHeight);
						insertionObjectDisplay.setSpaceWidth(spaceW);
						insertionObjectDisplay.setSpaceHeight(spaceH);
						
						insertionObjectDisplay.setPagePosition((Integer) obj[16]);
						insertionObjectDisplay.setPositioningDesc((String) obj[17]);
						insertionObjectDisplay.setFormatType((Integer) obj[18]);
						insertionObjectDisplay.setFormatTypeDesc((String) obj[19]);						
						insertionObjectDisplay.setPageNumber((Integer) obj[20]);
						insertionObjectDisplay.setPageNumberDesc((String) obj[21]);
						
						categoryDiscount = ((Float) obj[22]) != null?((Float) obj[22]).doubleValue():null;
						multiDiscount = ((Float) obj[23]) != null?((Float) obj[23]).doubleValue():null;
						additionalDiscount = ((Float) obj[24]) != null?((Float) obj[24]).doubleValue():null;
						
						multiDiscountAmount = ((Float) obj[25]) != null?((Float) obj[25]).doubleValue():null;
						additionalDiscountAmount = ((Float) obj[26]) != null?((Float) obj[26]).doubleValue():null;
						categoryDiscountAmount = ((Float) obj[27]) != null?((Float) obj[27]).doubleValue():null;
						
						
						insertionObjectDisplay.setCategoryDiscount(categoryDiscount != null ? (String.valueOf(categoryDiscount)) : null);
						insertionObjectDisplay.setMultiDiscount(multiDiscount != null ? (String.valueOf(multiDiscount)) : null);
						insertionObjectDisplay.setAdditionalDiscount(additionalDiscount != null ? (String.valueOf(additionalDiscount)) : null);
						insertionObjectDisplay.setMultiDiscountAmount(multiDiscountAmount != null ? (String.valueOf(multiDiscountAmount)) : null);
						insertionObjectDisplay.setAdditionalDiscountAmount(additionalDiscountAmount != null ? (String.valueOf(additionalDiscountAmount)) : null);
						insertionObjectDisplay.setCategoryDiscountAmount(categoryDiscountAmount != null ? (String.valueOf(categoryDiscountAmount)) : null);
						
						ratesResponse.setAmount(((Float) obj[28]) != null ?((Float) obj[28]).doubleValue() : 0.0);
						ratesResponse.setGrandTotal(((Float) obj[29]) != null ? ((Float) obj[29]).doubleValue() :0.0);
						ratesResponse.setDiscountTotal(((Float) obj[30]) != null ? ((Float) obj[30]).doubleValue() : 0.0);
						ratesResponse.setGstTotal(((Float) obj[31]) != null ? ((Float) obj[31]).doubleValue() : 0.0);
						
						ratesResponse.setAmountString(String.format("%.2f", ratesResponse.getAmount()));
						ratesResponse.setGrandTotalString(String.format("%.2f", ratesResponse.getGrandTotal()));
						ratesResponse.setDiscountTotalString(String.format("%.2f", ratesResponse.getDiscountTotal()));
						ratesResponse.setGstTotalString(String.format("%.2f", ratesResponse.getGstTotal()));
												
						premiumDiscount = ((Float) obj[32]) != null?((Float) obj[32]).doubleValue():null;
						premiumDiscountAmount = ((Float) obj[33]) != null?((Float) obj[33]).doubleValue():null;
						
						insertionObjectDisplay.setPremiumDiscount(premiumDiscount != null ? (String.valueOf(premiumDiscount)) : null);
						insertionObjectDisplay.setPremiumDiscountAmount(premiumDiscountAmount != null ? (String.valueOf(premiumDiscountAmount)) : null);
						
						insertionObjectDisplay.setAddType((Integer) obj[34]);
						insertionObjectDisplay.setAddTypeDesc((String) obj[35]);
						insertionObjectDisplay.setCaption((String) obj[36]);
						
						igstAmount = ((Float) obj[37]) != null?((Float) obj[37]).doubleValue():null;
						sgstAmount = ((Float) obj[38]) != null?((Float) obj[38]).doubleValue():null;
						cgstAmount = ((Float) obj[39]) != null?((Float) obj[39]).doubleValue():null;
						
						insertionObjectDisplay.setBookingCode((Integer) obj[40]);
						
						insertionObjectDisplay.setEditionType((Integer) obj[42]);
						insertionObjectDisplay.setEditionTypeDesc((String) obj[43]);
						insertionObjectDisplay.setNoOfDays((Integer) obj[44]);
						insertionObjectDisplay.setBillableDays((Integer) obj[45]);
						agreedPremiumDisPer = ((Float) obj[46]) != null?((Float) obj[46]).doubleValue():0;
						masterPremiumPer = ((Float) obj[47]) != null?((Float) obj[47]).doubleValue():null;
						premiumAmount = ((Float) obj[48]) != null?((Float) obj[48]).doubleValue():0;
						ratesResponse.setPremiumTotal(premiumAmount);
						insertionObjectDisplay.setFixedFormat(obj[49] != null ? (Integer) obj[49] : null);
						insertionObjectDisplay.setFixedFormatsDesc(obj[50] != null ? (String) obj[50] : null);
						
						if(categoryDiscount != null) {
							RmsDiscountModel discountModel=new RmsDiscountModel();
							discountModel.setType("Position Instruction Discount");
							discountModel.setAmount(categoryDiscountAmount);
							discountModel.setAmountString(String.format("%.2f", categoryDiscountAmount));
							discountModel.setPercent(categoryDiscount);
							discountModel.setPercentString(String.format("%.2f", categoryDiscount));	
							ratesResponse.getDiscounts().add(discountModel);
						}
						if(additionalDiscount != null) {
							RmsDiscountModel discountModel=new RmsDiscountModel();
							discountModel.setType("Additional Discount");
							discountModel.setAmount(additionalDiscountAmount);
							discountModel.setAmountString(String.format("%.2f", additionalDiscountAmount));
							discountModel.setPercent(additionalDiscount);
							discountModel.setPercentString(String.format("%.2f", additionalDiscount));	
							ratesResponse.getDiscounts().add(discountModel);
						}
						if(multiDiscount != null) {
							RmsDiscountModel discountModel=new RmsDiscountModel();
							discountModel.setType("Multi Discount");
							discountModel.setAmount(multiDiscountAmount);
							discountModel.setAmountString(String.format("%.2f", multiDiscountAmount));
							discountModel.setPercent(multiDiscount);
							discountModel.setPercentString(String.format("%.2f", multiDiscount));	
							ratesResponse.getDiscounts().add(discountModel);
							
						}
						
//						if(premiumDiscount != null) {
//							RmsDiscountModel discountModel=new RmsDiscountModel();
//							discountModel.setType("Premium Discount");
//							discountModel.setAmount(premiumDiscountAmount);
//							discountModel.setAmountString(String.format("%.2f", premiumDiscountAmount));
//							discountModel.setPercent(premiumDiscount);
//							discountModel.setPercentString(String.format("%.2f", premiumDiscount));	
//							ratesResponse.getDiscounts().add(discountModel);
//						}
						if(sgstAmount != null) {
							RmsTaxModel rmsTaxModel = new RmsTaxModel();
							rmsTaxModel.setType("SGST");
							rmsTaxModel.setAmount(sgstAmount);
							rmsTaxModel.setAmountString(String.format("%.2f", sgstAmount));
							rmsTaxModel.setPercent(2.5);
							rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
							ratesResponse.getTax().add(rmsTaxModel);
						}

						if(igstAmount != null) {
							RmsTaxModel rmsTaxModel = new RmsTaxModel();
							rmsTaxModel.setType("IGST");
							rmsTaxModel.setAmount(igstAmount);
							rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
							rmsTaxModel.setPercent(5.0);
							rmsTaxModel.setPercentString(String.format("%.2f", 5.0));
							ratesResponse.getTax().add(rmsTaxModel);
						}

						if(cgstAmount != null) {
							RmsTaxModel rmsTaxModel = new RmsTaxModel();
							rmsTaxModel.setType("CGST");
							rmsTaxModel.setAmount(cgstAmount);
							rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
							rmsTaxModel.setPercent(2.5);
							rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
							ratesResponse.getTax().add(rmsTaxModel);
						}
						
						if(premiumDiscount != null) {
							RmsPremiumModel rmsPremiumModel = new RmsPremiumModel();
							rmsPremiumModel.setType("Agreed Premium");
							rmsPremiumModel.setAmount(premiumAmount);
							rmsPremiumModel.setPercent(premiumDiscount);
							ratesResponse.getPremium().add(rmsPremiumModel);
						}else if(masterPremiumPer != null){
							RmsPremiumModel rmsPremiumModel = new RmsPremiumModel();
							rmsPremiumModel.setType("Premium");
							rmsPremiumModel.setAmount(premiumAmount);
							rmsPremiumModel.setPercent(masterPremiumPer);
							ratesResponse.getPremium().add(rmsPremiumModel);
						}
						
						if(ratesResponse.getDiscountTotal() != null) {
							ratesResponse.setAfterDiscountTotal(ratesResponse.getAmount() - ratesResponse.getDiscountTotal());
						}
						if(ratesResponse.getPremiumTotal() != null && ratesResponse.getAfterDiscountTotal() != null) {
							ratesResponse.setAfterPremiumTotal(ratesResponse.getAfterDiscountTotal() + ratesResponse.getPremiumTotal());
						}else {
							if(ratesResponse.getPremiumTotal() != null) {
								ratesResponse.setAfterPremiumTotal(ratesResponse.getAmount() + ratesResponse.getPremiumTotal());
							}
						}
						
						rmsViewDetails.setRatesResponse(ratesResponse);
						
					}
					
					if (itemIds != null && !itemIds.isEmpty()) {
						List<Object[]> editionsList = rmsClfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
						for (Object[] clObj : editionsList) {
							insertionObjectDisplay.getEditions().add((String) clObj[2]);
							insertionObjectDisplay.getEditionIds().add((Integer) (clObj[1]));
						}
						List<Object[]> publishDatesList = rmsClfPublishDates.getPublishDatesForErpData(itemIds);
						for (Object[] clObj : publishDatesList) {
							insertionObjectDisplay.getPublishDates()
									.add(CommonUtils.dateFormatter((Date) clObj[1], "dd-MM-yyyy"));
						}
						
						List<Object[]> discountTypes = rmsOrderDiscountTypesRepo.getDiscountTypes(itemIds);
						if(discountTypes != null && !discountTypes.isEmpty()) {
							for (Object[] obj : discountTypes) {
								insertionObjectDisplay.getDiscounts().add((Integer) obj[2]);
								insertionObjectDisplay.getDiscountsDesc().add((String) obj[3]);
							}
						}
					}
					rmsViewDetails.setInsertionObjectDisplay(insertionObjectDisplay);
					
					ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
							.getTransactionDetOnSecOrderId(orderId);
					if (clfPaymentResponseTracking != null) {
						if ("success".equalsIgnoreCase(clfPaymentResponseTracking.getPaymentStatus())) {
							rmsViewDetails.setPaymentInProgress(true);
							rmsViewDetails.setOnlinePaymentStatus(true);
							apiResponse.setData(rmsViewDetails);
							return apiResponse;
						}
						Date createdTs = clfPaymentResponseTracking.getCreatedTs();
						if (createdTs != null) {
							Date now = new Date();
							long tenMinutesInMillis = 5 * 60 * 1000;
							long differenceInMillis = now.getTime() - createdTs.getTime();
							boolean flag = differenceInMillis > tenMinutesInMillis;
							if (flag) {
								rmsViewDetails.setPaymentInProgress(false);
							} else {
								rmsViewDetails.setPaymentInProgress(true);
							}
						} else {
							rmsViewDetails.setPaymentInProgress(false);
						}
						//rmsViewDetails.setPaymentInProgress(true);
					} else {
						rmsViewDetails.setPaymentInProgress(false);
					}
				}
			}else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("No Details Found");
				return apiResponse;
			}
			
			apiResponse.setData(rmsViewDetails);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

//	@Override
//	public GenericApiResponse getRmsListView(LoggedUser loggedUser, DashboardFilterTo payload) {
//		GenericApiResponse apiResponse = new GenericApiResponse();
//		RmsViewDetails rmsViewDetails = new RmsViewDetails();
//		List<String> itemIds = new ArrayList<>();
//		RmsViewDetails classifieds = null;
//		InsertionObjectDisplay insertionObjectDisplay2 = new InsertionObjectDisplay();
//		insertionObjectDisplay2.setEditions(new ArrayList<String>());
//		insertionObjectDisplay2.setEditionIds(new ArrayList<Integer>());
//		insertionObjectDisplay2.setPublishDates(new ArrayList<String>());
//		insertionObjectDisplay2.setDiscounts(new ArrayList<Integer>());
//		insertionObjectDisplay2.setDiscountsDesc(new ArrayList<String>());
//		RmsRatesResponse ratesResponse2 = new RmsRatesResponse();
//		ratesResponse2.setDiscounts(new ArrayList<RmsDiscountModel>());
//		ratesResponse2.setTax(new ArrayList<RmsTaxModel>());
//		Double additionalDiscount=0.0;
//		Double additionalDiscountAmount=0.0;
//		Double multiDiscount=0.0;
//		Double multiDiscountAmount=0.0;
//		Double categoryDiscount=0.0;
//		Double categoryDiscountAmount=0.0;
//		Double premiumDiscount=0.0;
//		Double igstAmount=0.0;
//		Double sgstAmount=0.0;
//		Double cgstAmount=0.0;
//		Double premiumDiscountAmount=0.0;
//		String query1 = "select itm.item_id, itm.order_id, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme, itm.classified_ads_sub_type, gcast.ads_sub_type, itm.status, itm.ad_id, itm.category_group, itm.sub_group, itm.child_group, gcg.classified_group, gcsg.classified_sub_group, gcc.classified_child_group, roi.size_width, roi.size_height, roi.page_position, grpd.positioning_type, roi.format_type, grft.format_type as grft_format_type, roi.page_number, grpp.page_name, roi.category_discount, roi.multi_discount, roi.additional_discount, roi.multi_discount_amount, roi.additional_discount_amount, roi.category_discount_amount, roi.base_amount, roi.grand_total, roi.discount_total, roi.gst_total, roi.premium_discount, roi.premium_discount_amount, roi.ad_type, grat.add_type, roi.caption, roi.igst_amount, roi.sgst_amount, roi.cgst_amount, co.booking_unit, bu.booking_location, co.edition_type, gret.edition_type as gret_edition_type, uc.client_code, uc.gst_no, uc.customer_name, uc.email_id, uc.mobile_no, uc.address_1, uc.pin_code, uc.state, uc.city, uc.customer_type_id, uc.mobile_alt, uc.house_no, bu.booking_description, gs.state as gs_state, gpm.payment_method, gpm2.payment_mode, rpr.bank_or_upi, rpr.cash_receipt_no from clf_order_items itm inner join clf_orders co on itm.order_id = co.order_id inner join rms_order_items roi on itm.item_id = roi.item_id inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_positioning_discount grpd on roi.page_position = grpd.id left join gd_rms_format_types grft on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join booking_units bu on co.booking_unit = bu.booking_code inner join gd_rms_edition_type gret on co.edition_type = gret.id left join um_customers uc on co.customer_id = uc.customer_id left join gd_state gs ON uc.state = gs.state_code left join gd_city gc on uc.city = CAST(bu.booking_code AS VARCHAR) inner join rms_payments_response rpr on rpr.item_id = itm.item_id left join gd_payment_method gpm on gpm.id = rpr.payment_method left join gd_payment_mode gpm2 on gpm2.id = rpr.payment_mode where co.order_type = 1 and co.order_status = 'CLOSED' and itm.mark_as_delete = false and co.mark_as_delete = false";
//		Map<String,RmsViewDetails> map = new HashMap<String,RmsViewDetails>();
//		List<Object[]> itemDetails = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
//		if(itemDetails != null && !itemDetails.isEmpty()) {
//			for(Object[] obj : itemDetails) {
//				RmsViewDetails rmsView = new RmsViewDetails();
//				InsertionObjectDisplay insertionObjectDisplay = new InsertionObjectDisplay();
//				insertionObjectDisplay.setEditions(new ArrayList<String>());
//				insertionObjectDisplay.setEditionIds(new ArrayList<Integer>());
//				insertionObjectDisplay.setPublishDates(new ArrayList<String>());
//				insertionObjectDisplay.setDiscounts(new ArrayList<Integer>());
//				insertionObjectDisplay.setDiscountsDesc(new ArrayList<String>());
//				RmsRatesResponse ratesResponse = new RmsRatesResponse();
//				ratesResponse.setDiscounts(new ArrayList<RmsDiscountModel>());
//				ratesResponse.setTax(new ArrayList<RmsTaxModel>());
//				PaymentObjectDisplay objectDisplay = new PaymentObjectDisplay();
//				CustomerObjectDisplay customerObjectDisplay = new CustomerObjectDisplay();
//				itemIds.add((String) obj[0]);
//				insertionObjectDisplay.setItemId((String) obj[0]);
//				insertionObjectDisplay.setOrderId((String) obj[1]);
//				insertionObjectDisplay.setScheme((Integer) obj[2]);
//				insertionObjectDisplay.setSchemeStr((String) obj[3]);
//				insertionObjectDisplay.setClassifiedAdsSubType((Integer) obj[4]);
//				insertionObjectDisplay.setClassifiedAdsSubTypeStr((String) obj[5]);
//				insertionObjectDisplay.setStatus((String) obj[6]);
//				insertionObjectDisplay.setAdId((String) obj[7]);
//				insertionObjectDisplay.setCategoryGroup((Integer) obj[8]);
//				insertionObjectDisplay.setCategorySubGroup((Integer) obj[9]);
//				insertionObjectDisplay.setCategoryChildGroup((Integer) obj[10]);
//				
//				insertionObjectDisplay.setCategoryGroupDesc((String) obj[11]);
//				insertionObjectDisplay.setCategorySubgroupDesc((String) obj[12]);
//				insertionObjectDisplay.setCategoryChildgroupDesc((String) obj[13]);
//				
//				float spaceWidth = (obj[14] == null) ? 0.0f : (float) obj[14];
//				float spaceHeight = (obj[15] == null) ? 0.0f : (float) obj[15];
//				double formattedSpaceWidth = HelperUtil.parseDoubleValue((spaceWidth));
//				String spaceW = String.valueOf(formattedSpaceWidth);
//				double formattedSpaceHeight = HelperUtil.parseDoubleValue((spaceHeight));
//				String spaceH = String.valueOf(formattedSpaceHeight);
//				insertionObjectDisplay.setSpaceWidth(spaceW);
//				insertionObjectDisplay.setSpaceHeight(spaceH);
//				
//				insertionObjectDisplay.setPagePosition((Integer) obj[16]);
//				insertionObjectDisplay.setPositioningDesc((String) obj[17]);
//				insertionObjectDisplay.setFormatType((Integer) obj[18]);
//				insertionObjectDisplay.setFormatTypeDesc((String) obj[19]);						
//				insertionObjectDisplay.setPageNumber((Integer) obj[20]);
//				insertionObjectDisplay.setPageNumberDesc((String) obj[21]);
//				
//				categoryDiscount = ((Float) obj[22]) != null?((Float) obj[22]).doubleValue():null;
//				multiDiscount = ((Float) obj[23]) != null?((Float) obj[23]).doubleValue():null;
//				additionalDiscount = ((Float) obj[24]) != null?((Float) obj[24]).doubleValue():null;
//				
//				multiDiscountAmount = ((Float) obj[25]) != null?((Float) obj[25]).doubleValue():null;
//				additionalDiscountAmount = ((Float) obj[26]) != null?((Float) obj[26]).doubleValue():null;
//				categoryDiscountAmount = ((Float) obj[27]) != null?((Float) obj[27]).doubleValue():null;
//				
//				
//				insertionObjectDisplay.setCategoryDiscount(categoryDiscount != null ? (String.valueOf(categoryDiscount)) : null);
//				insertionObjectDisplay.setMultiDiscount(multiDiscount != null ? (String.valueOf(multiDiscount)) : null);
//				insertionObjectDisplay.setAdditionalDiscount(additionalDiscount != null ? (String.valueOf(additionalDiscount)) : null);
//				insertionObjectDisplay.setMultiDiscountAmount(multiDiscountAmount != null ? (String.valueOf(multiDiscountAmount)) : null);
//				insertionObjectDisplay.setAdditionalDiscountAmount(additionalDiscountAmount != null ? (String.valueOf(additionalDiscountAmount)) : null);
//				insertionObjectDisplay.setCategoryDiscountAmount(categoryDiscountAmount != null ? (String.valueOf(categoryDiscountAmount)) : null);
//				
//				ratesResponse.setAmount(((Float) obj[28]) != null ? ((Float) obj[28]).doubleValue() : 0.0);
//				ratesResponse.setGrandTotal(((Float) obj[29]) != null ? ((Float) obj[29]).doubleValue() : 0.0);
//				ratesResponse.setDiscountTotal(((Float) obj[30]) != null ? ((Float) obj[30]).doubleValue() : 0.0);
//				ratesResponse.setGstTotal(((Float) obj[31]) != null ? ((Float) obj[31]).doubleValue() : 0.0);
//				
//				ratesResponse.setAmountString(String.format("%.2f", ratesResponse.getAmount()));
//				ratesResponse.setGrandTotalString(String.format("%.2f", ratesResponse.getGrandTotal()));
//				ratesResponse.setDiscountTotalString(String.format("%.2f", ratesResponse.getDiscountTotal()));
//				ratesResponse.setGstTotalString(String.format("%.2f", ratesResponse.getGstTotal()));
//										
//				premiumDiscount = ((Float) obj[32]) != null?((Float) obj[32]).doubleValue():null;
//				premiumDiscountAmount = ((Float) obj[33]) != null?((Float) obj[33]).doubleValue():null;
//				
//				insertionObjectDisplay.setPremiumDiscount(premiumDiscount != null ? (String.valueOf(premiumDiscount)) : null);
//				insertionObjectDisplay.setPremiumDiscountAmount(premiumDiscountAmount != null ? (String.valueOf(premiumDiscountAmount)) : null);
//				
//				insertionObjectDisplay.setAddType((Integer) obj[34]);
//				insertionObjectDisplay.setAddTypeDesc((String) obj[35]);
//				insertionObjectDisplay.setCaption((String) obj[36]);
//				
//				igstAmount = ((Float) obj[37]) != null?((Float) obj[37]).doubleValue():null;
//				sgstAmount = ((Float) obj[38]) != null?((Float) obj[38]).doubleValue():null;
//				cgstAmount = ((Float) obj[39]) != null?((Float) obj[39]).doubleValue():null;
//				
//				insertionObjectDisplay.setBookingCode((Integer) obj[40]);
//				
//				insertionObjectDisplay.setEditionType((Integer) obj[42]);
//				insertionObjectDisplay.setEditionTypeDesc((String) obj[43]);
//				customerObjectDisplay.setClientCode((String) obj[44]);
//				customerObjectDisplay.setGstNo((String) obj[45]);
//				customerObjectDisplay.setClientName((String) obj[46]);
//				customerObjectDisplay.setEmailId((String) obj[47]);
//				customerObjectDisplay.setMobileNo((String) obj[48]);
//				customerObjectDisplay.setAddress1((String) obj[49]);
//				customerObjectDisplay.setPinCode((String) obj[50]);
//				customerObjectDisplay.setMobileAlt((String) obj[54]);
//				customerObjectDisplay.setHouseNo((String) obj[55]);
//				customerObjectDisplay.setBookingDescription((String) obj[56]);
//				customerObjectDisplay.setStateDesc((String) obj[57]);
//				objectDisplay.setPaymentMethodDesc((String) obj[58]);
//				objectDisplay.setPaymentModeDesc((String) obj[59]);
//				objectDisplay.setBankOrUpi((String) obj[60]);
//				objectDisplay.setCashReceiptNo((String) obj[61]);;
//				if(additionalDiscount != null) {
//					RmsDiscountModel discountModel=new RmsDiscountModel();
//					discountModel.setType("Additional Discount");
//					discountModel.setAmount(additionalDiscountAmount);
//					discountModel.setAmountString(String.format("%.2f", additionalDiscountAmount));
//					discountModel.setPercent(additionalDiscount);
//					discountModel.setPercentString(String.format("%.2f", additionalDiscount));	
//					ratesResponse.getDiscounts().add(discountModel);
//				}
//				if(multiDiscount != null) {
//					RmsDiscountModel discountModel=new RmsDiscountModel();
//					discountModel.setType("Multi Discount");
//					discountModel.setAmount(multiDiscountAmount);
//					discountModel.setAmountString(String.format("%.2f", multiDiscountAmount));
//					discountModel.setPercent(multiDiscount);
//					discountModel.setPercentString(String.format("%.2f", multiDiscount));	
//					ratesResponse.getDiscounts().add(discountModel);
//					
//				}
//				if(categoryDiscount != null) {
//					RmsDiscountModel discountModel=new RmsDiscountModel();
//					discountModel.setType("Category Discount");
//					discountModel.setAmount(categoryDiscountAmount);
//					discountModel.setAmountString(String.format("%.2f", categoryDiscountAmount));
//					discountModel.setPercent(categoryDiscount);
//					discountModel.setPercentString(String.format("%.2f", categoryDiscount));	
//					ratesResponse.getDiscounts().add(discountModel);
//				}
//				if(premiumDiscount != null) {
//					RmsDiscountModel discountModel=new RmsDiscountModel();
//					discountModel.setType("Premium Discount");
//					discountModel.setAmount(premiumDiscountAmount);
//					discountModel.setAmountString(String.format("%.2f", premiumDiscountAmount));
//					discountModel.setPercent(premiumDiscount);
//					discountModel.setPercentString(String.format("%.2f", premiumDiscount));	
//					ratesResponse.getDiscounts().add(discountModel);
//				}
//				if(sgstAmount != null) {
//					RmsTaxModel rmsTaxModel = new RmsTaxModel();
//					rmsTaxModel.setType("SGST");
//					rmsTaxModel.setAmount(sgstAmount);
//					rmsTaxModel.setAmountString(String.format("%.2f", sgstAmount));
//					rmsTaxModel.setPercent(2.5);
//					rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
//					ratesResponse.getTax().add(rmsTaxModel);
//				}
//
//				if(igstAmount != null) {
//					RmsTaxModel rmsTaxModel = new RmsTaxModel();
//					rmsTaxModel.setType("IGST");
//					rmsTaxModel.setAmount(igstAmount);
//					rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
//					rmsTaxModel.setPercent(5.0);
//					rmsTaxModel.setPercentString(String.format("%.2f", 5.0));
//					ratesResponse.getTax().add(rmsTaxModel);
//				}
//
//				if(cgstAmount != null) {
//					RmsTaxModel rmsTaxModel = new RmsTaxModel();
//					rmsTaxModel.setType("CGST");
//					rmsTaxModel.setAmount(igstAmount);
//					rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
//					rmsTaxModel.setPercent(2.5);
//					rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
//					ratesResponse.getTax().add(rmsTaxModel);
//				}
//				rmsView.setInsertionObjectDisplay(insertionObjectDisplay);
//				rmsView.setRatesResponse(ratesResponse);
//				rmsView.setCustomerObjectDisplay(customerObjectDisplay);
//				rmsView.setPaymentObjectDisplay(objectDisplay);
//				map.put((String) obj[0], rmsView);
//				
//			}
//			
//			if (itemIds != null && !itemIds.isEmpty()) {
//				List<Object[]> editionsList = rmsClfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
//				for (Object[] clObj : editionsList) {
//					
//					if (map.get((String) clObj[0]).getInsertionObjectDisplay().getEditions() != null) {
//						map.get((String) clObj[0]).getInsertionObjectDisplay().getEditions().add((String) clObj[2]);
//					} else {
//						List<String> edditions = new ArrayList<>();
//						edditions.add((String) clObj[2]);
//						RmsViewDetails classified = map.get((String) clObj[0]);
//						classified.getInsertionObjectDisplay().setEditions(edditions);
//						map.put((String) clObj[0], classified);
//					}
////					insertionObjectDisplay2.getEditions().add((String) clObj[2]);
////					insertionObjectDisplay2.getEditionIds().add((Integer) (clObj[1]));
//				}
//				List<Object[]> publishDatesList = rmsClfPublishDates.getPublishDatesForErpData(itemIds);
//				for (Object[] clObj : publishDatesList) {
//					map.get((String) clObj[0]).getInsertionObjectDisplay().getPublishDates()
//							.add(CommonUtils.dateFormatter((Date) clObj[1], "yyyy-MM-dd"));
//				}
//				
//				List<Object[]> discountTypes = rmsOrderDiscountTypesRepo.getDiscountTypes(itemIds);
//				if(discountTypes != null && !discountTypes.isEmpty()) {
//					for (Object[] obj : discountTypes) {
//						map.get((String) obj[0]).getInsertionObjectDisplay().getDiscounts().add((Integer) obj[2]);
//						map.get((String) obj[0]).getInsertionObjectDisplay().getDiscountsDesc().add((String) obj[3]);
//					}
//				}
//			}
////			rmsViewDetails.setInsertionObjectDisplay(insertionObjectDisplay2);
//			apiResponse.setStatus(0);
//			apiResponse.setData(map.values());
//		}
//		return apiResponse;
//	}
	
	@Override
	public GenericApiResponse getRmsListView(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		RmsViewDetails rmsViewDetails = new RmsViewDetails();
		List<String> itemIds = new ArrayList<>();
		RmsViewDetails classifieds = null;
		InsertionObjectDisplay insertionObjectDisplay2 = new InsertionObjectDisplay();
		insertionObjectDisplay2.setEditions(new ArrayList<String>());
		insertionObjectDisplay2.setEditionIds(new ArrayList<Integer>());
		insertionObjectDisplay2.setPublishDates(new ArrayList<String>());
		insertionObjectDisplay2.setDiscounts(new ArrayList<Integer>());
		insertionObjectDisplay2.setDiscountsDesc(new ArrayList<String>());
		RmsRatesResponse ratesResponse2 = new RmsRatesResponse();
		ratesResponse2.setDiscounts(new ArrayList<RmsDiscountModel>());
		ratesResponse2.setTax(new ArrayList<RmsTaxModel>());
		Double additionalDiscount=0.0;
		Double additionalDiscountAmount=0.0;
		Double multiDiscount=0.0;
		Double multiDiscountAmount=0.0;
		Double categoryDiscount=0.0;
		Double categoryDiscountAmount=0.0;
		Double premiumDiscount=0.0;
		Double igstAmount=0.0;
		Double sgstAmount=0.0;
		Double cgstAmount=0.0;
		Double premiumDiscountAmount=0.0;
		String query1 = "select distinct itm.item_id, itm.order_id, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme, itm.classified_ads_sub_type, gcast.ads_sub_type, itm.status, itm.ad_id, itm.category_group, itm.sub_group, itm.child_group, gcg.classified_group, gcsg.classified_sub_group, gcc.classified_child_group, roi.size_width, roi.size_height, roi.page_position, grpd.positioning_type, roi.format_type, grft.format_type as grft_format_type, roi.page_number, grpp.page_name, roi.category_discount, roi.multi_discount, roi.additional_discount, roi.multi_discount_amount, roi.additional_discount_amount, roi.category_discount_amount, roi.base_amount, roi.grand_total, roi.discount_total, roi.gst_total, roi.premium_discount, roi.premium_discount_amount, roi.ad_type, grat.add_type, roi.caption, roi.igst_amount, roi.sgst_amount, roi.cgst_amount, co.booking_unit, bu.booking_location, co.edition_type, gret.edition_type as gret_edition_type, uc.client_code, uc.gst_no, uc.customer_name, uc.email_id, uc.mobile_no, uc.address_1, uc.pin_code, uc.state, uc.city, uc.customer_type_id, uc.mobile_alt, uc.house_no, bu.booking_description, gs.state as gs_state, gpm.payment_method, gpm2.payment_mode, rpr.bank_or_upi, rpr.cash_receipt_no,co.wf_id,itm.created_ts from clf_order_items itm inner join clf_orders co on itm.order_id = co.order_id inner join rms_order_items roi on itm.item_id = roi.item_id inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_positioning_discount grpd on roi.page_position = grpd.id left join gd_rms_format_types grft on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join booking_units bu on co.booking_unit = bu.booking_code inner join gd_rms_edition_type gret on co.edition_type = gret.id left join um_customers uc on co.customer_id = uc.customer_id left join gd_state gs ON uc.state = gs.state_code left join gd_city gc on uc.city = CAST(bu.booking_code AS VARCHAR) inner join rms_payments_response rpr on rpr.item_id = itm.item_id left join gd_payment_method gpm on gpm.id = rpr.payment_method left join gd_payment_mode gpm2 on gpm2.id = rpr.payment_mode where co.order_type = 1 and co.order_status = 'CLOSED' and itm.mark_as_delete = false and co.mark_as_delete = false";
		
		if(!"SUPER_ADMIN".equalsIgnoreCase(loggedUser.getRoleType())) {
			query1 = query1 + " and itm.created_by =" + loggedUser.getUserId() + " order by itm.created_ts desc";
		}else {
			query1 = query1 + " order by itm.created_ts desc";
		}
		
		Map<String,RmsListViewModel> map = new HashMap<String,RmsListViewModel>();
		List<Object[]> itemDetails = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
		Double sizeWidth=0.0;
		Double sizeHeight=0.0;
		String orderId="";
		String itemId="";
		Double discount = 0.0;
		List<String> orderIds = new ArrayList<String>();
		Integer bookingCode=0;
		if(itemDetails != null && !itemDetails.isEmpty()) {
			for(Object[] obj : itemDetails) {
				RmsListViewModel rmsView = new RmsListViewModel();
				InsertionObjectDisplay insertionObjectDisplay = new InsertionObjectDisplay();
				rmsView.setEditions(new ArrayList<String>());
				rmsView.setEditionIds(new ArrayList<Integer>());
				rmsView.setPublishDates(new ArrayList<String>());
//				insertionObjectDisplay.setDiscounts(new ArrayList<Integer>());
				rmsView.setDiscountsDesc(new ArrayList<String>());
				RmsRatesResponse ratesResponse = new RmsRatesResponse();
				rmsView.setDiscounts(new ArrayList<RmsDiscountModel>());
				rmsView.setTax(new ArrayList<RmsTaxModel>());
				PaymentObjectDisplay objectDisplay = new PaymentObjectDisplay();
				CustomerObjectDisplay customerObjectDisplay = new CustomerObjectDisplay();
				itemIds.add((String) obj[0]);
				itemId=(String) obj[0];
				orderIds.add((String) obj[1]);
				rmsView.setItemId((String) obj[0]);
				rmsView.setOrderId((String) obj[1]);
				orderId=(String) obj[1];
				rmsView.setScheme((Integer) obj[2]);
				rmsView.setSchemeStr((String) obj[3]);
				rmsView.setClassifiedAdsSubType((Integer) obj[4]);
				rmsView.setClassifiedAdsSubTypeStr((String) obj[5]);
				rmsView.setStatus((String) obj[6]);
				rmsView.setAdId((String) obj[7]);
				rmsView.setCategoryGroup((Integer) obj[8]);
				rmsView.setCategorySubGroup((Integer) obj[9]);
				rmsView.setCategoryChildGroup((Integer) obj[10]);
				
				rmsView.setCategoryGroupDesc((String) obj[11]);
				rmsView.setCategorySubgroupDesc((String) obj[12]);
				rmsView.setCategoryChildgroupDesc((String) obj[13]);
				
				float spaceWidth = (obj[14] == null) ? 0.0f : (float) obj[14];
				float spaceHeight = (obj[15] == null) ? 0.0f : (float) obj[15];
				double formattedSpaceWidth = HelperUtil.parseDoubleValue((spaceWidth));
				String spaceW = String.valueOf(formattedSpaceWidth);
				double formattedSpaceHeight = HelperUtil.parseDoubleValue((spaceHeight));
				String spaceH = String.valueOf(formattedSpaceHeight);
				rmsView.setSpaceWidth(spaceW);
				rmsView.setSpaceHeight(spaceH);
				sizeHeight=formattedSpaceHeight;
				sizeWidth=formattedSpaceWidth;
				rmsView.setPagePosition((Integer) obj[16]);
				rmsView.setPositioningDesc((String) obj[17]);
				rmsView.setFormatType((Integer) obj[18]);
				rmsView.setFormatTypeDesc((String) obj[19]);						
				rmsView.setPageNumber((Integer) obj[20]);
				rmsView.setPageNumberDesc((String) obj[21]);
				
				categoryDiscount = ((Float) obj[22]) != null?((Float) obj[22]).doubleValue():null;
				multiDiscount = ((Float) obj[23]) != null?((Float) obj[23]).doubleValue():null;
				additionalDiscount = ((Float) obj[24]) != null?((Float) obj[24]).doubleValue():null;
				
				multiDiscountAmount = ((Float) obj[25]) != null?((Float) obj[25]).doubleValue():null;
				additionalDiscountAmount = ((Float) obj[26]) != null?((Float) obj[26]).doubleValue():null;
				categoryDiscountAmount = ((Float) obj[27]) != null?((Float) obj[27]).doubleValue():null;
				
				
				rmsView.setCategoryDiscount(categoryDiscount != null ? (String.valueOf(categoryDiscount)) : null);
				rmsView.setMultiDiscount(multiDiscount != null ? (String.valueOf(multiDiscount)) : null);
				rmsView.setAdditionalDiscount(additionalDiscount != null ? (String.valueOf(additionalDiscount)) : null);
				rmsView.setMultiDiscountAmount(multiDiscountAmount != null ? (String.valueOf(multiDiscountAmount)) : null);
				rmsView.setAdditionalDiscountAmount(additionalDiscountAmount != null ? (String.valueOf(additionalDiscountAmount)) : null);
				rmsView.setCategoryDiscountAmount(categoryDiscountAmount != null ? (String.valueOf(categoryDiscountAmount)) : null);
				
				rmsView.setAmount(((Float) obj[28]) != null ? ((Float) obj[28]).doubleValue() : 0.0);
				rmsView.setGrandTotal(((Float) obj[29]) != null ? ((Float) obj[29]).doubleValue() : 0.0);
				rmsView.setDiscountTotal(((Float) obj[30]) != null ? ((Float) obj[30]).doubleValue() : 0.0);
				rmsView.setGstTotal(((Float) obj[31]) != null ? ((Float) obj[31]).doubleValue() : 0.0);
				
				rmsView.setAmountString(String.format("%.2f", ratesResponse.getAmount()));
				rmsView.setGrandTotalString(String.format("%.2f", ratesResponse.getGrandTotal()));
				rmsView.setDiscountTotalString(String.format("%.2f", ratesResponse.getDiscountTotal()));
				rmsView.setGstTotalString(String.format("%.2f", ratesResponse.getGstTotal()));
										
				premiumDiscount = ((Float) obj[32]) != null?((Float) obj[32]).doubleValue():null;
				premiumDiscountAmount = ((Float) obj[33]) != null?((Float) obj[33]).doubleValue():null;
				
				rmsView.setPremiumDiscount(premiumDiscount != null ? (String.valueOf(premiumDiscount)) : null);
				rmsView.setPremiumDiscountAmount(premiumDiscountAmount != null ? (String.valueOf(premiumDiscountAmount)) : null);
				
				rmsView.setAddType((Integer) obj[34]);
				rmsView.setAddTypeDesc((String) obj[35]);
				rmsView.setCaption((String) obj[36]);
				
				igstAmount = ((Float) obj[37]) != null?((Float) obj[37]).doubleValue():null;
				sgstAmount = ((Float) obj[38]) != null?((Float) obj[38]).doubleValue():null;
				cgstAmount = ((Float) obj[39]) != null?((Float) obj[39]).doubleValue():null;
				
				rmsView.setBookingCode((Integer) obj[40]);
				bookingCode=(Integer) obj[40];
				rmsView.setEditionType((Integer) obj[42]);
				rmsView.setEditionTypeDesc((String) obj[43]);
				rmsView.setClientCode((String) obj[44]);
				rmsView.setGstNo((String) obj[45]);
				rmsView.setClientName((String) obj[46]);
				rmsView.setEmailId((String) obj[47]);
				rmsView.setMobileNo((String) obj[48]);
				rmsView.setAddress1((String) obj[49]);
				rmsView.setPinCode((String) obj[50]);
				rmsView.setMobileAlt((String) obj[54]);
				rmsView.setHouseNo((String) obj[55]);
				rmsView.setBookingDescription((String) obj[56]);
				rmsView.setStateDesc((String) obj[57]);
				rmsView.setPaymentMethodDesc((String) obj[58]);
				rmsView.setPaymentModeDesc((String) obj[59]);
				rmsView.setBankOrUpi((String) obj[60]);
				rmsView.setCashReceiptNo((String) obj[61]);
				rmsView.setWfId((String) obj[62]);
				rmsView.setCreatedTs(CommonUtils.dateFormatter((Date) obj[63], "yyyy-MM-dd HH:mm"));
				rmsView.setLoggedUserId(userContext.getLoggedUser().getUserId());
				if(additionalDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Additional Discount");
					discountModel.setAmount(additionalDiscountAmount);
					discountModel.setAmountString(String.format("%.2f", additionalDiscountAmount));
					discountModel.setPercent(additionalDiscount);
					discountModel.setPercentString(String.format("%.2f", additionalDiscount));	
					rmsView.getDiscounts().add(discountModel);
				}
				if(multiDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Multi Discount");
					discountModel.setAmount(multiDiscountAmount);
					discountModel.setAmountString(String.format("%.2f", multiDiscountAmount));
					discountModel.setPercent(multiDiscount);
					discountModel.setPercentString(String.format("%.2f", multiDiscount));	
					rmsView.getDiscounts().add(discountModel);
					
				}
				if(categoryDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Category Discount");
					discountModel.setAmount(categoryDiscountAmount);
					discountModel.setAmountString(String.format("%.2f", categoryDiscountAmount));
					discountModel.setPercent(categoryDiscount);
					discountModel.setPercentString(String.format("%.2f", categoryDiscount));	
					rmsView.getDiscounts().add(discountModel);
				}
				if(premiumDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Premium Discount");
					discountModel.setAmount(premiumDiscountAmount);
					discountModel.setAmountString(String.format("%.2f", premiumDiscountAmount));
					discountModel.setPercent(premiumDiscount);
					discountModel.setPercentString(String.format("%.2f", premiumDiscount));	
					rmsView.getDiscounts().add(discountModel);
				}
				if(sgstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("SGST");
					rmsTaxModel.setAmount(sgstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", sgstAmount));
					rmsTaxModel.setPercent(2.5);
					rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
					rmsView.getTax().add(rmsTaxModel);
				}

				if(igstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("IGST");
					rmsTaxModel.setAmount(igstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
					rmsTaxModel.setPercent(5.0);
					rmsTaxModel.setPercentString(String.format("%.2f", 5.0));
					rmsView.getTax().add(rmsTaxModel);
				}

				if(cgstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("CGST");
					rmsTaxModel.setAmount(igstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
					rmsTaxModel.setPercent(2.5);
					rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
					rmsView.getTax().add(rmsTaxModel);
				}
				 discount = 0.0;
				if(sizeHeight != null && sizeHeight != 0.0) {
					discount = discount + sizeHeight;
				}
				if(sizeWidth != null && sizeWidth != 0.0) {
					discount = discount + sizeWidth;
				}
				List<ApprovalDetailsModel> approvalDetailsModel = new ArrayList<>();
				List<ApprovalDetailsModel> list= new ArrayList<ApprovalDetailsModel>();
//				if(rmsView.getWfId() != null) {
					
					if(rmsView.getWfId() != null) {
						WfInboxMaster wfInboxMaster = wfInboxMasterRepo.getWfInboxMasterDetailsOnWfId(rmsView.getWfId(),rmsView.getItemId());
						if(wfInboxMaster != null) {
							List<WfInboxMasterDetails> wfInboxMasterDetailsList = wfInboxMasterDetailsRepo.getInboxMasterDetails(wfInboxMaster.getInboxMasterId());
							if(wfInboxMasterDetailsList != null && !wfInboxMasterDetailsList.isEmpty()) {
								rmsView.setLevel1("1");
								rmsView.setStatus1("---");
								rmsView.setLevel2("2");
								rmsView.setStatus2("---");
								rmsView.setLevel3("3");
								rmsView.setStatus3("---");
								for(WfInboxMasterDetails details : wfInboxMasterDetailsList) {
									if("1".equalsIgnoreCase(details.getTargetRef())) {
										rmsView.setLevel1(details.getTargetRef());
										rmsView.setStatus1(details.getStatus());
									}
									if("2".equalsIgnoreCase(details.getTargetRef())) {
										rmsView.setLevel2(details.getTargetRef());
										rmsView.setStatus2(details.getStatus());
									}
									if("3".equalsIgnoreCase(details.getTargetRef())) {
										rmsView.setLevel3(details.getTargetRef());
										rmsView.setStatus3(details.getStatus());
									}
								}
							}
						}
//						WfListViewModel wfListModle = new WfListViewModel();
//						wfListModle.setWfId(rmsView.getWfId());
//						wfListModle.setDocumentMasterId(itemId);
//						try {
//							GenericApiResponse wfStatus = workFlowService.getWfStatus(commonService.getRequestHeaders(), wfListModle);
//							System.out.println(wfStatus);
//							if(wfStatus.getStatus()== 0) {
//								WfStatusModel data = (WfStatusModel) wfStatus.getData();
//								if(data != null) {
//									List<DocumentWfStatus> wfStatus2 = data.getWfStatus();
//									 for(DocumentWfStatus wf : wfStatus2) {
//										 ApprovalDetailsModel approval = new ApprovalDetailsModel();
//										 approval.setApprovalStatus(wf.getWfStatus());
//										 if(wf.getRefLevelNo() != null) {
//											 approval.setApprovalLevels(Integer.parseInt(wf.getRefLevelNo()));
//										 }
//										 list.add(approval);
//									 }
//								}
//							}
//						} catch (JsonProcessingException e) {
//							e.printStackTrace();
//						}
					}
//					 approvalDetailsModel = this.getApprovalList(discount,orderId,bookingCode);
//					 int level = 0;
//					 if(!approvalDetailsModel.isEmpty())
//						 approvalDetailsModel.sort(Comparator.comparing(ApprovalDetailsModel::getApprovalLevels));
//					 for(ApprovalDetailsModel approvalDetailsModel2:approvalDetailsModel) {
//						 String existingDetails="";
//						  existingDetails = rmsView.getApprovalLevels() != null 
//				                    ? rmsView.getApprovalLevels() 
//				                    : "";
//						  String combinedDetails = existingDetails +
//				                    (existingDetails.isEmpty() ? "" : "\n") + // Add a newline if there are existing details
//				                    "Approval Levels: " + approvalDetailsModel2.getApprovalLevels() + ", " +
//				                    "Approver Name: " + approvalDetailsModel2.getApproverName() + ", " +
//				                    "Approval Timestamp: " + (approvalDetailsModel2.getApprovalTs() != null ? approvalDetailsModel2.getApprovalTs():"") + ", " +
//				                    "Approval Status: " + approvalDetailsModel2.getApprovalStatus();
//						  rmsView.setApprovalLevels(combinedDetails);
//						 ApprovalDetailsModel approval = new ApprovalDetailsModel();
////						 approval.setApprovalLevels(++level);
//						 approval.setApprovalLevels(approvalDetailsModel2.getApprovalLevels());
//						 approval.setApproverName(approvalDetailsModel2.getApproverName());
//						 approval.setApprovalStatus(approvalDetailsModel2.getApprovalStatus());
//						 approval.setApprovalTs( approvalDetailsModel2.getApprovalTs());
//						 approval.setApprovalDetails( "Approver Name: " + approvalDetailsModel2.getApproverName() + " "+ "Approval Status: " + approvalDetailsModel2.getApprovalStatus()+" "+"Approval Timestamp: " + approvalDetailsModel2.getApprovalTs());
//						 list.add(approval);
//						
//					 }
//					 rmsView.setApprovalDetailsModels(list);
//				}
				map.put((String) obj[0], rmsView);
				
			}
			
			if (itemIds != null && !itemIds.isEmpty()) {
				List<Object[]> editionsList = rmsClfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
				for (Object[] clObj : editionsList) {
					
					if (map.get((String) clObj[0]).getEditions() != null) {
						map.get((String) clObj[0]).getEditions().add((String) clObj[2]);
					} else {
						List<String> edditions = new ArrayList<>();
						edditions.add((String) clObj[2]);
						RmsListViewModel classified = map.get((String) clObj[0]);
						classified.setEditions(edditions);
						map.put((String) clObj[0], classified);
					}
//					insertionObjectDisplay2.getEditions().add((String) clObj[2]);
//					insertionObjectDisplay2.getEditionIds().add((Integer) (clObj[1]));
				}
				List<Object[]> publishDatesList = rmsClfPublishDates.getPublishDatesForErpData(itemIds);
				for (Object[] clObj : publishDatesList) {
					map.get((String) clObj[0]).getPublishDates()
							.add(CommonUtils.dateFormatter((Date) clObj[1], "dd-MM-yyyy"));
				}
				
				List<Object[]> discountTypes = rmsOrderDiscountTypesRepo.getDiscountTypes(itemIds);
				if(discountTypes != null && !discountTypes.isEmpty()) {
					for (Object[] obj : discountTypes) {
//						map.get((String) obj[0]).getInsertionObjectDisplay().getDiscounts().add((Integer) obj[2]);
						map.get((String) obj[0]).getDiscountsDesc().add((String) obj[3]);
					}
				}
			}
//			if(itemIds != null && !itemIds.isEmpty()) {
//				List<ApprovalDetailsModel> approvalDetailsModel = new ArrayList<>();
//				List<ApprovalInbox> approvalListByItemId = inboxRepo.getApprovalListByItemId(itemIds);
//				
//				for(ApprovalInbox approvalInbox:approvalListByItemId) {
//					if(map.get(approvalInbox).getItemId() != null) {
//						approvalDetailsModel=this.getApprovalList(discount,approvalInbox.getOrderId(),bookingCode);
//						map.get(approvalInbox.getItemId());
//					}
//				}
//			}
//			
			List<RmsListViewModel> sortedList = map.values().stream()
		            .sorted(Comparator.comparing(RmsListViewModel::getCreatedTs).reversed())
		            .collect(Collectors.toList());
		
//			rmsViewDetails.setInsertionObjectDisplay(insertionObjectDisplay2);
			apiResponse.setStatus(0);
			apiResponse.setData(sortedList);
		}
		return apiResponse;
	}

	@Override
	public GenericApiResponse deleteDraftOrders(LoggedUser loggedUser, String orderId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		Integer userId = userContext.getLoggedUser().getUserId();
		try {
			if (orderId != null) {
				ClfOrders clfOrders = rmsClfOrdersRepo.getOrderDetailsOnOrderId(orderId);
				if (clfOrders != null) {
					clfOrders.setMarkAsDelete(true);
					clfOrders.setChangedBy(userId);
					clfOrders.setChangedTs(new Date());

					baseDao.saveOrUpdate(clfOrders);

					ClfOrderItems clfOrderItems = clfOrderItemsRepo.getOpenOrderItemsByOrderId(orderId);
					if (clfOrderItems != null) {
						clfOrderItems.setMarkAsDelete(true);
						clfOrderItems.setChangedBy(userId);
						clfOrderItems.setChangedTs(new Date());

						baseDao.save(clfOrderItems);
					}

					RmsOrderItems rmsOrderItems = orderItemsRepo.getRmsItemsDetailsOnOrderId(orderId);
					if (rmsOrderItems != null) {
						rmsOrderItems.setMarkAsDelete(true);
						rmsOrderItems.setChangedBy(userId);
						rmsOrderItems.setChangedTs(new Date());

						baseDao.saveOrUpdate(rmsOrderItems);
					}
					apiResponse.setStatus(0);
					apiResponse.setMessage("Successfully Deleted");
				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage("Order Details Not Found");
				}
			}else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("Order Id is Null");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

	@Override
	public void deleteRmsDraftOrders() {
		try {
			
//			rmsClfOrdersRepo.updateDraftOrderDetails(new Date(),true);
			
			List<ClfOrders> clfOrdersList = rmsClfOrdersRepo.getDraftOderDetails();
			if(clfOrdersList != null && !clfOrdersList.isEmpty()) {
				for(ClfOrders clfOrders : clfOrdersList) {
					clfOrders.setMarkAsDelete(true);
					clfOrders.setChangedTs(new Date());
					
					baseDao.saveOrUpdate(clfOrders);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String formatToIndianCurrency(Double amount) {
        // Get the Indian locale
		String formattedAmount = "";
		if(amount != null) {
        Locale indianLocale = new Locale("en", "IN");
        
        // Get the number format instance for the Indian locale
        NumberFormat currencyFormatter = NumberFormat.getInstance(indianLocale);

        // Set the minimum and maximum fraction digits to 2
        currencyFormatter.setMinimumFractionDigits(2);
        currencyFormatter.setMaximumFractionDigits(2);

        // Format the number
         formattedAmount = currencyFormatter.format(amount);
		}

        return formattedAmount;
    }

	@Override
	public GenericApiResponse downloadPdf(LoggedUser loggedUser, @NotNull String orderId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			apiResponse.setStatus(0);
			List<String> orderIds = new ArrayList<String>();
			orderIds.add(orderId);
			Map<String, ErpClassifieds> rmsOrderDetailsForErp = this.getRmsOrderDetailsForErp(orderIds);
			Map<String, Object> mapData = new HashMap<>();
			
			List<Attachments> allAttachmentsByOrderId = rmsAttachmentsRepo.getAllAttachmentsByOrderId(orderId);
			if(allAttachmentsByOrderId!=null && !allAttachmentsByOrderId.isEmpty()) {
				for(Attachments attach:allAttachmentsByOrderId) {
                    mapData.put(attach.getAttachName(), new FileDataSource(getDIR_PATH_DOCS()+attach.getAttachUrl()));
				}
			}
			
			List<Object> pdfFileNames = new ArrayList<Object>(mapData.values());
            List<String> fileNames = new ArrayList<>();
            for(Object fileName : pdfFileNames) {
            	  if (fileName instanceof String) {
                      fileNames.add(getDIR_PATH_DOCS() + fileName.toString());
                  } else if (fileName instanceof javax.activation.FileDataSource) {
                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
                      fileNames.add(getDIR_PATH_DOCS() + fileDataSource.getName());
                  } else {
                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
                  }
            }
			GenericApiResponse generatePdf = this.generatePdf(rmsOrderDetailsForErp, fileNames);
			generatePdf.getData();
			apiResponse.setData(generatePdf.getData());	
			apiResponse.setMessage("PDF Generated Successfully");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		};
		return apiResponse;
	}
	
	public GenericApiResponse generatePdf (Map<String, ErpClassifieds> erpClassifiedsMap,List<String> fileNames)
			throws DocumentException, IOException, java.io.IOException{
		GenericApiResponse apiResponse = new GenericApiResponse();
		Document document = new Document(PageSize.A4);
		LineSeparator line = new LineSeparator();
		erpClassifiedsMap.entrySet().forEach(erpData -> {

			try {
				String filePath = getDIR_PATH_PDF_DOCS() + erpData.getValue().getAdId() + ".pdf";
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream( getDIR_PATH_PDF_DOCS()+erpData.getValue().getAdId()+".pdf"));
				document.open();

				// Creating a table of the columns
				com.itextpdf.text.Font fontHeader = new com.itextpdf.text.Font();// text font
				fontHeader.setSize(11);
				com.itextpdf.text.Font fontSubHeader = new com.itextpdf.text.Font();
				fontSubHeader.setSize(10);
				com.itextpdf.text.Font fontColumn = new com.itextpdf.text.Font();
				fontColumn.setSize(8);

				line.setLineColor(BaseColor.BLACK);



				PdfPTable table = new PdfPTable(3);
				table.setWidthPercentage(100);
				PdfPCell cell1 = new PdfPCell(new com.itextpdf.text.Paragraph("JAGATI PUBLICATIONS LIMITED"));
				cell1.setBorder(0);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				Image image1 ;
				
					image1 = Image
							.getInstance(new URL("https://pre-prod-asp.s3.ap-south-1.amazonaws.com/static_assets/u3.png"));
					image1.scaleAbsolute(80, 40);
//		            image1.scalePercent(20);
				
					PdfPCell cell2 = new PdfPCell(image1);
					cell2.setBorder(0);
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				

				PdfPCell cell3 = new PdfPCell(new com.itextpdf.text.Paragraph("Casual Insertion Order"));
				cell3.setBorder(0);
				cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);

				document.add(table);

				PdfPTable child = new PdfPTable(2);
				child.setWidthPercentage(100);
				Paragraph address = new Paragraph(erpData.getValue().getOfficeAddress(), fontColumn);
				PdfPCell child1 = new PdfPCell(address);
				child1.setBorder(0);
				child1.setHorizontalAlignment(Element.ALIGN_LEFT);
				

				Paragraph id = new Paragraph("HSN/SAC Code :" + erpData.getValue().getAdId(), fontColumn);
//				PdfPCell child2 = new PdfPCell(new com.itextpdf.text.Paragraph("HSN/SAC Code : SCA241234"));
				PdfPCell child2 = new PdfPCell(id);
				child1.setBorder(0);
				child2.setBorder(0);
				child2.setHorizontalAlignment(Element.ALIGN_RIGHT);

				child.addCell(child1);
				child.addCell(child2);
				document.add(child);

				PdfPTable childG = new PdfPTable(1);
				childG.setWidthPercentage(100);
				Paragraph gstn = new Paragraph("GSTIN : "+ erpData.getValue().getGstIn(), fontColumn);
				PdfPCell child3 = new PdfPCell(gstn);
//				PdfPCell child3 = new PdfPCell(new com.itextpdf.text.Paragraph("GSTIN : 123456"));
				child3.setBorder(0);
				child3.setHorizontalAlignment(Element.ALIGN_LEFT);

				Paragraph mobile = new Paragraph("Ph : "+ erpData.getValue().getPhoneNumber(),fontColumn);
				PdfPCell child4 = new PdfPCell(mobile);
//				PdfPCell child4 = new PdfPCell(new com.itextpdf.text.Paragraph("Ph : 9809098909"));
				child4.setBorder(0);
				child4.setHorizontalAlignment(Element.ALIGN_LEFT);
				childG.addCell(child3);
				childG.addCell(child4);
				document.add(childG);
				document.add(new Paragraph(" "));
				document.add(new Chunk(line));
				PdfPTable table1 = new PdfPTable(4);
				table1.addCell(createCell("Client Code", BaseColor.WHITE, fontSubHeader, false, false));
				table1.addCell(
						createCell(erpData.getValue().getClientCode(), BaseColor.WHITE, fontSubHeader, false, false));
				table1.addCell(createCell("City", BaseColor.WHITE, fontSubHeader, false, false));
				table1.addCell(
						createCell(erpData.getValue().getCityDesc(), BaseColor.WHITE, fontSubHeader, false, false));
				table1.setWidthPercentage(100);
				document.add(table1);
				PdfPTable table2 = new PdfPTable(4);
				table2.addCell(createCell("Order No", BaseColor.WHITE, fontSubHeader, false, false));
				table2.addCell(createCell(erpData.getValue().getAdId(), BaseColor.WHITE, fontSubHeader, false, false));
				table2.addCell(createCell("State", BaseColor.WHITE, fontSubHeader, false, false));
				table2.addCell(
						createCell(erpData.getValue().getStateDesc(), BaseColor.WHITE, fontSubHeader, false, false));
				table2.setWidthPercentage(100);
				document.add(table2);

				PdfPTable table3 = new PdfPTable(4);
				table3.addCell(createCell("Client Name ", BaseColor.WHITE, fontSubHeader, false, false));
				table3.addCell(
						createCell(erpData.getValue().getCustomerName(), BaseColor.WHITE, fontSubHeader, false, false));
				table3.addCell(createCell("Generated by", BaseColor.WHITE, fontSubHeader, false, false));
				table3.addCell(createCell("Others", BaseColor.WHITE, fontSubHeader, false, false));
				table3.setWidthPercentage(100);
				document.add(table3);

				PdfPTable table4 = new PdfPTable(4);
				table4.addCell(createCell("Date", BaseColor.WHITE, fontSubHeader, false, false));
				table4.addCell(
						createCell(erpData.getValue().getCreatedTs(), BaseColor.WHITE, fontSubHeader, false, false));
				table4.addCell(createCell("Phone", BaseColor.WHITE, fontSubHeader, false, false));
				table4.addCell(
						createCell(erpData.getValue().getMobileNo(), BaseColor.WHITE, fontSubHeader, false, false));
				table4.setWidthPercentage(100);
				document.add(table4);

				PdfPTable table5 = new PdfPTable(4);
				table5.addCell(createCell("Street/House No/Locality", BaseColor.WHITE, fontSubHeader, false, false));
				table5.addCell(
						createCell(erpData.getValue().getHouseNo(), BaseColor.WHITE, fontSubHeader, false, false));
				table5.addCell(createCell("Client GSTN", BaseColor.WHITE, fontSubHeader, false, false));
				table5.addCell(createCell(erpData.getValue().getGstNo(), BaseColor.WHITE, fontSubHeader, false, false));
				table5.setWidthPercentage(100);
				document.add(table5);

				PdfPTable table6 = new PdfPTable(4);
				table6.addCell(createCell("Employee / HR Code", BaseColor.WHITE, fontSubHeader, false, false));
				table6.addCell(
						createCell(erpData.getValue().getEmpCode(), BaseColor.WHITE, fontSubHeader, false, false));
				table6.addCell(createCell("Advertiesement Caption", BaseColor.WHITE, fontSubHeader, false, false));
				table6.addCell(
						createCell(erpData.getValue().getCaption(), BaseColor.WHITE, fontSubHeader, false, false));
				table6.setWidthPercentage(100);
				document.add(table6);

				PdfPTable table7 = new PdfPTable(4);
				table7.addCell(createCell("PinCode", BaseColor.WHITE, fontSubHeader, false, false));
				table7.addCell(
						createCell(erpData.getValue().getPinCode(), BaseColor.WHITE, fontSubHeader, false, false));
				table7.addCell(createCell("No of Insertions", BaseColor.WHITE, fontSubHeader, false, false));
				table7.addCell(createCell(erpData.getValue().getNoOfInsertions().toString(), BaseColor.WHITE,
						fontSubHeader, false, false));
				table7.setWidthPercentage(100);
				document.add(table7);

				PdfPTable table8 = new PdfPTable(4);
				table8.addCell(createCell("Employee", BaseColor.WHITE, fontSubHeader, false, false));
				table8.addCell(
						createCell(erpData.getValue().getCustomerName(), BaseColor.WHITE, fontSubHeader, false, false));
				table8.addCell(createCell("Scheme", BaseColor.WHITE, fontSubHeader, false, false));
				table8.addCell(
						createCell(erpData.getValue().getSchemeStr(), BaseColor.WHITE, fontSubHeader, false, false));
				table8.setWidthPercentage(100);
				document.add(table8);

				document.add(new Paragraph(" "));

				PdfPTable tableF = new PdfPTable(8);
				BaseColor silverColor = new BaseColor(192, 192, 192);
				tableF.addCell(createCell("Date of Insertion", silverColor, fontSubHeader, false, false));
				tableF.addCell(createCell("District/Edition", silverColor, fontSubHeader, false, false));
				tableF.addCell(createCell("Size Width(W)", silverColor, fontSubHeader, false, false));
				tableF.addCell(createCell("Size Height(H)", silverColor, fontSubHeader, false, false));
				tableF.addCell(createCell("Space(WxH)", silverColor, fontSubHeader, false, false));
				tableF.addCell(createCell("Card Rate", silverColor, fontSubHeader, false, false));
				tableF.addCell(createCell("Position", silverColor, fontSubHeader, false, false));
				tableF.addCell(createCell("Total Amount", silverColor, fontSubHeader, false, false));
				tableF.setWidthPercentage(100);
				document.add(tableF);



				PdfPTable tableG = new PdfPTable(8);
				tableG.setWidthPercentage(100);
				tableG.setWidths(new float[]{2, 2, 2, 2, 2, 2, 2, 2}); // Column widths

				// Get list of publish dates from erpData
				List<String> publishDates = erpData.getValue().getPublishDates();  // Assuming this is a list of publish dates

				// Loop through each publish date and create a row
				for (String publishDate : publishDates) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				    // Add Date of Insertion (Publish Date)
					Date date = new SimpleDateFormat("yyyyMMdd").parse(publishDate);
					String formattedDate = dateFormat.format(date);
				    tableG.addCell(createCell(formattedDate, BaseColor.WHITE, fontColumn, false, false));

				    // Add other fields that are the same for each row (e.g., District/Edition, Size, etc.)
				    List<String> editions = erpData.getValue().getEditions();
				    String editionsString = String.join(", ", editions);
				    tableG.addCell(createCell(editionsString, BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(Double.toString(erpData.getValue().getSizeWidth()), BaseColor.WHITE, fontColumn, false, false));
				    tableG.addCell(createCell(Double.toString(erpData.getValue().getSizeHeight()), BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(Double.toString(erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight()), BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

				    tableG.addCell(createCell(erpData.getValue().getPageNumberDesc(), BaseColor.WHITE, fontColumn, false, false));  // Position (null or can be adjusted later)

				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
				}
				document.add(tableG);
				PdfPTable totalAmount = new PdfPTable(8);
				PdfPCell totalLabelCell = createCell("Total", BaseColor.WHITE, fontSubHeader, false, false);
				totalLabelCell.setColspan(7);
				totalAmount.addCell(totalLabelCell);
				totalAmount.addCell(createCell(formatToIndianCurrency(erpData.getValue().getGrandTotal()), BaseColor.WHITE,
						fontSubHeader, false, true, Element.ALIGN_RIGHT));
				totalAmount.setWidthPercentage(100);
				document.add(totalAmount);

				document.add(new Paragraph(" "));


				  PdfPTable discount = new PdfPTable(4); // 3 columns
		            discount.setWidthPercentage(100);
		            discount.setWidths(new float[]{3, 2, 2 , 2 }); // Column widths

		            discount.addCell(createCell("Name of Discount", silverColor, fontSubHeader, false, false));
		            discount.addCell(createCell("Category type", silverColor, fontSubHeader, false, false));
		            discount.addCell(createCell("Discount (%)", silverColor, fontSubHeader, false, false));
		            discount.addCell(createCell("Discount Value", silverColor, fontSubHeader, false, false));
//		            discount.addCell(createCell("Balance after discount (to be hide only for calculation)", silverColor, fontSubHeader, false, false));

		            document.add(discount);
		            PdfPTable discountValuesTable = new PdfPTable(4); // 3 columns
		            discountValuesTable.setWidthPercentage(100);
		            discountValuesTable.setWidths(new float[]{3, 2,2,2});
		            List<RmsDiscountModel> discounts = erpData.getValue().getDiscounts();
		            List<Double> amountAfterDisc = new ArrayList<Double>();
		            for(RmsDiscountModel disc:discounts) {
		            	if (disc.getType() != null && !disc.getType().isEmpty()) {
		            		String formattedAmount = String.format("%.2f", disc.getAmount());
		            		Double discoun = disc.getAmount() - (disc.getAmount() * disc.getPercent()/100);
		            		String formattedAmo = String.format("%.2f", discoun);
		            		amountAfterDisc.add(discoun);
		                    discountValuesTable.addCell(createCell(disc.getType(), BaseColor.WHITE, fontColumn, false, false));
		                    discountValuesTable.addCell(createCell(disc.getCategoryType(), BaseColor.WHITE, fontColumn, false, false));
		                    discountValuesTable.addCell(createCell(disc.getPercent() + "", BaseColor.WHITE, fontColumn, false, false));
		                    discountValuesTable.addCell(createCell(disc.getAmountt() != null ? disc.getAmountt() : "", BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
//		                    discountValuesTable.addCell(createCell(formatToIndianCurrency(discoun), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		                }
		            }
		            document.add(discountValuesTable);
		            Double amt = 0.0;
		            for(Double amount : amountAfterDisc) {
		            	amt = amt + amount;
		            }
		            
		            PdfPTable totalAmountAfterDisc = new PdfPTable(4); // Ensure it has 5 columns like the previous table
		            totalAmountAfterDisc.setWidths(new float[]{3, 2, 2, 2}); // Match column widths
		            totalAmountAfterDisc.setWidthPercentage(100); // Match width percentage

		            // Create and add the "Total" label cell
		            PdfPCell totalLabelCellDisc = createCell("Total", BaseColor.WHITE, fontSubHeader, false, false);
		            totalLabelCellDisc.setColspan(3); // Span across the first 4 columns
		            totalLabelCellDisc.setHorizontalAlignment(Element.ALIGN_RIGHT);
		            totalAmountAfterDisc.addCell(totalLabelCellDisc);

		            // Add the total amount cell
		            
//		            discountValuesTable.addCell(createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
		            PdfPCell totalAmountCell = createCell(formatToIndianCurrency(amt), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT);
		            totalAmountAfterDisc.addCell(totalAmountCell);

		            // Add the total table to the document
		            document.add(totalAmountAfterDisc);

					document.add(new Paragraph(" "));


				document.add(new Paragraph(" "));

				Paragraph mainContent = new Paragraph(
						"Advertisement may be released as per the above details. Subject to your Terms & Conditions mentioned in the Rate Card.");
				document.add(mainContent);

				document.add(new Chunk(line));

				Paragraph signatureLines = new Paragraph("*All Advertisements are accepted on advance payment only");
				document.add(signatureLines);

				document.add(new Chunk(line));
				Paragraph remarks = new Paragraph("Remarks.");
				document.add(remarks);
				document.add(new Chunk(line));
				document.add(new Paragraph(" "));

				PdfPTable sig = new PdfPTable(2);
				sig.setWidthPercentage(100);
//				PdfPCell sig1 = new PdfPCell(new com.itextpdf.text.Paragraph("(E-Signature of the employee)"));
//				sig1.setBorder(0);
//				sig1.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell sigA1 = new PdfPCell(new com.itextpdf.text.Paragraph("(E-Signature of the client)"));
				sigA1.setBorder(0);
				sigA1.setHorizontalAlignment(Element.ALIGN_RIGHT);

//				sig.addCell(sig1);
				sig.addCell(sigA1);
				document.add(sig);

				document.add(new Paragraph(" "));
				PdfPTable sigAdvt = new PdfPTable(2);
				sigAdvt.setWidthPercentage(100);
				PdfPCell sigAdvt1 = new PdfPCell(new com.itextpdf.text.Paragraph("Advt.Rep."));
				sigAdvt1.setBorder(0);
				sigAdvt1.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell sigAdv = new PdfPCell(new com.itextpdf.text.Paragraph("Signature of the Advertiser"));
				sigAdv.setBorder(0);
				sigAdv.setHorizontalAlignment(Element.ALIGN_RIGHT);

				sigAdvt.addCell(sigAdvt1);
				sigAdvt.addCell(sigAdv);
				document.add(sigAdvt);
				document.add(new Paragraph(" "));
				if(fileNames != null && !fileNames.isEmpty()) {
					for(String fileName:fileNames) {
					    if (fileName.toLowerCase().endsWith(".jpg") || 
						        fileName.toLowerCase().endsWith(".jpeg") || 
						        fileName.toLowerCase().endsWith(".png")) {
						        try {
						            Image image = Image.getInstance(fileName);
						            image.scaleAbsolute(200, 100);
						            document.add(image);
						        } catch (Exception e) {
						            System.out.println("Error processing image file: " + fileName + " - " + e.getMessage());
						        }
						    }
						    else {
						        System.out.println("Unsupported file type: " + fileName);
						    }
						}
				}				
				document.add(new Paragraph(" "));
				document.close();
					DocumentsModel documentsModel = new DocumentsModel();
					File pdfFile = new File(filePath);
			        FileInputStream inputStream = new FileInputStream(pdfFile);
			        MultipartFile multipartFile = new MockMultipartFile(
			            pdfFile.getName(),                // Name of the file
			            pdfFile.getName(),                // Original file name
			            "application/pdf",                // MIME type
			            inputStream                       // Input stream
			        );
			        List<MultipartFile> multipartFileAttachments = new ArrayList<>();
			        multipartFileAttachments.add(multipartFile);
					documentsModel.setMultipartFileAtachments(multipartFileAttachments);
					documentsModel.setOrderId(erpData.getValue().getOrderId());
					documentsModel.setLoggedUser(userContext.getLoggedUser());
					GenericApiResponse uploadAdditionalDocuments = documentService.uploadAdditionalDocuments(documentsModel);
					apiResponse.setStatus(0);
					apiResponse.setData(uploadAdditionalDocuments);					
				
				
				System.out.println("PDF generated successfully.");
				
			} catch (java.io.IOException | DocumentException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		return apiResponse;
	}

	@Override
	public GenericApiResponse getCustomerDetailsByMobileNo(String mobileNo) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			List<UmCustomers> customerDetails = rmsUmCustomersRepo.getCustomerDetails(mobileNo);
			if(customerDetails != null && !customerDetails.isEmpty()) {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage(prop.getProperty("USR_020"));
			}else {
				genericApiResponse.setStatus(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse isMobileNumberDuplicate(String mobileNo) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<UmCustomers> customerDetails = new ArrayList<UmCustomers>();
		try {
			if (mobileNo != null) {
				customerDetails = rmsUmCustomersRepo.getCustomerDetailsOnMoblieNo(mobileNo);
				if (customerDetails.isEmpty()) {
					customerDetails = rmsUmCustomersRepo.getCustomerDetails(mobileNo);
					if (customerDetails.isEmpty()) {
						genericApiResponse.setMessage("No customer found with this mobile number.");
						genericApiResponse.setStatus(0);
					} else {
						genericApiResponse.setMessage(
								"The mobile number " + mobileNo + " is already associated with the customer "
										+ customerDetails.get(0).getCustomerName() + ".");
						genericApiResponse.setStatus(1);
					}
				} else {
					genericApiResponse
							.setMessage("The mobile number " + mobileNo + " is already associated with the customer "
									+ customerDetails.get(0).getCustomerName() + ".");
					genericApiResponse.setStatus(1);
				}
			} else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("Mobile No is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse validateAccessKey(WfRequest payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			if(payload != null && payload.getAccessKey() != null) {
				WfInboxMasterDetails wfInboxMasterDetails = wfInboxMasterDetailsRepo.validateInboxAccessKey(payload.getAccessKey());
				if(wfInboxMasterDetails != null) {
					apiResponse.setData(wfInboxMasterDetails);
					apiResponse.setStatus(0);
				}else {
					apiResponse.setStatus(1);
					apiResponse.setMessage("Action has already been taken against the order.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public Map<String, ErpClassifieds> getRmsOrderDetailsForErp(ApprovalDetailsModel detailsModel) {
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		Map<String, ErpClassifieds> classifiedsMap = new HashMap<>();
		List<String> itemIds = new ArrayList<String>();
		List<String> customerIds = new ArrayList<String>();
		List<Integer> createdByIds = new ArrayList<Integer>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		try {
			String joinedOrderIds = String.join("','", detailsModel.getOrderId());
			
			String query="select itm.item_id, itm.order_id, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme, itm.classified_ads_sub_type, gcast.ads_sub_type, itm.status, itm.ad_id, itm.category_group, itm.sub_group, itm.child_group, gcg.classified_group, gcsg.classified_sub_group, gcc.classified_child_group, roi.size_width, roi.size_height, roi.page_position, grpd.positioning_type, roi.format_type, grft.format_type as grft_format_type, roi.page_number, grpp.page_name, roi.category_discount, roi.multi_discount, roi.additional_discount, roi.multi_discount_amount, roi.additional_discount_amount, roi.category_discount_amount, roi.base_amount, roi.grand_total, roi.discount_total, roi.gst_total, roi.premium_discount, roi.premium_discount_amount, roi.ad_type, grat.add_type, roi.caption, roi.igst_amount, roi.sgst_amount, roi.cgst_amount, co.booking_unit, bu.booking_location, co.edition_type, gret.edition_type as gret_edition_type, uc.client_code, uc.gst_no, uc.customer_name, uc.email_id, uc.mobile_no, uc.address_1, uc.pin_code, uc.state, uc.city, uc.customer_type_id, uc.mobile_alt, uc.house_no, bu.booking_description, gs.state as gs_state, gpm.payment_method, gpm2.payment_mode, rpr.bank_or_upi, rpr.cash_receipt_no,itm.created_by,co.customer_id,bu.booking_location as city_name,itm.created_by as creater_name,itm.created_ts,gcs.erp_ref_id as gcs_erp_ref_id,gcast.erp_ref_id as gcast_erp_ref_id,gcc.erp_ref_id as gcc_erp_ref_id,grpd.erp_ref_id as grpd_erp_ref_id,grpp.erp_ref_code as grpp_erp_ref_code,roi.fixed_format,grff.erp_ref_code as grff_erp_ref_code,co.user_type_id,bu.sales_office,bu.sold_to_party,uc.aadhar_number,uc.pan_number,itm.classified_ads_type,gcat.ads_type as gcat_ads_type,gcat.erp_ref_id as gcat_erp_ref_id,roi.multi_discount_edi_count,grbu.office_address,grbu.gst_in,grbu.phone_no,roi.no_of_insertions,roi.master_premium_per,roi.premium_amount,roi.billable_days,roi.aggred_premium_dis_per,grbu.scheduling_mail,roi.card_rate from clf_order_items itm inner join clf_orders co on itm.order_id = co.order_id inner join rms_order_items roi on itm.item_id = roi.item_id inner join gd_rms_schemes gcs on itm.scheme = gcs.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_classified_group gcg on itm.category_group = gcg.id inner join gd_classified_sub_group gcsg on itm.sub_group = gcsg.id inner join gd_classified_child_group gcc on itm.child_group = gcc.id left join gd_rms_positioning_discount grpd on roi.page_position = grpd.id left join gd_rms_format_types grft on roi.format_type = grft.id left join gd_rms_page_positions grpp on roi.page_number = grpp.id inner join gd_rms_ads_type grat on roi.ad_type = grat.id inner join booking_units bu on co.booking_unit = bu.booking_code inner join gd_rms_edition_type gret on co.edition_type = gret.id left join um_customers uc on co.customer_id = uc.customer_id left join gd_state gs ON uc.state = gs.state_code inner join rms_payments_response rpr on rpr.item_id = itm.item_id left join gd_payment_method gpm on gpm.id = rpr.payment_method left join gd_payment_mode gpm2 on gpm2.id = rpr.payment_mode inner join gd_rms_fixed_formats grff on roi.fixed_format = grff.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id left join gd_rms_booking_units grbu on grbu.booking_unit = co.booking_unit where co.order_type = 1 and co.order_status = 'CLOSED' and itm.mark_as_delete = false and co.mark_as_delete = false and itm.order_id in ('"+joinedOrderIds+ "')";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			Double additionalDiscount=0.0;
			Double additionalDiscountAmount=0.0;
			Double multiDiscount=0.0;
			Double multiDiscountAmount=0.0;
			Double categoryDiscount=0.0;
			Double categoryDiscountAmount=0.0;
			Double premiumDiscount=0.0;
			Double igstAmount=0.0;
			Double sgstAmount=0.0;
			Double cgstAmount=0.0;
			Double premiumDiscountAmount=0.0;
			Double premiumAmount =0.0;
			Double masterPremiumPer = 0.0;
//			for (Object[] obj : classifiedList) {
			if(!classifiedList.isEmpty()) {
				 Object[] obj = classifiedList.get(0);
				ErpClassifieds classified = new ErpClassifieds();
				classified.setDiscounts(new ArrayList<RmsDiscountModel>());
				classified.setTax(new ArrayList<RmsTaxModel>());
				classified.setPremium(new ArrayList<RmsPremiumModel>());
//				classified.setEditions(new ArrayList<String>());
//				classified.setEditionIds(new ArrayList<Integer>());
//				classified.setPublishDates(new ArrayList<String>());
//				insertionObjectDisplay.setDiscounts(new ArrayList<Integer>());
//				classified.setDiscountsDesc(new ArrayList<String>());
				classified.setItemId((String) obj[0]);
				classified.setOrderId((String) obj[1]);
				classified.setScheme((Integer) obj[2]);
				classified.setSchemeStr((String) obj[3]);
				classified.setClassifiedAdsSubType((Integer) obj[4]);
				classified.setAdsSubType((String) obj[5]);
				classified.setContentStatus((String) obj[6]);
				classified.setAdId((String) obj[7]);
				classified.setGroup((Integer) obj[8]);
				classified.setSubGroup((Integer) obj[9]);
				classified.setChildGroup((Integer) obj[10]);
				classified.setGroupStr((String) obj[11]);
				classified.setSubGroupStr((String) obj[12]);
				classified.setChildGroupStr((String) obj[13]);
				
				
				float sizeWidth = (obj[14] == null) ? 0.0f : (float) obj[14];
				float sizeHeight = (obj[15] == null) ? 0.0f : (float) obj[15];
				double formattedSizeWidth = HelperUtil.parseDoubleValue((sizeWidth));
				classified.setSizeWidth(formattedSizeWidth);
				String sizeW = String.valueOf(formattedSizeWidth);
				double formattedSizeHeight = HelperUtil.parseDoubleValue((sizeHeight));
				classified.setSizeHeight(formattedSizeHeight);
				String sizeH = String.valueOf(formattedSizeHeight);
				classified.setSizeWidthD(sizeW);
				classified.setSizeHeightD(sizeH);
				
				classified.setPagePosition((Integer) obj[16]);
				classified.setPositioningDesc((String) obj[17]);
				classified.setFormatType((Integer) obj[18]);
				classified.setFormatTypeDesc((String) obj[19]);						
				classified.setPageNumber((Integer) obj[20]);
				classified.setPageNumberDesc((String) obj[21]);
				
				categoryDiscount = ((Float) obj[22]) != null?((Float) obj[22]).doubleValue():null;
				multiDiscount = ((Float) obj[23]) != null?((Float) obj[23]).doubleValue():null;
				additionalDiscount = ((Float) obj[24]) != null?((Float) obj[24]).doubleValue():null;
				
				multiDiscountAmount = ((Float) obj[25]) != null?((Float) obj[25]).doubleValue():null;
				additionalDiscountAmount = ((Float) obj[26]) != null?((Float) obj[26]).doubleValue():null;
				categoryDiscountAmount = ((Float) obj[27]) != null?((Float) obj[27]).doubleValue():null;
				
				
				classified.setCategoryDiscount(categoryDiscount != null ? (String.valueOf(categoryDiscount)) : null);
				classified.setMultiDiscount(multiDiscount != null ? (String.valueOf(multiDiscount)) : null);
				classified.setAdditionalDiscount(additionalDiscount != null ? (String.valueOf(additionalDiscount)) : null);
				classified.setMultiDiscountAmount(multiDiscountAmount != null ? (String.valueOf(multiDiscountAmount)) : null);
				classified.setAdditionalDiscountAmount(additionalDiscountAmount != null ? (String.valueOf(additionalDiscountAmount)) : null);
				classified.setCategoryDiscountAmount(categoryDiscountAmount != null ? (String.valueOf(categoryDiscountAmount)) : null);
				
				classified.setAmount(((Float) obj[28]) != null ? ((Float) obj[28]).doubleValue() : 0.0);
				classified.setGrandTotal(((Float) obj[29]) != null ? ((Float) obj[29]).doubleValue() : 0.0);
				classified.setDiscountTotal(((Float) obj[30]) != null ? ((Float) obj[30]).doubleValue() : 0.0);
				classified.setGstTotal(((Float) obj[31]) != null ? ((Float) obj[31]).doubleValue() : 0.0);
				
				classified.setAmountString(String.format("%.2f", classified.getAmount()));
				classified.setGrandTotalString(String.format("%.2f", classified.getGrandTotal()));
				classified.setDiscountTotalString(String.format("%.2f", classified.getDiscountTotal()));
				classified.setGstTotalString(String.format("%.2f", classified.getGstTotal()));
										
				premiumDiscount = ((Float) obj[32]) != null?((Float) obj[32]).doubleValue():null;
				premiumDiscountAmount = ((Float) obj[33]) != null?((Float) obj[33]).doubleValue():null;
				
				classified.setPremiumDiscount(premiumDiscount != null ? (String.valueOf(premiumDiscount)) : null);
				classified.setPremiumDiscountAmount(premiumDiscountAmount != null ? (String.valueOf(premiumDiscountAmount)) : null);
				
				classified.setAddType((Integer) obj[34]);
				classified.setAddTypeDesc((String) obj[35]);
				classified.setCaption((String) obj[36]);
				
				igstAmount = ((Float) obj[37]) != null?((Float) obj[37]).doubleValue():null;
				sgstAmount = ((Float) obj[38]) != null?((Float) obj[38]).doubleValue():null;
				cgstAmount = ((Float) obj[39]) != null?((Float) obj[39]).doubleValue():null;
				
				if (igstAmount != null) {
					igstAmount = Double.valueOf(df1.format(igstAmount));
					BigDecimal igstAmt = BigDecimal.valueOf(igstAmount);
					igstAmt = igstAmt.setScale(2, RoundingMode.HALF_UP);
					igstAmount = igstAmt.doubleValue();
				}
				
				if (sgstAmount != null) {
					sgstAmount = Double.valueOf(df1.format(sgstAmount));
					BigDecimal sgstAmt = BigDecimal.valueOf(sgstAmount);
					sgstAmt = sgstAmt.setScale(2, RoundingMode.HALF_UP);
					sgstAmount = sgstAmount.doubleValue();
				}
				if (cgstAmount != null) {
					cgstAmount = Double.valueOf(df1.format(cgstAmount));
					BigDecimal cgstAmt = BigDecimal.valueOf(cgstAmount);
					cgstAmt = cgstAmt.setScale(2, RoundingMode.HALF_UP);
					cgstAmount = cgstAmount.doubleValue();
				}
				classified.setBookingCode((Integer) obj[40]);
				classified.setCityDesc((String) obj[41]);
				classified.setEditionType((Integer) obj[42]);
				classified.setEditionTypeDesc((String) obj[43]);
				classified.setClientCode((String) obj[44]);
				if(classified.getClientCode() != null && classified.getClientCode().startsWith("AP-01") || classified.getClientCode().startsWith("TG-36")) {
					classified.setIsCustomerNew("New");
				}else {
					classified.setIsCustomerNew("");
				}
				classified.setGstNo((String) obj[45]);
				classified.setClientName((String) obj[46]);
				classified.setEmailId((String) obj[47]);
				classified.setMobileNo((String) obj[48]);
				classified.setAddress1((String) obj[49]);
				classified.setPinCode((String) obj[50]);
				if(!((String) obj[48]).equalsIgnoreCase((String) obj[54])) {
					classified.setMobileAlt((String) obj[54]);
				}
				classified.setStateCode((String) obj[51]);
				classified.setHouseNo((String) obj[55]);
				classified.setBookingDescription((String) obj[56]);
				classified.setStateDesc((String) obj[57]);
				classified.setPaymentMethodDesc((String) obj[58]);
				classified.setPaymentModeDesc((String) obj[59]);
				classified.setBankOrUpi((String) obj[60]);
				classified.setCashReceiptNo((String) obj[61]);;
				classified.setCustomerId((String) obj[63]);
				classified.setCreatedBy((Integer) obj[65]);
				Timestamp timestamp = (Timestamp) obj[66]; // Assuming obj[66] contains the Timestamp
				if (timestamp != null) {
					classified.setCreatedTs(CommonUtils.dateFormatter((Date) obj[66], "yyyy-MM-dd HH:mm"));
					classified.setCreatedDate(CommonUtils.dateFormatter((Date) obj[66], "yyyyMMdd"));
					classified.setBookingDate(CommonUtils.dateFormatter((Date) obj[66], "yyyy-MM-dd HH:mm:ss"));
//				    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//				    String formattedDate = dateFormat.format(timestamp);
//				    classified.setCreatedTs(formattedDate);
				} else {
				    classified.setCreatedTs(null); // Handle null case if required
				}
				classified.setOfficeAddress((String) obj[83]);
				classified.setGstIn((String) obj[84]);
				classified.setPhoneNumber((String) obj[85]);
				classified.setNoOfInsertions((Integer) obj[86]);;
				classified.setSchemeErpRefId((String) obj[67]);
				classified.setAdsSubTypeErpRefId((String) obj[68]);
				classified.setChildGroupErpRefId((String) obj[69]);
				classified.setPagePositionErpRefId((String) obj[70]);
				classified.setPageNumberErpRefId((String) obj[71]);
				classified.setFixedFormatErpRefId((String) obj[73]);
				classified.setUserTypeId((Integer) obj[74]);
				classified.setSalesOffice((String) obj[75]);
				classified.setSoldToParty((String) obj[76]);
				classified.setAadharNumber((String) obj[77]);
				classified.setPanNumber((String) obj[78]);
				classified.setAdsType((String) obj[80]);
				classified.setAdsTypeErpRefId((String) obj[81]);
				classified.setMultiDiscountEdiCount((Integer) obj[82]);
				classified.setAdTypeErpRefId((String) obj[83]);
				classified.setPaidAmount((Float) obj[29] != null?((Float) obj[29]).doubleValue():null);
				classified.setKeyword("Rms Order");
				classified.setTypeOfCustomer("01");
				classified.setCreatedTime(CommonUtils.dateFormatter((Date) obj[66], "HHmmss"));
				classified.setOrderIdentification("01");
				
				masterPremiumPer = ((Float) obj[87]) != null?((Float) obj[87]).doubleValue():null;
				premiumAmount = ((Float) obj[88]) != null?((Float) obj[88]).doubleValue():null;
				if (premiumAmount != null) {
					premiumAmount = Double.valueOf(df1.format(premiumAmount));
					BigDecimal premiumDiscountAmt = BigDecimal.valueOf(premiumAmount);
					premiumDiscountAmt = premiumDiscountAmt.setScale(2, RoundingMode.HALF_UP);
					premiumAmount = premiumDiscountAmt.doubleValue();
				}
				
				classified.setPremiumTotal(premiumAmount);
				Integer billableDays = obj[89] != null ? (Integer) obj[89] :1;
				Double withSchemeTotalAmt = classified.getAmount() * billableDays;
				classified.setWithSchemeTotalAmount(withSchemeTotalAmt);
				classified.setAggredPremiumDisPer(((Float) obj[90]).doubleValue());
				classified.setSchedulingMail((String) obj[91]);
				classified.setCardRate(((Float) obj[92]).doubleValue());

				if(additionalDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Additional Discount");
					discountModel.setAmount(additionalDiscountAmount);
					discountModel.setAmountt(formatToIndianCurrency(additionalDiscountAmount));;
					discountModel.setAmountString(String.format("%.2f", additionalDiscountAmount));
					discountModel.setPercent(additionalDiscount);
					discountModel.setPercentString(String.format("%.2f", additionalDiscount));	
					discountModel.setCategoryType((String) obj[17]);
					classified.getDiscounts().add(discountModel);
				}
				if(multiDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Multi Discount");
					discountModel.setAmount(multiDiscountAmount);
					discountModel.setAmountt(formatToIndianCurrency(multiDiscountAmount));
					discountModel.setAmountString(String.format("%.2f", multiDiscountAmount));
					discountModel.setPercent(multiDiscount);
					discountModel.setPercentString(String.format("%.2f", multiDiscount));
					discountModel.setCategoryType((String) obj[17]);
					classified.getDiscounts().add(discountModel);
					
				}
				if(categoryDiscount != null) {
					RmsDiscountModel discountModel=new RmsDiscountModel();
					discountModel.setType("Category Discount");
					discountModel.setAmount(categoryDiscountAmount);
					discountModel.setAmountt(formatToIndianCurrency(categoryDiscountAmount));
					discountModel.setAmountString(String.format("%.2f", categoryDiscountAmount));
					discountModel.setPercent(categoryDiscount);
					discountModel.setPercentString(String.format("%.2f", categoryDiscount));
					discountModel.setCategoryType((String) obj[17]);
					classified.getDiscounts().add(discountModel);
				}
//				if(premiumDiscount != null) {
//					RmsDiscountModel discountModel=new RmsDiscountModel();
//					discountModel.setType("Premium Discount");
//					discountModel.setAmount(premiumDiscountAmount);
////					discountModel.setAmountt(formatToIndianCurrency(premiumDiscountAmount));
//					discountModel.setAmountString(String.format("%.2f", premiumDiscountAmount));
//					discountModel.setPercent(premiumDiscount);
//					discountModel.setPercentString(String.format("%.2f", premiumDiscount));	
//					discountModel.setCategoryType((String) obj[17]);
//					classified.getDiscounts().add(discountModel);
//				}
				if(sgstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("SGST");
					rmsTaxModel.setAmount(sgstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", sgstAmount));
					rmsTaxModel.setPercent(2.5);
					rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
					classified.getTax().add(rmsTaxModel);
				}

				if(igstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("IGST");
					rmsTaxModel.setAmount(igstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
					rmsTaxModel.setPercent(5.0);
					rmsTaxModel.setPercentString(String.format("%.2f", 5.0));
					classified.getTax().add(rmsTaxModel);
				}

				if(cgstAmount != null) {
					RmsTaxModel rmsTaxModel = new RmsTaxModel();
					rmsTaxModel.setType("CGST");
					rmsTaxModel.setAmount(igstAmount);
					rmsTaxModel.setAmountString(String.format("%.2f", igstAmount));
					rmsTaxModel.setPercent(2.5);
					rmsTaxModel.setPercentString(String.format("%.2f", 2.5));
					classified.getTax().add(rmsTaxModel);
				}
				
				if(premiumDiscount != null) {
					RmsPremiumModel rmsPremiumModel = new RmsPremiumModel();
					rmsPremiumModel.setType("Agreed Premium");
					rmsPremiumModel.setAmount(premiumAmount);
					rmsPremiumModel.setPercent(premiumDiscount);
					classified.getPremium().add(rmsPremiumModel);
				}else if(masterPremiumPer != null){
					RmsPremiumModel rmsPremiumModel = new RmsPremiumModel();
					rmsPremiumModel.setType("Premium");
					rmsPremiumModel.setAmount(premiumAmount);
					rmsPremiumModel.setPercent(masterPremiumPer);
					classified.getPremium().add(rmsPremiumModel);
				}
				
				if(classified.getDiscountTotal() != null) {
					classified.setAfterDiscountTotal(classified.getAmount() - classified.getDiscountTotal());
				}
				if(classified.getPremiumTotal() != null && classified.getAfterDiscountTotal() != null) {
					classified.setAfterPremiumTotal(classified.getAfterDiscountTotal() + classified.getPremiumTotal());
				}else {
					if(classified.getPremiumTotal() != null) {
						classified.setAfterPremiumTotal(classified.getAmount() + classified.getPremiumTotal());
					}
				}
//
				itemIds.add((String) obj[0]);
				createdByIds.add((Integer) obj[62]);
				customerIds.add((String) obj[63]);
				classifiedsMap.put((String) obj[0], classified);
//			}

			if (itemIds != null && !itemIds.isEmpty()) {
				List<Object[]> editionsList = clfEditionsRepo.getRmsEditionIdAndNameOnItemId(itemIds);
				for (Object[] clObj : editionsList) {
					if (classifiedsMap.containsKey((String) clObj[0])) {
						if (classifiedsMap.get((String) clObj[0]).getEditions() != null) {
							classifiedsMap.get((String) clObj[0]).getEditions().add((String) clObj[2]);
							classifiedsMap.get((String) clObj[0]).getEditionsErpRefId().add((String) clObj[3]);
						} else {
							List<String> edditions = new ArrayList<>();
							List<String> edditionsErpRefIds = new ArrayList<>();
							edditions.add((String) clObj[2]);
							edditionsErpRefIds.add((String) clObj[3]);
							ErpClassifieds classifieds = classifiedsMap.get((String) clObj[0]);
							classifieds.setEditions(edditions);
							classifieds.setEditionsErpRefId(edditionsErpRefIds);
							classifiedsMap.put((String) clObj[0], classifieds);
						}
					}
				}

				List<Object[]> publishDatesList = clfPublishDatesRepo.getPublishDatesForErpData(itemIds);
				for (Object[] clObj : publishDatesList) {
					if (classifiedsMap.containsKey((String) clObj[0])) {
						if (classifiedsMap.get((String) clObj[0]).getPublishDates() != null) {
							classifiedsMap.get((String) clObj[0]).getPublishDates()
									.add(CommonUtils.dateFormatter((Date) clObj[1], "yyyyMMdd"));
						} else {
							List<String> publishDates = new ArrayList<>();
							publishDates.add(CommonUtils.dateFormatter((Date) clObj[1], "yyyyMMdd"));
							ErpClassifieds classifieds = classifiedsMap.get((String) clObj[0]);
							classifieds.setPublishDates(publishDates);
							classifiedsMap.put((String) clObj[0], classifieds);
						}
					}
				}
				
				List<ApprovalInbox> approvalInboxList = approvalInboxRepo.getPendingInboxForCurrentLevel(itemIds);
				if (approvalInboxList != null && !approvalInboxList.isEmpty()) {
					for (ApprovalInbox approvalInbox : approvalInboxList) {
						ErpClassifieds erpClassifieds = classifiedsMap.get(approvalInbox.getItemId());
						erpClassifieds.setCurrentLevel(approvalInbox.getCurrentLevel());
					}
				}
				WfInboxMaster wfInboxMasterDetailsOnObjectRefId = wfInboxMasterRepo.getWfInboxMasterDetailsOnObjectRefId(itemIds.get(0));
				if(wfInboxMasterDetailsOnObjectRefId != null) {
					ErpClassifieds erpClassifieds = classifiedsMap.get(wfInboxMasterDetailsOnObjectRefId.getObjectRefId());
					List<WfInboxMasterDetails> inboxMasterDetails = wfInboxMasterDetailsRepo.getInboxMasterDetails(wfInboxMasterDetailsOnObjectRefId.getInboxMasterId());
					List<RmsApprovalDetails> approvalDetails = new ArrayList<RmsApprovalDetails>();
					if(inboxMasterDetails != null && !inboxMasterDetails.isEmpty()) {
						for(WfInboxMasterDetails details : inboxMasterDetails) {
							if(details.getStatus().equalsIgnoreCase("PENDING")) {
								erpClassifieds.setCurrentLevel(Integer.parseInt(details.getTargetRef()));
							}
							if(details.getStatus().equalsIgnoreCase("APPROVED") ) {
								RmsApprovalDetails approveModel = new RmsApprovalDetails();
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								if(details.getChangedBy() == null && detailsModel.getAction().equalsIgnoreCase("TAKE_ACTION")) {									
										UmUsers approverEmails = umUsersRepository.getApproverEmails(detailsModel.getUserId());
										approveModel.setApprovedBy(approverEmails.getFirstName() != null ? approverEmails.getFirstName() : "");
										approveModel.setApprovedTime(new Date());
										approveModel.setLevel(Integer.parseInt(details.getTargetRef()));																	
								}else {
									UmUsers approverEmails = umUsersRepository.getApproverEmails(details.getChangedBy());
									approveModel.setApprovedBy(approverEmails.getFirstName() != null ? approverEmails.getFirstName() : "");
									approveModel.setApprovedTime(details.getChangedTs());
									approveModel.setLevel(Integer.parseInt(details.getTargetRef()));
								}		
								approvalDetails.add(approveModel);
							}
							
						}
						erpClassifieds.setApprovalDetails(approvalDetails);
					}
				}
				if (customerIds != null) {
					List<UmCustomers> umCustomersList = umCustomersRepo.getMulCustomerDetails(customerIds);
					List<Integer> cityIds = new ArrayList<Integer>();
					if (!umCustomersList.isEmpty()) {
						classifiedsMap.entrySet().forEach(erpData -> {
							Optional<UmCustomers> umCus = umCustomersList.stream()
									.filter(f -> f.getCustomerId().equals(erpData.getValue().getCustomerId()))
									.findFirst();
							if (umCus.isPresent()) {
								UmCustomers umCustom = umCus.get();
								erpData.getValue().setCustomerName(umCustom.getCustomerName());
								erpData.getValue().setMobileNumber(umCustom.getMobileNo());
								erpData.getValue().setEmailId(umCustom.getEmailId());
								erpData.getValue().setAddress1(umCustom.getAddress1());
								erpData.getValue().setAddress2(umCustom.getAddress2());
								erpData.getValue().setAddress3(umCustom.getAddress3());
								erpData.getValue().setPinCode(umCustom.getPinCode());
								erpData.getValue().setOfficeLocation(umCustom.getOfficeLocation());
								erpData.getValue().setGstNo(umCustom.getGstNo());
								erpData.getValue().setPanNumber(umCustom.getPanNumber());
								erpData.getValue().setAadharNumber(umCustom.getAadharNumber());
								erpData.getValue().setErpRefId(umCustom.getErpRefId());
								erpData.getValue().setState(umCustom.getState());
								erpData.getValue().setCity(umCustom.getCity());
								erpData.getValue().setHouseNo(umCustom.getHouseNo());
								erpData.getValue().setClientCode(umCustom.getClientCode());
								if(!umCustom.getMobileNo().equalsIgnoreCase(umCustom.getMobileAlt())) {
									erpData.getValue().setMobileAlt(umCustom.getMobileAlt());
								}
								if (umCustom != null && !umCustom.getCity().isEmpty()) {
									if(umCustom.getCity() != null && umCustom.getCity().matches("\\d+"))
										cityIds.add(Integer.parseInt(umCustom.getCity()));
									}
							}

						});
					}
				}

				if (!createdByIds.isEmpty()) {
					List<Integer> userTypes = new ArrayList<Integer>();
					List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByIds, false);
					if (!umUsers.isEmpty()) {
						classifiedsMap.entrySet().forEach(erpData -> {
							Optional<UmUsers> gd = umUsers.stream()
									.filter(f -> (f.getUserId()).equals(erpData.getValue().getCreatedBy())).findFirst();
							if (gd.isPresent()) {
								UmUsers umUser = gd.get();
								erpData.getValue().setEmpCode(umUser.getEmpCode());
								erpData.getValue().setCustomerName(umUser.getFirstName());
								if (!"2".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
									erpData.getValue().setSoldToParty(umUser.getSoldToParty());
								}
								if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
									erpData.getValue().setCustomerName(umUser.getFirstName());
									erpData.getValue().setMobileNumber(umUser.getMobile());
									erpData.getValue().setEmailId(umUser.getEmail());
									erpData.getValue().setAddress1(umUser.getAddress());
									erpData.getValue().setState(umUser.getState());
									erpData.getValue().setSoldToParty(umUser.getLogonId());
								}
								if("1".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")){
									erpData.getValue().setCreatedByEmail(umUser.getEmail());
								}
								userTypes.add(umUser.getGdUserTypes().getUserTypeId());
							}
						});
					}

					if (!userTypes.isEmpty()) {
						List<GdUserTypes> gdUserTypes = gdUserTypesRepo.getUserTypesList(userTypes);
						classifiedsMap.entrySet().forEach(erpData -> {
							Optional<GdUserTypes> gd = gdUserTypes.stream()
									.filter(f -> (f.getUserTypeId()).equals(erpData.getValue().getUserTypeId()))
									.findFirst();
							if (gd.isPresent()) {
								GdUserTypes gdUserType = gd.get();
								erpData.getValue().setUserTypeIdErpRefId(gdUserType.getErpRefId());
							}
						});
					}
				}
			}
		} }catch (Exception e) {
			e.printStackTrace();
		}
		return classifiedsMap;
	}
}