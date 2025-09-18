package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdAccessObjects;

public interface GdAccessObjectsRepo extends CrudRepository<GdAccessObjects, String>{

	@Query("from GdAccessObjects gda where gda.markAsDelete = false")      
	List<GdAccessObjects> getAccessObjects();
	
}
