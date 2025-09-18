package com.portal.datasecurity.dao;

/**
 * Security DAO
 * 
 * @author Sathish Babu D
 *
 */
public interface SecurityDao {

	/**
	 * Get access object permission
	 * 
	 * @param orgId
	 * @param userId
	 * @param accessObjId
	 * @return
	 */
	public boolean getAccessObjPermission(String orgId, String bpId, Integer userId, String accessObjId);

	/**
	 * Get organization permission
	 * 
	 * @param orgId
	 * @param loggedUser
	 * @return
	 */
	public boolean getOrgPermission(String orgId, Integer loggedUser, String bpId);

	/**
	 * Get business partner permission
	 * 
	 * @param bpId
	 * @param loggedUser
	 * @return
	 */
	public boolean getBpPermission(String bpId, Integer loggedUser);

	/**
	 * Get IP addresses
	 * 
	 * @param orgId
	 * @param bpId
	 * @return
	 */
	public String getIpAddresses(String orgId, String bpId);
}
