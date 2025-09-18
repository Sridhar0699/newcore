package com.portal.clf.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "payments_refund")
public class PaymentsRefund extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
    private String id;
	
	@Column(name = "merc_refund_ref_no")
	private String mercRefundRefno;
	
	@Column(name = "transaction_id")
	private String transaction_id;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "merc_id")
	private String mercId;
	
	@Column(name = "transaction_date")
	private Date transaction_date;
	
	@Column(name = "txn_amount")
	private Double  txnAmount;
	
	@Column(name = "refund_amount")
    private Double refundAmount;
	
	@Column(name = "refund_status")
    private String refundStatus;
	
	@Column(name = "currency")
    private String currency;
	
	@Column(name = "object_id")
    private String objectId;
	
	@Column(name = "refund_id")
    private String refundId;
	
	@Column(name = "encoded_request")
    private String encodedRequest;
	
	@Column(name = "encoded_response")
    private String encodedResponse;
	
	@Column(name = "mark_as_delete")
    private boolean markAsDelete;
	
	@Column(name = "refund_date")
	private Date refundDate;

	public String getMercRefundRefno() {
		return mercRefundRefno;
	}

	public void setMercRefundRefno(String mercRefundRefno) {
		this.mercRefundRefno = mercRefundRefno;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMercId() {
		return mercId;
	}

	public void setMercId(String mercId) {
		this.mercId = mercId;
	}

	public Date getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(Date transaction_date) {
		this.transaction_date = transaction_date;
	}

	public Double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	
	
	
}