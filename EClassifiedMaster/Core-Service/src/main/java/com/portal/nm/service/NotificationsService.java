package com.portal.nm.service;

import java.util.Map;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.nm.model.Notifications;

public interface NotificationsService {

	public void createNotifications(GenericRequestHeaders requestHeaders, Map<String, Object> payload);

	public GenericApiResponse getNotfications(GenericRequestHeaders requestHeaders,Notifications payload);
	
	public GenericApiResponse updateNotifiBasedOnNotifiId(GenericRequestHeaders requestHeaders, String notificationId);

	public GenericApiResponse markAllNotificationsAsRead(GenericRequestHeaders requestHeaders,Notifications payload);
	
	public GenericApiResponse getNotifiGroupCounts(GenericRequestHeaders requestHeaders,Notifications payload);
	
}
