package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfMaster;

public interface WfMasterRepo extends CrudRepository<WfMaster, String>{
	
	@Query("from WfMaster wm where wm.wfShortId = ?1 and wm.markAsDelete = ?2")      
	List<WfMaster> getWfMasterDetails(String shortId, boolean markAsDelete);
	
	@Query("from WfMaster wm where wm.wfId = ?1 and wm.markAsDelete = false")      
	WfMaster getWfShortIdDetailsByWfRefId(String wfId);
	
	@Query("from WfMaster wm where wm.wfId = ?1 and wm.markAsDelete = false")      
	WfMaster getWfMasterDataByWfId(String wfId);
	
	@Query("from WfMaster wm where wm.wfType = ?1 and wm.markAsDelete = false")      
	WfMaster getWfDetailsByWfType(String wfType);
	
	@Query("from WfMaster wm where wm.wfType = ?1 and wm.isDefault = true and wm.markAsDelete = false")
	WfMaster getDefaultWfDetails(String wfType);
	

}
