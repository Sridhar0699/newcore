package com.portal.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfInboxMasterDetails;

public interface WfInboxMasterDetailsRepo extends CrudRepository<WfInboxMasterDetails, String>{
	
	@Query("from WfInboxMasterDetails wm where wm.wfInboxId = ?1 and wm.markAsDelete = ?2")      
	List<WfInboxMasterDetails> getWfInboxMasterDetailsData(String inboxId, boolean markAsDelete);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE wf_inbox_master_details  SET comments = ?1,  status = ?2, changed_by = ?3, changed_ts = ?4 where wf_inbox_id = ?5 and mark_as_delete = false", nativeQuery = true)
	void updateInboxMasterDetails(String commets,String status, Integer userId, Date changedTs, String inboxMasterId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE wf_inbox_master_details  SET history_flag = ?1 where inbox_master_id = ?2 and wf_inbox_id in (?3) and mark_as_delete = false", nativeQuery = true)
	void updateWfInboxHistoryFlag(Boolean flag, String inboxMasterId,List<String> inboxIds);
	
	@Query("from WfInboxMasterDetails wm where wm.inboxMasterId = ?1 and wm.markAsDelete = false")
	List<WfInboxMasterDetails> getInboxMasterDetails(String inboxMasterId);
	
	@Query("from WfInboxMasterDetails wm where wm.inboxMasterId = ?1 and wm.markAsDelete = false and wm.status != 'APPROVED'")
	WfInboxMasterDetails getInboxMasterDetailsOnMasterId(String inboxMasterId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE wf_inbox_master_details  SET mark_as_delete = ?1 where inbox_master_id = ?2 ", nativeQuery = true)
	void updateWfInboxMasterDetils(Boolean flag, String inboxMasterId);
	
	@Query("from WfInboxMasterDetails wm where wm.accessKey = ?1 and wm.markAsDelete = false")
	WfInboxMasterDetails validateInboxAccessKey(String accessKey);
	

}
