package com.portal.nm.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.common.models.GenericRequestHeaders;
import com.portal.common.service.CommonService;
import com.portal.nm.model.Notifications;
import com.portal.nm.service.NotificationsServiceImpl;

import io.swagger.annotations.ApiParam;

@Controller
public class NotificationApiController implements NotificationApi {

	@Autowired(required = true)
	private CommonService commonService;
	
	@Autowired(required =true)
	private NotificationsServiceImpl createNotification;

	@Override
	public ResponseEntity<?> updateNotificationByNotId(@ApiParam(value = "Update Read Notification")@RequestParam String notificationId) 
	{
		GenericApiResponse apiResp = new GenericApiResponse();
		GenericRequestHeaders requestHeaders = commonService.getRequestHeaders();
		apiResp = createNotification.updateNotifiBasedOnNotifiId(requestHeaders,notificationId);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getNotifications(@NotNull @ApiParam(value = "Get Notification Details")@RequestBody Notifications payload) 
	{
		GenericApiResponse apiResp = new GenericApiResponse();
		GenericRequestHeaders requestHeaders = commonService.getRequestHeaders();
		apiResp = createNotification.getNotfications(requestHeaders,payload);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> markAllNotificationsAsRead(@NotNull @ApiParam(value = "update read all Notifications true of specific group")@RequestBody Notifications payload) {
		GenericApiResponse apiResp = new GenericApiResponse();
		GenericRequestHeaders requestHeaders = commonService.getRequestHeaders();
		apiResp = createNotification.markAllNotificationsAsRead(requestHeaders, payload);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getNotifiGroupCounts(@NotNull Notifications payload) {
		GenericApiResponse apiResp = new GenericApiResponse();
		GenericRequestHeaders requestHeaders = commonService.getRequestHeaders();
		apiResp = createNotification.getNotifiGroupCounts(requestHeaders, payload);
		return new ResponseEntity<GenericApiResponse>(apiResp, HttpStatus.OK);
	}
	
}
