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

@Entity
@Table(name = "gd_menu_types")
public class GdMenuTypes extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "menu_type_id")
	private Integer menuTypeId;

	@Column(name = "menu_type_short_id")
	private String menuTypeShortId;

	@Column(name = "menu_type_desc")
	private String menuTypeDesc;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	@Column(name = "mark_as_delete")
	private boolean markAsDelete;

	@OneToMany(mappedBy = "gdMenuTypes", fetch = FetchType.LAZY)
	private Set<GdAccessObjects> gdAccessObjects;

	/**
	 * @return the menuTypeId
	 */
	public Integer getMenuTypeId() {
		return menuTypeId;
	}

	/**
	 * @param menuTypeId
	 *            the menuTypeId to set
	 */
	public void setMenuTypeId(Integer menuTypeId) {
		this.menuTypeId = menuTypeId;
	}

	/**
	 * @return the menuTypeShortId
	 */
	public String getMenuTypeShortId() {
		return menuTypeShortId;
	}

	/**
	 * @param menuTypeShortId
	 *            the menuTypeShortId to set
	 */
	public void setMenuTypeShortId(String menuTypeShortId) {
		this.menuTypeShortId = menuTypeShortId;
	}

	/**
	 * @return the menuTypeDesc
	 */
	public String getMenuTypeDesc() {
		return menuTypeDesc;
	}

	/**
	 * @param menuTypeDesc
	 *            the menuTypeDesc to set
	 */
	public void setMenuTypeDesc(String menuTypeDesc) {
		this.menuTypeDesc = menuTypeDesc;
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
	 * @return the markAsDelete
	 */
	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @param markAsDelete
	 *            the markAsDelete to set
	 */
	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	/**
	 * @return the gdAccessObjects
	 */
	public Set<GdAccessObjects> getGdAccessObjects() {
		return gdAccessObjects;
	}

	/**
	 * @param gdAccessObjects
	 *            the gdAccessObjects to set
	 */
	public void setGdAccessObjects(Set<GdAccessObjects> gdAccessObjects) {
		this.gdAccessObjects = gdAccessObjects;
	}
}
