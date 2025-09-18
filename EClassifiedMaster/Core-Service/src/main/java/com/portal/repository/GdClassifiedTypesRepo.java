package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdClassifiedTypes;

public interface GdClassifiedTypesRepo extends CrudRepository<GdClassifiedTypes, Integer>{

	@Query("from GdClassifiedTypes gda where gda.markAsDelete = false")      
	List<GdClassifiedTypes> getClassifiedTypes();
}
