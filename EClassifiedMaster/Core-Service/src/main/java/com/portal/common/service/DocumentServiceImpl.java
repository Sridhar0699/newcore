package com.portal.common.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portal.basedao.IBaseDao;
import com.portal.common.models.AttUploadResponse;
import com.portal.common.models.DocumentsModel;
import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;
import com.portal.doc.entity.Attachments;
import com.portal.repository.AttachmentsRepo;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.CommonUtils;
import com.portal.security.util.LoggedUserContext;
import com.portal.user.dao.UserDao;
import com.portal.user.entities.UmUsers;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService{

	private static final Logger logger = LogManager.getLogger(DocumentServiceImpl.class);
	public static String DIR_PATH_DOCS =  "/SEC/DOCS/";
	public static String DIR_PATH_VIN_LIST =  "/SEC/VINERR/";
	public static String DIR_PATH_GD_UPD =  "/SEC/GDUPD/";
	public static String DIR_PATH_RPT =  "/SEC/RPT/";
	public static String DIR_PATH_PDF_DOCS="/SEC/PDFS/";
	
	private Path fileStorageLocation;
	
	@Autowired(required = true)
	private Environment prop;
	
	@Autowired
	private DocumentsDao documentsDao;
	
	@Autowired
	private AttachmentsRepo attachmentsRepo;
	
	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired(required = true)
	private IBaseDao baseDao;
	
	@Autowired
	private UserDao userDao;
	
	public String getDIR_PATH_DOCS() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_DOCS;
	}

	public String getDIR_PATH_VIN_LIST() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_VIN_LIST;
	}

	public String getDIR_PATH_GD_UPD() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_GD_UPD;
	}

	public String getDIR_PATH_RPT() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_RPT;
	}
	
	public  String getDIR_PATH_PDF_DOCS() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH_PDF_DOCS;
	}

	/**
	 * Uploading additional documents through out the Field Fix life cycle like QM, CSHQ , Additional CSHQ documents and other documents
	 */
	@SuppressWarnings("unused")
	public GenericApiResponse uploadAdditionalDocuments(DocumentsModel documentsModel) {
		final String METHOD_NAME = "uploadAdditionalDocuments";
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<Attachments> attachments = new ArrayList<>();
		List<AttUploadResponse> attIdNames = new ArrayList<>();
		String fileName = "";
		LoggedUser loggedUser = new LoggedUser();
		for(MultipartFile att : documentsModel.getMultipartFileAtachments())
		{
			if (!GeneralConstants.ALLOWED_DOC_TYPE.contains(att.getContentType())) {
				genericApiResponse.setMessage("Invalid file format found");
				genericApiResponse.setStatus(0);
				logger.info(METHOD_NAME + " Invalid File format error");
				return genericApiResponse;
			}
		}
		try {
			for (MultipartFile file : documentsModel.getMultipartFileAtachments()) {
				Attachments attachments2 = new Attachments();
				System.out.println("##################################");
				System.out.println("CATALINA PATH:"+System.getProperty("catalina.home"));
				System.out.println("##################################");
				logger.info("CATALINA PATH:"+System.getProperty("catalina.home"));
				if(!file.getOriginalFilename().contains(".")) {
					fileName = file.getOriginalFilename() + "_" + UUID.randomUUID().toString() + file.getOriginalFilename() + ".png";
				}else{
					fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + "_"
						+ UUID.randomUUID().toString() + file.getOriginalFilename().substring(
								file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
				}
				File fileToSave = new File(getDIR_PATH_DOCS() + fileName);
				Files.copy(file.getInputStream(), fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
				attachments2.setAttachName(file.getOriginalFilename());
				attachments2.setSourceType("MANUALUPLOAD");
//				attachments2.setAttachType(documentsModel.getDocType());
				attachments2.setAttachUrl(fileName);
				attachments2.setCreatedBy(documentsModel.getLoggedUser().getUserId());
				attachments2.setCreatedTs(new Date());
				attachments2.setAttachId(UUID.randomUUID().toString());
				if(documentsModel.getOrderId() != null) {
					attachments2.setOrderId(documentsModel.getOrderId());
				}
//				if(documentsModel.getRefObjId() != null)
//					attachments2.setRefObjId(documentsModel.getRefObjId());
				attachmentsRepo.save(attachments2);
				AttUploadResponse attUploadResponse = new AttUploadResponse();
				attUploadResponse.setId(attachments2.getAttachId());
				attUploadResponse.setAttatchUrl(getDIR_PATH_DOCS()+attachments2.getAttachUrl());
				attUploadResponse.setAttName(attachments2.getAttachName());
				attIdNames.add(attUploadResponse);
			}
			genericApiResponse.setStatus(0);
			genericApiResponse.setData(attIdNames);
			genericApiResponse.setMessage("Uploaded Successfully");
		} catch (Exception e) {
			logger.error("Error while uploading additioanl documents file" + e.getMessage());
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage(prop.getProperty("GEN_002"));
			genericApiResponse.setErrorcode("GEN_002");
			genericApiResponse.setData(ExceptionUtils.getStackTrace(e));
		}
		return genericApiResponse;
	}
	
	/**
	 * update the uploaded documents through out the field fix life cycle
	 * @param attachment ID
	 */
	
	public GenericApiResponse updateUploadedDocuments(DocumentsModel fieldFixCommonModel) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		LoggedUser loggedUser = new LoggedUser();
		BeanUtils.copyProperties(fieldFixCommonModel.getLoggedUser(), loggedUser);
		try {
			if (!documentsDao.updateUploadedDocTypes(fieldFixCommonModel)) {
				genericApiResponse.setStatus(0);
				genericApiResponse.setErrorcode("DOC_0001");
				genericApiResponse.setMessage(prop.getProperty("DOC_0001"));
			} else {
				genericApiResponse.setStatus(0);
				genericApiResponse.setMessage(prop.getProperty("DOC_0002"));
			}
		} catch (Exception e) {
			logger.error("Error while removeing file" + e.getMessage());
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage(prop.getProperty("GEN_002"));
			genericApiResponse.setErrorcode("GEN_002");
		}
		return genericApiResponse;
	}
	
	/**
	 * Removing the uploaded documents through out the field fix life cycle
	 * @param attachment ID
	 */
	
	public GenericApiResponse removeUploadedDocuments(DocumentsModel fieldFixCommonModel) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		List<String> files = new ArrayList<String>();
		try {
			// Fetch uploaded files
			for (String att : documentsDao.getDocumentsForDelete(fieldFixCommonModel)) {
				try {
					File file = new File(getDIR_PATH_DOCS() + att);
					file.delete();
				} catch (Exception e) {
					logger.error("Error while removeing file" + e.getMessage());
				}
			}
			genericApiResponse.setStatus(0);
			genericApiResponse.setData(files);
			genericApiResponse.setMessage("Removed Successfully");
		} catch (Exception e) {
			logger.error("Error while removeing file" + e.getMessage());
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage(prop.getProperty("GEN_002"));
			genericApiResponse.setErrorcode("GEN_002");
		}
		return genericApiResponse;
	}
	
	/**
	 * Download uploaded attachments through out the Field Fix life cycle
	 * @param Attachment id
	 */
	public DocumentsModel downloadAttachments(DocumentsModel documentsModel) {
		List<Attachments> attachments = documentsDao.getAttachment(documentsModel);
		if (!attachments.isEmpty()) {
			Resource resource = loadFileAsResource(getDIR_PATH_DOCS() + attachments.get(0).getAttachUrl());
			// Try to determine file's content type
			String contentType = "application/octet-stream";
			documentsModel.setContentType(contentType);
			documentsModel.setResource(resource);
			documentsModel.setFileName(attachments.get(0).getAttachName());
		}
		return documentsModel;
	}
	
	public Resource loadFileAsResource(String fileName) {
		try {
			this.fileStorageLocation = Paths.get(getDIR_PATH_DOCS()).toAbsolutePath().normalize();
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				return null;
			}
		} catch (MalformedURLException ex) {

		}
		return null;
	}
	
	/**
	 * Download uploaded attachments through out the Field Fix life cycle
	 * @param Attachment id
	 */
	public DocumentsModel downloadAttachmentsZip(DocumentsModel documentsModel) {
		List<Attachments> attachments = documentsDao.getMultipleAttachment(documentsModel);
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(bos);
			byte[] buffer = new byte[1024];
			for (Attachments att : attachments) {
				File srcFile = new File(getDIR_PATH_DOCS() + att.getAttachUrl());
				FileInputStream fis = new FileInputStream(srcFile);
				zos.putNextEntry(new ZipEntry(att.getAttachName()));
				int length;
				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}
				zos.closeEntry();
				fis.close();
			}
			 zos.close(); 
			String contentType = "application/zip";
			documentsModel.setContentType(contentType);
			documentsModel.setFileName("AdditionalDocuments_"+CommonUtils.dateFormatter(new Date(), "yyyyMMdd")+".zip");
			documentsModel.setBos(bos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentsModel;
	}

	@Override
	public GenericApiResponse uploadUserProfile(DocumentsModel documentsModel) {
		final String METHOD_NAME = "uploadUserProfile";
		GenericApiResponse genericApiResponse = new GenericApiResponse();
//		List<AmAttachments> attachments = new ArrayList<>();
		List<AttUploadResponse> attIdNames = new ArrayList<>();
		String fileName = "";
		for(MultipartFile att : documentsModel.getMultipartFileAtachments())
		{
			if (!GeneralConstants.ALLOWED_DOC_TYPE.contains(att.getContentType())) {
				genericApiResponse.setMessage("Invalid file format found");
				genericApiResponse.setStatus(0);
				logger.info(METHOD_NAME + " Invalid File format error");
				return genericApiResponse;
			}
		}
		try {
			UmUsers umUsers = userDao.getUserById(userContext.getLoggedUser().getUserId());
			for (MultipartFile file : documentsModel.getMultipartFileAtachments()) {
				System.out.println("##################################");
				System.out.println("CATALINA PATH:"+System.getProperty("catalina.home"));
				System.out.println("##################################");
				logger.info("CATALINA PATH:"+System.getProperty("catalina.home"));
				fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + "_"
						+ UUID.randomUUID().toString() + file.getOriginalFilename().substring(
								file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
				File fileToSave = new File(getDIR_PATH_DOCS() + fileName);
				Files.copy(file.getInputStream(), fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
				
				umUsers.setProfileId(fileName);
				baseDao.update(umUsers);
				
				AttUploadResponse attUploadResponse = new AttUploadResponse();
				attUploadResponse.setId(umUsers.getProfileId());
//				attUploadResponse.setAttName(attachments2.getAttachName());
				attIdNames.add(attUploadResponse);
			}
			genericApiResponse.setStatus(0);
			genericApiResponse.setData(attIdNames);
			genericApiResponse.setMessage("Uploaded Successfully");
		} catch (Exception e) {
			logger.error("Error while uploading user profile " + e.getMessage());
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage(prop.getProperty("GEN_002"));
			genericApiResponse.setErrorcode("GEN_002");
			genericApiResponse.setData(ExceptionUtils.getStackTrace(e));
		}
		return genericApiResponse;
	}
	
	
	public DocumentsModel downloadUserProfile(String profileId) {
		DocumentsModel documentsModel = new DocumentsModel();
		if(profileId != null) {
			Resource resource = loadFileAsResource(getDIR_PATH_DOCS() + profileId);
			// Try to determine file's content type
			String contentType = "application/octet-stream";
			documentsModel.setContentType(contentType);
			documentsModel.setResource(resource);
			documentsModel.setFileName(profileId);
		}
		return documentsModel;
	}

	@Override
	public DocumentsModel downloadPdfAttachments(DocumentsModel documentsModel) {
		List<Attachments> attachments = documentsDao.getAttachment(documentsModel);
		if (!attachments.isEmpty()) {
			Resource resource = loadFileAsResource(getDIR_PATH_PDF_DOCS() + attachments.get(0).getAttachName());
			// Try to determine file's content type
			String contentType = "application/octet-stream";
			documentsModel.setContentType(contentType);
			documentsModel.setResource(resource);
			documentsModel.setFileName(attachments.get(0).getAttachName());
		}
		return documentsModel;
	}
}
