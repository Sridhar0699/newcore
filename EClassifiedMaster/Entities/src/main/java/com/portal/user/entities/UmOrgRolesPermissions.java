package com.portal.user.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.gd.entities.GdAccessObjects;

@Entity
@Table(name = "um_org_roles_permissions")
public class UmOrgRolesPermissions extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UmOrgRolesPermissions_generator")
	@SequenceGenerator(name = "UmOrgRolesPermissions_generator", sequenceName = "UM_ORG_ROLES_PERMISSIONS_SEQ", allocationSize = 1)
	@Column(name = "role_ao_rel_id")
	private Integer roleAoRelId;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private UmOrgRoles umOrgRoles;

	@ManyToOne
	@JoinColumn(name = "access_obj_id")
	private GdAccessObjects gdAccessObjects;

	@Column(name = "permission_level")
	private Boolean permissionLevel;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "changed_by")
	private Integer changedBy;

	@Column(name = "changed_ts")
	private Date changedTs;

	@Column(name = "mark_as_delete")
	private Boolean markAsDelete;
	
	@Column(name = "read_permission")
	private Boolean readPermission;
	
	@Column(name = "write_permission")
	private Boolean writePermission;

	public Boolean getReadPermission() {
		return readPermission;
	}

	public void setReadPermission(Boolean readPermission) {
		this.readPermission = readPermission;
	}

	public Boolean getWritePermission() {
		return writePermission;
	}

	public void setWritePermission(Boolean writePermission) {
		this.writePermission = writePermission;
	}

	/**
	 * @return the roleAoRelId
	 */
	public Integer getRoleAoRelId() {
		return roleAoRelId;
	}

	/**
	 * @param roleAoRelId
	 *            the roleAoRelId to set
	 */
	public void setRoleAoRelId(Integer roleAoRelId) {
		this.roleAoRelId = roleAoRelId;
	}

	/**
	 * @return the umOrgRoles
	 */
	public UmOrgRoles getUmOrgRoles() {
		return umOrgRoles;
	}

	/**
	 * @param umOrgRoles
	 *            the umOrgRoles to set
	 */
	public void setUmOrgRoles(UmOrgRoles umOrgRoles) {
		this.umOrgRoles = umOrgRoles;
	}

	/**
	 * @return the permissionLevel
	 */
	public Boolean isPermissionLevel() {
		return permissionLevel;
	}

	/**
	 * @param permissionLevel
	 *            the permissionLevel to set
	 */
	public void setPermissionLevel(Boolean permissionLevel) {
		this.permissionLevel = permissionLevel;
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
	 * @return the gdAccessObjects
	 */
	public GdAccessObjects getGdAccessObjects() {
		return gdAccessObjects;
	}

	/**
	 * @param gdAccessObjects
	 *            the gdAccessObjects to set
	 */
	public void setGdAccessObjects(GdAccessObjects gdAccessObjects) {
		this.gdAccessObjects = gdAccessObjects;
	}

}
