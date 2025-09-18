package com.portal.nm.model;

import java.util.List;

public class NmInboxCustomObject_ {
	private int total_count;
	private String message;
	private List<NmInboxTo> listWfInboxTo;

	public List<NmInboxTo> getListWfInboxModel() {
		return listWfInboxTo;
	}

	public void setListWfInboxModel(List<NmInboxTo> listWfInboxModel) {
		this.listWfInboxTo = listWfInboxModel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

}
