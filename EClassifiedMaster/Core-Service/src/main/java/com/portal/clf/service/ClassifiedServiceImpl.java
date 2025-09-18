package com.portal.clf.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.portal.basedao.IBaseDao;
import com.portal.clf.entities.ClfEditions;
import com.portal.clf.entities.ClfOrderItemRates;
import com.portal.clf.entities.ClfOrderItems;
import com.portal.clf.entities.ClfOrders;
import com.portal.clf.entities.ClfPaymentResponseTracking;
import com.portal.clf.entities.ClfPayments;
import com.portal.clf.entities.ClfPublishDates;
import com.portal.clf.entities.ClfRates;
import com.portal.clf.models.AddToCartRequest;
import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.CartDetails;
import com.portal.clf.models.ClassifiedConstants;
import com.portal.clf.models.ClassifiedRateDetails;
import com.portal.clf.models.ClassifiedRates;
import com.portal.clf.models.ClassifiedStatus;
import com.portal.clf.models.ClassifiedTypesModel;
import com.portal.clf.models.Classifieds;
import com.portal.clf.models.ClassifiedsOrderItemDetails;
import com.portal.clf.models.ClfPaymentsRefund;
import com.portal.clf.models.CustomerDetails;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.clf.models.ErpClassifieds;
import com.portal.clf.models.OrderDetails;
import com.portal.clf.models.Payments;
import com.portal.clf.models.PaymentsPendingModel;
import com.portal.clf.models.PostClassifiedRates;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.constants.GeneralConstants;
import com.portal.da.service.DisplayService;
import com.portal.da.service.DisplayServiceImpl;
import com.portal.doc.entity.Attachments;
import com.portal.erp.service.ErpService;
import com.portal.erp.to.ApiResponse;
import com.portal.erp.to.ErpDataResponsePayload;
import com.portal.gd.entities.GDClassifiedTemplates;
import com.portal.gd.entities.GdClassifiedSchemes;
import com.portal.gd.entities.GdClassifiedTypes;
import com.portal.gd.entities.GdNumberSeries;
import com.portal.gd.entities.GdSettingsDefinitions;
import com.portal.gd.entities.GdUserTypes;
import com.portal.gd.models.GdClassifiedTemplateTo;
import com.portal.reports.utility.CommonUtils;
import com.portal.repository.AttachmentsRepo;
import com.portal.repository.ClfEditionsRepo;
import com.portal.repository.ClfOrderItemRatesRepo;
import com.portal.repository.ClfOrderItemsRepo;
import com.portal.repository.ClfOrdersRepo;
import com.portal.repository.ClfPaymentResponseTrackingRepo;
import com.portal.repository.ClfPaymentsRepo;
import com.portal.repository.ClfPublishDatesRepo;
import com.portal.repository.ClfRatesRepo;
import com.portal.repository.GDClassifiedTemplatesRepo;
import com.portal.repository.GdCityRepo;
import com.portal.repository.GdClassifiedSchemeRepo;
import com.portal.repository.GdClassifiedTypesRepo;
import com.portal.repository.GdNumberSeriesRepo;
import com.portal.repository.GdSettingsDefinitionsRepository;
import com.portal.repository.GdUserTypesRepo;
import com.portal.repository.UmCustomersRepo;
import com.portal.repository.UmUsersRepository;
import com.portal.security.model.LoggedUser;
import com.portal.security.repo.UmOrgUsersRepo;
import com.portal.security.util.LoggedUserContext;
import com.portal.send.models.EmailsTo;
import com.portal.send.service.SendMessageService;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;
import com.portal.user.dao.UserDao;
import com.portal.user.entities.UmCustomers;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.entities.UmUsers;

@Service
//@PropertySource(value = { "classpath:/com/portal/queries/clf_db.properties" })
public class ClassifiedServiceImpl implements ClassifiedService {

	@Autowired
	private GDClassifiedTemplatesRepo gDClassifiedTemplatesRepo;

	@Autowired
	private ClfOrdersRepo clfOrdersRepo;

	@Autowired
	private ClfOrderItemsRepo clfOrderItemsRepo;

	@Autowired
	private ClfPublishDatesRepo clfPublishDatesRepo;

	@Autowired
	private ClfEditionsRepo clfEditionsRepo;

	@Autowired
	private GdClassifiedTypesRepo gdClassifiedTypesRepo;

	@Autowired
	private UmCustomersRepo umCustomersRepo;

	@Autowired
	private ClfRatesRepo clfRatesRepo;

	@Autowired
	private ClfOrderItemRatesRepo clfOrderItemRatesRepo;

	@Autowired
	private ClfPaymentsRepo clfPaymentsRepo;

	@Autowired
	private Environment properties;

	@Autowired
	private GdSettingsDefinitionsRepository settingRepo;

	@Autowired
	private GdNumberSeriesRepo gdNumberSeriesRepo;

	@Autowired
	private GdCityRepo gdCityRepo;

	@Autowired(required = true)
	private IBaseDao baseDao;

	@Autowired(required = true)
	private SettingDao settingDao;

	@Autowired
	private ErpService erpService;

	@Autowired
	private ClassifiedDownloadService classifiedDownloadService;

	@Autowired(required = true)
	private SendMessageService sendService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private GdClassifiedSchemeRepo gdClassifiedSchemeRepo;

	@Autowired
	private UmUsersRepository umUsersRepository;

	@Autowired
	private GdUserTypesRepo gdUserTypesRepo;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ClfPaymentResponseTrackingRepo clfPaymentResponseTrackingRepo;

	@Autowired
	private AttachmentsRepo attachmentsRepo;
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	@Autowired
	private UmOrgUsersRepo umOrgUsersRepo;
	
	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired
	private DisplayService displayService;
	
	@Autowired
	private DisplayServiceImpl displayServiceImpl;

	@SuppressWarnings({ "unchecked" })
	@Override
	public GenericApiResponse getClassifiedList(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);

//		String query = properties.getProperty("GET_OPEN_ORD_DET");
//		String query = "select uc.mobile_no, itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,co.erp_order_id from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join um_customers uc on co.customer_id = uc.customer_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id where itm.mark_As_Delete = false and cp.payment_status = 'success'";
		String query = "select co.user_type_id, itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,co.erp_order_id,itm.created_by,co.attached_id,cp.order_id as payments_order_id,cpd.download_status as publishDateDownloadStatus,co.comments,itm.classified_ads_type,gcast.ads_sub_type,co.booking_unit,bu.booking_location,cp.ecom_order_id from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_publish_dates cpd on itm.item_id=cpd.item_id left join booking_units bu on bu.booking_code = co.booking_unit where itm.mark_As_Delete = false and co.order_type = 2 and cp.payment_status = 'success'";

		if (payload.getClassifiedType() != null) {
			query = query + " and itm.classified_type = " + payload.getClassifiedType() + "";
		}
		if (payload.getCategoryId() != null) {
			query = query + " and itm.category = " + payload.getCategoryId() + "";
		}
//		if(payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()){
//			query = query + " and to_char(itm.created_ts,'DD/MM/YYYY') = '" + payload.getRequestedDate() + "'";
//		}
		if (payload.getContentStatus() != null && !payload.getContentStatus().isEmpty()) {
			query = query + " and itm.status = '" + payload.getContentStatus() + "'";
		}
		if (payload.getPaymentStatus() != null && !payload.getPaymentStatus().isEmpty()) {
			query = query + " and co.payment_status = '" + payload.getPaymentStatus() + "'";
		}
		if (payload.getBookingUnit() != null && payload.getBookingUnit() != 0) {
			query = query + " and co.booking_unit = " + payload.getBookingUnit() + "";
		}
		if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()
				&& payload.getRequestedToDate() != null && !payload.getRequestedToDate().isEmpty()) {
			String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			String toDate = this.convertDateFormat(payload.getRequestedToDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query += " AND to_char(itm.created_ts,'YYYY-MM-DD') >= '" + formattedDate
					+ "' AND to_char(itm.created_ts,'YYYY-MM-DD') <= '" + toDate + "'";
		}
		if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()
				&& payload.getRequestedToDate() == null) {
			String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query = query + " and to_char(itm.created_ts,'YYYY-MM-DD') = '" + formattedDate
					+ "' AND to_char(itm.created_ts,'YYYY-MM-DD') <= '" + formattedDate + "'";
		}
		if (payload.getRequestedToDate() != null && !payload.getRequestedToDate().isEmpty()
				&& payload.getRequestedDate() == null) {
			String toDate = this.convertDateFormat(payload.getRequestedToDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query = query + " and to_char(itm.created_ts,'YYYY-MM-DD') = '" + toDate
					+ "' AND to_char(itm.created_ts,'YYYY-MM-DD') <= '" + toDate + "'";
		}

		if (loggedUser.getCustomerId() == null || loggedUser.getCustomerId().isEmpty()) {
			if (!"ADMIN".equalsIgnoreCase(loggedUser.getRoleName())
					&& !"SUPER_ADMIN".equalsIgnoreCase(loggedUser.getRoleName())
					&& !"APPROVER".equalsIgnoreCase(loggedUser.getRoleName())) {
				query = query + " and itm.created_by = " + loggedUser.getUserId() + "";
			}
		} else {
			query = query + " and uc.customer_id = '" + loggedUser.getCustomerId() + "'";
		}
		query = query + " ORDER BY itm.ad_id DESC";
		classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

//		Map<String, Classifieds> classifiedsMap = new HashMap<>();
		LinkedHashMap<String, Classifieds> classifiedsMap = new LinkedHashMap<>();
		List<String> itemIds = new ArrayList<String>();
		List<String> attachedIds = new ArrayList<String>();
		List<Integer> createdByForAgency = new ArrayList<Integer>();
		for (Object[] objs : classifiedList) {
			Classifieds classified = new Classifieds();
//			classified.setContactNo((String)objs[0]);
//			classified.setPublishedDate(CommonUtils.dateFormatter((Date)objs[1],"dd-MM-yyyy"));
			classified.setRequestedDate(CommonUtils.dateFormatter((Date) objs[1], "dd-MM-yyyy"));
			classified.setCategoryId(((Integer) objs[2]).intValue());
			classified.setCategory((String) objs[3]);
			Double val = (Double.valueOf(df.format(objs[4])));
			classified.setPaidAmount(new BigDecimal(df1.format(val)));
//			classified.setPaidAmount(new BigDecimal((Float)objs[4]));
			classified.setApprovalStatus((String) objs[5]);
			classified.setPaymentStatus((String) objs[12]);
			classified.setClfPaymentStatus((String) objs[6]);
			classified.setDownloadStatus(objs[7] == null ? false : (Boolean) objs[7]);
			classified.setMatter((String) objs[8]);
			classified.setItemId((String) objs[9]);
			classified.setOrderId((String) objs[10]);
			classified.setSubCategory((String) objs[11]);
			classified.setScheme((String) objs[14]);
			classified.setAdId((String) objs[15]);
			classified.setErpOrderId((String) objs[16]);
//			if ("3".equalsIgnoreCase(objs[0] + "")) {
				classified.setCreatedBy((Integer) objs[17]);
				createdByForAgency.add((Integer) objs[17]);
				classified.setUserTypeId((Integer) objs[0]);
//			}
			classified.setAttachedId(objs[18] != null ? (String) objs[18] : null);
			attachedIds.add((String) objs[18]);
			classified.setPaymentsOrderId((String) objs[19]);
			classified.setPublishDateDownloadStatus((boolean) objs[20]);
			classified.setComments((String) objs[21]);
			classified.setAdSubTypeId((Integer) objs[22]);
			classified.setAdSubtype((String) objs[23]);
			classified.setBookingUnitCode((Integer) objs[24]);
			classified.setBookingUnitDesc((String) objs[25]);
			classified.setEcomOrderId((String) objs[26]);
			itemIds.add((String) objs[9]);
			classifiedsMap.put((String) objs[9], classified);
		}

		if (itemIds != null && !itemIds.isEmpty()) {
			List<Object[]> editionsList = clfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
			for (Object[] clObj : editionsList) {
				if (classifiedsMap.containsKey((String) clObj[0])) {
					if (classifiedsMap.get((String) clObj[0]).getEditions() != null) {
						classifiedsMap.get((String) clObj[0]).getEditions().add((String) clObj[2]);
					} else {
						List<String> edditions = new ArrayList<>();
						edditions.add((String) clObj[2]);
						Classifieds classified = classifiedsMap.get((String) clObj[0]);
						classified.setEditions(edditions);
						classifiedsMap.put((String) clObj[0], classified);
					}
				}
			}
		}
		if (itemIds != null && !itemIds.isEmpty()) {
			String itemIds1 = String.join("','", itemIds);
			String query1 = "select itm.item_id,co.order_id,uc.mobile_no from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id where itm.mark_as_delete = false and co.mark_as_delete = false and itm.item_id in ('"
					+ itemIds1 + "')";
			List<Object[]> userTypeList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
			if (!userTypeList.isEmpty()) {
				for (Object[] obj : userTypeList) {
					if (classifiedsMap.containsKey((String) obj[0])) {
						if (classifiedsMap.get((String) obj[0]).getContactNo() == null) {
							classifiedsMap.get((String) obj[0]).setContactNo((String) obj[2]);
						}
					}
				}
			}
		}
		if (createdByForAgency != null && !createdByForAgency.isEmpty()) {
			List<UmOrgUsers> umOrgUsers = umOrgUsersRepo.getOrgUsersByCreatedIds(createdByForAgency);
			if (!umOrgUsers.isEmpty()) {
				classifiedsMap.entrySet().forEach(erpData -> {
					Optional<UmOrgUsers> gd = umOrgUsers.stream()
							.filter(f -> (f.getUmUsers().getUserId()).equals(erpData.getValue().getCreatedBy())).findFirst();
					if (gd.isPresent()) {
						UmOrgUsers umOrgUser = gd.get();
						erpData.getValue().setCreatedByRole(umOrgUser.getUmOrgRoles().getRoleShortId());						
					}
				});
			}
			List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByForAgency, false);
			if (!umUsers.isEmpty()) {
				classifiedsMap.entrySet().forEach(erpData -> {
					Optional<UmUsers> gd = umUsers.stream()
							.filter(f -> (f.getUserId()).equals(erpData.getValue().getCreatedBy())).findFirst();
					if (gd.isPresent()) {
						UmUsers umUser = gd.get();
						if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
							erpData.getValue().setContactNo(umUser.getMobile());
						}
						erpData.getValue().setCreatedByName(umUser.getLastName() != null ? umUser.getFirstName()+ " " +umUser.getLastName() : umUser.getFirstName());
						
						if("SSP_USER".equalsIgnoreCase(erpData.getValue().getCreatedByRole())) {
							erpData.getValue().setCreatedByEmpId(umUser.getLogonId());
						}else {
							erpData.getValue().setCreatedByEmpId(umUser.getEmpCode());
						}
					}
				});
			}
			

		}
		if (attachedIds != null && !attachedIds.isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getListOfAttachmentDetails(attachedIds);
			if (!listOfAttachmentDetails.isEmpty()) {
				classifiedsMap.entrySet().forEach(erpData -> {
					Optional<Attachments> attachmentDetails = listOfAttachmentDetails.stream()
							.filter(f -> f.getAttachId().equals(erpData.getValue().getAttachedId())).findFirst();
					if (attachmentDetails.isPresent()) {
						erpData.getValue().setAttachmentName(attachmentDetails.get().getAttachName());
					}
				});
			}
		}
		genericApiResponse.setData(classifiedsMap.values());
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse addClassifiedItemToCart(AddToCartRequest payload, LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		genericApiResponse.setMessage("Successfully added");

//		if(payload.getItemId() != null) {
//			genericApiResponse = this.removeOldCardDet(payload,loggedUser);
//		}
		payload = this.calculateTotalAmount(payload, loggedUser);
		CustomerDetails customerDetails = new CustomerDetails();
		if (!"AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
			customerDetails = populateCustomerDetails(payload.getCustomerDetails(), loggedUser);
			if (customerDetails != null && customerDetails.getCustomerId() == null) {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage("Customer details not found");
				return genericApiResponse;
			}
		}
//		ClfOrders clfOrders = getOpenCartDetails(loggedUser, customerDetails.getCustomerId());
		ClfOrders clfOrders = null;
		if (payload.getItemId() == null) {
			 clfOrders = new ClfOrders();
			clfOrders.setOrderId(UUID.randomUUID().toString());
			clfOrders.setCustomerId(customerDetails.getCustomerId() == null ? loggedUser.getCustomerId()
					: customerDetails.getCustomerId());
			clfOrders.setUserTypeId(loggedUser.getUserTypeId());
			clfOrders.setOrderStatus(ClassifiedConstants.ORDER_OPEN);
			clfOrders.setPaymentStatus(ClassifiedConstants.ORDER_PAYMENT_PENDING);
			clfOrders.setCreatedBy(loggedUser.getUserId());
			clfOrders.setCreatedTs(new Date());
			clfOrders.setMarkAsDelete(false);
			clfOrders.setBookingUnit(payload.getCustomerDetails().getBookingUnit());
			clfOrders.setEditionType(payload.getItemList().get(0).getEditionType());
			clfOrders.setOrderType(02);
			clfOrders.setAttachedId(payload.getItemList().get(0).getAttachedId());
			if ("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				clfOrders.setCustomerName(payload.getCustomerDetails().getCustomerName2());
			}
			clfOrdersRepo.save(clfOrders);
		} else {
			ClfOrderItems clfOrderItems = clfOrderItemsRepo.getItemDetailsOnItemId(payload.getItemId());
			clfOrders = new ClfOrders();
			clfOrders = clfOrdersRepo.getOrderDetailsOnOrderId(clfOrderItems.getClfOrders().getOrderId());
			clfOrders.setBookingUnit(payload.getCustomerDetails().getBookingUnit());
			clfOrders.setEditionType(payload.getItemList().get(0).getEditionType());
			clfOrders.setAttachedId(payload.getItemList().get(0).getAttachedId());
			if ("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				clfOrders.setCustomerName(payload.getCustomerDetails().getCustomerName2());
			}
			clfOrdersRepo.save(clfOrders);

			List<ClfOrderItems> clfOrderItemsList = clfOrderItemsRepo.getOpenOrderItems(clfOrders.getOrderId());
			if (clfOrderItemsList != null && !clfOrderItemsList.isEmpty()) {
				ClfOrderItems clfOrder = clfOrderItemsList.get(0);
				payload.setItemId(clfOrder.getItemId());
				payload.setClassifiedType(clfOrder.getClassifiedType());
				genericApiResponse = this.removeOldCardDet(payload, loggedUser);
			}
		}
		if (clfOrders.getOrderId() == null) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Order details not found");
		}
		for (ClassifiedsOrderItemDetails item : payload.getItemList()) {
			ClfOrderItems clfOrderItems = new ClfOrderItems();
			clfOrderItems.setItemId(UUID.randomUUID().toString());
			clfOrderItems.setClfOrders(clfOrders);
			clfOrderItems.setClassifiedType(payload.getClassifiedType());
			clfOrderItems.setClassifiedAdsType(item.getAdsType());
			clfOrderItems.setClassifiedAdsSubType(item.getAdsSubType());
			clfOrderItems.setScheme(item.getScheme());
			clfOrderItems.setCategory(item.getCategory());
			clfOrderItems.setSubcategory(item.getSubCategory());
//			clfOrderItems.setChildCategory(item.getChildCategory());
//			clfOrderItems.setGroup(item.getGroup());
//			clfOrderItems.setSubGroup(item.getSubGroup());
//			clfOrderItems.setChildGroup(item.getChildGroup());
			clfOrderItems.setLang(item.getLang());
			clfOrderItems.setClfContent(item.getContent());
			clfOrderItems.setCreatedBy(loggedUser.getUserId());
			clfOrderItems.setCreatedTs(new Date());
			clfOrderItems.setStatus(ClassifiedConstants.CLASSIFIED_APPROVAL_PENDING);
			clfOrderItems.setMarkAsDelete(false);
			clfOrderItems.setDownloadStatus(false);
			String adId = "";
			if("SSP_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				adId = this.generateSeries("DISPLAY_ADS");
			}else {
				adId = this.generateSeries("LINE_ADS");
			}			 
			if (adId != null) {
				clfOrderItems.setAdId(adId);
			}
			clfOrderItemsRepo.save(clfOrderItems);
			for (String pubDate : item.getPublishDates()) {
				ClfPublishDates clfPublishDate = new ClfPublishDates();
				clfPublishDate.setPublishDateId(UUID.randomUUID().toString());
				clfPublishDate.setClfOrderItems(clfOrderItems);
				clfPublishDate.setPublishDate(CommonUtils.dateFormatter(pubDate));
				clfPublishDate.setCreatedBy(loggedUser.getUserId());
				clfPublishDate.setCreatedTs(new Date());
				clfPublishDate.setMarkAsDelete(false);
				clfPublishDate.setOrderId(clfOrders.getOrderId());
				clfPublishDate.setDownloadStatus(false);
				clfPublishDatesRepo.save(clfPublishDate);
			}
			for (Integer editionId : item.getEditions()) {
				ClfEditions clfEdition = new ClfEditions();
				clfEdition.setOrderEditionId(UUID.randomUUID().toString());
				clfEdition.setEditionId(editionId);
				clfEdition.setClfOrderItems(clfOrderItems);
				clfEdition.setCreatedBy(loggedUser.getUserId());
				clfEdition.setCreatedTs(new Date());
				clfEdition.setMarkAsDelete(false);
				clfEdition.setOrderId(clfOrders.getOrderId());
				clfEditionsRepo.save(clfEdition);
			}
			PostClassifiedRates postClassifiedRates = item.getPostClassifiedRates();
			ClfOrderItemRates clfOrderItemRates = new ClfOrderItemRates();
			clfOrderItemRates.setItemRateId(UUID.randomUUID().toString());
			clfOrderItemRates.setOrderId(clfOrders.getOrderId());
			clfOrderItemRates.setItemId(clfOrderItems.getItemId());
			clfOrderItemRates.setRate(postClassifiedRates.getRate());
			clfOrderItemRates.setExtraLineRate(postClassifiedRates.getExtraLineAmount());
			clfOrderItemRates.setLines(postClassifiedRates.getMinLines());
			clfOrderItemRates.setLineCount(postClassifiedRates.getActualLines());
//			clfOrderItemRates.setTotalAmount(postClassifiedRates.getTotalAmount());
			clfOrderItemRates.setTotalAmount((double) Math.round(postClassifiedRates.getTotalAmount() * 100.0) / 100.0);
			clfOrderItemRates.setCreatedBy(loggedUser.getUserId());
			clfOrderItemRates.setCreatedTs(new Date());
			clfOrderItemRates.setMarkAsDelete(false);
			clfOrderItemRates.setAgencyCommition(postClassifiedRates.getAgencyCommitionAmount());
			clfOrderItemRates.setGstTotal(postClassifiedRates.getGstTaxAmount());
			clfOrderItemRatesRepo.save(clfOrderItemRates);
		}
		return genericApiResponse;
	}

