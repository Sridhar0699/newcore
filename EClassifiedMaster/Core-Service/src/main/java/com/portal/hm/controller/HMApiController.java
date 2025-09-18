package com.portal.hm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.service.CommonService;
import com.portal.datasecurity.DataSecurityService;
import com.portal.hm.model.HMDaoCommonModel;
import com.portal.hm.model.HelpManualDetails;
import com.portal.hm.service.HMService;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;

import io.swagger.annotations.ApiParam;

@Controller
@PropertySource(value = { "classpath:/com/portal/messages/messages.properties" })
public class HMApiController implements HMApi {

	private static final Logger logger = LogManager.getLogger(HMApiController.class);

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private DataSecurityService dataSecurityService;

	@Autowired(required = true)
	private HMService hmService;

	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> createOrUpdateHelpManualDetails(
			@ApiParam(value = "Help Manual Details", required = false) @RequestBody(required = false) HelpManualDetails payload,
			HttpServletRequest request) {
		String METHOD_NAME = "createOrUpdateHelpManualDetails";

		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();

		payload.setLoggedUser(loggedUser);

		apiResp = hmService.createOrUpdateHelpManuals(payload);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> getHelpManualDetails(
			@NotNull @ApiParam(value = "Manual Type", required = true) @RequestParam("manual_type") String manual_type,
			@NotNull @ApiParam(value = "Manual Id", required = false) @RequestParam(value = "manual_id", required = false) String manual_id,
			@NotNull @ApiParam(value = "Role Id", required = false) @RequestParam(value = "role_id", required = false) String role_id) {
		String METHOD_NAME = "getHelpManualDetails";

		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();

		HMDaoCommonModel hmDaoCommonModel = new HMDaoCommonModel();
		LoggedUser loggedUser = new LoggedUser();
		BeanUtils.copyProperties(userContext.getLoggedUser(), loggedUser);
		hmDaoCommonModel.setLoggedUser(loggedUser);
		hmDaoCommonModel.setManualType(manual_type);
		hmDaoCommonModel.setManualId(manual_id);
		hmDaoCommonModel.setRoleId(role_id);

		apiResp = hmService.getHelpManuals(hmDaoCommonModel);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}

	@Override
	public ResponseEntity<?> downloadHelpManualDetails(
			@NotNull @ApiParam(value = "Manual Type", required = true) @RequestParam("manual_type") String manual_type,
			@NotNull @ApiParam(value = "File Name", required = true) @RequestParam(value = "file_name", required = true) String file_name,
			HttpServletRequest request, HttpServletResponse response) {
		String METHOD_NAME = "downloadHelpManualDetails";
		logger.info("Entered into the method: " + METHOD_NAME);
		ResponseEntity<GenericApiResponse> respObj = null;
		GenericApiResponse apiResp = new GenericApiResponse();
		try {

			HMDaoCommonModel hmDaoCommonModel = new HMDaoCommonModel();
			LoggedUser loggedUser = new LoggedUser();
			BeanUtils.copyProperties(userContext.getLoggedUser(), loggedUser);
			hmDaoCommonModel.setLoggedUser(loggedUser);
			hmDaoCommonModel.setResponse(response);
			hmDaoCommonModel.setFileName(file_name);
			hmDaoCommonModel.setManualType(manual_type);

			HMDaoCommonModel hmDaoCommonModelResp = hmService.downloadHelpManual(hmDaoCommonModel);
			if (hmDaoCommonModelResp.getByteArrayInputStream() != null && hmDaoCommonModelResp.getFileName() != null) {
				InputStreamResource file = new InputStreamResource(hmDaoCommonModelResp.getByteArrayInputStream());
				response.setHeader("Content-type",
						request.getServletContext().getMimeType(hmDaoCommonModelResp.getFileName()));
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + hmDaoCommonModelResp.getFileName() + "\"");
				FileCopyUtils.copy(file.getInputStream(), response.getOutputStream());
			}

			return new ResponseEntity<Void>(HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error while executing falcon data load on demand : " + ExceptionUtils.getStackTrace(e));

			apiResp.setStatus(1);
			apiResp.setMessage(prop.getProperty("GEN_002"));
			apiResp.setErrorcode("GEN_002");
		}

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
}
