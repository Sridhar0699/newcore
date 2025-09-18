package com.portal.gd.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.portal.basedao.BaseEntity;

@Entity
@Table(name="gd_rms_number_series")
public class GdRmsNumberSeries extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "type")
	private String type;

	@Column(name = "year")
	private String year;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "series_length")
	private Integer seriesLength;

	@Column(name = "series_starts_with")
	private Integer seriesStartsWith;

	@Column(name = "current_series")
	private BigDecimal currentSeries;
	
	@Column(name = "mark_as_delete")
	private boolean markAsDelete;
	
	@Column(name = "year_format")
	private String yearFormat;
	
	@Column(name="booking_code")
	private  Integer bookingCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getSeriesLength() {
		return seriesLength;
	}

	public void setSeriesLength(Integer seriesLength) {
		this.seriesLength = seriesLength;
	}

	public Integer getSeriesStartsWith() {
		return seriesStartsWith;
	}

	public void setSeriesStartsWith(Integer seriesStartsWith) {
		this.seriesStartsWith = seriesStartsWith;
	}

	public BigDecimal getCurrentSeries() {
		return currentSeries;
	}

	public void setCurrentSeries(BigDecimal currentSeries) {
		this.currentSeries = currentSeries;
	}

	public boolean isMarkAsDelete() {
		return markAsDelete;
	}

	public void setMarkAsDelete(boolean markAsDelete) {
		this.markAsDelete = markAsDelete;
	}

	public String getYearFormat() {
		return yearFormat;
	}

	public void setYearFormat(String yearFormat) {
		this.yearFormat = yearFormat;
	}

	public Integer getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(Integer bookingCode) {
		this.bookingCode = bookingCode;
	}
}
