package com.portal.org.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.user.entities.UmOrgUsers;

@Entity
@Table(name = "OM_ORGANIZATIONS")
public class OmOrganizations extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ORG_ID")
	private String orgId;

	@Column(name = "ORG_NAME")
	private String orgName;

	@Column(name = "ERP_REF_ID")
	private String erpRefId;

	@Column(name = "COUNTRY_ID")
	private Integer countryId;

	@Column(name = "INTERNAL_BP_NAME")
	private String internalBpName;

	@Column(name = "ADDR_LINE1")
	private String addrLine1;

	@Column(name = "ADDR_LINE2")
	private String addrLine2;

	/*@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "contact")
	private String contact;*/

	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Column(name = "CREATED_TS")
	private Date createdTs;

	@Column(name = "CHANGED_BY")
	private Integer changedBy;

	@Column(name = "CHANGED_TS")
	private Date changedTs;

	@Column(name = "MARK_AS_DELETE")
	private boolean markAsDelete;

	@OneToMany(mappedBy = "omOrganizations", fetch = FetchType.LAZY)
	private Set<UmOrgUsers> umOrgUsers;

	@OneToMany(mappedBy = "omOrganizations", fetch = FetchType.LAZY)
	private Set<OmOrgBusinessPartners> omOrgBusinessPartners;

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
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the erpRefId
	 */
	public String getErpRefId() {
		return erpRefId;
	}

	/**
	 * @param erpRefId
	 *            the erpRefId to set
	 */
	public void setErpRefId(String erpRefId) {
		this.erpRefId = erpRefId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the internalBpName
	 */
	public String getInternalBpName() {
		return internalBpName;
	}

	/**
	 * @param internalBpName
	 *            the internalBpName to set
	 */
	public void setInternalBpName(String internalBpName) {
		this.internalBpName = internalBpName;
	}

	/**
	 * @return the addrLine1
	 */
	public String getAddrLine1() {
		return addrLine1;
	}

	/**
	 * @param addrLine1
	 *            the addrLine1 to set
	 */
	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	/**
	 * @return the addrLine2
	 */
	public String getAddrLine2() {
		return addrLine2;
	}

	/**
	 * @param addrLine2
	 *            the addrLine2 to set
	 */
	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	/**
	 * @return the zipCode
	 */
	/*public String getZipCode() {
		return zipCode;
	}

	*//**
	 * @param zipCode
	 *            the zipCode to set
	 *//*
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	*//**
	 * @return the contact
	 *//*
	public String getContact() {
		return contact;
	}

	*//**
	 * @param contact
	 *            the contact to set
	 *//*
	public void setContact(String contact) {
		this.contact = contact;
	}*/

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
	 * @return the umOrgUsers
	 */
	public Set<UmOrgUsers> getUmOrgUsers() {
		return umOrgUsers;
	}

	/**
	 * @param umOrgUsers
	 *            the umOrgUsers to set
	 */
	public void setUmOrgUsers(Set<UmOrgUsers> umOrgUsers) {
		this.umOrgUsers = umOrgUsers;
	}

	/**
	 * @return the omOrgBusinessPartners
	 */
	public Set<OmOrgBusinessPartners> getOmOrgBusinessPartners() {
		return omOrgBusinessPartners;
	}

	/**
	 * @param omOrgBusinessPartners
	 *            the omOrgBusinessPartners to set
	 */
	public void setOmOrgBusinessPartners(Set<OmOrgBusinessPartners> omOrgBusinessPartners) {
		this.omOrgBusinessPartners = omOrgBusinessPartners;
	}

}
