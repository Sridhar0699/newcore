package com.portal.da.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.activation.FileDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.util.concurrent.AtomicDouble;
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
import com.portal.clf.entities.ClfOrderItemRates;
import com.portal.clf.entities.ClfOrderItems;
import com.portal.clf.entities.ClfOrders;
import com.portal.clf.entities.ClfPaymentResponseTracking;
import com.portal.clf.entities.ClfPublishDates;
import com.portal.clf.models.AddToCartRequest;
import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.CartDetails;
import com.portal.clf.models.ClassifiedConstants;
import com.portal.clf.models.ClassifiedRateDetails;
import com.portal.clf.models.ClassifiedRates;
import com.portal.clf.models.ClassifiedStatus;
import com.portal.clf.models.Classifieds;
import com.portal.clf.models.ClassifiedsOrderItemDetails;
import com.portal.clf.models.ClfPaymentsRefund;
import com.portal.clf.models.CustomerDetails;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.clf.models.ErpClassifieds;
import com.portal.clf.models.OrderDetails;
import com.portal.clf.models.PostClassifiedRates;
import com.portal.clf.models.SSPSummaryModel;
import com.portal.clf.service.ClassifiedDownloadService;
import com.portal.clf.service.PaymentServiceImpl;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.constants.GeneralConstants;
import com.portal.da.entities.ClfTemplateContent;
import com.portal.da.entities.DaRates;
import com.portal.da.models.DaTempleContentModel;
import com.portal.da.models.DisplayAdsModel;
import com.portal.doc.entity.Attachments;
import com.portal.erp.service.ErpService;
import com.portal.gd.entities.GdClassifiedSchemes;
import com.portal.gd.entities.GdDaDiscounts;
import com.portal.gd.entities.GdDaLegalDiscounts;
import com.portal.gd.entities.GdNumberSeries;
import com.portal.gd.entities.GdUserTypes;
import com.portal.reports.utility.CommonUtils;
import com.portal.repository.AttachmentsRepo;
import com.portal.repository.ClfEditionsRepo;
import com.portal.repository.ClfOrderItemsRepo;
import com.portal.repository.ClfPaymentResponseTrackingRepo;
import com.portal.repository.ClfPublishDatesRepo;
import com.portal.repository.ClfTemplateContentRepo;
import com.portal.repository.DaEditionsRepo;
import com.portal.repository.DaOrderItemRatesRepo;
import com.portal.repository.DaOrderItemsRepo;
import com.portal.repository.DaOrdersRepo;
import com.portal.repository.DaPublishDatesRepo;
import com.portal.repository.DaRatesRepo;
import com.portal.repository.DaTemplateRepo;
import com.portal.repository.GDClassifiedTemplatesRepo;
import com.portal.repository.GdClassifiedSchemeRepo;
import com.portal.repository.GdClassifiedTypesRepo;
import com.portal.repository.GdDaDiscountsRepo;
import com.portal.repository.GdDaLegalDiscountsRepo;
import com.portal.repository.GdDaSspComRepo;
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
//import com.portal.utils.PrintUtility;

@Service
public class DisplayServiceImpl implements DisplayService{

	@Autowired
	private GDClassifiedTemplatesRepo gDClassifiedTemplatesRepo;
	
	@Autowired
	private DaOrdersRepo daOrdersRepo;

	@Autowired
	private ClfOrderItemsRepo clfOrderItemsRepo;
	
	@Autowired
	private DaOrderItemsRepo daOrderItemsRepo;
	
	@Autowired
	private DaPublishDatesRepo daPublishDatesRepo;

	@Autowired
	private ClfEditionsRepo clfEditionsRepo;
	
	@Autowired
	private DaEditionsRepo daEditionsRepo;

	@Autowired
	private GdClassifiedTypesRepo gdClassifiedTypesRepo;

	@Autowired
	private UmCustomersRepo umCustomersRepo;
	
	@Autowired
	private DaRatesRepo daRatesRepo;
	
	@Autowired
	private DaOrderItemRatesRepo daOrderItemRatesRepo;

	@Autowired
	private GdSettingsDefinitionsRepository settingRepo;

	@Autowired
	private GdNumberSeriesRepo gdNumberSeriesRepo;
	
	@Autowired
	private DaTemplateRepo  daTemplateRepo;

	@Autowired(required = true)
	private IBaseDao baseDao;

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
	
	@Autowired
	private ClfPublishDatesRepo clfPublishDatesRepo;

	@Autowired(required = true)
	private SettingDao settingDao;
	
	@Autowired
	private ErpService erpService;
	
	@Autowired
	private Environment properties;
	
	@Autowired
	private GdDaSspComRepo gdDaSspComRepo;
	
	@Autowired
	private LoggedUserContext userContext;
	
	@Autowired
	private GdDaDiscountsRepo gdDaDiscountsRepo;
	
	@Autowired
	private ClfTemplateContentRepo clfTemplateContentRepo;
	
	@Autowired
	private GdDaLegalDiscountsRepo gdDaLegalDiscountsRepo;
	
	public static final String DIR_PATH_DOCS = "/SEC/DOCS/";
	public static final String DIR_PATH_PDF_DOCS = "/SEC/PDFS/";
	public static final String DIR_PATH_TO_DOWNLOAD = "/SEC/PDF/";
	
	public String getDIR_PATH_DOCS() {
		return properties.getProperty("ROOT_DIR") + DIR_PATH_DOCS;
	}

	public String getDIR_PATH_PDF_DOCS() {
		return properties.getProperty("ROOT_DIR") + DIR_PATH_PDF_DOCS;
	}
	
	public String getDIR_PATH_TO_DOWNLOAD() {
		return properties.getProperty("ROOT_DIR") + DIR_PATH_TO_DOWNLOAD;
	}
	
	public static String TOMCAT_PATH = "/staticresources/docs/";
	public String getTOMCAT_PATH() {
	        return properties.getProperty("ROOT_DIR") + TOMCAT_PATH;
	    }

