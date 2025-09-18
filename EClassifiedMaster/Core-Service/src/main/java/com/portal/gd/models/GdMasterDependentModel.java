package com.portal.gd.models;

import java.util.Date;

public class GdMasterDependentModel {

	private Integer editionId;
	private String erpEditionCode;
	private String scheme;
	private Integer classifiedAdsSubtype;
	private Double rate;
	private Integer positionInstId;
	private String postionInstErpRefId;
	
	
	private String editionName;
	private String erpEdition;
	private String erpRefId;
	private Integer editionType;
	private Integer id;
	private Integer adType;
	private String stateCode;
	private String idStr;
	
	private String erpPositionInstCode;
	private Integer schemeId;

	
	private String pageName;
	private String pageDescription;
	private String erpRefCode;
	private Integer percentage;
	
	
	private String pageErpRefCode;
	
	private String positioningType;
	private String positioningDesc;
	private Integer discount;
	private String typeOfPosition;
	
	private String erpScheme;
	private Integer noOfDays;
	private Integer billableDays;
	private Integer schemeEditionType;
	
	private Integer createdBy;
	private Integer changedBy;
	private Date createdTs;
	private Date changedTs;
	private boolean markAsDelete;
	
	private String masterType;
	private String actionType;
	private Integer adsSubType;
	private Integer fixedFormat;
	private Integer bookingOffice;
	private Double rangeFrom;
	private Double rangeTo;
	private Integer approverUserId;
	private Integer approverCCUserID;
	private Integer level;
	private Integer editionTypeId;
	
	
	public Integer getEditionId() {
		return editionId;
	}
	public void setEditionId(Integer editionId) {
		this.editionId = editionId;
	}
	public String getErpEditionCode() {
		return erpEditionCode;
	}
	public void setErpEditionCode(String erpEditionCode) {
		this.erpEditionCode = erpEditionCode;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public Integer getClassifiedAdsSubtype() {
		return classifiedAdsSubtype;
	}
	public void setClassifiedAdsSubtype(Integer classifiedAdsSubtype) {
		this.classifiedAdsSubtype = classifiedAdsSubtype;
	}
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Integer getPositionInstId() {
		return positionInstId;
	}
	public void setPositionInstId(Integer positionInstId) {
		this.positionInstId = positionInstId;
	}
	public String getPostionInstErpRefId() {
		return postionInstErpRefId;
	}
	public void setPostionInstErpRefId(String postionInstErpRefId) {
		this.postionInstErpRefId = postionInstErpRefId;
	}
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
	public String getErpRefId() {
		return erpRefId;
	}
	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}
	public Integer getEditionType() {
		return editionType;
	}
	public void setEditionType(Integer editionType) {
		this.editionType = editionType;
	}
	public String getErpPositionInstCode() {
		return erpPositionInstCode;
	}
	public void setErpPositionInstCode(String erpPositionInstCode) {
		this.erpPositionInstCode = erpPositionInstCode;
	}
	public Integer getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
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
	public String getPageErpRefCode() {
		return pageErpRefCode;
	}
	public void setPageErpRefCode(String pageErpRefCode) {
		this.pageErpRefCode = pageErpRefCode;
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
	public String getErpScheme() {
		return erpScheme;
	}
	public void setErpScheme(String erpScheme) {
		this.erpScheme = erpScheme;
	}
	
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
	public Integer getBillableDays() {
		return billableDays;
	}
	public void setBillableDays(Integer billableDays) {
		this.billableDays = billableDays;
	}
	public Integer getSchemeEditionType() {
		return schemeEditionType;
	}
	public void setSchemeEditionType(Integer schemeEditionType) {
		this.schemeEditionType = schemeEditionType;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}
	public Date getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}
	public Date getChangedTs() {
		return changedTs;
	}
	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}
	public boolean isMarkAsDelete() {
		return markAsDelete;
	}
	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}
	public String getMasterType() {
		return masterType;
	}
	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAdType() {
		return adType;
	}
	public void setAdType(Integer adType) {
		this.adType = adType;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public Integer getAdsSubType() {
		return adsSubType;
	}
	public void setAdsSubType(Integer adsSubType) {
		this.adsSubType = adsSubType;
	}
	public Integer getFixedFormat() {
		return fixedFormat;
	}
	public void setFixedFormat(Integer fixedFormat) {
		this.fixedFormat = fixedFormat;
	}
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public Integer getBookingOffice() {
		return bookingOffice;
	}
	public void setBookingOffice(Integer bookingOffice) {
		this.bookingOffice = bookingOffice;
	}
	public Double getRangeFrom() {
		return rangeFrom;
	}
	public void setRangeFrom(Double rangeFrom) {
		this.rangeFrom = rangeFrom;
	}
	public Double getRangeTo() {
		return rangeTo;
	}
	public void setRangeTo(Double rangeTo) {
		this.rangeTo = rangeTo;
	}
	public Integer getApproverUserId() {
		return approverUserId;
	}
	public void setApproverUserId(Integer approverUserId) {
		this.approverUserId = approverUserId;
	}
	public Integer getApproverCCUserID() {
		return approverCCUserID;
	}
	public void setApproverCCUserID(Integer approverCCUserID) {
		this.approverCCUserID = approverCCUserID;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getEditionTypeId() {
		return editionTypeId;
	}
	public void setEditionTypeId(Integer editionTypeId) {
		this.editionTypeId = editionTypeId;
	}
	
	
}
