package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdFixedFormatCodes;

public interface GdFixedFormatCodesRepo extends CrudRepository<GdFixedFormatCodes, Integer>{

	@Query("from GdFixedFormatCodes where markAsDelete = false")      
	List<GdFixedFormatCodes> getFixedFormatCodeList();
}
