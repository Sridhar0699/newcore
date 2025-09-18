package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfMasterDocTypes;

public interface WfMasterDocTypesRepo extends CrudRepository<WfMasterDocTypes, String> {

	@Query(value = "select wd.wf_id,wd.wf_type,wd.is_default from wf_master_doc_types wd inner join wf_master wf on wf.wf_id = wd.wf_id where wd.wf_id in (?1)", nativeQuery = true)      
	List<Object[]> getWfTypedByTypeId(List<String> wfIds);
	
	@Query("from WfMasterDocTypes wm where wm.wfType = ?1 and wm.isDefault = true and wm.markAsDelete = false")
	WfMasterDocTypes getWfTypeOnType(String wfType);

	@Query("from WfMasterDocTypes wm where wm.wfType in (?1) and wm.isDefault = true and wm.markAsDelete = false")
	List<WfMasterDocTypes> getDefaultWfDataList(List<String> wfTypes);

	@Query("from WfMasterDocTypes wm where wm.wfId = ?1 and wm.markAsDelete = false")
	List<WfMasterDocTypes> getWfMasterDocByWfId(String wfId);
	
	List<WfMasterDocTypes> findByWfIdAndWfTypeId(String wfId, String wfTypeId);
	
	@Query("from WfMasterDocTypes wm where wm.wfId = ?1 and wm.markAsDelete = false")
	WfMasterDocTypes getWfTypeByWfId(String wfId);
	
	
	@Query(value = "select  wd.wf_id,wd.is_default,wf.location from wf_master_doc_types wd inner join wf_locations wf on wf.wf_id = wd.wf_id where wd.mark_as_delete = false and wf.mark_as_delete = false and wd.is_default = true and wf.location in (?1)" , nativeQuery = true)
	List<Object[]> getWflocationsOnLocation(List<Integer> location);

	@Query(value = "select  wd.wf_id,wd.is_default,wf.location from wf_master_doc_types wd inner join wf_locations wf on wf.wf_id = wd.wf_id where wd.mark_as_delete = false and wf.mark_as_delete = false and wd.is_default = true and wd.wf_type = ?1 and wf.location in (?2)" , nativeQuery = true)
	List<Object[]> getDefaultWfIdOnLocation(String type, Integer location);
	

}
