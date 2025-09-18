package com.portal.gd.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of Configuration values
 * 
 * @author Harika Kancharla
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfConfigValues {

	@JsonProperty("configValues")
	private List<ConfigValues> configValues = null;
	
	@JsonProperty("ttl_cnt")
	private Integer ttl_cnt = null;

	@JsonProperty("pg_size")
	private Integer pg_size = null;

	@JsonProperty("pg_num")
	private Integer pg_num = null;

	/**
	 * @return the configValues
	 */
	public List<ConfigValues> getConfigValues() {
		return configValues;
	}

	/**
	 * @param configValues
	 *            the configValues to set
	 */
	public void setConfigValues(List<ConfigValues> configValues) {
		this.configValues = configValues;
	}

	public Integer getTtl_cnt() {
		return ttl_cnt;
	}

	public void setTtl_cnt(Integer ttl_cnt) {
		this.ttl_cnt = ttl_cnt;
	}

	public Integer getPg_size() {
		return pg_size;
	}

	public void setPg_size(Integer pg_size) {
		this.pg_size = pg_size;
	}

	public Integer getPg_num() {
		return pg_num;
	}

	public void setPg_num(Integer pg_num) {
		this.pg_num = pg_num;
	}
	
	

}
