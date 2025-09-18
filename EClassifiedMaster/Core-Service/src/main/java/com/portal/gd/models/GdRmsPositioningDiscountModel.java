package com.portal.gd.models;

import java.math.BigInteger;

public class GdRmsPositioningDiscountModel {
	private String editionType;
	private String positioningType;
	private String positioningDesc;
	private String erpRefId;
	private Integer discount;
	private String typeOfPosition;
	private Short editionTypeId;
	private BigInteger id;
	public String getEditionType() {
		return editionType;
	}
	public void setEditionType(String editionType) {
		this.editionType = editionType;
	}
	public String getPositioningType() {
		return positioningType;
	}
	public void setPositioningType(String positioningType) {
		this.positioningType = positioningType;
	}
	public String getPositioningDesc() {
		return positioningDesc;
	}
	public void setPositioningDesc(String positioningDesc) {
		this.positioningDesc = positioningDesc;
	}
	public String getErpRefId() {
		return erpRefId;
	}
	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public String getTypeOfPosition() {
		return typeOfPosition;
	}
	public void setTypeOfPosition(String typeOfPosition) {
		this.typeOfPosition = typeOfPosition;
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
