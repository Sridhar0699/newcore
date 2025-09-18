package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.google.common.base.Optional;
import com.portal.user.entities.UmOrgRoles;

public interface UmOrgRolesRepo extends CrudRepository<UmOrgRoles, String>{
	Optional<UmOrgRoles> findByRoleShortId(String roleShortId);
	
	@Query("from UmOrgRoles where markAsDelete = false")
	List<UmOrgRoles> findAllUmOrgRoles();

}
