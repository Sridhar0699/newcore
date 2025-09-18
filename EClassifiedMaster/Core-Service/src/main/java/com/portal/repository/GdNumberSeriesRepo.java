package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdNumberSeries;

public interface GdNumberSeriesRepo extends CrudRepository<GdNumberSeries, String>{

	@Query("from GdNumberSeries where type = ?1 and markAsDelete = false")      
	List<GdNumberSeries> getNumberSeriesByType(String type);
	
	@Query("from GdNumberSeries where bookingCode = ?1 and markAsDelete = false")      
	List<GdNumberSeries> getNumberSeriesByBookingCode(Integer bookingCode);
	
}
