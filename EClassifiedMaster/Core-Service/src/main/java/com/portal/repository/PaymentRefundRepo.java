package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.clf.entities.PaymentsRefund;

public interface PaymentRefundRepo extends CrudRepository<PaymentsRefund, String>{
	
	@Query(value = "from PaymentsRefund where orderId = ?1 ")
	PaymentsRefund getPaymentsByOrderId(String orderId);
	
	@Query(value = "select mercId,refundId from PaymentsRefund where refundStatus=?1 and markAsDelete=false")
	List<Object[]> getPayments(List<String> refundStatus);
	
	@Query(value = "from PaymentsRefund where refundId = ?1 and markAsDelete=false")
	PaymentsRefund getPaymentDetailsOnRefundId(String refundId);
	
	@Query(value = "from PaymentsRefund where orderId = ?1 ")
	List<PaymentsRefund> getPaymentsDetailsByOrderId(String orderId);

}
