package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdClassifiedLanguages;

public interface GdClassifiedLanguageRepo extends CrudRepository<GdClassifiedLanguages, Integer>{

	@Query(value = "from GdClassifiedLanguages gcl where gcl.id = ?1 and gcl.markAsDelete = false")
	GdClassifiedLanguages getLanguageByLangId(Integer langId);
	
	@Query(value = "from GdClassifiedLanguages gcl where gcl.id in (?1) and gcl.markAsDelete = false")
	List<GdClassifiedLanguages> getLanguagesByLangIds(List<Integer> langId);
}
