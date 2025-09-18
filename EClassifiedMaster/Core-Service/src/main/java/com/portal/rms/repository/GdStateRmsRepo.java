package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdState;

public interface GdStateRmsRepo extends CrudRepository<GdState, Integer>{

	@Query(value = "from GdState gs where gs.stateCode in (?1) and gs.markAsDelete = false")      
	List<GdState> getStateDetails(List<String> stateCode);
}
