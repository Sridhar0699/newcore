package com.portal.communication.dao;

import com.portal.communication.to.CommunicationTo;

/**
 * Communication DAO
 * 
 * @author Sathish Babu D
 *
 */
public interface CommunicationDao {

	/**
	 * Get Email template details
	 * 
	 * @param orgId
	 * @param templateName
	 * @return
	 */
	public CommunicationTo getEmailTemplateDetails(String orgId, String templateName);

}
