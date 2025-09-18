package com.portal.rms.model;

import java.util.List;

public class RmsListViewModel {

	private String customerDetails;
	private String clientCode;
	private String gstNo;
	private String clientName;
	private String emailId;
	private String mobileNo;;
	private String address1;
	private String pinCode;
	private String stateDesc;
	private String cityDesc;

	private String cityCode;
	private String stateCode;
	private Integer customerTypeId;
	private String mobileAlt;
	private String houseNo;
	private String bookingDescription;
	private String content;
	private String spaceWidth;
	private String spaceHeight;
	private String ratePerSquareCms;
	private Integer pagePosition;
	private List<String> publishDates;
//	private BigDecimal fixedRate;
//	private BigDecimal paidAmount;

	private String classifiedAdsSubTypeStr;
	private String itemId;
	private List<String> editions;
	private String schemeStr;

	private String categoryGroupDesc;
	private String categorySubgroupDesc;
	private String categoryChildgroupDesc;

	private String editionTypeDesc;

	private String fixedFormatsDesc;
	private String formatTypeDesc;
	private String pageNumberDesc;
	private String categoryDiscountId;
	private String additionalDiscount;
//	private String surchargeRate;

//	private String cgst;
//	private String sgst;
//	private String igst;
	private Integer bookingCode;

//	private Integer customerTypeId;

	private Integer categoryGroup;
	private Integer categorySubGroup;
	private Integer categoryChildGroup;

	private Integer editionType;

	private Integer fixedFormat;
	private Integer formatType;
	private Integer pageNumber;
	private String multiDiscount;

	private String categoryDiscount;
	private String positioningDesc;

	private String additionalDiscountAmount;
//	private String surchargeAmount;
	private String multiDiscountAmount;
	private String categoryDiscountAmount;

	private Integer classifiedAdsSubType;
	private Integer scheme;
	
	private Integer loggedUserId;

//	private String igstValue;
//	private String cgstValue;
//	private String sgstValue;
//	private String amount;
//	private String totalAmount;
	private Integer classifiedType;
	private String classifiedTypeDesc;
	private String rmsItemId;
	private String baseAmount;
//	private String grandTotal;
//	private String discountTotal;
//	private String gstTotal;
	private String premiumTotal;
	private String premiumDiscountAmount;
	private String pagePositionDesc;
	private String status;
	private String orderId;
	private String paymentStatus;
	private List<Integer> editionIds;
	private Integer addType;
	private String addTypeDesc;
	private String caption;
//	private String mobileAlt;
//	private String houseNo;
//	private List<Integer> discounts;
	private List<String> discountsDesc;
	private String adId;
	private String premiumDiscount;
	private String edition;
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
    private Double amount;
	
	private Double grandTotal;
	private Double discountTotal;
	private List<RmsTaxModel> tax;
	private List<RmsDiscountModel> discounts;
	private Double gstTotal;
	