	@Override
	public GenericApiResponse addDisplayItemToCart(AddToCartRequest payload, LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		genericApiResponse.setMessage("Successfully added");
		
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
			clfOrders.setOrderType(03);
			clfOrders.setAttachedId(payload.getItemList().get(0).getAttachedId());
			if ("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				clfOrders.setCustomerName(payload.getCustomerDetails().getCustomerName2());
			}
			daOrdersRepo.save(clfOrders);
		} else {
			ClfOrderItems clfOrderItems = clfOrderItemsRepo.getItemDetailsOnItemId(payload.getItemId());
			clfOrders = new ClfOrders();
			clfOrders = daOrdersRepo.getOrderDetailsOnOrderId(clfOrderItems.getClfOrders().getOrderId());
			clfOrders.setBookingUnit(payload.getCustomerDetails().getBookingUnit());
			clfOrders.setEditionType(payload.getItemList().get(0).getEditionType());
			clfOrders.setAttachedId(payload.getItemList().get(0).getAttachedId());
			if ("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				clfOrders.setCustomerName(payload.getCustomerDetails().getCustomerName2());
			}
			daOrdersRepo.save(clfOrders);

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
			clfOrderItems.setSizeId(item.getSize());
			clfOrderItems.setTemplateId(item.getTemplateId());
			clfOrderItems.setMainAttachmentId(item.getMainAttachmentId());
			clfOrderItems.setArtWorkAttachmentId(item.getArtWorkAttachmentId());
			String adId = this.generateSeries("DISPLAY_ADS");
			if (adId != null) {
				clfOrderItems.setAdId(adId);
			}
			daOrderItemsRepo.save(clfOrderItems);
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
				daPublishDatesRepo.save(clfPublishDate);
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
				daEditionsRepo.save(clfEdition);
			}
			for(DaTempleContentModel templateContent : payload.getContentModel()) {
				ClfTemplateContent clfContent = new ClfTemplateContent();
				clfContent.setTextId(UUID.randomUUID().toString());
				clfContent.setOrderId(clfOrders.getOrderId());
				clfContent.setItemId(clfOrderItems.getItemId());
				clfContent.setTemplateId(templateContent.getTemplate_id());
				clfContent.setTextIndex(templateContent.getText_index());
				clfContent.setContent(templateContent.getContent());
				clfContent.setxCoordinate(templateContent.getX_coordinate());
				clfContent.setyCoordinate(templateContent.getY_coordinate());
				clfContent.setWidth(templateContent.getWidth());
				clfContent.setHeight(templateContent.getHeight());
				clfContent.setFont(templateContent.getFont());
				clfContent.setType(templateContent.getType());
				clfContent.setCreatedBy(loggedUser.getUserId());
				clfContent.setTemplateCntId(templateContent.getTemplate_cnt_id());
				if(templateContent.getAttachmentId() != null && templateContent.getAttatchUrl() != null) {
					clfContent.setAttachmentId(templateContent.getAttachmentId());
					clfContent.setAttatchUrl(templateContent.getAttatchUrl());
					clfContent.setSrc(templateContent.getSrc());
					clfContent.setImageType(templateContent.getImage_type());
				}
				clfContent.setCreatedTs(new Date());
				clfContent.setMarkAsDelete(false);
				
				clfTemplateContentRepo.save(clfContent);
			}
			PostClassifiedRates postClassifiedRates = item.getPostClassifiedRates();
			ClfOrderItemRates clfOrderItemRates = new ClfOrderItemRates();
			clfOrderItemRates.setItemRateId(UUID.randomUUID().toString());
			clfOrderItemRates.setOrderId(clfOrders.getOrderId());
			clfOrderItemRates.setItemId(clfOrderItems.getItemId());
			clfOrderItemRates.setRate(postClassifiedRates.getRate());
//			clfOrderItemRates.setExtraLineRate(postClassifiedRates.getExtraLineAmount());
//			clfOrderItemRates.setLines(postClassifiedRates.getMinLines());
//			clfOrderItemRates.setLineCount(postClassifiedRates.getActualLines());
//			clfOrderItemRates.setTotalAmount(postClassifiedRates.getTotalAmount());
			clfOrderItemRates.setTotalAmount((double) Math.round(postClassifiedRates.getTotalAmount() * 100.0) / 100.0);
			clfOrderItemRates.setCreatedBy(loggedUser.getUserId());
			clfOrderItemRates.setCreatedTs(new Date());
			clfOrderItemRates.setMarkAsDelete(false);
//			clfOrderItemRates.setAgencyCommition(postClassifiedRates.getAgencyCommitionAmount());
			clfOrderItemRates.setGstTotal(postClassifiedRates.getGstTaxAmount());
			clfOrderItemRates.setCategoryDiscountAmount(postClassifiedRates.getAfterCatgoryDiscount());
			clfOrderItemRates.setSpeicalDiscountAmount(postClassifiedRates.getAfterSpeicalDiscount());
			clfOrderItemRates.setCategoryDiscountPercentage(postClassifiedRates.getCategoryDiscountPercentage());
			clfOrderItemRates.setSpecialDiscountPercentage(postClassifiedRates.getSpecialDiscountPercentage());
			daOrderItemRatesRepo.save(clfOrderItemRates);
			List<String> orderIds = new ArrayList<String>();
			orderIds.add(clfOrders.getOrderId());
			Map<String, ErpClassifieds> daOrderDetailsForErp = this.getDaOrderDetailsForErp(orderIds);
//			this.sendOrderBookedDetailsViaSMS(rmsOrderDetailsForErp,payload);
			this.sendDaMailToCustomer(daOrderDetailsForErp, userContext.getLoggedUser());
		}
		return genericApiResponse;
	}
	
	private void sendDaMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap,
			LoggedUser loggedUser) {
		try {

			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));
			String [] ccMails = {loggedUser.getEmail()};
			emailTo.setBcc(ccMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
			mapProperties.put("userId", loggedUser.getLogonId());// new userName
			erpClassifiedsMap.entrySet().forEach(erpData -> {
				emailTo.setTo(erpData.getValue().getEmailId());
//				emailTo.setTo("sridhar.aleti@incresol.com");
				mapProperties.put("orderId", erpData.getValue().getAdId());
//				mapProperties.put("clientCode", erpData.getValue().getClientCode());
				mapProperties.put("city", erpData.getValue().getCityDesc());
				mapProperties.put("state", erpData.getValue().getStateDesc());
				mapProperties.put("clientName", erpData.getValue().getCustomerName());
//				mapProperties.put("amount", erpData.getValue().getGrandTotal());
//				mapProperties.put("nameOfDiscount", erpData.getValue().getPageNumberDesc());
//				mapProperties.put("discountValue", erpData.getValue().getDiscountValue());
//				mapProperties.put("iGst", erpData.getValue().getIgst());
//				mapProperties.put("caption", erpData.getValue().getCaption());
				mapProperties.put("approvalStatus", "PENDING");
//				mapProperties.put("cGst", erpData.getValue().getCgst());
//				mapProperties.put("sGst", erpData.getValue().getSgst());
				mapProperties.put("phone", erpData.getValue().getMobileNo());
				mapProperties.put("Code", erpData.getValue().getAdId());
				mapProperties.put("categoryType", erpData.getValue().getAdsSubType());
				mapProperties.put("address",erpData.getValue().getOfficeAddress());
				mapProperties.put("gstIn", erpData.getValue().getGstIn());
				mapProperties.put("mobileNo", erpData.getValue().getPhoneNumber());
				mapProperties.put("categoryName", erpData.getValue().getCategoryStr());
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
//				mapProperties.put("bankOrBranch", erpData.getValue().getBankOrUpi());
//				mapProperties.put("cashReceiptNo", erpData.getValue().getCashReceiptNo());
//				mapProperties.put("scheme", erpData.getValue().getSchemeStr());

				mapProperties.put("street", erpData.getValue().getHouseNo());
				mapProperties.put("gstNo", erpData.getValue().getGstNo());
				mapProperties.put("pinCode", erpData.getValue().getPinCode());
//				mapProperties.put("noOfInsertion", erpData.getValue().getNoOfInsertions());
				mapProperties.put("createdTs", erpData.getValue().getCreatedTs());
//				mapProperties.put("sizeHeight", erpData.getValue().getSizeHeightD());
//				mapProperties.put("sizeWidth", erpData.getValue().getSizeWidthD());
//				mapProperties.put("Position", erpData.getValue().getPageNumberDesc());
				mapProperties.put("employeeHrCode", erpData.getValue().getCreatedBy());
				mapProperties.put("employee", erpData.getValue().getCustomerName());
//				mapProperties.put("totalPremium", erpData.getValue().getPremiumTotal());
//				mapProperties.put("afterTotalPremium", erpData.getValue().getAfterPremiumTotal());
//				mapProperties.put("totalAfterDiscount", erpData.getValue().getAfterDiscountTotal());
				mapProperties.put("withSchemeTotalAmount", erpData.getValue().getGrandTotal());
//				if (erpData.getValue().getSizeWidth() != null && erpData.getValue().getSizeHeight() != null) {
//					mapProperties.put("space",
//							erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight());
//				}
//				mapProperties.put("cardRate", erpData.getValue().getAmount());
//				mapProperties.put("rate", erpData.getValue().getAmount());
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
					String formatToIndianCurrency = formatToIndianCurrency(erpData.getValue().getGrandTotal());
				    dynamicTableRows.append("<tr>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(formattedDate).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(editions).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeHeightD()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeWidthD()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getSizeWidth() * erpData.getValue().getSizeHeight()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(formatToIndianCurrency).append("</td>")
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
//		            try {
//		            	if(fileNames1 != null && !fileNames1.isEmpty()) {
////						String encodeImageToBase64 = this.encodeImageToBase64(fileNames1.get(0));
////						String base64DataUrl = "data:image/png;base64," + encodeImageToBase64;
////						String emailContent = "<img src=\"" + encodeImageToBase64 + "\" alt=\"Signature\" style=\"width: 200px;\">";
////						mapProperties.put("signature", "http://97.74.94.194:8080/staticresources/docs/SIGN_0_1678051b-9acb-481c-abb5-d1c132c1d203SIGN_0.png"); 
//						mapProperties.put("signature", prop.getProperty("TOMCAT_SERVER")+TOMCAT_PATH+fileNames1.get(0));
//		            	}
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		            List<String> cids = new ArrayList<>(mapData1.keySet());
		            
				emailTo.setTemplateName(GeneralConstants.DISPLAY_PAYMENT);
				emailTo.setTemplateProps(mapProperties);

//              String imagePath = "D:\\Projects During Training\\Pictures\\Screenshots\\Screenshot (19).png";
			//	String imagePath = "http://192.168.1.86:8072/staticresources/docs/Screenshot%20(1)_902a9525-356c-4e52-bc48-36dff71011de.png";
//				File pdfFile = null;
//				try {
//					pdfFile = convertImageToPdf(imagePath);
//				} catch (IOException | DocumentException e) {
//					e.printStackTrace();
//				} catch (java.io.IOException e) {
//					e.printStackTrace();
//				}

//				List<Map<String, Object>> multiAttachments = new ArrayList<Map<String, Object>>();
//				Map<String, Object> mapData = new HashMap<>();
//				
//				List<Attachments> allAttachmentsByOrderId = rmsAttachmentsRepo.getAllAttachmentsByOrderId(erpData.getValue().getOrderId());
//				if(allAttachmentsByOrderId!=null && !allAttachmentsByOrderId.isEmpty()) {
//					for(Attachments attach:allAttachmentsByOrderId) {
//	                    mapData.put(attach.getAttachName()+".png", new FileDataSource(getDIR_PATH_DOCS()+attach.getAttachUrl()));
//					}
//				}
				
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
//					this.genratePDF(erpClassifiedsMap,fileNames);
//				} catch (IOException | DocumentException | java.io.IOException e) {
//					e.printStackTrace();
//				}
//				 String pdfFilePath =  getDIR_PATH_PDF_DOCS()+erpData.getValue().getAdId()+".pdf";
//		         mapData.put(erpData.getValue().getAdId()+".pdf", new FileDataSource(pdfFilePath));
//		
//				multiAttachments.add(mapData);
//				
//				emailTo.setDataSource(multiAttachments);
//	            emailTo.setTemplateProps(mapProperties);
				sendService.sendCommunicationMail(emailTo, emailConfigs);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String formatToIndianCurrency(Double amount) {
        // Get the Indian locale
        Locale indianLocale = new Locale("en", "IN");
        
        // Get the number format instance for the Indian locale
        NumberFormat currencyFormatter = NumberFormat.getInstance(indianLocale);

        // Set the minimum and maximum fraction digits to 2
        currencyFormatter.setMinimumFractionDigits(2);
        currencyFormatter.setMaximumFractionDigits(2);

        // Format the number
        String formattedAmount = currencyFormatter.format(amount);

        return formattedAmount;
    }
	@SuppressWarnings("unchecked")
	private Map<String, ErpClassifieds> getDaOrderDetailsForErp(List<String> orderIds) {
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
			
			String query="SELECT itm.ad_id, ds.size, gct.type, gast.ads_sub_type, gcast2.ads_type, itm.created_ts, gdct.category_type, coir.total_amount,itm.classified_ads_sub_type,itm.classified_type,itm.classified_ads_type,co.customer_id,itm.created_by FROM clf_order_items itm INNER JOIN clf_orders co ON co.order_id = itm.order_id INNER JOIN da_sizes ds ON ds.size_id = itm.size_id INNER JOIN gd_classified_types gct ON gct.id = itm.classified_type INNER JOIN gd_classified_ads_sub_types gast ON gast.id = itm.classified_ads_sub_type INNER JOIN gd_classified_ads_types gcast2 ON gcast2.id = itm.classified_ads_type INNER JOIN gd_da_classified_category gdct ON gdct.id = itm.category INNER JOIN clf_order_item_rates coir ON coir.order_id = itm.order_id WHERE co.order_type = 3 AND co.order_status = 'CLOSED' AND itm.mark_as_delete = FALSE AND co.mark_as_delete = FALSE AND itm.order_id IN ('"+joinedOrderIds+"')";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			
			if(!classifiedList.isEmpty()) {
				 Object[] obj = classifiedList.get(0);
				ErpClassifieds classified = new ErpClassifieds();

				classified.setItemId((String) obj[0]);
				classified.setOrderId((String) obj[1]);
				classified.setSize((String) obj[2]);
				classified.setClassifiedTypeStr((String) obj[3]);
				classified.setAdsSubType((String) obj[4]);
				classified.setAdsType((String) obj[5]);
				Timestamp timestamp = (Timestamp) obj[6]; 
				if (timestamp != null) {
					classified.setCreatedTs(CommonUtils.dateFormatter((Date) obj[6], "yyyy-MM-dd HH:mm"));
					classified.setCreatedDate(CommonUtils.dateFormatter((Date) obj[6], "yyyyMMdd"));
					classified.setBookingDate(CommonUtils.dateFormatter((Date) obj[6], "yyyy-MM-dd HH:mm:ss"));
				} else {
				    classified.setCreatedTs(null);
				}
				classified.setCategoryStr((String) obj[7]);
				classified.setGrandTotal(((Float) obj[8]) != null ? ((Float) obj[8]).doubleValue() : 0.0);
				classified.setClassifiedAdsSubType((Integer) obj[9]);;
				classified.setClassifiedType((Integer) obj[10]);
				classified.setClassifiedAdsType((Integer) obj[11]);
				
				
				itemIds.add((String) obj[0]);
				createdByIds.add((Integer) obj[13]);
				customerIds.add((String) obj[12]);
				classifiedsMap.put((String) obj[0], classified);

			if (itemIds != null && !itemIds.isEmpty()) {
				List<Object[]> editionsList = clfEditionsRepo.getDaEditionIdAndNameOnItemId(itemIds);
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
								userTypes.add(umUser.getGdUserTypes().getUserTypeId());
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
	private AddToCartRequest calculateTotalAmount(AddToCartRequest payload, LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		ClassifiedRateDetails classifiedRateDetails = new ClassifiedRateDetails();
		try {
			double rates = 0.0;
			double extraLineRate = 0.0;
			Integer minLines = 0;
			Integer lineCount = 0;
			Double tax = 0.0;
			Double categoryDiscount = 0.0;
			Double categoryDiscountAmount = 0.0;
			Double speicalDiscountAmount = 0.0;
			Double categoryAmount =0.0;
			Double cAmount;
			Double sAmount;
			Double speicalAmount = 0.0;
			Double speicalDiscount = 0.0;
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

			genericApiResponse = this.getDisplatRates(payload.getItemList().get(0));
			PostClassifiedRates postClassifiedRates = payload.getItemList().get(0).getPostClassifiedRates();

			GdClassifiedSchemes gdClassifiedSchemes = new GdClassifiedSchemes();

			classifiedRateDetails = (ClassifiedRateDetails) genericApiResponse.getData();
			tax = classifiedRateDetails.getTax();
			categoryDiscount = classifiedRateDetails.getCategoryDiscount();
			speicalDiscount = classifiedRateDetails.getSpecialDiscount();
//			classifiedRateDetails.setAgencyCommission(classifiedRateDetails.getAgencyCommission());
			classifiedRateDetails.setTax(classifiedRateDetails.getTax());
//			agencyCommition = classifiedRateDetails.getAgencyCommission();

			for (ClassifiedRates clfRates : classifiedRateDetails.getClassifiedRates()) {
				Double rates1 = clfRates.getRate();
				rates += rates1;
				Double rate1 = rates1;
				taxAmount1 = 0.0;
				taxAmount2 = 0.0;
				
				categoryDiscountAmount = clfRates.getRate() * categoryDiscount / 100;
				cAmount = clfRates.getRate() - categoryDiscountAmount;
				categoryAmount = categoryAmount + categoryDiscountAmount;
				
				speicalDiscountAmount = cAmount * speicalDiscount / 100;
				sAmount = cAmount - speicalDiscountAmount;
				speicalAmount = speicalAmount + speicalDiscountAmount;
				
//				cmsAmount = rate1 * agencyCommition / 100;
//				cmsAmount = Double.valueOf(df1.format(cmsAmount));
//				BigDecimal bcmsAmount = BigDecimal.valueOf(cmsAmount);
//				bcmsAmount = bcmsAmount.setScale(2, RoundingMode.HALF_UP);
//				cmsAmount = bcmsAmount.doubleValue();
//				totalAmount1 = rate1 - cmsAmount;
				totalAmount1 = sAmount;
				taxAmount1 = (totalAmount1) * tax / 100 / 2;
				taxAmount2 = (totalAmount1) * tax / 100 / 2;
//				totalAgencyCommition = totalAgencyCommition + cmsAmount;

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
				totalAmount = totalAmount + totalAmount1 + taxAmount;
			}

			postClassifiedRates.setRate(rates);
			Double rate = postClassifiedRates.getRate();
			postClassifiedRates.setRate(rate);
			postClassifiedRates.setAfterCatgoryDiscount(categoryAmount);
			postClassifiedRates.setAfterSpeicalDiscount(speicalAmount);
			postClassifiedRates.setCategoryDiscountPercentage(categoryDiscount);
			postClassifiedRates.setSpecialDiscountPercentage(speicalDiscount);

			totalAmount = Double.valueOf(df.format(totalAmount));
			if (totalAmount - Math.floor(totalAmount) >= 0.50) {
				totalAmount = Math.ceil(totalAmount);
			} else {
				totalAmount = Math.floor(totalAmount);
			}
			postClassifiedRates.setGstTaxAmount(totalTaxAmount);
//			postClassifiedRates.setAgencyCommitionAmount(totalAgencyCommition);
			postClassifiedRates.setTotalAmount(totalAmount);
			payload.getItemList().get(0).setPostClassifiedRates(postClassifiedRates);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return payload;
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
				customerDetailsFromDB.get().setSignatureId(customerDetails.getSignature() != null ? customerDetails.getSignature():customerDetailsFromDB.get().getSignatureId());
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

			daPublishDatesRepo.removeClfPublishDatesOnItemId(true, loggedUser.getUserId(), new Date(),
					payload.getItemId());

			clfEditionsRepo.removeClfEditionsOnItemId(true, loggedUser.getUserId(), new Date(), payload.getItemId());

			daOrderItemRatesRepo.removeClfItemRatesOnItemId(true, loggedUser.getUserId(), new Date(),
					payload.getItemId());
			
			clfTemplateContentRepo.removeClfTemplateContent(true,loggedUser.getUserId(),new Date(),payload.getItemId());

		} catch (Exception e) {
			e.printStackTrace();
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Something went wrong. Please contact our administrator.");
		}
		return genericApiResponse;
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


	@Override
	public GenericApiResponse getDisplatRates(ClassifiedsOrderItemDetails itemDetails) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		ClassifiedRateDetails classifiedRateDetails = new ClassifiedRateDetails();
		List<ClassifiedRates> clfRates = new ArrayList<>();
		Integer sspCommission = 1;

		List<DaRates> rates = new ArrayList<DaRates>();
		rates = daRatesRepo.getRates1(itemDetails.getAdsType(), itemDetails.getEditions(), itemDetails.getAdsSubType(),
				itemDetails.getSize(),itemDetails.getCategory());
		if (!rates.isEmpty() && rates.size() != itemDetails.getEditions().size()) {
			for (DaRates cl : rates) {
				itemDetails.getEditions().remove((Integer) cl.getEditionId());
			}

			List<DaRates> rates1 = daRatesRepo.getRates(itemDetails.getAdsType(), itemDetails.getEditions(),
					itemDetails.getAdsSubType(), itemDetails.getSize(),itemDetails.getCategory());
			rates.addAll(rates1);
		}
		if (rates.isEmpty()) {
			rates = daRatesRepo.getRates(itemDetails.getAdsType(), itemDetails.getEditions(),
					itemDetails.getAdsSubType(), itemDetails.getSize(),itemDetails.getCategory());
		}

		ClassifiedRates classifiedRates = null;
		if (!rates.isEmpty()) {
			classifiedRateDetails.setClassifiedRates(clfRates);
			List<String> gdDiscountErpCodes = new ArrayList<String>();
			
			List<String> allLegalDiscountErpCodes = gdDaLegalDiscountsRepo.getAllLegalDiscountErpCodes();
			List<String> inputErpCodes = itemDetails.getEditionsErpRefIds();
			boolean anyOne = inputErpCodes.stream().anyMatch(allLegalDiscountErpCodes::contains);
			List<GdDaLegalDiscounts> gdDaLegalDiscountsList = new ArrayList<>();
			boolean allValid = inputErpCodes.stream().allMatch(allLegalDiscountErpCodes::contains);
			if(GeneralConstants.DIVISION.equalsIgnoreCase(itemDetails.getEditionType() + "")
					&& GeneralConstants.LEGAL.equalsIgnoreCase(itemDetails.getCategory() + "")) {
				gdDaLegalDiscountsList = gdDaLegalDiscountsRepo.getLegalDiscountPercentageByCategory(
				        inputErpCodes, itemDetails.getCategory()
				    );
			}
			if (!GeneralConstants.LEGAL.equalsIgnoreCase(itemDetails.getCategory() + "")&& allValid) {
			    gdDaLegalDiscountsList = gdDaLegalDiscountsRepo.getLegalDiscountPercentageByCategory(
			        inputErpCodes, itemDetails.getCategory()
			    );
			}
			
//			List<GdDaLegalDiscounts> gdDaLegalDiscountsList = gdDaLegalDiscountsRepo.getLegalDiscountPercentage(itemDetails.getEditionsErpRefIds());
//			List<GdDaLegalDiscounts> gdDaLegalDiscountsList = gdDaLegalDiscountsRepo.getLegalDiscountPercentageByCategory(itemDetails.getEditionsErpRefIds(), itemDetails.getCategory());
			if (GeneralConstants.DIVISION.equalsIgnoreCase(itemDetails.getEditionType() + "")
					&& GeneralConstants.LEGAL.equalsIgnoreCase(itemDetails.getCategory() + "") && gdDaLegalDiscountsList != null && !gdDaLegalDiscountsList.isEmpty()) { // checking category type is legal and
																				// edition type is division
				if (itemDetails != null && !itemDetails.getEditionsErpRefIds().isEmpty()
						&& itemDetails.getEditionsErpRefIds().size() == 1) {
					if (gdDaLegalDiscountsList != null && !gdDaLegalDiscountsList.isEmpty()) {
						if (gdDaLegalDiscountsList.size() == itemDetails.getEditionsErpRefIds().size()) {
							classifiedRateDetails.setCategoryDiscount(gdDaLegalDiscountsList.get(0).getDiscount());
							classifiedRateDetails.setSpecialDiscount(gdDaLegalDiscountsList.get(0).getSpecialDiscount());
						} else {
							genericApiResponse.setStatus(1);
							genericApiResponse.setMessage("Please select only HYD Division or Other Division.");
						}
					} else {
						genericApiResponse.setStatus(1);
						genericApiResponse.setMessage("Legal Discounts is empty,Please contact support team.");
					}
				} else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("For Legal Category Please select only one Edition and select only HYD Division or Other Division");
				}
			}else if(GeneralConstants.DIVISION.equalsIgnoreCase(itemDetails.getEditionType() + "") && anyOne ) {
				if(gdDaLegalDiscountsList != null && !gdDaLegalDiscountsList.isEmpty()) {
					classifiedRateDetails.setCategoryDiscount(gdDaLegalDiscountsList.get(0).getDiscount());
					classifiedRateDetails.setSpecialDiscount(gdDaLegalDiscountsList.get(0).getSpecialDiscount());
				}else {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("Please select only HYD Divisions or Other Divisions.");
				}
				
			}else {
				GdDaDiscounts gdDaDiscounts = gdDaDiscountsRepo.getDiscountPercentage(itemDetails.getCategory(),
						itemDetails.getEditionType());
				if (gdDaDiscounts != null) {
					classifiedRateDetails.setCategoryDiscount(gdDaDiscounts.getDiscount());
					classifiedRateDetails.setSpecialDiscount(gdDaDiscounts.getSpecialDiscount());

//					List<String> groups = new ArrayList<String>();
//					groups.add(GeneralConstants.DA);
//					List<GdSettingsDefinitions> settings = settingRepo.getSettingsBySTypeGrps(1, groups);
//					if (!settings.isEmpty()) {
//						Map<String, String> settingValues = settings.stream()
//								.collect(Collectors.toMap(GdSettingsDefinitions::getSettingShortId,
//										GdSettingsDefinitions::getSettingDefaultValue));
//
//						classifiedRateDetails.setSpecialDiscount(settingValues.containsKey("SSP_SPECIAL_DISCOUNT")
//								&& settingValues.get("SSP_SPECIAL_DISCOUNT") != null
//								&& !((String) settingValues.get("SSP_SPECIAL_DISCOUNT")).trim().isEmpty()
//										? Double.parseDouble(settingValues.get("SSP_SPECIAL_DISCOUNT"))
//										: 0);
//					}
				}
			}
			for (DaRates cr : rates) {
				classifiedRates = new ClassifiedRates();
				classifiedRates.setRate(cr.getRate());
				classifiedRates.setEditionId(cr.getEditionId());
				clfRates.add(classifiedRates);
//				classifiedRateDetails.setAgencyCommission(sspCommission.doubleValue());
				classifiedRateDetails.setTax(Double.parseDouble("5"));
			}
			genericApiResponse.setData(classifiedRateDetails);

		} else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("There is no rate card available with the selected details.");
		}
		return genericApiResponse;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public GenericApiResponse getDisplayAdsList(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);

		String query = "select co.user_type_id, itm.created_ts ,itm.category,gdcc.category_type,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id, itm.order_id, co.payment_status AS co_payment_status,itm.ad_id,co.erp_order_id,itm.created_by,co.attached_id,cp.order_id as payments_order_id,cpd.download_status as publishDateDownloadStatus,co.comments,itm.classified_ads_type,gcast.ads_sub_type,co.booking_unit,bu.booking_location,itm.size_id,da.size,itm.template_id,itm.main_attachment_id,itm.is_settled,itm.art_work_attachment_id,cp.ecom_order_id from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_publish_dates cpd on itm.item_id=cpd.item_id left join booking_units bu on bu.booking_code = co.booking_unit inner join da_sizes da on itm.size_id = da.size_id where itm.mark_As_Delete = false and co.order_type = 3 and cp.payment_status = 'success'";
//		String query = "select co.user_type_id, itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,co.erp_order_id,itm.created_by,co.attached_id,cp.order_id as payments_order_id,cpd.download_status as publishDateDownloadStatus,co.comments,itm.classified_ads_type,gcast.ads_sub_type,co.booking_unit,bu.booking_location from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_publish_dates cpd on itm.item_id=cpd.item_id left join booking_units bu on bu.booking_code = co.booking_unit where itm.mark_As_Delete = false and co.order_type = 3 and cp.payment_status = 'success'";

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
		
		if(payload.getUserId() != null) {
			query = query + " and itm.created_by = "+payload.getUserId();
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
					&& !"APPROVER".equalsIgnoreCase(loggedUser.getRoleName()) &&  !"BILLING_ADMIN".equalsIgnoreCase(loggedUser.getRoleName())) {
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
		List<String> artWorkAttachmentIds = new ArrayList<String>();
		List<Integer> createdByForAgency = new ArrayList<Integer>();
		List<String> templateIds = new ArrayList<>();
		for (Object[] objs : classifiedList) {
			Classifieds classified = new Classifieds();
			classified.setUserTypeId((Integer) objs[0]);
			classified.setRequestedDate(CommonUtils.dateFormatter((Date) objs[1], "dd-MM-yyyy"));
			classified.setCategoryId(((Integer) objs[2]).intValue());
			classified.setCategory((String) objs[3]);
			Double val = (Double.valueOf(df.format(objs[4])));
			classified.setPaidAmount(new BigDecimal(df1.format(val)));
			classified.setApprovalStatus((String) objs[5]);
			classified.setClfPaymentStatus((String) objs[6]);
			classified.setDownloadStatus(objs[7] == null ? false : (Boolean) objs[7]);
			classified.setMatter((String) objs[8]);
			classified.setItemId((String) objs[9]);
			classified.setOrderId((String) objs[10]);
			classified.setPaymentStatus((String) objs[11]);
			
			classified.setAdId((String) objs[12]);
			classified.setErpOrderId((String) objs[13]);
//			classified.setSubCategory((String) objs[11]);
//			classified.setScheme((String) objs[14]);
		
			
//			if ("3".equalsIgnoreCase(objs[0] + "")) {
				classified.setCreatedBy((Integer) objs[14]);
				createdByForAgency.add((Integer) objs[14]);
				classified.setUserTypeId((Integer) objs[0]);
//			}
			classified.setAttachedId(objs[15] != null ? (String) objs[15] : null);
			attachedIds.add((String) objs[15]);
			classified.setPaymentsOrderId((String) objs[16]);
			classified.setPublishDateDownloadStatus((boolean) objs[17]);
			classified.setComments((String) objs[18]);
			classified.setAdSubTypeId((Integer) objs[19]);
			classified.setAdSubtype((String) objs[20]);
			classified.setBookingUnitCode((Integer) objs[21]);
			classified.setBookingUnitDesc((String) objs[22]);
			classified.setSizeId((Integer) objs[23]);
			classified.setSize((String) objs[24]);
			if((String) objs[25] != null && !"".equalsIgnoreCase((String) objs[25])) {
				classified.setTemplateId((String) objs[25]);
				templateIds.add( (String) objs[25]);
			}
			
			classified.setMainAttachmentId((String) objs[26]);
			classified.setIsSettled((Boolean) objs[27]);
			classified.setArtWorkAttachmentId((String) objs[28]);
			classified.setEcomOrderId((String) objs[29]);
			artWorkAttachmentIds.add((String) objs[28]);
			
//			DaTemplates templateById = daTemplateRepo.getTemplateById((String) objs[25]);
			itemIds.add((String) objs[9]);
			classifiedsMap.put((String) objs[9], classified);
		}

		if (itemIds != null && !itemIds.isEmpty()) {
			List<Object[]> editionsList = daEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
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
		if(itemIds != null && !itemIds.isEmpty()) {
			List<ClfTemplateContent> templateAttachmentsOnItemIds = clfTemplateContentRepo.getTemplateAttachmentsOnItemIds(itemIds);
			if(templateAttachmentsOnItemIds != null && !templateAttachmentsOnItemIds.isEmpty()) {
				classifiedsMap.entrySet().forEach(erpData->{
					  List<String> attachmentIds = templateAttachmentsOnItemIds.stream()
				                .filter(f -> f.getItemId().equalsIgnoreCase(erpData.getValue().getItemId()))
				                .map(ClfTemplateContent::getAttachmentId) // Assuming getAttachmentId() exists
				                .collect(Collectors.toList());
					  if (!attachmentIds.isEmpty()) {
			                erpData.getValue().setAttachmentIds(attachmentIds);
			            }
				});
			}
		}
		if(attachedIds != null && !attachedIds.isEmpty()) {
			List<Attachments> listOfAttachmentDetails = attachmentsRepo.getListOfAttachmentDetails(attachedIds);
			if(listOfAttachmentDetails != null && !listOfAttachmentDetails.isEmpty()) {
				classifiedsMap.entrySet().forEach(erpData->{
					List<String> attachmentIds = erpData.getValue().getAttachmentIds(); // Get attachment IDs set earlier

		            if (attachmentIds != null && !attachmentIds.isEmpty()) {
		                // Filter attachment details based on the stored attachment IDs
		                List<String> attachmentNames = listOfAttachmentDetails.stream()
		                        .filter(attachment -> attachmentIds.contains(attachment.getAttachId()))
		                        .map(Attachments::getAttachName) // Extract attachment names
		                        .collect(Collectors.toList());

		                // Set attachment names
		                if (!attachmentNames.isEmpty()) {
		                    erpData.getValue().setAttachmentNames(attachmentNames);
		                }
		            }
				});
			}
		}
		
		if(artWorkAttachmentIds != null && !artWorkAttachmentIds.isEmpty()) {
			List<Attachments> artWorkAttachmentList = attachmentsRepo.getListOfAttachmentDetails(artWorkAttachmentIds);
			if(artWorkAttachmentList != null && !artWorkAttachmentList.isEmpty()) {
				classifiedsMap.entrySet().forEach(erpData->{
					String artWorkAttachmentId = erpData.getValue().getArtWorkAttachmentId(); // Get attachment IDs set earlier

		            if (artWorkAttachmentId != null && !artWorkAttachmentId.isEmpty()) {
		                // Filter attachment details based on the stored attachment IDs
		            	String attachmentNames = artWorkAttachmentList.stream()
		            	        .filter(attachment -> artWorkAttachmentId.equals(attachment.getAttachId())) // Match by AttachId
		            	        .map(Attachments::getAttachName) // Get the attachment names
		            	        .collect(Collectors.joining(", "));

		                // Set attachment names
		                if (attachmentNames != null && !attachmentNames.isEmpty()) {
		                    erpData.getValue().setArtWorkAttachmentName(attachmentNames);
		                }
		            }
				});
			}
		}
		
		if(templateIds != null && !templateIds.isEmpty()) {
			List<Object[]> daOrderItemsByTemplateId = daOrderItemsRepo.getDaOrderItemsByTemplateId(templateIds);
			for(Object[] obj : daOrderItemsByTemplateId) {
				if(classifiedsMap.containsKey((String) obj[0])) {
					if(classifiedsMap.get((String) obj[0]).getTemplateImageName() != null) {
						
					}else {
						Attachments attachmentDetails = attachmentsRepo.getAttachmentOnAttachmentId((String) obj[1]);
						Classifieds classified = classifiedsMap.get((String) obj[0]);
						classified.setTemplateImageName(attachmentDetails.getAttachName());
						classified.setTemplateImageId((String) obj[1]);
						classified.setTemplateImageUrl((String) obj[2]);
						classifiedsMap.put((String) obj[0], classified);
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
		Map<String, Object> params = new HashMap<>();
		params.put("stype", SettingType.APP_SETTING.getValue());
		params.put("grps", Arrays.asList(GeneralConstants.VENDOR_COMMISSION, GeneralConstants.ENV_SET_GP));
		SettingTo settingTo = settingDao.getSMTPSettingValues(params);
		Map<String, String> emailConfigs = settingTo.getSettings();
		String isVendor = emailConfigs.get("VEND_COMM");
		if (createdByForAgency != null && !createdByForAgency.isEmpty()) {
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
						erpData.getValue().setCreatedByEmpId(umUser.getEmpCode());
						erpData.getValue().setContactNo(umUser.getMobile());
						erpData.getValue().setLogOnId(umUser.getLogonId());
						if("true".equalsIgnoreCase(isVendor)) {
							if(umUser != null && umUser.getVendorCommission() != null) {
								erpData.getValue().setVendorCommPer(umUser.getVendorCommission());
								erpData.getValue().setVendorCommission(erpData.getValue().getPaidAmount().multiply(BigDecimal.valueOf(umUser.getVendorCommission())).divide(BigDecimal.valueOf(100)));
							}
						}else {
							erpData.getValue().setVendorCommPer(0);
							erpData.getValue().setVendorCommission(null);
						}
						
						
					}
				});
			}
			
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
	
	public String convertDateFormat(String date, String fromPattern, String toPattern) {
		DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern(fromPattern);
		DateTimeFormatter toFormatter = DateTimeFormatter.ofPattern(toPattern);
		LocalDate localDate = LocalDate.parse(date, fromFormatter);
		return localDate.format(toFormatter);
	}

	@Override
	public GenericApiResponse getDisplayAdsDashboardCounts(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		Map<String, Integer> countsMap = new HashMap<>();
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_PENDING, 0);
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_APPROVED, 0);
		countsMap.put(ClassifiedConstants.CLASSIFIED_APPROVAL_REJECT, 0);
		List<Object[]> dashboardStatusCounts = new ArrayList<>();
		if (loggedUser.getCustomerId() == null || loggedUser.getCustomerId().isEmpty()) {
			if ("ADMIN".equalsIgnoreCase(loggedUser.getRoleName())
					|| "SUPER_ADMIN".equalsIgnoreCase(loggedUser.getRoleName()) || "BILLING_ADMIN".equalsIgnoreCase(loggedUser.getRoleName())) {
				dashboardStatusCounts = daOrderItemsRepo.getDashboardCountsByAdminAndSuperAdmin();
			} else if ("APPROVER".equalsIgnoreCase(loggedUser.getRoleName())) {
				dashboardStatusCounts = daOrderItemsRepo.getDashboardCountsByAdmin(payload.getBookingUnit());
			} else {
				dashboardStatusCounts = daOrderItemsRepo.getDashboardCountsByLogin(loggedUser.getUserId(),
						payload.getBookingUnit());
			}
		} else {
			dashboardStatusCounts = daOrderItemsRepo.getDashboardCountsByCustomerId(loggedUser.getCustomerId(),
					payload.getBookingUnit());
		}
		for (Object[] obj : dashboardStatusCounts) {
			countsMap.put((String) obj[0], ((BigInteger) obj[1]).intValue());
		}

		genericApiResponse.setData(countsMap);
		return genericApiResponse;
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
//		LocalDate nextDay = currentDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedNextDay = nextDay.format(formatter);

		String query = "select co.user_type_id, itm.created_ts ,itm.category,gdcc.category_type,coir.rate,itm.status,cp.payment_status AS cp_payment_status,itm.download_status as itm_download_status ,itm.clf_content,itm.item_id,itm.order_id, co.payment_status AS co_payment_status,itm.ad_id,cpd.publish_date,cpd.download_status as cpd_download_status,itm.created_by,itm.size_id,da.size,itm.template_id,itm.main_attachment_id,itm.art_work_attachment_id from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_publish_dates cpd on itm.item_id = cpd.item_id inner join da_sizes da on itm.size_id = da.size_id where itm.mark_As_Delete = false and itm.status = 'APPROVED' and co.payment_status = 'APPROVED' and co.order_type = 3 and cp.payment_status = 'success'";
//		String query = "select co.user_type_id, itm.created_ts ,itm.category,gcc.classified_category,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status as itm_download_status ,itm.clf_content,itm.item_id , itm.order_id,gcs2.classified_subcategory, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,cpd.publish_date,cpd.download_status as cpd_download_status,itm.created_by from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_publish_dates cpd on itm.item_id = cpd.item_id where itm.mark_As_Delete = false and itm.status = 'APPROVED' and co.payment_status = 'APPROVED' and cp.payment_status = 'success'";
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
		List<String> templateIds = new ArrayList<>();
		for (Object[] objs : classifiedList) {
			Classifieds classified = new Classifieds();
//			classified.setContactNo((String)objs[0]);
			classified.setRequestedDate(CommonUtils.dateFormatter((Date) objs[1], "dd-MM-yyyy"));
			classified.setCategoryId(((Integer) objs[2]).intValue());
			classified.setCategory((String) objs[3]);
			Double val = (Double.valueOf(df.format(objs[4])));
			classified.setPaidAmount(new BigDecimal(df1.format(val)));
			classified.setApprovalStatus((String) objs[5]);
			classified.setClfPaymentStatus((String) objs[6]);
//			classified.setDownloadStatus(objs[7] == null ? false : (Boolean)objs[7]);
			classified.setMatter((String) objs[8]);
			classified.setItemId((String) objs[9]);
			classified.setOrderId((String) objs[10]);
			classified.setPaymentStatus((String) objs[11]);
			classified.setAdId((String) objs[12]);
			classified.setPublishedDate(CommonUtils.dateFormatter((Date) objs[13], "dd-MM-yyyy"));
			classified.setDownloadStatus(objs[14] == null ? false : (Boolean) objs[14]);
			itemIds.add((String) objs[9]);
			if ("4".equalsIgnoreCase(objs[0] + "")) {
				classified.setCreatedBy((Integer) objs[15]);
				createdByForAgency.add((Integer) objs[15]);
			}
			classified.setSizeId((Integer) objs[16]);
			classified.setSize((String) objs[17]);
			classified.setTemplateId((String) objs[18]);
			classified.setMainAttachmentId((String) objs[19]);
			if(objs[20] != null) {
				classified.setArtWorkAttachmentId((String) objs[20]);
				Attachments attachments1 = attachmentsRepo.getAttachmentOnAttachmentId((String) objs[20]);
				if(attachments1 != null) {
					classified.setArtWorkAttachmentName(attachments1.getAttachName());
				}	
			}
			templateIds.add((String) objs[18]);
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
		
		if(templateIds != null && !templateIds.isEmpty()) {
			String templateId = String.join("','", templateIds);
			String query2 = "select itm.item_id,dt.template_image_id,dt.template_image_url from clf_order_items itm inner join da_templates dt on dt.template_id = itm.template_id where itm.template_id in ('" + templateId + "') and itm.mark_as_delete = false";
			List<Object[]> templateIdsList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query2);
			if(!templateIdsList.isEmpty()) {
				for (Object[] obj : templateIdsList) {
					List<Classifieds> gd = classifiedDetails.stream().filter(f -> (f.getItemId()).equals(obj[0]))
							.collect(Collectors.toList());
					if (!gd.isEmpty()) {
						for (Classifieds cls : gd) {
							if (cls.getAttachmentName() == null) {
								Attachments attachmentOnAttachmentId = attachmentsRepo.getAttachmentOnAttachmentId((String) obj[1]);
								cls.setTemplateImageId((String) obj[1]);
								cls.setTemplateImageUrl((String) obj[1]);
								cls.setTemplateImageName(attachmentOnAttachmentId.getAttachName());
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
						cls.setCreatedByName(user.getFirstName()+" "+(user.getLastName() != null? user.getLastName():""));
						cls.setCreatedByEmpId(user.getEmpCode() != null ? user.getEmpCode() : "");
						cls.setLogOnId(user.getLogonId());
					}
				}
			}
		}

		genericApiResponse.setData(classifiedDetails);
		return genericApiResponse;
	}
	
	
	@Override
	public GenericApiResponse getDisplayAdsPendingPaymentList(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);

		String query = "select co.user_type_id,itm.created_ts ,itm.category,gdcc.category_type,coir.rate,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id , itm.order_id,co.payment_status AS co_payment_status,itm.ad_id,co.erp_order_id,itm.created_by,itm.size_id, da.size,itm.template_id from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join da_sizes da on itm.size_id = da.size_id where itm.mark_As_Delete = false and co.order_type = 3 and co.mark_as_delete = false and co.order_status ='OPEN'";
//		String query = "select co.user_type_id,itm.created_ts ,itm.category,gcc.category_type,coir.total_amount,itm.status,cp.payment_status AS cp_payment_status,itm.download_status ,itm.clf_content,itm.item_id , itm.order_id, co.payment_status AS co_payment_status, itm.scheme AS itm_scheme, gcs.scheme AS gcs_scheme,itm.ad_id,co.erp_order_id,itm.created_by,itm.size_id, da.size from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id left join clf_payment_response_tracking cp on cp.sec_order_id = co.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join da_sizes da on itm.size_id = da.size_id where itm.mark_As_Delete = false and co.order_type = 3 and co.mark_as_delete = false and co.order_status ='OPEN'";
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
		List<String> templateIds = new ArrayList<>();
		for (Object[] objs : classifiedList) {
			Classifieds classified = new Classifieds();
//				classified.setContactNo((String)objs[0]);
			classified.setUserTypeId((Integer) objs[0]);
			classified.setRequestedDate(CommonUtils.dateFormatter((Date) objs[1], "dd-MM-yyyy"));
			classified.setCategoryId(((Integer) objs[2]).intValue());
			classified.setCategory((String) objs[3]);
			Double val = (Double.valueOf(df.format(objs[4])));
			classified.setPaidAmount(new BigDecimal(df1.format(val)));
			classified.setApprovalStatus((String) objs[5]);
			classified.setClfPaymentStatus((String) objs[6]);
			classified.setDownloadStatus(objs[7] == null ? false : (Boolean) objs[7]);
			classified.setMatter((String) objs[8]);
			classified.setItemId((String) objs[9]);
			classified.setOrderId((String) objs[10]);
			classified.setPaymentStatus((String) objs[11]);
			classified.setAdId((String) objs[12]);
			classified.setErpOrderId((String) objs[13]);		
			
			itemIds.add((String) objs[9]);
			if ("3".equalsIgnoreCase(objs[0] + "")) {
				classified.setCreatedBy((Integer) objs[14]);
				createdByForAgency.add((Integer) objs[14]);
			}
			classified.setSizeId((Integer) objs[15]);
			classified.setSize((String) objs[16]);
			classified.setTemplateId((String) objs[17]);
			templateIds.add((String) objs[17]);
			classifiedsMap.put((String) objs[9], classified);
		}

		if (itemIds != null && !itemIds.isEmpty()) {
			List<Object[]> editionsList = daEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
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

		if(templateIds != null && !templateIds.isEmpty()) {
			List<Object[]> daOrderItemsByTemplateId = daOrderItemsRepo.getDaOrderItemsByTemplateId(templateIds);
			for(Object[] obj : daOrderItemsByTemplateId) {
				if(classifiedsMap.containsKey((String) obj[0])) {
					if(classifiedsMap.get((String) obj[0]).getTemplateImageName() != null) {
						
					}else {
						Attachments attachmentDetails = attachmentsRepo.getAttachmentOnAttachmentId((String) obj[1]);
						Classifieds classified = classifiedsMap.get((String) obj[0]);
						classified.setTemplateImageName(attachmentDetails.getAttachName());
						classified.setTemplateImageId((String) obj[1]);
						classified.setTemplateImageUrl((String) obj[2]);
						classifiedsMap.put((String) obj[0], classified);
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

	@Override
	public GenericApiResponse getDisplayCartItems(LoggedUser loggedUser) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<ClfOrders> clfOrdersList = getOpenCartDetailsByLoggedInUser(loggedUser);
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		List<String> orderIds=new ArrayList<String>();
		List<CartDetails> cartDetailsList=null;
		List<String> itemIds1 = new ArrayList<>();
		if(clfOrdersList!=null) {
			 orderIds = clfOrdersList.stream().map(ClfOrders::getOrderId).collect(Collectors.toList());
		}
		if (!orderIds.isEmpty()) {
			
			List<Object[]> orderDetailsList = daOrdersRepo.getOrderDetailsList(orderIds);

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

			
			List<Object[]> clfOrderItems = daOrderItemsRepo.getOpenOrderItemsDetailsByOrderIdList(orderIds);

			// Prepare a map to hold ClassifiedsOrderItemDetails by orderId
			Map<String, ClassifiedsOrderItemDetails> orderItemsByOrderMap = new HashMap<>();
			for (Object[] obj : clfOrderItems) {
				String orderId = (String) obj[1]; 
				if (!orderItemsByOrderMap.containsKey(orderId)) {
					ClassifiedsOrderItemDetails classifiedsOrderItemDetails = new ClassifiedsOrderItemDetails();
					classifiedsOrderItemDetails.setItemId((String) obj[0]);
					classifiedsOrderItemDetails.setAdsTypeStr((String) obj[3]);
					classifiedsOrderItemDetails.setAdsSubTypeStr((String) obj[4]);
//					classifiedsOrderItemDetails.setSchemeStr((String) obj[5]);
					classifiedsOrderItemDetails.setCategoryStr((String) obj[5]);
//					classifiedsOrderItemDetails.setSubCategoryStr((String) obj[7]);
					classifiedsOrderItemDetails.setLangStr((String) obj[6]);
					classifiedsOrderItemDetails.setContent((String) obj[7]);
					classifiedsOrderItemDetails.setCreateDate((Date) obj[8]);
					classifiedsOrderItemDetails.setCreateTime((Date) obj[8]);
					Double val = (Double.valueOf(df.format(obj[9])));
					classifiedsOrderItemDetails.setAmount(Double.valueOf(df1.format(val)));
					classifiedsOrderItemDetails.setAdId((String) obj[10]);
					classifiedsOrderItemDetails.setSizeDesc((String) obj[11]);
					itemIds1.add((String) obj[0]);
					if((String) obj[12] != null) {
						classifiedsOrderItemDetails.setAttachedId((String) obj[12]);
						Attachments attachments = attachmentsRepo.getAttachmentOnAttachmentId((String) obj[12]);
						if(attachments != null) {
							classifiedsOrderItemDetails.setAttachmentName(attachments.getAttachName());
						}							
					};
					if(obj[13] != null) {
						classifiedsOrderItemDetails.setArtWorkAttachmentId((String) obj[13]);
						Attachments attachments1 = attachmentsRepo.getAttachmentOnAttachmentId((String) obj[13]);
						if(attachments1 != null) {
							classifiedsOrderItemDetails.setArtWorkAttachmentName(attachments1.getAttachName());
						}	
					}
					orderItemsByOrderMap.put(orderId, classifiedsOrderItemDetails);
				}
			}

			if(itemIds1 != null && !itemIds1.isEmpty()) {
				List<ClfTemplateContent> templateAttachmentsOnItemIds = clfTemplateContentRepo.getTemplateAttachmentsOnItemIds(itemIds1);
				if(templateAttachmentsOnItemIds != null && !templateAttachmentsOnItemIds.isEmpty()) {
					orderItemsByOrderMap.entrySet().forEach(erpData->{
						  List<String> attachmentIds = templateAttachmentsOnItemIds.stream()
					                .filter(f -> f.getItemId().equalsIgnoreCase(erpData.getValue().getItemId()))
					                .map(ClfTemplateContent::getAttachmentId) // Assuming getAttachmentId() exists
					                .collect(Collectors.toList());
						  if (!attachmentIds.isEmpty()) {
				                erpData.getValue().setAttachmentIds(attachmentIds);
				            }
					});
				}
			}
			
			List<String> itemIds = new ArrayList<>(orderItemsByOrderMap.values().stream()
					.map(ClassifiedsOrderItemDetails::getItemId).collect(Collectors.toSet()));
			List<Object[]> editionDetails = clfEditionsRepo.getDaEditionIdAndNameOnItemId(itemIds);

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
	
	public List<ClfOrders> getOpenCartDetailsByLoggedInUser(LoggedUser loggedUser) {
		List<ClfOrders> clfOrders = daOrdersRepo.getOpenOrderDetailsByLoggedInUser(loggedUser.getUserId(),
				ClassifiedConstants.ORDER_OPEN,3);
		if (!clfOrders.isEmpty())
			return clfOrders;
		return null;
	}

	@Override
	public GenericApiResponse viewDaItem(LoggedUser loggedUser, @NotNull String itemId) {

		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<Object[]> object = daOrderItemsRepo.viewDaItemDetails(itemId);
		AddToCartRequest addToCartRequest = new AddToCartRequest();
		List<ClassifiedsOrderItemDetails> classifiedsOrderItemDetails = new ArrayList<ClassifiedsOrderItemDetails>();
		List<DaTempleContentModel> templateContentList = new ArrayList<>();
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
				clfItemDetails.setSize((Integer) obj[24]);
				clfItemDetails.setTemplateId((String) obj[25] != null ? (String) obj[25] : null);
				if(obj[26] != null) {
					clfItemDetails.setArtWorkAttachmentId((String) obj[26]);
					List<Attachments> attachements = attachmentsRepo
							.getAttachmentDetails(clfItemDetails.getArtWorkAttachmentId());
					if (attachements != null && !attachements.isEmpty()) {
						clfItemDetails.setArtWorkAttachmentName(attachements.get(0).getAttachName());
					}
				}
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

			List<ClfTemplateContent> clfTemplateContentDetails = clfTemplateContentRepo.getTemplateContentDetailsOnItemId(itemId);
			if(clfTemplateContentDetails != null && !clfTemplateContentDetails.isEmpty()) {
				for (ClfTemplateContent content : clfTemplateContentDetails) {
					DaTempleContentModel templateContent = new DaTempleContentModel();
					templateContent.setTextId(content.getTextId());
					templateContent.setTemplate_id(content.getTemplateId());
					templateContent.setText_index(content.getTextIndex());
					templateContent.setContent(content.getContent());
					templateContent.setX_coordinate(content.getxCoordinate());
					templateContent.setY_coordinate(content.getyCoordinate());
					templateContent.setWidth(content.getWidth());
					templateContent.setHeight(content.getHeight());
					templateContent.setFont(content.getFont());
					templateContent.setType(content.getType());
					templateContent.setTemplate_cnt_id(content.getTemplateCntId());
					templateContent.setAttachmentId(content.getAttachmentId());
					templateContent.setAttatchUrl(content.getAttatchUrl());
					templateContent.setSrc(content.getSrc());
					templateContent.setImage_type(content.getImageType());
					
					templateContentList.add(templateContent);
				}
				
				addToCartRequest.setContentModel(templateContentList);
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
						
						if(umc.getSignatureId() != null) {
							customerDetails.setSignature(umc.getSignatureId() != null ? umc.getSignatureId():null);
							Attachments attachmentOnAttachmentId = attachmentsRepo.getAttachmentOnAttachmentId(umc.getSignatureId());
							customerDetails.setSignatureName(attachmentOnAttachmentId.getAttachName());
						}
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

	@Override
	public GenericApiResponse deleteClassified(LoggedUser loggedUser, @NotNull String itemId) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		ClfOrderItems clfOrderItems = daOrderItemsRepo.getItemDetailsOnItemId(itemId);
		if (clfOrderItems != null) {
			clfOrderItems.setMarkAsDelete(true);
			clfOrderItems.setChangedBy(loggedUser.getUserId());
			clfOrderItems.setChangedTs(new Date());

			daOrderItemsRepo.save(clfOrderItems);
			
			ClfOrders clfOrders = daOrdersRepo.getOrderDetailsOnOrderId(clfOrderItems.getClfOrders().getOrderId());
			if(clfOrders != null) {
				clfOrders.setMarkAsDelete(true);
				clfOrders.setChangedBy(loggedUser.getUserId());
				clfOrders.setChangedTs(new Date());
				
				daOrdersRepo.save(clfOrders);
			}
			genericApiResponse.setStatus(0);
			genericApiResponse.setMessage("Item Successfully deleted");
		} else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Something went wrong. Please contact our administrator.");
		}
		return genericApiResponse;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericApiResponse getDisplayAdEncodedString(String orderId, LoggedUser loggedUser) {
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
	
	@Override
	public GenericApiResponse sendDisplayEmailToSchedulingTeam(String orderId, LoggedUser loggedUser) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			if (orderId != null) {
				List<String> orderIdss = Arrays.asList(orderId);
				Map<String, ErpClassifieds> erpClassifiedsMap = this.getDisplayOrderDetailsForErp(orderIdss);
				if (erpClassifiedsMap != null && !erpClassifiedsMap.isEmpty()) {
					ClfPaymentResponseTracking clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
							.getTransactionDetOnSecOrderId(orderId);
					String status = "";
					erpClassifiedsMap.entrySet().forEach(erpData->{
						if (clfPaymentResponseTracking != null) {
							BillDeskPaymentResponseModel billDeskPaymentResponseModel = new BillDeskPaymentResponseModel();
							try {
								if(erpData.getValue().getContentStatus().equalsIgnoreCase("PENDING")) {
									this.sendDisplayMailToCustomer(erpClassifiedsMap, billDeskPaymentResponseModel, loggedUser,
											clfPaymentResponseTracking);
								}else {
									this.sendMailToCustomer(erpClassifiedsMap, loggedUser, "orderId", erpData.getValue().getContentStatus(), status);
								}
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							apiResponse.setStatus(0);
							apiResponse.setMessage("The email has been sent. Kindly check it.");
						} else {
							apiResponse.setStatus(1);
							apiResponse.setMessage("Payment Details Not Found");
						}
					});;
					
				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage("Something went wrong. Please contact our administrator.");
				}
			} else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("OrderId is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public Map<String, ErpClassifieds> getDisplayOrderDetailsForErp(List<String> orderIds) {
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
			String query = "select itm.item_id , itm.order_id , itm.classified_type,itm .classified_ads_type,itm.category,itm.lang,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id, co.user_type_id,gct.type,gct.erp_ref_id as gct_erp_ref_id,gcat.ads_type,gcat.erp_ref_id as gcat_erp_ref_id,gcast.ads_sub_type,gcast.erp_ref_id as gcast_erp_ref_id,gdcc.category_type ,gcl.language, coir.total_amount,gdcc.erp_ref_id as gdcc_erp_ref_id,co.booking_unit,bu.sales_office,bu.booking_location,bu.sold_to_party,co.customer_name,itm.status,bu.booking_unit_email,coir.gst_total,coir.rate,cprt.order_id as ro_order_id,itm.size_id,gdas.size as gdas_size,uc.state ,gs.state as state_desc,grbu.booking_name ,grbu.office_address ,grbu.gst_in ,grbu.phone_no,grbu.scheduling_mail,coir.category_discount_amount,coir.special_discount_amount,coir.category_discount_percentage,coir.special_discount_percentage  from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on itm.order_id = co.order_id inner join booking_units bu on co.booking_unit = bu.booking_code inner join clf_payment_response_tracking cprt on itm.order_id = cprt.sec_order_id inner join da_sizes gdas on itm.size_id = gdas.size_id inner join um_customers uc on uc.customer_id  = co.customer_id	left join gd_state gs on gs.state_code = uc.state left join gd_rms_booking_units grbu on grbu.booking_unit = co.booking_unit where itm.order_id in ('" + joinedOrderIds + "') and itm.mark_as_delete = false and cprt.mark_as_delete = false";
//			String query = "select itm.item_id , itm.order_id , itm.classified_type,itm .classified_ads_type,itm.scheme as itm_scheme,itm.category,itm.subcategory,itm.lang,itm.clf_content,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id , co.user_type_id,gct.type,gct.erp_ref_id as gct_erp_ref_id, gcat.ads_type,gcat.erp_ref_id as gcat_erp_ref_id,gcast.ads_sub_type,gcast.erp_ref_id as gcast_erp_ref_id,gcs.scheme as gcs_scheme,gcs.erp_ref_id as gcs_erp_ref_id,gcc.classified_category ,gcs2.classified_subcategory,gcl.language, coir.total_amount,coir.line_count,gcc.erp_ref_id as gcc_erp_ref_id,gcs2.erp_ref_id as gcs2_erp_ref_id,co.booking_unit,gcc.product_hierarchy,bu.sales_office,bu.booking_location,bu.sold_to_party,co.customer_name,itm.status,bu.booking_unit_email,coir.gst_total,coir.rate,coir.extra_line_rate,coir.agency_commition,cprt.order_id as ro_order_id from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on itm.order_id = co.order_id inner join booking_units bu on co.booking_unit = bu.booking_code inner join clf_payment_response_tracking cprt on itm.order_id = cprt.sec_order_id where itm.order_id in ('"
//					+ joinedOrderIds + "')and itm.mark_as_delete = false";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

			for (Object[] objs : classifiedList) {
				ErpClassifieds classified = new ErpClassifieds();
				classified.setItemId((String) objs[0]);
				classified.setOrderId((String) objs[1]);
				classified.setClassifiedType((Integer) objs[2]);
				classified.setClassifiedAdsType((Integer) objs[3]);
				classified.setCategory((Integer) objs[4]);
				classified.setLang((Integer) objs[5]);
				classified.setCreatedBy((Integer) objs[6]);
				classified.setCreatedTs(CommonUtils.dateFormatter((Date) objs[7], "yyyyMMddHHmmss"));
				classified.setCreatedDate(CommonUtils.dateFormatter((Date) objs[7], "dd-MM-yyyy"));
				classified.setBookingDate(CommonUtils.dateFormatter((Date) objs[7], "yyyy-MM-dd HH:mm:ss"));
				classified.setChangedBy((Integer) objs[8]);
				classified.setChangedTs(objs[9] != null ? CommonUtils.dateFormatter((Date) objs[9], "ddMMyyyy") : "");
				classified.setAdId((String) objs[10]);
				classified.setClassifiedAdsSubType((Integer) objs[11]);
				classified.setCustomerId((String) objs[12]);
				classified.setUserTypeId((Integer) objs[13]);
				classified.setClassifiedTypeStr((String) objs[14]);
				classified.setClassifiedTypeErpRefId((String) objs[15]);
				classified.setAdsType((String) objs[16]);
				classified.setAdsTypeErpRefId((String) objs[17]);
				classified.setAdsSubType((String) objs[18]);
				classified.setAdsSubTypeErpRefId((String) objs[19]);
				classified.setCategoryStr((String) objs[20]);
				classified.setLangStr((String) objs[21]);
				Double val = (Double.valueOf(df.format(objs[22])));
				classified.setPaidAmount(val);
				classified.setCategoryErpRefId((String) objs[23]);
				classified.setBookingUnit((Integer) objs[24]);
				classified.setSalesOffice((String) objs[25]);
				classified.setBookingUnitStr((String) objs[26]);
				classified.setCity((String) objs[26]);
				classified.setSoldToParty((String) objs[27]);
				classified.setCustomerName2((String) objs[28]);
				classified.setContentStatus((String) objs[29]);
				classified.setBookingUnitEmail((String) objs[30]);
				if(objs[31] != null) {
					Double gstVal = (Double.valueOf(df.format(objs[31])));
					classified.setGstTotalAmount(Double.valueOf(df1.format(gstVal)));
				}				
				Double rate = (Double.valueOf(df.format(objs[32])));
				classified.setRate(Double.valueOf(df1.format(rate)));
				classified.setRoOrderId((String) objs[33]);
				classified.setSizeId((Integer) objs[34]);
				classified.setSize((String) objs[35]);
				classified.setStateCode((String) objs[36]);
				classified.setStateDesc((String) objs[37]);
				classified.setBookingUnitName((String) objs[38]);
				classified.setOfficeAddress((String) objs[39]);
				classified.setBookingGstIn((String) objs[40]);
				classified.setBookingPhnNo((String) objs[41]);
				classified.setSchedulingMail((String) objs[42]);
				if(objs[43] != null) {
					Double categoryDiscountAmt = (Double.valueOf(df.format(objs[43])));
					classified.setCategoryDisAmount(categoryDiscountAmt);
				}
				if(objs[44] != null) {
					Double speicalDiscountAmt = (Double.valueOf(df.format(objs[44])));
					classified.setSpecialDiscountAmount(speicalDiscountAmt);
				}
				if(objs[45] != null) {
					Double categoryDisPer = (Double.valueOf(df.format(objs[45])));
					classified.setCategoryDiscountPercentage(categoryDisPer);
				}	
				if(objs[46] != null) {
					Double speicalDisPer = (Double.valueOf(df.format(objs[46])));
					classified.setSpecialDiscountPercentage(speicalDisPer);
				}				
				classified.setKeyword("Display Order");
				classified.setTypeOfCustomer("01");
				classified.setCreatedTime(CommonUtils.dateFormatter((Date) objs[7], "HHmmss"));
				classified.setOrderIdentification("03");

				itemIds.add((String) objs[0]);
				customerIds.add((String) objs[12]);
				createdByIds.add((Integer) objs[6]);
				classifiedsMap.put((String) objs[0], classified);
			}

			if (itemIds != null && !itemIds.isEmpty()) {
				List<Object[]> editionsList = daEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
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

				List<Object[]> publishDatesList = daPublishDatesRepo.getDisplayPublishDatesForErpData(itemIds);
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
								erpData.getValue().setHouseNo(umCustom.getHouseNo() +","+umCustom.getAddress1());
								if (umCustom != null && !umCustom.getCity().isEmpty()) {
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
								erpData.getValue().setLogonId(umUser.getLogonId());
								erpData.getValue().setSspUserName(umUser.getFirstName());
								erpData.getValue().setMobileNo(umUser.getMobile());
								erpData.getValue().setAddress3(umUser.getAddress());
								UmUsers approverEmails = umUsersRepository.getApproverEmails(umUser.getCreatedBy());
								erpData.getValue().setExecutiveEmpCode(approverEmails.getEmpCode());
								erpData.getValue().setExecutiveName(approverEmails.getFirstName());
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
								if("4".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
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
	
	public void sendDisplayMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap, BillDeskPaymentResponseModel payload,
			LoggedUser loggedUser, ClfPaymentResponseTracking clfPaymentResponseTracking) throws IOException {
		// TODO Auto-generated method stub
		try {
			
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();
			
			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("SSP_EMAIL_FROM"));
//			emailTo.setBcc(adminCcMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url",emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());//created by userName
			mapProperties.put("userId", loggedUser.getLogonId());//new userName
			if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				mapProperties.put("agencyUsername", loggedUser.getFirstName());
				mapProperties.put("agencyMobileNo", loggedUser.getMobile());
				mapProperties.put("agencyEmail", loggedUser.getEmail());
				mapProperties.put("isAgencyUser", "inline-block");
				mapProperties.put("isCustomerUser", "none");
				mapProperties.put("isAgencyUserCommition", true);
			}else {
				mapProperties.put("isAgencyUser", "none");
				mapProperties.put("isCustomerUser", "inline-block");
			}
			erpClassifiedsMap.entrySet().forEach(erpData -> {
				emailTo.setTo(erpData.getValue().getEmailId());
				mapProperties.put("adId", erpData.getValue().getAdId());
				mapProperties.put("clientName", erpData.getValue().getCustomerName());
				mapProperties.put("street", erpData.getValue().getHouseNo());
				mapProperties.put("pinCode", erpData.getValue().getPinCode());
				mapProperties.put("city", erpData.getValue().getBookingUnitStr());
				mapProperties.put("phone", erpData.getValue().getMobileNumber());
				mapProperties.put("state", erpData.getValue().getStateDesc());
				mapProperties.put("gstNo", erpData.getValue().getGstNo() != null ? erpData.getValue().getGstNo() : "");
				mapProperties.put("categoryName", erpData.getValue().getCategoryStr());
				mapProperties.put("noOfInsertion", erpData.getValue().getNoOfInsertions() != null ? erpData.getValue().getNoOfInsertions():"");
				mapProperties.put("date", erpData.getValue().getCreatedDate());
				mapProperties.put("employeeHrCode", erpData.getValue().getExecutiveEmpCode());
				mapProperties.put("employee", erpData.getValue().getExecutiveName());
				
				mapProperties.put("Code", "998363");
				mapProperties.put("address", erpData.getValue().getOfficeAddress());
				mapProperties.put("mobileNo", erpData.getValue().getBookingPhnNo());
				mapProperties.put("gstIn", erpData.getValue().getBookingGstIn());
				
				if(payload != null) {
					mapProperties.put("sspName", erpData.getValue().getSspUserName());
					mapProperties.put("sspVendorCode", erpData.getValue().getLogonId());
					mapProperties.put("sspPhnNo", erpData.getValue().getMobileNo());
					mapProperties.put("sspAddress", erpData.getValue().getAddress3());
				}else {
					mapProperties.put("sspAddress", erpData.getValue().getAddress1());
					mapProperties.put("sspName", userContext.getLoggedUser().getFirstName()+" "+ userContext.getLoggedUser().getLastName());
					mapProperties.put("sspVendorCode", loggedUser.getLogonId());
					mapProperties.put("sspPhnNo", userContext.getLoggedUser().getMobile() != null ? userContext.getLoggedUser().getMobile():"");
				}
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
						formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					pubDatesList.add(formattedDate);
					String formatToIndianCurrency = formatToIndianCurrency(erpData.getValue().getRate());
					String size = erpData.getValue().getSize();
					 String[] dimensions = size.split("x| X ");
					 int width = Integer.parseInt(dimensions[0].trim());
				     int height = Integer.parseInt(dimensions[1].trim());
				    dynamicTableRows.append("<tr>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(formattedDate).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(editions).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(width).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(height).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(width * height).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getAdsSubType()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(formatToIndianCurrency).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getPageNumberDesc() != null ? erpData.getValue().getPageNumberDesc() : "").append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(formatToIndianCurrency).append("</td>")
		            .append("</tr>");
		
				}
				mapProperties.put("dynamicTableRows", dynamicTableRows.toString());
				mapProperties.put("withSchemeTotalAmount",formatToIndianCurrency(erpData.getValue().getPaidAmount()));
				if(erpData.getValue().getGstTotalAmount() != null) {
					mapProperties.put("gst",formatToIndianCurrency(erpData.getValue().getGstTotalAmount()));
				}else {
					mapProperties.put("gst","");
				}
				
				
				mapProperties.put("totalValue",erpData.getValue().getPaidAmount());
				
				
				mapProperties.put("adType", erpData.getValue().getAdsType());
				mapProperties.put("adSubType", erpData.getValue().getAdsSubType());
//				mapProperties.put("pack", erpData.getValue().getSchemeStr());
//				mapProperties.put("lineCount", erpData.getValue().getLineCount());
				mapProperties.put("amount", erpData.getValue().getPaidAmount());
				mapProperties.put("langStr", erpData.getValue().getLangStr());
				mapProperties.put("category", erpData.getValue().getCategoryStr());
//				mapProperties.put("subCategory", erpData.getValue().getSubCategoryStr());
//				mapProperties.put("printAdMatter", erpData.getValue().getContent());
				mapProperties.put("size", erpData.getValue().getSize());
				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
					mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
				} else {
					mapProperties.put("approvalStatus", "PENDING");
				}
				mapProperties.put("status", "PENDING");
				mapProperties.put("clientName", erpData.getValue().getCustomerName());
//				mapProperties.put("street", erpData.getValue().getEmailId());
				mapProperties.put("customerMobile", erpData.getValue().getMobileNumber());
//				mapProperties.put("transactionDate", clfPaymentResponseTracking.getTransactionDate());
//				mapProperties.put("transactionId", clfPaymentResponseTracking.getTransactionId());
//				mapProperties.put("transactionType", clfPaymentResponseTracking.getTxnProcessType());
//				mapProperties.put("paymentStatus", clfPaymentResponseTracking.getPaymentStatus());
//				mapProperties.put("bankRefId", clfPaymentResponseTracking.getBankRefNo());
				mapProperties.put("date", erpData.getValue().getBookingDate());
				mapProperties.put("gstAmount", erpData.getValue().getGstTotalAmount());
				mapProperties.put("rate", erpData.getValue().getRate());
//				mapProperties.put("extraLineAmount", erpData.getValue().getExtraLineRateAmount());
//				mapProperties.put("agencyCommition", erpData.getValue().getAgencyCommition());
				if(erpData.getValue().getCategoryDisAmount() != null) {
					mapProperties.put("categoryDiscountAmount", erpData.getValue().getCategoryDisAmount());
				}else {
					mapProperties.put("categoryDiscountAmount", "-");
				}
				if(erpData.getValue().getSpecialDiscountAmount() != null) {
					mapProperties.put("specialDiscountAmount", erpData.getValue().getSpecialDiscountAmount());
				}else {
					mapProperties.put("specialDiscountAmount", "-");
				}
				
				if(erpData.getValue().getCategoryDiscountPercentage() != null) {
					mapProperties.put("categoryDiscountPercentage", erpData.getValue().getCategoryDiscountPercentage());
				}else {
					mapProperties.put("categoryDiscountPercentage", "");
				}
				if(erpData.getValue().getSpecialDiscountPercentage() != null) {
					mapProperties.put("specialDiscountPercentage", erpData.getValue().getSpecialDiscountPercentage());
				}else {
					mapProperties.put("specialDiscountPercentage", "");
				}
				
				
				mapProperties.put("subject_edit", true);
				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
					mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
				} else {
					mapProperties.put("approvalStatus", "PENDING");
				}
				
				if(loggedUser.getEmail() == null) {
					loggedUser.setEmail(erpData.getValue().getCreatedByEmail());
				}
				if(erpData.getValue().getLogonId() != null) {
					UmUsers usersLoginId = umUsersRepository.getUsersLoginId(erpData.getValue().getLogonId(), false);
					if(usersLoginId != null) {
						UmUsers approverEmails = umUsersRepository.getApproverEmails(usersLoginId.getCreatedBy());
						if(approverEmails != null) {
							String [] ccMails = {erpData.getValue().getCreatedByEmail() ,erpData.getValue().getSchedulingMail(),approverEmails.getEmail()};
							emailTo.setBcc(ccMails);
						}else {
							String [] ccMails = {erpData.getValue().getCreatedByEmail() ,erpData.getValue().getSchedulingMail()};
							emailTo.setBcc(ccMails);
						}
					}else {
						String [] ccMails = {erpData.getValue().getCreatedByEmail() ,erpData.getValue().getSchedulingMail()};
						emailTo.setBcc(ccMails);
					}
				}else {
					String [] ccMails = {erpData.getValue().getCreatedByEmail() ,erpData.getValue().getSchedulingMail()};
					emailTo.setBcc(ccMails);
				}
				
				
				
				mapProperties.put("rate", erpData.getValue().getRate());
				
				if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
					Double totValue = erpData.getValue().getRate();
					totValue = totValue - erpData.getValue().getAgencyCommition();
					totValue = totValue + erpData.getValue().getGstTotalAmount();
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				} else {
					Double totValue = erpData.getValue().getRate() + (erpData.getValue().getGstTotalAmount() != null ? erpData.getValue().getGstTotalAmount() : 0.0);
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				}
				
				if("11".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isTelangana", "inline-block");
				}else {
					mapProperties.put("isTelangana", "none");
				}
				if("25".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isAndhraPradesh", "inline-block");
				}else {
					mapProperties.put("isAndhraPradesh", "none");
				}
//				List<String> pubDates = erpData.getValue().getPublishDates();
//				List<String> pubDatesList = new ArrayList<String>();
//				for(String pubD : pubDates) {
//					 Date date;
//					 String formattedDate = null;
//					try {
//						date = new SimpleDateFormat("yyyyMMdd").parse(pubD);
//						formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					pubDatesList.add(formattedDate);
//				}
//				
//				//sorting Publish dates in acending order
//				pubDatesList = pubDatesList.stream()
//			            .sorted((date1, date2) -> {
//			                try {
//			                    return new SimpleDateFormat("dd-MM-yyyy").parse(date1)
//			                        .compareTo(new SimpleDateFormat("dd-MM-yyyy").parse(date2));
//			                } catch (ParseException e) {
//			                    throw new RuntimeException(e);
//			                }
//			            })
//			            .collect(Collectors.toList());
//				
//				String publishDates = pubDatesList.stream().map(Object::toString)
//						.collect(Collectors.joining(","));
//				mapProperties.put("pubDates", publishDates);
//				List<String> editionsList = erpData.getValue().getEditions();
//				String editions = editionsList.stream().map(Object::toString)
//						.collect(Collectors.joining(","));
				mapProperties.put("editions", editions);
				
				
				 Map<String, Object> mapData1 = new HashMap<>();
				 List<Attachments> attachmentsByOrderId = attachmentsRepo.getAllAttachmentsOnUserId(loggedUser.getUserId());
				
				 for(Attachments attachments : attachmentsByOrderId) {
					 if(attachments.getAttachName().startsWith("sig_")) {
						 String cid = UUID.randomUUID().toString(); // Unique Content-ID
						 mapProperties.put("vendorSign", properties.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
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
		            
		            
		            
		            
		            Map<String, Object> mapData2 = new HashMap<>();
					  UmCustomers customerDetailsOnOrderId = umCustomersRepo.getCustomerDetailsOnCustomerId(erpData.getValue().getCustomerId());
					if(customerDetailsOnOrderId != null) {
						if(customerDetailsOnOrderId.getSignatureId() !=  null) {
							List<Attachments> attachmentDetails = attachmentsRepo.getAttachmentDetails(customerDetailsOnOrderId.getSignatureId());
							if(attachmentDetails != null && !attachmentDetails.isEmpty()) {
								for(Attachments attachments : attachmentDetails) {
									 if(attachments.getAttachName().startsWith("sig_")) {
										 String cid = UUID.randomUUID().toString();
										 mapProperties.put("customerSign", properties.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
										 mapData2.put(cid, new FileDataSource(getDIR_PATH_DOCS() + attachments.getAttachUrl()));
									 }
								}
							}
						}
						
					}
					
					 List<Object> pdfFileNames2 = new ArrayList<Object>(mapData2.values());
			            List<String> fileNames2 = new ArrayList<>();
			            for(Object fileName : pdfFileNames2) {
			            	  if (fileName instanceof String) {
			                      fileNames1.add(getDIR_PATH_DOCS() + fileName.toString());
			                  } else if (fileName instanceof javax.activation.FileDataSource) {
			                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
			                      fileNames1.add(getDIR_PATH_DOCS() + fileDataSource.getName());
			                  } else {
			                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
			                  }
			            }
				emailTo.setTemplateName(GeneralConstants.DISPLAY_PAYMENT);
				emailTo.setTemplateProps(mapProperties);
				
				
				
				
				
				
				
				
				
				
				List<Map<String, Object>> multiAttachments = new ArrayList<Map<String, Object>>();
				Map<String, Object> mapData = new HashMap<>();
				List<Attachments> allAttachmentsOnUserId = attachmentsRepo.getAllAttachmentsOnUserId(loggedUser.getUserId());
				if(allAttachmentsOnUserId!=null && !allAttachmentsOnUserId.isEmpty()) {
					for(Attachments attach:allAttachmentsOnUserId) {
						if(attach.getAttachName().startsWith("sig")) {
//							mapData.put(attach.getAttachName()+".png", new FileDataSource(getDIR_PATH_DOCS()+attach.getAttachUrl()));
						}	                    
					}
				}
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
	            
	            
	            Map<String, Object> mapData3 = new HashMap<>();
	            UmCustomers customerDetailsOnOrderId1 = umCustomersRepo.getCustomerDetailsOnCustomerId(erpData.getValue().getCustomerId());
	            if(customerDetailsOnOrderId1 != null) {
					if(customerDetailsOnOrderId1.getSignatureId() !=  null) {
						List<Attachments> attachmentDetails = attachmentsRepo.getAttachmentDetails(customerDetailsOnOrderId1.getSignatureId());
						if(attachmentDetails != null && !attachmentDetails.isEmpty()) {
							for(Attachments attachments : attachmentDetails) {
								 if(attachments.getAttachName().startsWith("sig_")) {
									 String cid = UUID.randomUUID().toString();
									 mapProperties.put("customerSign", properties.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
									 mapData3.put(cid, new FileDataSource(getDIR_PATH_DOCS() + attachments.getAttachUrl()));
								 }
							}
						}
					}
					
				}
	            List<Object> pdfFileNames3 = new ArrayList<Object>(mapData3.values());
	            List<String> fileNames3 = new ArrayList<>();
	            for(Object fileName : pdfFileNames3) {
	            	  if (fileName instanceof String) {
	            		  fileNames3.add(getDIR_PATH_DOCS() + fileName.toString());
	                  } else if (fileName instanceof javax.activation.FileDataSource) {
	                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
	                      fileNames3.add(getDIR_PATH_DOCS() + fileDataSource.getName());
	                  } else {
	                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
	                  }
	            }
	            mapProperties.put("pdf_download", fileNames);
	            try {
					this.genrateSSPPDF(erpClassifiedsMap,fileNames,fileNames3);
				} catch (DocumentException | java.io.IOException e) {
					e.printStackTrace();
				}
				 String generatedpdfFilePath =  getDIR_PATH_PDF_DOCS()+erpData.getValue().getAdId()+".pdf";
		         mapData.put(erpData.getValue().getAdId()+".pdf", new FileDataSource(generatedpdfFilePath));
		
				multiAttachments.add(mapData);
				
				
				 Map<String, Object> mapData4 = new HashMap<>();
					String pdfDirPath = getDIR_PATH_DOCS();
//					String pdfFileName = "C:\\Users\\admin\\SEC\\DOCS\\Engagement of Business Represntative-Vetted-10.03.25_9dc7503c-fa67-4455-8461-c3c6526bcd45.pdf";
//					String pdfFilePath = pdfFileName;
					String pdfFileName = properties.getProperty("TERMS_CONDITIONS");
					String pdfFilePath = pdfDirPath + pdfFileName;
					File pdfFile = new File(pdfFilePath);
					if (!pdfFile.exists()) {
					    System.err.println("PDF creation failed: " + pdfFilePath);
					    throw new RuntimeException("PDF generation failed.");
					}

					mapData4.put(pdfFileName, new FileDataSource(pdfFilePath));
			
					multiAttachments.add(mapData4);
			
				
				
				
////				List<Map<String, Object>> multiAttachments = new ArrayList<Map<String, Object>>();
//				 Map<String, Object> mapData3 = new HashMap<>();
//				String pdfDirPath = getDIR_PATH_DOCS();
////				String pdfFileName = "Engagement of Business Represntative-Vetted-10.03.25_f000cc1c-6329-4466-8cf3-ab687b5a34ac.pdf";
//				String pdfFileName = properties.getProperty("TERMS_CONDITIONS");
//				String pdfFilePath = pdfDirPath + pdfFileName;
//				File pdfFile = new File(pdfFilePath);
//				if (!pdfFile.exists()) {
//				    System.err.println("PDF creation failed: " + pdfFilePath);
//				    throw new RuntimeException("PDF generation failed.");
//				}
//
//				mapData3.put(pdfFileName, new FileDataSource(pdfFilePath));
//		
//				multiAttachments.add(mapData3);
				
				emailTo.setDataSource(multiAttachments);
				emailTo.setTemplateProps(mapProperties);
				sendService.sendCommunicationToSSPMail(emailTo, emailConfigs);
			});	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("unused")
	@Override
	public GenericApiResponse approveDisplayAds(LoggedUser loggedUser, ClassifiedStatus payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			String email = "";
			String adId = "";
			daOrderItemsRepo.updateDisplayAds(payload.getStatus(), loggedUser.getUserId(), new Date(), payload.getItemId());

			List<Object[]> cusDetails = daOrdersRepo.getCustomerData(payload.getOrderId());
			for (String orderId : payload.getOrderId()) {
				Optional<Object[]> obj = cusDetails.stream().filter(f -> f[0].equals(orderId)).findFirst();
				if (obj.isPresent()) {
					Object[] ob = obj.get();
					email = ob[2] + "";
					adId = ob[3] + "";
				}

				List<String> orderIdss = Arrays.asList(orderId);
				Map<String, ErpClassifieds> erpClassifiedsMap = this.getDisplayOrderDetailsForErp(orderIdss);
				this.sendMailToCustomer(erpClassifiedsMap, loggedUser, orderId, payload.getStatus(),
						payload.getComments());

			}

			genericApiResponse.setStatus(0);
			if ("APPROVED".equalsIgnoreCase(payload.getStatus())) {
				genericApiResponse.setMessage("Successfully Approved");
			} else {
				genericApiResponse.setMessage("Successfully Disapproved and Refund will be initiated");
			}

			daOrdersRepo.updateClfOnOrderIds(payload.getStatus(), loggedUser.getUserId(), new Date(),
					payload.getOrderId(),payload.getComments());

			genericApiResponse.setStatus(0);
			if ("APPROVED".equalsIgnoreCase(payload.getStatus())) {
				ClassifiedStatus classifiedStatus = new ClassifiedStatus();
				classifiedStatus.setOrderId(payload.getOrderId());
//				this.syncronizeSAPData(commonService.getRequestHeaders(), classifiedStatus);
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

//	@SuppressWarnings({ "unused", "unchecked" })
//	private void sendMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap, LoggedUser loggedUser,
//			String orderId, String status, String comments) {
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
////			emailTo.setBcc(adminCcMails);
//			emailTo.setOrgId("1000");
//			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
//			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
//			mapProperties.put("userName", loggedUser.getLogonId());// created by userName
//			mapProperties.put("userId", loggedUser.getLogonId());// new userName
//			if ("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
//				mapProperties.put("agencyUsername", loggedUser.getFirstName());
//				mapProperties.put("agencyMobileNo", loggedUser.getMobile());
//				mapProperties.put("agencyEmail", loggedUser.getEmail());
//				mapProperties.put("isAgencyUser", "inline-block");
//			} else {
//				mapProperties.put("isAgencyUser", "none");
//			}
//			List<String> secOrderIds = Arrays.asList(orderId);
//			List<Object[]> clfPaymentResponseTracking = clfPaymentResponseTrackingRepo
//					.getTransactionDetails(secOrderIds);
//			if (clfPaymentResponseTracking != null && !clfPaymentResponseTracking.isEmpty()) {
//				for (Object[] obj : clfPaymentResponseTracking) {
//					mapProperties.put("transactionDate", obj[4]);
//					mapProperties.put("paymentStatus", obj[7]);
//					mapProperties.put("bankRefId", obj[8]);
//				}
//			}
//			erpClassifiedsMap.entrySet().forEach(erpData -> {
//				emailTo.setTo(erpData.getValue().getEmailId());
//				mapProperties.put("adId", erpData.getValue().getAdId());
//				mapProperties.put("adType", erpData.getValue().getAdsType());
//				mapProperties.put("adSubType", erpData.getValue().getAdsSubType());
////				mapProperties.put("pack", erpData.getValue().getSchemeStr());
////				mapProperties.put("lineCount", erpData.getValue().getLineCount());
//				mapProperties.put("amount", erpData.getValue().getPaidAmount());
//				mapProperties.put("langStr", erpData.getValue().getLangStr());
//				mapProperties.put("category", erpData.getValue().getCategoryStr());
////				mapProperties.put("subCategory", erpData.getValue().getSubCategoryStr());
////				mapProperties.put("printAdMatter", erpData.getValue().getContent());
////				mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
//				mapProperties.put("customerName", erpData.getValue().getCustomerName());
//				mapProperties.put("customerEmail", erpData.getValue().getEmailId());
//				mapProperties.put("customerMobile", erpData.getValue().getMobileNumber());
//				mapProperties.put("bookingDate", erpData.getValue().getBookingDate());
//				mapProperties.put("gstAmount", erpData.getValue().getGstTotalAmount());
//				mapProperties.put("rate", erpData.getValue().getRate());
//				mapProperties.put("size", erpData.getValue().getSize());
////				mapProperties.put("extraLineAmount", erpData.getValue().getExtraLineRateAmount());
////				mapProperties.put("agencyCommition", erpData.getValue().getAgencyCommition());
//				mapProperties.put("subject_edit", true);
//				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
//					mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
//				} else {
//					mapProperties.put("approvalStatus", "PENDING");
//				}
//
//				String[] ccMails = { loggedUser.getEmail(), erpData.getValue().getBookingUnitEmail() };
//				emailTo.setBcc(ccMails);
//
//				mapProperties.put("rate", erpData.getValue().getRate());
//
////				if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {// 3 Agency
////					Double totValue = erpData.getValue().getRate();
////					totValue = totValue - erpData.getValue().getAgencyCommition();
////					totValue = totValue + erpData.getValue().getGstTotalAmount();
////					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
////					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
////					mapProperties.put("roundingOff", roundedDifference);
////				} else {
//					Double totValue = erpData.getValue().getRate() + erpData.getValue().getGstTotalAmount();
//					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
//					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
//					mapProperties.put("roundingOff", roundedDifference);
////				}
//
//				if ("11".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
//					mapProperties.put("isTelangana", "inline-block");
//				} else {
//					mapProperties.put("isTelangana", "none");
//				}
//				if ("25".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
//					mapProperties.put("isAndhraPradesh", "inline-block");
//				} else {
//					mapProperties.put("isAndhraPradesh", "none");
//				}
//				if ("3".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {// 3 means Agency
//					mapProperties.put("agencyUsername", erpData.getValue().getCustomerName());
//					mapProperties.put("agencyEmail", erpData.getValue().getEmailId());
//					mapProperties.put("agencyMobileNo", erpData.getValue().getMobileNumber());
//					mapProperties.put("isAgencyUser", "inline-block");
//					mapProperties.put("isCustomerUser", "none");
//				}
//				if ("2".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {// 2 means customer
//					mapProperties.put("isAgencyUser", "none");
//					mapProperties.put("isCustomerUser", "inline-block");
//				}
//				List<String> pubDates = erpData.getValue().getPublishDates();
//				List<String> pubDatesList = new ArrayList<String>();
//				for (String pubD : pubDates) {
//					Date date;
//					String formattedDate = null;
//					try {
//						date = new SimpleDateFormat("yyyyMMdd").parse(pubD);
//						formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					pubDatesList.add(formattedDate);
//				}
//				
//				//sorting Publish dates in acending order
//				pubDatesList = pubDatesList.stream()
//			            .sorted((date1, date2) -> {
//			                try {
//			                    return new SimpleDateFormat("dd-MM-yyyy").parse(date1)
//			                        .compareTo(new SimpleDateFormat("dd-MM-yyyy").parse(date2));
//			                } catch (ParseException e) {
//			                    throw new RuntimeException(e);
//			                }
//			            })
//			            .collect(Collectors.toList());
//				
//				String publishDates = pubDatesList.stream().map(Object::toString).collect(Collectors.joining(","));
//				mapProperties.put("pubDates", publishDates);
//				List<String> editionsList = erpData.getValue().getEditions();
//				String editions = editionsList.stream().map(Object::toString).collect(Collectors.joining(","));
//				mapProperties.put("editions", editions);
//				if ("APPROVED".equalsIgnoreCase(status)) {
//						emailTo.setTemplateName(GeneralConstants.DISPLAY_ORDER_APPROVED);
//				} else {
//					mapProperties.put("comments", comments);
//						emailTo.setTemplateName(GeneralConstants.DISPLAY_ORDER_REJECTED);
//				}
//				emailTo.setTemplateProps(mapProperties);
//
//				sendService.sendCommunicationMail(emailTo, emailConfigs);
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void sendMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap, LoggedUser loggedUser,
			String orderId, String status, String comments) {
		// TODO Auto-generated method stub
		try {
			
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();
			
			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("SSP_EMAIL_FROM"));
//			emailTo.setBcc(adminCcMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url",emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());//created by userName
			mapProperties.put("userId", loggedUser.getLogonId());//new userName
			if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				mapProperties.put("agencyUsername", loggedUser.getFirstName());
				mapProperties.put("agencyMobileNo", loggedUser.getMobile());
				mapProperties.put("agencyEmail", loggedUser.getEmail());
				mapProperties.put("isAgencyUser", "inline-block");
				mapProperties.put("isCustomerUser", "none");
				mapProperties.put("isAgencyUserCommition", true);
			}else {
				mapProperties.put("isAgencyUser", "none");
				mapProperties.put("isCustomerUser", "inline-block");
			}
			erpClassifiedsMap.entrySet().forEach(erpData -> {
				emailTo.setTo(erpData.getValue().getEmailId());
				mapProperties.put("adId", erpData.getValue().getAdId());
				mapProperties.put("clientName", erpData.getValue().getCustomerName());
				mapProperties.put("street", erpData.getValue().getAddress1());
				mapProperties.put("pinCode", erpData.getValue().getPinCode());
				mapProperties.put("city", erpData.getValue().getBookingUnitStr());
				mapProperties.put("phone", erpData.getValue().getMobileNumber());
				mapProperties.put("state", erpData.getValue().getStateDesc());
				mapProperties.put("gstNo", erpData.getValue().getGstNo() != null ? erpData.getValue().getGstNo() : "");
				mapProperties.put("categoryName", erpData.getValue().getCategoryStr());
				mapProperties.put("noOfInsertion", erpData.getValue().getNoOfInsertions() != null ? erpData.getValue().getNoOfInsertions():"");
				mapProperties.put("date", erpData.getValue().getCreatedDate());
				mapProperties.put("employeeHrCode", erpData.getValue().getExecutiveEmpCode());
				mapProperties.put("employee", erpData.getValue().getExecutiveName());
//				mapProperties.put("sspName", erpData.getValue().getSspUserName());
//				mapProperties.put("sspVendorCode", erpData.getValue().getLogonId());
//				mapProperties.put("sspPhnNo", userContext.getLoggedUser().getMobile() != null ? userContext.getLoggedUser().getMobile():"");
//				mapProperties.put("sspPhnNo", erpData.getValue().getMobileNo());
				mapProperties.put("Code", "998363");
				mapProperties.put("address", erpData.getValue().getOfficeAddress());
				mapProperties.put("mobileNo", erpData.getValue().getBookingPhnNo());
				mapProperties.put("gstIn", erpData.getValue().getBookingGstIn());
//				mapProperties.put("sspAddress", erpData.getValue().getAddress1());
				mapProperties.put("comments", comments != null ? comments : "");
				
				
					mapProperties.put("sspName", erpData.getValue().getSspUserName());
					mapProperties.put("sspVendorCode", erpData.getValue().getLogonId());
					mapProperties.put("sspPhnNo", erpData.getValue().getMobileNo());
					mapProperties.put("sspAddress", erpData.getValue().getAddress3());
				
//					mapProperties.put("sspAddress", erpData.getValue().getAddress1());
//					mapProperties.put("sspName", userContext.getLoggedUser().getFirstName()+" "+ userContext.getLoggedUser().getLastName());
//					mapProperties.put("sspVendorCode", loggedUser.getLogonId());
//					mapProperties.put("sspPhnNo", userContext.getLoggedUser().getMobile() != null ? userContext.getLoggedUser().getMobile():"");
				
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
						formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					pubDatesList.add(formattedDate);
					String formatToIndianCurrency = formatToIndianCurrency(erpData.getValue().getRate());
					String size = erpData.getValue().getSize();
					 String[] dimensions = size.split("x| X ");
					 int width = Integer.parseInt(dimensions[0].trim());
				     int height = Integer.parseInt(dimensions[1].trim());
				    dynamicTableRows.append("<tr>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(formattedDate).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(editions).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(width).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(height).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(width * height).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getAdsSubType()).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(formatToIndianCurrency).append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getPageNumberDesc() != null ? erpData.getValue().getPageNumberDesc() : "").append("</td>")
		            .append("<td style=\"border: 1px solid black; padding: 8px;text-align:right;\">").append(formatToIndianCurrency).append("</td>")
		            .append("</tr>");
		
				}
				mapProperties.put("dynamicTableRows", dynamicTableRows.toString());
				mapProperties.put("withSchemeTotalAmount",formatToIndianCurrency(erpData.getValue().getPaidAmount()));
				if(erpData.getValue().getGstTotalAmount()!= null) {
					mapProperties.put("gst",formatToIndianCurrency(erpData.getValue().getGstTotalAmount()));
				}else {
					mapProperties.put("gst","");
				}
				
				
				mapProperties.put("totalValue",erpData.getValue().getPaidAmount());
				
				
				mapProperties.put("adType", erpData.getValue().getAdsType());
				mapProperties.put("adSubType", erpData.getValue().getAdsSubType());
//				mapProperties.put("pack", erpData.getValue().getSchemeStr());
//				mapProperties.put("lineCount", erpData.getValue().getLineCount());
				mapProperties.put("amount", erpData.getValue().getPaidAmount());
				mapProperties.put("langStr", erpData.getValue().getLangStr());
				mapProperties.put("category", erpData.getValue().getCategoryStr());
//				mapProperties.put("subCategory", erpData.getValue().getSubCategoryStr());
//				mapProperties.put("printAdMatter", erpData.getValue().getContent());
				if(erpData.getValue().getCategoryDisAmount() != null) {
					mapProperties.put("categoryDiscountAmount", erpData.getValue().getCategoryDisAmount());
				}else {
					mapProperties.put("categoryDiscountAmount", "");
				}
				if(erpData.getValue().getCategoryDisAmount() != null) {
					mapProperties.put("categoryDiscountAmount", erpData.getValue().getCategoryDisAmount());
				}else {
					mapProperties.put("categoryDiscountAmount", "");
				}
				
				if(erpData.getValue().getSpecialDiscountAmount() != null) {
					mapProperties.put("specialDiscountAmount", erpData.getValue().getSpecialDiscountAmount());
				}else {
					mapProperties.put("specialDiscountAmount", "");
				}
				if(erpData.getValue().getCategoryDiscountPercentage() != null) {
					mapProperties.put("categoryDiscountPercentage", erpData.getValue().getCategoryDiscountPercentage());	
				}else {
					mapProperties.put("categoryDiscountPercentage", "");
				}
				if(erpData.getValue().getSpecialDiscountPercentage() != null) {
					mapProperties.put("specialDiscountPercentage", erpData.getValue().getSpecialDiscountPercentage());
				}else {
					mapProperties.put("specialDiscountPercentage", "");
				}
				
				mapProperties.put("size", erpData.getValue().getSize());
				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
					mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
				} else {
					mapProperties.put("approvalStatus", "PENDING");
				}
				mapProperties.put("status", status);
				mapProperties.put("clientName", erpData.getValue().getCustomerName());
//				mapProperties.put("street", erpData.getValue().getEmailId());
				mapProperties.put("customerMobile", erpData.getValue().getMobileNumber());
//				mapProperties.put("transactionDate", clfPaymentResponseTracking.getTransactionDate());
//				mapProperties.put("transactionId", clfPaymentResponseTracking.getTransactionId());
//				mapProperties.put("transactionType", clfPaymentResponseTracking.getTxnProcessType());
//				mapProperties.put("paymentStatus", clfPaymentResponseTracking.getPaymentStatus());
//				mapProperties.put("bankRefId", clfPaymentResponseTracking.getBankRefNo());
				mapProperties.put("date", erpData.getValue().getBookingDate());
				mapProperties.put("gstAmount", erpData.getValue().getGstTotalAmount() != null ?erpData.getValue().getGstTotalAmount() :"");
				mapProperties.put("rate", erpData.getValue().getRate());
//				mapProperties.put("extraLineAmount", erpData.getValue().getExtraLineRateAmount());
//				mapProperties.put("agencyCommition", erpData.getValue().getAgencyCommition());
				mapProperties.put("subject_edit", true);
				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
					mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
				} else {
					mapProperties.put("approvalStatus", "PENDING");
				}
				
				if(loggedUser.getEmail() == null) {
					loggedUser.setEmail(erpData.getValue().getCreatedByEmail());
				}
				
				UmUsers usersLoginId = umUsersRepository.getUsersLoginId(erpData.getValue().getLogonId(), false);
                if(usersLoginId != null) {
                    UmUsers approverEmails = umUsersRepository.getApproverEmails(usersLoginId.getCreatedBy());
                    if(approverEmails != null) {
                        String [] ccMails = {erpData.getValue().getCreatedByEmail() ,erpData.getValue().getSchedulingMail(), approverEmails.getEmail()};
                        emailTo.setBcc(ccMails);
                    }else {
                        String [] ccMails = {erpData.getValue().getCreatedByEmail() ,erpData.getValue().getSchedulingMail()};
                        emailTo.setBcc(ccMails);
                    }
                }else {
                    String [] ccMails = {erpData.getValue().getCreatedByEmail() ,erpData.getValue().getSchedulingMail()};
                    emailTo.setBcc(ccMails);
                }
				
				mapProperties.put("rate", erpData.getValue().getRate());
				
				if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
					Double totValue = erpData.getValue().getRate();
					totValue = totValue - erpData.getValue().getAgencyCommition();
					totValue = totValue + erpData.getValue().getGstTotalAmount();
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				} else {
					Double totValue = erpData.getValue().getRate() + (erpData.getValue().getGstTotalAmount() != null ? erpData.getValue().getGstTotalAmount() :0.0) ;
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				}
				
				if("11".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isTelangana", "inline-block");
				}else {
					mapProperties.put("isTelangana", "none");
				}
				if("25".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isAndhraPradesh", "inline-block");
				}else {
					mapProperties.put("isAndhraPradesh", "none");
				}
//				List<String> pubDates = erpData.getValue().getPublishDates();
//				List<String> pubDatesList = new ArrayList<String>();
//				for(String pubD : pubDates) {
//					 Date date;
//					 String formattedDate = null;
//					try {
//						date = new SimpleDateFormat("yyyyMMdd").parse(pubD);
//						formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					pubDatesList.add(formattedDate);
//				}
//				
//				//sorting Publish dates in acending order
//				pubDatesList = pubDatesList.stream()
//			            .sorted((date1, date2) -> {
//			                try {
//			                    return new SimpleDateFormat("dd-MM-yyyy").parse(date1)
//			                        .compareTo(new SimpleDateFormat("dd-MM-yyyy").parse(date2));
//			                } catch (ParseException e) {
//			                    throw new RuntimeException(e);
//			                }
//			            })
//			            .collect(Collectors.toList());
//				
//				String publishDates = pubDatesList.stream().map(Object::toString)
//						.collect(Collectors.joining(","));
//				mapProperties.put("pubDates", publishDates);
//				List<String> editionsList = erpData.getValue().getEditions();
//				String editions = editionsList.stream().map(Object::toString)
//						.collect(Collectors.joining(","));
				mapProperties.put("editions", editions);
				
				Map<String, Object> mapData1 = new HashMap<>();
//				 List<Attachments> attachmentsByOrderId = attachmentsRepo.getAllAttachmentsOnUserId(loggedUser.getUserId());
				List<Attachments> attachmentsByOrderId = attachmentsRepo.getAllAttachmentsOnUserId(erpData.getValue().getCreatedBy());
				
				 for(Attachments attachments : attachmentsByOrderId) {
					 if(attachments.getAttachName().startsWith("sig_")) {
						 String cid = UUID.randomUUID().toString(); // Unique Content-ID
						 mapProperties.put("vendorSign", properties.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
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
		            
		            
		            
		            
		            Map<String, Object> mapData2 = new HashMap<>();
					  UmCustomers customerDetailsOnOrderId = umCustomersRepo.getCustomerDetailsOnCustomerId(erpData.getValue().getCustomerId());
					if(customerDetailsOnOrderId != null) {
						if(customerDetailsOnOrderId.getSignatureId() !=  null) {
							List<Attachments> attachmentDetails = attachmentsRepo.getAttachmentDetails(customerDetailsOnOrderId.getSignatureId());
							if(attachmentDetails != null && !attachmentDetails.isEmpty()) {
								for(Attachments attachments : attachmentDetails) {
									 if(attachments.getAttachName().startsWith("sig_")) {
										 String cid = UUID.randomUUID().toString();
										 mapProperties.put("customerSign", properties.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
										 mapData2.put(cid, new FileDataSource(getDIR_PATH_DOCS() + attachments.getAttachUrl()));
									 }
								}
							}
						}
						
					}
					
					 List<Object> pdfFileNames2 = new ArrayList<Object>(mapData2.values());
			            List<String> fileNames2 = new ArrayList<>();
			            for(Object fileName : pdfFileNames2) {
			            	  if (fileName instanceof String) {
			                      fileNames1.add(getDIR_PATH_DOCS() + fileName.toString());
			                  } else if (fileName instanceof javax.activation.FileDataSource) {
			                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
			                      fileNames1.add(getDIR_PATH_DOCS() + fileDataSource.getName());
			                  } else {
			                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
			                  }
			            }
				
				if ("APPROVED".equalsIgnoreCase(status)) {
					emailTo.setTemplateName(GeneralConstants.DISPLAY_ORDER_APPROVED);
			} else {
				mapProperties.put("comments", comments);
					emailTo.setTemplateName(GeneralConstants.DISPLAY_ORDER_REJECTED);
			}
				emailTo.setTemplateProps(mapProperties);
				
				
				
				
				
				List<Map<String, Object>> multiAttachments = new ArrayList<Map<String, Object>>();
				Map<String, Object> mapData = new HashMap<>();
				List<Attachments> allAttachmentsOnUserId = attachmentsRepo.getAllAttachmentsOnUserId(loggedUser.getUserId());
				if(allAttachmentsOnUserId!=null && !allAttachmentsOnUserId.isEmpty()) {
					for(Attachments attach:allAttachmentsOnUserId) {
						if(attach.getAttachName().startsWith("sig")) {
//							mapData.put(attach.getAttachName()+".png", new FileDataSource(getDIR_PATH_DOCS()+attach.getAttachUrl()));
						}	                    
					}
				}
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
	            
	            
	            Map<String, Object> mapData3 = new HashMap<>();
	            UmCustomers customerDetailsOnOrderId1 = umCustomersRepo.getCustomerDetailsOnCustomerId(erpData.getValue().getCustomerId());
	            if(customerDetailsOnOrderId1 != null) {
					if(customerDetailsOnOrderId1.getSignatureId() !=  null) {
						List<Attachments> attachmentDetails = attachmentsRepo.getAttachmentDetails(customerDetailsOnOrderId1.getSignatureId());
						if(attachmentDetails != null && !attachmentDetails.isEmpty()) {
							for(Attachments attachments : attachmentDetails) {
								 if(attachments.getAttachName().startsWith("sig_")) {
									 String cid = UUID.randomUUID().toString();
									 mapProperties.put("customerSign", properties.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
									 mapData3.put(cid, new FileDataSource(getDIR_PATH_DOCS() + attachments.getAttachUrl()));
								 }
							}
						}
					}
					
				}
	            List<Object> pdfFileNames3 = new ArrayList<Object>(mapData3.values());
	            List<String> fileNames3 = new ArrayList<>();
	            for(Object fileName : pdfFileNames3) {
	            	  if (fileName instanceof String) {
	            		  fileNames3.add(getDIR_PATH_DOCS() + fileName.toString());
	                  } else if (fileName instanceof javax.activation.FileDataSource) {
	                      javax.activation.FileDataSource fileDataSource = (javax.activation.FileDataSource) fileName;
	                      fileNames3.add(getDIR_PATH_DOCS() + fileDataSource.getName());
	                  } else {
	                      throw new IllegalArgumentException("Unsupported file name type: " + fileName.getClass());
	                  }
	            }
	            mapProperties.put("pdf_download", fileNames);
	            try {
					this.genrateSSPPDF(erpClassifiedsMap,fileNames,fileNames3);
				} catch (DocumentException | java.io.IOException e) {
					e.printStackTrace();
				}
				 String generatedpdfFilePath =  getDIR_PATH_PDF_DOCS()+erpData.getValue().getAdId()+".pdf";
		         mapData.put(erpData.getValue().getAdId()+".pdf", new FileDataSource(generatedpdfFilePath));
		
				multiAttachments.add(mapData);
				emailTo.setDataSource(multiAttachments);
				emailTo.setTemplateProps(mapProperties);
				
				sendService.sendCommunicationToSSPMail(emailTo, emailConfigs);
			});	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public GenericApiResponse syncronizeSAPData(GenericRequestHeaders requestHeaders, ClassifiedStatus orderIds) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			boolean flag = true;
			if (orderIds != null) {
				Map<String, ErpClassifieds> erpClassifieds = this.getDisplayOrderDetailsForErp(orderIds.getOrderId());
				if (!erpClassifieds.isEmpty()) {
					Map<String, Object> payloadJson = new HashMap<String, Object>();
					payloadJson.put("userId", requestHeaders.getLoggedUser().getUserId());
					payloadJson.put("orderId", orderIds.getOrderId());
					payloadJson.put("orgId", requestHeaders.getOrgId());
					payloadJson.put("data", erpClassifieds);
					payloadJson.put("orgOpuId", requestHeaders.getOrgOpuId());
					payloadJson.put("action", "Classified_order");
//					boolean flag = erpService.processClassifiedCreationFtpFiles(payloadJson, erpClassifieds);
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

	@SuppressWarnings("unchecked")
	public void getDisplayAddsForCurrentDate() {
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		try {
			Map<String, ErpClassifieds> classifiedsMap = new HashMap<>();
			List<String> itemIds = new ArrayList<String>();
			String currentDate = CommonUtils.dateFormatter(new Date(), "yyyy-MM-dd");
			String query = "select itm.item_id,itm.order_id,itm.classified_type,itm.classified_ads_type,itm.category,itm.lang,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id,co.user_type_id,gct.type,gcat.ads_type,gcast.ads_sub_type,gdcc.category_type,gcl.language,coir.total_amount,cpd.publish_date,um.email_id,um.mobile_no,bu.sales_office,itm.size_id,das.size as das_size FROM clf_order_items itm INNER JOIN gd_classified_types gct ON itm.classified_type = gct.id INNER JOIN gd_classified_ads_types gcat ON itm.classified_ads_type = gcat.id INNER JOIN gd_classified_ads_sub_types gcast ON itm.classified_ads_sub_type = gcast.id INNER JOIN gd_classified_languages gcl ON itm.lang = gcl.id INNER JOIN clf_order_item_rates coir ON itm.item_id = coir.item_id INNER JOIN clf_orders co ON itm.order_id = co.order_id inner join gd_da_classified_category gdcc on itm.category = gdcc.id INNER JOIN clf_publish_dates cpd ON itm.item_id = cpd.item_id INNER JOIN um_customers um ON co.customer_id = um.customer_id inner join booking_units bu on co.booking_unit = bu.booking_code inner join da_sizes das on itm.size_id = das.size_id where cpd.publish_date = TO_DATE('" + currentDate + "', 'YYYY-MM-DD') AND cpd.download_status = true AND itm.mark_as_delete = false";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
			for (Object[] objs : classifiedList) {
				ErpClassifieds classified = new ErpClassifieds();
				classified.setItemId((String) objs[0]);
				classified.setOrderId((String) objs[1]);
				classified.setClassifiedType((Integer) objs[2]);
				classified.setClassifiedAdsType((Integer) objs[3]);
				classified.setCategory((Integer) objs[4]);
				classified.setLang((Integer) objs[5]);
				classified.setCreatedBy((Integer) objs[6]);
				classified.setCreatedTs(CommonUtils.dateFormatter((Date) objs[7], "yyyyMMddHHmmss"));
				classified.setCreatedDate(CommonUtils.dateFormatter((Date) objs[7], "yyyyMMdd"));
				classified.setBookingDate(CommonUtils.dateFormatter((Date) objs[7], "yyyy-MM-dd HH:mm:ss"));
				classified.setChangedBy((Integer) objs[8]);
				classified.setChangedTs(objs[9] != null ? CommonUtils.dateFormatter((Date) objs[9], "ddMMyyyy") : "");
				classified.setAdId((String) objs[10]);
				classified.setClassifiedAdsSubType((Integer) objs[11]);
				classified.setCustomerId((String) objs[12]);
				classified.setUserTypeId((Integer) objs[13]);
				classified.setClassifiedTypeStr((String) objs[14]);
				classified.setAdsType((String) objs[15]);
				classified.setAdsSubType((String) objs[16]);
				classified.setCategoryStr((String) objs[17]);
				classified.setLangStr((String) objs[18]);
				classified.setPaidAmount(((Float) objs[19]).doubleValue());
				classified.setPublishedDate(CommonUtils.dateFormatter((Date) objs[20], "yyyy-MM-dd"));
				classified.setEmailId((String) objs[21]);
				classified.setMobileNumber((String) objs[22]);
				classified.setSalesOffice((String) objs[23]);
				classified.setSizeId((Integer) objs[24]);
				classified.setSize((String) objs[25]);
				classified.setCreatedTime(CommonUtils.dateFormatter((Date) objs[7], "HHmmss"));

				itemIds.add((String) objs[0]);
				classifiedsMap.put((String) objs[0], classified);
			}

			List<Object[]> editionsList = daEditionsRepo.getEditionIdAndNameOnItemId(itemIds);
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

			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("SSP_EMAIL_FROM"));
//			emailTo.setBcc(adminCcMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url", emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			emailTo.setTemplateName(GeneralConstants.DISPLAY_PUBLISH_AD);

			classifiedsMap.entrySet().forEach(erpData -> {
				emailTo.setTo(erpData.getValue().getEmailId());
				mapProperties.put("adId", erpData.getValue().getAdId());
				mapProperties.put("adType", erpData.getValue().getAdsType());
				mapProperties.put("adSubType", erpData.getValue().getAdsSubType());
				mapProperties.put("amount", erpData.getValue().getPaidAmount());
				mapProperties.put("langStr", erpData.getValue().getLangStr());
				mapProperties.put("category", erpData.getValue().getCategoryStr());
				mapProperties.put("bookingDate", erpData.getValue().getBookingDate());
				mapProperties.put("publishDate", erpData.getValue().getPublishedDate());
				mapProperties.put("size", erpData.getValue().getSize());
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

				sendService.sendCommunicationToSSPMail(emailTo, emailConfigs);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	@Override
	public GenericApiResponse downloadAdsPdfDocument(DashboardFilterTo payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		LinkedHashMap<Integer, List<Object>> dataMap = new LinkedHashMap<>();
		Integer count = 0;
		List<Object[]> adsdata = clfTemplateContentRepo.getOrderContent(payload.getItemId());
		if (!adsdata.isEmpty()) {
			for (Object[] obj : adsdata) {
				count = count +1;
				List<Object> data = new ArrayList<>();
				data.add((String) obj[1]);
				dataMap.put(count, data);
			}
			byte[] pdfData = classifiedDownloadService.downloadDisplayAdsContent(dataMap);
			apiResponse.setStatus(0);
			apiResponse.setMessage("Success");
			apiResponse.setData(pdfData);
		} else {
			apiResponse.setStatus(1);
			apiResponse.setMessage("No data available");
		}
		return apiResponse;
	}

	private Map<String, Object> convertToTemplateConfig(List<Object[]> adsdata) {
		 Map<String, Object> templateConfig = new HashMap<>(); 
		 try {
	            // Declare lists to store images and texts
	            List<Map<String, Object>> images = new ArrayList<>();
	            List<Map<String, Object>> texts = new ArrayList<>();
	            
	            // Process each Object[] from adsData
	            for (Object[] data : adsdata) {
	            	String itemId = (String) data[0];
	            	String orderId = (String) data[1];
	            	String adId = (String) data[2];
	            	String textId = (String) data[3];
	            	String templateId = (String) data[4];
//	            	String textIndex = (Integer) data[5];
	            	String text = "";
	            	if(data[6] != null) {
	            		 text = (String) data[6];
	            	}
//	                 // Assuming image path is at index 0
	                int x = (Integer) data[7];            // x position at index 1
	                int y = (Integer) data[8];            // y position at index 2
	                int width = (Integer) data[9];        // width at index 3
	                int height = (Integer) data[10];       // height at index 4
//	                String text = (String) data[5];       // text at index 5
//	                int textX = (Integer) data[6];        // text x position at index 6
//	                int textY = (Integer) data[7];        // text y position at index 7
	                int fontSize = (Integer) data[11];     // font size at index 8
//	                Color color = (Color) data[9];        // color at index 9
	                String type = (String) data[12];
	                String templateCntId = (String) data[13];
	                String imagePath = "";
	                String attachedId;
	                if(data[14] != null && data[15] != null) {
						 imagePath = (String) data[14];
						 attachedId = (String) data[15];
	                }

	                // Add image configuration
					if (imagePath != null && imagePath != "") {
						images.add(Map.of("path", imagePath, "x", x, "y", y, "width", width, "height", height));
					} else {

						// Add text configuration
						texts.add(Map.of("text", text, "x", x, "y", y, "fontSize", fontSize, "width", width, "height",
								height));
					}
	            }

	            // Initialize templateConfig with the required data
	            templateConfig = Map.of(
	                    "templatePath", "C:\\Users\\Admin\\Downloads\\Incresol_Templets\\Incresol_Templets\\Birthday\\BIRTHDAY- 8X5.jpg",
	                    "outputPath", "C:\\Users\\Admin\\Downloads\\Incresol_Templets\\Incresol_Templets\\Birthday\\BIRTHDAY11- 8X5.jpg",
	                    "images", images,
	                    "texts", texts
	            );
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return templateConfig;
	}

	@Override
	public GenericApiResponse getCustomerDetails(@NotNull String mobileNo) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		List<UmCustomers> umCustomers = umCustomersRepo.getCustomerDetails(mobileNo);
		if (!umCustomers.isEmpty()) {
			CustomerDetails customerDetails = new CustomerDetails();
			customerDetails.setAadharNumber(umCustomers.get(0).getAadharNumber() != null ? umCustomers.get(0).getAadharNumber() : null);
			customerDetails.setAddress1(umCustomers.get(0).getAddress1() != null ? umCustomers.get(0).getAddress1(): null);
			customerDetails.setAddress2(umCustomers.get(0).getAddress2() != null ? umCustomers.get(0).getAddress2():null);
			customerDetails.setAddress3(umCustomers.get(0).getAddress3() != null ? umCustomers.get(0).getAddress3(): null);
			customerDetails.setCity(umCustomers.get(0).getCity() != null ? umCustomers.get(0).getCity():null);
			customerDetails.setCustomerId(umCustomers.get(0).getCustomerId() != null ? umCustomers.get(0).getCustomerId():null);
			customerDetails.setCustomerName(umCustomers.get(0).getCustomerName() != null ? umCustomers.get(0).getCustomerName():null);
			customerDetails.setEmailId(umCustomers.get(0).getEmailId()!= null ?umCustomers.get(0).getEmailId():null);
			customerDetails.setHouseNo(umCustomers.get(0).getHouseNo() != null ? umCustomers.get(0).getHouseNo():null);
			customerDetails.setMobileNo(umCustomers.get(0).getMobileNo() != null ? umCustomers.get(0).getMobileNo(): null);
			customerDetails.setOfficeLocation(umCustomers.get(0).getOfficeLocation() != null ? umCustomers.get(0).getOfficeLocation(): null);
			customerDetails.setPanNumber(umCustomers.get(0).getPanNumber() != null ? umCustomers.get(0).getPanNumber() : null);
			customerDetails.setPinCode(umCustomers.get(0).getPinCode() != null ? umCustomers.get(0).getPinCode(): null);
			customerDetails.setSignature(umCustomers.get(0).getSignatureId() != null ? umCustomers.get(0).getSignatureId(): null);
			customerDetails.setState(umCustomers.get(0).getState() != null ? umCustomers.get(0).getState():null);
			customerDetails.setUserId(umCustomers.get(0).getUserId() != null ? umCustomers.get(0).getUserId(): null);
			if(umCustomers.get(0).getSignatureId() != null) {
				Attachments attachmentOnAttachmentId = attachmentsRepo.getAttachmentOnAttachmentId(umCustomers.get(0).getSignatureId());
				customerDetails.setSignatureName(attachmentOnAttachmentId.getAttachName());
			}			
			genericApiResponse.setData(customerDetails);
		}			
		else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Customer details not found");
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getVendorCommission(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		List<Object[]> classifiedList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		DisplayAdsModel displayAdsModel = new DisplayAdsModel();
		List<DisplayAdsModel> adsModels = new ArrayList<DisplayAdsModel>();
		double grandTotal = 0.0;
		double vendorCommission = 0.0;
		Integer vendorComPer = null;
		Map<String, Object> params = new HashMap<>();
		params.put("stype", SettingType.APP_SETTING.getValue());
		params.put("grps", Arrays.asList(GeneralConstants.VENDOR_COMMISSION, GeneralConstants.ENV_SET_GP));
		SettingTo settingTo = settingDao.getSMTPSettingValues(params);
		Map<String, String> emailConfigs = settingTo.getSettings();
		String isVendor = emailConfigs.get("VEND_COMM");
		

//		double overAllTotal = 0.0;
		AtomicDouble overAllTotal = new AtomicDouble(0.0);
//		double totalCommition = 0.0;
		AtomicDouble totalCommition = new AtomicDouble(0.0);
//		double pendingCommition = 0.0;
		AtomicDouble pendingCommition = new AtomicDouble(0.0);

		String query = "select itm.ad_id,itm.item_id,itm.order_id,coir.rate,coir.total_amount,coir.gst_total,um.vendor_commission,cpd.download_status,itm.created_ts,itm.is_settled,itm.settled_amount from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on itm.order_id = co.order_id inner join um_users um on itm.created_by = um.user_id inner join clf_publish_dates cpd on itm.item_id = cpd.item_id where itm.mark_as_delete = false and itm.status = 'APPROVED' and co.payment_status = 'APPROVED' and cpd.download_status = true and itm.created_by = "
				+ loggedUser.getUserId();
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

		query = query + " order by itm.created_ts desc";
		classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

		LinkedHashMap<String, DisplayAdsModel> classifiedsMap = new LinkedHashMap<String, DisplayAdsModel>();
		if("true".equalsIgnoreCase(isVendor)) {
			if (classifiedList != null && !classifiedList.isEmpty()) {
				for (Object[] obj : classifiedList) {
					String date = (CommonUtils.dateFormatter((Date) obj[8], "dd-MM-yyyy"));

					if (classifiedsMap.containsKey(date)) {
						DisplayAdsModel displayAdsModel2 = classifiedsMap.get(date);
						if (displayAdsModel2 != null) {
							double totalAmt = ((Float) obj[4]).doubleValue();
							double totCom = 0.0;
							double totPenCom = 0.0;
							displayAdsModel2
									.setGrandTotal(displayAdsModel2.getGrandTotal() + ((Float) obj[4]).doubleValue());
							displayAdsModel2.setTotalOrders(displayAdsModel2.getTotalOrders() + 1);
							if ("true".equalsIgnoreCase(obj[9] + "")) {
								displayAdsModel2.setSetteldOrders(displayAdsModel2.getSetteldOrders() + 1);
							} else {
								displayAdsModel2.setUnSetteldOrders(displayAdsModel2.getUnSetteldOrders() + 1);
							}

							if (obj[6] != null) {
								totCom = totalAmt * ((Integer) obj[6]).doubleValue() / 100;
								displayAdsModel2.setCommission(
										displayAdsModel2.getGrandTotal() * ((Integer) obj[6]).doubleValue() / 100);
							}

							if (obj[10] != null) {
								displayAdsModel2.setSetteldAmount(
										displayAdsModel2.getSetteldAmount() + ((Float) obj[10]).doubleValue());
							}
							
							totPenCom = totCom - (obj[10] != null ? ((Float) obj[10]).doubleValue() : 0);
							displayAdsModel2.setUnSetteldAmount(
									displayAdsModel2.getCommission() - displayAdsModel2.getSetteldAmount());
							
							overAllTotal.addAndGet(totalAmt);
							totalCommition.addAndGet(totCom);
							pendingCommition.addAndGet(totPenCom);

						}
					} else {
						DisplayAdsModel dis = new DisplayAdsModel();
						dis.setSetteldOrders(0);
						dis.setUnSetteldOrders(0);
						dis.setSetteldAmount(0.0);
						dis.setUnSetteldAmount(0.0);
						dis.setSetteldAmount(0.0);
						dis.setCreatedTs(date);
						dis.setGrandTotal(((Float) obj[4]).doubleValue());
						dis.setTotalOrders(1);
						if ("true".equalsIgnoreCase(obj[9] + "")) {
							dis.setSetteldOrders(1);
						} else {
							dis.setUnSetteldOrders(1);
						}
						if (obj[6] != null) {
							dis.setCommission(dis.getGrandTotal() * ((Integer) obj[6]).doubleValue() / 100);
						}
						if (obj[10] != null) {
							dis.setSetteldAmount(((Float) obj[10]).doubleValue());
						}
						dis.setUnSetteldAmount(dis.getCommission() - dis.getSetteldAmount());

						overAllTotal.addAndGet(dis.getGrandTotal());
						totalCommition.addAndGet(dis.getCommission());
						pendingCommition.addAndGet(dis.getUnSetteldAmount());

						classifiedsMap.put(date, dis);
					}
				}

				classifiedsMap.entrySet().forEach(erpData -> {
					erpData.getValue().setOverAllTotal(overAllTotal.get());
					erpData.getValue().setTotalCommission((double) Math.round(totalCommition.get()));
					erpData.getValue().setPendingCommition((double) Math.round(pendingCommition.get()));

				});

				genericApiResponse.setData(classifiedsMap.values());
				genericApiResponse.setStatus(0);
			} else {
				genericApiResponse.setMessage("No data found");
				genericApiResponse.setStatus(1);
			}
		}
		else {
			genericApiResponse.setMessage("No data found");
			genericApiResponse.setStatus(1);
		}

		return genericApiResponse;
	}

	@Override
	public GenericApiResponse updateDownloadStatus(LoggedUser loggedUser, @NotNull String itemId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		try {
			if(itemId != null) {
				daPublishDatesRepo.updateDownloadStatus(true, loggedUser.getUserId(), new Date(),
						itemId);
				apiResponse.setStatus(0);
			}else {
				apiResponse.setStatus(1);
				apiResponse.setMessage("Something went wrong. Please contact our administrator.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return apiResponse;
	}

	@Override
	public GenericApiResponse uploadVendorCommision(HttpServletRequest request) {
		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile uploadfile = multipartHttpServletRequest.getFile("file");
			List<String> expectedHeaders = new ArrayList<>();
			List<String> expectedFields = new ArrayList<>();
			expectedHeaders.add("Ad Id");
			expectedHeaders.add("Total Amount");
			expectedHeaders.add("Vendor Commission");
			
			expectedFields.add("ad_id");
			expectedFields.add("total_amount");
			expectedFields.add("settled_amount");
			if(uploadfile != null) {
				Workbook workbook = null;
				try {
					workbook = new XSSFWorkbook(uploadfile.getInputStream());
				} catch (Exception e) {
					workbook = new HSSFWorkbook(uploadfile.getInputStream());
				}
				Sheet sheet = workbook.getSheetAt(0);
				Row headerRow = sheet.getRow(0);
				if (headerRow != null) {
					for (int colIndex = 0; colIndex < headerRow.getPhysicalNumberOfCells(); colIndex++) {
						Cell headerCell = headerRow.getCell(colIndex);
						String actualHeader = headerCell.toString().trim();
						String expectedHeader = expectedHeaders.get(colIndex);

						if (!actualHeader.equals(expectedHeader)) {
							// header doesn't match the expected value
							apiResp.setStatus(1);
							apiResp.setMessage("Headers doesn't match, Please download the template");
							return apiResp;
						}
					}
				} else {
					apiResp.setStatus(1);
					apiResp.setMessage("Header row not found");
					return apiResp;
				}
				
				LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
				Iterator<Row> rowIterator = sheet.iterator();
				if (rowIterator.hasNext()) {
					rowIterator.next();
				}
				if(!rowIterator.hasNext()) {
					apiResp.setStatus(1);
					apiResp.setMessage("Empty sheet");
				}
				
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					for (int colIndex = 0; colIndex < row.getPhysicalNumberOfCells(); colIndex++) {
						Cell cell = row.getCell(colIndex);
						String sCell = new String();
						if(cell != null) {
							if (cell.getCellType() == CellType.NUMERIC && cell.getNumericCellValue() == (int) cell.getNumericCellValue()) {
								 sCell = String.valueOf((int) cell.getNumericCellValue());
							}else {
								sCell = cell.toString().trim();
							}
						}
						String key = expectedFields.get(colIndex);
						data.put(key, sCell);
					}
					this.uploadCommission(data);
					apiResp.setStatus(0);
					apiResp.setMessage(GeneralConstants.SUCCESS);
				}
				
				workbook.close();
				return apiResp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResp;
	}

	private void uploadCommission(LinkedHashMap<String, Object> data) {
		try {
			String adId = data.get("ad_id") + "";
			ClfOrderItems clfOrderItems = clfOrderItemsRepo.getItemDetailsOnadId(adId);
			if(clfOrderItems != null) {
				clfOrderItems.setSettledAmount(data.get("settled_amount") != null ? Double.parseDouble(data.get("settled_amount").toString()) : 0.0);
				clfOrderItems.setIsSettled(true);
				
				clfOrderItemsRepo.save(clfOrderItems);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public GenericApiResponse downloadTemplateHeaders(HttpServletResponse response) {
		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Vendor");
//			Map<String, Object> headerData = payload.getHeadersData();
			List<String> headerFields = new ArrayList<String>();
			headerFields.add("Ad Id");
			headerFields.add("Total Amount");
			headerFields.add("Vendor Commission");
//			Map<String, String> headerFileds = (Map<String, String>) headerData.get(payload.getMasterDataId());
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setLocked(true);
			// Create header cells
			Row headerRow = sheet.createRow(0);
			int colIndex = 0;
			for (String key : headerFields) {
				Cell headerCell = headerRow.createCell(colIndex);
				headerCell.setCellValue(key);
				headerCell.setCellStyle(headerCellStyle);
				colIndex++;
			}
			try (FileOutputStream fileOut = new FileOutputStream("E:\\headers.xlsx")) {
				workbook.write(fileOut);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			workbook.write(byteArrayOutputStream);
			apiResp.setData(byteArrayOutputStream.toByteArray());
			apiResp.setStatus(0);
			;
		} catch (Exception e) {
			e.printStackTrace();
			apiResp.setStatus(1);
		}
		return apiResp;
	}

	@Override
	public void sendMailToCustomerForSSPVendor(Map<String, ErpClassifieds> erpClassifiedsMap,
			 BillDeskPaymentResponseModel payload, LoggedUser loggedUser,
			ClfPaymentResponseTracking clfPaymentResponseTracking) {
		// TODO Auto-generated method stub
		try {
			
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();
			
			Map<String, Object> mapProperties = new HashMap<String, Object>();
			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("SSP_EMAIL_FROM"));
//			emailTo.setBcc(adminCcMails);
			emailTo.setOrgId("1000");
			mapProperties.put("action_url",emailConfigs.get("WEB_URL"));
			mapProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
			mapProperties.put("userName", loggedUser.getLogonId());//created by userName
			mapProperties.put("userId", loggedUser.getLogonId());//new userName
			if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
				mapProperties.put("agencyUsername", loggedUser.getFirstName());
				mapProperties.put("agencyMobileNo", loggedUser.getMobile());
				mapProperties.put("agencyEmail", loggedUser.getEmail());
				mapProperties.put("isAgencyUser", "inline-block");
				mapProperties.put("isCustomerUser", "none");
				mapProperties.put("isAgencyUserCommition", true);
			}else {
				mapProperties.put("isAgencyUser", "none");
				mapProperties.put("isCustomerUser", "inline-block");
			}
			erpClassifiedsMap.entrySet().forEach(erpData -> {
				emailTo.setTo(erpData.getValue().getEmailId());
				mapProperties.put("adId", erpData.getValue().getAdId());
				mapProperties.put("clientName", erpData.getValue().getCustomerName());
				mapProperties.put("street", erpData.getValue().getAddress1());
				mapProperties.put("pinCode", erpData.getValue().getPinCode());
				mapProperties.put("city", erpData.getValue().getBookingUnitStr());
				mapProperties.put("phone", erpData.getValue().getMobileNumber());
				mapProperties.put("state", erpData.getValue().getStateDesc());
				mapProperties.put("gstNo", erpData.getValue().getGstNo() != null ? erpData.getValue().getGstNo() : "");
				mapProperties.put("categoryName", erpData.getValue().getCategoryStr());
				mapProperties.put("noOfInsertion", erpData.getValue().getNoOfInsertions() != null ? erpData.getValue().getNoOfInsertions():"");
				mapProperties.put("date", erpData.getValue().getCreatedDate());
				mapProperties.put("employeeHrCode", erpData.getValue().getExecutiveEmpCode());
				mapProperties.put("employee", erpData.getValue().getExecutiveName());
//				mapProperties.put("sspName", userContext.getLoggedUser().getFirstName()+" "+ userContext.getLoggedUser().getLastName());
//				mapProperties.put("sspVendorCode", loggedUser.getLogonId());
//				mapProperties.put("sspPhnNo", userContext.getLoggedUser().getMobile() != null ? userContext.getLoggedUser().getMobile():"");
				mapProperties.put("Code", "998363");
				mapProperties.put("address", erpData.getValue().getOfficeAddress());
				mapProperties.put("mobileNo", erpData.getValue().getBookingPhnNo());
				mapProperties.put("gstIn", erpData.getValue().getBookingGstIn());
//				mapProperties.put("sspAddress", erpData.getValue().getAddress1());
				if(payload != null) {
					mapProperties.put("sspName", erpData.getValue().getSspUserName());
					mapProperties.put("sspVendorCode", erpData.getValue().getLogonId());
					mapProperties.put("sspPhnNo", erpData.getValue().getMobileNo());
					mapProperties.put("sspAddress", erpData.getValue().getSspAddress());
				}else {
					mapProperties.put("sspAddress", erpData.getValue().getSspAddress());
					mapProperties.put("sspName", userContext.getLoggedUser().getFirstName()+" "+ userContext.getLoggedUser().getLastName());
					mapProperties.put("sspVendorCode", loggedUser.getLogonId());
					mapProperties.put("sspPhnNo", userContext.getLoggedUser().getMobile() != null ? userContext.getLoggedUser().getMobile():"");
				}
				List<String> editionsList = erpData.getValue().getEditions();
				String editions = editionsList.stream().map(Object::toString).collect(Collectors.joining(","));
//				mapProperties.put("editionName", editions);
				List<String> pubDates = erpData.getValue().getPublishDates();
				List<String> pubDatesList = new ArrayList<String>();
				StringBuilder dynamicTableRows = new StringBuilder();
				String scheme = erpData.getValue().getScheme1();
				Double ratePerDay = erpData.getValue().getRate();

				int chargeableDays = 1; // default
				int freeDays = 0;

				if (scheme != null && scheme.contains("+")) {
				    try {
				        String[] schemeParts = scheme.split("\\+");
				        chargeableDays = Integer.parseInt(schemeParts[0].trim());
				        freeDays = Integer.parseInt(schemeParts[1].replaceAll("[^0-9]", "").trim());
				    } catch (Exception e) {
				        System.out.println("Invalid scheme format: " + scheme);
				    }
				}

				int totalCycle = chargeableDays + freeDays;
				int index = 0;
				ratePerDay = ratePerDay / chargeableDays;

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

				    // Determine if current date is billable or free
				    int modIndex = index % totalCycle;
				    Double effectiveRate = (modIndex < chargeableDays) ? ratePerDay : 0.0;

				    String formattedRate = formatToIndianCurrency(effectiveRate);

				    dynamicTableRows.append("<tr>")
				        .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(formattedDate).append("</td>")
				        .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(editions).append("</td>")
				        .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getAdsType()).append("</td>")
				        .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getAdsSubType()).append("</td>")
				        .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getScheme1()).append("</td>")
				        .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(erpData.getValue().getLineCountSsp()).append("</td>")
				        .append("<td style=\"border: 1px solid black; padding: 8px;\">").append(formattedRate).append("</td>")
				        .append("</tr>");

				    index++;
				}

				mapProperties.put("dynamicTableRows", dynamicTableRows.toString());
				mapProperties.put("gstAmount",formatToIndianCurrency(erpData.getValue().getGstTotalAmount()));
				
				mapProperties.put("amount",erpData.getValue().getPaidAmount());
				
				
				mapProperties.put("adType", erpData.getValue().getAdsType());
				mapProperties.put("adSubType", erpData.getValue().getAdsSubType());
//				mapProperties.put("pack", erpData.getValue().getSchemeStr());
//				mapProperties.put("lineCount", erpData.getValue().getLineCount());
				mapProperties.put("amount", erpData.getValue().getPaidAmount());
				mapProperties.put("langStr", erpData.getValue().getLangStr());
				mapProperties.put("category", erpData.getValue().getCategoryStr());
//				mapProperties.put("subCategory", erpData.getValue().getSubCategoryStr());
//				mapProperties.put("printAdMatter", erpData.getValue().getContent());
				mapProperties.put("size", erpData.getValue().getSize());
				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
					mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
					mapProperties.put("status", erpData.getValue().getContentStatus());
				} else {
					mapProperties.put("approvalStatus", "PENDING");
					mapProperties.put("status", "PENDING");
				}
//				mapProperties.put("status", "PENDING");
				mapProperties.put("clientName", erpData.getValue().getCustomerName());
				mapProperties.put("customerMobile", erpData.getValue().getMobileNumber());
				mapProperties.put("subCategory", erpData.getValue().getSubCategoryStr());
				mapProperties.put("printAdMatter", erpData.getValue().getMatter());
				mapProperties.put("date", erpData.getValue().getBookingDate());
				mapProperties.put("gstAmount", erpData.getValue().getGstTotalAmount());
				mapProperties.put("rate", erpData.getValue().getRate());
				mapProperties.put("categoryDiscountAmount", erpData.getValue().getCategoryDisAmount());
				mapProperties.put("specialDiscountAmount", erpData.getValue().getSpecialDiscountAmount());
				mapProperties.put("categoryDiscountPercentage", erpData.getValue().getCategoryDiscountPercentage());
				mapProperties.put("specialDiscountPercentage", erpData.getValue().getSpecialDiscountPercentage());
				mapProperties.put("subject_edit", true);
				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
					mapProperties.put("approvalStatus", erpData.getValue().getContentStatus());
				} else {
					mapProperties.put("approvalStatus", "PENDING");
				}
				
				if(loggedUser.getEmail() == null) {
					loggedUser.setEmail(erpData.getValue().getCreatedByEmail());
				}
				
				
				
				if(erpData.getValue().getLogonId() != null) {
					if(erpData.getValue().getLogonId().contains("SSP")) {
						UmUsers usersLoginId = umUsersRepository.getUsersLoginId(erpData.getValue().getLogonId(), false);
						if(usersLoginId != null) {
							UmUsers approverEmails = umUsersRepository.getApproverEmails(usersLoginId.getCreatedBy());
							if(approverEmails != null) {
								String[] ccMails = { erpData.getValue().getCreatedByEmail(), erpData.getValue().getBookingUnitEmail(),approverEmails.getEmail() };
								emailTo.setBcc(ccMails);
							}else {
								String[] ccMails = { erpData.getValue().getCreatedByEmail(), erpData.getValue().getBookingUnitEmail() };
								emailTo.setBcc(ccMails);
							}
						}else {
							String[] ccMails = { erpData.getValue().getCreatedByEmail(), erpData.getValue().getBookingUnitEmail() };
							emailTo.setBcc(ccMails);
						}
					}else {
						String[] ccMails = { erpData.getValue().getCreatedByEmail(), erpData.getValue().getBookingUnitEmail() };
						emailTo.setBcc(ccMails);
					}
				}else {
					String[] ccMails = { loggedUser.getEmail(), erpData.getValue().getBookingUnitEmail() };
					emailTo.setBcc(ccMails);
				}
				
//				String [] ccMails = {loggedUser.getEmail() ,erpData.getValue().getSchedulingMail()};
//				emailTo.setBcc(ccMails);
				
				mapProperties.put("rate", erpData.getValue().getRate());
				
				if("AGENCY_USER".equalsIgnoreCase(loggedUser.getRoleName())) {
					Double totValue = erpData.getValue().getRate();
					totValue = totValue - erpData.getValue().getAgencyCommition();
					totValue = totValue + erpData.getValue().getGstTotalAmount();
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				} else {
					Double totValue = erpData.getValue().getRate() + erpData.getValue().getGstTotalAmount();
					Double roundingOff = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff * 100.0) / 100.0;
					mapProperties.put("roundingOff", roundedDifference);
				}
				
				if("11".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isTelangana", "inline-block");
				}else {
					mapProperties.put("isTelangana", "none");
				}
				if("25".equalsIgnoreCase(erpData.getValue().getSalesOffice())) {
					mapProperties.put("isAndhraPradesh", "inline-block");
				}else {
					mapProperties.put("isAndhraPradesh", "none");
				}
				mapProperties.put("editions", editions);
				
				
				 Map<String, Object> mapData1 = new HashMap<>();
				 List<Attachments> attachmentsByOrderId = attachmentsRepo.getAllAttachmentsOnUserId(loggedUser.getUserId());
				
				 for(Attachments attachments : attachmentsByOrderId) {
					 if(attachments.getAttachName().startsWith("sig_")) {
						 String cid = UUID.randomUUID().toString(); // Unique Content-ID
						 mapProperties.put("vendorSign", properties.getProperty("TOMCAT_SERVER1")+TOMCAT_PATH+attachments.getAttachUrl());
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
				emailTo.setTemplateName(GeneralConstants.SSP_CLF_PAYMENT);
				emailTo.setTemplateProps(mapProperties);
				
				
				
//				try {
//				    PrintUtility printUtility = new PrintUtility(); // Or autowire if it's a Spring Bean
//				    String html = printUtility.generateCsqHtml(mapProperties);
//				    String rptDataEncoded = Base64.getEncoder().encodeToString(html.getBytes(StandardCharsets.UTF_8));
//				    mapProperties.put("rptData", rptDataEncoded);
//				    // You may need to convert `mapProperties` into JSONObject
//				    JSONObject payloadJson = new JSONObject(mapProperties);
//				    
//
//				    // Generate PDF and get file path
//				    String pdfPath = printUtility.generateCsqPdf(payloadJson);
//
//				    // Create attachment entry
//				    Map<String, Object> pdfAttachment = new HashMap<>();
//				    pdfAttachment.put("CustomerCSQ.pdf", new FileDataSource(pdfPath));
//
//				    List<Map<String, Object>> multiAttachments = new ArrayList<>();
//				    multiAttachments.add(pdfAttachment);
//
//				    emailTo.setDataSource(multiAttachments);
//				    
//				} catch (Exception e) {
//				    e.printStackTrace(); // or proper logging
//				}
				List<Map<String, Object>> multiAttachments = new ArrayList<Map<String, Object>>();
				Map<String, Object> mapData = new HashMap<>();
				List<Attachments> allAttachmentsOnUserId = attachmentsRepo.getAllAttachmentsOnUserId(loggedUser.getUserId());
				if(allAttachmentsOnUserId!=null && !allAttachmentsOnUserId.isEmpty()) {
					for(Attachments attach:allAttachmentsOnUserId) {
						if(attach.getAttachName().startsWith("sig")) {
//							mapData.put(attach.getAttachName()+".png", new FileDataSource(getDIR_PATH_DOCS()+attach.getAttachUrl()));
						}	                    
					}
				}
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
				} catch (DocumentException | java.io.IOException e) {
					e.printStackTrace();
				}
				 String generatedpdfFilePath =  getDIR_PATH_PDF_DOCS()+erpData.getValue().getAdId()+".pdf";
		         mapData.put(erpData.getValue().getAdId()+".pdf", new FileDataSource(generatedpdfFilePath));
		
				multiAttachments.add(mapData);
				
//				emailTo.setDataSource(multiAttachments);
//				List<Map<String, Object>> multiAttachments = new ArrayList<Map<String, Object>>();
				 Map<String, Object> mapData3 = new HashMap<>();
				String pdfDirPath = getDIR_PATH_DOCS();
//				String pdfFileName = "Engagement of Business Represntative-Vetted-10.03.25_9dc7503c-fa67-4455-8461-c3c6526bcd45.pdf";
				String pdfFileName = properties.getProperty("TERMS_CONDITIONS");
				String pdfFilePath = pdfDirPath + pdfFileName;
				File pdfFile = new File(pdfFilePath);
				if (!pdfFile.exists()) {
				    System.err.println("PDF creation failed: " + pdfFilePath);
				    throw new RuntimeException("PDF generation failed.");
				}

				mapData3.put(pdfFileName, new FileDataSource(pdfFilePath));
		
				multiAttachments.add(mapData3);
				
				emailTo.setDataSource(multiAttachments);
				emailTo.setTemplateProps(mapProperties);
				sendService.sendCommunicationToSSPMail(emailTo, emailConfigs);
			});	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Map<String, ErpClassifieds> getClassifiedOrderDetailsForSSPErp(List<String> orderIds) {
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
			String query = "select itm.item_id , itm.order_id , itm.classified_type,itm .classified_ads_type,itm.category,itm.lang,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id, co.user_type_id,gct.type,gct.erp_ref_id as gct_erp_ref_id,gcat.ads_type,gcat.erp_ref_id as gcat_erp_ref_id,gcast.ads_sub_type,gcast.erp_ref_id as gcast_erp_ref_id,gcl.language, coir.total_amount,co.booking_unit,bu.sales_office,bu.booking_location,bu.sold_to_party,uc.customer_name,itm.status,bu.booking_unit_email,coir.gst_total,coir.rate,cprt.order_id as ro_order_id,uc.state ,gs.state as state_desc,uc.address_1,uc.pin_code,uc.mobile_no,gcc.classified_category,gcs.classified_subcategory,itm.clf_content,gcs2.scheme,coir.line_count,uu.first_name,uu.address,uu.logon_id,uu.mobile,uu.emp_code,grbu.office_address ,grbu.gst_in ,grbu.phone_no ,grbu.scheduling_mail  from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id  inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on itm.order_id = co.order_id inner join booking_units bu on co.booking_unit = bu.booking_code inner join clf_payment_response_tracking cprt on itm.order_id = cprt.sec_order_id  inner join um_customers uc on uc.customer_id  = co.customer_id	left join gd_state gs on gs.state_code = uc.state inner join gd_classified_category gcc on gcc.id = itm.category inner join gd_classified_subcategory gcs on gcs.id = itm.subcategory inner join gd_classified_schemes gcs2 on gcs2.id = itm.scheme inner join um_users uu on uu.user_id = itm.created_by left join gd_rms_booking_units grbu on grbu.booking_unit = co.booking_unit where itm.order_id in ('" + joinedOrderIds + "') and itm.mark_as_delete = false and cprt.mark_as_delete = false;";
//			String query = "select itm.item_id , itm.order_id , itm.classified_type,itm .classified_ads_type,itm.scheme as itm_scheme,itm.category,itm.subcategory,itm.lang,itm.clf_content,itm.created_by,itm.created_ts,itm.changed_by,itm.changed_ts,itm.ad_id,itm.classified_ads_sub_type,co.customer_id , co.user_type_id,gct.type,gct.erp_ref_id as gct_erp_ref_id, gcat.ads_type,gcat.erp_ref_id as gcat_erp_ref_id,gcast.ads_sub_type,gcast.erp_ref_id as gcast_erp_ref_id,gcs.scheme as gcs_scheme,gcs.erp_ref_id as gcs_erp_ref_id,gcc.classified_category ,gcs2.classified_subcategory,gcl.language, coir.total_amount,coir.line_count,gcc.erp_ref_id as gcc_erp_ref_id,gcs2.erp_ref_id as gcs2_erp_ref_id,co.booking_unit,gcc.product_hierarchy,bu.sales_office,bu.booking_location,bu.sold_to_party,co.customer_name,itm.status,bu.booking_unit_email,coir.gst_total,coir.rate,coir.extra_line_rate,coir.agency_commition,cprt.order_id as ro_order_id from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on itm.order_id = co.order_id inner join booking_units bu on co.booking_unit = bu.booking_code inner join clf_payment_response_tracking cprt on itm.order_id = cprt.sec_order_id where itm.order_id in ('"
//					+ joinedOrderIds + "')and itm.mark_as_delete = false";
			classifiedList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);

			for (Object[] objs : classifiedList) {
				ErpClassifieds classified = new ErpClassifieds();
				classified.setItemId((String) objs[0]);
				classified.setOrderId((String) objs[1]);
				classified.setClassifiedType((Integer) objs[2]);
				classified.setClassifiedAdsType((Integer) objs[3]);
				classified.setCategory((Integer) objs[4]);
				classified.setLang((Integer) objs[5]);
				classified.setCreatedBy((Integer) objs[6]);
				classified.setCreatedTs(CommonUtils.dateFormatter((Date) objs[7], "yyyyMMddHHmmss"));
				classified.setCreatedDate(CommonUtils.dateFormatter((Date) objs[7], "dd-MM-yyyy"));
				classified.setBookingDate(CommonUtils.dateFormatter((Date) objs[7], "yyyy-MM-dd HH:mm:ss"));
				classified.setChangedBy((Integer) objs[8]);
				classified.setChangedTs(objs[9] != null ? CommonUtils.dateFormatter((Date) objs[9], "ddMMyyyy") : "");
				classified.setAdId((String) objs[10]);
				classified.setClassifiedAdsSubType((Integer) objs[11]);
				classified.setCustomerId((String) objs[12]);
				classified.setUserTypeId((Integer) objs[13]);
				classified.setClassifiedTypeStr((String) objs[14]);
				classified.setClassifiedTypeErpRefId((String) objs[15]);
				classified.setAdsType((String) objs[16]);
				classified.setAdsTypeErpRefId((String) objs[17]);
				classified.setAdsSubType((String) objs[18]);
				classified.setAdsSubTypeErpRefId((String) objs[19]);
				classified.setLangStr((String) objs[20]);
				Double val = (Double.valueOf(df.format(objs[21])));
				classified.setPaidAmount(val);
				classified.setBookingUnit((Integer) objs[22]);
				classified.setSalesOffice((String) objs[23]);
				classified.setBookingUnitStr((String) objs[24]);
				classified.setSoldToParty((String) objs[25]);
				classified.setCustomerName2((String) objs[26]);
				classified.setContentStatus((String) objs[27]);
				classified.setBookingUnitEmail((String) objs[28]);

				Double gstVal = (Double.valueOf(df.format(objs[29])));
				classified.setGstTotalAmount(Double.valueOf(df1.format(gstVal)));
				Double rate = (Double.valueOf(df.format(objs[30])));
				classified.setRate(Double.valueOf(df1.format(rate)));
				classified.setRoOrderId((String) objs[31]);
				classified.setStateCode((String) objs[32]);
				classified.setStateDesc((String) objs[33]);
				classified.setAddress1((String) objs[34]);
				classified.setPinCode((String) objs[35]);
				classified.setMobileNo((String) objs[36]);
				classified.setCategoryStr((String) objs[37]);
				classified.setSubCategoryStr((String) objs[38]);
				classified.setMatter((String) objs[39]);
				classified.setScheme1((String) objs[40]);
				classified.setLineCountSsp((Short) objs[41]);
				classified.setSspUserName((String) objs[42]);
				classified.setSspAddress((String) objs[43]);
				classified.setSspLogonId((String) objs[44]);
				classified.setSspMobileNo((String) objs[45]);
				classified.setEmpCode((String) objs[46]);;
				classified.setOfficeAddress((String) objs[47]);
				classified.setBookingGstIn((String) objs[48]);
				classified.setBookingPhnNo((String) objs[49]);
				classified.setSchedulingMail((String) objs[50]);
				;
				classified.setKeyword("Display Order");
				classified.setTypeOfCustomer("01");
				classified.setCreatedTime(CommonUtils.dateFormatter((Date) objs[7], "HHmmss"));
				classified.setOrderIdentification("03");

				itemIds.add((String) objs[0]);
				customerIds.add((String) objs[12]);
				createdByIds.add((Integer) objs[6]);
				classifiedsMap.put((String) objs[0], classified);
			}

			if (itemIds != null && !itemIds.isEmpty()) {
				List<Object[]> editionsList = daEditionsRepo.getClassifiedEditionIdAndNameOnItemId(itemIds);
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

				List<Object[]> publishDatesList = daPublishDatesRepo.getDisplayPublishDatesForErpData(itemIds);
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
								erpData.getValue().setSspUserName(umUser.getFirstName());
								erpData.getValue().setMobileNo(umUser.getMobile());
								UmUsers approverEmails = umUsersRepository.getApproverEmails(umUser.getCreatedBy());
								erpData.getValue().setExecutiveEmpCode(approverEmails.getEmpCode());
								erpData.getValue().setExecutiveName(approverEmails.getFirstName());
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
								if("4".equalsIgnoreCase(erpData.getValue().getUserTypeId() + "")) {
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
	public GenericApiResponse getSSPSummaryReports(LoggedUser loggedUser, DashboardFilterTo payload) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(1);
		List<Object[]> sspSummaryList = new ArrayList<Object[]>();
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat df1 = new DecimalFormat("#.##");
		df.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		df1.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
		String query = "select coi.created_by,uu.first_name,uu.logon_id,uu.emp_code ,TO_CHAR(CAST(coi.created_ts AS DATE), 'DD-MM-YYYY') AS booking_date,COUNT(*) AS total_ads_booked,SUM(coi2.total_amount) AS total_amount,bu.booking_location FROM clf_order_items coi INNER JOIN um_users uu ON uu.user_id = coi.created_by INNER JOIN clf_order_item_rates coi2 ON coi2.item_id = coi.item_id inner join clf_orders co on co.order_id  = coi.order_id left join booking_units bu on bu.booking_code = co.booking_unit where coi.status = 'APPROVED' and coi.mark_as_delete  = false and co.order_type = 3 ";
		
		if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()
				&& payload.getRequestedToDate() != null && !payload.getRequestedToDate().isEmpty()) {
			String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			String toDate = this.convertDateFormat(payload.getRequestedToDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query += " AND to_char(coi.created_ts,'YYYY-MM-DD') >= '" + formattedDate
					+ "' AND to_char(coi.created_ts,'YYYY-MM-DD') <= '" + toDate + "'";
		}
		if (payload.getRequestedDate() != null && !payload.getRequestedDate().isEmpty()
				&& payload.getRequestedToDate() == null) {
			String formattedDate = this.convertDateFormat(payload.getRequestedDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query = query + " and to_char(coi.created_ts,'YYYY-MM-DD') = '" + formattedDate
					+ "' AND to_char(coi.created_ts,'YYYY-MM-DD') <= '" + formattedDate + "'";
		}
		if (payload.getRequestedToDate() != null && !payload.getRequestedToDate().isEmpty()
				&& payload.getRequestedDate() == null) {
			String toDate = this.convertDateFormat(payload.getRequestedToDate(), "dd/MM/yyyy", "yyyy-MM-dd");
			query = query + " and to_char(coi.created_ts,'YYYY-MM-DD') = '" + toDate
					+ "' AND to_char(coi.created_ts,'YYYY-MM-DD') <= '" + toDate + "'";
		}
		
		query = query + " GROUP BY coi.created_by,uu.first_name,CAST(coi.created_ts AS DATE),uu.logon_id ,uu.emp_code,bu.booking_location ORDER BY   CAST(coi.created_ts AS DATE) DESC";
		sspSummaryList = (List<Object[]>) baseDao.findBySQLQueryWithoutParams(query);
//		LinkedHashMap<String, Classifieds> classifiedsMap = new LinkedHashMap<>();
		List<SSPSummaryModel> sspSummary = new ArrayList<SSPSummaryModel>();
		if(sspSummaryList != null && !sspSummaryList.isEmpty()) {
			for(Object[] obj : sspSummaryList) {
				SSPSummaryModel sspSummaryModel = new SSPSummaryModel();
				sspSummaryModel.setCreatedBy((Integer) obj[0]);
				sspSummaryModel.setCreatedByName((String) obj[1]);
				sspSummaryModel.setLogOnId((String) obj[2]);
				sspSummaryModel.setEmpCode((String) obj[3]);
				sspSummaryModel.setCreatedTs((String) obj[4]);
				sspSummaryModel.setCount(((BigInteger) obj[5]).intValue());
				Double val = (Double.valueOf(df.format(obj[6])));
				sspSummaryModel.setAmount(new BigDecimal(df1.format(val)));
				sspSummaryModel.setBookingLocation((String) obj[7]);
//				classifiedsMap.put((String) obj[7], classified);
				sspSummary.add(sspSummaryModel);
				}
			genericApiResponse.setStatus(0);
			genericApiResponse.setData(sspSummary);
		}else {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("No Data Available");
		}		
		return genericApiResponse;
	}
	
	
	private static PdfPCell noBorderCell(Paragraph p) {
	    PdfPCell cell = new PdfPCell(p);
	    cell.setBorder(0);
	    return cell;
	}
	
	public void genrateSSPPDF(Map<String, ErpClassifieds> erpClassifiedsMap,List<String> fileNames, List<String> fileNames3)
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


				PdfPTable table = new PdfPTable(3);
				table.setWidthPercentage(100);
				
				PdfPTable leftTable = new PdfPTable(1);
				leftTable.setWidthPercentage(100);
				
				
//				PdfPCell cell1 = new PdfPCell(new com.itextpdf.text.Paragraph("JAGATI PUBLICATIONS LIMITED"));
				Paragraph company = new Paragraph("JAGATI PUBLICATIONS LIMITED", fontHeader);
				Paragraph address = new Paragraph(erpData.getValue().getOfficeAddress(), fontColumn);
				Paragraph gstn = new Paragraph("GSTIN : "+ erpData.getValue().getBookingGstIn(), fontColumn);
				Paragraph phone = new Paragraph("Ph : "+ erpData.getValue().getBookingPhnNo(),fontColumn);
				
				leftTable.addCell(noBorderCell(company));
				leftTable.addCell(noBorderCell(address));
				leftTable.addCell(noBorderCell(gstn));
				leftTable.addCell(noBorderCell(phone));
				
				PdfPCell cell1 = new PdfPCell(leftTable);
				cell1.setBorder(0);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				
//				cell1.setBorder(0);
//				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				Image	image1;
				image1 = Image
							.getInstance(new URL("https://pre-prod-asp.s3.ap-south-1.amazonaws.com/static_assets/u3.png"));
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
//				PdfPCell child2 = new PdfPCell(new com.itextpdf.text.Paragraph("HSN/SAC Code : SCA241234"));
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
//				Paragraph gstn = new Paragraph("GSTIN : "+ erpData.getValue().getBookingGstIn(), fontColumn);
//				PdfPCell child3 = new PdfPCell(gstn);
//				PdfPCell child3 = new PdfPCell(new com.itextpdf.text.Paragraph("GSTIN : 123456"));
//				child3.setBorder(0);
//				child3.setHorizontalAlignment(Element.ALIGN_LEFT);

//				Paragraph mobile = new Paragraph("Ph : "+ erpData.getValue().getBookingPhnNo(),fontColumn);
//				PdfPCell child4 = new PdfPCell(phone);
//				PdfPCell child4 = new PdfPCell(new com.itextpdf.text.Paragraph("Ph : 9809098909"));
//				child4.setBorder(0);
//				child4.setHorizontalAlignment(Element.ALIGN_LEFT);
//				childG.addCell(child3);
//				childG.addCell(child4);
//				document.add(childG);
				document.add(new Paragraph(" "));
//				document.add(new Chunk(line));
				PdfPTable table1 = new PdfPTable(4);
				table1.addCell(createCell("Client Name", BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(
						createCell(erpData.getValue().getCustomerName(), BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(createCell("SSP Order", BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(
						createCell(erpData.getValue().getAdId(), BaseColor.WHITE, fontColumn, false, false));
				table1.setWidthPercentage(100);
				document.add(table1);
				PdfPTable table2 = new PdfPTable(4);
				table2.addCell(createCell("House number", BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(createCell(erpData.getValue().getAddress1(), BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(createCell("Date", BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(
						createCell(erpData.getValue().getCreatedDate(), BaseColor.WHITE, fontColumn, false, false));
				table2.setWidthPercentage(100);
				document.add(table2);

				PdfPTable table3 = new PdfPTable(4);
				table3.addCell(createCell("Pin code", BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(
						createCell(erpData.getValue().getPinCode(), BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(createCell("Employee code", BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(createCell(erpData.getValue().getExecutiveEmpCode(), BaseColor.WHITE, fontColumn, false, false));
				table3.setWidthPercentage(100);
				document.add(table3);

				PdfPTable table4 = new PdfPTable(4);
				table4.addCell(createCell("City", BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(
						createCell(erpData.getValue().getBookingUnitStr(), BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(createCell("Employee", BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(
						createCell(erpData.getValue().getExecutiveName(), BaseColor.WHITE, fontColumn, false, false));
				table4.setWidthPercentage(100);
				document.add(table4);

				PdfPTable table5 = new PdfPTable(4);
				table5.addCell(createCell("Client No", BaseColor.WHITE, fontColumn, false, false));
				table5.addCell(
						createCell(erpData.getValue().getMobileNumber(), BaseColor.WHITE, fontColumn, false, false));
				table5.addCell(createCell("Ad Booked By:", BaseColor.WHITE, fontColumn, false, false));
//				table5.addCell(createCell(erpData.getValue().getGstNo(), BaseColor.WHITE, fontSubHeader, false, false));
				table5.setWidthPercentage(100);
				document.add(table5);

				PdfPTable table6 = new PdfPTable(4);
				table6.addCell(createCell("State", BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(
						createCell(erpData.getValue().getStateDesc(), BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(createCell("SSP Name ", BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(
						createCell(erpData.getValue().getSspUserName(), BaseColor.WHITE, fontColumn, false, false));
				table6.setWidthPercentage(100);
				document.add(table6);

				PdfPTable table7 = new PdfPTable(4);
				table7.addCell(createCell("Client GSTIN", BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(
						createCell(erpData.getValue().getGstNo() != null ? erpData.getValue().getGstNo() : "", BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(createCell("SSP Vendor Code", BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(createCell(erpData.getValue().getLogonId(), BaseColor.WHITE,
						fontColumn, false, false));
				table7.setWidthPercentage(100);
				document.add(table7);

				PdfPTable table8 = new PdfPTable(4);
				table8.addCell(createCell("Category Name", BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(
						createCell(erpData.getValue().getCategoryStr(), BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(createCell("SSP Address:", BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(
						createCell(erpData.getValue().getAddress3(), BaseColor.WHITE, fontColumn, false, false));
				table8.setWidthPercentage(100);
				document.add(table8);
				String status = "";
				if(erpData.getValue().getContentStatus() != null) {
					status = erpData.getValue().getContentStatus();
				}else {
					status = "PENDING";
				}
				 
				PdfPTable table9 = new PdfPTable(4);
				table9.addCell(createCell("Status", BaseColor.WHITE, fontColumn, false, false));
				table9.addCell(
						createCell(status, BaseColor.WHITE, fontColumn, false, false));
				table9.addCell(createCell("SSP Phn No", BaseColor.WHITE, fontColumn, false, false));
				table9.addCell(
						createCell(erpData.getValue().getMobileNo(), BaseColor.WHITE, fontColumn, false, false));
				table9.setWidthPercentage(100);
				document.add(table9);

				document.add(new Paragraph(" "));

				PdfPTable tableF = new PdfPTable(9);
				BaseColor silverColor = new BaseColor(192, 192, 192);
				tableF.addCell(createCell("Date of Insertion", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("District / Edition", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Size (W)", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Size (H)", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Space (W*H)", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Card Rate (Clr)", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Card Rate (Rate)", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Position", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Total Amount", silverColor, fontColumn, false, false));
//				tableF.addCell(createCell("Total Amount", silverColor, fontSubHeader, false, false));
				tableF.setWidthPercentage(100);
				document.add(tableF);



				PdfPTable tableG = new PdfPTable(9);
				tableG.setWidthPercentage(100);
				tableG.setWidths(new float[]{2, 2, 2, 2, 2, 2, 2,2,2}); // Column widths

				// Get list of publish dates from erpData
				List<String> publishDates = erpData.getValue().getPublishDates();  // Assuming this is a list of publish dates
				
				for (String publishDate : publishDates) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				    // Add Date of Insertion (Publish Date)
					Date date = new SimpleDateFormat("yyyyMMdd").parse(publishDate);
					String formattedDate = dateFormat.format(date);
					
					String size = erpData.getValue().getSize();
					 String[] dimensions = size.split("x| X ");
					 int width = Integer.parseInt(dimensions[0].trim());
				     int height = Integer.parseInt(dimensions[1].trim());
				     String formatToIndianCurrency = formatToIndianCurrency(erpData.getValue().getRate());
				   
				    tableG.addCell(createCell(formattedDate, BaseColor.WHITE, fontColumn, false, false));
				    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
//			        String formattedAmount = currencyFormat.format(erpData.getValue().getAmount());
				    // Add other fields that are the same for each row (e.g., District/Edition, Size, etc.)
				    List<String> editions = erpData.getValue().getEditions();
				    String editionsString = String.join(", ", editions);
				    tableG.addCell(createCell(editionsString, BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(String.valueOf(width), BaseColor.WHITE, fontColumn, false, false));
				    tableG.addCell(createCell(String.valueOf(height), BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(String.valueOf(width*height), BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(erpData.getValue().getAdsSubType(), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
				    tableG.addCell(createCell(formatToIndianCurrency, BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
				    tableG.addCell(createCell(erpData.getValue().getPageNumberDesc() != null ? erpData.getValue().getPageNumberDesc() : "", BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
				    tableG.addCell(createCell(formatToIndianCurrency, BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

				}

				// Add tableG to document
				document.add(tableG);
				PdfPTable gst = new PdfPTable(9);
				if(erpData.getValue().getCategoryDisAmount() != null) {
					PdfPCell totalLabelCell = createCell("Category Discount ("+erpData.getValue().getCategoryDiscountPercentage()+"%)", BaseColor.WHITE, fontColumn, false, false);
					totalLabelCell.setColspan(8);
					totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					gst.addCell(totalLabelCell);
					gst.addCell(createCell(formatToIndianCurrency(erpData.getValue().getCategoryDisAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

					gst.setWidthPercentage(100);
					document.add(gst);
				}
				
				
				
				PdfPTable totalAmount = new PdfPTable(9);
				if(erpData.getValue().getSpecialDiscountAmount() != null) {
					PdfPCell totalLabelCell2 = createCell("SSP Spl. Discount ("+erpData.getValue().getSpecialDiscountPercentage()+"%)", BaseColor.WHITE, fontColumn, false, false);
					totalLabelCell2.setColspan(8);
					totalLabelCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					totalAmount.addCell(totalLabelCell2);
					totalAmount.addCell(createCell(formatToIndianCurrency(erpData.getValue().getSpecialDiscountAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

					totalAmount.setWidthPercentage(100);
					document.add(totalAmount);
				}
				
				
				
				PdfPTable gstAmount = new PdfPTable(9);
				if(erpData.getValue().getGstTotalAmount() != null) {
					PdfPCell totalLabelCell3 = createCell("GST (5%)", BaseColor.WHITE, fontColumn, false, false);
					totalLabelCell3.setColspan(8);
					totalLabelCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					gstAmount.addCell(totalLabelCell3);
					gstAmount.addCell(createCell(formatToIndianCurrency(erpData.getValue().getGstTotalAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

					gstAmount.setWidthPercentage(100);
					document.add(gstAmount);
				}
				
				
				PdfPTable totalValue = new PdfPTable(9);
				PdfPCell totalLabelCell4 = createCell("Total Value", BaseColor.WHITE, fontColumn, false, false);
				totalLabelCell4.setColspan(8);
				totalLabelCell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				totalValue.addCell(totalLabelCell4);
				totalValue.addCell(createCell(formatToIndianCurrency(erpData.getValue().getPaidAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

				totalValue.setWidthPercentage(100);
				document.add(totalValue);

				document.add(new Paragraph(" "));
				
				
				Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 8f);


				Paragraph mainContent = new Paragraph(
				        "Advertisement may be released as per the above details. Subject to your Terms & Conditions mentioned in the Rate Card.",
				        smallFont);
				document.add(mainContent);

//				document.add(new Chunk(line));


				
				Paragraph signatureLines = new Paragraph("*All Advertisements are accepted on advance payment only",smallFont);
				document.add(signatureLines);
//
				document.add(new Chunk(line));
//				Paragraph remarks = new Paragraph("Remarks.");
//				document.add(remarks);
//				document.add(new Chunk(line));
				document.add(new Paragraph(" "));

				PdfPTable sig = new PdfPTable(2);
				sig.setWidthPercentage(100);


				PdfPCell sigA2 = new PdfPCell();
				sigA2.setBorder(0); // No border
				sigA2.setHorizontalAlignment(Element.ALIGN_LEFT); // Align to the right
				
				
				for (String fileName : fileNames) {
					String fileNameAfterDocs = fileName.substring(fileName.indexOf("DOCS/") + "DOCS/".length());
				    if (fileNameAfterDocs.toLowerCase().startsWith("sig") && 
				        (fileName.toLowerCase().endsWith(".jpg") || 
				        fileName.toLowerCase().endsWith(".jpeg") || 
				        fileName.toLowerCase().endsWith(".png"))) {
				        
				        try {
				            // Load the signature image
				            Image signatureImage = Image.getInstance(fileName);

				            signatureImage.scaleToFit(100, 50); // Scale the image to fit
				            signatureImage.setAlignment(Image.ALIGN_LEFT);
				            
				            // Add the signature image to the client signature cell
				            sigA2.addElement(signatureImage); // Add image inside the cell
				        } catch (Exception e) {
				            System.out.println("Error processing signature image file: " + fileName + " - " + e.getMessage());
				        }
				    }
				}
				
				com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);
				Paragraph signatureText = new Paragraph("(E-Signature of the client)", fontColumn);
				signatureText.setAlignment(Element.ALIGN_LEFT); // Align the text to the right
				sigA2.addElement(signatureText);
				

				sig.addCell(sigA2);
				document.add(sig);
//				document.add(new Chunk(line));

				
				
				
				


				PdfPCell sigA3 = new PdfPCell();
				sigA3.setBorder(0); // No border
				sigA3.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align to the left
				for (String fileName : fileNames3) {
					String fileNameAfterDocs = fileName.substring(fileName.indexOf("DOCS/") + "DOCS/".length());
				    if (fileNameAfterDocs.toLowerCase().startsWith("sig") && 
				        (fileName.toLowerCase().endsWith(".jpg") || 
				        fileName.toLowerCase().endsWith(".jpeg") || 
				        fileName.toLowerCase().endsWith(".png"))) {
				        
				        try {
				            // Load the signature image
				            Image signatureImage = Image.getInstance(fileName);

				            signatureImage.scaleToFit(100, 50); // Scale the image to fit
				            signatureImage.setAlignment(Image.ALIGN_RIGHT);
				            
				            // Add the signature image to the client signature cell
				            sigA3.addElement(signatureImage); // Add image inside the cell
				        } catch (Exception e) {
				            System.out.println("Error processing signature image file: " + fileName + " - " + e.getMessage());
				        }
				    }
				}
				com.itextpdf.text.Font font1 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);
				Paragraph signatureText1 = new Paragraph("(E-Signature of the Customer)", fontColumn);
				signatureText1.setAlignment(Element.ALIGN_RIGHT); // Align the text to the right
				sigA3.addElement(signatureText1);
				
				
				sig.addCell(sigA3);
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
//				document.add(new Paragraph(" "));
//				for (String fileName : fileNames) {
//				    if (fileName.toLowerCase().endsWith(".jpg") || 
//				        fileName.toLowerCase().endsWith(".jpeg") || 
//				        fileName.toLowerCase().endsWith(".png")) {
//				        try {
//				            Image image = Image.getInstance(fileName);
//				            image.scaleAbsolute(200, 100);
//				            document.add(image);
//				        } catch (Exception e) {
//				            System.out.println("Error processing image file: " + fileName + " - " + e.getMessage());
//				        }
//				    }
//
//				    else {
//				        System.out.println("Unsupported file type: " + fileName);
//				    }
//				}
				
				
				
//				for (String fileName : fileNames3) {
//				    if (fileName.toLowerCase().endsWith(".jpg") || 
//				        fileName.toLowerCase().endsWith(".jpeg") || 
//				        fileName.toLowerCase().endsWith(".png")) {
//				        try {
//				            Image image = Image.getInstance(fileName);
//				            image.scaleAbsolute(200, 100);
//				            document.add(image);
//				        } catch (Exception e) {
//				            System.out.println("Error processing image file: " + fileName + " - " + e.getMessage());
//				        }
//				    }
//
//				    else {
//				        System.out.println("Unsupported file type: " + fileName);
//				    }
//				}

								
//				document.add(new Paragraph(" "));
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


				PdfPTable table = new PdfPTable(3);
				table.setWidthPercentage(100);
				
				
				PdfPTable leftTable = new PdfPTable(1);
				leftTable.setWidthPercentage(100);
				
				
//				PdfPCell cell1 = new PdfPCell(new com.itextpdf.text.Paragraph("JAGATI PUBLICATIONS LIMITED"));
				Paragraph company = new Paragraph("JAGATI PUBLICATIONS LIMITED", fontHeader);
				Paragraph address = new Paragraph(erpData.getValue().getOfficeAddress(), fontColumn);
				Paragraph gstn = new Paragraph("GSTIN : "+ erpData.getValue().getBookingGstIn(), fontColumn);
				Paragraph phone = new Paragraph("Ph : "+ erpData.getValue().getBookingPhnNo(),fontColumn);
				
				leftTable.addCell(noBorderCell(company));
				leftTable.addCell(noBorderCell(address));
				leftTable.addCell(noBorderCell(gstn));
				leftTable.addCell(noBorderCell(phone));
				
				PdfPCell cell1 = new PdfPCell(leftTable);
				cell1.setBorder(0);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				
//				cell1.setBorder(0);
//				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				
//				PdfPCell cell1 = new PdfPCell(new com.itextpdf.text.Paragraph("JAGATI PUBLICATIONS LIMITED"));
//				cell1.setBorder(0);
//				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				Image	image1;
				image1 = Image
							.getInstance(new URL("https://pre-prod-asp.s3.ap-south-1.amazonaws.com/static_assets/u3.png"));
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

//				child.addCell(child1);
//				child.addCell(child2);
//				document.add(child);

//				PdfPTable childG = new PdfPTable(1);
//				childG.setWidthPercentage(100);
//				Paragraph gstn = new Paragraph("GSTIN : "+ erpData.getValue().getBookingGstIn(), fontColumn);
//				PdfPCell child3 = new PdfPCell(gstn);
//
//				child3.setBorder(0);
//				child3.setHorizontalAlignment(Element.ALIGN_LEFT);
//
//				Paragraph mobile = new Paragraph("Ph : "+ erpData.getValue().getBookingPhnNo(),fontColumn);
//				PdfPCell child4 = new PdfPCell(mobile);
//				child4.setBorder(0);
//				child4.setHorizontalAlignment(Element.ALIGN_LEFT);
//				childG.addCell(child3);
//				childG.addCell(child4);
//				document.add(childG);
				document.add(new Paragraph(" "));
//				document.add(new Chunk(line));
				PdfPTable table1 = new PdfPTable(4);
				table1.addCell(createCell("Client Name", BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(
						createCell(erpData.getValue().getCustomerName(), BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(createCell("SSP Order", BaseColor.WHITE, fontColumn, false, false));
				table1.addCell(
						createCell(erpData.getValue().getAdId(), BaseColor.WHITE, fontColumn, false, false));
				table1.setWidthPercentage(100);
				document.add(table1);
				PdfPTable table2 = new PdfPTable(4);
				table2.addCell(createCell("House number", BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(createCell(erpData.getValue().getAddress1(), BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(createCell("Date", BaseColor.WHITE, fontColumn, false, false));
				table2.addCell(
						createCell(erpData.getValue().getCreatedDate(), BaseColor.WHITE, fontColumn, false, false));
				table2.setWidthPercentage(100);
				document.add(table2);

				PdfPTable table3 = new PdfPTable(4);
				table3.addCell(createCell("Pin code", BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(
						createCell(erpData.getValue().getPinCode(), BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(createCell("Employee code", BaseColor.WHITE, fontColumn, false, false));
				table3.addCell(createCell(erpData.getValue().getExecutiveEmpCode(), BaseColor.WHITE, fontColumn, false, false));
				table3.setWidthPercentage(100);
				document.add(table3);

				PdfPTable table4 = new PdfPTable(4);
				table4.addCell(createCell("City", BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(
						createCell(erpData.getValue().getBookingUnitStr(), BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(createCell("Employee", BaseColor.WHITE, fontColumn, false, false));
				table4.addCell(
						createCell(erpData.getValue().getExecutiveName(), BaseColor.WHITE, fontColumn, false, false));
				table4.setWidthPercentage(100);
				document.add(table4);

				PdfPTable table5 = new PdfPTable(4);
				table5.addCell(createCell("Client No", BaseColor.WHITE, fontColumn, false, false));
				table5.addCell(
						createCell(erpData.getValue().getMobileNumber(), BaseColor.WHITE, fontColumn, false, false));
				table5.addCell(createCell("Ad Booked By:", BaseColor.WHITE, fontColumn, false, false));
//				table5.addCell(createCell(erpData.getValue().getGstNo(), BaseColor.WHITE, fontSubHeader, false, false));
				table5.setWidthPercentage(100);
				document.add(table5);

				PdfPTable table6 = new PdfPTable(4);
				table6.addCell(createCell("State", BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(
						createCell(erpData.getValue().getStateDesc(), BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(createCell("SSP Name ", BaseColor.WHITE, fontColumn, false, false));
				table6.addCell(
						createCell(erpData.getValue().getSspUserName(), BaseColor.WHITE, fontColumn, false, false));
				table6.setWidthPercentage(100);
				document.add(table6);

				PdfPTable table7 = new PdfPTable(4);
				table7.addCell(createCell("Client GSTIN", BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(
						createCell(erpData.getValue().getGstNo() != null ? erpData.getValue().getGstNo() : "", BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(createCell("SSP Vendor Code", BaseColor.WHITE, fontColumn, false, false));
				table7.addCell(createCell(erpData.getValue().getLogonId(), BaseColor.WHITE,
						fontColumn, false, false));
				table7.setWidthPercentage(100);
				document.add(table7);

				PdfPTable table8 = new PdfPTable(4);
				table8.addCell(createCell("Category Name", BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(
						createCell(erpData.getValue().getCategoryStr(), BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(createCell("SSP Address:", BaseColor.WHITE, fontColumn, false, false));
				table8.addCell(
						createCell(erpData.getValue().getSspAddress(), BaseColor.WHITE, fontColumn, false, false));
				table8.setWidthPercentage(100);
				document.add(table8);
				String status = "PENDING";
				if(erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
					status = erpData.getValue().getContentStatus();
				}
				
				PdfPTable table9 = new PdfPTable(4);
				table9.addCell(createCell("Status", BaseColor.WHITE, fontColumn, false, false));
				table9.addCell(
						createCell(status, BaseColor.WHITE, fontColumn, false, false));
				table9.addCell(createCell("SSP Phn No", BaseColor.WHITE, fontColumn, false, false));
				table9.addCell(
						createCell(erpData.getValue().getMobileNo() != null ? erpData.getValue().getMobileNo():"", BaseColor.WHITE, fontColumn, false, false));
				table9.setWidthPercentage(100);
				document.add(table9);

				document.add(new Paragraph(" "));

				PdfPTable tableF = new PdfPTable(7);
				BaseColor silverColor = new BaseColor(192, 192, 192);
				tableF.addCell(createCell("Date of Insertion", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Publish Locations", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Ad Type", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Ad Subtype", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Pack", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Lines/Size", silverColor, fontColumn, false, false));
				tableF.addCell(createCell("Amount", silverColor, fontColumn, false, false));
//				tableF.addCell(createCell("Total Amount", silverColor, fontSubHeader, false, false));
				tableF.setWidthPercentage(100);
				document.add(tableF);



				PdfPTable tableG = new PdfPTable(7);
				tableG.setWidthPercentage(100);
				tableG.setWidths(new float[]{2, 2, 2, 2, 2, 2, 2}); // Column widths

				// Get list of publish dates from erpData
				List<String> publishDates = erpData.getValue().getPublishDates();  // Assuming this is a list of publish dates
				String scheme = erpData.getValue().getScheme1();
				Double ratePerDay = erpData.getValue().getRate();

				int chargeableDays = 1; // default
				int freeDays = 0;

				if (scheme != null && scheme.contains("+")) {
				    try {
				        String[] schemeParts = scheme.split("\\+");
				        chargeableDays = Integer.parseInt(schemeParts[0].trim());
				        freeDays = Integer.parseInt(schemeParts[1].replaceAll("[^0-9]", "").trim());
				    } catch (Exception e) {
				        System.out.println("Invalid scheme format: " + scheme);
				    }
				}
				int totalCycle = chargeableDays + freeDays;
				int index = 0;
				ratePerDay = ratePerDay / chargeableDays;

				// Loop through each publish date and create a row
				for (String publishDate : publishDates) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				    // Add Date of Insertion (Publish Date)
					Date date = new SimpleDateFormat("yyyyMMdd").parse(publishDate);
					String formattedDate = dateFormat.format(date);
					int modIndex = index % totalCycle;
				    Double effectiveRate = (modIndex < chargeableDays) ? ratePerDay : 0.0;

				    String formattedRate = formatToIndianCurrency(effectiveRate);
				    tableG.addCell(createCell(formattedDate, BaseColor.WHITE, fontColumn, false, false));
				    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
//			        String formattedAmount = currencyFormat.format(erpData.getValue().getAmount());
				    // Add other fields that are the same for each row (e.g., District/Edition, Size, etc.)
				    List<String> editions = erpData.getValue().getEditions();
				    String editionsString = String.join(", ", editions);
				    tableG.addCell(createCell(editionsString, BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(erpData.getValue().getAdsType(), BaseColor.WHITE, fontColumn, false, false));
				    tableG.addCell(createCell(erpData.getValue().getAdsSubType(), BaseColor.WHITE, fontColumn, false, false));

				    tableG.addCell(createCell(erpData.getValue().getScheme1(), BaseColor.WHITE, fontColumn, false, false));

//				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, false));
				    tableG.addCell(createCell(erpData.getValue().getLineCountSsp().toString(), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
//				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, false));

//				    tableG.addCell(createCell(erpData.getValue().getPageNumberDesc(), BaseColor.WHITE, fontColumn, false, false));  // Position (null or can be adjusted later)

//				    tableG.addCell(createCell(formatToIndianCurrency(erpData.getValue().getAmount()), BaseColor.WHITE, fontColumn, false, false));
				    tableG.addCell(createCell(formattedRate, BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
				    
				    index++;

				}

				// Add tableG to document
				document.add(tableG);
				PdfPTable gst = new PdfPTable(7);
				PdfPCell totalLabelCell = createCell("GST", BaseColor.WHITE, fontColumn, false, false);
				totalLabelCell.setColspan(6);
				totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				gst.addCell(totalLabelCell);
				gst.addCell(createCell(formatToIndianCurrency(erpData.getValue().getGstTotalAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

				gst.setWidthPercentage(100);
				document.add(gst);
				
				if("AGENCY_USER".equalsIgnoreCase(userContext.getLoggedUser().getRoleName())) {
					Double totValue = erpData.getValue().getRate();
					totValue = totValue - erpData.getValue().getAgencyCommition();
					totValue = totValue + erpData.getValue().getGstTotalAmount();
					Double roundingOff1 = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff1 * 100.0) / 100.0;
					PdfPTable roundingOff = new PdfPTable(7);
					PdfPCell totalLabelCell1 = createCell("Rounding Off", BaseColor.WHITE, fontColumn, false, false);
					totalLabelCell1.setColspan(6);
					totalLabelCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					roundingOff.addCell(totalLabelCell1);
					roundingOff.addCell(createCell(String.valueOf(roundedDifference), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

					roundingOff.setWidthPercentage(100);
					document.add(roundingOff);
					
				} else {
					Double totValue = erpData.getValue().getRate() + erpData.getValue().getGstTotalAmount();
					Double roundingOff1 = erpData.getValue().getPaidAmount().doubleValue() - totValue;
					double roundedDifference = Math.round(roundingOff1 * 100.0) / 100.0;
					PdfPTable roundingOff = new PdfPTable(7);
					PdfPCell totalLabelCell1 = createCell("Rounding Off", BaseColor.WHITE, fontColumn, false, false);
					totalLabelCell1.setColspan(6);
					totalLabelCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					roundingOff.addCell(totalLabelCell1);
					roundingOff.addCell(createCell(String.valueOf(roundedDifference), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

					roundingOff.setWidthPercentage(100);
					document.add(roundingOff);
					
				}
//				PdfPTable roundingOff = new PdfPTable(7);
//				PdfPCell totalLabelCell1 = createCell("Rounding Off", BaseColor.WHITE, fontSubHeader, false, false);
//				totalLabelCell1.setColspan(6);
//				totalLabelCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				roundingOff.addCell(totalLabelCell1);
//				roundingOff.addCell(createCell(formatToIndianCurrency(erpData.getValue().getWithSchemeTotalAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));
//
//				roundingOff.setWidthPercentage(100);
//				document.add(roundingOff);

				
				
				PdfPTable totalAmount = new PdfPTable(7);
				PdfPCell totalLabelCell2 = createCell("Total Amount", BaseColor.WHITE, fontColumn, false, false);
				totalLabelCell2.setColspan(6);
				totalLabelCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				totalAmount.addCell(totalLabelCell2);
				totalAmount.addCell(createCell(formatToIndianCurrency(erpData.getValue().getPaidAmount()), BaseColor.WHITE, fontColumn, false, true, Element.ALIGN_RIGHT));

				totalAmount.setWidthPercentage(100);
				document.add(totalAmount);

				document.add(new Paragraph(" "));
				
				
				PdfPTable table10 = new PdfPTable(4);
				table10.addCell(createCell("Publish Lang. :", silverColor, fontColumn, false, false));
				table10.addCell(
						createCell(erpData.getValue().getLangStr(), BaseColor.WHITE, fontColumn, false, false));
				table10.addCell(createCell("Category :", silverColor, fontColumn, false, false));
				table10.addCell(
						createCell(erpData.getValue().getCategoryStr(), BaseColor.WHITE, fontColumn, false, false));
				table10.setWidthPercentage(100);
				document.add(table10);
				
				
				PdfPTable table11 = new PdfPTable(4);
				table11.addCell(createCell("Subcategory :", silverColor, fontColumn, false, false));
				table11.addCell(
						createCell(erpData.getValue().getSubCategoryStr(), BaseColor.WHITE, fontColumn, false, false));
				table11.addCell(createCell("Print Ad Matter :", silverColor, fontColumn, false, false));
				table11.addCell(
						createCell(erpData.getValue().getMatter(), BaseColor.WHITE, fontColumn, false, false));
				table11.setWidthPercentage(100);
				document.add(table11);

				document.add(new Paragraph(" "));
				
//				PdfPTable detailsTable = new PdfPTable(2);
//				detailsTable.setWidthPercentage(100);
//				detailsTable.setSpacingBefore(10f);
//
//				// Set column widths
//				detailsTable.setWidths(new float[] { 2, 4 }); // key column smaller, value column larger
//
//				// Add rows
//				detailsTable.addCell(createCell("Publish Lang. :", BaseColor.BLACK, fontSubHeader, false, false));
//				detailsTable.addCell(createCell(erpData.getValue().getLangStr(), BaseColor.BLACK, fontSubHeader, false, false));
//
//				detailsTable.addCell(createCell("Category :", BaseColor.BLACK, fontSubHeader, true, false));
//				detailsTable.addCell(createCell(erpData.getValue().getCategoryStr(), BaseColor.BLACK, fontSubHeader, false, false));
//
//				detailsTable.addCell(createCell("Subcategory :", BaseColor.BLACK, fontSubHeader, true, false));
//				detailsTable.addCell(createCell(erpData.getValue().getSubCategoryStr(), BaseColor.BLACK, fontSubHeader, false, false));
//
//				detailsTable.addCell(createCell("Print Ad Matter :", BaseColor.BLACK, fontSubHeader, true, false));
//				detailsTable.addCell(createCell(erpData.getValue().getMatter(), BaseColor.BLACK, fontSubHeader, false, false));
//
//				if (erpData.getValue().getContentStatus() != null && !erpData.getValue().getContentStatus().isEmpty()) {
//					detailsTable.addCell(createCell("Approval Status :", BaseColor.BLACK, fontSubHeader, true, false));
//					detailsTable.addCell(createCell(erpData.getValue().getContentStatus(), BaseColor.BLACK, fontSubHeader, false, false));
//					
//				} else {
//					detailsTable.addCell(createCell("Approval Status :", BaseColor.BLACK, fontSubHeader, true, false));
//					detailsTable.addCell(createCell("PENDING", BaseColor.BLACK, fontSubHeader, false, false));
//				}
//				
//
//				// Add to document
//				document.add(detailsTable);
//				document.add(new Paragraph(" "));
				Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 8f);
				Paragraph mainContent = new Paragraph(
						"Advertisement may be released as per the above details. Subject to your Terms & Conditions mentioned in the Rate Card.All Advertisements are accepted on advance payment only",smallFont);
				document.add(mainContent);

//				document.add(new Chunk(line));



				Paragraph signatureLines = new Paragraph("*All Advertisements are accepted on advance payment only",smallFont);
				document.add(signatureLines);
//
//				document.add(new Chunk(line));
//				Paragraph remarks = new Paragraph("Remarks.");
//				document.add(remarks);
//				document.add(new Chunk(line));
				document.add(new Paragraph(" "));

				PdfPTable sig = new PdfPTable(1);
				sig.setWidthPercentage(100);


				PdfPCell sigA2 = new PdfPCell();
				sigA2.setBorder(0); // No border
				sigA2.setHorizontalAlignment(Element.ALIGN_RIGHT); // Align to the right
				
				
				for (String fileName : fileNames) {
					String fileNameAfterDocs = fileName.substring(fileName.indexOf("DOCS/") + "DOCS/".length());
				    if (fileNameAfterDocs.toLowerCase().startsWith("sig") && 
				        (fileName.toLowerCase().endsWith(".jpg") || 
				        fileName.toLowerCase().endsWith(".jpeg") || 
				        fileName.toLowerCase().endsWith(".png"))) {
				        
				        try {
				            // Load the signature image
				            Image signatureImage = Image.getInstance(fileName);

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
//				document.add(new Paragraph(" "));
//				for (String fileName : fileNames) {
//				    if (fileName.toLowerCase().endsWith(".jpg") || 
//				        fileName.toLowerCase().endsWith(".jpeg") || 
//				        fileName.toLowerCase().endsWith(".png")) {
//				        try {
//				            Image image = Image.getInstance(fileName);
//				            image.scaleAbsolute(200, 100);
//				            document.add(image);
//				        } catch (Exception e) {
//				            System.out.println("Error processing image file: " + fileName + " - " + e.getMessage());
//				        }
//				    }
//
//				    else {
//				        System.out.println("Unsupported file type: " + fileName);
//				    }
//				}
//
//								
//				document.add(new Paragraph(" "));
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

}
