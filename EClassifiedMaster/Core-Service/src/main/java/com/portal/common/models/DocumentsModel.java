package com.portal.common.models;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.portal.doc.entity.Attachments;
import com.portal.security.model.LoggedUser;

public class DocumentsModel {

	private List<MultipartFile> multipartFileAtachments;
	private List<Attachments> attachments;
	private Integer docType;
	private LoggedUser loggedUser;
	private boolean clearTmpData;
	private List<String> docIds;
	private List<String> attIds;
	private Integer attType;
	private String contentType;
	private Resource resource;
	private String fileName;
	private HttpServletResponse response;
	private ByteArrayOutputStream bos;
	private Integer userId;
	private String refObjId;
	private Document document;	
	private String orderId;

	public List<MultipartFile> getMultipartFileAtachments() {
		return multipartFileAtachments;
	}

	public void setMultipartFileAtachments(List<MultipartFile> multipartFileAtachments) {
		this.multipartFileAtachments = multipartFileAtachments;
	}

	public List<Attachments> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachments> attachments) {
		this.attachments = attachments;
	}

	public Integer getDocType() {
		return docType;
	}

	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public boolean isClearTmpData() {
		return clearTmpData;
	}

	public void setClearTmpData(boolean clearTmpData) {
		this.clearTmpData = clearTmpData;
	}

	public List<String> getDocIds() {
		return docIds;
	}

	public void setDocIds(List<String> docIds) {
		this.docIds = docIds;
	}

	public List<String> getAttIds() {
		return attIds;
	}

	public void setAttIds(List<String> attIds) {
		this.attIds = attIds;
	}

	public Integer getAttType() {
		return attType;
	}

	public void setAttType(Integer attType) {
		this.attType = attType;
	}

	public String getRefObjId() {
		return refObjId;
	}

	public void setRefObjId(String refObjId) {
		this.refObjId = refObjId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ByteArrayOutputStream getBos() {
		return bos;
	}

	public void setBos(ByteArrayOutputStream bos) {
		this.bos = bos;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	
}
