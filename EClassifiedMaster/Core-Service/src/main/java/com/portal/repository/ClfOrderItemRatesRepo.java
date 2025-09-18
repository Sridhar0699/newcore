package com.portal.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.clf.entities.ClfOrderItemRates;

public interface ClfOrderItemRatesRepo extends CrudRepository<ClfOrderItemRates, String>{
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_order_item_rates  SET mark_as_delete = ?1, changed_by = ?2, changed_ts = ?3 where item_id = ?4 and mark_as_delete = false", nativeQuery = true)      
	void removeClfItemRatesOnItemId(Boolean flag, Integer userId, Date changedTs, String itemId);

}
