package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfUserInbox;

public interface WfUserInboxRepo extends CrudRepository<WfUserInbox, String>{
	
	@Query("from WfUserInbox wi where wi.inboxMasterId = ?1 and wi.wfInboxId = ?2 and wi.markAsDelete = false")      
	List<WfUserInbox> getWfUserInboxList(String inboxMasterId, String wfInboxId);
	
	@Query("from WfUserInbox wi where wi.wfInboxId = ?1 and wi.markAsDelete = false") 
	List<WfUserInbox> getWfUserInboxOnWfInbId(String wfInboxId);
	
	@Query("from WfUserInbox wi where wi.inboxMasterId = ?1 and wi.userId = ?2 and wi.markAsDelete = false")      
	WfUserInbox getWfUserInboxListByUserId(String inboxMasterId, Integer userId);
	
	@Query("from WfUserInbox wi where wi.inboxMasterId = ?1  and wi.markAsDelete = false")      
	List<WfUserInbox> getWfUserInboxListByInboxMasterId(String inboxMasterId);

}
