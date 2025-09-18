package com.portal.rms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "rms_payments_response")
public class RmsPaymentsResponse extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "payments_id")
    private String paymentsId;
	
	@Column(name = "item_id")
	private String itemId;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "payment_mode")
    private Integer paymentMode;
	
	@Column(name = "bank_branch")
    private String bankBranch;
	
	@Column(name = "bank_ref_id")
    private String bankRefId;
	
	@Column(name = "mark_as_delete")
    private boolean markAsDelete;
	
	@Column(name="created_ts")
	private Date createdTs;
	
	@Column(name="created_by")
    private Integer createdBy;
	
    @Column(name="changed_ts")
    private Date changedTs;
    
    @Column(name="changed_by")
    private Integer changedBy;
    
    @Column(name="signature_id")
    private String signatureId;
    
    @Column(name="payment_method")
    private Integer paymentMethod;
    
    @Column(name="bank_or_upi")
    private String bankOrUpi;
    
    @Column(name="cash_receipt_no")
    private String cashReceiptNo;
    
    @Column(name="other_details")
    private String otherDetails;
    

    @Column(name="cheque_att_id")
    private String chequeAttId;
    
    @Column(name="payment_att_id")
    private String paymentAttId;
    
	public String getPaymentsId() {
		return paymentsId;
	}

	public void setPaymentsId(String paymentsId) {
		this.paymentsId = paymentsId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	
	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankRefId() {
		return bankRefId;
	}

	public void setBankRefId(String bankRefId) {
		this.bankRefId = bankRefId;
	}

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}
	public Integer getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBankOrUpi() {
		return bankOrUpi;
	}

	public void setBankOrUpi(String bankOrUpi) {
		this.bankOrUpi = bankOrUpi;
	}

	public String getCashReceiptNo() {
		return cashReceiptNo;
	}

	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}

	public String getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public String getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}

	public String getChequeAttId() {
		return chequeAttId;
	}

	public void setChequeAttId(String chequeAttId) {
		this.chequeAttId = chequeAttId;
	}

	public String getPaymentAttId() {
		return paymentAttId;
	}

	public void setPaymentAttId(String paymentAttId) {
		this.paymentAttId = paymentAttId;
	}
	

    
    
    
    
    
}
