package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.user.entities.UmOrgRolesPermissions;

public interface UmOrgRolesPermissionsRepo extends CrudRepository<UmOrgRolesPermissions, Integer>{

	@Query("from UmOrgRolesPermissions permi where permi.umOrgRoles.roleId = ?1 and permi.gdAccessObjects.accessObjId = ?2 and permi.markAsDelete = ?3")      
	List<UmOrgRolesPermissions> getOrgRolePermissions(Integer roleId, String accessObjId, boolean markAsDelete);
	
	@Query("select urp.gdAccessObjects.accessObjId,urp.gdAccessObjects.parentObjId,urp.gdAccessObjects.accessObjDesc,urp.permissionLevel,urp.readPermission,urp.writePermission,urp.umOrgRoles.roleType,urp.umOrgRoles.roleDesc from UmOrgRolesPermissions urp where urp.umOrgRoles.roleId=?1 and urp.markAsDelete=?2 and urp.permissionLevel= true")      
	List<Object[]> getAccessObjects(Integer roleId, boolean markAsDelete);
	
	
	
}
