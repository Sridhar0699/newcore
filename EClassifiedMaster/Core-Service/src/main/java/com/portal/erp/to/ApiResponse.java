package com.portal.erp.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
	
	@JsonProperty("errorCode")
	private String errorCode = null;

	@JsonProperty("errorInf")
	private String errorInf = null;

	@JsonProperty("status")
	private Boolean status = null;

	@JsonProperty("message")
	private String message = null;

	@JsonProperty("filePath")
	private String filePath = null;
	
	@JsonProperty("bpId")
	private String bpId = null;
	
	@JsonProperty("request")
	private String request = null;
	
	@JsonProperty("uploadedBy")
	private String uploadedBy = null;
	
	@JsonProperty("seviceType")
	private String seviceType = null;
	
	@JsonProperty("accessTokenResponse")
	private String accessTokenResponse = null;
	
//	@JsonProperty("ftpFileLogs")
//	private FTPFilesLogs ftpFileLogs = null;
	
	@JsonProperty("errorFilePath")
	private String errorFilePath = null;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInf() {
		return errorInf;
	}

	public void setErrorInf(String errorInf) {
		this.errorInf = errorInf;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getBpId() {
		return bpId;
	}

	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public String getSeviceType() {
		return seviceType;
	}

	public void setSeviceType(String seviceType) {
		this.seviceType = seviceType;
	}

	public String getAccessTokenResponse() {
		return accessTokenResponse;
	}

	public void setAccessTokenResponse(String accessTokenResponse) {
		this.accessTokenResponse = accessTokenResponse;
	}

//	public FTPFilesLogs getFtpFileLogs() {
//		return ftpFileLogs;
//	}
//
//	public void setFtpFileLogs(FTPFilesLogs ftpFileLogs) {
//		this.ftpFileLogs = ftpFileLogs;
//	}

	public String getErrorFilePath() {
		return errorFilePath;
	}

	public void setErrorFilePath(String errorFilePath) {
		this.errorFilePath = errorFilePath;
	}

}
