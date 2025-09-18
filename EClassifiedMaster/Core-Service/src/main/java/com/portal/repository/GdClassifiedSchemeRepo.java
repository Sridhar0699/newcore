package com.portal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdClassifiedSchemes;

public interface GdClassifiedSchemeRepo extends CrudRepository<GdClassifiedSchemes, Integer>{

	@Query("from GdClassifiedSchemes gcs where gcs.id = ?1 and gcs.markAsDelete = false ")      
	GdClassifiedSchemes getSchemeDetails(Integer id);

}
