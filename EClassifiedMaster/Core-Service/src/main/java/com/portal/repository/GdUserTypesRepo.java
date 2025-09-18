package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdUserTypes;

public interface GdUserTypesRepo extends CrudRepository<GdUserTypes, Integer>{
	
	@Query("from GdUserTypes gu where gu.markAsDelete = false")      
	List<GdUserTypes> getUserTypes();
	
	@Query("from GdUserTypes gu where gu.userTypeId in (?1) and gu.markAsDelete = false")      
	List<GdUserTypes> getUserTypesList(List<Integer> ids);

}
