package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfServiceTasks;

public interface WfServiceTasksRepo extends CrudRepository<WfServiceTasks, String>{

	@Query("from WfServiceTasks ws where ws.wfTypeId = ?1 and ws.serviceType not in (?2) and ws.markAsDelete = false")      
	List<WfServiceTasks> getWfServiceTypesList(String wfTypeId, List<String> serviceTypes);
	
	@Query("from WfServiceTasks ws where ws.serviceId = ?1 and ws.markAsDelete = false")      
	WfServiceTasks getWfServiceTaskDataById(String serviceId);
	
	@Query("from WfServiceTasks ws where ws.wfTypeId = ?1 and ws.serviceType in (?2) and ws.markAsDelete = false")      
	List<WfServiceTasks> getDefaultWfServiceTaksDetails(String wfTypeId,List<String> serviceType);
	
	@Query("from WfServiceTasks ws where ws.wfTypeId = ?1 and ws.markAsDelete = false")      
	List<WfServiceTasks> getWfServiceTypesList1(String wfTypeId);
	
}
