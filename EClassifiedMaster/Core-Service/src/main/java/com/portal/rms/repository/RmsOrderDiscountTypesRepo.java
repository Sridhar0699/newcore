package com.portal.rms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.rms.entity.RmsOrderDiscountTypes;

public interface RmsOrderDiscountTypesRepo extends CrudRepository<RmsOrderDiscountTypes, String>{

	@Transactional
	@Modifying
	@Query(value = "UPDATE rms_order_discount_types  SET mark_as_delete = ?1, changed_by = ?2, changed_ts = ?3 where order_id = ?4 and mark_as_delete = false", nativeQuery = true)      
	void removeDiscounTypes(Boolean flag, Integer userId, Date changedTs, String itemId);

	@Query(value = "select rodt.item_id, rodt.order_id, rodt.discount_id, grdt.discount_type from rms_order_discount_types rodt inner join gd_rms_discount_types grdt on grdt.id = rodt.discount_id where rodt.item_id in (?1) and rodt.mark_as_delete = false",nativeQuery = true)
	List<Object[]> getDiscountTypes(List<String> itemIds);
}
