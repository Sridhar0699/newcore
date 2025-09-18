package com.portal.gd.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of Configuration values
 * 
 * @author Harika Kancharla
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConfigGroup {

	@JsonProperty("group_name")
	private String groupName = null;

	@JsonProperty("parent_id")
	private String parentGroup = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("add_field1")
	private String addField1 = null;

	@JsonProperty("add_field2")
	private String addField2 = null;

	@JsonProperty("add_field3")
	private String addField3 = null;

	@JsonProperty("add_field4")
	private String addField4 = null;

	@JsonProperty("add_field5")
	private String addField5 = null;

	@JsonProperty("add_field6")
	private String addField6 = null;

	@JsonProperty("add_field7")
	private String addField7 = null;

	@JsonProperty("add_field8")
	private String addField8 = null;

	@JsonProperty("add_field9")
	private String addField9 = null;

	@JsonProperty("add_field10")
	private String addField10 = null;
	

	@JsonProperty("add_field11")
	private String addField11 = null;

	@JsonProperty("add_field12")
	private String addField12 = null;



	public String getAddField11() {
		return addField11;
	}

	public void setAddField11(String addField11) {
		this.addField11 = addField11;
	}

	public String getAddField12() {
		return addField12;
	}

	public void setAddField12(String addField12) {
		this.addField12 = addField12;
	}

	@JsonProperty("mark_as_delete")
	private Boolean markAsDelete = false;
	
	@JsonProperty("is_manageble")
	private Boolean manageble = false;
	
	@JsonProperty("bp_mandatory")
	private Boolean bpMandatory = false;;

	
	
	
	public Boolean getBpMandatory() {
		return bpMandatory;
	}

	public void setBpMandatory(Boolean bpMandatory) {
		this.bpMandatory = bpMandatory;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the parentGroup
	 */
	public String getParentGroup() {
		return parentGroup;
	}

	/**
	 * @param parentGroup
	 *            the parentGroup to set
	 */
	public void setParentGroup(String parentGroup) {
		this.parentGroup = parentGroup;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the addField1
	 */
	public String getAddField1() {
		return addField1;
	}

	/**
	 * @param addField1
	 *            the addField1 to set
	 */
	public void setAddField1(String addField1) {
		this.addField1 = addField1;
	}

	/**
	 * @return the addField2
	 */
	public String getAddField2() {
		return addField2;
	}

	/**
	 * @param addField2
	 *            the addField2 to set
	 */
	public void setAddField2(String addField2) {
		this.addField2 = addField2;
	}

	/**
	 * @return the addField3
	 */
	public String getAddField3() {
		return addField3;
	}

	/**
	 * @param addField3
	 *            the addField3 to set
	 */
	public void setAddField3(String addField3) {
		this.addField3 = addField3;
	}

	/**
	 * @return the addField4
	 */
	public String getAddField4() {
		return addField4;
	}

	/**
	 * @param addField4
	 *            the addField4 to set
	 */
	public void setAddField4(String addField4) {
		this.addField4 = addField4;
	}

	/**
	 * @return the addField5
	 */
	public String getAddField5() {
		return addField5;
	}

	/**
	 * @param addField5
	 *            the addField5 to set
	 */
	public void setAddField5(String addField5) {
		this.addField5 = addField5;
	}

	/**
	 * @return the addField6
	 */
	public String getAddField6() {
		return addField6;
	}

	/**
	 * @param addField6
	 *            the addField6 to set
	 */
	public void setAddField6(String addField6) {
		this.addField6 = addField6;
	}

	/**
	 * @return the addField7
	 */
	public String getAddField7() {
		return addField7;
	}

	/**
	 * @param addField7
	 *            the addField7 to set
	 */
	public void setAddField7(String addField7) {
		this.addField7 = addField7;
	}

	/**
	 * @return the addField8
	 */
	public String getAddField8() {
		return addField8;
	}

	/**
	 * @param addField8
	 *            the addField8 to set
	 */
	public void setAddField8(String addField8) {
		this.addField8 = addField8;
	}

	/**
	 * @return the addField9
	 */
	public String getAddField9() {
		return addField9;
	}

	/**
	 * @param addField9
	 *            the addField9 to set
	 */
	public void setAddField9(String addField9) {
		this.addField9 = addField9;
	}

	/**
	 * @return the addField10
	 */
	public String getAddField10() {
		return addField10;
	}

	/**
	 * @param addField10
	 *            the addField10 to set
	 */
	public void setAddField10(String addField10) {
		this.addField10 = addField10;
	}

	/**
	 * @return the markAsDelete
	 */
	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @param markAsDelete
	 *            the markAsDelete to set
	 */
	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public Boolean getManageble() {
		return manageble;
	}

	public void setManageble(Boolean manageble) {
		this.manageble = manageble;
	}
	
	

}
