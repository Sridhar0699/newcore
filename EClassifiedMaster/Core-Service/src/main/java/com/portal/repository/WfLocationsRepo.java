package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfLocations;

public interface WfLocationsRepo extends CrudRepository<WfLocations, String>{
	
	@Query("from WfLocations wm where wm.wfId = ?1 and wm.markAsDelete = false")
	List<WfLocations> getLocationsByWfId(String wfId);
	
	@Query(value = "select wd.wf_id,wd.location from wf_locations wd inner join wf_master wf on wf.wf_id = wd.wf_id where wd.wf_id in (?1) and wd.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getWfTypedByTypeId(List<String> wfIds);

}
