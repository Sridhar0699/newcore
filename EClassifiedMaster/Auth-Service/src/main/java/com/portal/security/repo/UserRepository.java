package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.user.entities.UmUsers;

public interface UserRepository extends CrudRepository<UmUsers, String> {

	@Query("from UmUsers where UPPER(logonId) = UPPER(?1) and  markAsDelete = false and isDeactivated = false and gdUserTypes.userTypeId in (?2)")
	UmUsers findByLogonId(String username, List<Integer> userTypeId);
	
	@Query("from UmUsers where logonId = ?1 and  markAsDelete = false ")
	UmUsers findByLogonId(String username);

	@Query("from UmUsers us where upper(us.logonId)= upper( ?1 ) and us.markAsDelete = ?2 ")
	List<UmUsers> findByLogonIdCaseSensitive(String username, boolean markAsDelete);

}
