package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.org.entities.OmOrganizations;
import com.portal.wf.entity.WfDocTypes;
import com.portal.wf.entity.WfMasterDocTypes;

public interface WfDocTypesRepo extends CrudRepository<WfDocTypes, String>{

	@Query("from WfDocTypes wm where wm.documentType in (?1) and wm.isDefault = true and wm.markAsDelete = false")
	List<WfDocTypes> getDefaultWfDocumentTypeList(List<Integer> wfTypes);
	
	@Query("from WfDocTypes wm where wm.wfId = ?1 and wm.markAsDelete = false")
	List<WfDocTypes> getWfMasterDocByWfId(String wfId);
	
	@Query("from WfDocTypes wm where wm.wfId = ?1 and wm.markAsDelete = false")
	List<WfDocTypes> getWfMasterDocByWfId(List<String> wfId);
	
	
	@Query(value = "select wd.wf_id,wd.document_type,wd.is_default from wf_doc_types wd inner join wf_master wf on wf.wf_id = wd.wf_id where wd.wf_id in (?1) and wd.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getWfTypedByTypeId(List<String> wfIds);
	
	@Query(value = "select wd.wf_id,wd.document_type,wd.is_default,wf.location from wf_doc_types wd inner join wf_locations wf on wf.wf_id = wd.wf_id where wd.document_type = ?1 and wf.location =?2 and wd.mark_as_delete = false and wf.mark_as_delete = false and wd.is_default = true", nativeQuery = true)      
	List<Object[]> getWfDocTypesandLocations(Integer docId,Integer locationId);
	
	@Query(value = "select wd.wf_id,wd.document_type,wd.is_default,wf.location from wf_doc_types wd inner join wf_locations wf on wf.wf_id = wd.wf_id where wd.document_type in (?1) and wf.location in (?2) and wd.mark_as_delete = false and wf.mark_as_delete = false and wd.is_default = true", nativeQuery = true)      
	List<Object[]> getWfDocTypesandLocations(List<Integer> docId,List<Integer> locationId);
	
	
	
	@Query(value = "select wd.wf_id,wd.document_type,wd.is_default,wf.location from wf_doc_types wd inner join wf_locations wf on wf.wf_id = wd.wf_id inner join wf_master wm on wm.wf_id = wd.wf_id where wd.document_type in (?1) and wf.location in (?2) and wd.mark_as_delete = false and wf.mark_as_delete = false and wm.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getWfDocTypesandLocationsByIds(List<Integer> docId,List<Integer> locationId);
}
