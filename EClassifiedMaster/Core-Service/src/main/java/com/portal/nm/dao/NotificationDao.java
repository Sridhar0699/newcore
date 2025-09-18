package com.portal.nm.dao;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.portal.nm.model.GroupCount;
import com.portal.nm.model.Notifications;

public interface NotificationDao {

	public boolean createNotifications(Notifications payload);

	public boolean updateNotificationByNotifiId(Map<String, Object> payload, String notificationId);
	
	public List<Notifications> getNotifications(@NotNull Notifications payload);
	
	public boolean markAllNotificationsAsRead(Notifications notificationCommonModelTo);
	
	public List<Map<String,String>> getGroupCounts(@NotNull Notifications payload);
	
}
