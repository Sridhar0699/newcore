package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfRefToUsers;

public interface WfRefToUsersRepo extends CrudRepository<WfRefToUsers, String>{
	
	@Query("from WfRefToUsers wm where wm.wfInboxId = ?1 and wm.markAsDelete = false")      
	List<WfRefToUsers> getRefToUsersList(String wfInboxId);

}
