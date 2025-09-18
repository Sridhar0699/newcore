package com.portal.nm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.basedao.IBaseDao;
import com.portal.nm.entities.NmNotifications;
import com.portal.nm.model.GroupCount;
import com.portal.nm.model.Notifications;
import com.portal.repository.NmNotificationsRepo;

@Service
public class NotificationDaoImpl implements NotificationDao {

	private static final Logger logger = Logger.getLogger(NotificationDaoImpl.class);

	@Autowired
	private NmNotificationsRepo nmNotificationsRepo;
	
	@Autowired
	private IBaseDao baseDao;
	

	public boolean createNotifications(Notifications notificationCommonModelTo) {
		String METHOD_NAME = "addNotificationDetails()";
		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isCreated = false;
		NmNotifications nmNotification = new NmNotifications();
		try {

			if (notificationCommonModelTo.getNotificationMessage() != null) {

//			if (notificationCommonModelTo.getNotificationId() != null
//					&& notificationCommonModelTo.getNotificationId() != null) {
				nmNotification.setNotificationId(UUID.randomUUID().toString());
				nmNotification.setNotificationType("BELL");
				nmNotification.setNotificationMessage(notificationCommonModelTo.getNotificationMessage());
				nmNotification.setRead(false);
				nmNotification.setObjectRefId(notificationCommonModelTo.getObjectRefId());
				nmNotification.setCreatedBy(notificationCommonModelTo.getUserId());
				nmNotification.setCreatedTs(new Date());
				nmNotification.setUpdatedBy(notificationCommonModelTo.getUserId());
				nmNotification.setUpdatedTs(new Date());
				nmNotification.setMarkAsDelete(false);
				nmNotification.setOrgId(notificationCommonModelTo.getOrgId());
				nmNotification.setOrgOpuId(notificationCommonModelTo.getOrgOpuId());
				nmNotification.setDocId(notificationCommonModelTo.getDocId());
				nmNotification.setActionStatus(notificationCommonModelTo.getActionStatus());
				nmNotification.setType(notificationCommonModelTo.getType());
				nmNotification.setUserName(notificationCommonModelTo.getUserName());
				nmNotification.setUserId(notificationCommonModelTo.getUserId());

				nmNotificationsRepo.save(nmNotification);

				return true;
//			}
		}
		} catch (Exception e) {
			logger.error("Error while creating Notification : " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return isCreated;
	}

	@Override
	public boolean updateNotificationByNotifiId(Map<String, Object> payload, String notificationId) {
		String METHOD_NAME = "updateNotificationByNotifiId()";
		logger.info("Entered into the method: " + METHOD_NAME);

		boolean flag = false;
		Integer changedBy = (Integer)payload.get("userId");
		try {
			Optional<NmNotifications> nmOptional =  nmNotificationsRepo.findById(notificationId);
			if(nmOptional.isPresent()){
				NmNotifications nmNotifications = nmOptional.get();
				nmNotifications.setUpdatedBy(changedBy);
				nmNotifications.setUpdatedTs(new Date());
				nmNotifications.setRead(true);
				nmNotificationsRepo.save(nmNotifications);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while updating read Notification: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<Notifications> getNotifications(@NotNull Notifications payload) {
		List<Notifications> notification = new ArrayList<Notifications>();
		String METHOD_NAME = "getNotifications()";
		logger.info("Entered into the method: " + METHOD_NAME);

		try {
			if (payload.getUserId() != null) {
				String query;
				if(payload.isUnReadNotification()){
				 query = "from NmNotifications nn where nn.userId = ?1 and nn.orgId = ?2 and nn.isRead = false order by createdTs desc";
				}else{
					 query = "from NmNotifications nn where nn.userId = ?1 and nn.orgId = ?2 order by createdTs desc";
				}
				int skip = (payload.getPageNumber() - 1) * payload.getPageSize();
				List<NmNotifications> nmNotificationsList = (List<NmNotifications>) baseDao.findByHQLQueryWithIndexedParamsAndLimits(query, new Object[]{payload.getUserId(),payload.getOrgId()}, payload.getPageSize(), payload.getPageNumber());
				for(NmNotifications nn : nmNotificationsList){
					Notifications notif = new Notifications();
					BeanUtils.copyProperties(nn, notif);
					notification.add(notif);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting Notification Details: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return notification;
	}
	
	@SuppressWarnings("unused")
	public boolean markAllNotificationsAsRead(Notifications notificationCommonModelTo) {
		String METHOD_NAME = "readAllNotifications()";
		logger.info("Entered into the method:"+METHOD_NAME);
		boolean isReadAll = false;
		try {
			List<NmNotifications> nmNotifications = new ArrayList<NmNotifications>();
				nmNotifications = nmNotificationsRepo.getUnReadNotification(notificationCommonModelTo.getUserId(), notificationCommonModelTo.getOrgId());
			if (!nmNotifications.isEmpty()) {
				nmNotifications.stream().forEach(frmNotification -> {

					frmNotification.setRead(true);
					frmNotification.setUpdatedBy(notificationCommonModelTo.getUserId());
					frmNotification.setUpdatedTs(new Date());
					nmNotificationsRepo.save(frmNotification);
				});
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting TemplateId: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return isReadAll;
	}

	@Override
	public List<Map<String,String>> getGroupCounts(@NotNull Notifications notificationCommonModelTo) {
		List<Map<String,String>> counts=new ArrayList<Map<String,String>>();
		String METHOD_NAME="getGroupCounts()";
		logger.info("Entered into the method: "+METHOD_NAME);

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", notificationCommonModelTo.getUserId());
			params.put("orgId", notificationCommonModelTo.getOrgId());
			String query = "select nm.type, count(type) FROM NmNotifications nm where nm.userId = :userId and nm.orgId = :orgId and markAsDelete = false GROUP BY nm.type";
			List<Object[]> nmNotifications = (List<Object[]>) baseDao.findByHQLQueryWithNamedParams(query, params);
		if (!nmNotifications.isEmpty()) {
			for(int i = 0;i<nmNotifications.size();i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("notificationGroup", nmNotifications.get(i)[0]+"");
				map.put("groupCounts", nmNotifications.get(i)[1]+"");
				counts.add(map);
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Error while getting Notification Group Counts: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return counts;
	}

}
