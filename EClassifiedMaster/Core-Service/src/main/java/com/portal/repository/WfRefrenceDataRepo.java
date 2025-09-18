package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfRefrenceData;

public interface WfRefrenceDataRepo extends CrudRepository<WfRefrenceData, String>{

	@Query("from WfRefrenceData wr where wr.wfId = ?1 and wr.markAsDelete = false order by wr.createdTs desc")      
	List<WfRefrenceData> getWfRefDetails(String wfId);
	
	@Query("from WfRefrenceData wr where wr.wfRefId = ?1 and wr.markAsDelete = false")   
	WfRefrenceData getWfRefDataOnRefId(String wfRefId);
}