//	private AddToCartRequest calculateTotalAmount(AddToCartRequest payload, LoggedUser loggedUser) {
//		GenericApiResponse genericApiResponse = new GenericApiResponse();
//		ClassifiedRateDetails classifiedRateDetails = new ClassifiedRateDetails();
//		try {
//			double rates = 0.0;
//			double extraLineRate = 0.0;
//			Integer minLines = 0;
//			Integer lineCount = 0;
//			Double tax = 0.0;
//			Double agencyCommition = 0.0;
//			Double totalAmount = 0.0;
//			
//			genericApiResponse = this.getRatesAndLines(payload.getItemList().get(0));
//			PostClassifiedRates postClassifiedRates = payload.getItemList().get(0).getPostClassifiedRates();
//			
//			classifiedRateDetails = (ClassifiedRateDetails) genericApiResponse.getData();
//			for(ClassifiedRates clfRates : classifiedRateDetails.getClassifiedRates()) {
//				rates = rates + clfRates.getRate();
//				extraLineRate = extraLineRate + clfRates.getExtraLineRate();
//				minLines =  clfRates.getMinLines();
//				classifiedRateDetails.setAgencyCommission(classifiedRateDetails.getAgencyCommission());
//				classifiedRateDetails.setMaxLinesAllowed(classifiedRateDetails.getMaxLinesAllowed());
//				classifiedRateDetails.setTax(classifiedRateDetails.getTax());
//			}
//			postClassifiedRates.setRate(rates);
//			postClassifiedRates.setExtraLineRate(extraLineRate);
//			postClassifiedRates.setMinLines(minLines);
//			lineCount = postClassifiedRates.getActualLines();
//			tax = classifiedRateDetails.getTax();
//			agencyCommition = classifiedRateDetails.getAgencyCommission();
//			
//			GdClassifiedSchemes gdClassifiedSchemes = gdClassifiedSchemeRepo.getSchemeDetails(payload.getItemList().get(0).getScheme());
//			Integer bDays = gdClassifiedSchemes.getBillableDays();
//			Double rate = postClassifiedRates.getRate() * bDays;
//			postClassifiedRates.setRate(rate);
//			lineCount = lineCount > minLines ? lineCount : minLines;
//			if (!"AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
//				Double taxAmount = ((rate) + ((extraLineRate * bDays) * (lineCount - minLines))) * tax / 100;
//				Double cmsAmount = ((rate) + ((extraLineRate * bDays) * (lineCount - minLines))) * agencyCommition
//						/ 100;
//				totalAmount = (rate + (extraLineRate * bDays) * (lineCount - minLines)) + taxAmount;
//			}
//			if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
//				Double cmsAmount = ((rate) + ((extraLineRate * bDays) * (lineCount - minLines))) * agencyCommition
//						/ 100;
//				totalAmount = (rate + (extraLineRate * bDays) * (lineCount - minLines)) - cmsAmount;
//				Double taxAmount = totalAmount * tax / 100;
//				totalAmount = totalAmount + taxAmount;
//			}
//			DecimalFormat df = new DecimalFormat("#.##");
//		    totalAmount = Double.valueOf(df.format(totalAmount));
//			postClassifiedRates.setTotalAmount(totalAmount);
//			payload.getItemList().get(0).setPostClassifiedRates(postClassifiedRates);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return payload;
//	}

	@SuppressWarnings("unused")
	private AddToCartRequest calculateTotalAmount(AddToCartRequest payload, LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		ClassifiedRateDetails classifiedRateDetails = new ClassifiedRateDetails();
		try {
			double rates = 0.0;
			double extraLineRate = 0.0;
			Integer minLines = 0;
			Integer lineCount = 0;
			Double tax = 0.0;
			Double agencyCommition = 0.0;
			Double totalAmount = 0.0;
			Double totalAmount1 = 0.0;
			Double taxAmount1 = 0.0;
			Double taxAmount2 = 0.0;
			Double taxAmount = 0.0;
			Double cmsAmount = 0.0;
			Double totalAgencyCommition = 0.0;
			Double totalTaxAmount = 0.0;
			Integer bDays = 0;
			DecimalFormat df = new DecimalFormat("#.##");
			DecimalFormat df1 = new DecimalFormat("#.###");
			df1.setRoundingMode(java.math.RoundingMode.FLOOR);
//			df.setRoundingMode(RoundingMode.CEILING);
			df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);

			genericApiResponse = this.getRatesAndLines(payload.getItemList().get(0));
			PostClassifiedRates postClassifiedRates = payload.getItemList().get(0).getPostClassifiedRates();

			GdClassifiedSchemes gdClassifiedSchemes = new GdClassifiedSchemes();
			if (payload.getItemList() != null && payload.getItemList().get(0).getScheme() != 0) {
				gdClassifiedSchemes = gdClassifiedSchemeRepo.getSchemeDetails(payload.getItemList().get(0).getScheme());
				bDays = gdClassifiedSchemes.getBillableDays();
			} else {
				bDays = payload.getItemList().get(0).getPublishDates().size();
			}

			lineCount = postClassifiedRates.getActualLines();

			classifiedRateDetails = (ClassifiedRateDetails) genericApiResponse.getData();
			tax = classifiedRateDetails.getTax();
			classifiedRateDetails.setAgencyCommission(classifiedRateDetails.getAgencyCommission());
			classifiedRateDetails.setMaxLinesAllowed(classifiedRateDetails.getMaxLinesAllowed());
			classifiedRateDetails.setTax(classifiedRateDetails.getTax());
			agencyCommition = classifiedRateDetails.getAgencyCommission();

			for (ClassifiedRates clfRates : classifiedRateDetails.getClassifiedRates()) {
				Double rates1 = clfRates.getRate();
				rates += rates1;
				extraLineRate = clfRates.getExtraLineRate();
				minLines = clfRates.getMinLines();
				Double rate1 = rates1 * bDays;
				Integer lineCount1 = Math.max(lineCount, minLines);

				if (!"AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
					taxAmount1 = 0.0;
					taxAmount2 = 0.0;
					totalAmount1 = (rate1 + (extraLineRate * bDays) * (lineCount1 - minLines));
					taxAmount1 = (totalAmount1) * tax / 100 / 2;
					taxAmount2 = (totalAmount1) * tax / 100 / 2;
				} else {
					taxAmount1 = 0.0;
					taxAmount2 = 0.0;
					cmsAmount = ((rate1) + ((extraLineRate * bDays) * (lineCount1 - minLines))) * agencyCommition / 100;
					cmsAmount = Double.valueOf(df1.format(cmsAmount));
					BigDecimal bcmsAmount = BigDecimal.valueOf(cmsAmount);
					bcmsAmount = bcmsAmount.setScale(2, RoundingMode.HALF_UP);
					cmsAmount = bcmsAmount.doubleValue();
					totalAmount1 = (rate1 + (extraLineRate * bDays) * (lineCount1 - minLines)) - cmsAmount;
					taxAmount1 = (totalAmount1) * tax / 100 / 2;
					taxAmount2 = (totalAmount1) * tax / 100 / 2;
					totalAgencyCommition = totalAgencyCommition + cmsAmount;
				}

				taxAmount1 = Double.valueOf(df1.format(taxAmount1));
				taxAmount2 = Double.valueOf(df1.format(taxAmount2));
				BigDecimal bd1 = BigDecimal.valueOf(taxAmount1);
				BigDecimal bd2 = BigDecimal.valueOf(taxAmount2);
				bd1 = bd1.setScale(2, RoundingMode.HALF_UP);
				bd2 = bd2.setScale(2, RoundingMode.HALF_UP);
				double roundedTaxAmount1 = bd1.doubleValue();
				double roundedTaxAmount2 = bd2.doubleValue();

				System.out.println(roundedTaxAmount1);
				System.out.println(roundedTaxAmount2);
				taxAmount = roundedTaxAmount1 + roundedTaxAmount2;
				totalTaxAmount = totalTaxAmount + taxAmount;
//				    totalAmount += (rate1 + (extraLineRate * bDays) * (lineCount - minLines)) + taxAmount;
				totalAmount = totalAmount + totalAmount1 + taxAmount;
			}
//				if (!"AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
//					taxAmount1 = 0.0;
//					taxAmount2 = 0.0;
//					Double rates1 = clfRates.getRate();
//					rates = rates + clfRates.getRate();
//					extraLineRate = clfRates.getExtraLineRate();
//					minLines = clfRates.getMinLines();
//					Double rate1 = rates1 * bDays;
//					lineCount = lineCount > minLines ? lineCount : minLines;
//					taxAmount1 = ((rate1) + ((extraLineRate * bDays) * (lineCount - minLines))) * tax / 100 / 2;
//					taxAmount2 = ((rate1) + ((extraLineRate * bDays) * (lineCount - minLines))) * tax / 100 / 2;
//					 DecimalFormat df1 = new DecimalFormat("#.###");
//					 df1.setRoundingMode(java.math.RoundingMode.FLOOR);
//					 taxAmount1 = Double.valueOf(df1.format(taxAmount1));
//					 taxAmount2 = Double.valueOf(df1.format(taxAmount2));
//					 BigDecimal bd1 = BigDecimal.valueOf(taxAmount1);
//					 BigDecimal bd2 = BigDecimal.valueOf(taxAmount2);
//					 bd1 = bd1.setScale(2, RoundingMode.HALF_UP);
//					 bd2 = bd2.setScale(2, RoundingMode.HALF_UP);
////					double roundedTaxAmount1 = Double.valueOf(df.format(taxAmount1));
////					double roundedTaxAmount2 = Double.valueOf(df.format(taxAmount2));
//					 double roundedTaxAmount1 = bd1.doubleValue();
//					 double roundedTaxAmount2 = bd2.doubleValue();
//					System.out.println(roundedTaxAmount1);
//					System.out.println(roundedTaxAmount2);
//					taxAmount = roundedTaxAmount1 + roundedTaxAmount2;
//
//					totalAmount = totalAmount + (rate1 + (extraLineRate * bDays) * (lineCount - minLines)) + taxAmount;
//				} else {
//					taxAmount1 = 0.0;
//					taxAmount2 = 0.0;
//					Double rates1 = clfRates.getRate();
//					rates = rates + clfRates.getRate();
//					extraLineRate = clfRates.getExtraLineRate();
//					minLines = clfRates.getMinLines();
//					Double rate1 = rates1 * bDays;
//					lineCount = lineCount > minLines ? lineCount : minLines;
//
//					cmsAmount = ((rate1) + ((extraLineRate * bDays) * (lineCount - minLines))) * agencyCommition / 100;
//					Double totalAmount1 = (rate1 + (extraLineRate * bDays) * (lineCount - minLines)) - cmsAmount;
////					taxAmount = totalAmount1 * tax / 100 / 2;
//					taxAmount1 = (totalAmount1) * tax / 100 / 2;
//					taxAmount2 = (totalAmount1) * tax / 100 / 2;
//					 DecimalFormat df1 = new DecimalFormat("#.###");
//					 df1.setRoundingMode(java.math.RoundingMode.FLOOR);
//					 taxAmount1 = Double.valueOf(df1.format(taxAmount1));
//					 taxAmount2 = Double.valueOf(df1.format(taxAmount2));
//					 BigDecimal bd1 = BigDecimal.valueOf(taxAmount1);
//					 BigDecimal bd2 = BigDecimal.valueOf(taxAmount2);
//					 bd1 = bd1.setScale(2, RoundingMode.HALF_UP);
//					 bd2 = bd2.setScale(2, RoundingMode.HALF_UP);
////					double roundedTaxAmount1 = Double.valueOf(df.format(taxAmount1));
////					double roundedTaxAmount2 = Double.valueOf(df.format(taxAmount2));
//					 double roundedTaxAmount1 = bd1.doubleValue();
//					 double roundedTaxAmount2 = bd2.doubleValue();
//					System.out.println(roundedTaxAmount1);
//					System.out.println(roundedTaxAmount2);
//					taxAmount = roundedTaxAmount1 + roundedTaxAmount2;
//
//					totalAmount = totalAmount + (totalAmount1) + taxAmount;
//				}

			postClassifiedRates.setRate(rates);
			postClassifiedRates.setExtraLineRate(extraLineRate);
			postClassifiedRates.setMinLines(minLines);
			Double rate = postClassifiedRates.getRate() * bDays;
			postClassifiedRates.setRate(rate);

			totalAmount = Double.valueOf(df.format(totalAmount));
			if (totalAmount - Math.floor(totalAmount) >= 0.50) {
				totalAmount = Math.ceil(totalAmount);
			} else {
				totalAmount = Math.floor(totalAmount);
			}
			postClassifiedRates.setGstTaxAmount(totalTaxAmount);
			postClassifiedRates.setAgencyCommitionAmount(totalAgencyCommition);
			postClassifiedRates.setTotalAmount(totalAmount);
			payload.getItemList().get(0).setPostClassifiedRates(postClassifiedRates);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return payload;
	}

	private GenericApiResponse removeOldCardDet(AddToCartRequest payload, LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		genericApiResponse.setMessage("Successfully Updated");
		try {
			ClfOrderItems clfOrderItems = clfOrderItemsRepo.getItemDetailsOnItemId(payload.getItemId());
			if (clfOrderItems != null) {
				clfOrderItems.setMarkAsDelete(true);
				clfOrderItems.setChangedBy(loggedUser.getUserId());
				clfOrderItems.setChangedTs(new Date());
				clfOrderItemsRepo.save(clfOrderItems);
			}

			clfPublishDatesRepo.removeClfPublishDatesOnItemId(true, loggedUser.getUserId(), new Date(),
					payload.getItemId());

			clfEditionsRepo.removeClfEditionsOnItemId(true, loggedUser.getUserId(), new Date(), payload.getItemId());

			clfOrderItemRatesRepo.removeClfItemRatesOnItemId(true, loggedUser.getUserId(), new Date(),
					payload.getItemId());

		} catch (Exception e) {
			e.printStackTrace();
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Something went wrong. Please contact our administrator.");
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getCartDetails() {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		return genericApiResponse;
	}

//	@Override
//	public GenericApiResponse getCartItems(LoggedUser loggedUser) {
//		GenericApiResponse genericApiResponse = new GenericApiResponse();
//		List<ClfOrders> clfOrdersList = getOpenCartDetailsByLoggedInUser(loggedUser);
//		DecimalFormat df = new DecimalFormat("#.###");
//		DecimalFormat df1 = new DecimalFormat("#.##");
////		df.setRoundingMode(RoundingMode.CEILING);
//		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
//		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
////		Double grandTotal = 0.0;
//		List<CartDetails> cartDetailsList = new ArrayList<>();
//		ClassifiedsOrderItemDetails classifiedsOrderItemDetails = new ClassifiedsOrderItemDetails();
//		if (clfOrdersList != null && !clfOrdersList.isEmpty()) {
//			for(ClfOrders clfOrders : clfOrdersList) {
//			Double grandTotal = 0.0;
//			OrderDetails orderDetails = new OrderDetails();
//			CartDetails cartDetails = new CartDetails();
//			cartDetails.setCustomerId(clfOrders.getCustomerId());
//			List<Object[]> orderDetailsList = clfOrdersRepo.getOrderDetails(clfOrders.getOrderId());
//			if (!orderDetailsList.isEmpty()) {
//				orderDetails.setOrderId((String) orderDetailsList.get(0)[0]);
//				orderDetails.setCreatedUserType((String) orderDetailsList.get(0)[1]);
//				orderDetails.setCreatedUserName((String) orderDetailsList.get(0)[2]);
//				orderDetails.setOrderStatus((String) orderDetailsList.get(0)[4]);
//				orderDetails.setPaymentStatus((String) orderDetailsList.get(0)[5]);
//				orderDetails.setBookingUnit((Integer) orderDetailsList.get(0)[6]);
//				orderDetails.setPaymentChildId((String) orderDetailsList.get(0)[7]);
//				cartDetails.setOrderDetails(orderDetails);
//			}
//			Map<String, ClassifiedsOrderItemDetails> orderItemsMap = new HashMap<>();
//			List<String> itemIds = new ArrayList<String>();
//			List<Object[]> clfOrderItems = clfOrderItemsRepo.getOpenOrderItemsDetailsByOrderId(clfOrders.getOrderId());
//			for (Object[] obj : clfOrderItems) {
//				classifiedsOrderItemDetails = new ClassifiedsOrderItemDetails();
//				classifiedsOrderItemDetails.setItemId((String) obj[0]);
//				classifiedsOrderItemDetails.setAdsTypeStr((String) obj[3]);
//				classifiedsOrderItemDetails.setAdsSubTypeStr((String) obj[4]);
//				classifiedsOrderItemDetails.setSchemeStr((String) obj[5]);
//				classifiedsOrderItemDetails.setCategoryStr((String) obj[6]);
//				classifiedsOrderItemDetails.setSubCategoryStr((String) obj[7]);
//				classifiedsOrderItemDetails.setLangStr((String) obj[8]);
//				classifiedsOrderItemDetails.setContent((String) obj[9]);
//				classifiedsOrderItemDetails.setCreateDate((Date) obj[10]);
//				classifiedsOrderItemDetails.setCreateTime((Date) obj[10]);
////				classifiedsOrderItemDetails.setAmount(new Double((Float) obj[11]));
//				Double val = (Double.valueOf(df.format(obj[11])));
//				classifiedsOrderItemDetails.setAmount(Double.valueOf(df1.format(val)));
////				classifiedsOrderItemDetails.setAmount(Double.valueOf(1.00));
////				classifiedsOrderItemDetails.setAmount(Double.valueOf(df.format(obj[11])));
//				classifiedsOrderItemDetails.setAdId((String) obj[12]);
//				itemIds.add((String) obj[0]);
//				// Group subgrop and childgroup are removed Date : 31/01/2024
////				classifiedsOrderItemDetails.setChildCategoryStr((String) obj[14]);
////				classifiedsOrderItemDetails.setGroup((Integer) obj[14]);
////				classifiedsOrderItemDetails.setSubGroup((Integer) obj[15]);
////				classifiedsOrderItemDetails.setChildGroup((Integer) obj[16]);
////				classifiedsOrderItemDetails.setGroupStr((String) obj[17]);
////				classifiedsOrderItemDetails.setSubGroupStr((String) obj[18]);
////				classifiedsOrderItemDetails.setChildGroupStr((String) obj[19]);
////				grandTotal = grandTotal+ classifiedsOrderItemDetails.getAmount();
////				if(orderItemsMap.containsKey((String)obj[0])){
////					orderItemsMap.get((String)obj[0]).getEditionsStr().add((String)obj[12]);
////				} else {
////					List<String> editions = new ArrayList<>();
////					editions.add((String)obj[12]);
////					classifiedsOrderItemDetails.setEditionsStr(editions);
////					orderItemsMap.put((String)obj[0], classifiedsOrderItemDetails);
////				}
//			}
//			if (!itemIds.isEmpty()) {
//				List<Object[]> editionDetails = clfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
//				if (!editionDetails.isEmpty()) {
//					for (Object[] objs : editionDetails) {
//						if (orderItemsMap.containsKey(objs[0])) {
//							orderItemsMap.get((String) objs[0]).getEditionsStr().add((String) objs[2]);
//						} else {
//							List<String> editions = new ArrayList<>();
//							editions.add((String) objs[2]);
//							classifiedsOrderItemDetails.setEditionsStr(editions);
//							orderItemsMap.put((String) objs[0], classifiedsOrderItemDetails);
//						}
//					}
//				}
//			}
//			grandTotal = grandTotal + classifiedsOrderItemDetails.getAmount();
//			grandTotal = Double.valueOf(df.format(grandTotal));
//			orderDetails.setGrandTotal(grandTotal);
//			cartDetails.setItems(new ArrayList<>(orderItemsMap.values()));
//			cartDetailsList.add(cartDetails);
//			genericApiResponse.setData(cartDetailsList);
//		}
//	}
//		return genericApiResponse;
//	}
	
	@Override
	public GenericApiResponse getCartItems(LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<ClfOrders> clfOrdersList = getOpenCartDetailsByLoggedInUser(loggedUser);
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		List<String> orderIds=new ArrayList<String>();
		List<CartDetails> cartDetailsList=null;
		if(clfOrdersList!=null) {
			 orderIds = clfOrdersList.stream().map(ClfOrders::getOrderId).collect(Collectors.toList());
		}
		if (!orderIds.isEmpty()) {
			
			List<Object[]> orderDetailsList = clfOrdersRepo.getOrderDetailsList(orderIds);

			// Prepare a map to hold OrderDetails by orderId
			Map<String, OrderDetails> orderDetailsMap = new HashMap<>();
			for (Object[] obj : orderDetailsList) {
				OrderDetails orderDetails = new OrderDetails();
				orderDetails.setOrderId((String) obj[0]);
				orderDetails.setCreatedUserType((String) obj[1]);
				orderDetails.setCreatedUserName((String) obj[2]);
				orderDetails.setOrderStatus((String) obj[4]);
				orderDetails.setPaymentStatus((String) obj[5]);
				orderDetails.setBookingUnit((Integer) obj[6]);
				orderDetails.setPaymentChildId((String) obj[7]);
				orderDetailsMap.put(orderDetails.getOrderId(), orderDetails);
			}

			
			List<Object[]> clfOrderItems = clfOrderItemsRepo.getOpenOrderItemsDetailsByOrderIdList(orderIds);

			// Prepare a map to hold ClassifiedsOrderItemDetails by orderId
			Map<String, ClassifiedsOrderItemDetails> orderItemsByOrderMap = new HashMap<>();
			for (Object[] obj : clfOrderItems) {
				String orderId = (String) obj[1]; 
				if (!orderItemsByOrderMap.containsKey(orderId)) {
					ClassifiedsOrderItemDetails classifiedsOrderItemDetails = new ClassifiedsOrderItemDetails();
					classifiedsOrderItemDetails.setItemId((String) obj[0]);
					classifiedsOrderItemDetails.setAdsTypeStr((String) obj[3]);
					classifiedsOrderItemDetails.setAdsSubTypeStr((String) obj[4]);
					classifiedsOrderItemDetails.setSchemeStr((String) obj[5]);
					classifiedsOrderItemDetails.setCategoryStr((String) obj[6]);
					classifiedsOrderItemDetails.setSubCategoryStr((String) obj[7]);
					classifiedsOrderItemDetails.setLangStr((String) obj[8]);
					classifiedsOrderItemDetails.setContent((String) obj[9]);
					classifiedsOrderItemDetails.setCreateDate((Date) obj[10]);
					classifiedsOrderItemDetails.setCreateTime((Date) obj[10]);
					Double val = (Double.valueOf(df.format(obj[11])));
					classifiedsOrderItemDetails.setAmount(Double.valueOf(df1.format(val)));
					classifiedsOrderItemDetails.setAdId((String) obj[12]);
					orderItemsByOrderMap.put(orderId, classifiedsOrderItemDetails);
				}
			}

			
			List<String> itemIds = new ArrayList<>(orderItemsByOrderMap.values().stream()
					.map(ClassifiedsOrderItemDetails::getItemId).collect(Collectors.toSet()));
			List<Object[]> editionDetails = clfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);

			for (Object[] objs : editionDetails) {
				String itemId = (String) objs[0];
				String editionName = (String) objs[2];
				for (ClassifiedsOrderItemDetails itemDetails : orderItemsByOrderMap.values()) {
					if (itemDetails.getItemId().equals(itemId)) {
						List<String> editions = itemDetails.getEditionsStr();
						if (editions == null) {
							editions = new ArrayList<>();
							itemDetails.setEditionsStr(editions);
						}
						editions.add(editionName);
						break;
					}
				}
			}

			// Create CartDetails for each order and set details
			 cartDetailsList = new ArrayList<>();
			for (ClfOrders clfOrders : clfOrdersList) {
				CartDetails cartDetails = new CartDetails();
				OrderDetails orderDetails = orderDetailsMap.get(clfOrders.getOrderId());
				if (orderDetails != null) {
					cartDetails.setCustomerId(clfOrders.getCustomerId());
					cartDetails.setOrderDetails(orderDetails);

					// Get the item for this order
					ClassifiedsOrderItemDetails item = orderItemsByOrderMap.get(clfOrders.getOrderId());
					if (item != null) {
						cartDetails.setItems(Collections.singletonList(item));

						// Calculate grand total
						double grandTotal = item.getAmount();
						grandTotal = Double.valueOf(df.format(grandTotal));
						orderDetails.setGrandTotal(grandTotal);
					} else {
						cartDetails.setItems(Collections.emptyList());
					}
				}
//				if(cartDetails.getCustomerId() != null) {
					cartDetailsList.add(cartDetails);
//				}
				
			}
					
		}
		if(cartDetailsList!=null) {
			genericApiResponse.setData(cartDetailsList);
			genericApiResponse.setStatus(0);
		}else {
			genericApiResponse.setStatus(1);
            genericApiResponse.setMessage("No data available");
		}
		return genericApiResponse;
	}
	

	@Override
	public GenericApiResponse getClassifiedTypes() {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		List<Integer> daAdsTypes = new ArrayList<>();
		List<ClassifiedTypesModel> classifiedTypesModels = new ArrayList<>();
		List<GdClassifiedTypes> gdClassifiedTypes = gdClassifiedTypesRepo.getClassifiedTypes();
		if (gdClassifiedTypes.isEmpty()) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage(properties.getProperty("GEN_004"));
		}
		
		//SSP USER Ads type permission
		if("SSP_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
			List<UmUsers> users = umUsersRepository.getUserLoginId(loggedUser.getLogonId(), false);
			if(users != null && !users.isEmpty()) {
				for(UmUsers user : users) {
					if(user != null && user.getDaAdsTypes() != null) {
						String daAdsTypess = user.getDaAdsTypes();
						String[] items = daAdsTypess.split(",");
						for(String id : items) {
							daAdsTypes.add(Integer.parseInt(id.trim()));
						}
					}
				}
			}
		}
		for (GdClassifiedTypes gdClsTypes : gdClassifiedTypes) {
			ClassifiedTypesModel clsMode = new ClassifiedTypesModel();
			BeanUtils.copyProperties(gdClsTypes, clsMode);
			if ("SSP_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				if (daAdsTypes.contains(gdClsTypes.getId())) {
					classifiedTypesModels.add(clsMode);
				}
			} else {
				classifiedTypesModels.add(clsMode);
			}
		}
		genericApiResponse.setStatus(0);
		genericApiResponse.setData(classifiedTypesModels);
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getClassifiedTemplates(String id) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<GdClassifiedTemplateTo> classifiedTemplateTos = new ArrayList<>();
		List<GDClassifiedTemplates> templates = gDClassifiedTemplatesRepo.getTemplatesDetails(Integer.valueOf(id));