	private String grandTotalString;
	private String discountTotalString;
	private String gstTotalString;
	private String amountString;
	private String publishDate;
	private String approvalLevels;
	private List<ApprovalDetailsModel> approvalDetailsModels;
	private String wfId;
	private String level1;
	private String status1;
	private String level2;
	private String status2;
	private String level3;
	private String status3;
	private String createdTs;
	public String getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(String customerDetails) {
		this.customerDetails = customerDetails;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public String getCityDesc() {
		return cityDesc;
	}
	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	public String getMobileAlt() {
		return mobileAlt;
	}
	public void setMobileAlt(String mobileAlt) {
		this.mobileAlt = mobileAlt;
	}
	public String getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}
	public String getBookingDescription() {
		return bookingDescription;
	}
	public void setBookingDescription(String bookingDescription) {
		this.bookingDescription = bookingDescription;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSpaceWidth() {
		return spaceWidth;
	}
	public void setSpaceWidth(String spaceWidth) {
		this.spaceWidth = spaceWidth;
	}
	public String getSpaceHeight() {
		return spaceHeight;
	}
	public void setSpaceHeight(String spaceHeight) {
		this.spaceHeight = spaceHeight;
	}
	public String getRatePerSquareCms() {
		return ratePerSquareCms;
	}
	public void setRatePerSquareCms(String ratePerSquareCms) {
		this.ratePerSquareCms = ratePerSquareCms;
	}
	public Integer getPagePosition() {
		return pagePosition;
	}
	public void setPagePosition(Integer pagePosition) {
		this.pagePosition = pagePosition;
	}
	public List<String> getPublishDates() {
		return publishDates;
	}
	public void setPublishDates(List<String> publishDates) {
		this.publishDates = publishDates;
	}
	public String getClassifiedAdsSubTypeStr() {
		return classifiedAdsSubTypeStr;
	}
	public void setClassifiedAdsSubTypeStr(String classifiedAdsSubTypeStr) {
		this.classifiedAdsSubTypeStr = classifiedAdsSubTypeStr;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public List<String> getEditions() {
		return editions;
	}
	public void setEditions(List<String> editions) {
		this.editions = editions;
	}
	public String getSchemeStr() {
		return schemeStr;
	}
	public void setSchemeStr(String schemeStr) {
		this.schemeStr = schemeStr;
	}
	public String getCategoryGroupDesc() {
		return categoryGroupDesc;
	}
	public void setCategoryGroupDesc(String categoryGroupDesc) {
		this.categoryGroupDesc = categoryGroupDesc;
	}
	public String getCategorySubgroupDesc() {
		return categorySubgroupDesc;
	}
	public void setCategorySubgroupDesc(String categorySubgroupDesc) {
		this.categorySubgroupDesc = categorySubgroupDesc;
	}
	public String getCategoryChildgroupDesc() {
		return categoryChildgroupDesc;
	}
	public void setCategoryChildgroupDesc(String categoryChildgroupDesc) {
		this.categoryChildgroupDesc = categoryChildgroupDesc;
	}
	public String getEditionTypeDesc() {
		return editionTypeDesc;
	}
	public void setEditionTypeDesc(String editionTypeDesc) {
		this.editionTypeDesc = editionTypeDesc;
	}
	public String getFixedFormatsDesc() {
		return fixedFormatsDesc;
	}
	public void setFixedFormatsDesc(String fixedFormatsDesc) {
		this.fixedFormatsDesc = fixedFormatsDesc;
	}
	public String getFormatTypeDesc() {
		return formatTypeDesc;
	}
	public void setFormatTypeDesc(String formatTypeDesc) {
		this.formatTypeDesc = formatTypeDesc;
	}
	public String getPageNumberDesc() {
		return pageNumberDesc;
	}
	public void setPageNumberDesc(String pageNumberDesc) {
		this.pageNumberDesc = pageNumberDesc;
	}
	public String getCategoryDiscountId() {
		return categoryDiscountId;
	}
	public void setCategoryDiscountId(String categoryDiscountId) {
		this.categoryDiscountId = categoryDiscountId;
	}
	public String getAdditionalDiscount() {
		return additionalDiscount;
	}
	public void setAdditionalDiscount(String additionalDiscount) {
		this.additionalDiscount = additionalDiscount;
	}
	public Integer getBookingCode() {
		return bookingCode;
	}
	public void setBookingCode(Integer bookingCode) {
		this.bookingCode = bookingCode;
	}
	public Integer getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(Integer customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public Integer getCategoryGroup() {
		return categoryGroup;
	}
	public void setCategoryGroup(Integer categoryGroup) {
		this.categoryGroup = categoryGroup;
	}
	public Integer getCategorySubGroup() {
		return categorySubGroup;
	}
	public void setCategorySubGroup(Integer categorySubGroup) {
		this.categorySubGroup = categorySubGroup;
	}
	public Integer getCategoryChildGroup() {
		return categoryChildGroup;
	}
	public void setCategoryChildGroup(Integer categoryChildGroup) {
		this.categoryChildGroup = categoryChildGroup;
	}
	public Integer getEditionType() {
		return editionType;
	}
	public void setEditionType(Integer editionType) {
		this.editionType = editionType;
	}
	public Integer getFixedFormat() {
		return fixedFormat;
	}
	public void setFixedFormat(Integer fixedFormat) {
		this.fixedFormat = fixedFormat;
	}
	public Integer getFormatType() {
		return formatType;
	}
	public void setFormatType(Integer formatType) {
		this.formatType = formatType;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getMultiDiscount() {
		return multiDiscount;
	}
	public void setMultiDiscount(String multiDiscount) {
		this.multiDiscount = multiDiscount;
	}
	public String getCategoryDiscount() {
		return categoryDiscount;
	}
	public void setCategoryDiscount(String categoryDiscount) {
		this.categoryDiscount = categoryDiscount;
	}
	public String getPositioningDesc() {
		return positioningDesc;
	}
	public void setPositioningDesc(String positioningDesc) {
		this.positioningDesc = positioningDesc;
	}
	public String getAdditionalDiscountAmount() {
		return additionalDiscountAmount;
	}
	public void setAdditionalDiscountAmount(String additionalDiscountAmount) {
		this.additionalDiscountAmount = additionalDiscountAmount;
	}
	public String getMultiDiscountAmount() {
		return multiDiscountAmount;
	}
	public void setMultiDiscountAmount(String multiDiscountAmount) {
		this.multiDiscountAmount = multiDiscountAmount;
	}
	public String getCategoryDiscountAmount() {
		return categoryDiscountAmount;
	}
	public void setCategoryDiscountAmount(String categoryDiscountAmount) {
		this.categoryDiscountAmount = categoryDiscountAmount;
	}
	public Integer getClassifiedAdsSubType() {
		return classifiedAdsSubType;
	}
	public void setClassifiedAdsSubType(Integer classifiedAdsSubType) {
		this.classifiedAdsSubType = classifiedAdsSubType;
	}
	public Integer getScheme() {
		return scheme;
	}
	public void setScheme(Integer scheme) {
		this.scheme = scheme;
	}
	public Integer getClassifiedType() {
		return classifiedType;
	}
	public void setClassifiedType(Integer classifiedType) {
		this.classifiedType = classifiedType;
	}
	public String getClassifiedTypeDesc() {
		return classifiedTypeDesc;
	}
	public void setClassifiedTypeDesc(String classifiedTypeDesc) {
		this.classifiedTypeDesc = classifiedTypeDesc;
	}
	public String getRmsItemId() {
		return rmsItemId;
	}
	public void setRmsItemId(String rmsItemId) {
		this.rmsItemId = rmsItemId;
	}
	public String getBaseAmount() {
		return baseAmount;
	}
	public void setBaseAmount(String baseAmount) {
		this.baseAmount = baseAmount;
	}
	public String getPremiumTotal() {
		return premiumTotal;
	}
	public void setPremiumTotal(String premiumTotal) {
		this.premiumTotal = premiumTotal;
	}
	public String getPremiumDiscountAmount() {
		return premiumDiscountAmount;
	}
	public void setPremiumDiscountAmount(String premiumDiscountAmount) {
		this.premiumDiscountAmount = premiumDiscountAmount;
	}
	public String getPagePositionDesc() {
		return pagePositionDesc;
	}
	public void setPagePositionDesc(String pagePositionDesc) {
		this.pagePositionDesc = pagePositionDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public List<Integer> getEditionIds() {
		return editionIds;
	}
	public void setEditionIds(List<Integer> editionIds) {
		this.editionIds = editionIds;
	}
	public Integer getAddType() {
		return addType;
	}
	public void setAddType(Integer addType) {
		this.addType = addType;
	}
	public String getAddTypeDesc() {
		return addTypeDesc;
	}
	public void setAddTypeDesc(String addTypeDesc) {
		this.addTypeDesc = addTypeDesc;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public List<String> getDiscountsDesc() {
		return discountsDesc;
	}
	public void setDiscountsDesc(List<String> discountsDesc) {
		this.discountsDesc = discountsDesc;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getPremiumDiscount() {
		return premiumDiscount;
	}
	public void setPremiumDiscount(String premiumDiscount) {
		this.premiumDiscount = premiumDiscount;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
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
	public RmsKycAttatchments getAttatchments() {
		return attatchments;
	}
	public void setAttatchments(RmsKycAttatchments attatchments) {
		this.attatchments = attatchments;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public Double getDiscountTotal() {
		return discountTotal;
	}
	public void setDiscountTotal(Double discountTotal) {
		this.discountTotal = discountTotal;
	}
	public List<RmsTaxModel> getTax() {
		return tax;
	}
	public void setTax(List<RmsTaxModel> tax) {
		this.tax = tax;
	}
	public List<RmsDiscountModel> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(List<RmsDiscountModel> discounts) {
		this.discounts = discounts;
	}
	public Double getGstTotal() {
		return gstTotal;
	}
	public void setGstTotal(Double gstTotal) {
		this.gstTotal = gstTotal;
	}
	public String getGrandTotalString() {
		return grandTotalString;
	}
	public void setGrandTotalString(String grandTotalString) {
		this.grandTotalString = grandTotalString;
	}
	public String getDiscountTotalString() {
		return discountTotalString;
	}
	public void setDiscountTotalString(String discountTotalString) {
		this.discountTotalString = discountTotalString;
	}
	public String getGstTotalString() {
		return gstTotalString;
	}
	public void setGstTotalString(String gstTotalString) {
		this.gstTotalString = gstTotalString;
	}
	public String getAmountString() {
		return amountString;
	}
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getApprovalLevels() {
		return approvalLevels;
	}
	public void setApprovalLevels(String approvalLevels) {
		this.approvalLevels = approvalLevels;
	}
	public List<ApprovalDetailsModel> getApprovalDetailsModels() {
		return approvalDetailsModels;
	}
	public void setApprovalDetailsModels(List<ApprovalDetailsModel> approvalDetailsModels) {
		this.approvalDetailsModels = approvalDetailsModels;
	}
	public Integer getLoggedUserId() {
		return loggedUserId;
	}
	public void setLoggedUserId(Integer loggedUserId) {
		this.loggedUserId = loggedUserId;
	}
	public String getWfId() {
		return wfId;
	}
	public void setWfId(String wfId) {
		this.wfId = wfId;
	}
	public String getLevel1() {
		return level1;
	}
	public void setLevel1(String level1) {
		this.level1 = level1;
	}
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getLevel2() {
		return level2;
	}
	public void setLevel2(String level2) {
		this.level2 = level2;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getLevel3() {
		return level3;
	}
	public void setLevel3(String level3) {
		this.level3 = level3;
	}
	public String getStatus3() {
		return status3;
	}
	public void setStatus3(String status3) {
		this.status3 = status3;
	}
	public String getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}
	
	
	
}
