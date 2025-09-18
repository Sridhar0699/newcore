package com.portal.clf.service;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;

import com.portal.clf.models.BillDeskPaymentResponseModel;
import com.portal.clf.models.CartDetails;
import com.portal.clf.models.ClfPaymentsRefund;
import com.portal.common.models.GenericApiResponse;
import com.portal.security.model.LoggedUser;

public interface PaymentService {

	public GenericApiResponse updatePaymentResponse(BillDeskPaymentResponseModel payload, LoggedUser loggedUser);
	public void updatePendingPaymentStatuses();
	public GenericApiResponse prepareRequest(@NotNull CartDetails payload, LoggedUser loggedUser);
	public void saveEncodedRequest(String encToken, String orderId, String portalOrderId, LoggedUser loggedUser, Integer orderType,Boolean flag,boolean encodedRequest);
	public void saveEncodedHeaders(String encToken, String orderId);
	public GenericApiResponse billDeskWebHookLogs(@NotNull BillDeskPaymentResponseModel payload, LoggedUser loggedUser);
	public GenericApiResponse prepareRequestForCreateRefund(@NotNull ClfPaymentsRefund payload,LoggedUser loggedUser) ;
	public GenericApiResponse getRefund(String mercId,String refundId) ;
	public void getRefundDetails();
	public ResponseEntity<?> saveEncodedResponse(@NotNull String encToken,LoggedUser loggedUser);
	public GenericApiResponse getPaymentsAndRefundDetails(String orderId, LoggedUser loggedUser);
	public GenericApiResponse getTransactionDetailsOnOrderId(String orderId, LoggedUser loggedUser, Boolean linkFlag);
}
