package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdAccessObjects;

public interface GdAccessObjectsRepository extends CrudRepository<GdAccessObjects, String>{

	@Query("from GdAccessObjects gda where gda.markAsDelete = false")      
	List<GdAccessObjects> getAccessObjects();
	
	@Query("from GdAccessObjects gda where gda.accessObjId=?1 and gda.markAsDelete = false")      
	GdAccessObjects getAccessObjectsById(String accessObjId);
	
}
