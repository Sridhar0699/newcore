package com.portal.gd.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "gd_payment_gateway_config")
public class GdPaymentGatewayConfig extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "provider")
	private Integer provider;
	
	@Column(name = "env")
	private Integer env;
	
	@Column(name = "config_params")
	private Integer configParams;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_ts")
	private Integer createdTs;
	
	@Column(name = "changed_by")
	private Integer changedBy;
	
	@Column(name = "changed_ts")
	private Integer changedTs;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "mark_as_delete")
	private Integer markAsDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProvider() {
		return provider;
	}

	public void setProvider(Integer provider) {
		this.provider = provider;
	}

	public Integer getEnv() {
		return env;
	}

	public void setEnv(Integer env) {
		this.env = env;
	}

	public Integer getConfigParams() {
		return configParams;
	}

	public void setConfigParams(Integer configParams) {
		this.configParams = configParams;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Integer createdTs) {
		this.createdTs = createdTs;
	}

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	public Integer getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Integer changedTs) {
		this.changedTs = changedTs;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Integer markAsDelete) {
		this.markAsDelete = markAsDelete;
	}
	
}
