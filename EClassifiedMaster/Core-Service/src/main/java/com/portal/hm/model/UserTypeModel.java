package com.portal.hm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserTypeModel {

	@JsonProperty("user_type_id")
	private Integer userTypeId;

	@JsonProperty("type_desc")
	private String typeDesc;
	
	@JsonProperty("type_short_id")
	private String typeShortId;

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getTypeShortId() {
		return typeShortId;
	}

	public void setTypeShortId(String typeShortId) {
		this.typeShortId = typeShortId;
	}
	
	

}
