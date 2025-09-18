package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.GdWfTypes;

public interface GdWfTypesRepo extends CrudRepository<GdWfTypes, String>{
	
	@Query("from GdWfTypes gw where gw.markAsDelete = false")      
	List<GdWfTypes> getWfTypesList();
	
	@Query("from GdWfTypes gw where gw.wfTypeId = ?1 and gw.markAsDelete = false")      
	GdWfTypes getWfTypeDataById(String wfTypeId);
	
	@Query("from GdWfTypes gw where gw.wfTypeId in (?1) and gw.markAsDelete = false")      
	List<GdWfTypes> getWfTypeDataByIds(List<String> wfTypeId);
	
	

}
