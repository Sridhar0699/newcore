package com.portal.send.service;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.portal.communication.dao.CommunicationDao;
import com.portal.communication.to.CommunicationTo;
import com.portal.constants.GeneralConstants.SettingType;
import com.portal.erp.constants.EtlGeneralConstants;
import com.portal.repository.EmailLogRepository;
import com.portal.rms.model.CreateOrder;
import com.portal.send.models.EmailsTo;
import com.portal.setting.dao.SettingDao;
import com.portal.wf.entity.EmailLog;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@Component(value = "emailService")
public class SendMessageServiceImpl implements SendMessageService {

	private static final Logger logger = LogManager.getLogger(SendMessageServiceImpl.class);

	@Autowired(required = true)
	private JavaMailSender javaMailSender;

	@Autowired(required = true)
	private CommunicationDao communicationDao;

	@Autowired(required = true)
	private EmailLogRepository emailLogRepository;
	
	@Autowired(required = true)
	private Environment prop;
	
	@Autowired(required = true)
	private SettingDao settingDao;
	
	public static final String DIR_PATH = "/SEC/EMT/";

	public String getDIR_PATH() {
		return prop.getProperty("ROOT_DIR") + DIR_PATH;
	}

	/**
	 * Send email communication
	 * 
	 * @param emailUtilsTo
	 * @param emailConfigs
	 */
	@Override
	@Async
	public void sendCommunicationMail(EmailsTo emailTo, Map<String, String> emailConfigs) {

		String METHOD_NAME = "getMailSenderConfig";

		logger.info("Entered into the method: " + METHOD_NAME);
		EmailLog emailLog = new EmailLog();
		EmailLogTo emailLogTo = new EmailLogTo();
		boolean emailSent = false;
		try {
			CommunicationTo communicationTo = communicationDao.getEmailTemplateDetails(emailTo.getOrgId(),
					emailTo.getTemplateName());
			String toMails = null, tempcon = null, ccMails = null, bccMails = null;
			if (emailTo.getTo() != null && !emailTo.getTo().trim().isEmpty()) {
				toMails = emailTo.getTo();
			}else {
				emailLog.setExceptionDetails("To-mail not available!");
				emailLog.setMailSent(emailSent ? 1 : 0);
				emailLog.setCreatedBy(emailTo.getLoginId());
				emailLog.setCreatedTs(new Date());
				emailLogRepository.save(emailLog);
				logger.info("Exit from the method: " + METHOD_NAME);
				return;
			}
			if (emailTo.getBcc() != null && emailTo.getBcc().length > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < emailTo.getBcc().length; i++) {
					if (i == 0) {
						sb.append(emailTo.getBcc()[i]);
					} else {
						sb.append("," + emailTo.getBcc()[i]);
					}
				}
				bccMails = sb.toString();
			}
			if (emailTo.getCc() != null && emailTo.getCc().length > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < emailTo.getCc().length; i++) {
					if (i == 0) {
						sb.append(emailTo.getCc()[i]);
					} else {
						sb.append("," + emailTo.getCc()[i]);
					}
				}
				ccMails = sb.toString();
			}
			emailLog.setFromMail(emailTo.getFrom());
			emailLog.setToMail(toMails);
			emailLog.setBccMail(bccMails);
			emailLog.setCcMail(ccMails);

