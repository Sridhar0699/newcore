package com.portal.rms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.rms.entity.RmsOrderItems;

public interface RmsOrderItemsRepo extends CrudRepository<RmsOrderItems,String>{
	
	
	@Query("from RmsOrderItems rs where rs.itemId=?1 and rs.markAsDelete=false")
	public List<RmsOrderItems> getRmsOrderItemsOnItemId(String itemId);	
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE rms_order_items  SET mark_as_delete = ?1, changed_by = ?2, changed_ts = ?3 where item_id = ?4 and mark_as_delete = false", nativeQuery = true)      
	void removeRmsOrderItemsOnItemId(Boolean flag, Integer userId, Date changedTs, String itemId);
	
	@Query("from RmsOrderItems rs where rs.orderId = ?1 and rs.markAsDelete = false")
	public RmsOrderItems getRmsItemsDetailsOnOrderId(String orderId);


}
