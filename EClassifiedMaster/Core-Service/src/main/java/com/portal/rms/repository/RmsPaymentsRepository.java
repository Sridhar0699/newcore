package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.rms.entity.RmsPaymentsResponse;

public interface RmsPaymentsRepository extends CrudRepository<RmsPaymentsResponse, String>{
	
	@Query("SELECT rp FROM RmsPaymentsResponse rp  WHERE rp.itemId = ?1 AND rp.markAsDelete = false")
	List<RmsPaymentsResponse> getPaymentsByItemId(String itemId);

	
	

}
