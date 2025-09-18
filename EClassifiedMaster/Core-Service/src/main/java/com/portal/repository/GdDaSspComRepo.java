package com.portal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdDaSspCom;

public interface GdDaSspComRepo extends CrudRepository<GdDaSspCom, Integer>{

	@Query("from GdDaSspCom gds where editionType = ?1 and categoryType = ?2 and gds.markAsDelete = false")      
	GdDaSspCom getCommissionOnCategory(Integer editionType,Integer categoryType);

}
