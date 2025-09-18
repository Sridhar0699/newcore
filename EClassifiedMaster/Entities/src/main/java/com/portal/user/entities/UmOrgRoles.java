package com.portal.user.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "um_org_roles")
public class UmOrgRoles extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UmOrgRoles_generator")
	@SequenceGenerator(name = "UmOrgRoles_generator", sequenceName = "UM_ORG_ROLES_SEQ", allocationSize = 1)
	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "role_short_id")
	private String roleShortId;

	@Column(name = "role_desc")
	private String roleDesc;

	@Column(name = "role_type")
	private String roleType;

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

	@OneToMany(mappedBy = "umOrgRoles", fetch = FetchType.LAZY)
	private Set<UmOrgUsers> UmOrgUsers;

	@OneToMany(mappedBy = "umOrgRoles", fetch = FetchType.LAZY)
	private Set<UmOrgRolesPermissions> umOrgRolesPermissions;
	
	@Column(name = "user_type_id")
	private Integer userTypeId;

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	 * @return the roleShortId
	 */
	public String getRoleShortId() {
		return roleShortId;
	}

	/**
	 * @param roleShortId
	 *            the roleShortId to set
	 */
	public void setRoleShortId(String roleShortId) {
		this.roleShortId = roleShortId;
	}

	/**
	 * @return the roleDesc
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @param roleDesc
	 *            the roleDesc to set
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
	 * @return the umOrgUsers
	 */
	public Set<UmOrgUsers> getUmOrgUsers() {
		return UmOrgUsers;
	}

	/**
	 * @param umOrgUsers
	 *            the umOrgUsers to set
	 */
	public void setUmOrgUsers(Set<UmOrgUsers> umOrgUsers) {
		UmOrgUsers = umOrgUsers;
	}

	/**
	 * @return the umOrgRolesPermissions
	 */
	public Set<UmOrgRolesPermissions> getUmOrgRolesPermissions() {
		return umOrgRolesPermissions;
	}

	/**
	 * @param umOrgRolesPermissions
	 *            the umOrgRolesPermissions to set
	 */
	public void setUmOrgRolesPermissions(Set<UmOrgRolesPermissions> umOrgRolesPermissions) {
		this.umOrgRolesPermissions = umOrgRolesPermissions;
	}

	/**
	 * @return the markAsDelete
	 */
	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType
	 *            the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
}
