package com.portal.apilog.entities;

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
import com.portal.gd.entities.GdApiModules;

@Entity
@Table(name = "portal_api_log")
public class PortalApiLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portalapilog_generator")
	@SequenceGenerator(name = "portalapilog_generator", sequenceName = "portal_api_log_SEQ", allocationSize = 1)
	@Column(name = "log_id")
	private Integer logId;

	@Column(name = "ref_obj_id")
	private String refObjId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "api_status")
	private String apiStatus;

	@Column(name = "api_url")
	private String apiUrl;

	@Column(name = "api_req_method")
	private String apiReqMethod;

	@Column(name = "api_req_header")
	private String apiReqHeader;

	@Column(name = "api_req_body")
	private String apiReqBody;

	@Column(name = "api_res_header")
	private String apiResHeader;

	@Column(name = "api_res_body")
	private String apiResBody;

	@Column(name = "api_res_ts")
	private Long apiResTs;

	@Column(name = "sap_service_name")
	private String sapServiuceName;

	@Column(name = "client_ip")
	private String clientIp;

	@ManyToOne
	@JoinColumn(name = "api_module_id")
	private GdApiModules gdApiModules;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_ts")
	private Date createdTs;
	
	

	/**
	 * @return the logId
	 */
	public Integer getLogId() {
		return logId;
	}

	/**
	 * @param logId
	 *            the logId to set
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * @return the refObjId
	 */
	public String getRefObjId() {
		return refObjId;
	}

	/**
	 * @param refObjId
	 *            the refObjId to set
	 */
	public void setRefObjId(String refObjId) {
		this.refObjId = refObjId;
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
	 * @return the apiStatus
	 */
	public String getApiStatus() {
		return apiStatus;
	}

	/**
	 * @param apiStatus
	 *            the apiStatus to set
	 */
	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}

	/**
	 * @return the apiUrl
	 */
	public String getApiUrl() {
		return apiUrl;
	}

	/**
	 * @param apiUrl
	 *            the apiUrl to set
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/**
	 * @return the apiReqMethod
	 */
	public String getApiReqMethod() {
		return apiReqMethod;
	}

	/**
	 * @param apiReqMethod
	 *            the apiReqMethod to set
	 */
	public void setApiReqMethod(String apiReqMethod) {
		this.apiReqMethod = apiReqMethod;
	}

	/**
	 * @return the apiReqHeader
	 */
	public String getApiReqHeader() {
		return apiReqHeader;
	}

	/**
	 * @param apiReqHeader
	 *            the apiReqHeader to set
	 */
	public void setApiReqHeader(String apiReqHeader) {
		this.apiReqHeader = apiReqHeader;
	}

	/**
	 * @return the apiReqBody
	 */
	public String getApiReqBody() {
		return apiReqBody;
	}

	/**
	 * @param apiReqBody
	 *            the apiReqBody to set
	 */
	public void setApiReqBody(String apiReqBody) {
		this.apiReqBody = apiReqBody;
	}

	/**
	 * @return the apiResHeader
	 */
	public String getApiResHeader() {
		return apiResHeader;
	}

	/**
	 * @param apiResHeader
	 *            the apiResHeader to set
	 */
	public void setApiResHeader(String apiResHeader) {
		this.apiResHeader = apiResHeader;
	}

	/**
	 * @return the apiResBody
	 */
	public String getApiResBody() {
		return apiResBody;
	}

	/**
	 * @param apiResBody
	 *            the apiResBody to set
	 */
	public void setApiResBody(String apiResBody) {
		this.apiResBody = apiResBody;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the gdApiModules
	 */
	public GdApiModules getGdApiModules() {
		return gdApiModules;
	}

	/**
	 * @param gdApiModules
	 *            the gdApiModules to set
	 */
	public void setGdApiModules(GdApiModules gdApiModules) {
		this.gdApiModules = gdApiModules;
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
	 * @return the apiResTs
	 */
	public Long getApiResTs() {
		return apiResTs;
	}

	/**
	 * @return the sapServiuceName
	 */
	public String getSapServiuceName() {
		return sapServiuceName;
	}

	/**
	 * @param apiResTs
	 *            the apiResTs to set
	 */
	public void setApiResTs(Long apiResTs) {
		this.apiResTs = apiResTs;
	}

	/**
	 * @param sapServiuceName
	 *            the sapServiuceName to set
	 */
	public void setSapServiuceName(String sapServiuceName) {
		this.sapServiuceName = sapServiuceName;
	}
}
