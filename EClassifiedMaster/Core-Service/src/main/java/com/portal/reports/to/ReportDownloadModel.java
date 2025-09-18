package com.portal.reports.to;

import java.io.ByteArrayOutputStream;

import org.springframework.core.io.Resource;

public class ReportDownloadModel {

	private Resource resource;
	private String fileName;
	private String contentType;
	private ByteArrayOutputStream bos;
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
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public ByteArrayOutputStream getBos() {
		return bos;
	}
	public void setBos(ByteArrayOutputStream bos) {
		this.bos = bos;
	}
	
}
