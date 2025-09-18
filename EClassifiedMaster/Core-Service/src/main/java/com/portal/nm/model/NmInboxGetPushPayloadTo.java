package com.portal.nm.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NmInboxGetPushPayloadTo {

	@JsonProperty("reqFromType")
	private String reqFromType;
	@JsonProperty("reqToType")
	private String reqToType;
	@JsonProperty("reqDate")
	private Date reqDate;
	@JsonProperty("refFromId")
	private Integer refFromId;
	@JsonProperty("refToId")
	private Integer refToId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("readStatus")
	private String readStatus;
	@JsonProperty("recUserId")
	private Integer recUserId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	public String getReqFromType() {
		return reqFromType;
	}

	public void setReqFromType(String reqFromType) {
		this.reqFromType = reqFromType;
	}

	public String getReqToType() {
		return reqToType;
	}

	public void setReqToType(String reqToType) {
		this.reqToType = reqToType;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public Integer getRefFromId() {
		return refFromId;
	}

	public void setRefFromId(Integer refFromId) {
		this.refFromId = refFromId;
	}

	public Integer getRefToId() {
		return refToId;
	}

	public void setRefToId(Integer refToId) {
		this.refToId = refToId;
	}

	public Integer getRecUserId() {
		return recUserId;
	}

	public void setRecUserId(Integer recUserId) {
		this.recUserId = recUserId;
	}
}
