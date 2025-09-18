package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdRmsMultipleDiscountVal;

public interface GdRmsMultipleDiscountValRepo extends CrudRepository<GdRmsMultipleDiscountVal, Integer>{

	@Query("from GdRmsMultipleDiscountVal where erpEditionCode = ?1 and erpPositionInstCode = ?2 and erpSchemeCode = ?3 and markAsDelete = false") 
	List<GdRmsMultipleDiscountVal> getMultiDiscountValidationWithScheme(String erpEditionCode, String erpPositionInstCode, String erpSchemeCode);
	
	@Query("from GdRmsMultipleDiscountVal where erpEditionCode = ?1 and erpPositionInstCode = ?2 and markAsDelete = false") 
	List<GdRmsMultipleDiscountVal> getMultiDiscountValidationWithoutScheme(String erpEditionCode, String erpPositionInstCode);

	@Query("from GdRmsMultipleDiscountVal where erpEditionCode = ?1 and erpPositionInstCode = ?2 and schemeId = ?3 and markAsDelete = false") 
	GdRmsMultipleDiscountVal getMultipleDiscountVal(String erpEditionCode, String erpPositionInstCode,
			Integer schemeId);
	@Query("from GdRmsMultipleDiscountVal where id = ?1 and markAsDelete = false")
	GdRmsMultipleDiscountVal getMultipleDisById(Integer id);


}
