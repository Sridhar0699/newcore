package com.portal.gd.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of countries
 * 
 * @author Sathish Babu D
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListOfCountries {

	@JsonProperty("countries")
	private List<CountryDetails> countries = new ArrayList<CountryDetails>();

	/**
	 * @return the countries
	 */
	public List<CountryDetails> getCountries() {
		return countries;
	}

	/**
	 * @param countries
	 *            the countries to set
	 */
	public void setCountries(List<CountryDetails> countries) {
		this.countries = countries;
	}

}
