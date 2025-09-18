package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.rms.entity.UserLoginHistory;

public interface UserLoginHistoryRepo extends CrudRepository<UserLoginHistory,Integer>{
	
//	@Query(value="select ulh.user_id,ulh.entry_time,ulh.action,ulh.user_type_id,ulh.logon_id,ulh.login_history_id from user_login_history ulh ORDER BY ulh.entry_time DESC",nativeQuery = true)
//	public List<UserLoginHistory> getUserLoginHistory();
	
	@Query(value="select ulh.* from user_login_history ulh ORDER BY ulh.entry_time DESC",nativeQuery = true)
	public List<UserLoginHistory> getUserLoginHistory();

}
