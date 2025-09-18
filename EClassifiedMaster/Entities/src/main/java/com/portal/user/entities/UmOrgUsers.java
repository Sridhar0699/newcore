package com.portal.user.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.org.entities.OmOrgBusinessPartners;
import com.portal.org.entities.OmOrganizations;

@Entity
@Table(name = "UM_ORG_USERS")
public class UmOrgUsers extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ORG_USER_REL_ID")
	private Integer orgUserRelId;

	@ManyToOne
	@JoinColumn(name = "ORG_ID")
	private OmOrganizations omOrganizations;

	@ManyToOne
	@JoinColumn(name = "ORG_BP_ID")
	private OmOrgBusinessPartners omOrgBusinessPartners;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UmUsers umUsers;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private UmOrgRoles umOrgRoles;
	
	@Column(name = "secondary_roles")
	private String secondaryRoles;

	@Column(name = "CREATED_BY")
	private Integer createdBy;

	public String getSecondaryRoles() {
		return secondaryRoles;
	}

	public void setSecondaryRoles(String secondaryRoles) {
		this.secondaryRoles = secondaryRoles;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	@Column(name = "cREATED_TS")
	private Date createdTs;

	@Column(name = "CHANGED_BY")
	private Integer changedBy;

	@Column(name = "CHANGED_TS")
	private Date changedTs;

	@Column(name = "MARK_AS_DELETE")
	private Boolean markAsDelete;

	/**
	 * @return the orgUserRelId
	 */
	public Integer getOrgUserRelId() {
		return orgUserRelId;
	}

	/**
	 * @param orgUserRelId
	 *            the orgUserRelId to set
	 */
	public void setOrgUserRelId(Integer orgUserRelId) {
		this.orgUserRelId = orgUserRelId;
	}

	/**
	 * @return the umUsers
	 */
	public UmUsers getUmUsers() {
		return umUsers;
	}

	/**
	 * @param umUsers
	 *            the umUsers to set
	 */
	public void setUmUsers(UmUsers umUsers) {
		this.umUsers = umUsers;
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
	 * @return the omOrganizations
	 */
	public OmOrganizations getOmOrganizations() {
		return omOrganizations;
	}

	/**
	 * @param omOrganizations
	 *            the omOrganizations to set
	 */
	public void setOmOrganizations(OmOrganizations omOrganizations) {
		this.omOrganizations = omOrganizations;
	}

	/**
	 * @return the omOrgBusinessPartners
	 */
	public OmOrgBusinessPartners getOmOrgBusinessPartners() {
		return omOrgBusinessPartners;
	}

	/**
	 * @param omOrgBusinessPartners
	 *            the omOrgBusinessPartners to set
	 */
	public void setOmOrgBusinessPartners(OmOrgBusinessPartners omOrgBusinessPartners) {
		this.omOrgBusinessPartners = omOrgBusinessPartners;
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

}
