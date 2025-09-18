package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdEditionType;

public interface GdClassifiedEditionTypeRepo  extends CrudRepository<GdEditionType, Integer> {
	
	@Query(value="from GdEditionType ge where ge.markAsDelete=false")
	List<GdEditionType> findAll();

}
