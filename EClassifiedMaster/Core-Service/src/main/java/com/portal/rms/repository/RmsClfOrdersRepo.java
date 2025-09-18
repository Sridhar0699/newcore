package com.portal.rms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.clf.entities.ClfOrders;

public interface RmsClfOrdersRepo extends CrudRepository<ClfOrders, String>{
	
	@Query("from ClfOrders co where createdBy=?1 and orderStatus=?2 and orderType=1 and markAsDelete = false ")
	List<ClfOrders> getOpenOrderDetails(Integer userId,String status);
	
	@Query("from ClfOrders co where createdBy=?1 and order_status=?2 and order_id = ?3 and co.markAsDelete = false ")
	List<ClfOrders> getOpenOrderDetailsByOrderId(Integer userId,String status,String orderId);

	@Query("from ClfOrders co where co.orderId = ?1 and co.markAsDelete = false ")
	ClfOrders getOrderDetails(String orderId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_orders SET order_status = ?1,  changed_ts = ?2 where order_id = ?3 and mark_as_delete = false", nativeQuery = true)      
	void updateRmsOnOrderIds(String status, Date date, String orderId);
	
	@Query("from ClfOrders clo where clo.orderId = ?1 and clo.markAsDelete = false ")      
	ClfOrders getOrderDetailsOnOrderId(String orderId);

	@Query("from ClfOrders co where co.customerId is null and co.markAsDelete = false")
	List<ClfOrders> getDraftOderDetails();

	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_orders set mark_as_delete = ?2 and changed_ts = ?1 where customer_id is null and mark_as_delete = false", nativeQuery = true)
	void updateDraftOrderDetails(Date date, boolean b);
}
