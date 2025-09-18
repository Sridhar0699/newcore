package com.portal.common.service;

import java.util.List;
import java.util.Map;

import com.portal.common.models.DocumentsModel;
import com.portal.doc.entity.Attachments;

public interface DocumentsDao {

	public Map<String, String> saveUploadedDocuments(DocumentsModel documentsModel);
	public boolean updateUploadedDocTypes(DocumentsModel documentsModel);
	public List<String> getDocumentsForDelete(DocumentsModel documentsModel);
	public List<Attachments> getAttachment(DocumentsModel documentsModel);
	public List<Attachments> getMultipleAttachment(DocumentsModel documentsModel);
}
