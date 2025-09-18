package com.portal.datasecurity;

import com.portal.common.models.GenericApiResponse;
import com.portal.datasecurity.model.DataSecurity;

/**
 * Data security service
 * 
 * @author Sathish Babu D
 *
 */
public interface DataSecurityService {

	/**
	 * Check the accessing object permissions
	 * 
	 * @param dataSecurity
	 * @return
	 */
	public boolean verifyAccessObjectPermissions(DataSecurity dataSecurity);

	/**
	 * Check the organization permissions
	 * 
	 * @param dataSecurity
	 * @return
	 */
	public boolean verifyOrgPermissions(DataSecurity dataSecurity);

	/**
	 * Check the business partner permissions
	 * 
	 * @param dataSecurity
	 * @return
	 */
	public boolean verifyBpPermissions(DataSecurity dataSecurity);

	/**
	 * Check the IP address permissions
	 * 
	 * @param dataSecurity
	 * @return
	 */
	public boolean verifyIpAddressPermissions(DataSecurity dataSecurity);

	/**
	 * Data security verification
	 * 
	 * @param dataSecurity
	 * @return
	 */
	public GenericApiResponse dataSecurityVerification(DataSecurity dataSecurity);

}
