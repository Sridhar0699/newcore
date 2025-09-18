package com.portal.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfInboxMaster;

public interface WfInboxMasterRepo extends CrudRepository<WfInboxMaster, String>{

	@Query("from WfInboxMaster wm where wm.inboxMasterId = ?1 and wm.markAsDelete = ?2")      
	List<WfInboxMaster> getWfInboxMasterDetails(String inboxMasterId, boolean markAsDelete);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE wf_inbox_master  SET current_status = ?2, changed_by = ?3, changed_ts = ?4 where inbox_master_id = ?1 and mark_as_delete = false", nativeQuery = true)
	void updateWfInboxMasterData(String inboxMasterId,String currentStatus, Integer userId, Date date);
	
	@Query("from WfInboxMaster wm where wm.inboxMasterId = ?1 and wm.markAsDelete = ?2")      
	WfInboxMaster getWfInboxMaster(String inboxMasterId, boolean markAsDelete);

	@Query("from WfInboxMaster wm where wm.objectRefId = ?1 and wm.markAsDelete = ?2")  
	WfInboxMaster getWfInboxMasterDetailsOnObjRefId(String wfId);
	
	@Query("from WfInboxMaster wm where wm.wfTypeId = ?1 and wm.objectRefId = ?2 and wm.markAsDelete = false")  
	WfInboxMaster getWfInboxMasterDetailsOnWfId(String wfId,String objectRefId);
	
	@Query("from WfInboxMaster wm where  wm.objectRefId = ?1 and wm.markAsDelete = false")  
	WfInboxMaster getWfInboxMasterDetailsOnObjectRefId(String objectRefId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE wf_inbox_master  SET current_status = ?2, changed_by = ?3, changed_ts = ?4 where inbox_master_id = ?1", nativeQuery = true)
	void updateWfInboxMasterDataByMasterId(String inboxMasterId,String currentStatus, Integer userId, Date date);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE wf_inbox_master  SET current_status = ?2, changed_by = ?3, changed_ts = ?4  where inbox_master_id = ?1", nativeQuery = true)
	void updateWfInboxMasterDataByMasterIdRejectCase(String inboxMasterId,String currentStatus, Integer userId, Date date);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE wf_inbox_master  SET current_status = ?2, changed_by = ?3, changed_ts = ?4 , mark_as_delete = ?5 where inbox_master_id = ?1", nativeQuery = true)
	void updateWfInboxMasterDataByMaster(String inboxMasterId,String currentStatus, Integer userId, Date date,boolean markAsDelete);
}
