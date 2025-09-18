package com.portal.common.service;

import com.portal.common.models.DocumentsModel;
import com.portal.common.models.GenericApiResponse;

public interface DocumentService {

	public GenericApiResponse uploadAdditionalDocuments(DocumentsModel documentsModel);
	public GenericApiResponse updateUploadedDocuments(DocumentsModel fieldFixCommonModel);
	public GenericApiResponse removeUploadedDocuments(DocumentsModel fieldFixCommonModel);
	public DocumentsModel downloadAttachments(DocumentsModel documentsModel);
	public DocumentsModel downloadAttachmentsZip(DocumentsModel documentsModel);
	public GenericApiResponse uploadUserProfile(DocumentsModel documentsModel);
	public DocumentsModel downloadUserProfile(String profileId);
	public DocumentsModel downloadPdfAttachments(DocumentsModel documentsModel);
}
