package com.portal.nm.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.constants.GeneralConstants;
import com.portal.gd.dao.GlobalDataDao;
import com.portal.nm.dao.NotificationDaoImpl;
import com.portal.nm.model.Notifications;
import com.portal.nm.websocket.service.WebSocketServiceImpl;
import com.portal.repository.UmUsersRepository;
import com.portal.security.util.LoggedUserContext;
import com.portal.send.models.EmailsTo;
import com.portal.send.service.SendMessageService;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;
import com.portal.user.entities.UmUsers;

@Service
public class NotificationsServiceImpl implements NotificationsService {

	@Autowired
	private NotificationDaoImpl notificationDao;

	@Autowired
	private WebSocketServiceImpl webSocketService;

	@Autowired
	private NotificationDaoImpl nofifDao;

	@Autowired
	private Environment prop;

	@Autowired
	CommonService commonService;

	@Autowired
	private UmUsersRepository umUsersRepository;
	
	@Autowired(required = true)
	private SendMessageService sendService;
	
	@Autowired(required = true)
	private SettingDao settingDao;
	
	@Autowired(required = true)
	private LoggedUserContext userContext;
	
	@Autowired
	private GlobalDataDao globalDataDao;
	
	private static final Logger logger = Logger.getLogger(NotificationsServiceImpl.class);

	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public void createNotifications(GenericRequestHeaders requestHeaders, Map<String, Object> payload) {
		logger.info("Entered into the method: createNotifications()");
		String METHOD_NAME = "createNotifications";

		// TODO Auto-generated method stub
		List<Notifications> notifiList = new ArrayList<Notifications>();
		Notifications notificationCommonModelTo = new Notifications();
		String firstName = requestHeaders.getLoggedUser().getFirstName();
		String lastName = requestHeaders.getLoggedUser().getLastName();
		String userName = null;
		try {
			if (requestHeaders.getOrgId() != null && requestHeaders.getLoggedUser().getUserId() != null) {
				notificationCommonModelTo.setOrgId(requestHeaders.getOrgId());
				notificationCommonModelTo.setUserId(requestHeaders.getLoggedUser().getUserId());
				notificationCommonModelTo.setOrgOpuId(requestHeaders.getOrgOpuId());
				if (notificationCommonModelTo.getUserId() != null) {
					if (firstName != null && lastName != null) {
						userName = firstName + " " + lastName;
						userName = userName.equalsIgnoreCase("") ? "-" : userName;
					} else {
						List<UmUsers> users = umUsersRepository.getUserByUserId(notificationCommonModelTo.getUserId(),
								false);
						if (!users.isEmpty()) {
							UmUsers user = users.get(0);
							firstName = user.getFirstName();
							lastName = user.getLastName() != null ? user.getLastName() : "";
							userName = firstName + " " + lastName;
							userName = userName.equalsIgnoreCase("") ? "-" : userName;
						}
					}
				}
				notificationCommonModelTo.setUserName(userName);
				if (payload != null) {
					notificationCommonModelTo.setObjectRefId(payload.get("docPkId") + "");
					notificationCommonModelTo.setDocId(payload.get("docId") + "");
				}
				Map data = new HashMap();
				switch (payload.get("action") + "") {
				case GeneralConstants.CREATE_ACTION:
					notificationCommonModelTo.setNotificationMessage(MessageFormat.format(
							prop.getProperty(payload.get("ACTION_NAME") + "_ACTION"), firstName, lastName));
					notifiList.add(notificationCommonModelTo);
					break;
				case GeneralConstants.UPDATE_ACTION:
					notificationCommonModelTo.setNotificationMessage(MessageFormat.format(
							prop.getProperty(payload.get("ACTION_NAME") + "_ACTION"), firstName, lastName));
					notifiList.add(notificationCommonModelTo);
					break;
				case GeneralConstants.DELETE_ACTION:
					notificationCommonModelTo.setNotificationMessage(MessageFormat.format(
							prop.getProperty(payload.get("ACTION_NAME") + "_ACTION"), firstName, lastName));
					notifiList.add(notificationCommonModelTo);
					break;

				default:
					logger.error("No action Matched in SWITCH CASE");
					break;
				}
				nofifDao.createNotifications(notificationCommonModelTo);
				webSocketService.getUnreadNotificationCounts(notificationCommonModelTo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while create  Notifications : " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
	}

	@Override
	public GenericApiResponse updateNotifiBasedOnNotifiId(GenericRequestHeaders requestHeaders, String notificationId) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		boolean isUpdate = false;
		String METHOD_NAME = "updateNotifiBasedNotifiId";
		logger.info("Entered into the method: " + METHOD_NAME);
		try {
			if (notificationId != null) {
				Map<String, Object> payload = new HashMap<String, Object>();
				payload.put("userId", requestHeaders.getLoggedUser().getUserId());
				isUpdate = notificationDao.updateNotificationByNotifiId(payload, notificationId);
				if (isUpdate) {
					webSocketService.getNotificationUnreadCount(requestHeaders);
					apiResponse.setStatus(0);
					apiResponse.setMessage(prop.getProperty("NOTIFI_N001"));
				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage(prop.getProperty("NOTIFI_N002"));
					apiResponse.setErrorcode("NOTIFI_N002");
				}
			} else {
				apiResponse.setStatus(1);
				apiResponse.setMessage(prop.getProperty("GEN_002"));
				apiResponse.setErrorcode("GEN_002");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while update Notification : " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResponse;
	}

	@Override
	public GenericApiResponse getNotfications(GenericRequestHeaders requestHeaders, Notifications payload) {
		// TODO Auto-generated method stub
		GenericApiResponse apiResponse = new GenericApiResponse();
		List<Notifications> notifiList = new ArrayList<Notifications>();
		logger.info("Entered into the method: getNotifications()");
		String METHOD_NAME = "getNotfications";
		try {
			if (requestHeaders.getOrgId() != null) {
				payload.setOrgId(requestHeaders.getOrgId());
			}
			if (payload != null) {
				notifiList = notificationDao.getNotifications(payload);
				if (notifiList != null && !notifiList.isEmpty()) {
					notifiList.stream().forEach(noti -> {
						if (noti.getUserName() == null) {
							List<UmUsers> users = umUsersRepository.getUserByUserId(noti.getUserId(), false);
							if (!users.isEmpty()) {
								UmUsers user = users.get(0);
								String firstName = user.getFirstName();
								String lastName = user.getLastName() != null ? user.getLastName() : "";
								String userName = firstName + " " + lastName;
								userName = userName.equalsIgnoreCase("") ? "-" : userName;
								if (userName != null) {
									noti.setUserName(userName);
								}
							}
						}
					});
				}
				System.out.println(notifiList);
				if (notifiList != null) {
					apiResponse.setData(notifiList);
					apiResponse.setStatus(0);
					apiResponse.setMessage(prop.getProperty("NOTIFI_N004"));
				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage(prop.getProperty("NOTIFI_N003"));
					apiResponse.setErrorcode("NOTIFI_N003");
				}
			} else {
				apiResponse.setStatus(1);
				apiResponse.setMessage(prop.getProperty("GEN_002"));
				apiResponse.setErrorcode("GEN_002");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting Notification Details: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResponse;
	}

	@Override
	public GenericApiResponse markAllNotificationsAsRead(GenericRequestHeaders requestHeaders, Notifications payload) {
		String METHOD_NAME = "updateReadAllNotifications()";
		logger.info("Entered into the method: " + METHOD_NAME);
		GenericApiResponse apiResponse = new GenericApiResponse();
		boolean isReadAll = false;
		Notifications notificationCommonModelTo = new Notifications();
		try {
			if (requestHeaders != null) {
				notificationCommonModelTo.setOrgId(requestHeaders.getOrgId());
				notificationCommonModelTo.setUserId(requestHeaders.getLoggedUser().getUserId());
			}
			isReadAll = notificationDao.markAllNotificationsAsRead(notificationCommonModelTo);
			if (isReadAll) {
				apiResponse.setStatus(0);
				apiResponse.setMessage(prop.getProperty("NOTIFI_N007"));
			} else {
				apiResponse.setStatus(1);
				apiResponse.setMessage(prop.getProperty("NOTIFI_N008"));
				apiResponse.setErrorcode("NOTIFI_N008");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while update read All Notification : " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResponse;
	}

	@Override
	public GenericApiResponse getNotifiGroupCounts(GenericRequestHeaders requestHeaders, Notifications payload) {
		GenericApiResponse apiResponse = new GenericApiResponse();
		List<Map<String,String>> groupCounts = new ArrayList<Map<String,String>>();
		logger.info("Entered into the method: getNotificationCount()");
		String METHOD_NAME="getNotifiGroupCounts";
		try {
			if (requestHeaders.getOrgId() != null) {
				payload.setOrgId(requestHeaders.getOrgId());
			}
			if (payload != null) {
				groupCounts = notificationDao.getGroupCounts(payload);
				if (groupCounts != null) {
					apiResponse.setData(groupCounts);
					apiResponse.setStatus(0);
					apiResponse.setMessage(prop.getProperty("N0TIFI_N005"));
				} else {
					apiResponse.setStatus(1);
					apiResponse.setMessage(prop.getProperty("NOTIFI_N006"));
					apiResponse.setErrorcode("NOTIFI_N006");
				}
			} else {
				apiResponse.setStatus(1);
				apiResponse.setMessage(prop.getProperty("GEN_002"));
				apiResponse.setErrorcode("GEN_002");
			}
		}
		 catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting group counts : " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return apiResponse;
	}

}
