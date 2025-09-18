package com.portal.gd.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "booking_units")
public class BookingUnits extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name ="booking_location")
	private String bookingLocation;
	
	@Column(name = "booking_description")
	private String bookingDescription;
	
	@Column(name = "booking_code")
	private Integer bookingCode;
	
	@Column(name = "profit_center")
	private String profitCenter;
	
	@Column(name = "gi_account")
	private String giAccount;
	
	@Column(name = "sales_office")
	private String salesOffice;
	
	@Column(name = "sold_to_party")
	private String soldToParty;
	
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
	
	@Column(name = "state_code")
	private String stateCode;
	
	@Column(name = "payment_child_id")
	private String paymentChildId;

	public String getPaymentChildId() {
		return paymentChildId;
	}

	public void setPaymentChildId(String paymentChildId) {
		this.paymentChildId = paymentChildId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookingLocation() {
		return bookingLocation;
	}

	public void setBookingLocation(String bookingLocation) {
		this.bookingLocation = bookingLocation;
	}

	public String getBookingDescription() {
		return bookingDescription;
	}

	public void setBookingDescription(String bookingDescription) {
		this.bookingDescription = bookingDescription;
	}

	public Integer getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(Integer bookingCode) {
		this.bookingCode = bookingCode;
	}

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public String getGiAccount() {
		return giAccount;
	}

	public void setGiAccount(String giAccount) {
		this.giAccount = giAccount;
	}

	public String getSalesOffice() {
		return salesOffice;
	}

	public void setSalesOffice(String salesOffice) {
		this.salesOffice = salesOffice;
	}

	public String getSoldToParty() {
		return soldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
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
}
