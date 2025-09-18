package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "GD_SMTYPE")
public class GdSmtype extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GdSmtype_generator")
	@SequenceGenerator(name = "GdSmtype_generator", sequenceName = "GD_SMTYPE_SEQ", allocationSize = 1)
	@Column(name = "SMTYPE_ID")
	private Integer smtypeId;

	@Column(name = "SMTYPE_NAME")
	private String smTypeName;

	@Column(name = "MARK_AS_DELETE")
	private Integer markAsDelete;

	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TS")
	private Date createdTs;

	@Column(name = "UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_TS")
	private Date updatedTs;

	public Integer getSmtypeId() {
		return smtypeId;
	}

	public void setSmtypeId(Integer smtypeId) {
		this.smtypeId = smtypeId;
	}

	public String getSmTypeName() {
		return smTypeName;
	}

	public void setSmTypeName(String smTypeName) {
		this.smTypeName = smTypeName;
	}

	public Integer getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Integer markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

}
