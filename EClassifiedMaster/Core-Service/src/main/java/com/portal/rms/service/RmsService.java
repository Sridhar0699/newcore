package com.portal.rms.service;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.itextpdf.io.IOException;
import com.itextpdf.text.DocumentException;
import com.portal.clf.entities.ClfPaymentResponseTracking;
import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.clf.models.ErpClassifieds;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.rms.model.ApprovalDetailsModel;
import com.portal.rms.model.CreateOrder;
import com.portal.rms.model.OtpModel;
import com.portal.rms.model.RmsApproveModel;
import com.portal.rms.model.RmsDashboardFilter;
import com.portal.rms.model.RmsModel;
import com.portal.rms.model.RmsPaymentLinkModel;
import com.portal.rms.model.RmsRateModel;
import com.portal.security.model.LoggedUser;
import com.portal.workflow.model.WfRequest;

public interface RmsService {

	public GenericApiResponse getDashboardCounts(LoggedUser loggedUser, RmsDashboardFilter payload);

	public GenericApiResponse getRmsClassifiedList(LoggedUser loggedUser,RmsDashboardFilter payload);
	
	public GenericApiResponse getRmsClassifiedsByAdId(LoggedUser loggedUser, @NotNull String adId);

	public GenericApiResponse addRmsClassifiedItemToCart(CreateOrder payload, LoggedUser loggedUser);

	public GenericApiResponse getCustomerDetails(String clientCode, LoggedUser loggedUser);

	public GenericApiResponse getRmsRates(RmsRateModel payload);

	public GenericApiResponse genrateOTP(OtpModel payload);

	public GenericApiResponse validateOTP(OtpModel payload);

	public GenericApiResponse syncronizeRmsSAPData(GenericRequestHeaders requestHeaders, @NotNull RmsModel payload);

	public GenericApiResponse generateSendPaymentLink(RmsPaymentLinkModel payload);

	public GenericApiResponse getFixedFormatsMasters(RmsRateModel payload);

	public GenericApiResponse getEditionTypeWiseDistricts(@NotNull Integer editionType, @NotNull Integer formatType, Integer addType, String stateCode);

	public GenericApiResponse getPagePositionsOnEditionType(@NotNull Integer editionType);

	public GenericApiResponse approveOrRejectRmsOrder(RmsApproveModel payload);

	public GenericApiResponse getApprovalInbox(LoggedUser loggedUser, RmsDashboardFilter payload);

	public GenericApiResponse getDraftDetailView(@NotNull String orderId);

	public GenericApiResponse getRmsListView(LoggedUser loggedUser, DashboardFilterTo payload);

	public GenericApiResponse deleteDraftOrders(LoggedUser loggedUser, String orderId);

	public void deleteRmsDraftOrders();

	public GenericApiResponse downloadPdf(LoggedUser loggedUser, @NotNull String orderId) throws IOException, DocumentException, java.io.IOException;

	public GenericApiResponse getCustomerDetailsByMobileNo(String mobileNo);

	public GenericApiResponse isMobileNumberDuplicate(String mobileNo);

	public void updateOrderDetailsonStatus(RmsApproveModel payload);

	public Map<String, ErpClassifieds> getRmsOrderDetailsForErp(List<String> orderIds);

	public void sendRmsMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap, BillDeskPaymentResponseModel payload,
			LoggedUser loggedUser, ClfPaymentResponseTracking clfPaymentResponseTracking);

	public void sendMailToApprover(String toMail, String ccMail, Map<String, ErpClassifieds> erpMap);

	public GenericApiResponse validateAccessKey(WfRequest payload);
	
	public Map<String, ErpClassifieds> getRmsOrderDetailsForErp(ApprovalDetailsModel detailsModel);
}
