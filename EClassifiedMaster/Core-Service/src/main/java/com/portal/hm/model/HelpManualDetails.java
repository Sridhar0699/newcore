package com.portal.hm.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portal.security.model.LoggedUser;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HelpManualDetails {

	@JsonProperty("users")
	private List<UserType> allowedUser;

	@JsonProperty("manual_type")
	private String helpManualType;

	@JsonProperty("version_no")
	private String versionNo;

	@JsonProperty("mark_as_delete")
	private boolean markAsDelete;

	@JsonProperty("hm_attachments")
	private List<HMAttachments> hmAttachment;

	@JsonProperty("created_ts")
	private String createdTs;

	public String getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}

	public List<HMAttachments> getHmAttachment() {
		return hmAttachment;
	}

	public void setHmAttachment(List<HMAttachments> hmAttachment) {
		this.hmAttachment = hmAttachment;
	}

	public boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public List<UserType> getAllowedUser() {
		return allowedUser;
	}

	public void setAllowedUser(List<UserType> allowedUser) {
		this.allowedUser = allowedUser;
	}

	public String getHelpManualType() {
		return helpManualType;
	}

	public void setHelpManualType(String helpManualType) {
		this.helpManualType = helpManualType;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	@JsonIgnore
	private LoggedUser loggedUser;

	@JsonIgnore
	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	@JsonIgnore
	List<MultipartFile> manualsAttached;

	@JsonIgnore
	public List<MultipartFile> getManualsAttached() {
		return manualsAttached;
	}

	public void setManualsAttached(List<MultipartFile> manualsAttached) {
		this.manualsAttached = manualsAttached;
	}

}
