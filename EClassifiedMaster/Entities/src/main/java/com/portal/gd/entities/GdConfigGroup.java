package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_config_group")
public class GdConfigGroup extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "group_name")
	private String groupName;

	@Column(name = "parent_group")
	private String parentGroup;

	@Column(name = "description")
	private String description;

	@Column(name = "add_field1")
	private String addField1;

	@Column(name = "add_field2")
	private String addField2;

	@Column(name = "add_field3")
	private String addField3;

	@Column(name = "add_field4")
	private String addField4;


	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;
	
	@Column(name = "is_manageble")
	private Boolean manageble;
	
	
	@Column(name = "bp_mandatory")
	private Boolean bpMandatory;
	

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

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdTs
	 */
	public Date getCreatedTs() {
		return createdTs;
	}

	/**
	 * @param createdTs
	 *            the createdTs to set
	 */
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	/**
	 * @return the changedBy
	 */
	public Integer getChangedBy() {
		return changedBy;
	}

	/**
	 * @param changedBy
	 *            the changedBy to set
	 */
	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	/**
	 * @return the changedTs
	 */
	public Date getChangedTs() {
		return changedTs;
	}

	/**
	 * @param changedTs
	 *            the changedTs to set
	 */
	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	public Boolean getManageble() {
		return manageble;
	}

	public void setManageble(Boolean manageble) {
		this.manageble = manageble;
	}

	public Boolean getBpMandatory() {
		return bpMandatory;
	}

	public void setBpMandatory(Boolean bpMandatory) {
		this.bpMandatory = bpMandatory;
	}
	
	
	
}
