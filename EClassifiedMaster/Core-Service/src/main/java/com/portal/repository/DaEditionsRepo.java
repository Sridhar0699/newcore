package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.clf.entities.ClfEditions;

public interface DaEditionsRepo extends CrudRepository<ClfEditions, String>{

	@Query(value = "select ce.item_id,ce.edition_id, gce.edition_name,gce.erp_ref_id from clf_editions ce inner join gd_da_editions gce on gce.id = ce.edition_id where ce.item_id in (?1) and ce.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getEditionIdAndNameOnItemId(List<String> itemId);
	
	@Query(value = "select ce.item_id,ce.edition_id, gce.edition_name,gce.erp_ref_id from clf_editions ce inner join gd_classified_editions gce on gce.id = ce.edition_id where ce.item_id in (?1) and ce.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getClassifiedEditionIdAndNameOnItemId(List<String> itemId);
}
