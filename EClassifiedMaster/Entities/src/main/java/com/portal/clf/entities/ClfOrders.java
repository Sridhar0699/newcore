package com.portal.clf.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "clf_orders")
public class ClfOrders extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "user_type_id")
	private Integer userTypeId;
	
	@Column(name = "order_status")
	private String orderStatus;
	
	@Column(name = "payment_status")
	private String paymentStatus;
	
	@OneToMany(mappedBy = "clfOrders", fetch = FetchType.LAZY)
	private Set<ClfOrderItems> clfOrderItems;
	
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
	
	@Column(name = "booking_unit")
	private Integer bookingUnit;
	
	@Column(name = "sync_status")
	private String syncStatus;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "edition_type")
	private Integer editionType;
	
	@Column(name = "order_type")
	private Integer orderType;
	
	@Column(name = "attached_id")
	private String attachedId;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "rms_order_status")
	private String rmsOrderStatus;
	
	@Column(name = "no_of_levels")
	private Integer noOfLevels;
	
	@Column(name = "lat")
	private String lat;
	
	@Column(name = "lang")
	private String lang;
	
	@Column(name = "wf_id")
	private String wfId;
	
	public String getAttachedId() {
		return attachedId;
	}

	public void setAttachedId(String attachedId) {
		this.attachedId = attachedId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getEditionType() {
		return editionType;
	}

	public void setEditionType(Integer editionType) {
		this.editionType = editionType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Set<ClfOrderItems> getClfOrderItems() {
		return clfOrderItems;
	}

	public void setClfOrderItems(Set<ClfOrderItems> clfOrderItems) {
		this.clfOrderItems = clfOrderItems;
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

	public Integer getBookingUnit() {
		return bookingUnit;
	}

	public void setBookingUnit(Integer bookingUnit) {
		this.bookingUnit = bookingUnit;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRmsOrderStatus() {
		return rmsOrderStatus;
	}

	public void setRmsOrderStatus(String rmsOrderStatus) {
		this.rmsOrderStatus = rmsOrderStatus;
	}

	public Integer getNoOfLevels() {
		return noOfLevels;
	}

	public void setNoOfLevels(Integer noOfLevels) {
		this.noOfLevels = noOfLevels;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	
}
