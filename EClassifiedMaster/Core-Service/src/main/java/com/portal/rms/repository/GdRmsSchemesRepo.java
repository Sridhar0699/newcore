package com.portal.rms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdRmsSchemes;

public interface GdRmsSchemesRepo extends CrudRepository<GdRmsSchemes, Integer>{

	@Query("from GdRmsSchemes where id = ?1 and markAsDelete = false")      
	GdRmsSchemes getSchemeDetails(Integer schemeId);
	
	
	@Query("from GdRmsSchemes where UPPER(scheme) = UPPER(?1) and markAsDelete = false")      
	GdRmsSchemes getSchemesByName(String scheme);
}
