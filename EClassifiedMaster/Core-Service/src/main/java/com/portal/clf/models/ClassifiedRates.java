package com.portal.clf.models;

public class ClassifiedRates {

	private Double rate = 0.0;
	private Double extraLineRate = 0.0;
	private Integer minLines;
	private Integer editionId;

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getExtraLineRate() {
		return extraLineRate;
	}

	public void setExtraLineRate(Double extraLineRate) {
		this.extraLineRate = extraLineRate;
	}

	public Integer getMinLines() {
		return minLines;
	}

	public void setMinLines(Integer minLines) {
		this.minLines = minLines;
	}

	public Integer getEditionId() {
		return editionId;
	}

	public void setEditionId(Integer editionId) {
		this.editionId = editionId;
	}

}
