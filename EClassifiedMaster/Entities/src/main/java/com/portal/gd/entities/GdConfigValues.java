package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_config_values")
public class GdConfigValues extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GdConfigValues_generator")
	@SequenceGenerator(name = "GdConfigValues_generator", sequenceName = "GD_CONFIG_VALUES_SEQ", allocationSize = 1)
	@Column(name = "config_val_id")
	private Integer configValId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "org_bp_id")
	private String orgBpId;

	@Column(name = "config_group_id")
	private String configGroupId;

	@Column(name = "parent_val_id")
	private String parentValId;

	@Column(name = "config_key")
	private String key;

	@Column(name = "config_value")
	private String value;

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

	/**
	 * @return the configValId
	 */
	public Integer getConfigValId() {
		return configValId;
	}

	/**
	 * @param configValId
	 *            the configValId to set
	 */
	public void setConfigValId(Integer configValId) {
		this.configValId = configValId;
	}

	/**
	 * @return the configGroupId
	 */
	public String getConfigGroupId() {
		return configGroupId;
	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the orgBpId
	 */
	public String getOrgBpId() {
		return orgBpId;
	}

	/**
	 * @param orgBpId
	 *            the orgBpId to set
	 */
	public void setOrgBpId(String orgBpId) {
		this.orgBpId = orgBpId;
	}

	/**
	 * @param configGroupId
	 *            the configGroupId to set
	 */
	public void setConfigGroupId(String configGroupId) {
		this.configGroupId = configGroupId;
	}

	/**
	 * @return the parentValId
	 */
	public String getParentValId() {
		return parentValId;
	}

	/**
	 * @param parentValId
	 *            the parentValId to set
	 */
	public void setParentValId(String parentValId) {
		this.parentValId = parentValId;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
}
