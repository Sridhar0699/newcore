package com.portal.nm.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.portal.common.models.GenericApiResponse;
import com.portal.constants.GeneralConstants;
import com.portal.nm.model.Notifications;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
	
	@Api(value = "Notification", description = "Notification APIs")
	@RequestMapping(value = GeneralConstants.API_VERSION)
	public interface NotificationApi {

		/**
		 * Update the existed Notification details
		 * @param org_id
		 * @param payload or notificationId
		 * @return
		 */
		@ApiOperation(value = "Update Notification when it reads true", notes = "Update the existed Notification details", response = Void.class, tags = {
				"Notification"})
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
				@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
				@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
				@ApiResponse(code = 405, message = "Inavlid input") })
		@RequestMapping(value = "/notification/updateNotifications", produces = { "application/json" }, consumes = {
				"application/json" }, method = RequestMethod.POST)
		ResponseEntity<?> updateNotificationByNotId(
				@NotNull @ApiParam(value = "Update Notification by notificationId")@RequestParam String notificationId);

		/**
		 * Get Notification details
		 * @RequestBody Notifications
		 * @return
		 */
		@ApiOperation(value = "get Notification Details", notes = "get the Notification details", response = Void.class, tags = {
				"Notification"})
		@ApiResponses(value = {
				@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
				@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
				@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
				@ApiResponse(code = 405, message = "Inavlid input") })
		@RequestMapping(value = "/notification/getNotifications", produces = { "application/json" }, consumes = {
				"application/json" }, method = RequestMethod.POST)
		ResponseEntity<?> getNotifications(
				@NotNull @ApiParam(value = "get Notification by payload")@RequestBody Notifications payload);

	/**
	 * update Notification 
	 * read all Notifications 
	 * @RequestBody Notifications
	 * @return
	 */
	@ApiOperation(value = "update read All Notifications", notes = "update read all Notifications", response = Void.class, tags = {
			"Notification"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/notification/markAsReadAllNotifications", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> markAllNotificationsAsRead(
			@NotNull @ApiParam(value = "update read all Notifications")@RequestBody Notifications payload);
	
	/**
	 * Get Notification Group Counts
	 * @RequestBody Notifications
	 * @return
	 */
	@ApiOperation(value = "get Notification Group Counts", notes = "get the Notification Group Counts", response = Void.class, tags = {
			"Notification"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Operation", response = GenericApiResponse.class),
			@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 405, message = "Inavlid input") })
	@RequestMapping(value = "/notification/getNotifyGroupCounts", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> getNotifiGroupCounts(
			@NotNull @ApiParam(value = "get Notification Group Count")@RequestBody Notifications payload);
	}


