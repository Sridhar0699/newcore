package com.portal.nm.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NmInboxPushPayload {

	@JsonProperty("objectType")
	private String objectType;
	@JsonProperty("objectKey")
	private String objectKey;
	@JsonProperty("objectFrom")
	private String objectFrom;
	@JsonProperty("reqFromType")
	private String reqFromType;
	@JsonProperty("reqToType")
	private String reqToType;
	@JsonProperty("reqDate")
	private Date reqDate;
	@JsonProperty("tags")
	private String tags;
	@JsonProperty("subject")
	private String subject;
	@JsonProperty("status")
	private Integer status;
	@JsonProperty("comments")
	private String comments;
	@JsonProperty("refFromId")
	private Integer refFromId;
	@JsonProperty("refToId")
	private Integer refToId;
	@JsonProperty("recUserId")
	private Integer recUserId;
	@JsonProperty("smType")
	private String smType;

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public String getObjectFrom() {
		return objectFrom;
	}

	public void setObjectFrom(String objectFrom) {
		this.objectFrom = objectFrom;
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getSmType() {
		return smType;
	}

	public void setSmType(String smType) {
		this.smType = smType;
	}
	
}
