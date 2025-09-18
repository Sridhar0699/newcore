package com.portal.reports.to;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportsRequest {

	@JsonProperty("filter")
	private ReportsRequestFilter filter = new ReportsRequestFilter();

	@JsonProperty("reportShortId")
	private String reportShortId;

	@JsonProperty("rptAccessObjId")
	private String rptAccessObjId;

	@JsonProperty("masterDataId")
	private String masterDataId;
	
	@JsonProperty("data")
	private Map<String,Object> headersData;

	public ReportsRequestFilter getFilter() {
		return filter;
	}

	public void setFilter(ReportsRequestFilter filter) {
		this.filter = filter;
	}

	public String getReportShortId() {
		return reportShortId;
	}

	public void setReportShortId(String reportShortId) {
		this.reportShortId = reportShortId;
	}

	public String getRptAccessObjId() {
		return rptAccessObjId;
	}

	public void setRptAccessObjId(String rptAccessObjId) {
		this.rptAccessObjId = rptAccessObjId;
	}

	public String getMasterDataId() {
		return masterDataId;
	}

	public void setMasterDataId(String masterDataId) {
		this.masterDataId = masterDataId;
	}

	public Map<String, Object> getHeadersData() {
		return headersData;
	}

	public void setHeadersData(Map<String, Object> headersData) {
		this.headersData = headersData;
	}

}
