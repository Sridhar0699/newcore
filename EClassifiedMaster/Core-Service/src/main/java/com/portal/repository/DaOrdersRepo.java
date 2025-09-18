package com.portal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.clf.entities.ClfOrders;

public interface DaOrdersRepo extends CrudRepository<ClfOrders, String>{

	@Query("from ClfOrders clo where clo.orderId = ?1 and clo.markAsDelete = false ")      
	ClfOrders getOrderDetailsOnOrderId(String orderId);

	@Query("from ClfOrders us where createdBy=?1 and order_status=?2 and us.orderType = ?3 and us.markAsDelete = false order by us.createdTs desc")      
	List<ClfOrders> getOpenOrderDetailsByLoggedInUser(Integer userId, String status,Integer orderType);
	
	@Query(value = "select ord.order_id, gut.type_desc , uu.first_name, uu.middle_name, ord.order_status,ord.payment_status,ord.booking_unit,bu.payment_child_id,ord.edition_type from clf_orders ord inner join booking_units bu on ord.booking_unit = bu.booking_code inner join gd_user_types gut on ord.user_type_id = gut.user_type_id inner join um_users uu on ord.created_by = uu.user_id where ord.order_id  in (?1) and ord.order_type = 3 and  ord.mark_as_delete  = false order by ord.created_ts desc", nativeQuery = true)      
	List<Object[]> getOrderDetailsList(List<String> orderId);

	@Query(value = "select ord.order_id,umc.customer_id,umc.email_id,itm.ad_Id from clf_orders ord inner join um_customers umc on ord.customer_id = umc.customer_id inner join clf_order_items itm on ord.order_id = itm.order_id where ord.order_id in(?1) and ord.mark_as_delete  = false and itm.mark_as_delete = false", nativeQuery = true)      
	List<Object[]> getCustomerData(List<String> orderId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_orders SET payment_status = ?1, changed_by = ?2, changed_ts = ?3,comments=?5 where order_id in (?4) and mark_as_delete = false", nativeQuery = true)      
	void updateClfOnOrderIds(String status, Integer userId, Date date, List<String> orderId,String comments);
}
