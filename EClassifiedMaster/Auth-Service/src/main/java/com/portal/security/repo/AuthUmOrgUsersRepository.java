package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.user.entities.UmOrgUsers;

public interface AuthUmOrgUsersRepository extends CrudRepository<UmOrgUsers, Integer> {

	@Query("from UmOrgUsers where umUsers.userId =?1 and markAsDelete = false")
	List<UmOrgUsers> findByUsers(Integer userId);
}
