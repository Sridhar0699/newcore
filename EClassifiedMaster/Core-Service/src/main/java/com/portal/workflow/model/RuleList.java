package com.portal.workflow.model;

import java.util.List;

public class RuleList {

	private String sequenceNo;
	
	private String dataField;
	
	private double value1;
	
	private double value2;
	
	private String expression;
	
	private String description;
	
	private String approvedType;
	
	private List<String> roles;
	
	private List<String> userIds;
	
	private List<String> approvers;
	
	private String ruleType;
	
	private String order;
	
	private boolean active;
	
	private String fieldLocation;
	
	private String id;
	
	private String refLevelNo;
	
	private List candidateUsers;
	
	private List candidateGroups;
	
	private String approvalLevels;
	
	private List<RuleFields> ruleFields;
	
	private String ruleExpressionType;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public String getApprovedType() {
		return approvedType;
	}

	public void setApprovedType(String approvedType) {
		this.approvedType = approvedType;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public double getValue1() {
		return value1;
	}

	public void setValue1(double value1) {
		this.value1 = value1;
	}

	public double getValue2() {
		return value2;
	}

	public void setValue2(double value2) {
		this.value2 = value2;
	}

	public List<String> getApprovers() {
		return approvers;
	}

	public void setApprovers(List<String> approvers) {
		this.approvers = approvers;
	}

	public String getFieldLocation() {
		return fieldLocation;
	}

	public void setFieldLocation(String fieldLocation) {
		this.fieldLocation = fieldLocation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefLevelNo() {
		return refLevelNo;
	}

	public void setRefLevelNo(String refLevelNo) {
		this.refLevelNo = refLevelNo;
	}

	public List getCandidateUsers() {
		return candidateUsers;
	}

	public void setCandidateUsers(List candidateUsers) {
		this.candidateUsers = candidateUsers;
	}

	public List getCandidateGroups() {
		return candidateGroups;
	}

	public void setCandidateGroups(List candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	public String getApprovalLevels() {
		return approvalLevels;
	}

	public void setApprovalLevels(String approvalLevels) {
		this.approvalLevels = approvalLevels;
	}

	public List<RuleFields> getRuleFields() {
		return ruleFields;
	}

	public void setRuleFields(List<RuleFields> ruleFields) {
		this.ruleFields = ruleFields;
	}

	public String getRuleExpressionType() {
		return ruleExpressionType;
	}

	public void setRuleExpressionType(String ruleExpressionType) {
		this.ruleExpressionType = ruleExpressionType;
	}

	
}
