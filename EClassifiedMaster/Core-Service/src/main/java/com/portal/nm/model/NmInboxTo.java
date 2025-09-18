package com.portal.nm.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NmInboxTo {

	@JsonProperty("wfItemId")
	private Integer wfItemId;
	@JsonProperty("tags")
	private String tags;
	@JsonProperty("subject")
	private String subject;
	@JsonProperty("status")
	private Integer status;
	@JsonProperty("reqToType")
	private String reqToType;
	@JsonProperty("reqFromType")
	private String reqFromType;
	@JsonProperty("reqDate")
	private Date reqDate;
	@JsonProperty("objectType")
	private String objectType;
	@JsonProperty("objectKey")
	private String objectKey;
	@JsonProperty("objectFrom")
	private String objectFrom;
	@JsonProperty("markAsDelete")
	private Integer markAsDelete;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.S'Z'", shape = JsonFormat.Shape.STRING, timezone = "Asia/Kolkata")
	@JsonProperty("createdTs")
	private Date createdTs;
	@JsonProperty("createdBy")
	private String createdBy;
	@JsonProperty("comments")
	private String comments;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.S'Z'", shape = JsonFormat.Shape.STRING, timezone = "Asia/Kolkata")
	@JsonProperty("changedTs")
	private Date changedTs;
	@JsonProperty("changedBy")
	private String changedBy;
	@JsonProperty("readStatus")
	private String readStatus;
	@JsonProperty("refFromId")
	private Integer refFromId;
	@JsonProperty("refToId")
	private Integer refToId;
	@JsonProperty("recUserId")
	private Integer recUserId;

	public Integer getWfItemId() {
		return wfItemId;
	}

	public void setWfItemId(Integer wfItemId) {
		this.wfItemId = wfItemId;
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

	public String getReqToType() {
		return reqToType;
	}

	public void setReqToType(String reqToType) {
		this.reqToType = reqToType;
	}

	public String getReqFromType() {
		return reqFromType;
	}

	public void setReqFromType(String reqFromType) {
		this.reqFromType = reqFromType;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

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

	public Integer getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Integer markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
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
