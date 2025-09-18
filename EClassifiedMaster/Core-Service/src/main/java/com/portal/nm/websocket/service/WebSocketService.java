package com.portal.nm.websocket.service;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.nm.model.Notifications;

public interface WebSocketService {
	public GenericApiResponse getNotificationUnreadCount(GenericRequestHeaders requestHeaders);
	public boolean getUnreadNotificationCounts(Notifications notification);
}
