package com.portal.workflow.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WfDetails {

	private LinkedHashMap<String, String> seqFlowMap;
	private LinkedHashMap<String, Map<String, Map>> condSeqFlowMap;
	private LinkedHashMap<String, Map<String, Object>> sourceRefVsActor;
	private String exclusiveGateway;
	private LinkedHashMap<String, List<Map<String, Object>>> serviceTasks;
	private List<String> endEvents;
	private String sourceRef;
	private String targetRef;
	private Map objectRefMap;
	private String wfDesc;
	private String wfType;
	private String initiatorFlag;
	private Map mailNotificationsMap;
	private Map<String, Object> condSeqSrc;
	private Set<String> previousWfApproversMails = null;

	public LinkedHashMap<String, String> getSeqFlowMap() {
		return seqFlowMap;
	}

	public void setSeqFlowMap(LinkedHashMap<String, String> seqFlowMap) {
		this.seqFlowMap = seqFlowMap;
	}
	
	public LinkedHashMap<String, Map<String, Object>> getSourceRefVsActor() {
		return sourceRefVsActor;
	}

	public void setSourceRefVsActor(LinkedHashMap<String, Map<String, Object>> sourceRefVsActor) {
		this.sourceRefVsActor = sourceRefVsActor;
	}

	public String getExclusiveGateway() {
		return exclusiveGateway;
	}

	public void setExclusiveGateway(String exclusiveGateway) {
		this.exclusiveGateway = exclusiveGateway;
	}

	public LinkedHashMap<String, List<Map<String, Object>>> getServiceTasks() {
		return serviceTasks;
	}

	public void setServiceTasks(LinkedHashMap<String, List<Map<String, Object>>> serviceTasks) {
		this.serviceTasks = serviceTasks;
	}

	public List<String> getEndEvents() {
		return endEvents;
	}

	public void setEndEvents(List<String> endEvents) {
		this.endEvents = endEvents;
	}

	public LinkedHashMap<String, Map<String, Map>> getCondSeqFlowMap() {
		return condSeqFlowMap;
	}

	public void setCondSeqFlowMap(LinkedHashMap<String, Map<String, Map>> condSeqFlowMap) {
		this.condSeqFlowMap = condSeqFlowMap;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}

	public Map getObjectRefMap() {
		return objectRefMap;
	}

	public void setObjectRefMap(Map objectRefMap) {
		this.objectRefMap = objectRefMap;
	}

	public String getWfDesc() {
		return wfDesc;
	}

	public void setWfDesc(String wfDesc) {
		this.wfDesc = wfDesc;
	}

	public String getWfType() {
		return wfType;
	}

	public void setWfType(String wfType) {
		this.wfType = wfType;
	}

	public Map getMailNotificationsMap() {
		return mailNotificationsMap;
	}

	public void setMailNotificationsMap(Map mailNotificationsMap) {
		this.mailNotificationsMap = mailNotificationsMap;
	}

	public Map<String, Object> getCondSeqSrc() {
		return condSeqSrc;
	}

	public void setCondSeqSrc(Map<String, Object> condSeqSrc) {
		this.condSeqSrc = condSeqSrc;
	}

	public Set<String> getPreviousWfApproversMails() {
		return previousWfApproversMails;
	}

	public void setPreviousWfApproversMails(Set<String> previousWfApproversMails) {
		this.previousWfApproversMails = previousWfApproversMails;
	}

	public String getInitiatorFlag() {
		return initiatorFlag;
	}

	public void setInitiatorFlag(String initiatorFlag) {
		this.initiatorFlag = initiatorFlag;
	}
	
}
