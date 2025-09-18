package com.portal.gd.models;

import java.math.BigInteger;
import java.util.Date;

public class GdRmsEditionsModel {
	private String editionName;
	private String erpEdition;
	private Integer createdBy;
	private Date createdDate;
	private Integer updatedBy;
	private Date updatedDate;
	private BigInteger id;
	private String erpRefId;
	private String editionType;
	private Integer editionTypeId;
	private Integer addType;
	private String stateCode;
	private String addTypeDescription;
	private String state;
	public String getEditionName() {
		return editionName;
	}
	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}
	public String getErpEdition() {
		return erpEdition;
	}
	public void setErpEdition(String erpEdition) {
		this.erpEdition = erpEdition;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getErpRefId() {
		return erpRefId;
	}
	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}
	public String getEditionType() {
		return editionType;
	}
	public void setEditionType(String editionType) {
		this.editionType = editionType;
	}
	public Integer getEditionTypeId() {
		return editionTypeId;
	}
	public void setEditionTypeId(Integer editionTypeId) {
		this.editionTypeId = editionTypeId;
	}
	public Integer getAddType() {
		return addType;
	}
	public void setAddType(Integer addType) {
		this.addType = addType;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getAddTypeDescription() {
		return addTypeDescription;
	}
	public void setAddTypeDescription(String addTypeDescription) {
		this.addTypeDescription = addTypeDescription;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
