package com.portal.user.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "um_user_pwd_reset")
public class UmUserPwdReset extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "reset_key")
	private String resetKey;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UmUsers umUsers;

	@Column(name = "valid_till")
	private Date validTill;

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

	/**
	 * @return the resetKey
	 */
	public String getResetKey() {
		return resetKey;
	}

	/**
	 * @param resetKey
	 *            the resetKey to set
	 */
	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
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
	 * @return the validTill
	 */
	public Date getValidTill() {
		return validTill;
	}

	/**
	 * @param validTill
	 *            the validTill to set
	 */
	public void setValidTill(Date validTill) {
		this.validTill = validTill;
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
}
