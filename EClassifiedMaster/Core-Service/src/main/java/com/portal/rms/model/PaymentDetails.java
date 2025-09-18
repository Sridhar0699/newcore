package com.portal.rms.model;

public class PaymentDetails {
	
	private Integer paymentMode;
	private String bankOrBranch;
	private String referenceId;
	private String signatureId;
	private Integer paymentMethod;
	private String bankOrUpi;
	private String cashReceiptNo;
	private String otherDetails;
	private String paymentId;
	private String chequeId;
	
	private RmsKycAttatchments kycAttatchments;

	public String getBankOrBranch() {
		return bankOrBranch;
	}
	public void setBankOrBranch(String bankOrBranch) {
		this.bankOrBranch = bankOrBranch;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
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
	
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getChequeId() {
		return chequeId;
	}
	public void setChequeId(String chequeId) {
		this.chequeId = chequeId;
	}
	public RmsKycAttatchments getKycAttatchments() {
		return kycAttatchments;
	}
	public void setKycAttatchments(RmsKycAttatchments kycAttatchments) {
		this.kycAttatchments = kycAttatchments;
	}
	public String getSignatureId() {
		return signatureId;
	}
	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}
	
}
