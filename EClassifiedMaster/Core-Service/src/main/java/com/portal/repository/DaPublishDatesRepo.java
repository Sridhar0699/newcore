package com.portal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.clf.entities.ClfPublishDates;

public interface DaPublishDatesRepo extends CrudRepository<ClfPublishDates, String>{

	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_publish_dates  SET mark_as_delete = ?1, changed_by = ?2, changed_ts = ?3 where item_id = ?4 and mark_as_delete = false", nativeQuery = true)      
	void removeClfPublishDatesOnItemId(Boolean flag, Integer userId, Date changedTs, String itemId);
	
	@Query(value = "select cpd.item_id, cpd.publish_date from clf_publish_dates cpd where cpd.item_id in (?1) and cpd.mark_as_delete = false",nativeQuery = true)
	List<Object[]> getDisplayPublishDatesForErpData(List<String> itemIds);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_publish_dates  SET download_status = ?1, changed_by = ?2, changed_ts = ?3 where item_id = ?4 and mark_as_delete = false", nativeQuery = true)      
	void updateDownloadStatus(Boolean flag, Integer userId, Date changedTs, String itemId);
}
