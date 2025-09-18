package com.portal.hm.model;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.portal.hm.entities.HmManuals;
import com.portal.security.model.LoggedUser;

public class HMDaoCommonModel {

	private LoggedUser loggedUser;
	private List<HmManuals> helpManual;
	private String manualType;
	private String manualId;
	private List<String> utilParams;
	private HttpServletResponse response;
	private ByteArrayInputStream byteArrayInputStream;
	private String fileName;
	private String fileUrl;
	private String roleId;
	private String notificationRole;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public List<HmManuals> getHelpManual() {
		return helpManual;
	}

	public void setHelpManual(List<HmManuals> helpManual) {
		this.helpManual = helpManual;
	}

	public String getManualType() {
		return manualType;
	}

	public void setManualType(String manualType) {
		this.manualType = manualType;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public List<String> getUtilParams() {
		return utilParams;
	}

	public void setUtilParams(List<String> utilParams) {
		this.utilParams = utilParams;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ByteArrayInputStream getByteArrayInputStream() {
		return byteArrayInputStream;
	}

	public void setByteArrayInputStream(ByteArrayInputStream byteArrayInputStream) {
		this.byteArrayInputStream = byteArrayInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getNotificationRole() {
		return notificationRole;
	}

	public void setNotificationRole(String notificationRole) {
		this.notificationRole = notificationRole;
	}

}
