package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GDClassifiedTemplates;

public interface GDClassifiedTemplatesRepo extends CrudRepository<GDClassifiedTemplates, String>{
	
	@Query("from GDClassifiedTemplates ct where langId=?1 and ct.markAsDelete = false ")      
	List<GDClassifiedTemplates> getTemplatesDetails(Integer id);

}
