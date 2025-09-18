package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.user.entities.UmCustomers;

public interface RmsUmCustomersRepo extends CrudRepository<UmCustomers,String> {
	
	@Query("from UmCustomers where mobileNo=?1 and markAsDelete=false")
	List<UmCustomers> getCustomerDetails(String mobileNo);
	
	@Query("from UmCustomers where customerId=?1 and markAsDelete=false")
	List<UmCustomers> getCustomerDetailsOnCustomerId(String customerId);
		
	@Query("from UmCustomers where clientCode LIKE %?1% and markAsDelete=false")
	List<UmCustomers> getCustomerDetailsByClientCode(String clientCode);

	@Query("from UmCustomers uc where uc.customerId = ?1 and uc.markAsDelete = false")
	UmCustomers getCustomerDetailsByOrderId(String customerId);
	

	
	/*
	 * @Query(
	 * value="SELECT uc.state AS uc_state, uc.city, bu.booking_description, gs.state AS gs_state, uc.customer_id, uc.customer_name, uc.mobile_no, uc.client_code, uc.attatch_id, uc.house_no, uc.changed_ts, uc.changed_by, uc.created_ts, uc.created_by, uc.user_id, uc.erp_ref_id, uc.aadhar_number, uc.pan_number, uc.gst_no, uc.office_location, uc.pin_code, uc.address_3, uc.address_2, uc.address_1, uc.email_id, uc.kyc_required,uc.mobile_alt FROM um_customers uc INNER JOIN booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) INNER JOIN gd_state gs ON uc.state = gs.state_code WHERE (uc.client_code LIKE CONCAT('%', ?1, '%') OR LOWER(uc.customer_name) LIKE CONCAT('%', ?1, '%') OR LOWER(uc.mobile_no) LIKE CONCAT('%',?1, '%')) AND uc.mark_as_delete = false"
	 * , nativeQuery = true) List<Object[]> getCustomerDetailsOnClientCode(String
	 * clientCode);
	 */
	
	
	@Query(value="SELECT uc.state AS uc_state, uc.city, gs.state AS gs_state, uc.customer_id, uc.customer_name, uc.mobile_no, uc.client_code, uc.attatch_id, uc.house_no, uc.changed_ts, uc.changed_by, uc.created_ts, uc.created_by, uc.user_id, uc.erp_ref_id, uc.aadhar_number, uc.pan_number, uc.gst_no, uc.office_location, uc.pin_code, uc.address_3, uc.address_2, uc.address_1, uc.email_id, uc.kyc_required,uc.mobile_alt FROM um_customers uc INNER JOIN gd_state gs ON uc.state = gs.state_code WHERE (uc.client_code LIKE CONCAT('%', ?1, '%') OR LOWER(uc.customer_name) LIKE CONCAT('%', ?1, '%') OR LOWER(uc.mobile_no) LIKE CONCAT('%',?1, '%')) AND uc.mark_as_delete = false", nativeQuery = true)
	List<Object[]> getCustomerDetailsOnClientCode(String clientCode);
	
	
	@Query(value="SELECT uc.state AS uc_state, uc.city, gs.state AS gs_state, uc.customer_id, uc.customer_name, uc.mobile_no, uc.client_code, uc.attatch_id, uc.house_no, uc.changed_ts, uc.changed_by, uc.created_ts, uc.created_by, uc.user_id, uc.erp_ref_id, uc.aadhar_number, uc.pan_number, uc.gst_no, uc.office_location, uc.pin_code, uc.address_3, uc.address_2, uc.address_1, uc.email_id, uc.kyc_required,uc.mobile_alt FROM um_customers uc INNER JOIN gd_state gs ON uc.state = gs.state_code WHERE (uc.client_code LIKE CONCAT('%', ?1, '%') OR LOWER(uc.customer_name) LIKE CONCAT('%', ?1, '%') OR LOWER(uc.mobile_no) LIKE CONCAT('%',?1, '%')) AND uc.mark_as_delete = false and uc.emp_id = ?2", nativeQuery = true)
	List<Object[]> getCustomerDetailsOnClientCodeAndLoggedUser(String clientCode,Integer empCode);
	
	
	@Query(value="SELECT uc.state AS uc_state, uc.city, bu.booking_description, gs.state AS gs_state, uc.customer_id, uc.customer_name, uc.mobile_no, uc.client_code, uc.attatch_id, uc.house_no, uc.changed_ts, uc.changed_by, uc.created_ts, uc.created_by, uc.user_id, uc.erp_ref_id, uc.aadhar_number, uc.pan_number, uc.gst_no, uc.office_location, uc.pin_code, uc.address_3, uc.address_2, uc.address_1, uc.email_id, uc.kyc_required FROM um_customers uc INNER JOIN booking_units bu ON uc.city = CAST(bu.booking_code AS VARCHAR) INNER JOIN gd_state gs ON uc.state = gs.state_code WHERE  LOWER(uc.customer_name) LIKE CONCAT('%', LOWER(CAST(?1 AS VARCHAR)), '%') AND uc.mark_as_delete = false", nativeQuery = true)
	List<Object[]> getCustomerDetailsOnName(String customerName);
	
	
	@Query(value="select uc.state,uc.city,uc.customer_id,uc.customer_name,uc.mobile_no,uc.email_id,uc.address_1,uc.pin_code,uc.gst_no,uc.client_code,uc.customer_type_id,uc.user_id  from um_customers uc inner join clf_orders co on uc.customer_id=co.customer_id where uc.customer_id=?1  and uc.mark_as_delete=false and co.order_status=?2", nativeQuery = true)
	List<Object[]> getCustomerByOrderId(String customerId,String status);
	
	@Query("from UmCustomers where mobileAlt=?1 and markAsDelete=false")
	List<UmCustomers> getCustomerDetailsOnMoblieNo(String mobileNo);
}


