package com.portal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.clf.entities.ClfEditions;

public interface ClfEditionsRepo extends CrudRepository<ClfEditions, String>{

	@Query(value = "select ce.edition_id from clf_editions ce where ce.item_id = ?1 and ce.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getEditionIdOnItemId(String itemId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_editions  SET mark_as_delete = ?1, changed_by = ?2, changed_ts = ?3 where item_id = ?4 and mark_as_delete = false", nativeQuery = true)      
	void removeClfEditionsOnItemId(Boolean flag, Integer userId, Date changedTs, String itemId);
	
	@Query(value = "select ce.item_id,ce.edition_id, gce.edition_name,gce.erp_ref_id from clf_editions ce inner join gd_classified_editions gce on gce.id = ce.edition_id where ce.item_id in (?1) and ce.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getEditionIdAndNameOnItemId(List<String> itemId);
	
	
	@Query(value = "select ce.item_id,ce.edition_id, gce.edition_name,gce.erp_ref_id from clf_editions ce inner join gd_da_editions gce on gce.id = ce.edition_id where ce.item_id in (?1) and ce.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getDaEditionIdAndNameOnItemId(List<String> itemId);
	
	@Query(value = "select ce.item_id,ce.edition_id, gre.edition_name,gre.erp_ref_id from clf_editions ce inner join gd_rms_editions gre on gre.id = ce.edition_id where ce.item_id in (?1) and ce.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getRmsEditionIdAndNameOnItemId(List<String> itemId);
}
