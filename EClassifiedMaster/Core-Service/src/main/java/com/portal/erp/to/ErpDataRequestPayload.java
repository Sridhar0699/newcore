package com.portal.erp.to;

import java.util.List;
import java.util.Map;

import com.portal.clf.models.ErpClassifieds;

public class ErpDataRequestPayload {

private String orgId;
	
	private String orgOpuId;
	
	private String serviceType;

	private String action;
	
	private String dataFormat;

	private List<Map<String, Object>> requestData1 = null;
	
	private ErpClassifieds  requestData = null;

	private String reqFrom;
	
	private String userId;
	
	private String userSecret;
	
	private Integer dlKeyAct;
	
	private String reqDataAction;
	
	private String ftpFilePath;
	
	private String fileTrackingId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgOpuId() {
		return orgOpuId;
	}

	public void setOrgOpuId(String orgOpuId) {
		this.orgOpuId = orgOpuId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getReqFrom() {
		return reqFrom;
	}

	public void setReqFrom(String reqFrom) {
		this.reqFrom = reqFrom;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserSecret() {
		return userSecret;
	}

	public void setUserSecret(String userSecret) {
		this.userSecret = userSecret;
	}

	public Integer getDlKeyAct() {
		return dlKeyAct;
	}

	public void setDlKeyAct(Integer dlKeyAct) {
		this.dlKeyAct = dlKeyAct;
	}

	public String getReqDataAction() {
		return reqDataAction;
	}

	public void setReqDataAction(String reqDataAction) {
		this.reqDataAction = reqDataAction;
	}

	public String getFtpFilePath() {
		return ftpFilePath;
	}

	public void setFtpFilePath(String ftpFilePath) {
		this.ftpFilePath = ftpFilePath;
	}

	public String getFileTrackingId() {
		return fileTrackingId;
	}

	public void setFileTrackingId(String fileTrackingId) {
		this.fileTrackingId = fileTrackingId;
	}

	public List<Map<String, Object>> getRequestData1() {
		return requestData1;
	}

	public void setRequestData1(List<Map<String, Object>> requestData1) {
		this.requestData1 = requestData1;
	}

	public ErpClassifieds getRequestData() {
		return requestData;
	}

	public void setRequestData(ErpClassifieds requestData) {
		this.requestData = requestData;
	}
	
}
