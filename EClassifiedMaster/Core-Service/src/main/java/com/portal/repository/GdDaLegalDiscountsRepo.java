package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdDaLegalDiscounts;

public interface GdDaLegalDiscountsRepo extends CrudRepository<GdDaLegalDiscounts, Integer>{

	@Query("from GdDaLegalDiscounts ld where ld.editionErpCode in (?1) and ld.markAsDelete = false ")      
	List<GdDaLegalDiscounts> getLegalDiscountPercentage(List<String> erpEditionCodes);
	
	
	@Query("from GdDaLegalDiscounts ld where ld.editionErpCode in (?1)and ld.categoryId = ?2 and ld.markAsDelete = false ")      
	List<GdDaLegalDiscounts> getLegalDiscountPercentageByCategory(List<String> erpEditionCodes,Integer categoryId);
	
	@Query("SELECT DISTINCT  editionErpCode FROM GdDaLegalDiscounts WHERE markAsDelete = false ORDER BY editionErpCode")
	List<String> getAllLegalDiscountErpCodes();
}