			if (communicationTo != null) {
				Map<String, Object> maprProperties = emailTo.getTemplateProps();
				JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
				if(emailTo.getFrom().equalsIgnoreCase("rms@sakshi.com")) {
					 mailSender = this.getRMSMailSenderConfig(emailConfigs);
				}else {
					mailSender = this.getMailSenderConfig(emailConfigs);
				}				 
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				message.setFrom(emailTo.getFrom());
				String subject = communicationTo.getEmailSubject();
				try {
					boolean subjectEdit = (boolean) maprProperties.get("subject_edit");
					if (subjectEdit) {
						subject = subject.replaceAll("current_level", (String) maprProperties.get("currentLevel"));
						subject = subject.replaceAll("order_id", (String) maprProperties.get("orderId"));
						subject = subject.replaceAll("ad_id", (String) maprProperties.get("adId"));
						subject = subject.replaceAll("order_id", (String) maprProperties.get("orderId"));
						subject = subject.replaceAll("country_name", (String) maprProperties.get("country_name"));
						subject = subject.replaceAll("campaign_name", (String) maprProperties.get("sm_name"));
						String sm_type = maprProperties.get("sm_type") != null ? (String) maprProperties.get("sm_type") : null;
						if(sm_type!=null) {
							if("New".equals(sm_type)) {
								subject = subject.replaceAll("sm_type", sm_type);		
							}else {
								if(emailTo.getTemplateName().equals("CS_L4")) {
									subject = subject.replaceAll("created", "");	
								}
								subject = subject.replaceAll("released", "");
								subject = subject.replaceAll("sm_type", sm_type);
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				String sm_type = maprProperties.get("sm_type") != null ? (String) maprProperties.get("sm_type") : null;
				if(sm_type!=null&&sm_type.length()>0) {
					if(sm_type.equals("New")) {
						sm_type = "The Following Service Measure has been released, Please check below details";
					} else {
						sm_type = "The Following Service Measure has been re-released, Please check below details";
					}
					maprProperties.put("sm_type", sm_type);
				}
				
				message.setTo(toMails.split(","));
				if (bccMails != null) {
					//message.setBcc(bccMails);
					mimeMessage.setRecipients(Message.RecipientType.CC, bccMails);
				}
				if (ccMails != null) {
					//message.setCc(emailTo.getCc());
					mimeMessage.setRecipients(Message.RecipientType.CC, ccMails);
				}

				message.setSubject(subject);
				emailLog.setMailSubject(subject);

				/*if (communicationTo.getTemplateShortId().contentEquals(emailTo.getTemplateName())) {
					tempcon = communicationTo.getTemplateContent();
				}*/
				
				byte[] fileContent = Files
						.readAllBytes(Paths.get(getDIR_PATH() + emailTo.getTemplateName()+".html"));
				tempcon = new String(fileContent);

				VelocityEngine engine = getVelocityEngine(null, false);
				engine.init();

				StringResourceRepository repo = getRepo(null, engine);
				repo.putStringResource("emailTemplate", tempcon);
				Template template = engine.getTemplate("emailTemplate");
				VelocityContext velocityContext = new VelocityContext();
				if (maprProperties == null) {
					maprProperties = new HashMap<String, Object>();
				}
				maprProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
				if(emailTo.getFrom().equalsIgnoreCase("rms@sakshi.com")) {
					maprProperties.put("support_mail", emailConfigs.get("RMS_SUPPORT_EMAIL"));
				}else {
					maprProperties.put("support_mail", emailConfigs.get("SUPPORT_EMAIL"));
				}				
				maprProperties.put("web_url", emailConfigs.get("WEB_URL"));
				maprProperties.put("mobile_number", emailConfigs.get("SUPPORT_MOBILE_NUMBER"));
				if (maprProperties != null) {
					for (Map.Entry<String, Object> map : maprProperties.entrySet()) {
						velocityContext.put(map.getKey(), map.getValue());
					}
				}
				StringWriter stringWriter = new StringWriter();
				template.merge(velocityContext, stringWriter);
				message.setText(stringWriter.toString(), true);
				if(emailTo.getDataSource()==null) {
					mimeMessage.setContent(stringWriter.toString(), "text/html; charset=UTF-8");
				}
				emailLog.setMailContent(stringWriter.toString());
				if (emailTo.getDataSource() != null) {
					for (Map<String, Object> dataMap : emailTo.getDataSource()) {
                        dataMap.forEach((key,value)->{
                            try {
                                message.addAttachment(key, (DataSource)value);
                                //message.addAttachment(emailTo.getAttachmentName(), emailTo.getDataSource())
                            } catch (MessagingException e) {
                                    e.printStackTrace();
                            }
                        });
                    }
//					message.addAttachment(emailTo.getAttachmentName(), emailTo.getDataSource());
				}
				mailSender.send(mimeMessage);
				emailSent = true;
			} else {
				emailLog.setExceptionDetails("Template not found! with request : " + emailTo.getTemplateName());
			}
			BeanUtils.copyProperties(emailLog, emailLogTo);
		} catch (Exception e) {
			BeanUtils.copyProperties(emailLog, emailLogTo);
			logger.error("Error while sending emails: " + ExceptionUtils.getStackTrace(e));
			emailLog.setExceptionDetails(e.getMessage());
			emailLogTo.setExceptionDetails(ExceptionUtils.getStackTrace(e));
		}
		emailLog.setMailSent(emailSent ? 1 : 0);
		emailLogTo.setMailSent(emailSent ? 1 : 0);
		emailLog.setCreatedBy(emailTo.getLoginId());
		emailLog.setCreatedTs(new Date());
		emailLogRepository.save(emailLog);
		emailTo.setEmailLogTo(emailLogTo);
		logger.info("Exit from the method: " + METHOD_NAME);
	}

	@Override
	@Async
	public void sendCommunicationToSSPMail(EmailsTo emailTo, Map<String, String> emailConfigs) {

		String METHOD_NAME = "getMailSenderConfig";

		logger.info("Entered into the method: " + METHOD_NAME);
		EmailLog emailLog = new EmailLog();
		EmailLogTo emailLogTo = new EmailLogTo();
		boolean emailSent = false;
		try {
			CommunicationTo communicationTo = communicationDao.getEmailTemplateDetails(emailTo.getOrgId(),
					emailTo.getTemplateName());
			String toMails = null, tempcon = null, ccMails = null, bccMails = null;
			if (emailTo.getTo() != null && !emailTo.getTo().trim().isEmpty()) {
				toMails = emailTo.getTo();
			}else {
				emailLog.setExceptionDetails("To-mail not available!");
				emailLog.setMailSent(emailSent ? 1 : 0);
				emailLog.setCreatedBy(emailTo.getLoginId());
				emailLog.setCreatedTs(new Date());
				emailLogRepository.save(emailLog);
				logger.info("Exit from the method: " + METHOD_NAME);
				return;
			}
			if (emailTo.getBcc() != null && emailTo.getBcc().length > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < emailTo.getBcc().length; i++) {
					if (i == 0) {
						sb.append(emailTo.getBcc()[i]);
					} else {
						sb.append("," + emailTo.getBcc()[i]);
					}
				}
				bccMails = sb.toString();
			}
			if (emailTo.getCc() != null && emailTo.getCc().length > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < emailTo.getCc().length; i++) {
					if (i == 0) {
						sb.append(emailTo.getCc()[i]);
					} else {
						sb.append("," + emailTo.getCc()[i]);
					}
				}
				ccMails = sb.toString();
			}
			emailLog.setFromMail(emailTo.getFrom());
			emailLog.setToMail(toMails);
			emailLog.setBccMail(bccMails);
			emailLog.setCcMail(ccMails);

			if (communicationTo != null) {
				Map<String, Object> maprProperties = emailTo.getTemplateProps();
				JavaMailSenderImpl mailSender = this.getSSPMailSenderConfig(emailConfigs);
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				message.setFrom(emailTo.getFrom());
				String subject = communicationTo.getEmailSubject();
				try {
					boolean subjectEdit = (boolean) maprProperties.get("subject_edit");
					if (subjectEdit) {
						subject = subject.replaceAll("current_level", (String) maprProperties.get("currentLevel"));
						subject = subject.replaceAll("order_id", (String) maprProperties.get("orderId"));
						subject = subject.replaceAll("ad_id", (String) maprProperties.get("adId"));
						subject = subject.replaceAll("country_name", (String) maprProperties.get("country_name"));
						subject = subject.replaceAll("campaign_name", (String) maprProperties.get("sm_name"));
						String sm_type = maprProperties.get("sm_type") != null ? (String) maprProperties.get("sm_type") : null;
						if(sm_type!=null) {
							if("New".equals(sm_type)) {
								subject = subject.replaceAll("sm_type", sm_type);		
							}else {
								if(emailTo.getTemplateName().equals("CS_L4")) {
									subject = subject.replaceAll("created", "");	
								}
								subject = subject.replaceAll("released", "");
								subject = subject.replaceAll("sm_type", sm_type);
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				String sm_type = maprProperties.get("sm_type") != null ? (String) maprProperties.get("sm_type") : null;
				if(sm_type!=null&&sm_type.length()>0) {
					if(sm_type.equals("New")) {
						sm_type = "The Following Service Measure has been released, Please check below details";
					} else {
						sm_type = "The Following Service Measure has been re-released, Please check below details";
					}
					maprProperties.put("sm_type", sm_type);
				}
				
				message.setTo(toMails.split(","));
				if (bccMails != null) {
					//message.setBcc(bccMails);
					mimeMessage.setRecipients(Message.RecipientType.CC, bccMails);
				}
				if (ccMails != null) {
					//message.setCc(emailTo.getCc());
					mimeMessage.setRecipients(Message.RecipientType.CC, ccMails);
				}

				message.setSubject(subject);
				emailLog.setMailSubject(subject);

				/*if (communicationTo.getTemplateShortId().contentEquals(emailTo.getTemplateName())) {
					tempcon = communicationTo.getTemplateContent();
				}*/
				
				byte[] fileContent = Files
						.readAllBytes(Paths.get(getDIR_PATH() + emailTo.getTemplateName()+".html"));
				tempcon = new String(fileContent);

				VelocityEngine engine = getVelocityEngine(null, false);
				engine.init();

				StringResourceRepository repo = getRepo(null, engine);
				repo.putStringResource("emailTemplate", tempcon);
				Template template = engine.getTemplate("emailTemplate");
				VelocityContext velocityContext = new VelocityContext();
				if (maprProperties == null) {
					maprProperties = new HashMap<String, Object>();
				}
				maprProperties.put("logo_url", emailConfigs.get("LOGO_URL"));
				maprProperties.put("support_mail", emailConfigs.get("SUPPORT_SSP_EMAIL"));
				maprProperties.put("web_url", emailConfigs.get("WEB_URL"));
				maprProperties.put("mobile_number", emailConfigs.get("SUPPORT_MOBILE_NUMBER"));
				if (maprProperties != null) {
					for (Map.Entry<String, Object> map : maprProperties.entrySet()) {
						velocityContext.put(map.getKey(), map.getValue());
					}
				}
				StringWriter stringWriter = new StringWriter();
				template.merge(velocityContext, stringWriter);
				message.setText(stringWriter.toString(), true);
				if(emailTo.getDataSource()==null) {
					mimeMessage.setContent(stringWriter.toString(), "text/html; charset=UTF-8");
				}
				emailLog.setMailContent(stringWriter.toString());
				if (emailTo.getDataSource() != null) {
					for (Map<String, Object> dataMap : emailTo.getDataSource()) {
                        dataMap.forEach((key,value)->{
                            try {
                                message.addAttachment(key, (DataSource)value);
                                //message.addAttachment(emailTo.getAttachmentName(), emailTo.getDataSource())
                            } catch (MessagingException e) {
                                    e.printStackTrace();
                            }
                        });
                    }
//					message.addAttachment(emailTo.getAttachmentName(), emailTo.getDataSource());
				}
				mailSender.send(mimeMessage);
				emailSent = true;
			} else {
				emailLog.setExceptionDetails("Template not found! with request : " + emailTo.getTemplateName());
			}
			BeanUtils.copyProperties(emailLog, emailLogTo);
		} catch (Exception e) {
			BeanUtils.copyProperties(emailLog, emailLogTo);
			logger.error("Error while sending emails: " + ExceptionUtils.getStackTrace(e));
			emailLog.setExceptionDetails(e.getMessage());
			emailLogTo.setExceptionDetails(ExceptionUtils.getStackTrace(e));
		}
		emailLog.setMailSent(emailSent ? 1 : 0);
		emailLogTo.setMailSent(emailSent ? 1 : 0);
		emailLog.setCreatedBy(emailTo.getLoginId());
		emailLog.setCreatedTs(new Date());
		emailLogRepository.save(emailLog);
		emailTo.setEmailLogTo(emailLogTo);
		logger.info("Exit from the method: " + METHOD_NAME);
	}
	private JavaMailSenderImpl getMailSenderConfig(Map<String, String> emailConfigs) {

		String METHOD_NAME = "getMailSenderConfig";

		logger.info("Entered into the method: " + METHOD_NAME);

		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) this.javaMailSender;

		try {

			mailSender.setHost(emailConfigs.get("SMTP_HOST"));
			mailSender.setPort(Integer.parseInt(emailConfigs.get("SMTP_PORT")));
			if(emailConfigs.get("SMTP_AUTH") != null && "true".equalsIgnoreCase(emailConfigs.get("SMTP_AUTH"))){
				mailSender.setUsername(emailConfigs.get("SMTP_USER"));
				mailSender.setPassword(emailConfigs.get("SMTP_PASSWORD"));
			}

			Properties javaMailProperties = new Properties();
			javaMailProperties.put("mail.smtp.starttls.enable", emailConfigs.get("SMTP_TLS"));
			javaMailProperties.put("mail.smtp.auth", emailConfigs.get("SMTP_AUTH"));
			javaMailProperties.put("mail.transport.protocol", emailConfigs.get("SMTP_TP"));
			javaMailProperties.put("mail.debug", emailConfigs.get("SMTP_DEBUG"));
			javaMailProperties.put("mail.mime.charset", "UTF-8");

			mailSender.setJavaMailProperties(javaMailProperties);

		} catch (Exception e) {
			logger.error("Error while getting mail sender configuration: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return mailSender;
	}
	
	
	private JavaMailSenderImpl getSSPMailSenderConfig(Map<String, String> emailConfigs) {

		String METHOD_NAME = "getMailSenderConfig";

		logger.info("Entered into the method: " + METHOD_NAME);

		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) this.javaMailSender;

		try {

			mailSender.setHost(emailConfigs.get("SMTP_HOST"));
			mailSender.setPort(Integer.parseInt(emailConfigs.get("SMTP_PORT")));
			if(emailConfigs.get("SMTP_AUTH") != null && "true".equalsIgnoreCase(emailConfigs.get("SMTP_AUTH"))){
				mailSender.setUsername(emailConfigs.get("SSP_SMTP_USER"));
				mailSender.setPassword(emailConfigs.get("SSP_SMTP_PASSWORD"));
			}

			Properties javaMailProperties = new Properties();
			javaMailProperties.put("mail.smtp.starttls.enable", emailConfigs.get("SMTP_TLS"));
			javaMailProperties.put("mail.smtp.auth", emailConfigs.get("SMTP_AUTH"));
			javaMailProperties.put("mail.transport.protocol", emailConfigs.get("SMTP_TP"));
			javaMailProperties.put("mail.debug", emailConfigs.get("SMTP_DEBUG"));
			javaMailProperties.put("mail.mime.charset", "UTF-8");

			mailSender.setJavaMailProperties(javaMailProperties);

		} catch (Exception e) {
			logger.error("Error while getting mail sender configuration: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return mailSender;
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
	public Map<String, String> sendSms(String otp, String mobileNo,String customerName) {
		String METHOD_NAME = "sendSms";

		logger.info("Entered into the method: " + METHOD_NAME);
		Map<String,String> response = new HashMap<String,String>();
		try {
			
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList("OTP"));
			Map<String, String> ftpConfigs = settingDao.getSMTPSettingValues(params).getSettings();
			
			String text = ftpConfigs.get("OTP_TEXT");
			String token = ftpConfigs.get("OTP_TOKEN");
			
			String message = text.replace("{{customerName}}", customerName)
                    .replace("{{otp}}", otp);
			
			 HttpResponse<String> res = Unirest.post("https://http.myvfirst.com/smpp/sendsms")
			            .header("Authorization", "Bearer " + token )  // Add Authorization header with Bearer token  eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FwaS5teXZhbHVlZmlyc3QuY29tL3BzbXMiLCJzdWIiOiJzYWtzaGlodHB0cm4iLCJleHAiOjIwMzE4MjY0MDZ9.Kq92aT4JietghhGjsFnqiwNitmnmNPhpSM69OCUvUfY
			            .field("to", mobileNo)
			            .field("from", "SAKSHY")  // Replace with the sender ID
			            .field("text", message)  // Replace with the actual message
			            .asString();
			
			if(res.getBody() != null && res.getStatus() == 200) {
				response.put("statusCode", res.getStatus()+"");
				response.put("statusText", res.getStatusText());
				response.put("status", "Success");
			}else {
				response.put("statusCode", res.getStatus()+"");
				response.put("statusText", res.getStatusText());
				response.put("status", "Failed");
			}
		}catch(Exception e) {
			logger.error("Error while sending sms otp: " + ExceptionUtils.getStackTrace(e));;
		}
		logger.info("Exit from the method: " + METHOD_NAME);
		return response;
	}

	@Override
	public void sendSms1(CreateOrder payload) {
		String METHOD_NAME = "sendSms1";

		logger.info("Entered into the method: " + METHOD_NAME);
		Map<String,String> response = new HashMap<String,String>();
		try {
			
			Map<String, Object> params = new HashMap<>();
			params.put("stype", SettingType.APP_SETTING.getValue());
			params.put("grps", Arrays.asList("OTP"));
			Map<String, String> ftpConfigs = settingDao.getSMTPSettingValues(params).getSettings();
			
			String text = ftpConfigs.get("SMS_TEXT");
			String token = ftpConfigs.get("OTP_TOKEN");
			
			String mobileNo = "";
			if(payload.getClientPo().getMobileAlt() != null) {
				 mobileNo = payload.getClientPo().getMobileAlt();
			}else {
				mobileNo = payload.getClientPo().getMobileNo();
			}
			
			String message = text.replace("{{width}}", payload.getInsertionPo().getWidth()+"")
					.replace("{{height}}", payload.getInsertionPo().getHeight()+"")
					.replace("{{editions}}", payload.getEditionsErpId())
					.replace("{{adId}}", payload.getAdId())
					.replace("{{amount}}", payload.getAmount());
			
			 HttpResponse<String> res = Unirest.post("https://http.myvfirst.com/smpp/sendsms")
			            .header("Authorization", "Bearer " + token )  // Add Authorization header with Bearer token  eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FwaS5teXZhbHVlZmlyc3QuY29tL3BzbXMiLCJzdWIiOiJzYWtzaGlodHB0cm4iLCJleHAiOjIwMzE4MjY0MDZ9.Kq92aT4JietghhGjsFnqiwNitmnmNPhpSM69OCUvUfY
			            .field("to", mobileNo)
			            .field("from", "SAKSHY")  // Replace with the sender ID
			            .field("text", message)  // Replace with the actual message
			            .asString();
			 
			 if(res.getBody() != null && res.getStatus() == 200) {
					response.put("statusCode", res.getStatus()+"");
					response.put("statusText", res.getStatusText());
					response.put("status", "Success");
				}else {
					response.put("statusCode", res.getStatus()+"");
					response.put("statusText", res.getStatusText());
					response.put("status", "Failed");
				}

		} catch (Exception e) {
			logger.error("Error while sending sms: " + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Exit from the method: " + METHOD_NAME);

	}
	
	
	private JavaMailSenderImpl getRMSMailSenderConfig(Map<String, String> emailConfigs) {

		String METHOD_NAME = "getMailSenderConfig";

		logger.info("Entered into the method: " + METHOD_NAME);

		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) this.javaMailSender;

		try {

			mailSender.setHost(emailConfigs.get("SMTP_HOST"));
			mailSender.setPort(Integer.parseInt(emailConfigs.get("SMTP_PORT")));
			if(emailConfigs.get("SMTP_AUTH") != null && "true".equalsIgnoreCase(emailConfigs.get("SMTP_AUTH"))){
				mailSender.setUsername(emailConfigs.get("RMS_SMTP_USER"));
				mailSender.setPassword(emailConfigs.get("RMS_SMTP_PASSWORD"));
			}

			Properties javaMailProperties = new Properties();
			javaMailProperties.put("mail.smtp.starttls.enable", emailConfigs.get("SMTP_TLS"));
			javaMailProperties.put("mail.smtp.auth", emailConfigs.get("SMTP_AUTH"));
			javaMailProperties.put("mail.transport.protocol", emailConfigs.get("SMTP_TP"));
			javaMailProperties.put("mail.debug", emailConfigs.get("SMTP_DEBUG"));
			javaMailProperties.put("mail.mime.charset", "UTF-8");

			mailSender.setJavaMailProperties(javaMailProperties);

		} catch (Exception e) {
			logger.error("Error while getting mail sender configuration: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return mailSender;
	}

}
