package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdReportsMaster;

public interface GdReportsMasterRepo extends CrudRepository<GdReportsMaster, String>{

	@Query("from GdReportsMaster grm where grm.markAsDelete = false")      
	List<GdReportsMaster> getReportsMaster();
	
	@Query("from GdReportsMaster grm where grm.rptShortId = ?1 and grm.markAsDelete = false")      
	List<GdReportsMaster> getReportsMasterByShortId(String reportShortId);
	
	@Query("from GdReportsMaster where  rptShortId = ?1 and markAsDelete = false and status = 1 order by rptGroup")      
	List<GdReportsMaster> getReportsMasterByShortIdAndStatus(String reportShortId);
	
}
