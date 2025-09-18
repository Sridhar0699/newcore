package com.portal.gd.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.user.entities.UmUsers;

@Entity
@Table(name = "gd_user_types")
public class GdUserTypes extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "user_type_id")
	private Integer userTypeId;

	@Column(name = "type_short_id")
	private String typeShortId;

	@Column(name = "type_desc")
	private String typeDesc;

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

	@OneToMany(mappedBy = "gdUserTypes", fetch = FetchType.LAZY)
	private Set<UmUsers> umUsers;
	
	@Column(name = "erp_ref_id")
	private String erpRefId;

	/**
	 * @return the userTypeId
	 */
	public Integer getUserTypeId() {
		return userTypeId;
	}

	/**
	 * @param userTypeId
	 *            the userTypeId to set
	 */
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	/**
	 * @return the typeShortId
	 */
	public String getTypeShortId() {
		return typeShortId;
	}

	/**
	 * @param typeShortId
	 *            the typeShortId to set
	 */
	public void setTypeShortId(String typeShortId) {
		this.typeShortId = typeShortId;
	}

	/**
	 * @return the typeDesc
	 */
	public String getTypeDesc() {
		return typeDesc;
	}

	/**
	 * @param typeDesc
	 *            the typeDesc to set
	 */
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	/**
	 * @return the markAsDelete
	 */
	public Boolean isMarkAsDelete() {
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

	/**
	 * @return the umUsers
	 */
	public Set<UmUsers> getUmUsers() {
		return umUsers;
	}

	/**
	 * @param umUsers
	 *            the umUsers to set
	 */
	public void setUmUsers(Set<UmUsers> umUsers) {
		this.umUsers = umUsers;
	}

	public String getErpRefId() {
		return erpRefId;
	}

	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}
}
