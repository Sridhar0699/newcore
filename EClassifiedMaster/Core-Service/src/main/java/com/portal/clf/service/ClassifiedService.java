package com.portal.clf.service;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.portal.clf.models.AddToCartRequest;
import com.portal.clf.models.ClassifiedStatus;
import com.portal.clf.models.ClassifiedsOrderItemDetails;
import com.portal.clf.models.DashboardFilterTo;
import com.portal.clf.models.ErpClassifieds;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.erp.to.ApiResponse;
import com.portal.erp.to.ErpDataResponsePayload;
import com.portal.security.model.LoggedUser;

public interface ClassifiedService {

	public GenericApiResponse getClassifiedList(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse addClassifiedItemToCart(AddToCartRequest item, LoggedUser loggedUser);
	public GenericApiResponse getCartDetails();
	public GenericApiResponse getCartItems(LoggedUser loggedUser);
	public GenericApiResponse getClassifiedTypes();
	public GenericApiResponse getClassifiedTemplates(String id);
	public GenericApiResponse getCustomerDetails(String mobileNo);
	public GenericApiResponse getDashboardCounts(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse getRatesAndLines(ClassifiedsOrderItemDetails itemDetails);
	public GenericApiResponse getPaymentHistory(LoggedUser loggedUser);
	public GenericApiResponse deleteClassified(LoggedUser loggedUser, String itemId);
	public GenericApiResponse viewClfItem(LoggedUser loggedUser, String itemId);
	public GenericApiResponse approveClassifieds(LoggedUser loggedUser, ClassifiedStatus payload);
	public GenericApiResponse syncronizeSAPData(GenericRequestHeaders requestHeaders, ClassifiedStatus payload);
	public GenericApiResponse downloadAdsPdfDocument(DashboardFilterTo payload);
	public Map<String, ErpClassifieds> getOrderDetailsForErp(List<String> orderIds);
	public void getAddsForCurrentDate();
	public GenericApiResponse getDownloadStatusList(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse getPendingPaymentList(LoggedUser loggedUser, DashboardFilterTo payload);
	public ApiResponse updateSyncStatus(List<ErpDataResponsePayload> erpResponseList);
	public String generateSeries(String type);
	public GenericApiResponse getDownloadStatusListExcelDownload(LoggedUser loggedUser, DashboardFilterTo payload);
	public GenericApiResponse getEncodedString(String orderId, LoggedUser loggedUser);
	public GenericApiResponse sendEmailToSchedulingTeam(@NotNull String orderId, LoggedUser loggedUser);
	public GenericApiResponse getPaymentSuccessOrderPendingData(DashboardFilterTo payload);
}
