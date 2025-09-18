package com.portal.nm.websocket.service;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.nm.model.Notifications;
import com.portal.nm.service.NotificationsServiceImpl;
import com.portal.nm.websocket.WebSocketDaoImpl;

@Service
public class WebSocketServiceImpl implements WebSocketService {

	private static final Logger logger = Logger.getLogger(NotificationsServiceImpl.class);

	@Autowired
	private Environment prop;
	
	@Autowired
	private WebSocketDaoImpl webSocketDao;
	
	@Autowired
	private SimpMessagingTemplate template;

	public GenericApiResponse getNotificationUnreadCount(GenericRequestHeaders requestHeaders) {
		String METHOD_NAME = "getNotificationUnreadCounts";
		logger.info("Entered into the method :: " + METHOD_NAME);

		GenericApiResponse apiResp = new GenericApiResponse();
		Notifications notification = new Notifications();
		try {
			if (requestHeaders != null) {
				notification.setOrgId(requestHeaders.getOrgId());
				notification.setUserId(requestHeaders.getLoggedUser().getUserId());
			}
			boolean notifications = getUnreadNotificationCounts(notification);
			if (notifications) {
				apiResp.setStatus(0);
				apiResp.setMessage(prop.getProperty("NOTIFI_UNREAD_COUNT_001"));
			} else {
				apiResp.setStatus(1);
				apiResp.setMessage(prop.getProperty("NOTIFI_UNREAD_COUNT_002"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Creating Uread Notification Counts : " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResp;
	}
	
	@Override
	public boolean getUnreadNotificationCounts(Notifications notification) {
		String METHOD_NAME = "getUnreadNotificationCounts";
		logger.info("Entered into the method..Cloud-Service :: "+METHOD_NAME);
		boolean flag = false;
		try {
			if (notification != null) {
				long notifications = webSocketDao.getUnreadNotificationCounts(notification);
				if (notifications != 0) {
					// Push notifications to front-end
					template.convertAndSend("/eclassified/notification", notifications);
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting unread Notification Counts Cloud-Service :: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method Cloud-Service :: " + METHOD_NAME);
		return flag;
	}

}
