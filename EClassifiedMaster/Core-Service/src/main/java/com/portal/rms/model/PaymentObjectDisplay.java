package com.portal.rms.model;

import java.util.List;

public class PaymentObjectDisplay {
	
	private Short paymentMethod;
	private String paymentMethodDesc;
	private Short paymentMode;
	private String paymentModeDesc;
	private String referenceId;
	private String cashReceiptNo;
	private String otherDetails;
	private String signatureId;
	private String bankOrUpi;
	private List<String> attachmentUrls;
	private String paymentId;
	private String chequeId;
	private RmsKycAttatchments attatchments;

	public Short getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Short paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentMethodDesc() {
		return paymentMethodDesc;
	}

	public void setPaymentMethodDesc(String paymentMethodDesc) {
		this.paymentMethodDesc = paymentMethodDesc;
	}

	public Short getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Short paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentModeDesc() {
		return paymentModeDesc;
	}

	public void setPaymentModeDesc(String paymentModeDesc) {
		this.paymentModeDesc = paymentModeDesc;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
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

	public String getBankOrUpi() {
		return bankOrUpi;
	}

	public void setBankOrUpi(String bankOrUpi) {
		this.bankOrUpi = bankOrUpi;
	}

	public List<String> getAttachmentUrls() {
		return attachmentUrls;
	}

	public void setAttachmentUrls(List<String> attachmentUrls) {
		this.attachmentUrls = attachmentUrls;
	}

	public RmsKycAttatchments getAttatchments() {
		return attatchments;
	}

	public void setAttatchments(RmsKycAttatchments attatchments) {
		this.attatchments = attatchments;
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


}
