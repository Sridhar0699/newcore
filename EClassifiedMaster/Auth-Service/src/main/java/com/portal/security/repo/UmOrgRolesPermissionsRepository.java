package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.user.entities.UmOrgRolesPermissions;

public interface UmOrgRolesPermissionsRepository extends CrudRepository<UmOrgRolesPermissions, String>{

	@Query("from UmOrgRolesPermissions permi where permi.umOrgRoles.roleId in (?1) and permi.gdAccessObjects.accessObjId = ?2 and permi.markAsDelete = ?3")      
	List<UmOrgRolesPermissions> getOrgRolePermissions(List<Integer> roleId, String accessObjId, boolean markAsDelete);
}
