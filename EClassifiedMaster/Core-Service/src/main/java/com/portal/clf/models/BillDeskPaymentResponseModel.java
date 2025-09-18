package com.portal.clf.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portal.clf.entities.ClfPaymentResponseTracking;

public class BillDeskPaymentResponseModel {

	private String mercid;
	private String terminal_state;
	private String orderid;
	private String bdcres;
	private String transaction_response;
	private String secOrderId;
	private String itemOrderId;
	@JsonIgnore
	private ClfPaymentResponseTracking clfPaymentResponseTracking;

	public String getMercid() {
		return mercid;
	}

	public void setMercid(String mercid) {
		this.mercid = mercid;
	}

	public String getTerminal_state() {
		return terminal_state;
	}

	public void setTerminal_state(String terminal_state) {
		this.terminal_state = terminal_state;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getBdcres() {
		return bdcres;
	}

	public void setBdcres(String bdcres) {
		this.bdcres = bdcres;
	}

	public String getTransaction_response() {
		return transaction_response;
	}

	public void setTransaction_response(String transaction_response) {
		this.transaction_response = transaction_response;
	}

	public String getSecOrderId() {
		return secOrderId;
	}

	public void setSecOrderId(String secOrderId) {
		this.secOrderId = secOrderId;
	}

	public String getItemOrderId() {
		return itemOrderId;
	}

	public void setItemOrderId(String itemOrderId) {
		this.itemOrderId = itemOrderId;
	}

	public ClfPaymentResponseTracking getClfPaymentResponseTracking() {
		return clfPaymentResponseTracking;
	}

	public void setClfPaymentResponseTracking(ClfPaymentResponseTracking clfPaymentResponseTracking) {
		this.clfPaymentResponseTracking = clfPaymentResponseTracking;
	}

	
}
