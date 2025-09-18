package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdRmsPagePositions;

public interface GdRmsPagePositionsRepo extends CrudRepository<GdRmsPagePositions, Integer>{

	@Query("from GdRmsPagePositions where id = ?1 and markAsDelete = false")      
	GdRmsPagePositions getPagePosition(Integer id);

	@Query("from GdRmsPagePositions where editionType = ?1 and markAsDelete = false")   
	List<GdRmsPagePositions> getPagePosionOnEditionType(Integer editionType);
	
	@Query("from GdRmsPagePositions where UPPER(pageName) = UPPER(?1) and markAsDelete = false")      
    GdRmsPagePositions getPagePositionsOnPageName(String pageName);
}
