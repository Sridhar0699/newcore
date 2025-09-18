package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.clf.entities.ClfPayments;

public interface ClfPaymentsRepo extends CrudRepository<ClfPayments, String>{

	@Query("from ClfPayments cp where cp.createdBy = ?1 and cp.markAsDelete = false ")      
	List<ClfPayments> getSelfPaymentHistory(Integer userId);
	
	@Query("from ClfPayments cp where cp.markAsDelete = false ")      
	List<ClfPayments> getPaymentHistory();
}
