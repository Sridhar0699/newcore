package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.nm.entities.NmNotifications;

public interface NmNotificationsRepo extends CrudRepository<NmNotifications, String>{

	@Query("select count(notificationId) from NmNotifications nn where nn.userId = ?1 and nn.orgId = ?2 and nn.isRead = false and nn.markAsDelete = false")      
	Long getUnReadNotificationCount(Integer userId, String orgId);
	
	@Query("from NmNotifications nn where nn.userId = ?1 and nn.orgId = ?2 and nn.isRead = false and nn.markAsDelete = false")      
	List<NmNotifications> getUnReadNotification(Integer userId, String orgId);
}
