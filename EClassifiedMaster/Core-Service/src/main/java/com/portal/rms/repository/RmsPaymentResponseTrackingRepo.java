package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.rms.entity.RmsPaymentsResponse;


public interface RmsPaymentResponseTrackingRepo extends CrudRepository<RmsPaymentsResponse, String>{
	
	@Query(value = "select itm.ad_id,rpr.item_id,rpr.order_id,rpr.bank_branch,rpr.bank_ref_id,rpr.bank_or_upi,rpr.cash_receipt_no,rpr.other_details,rpr.payment_mode,rpr.payment_method,co.booking_unit,bu.profit_center,bu.gi_account from rms_payments_response rpr inner join clf_order_items itm ON rpr.order_id = itm.order_id inner join clf_orders co ON rpr.order_id = co.order_id inner join booking_units bu ON co.booking_unit = bu.booking_code where rpr.order_id in(?1) and itm.mark_as_delete = false", nativeQuery = true)
	List<Object[]> getRmsTransactionDetails(List<String> orderIds);

	
}
