package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.org.entities.OmOrganizations;
import com.portal.user.entities.UmOrgUsers;

public interface UmOrgUsersRepo extends CrudRepository<UmOrgUsers, Integer> {

	@Query("from UmOrgUsers as uou where uou.umUsers.userId= ?1 and uou.markAsDelete = ?2")
	List<UmOrgUsers> getUmOrgDetailsByUserId(Integer userId, boolean markAsDelete);

	@Query("select distinct uou.omOrganizations from UmOrgUsers uou inner join uou.omOrganizations org where uou.umUsers.userId=?1 and org.markAsDelete = false and uou.markAsDelete=?2")
	List<OmOrganizations> getOrgByUserId(Integer userId, boolean markAsDelete);

	@Query("select bp,uou from UmOrgUsers uou inner join uou.omOrgBusinessPartners bp where uou.omOrganizations.orgId= ?1 and uou.umUsers.userId=?2 and uou.markAsDelete=false and bp.markAsDelete=false")
	List<Object[]> getBpByUserId(String orgId, Integer userId);

	@Query("from UmOrgUsers orgs where orgs.umUsers.userId = ?1 and orgs.omOrganizations.orgId = ?2 and orgs.markAsDelete = ?3")
	List<UmOrgUsers> getOrgUsersByUserOrgId(Integer userId, String orgId, boolean markAsDelete);

	@Query("from UmOrgUsers orgs where orgs.umUsers.userId = ?1 and orgs.omOrgBusinessPartners.orgBpId = ?2 and orgs.markAsDelete = ?3")
	List<UmOrgUsers> getOrgUsersBps(Integer userId, String orgBpId, boolean markAsDelete);

	@Query("from UmOrgUsers orgs where orgs.umUsers.userId = ?1 and orgs.markAsDelete = ?2")
	List<UmOrgUsers> getOrgByUser(Integer userId, boolean markAsDelete);
	
	@Query("from UmOrgUsers uou where uou.umUsers.userId in (?1) and uou.markAsDelete = false")
	List<UmOrgUsers> getOrgUsersByCreatedIds(List<Integer> userId);
	
	@Query("from UmOrgUsers orgs where orgs.umUsers.userId in (?1) and orgs.markAsDelete = false")
	List<UmOrgUsers> geUserDetailsByUserIds(List<Integer> userId);
	
	@Query("from UmOrgUsers orgs where orgs.umOrgRoles.roleId in (?1) and orgs.markAsDelete = false")
	List<UmOrgUsers> getUsersByRoleIds(List<Integer> roleIds);

}
