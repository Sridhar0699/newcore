package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.user.entities.UmCustomers;

public interface UmCustomersRepo extends CrudRepository<UmCustomers, String>{

	@Query(value = "from UmCustomers where mobileNo= ?1 and markAsDelete = false ")      
	List<UmCustomers> getCustomerDetails(String customerMobileNo); 
	
	@Query(value = "from UmCustomers where customerId= ?1 and markAsDelete = false ")      
	List<UmCustomers> getCustomerDetailsOnOrderId(String customerId);
	
	@Query(value = "from UmCustomers where customerId in (?1) and markAsDelete = false")
	List<UmCustomers> getMulCustomerDetails(List<String> customerIds);
	
	@Query(value = "from UmCustomers where clientCode in (?1) and markAsDelete = false ")      
	List<UmCustomers> getCustomerDataOnClientCode(List<String> clientCode); 
	
	
	@Query(value = "from UmCustomers where customerId= ?1 and markAsDelete = false ")      
	UmCustomers getCustomerDetailsOnCustomerId(String customerId);
}
