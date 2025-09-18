package com.portal.workflow;

import com.portal.workflow.model.DocumentFilters;

public class ApprovalListView {
	
	DocumentFilters  advFilters = null ;
	
	private Integer pageNumber;
	
	private Integer pageSize;

	/**
	 * @return the advFilters
	 */
	public DocumentFilters getAdvFilters() {
		return advFilters;
	}

	/**
	 * @param advFilters the advFilters to set
	 */
	public void setAdvFilters(DocumentFilters advFilters) {
		this.advFilters = advFilters;
	}

	/**
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}	
}