//		List<GDClassifiedTemplates> templates = (List<GDClassifiedTemplates>) gDClassifiedTemplatesRepo.findAll();
		templates.forEach(tmp -> {
			GdClassifiedTemplateTo ctmp = new GdClassifiedTemplateTo();
			BeanUtils.copyProperties(tmp, ctmp);
			classifiedTemplateTos.add(ctmp);
		});
		genericApiResponse.setData(classifiedTemplateTos);
		return genericApiResponse;
	}

	public ClfOrders getOpenCartDetails(LoggedUser loggedUser, String customerId) {
		List<ClfOrders> clfOrders = clfOrdersRepo.getOpenOrderDetails(loggedUser.getUserId(),
				ClassifiedConstants.ORDER_OPEN);
		if (!clfOrders.isEmpty()) {
			if ("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				return clfOrders.get(0);
			}
			if (clfOrders.get(0).getCustomerId().equalsIgnoreCase(customerId)) {
				return clfOrders.get(0);
			} else {
				clfOrders.get(0).setMarkAsDelete(true);
				clfOrdersRepo.save(clfOrders.get(0));
				return null;
			}
		}
		return null;
	}

//	public ClfOrders getOpenCartDetailsByLoggedInUser(LoggedUser loggedUser) {
//		List<ClfOrders> clfOrders = clfOrdersRepo.getOpenOrderDetailsByLoggedInUser(loggedUser.getUserId(),
//				ClassifiedConstants.ORDER_OPEN);
//		if (!clfOrders.isEmpty())
//			return clfOrders.get(0);
//		return null;
//	}
	
	public List<ClfOrders> getOpenCartDetailsByLoggedInUser(LoggedUser loggedUser) {
		List<ClfOrders> clfOrders = clfOrdersRepo.getOpenOrderDetailsByLoggedInUser(loggedUser.getUserId(),
				ClassifiedConstants.ORDER_OPEN,2);
		if (!clfOrders.isEmpty())
			return clfOrders;
		return null;
	}

	@Override
	public GenericApiResponse getCustomerDetails(String mobileNo) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		List<UmCustomers> umCustomers = umCustomersRepo.getCustomerDetails(mobileNo);
		if (!umCustomers.isEmpty())
			genericApiResponse.setData(umCustomers.get(0));
		else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Customer details not found");
		}
		return genericApiResponse;
	}

	public CustomerDetails populateCustomerDetails(CustomerDetails customerDetails, LoggedUser loggedUser) {
		if (customerDetails != null && customerDetails.getCustomerId() != null
				&& !customerDetails.getCustomerId().isEmpty()) {
			Optional<UmCustomers> customerDetailsFromDB = umCustomersRepo.findById(customerDetails.getCustomerId());
			if (customerDetailsFromDB.isPresent()) {
				customerDetailsFromDB.get()
						.setAadharNumber(customerDetails.getAadharNumber() != null ? customerDetails.getAadharNumber()
								: customerDetailsFromDB.get().getAadharNumber());
				customerDetailsFromDB.get()
						.setPanNumber(customerDetails.getPanNumber() != null ? customerDetails.getPanNumber()
								: customerDetailsFromDB.get().getPanNumber());
				customerDetailsFromDB.get()
						.setAddress1(customerDetails.getAddress1() != null ? customerDetails.getAddress1()
								: customerDetailsFromDB.get().getAddress1());
				customerDetailsFromDB.get()
						.setAddress2(customerDetails.getAddress2() != null ? customerDetails.getAddress2()
								: customerDetailsFromDB.get().getAddress2());
				customerDetailsFromDB.get()
						.setAddress3(customerDetails.getAddress3() != null ? customerDetails.getAddress3()
								: customerDetailsFromDB.get().getAddress3());
				customerDetailsFromDB.get()
						.setEmailId(customerDetails.getEmailId() != null ? customerDetails.getEmailId()
								: customerDetailsFromDB.get().getEmailId());
				customerDetailsFromDB.get()
						.setHouseNo(customerDetails.getHouseNo() != null ? customerDetails.getHouseNo()
								: customerDetailsFromDB.get().getHouseNo());
				customerDetailsFromDB.get().setCity(customerDetails.getCity() != null ? customerDetails.getCity()
						: customerDetailsFromDB.get().getCity());
				customerDetailsFromDB.get()
						.setCity(customerDetails.getBookingUnit() != null ? customerDetails.getBookingUnit() + ""
								: customerDetailsFromDB.get().getCity());
				customerDetailsFromDB.get().setState(customerDetails.getState() != null ? customerDetails.getState()
						: customerDetailsFromDB.get().getState());
				customerDetailsFromDB.get()
						.setCustomerName(customerDetails.getCustomerName() != null ? customerDetails.getCustomerName()
								: customerDetailsFromDB.get().getCustomerName());
				customerDetailsFromDB.get()
						.setPinCode(customerDetails.getPinCode() != null ? customerDetails.getPinCode()
								: customerDetailsFromDB.get().getPinCode());
				customerDetailsFromDB.get().setGstNo(customerDetails.getGstNo() != null ? customerDetails.getGstNo()
						: customerDetailsFromDB.get().getGstNo());
				umCustomersRepo.save(customerDetailsFromDB.get());
			}
			{
				// calling add customer method
				addCustomer(customerDetails, loggedUser);
			}
		} else {
			// calling add customer method
			addCustomer(customerDetails, loggedUser);
		}
		return customerDetails;
	}

	public boolean addCustomer(CustomerDetails customerDetails, LoggedUser loggedUser) {
		try {
			UmCustomers umCustomers = new UmCustomers();
			List<UmCustomers> umCustomersList = umCustomersRepo.getCustomerDetails(customerDetails.getMobileNo());
			BeanUtils.copyProperties(customerDetails, umCustomers);
			umCustomers.setCity(customerDetails.getBookingUnit() + "");
			if (!umCustomersList.isEmpty()) {
				umCustomers = umCustomersList.get(0);
				umCustomers.setChangedBy(loggedUser.getUserId());
				umCustomers.setChangedTs(new Date());
				customerDetails.setCustomerId(umCustomers.getCustomerId());
			} else {
				umCustomers.setCustomerId(UUID.randomUUID().toString());
				umCustomers.setCreatedBy(loggedUser.getUserId());
				umCustomers.setCreatedTs(new Date());
				umCustomers.setMarkAsDelete(false);
			}
			umCustomers.setUserId(customerDetails.getUserId());
			umCustomersRepo.save(umCustomers);
			customerDetails.setCustomerId(umCustomers.getCustomerId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public GenericApiResponse getDashboardCounts(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		Map<String, Integer> countsMap = new HashMap<>();
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_PENDING, 0);
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_APPROVED, 0);
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_REJECT, 0);
		List<Object[]> dashboardStatusCounts = new ArrayList<>();
		if (loggedUser.getCustomerId() == null || loggedUser.getCustomerId().isEmpty()) {
			if ("ADMIN".equalsIgnoreCase(loggedUser.getRoleName())
					|| "SUPER_ADMIN".equalsIgnoreCase(loggedUser.getRoleName())) {
				dashboardStatusCounts = clfOrderItemsRepo.getDashboardCountsByAdminAndSuperAdmin();
			} else if ("APPROVER".equalsIgnoreCase(loggedUser.getRoleName())) {
				dashboardStatusCounts = clfOrderItemsRepo.getDashboardCountsByAdmin(payload.getBookingUnit());
			} else {
				dashboardStatusCounts = clfOrderItemsRepo.getDashboardCountsByLogin(loggedUser.getUserId(),
						payload.getBookingUnit());
			}
		} else {
			dashboardStatusCounts = clfOrderItemsRepo.getDashboardCountsByCustomerId(loggedUser.getCustomerId(),
					payload.getBookingUnit());
		}
		for (Object[] obj : dashboardStatusCounts) {
			countsMap.put((String) obj[0], ((BigInteger) obj[1]).intValue());
		}

		genericApiResponse.setData(countsMap);
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getRatesAndLines(ClassifiedsOrderItemDetails itemDetails) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		ClassifiedRateDetails classifiedRateDetails = new ClassifiedRateDetails();
		List<ClassifiedRates> clfRates = new ArrayList<>();

		List<ClfRates> rates = new ArrayList<ClfRates>();
		rates = clfRatesRepo.getRates1(itemDetails.getAdsType(), itemDetails.getEditions(), itemDetails.getAdsSubType(),
				itemDetails.getCategory(), itemDetails.getSubCategory());
		if (!rates.isEmpty() && rates.size() != itemDetails.getEditions().size()) {
			for (ClfRates cl : rates) {
				itemDetails.getEditions().remove((Integer) cl.getEditionId());
			}

			List<ClfRates> rates1 = clfRatesRepo.getRates(itemDetails.getAdsType(), itemDetails.getEditions(),
					itemDetails.getAdsSubType());
			rates.addAll(rates1);
		}
		if (rates.isEmpty()) {
			rates = clfRatesRepo.getRates(itemDetails.getAdsType(), itemDetails.getEditions(),
					itemDetails.getAdsSubType());
		}

		ClassifiedRates classifiedRates = null;
		if (!rates.isEmpty()) {
			// Read setting values for tax and color ads extra amount on top of regular
			// amount
			List<String> groups = new ArrayList<String>();
			groups.add(GeneralConstants.CLF);
			List<GdSettingsDefinitions> settings = settingRepo.getSettingsBySTypeGrps(1, groups);
			Double colorAdsExtraAmount = 0.0;
			if (!settings.isEmpty()) {
				Map<String, String> settingValues = settings.stream().collect(Collectors.toMap(
						GdSettingsDefinitions::getSettingShortId, GdSettingsDefinitions::getSettingDefaultValue));
				classifiedRateDetails
						.setTax(settingValues.containsKey("CLF_TAX") && settingValues.get("CLF_TAX") != null
								&& !((String) settingValues.get("CLF_TAX")).trim().isEmpty()
										? Double.parseDouble(settingValues.get("CLF_TAX"))
										: 5);
				colorAdsExtraAmount = settingValues.containsKey("CLF_EXTRA_AMT_CLR_ADS")
						&& settingValues.get("CLF_EXTRA_AMT_CLR_ADS") != null
						&& !((String) settingValues.get("CLF_EXTRA_AMT_CLR_ADS")).trim().isEmpty()
								? Double.parseDouble(settingValues.get("CLF_EXTRA_AMT_CLR_ADS"))
								: 25;
				classifiedRateDetails.setMaxLinesAllowed(
						settingValues.containsKey("CLF_MAX_NUM_LINES") && settingValues.get("CLF_MAX_NUM_LINES") != null
								&& !((String) settingValues.get("CLF_MAX_NUM_LINES")).trim().isEmpty()
										? Integer.parseInt(settingValues.get("CLF_MAX_NUM_LINES"))
										: 12);
				classifiedRateDetails.setAgencyCommission(settingValues.containsKey("CLF_AGENCY_COMMISSION")
						&& settingValues.get("CLF_AGENCY_COMMISSION") != null
						&& !((String) settingValues.get("CLF_AGENCY_COMMISSION")).trim().isEmpty()
								? Double.parseDouble(settingValues.get("CLF_AGENCY_COMMISSION"))
								: 15);
			}
			classifiedRateDetails.setClassifiedRates(clfRates);
			for (ClfRates cr : rates) {
				classifiedRates = new ClassifiedRates();
				classifiedRates.setRate(cr.getRate());
				classifiedRates.setExtraLineRate(cr.getExtraLineAmount());
				if (itemDetails.getAdsSubType() != null && itemDetails.getAdsSubType() == 2) {
					classifiedRates.setRate(
							classifiedRates.getRate() + (classifiedRates.getRate() * (colorAdsExtraAmount / 100)));
					classifiedRates.setExtraLineRate(
							cr.getExtraLineAmount() + (cr.getExtraLineAmount() * (colorAdsExtraAmount / 100)));
				}
				classifiedRates.setMinLines(cr.getMinLines());
				classifiedRates.setEditionId(cr.getEditionId());
				clfRates.add(classifiedRates);
			}

			genericApiResponse.setData(classifiedRateDetails);
		} else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("There is no rate card available with the selected details.");
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getPaymentHistory(LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		List<Payments> paymentHistory = new ArrayList<>();
		List<ClfPayments> payments = clfPaymentsRepo.getSelfPaymentHistory(loggedUser.getUserId());
		if (payments.isEmpty()) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("No payment history available");
			return genericApiResponse;
		}
		for (ClfPayments cp : payments) {
			Payments ph = new Payments();
			ph.setAmount(cp.getAmount());
			ph.setInvNo(cp.getInvoiceNo());
			ph.setInvoiceAmount(cp.getTotal());
			ph.setOrderId(cp.getOrderId());
			ph.setPaymentDate(cp.getCreatedTs());
			ph.setPaymentMode(cp.getPaymentMethod());
			ph.setPaymentRefNo(cp.getTransactionId());
			ph.setStatus(cp.getPaymentStatus());
			paymentHistory.add(ph);
		}
		genericApiResponse.setData(paymentHistory);
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse deleteClassified(LoggedUser loggedUser, String itemId) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		ClfOrderItems clfOrderItems = clfOrderItemsRepo.getItemDetailsOnItemId(itemId);
		if (clfOrderItems != null) {
			clfOrderItems.setMarkAsDelete(true);
			clfOrderItems.setChangedBy(loggedUser.getUserId());
			clfOrderItems.setChangedTs(new Date());

			clfOrderItemsRepo.save(clfOrderItems);
			
			ClfOrders clfOrders = clfOrdersRepo.getOrderDetailsOnOrderId(clfOrderItems.getClfOrders().getOrderId());
			if(clfOrders != null) {
				clfOrders.setMarkAsDelete(true);
				clfOrders.setChangedBy(loggedUser.getUserId());
				clfOrders.setChangedTs(new Date());
				
				clfOrdersRepo.save(clfOrders);
			}
			genericApiResponse.setStatus(0);
			genericApiResponse.setMessage("Item Successfully deleted");
		} else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Something went wrong. Please contact our administrator.");
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse viewClfItem(LoggedUser loggedUser, String itemId) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<Object[]> object = clfOrderItemsRepo.viewClfItemDetails(itemId);
		AddToCartRequest addToCartRequest = new AddToCartRequest();
		List<ClassifiedsOrderItemDetails> classifiedsOrderItemDetails = new ArrayList<ClassifiedsOrderItemDetails>();
		ClassifiedsOrderItemDetails clfItemDetails = new ClassifiedsOrderItemDetails();
		PostClassifiedRates postClassifiedRates = new PostClassifiedRates();
		List<Integer> editionIds = new ArrayList<Integer>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> pDates = new ArrayList<String>();
		CustomerDetails customerDetails = new CustomerDetails();
		String customerId = null;

		if (object != null) {
			for (Object[] obj : object) {
				addToCartRequest.setClassifiedType((Integer) obj[2]);
				clfItemDetails.setItemId((String) obj[0]);
				clfItemDetails.setAdsType((Integer) obj[3]);
				clfItemDetails.setAdsSubType((Integer) obj[18]);
				clfItemDetails.setAdId((String) obj[19]);
				clfItemDetails.setScheme((Integer) obj[4]);
				clfItemDetails.setCategory((Integer) obj[5]);
				clfItemDetails.setSubCategory((Integer) obj[6]);
				clfItemDetails.setLang((Integer) obj[7]);
				clfItemDetails.setContent((String) obj[8]);
				clfItemDetails.setCreateTime((Date) obj[10]);
				clfItemDetails.setBookingUnit((Integer) obj[20]);
				clfItemDetails.setCustomerName2((String) obj[21]);
				clfItemDetails.setEditionType((Integer) obj[22]);
				clfItemDetails.setAttachedId((String) obj[23]);
//				clfItemDetails.setChildCategory((Integer) obj[20]);
				// group subgroup childgroup removed
//				clfItemDetails.setGroup((Integer) obj[20]);
//				clfItemDetails.setSubGroup((Integer) obj[21]);
//				clfItemDetails.setChildGroup((Integer) obj[22]);

				postClassifiedRates.setRate((double) ((float) obj[11]));
				postClassifiedRates.setActualLines((Integer) obj[12]);
//				postClassifiedRates.setExtraLineRate((double) ((float) obj[13]) != null ?(double) ((float) obj[13]) : null);
				postClassifiedRates.setTotalAmount((double) ((float) obj[11]));
				addToCartRequest.setPostClassifiedRates(postClassifiedRates);

				customerId = (String) obj[17];

				if (clfItemDetails.getAttachedId() != null && !clfItemDetails.getAttachedId().isEmpty()) {
					List<Attachments> attachements = attachmentsRepo
							.getAttachmentDetails(clfItemDetails.getAttachedId());
					if (attachements != null && !attachements.isEmpty()) {
						clfItemDetails.setAttachmentName(attachements.get(0).getAttachName());
					}
				}

				classifiedsOrderItemDetails.add(clfItemDetails);
				addToCartRequest.setItemList(classifiedsOrderItemDetails);
			}

			List<Object[]> cfd = clfPublishDatesRepo.getPublishDatesOnItemId(itemId);
			if (cfd != null) {
				for (Object[] cf : cfd) {
					String pDateFormat = dateFormat.format(cf[0]);
					pDates.add(pDateFormat);
				}
				clfItemDetails.setPublishDates(pDates);
			}

			List<Object[]> editionDetails = clfEditionsRepo.getEditionIdOnItemId(itemId);
			if (editionDetails != null) {
				for (Object[] ed : editionDetails) {
					editionIds.add((Integer) ed[0]);
				}
				clfItemDetails.setEditions(editionIds);
			}

			if (customerId != null) {
				List<UmCustomers> umCustomers = umCustomersRepo.getCustomerDetailsOnOrderId(customerId);
				if (umCustomers != null) {
					for (UmCustomers umc : umCustomers) {
						customerDetails.setCustomerId(umc.getCustomerId());
						customerDetails.setCustomerName(umc.getCustomerName());
						customerDetails.setMobileNo(umc.getMobileNo());
						customerDetails.setEmailId(umc.getEmailId());
						customerDetails.setAddress1(umc.getAddress1());
						customerDetails.setAddress2(umc.getAddress2());
						customerDetails.setAddress3(umc.getAddress3());
						customerDetails.setPinCode(umc.getPinCode());
						customerDetails.setOfficeLocation(umc.getOfficeLocation());
						customerDetails.setPanNumber(umc.getPanNumber());
						customerDetails.setGstNo(umc.getGstNo());
						customerDetails.setAadharNumber(umc.getAadharNumber());
						customerDetails.setHouseNo(umc.getHouseNo());
						customerDetails.setState(umc.getState());
						customerDetails.setCity(umc.getCity());
					}
					addToCartRequest.setCustomerDetails(customerDetails);
				}
			}
			genericApiResponse.setData(addToCartRequest);
			genericApiResponse.setStatus(0);
		} else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Something went wrong. Please contact our administrator.");
		}
		return genericApiResponse;
	}

	@SuppressWarnings("unused")
	@Override
	public GenericApiResponse approveClassifieds(LoggedUser loggedUser, ClassifiedStatus payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			String email = "";
			String adId = "";
//			if ("CONTENT_TYPE".equalsIgnoreCase(payload.getApprovalType())) {
			clfOrderItemsRepo.updateClf(payload.getStatus(), loggedUser.getUserId(), new Date(), payload.getItemId());

//				 if(!"APPROVED".equalsIgnoreCase(payload.getStatus())){
			List<Object[]> cusDetails = clfOrdersRepo.getCustomerData(payload.getOrderId());
			for (String orderId : payload.getOrderId()) {
				Optional<Object[]> obj = cusDetails.stream().filter(f -> f[0].equals(orderId)).findFirst();
				if (obj.isPresent()) {
					Object[] ob = obj.get();
					email = ob[2] + "";
					adId = ob[3] + "";
				}
//					 sendMail(loggedUser,orderId,payload,email,adId);
				List<String> orderIdss = Arrays.asList(orderId);
				ClfOrderItems openOrderItemsByOrderId = clfOrderItemsRepo.getOpenOrderItemsByOrderId(orderId);
				if(openOrderItemsByOrderId != null) {
					if(openOrderItemsByOrderId.getAdId().contains("SSP")) {
						Map<String, ErpClassifieds> erpClassifiedsDAMap = displayServiceImpl.getClassifiedOrderDetailsForSSPErp(orderIdss);
						BillDeskPaymentResponseModel billDeskPaymentResponseModel = new BillDeskPaymentResponseModel();
						if(erpClassifiedsDAMap != null && !erpClassifiedsDAMap.isEmpty()) {																
										displayService.sendMailToCustomerForSSPVendor(erpClassifiedsDAMap, billDeskPaymentResponseModel, loggedUser,null);
										genericApiResponse.setStatus(0);
										genericApiResponse.setMessage("The email has been sent. Kindly check it.");
						}else {
							genericApiResponse.setStatus(1);
							genericApiResponse.setMessage("Something went wrong. Please contact our administrator.");
						}
						
					
					}else {
						Map<String, ErpClassifieds> erpClassifiedsMap = this.getOrderDetailsForErp(orderIdss);
						this.sendMailToCustomer(erpClassifiedsMap, loggedUser, orderId, payload.getStatus(),
								payload.getComments());
					}
				}
				

			}
//				 }
			genericApiResponse.setStatus(0);
			if ("APPROVED".equalsIgnoreCase(payload.getStatus())) {
				genericApiResponse.setMessage("Content Successfully Approved");
			} else {
				genericApiResponse.setMessage("Content Successfully Disapproved and Refund will be initiated");
			}
//			} else {
			clfOrdersRepo.updateClfOnOrderIds(payload.getStatus(), loggedUser.getUserId(), new Date(),
					payload.getOrderId(),payload.getComments());

//				List<Object[]> cusDetails = clfOrdersRepo.getCustomerData(payload.getOrderId());
//					for(String orderId : payload.getOrderId()) {
//						Optional<Object[]> obj = cusDetails.stream()
//						.filter(f -> f[0].equals(orderId))
//						.findFirst();
//						if(obj.isPresent()) {
//							Object[] ob = obj.get();
//							email = ob[2]+"";
//							adId = ob[3]+"";
//						}
//					 sendMail(loggedUser,orderId,payload,email,adId);
//					}

			genericApiResponse.setStatus(0);
			if ("APPROVED".equalsIgnoreCase(payload.getStatus())) {
//					genericApiResponse.setMessage("Payment Successfully Approved");
				ClassifiedStatus classifiedStatus = new ClassifiedStatus();
				classifiedStatus.setOrderId(payload.getOrderId());
				this.syncronizeSAPData(commonService.getRequestHeaders(), classifiedStatus);
			} else {
				
				List<ClfPaymentsRefund> transactionDetail = this.getTransactionDetail(payload.getOrderId());
				for(ClfPaymentsRefund paymentsRefund: transactionDetail) {
					GenericApiResponse apiResp = paymentService.prepareRequestForCreateRefund(paymentsRefund, loggedUser);
					if(apiResp!=null&& apiResp.getStatus() == 0 && apiResp.getData() != null) {
						ResponseEntity<?> showbilldeskModel = paymentService.saveEncodedResponse(apiResp.getData()+"",loggedUser);
						apiResp = (GenericApiResponse) showbilldeskModel.getBody();
					}
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Something went wrong. Please contact our administrator.");
		}
		return genericApiResponse;
	}

	private List<ClfPaymentsRefund> getTransactionDetail(List<String> orderId) {
		List<Object[]> paymentsDetialsByOrderId = clfPaymentResponseTrackingRepo.getPaymentsDetailsByOrderId(orderId);
		List<ClfPaymentsRefund> clfPaymentsTrackings =new ArrayList<ClfPaymentsRefund>();
		for(Object[] obj:paymentsDetialsByOrderId) {
			ClfPaymentsRefund clfPaymentsTracking=new ClfPaymentsRefund();
			clfPaymentsTracking.setTransactionId((String) obj[0]);
			clfPaymentsTracking.setOrderId((String) obj[1]);
			clfPaymentsTracking.setAmount(Double.parseDouble((String) obj[2]));
			clfPaymentsTracking.setPaymentChildId((String) obj[3]);
			clfPaymentsTracking.setTransactionDate((String) obj[4]);
			clfPaymentsTrackings.add(clfPaymentsTracking);
		}
		
		return clfPaymentsTrackings;
	}

	private void sendMail(LoggedUser loggedUser, String orderId, ClassifiedStatus payload, String customerEmail,
			String adId) {
		// TODO Auto-generated method stub
		try {
			List<String> adminEmail = new ArrayList<String>();
			List<UmOrgUsers> umOrgU = userDao.getAdminAndHqUsers();
			for (UmOrgUsers umOrg : umOrgU) {
				if ("ADMIN".equalsIgnoreCase(umOrg.getUmOrgRoles().getRoleShortId()))
					adminEmail.add(umOrg.getUmUsers().getEmail());
			}

			String adminEmails = adminEmail.stream().map(Object::toString).collect(Collectors.joining(","));
			String[] adminCcMails = { adminEmails };

			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
			emailTo.setTo(customerEmail);
			emailTo.setBcc(adminCcMails);
			if ("CONTENT_TYPE".equalsIgnoreCase(payload.getApprovalType())) {
				emailTo.setTemplateName(GeneralConstants.CONTENT_REJECT);
			} else if ("PAYMENT_TYPE".equalsIgnoreCase(payload.getApprovalType())) {
				if ("APPROVED".equalsIgnoreCase(payload.getStatus())) {
					emailTo.setTemplateName(GeneralConstants.PAYMENT_APPROVE);
				} else {
					emailTo.setTemplateName(GeneralConstants.PAYMENT_REJECT);
				}
			}
			emailTo.setOrgId("1000");

			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
			mapProperties.put("userId", loggedUser.getLogonId());// new userName
			mapProperties.put("comments", payload.getComments());
			mapProperties.put("adId", adId);
			emailTo.setTemplateProps(mapProperties);
			sendService.sendCommunicationMail(emailTo, emailConfigs);

		} catch (Exception e) {
			e.printStackTrace();
		}

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
					gdCurrentYearNumberSeries.setId(UUID.randomUUID().toString());
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

	public static void main(String a[]) throws MalformedURLException, DocumentException, IOException {
		ClassifiedServiceImpl cs = new ClassifiedServiceImpl();
		cs.generateSeries("LINE_ADS");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public GenericApiResponse syncronizeSAPData(GenericRequestHeaders requestHeaders, ClassifiedStatus orderIds) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			if (orderIds != null) {
				Map<String, ErpClassifieds> erpClassifieds = this.getOrderDetailsForErp(orderIds.getOrderId());
				if (!erpClassifieds.isEmpty()) {
					Map<String, Object> payloadJson = new HashMap<String, Object>();
					payloadJson.put("userId", requestHeaders.getLoggedUser().getUserId());
					payloadJson.put("orderId", orderIds.getOrderId());
					payloadJson.put("orgId", requestHeaders.getOrgId());
					payloadJson.put("data", erpClassifieds);
					payloadJson.put("orgOpuId", requestHeaders.getOrgOpuId());
					payloadJson.put("action", "Classified_order");
					boolean flag = erpService.processClassifiedCreationFtpFiles(payloadJson, erpClassifieds);
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
			apiResponse.setMessage(properties.getProperty("GEN_002"));
			apiResponse.setErrorcode("GEN_002");
		}
		return apiResponse;
	}

	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public Map<String, ErpClassifieds> getOrderDetailsForErp(List<String> orderIds) {
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
//			String query = "select itm.item_id , itm.order_id , itm.classified_type,itm .classified_ads_type,itm.scheme as itm_scheme,itm.category,itm.subcategory,itm.lang,itm.clf_content,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id , co.user_type_id,gct.type,gct.erp_ref_id as gct_erp_ref_id, gcat.ads_type,gcat.erp_ref_id as gcat_erp_ref_id,gcast.ads_sub_type,gcast.erp_ref_id as gcast_erp_ref_id,gcs.scheme as gcs_scheme,gcs.erp_ref_id as gcs_erp_ref_id,gcc.classified_category ,gcs2.classified_subcategory,gcl.language, coir.total_amount,coir.line_count,itm.category_group,gg.classified_group,gg.erp_ref_id as gg_erp_ref_id,gcc.erp_ref_id as gcc_erp_ref_id,gcs2.erp_ref_id as gcs2_erp_ref_id,itm.sub_group,gsg.classified_sub_group,gsg.erp_ref_id as gsg_erp_ref_id,itm.child_group,gcg.classified_child_group,gcg.erp_ref_id as gcg_erp_ref_id from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_group gg on itm.category_group = gg.id inner join gd_classified_sub_group gsg on itm.sub_group = gsg.id inner join gd_classified_child_group gcg on itm.child_group = gcg.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on itm.order_id = co.order_id where itm.order_id in ('"
//					+ joinedOrderIds + "')and itm.mark_as_delete = false";
			String query = "select itm.item_id , itm.order_id , itm.classified_type,itm .classified_ads_type,itm.scheme as itm_scheme,itm.category,itm.subcategory,itm.lang,itm.clf_content,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id , co.user_type_id,gct.type,gct.erp_ref_id as gct_erp_ref_id, gcat.ads_type,gcat.erp_ref_id as gcat_erp_ref_id,gcast.ads_sub_type,gcast.erp_ref_id as gcast_erp_ref_id,gcs.scheme as gcs_scheme,gcs.erp_ref_id as gcs_erp_ref_id,gcc.classified_category ,gcs2.classified_subcategory,gcl.language, coir.total_amount,coir.line_count,gcc.erp_ref_id as gcc_erp_ref_id,gcs2.erp_ref_id as gcs2_erp_ref_id,co.booking_unit,gcc.product_hierarchy,bu.sales_office,bu.booking_location,bu.sold_to_party,co.customer_name,itm.status,bu.booking_unit_email,coir.gst_total,coir.rate,coir.extra_line_rate,coir.agency_commition,cprt.order_id as ro_order_id,coir.lines from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on itm.order_id = co.order_id inner join booking_units bu on co.booking_unit = bu.booking_code inner join clf_payment_response_tracking cprt on itm.order_id = cprt.sec_order_id where itm.order_id in ('"
					+ joinedOrderIds + "')and itm.mark_as_delete = false";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

			for (Object[] objs : classifiedList) {
				ErpClassifieds classified = new ErpClassifieds();
				classified.setItemId((String) objs[0]);
				classified.setOrderId((String) objs[1]);
				classified.setClassifiedType((Integer) objs[2]);
				classified.setClassifiedAdsType((Integer) objs[3]);
				classified.setScheme((Integer) objs[4]);
				classified.setCategory((Integer) objs[5]);
				classified.setSubCategory((Integer) objs[6]);
				classified.setLang((Integer) objs[7]);
				classified.setContent((String) objs[8]);
				classified.setCreatedBy((Integer) objs[9]);
				classified.setCreatedTs(CommonUtils.dateFormatter((Date) objs[10], "yyyyMMddHHmmss"));
				classified.setCreatedDate(CommonUtils.dateFormatter((Date) objs[10], "yyyyMMdd"));
				classified.setBookingDate(CommonUtils.dateFormatter((Date) objs[10], "yyyy-MM-dd HH:mm:ss"));
				classified.setChangedBy((Integer) objs[11]);
				classified.setChangedTs(objs[12] != null ? CommonUtils.dateFormatter((Date) objs[12], "ddMMyyyy") : "");
				classified.setAdId((String) objs[13]);
				classified.setClassifiedAdsSubType((Integer) objs[14]);
				classified.setCustomerId((String) objs[15]);
				classified.setUserTypeId((Integer) objs[16]);
				classified.setClassifiedTypeStr((String) objs[17]);
				classified.setClassifiedTypeErpRefId((String) objs[18]);
				classified.setAdsType((String) objs[19]);
				classified.setAdsTypeErpRefId((String) objs[20]);
				classified.setAdsSubType((String) objs[21]);
				classified.setAdsSubTypeErpRefId((String) objs[22]);
				classified.setSchemeStr((String) objs[23]);
				classified.setSchemeErpRefId((String) objs[24]);
				classified.setCategoryStr((String) objs[25]);
				classified.setSubCategoryStr((String) objs[26]);
				classified.setLangStr((String) objs[27]);
				Double val = (Double.valueOf(df.format(objs[28])));
//				classified.setPaidAmount(new BigDecimal(df1.format(val)));
				classified.setPaidAmount(val);
				classified.setLineCount(Integer.valueOf((Short) objs[29]));
				classified.setCategoryErpRefId((String) objs[30]);
				classified.setSubCategoryErpRefId((String) objs[31]);
				classified.setBookingUnit((Integer) objs[32]);
				classified.setProductHierarchy((String) objs[33]);
				classified.setSalesOffice((String) objs[34]);
				classified.setBookingUnitStr((String) objs[35]);
				classified.setCity((String) objs[35]);
//				if("2".equalsIgnoreCase(classified.getUserTypeId()+"")) {
				classified.setSoldToParty((String) objs[36]);
//				}
				classified.setCustomerName2((String) objs[37]);
				classified.setContentStatus((String) objs[38]);
				classified.setBookingUnitEmail((String) objs[39]);

				Double gstVal = (Double.valueOf(df.format(objs[40])));
				classified.setGstTotalAmount(Double.valueOf(df1.format(gstVal)));
				Double rate = (Double.valueOf(df.format(objs[41])));
				classified.setRate(Double.valueOf(df1.format(rate)));
				Double extraLineRateAmount = (Double.valueOf(df.format(objs[42])));
				classified.setExtraLineRateAmount(Double.valueOf(df1.format(extraLineRateAmount)));
				Double agencyCommition = (Double.valueOf(df.format(objs[43])));
				classified.setAgencyCommition(Double.valueOf(df1.format(agencyCommition)));
				classified.setRoOrderId((String) objs[44]);
				classified.setMinLines((Integer) objs[45]);
				if(classified.getLineCount() >= classified.getMinLines()) {
					classified.setLineCount1(Integer.valueOf((Short) objs[29]));
				}else {
					classified.setLineCount1((Integer) objs[45]);
				}

//				classified.setGstTotalAmount((double) ((float) objs[40]));
//				classified.setRate((double) ((float) objs[41]));
//				classified.setExtraLineRateAmount((double) ((float) objs[42]));
//				classified.setAgencyCommition((double) ((float) objs[43]));
				classified.setKeyword("Classified Order");
				classified.setTypeOfCustomer("01");
				classified.setCreatedTime(CommonUtils.dateFormatter((Date) objs[10], "HHmmss"));
				classified.setOrderIdentification("02");

				itemIds.add((String) objs[0]);
				customerIds.add((String) objs[15]);
				createdByIds.add((Integer) objs[9]);
				classifiedsMap.put((String) objs[0], classified);
			}

			if (itemIds != null && !itemIds.isEmpty()) {
				List<Object[]> editionsList = clfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
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
							ErpClassifieds classified = classifiedsMap.get((String) clObj[0]);
							classified.setEditions(edditions);
							classified.setEditionsErpRefId(edditionsErpRefIds);
							classifiedsMap.put((String) clObj[0], classified);
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
							ErpClassifieds classified = classifiedsMap.get((String) clObj[0]);
							classified.setPublishDates(publishDates);
							classifiedsMap.put((String) clObj[0], classified);
						}
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
								erpData.getValue().setHouseNo("1" + umCustom.getHouseNo());
								if (umCustom != null && !umCustom.getCity().isEmpty()) {
									cityIds.add(Integer.parseInt(umCustom.getCity()));
								}
							}

						});
					}
