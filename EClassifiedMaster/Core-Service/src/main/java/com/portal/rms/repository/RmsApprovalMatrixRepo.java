package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.RmsApprovalMatrix;

public interface RmsApprovalMatrixRepo extends CrudRepository<RmsApprovalMatrix, Integer>{

	@Query(value = "from RmsApprovalMatrix am where am.bookingOffice = ?1 and am.markAsDelete = false order by am.rangeFrom asc")  
	List<RmsApprovalMatrix> getApprovalBookingUnit(Integer bookingOffice);

	@Query(value = "from RmsApprovalMatrix am where am.id = ?1 and am.markAsDelete = false")
	RmsApprovalMatrix getRmsApprovalMatrixOnId(Integer id);

	@Query(value = "from RmsApprovalMatrix am where am.bookingOffice = ?1 and am.rangeFrom = ?2 and am.rangeTo = ?3 and am.level = ?4 and am.markAsDelete = false")
	RmsApprovalMatrix getCheckRmsApprovalMatrixOnCobination(Integer bookingOffice, Double rangeFrom, Double rangeTo, Integer level);

}
