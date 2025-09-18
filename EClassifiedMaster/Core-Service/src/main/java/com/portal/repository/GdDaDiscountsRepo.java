package com.portal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdDaDiscounts;

public interface GdDaDiscountsRepo extends CrudRepository<GdDaDiscounts, Integer>{

	@Query("from GdDaDiscounts dd where dd.categoryId = ?1 and dd.editionType = ?2 and dd.markAsDelete = false ")      
	GdDaDiscounts getDiscountPercentage(Integer categoryId,Integer editionType);
}
