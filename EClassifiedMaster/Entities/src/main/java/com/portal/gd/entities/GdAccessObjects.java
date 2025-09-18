package com.portal.gd.entities;

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
import com.portal.user.entities.UmOrgRolesPermissions;

@Entity
@Table(name = "gd_access_objects")
public class GdAccessObjects extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "access_obj_id")
	private String accessObjId;

	@Column(name = "parent_obj_id")
	private String parentObjId;

	@Column(name = "read_role")
	private String readRole;

	@Column(name = "write_role")
	private String writeRole;

	@Column(name = "read_method")
	private String readMethod;

	@Column(name = "write_method")
	private String writeMethod;

	@Column(name = "access_obj_desc")
	private String accessObjDesc;

	@Column(name = "menu_title")
	private String menuTitle;

	@Column(name = "menu_icon")
	private String menuIcon;

	@Column(name = "seq_no")
	private Integer seqNo;

	@Column(name = "mobile_ios")
	private Boolean mobileIos;

	@Column(name = "mobile_android")
	private Boolean mobileAndroid;

	@Column(name = "tablet_ios")
	private Boolean tabletIos;

	@Column(name = "tablet_android")
	private Boolean tabletAndroid;

	@ManyToOne
	@JoinColumn(name = "display_menu_id")
	private GdMenuTypes gdMenuTypes;

	@Column(name = "nav_link")
	private String navLink;
	
	@Column(name = "secure_group")
	private boolean secureGroup; 

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

	@OneToMany(mappedBy = "gdAccessObjects", fetch = FetchType.LAZY)
	private Set<UmOrgRolesPermissions> umOrgRolesPermissions;

	/**
	 * @return the accessObjId
	 */
	public String getAccessObjId() {
		return accessObjId;
	}

	/**
	 * @param accessObjId
	 *            the accessObjId to set
	 */
	public void setAccessObjId(String accessObjId) {
		this.accessObjId = accessObjId;
	}

	/**
	 * @return the parentObjId
	 */
	public String getParentObjId() {
		return parentObjId;
	}

	/**
	 * @param parentObjId
	 *            the parentObjId to set
	 */
	public void setParentObjId(String parentObjId) {
		this.parentObjId = parentObjId;
	}

	/**
	 * @return the readRole
	 */
	public String getReadRole() {
		return readRole;
	}

	/**
	 * @param readRole
	 *            the readRole to set
	 */
	public void setReadRole(String readRole) {
		this.readRole = readRole;
	}

	/**
	 * @return the writeRole
	 */
	public String getWriteRole() {
		return writeRole;
	}

	/**
	 * @param writeRole
	 *            the writeRole to set
	 */
	public void setWriteRole(String writeRole) {
		this.writeRole = writeRole;
	}

	/**
	 * @return the readMethod
	 */
	public String getReadMethod() {
		return readMethod;
	}

	/**
	 * @param readMethod
	 *            the readMethod to set
	 */
	public void setReadMethod(String readMethod) {
		this.readMethod = readMethod;
	}

	/**
	 * @return the writeMethod
	 */
	public String getWriteMethod() {
		return writeMethod;
	}

	/**
	 * @param writeMethod
	 *            the writeMethod to set
	 */
	public void setWriteMethod(String writeMethod) {
		this.writeMethod = writeMethod;
	}

	/**
	 * @return the accessObjDesc
	 */
	public String getAccessObjDesc() {
		return accessObjDesc;
	}

	/**
	 * @param accessObjDesc
	 *            the accessObjDesc to set
	 */
	public void setAccessObjDesc(String accessObjDesc) {
		this.accessObjDesc = accessObjDesc;
	}

	/**
	 * @return the menuTitle
	 */
	public String getMenuTitle() {
		return menuTitle;
	}

	/**
	 * @param menuTitle
	 *            the menuTitle to set
	 */
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	/**
	 * @return the menuIcon
	 */
	public String getMenuIcon() {
		return menuIcon;
	}

	/**
	 * @param menuIcon
	 *            the menuIcon to set
	 */
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	/**
	 * @return the seqNo
	 */
	public Integer getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return the mobileIos
	 */
	public Boolean getMobileIos() {
		return mobileIos;
	}

	/**
	 * @param mobileIos
	 *            the mobileIos to set
	 */
	public void setMobileIos(Boolean mobileIos) {
		this.mobileIos = mobileIos;
	}

	/**
	 * @return the mobileAndroid
	 */
	public Boolean getMobileAndroid() {
		return mobileAndroid;
	}

	/**
	 * @param mobileAndroid
	 *            the mobileAndroid to set
	 */
	public void setMobileAndroid(Boolean mobileAndroid) {
		this.mobileAndroid = mobileAndroid;
	}

	/**
	 * @return the tabletIos
	 */
	public Boolean getTabletIos() {
		return tabletIos;
	}

	/**
	 * @param tabletIos
	 *            the tabletIos to set
	 */
	public void setTabletIos(Boolean tabletIos) {
		this.tabletIos = tabletIos;
	}

	/**
	 * @return the tabletAndroid
	 */
	public Boolean getTabletAndroid() {
		return tabletAndroid;
	}

	/**
	 * @param tabletAndroid
	 *            the tabletAndroid to set
	 */
	public void setTabletAndroid(Boolean tabletAndroid) {
		this.tabletAndroid = tabletAndroid;
	}

	/**
	 * @return the gdMenuTypes
	 */
	public GdMenuTypes getGdMenuTypes() {
		return gdMenuTypes;
	}

	/**
	 * @param gdMenuTypes
	 *            the gdMenuTypes to set
	 */
	public void setGdMenuTypes(GdMenuTypes gdMenuTypes) {
		this.gdMenuTypes = gdMenuTypes;
	}

	/**
	 * @return the navLink
	 */
	public String getNavLink() {
		return navLink;
	}

	/**
	 * @param navLink
	 *            the navLink to set
	 */
	public void setNavLink(String navLink) {
		this.navLink = navLink;
	}

	public boolean isSecureGroup() {
		return secureGroup;
	}

	public void setSecureGroup(boolean secureGroup) {
		this.secureGroup = secureGroup;
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
}
