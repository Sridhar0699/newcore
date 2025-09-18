package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfRefToType;

public interface WfRefToTypeRepo extends CrudRepository<WfRefToType,String>{

	@Query("from WfRefToType wm where wm.inboxMasterId = ?1 and wm.markAsDelete = false")      
	List<WfRefToType> getWfRefToTypeList(String inboxMasterId);
}
