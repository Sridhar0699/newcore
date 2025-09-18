package com.portal.common.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.portal.common.models.DocumentsModel;
import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.DocumentService;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;


@RestController
public class DocumentsApiController implements DocumentApi{
	
	private static final Logger logger = LogManager.getLogger(DocumentsApiController.class);
	
	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired 
	private DocumentService documentService;

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<?> uploadRmsDocUpload(HttpServletRequest request) {
		String METHOD_NAME = "amDocsUpload";

        logger.info("Entered into the method: " + METHOD_NAME);
        Date respFrmTs = new Date();
        ResponseEntity<GenericApiResponse> respObj = null;
        LoggedUser loggedUser = userContext.getLoggedUser();
        GenericApiResponse apiResp = new GenericApiResponse();
        DocumentsModel rmsDocumentsModel = new DocumentsModel();
//        rmsDocumentsModel.setDocIds(docId);
        rmsDocumentsModel.setLoggedUser(loggedUser);
//        rmsDocumentsModel.setDocType(attType);
//        rmsDocumentsModel.setDealerAchDataId(achievementId);
//        rmsDocumentsModel.setDealerAuditId(dealerAuditId);

                if (request instanceof MultipartHttpServletRequest) {
                    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                    // Taking the Multiple files from upload document request
                    List<MultipartFile> uploadMultipleFiles = multipartHttpServletRequest.getFiles("files");
                    logger.info("Number of documnets requested to upload: " + uploadMultipleFiles.size());
                    if (uploadMultipleFiles != null && !uploadMultipleFiles.isEmpty()) {
                    	rmsDocumentsModel.setMultipartFileAtachments(uploadMultipleFiles);
                    }else {
                    	apiResp.setMessage("File Not Found");
                    	respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
                    	return respObj;
                    }
                }
                apiResp = documentService.uploadAdditionalDocuments(rmsDocumentsModel);
//                if (action == 1) {
//                    apiResp = documentService.uploadAdditionalDocuments(auditDocumentsModel);
//                } else if (action == 2) {
//                    apiResp = auditDocumentService.updateUploadedDocuments(auditDocumentsModel);
//                } else if (action == 5) {
//                    apiResp = auditDocumentService.uploadUserProfile(auditDocumentsModel);
//                } else {
//                    apiResp = auditDocumentService.removeUploadedDocuments(auditDocumentsModel);
//                }

        respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

        logger.info("Exit from the method: " + METHOD_NAME);

        return respObj;
	}

	@Override
	public ResponseEntity<?> downloadView(@NotNull String docId, HttpServletRequest request,
			HttpServletResponse response) {
		String METHOD_NAME = "downloadAttachments";
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		logger.info("Entered into the method: " + METHOD_NAME);

		LoggedUser loggedUser = userContext.getLoggedUser();

		DocumentsModel documentsModel = new DocumentsModel();
		documentsModel.setLoggedUser(loggedUser);
		List<String> docIds = new ArrayList<>();
		docIds.add(docId);
		documentsModel.setDocIds(docIds);
		documentsModel = documentService.downloadAttachments(documentsModel);
		if (documentsModel.getResource() == null) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Document not available, Please contact support team");
		} else {
			try {
				byte[] sourceBytes = IOUtils.toByteArray(documentsModel.getResource().getInputStream());
				genericApiResponse.setData(Base64.getEncoder().encodeToString(sourceBytes)); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<GenericApiResponse>(genericApiResponse,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> downloadAttachments(@NotNull List<String> docIds, HttpServletRequest request,
			HttpServletResponse response) {
		String METHOD_NAME = "downloadAttachments";

		logger.info("Entered into the method: " + METHOD_NAME);

		LoggedUser loggedUser = userContext.getLoggedUser();

		DocumentsModel documentsModel = new DocumentsModel();
		documentsModel.setLoggedUser(loggedUser);
		documentsModel.setDocIds(docIds);
		try {
			if (docIds.size() == 1) {
				documentsModel = documentService.downloadAttachments(documentsModel);
				if (documentsModel.getResource() == null) {
					return new ResponseEntity<String>("Document not available, Please contact support team", HttpStatus.OK);
				}
				documentsModel.setContentType(request.getServletContext()
						.getMimeType(documentsModel.getResource().getFile().getAbsolutePath()));
				response.setHeader("Content-type", documentsModel.getContentType());
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + documentsModel.getFileName() + "\"");
				response.setHeader("Content-Length",
						String.valueOf(documentsModel.getResource().getInputStream().available()));
				FileCopyUtils.copy(documentsModel.getResource().getInputStream(), response.getOutputStream());
			} else {
				documentsModel.setResponse(response);
				documentsModel = documentService.downloadAttachmentsZip(documentsModel);
				response.setHeader("Content-type", documentsModel.getContentType());
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + documentsModel.getFileName() + "\"");
				response.setHeader("Content-Length", String.valueOf(documentsModel.getBos().size()));
				response.getOutputStream().write(documentsModel.getBos().toByteArray());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> downloadPdfDocView(@NotNull String docId, HttpServletRequest request,
			HttpServletResponse response) {
		String METHOD_NAME = "downloadPdfDocView";
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		genericApiResponse.setStatus(0);
		logger.info("Entered into the method: " + METHOD_NAME);

		LoggedUser loggedUser = userContext.getLoggedUser();

		DocumentsModel documentsModel = new DocumentsModel();
		documentsModel.setLoggedUser(loggedUser);
		List<String> docIds = new ArrayList<>();
		docIds.add(docId);
		documentsModel.setDocIds(docIds);
		documentsModel = documentService.downloadPdfAttachments(documentsModel);
		if (documentsModel.getResource() == null) {
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage("Document not available, Please contact support team");
		} else {
			try {
				byte[] sourceBytes = IOUtils.toByteArray(documentsModel.getResource().getInputStream());
				genericApiResponse.setData(Base64.getEncoder().encodeToString(sourceBytes)); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<GenericApiResponse>(genericApiResponse,HttpStatus.OK);
	}
	
}
