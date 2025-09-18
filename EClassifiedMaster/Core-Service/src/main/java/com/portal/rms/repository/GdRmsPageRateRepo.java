package com.portal.rms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdRmsPageRate;

public interface GdRmsPageRateRepo extends CrudRepository<GdRmsPageRate, Integer>{

	@Query("from GdRmsPageRate where editionId = ?1 and pageErpRefCode = ?2 and markAsDelete = false")      
	GdRmsPageRate getPagePercentage(Integer editionId,String pageErpRefCode);
}
