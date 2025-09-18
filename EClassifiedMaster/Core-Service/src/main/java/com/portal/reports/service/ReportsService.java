package com.portal.reports.service;

import javax.servlet.http.HttpServletResponse;

import com.portal.common.models.GenericApiResponse;
import com.portal.reports.to.ReportDownloadModel;
import com.portal.reports.to.ReportsCommonModel;

public interface ReportsService {

	public GenericApiResponse reportDownload(ReportsCommonModel reportsCommonModel,
			HttpServletResponse response);
	public GenericApiResponse getReportsMaster(ReportsCommonModel reportsCommonModel);
	public void reportDownloadAsync(ReportsCommonModel reportsCommonModel, HttpServletResponse response);
	public ReportDownloadModel downloadGeneratedReport(String rptShortId, GenericApiResponse genericApiResponse);
}
