package com.portal.clf.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name = "billdesk_webhook_logs")
public class BillDeskWebhookLogs extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "encoded_request")
    private String encodedRequest;
	
	@Column(name = "created_ts")
	private Date createdTs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEncodedRequest() {
		return encodedRequest;
	}

	public void setEncodedRequest(String encodedRequest) {
		this.encodedRequest = encodedRequest;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}
	
	

}
