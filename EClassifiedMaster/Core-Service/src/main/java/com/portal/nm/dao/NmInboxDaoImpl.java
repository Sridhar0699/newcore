package com.portal.nm.dao;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.basedao.IBaseDao;
import com.portal.communication.dao.CommunicationDao;
import com.portal.communication.to.CommunicationTo;
import com.portal.constants.GeneralConstants;
import com.portal.nm.model.EmailsTo;
import com.portal.nm.model.NmEmailPayloadTo;
import com.portal.nm.model.NmInboxGetPushPayloadTo;
import com.portal.nm.model.NmInboxTo;
import com.portal.security.util.CommonUtils;
import com.portal.setting.dao.SettingDao;
import com.portal.setting.to.SettingTo;
import com.portal.setting.to.SettingTo.SettingType;
import com.portal.user.dao.UserDao;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.to.UserTo;
import com.portal.wf.entity.EmailLog;
import com.portal.wf.entity.WfInbox;

/**
 * User DAO implementation
 * 
 * @author
 *
 */
@Service("wfInboxDao")
@Transactional
public class NmInboxDaoImpl implements NmInboxDao {

	private static final Logger logger = LogManager.getLogger(NmInboxDaoImpl.class);

	@Autowired(required = true)
	private IBaseDao baseDao;

	@Autowired(required = true)
	private Environment prop;

	@Autowired(required = true)
	private UserDao userDao;

	@Autowired(required = true)
	private SettingDao settingDao;

	@Autowired(required = true)
	private JavaMailSender javaMailSender;

	@Autowired(required = true)
	private CommunicationDao communicationDao;

