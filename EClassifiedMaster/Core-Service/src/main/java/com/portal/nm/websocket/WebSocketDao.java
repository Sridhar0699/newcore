package com.portal.nm.websocket;

import com.portal.nm.model.Notifications;

public interface WebSocketDao {
	public long getUnreadNotificationCounts(Notifications notification);
	public long getPendingCartCount(Notifications notification);
	public long getDisplayPendingCartCount(Notifications notification);

}
