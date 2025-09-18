package com.portal.reports.controller;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriUtils;

import com.portal.common.models.GenericApiResponse;
import com.portal.datasecurity.DataSecurityService;
import com.portal.reports.service.ReportsService;
import com.portal.reports.to.ReportDownloadModel;
import com.portal.reports.to.ReportsCommonModel;
import com.portal.reports.to.ReportsRequest;
import com.portal.security.model.LoggedUser;
import com.portal.security.util.LoggedUserContext;

import io.swagger.annotations.ApiParam;

@Controller
public class ReportsApiController implements ReportsApi {

	private static final Logger logger = LogManager.getLogger(ReportsApiController.class);
	
	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private DataSecurityService dataSecurityService;

	
	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired
	private ReportsService reportsService;
	
	public ResponseEntity<?> getReportsMaster() {
		String METHOD_NAME = "getOrgDetails";

		logger.info("Entered into the method: " + METHOD_NAME);
		LoggedUser loggedUser = userContext.getLoggedUser();
		Date respFrmTs = new Date();

		ResponseEntity<GenericApiResponse> respObj = null;

		GenericApiResponse apiResp = new GenericApiResponse();

		ReportsCommonModel reportsCommonModel = new ReportsCommonModel();
		reportsCommonModel.setLoggedUser(loggedUser);
		apiResp = reportsService.getReportsMaster(reportsCommonModel);

		respObj = new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);

		logger.info("Exit from the method: " + METHOD_NAME);

		return respObj;
	}
	
	@Override
	public ResponseEntity<?> reportDownload(
			@ApiParam(value = "Reports Request Body", required = false) @RequestBody(required = false) ReportsRequest payload,
			HttpServletRequest request, HttpServletResponse response) {
		String METHOD_NAME = "reportDownload";
		
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResp = new GenericApiResponse();
		LoggedUser loggedUser = userContext.getLoggedUser();
		ReportsCommonModel reportsCommonModel = new ReportsCommonModel();
		reportsCommonModel.setReportsRequest(payload);
		reportsCommonModel.setLoggedUser(loggedUser);
		if (reportsCommonModel.getReportsRequest() != null
				&& "SAMPLE".equalsIgnoreCase(reportsCommonModel.getReportsRequest().getReportShortId())) {
			reportsService.reportDownloadAsync(reportsCommonModel, response);
			apiResp.setStatus(0);
			apiResp.setMessage("Request submitted ,please check after some time");
		} else {
			apiResp = reportsService.reportDownload(reportsCommonModel, response);
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> downloadReportFile(@NotNull String shortId, HttpServletRequest request, HttpServletResponse response) {

		String METHOD_NAME = "downloadReport";

		logger.info("Entered into the method: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();
		try {
			ReportDownloadModel fieldFixDownloadModel = reportsService.downloadGeneratedReport(shortId, apiResp);
			if (fieldFixDownloadModel.getResource() == null) {
				return new ResponseEntity<String>(prop.getProperty("DOC_0001"), HttpStatus.OK);
			}
			fieldFixDownloadModel.setContentType(request.getServletContext()
					.getMimeType(fieldFixDownloadModel.getResource().getFile().getAbsolutePath()));
			response.setHeader("Content-type", fieldFixDownloadModel.getContentType());
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + UriUtils.encode(fieldFixDownloadModel.getFileName(), "UTF-8") + "\"");
			response.setHeader("Content-Length",
					String.valueOf(fieldFixDownloadModel.getResource().getInputStream().available()));
			FileCopyUtils.copy(fieldFixDownloadModel.getResource().getInputStream(), response.getOutputStream());
			logger.info("Exit from the method: " + METHOD_NAME);
		} catch (Exception e) {
		}
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

}
