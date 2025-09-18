package com.portal.clf.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "clf_payment_response_tracking")
public class ClfPaymentResponseTracking extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "gate_way_id")
	private String gateWayId;

	@Column(name = "sec_order_id")
	private String secOrderId;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "transaction_date")
	private String transactionDate;

	@Column(name = "payment_method_type")
	private String paymentMethodType;

	@Column(name = "amount")
	private String amount;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "bank_ref_no")
	private String bankRefNo;

	@Column(name = "transactionid")
	private String transactionId;

	@Column(name = "txn_process_type")
	private String txnProcessType;
	
	@Column(name = "response")
	private String response;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;
	
	@Column(name = "auth_status")
	private String authStatus;
	
	@Column(name="encoded_request")
	private String encodedRequest;
	
	@Column(name="encoded_response")
	private String encodedResponse;
	
	@Column(name="transaction_error_desc")
	private String transactionErrorDesc;
	
	@Column(name="order_type")
	private Integer orderType;
	
	@Column(name="headers")
	private String headers;
	
	@Column(name="mark_as_delete")
	private Boolean markAsDelete;
	
	@Column(name="link_flag")
	private Boolean linkFlag;
	
	@Column(name = "ecom_encoded_request")
	private String ecomEncodedRequest;
	
	@Column(name = "ecom_encoded_response")
	private String ecomEncodedResponse;
	
	@Column(name = "ecom_headers")
	private String ecomHeaders;
	
	@Column(name = "ecom_order_id")
	private String ecomOrderId;

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGateWayId() {
		return gateWayId;
	}

	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}

	public String getSecOrderId() {
		return secOrderId;
	}

	public void setSecOrderId(String secOrderId) {
		this.secOrderId = secOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPaymentMethodType() {
		return paymentMethodType;
	}

	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getBankRefNo() {
		return bankRefNo;
	}

	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTxnProcessType() {
		return txnProcessType;
	}

	public void setTxnProcessType(String txnProcessType) {
		this.txnProcessType = txnProcessType;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getEncodedRequest() {
		return encodedRequest;
	}

	public void setEncodedRequest(String encodedRequest) {
		this.encodedRequest = encodedRequest;
	}

	public String getEncodedResponse() {
		return encodedResponse;
	}

	public void setEncodedResponse(String encodedResponse) {
		this.encodedResponse = encodedResponse;
	}

	public String getTransactionErrorDesc() {
		return transactionErrorDesc;
	}

	public void setTransactionErrorDesc(String transactionErrorDesc) {
		this.transactionErrorDesc = transactionErrorDesc;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public Boolean getLinkFlag() {
		return linkFlag;
	}

	public void setLinkFlag(Boolean linkFlag) {
		this.linkFlag = linkFlag;
	}

	public String getEcomEncodedRequest() {
		return ecomEncodedRequest;
	}

	public void setEcomEncodedRequest(String ecomEncodedRequest) {
		this.ecomEncodedRequest = ecomEncodedRequest;
	}

	public String getEcomEncodedResponse() {
		return ecomEncodedResponse;
	}

	public void setEcomEncodedResponse(String ecomEncodedResponse) {
		this.ecomEncodedResponse = ecomEncodedResponse;
	}

	public String getEcomHeaders() {
		return ecomHeaders;
	}

	public void setEcomHeaders(String ecomHeaders) {
		this.ecomHeaders = ecomHeaders;
	}

	public String getEcomOrderId() {
		return ecomOrderId;
	}

	public void setEcomOrderId(String ecomOrderId) {
		this.ecomOrderId = ecomOrderId;
	}
	
}
