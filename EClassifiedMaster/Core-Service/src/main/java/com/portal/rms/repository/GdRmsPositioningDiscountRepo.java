package com.portal.rms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdRmsPositioningDiscount;
import com.portal.gd.entities.GdRmsSchemes;

public interface GdRmsPositioningDiscountRepo extends CrudRepository<GdRmsPositioningDiscount, Integer>{

	@Query("from GdRmsPositioningDiscount where id = ?1 and markAsDelete = false")      
	GdRmsPositioningDiscount getPositioningDiscount(Integer id);
	
	
	@Query("from GdRmsPositioningDiscount where UPPER(positioningType) = UPPER(?1) and markAsDelete = false")      
	GdRmsPositioningDiscount getPDByName(String scheme);
}
