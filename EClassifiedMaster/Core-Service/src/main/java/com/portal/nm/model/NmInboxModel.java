package com.portal.nm.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "wf_item_id", "tags", "subject", "status", "req_to", "req_from", "req_date", "object_type",
		"object_key", "object_event", "mark_as_delete", "created_ts", "created_by", "comment", "changed_ts",
		"changed_by", "read_status" })
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.S'Z'", shape = JsonFormat.Shape.STRING, timezone = "Asia/Kolkata")
public class NmInboxModel {

	@JsonProperty("wfItemId")
	private String wfItemId;
	@JsonProperty("tags")
	private String tags;
	@JsonProperty("subject")
	private String subject;
	@JsonProperty("status")
	private Integer status;
	@JsonProperty("reqTo")
	private String reqTo;
	@JsonProperty("reqFrom")
	private String reqFrom;
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
	private Long createdBy;
	@JsonProperty("comments")
	private String comments;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.S'Z'", shape = JsonFormat.Shape.STRING, timezone = "Asia/Kolkata")
	@JsonProperty("changedTs")
	private Date changedTs;
	@JsonProperty("changedBy")
	private Long changedBy;
	@JsonProperty("readStatus")
	private String readStatus;

	@JsonIgnore
	@JsonProperty("branchId")
	private String branchId;
	@JsonIgnore
	@JsonProperty("orgId")
	private String orgId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("wf_item_id")
	public String getWfItemId() {
		return wfItemId;
	}

	@JsonProperty("wf_item_id")
	public void setWfItemId(String wfItemId) {
		this.wfItemId = wfItemId;
	}

	@JsonProperty("tags")
	public String getTags() {
		return tags;
	}

	@JsonProperty("tags")
	public void setTags(String tags) {
		this.tags = tags;
	}

	@JsonProperty("subject")
	public String getSubject() {
		return subject;
	}

	@JsonProperty("subject")
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@JsonProperty("status")
	public Integer getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonProperty("req_to")
	public String getReqTo() {
		return reqTo;
	}

	@JsonProperty("req_to")
	public void setReqTo(String reqTo) {
		this.reqTo = reqTo;
	}

	@JsonProperty("req_from")
	public String getReqFrom() {
		return reqFrom;
	}

	@JsonProperty("req_from")
	public void setReqFrom(String reqFrom) {
		this.reqFrom = reqFrom;
	}

	@JsonProperty("req_date")
	public Date getReqDate() {
		return reqDate;
	}

	@JsonProperty("req_date")
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	@JsonProperty("object_type")
	public String getObjectType() {
		return objectType;
	}

	@JsonProperty("object_type")
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@JsonProperty("object_key")
	public String getObjectKey() {
		return objectKey;
	}

	@JsonProperty("object_key")
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	@JsonProperty("object_from")
	public String getObjectFrom() {
		return objectFrom;
	}

	@JsonProperty("object_from")
	public void setObjectFrom(String objectFrom) {
		this.objectFrom = objectFrom;
	}

	@JsonProperty("mark_as_delete")
	public Integer getMarkAsDelete() {
		return markAsDelete;
	}

	@JsonProperty("mark_as_delete")
	public void setMarkAsDelete(Integer markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	@JsonProperty("created_ts")
	public Date getCreatedTs() {
		return createdTs;
	}

	@JsonProperty("created_ts")
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	@JsonProperty("created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	@JsonProperty("created_by")
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@JsonProperty("comments")
	public String getComments() {
		return comments;
	}

	@JsonProperty("comments")
	public void setComments(String comments) {
		this.comments = comments;
	}

	@JsonProperty("changed_ts")
	public Date getChangedTs() {
		return changedTs;
	}

	@JsonProperty("changed_ts")
	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	@JsonProperty("changed_by")
	public Long getChangedBy() {
		return changedBy;
	}

	@JsonProperty("changed_by")
	public void setChangedBy(Long changedBy) {
		this.changedBy = changedBy;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@JsonProperty("read_status")
	public String getReadStatus() {
		return readStatus;
	}

	@JsonProperty("read_status")
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

}