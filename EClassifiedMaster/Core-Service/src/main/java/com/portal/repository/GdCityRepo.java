package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdCity;

public interface GdCityRepo extends CrudRepository<GdCity, Integer>{
	
	@Query(value = "from GdCity gc where gc.id in (?1) and gc.markAsDelete = false")      
	List<GdCity> getCityDetails(List<Integer> id);

}
