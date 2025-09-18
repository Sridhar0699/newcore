package com.portal.activity.entities;

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
@Table(name = "login_history")
public class LoginHistory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loginHist_generator")
	@SequenceGenerator(name = "loginHist_generator", sequenceName = "LOGIN_HISTORY_SEQ", allocationSize = 1)
	@Column(name = "login_history_id")
	private Integer loginHistoryId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "login_id")
	private String loginId;

	@Column(name = "action")
	private String action;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "browser_version")
	private String browserversion;

	@Column(name = "browser_name")
	private String browsername;

	@Column(name = "action_ts")
	private Date actionTs;

	@Column(name = "message")
	private String message;

	@Column(name = "mark_as_delete")
	private boolean markAsDelete;

	/**
	 * @return the loginHistoryId
	 */
	public Integer getLoginHistoryId() {
		return loginHistoryId;
	}

	/**
	 * @param loginHistoryId
	 *            the loginHistoryId to set
	 */
	public void setLoginHistoryId(Integer loginHistoryId) {
		this.loginHistoryId = loginHistoryId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the actionTs
	 */
	public Date getActionTs() {
		return actionTs;
	}

	/**
	 * @param actionTs
	 *            the actionTs to set
	 */
	public void setActionTs(Date actionTs) {
		this.actionTs = actionTs;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the browserversion
	 */
	public String getBrowserversion() {
		return browserversion;
	}

	/**
	 * @param browserversion
	 *            the browserversion to set
	 */
	public void setBrowserversion(String browserversion) {
		this.browserversion = browserversion;
	}

	/**
	 * @return the browsername
	 */
	public String getBrowsername() {
		return browsername;
	}

	/**
	 * @param browsername
	 *            the browsername to set
	 */
	public void setBrowsername(String browsername) {
		this.browsername = browsername;
	}
}
