package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.activity.entities.LoginHistory;

public interface LoginHistoryRepository extends CrudRepository<LoginHistory, Integer> {

	@Query(value = "select login_id, action_ts,ip_address,browser_name,browser_version from login_history as a WHERE action_ts = (SELECT MAX(action_ts) from login_history as b where a.login_id = b.login_id and action='LOGIN' and a.login_id in (select user_name from oauth_access_token t)) and DATE(`action_ts`) = CURDATE() order by a.action_ts desc", nativeQuery = true)
	List<Object[]> getOnlineUsers();

	@Query(value = "select login_id, action_ts,ip_address,browser_name,browser_version from login_history as a WHERE login_id = ?1 and  action='LOGIN' and  action_ts BETWEEN ?2 and ?3  ORDER BY action_ts desc", nativeQuery = true)
	List<Object[]> getUserLoginHistory(Integer loginId, String frmDate, String toDate);
	
	@Query(value = "select * from oauth_access_token where user_name = ?1 " , nativeQuery = true)
	List<Object[]> getLoginSessionByUserName(String userName);
}
