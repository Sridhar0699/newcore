package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.clf.entities.ClfPaymentResponseTracking;

public interface ClfPaymentResponseTrackingRepo extends CrudRepository<ClfPaymentResponseTracking, String>{
	
	@Query(value = "select itm.ad_id, cpt.gate_way_id, cpt.sec_order_id,cpt.ecom_order_id,cpt.transaction_date,cpt.payment_method_type,cpt.amount,cpt.payment_status,cpt.bank_ref_no,cpt.transactionid,cpt.txn_process_type,co.booking_unit,bu.profit_center,bu.gi_account from clf_payment_response_tracking cpt inner join clf_order_items itm ON cpt.sec_order_id = itm.order_id inner join clf_orders co ON cpt.sec_order_id = co.order_id inner join booking_units bu ON co.booking_unit = bu.booking_code where cpt.sec_order_id in(?1) and cpt.payment_status = 'success' and itm.mark_as_delete = false ", nativeQuery = true)
	List<Object[]> getTransactionDetails(List<String> orderIds);
	
//	@Query(value = "select transactionid,sec_order_id,order_id,payment_status,created_by from clf_payment_response_tracking  where payment_status not in (?1) ", nativeQuery = true)
//	List<Object[]> getPendingStatusUpdateOrders(List<String> statuses);
	
	@Query(value = "select transactionid,sec_order_id,order_id,payment_status,created_by from clf_payment_response_tracking  where payment_status not in (?1) and mark_as_delete = false", nativeQuery = true)
	List<Object[]> getPendingStatusUpdateOrders(List<String> statuses);
	
	@Query(value = "from ClfPaymentResponseTracking where transactionId in (?1) ")
	List<ClfPaymentResponseTracking> getPaymentsByTransactionIds(List<String> transactionIds);
	
	@Query(value = "from ClfPaymentResponseTracking where orderId in (?1) and markAsDelete = false")
	List<ClfPaymentResponseTracking> getPaymentsByOrderIds(List<String> orderIds);
	
	@Query(value = "from ClfPaymentResponseTracking where orderId = ?1 ")
	ClfPaymentResponseTracking getPaymentsByOrderId(String orderId);
	
	@Query(value = "from ClfPaymentResponseTracking where ecomOrderId = ?1 ")
	ClfPaymentResponseTracking getPaymentsByEcomOrderId(String orderId);
	
	 @Query(value = "select cp.transactionid, cp.order_id, cp.amount, bu.payment_child_id,cp.transaction_date from clf_payment_response_tracking cp inner join clf_orders co on cp.sec_order_id=co.order_id inner join booking_units bu ON co.booking_unit = bu.booking_code where cp.sec_order_id in (?1) and cp.response IS NOT NULL", nativeQuery = true)
	 List<Object[]> getPaymentsDetailsByOrderId(List<String> orderId);
	    
	@Query(value = "select cp.transactionid, cp.order_id, cp.amount, bu.payment_child_id,cp.transaction_date from clf_payment_response_tracking cp inner join clf_orders co on cp.sec_order_id=co.order_id inner join booking_units bu ON co.booking_unit = bu.booking_code where cp.sec_order_id =?1 and cp.response IS NOT NULL", nativeQuery = true)
	List<Object[]> getPaymentsDetailsUsingOrderId(String orderId);
	    
	@Query(value = "select cp.transactionid, cp.order_id, cp.amount,cp.transaction_date,cp.bank_ref_no,cp.transaction_error_desc,bu.booking_location from clf_payment_response_tracking cp inner join clf_orders co on cp.sec_order_id=co.order_id inner join booking_units bu on co.booking_unit=bu.booking_code where cp.sec_order_id =?1 and cp.response IS NOT NULL", nativeQuery = true)
	List<Object[]> getPaymentDetailByOrderId(String orderId);
	
	@Query(value = "from ClfPaymentResponseTracking where secOrderId = ?1 and response IS NOT NULL ")
	List<ClfPaymentResponseTracking> getPaymentDetailsByOrderId(String orderId);
	
	@Query(value = "from ClfPaymentResponseTracking where secOrderId = ?1 and markAsDelete = false")
	ClfPaymentResponseTracking getTransactionDetOnSecOrderId(String orderId);
}
