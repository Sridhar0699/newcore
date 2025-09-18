package com.portal.gd.models;

import java.math.BigInteger;

public class GdRmsSchemesModel {
	private String editionType;
	private String scheme;
	private String erpScheme;
	private String erpRefId;
	private Short noOfDays;
	private Short billableDays;
	private Short editionTypeId;
	private BigInteger id;
	public String getEditionType() {
		return editionType;
	}
	public void setEditionType(String editionType) {
		this.editionType = editionType;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getErpScheme() {
		return erpScheme;
	}
	public void setErpScheme(String erpScheme) {
		this.erpScheme = erpScheme;
	}
	public String getErpRefId() {
		return erpRefId;
	}
	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}
	public Short getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Short noOfDays) {
		this.noOfDays = noOfDays;
	}
	public Short getBillableDays() {
		return billableDays;
	}
	public void setBillableDays(Short billableDays) {
		this.billableDays = billableDays;
	}
	public Short getEditionTypeId() {
		return editionTypeId;
	}
	public void setEditionTypeId(Short editionTypeId) {
		this.editionTypeId = editionTypeId;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	
	
	
}
