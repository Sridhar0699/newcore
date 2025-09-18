package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.da.entities.DaTemplates;


public interface DaTemplateRepo extends CrudRepository<DaTemplates, String>{
	
	@Query("from DaTemplates dt where templateId in (?1) and markAsDelete = false")
	List<DaTemplates> getTemplatesById(List<String> templateIds);
	
	@Query("from DaTemplates dt where templateId = ?1 and markAsDelete = false")
	DaTemplates getTemplateById(String templateId);
	
	@Query("from DaTemplates where  markAsDelete = false") 
	List<DaTemplates> getAllTemplates();

}
