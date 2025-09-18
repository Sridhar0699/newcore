package com.portal.workflow.model;

import java.util.List;

public class WfInitiateRequest {

	private String orgId;
	
	private String userId;
	
	private String wfShortId;
	
	private String orgFormTempltId;
	
	private List<WfEvent> wfEvent;
	
	private String reqFrom;
	
	private String wfId;

	private String actionComments;

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the wfShortId
	 */
	public String getWfShortId() {
		return wfShortId;
	}

	/**
	 * @param wfShortId the wfShortId to set
	 */
	public void setWfShortId(String wfShortId) {
		this.wfShortId = wfShortId;
	}

	/**
	 * @return the orgFormTempltId
	 */
	public String getOrgFormTempltId() {
		return orgFormTempltId;
	}

	/**
	 * @param orgFormTempltId the orgFormTempltId to set
	 */
	public void setOrgFormTempltId(String orgFormTempltId) {
		this.orgFormTempltId = orgFormTempltId;
	}

	/**
	 * @return the wfEvent
	 */
	public List<WfEvent> getWfEvent() {
		return wfEvent;
	}

	/**
	 * @param wfEvent the wfEvent to set
	 */
	public void setWfEvent(List<WfEvent> wfEvent) {
		this.wfEvent = wfEvent;
	}

	/**
	 * @return the reqFrom
	 */
	public String getReqFrom() {
		return reqFrom;
	}

	/**
	 * @param reqFrom the reqFrom to set
	 */
	public void setReqFrom(String reqFrom) {
		this.reqFrom = reqFrom;
	}

	/**
	 * @return the wfId
	 */
	public String getWfId() {
		return wfId;
	}

	/**
	 * @param wfId the wfId to set
	 */
	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	/**
	 * @return the actionComments
	 */
	public String getActionComments() {
		return actionComments;
	}

	/**
	 * @param actionComments the actionComments to set
	 */
	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}
	
	
	
	
}
