package com.portal.hm.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.portal.common.models.GenericApiResponse;
import com.portal.hm.dao.HelpManualDao;
import com.portal.hm.entities.HmManuals;
import com.portal.hm.model.HMAttachments;
import com.portal.hm.model.HMDaoCommonModel;
import com.portal.hm.model.HelpManualDetails;
import com.portal.hm.model.UserType;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;

@Service("hmService")
@PropertySource(value = { "classpath:/com/portal/messages/messages.properties" })
public class HMServiceImpl implements HMService {

	private static final Logger logger = LogManager.getLogger(HMServiceImpl.class);

	@Autowired(required = true)
	private HelpManualDao hmDao;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private LoggedUserContext userContext;

	public static final String DIR_PATH = "/SEC/HM/";
	
	public String getDIR_PATH() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH;
	}

	public GenericApiResponse createOrUpdateHelpManuals(HelpManualDetails helpManualDetails) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		String fileNameAll = "";
		String fileUrlAll = "";
		LoggedUser loggedUser = new LoggedUser();
		BeanUtils.copyProperties(helpManualDetails.getLoggedUser(), loggedUser);
		try {
			for (HMAttachments hmAttachments : helpManualDetails.getHmAttachment()) {

				String mimeType = hmAttachments.getAttachmentName().substring(
						hmAttachments.getAttachmentName().lastIndexOf("."), hmAttachments.getAttachmentName().length());
				if (!".docx,.doc,.pdf,.pptx,.ppt".contains(mimeType)) {
					genericApiResponse.setStatus(1);
					genericApiResponse.setMessage("Invalid file format found");
					genericApiResponse.setErrorcode("GEN_002");
					return genericApiResponse;
				}
				String file = hmAttachments.getAttachment();
				byte[] fileContent = new byte[0];
				if ((hmAttachments.getAttachmentUrl() != null && hmAttachments.getAttachmentUrl().length() > 0)
						&& (file == null || "".equalsIgnoreCase(file))) {
					fileContent = Files.readAllBytes(Paths.get(getDIR_PATH() + hmAttachments.getAttachmentUrl()));
				}
				String fileUrl = "";
				if (fileContent.length > 0) {
					fileUrl = hmAttachments.getAttachmentUrl();
				} else {
					String delims = "[,]";
					String[] parts = file.split(delims);
					String imageString = parts[1];
					byte[] dataArr = Base64.getDecoder().decode(imageString);
					InputStream is = new ByteArrayInputStream(dataArr);

					fileUrl = hmAttachments.getAttachmentName().substring(0,
							hmAttachments.getAttachmentName().lastIndexOf("."))
							+ "_"
							+ UUID.randomUUID().toString()
							+ hmAttachments.getAttachmentName().substring(
									hmAttachments.getAttachmentName().lastIndexOf("."),
									hmAttachments.getAttachmentName().length());
					File fileToSave = new File(getDIR_PATH() + fileUrl);
					Files.copy(is, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}

				String fileName = hmAttachments.getAttachmentName();
				if (fileNameAll.isEmpty() && fileUrlAll.isEmpty()) {
					fileNameAll = fileName;
					fileUrlAll = fileUrl;
				} else {
					fileNameAll = fileNameAll + "," + fileName;
					fileUrlAll = fileUrlAll + "," + fileUrl;
				}
			}
			HMDaoCommonModel hmDaoCommonModel = new HMDaoCommonModel();
			List<HmManuals> helpManual = new ArrayList<>();
			for (UserType userType : helpManualDetails.getAllowedUser()) {
				HmManuals hmManuals = new HmManuals();
				hmManuals.setFileName(fileNameAll);
				hmManuals.setFileUrl(fileUrlAll);
				hmManuals.setManualType(helpManualDetails.getHelpManualType());
				hmManuals.setMarkAsDelete(helpManualDetails.getMarkAsDelete());
				hmManuals.setRoleShortId(userType.getRoleShortId());
				hmManuals.setVersionNo(helpManualDetails.getVersionNo());
				hmManuals.setCreatedBy(helpManualDetails.getLoggedUser().getUserId());
				hmManuals.setCreatedTs(new Date());
				helpManual.add(hmManuals);
				if (userType.getNotification() != null && userType.getNotification() != 0)
					hmDaoCommonModel.setNotificationRole(hmDaoCommonModel.getNotificationRole() != null
							? hmDaoCommonModel.getNotificationRole() + "," + userType.getRoleShortId()
							: userType.getRoleShortId());
			}

			hmDaoCommonModel.setLoggedUser(loggedUser);
			hmDaoCommonModel.setHelpManual(helpManual);
			boolean uploaded = hmDao.createOrUpdateHelpManuals(hmDaoCommonModel);
			if (uploaded) {
				genericApiResponse.setStatus(0);
				genericApiResponse.setMessage("Uploaded Successfully");
			} else {
				genericApiResponse.setStatus(1);
				genericApiResponse.setMessage(prop.getProperty("GEN_002"));
				genericApiResponse.setErrorcode("GEN_002");
			}

		} catch (NoSuchFileException e) {
			File hmDir = new File(getDIR_PATH());
			if (!hmDir.exists())
				hmDir.mkdir();
			logger.error("Error while uploading Help Manuals" + e.getMessage());
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage(prop.getProperty("GEN_002"));
			genericApiResponse.setErrorcode("GEN_002");
		} catch (Exception e) {
			logger.error("Error while uploading Help Manuals" + e.getMessage());
			genericApiResponse.setStatus(1);
			genericApiResponse.setMessage(prop.getProperty("GEN_002"));
			genericApiResponse.setErrorcode("GEN_002");
		}
		return genericApiResponse;
	}

	@Override
	public GenericApiResponse getHelpManuals(HMDaoCommonModel hmDaoCommonModel) {
		String METHOD_NAME = "getHelpManuals";
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResp = new GenericApiResponse();
		List<HelpManualDetails> helpManuals = new ArrayList<>();

		try {
			HMDaoCommonModel hmDaoCommonModelResp = hmDao.getHelpManuals(hmDaoCommonModel);
			List<HmManuals> manuals = hmDaoCommonModelResp.getHelpManual();
			List<String> allManualTypes = hmDaoCommonModelResp.getUtilParams();
			if (manuals != null && allManualTypes != null && allManualTypes.size() > 0) {
				for (String manualTypes : allManualTypes) {
					HelpManualDetails helpManualDetails = new HelpManualDetails();
					String fileUrls = "";
					String fileNames = "";
					List<UserType> allowedUser = new ArrayList<>();
					List<HMAttachments> hmAttachment = new ArrayList<>();
					for (HmManuals hmManuals : manuals) {
						if (manualTypes.equalsIgnoreCase(hmManuals.getManualType())) {
							UserType userType = new UserType();
							userType.setRoleShortId(hmManuals.getRoleShortId());
							userType.setUserType(hmDao.getRolDetailByShortId(hmManuals.getRoleShortId()).getRoleType());
							allowedUser.add(userType);

							fileUrls = hmManuals.getFileUrl();
							fileNames = hmManuals.getFileName();

							helpManualDetails.setHelpManualType(hmManuals.getManualType());
							helpManualDetails.setVersionNo(hmManuals.getVersionNo());
							helpManualDetails
									.setCreatedTs(new SimpleDateFormat("yyyy-MM-dd").format(hmManuals.getCreatedTs()));
						}
					}
					String[] fileUrlArr = fileUrls.split(",");
					String[] fileNameArr = fileNames.split(",");
					for (int i = 0; i < fileUrlArr.length; i++) {
						HMAttachments hmAttachments = new HMAttachments();
						String fileUrl = fileUrlArr[i];
						// FileInputStream is = new FileInputStream(new File(getDIR_PATH() + fileUrl));
						// byte[] bytes = IOUtils.toByteArray(is);
						// hmAttachments.setAttachment(Base64.getEncoder().encodeToString(bytes));
						hmAttachments.setAttachmentName(fileNameArr[i]);
						hmAttachments.setAttachmentUrl(fileUrl);
						hmAttachment.add(hmAttachments);
					}

					helpManualDetails.setAllowedUser(allowedUser);
					helpManualDetails.setHmAttachment(hmAttachment);

					helpManuals.add(helpManualDetails);
				}
				apiResp.setStatus(0);
				apiResp.setData(helpManuals);
			} else {
				apiResp.setMessage(prop.getProperty("HM_002"));
				apiResp.setErrorcode("HM_002");
			}

		} catch (Exception e) {
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
			logger.error("Error occured while getting Help Manuals: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return apiResp;
	}

	@Override
	public HMDaoCommonModel downloadHelpManual(HMDaoCommonModel hmDaoCommonModel) {
		GenericApiResponse genericApiResponse = new GenericApiResponse();
		try {
			HMDaoCommonModel hmDaoCommonModelResp = hmDao.downloadHelpManual(hmDaoCommonModel);

			if (hmDaoCommonModelResp.getFileName() != null && hmDaoCommonModelResp.getFileUrl() != null) {
				byte[] fileContent = Files.readAllBytes(Paths.get(getDIR_PATH() + hmDaoCommonModelResp.getFileUrl()));
				ByteArrayOutputStream out = new ByteArrayOutputStream(fileContent.length);
				out.write(fileContent, 0, fileContent.length);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
				hmDaoCommonModel.setByteArrayInputStream(byteArrayInputStream);
				hmDaoCommonModel.setFileName(hmDaoCommonModelResp.getFileName());
			}
		} catch (Exception e) {
			logger.error("Error while getting help manual file" + e.getMessage());
			genericApiResponse.setStatus(0);
			genericApiResponse.setMessage("Unable to process your request");
		}
		return hmDaoCommonModel;
	}
}
