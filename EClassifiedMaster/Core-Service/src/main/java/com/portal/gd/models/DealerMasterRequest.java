package com.portal.gd.models;

import java.util.List;

public class DealerMasterRequest {
	
	private List<String> dealerId;
	
	private List<String> dealerLocationId;
	
	private String action;
	
	private boolean status;
	
	private boolean geoStatus;

	public List<String> getDealerId() {
		return dealerId;
	}

	public void setDealerId(List<String> dealerId) {
		this.dealerId = dealerId;
	}

	public List<String> getDealerLocationId() {
		return dealerLocationId;
	}

	public void setDealerLocationId(List<String> dealerLocationId) {
		this.dealerLocationId = dealerLocationId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getGeoStatus() {
		return geoStatus;
	}

	public void setGeoStatus(boolean geoStatus) {
		this.geoStatus = geoStatus;
	}
	
}
