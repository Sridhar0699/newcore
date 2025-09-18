package com.portal.gd.models;

import java.math.BigInteger;

public class GdRmsPagePositionsModel {

	private String editionType;
	private String pageName;
	private String pageDescription;
	private String erpRefCode;
	private Integer percentage;
	private Integer editionTypeId;
	private BigInteger id;
	public String getEditionType() {
		return editionType;
	}
	public void setEditionType(String editionType) {
		this.editionType = editionType;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageDescription() {
		return pageDescription;
	}
	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}
	public String getErpRefCode() {
		return erpRefCode;
	}
	public void setErpRefCode(String erpRefCode) {
		this.erpRefCode = erpRefCode;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public Integer getEditionTypeId() {
		return editionTypeId;
	}
	public void setEditionTypeId(Integer editionTypeId) {
		this.editionTypeId = editionTypeId;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	
}
