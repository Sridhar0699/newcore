package com.portal.gd.models;

import java.math.BigInteger;

public class GdRmsMultipleDiscountValModel {
	private String editionName;
	private String erpEditionCode;
	private String erpPositionInstCode;
	private String scheme;
	private Integer editionId;
	private Integer schemeId;
	private BigInteger id;
	public String getEditionName() {
		return editionName;
	}
	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}
	public String getErpEditionCode() {
		return erpEditionCode;
	}
	public void setErpEditionCode(String erpEditionCode) {
		this.erpEditionCode = erpEditionCode;
	}
	public String getErpPositionInstCode() {
		return erpPositionInstCode;
	}
	public void setErpPositionInstCode(String erpPositionInstCode) {
		this.erpPositionInstCode = erpPositionInstCode;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public Integer getEditionId() {
		return editionId;
	}
	public void setEditionId(Integer editionId) {
		this.editionId = editionId;
	}
	public Integer getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	
}