	@Override
	@SuppressWarnings("unchecked")
	public List<NmInboxTo> getAllRequesters(String loginId) {
		String METHOD_NAME = "getAllRequesters";
		logger.info("Entered into the method: " + METHOD_NAME);
		List<NmInboxTo> wfInboxToList = new ArrayList<>();
		try {
			String WfInboxListHQL = "";
			Object[] WfInboxListHQLParamVal = new Object[] {};
			UserTo userTo = userDao.getUserDetails(loginId);
			WfInboxListHQL = prop.getProperty("GET_WFI_REQ_FROM");
			WfInboxListHQLParamVal = new Object[] { userTo.getUserId() };
			List<Object[]> wfInboxList = (List<Object[]>) baseDao.findByHQLQueryWithIndexedParams(WfInboxListHQL,
					WfInboxListHQLParamVal);
			if (wfInboxList.size() > 0) {
				for (Object[] wfInbox : wfInboxList) {
					NmInboxTo wfInboxTo = new NmInboxTo();
					wfInboxTo.setWfItemId((Integer) wfInbox[0]);
					wfInboxTo.setTags(wfInbox[1] + "");
					wfInboxTo.setSubject(wfInbox[2] + "");
					if (wfInbox[3] != null)
						wfInboxTo.setStatus(Integer.parseInt(wfInbox[3] + ""));
					wfInboxTo.setReqToType(wfInbox[17] + " " + wfInbox[18]);
					wfInboxTo.setReqFromType(wfInbox[5] + " ");
//					wfInboxTo.setReqDate(CommonUtils.formatDateToISOString(CommonUtils.formatDate(wfInbox[6] + "")));
					wfInboxTo.setObjectType(wfInbox[7] + "");
					wfInboxTo.setObjectKey(wfInbox[8] + "");
					wfInboxTo.setObjectFrom(wfInbox[9] + "");
					if (wfInbox[10] != null)
						wfInboxTo.setMarkAsDelete((Integer) wfInbox[10]);
					wfInboxTo.setCreatedTs(CommonUtils.formatDate(wfInbox[11] + ""));
					if (wfInbox[12] != null)
						wfInboxTo.setCreatedBy(wfInbox[12] + "");
					wfInboxTo.setComments(wfInbox[13] + "");
					if (wfInbox[14] != null)
						wfInboxTo.setChangedTs(CommonUtils.formatDate(wfInbox[14] + ""));
					if (wfInbox[15] != null)
						wfInboxTo.setChangedBy(wfInbox[15] + "");
					wfInboxTo.setReadStatus(wfInbox[16] + "");
					wfInboxToList.add(wfInboxTo);
				}
			}

		} catch (Exception e) {
			logger.error("Error while finding WfInbox detais :" + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return wfInboxToList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NmInboxTo> getAllApprovers(String loginId, String roleType) {
		String METHOD_NAME = "findWfInboxDao";
		logger.info("Entered into the method: " + METHOD_NAME);
		List<NmInboxTo> wfInboxToList = new ArrayList<>();
		try {
			String WfInboxListHQL = "";
			Object[] WfInboxListHQLParamVal = new Object[] {};
			UserTo userTo = userDao.getUserDetails(loginId);
			WfInboxListHQL = prop.getProperty("GET_WFI_REQ_TO");
			WfInboxListHQLParamVal = new Object[] { userTo.getUserId() };
			if (roleType.equals("CO")) {
				WfInboxListHQL = prop.getProperty("GET_WFI_REQ_TO_COFFICER");
				WfInboxListHQLParamVal = new Object[] { userTo.getUserId(), "CYL_APPROVAL" };
			}

			List<Object[]> wfInboxList = (List<Object[]>) baseDao.findByHQLQueryWithIndexedParams(WfInboxListHQL,
					WfInboxListHQLParamVal);
			if (wfInboxList.size() > 0) {
				for (Object[] wfInbox : wfInboxList) {
					NmInboxTo wfInboxTo = new NmInboxTo();
					wfInboxTo.setWfItemId((Integer) wfInbox[0]);
					wfInboxTo.setTags(wfInbox[1] + "");
					wfInboxTo.setSubject(wfInbox[2] + "");
					if (wfInbox[3] != null)
						wfInboxTo.setStatus(Integer.parseInt(wfInbox[3] + ""));
					wfInboxTo.setReqToType(wfInbox[4] + "");
					wfInboxTo.setReqFromType(wfInbox[17] + " " + wfInbox[18]);
//					wfInboxTo.setReqDate(CommonUtils.formatDateToISOString(CommonUtils.formatDate(wfInbox[6] + "")));
					wfInboxTo.setObjectType(wfInbox[7] + "");
					wfInboxTo.setObjectKey(wfInbox[8] + "");
					wfInboxTo.setObjectFrom(wfInbox[9] + "");
					if (wfInbox[10] != null)
						wfInboxTo.setMarkAsDelete((Integer) wfInbox[10]);
					wfInboxTo.setCreatedTs(CommonUtils.formatDate(wfInbox[11] + ""));
					if (wfInbox[12] != null)
						wfInboxTo.setCreatedBy(wfInbox[12] + "");
					wfInboxTo.setComments(wfInbox[13] + "");
					if (wfInbox[14] != null)
						wfInboxTo.setChangedTs(CommonUtils.formatDate(wfInbox[14] + ""));
					if (wfInbox[15] != null)
						wfInboxTo.setChangedBy(wfInbox[15] + "");
					wfInboxTo.setReadStatus(wfInbox[16] + "");
					wfInboxToList.add(wfInboxTo);
				}
			}

		} catch (Exception e) {
			logger.error("Error while finding WfInbox detais :" + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return wfInboxToList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject findUnreadWfInboxDao(String login_id, String last_ts, boolean fromWebSocket) {
//		String METHOD_NAME = "findWfInboxDao";
//		logger.info("Entered into the method: " + METHOD_NAME);
//		List<NmInboxTo> wfInboxToList = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
//		try {
//			String WfInboxListHQL = "";
//			Object[] WfInboxListHQLParamVal = new Object[] {};
//			UserTo userTo = userDao.getUserDetails(login_id);
//			int count = -1;
//			int pushCount = 1;
//			int timeDiff = 1;
//			String hqlQuery = prop.getProperty("GET_GCV_PUSHNOTIFICATION_COUNT");
//			List<GdConfigValues> pushConfig = (List<GdConfigValues>) baseDao.findByHQLQueryWithoutParams(hqlQuery);
//
//			List<ListOfConfigValuesTo> list = new ArrayList<ListOfConfigValuesTo>();
//
//			for (GdConfigValues gcv : pushConfig) {
//				ListOfConfigValuesTo configValuesTo = new ListOfConfigValuesTo();
//				BeanUtils.copyProperties(gcv, configValuesTo);
//				if (gcv.getParentValId().equalsIgnoreCase("PUSHCOUNT")) {
//					String ca = gcv.getKey();
//					pushCount = Integer.parseInt(ca);
//				} else {
//					String ca = gcv.getKey();
//					timeDiff = Integer.parseInt(ca);
//				}
//				list.add(configValuesTo);
//			}
//
//			if (fromWebSocket) {
//				Calendar calendar = Calendar.getInstance();
//				calendar.set(Calendar.MINUTE, -timeDiff);
//				Date date = calendar.getTime();
//				DateFormat format = new SimpleDateFormat("yyyy-MM-dd mm:ss:SS");
//				String lastTime = format.format(date);
//				if ("PENG".equalsIgnoreCase(userTo.getUserType())) {
//					WfInboxListHQL = prop.getProperty("GET_WFI_REQ_FROM_UNREAD_WITHDATE");
//					WfInboxListHQLParamVal = new Object[] { userTo.getUserId(), date };
//				} else {
//					WfInboxListHQL = prop.getProperty("GET_WFI_REQ_TO_UNREAD_WITHDATE");
//					WfInboxListHQLParamVal = new Object[] { userTo.getUserId(), date };
//				}
//			} else {
//				if (last_ts == null) {
//					if ("PENG".equalsIgnoreCase(userTo.getUserType())) {
//						WfInboxListHQL = prop.getProperty("GET_WFI_REQ_FROM_UNREAD");
//						WfInboxListHQLParamVal = new Object[] { userTo.getUserId() };
//					} else {
//						WfInboxListHQL = prop.getProperty("GET_WFI_REQ_TO_UNREAD");
//						WfInboxListHQLParamVal = new Object[] { userTo.getUserId() };
//					}
//				} else {
//					DateFormat format = new SimpleDateFormat("yyyy-MM-dd mm:ss:SS");
//					Date lastDate = format.parse(last_ts);
//					if ("PENG".equalsIgnoreCase(userTo.getUserType())) {
//						WfInboxListHQL = prop.getProperty("GET_WFI_REQ_FROM_UNREAD_WITHDATE");
//						WfInboxListHQLParamVal = new Object[] { userTo.getUserId(), lastDate };
//					} else {
//						WfInboxListHQL = prop.getProperty("GET_WFI_REQ_TO_UNREAD_WITHDATE");
//						WfInboxListHQLParamVal = new Object[] { userTo.getUserId(), lastDate };
//					}
//				}
//			}
//
//			List<WfInbox> wfInboxList = (List<WfInbox>) baseDao.findByHQLQueryWithIndexedParamsAndLimits(WfInboxListHQL,
//					WfInboxListHQLParamVal, pushCount, 1);
//			if (wfInboxList.size() > 0) {
//				jsonObject.put("count", wfInboxList.size());
//				for (WfInbox wfInbox : wfInboxList) {
//					NmInboxTo NmInboxTo = new NmInboxTo();
//					BeanUtils.copyProperties(wfInbox, NmInboxTo);
//					UmUsers umUsersReqFrom = (UmUsers) baseDao
//							.findByHQLQueryWithIndexedParams(prop.getProperty("GET_BY_USER_ID"),
//									new Object[] { wfInbox.getReqFromType().toString() })
//							.get(0);
//					NmInboxTo.setReqFromType(umUsersReqFrom.getFirstName() + " " + umUsersReqFrom.getLastName());
//					UmUsers umUsersReqTo = (UmUsers) baseDao
//							.findByHQLQueryWithIndexedParams(prop.getProperty("GET_BY_USER_ID"),
//									new Object[] { wfInbox.getReqToType().toString() })
//							.get(0);
//					NmInboxTo.setReqToType(umUsersReqTo.getFirstName() + " " + umUsersReqTo.getLastName());
//					wfInboxToList.add(NmInboxTo);
//				}
//			} else {
//				jsonObject.put("count", 0);
//			}
//			jsonObject.put("objects_list", wfInboxToList);
//		} catch (Exception e) {
//			logger.error("Error while finding WfInbox detais :" + ExceptionUtils.getStackTrace(e));
//			try {
//				jsonObject.put("count", 0);
//			} catch (Exception je) {
//
//			}
//		}
//		logger.info("Exit from the method: " + METHOD_NAME);
		return jsonObject;
	}

	void sendEmailOnTripUpdates(String fromUserType, NmInboxTo wfInboxTo, List<UmOrgUsers> umOrgUsersList) {

		String METHOD_NAME = "sendEmailOnTripUpdates";

		logger.info("Entered into the method: " + METHOD_NAME);

		//
		try {
			String toUserType = "", reason = "";
			if (fromUserType.equalsIgnoreCase("SEC")) {
				if (wfInboxTo.getStatus() == 1) {
					toUserType = "Commmercial officer";
					reason = "Sstage: " + wfInboxTo.getStatus() + " From SEC => CO";
				} else if (wfInboxTo.getStatus() == 3) {
					toUserType = "Loading officer";
					reason = "Stage: " + wfInboxTo.getStatus() + " From SEC => LUO";
				}
			} else if (fromUserType.equalsIgnoreCase("CO")) {
				if (wfInboxTo.getStatus() == 2 || wfInboxTo.getStatus() == 0) {
					toUserType = "Security officer";
					reason = "Stage: " + wfInboxTo.getStatus() + " From CO => SEC";
				} else if (wfInboxTo.getStatus() == 5) {
					toUserType = "Loading Officer";
					reason = "Stage: " + wfInboxTo.getStatus() + " From CO => LO";
				}
			} else if (fromUserType.equalsIgnoreCase("LUO")) {
				if (wfInboxTo.getStatus() == 4) {
					toUserType = "Commmercial officer";
					reason = "Stage: " + wfInboxTo.getStatus() + " From LUO => CO";
				} else if (wfInboxTo.getStatus() == 6) {
					toUserType = "Security officer";
					reason = "Stage: " + wfInboxTo.getStatus() + " From LUO => SEC";
				}
			}
			String toEmailIds = "";
			for (UmOrgUsers umOrgUsers : umOrgUsersList) {
				if (toEmailIds.length() == 0) {
					toEmailIds = umOrgUsers.getUmUsers().getEmail();
				} else {
					toEmailIds = toEmailIds + "," + umOrgUsers.getUmUsers().getEmail();
				}
			}
			logger.info("toEmailIds -> " + toEmailIds);
			toEmailIds = "manikiran.chilukuri@incresol.com,vinaykrishna.mudumbai@incresol.com";
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();

			EmailsTo emailTo = new EmailsTo();
			emailTo.setFrom(emailConfigs.get("EMAIL_FROM"));//
			emailTo.setTo(toEmailIds);
			String content = "";
			Map<String, Object> maprProperties1 = new HashMap<String, Object>();
			String[] tripId = wfInboxTo.getObjectKey().split("_");

			// comment from Supergas
//			TmTripDetails tmTripDetails = (TmTripDetails) baseDao.findByPK(TmTripDetails.class, tripId[0]);

//			maprProperties1.put("truck_number", tmTripDetails.getVehicleNum());
			maprProperties1.put("user_reason", reason);

			emailTo.setTemplateProps(maprProperties1);
			emailTo.setTemplateName("TRIP_STAGE_UPDATES");
//			emailTo.setOrgId(wfInboxTo.getOrgId());
			//

			CommunicationTo communicationTo = communicationDao.getEmailTemplateDetails(emailTo.getOrgId(),
					emailTo.getTemplateName());
			if (communicationTo != null) {
				JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

				mailSender.setHost(emailConfigs.get("SMTP_HOST"));
				mailSender.setPort(Integer.parseInt(emailConfigs.get("SMTP_PORT")));
				mailSender.setUsername(emailConfigs.get("SMTP_USER"));
				mailSender.setPassword(emailConfigs.get("SMTP_PASSWORD"));

				Properties javaMailProperties = new Properties();
				javaMailProperties.put("mail.smtp.starttls.enable", emailConfigs.get("SMTP_TLS"));
				javaMailProperties.put("mail.smtp.auth", emailConfigs.get("SMTP_AUTH"));
				javaMailProperties.put("mail.transport.protocol", emailConfigs.get("SMTP_TP"));
				javaMailProperties.put("mail.debug", emailConfigs.get("SMTP_DEBUG"));

				mailSender.setJavaMailProperties(javaMailProperties);

				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

				message.setFrom(emailTo.getFrom());
				message.setSubject(communicationTo.getEmailSubject());

				String toMails = null, tempcon = null;

				if (emailTo.getTo() != null && !emailTo.getTo().trim().isEmpty()) {

					toMails = emailTo.getTo();
					message.setTo(toMails.split(","));
				}

				if (emailTo.getBcc() != null && emailTo.getBcc().length > 0) {

					message.setBcc(emailTo.getBcc());
				}

				if (emailTo.getCc() != null && emailTo.getCc().length > 0) {

					message.setCc(emailTo.getCc());
				}

				Map<String, Object> maprProperties = emailTo.getTemplateProps();

				if (communicationTo.getTemplateShortId().contentEquals(emailTo.getTemplateName())) {

					tempcon = communicationTo.getTemplateContent();
				}

				VelocityEngine engine = getVelocityEngine(null, false);
				engine.init();

				StringResourceRepository repo = getRepo(null, engine);
				repo.putStringResource("emailTemplate", tempcon);
				Template template = engine.getTemplate("emailTemplate");

				VelocityContext velocityContext = new VelocityContext();

				if (maprProperties == null) {
					maprProperties = new HashMap<String, Object>();
				}

//				maprProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
//				maprProperties.put("support_email", emailConfigs.get("SUPPORT_EMAIL"));
//				maprProperties.put("asp_url", emailConfigs.get("WEB_URL"));

				if (maprProperties != null) {

					for (Map.Entry<String, Object> map : maprProperties.entrySet()) {
						velocityContext.put(map.getKey(), map.getValue());
					}
				}

				StringWriter stringWriter = new StringWriter();

				template.merge(velocityContext, stringWriter);

				message.setText(stringWriter.toString(), true);

				if (emailTo.getDataSource() != null) {
					message.addAttachment(emailTo.getAttachmentName(), emailTo.getDataSource());
				}

				mailSender.send(mimeMessage);
			}

		} catch (Exception e) {
			logger.error("Error while sending emails: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);
	}

	protected VelocityEngine getVelocityEngine(String repoName, boolean isStatic) {

		String METHOD_NAME = "getVelocityEngine";

		logger.info("Entered into the method: " + METHOD_NAME);

		VelocityEngine engine = new VelocityEngine();

		try {

			engine.setProperty(Velocity.RESOURCE_LOADER, "string");
			engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());

			if (repoName != null) {

				engine.addProperty("string.resource.loader.repository.name", repoName);
			}

			if (!isStatic) {

				engine.addProperty("string.resource.loader.repository.static", "false");
			}

			engine.addProperty("string.resource.loader.modificationCheckInterval", "1");

			Properties props = new Properties();
			props.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
			props.put("runtime.log.logsystem.log4j.category", "velocity");
			props.put("runtime.log.logsystem.log4j.logger", "velocity");

			engine.init(props);

		} catch (Exception e) {
			logger.error("Error while getting velocity engine: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return engine;
	}

	protected StringResourceRepository getRepo(String name, VelocityEngine engine) {

		String METHOD_NAME = "getRepo";

		logger.info("Entered into the method: " + METHOD_NAME);

		StringResourceRepository repository = null;

		try {

			if (engine == null) {

				if (name == null) {

					repository = StringResourceLoader.getRepository();

				} else {

					repository = StringResourceLoader.getRepository(name);
				}

			} else {

				if (name == null) {

					repository = (StringResourceRepository) engine
							.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);

				} else {

					repository = (StringResourceRepository) engine.getApplicationAttribute(name);
				}
			}

		} catch (Exception e) {
			logger.error("Error while getting String resource respository: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return repository;
	}

	@Override
	public boolean addWfInboxPushNotification(NmInboxTo wfInboxTo) {
		String METHOD_NAME = "addWfInboxPushNotification";

		logger.info("Entered into the method: " + METHOD_NAME);

		boolean isUpdated = false;

//		try {
//			WfInbox wfInbox = new WfInbox();
//			BeanUtils.copyProperties(wfInboxTo, wfInbox);
//
//			wfInbox.setMarkAsDelete(0);
//			wfInbox.setReadStatus("UNREAD");
//			ObjectMapper obj = new ObjectMapper();
//			String json = obj.writeValueAsString(wfInbox);
//			System.out.println("#### -> " + json);
//			baseDao.save(wfInbox);
//			isUpdated = true;
//
//		} catch (Exception e) {
//			logger.error("Error while updating wfinbox push details : " + ExceptionUtils.getStackTrace(e));
//			isUpdated = false;
//		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return isUpdated;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NmInboxTo> getUnreadWfInboxPushnotifications(String org_id,
			NmInboxGetPushPayloadTo wfInboxGetPushPayloadTo, String logonId) {
		String METHOD_NAME = "getUnreadWfInboxPushnotifications";
		logger.info("Entered into the method: " + METHOD_NAME);
		List<NmInboxTo> wfInboxToList = new ArrayList<>();
		UserTo loggedUser = userDao.getUserDetails(logonId);
		try {
			String query = "FROM WfInbox wfi where (wfi.recUserId=" + loggedUser.getUserId() + " OR (";
			if (loggedUser.getCategoryId() != null) {
				query = query + "wfi.reqToType='" + loggedUser.getRoleType() + "' AND wfi.refToId IN ("
						+ loggedUser.getCategoryId().stream().map(String::valueOf).collect(Collectors.joining(","))
						+ "))) ";
			} else {
				query = query + "wfi.reqToType='" + loggedUser.getRoleType() + "')) ";
			}
			
			query = query + " AND wfi.status='0' AND wfi.readStatus='UNREAD' AND  wfi.markAsDelete='0' ORDER BY wfi.createdTs DESC";
			System.out.println("## - " + query);

			List<WfInbox> wfInboxList = (List<WfInbox>) baseDao.findByHQLQueryWithoutParams(query);

			if (wfInboxList.size() > 0) {
				for (WfInbox wfInbox : wfInboxList) {					
					NmInboxTo wfInboxTo = new NmInboxTo();
//					wfInboxTo.setWfItemId(wfInbox.getWfItemId());
//					wfInboxTo.setObjectType(wfInbox.getObjectType());
//					wfInboxTo.setObjectKey(wfInbox.getObjectKey());
//					wfInboxTo.setObjectFrom(wfInbox.getObjectFrom());
//					wfInboxTo.setReqFromType(wfInbox.getReqFromType());
//					wfInboxTo.setReqToType(wfInbox.getReqToType());
//					wfInboxTo.setReqDate(wfInbox.getReqDate());
//					wfInboxTo.setTags(wfInbox.getTags());
//					wfInboxTo.setSubject(wfInbox.getSubject());
//					wfInboxTo.setStatus(wfInbox.getStatus());
//					wfInboxTo.setComments(wfInbox.getComments());
//					wfInboxTo.setReadStatus(wfInbox.getReadStatus());
//					wfInboxTo.setRefFromId(wfInbox.getRefFromId());
//					wfInboxTo.setRefToId(wfInbox.getRefToId());
//					wfInboxTo.setRecUserId(wfInbox.getRecUserId());
					wfInboxTo.setCreatedTs(wfInbox.getCreatedTs());
					wfInboxTo.setCreatedBy(wfInbox.getCreatedBy());
					wfInboxTo.setChangedTs(wfInbox.getChangedTs());
					wfInboxTo.setChangedBy(wfInbox.getChangedBy());
					wfInboxToList.add(wfInboxTo);					
				}
			}
		} catch (Exception e) {
			logger.error("Error while finding WfInbox detais :" + ExceptionUtils.getStackTrace(e));
			wfInboxToList = null;
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return wfInboxToList;
	}

	@Override
	public boolean updateWfInboxPushnotifications(Integer wf_item_id, String action_type, String action_value,
			String logonId) {

		String METHOD_NAME = "updateWfInboxPushnotifications";
		logger.info("Entered into the method: " + METHOD_NAME);
		boolean updated = false;
		try {
			String sqlQuery = "FROM WfInbox wfi where wfi.wfItemId=" + wf_item_id;

			List<WfInbox> wfInboxList = (List<WfInbox>) baseDao.findByHQLQueryWithoutParams(sqlQuery);
			if (wfInboxList.size() > 0) {
				WfInbox wfInbox = wfInboxList.get(0);
				if (action_type.equalsIgnoreCase("STATUS")) {
					Integer status_ = Integer.parseInt(action_value);
//					wfInbox.setStatus(status_);
				} else {
//					wfInbox.setReadStatus(action_value);
				}
				wfInbox.setChangedBy(logonId);
				wfInbox.setChangedTs(new Date());
				baseDao.saveOrUpdate(wfInbox);
				updated = true;
			}
		} catch (Exception e) {
			logger.error("Error while finding WfInbox detais :" + ExceptionUtils.getStackTrace(e));
			updated = false;
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return updated;
	}

	@Override
	public boolean ffpEmailTrigger(NmEmailPayloadTo ffpEmailPayloadTo, String loginId, String orgId) {
		String METHOD_NAME = "ffpEmailTrigger";
		logger.info("Entered into the method: " + METHOD_NAME);
		boolean updated = false;
		EmailLog emailLog = new EmailLog();
		try {
//			sendService.sendCommunicationMail(emailTo, emailConfigs);
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList(GeneralConstants.EMAIL_SET_GP, GeneralConstants.ENV_SET_GP));
			SettingTo settingTo = settingDao.getSMTPSettingValues(params);
			Map<String, String> emailConfigs = settingTo.getSettings();
			EmailsTo emailTo = new EmailsTo();
			String toEmailIds = "", fromEmailId = "";
			if (ffpEmailPayloadTo.getTo_mail() == null) {
				toEmailIds = "manikiran.chilukuri@incresol.com";
			} else {
				toEmailIds = ffpEmailPayloadTo.getTo_mail();
			}
			if (ffpEmailPayloadTo.getFrom_mail() == null) {
				fromEmailId = emailConfigs.get("EMAIL_FROM");
			} else {
				fromEmailId = ffpEmailPayloadTo.getFrom_mail();
			}
			emailTo.setFrom(fromEmailId);
			emailTo.setTo(toEmailIds);
			emailLog.setFromMail(fromEmailId);
			emailLog.setToMail(toEmailIds);
			Map<String, Object> maprProperties1 = new HashMap<String, Object>();
			maprProperties1.put("sm_name", ffpEmailPayloadTo.getSm_name());
			maprProperties1.put("qm_doc_num", ffpEmailPayloadTo.getQm_doc_num());
			maprProperties1.put("qm_doc_rel_date", ffpEmailPayloadTo.getQm_doc_rel_date());
			maprProperties1.put("service_mtype", ffpEmailPayloadTo.getService_mtype());
			maprProperties1.put("reagion", ffpEmailPayloadTo.getReagion());
			maprProperties1.put("reagion_center", ffpEmailPayloadTo.getReagion_center());
			maprProperties1.put("country", ffpEmailPayloadTo.getCountry());
			maprProperties1.put("num_of_vins", ffpEmailPayloadTo.getNum_of_vins());
			maprProperties1.put("target_duration", ffpEmailPayloadTo.getTarget_duration());
			emailTo.setTemplateProps(maprProperties1);
			emailTo.setTemplateName(ffpEmailPayloadTo.getTemplate_name());
			//
			CommunicationTo communicationTo = communicationDao.getEmailTemplateDetails(orgId,
					emailTo.getTemplateName());
			if (communicationTo != null) {
				JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
				mailSender.setHost(emailConfigs.get("SMTP_HOST"));
				mailSender.setPort(Integer.parseInt(emailConfigs.get("SMTP_PORT")));
				mailSender.setUsername(emailConfigs.get("SMTP_USER"));
				mailSender.setPassword(emailConfigs.get("SMTP_PASSWORD"));
				Properties javaMailProperties = new Properties();
				javaMailProperties.put("mail.smtp.starttls.enable", emailConfigs.get("SMTP_TLS"));
				javaMailProperties.put("mail.smtp.auth", emailConfigs.get("SMTP_AUTH"));
				javaMailProperties.put("mail.transport.protocol", emailConfigs.get("SMTP_TP"));
				javaMailProperties.put("mail.debug", emailConfigs.get("SMTP_DEBUG"));
				mailSender.setJavaMailProperties(javaMailProperties);
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				message.setFrom(emailTo.getFrom());
				String subject = communicationTo.getEmailSubject();
				if (ffpEmailPayloadTo.getCountry() != null) {
					subject = subject.replaceAll("country_name", ffpEmailPayloadTo.getCountry());
				}
				if (ffpEmailPayloadTo.getSm_name() != null) {
					subject = subject.replaceAll("campaign_name", ffpEmailPayloadTo.getSm_name());
				}
				message.setSubject(subject);
				emailLog.setBccMail(ffpEmailPayloadTo.getBcc_mail());
				emailLog.setCcMail(ffpEmailPayloadTo.getCc_mail());
				emailLog.setMailSubject(subject);
				String toMails = null, tempcon = null;
				if (emailTo.getTo() != null && !emailTo.getTo().trim().isEmpty()) {
					toMails = emailTo.getTo();
					message.setTo(toMails.split(","));
				}
				if (ffpEmailPayloadTo.getBcc_mail() != null) {
					message.setBcc(ffpEmailPayloadTo.getBcc_mail());
					emailLog.setBccMail(ffpEmailPayloadTo.getBcc_mail());
				}
				if (ffpEmailPayloadTo.getCc_mail() != null) {
					emailLog.setBccMail(ffpEmailPayloadTo.getCc_mail());
					message.setCc(ffpEmailPayloadTo.getCc_mail());
				}
				Map<String, Object> maprProperties = emailTo.getTemplateProps();
				if (communicationTo.getTemplateShortId().contentEquals(emailTo.getTemplateName())) {
					tempcon = communicationTo.getTemplateContent();
				}
				VelocityEngine engine = getVelocityEngine(null, false);
				engine.init();
				StringResourceRepository repo = getRepo(null, engine);
				repo.putStringResource("emailTemplate", tempcon);
				Template template = engine.getTemplate("emailTemplate");
				VelocityContext velocityContext = new VelocityContext();
				if (maprProperties == null) {
					maprProperties = new HashMap<String, Object>();
				}
//				maprProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
//				maprProperties.put("support_email", emailConfigs.get("SUPPORT_EMAIL"));
//				maprProperties.put("asp_url", emailConfigs.get("WEB_URL"));
				if (maprProperties != null) {

					for (Map.Entry<String, Object> map : maprProperties.entrySet()) {
						velocityContext.put(map.getKey(), map.getValue());
					}
				}
				StringWriter stringWriter = new StringWriter();
				template.merge(velocityContext, stringWriter);
				message.setText(stringWriter.toString(), true);
				emailLog.setMailContent(stringWriter.toString());
				if (emailTo.getDataSource() != null) {
					message.addAttachment(emailTo.getAttachmentName(), emailTo.getDataSource());
				}
				mailSender.send(mimeMessage);
				updated = true;
			} else {
				emailLog.setExceptionDetails(
						"No Template found for the request :- " + ffpEmailPayloadTo.getTemplate_name());
			}
		} catch (Exception e) {
			logger.error("Error while sending emails: " + ExceptionUtils.getStackTrace(e));
			updated = false;
			emailLog.setExceptionDetails(e.getMessage());
		}
		emailLog.setMailSent(updated ? 1 : 0);
		emailLog.setCreatedBy(loginId);
		emailLog.setCreatedTs(new Date());
		baseDao.save(emailLog);
		logger.info("Exit from the method: " + METHOD_NAME);
		return updated;
	}

}
