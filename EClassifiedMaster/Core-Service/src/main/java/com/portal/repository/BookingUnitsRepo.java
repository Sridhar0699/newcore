package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.BookingUnits;

public interface BookingUnitsRepo extends CrudRepository<BookingUnits, Integer>{

	@Query("from BookingUnits us where bookingCode=?1  and us.markAsDelete = false ")      
	BookingUnits getBookingUnitById(Integer id);
	
	@Query("from BookingUnits us where bookingCode in (?1)  and us.markAsDelete = false ") 
	List<BookingUnits> getBookingUnitsByBookingCode(List<Integer> bookingCodes);
}
