package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.da.entities.DaRates;

public interface DaRatesRepo extends CrudRepository<DaRates, String>{

	@Query("from DaRates gda where classifiedAdsType = ?1 and editionId in(?2) and classifiedAdsSubtype = ?3 and sizeId = ?4 and categoryId = ?5 and gda.markAsDelete = false")      
	List<DaRates> getRates(Integer adsType, List<Integer> editions, Integer adsSubtype,Integer size,Integer category);
	
	@Query("from DaRates gda where classifiedAdsType = ?1 and editionId in(?2) and classifiedAdsSubtype = ?3 and sizeId =?4 and categoryId = ?5 and gda.markAsDelete = false ")      
	List<DaRates> getRates1(Integer adsType, List<Integer> editions, Integer adsSubtype,Integer size,Integer category);
	
	@Query("from DaRates cr where cr.classifiedAdsType = ?1 and cr.classifiedAdsSubtype = ?2 and cr.editionId = ?3 and cr.markAsDelete = false")
	List<DaRates> getClfRates(Integer adsType,Integer adsSubtype,Integer editions);
	
	@Query("from DaRates gda where classifiedAdsType = ?1 and editionId =?2 and classifiedAdsSubtype = ?3 and sizeId =?4 and categoryId = ?5 and gda.markAsDelete = false ")      
	List<DaRates> getRatesByAllDetails(Integer adsType,Integer editions, Integer adsSubtype,Integer size,Integer category);
}
