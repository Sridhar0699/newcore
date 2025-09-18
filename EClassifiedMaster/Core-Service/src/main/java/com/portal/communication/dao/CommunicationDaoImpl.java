package com.portal.communication.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.portal.communication.entities.NmEmailTemplates;
import com.portal.communication.to.CommunicationTo;
import com.portal.repository.NmEmailTemplatesRepo;

/**
 * Communication DAO implementation
 * 
 * @author Sathish Babu D
 *
 */
@Service("sendDao")
public class CommunicationDaoImpl implements CommunicationDao {

	private static final Logger logger = LogManager.getLogger(CommunicationDaoImpl.class);

	@Autowired(required = true)
	private NmEmailTemplatesRepo emailTemplateRepo;

	@Autowired(required = true)
	private Environment prop;

	/**
	 * Get Email template details
	 * 
	 * @param orgId
	 * @param templateName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CommunicationTo getEmailTemplateDetails(String orgId, String templateName) {

		String METHOD_NAME = "getEmailTemplateDetails";

		logger.info("Entered into the method: " + METHOD_NAME);

		CommunicationTo communicationTo = null;

		try {

			String headerTemplate = null, bodyTemplate = null;

			/*List<NmEmailTemplates> emailTemplates = (List<NmEmailTemplates>) baseDao.findByHQLQueryWithIndexedParams(
					prop.getRequiredProperty("GET_EMAIL_TEMPLATE"), new Object[] { orgId, templateName });*/
			List<NmEmailTemplates> emailTemplates = emailTemplateRepo.getNmTemplates(orgId, templateName);
			
			for (NmEmailTemplates template : emailTemplates) {

				if (!templateName.equals(template.getTemplateShortId())) {

					headerTemplate = template.getTemplateContent();

				} else {

					bodyTemplate = template.getTemplateContent();

					communicationTo = new CommunicationTo();

					BeanUtils.copyProperties(template, communicationTo);
				}
			}

			if (communicationTo != null)
				
				//communicationTo.setTemplateContent(headerTemplate.replace("{email_content}", bodyTemplate));
				communicationTo.setTemplateContent(bodyTemplate);

		} catch (Exception e) {
			logger.error("Error while getting email template details: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("Exit from the method: " + METHOD_NAME);

		return communicationTo;
	}

}
