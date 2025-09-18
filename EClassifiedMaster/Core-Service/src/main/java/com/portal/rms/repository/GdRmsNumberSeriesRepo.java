package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdNumberSeries;
import com.portal.gd.entities.GdRmsNumberSeries;

public interface GdRmsNumberSeriesRepo extends CrudRepository<GdRmsNumberSeries, String> {
	
	@Query("from GdRmsNumberSeries where bookingCode = ?1 and markAsDelete = false")      
	List<GdRmsNumberSeries> getNumberSeriesByBookingCode(Integer bookingCode);

}
	