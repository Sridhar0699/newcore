package com.portal.nm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NmEmailPayload {

	@JsonProperty("sm_name")
	private String sm_name;
	@JsonProperty("qm_doc_num")
	private String qm_doc_num;
	@JsonProperty("qm_doc_rel_date")
	private String qm_doc_rel_date;
	@JsonProperty("service_mtype")
	private String service_mtype;
	@JsonProperty("reagion")
	private String reagion;
	@JsonProperty("reagion_center")
	private String reagion_center;
	@JsonProperty("country")
	private String country;
	@JsonProperty("num_of_vins")
	private String num_of_vins;
	@JsonProperty("target_duration")
	private String target_duration;
	@JsonProperty("req_from_type")
	private String req_from_type;
	@JsonProperty("req_to_type")
	private String req_to_type;
	@JsonProperty("from_mail")
	private String from_mail;
	@JsonProperty("to_mail")
	private String to_mail;
	@JsonProperty("cc_mail")
	private String cc_mail;
	@JsonProperty("bcc_mail")
	private String bcc_mail;
	@JsonProperty("template_name")
	private String template_name;
	@JsonProperty("remarks")
	private String remarks;
	@JsonProperty("general_distributor")
	private String general_distributor;
	@JsonProperty("smType")
	private String smType;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getSm_name() {
		return sm_name;
	}

	public void setSm_name(String sm_name) {
		this.sm_name = sm_name;
	}

	public String getQm_doc_num() {
		return qm_doc_num;
	}

	public void setQm_doc_num(String qm_doc_num) {
		this.qm_doc_num = qm_doc_num;
	}

	public String getQm_doc_rel_date() {
		return qm_doc_rel_date;
	}

	public void setQm_doc_rel_date(String qm_doc_rel_date) {
		this.qm_doc_rel_date = qm_doc_rel_date;
	}

	public String getService_mtype() {
		return service_mtype;
	}

	public void setService_mtype(String service_mtype) {
		this.service_mtype = service_mtype;
	}

	public String getReagion() {
		return reagion;
	}

	public void setReagion(String reagion) {
		this.reagion = reagion;
	}

	public String getReagion_center() {
		return reagion_center;
	}

	public void setReagion_center(String reagion_center) {
		this.reagion_center = reagion_center;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNum_of_vins() {
		return num_of_vins;
	}

	public void setNum_of_vins(String num_of_vins) {
		this.num_of_vins = num_of_vins;
	}

	public String getTarget_duration() {
		return target_duration;
	}

	public void setTarget_duration(String target_duration) {
		this.target_duration = target_duration;
	}

	public String getReq_from_type() {
		return req_from_type;
	}

	public void setReq_from_type(String req_from_type) {
		this.req_from_type = req_from_type;
	}

	public String getReq_to_type() {
		return req_to_type;
	}

	public void setReq_to_type(String req_to_type) {
		this.req_to_type = req_to_type;
	}

	public String getFrom_mail() {
		return from_mail;
	}

	public void setFrom_mail(String from_mail) {
		this.from_mail = from_mail;
	}

	public String getTo_mail() {
		return to_mail;
	}

	public void setTo_mail(String to_mail) {
		this.to_mail = to_mail;
	}

	public String getCc_mail() {
		return cc_mail;
	}

	public void setCc_mail(String cc_mail) {
		this.cc_mail = cc_mail;
	}

	public String getBcc_mail() {
		return bcc_mail;
	}

	public void setBcc_mail(String bcc_mail) {
		this.bcc_mail = bcc_mail;
	}

	public String getGeneral_distributor() {
		return general_distributor;
	}

	public void setGeneral_distributor(String general_distributor) {
		this.general_distributor = general_distributor;
	}

	public String getSmType() {
		return smType;
	}

	public void setSmType(String smType) {
		this.smType = smType;
	}

}
