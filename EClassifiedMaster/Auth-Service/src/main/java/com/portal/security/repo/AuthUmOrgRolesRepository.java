package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.user.entities.UmOrgRoles;

public interface AuthUmOrgRolesRepository extends CrudRepository<UmOrgRoles, Integer> {

	@Query("from UmOrgRoles where roleId  in (?1) and markAsDelete = false")
	List<UmOrgRoles> findUmOrgRoles(List<Integer> roleIds);
}
