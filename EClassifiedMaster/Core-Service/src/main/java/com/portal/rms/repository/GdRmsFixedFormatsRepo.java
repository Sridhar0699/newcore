package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdRmsFixedFormats;

public interface GdRmsFixedFormatsRepo extends CrudRepository<GdRmsFixedFormats, Integer>{

	@Query("from GdRmsFixedFormats where markAsDelete = false") 
	List<GdRmsFixedFormats> getAllFixedFormats();
	
	@Query("from GdRmsFixedFormats where erpRefCode in (?1) and markAsDelete = false") 
	List<GdRmsFixedFormats> getFixedFormatsOnErpRefCode(List<String> erpRefcodes);
}