//					if(!cityIds.isEmpty()) {
//						List<GdCity> gdCityDetails = gdCityRepo.getCityDetails(cityIds);
//						if (!gdCityDetails.isEmpty()) {
//							classifiedsMap.entrySet().forEach(erpData -> {
//								Optional<GdCity> gd = gdCityDetails.stream()
//										.filter(f -> String.valueOf(f.getId()).equals(erpData.getValue().getCity()))
//										.findFirst();
//								if (gd.isPresent()) {
//									GdCity gdCity = gd.get();
//									erpData.getValue().setCity(gdCity.getCity());
//								}
//							});
//						}
//					}
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
								erpData.getValue().setLogonId(umUser.getLogonId());
//								if (!"2".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
//									erpData.getValue().setSoldToParty(umUser.getSoldToParty());
//								}
								if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
									erpData.getValue().setCustomerName(umUser.getFirstName());
									erpData.getValue().setMobileNumber(umUser.getMobile());
									erpData.getValue().setEmailId(umUser.getEmail());
									erpData.getValue().setAddress1(umUser.getAddress());
									erpData.getValue().setState(umUser.getState());
									erpData.getValue().setSoldToParty(umUser.getLogonId());
								}
								if("1".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
									erpData.getValue().setCreatedByEmail(umUser.getEmail());
								}
								if("2".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classifiedsMap;
	}

	@Override
	public GenericApiResponse downloadAdsPdfDocument(DashboardFilterTo payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		LinkedHashMap<String, List<Object>> dataMap = new LinkedHashMap<>();
		List<String> itemIds = new ArrayList<>();
		LinkedHashMap<String, List<String>> itemIdToEditionNameMap = new LinkedHashMap<>();
		List<Object[]> adsdata = clfOrderItemsRepo.getAdsTobeDownload(
				CommonUtils.dateFormatter(payload.getPublishedDate(), "yyyy-MM-dd"), payload.getBookingUnit());

		for (Object[] obj1 : adsdata) {
			itemIds.add((String) obj1[2]);
		}
		List<Object[]> clfEditions = clfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
		if (clfEditions != null && !clfEditions.isEmpty()) {
			for (Object[] clf : clfEditions) {
				if (itemIdToEditionNameMap.containsKey(clf[0])) {
					itemIdToEditionNameMap.get((String) clf[0]).add((String) clf[2]);
				} else {
					List<String> editionNames = new ArrayList<>();
					editionNames.add((String) clf[2]);
					itemIdToEditionNameMap.put(((String) clf[0]), editionNames);
				}
			}
		}
		for (Object[] obj : adsdata) {
//			itemIds.add((String)obj[2]);
			if (dataMap.containsKey((String) obj[0])) {
				dataMap.get((String) obj[0]).add((String) obj[1]);
			} else {
				List<Object> data = new ArrayList<>();
				data.add((String) obj[1]);
				dataMap.put((String) obj[0] + " - " + obj[4] + " - " + itemIdToEditionNameMap.get(obj[2]) + " - "
						+ obj[5] + " Lines - " + obj[6], data);
			}
		}
		if (!dataMap.isEmpty()) {
			if (payload.isDownloadConfimFlag()) {
//				clfOrderItemsRepo.updateDownloadConfirmStatus(itemIds);
//				clfPublishDatesRepo.updateDownloadConfirmStatus(itemIds,
//						CommonUtils.dateFormatter(payload.getPublishedDate(), "yyyy-MM-dd"));
				
				clfPublishDatesRepo.updateDownloadStatus(itemIds);

				apiResponse.setStatus(0);
				apiResponse.setMessage("Updated Successfully");
			} else {
				byte[] pdfData = classifiedDownloadService.createTableText(dataMap);
//				byte[] pdfData = classifiedDownloadService.creteTablePdf(dataMap);
				apiResponse.setStatus(0);
				apiResponse.setMessage("Success");
				apiResponse.setData(pdfData);
			}
		} else {
			apiResponse.setStatus(1);
			apiResponse.setMessage("No data available");
		}
		return apiResponse;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void getAddsForCurrentDate() {
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		try {
			Map<String, ErpClassifieds> classifiedsMap = new HashMap<>();
			List<String> itemIds = new ArrayList<String>();
			String currentDate = CommonUtils.dateFormatter(new Date(), "yyyy-MM-dd");
//			String query = "select itm.item_id,itm.order_id,itm.classified_type,itm.classified_ads_type,itm.scheme AS itm_scheme,itm.category,itm.subcategory,itm.lang,itm.clf_content,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id,co.user_type_id,gct.type,gcat.ads_type,gcast.ads_sub_type,gcs.scheme AS gcs_scheme,gcc.classified_category,gcs2.classified_subcategory,gcl.language,coir.total_amount,coir.line_count,itm.category_group,gg.classified_group,itm.sub_group,gsg.classified_sub_group,itm.child_group,gcg.classified_child_group,cpd.publish_date,um.email_id,um.mobile_no FROM clf_order_items itm INNER JOIN gd_classified_types gct ON itm.classified_type = gct.id INNER JOIN gd_classified_ads_types gcat ON itm.classified_ads_type = gcat.id INNER JOIN gd_classified_ads_sub_types gcast ON itm.classified_ads_sub_type = gcast.id INNER JOIN gd_classified_languages gcl ON itm.lang = gcl.id INNER JOIN clf_order_item_rates coir ON itm.item_id = coir.item_id INNER JOIN clf_orders co ON itm.order_id = co.order_id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_group gg on itm.category_group = gg.id inner join gd_classified_sub_group gsg on itm.sub_group = gsg.id inner join gd_classified_child_group gcg on itm.child_group = gcg.id INNER JOIN clf_publish_dates cpd ON itm.item_id = cpd.item_id INNER JOIN um_customers um ON co.customer_id = um.customer_id where cpd.publish_date = TO_DATE('" +currentDate+"', 'YYYY-MM-DD') AND itm.download_status = true AND itm.mark_as_delete = false";
			String query = "select itm.item_id,itm.order_id,itm.classified_type,itm.classified_ads_type,itm.scheme AS itm_scheme,itm.category,itm.subcategory,itm.lang,itm.clf_content,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id,co.user_type_id,gct.type,gcat.ads_type,gcast.ads_sub_type,gcs.scheme AS gcs_scheme,gcc.classified_category,gcs2.classified_subcategory,gcl.language,coir.total_amount,coir.line_count,cpd.publish_date,um.email_id,um.mobile_no,bu.sales_office FROM clf_order_items itm INNER JOIN gd_classified_types gct ON itm.classified_type = gct.id INNER JOIN gd_classified_ads_types gcat ON itm.classified_ads_type = gcat.id INNER JOIN gd_classified_ads_sub_types gcast ON itm.classified_ads_sub_type = gcast.id INNER JOIN gd_classified_languages gcl ON itm.lang = gcl.id INNER JOIN clf_order_item_rates coir ON itm.item_id = coir.item_id INNER JOIN clf_orders co ON itm.order_id = co.order_id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id INNER JOIN clf_publish_dates cpd ON itm.item_id = cpd.item_id INNER JOIN um_customers um ON co.customer_id = um.customer_id inner join booking_units bu on co.booking_unit = bu.booking_code where cpd.publish_date = TO_DATE('"
					+ currentDate + "', 'YYYY-MM-DD') AND cpd.download_status = true AND itm.mark_as_delete = false";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			for (Object[] objs : classifiedList) {
				ErpClassifieds classified = new ErpClassifieds();
				classified.setItemId((String) objs[0]);
				classified.setOrderId((String) objs[1]);
				classified.setClassifiedType((Integer) objs[2]);
				classified.setClassifiedAdsType((Integer) objs[3]);
				classified.setScheme((Integer) objs[4]);
				classified.setCategory((Integer) objs[5]);
				classified.setSubCategory((Integer) objs[6]);
				classified.setLang((Integer) objs[7]);
				classified.setContent((String) objs[8]);
				classified.setCreatedBy((Integer) objs[9]);
				classified.setCreatedTs(CommonUtils.dateFormatter((Date) objs[10], "yyyyMMddHHmmss"));
				classified.setCreatedDate(CommonUtils.dateFormatter((Date) objs[10], "yyyyMMdd"));
				classified.setBookingDate(CommonUtils.dateFormatter((Date) objs[10], "yyyy-MM-dd HH:mm:ss"));
				classified.setChangedBy((Integer) objs[11]);
				classified.setChangedTs(objs[12] != null ? CommonUtils.dateFormatter((Date) objs[12], "ddMMyyyy") : "");
				classified.setAdId((String) objs[13]);
				classified.setClassifiedAdsSubType((Integer) objs[14]);
				classified.setCustomerId((String) objs[15]);
				classified.setUserTypeId((Integer) objs[16]);
				classified.setClassifiedTypeStr((String) objs[17]);
				classified.setAdsType((String) objs[18]);
				classified.setAdsSubType((String) objs[19]);
				classified.setSchemeStr((String) objs[20]);
				classified.setCategoryStr((String) objs[21]);
				classified.setSubCategoryStr((String) objs[22]);
				classified.setLangStr((String) objs[23]);
//				classified.setPaidAmount(new BigDecimal((Float) objs[24]));
				classified.setPaidAmount(((Float) objs[24]).doubleValue());
				classified.setLineCount(Integer.valueOf((Short) objs[25]));
				// group subgroup childgroup removed
//				classified.setGroup((Integer) objs[26]);
//				classified.setGroupStr((String) objs[27]);
//				classified.setSubGroup((Integer) objs[28]);
//				classified.setSubGroupStr((String) objs[29]);
//				classified.setChildGroup((Integer) objs[30]);
//				classified.setChildGroupStr((String) objs[31]);
				classified.setPublishedDate(CommonUtils.dateFormatter((Date) objs[26], "yyyy-MM-dd"));
				classified.setEmailId((String) objs[27]);
				classified.setMobileNumber((String) objs[28]);
				classified.setSalesOffice((String) objs[29]);
				classified.setCreatedTime(CommonUtils.dateFormatter((Date) objs[10], "HHmmss"));

				itemIds.add((String) objs[0]);
				classifiedsMap.put((String) objs[0], classified);
			}

			List<Object[]> editionsList = clfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
			for (Object[] clObj : editionsList) {
				if (classifiedsMap.containsKey((String) clObj[0])) {
					if (classifiedsMap.get((String) clObj[0]).getEditions() != null) {
						classifiedsMap.get((String) clObj[0]).getEditions().add((String) clObj[2]);
					} else {
						List<String> edditions = new ArrayList<>();
						edditions.add((String) clObj[2]);
						ErpClassifieds classified = classifiedsMap.get((String) clObj[0]);
						classified.setEditions(edditions);
						classifiedsMap.put((String) clObj[0], classified);
					}
				}
			}
			sendSchedularMail(classifiedsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sendSchedularMail(Map<String, ErpClassifieds> classifiedsMap) {
		try {
//			List<String> adminEmail = new ArrayList<String>();
//			List<UmOrgUsers> umOrgU = userDao.getAdminAndHqUsers();
//			for(UmOrgUsers umOrg : umOrgU) {
//				if("ADMIN".equalsIgnoreCase(umOrg.getUmOrgRoles().getRoleShortId()))
//				adminEmail.add(umOrg.getUmUsers().getEmail());
//			}

//			String adminEmails = adminEmail.stream().map(Object::toString)
//					.collect(Collectors.joining(","));
//			String[] adminCcMails = {adminEmails};

			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
//			emailTo.setBcc(adminCcMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			emailTo.setTemplateName(GeneralConstants.PUBLISH_AD);

			classifiedsMap.entrySet().forEach(erpData -> {
				emailTo.setTo(erpData.getValue().getEmailId());
				mapProperties.put("adId", erpData.getValue().getAdId());
				mapProperties.put("adType", erpData.getValue().getAdsType());
				mapProperties.put("adSubType", erpData.getValue().getAdsSubType());
				mapProperties.put("pack", erpData.getValue().getSchemeStr());
				mapProperties.put("lineCount", erpData.getValue().getLineCount());
				mapProperties.put("amount", erpData.getValue().getPaidAmount());
				mapProperties.put("langStr", erpData.getValue().getLangStr());
				mapProperties.put("category", erpData.getValue().getCategoryStr());
				mapProperties.put("subCategory", erpData.getValue().getSubCategoryStr());
				mapProperties.put("printAdMatter", erpData.getValue().getContent());
				mapProperties.put("bookingDate", erpData.getValue().getBookingDate());
				mapProperties.put("publishDate", erpData.getValue().getPublishedDate());
				mapProperties.put("subject_edit", true);

				if ("11".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isTelangana", "inline-block");
				} else {
					mapProperties.put("isTelangana", "none");
				}
				if ("25".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isAndhraPradesh", "inline-block");
				} else {
					mapProperties.put("isAndhraPradesh", "none");
				}

				List<String> editionsList = erpData.getValue().getEditions();
				String editions = editionsList.stream().map(Object::toString).collect(Collectors.joining(","));
				mapProperties.put("editions", editions);
				emailTo.setTemplateProps(mapProperties);

				sendService.sendCommunicationMail(emailTo, emailConfigs);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getDownloadStatusList(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		LocalDate currentDate = LocalDate.now();
		LocalDate nextDay = currentDate.plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedNextDay = nextDay.format(formatter);

//		String query = "select uc.mobile_no, itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status as itm_download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,cpd.publish_date,cpd.download_status as cpd_download_status from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join um_customers uc on co.customer_id = uc.customer_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_publish_dates cpd on itm.item_id = cpd.item_id where itm.mark_As_Delete = false and itm.status = 'APPROVED' and co.payment_status = 'APPROVED' and cp.payment_status = 'success'";
		String query = "select co.user_type_id, itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status as itm_download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,cpd.publish_date,cpd.download_status as cpd_download_status,itm.created_by from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_publish_dates cpd on itm.item_id = cpd.item_id where itm.mark_As_Delete = false and itm.status = 'APPROVED' and co.payment_status = 'APPROVED' and cp.payment_status = 'success'";
		if (payload.getType() != null && !payload.getType().isEmpty() && !"ALL".equalsIgnoreCase(payload.getType())) {
			query = query + " and co.payment_status ='" + payload.getType() + "'";
		}
		if (payload.getPublishedDate() != null && !payload.getPublishedDate().isEmpty()) {
			query = query + " and to_char(cpd.publish_date,'DD/MM/YYYY') = '" + payload.getPublishedDate() + "'";
		}
		if (payload.getDownloadStatus() != null && !payload.getDownloadStatus().isEmpty()) {
			query = query + " and cpd.download_status =" + payload.getDownloadStatus() + "";
		}
		if (payload.getBookingUnit() != null && payload.getBookingUnit() != 0) {
			query = query + " and co.booking_unit = " + payload.getBookingUnit() + "";
		}
		query = query + " and to_char(cpd.publish_date,'DD/MM/YYYY') = '" + formattedNextDay + "'";
		query = query + " ORDER BY itm.ad_id DESC ";
		classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

		List<Classifieds> classifiedDetails = new ArrayList<Classifieds>();
		List<String> itemIds = new ArrayList<String>();
		List<Integer> createdByForAgency = new ArrayList<Integer>();
		for (Object[] objs : classifiedList) {
			Classifieds classified = new Classifieds();
//			classified.setContactNo((String)objs[0]);
			classified.setRequestedDate(CommonUtils.dateFormatter((Date) objs[1], "dd-MM-yyyy"));
			classified.setCategoryId(((Integer) objs[2]).intValue());
			classified.setCategory((String) objs[3]);
			Double val = (Double.valueOf(df.format(objs[4])));
			classified.setPaidAmount(new BigDecimal(df1.format(val)));
//			classified.setPaidAmount(new BigDecimal((Float)objs[4]));
			classified.setApprovalStatus((String) objs[5]);
			classified.setPaymentStatus((String) objs[12]);
			classified.setClfPaymentStatus((String) objs[6]);
//			classified.setDownloadStatus(objs[7] == null ? false : (Boolean)objs[7]);
			classified.setMatter((String) objs[8]);
			classified.setItemId((String) objs[9]);
			classified.setOrderId((String) objs[10]);
			classified.setSubCategory((String) objs[11]);
			classified.setScheme((String) objs[14]);
			classified.setAdId((String) objs[15]);
			classified.setPublishedDate(CommonUtils.dateFormatter((Date) objs[16], "dd-MM-yyyy"));
			classified.setDownloadStatus(objs[17] == null ? false : (Boolean) objs[17]);
			itemIds.add((String) objs[9]);
			if ("3".equalsIgnoreCase(objs[0] + "")) {
				classified.setCreatedBy((Integer) objs[18]);
				createdByForAgency.add((Integer) objs[18]);
			}
			classifiedDetails.add(classified);
		}

		if (itemIds != null && !itemIds.isEmpty()) {
			String itemIds1 = String.join("','", itemIds);
			String query1 = "select itm.item_id,co.order_id,uc.mobile_no from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id where itm.mark_as_delete = false and co.mark_as_delete = false and itm.item_id in ('"
					+ itemIds1 + "')";
			List<Object[]> userTypeList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
			if (!userTypeList.isEmpty()) {
				for (Object[] obj : userTypeList) {
					List<Classifieds> gd = classifiedDetails.stream().filter(f -> (f.getItemId()).equals(obj[0]))
							.collect(Collectors.toList());
					if (!gd.isEmpty()) {
						for (Classifieds cls : gd) {
							if (cls.getContactNo() == null) {
								cls.setContactNo((String) obj[2]);
							}
						}
					}
				}
			}
		}
		if (createdByForAgency != null && !createdByForAgency.isEmpty()) {
			List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByForAgency, false);
			if (!umUsers.isEmpty()) {
				for (Classifieds cls : classifiedDetails) {
					UmUsers user = umUsers.stream().filter(f -> (f.getUserId()).equals(cls.getCreatedBy())).findFirst()
							.orElse(null);
					if (user != null) {
						cls.setContactNo(user.getMobile());
					}
				}
			}
		}

		genericApiResponse.setData(classifiedDetails);
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getPendingPaymentList(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);

//		String query = "select uc.mobile_no, itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,co.erp_order_id from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join um_customers uc on co.customer_id = uc.customer_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id where itm.mark_As_Delete = false and co.mark_as_delete = false and co.order_status ='OPEN'";
		String query = "select co.user_type_id,itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,co.erp_order_id,itm.created_by from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id where itm.mark_As_Delete = false and co.mark_as_delete = false and co.order_status ='OPEN'";
		if (payload.getCategoryId() != null) {
			query = query + " and itm.category = " + payload.getCategoryId() + "";
		}
		if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()) {
			query = query + " and to_char(itm.created_ts,'DD/MM/YYYY') = '" + payload.getRequestedDate() + "'";
		}
		if (!"ADMIN".equalsIgnoreCase(loggedUser.getRoleName())
				&& !"SUPER_ADMIN".equalsIgnoreCase(loggedUser.getRoleName())) {
			query = query + " and itm.created_by = " + loggedUser.getUserId() + "";
		}
		query = query + " ORDER BY itm.ad_id DESC ";
		classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

		LinkedHashMap<String, Classifieds> classifiedsMap = new LinkedHashMap<>();
		List<String> itemIds = new ArrayList<String>();
		List<Integer> createdByForAgency = new ArrayList<Integer>();
		for (Object[] objs : classifiedList) {
			Classifieds classified = new Classifieds();
//				classified.setContactNo((String)objs[0]);
			classified.setUserTypeId((Integer) objs[0]);
//				classified.setPublishedDate(CommonUtils.dateFormatter((Date)objs[1],"dd-MM-yyyy"));
			classified.setRequestedDate(CommonUtils.dateFormatter((Date) objs[1], "dd-MM-yyyy"));
			classified.setCategoryId(((Integer) objs[2]).intValue());
			classified.setCategory((String) objs[3]);
			Double val = (Double.valueOf(df.format(objs[4])));
			classified.setPaidAmount(new BigDecimal(df1.format(val)));
//				classified.setPaidAmount(new BigDecimal((Float)objs[4]));
			classified.setApprovalStatus((String) objs[5]);
			classified.setPaymentStatus((String) objs[12]);
			classified.setClfPaymentStatus((String) objs[6]);
			classified.setDownloadStatus(objs[7] == null ? false : (Boolean) objs[7]);
			classified.setMatter((String) objs[8]);
			classified.setItemId((String) objs[9]);
			classified.setOrderId((String) objs[10]);
			classified.setSubCategory((String) objs[11]);
			classified.setScheme((String) objs[14]);
			classified.setAdId((String) objs[15]);
			classified.setErpOrderId((String) objs[16]);
			itemIds.add((String) objs[9]);
			if ("3".equalsIgnoreCase(objs[0] + "")) {
				classified.setCreatedBy((Integer) objs[17]);
				createdByForAgency.add((Integer) objs[17]);
			}
			classifiedsMap.put((String) objs[9], classified);
		}

		if (itemIds != null && !itemIds.isEmpty()) {
			List<Object[]> editionsList = clfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
			for (Object[] clObj : editionsList) {
				if (classifiedsMap.containsKey((String) clObj[0])) {
					if (classifiedsMap.get((String) clObj[0]).getEditions() != null) {
						classifiedsMap.get((String) clObj[0]).getEditions().add((String) clObj[2]);
					} else {
						List<String> edditions = new ArrayList<>();
						edditions.add((String) clObj[2]);
						Classifieds classified = classifiedsMap.get((String) clObj[0]);
						classified.setEditions(edditions);
						classifiedsMap.put((String) clObj[0], classified);
					}
				}
			}
		}

		if (itemIds != null && !itemIds.isEmpty()) {
			String itemIds1 = String.join("','", itemIds);
			String query1 = "select itm.item_id,co.order_id,uc.mobile_no from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id where itm.mark_as_delete = false and co.mark_as_delete = false and itm.item_id in ('"
					+ itemIds1 + "')";
			List<Object[]> userTypeList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
			if (!userTypeList.isEmpty()) {
				for (Object[] obj : userTypeList) {
					if (classifiedsMap.containsKey((String) obj[0])) {
						if (classifiedsMap.get((String) obj[0]).getContactNo() == null) {
							classifiedsMap.get((String) obj[0]).setContactNo((String) obj[2]);
						}
					}
				}
			}
		}
		if (createdByForAgency != null && !createdByForAgency.isEmpty()) {
			List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByForAgency, false);
			if (!umUsers.isEmpty()) {
				classifiedsMap.entrySet().forEach(erpData -> {
					Optional<UmUsers> gd = umUsers.stream()
							.filter(f -> (f.getUserId()).equals(erpData.getValue().getCreatedBy())).findFirst();
					if (gd.isPresent()) {
						UmUsers umUser = gd.get();
						erpData.getValue().setContactNo(umUser.getMobile());
					}
				});
			}
		}
		genericApiResponse.setData(classifiedsMap.values());
		return genericApiResponse;

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public ApiResponse updateSyncStatus(List<ErpDataResponsePayload> erpResponseList) {
		ApiResponse apiResponse = new ApiResponse();
		List<String> adIds = new ArrayList<String>();
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		for (ErpDataResponsePayload resPayload : erpResponseList) {
			adIds.add(resPayload.getPortalId());
		}
		String adIdsList = String.join("','", adIds);
		String query = "select itm.ad_id,co.order_id from clf_order_items itm inner join clf_orders co on itm.order_id = co.order_id where itm.ad_id in ('"
				+ adIdsList + "') and itm.mark_as_delete = false and co.mark_as_delete = false";
		classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
		LinkedHashMap<String, Classifieds> classifiedsMap = new LinkedHashMap<>();
		List<String> itemIds = new ArrayList<String>();
		for (Object[] objs : classifiedList) {
			Classifieds classified = new Classifieds();
			classified.setAdId((String) objs[0]);
			classified.setOrderId((String) objs[1]);
			classifiedsMap.put((String) objs[0], classified);
		}
		for (ErpDataResponsePayload res : erpResponseList) {
			Classifieds classified = classifiedsMap.get(res.getPortalId());
			if (classified != null) {
				res.setPortalOrderId(classified.getOrderId());
				clfOrdersRepo.updateSyncStatus(res.getType(), res.getOrderId(), classified.getOrderId());
			}
		}

		return apiResponse;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void sendMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap, LoggedUser loggedUser,
			String orderId, String status, String comments) {
		// TODO Auto-generated method stub
		try {

//			List<String> adminEmail = new ArrayList<String>();
//			List<UmOrgUsers> umOrgU = userDao.getAdminAndHqUsers();
//			for (UmOrgUsers umOrg : umOrgU) {
//				if ("ADMIN".equalsIgnoreCase(umOrg.getUmOrgRoles().getRoleShortId()))
//					adminEmail.add(umOrg.getUmUsers().getEmail());
//			}

//			String adminEmails = adminEmail.stream().map(Object::toString).collect(Collectors.joining(","));
//			String[] adminCcMails = { adminEmails };

			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
//			emailTo.setBcc(adminCcMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
			mapProperties.put("userId", loggedUser.getLogonId());// new userName
			if ("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				mapProperties.put("agencyUsername", loggedUser.getFirstName());
				mapProperties.put("agencyMobileNo", loggedUser.getMobile());
				mapProperties.put("agencyEmail", loggedUser.getEmail());
				mapProperties.put("isAgencyUser", "inline-block");
			} else {
				mapProperties.put("isAgencyUser", "none");
			}
			List<String> secOrderIds = Arrays.asList(orderId);
			List<Object[]> clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
					.getTransactionDetails(secOrderIds);
			if (clfPaymentResponseTracking != null && !clfPaymentResponseTracking.isEmpty()) {
				for (Object[] obj : clfPaymentResponseTracking) {
					mapProperties.put("transactionDate", obj[4]);
					mapProperties.put("paymentStatus", obj[7]);
					mapProperties.put("bankRefId", obj[8]);
				}
			}
			erpClassifiedsMap.entrySet().forEach(erpData -> {
				emailTo.setTo(erpData.getValue().getEmailId());
				mapProperties.put("adId", erpData.getValue().getAdId());
				mapProperties.put("adType", erpData.getValue().getAdsType());
				mapProperties.put("adSubType", erpData.getValue().getAdsSubType());
				mapProperties.put("pack", erpData.getValue().getSchemeStr());
				mapProperties.put("lineCount", erpData.getValue().getLineCount());
				mapProperties.put("amount", erpData.getValue().getPaidAmount());
				mapProperties.put("langStr", erpData.getValue().getLangStr());
				mapProperties.put("category", erpData.getValue().getCategoryStr());
				mapProperties.put("subCategory", erpData.getValue().getSubCategoryStr());
				mapProperties.put("printAdMatter", erpData.getValue().getContent());
				mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
				mapProperties.put("customerName", erpData.getValue().getCustomerName());
				mapProperties.put("customerEmail", erpData.getValue().getEmailId());
				mapProperties.put("customerMobile", erpData.getValue().getMobileNumber());
				mapProperties.put("bookingDate", erpData.getValue().getBookingDate());
				mapProperties.put("gstAmount", erpData.getValue().getGstTotalAmount());
				mapProperties.put("rate", erpData.getValue().getRate());
				mapProperties.put("extraLineAmount", erpData.getValue().getExtraLineRateAmount());
				mapProperties.put("agencyCommition", erpData.getValue().getAgencyCommition());
				mapProperties.put("subject_edit", true);
				if(erpData.getValue().getLogonId() != null) {
					if(erpData.getValue().getLogonId().contains("SSP")) {
						UmUsers usersLoginId = umUsersRepository.getUsersLoginId(erpData.getValue().getLogonId(), false);
						if(usersLoginId != null) {
							UmUsers approverEmails = umUsersRepository.getApproverEmails(usersLoginId.getCreatedBy());
							if(approverEmails != null) {
								String[] ccMails = { loggedUser.getEmail(), erpData.getValue().getBookingUnitEmail(),approverEmails.getEmail() };
								emailTo.setBcc(ccMails);
							}else {
								String[] ccMails = { loggedUser.getEmail(), erpData.getValue().getBookingUnitEmail() };
								emailTo.setBcc(ccMails);
							}
						}else {
							String[] ccMails = { loggedUser.getEmail(), erpData.getValue().getBookingUnitEmail() };
							emailTo.setBcc(ccMails);
						}
					}else {
						String[] ccMails = { loggedUser.getEmail(), erpData.getValue().getBookingUnitEmail() };
						emailTo.setBcc(ccMails);
					}
				}else {
					String[] ccMails = { loggedUser.getEmail(), erpData.getValue().getBookingUnitEmail() };
					emailTo.setBcc(ccMails);
				}
				
//				String[] ccMails = { loggedUser.getEmail(), erpData.getValue().getBookingUnitEmail() };
//				emailTo.setBcc(ccMails);

				mapProperties.put("rate", erpData.getValue().getRate() + erpData.getValue().getExtraLineRateAmount());

				if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {// 3 Agency
					Double totValue = erpData.getValue().getRate() + erpData.getValue().getExtraLineRateAmount();
					totValue = totValue - erpData.getValue().getAgencyCommition();
					totValue = totValue + erpData.getValue().getGstTotalAmount();
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				} else {
					Double totValue = erpData.getValue().getRate() + erpData.getValue().getExtraLineRateAmount()
							+ erpData.getValue().getGstTotalAmount();
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				}

//				adminEmail.add(erpData.getValue().getBookingUnitEmail());
//				String adminEmails = adminEmail.stream().map(Object::toString)
//						.collect(Collectors.joining(","));
//				String[] adminCcMails = {adminEmails};
//				emailTo.setBcc(adminCcMails);

				if ("11".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isTelangana", "inline-block");
				} else {
					mapProperties.put("isTelangana", "none");
				}
				if ("25".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isAndhraPradesh", "inline-block");
				} else {
					mapProperties.put("isAndhraPradesh", "none");
				}
				if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {// 3 means Agency
					mapProperties.put("agencyUsername", erpData.getValue().getCustomerName());
					mapProperties.put("agencyEmail", erpData.getValue().getEmailId());
					mapProperties.put("agencyMobileNo", erpData.getValue().getMobileNumber());
					mapProperties.put("isAgencyUser", "inline-block");
					mapProperties.put("isCustomerUser", "none");
				}
				if ("2".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {// 2 means customer
					mapProperties.put("isAgencyUser", "none");
					mapProperties.put("isCustomerUser", "inline-block");
				}
				List<String> pubDates = erpData.getValue().getPublishDates();
				List<String> pubDatesList = new ArrayList<String>();
				for (String pubD : pubDates) {
					Date date;
					String formattedDate = null;
					try {
						date = new SimpleDateFormat("yyyyMMdd").parse(pubD);
						formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					pubDatesList.add(formattedDate);
				}
				
				//sorting Publish dates in acending order
				pubDatesList = pubDatesList.stream()
			            .sorted((date1, date2) -> {
			                try {
			                    return new SimpleDateFormat("dd-MM-yyyy").parse(date1)
			                        .compareTo(new SimpleDateFormat("dd-MM-yyyy").parse(date2));
			                } catch (ParseException e) {
			                    throw new RuntimeException(e);
			                }
			            })
			            .collect(Collectors.toList());
				
				String publishDates = pubDatesList.stream().map(Object::toString).collect(Collectors.joining(","));
				mapProperties.put("pubDates", publishDates);
				List<String> editionsList = erpData.getValue().getEditions();
				String editions = editionsList.stream().map(Object::toString).collect(Collectors.joining(","));
				mapProperties.put("editions", editions);
				if ("APPROVED".equalsIgnoreCase(status)) {
					if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {// 3 - Agency
						emailTo.setTemplateName(GeneralConstants.CLF_ORDER_APPROVED_AGENCY);
					} else {
						emailTo.setTemplateName(GeneralConstants.CLF_ORDER_APPROVED);
					}
				} else {
					mapProperties.put("comments", comments);
					if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
						emailTo.setTemplateName(GeneralConstants.CLF_ORDER_REJECTED_AGENCY);
					} else {
						emailTo.setTemplateName(GeneralConstants.CLF_ORDER_REJECTED);
					}
				}
				emailTo.setTemplateProps(mapProperties);

				sendService.sendCommunicationMail(emailTo, emailConfigs);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private File convertImageToPdf(String imageFile) throws IOException, DocumentException {
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

	public static void genratePDF() throws DocumentException, MalformedURLException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document,
				new FileOutputStream("C:\\Users\\Admin\\Pictures\\Screenshots\\Screenshot (1011).pdf"));
		document.open();

		// Creating a table of the columns
		com.itextpdf.text.Font fontHeader = new com.itextpdf.text.Font();// text font
		fontHeader.setSize(11);
		com.itextpdf.text.Font fontSubHeader = new com.itextpdf.text.Font();
		fontSubHeader.setSize(10);
		com.itextpdf.text.Font fontColumn = new com.itextpdf.text.Font();
		fontColumn.setSize(8);

		LineSeparator line = new LineSeparator();
		line.setLineColor(BaseColor.BLACK);

//		PdfPTable header = new PdfPTable(1);
//		header.setWidthPercentage(100);
//		PdfPCell headerCell = new PdfPCell(new com.itextpdf.text.Paragraph("Welcome to Sakshi e-Classifieds!"));
//		headerCell.setBorder(0);
//		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		header.addCell(headerCell);
//		document.add(header);
//
//		document.add(new Paragraph(" "));
//		document.add(new Chunk(line));

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		PdfPCell cell1 = new PdfPCell(new com.itextpdf.text.Paragraph("JAGATI PUBLICATIONS LIMITED"));
		cell1.setBorder(0);
		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);

		Image image1 = Image
				.getInstance(new URL("https://pre-prod-asp.s3.ap-south-1.amazonaws.com/static_assets/u3.png"));
		image1.scaleAbsolute(80, 40);
//	            image1.scalePercent(20);
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
		Paragraph address = new Paragraph("Banjara Hills Hyderabad", fontColumn);
		PdfPCell child1 = new PdfPCell(address);
		child1.setBorder(0);
		child1.setHorizontalAlignment(Element.ALIGN_LEFT);

		Paragraph id = new Paragraph("HSN/SAC Code : SCA241234", fontColumn);
//		PdfPCell child2 = new PdfPCell(new com.itextpdf.text.Paragraph("HSN/SAC Code : SCA241234"));
		PdfPCell child2 = new PdfPCell(id);
		child1.setBorder(0);
		child2.setBorder(0);
		child2.setHorizontalAlignment(Element.ALIGN_RIGHT);

		child.addCell(child1);
		child.addCell(child2);
		document.add(child);

		PdfPTable childG = new PdfPTable(1);
		childG.setWidthPercentage(100);
		Paragraph gstn = new Paragraph("GSTIN : 123456", fontColumn);
		PdfPCell child3 = new PdfPCell(gstn);
//		PdfPCell child3 = new PdfPCell(new com.itextpdf.text.Paragraph("GSTIN : 123456"));
		child3.setBorder(0);
		child3.setHorizontalAlignment(Element.ALIGN_LEFT);

		Paragraph mobile = new Paragraph("Ph : 9809098909", fontColumn);
		PdfPCell child4 = new PdfPCell(mobile);
//		PdfPCell child4 = new PdfPCell(new com.itextpdf.text.Paragraph("Ph : 9809098909"));
		child4.setBorder(0);
		child4.setHorizontalAlignment(Element.ALIGN_LEFT);
		childG.addCell(child3);
		childG.addCell(child4);
		document.add(childG);

		document.add(new Paragraph(" "));
		document.add(new Chunk(line));

		PdfPTable table1 = new PdfPTable(4);
		table1.addCell(createCell("Client Code", BaseColor.WHITE, fontSubHeader, false, false));
		table1.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table1.addCell(createCell("City", BaseColor.WHITE, fontSubHeader, false, false));
		table1.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table1.setWidthPercentage(100);
		document.add(table1);

		PdfPTable table2 = new PdfPTable(4);
		table2.addCell(createCell("Order No", BaseColor.WHITE, fontSubHeader, false, false));
		table2.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table2.addCell(createCell("State", BaseColor.WHITE, fontSubHeader, false, false));
		table2.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table2.setWidthPercentage(100);
		document.add(table2);

		PdfPTable table3 = new PdfPTable(4);
		table3.addCell(createCell("Client Name ", BaseColor.WHITE, fontSubHeader, false, false));
		table3.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table3.addCell(createCell("Generated by", BaseColor.WHITE, fontSubHeader, false, false));
		table3.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table3.setWidthPercentage(100);
		document.add(table3);

		PdfPTable table4 = new PdfPTable(4);
		table4.addCell(createCell("Date", BaseColor.WHITE, fontSubHeader, false, false));
		table4.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table4.addCell(createCell("Phone", BaseColor.WHITE, fontSubHeader, false, false));
		table4.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table4.setWidthPercentage(100);
		document.add(table4);

		PdfPTable table5 = new PdfPTable(4);
		table5.addCell(createCell("Street/House No/Locality", BaseColor.WHITE, fontSubHeader, false, false));
		table5.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table5.addCell(createCell("Client GSTN", BaseColor.WHITE, fontSubHeader, false, false));
		table5.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table5.setWidthPercentage(100);
		document.add(table5);

		PdfPTable table6 = new PdfPTable(4);
		table6.addCell(createCell("Employee / HR Code", BaseColor.WHITE, fontSubHeader, false, false));
		table6.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table6.addCell(createCell("Advertiesement Caption", BaseColor.WHITE, fontSubHeader, false, false));
		table6.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table6.setWidthPercentage(100);
		document.add(table6);

		PdfPTable table7 = new PdfPTable(4);
		table7.addCell(createCell("PinCode", BaseColor.WHITE, fontSubHeader, false, false));
		table7.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table7.addCell(createCell("No of Insertions", BaseColor.WHITE, fontSubHeader, false, false));
		table7.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table7.setWidthPercentage(100);
		document.add(table7);

		PdfPTable table8 = new PdfPTable(4);
		table8.addCell(createCell("Employee", BaseColor.WHITE, fontSubHeader, false, false));
		table8.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table8.addCell(createCell("Scheme", BaseColor.WHITE, fontSubHeader, false, false));
		table8.addCell(createCell("", BaseColor.WHITE, fontSubHeader, false, false));
		table8.setWidthPercentage(100);
		document.add(table8);

		document.add(new Paragraph(" "));

		PdfPTable tableF = new PdfPTable(8);
		BaseColor silverColor = new BaseColor(192, 192, 192);
		tableF.addCell(createCell("Date of Insertion", silverColor, fontSubHeader, false, false));
		tableF.addCell(createCell("District/Edition", silverColor, fontSubHeader, false, false));
		tableF.addCell(createCell("Size Width", silverColor, fontSubHeader, false, false));
		tableF.addCell(createCell("Size Height", silverColor, fontSubHeader, false, false));
		tableF.addCell(createCell("Space", silverColor, fontSubHeader, false, false));
		tableF.addCell(createCell("Card Rate", silverColor, fontSubHeader, false, false));
		tableF.addCell(createCell("Position", silverColor, fontSubHeader, false, false));
		tableF.addCell(createCell("Total Amount", silverColor, fontSubHeader, false, false));
		tableF.setWidthPercentage(100);
		document.add(tableF);

		PdfPTable tableG = new PdfPTable(8);
		tableG.addCell(createCell("A", BaseColor.WHITE, fontColumn, false, false));
		tableG.addCell(createCell("B", BaseColor.WHITE, fontColumn, false, false));
		tableG.addCell(createCell("C", BaseColor.WHITE, fontColumn, false, false));
		tableG.addCell(createCell("", BaseColor.WHITE, fontColumn, false, false));
		tableG.addCell(createCell("", BaseColor.WHITE, fontColumn, false, false));
		tableG.addCell(createCell("", BaseColor.WHITE, fontColumn, false, false));
		tableG.addCell(createCell("", BaseColor.WHITE, fontColumn, false, false));
		tableG.addCell(createCell("", BaseColor.WHITE, fontColumn, false, false));
		tableG.setWidthPercentage(100);
		document.add(tableG);

		PdfPTable totalAmount = new PdfPTable(8);
		PdfPCell totalLabelCell = createCell("Total", BaseColor.WHITE, fontSubHeader, false, false);
		totalLabelCell.setColspan(7);
		totalAmount.addCell(totalLabelCell);
		totalAmount.addCell(createCell("100", BaseColor.WHITE, fontSubHeader, false, false));
		totalAmount.setWidthPercentage(100);
		document.add(totalAmount);

		document.add(new Paragraph(" "));

		PdfPTable discount = new PdfPTable(5);
		discount.addCell(createCell("Name of discount", silverColor, fontSubHeader, false, false));
		discount.addCell(createCell("Category type", silverColor, fontSubHeader, false, false));
		discount.addCell(createCell("Discount %", silverColor, fontSubHeader, false, false));
		discount.addCell(createCell("Discount Value", silverColor, fontSubHeader, false, false));
		discount.addCell(createCell("Balance after discount (to be hide only for calculation)", silverColor,
				fontSubHeader, false, false));
		discount.setWidthPercentage(100);
		document.add(discount);

		PdfPTable discountVal = new PdfPTable(5);
		discountVal.addCell(createCell("A", BaseColor.WHITE, fontColumn, false, false));
		discountVal.addCell(createCell("B", BaseColor.WHITE, fontColumn, false, false));
		discountVal.addCell(createCell("C", BaseColor.WHITE, fontColumn, false, false));
		discountVal.addCell(createCell("", BaseColor.WHITE, fontColumn, false, false));
		discountVal.addCell(createCell("", BaseColor.WHITE, fontColumn, false, false));
		discountVal.setWidthPercentage(100);
		document.add(discountVal);

		PdfPTable totalAmount1 = new PdfPTable(5);
		PdfPCell totalLabelCell1 = createCell("Total Discount", BaseColor.WHITE, fontSubHeader, false, false);
		totalLabelCell1.setColspan(4);
		totalLabelCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalAmount1.addCell(totalLabelCell1);
		totalAmount1.addCell(createCell("100", BaseColor.WHITE, fontSubHeader, false, false));

		PdfPCell totalLabelCell2 = createCell("Taxable value", BaseColor.WHITE, fontSubHeader, false, false);
		totalLabelCell2.setColspan(4);
		totalAmount1.addCell(totalLabelCell2);
		totalAmount1.addCell(createCell("100", BaseColor.WHITE, fontSubHeader, false, false));

//		totalAmount1.setWidthPercentage(100);
//		document.add(totalAmount1);

		PdfPCell totalLabelCell3 = createCell("GST(IGST)", BaseColor.WHITE, fontSubHeader, false, false);
		totalLabelCell3.setColspan(4);
		totalAmount1.addCell(totalLabelCell3);
		totalAmount1.addCell(createCell("100", BaseColor.WHITE, fontSubHeader, false, false));

		PdfPCell totalLabelCell4 = createCell("GST(CGST)", BaseColor.WHITE, fontSubHeader, false, false);
		totalLabelCell4.setColspan(4);
		totalAmount1.addCell(totalLabelCell4);
		totalAmount1.addCell(createCell("100", BaseColor.WHITE, fontSubHeader, false, false));

		PdfPCell totalLabelCell5 = createCell("GST(SGST)", BaseColor.WHITE, fontSubHeader, false, false);
		totalLabelCell5.setColspan(4);
		totalAmount1.addCell(totalLabelCell5);
		totalAmount1.addCell(createCell("100", BaseColor.WHITE, fontSubHeader, false, false));

		PdfPCell totalLabelCell6 = createCell("Total Value", BaseColor.WHITE, fontSubHeader, false, false);
		totalLabelCell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalLabelCell6.setColspan(4);
		totalAmount1.addCell(totalLabelCell6);
		totalAmount1.addCell(createCell("100", BaseColor.WHITE, fontSubHeader, false, false));

		totalAmount1.setWidthPercentage(100);
		document.add(totalAmount1);

		document.add(new Paragraph(" "));

		document.add(new Chunk(line));
		document.add(new Paragraph(" "));

		Paragraph mainContent = new Paragraph(
				"Advertisement may be released as per the above details. Subject to your Terms & Conditions mentioned in the Rate Card.");
		document.add(mainContent);

		document.add(new Chunk(line));

		Paragraph paymentParticulars = new Paragraph("Payment Particulars");
		document.add(paymentParticulars);
		document.add(new Paragraph(" "));
		PdfPTable tabl = new PdfPTable(3);
		tabl.addCell(createCell("Chq./D.D No ", BaseColor.WHITE, fontColumn, false, false));
		tabl.addCell(createCell("Bank/Branch", BaseColor.WHITE, fontColumn, false, false));
		tabl.addCell(createCell("Cash Receipt No", BaseColor.WHITE, fontColumn, false, false));
		tabl.setWidthPercentage(100);
		document.add(tabl);

		PdfPTable tabl1 = new PdfPTable(3);
		tabl1.addCell(createCell("A", BaseColor.WHITE, fontColumn, false, false));
		tabl1.addCell(createCell("B", BaseColor.WHITE, fontColumn, false, false));
		tabl1.addCell(createCell("C", BaseColor.WHITE, fontColumn, false, false));
		tabl1.setWidthPercentage(100);
		document.add(tabl1);

		Paragraph signatureLines = new Paragraph("*All Advertisements are accepted on advance payment only");
		document.add(signatureLines);

		document.add(new Chunk(line));
		Paragraph remarks = new Paragraph("Remarks.");
		document.add(remarks);
		document.add(new Chunk(line));
		document.add(new Paragraph(" "));

		PdfPTable sig = new PdfPTable(2);
		sig.setWidthPercentage(100);
		PdfPCell sig1 = new PdfPCell(new com.itextpdf.text.Paragraph("(E-Signature of the employee)"));
		sig1.setBorder(0);
		sig1.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell sigA1 = new PdfPCell(new com.itextpdf.text.Paragraph("(E-Signature of the client)"));
		sigA1.setBorder(0);
		sigA1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		sig.addCell(sig1);
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

		Image image = Image.getInstance("C:\\Users\\Admin\\Pictures\\Screenshots\\Screenshot (1).png");
		image.scaleAbsolute(200, 100);
		document.add(image);

		document.add(new Paragraph(" "));

		Image image2 = Image.getInstance("C:\\Users\\Admin\\Pictures\\Screenshots\\Screenshot (20).png");
		image2.scaleAbsolute(200, 100);
		document.add(image2);

		document.close();
		System.out.println("PDF generated successfully.");

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

	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getDownloadStatusListExcelDownload(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		String query = "select co.user_type_id, itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status as itm_download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,cpd.publish_date,cpd.download_status as cpd_download_status,itm.created_by,coir.lines,cp.order_id as payments_order_id from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_publish_dates cpd on itm.item_id = cpd.item_id where itm.mark_As_Delete = false and itm.status = 'APPROVED' and co.payment_status = 'APPROVED' and cp.payment_status = 'success'";
		if (payload.getType() != null && !payload.getType().isEmpty() && !"ALL".equalsIgnoreCase(payload.getType())) {
			query = query + " and co.payment_status ='" + payload.getType() + "'";
		}
		if (payload.getPublishedDate() != null && !payload.getPublishedDate().isEmpty()) {
			query = query + " and to_char(cpd.publish_date,'DD/MM/YYYY') = '" + payload.getPublishedDate() + "'";
		}
		if (payload.getDownloadStatus() != null && !payload.getDownloadStatus().isEmpty()) {
			query = query + " and cpd.download_status =" + payload.getDownloadStatus() + "";
		}
		if (payload.getBookingUnit() != null && payload.getBookingUnit() != 0) {
			query = query + " and co.booking_unit = " + payload.getBookingUnit() + "";
		}
		if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()
				&& payload.getRequestedToDate() != null && !payload.getRequestedToDate().isEmpty()) {
			String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			String toDate = this.convertDateFormat(payload.getRequestedToDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query = query + " AND to_char(cpd.publish_date,'YYYY-MM-DD') >= '" + formattedDate
					+ "' AND to_char(cpd.publish_date,'YYYY-MM-DD') <= '" + toDate + "'";
		}
		if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()
				&& payload.getRequestedToDate() == null && payload.getRequestedToDate().isEmpty()) {
			String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query = query + " and to_char(cpd.publish_date,'YYYY-MM-DD') = '" + formattedDate + "'";
		}
		if (payload.getRequestedToDate() != null && !payload.getRequestedToDate().isEmpty()
				&& payload.getRequestedDate() == null && payload.getRequestedDate().isEmpty()) {
			String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query = query + " and to_char(cpd.publish_date,'YYYY-MM-DD') = '" + formattedDate + "'";
		}
		query = query + " and cpd.mark_as_delete=false ORDER BY itm.ad_id DESC ";
		classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

		List<Classifieds> classifiedDetails = new ArrayList<Classifieds>();
		List<String> itemIds = new ArrayList<String>();
		List<Integer> createdByForAgency = new ArrayList<Integer>();
		for (Object[] objs : classifiedList) {
			Classifieds classified = new Classifieds();
			classified.setEditions(new ArrayList<String>());
			classified.setRequestedDate(CommonUtils.dateFormatter((Date) objs[1], "dd-MM-yyyy"));
			classified.setCategoryId(((Integer) objs[2]).intValue());
			classified.setCategory((String) objs[3]);
			Double val = (Double.valueOf(df.format(objs[4])));
			classified.setPaidAmount(new BigDecimal(df1.format(val)));
			classified.setApprovalStatus((String) objs[5]);
			classified.setPaymentStatus((String) objs[12]);
			classified.setClfPaymentStatus((String) objs[6]);
			classified.setMatter((String) objs[8]);
			classified.setItemId((String) objs[9]);
			classified.setOrderId((String) objs[20]);
			classified.setSubCategory((String) objs[11]);
			classified.setScheme((String) objs[14]);
			classified.setAdId((String) objs[15]);
			classified.setPublishedDate(CommonUtils.dateFormatter((Date) objs[16], "dd-MM-yyyy"));
			classified.setDownloadStatus(objs[17] == null ? false : (Boolean) objs[17]);
			classified.setLines((Integer) objs[19]);
			itemIds.add((String) objs[9]);
			if ("3".equalsIgnoreCase(objs[0] + "")) {
				classified.setCreatedBy((Integer) objs[18]);
				createdByForAgency.add((Integer) objs[18]);
			}
			classifiedDetails.add(classified);
		}

		if (itemIds != null && !itemIds.isEmpty()) {
			String itemIds1 = String.join("','", itemIds);
			String query1 = "select itm.item_id,co.order_id,uc.mobile_no from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id where itm.mark_as_delete = false and co.mark_as_delete = false and itm.item_id in ('"
					+ itemIds1 + "')";
			List<Object[]> userTypeList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query1);
			if (!userTypeList.isEmpty()) {
				for (Object[] obj : userTypeList) {
					List<Classifieds> gd = classifiedDetails.stream().filter(f -> (f.getItemId()).equals(obj[0]))
							.collect(Collectors.toList());
					if (!gd.isEmpty()) {
						for (Classifieds cls : gd) {
							if (cls.getContactNo() == null) {
								cls.setContactNo((String) obj[2]);
							}
						}
					}
				}
			}
		}
		if (createdByForAgency != null && !createdByForAgency.isEmpty()) {
			List<UmUsers> umUsers = umUsersRepository.getUsersByCreatedId(createdByForAgency, false);
			if (!umUsers.isEmpty()) {
				for (Classifieds cls : classifiedDetails) {
					UmUsers user = umUsers.stream().filter(f -> (f.getUserId()).equals(cls.getCreatedBy())).findFirst()
							.orElse(null);
					if (user != null) {
						cls.setContactNo(user.getMobile());
					}
				}
			}
		}
		List<Object[]> editionsList = clfEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
		if (itemIds != null && !itemIds.isEmpty()) {
			for (Object[] clObj : editionsList) {
				String itemId = (String) clObj[0];
				String editionName = (String) clObj[2];

				classifiedDetails.stream().filter(cls -> cls.getItemId().equals(itemId)).forEach(cls -> {
					if (cls.getEditions() != null) {
						cls.getEditions().add(editionName);
					} else {
						List<String> editions = new ArrayList<>();
						editions.add(editionName);
						cls.setEditions(editions);
					}
				});
			}
		}
		genericApiResponse.setData(classifiedDetails);
		return genericApiResponse;
	}
	

	public String convertDateFormat(String date, String fromPattern, String toPattern) {
		DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern(fromPattern);
		DateTimeFormatter toFormatter = DateTimeFormatter.ofPattern(toPattern);
		LocalDate localDate = LocalDate.parse(date, fromFormatter);
		return localDate.format(toFormatter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getEncodedString(String orderId, LoggedUser loggedUser) {
		// TODO Auto-generated method stub
		GenericApiResponse apiResponse = new GenericApiResponse();
		apiResponse.setStatus(1);
		apiResponse.setMessage("Something went wrong. Please contact our administrator.");
		try {
			if (orderId != null) {
				String query = "select cp.order_id,cp.transactionid,cp.encoded_request,cp.encoded_response,cp.headers,cp.response from clf_payment_response_tracking cp where cp.sec_order_id = '"
						+ orderId + "' and cp.mark_as_delete = false";
				List<Object[]> list = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
				if (list != null && !list.isEmpty()) {
					for (Object[] obj : list) {
						Map<String, Object> params = new HashMap<>();
						params.put("stype", SettingType.APP_SETTING.getValue());
						params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
						SettingTo settingTo = settingDao.getSMTPSettingValues(params);
						Map<String, String> emailConfigs = settingTo.getSettings();

						Map<String, Object> mapProperties = new HashMap<String, Object>();
						EmailsTo emailTo = new EmailsTo();
						emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
						emailTo.setOrgId("1000");
						emailTo.setTo(loggedUser.getEmail());
						mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
						mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
						mapProperties.put("userName", loggedUser.getLogonId());// created by userName
						mapProperties.put("userId", loggedUser.getLogonId());// new userName
						mapProperties.put("encodedRequest", obj[2]);
						mapProperties.put("encodedResponse", obj[3]);
						mapProperties.put("headers", obj[4]);
						mapProperties.put("transactionResponse", obj[5]);
						mapProperties.put("transactionId", obj[1]);
						mapProperties.put("orderId", obj[0]);
						mapProperties.put("subject_edit", true);
						
						emailTo.setTemplateName(GeneralConstants.ENCODED_STRING);

						emailTo.setTemplateProps(mapProperties);

						sendService.sendCommunicationMail(emailTo, emailConfigs);
						apiResponse.setStatus(0);
						apiResponse.setMessage("The email has been sent. Kindly check it.");
					}

				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage("Something went wrong. Please contact our administrator.");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();;
		}
		return apiResponse;
	}

	@Override//06/08/2025(Wednesday)
	public GenericApiResponse sendEmailToSchedulingTeam(String orderId, LoggedUser loggedUser) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			if (orderId != null) {
				List<String> orderIdss = Arrays.asList(orderId);
				Map<String, ErpClassifieds> erpClassifiedsMap = this.getOrderDetailsForErp(orderIdss);
				ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
						.getTransactionDetOnSecOrderId(orderId);
				ClfOrderItems openOrderItemsByOrderId = clfOrderItemsRepo.getOpenOrderItemsByOrderId(orderId);
				if(openOrderItemsByOrderId != null) {
					if(openOrderItemsByOrderId.getAdId().contains("SSP")) {
						Map<String, ErpClassifieds> erpClassifiedsDAMap = displayServiceImpl.getClassifiedOrderDetailsForSSPErp(orderIdss);
						BillDeskPaymentResponseModel billDeskPaymentResponseModel = new BillDeskPaymentResponseModel();
						if(erpClassifiedsDAMap != null && !erpClassifiedsDAMap.isEmpty()) {
							if (clfPaymentResponseTracking != null) {									
										displayService.sendMailToCustomerForSSPVendor(erpClassifiedsDAMap, billDeskPaymentResponseModel, loggedUser,
												clfPaymentResponseTracking);
										apiResponse.setStatus(0);
										apiResponse.setMessage("The email has been sent. Kindly check it.");								
								
							} else {
								apiResponse.setStatus(1);
								apiResponse.setMessage("Payment Details Not Found");
							}
						}else {
							apiResponse.setStatus(1);
							apiResponse.setMessage("Something went wrong. Please contact our administrator.");
						}
						
					}else {
						if (erpClassifiedsMap != null && !erpClassifiedsMap.isEmpty()) {
							if (clfPaymentResponseTracking != null) {									
										paymentService.sendMailToCustomer(erpClassifiedsMap, null, loggedUser,
												clfPaymentResponseTracking);
										apiResponse.setStatus(0);
										apiResponse.setMessage("The email has been sent. Kindly check it.");							
							
						} else {
							apiResponse.setStatus(1);
							apiResponse.setMessage("Payment Details Not Found");
						}
					}else {
						apiResponse.setStatus(1);
						apiResponse.setMessage("Something went wrong. Please contact our administrator.");
					}
				} 
				}
			
			}else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("OrderId is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

	@Override
	public GenericApiResponse getPaymentSuccessOrderPendingData(DashboardFilterTo payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		try {
			String query = "select itm.ad_id,cprt.sec_order_id,bu.booking_location,uu.first_name,uu.mobile,uc.customer_name,uc.mobile_no,cprt.amount,uu.logon_id,cprt.created_ts from clf_payment_response_tracking cprt inner join clf_orders co on co.order_id = cprt.sec_order_id left join clf_order_items itm on itm.order_id = co.order_id left join booking_units bu on bu.booking_code = co.booking_unit left join um_users uu on uu.user_id = itm.created_by left join um_customers uc on uc.customer_id = co.customer_id  where cprt.mark_as_delete = false and co.payment_status = 'PAYMENT_PENDING' and co.order_status = 'OPEN' and cprt.payment_status = 'success' and co.mark_as_delete  = false and itm.mark_as_delete  = false";
			
			if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()
					&& payload.getRequestedToDate() != null && !payload.getRequestedToDate().isEmpty()) {
				String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
				String toDate = this.convertDateFormat(payload.getRequestedToDate(), "dd/MM/yyyy", "yyyy-MM-dd");
				query += " AND to_char(cprt.created_ts,'YYYY-MM-DD') >= '" + formattedDate
						+ "' AND to_char(cprt.created_ts,'YYYY-MM-DD') <= '" + toDate + "'";
			}
			if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()
					&& payload.getRequestedToDate() == null) {
				String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
				String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				query = query + " and to_char(cprt.created_ts,'YYYY-MM-DD') >= '" + formattedDate
						+ "' AND to_char(cprt.created_ts,'YYYY-MM-DD') <= '" + today + "'";
			}
			if (payload.getRequestedToDate() != null && !payload.getRequestedToDate().isEmpty()
					&& payload.getRequestedDate() == null) {
				String toDate = this.convertDateFormat(payload.getRequestedToDate(), "dd/MM/yyyy", "yyyy-MM-dd");
				query = query + " and to_char(cprt.created_ts,'YYYY-MM-DD') = '" + toDate
						+ "' AND to_char(cprt.created_ts,'YYYY-MM-DD') <= '" + toDate + "'";
			}
			if ((payload.getRequestedDate() == null || "".equalsIgnoreCase(payload.getRequestedDate())) && (payload.getRequestedToDate() == null ||"".equalsIgnoreCase(payload.getRequestedToDate()))) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				LocalDate now = LocalDate.now();
				String defaultFrom = now.minusDays(3).format(formatter);
				String defaultTo = now.format(formatter);

				query += " AND to_char(cprt.created_ts,'YYYY-MM-DD') >= '" + defaultFrom
						+ "' AND to_char(cprt.created_ts,'YYYY-MM-DD') <= '" + defaultTo + "'";
			}
			query = query + " ORDER BY itm.ad_id DESC";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			LinkedHashMap<String, PaymentsPendingModel> paymentsPendingModel = new LinkedHashMap<>();
			if(classifiedList != null && !classifiedList.isEmpty()) {
				for(Object[] obj : classifiedList) {
					PaymentsPendingModel paymentPendingModel = new PaymentsPendingModel();
					paymentPendingModel.setAdId((String) obj[0]);
					paymentPendingModel.setBookingUnitName((String) obj[2]);
					paymentPendingModel.setCreatedByName((String) obj[3]);
					paymentPendingModel.setCreatorMobile((String) obj[4]);
					paymentPendingModel.setCustomerName((String) obj[5]);
					paymentPendingModel.setCustomerMobileNo((String) obj[6]);					
					paymentPendingModel.setPaidAmount((String) obj[7]);
					paymentPendingModel.setLogOnId((String) obj[8]);
					paymentPendingModel.setCreatedTs(CommonUtils.dateFormatter((Date) obj[9], "dd-MM-yyyy"));
					paymentsPendingModel.put((String) obj[1], paymentPendingModel);
				}
				apiResponse.setStatus(0);
				apiResponse.setData(paymentsPendingModel.values());
				apiResponse.setMessage("");
			}else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("No Data found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}
}
