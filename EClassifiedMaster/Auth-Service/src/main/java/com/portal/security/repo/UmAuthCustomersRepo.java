package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.user.entities.UmCustomers;

public interface UmAuthCustomersRepo extends CrudRepository<UmCustomers, String>{

	@Query(value = "from UmCustomers where mobileNo= ?1 and markAsDelete = false ")      
	List<UmCustomers> getCustomerDetails(String customerMobileNo);
	
	@Query(value = "from UmCustomers where userId= ?1 and markAsDelete = false ")      
	List<UmCustomers> getCustomerDetailsByUser(Integer userId); 
}
