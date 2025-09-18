package com.portal.org.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;
import com.portal.user.entities.UmOrgUsers;

@Entity
@Table(name = "om_org_business_partners")
public class OmOrgBusinessPartners extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "org_bp_id")
	private String orgBpId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	private OmOrganizations omOrganizations;

	@Column(name = "bp_legal_name")
	private String bpLegalName;

	@Column(name = "bp_gstin_number")
	private String bpGstinNumber;

	@Column(name = "drug_lic_num")
	private String drugLicNum;

	@Column(name = "gstin_status")
	private String gstinStatus;

	@Column(name = "erp_ref_id")
	private String erpRefId;

	@Column(name = "country_id")
	private Integer countryId;

	@Column(name = "addr_line1")
	private String addrLine1;

	@Column(name = "addr_line2")
	private String addrLine2;

	@Column(name = "bp_zip_code")
	private String bpZipCode;

	@Column(name = "bp_contact")
	private String bpContact;

	@Column(name = "bp_type")
	private String bpType;

	@Column(name = "bp_pan")
	private String bpPan;

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

	@OneToMany(mappedBy = "omOrgBusinessPartners", fetch = FetchType.LAZY)
	private Set<UmOrgUsers> umOrgUsers;

	

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
	 * @return the bpLegalName
	 */
	public String getBpLegalName() {
		return bpLegalName;
	}

	/**
	 * @param bpLegalName
	 *            the bpLegalName to set
	 */
	public void setBpLegalName(String bpLegalName) {
		this.bpLegalName = bpLegalName;
	}


	/**
	 * @return the bpGstinNumber
	 */
	public String getBpGstinNumber() {
		return bpGstinNumber;
	}

	/**
	 * @param bpGstinNumber
	 *            the bpGstinNumber to set
	 */
	public void setBpGstinNumber(String bpGstinNumber) {
		this.bpGstinNumber = bpGstinNumber;
	}

	/**
	 * @return the drugLicNum
	 */
	public String getDrugLicNum() {
		return drugLicNum;
	}

	/**
	 * @param drugLicNum
	 *            the drugLicNum to set
	 */
	public void setDrugLicNum(String drugLicNum) {
		this.drugLicNum = drugLicNum;
	}

	/**
	 * @return the gstinStatus
	 */
	public String getGstinStatus() {
		return gstinStatus;
	}

	/**
	 * @param gstinStatus
	 *            the gstinStatus to set
	 */
	public void setGstinStatus(String gstinStatus) {
		this.gstinStatus = gstinStatus;
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
	 * @return the bpZipCode
	 */
	public String getBpZipCode() {
		return bpZipCode;
	}

	/**
	 * @param bpZipCode
	 *            the bpZipCode to set
	 */
	public void setBpZipCode(String bpZipCode) {
		this.bpZipCode = bpZipCode;
	}

	/**
	 * @return the bpContact
	 */
	public String getBpContact() {
		return bpContact;
	}

	/**
	 * @param bpContact
	 *            the bpContact to set
	 */
	public void setBpContact(String bpContact) {
		this.bpContact = bpContact;
	}

	/**
	 * @return the bpType
	 */
	public String getBpType() {
		return bpType;
	}

	/**
	 * @param bpType
	 *            the bpType to set
	 */
	public void setBpType(String bpType) {
		this.bpType = bpType;
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
	 * @return the bpPan
	 */
	public String getBpPan() {
		return bpPan;
	}

	/**
	 * @param bpPan
	 *            the bpPan to set
	 */
	public void setBpPan(String bpPan) {
		this.bpPan = bpPan;
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

}
