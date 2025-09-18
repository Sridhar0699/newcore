package com.portal.clf.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "clf_publish_dates")
public class ClfPublishDates extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "publish_date_id")
	private String publishDateId;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private ClfOrderItems clfOrderItems;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "publish_date")
	private Date publishDate;
	
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
	
	@Column(name = "download_status")
	private Boolean downloadStatus;

	public String getPublishDateId() {
		return publishDateId;
	}

	public void setPublishDateId(String publishDateId) {
		this.publishDateId = publishDateId;
	}

	public ClfOrderItems getClfOrderItems() {
		return clfOrderItems;
	}

	public void setClfOrderItems(ClfOrderItems clfOrderItems) {
		this.clfOrderItems = clfOrderItems;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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

	public Integer getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Integer changedBy) {
		this.changedBy = changedBy;
	}

	public Date getChangedTs() {
		return changedTs;
	}

	public void setChangedTs(Date changedTs) {
		this.changedTs = changedTs;
	}

	public Boolean getMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(Boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Boolean getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(Boolean downloadStatus) {
		this.downloadStatus = downloadStatus;
	}
	
}
