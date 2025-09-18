package com.portal.da.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import com.portal.clf.entities.ClfPaymentResponseTracking;
import com.portal.clf.models.AddToCartRequest;
import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.ClassifiedStatus;
import com.portal.clf.models.ClassifiedsOrderItemDetails;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.clf.models.ErpClassifieds;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.security.model.LoggedUser;

public interface DisplayService {

	public GenericApiResponse addDisplayItemToCart(AddToCartRequest payload, LoggedUser loggedUser);
//	public GenericApiResponse getRatesAndLines(ClassifiedsOrderItemDetails itemDetails);
	public GenericApiResponse getDisplayAdsList(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse getDisplayAdsDashboardCounts(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse getDownloadStatusList(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse getDisplayAdsPendingPaymentList(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse getDisplatRates(ClassifiedsOrderItemDetails payload);
	public GenericApiResponse getDisplayCartItems(LoggedUser loggedUser);
	public GenericApiResponse viewDaItem(LoggedUser loggedUser, @NotNull String itemId);
	public GenericApiResponse deleteClassified(LoggedUser loggedUser, @NotNull String itemId);
	public GenericApiResponse getDisplayAdEncodedString(String orderId, LoggedUser loggedUser);
	public GenericApiResponse sendDisplayEmailToSchedulingTeam(@NotNull String orderId, LoggedUser loggedUser);
	public GenericApiResponse approveDisplayAds(LoggedUser loggedUser, ClassifiedStatus payload);
	public GenericApiResponse syncronizeSAPData(GenericRequestHeaders requestHeaders, ClassifiedStatus orderIds);
	public Map<String, ErpClassifieds> getDisplayOrderDetailsForErp(List<String> orderIds);
	public void sendDisplayMailToCustomer(Map<String, ErpClassifieds> erpClassifiedsMap,BillDeskPaymentResponseModel payload, LoggedUser loggedUser,ClfPaymentResponseTracking clfPaymentResponseTracking) throws IOException;
	public void getDisplayAddsForCurrentDate();
	public GenericApiResponse downloadAdsPdfDocument(DashboardFilterTo payload);
	public GenericApiResponse getCustomerDetails(@NotNull String mobileNo);
	public GenericApiResponse getVendorCommission(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse updateDownloadStatus(LoggedUser loggedUser, @NotNull String itemId);
	public GenericApiResponse uploadVendorCommision(HttpServletRequest request);
	public GenericApiResponse downloadTemplateHeaders(HttpServletResponse response);
	public void sendMailToCustomerForSSPVendor(Map<String, ErpClassifieds> erpClassifiedsMap,
			 BillDeskPaymentResponseModel payload, LoggedUser loggedUser,
			ClfPaymentResponseTracking clfPaymentResponseTracking);
	
	public Map<String, ErpClassifieds> getClassifiedOrderDetailsForSSPErp(List<String> orderIds);
	public GenericApiResponse getSSPSummaryReports(LoggedUser loggedUser, DashboardFilterTo payload);

}
