package com.portal.da.models;

public class DisplayAdsModel {

	private Double grandTotal;
	private Double commission;
	private String createdTs;
	private Integer totalOrders;
	private Integer setteldOrders;
	private Integer unSetteldOrders;
	private Double setteldAmount;
	private Double unSetteldAmount;
	private Double overAllTotal;
	private Double totalCommission;
	private Double pendingCommition;
	public Double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public String getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(String createdTs) {
		this.createdTs = createdTs;
	}
	public Integer getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
	}
	public Integer getSetteldOrders() {
		return setteldOrders;
	}
	public void setSetteldOrders(Integer setteldOrders) {
		this.setteldOrders = setteldOrders;
	}
	public Double getSetteldAmount() {
		return setteldAmount;
	}
	public void setSetteldAmount(Double setteldAmount) {
		this.setteldAmount = setteldAmount;
	}
	public Double getUnSetteldAmount() {
		return unSetteldAmount;
	}
	public void setUnSetteldAmount(Double unSetteldAmount) {
		this.unSetteldAmount = unSetteldAmount;
	}
	public Integer getUnSetteldOrders() {
		return unSetteldOrders;
	}
	public void setUnSetteldOrders(Integer unSetteldOrders) {
		this.unSetteldOrders = unSetteldOrders;
	}
	public Double getOverAllTotal() {
		return overAllTotal;
	}
	public void setOverAllTotal(Double overAllTotal) {
		this.overAllTotal = overAllTotal;
	}
	public Double getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(Double totalCommission) {
		this.totalCommission = totalCommission;
	}
	public Double getPendingCommition() {
		return pendingCommition;
	}
	public void setPendingCommition(Double pendingCommition) {
		this.pendingCommition = pendingCommition;
	}
	
	
}
