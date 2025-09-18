package com.portal.rms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdRmsAnnexure;

public interface GdRmsAnnexureRepo extends CrudRepository<GdRmsAnnexure, Integer>{

	@Query("from GdRmsAnnexure where editionId = ?1 and classifiedAdsSubtype = ?2 and schemeId = ?3 and positionInstErpRefId = ?4 and markAsDelete = false")      
	GdRmsAnnexure getSpecialRate(Integer editionId,Integer adsType,Integer schemeId,String positioninstErpRefId);
	
	@Query("from GdRmsAnnexure where editionId = ?1 and classifiedAdsSubtype = ?2 and positionInstErpRefId = ?3 and schemeId is null and markAsDelete = false")      
	GdRmsAnnexure getSpecialRateWithoutScheme(Integer editionId,Integer adsType,String positionInstErpRefId);

	@Query("from GdRmsAnnexure where id = ?1 and markAsDelete = false")      
	GdRmsAnnexure getSpecialRateById(Integer id);
}
