package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdRmsEditions;

public interface GdRmsEditionsRepo extends CrudRepository<GdRmsEditions, Integer>{
	
	@Query("from GdRmsEditions where id in (?1) and markAsDelete = false")      
	List<GdRmsEditions> getRmsEditionsList(List<Integer> ids);
	
	@Query("from GdRmsEditions where id =?1 and markAsDelete = false")      
	GdRmsEditions getRmsEditions(Integer ids);

	@Query("from GdRmsEditions where editionType = ?1 and ffpFlag = true and markAsDelete = false")   
	List<GdRmsEditions> getRmsEditionsOnEditionType(Integer editionType);
	
	@Query("from GdRmsEditions where editionType = ?1 and markAsDelete = false")   
	List<GdRmsEditions> getRmsDistrictEditionse(Integer editionType);

	@Query("from GdRmsEditions where id = ?1 and markAsDelete = false") 
	GdRmsEditions getRmsEditionsOnEditionId(Integer editionId);

	@Query("from GdRmsEditions where addType in (?1) and markAsDelete = false") 
	List<GdRmsEditions> getRmsCentralEditions(Integer addType);

	@Query("from GdRmsEditions where addType = ?1 and stateCode = ?2 and markAsDelete = false") 
	List<GdRmsEditions> getGovtStateEditionsonStateCode(Integer addType, String stateCode);
	
	@Query("from GdRmsEditions where UPPER(editionName) = UPPER(?1) and markAsDelete = false") 
	GdRmsEditions getEditionsByName(String editionName);
	
	@Query("from GdRmsEditions where UPPER(editionName) = UPPER(?1) and addType = ?2 and editionType = ?3 and UPPER(erpRefId) = UPPER(?4) and markAsDelete = false") 
	GdRmsEditions getEditions(String editionName,Integer adType,Integer editionType,String erpRefId);

}
