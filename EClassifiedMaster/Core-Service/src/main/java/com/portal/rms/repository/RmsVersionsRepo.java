package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.rms.entity.RmsVersions;

public interface RmsVersionsRepo extends CrudRepository<RmsVersions,Integer>{
	
	@Query("from RmsVersions rv where rv.markAsDelete = false")
	List<RmsVersions> getRmsVersions();

}
