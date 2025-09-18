package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.clf.entities.ClfOrderItems;

public interface RmsClfOrderItemsRepo extends CrudRepository<ClfOrderItems,String>{

	@Query(value = "select status,count(*) from clf_order_items itm inner join clf_payment_response_tracking cp on itm.order_id = cp.sec_order_id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id where (itm.status = 'PENDING' or itm.download_status = true) and itm.mark_as_delete  = false group by status", nativeQuery = true)      
	List<Object[]> getDashboardCountsByAdmin();
	
	@Query(value = "select status,count(*) from clf_order_items itm inner join clf_payment_response_tracking cp on itm.order_id = cp.sec_order_id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id inner join um_customers uc on co.customer_id = uc.customer_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id where itm.created_by = ?1 and cp.payment_status = 'success' and itm.mark_as_delete  = false group by status", nativeQuery = true)      
	List<Object[]> getDashboardCountsByLogin(Integer userId);
	
	@Query(value = "select status,count(*) from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join clf_payment_response_tracking cp on itm.order_id = cp.sec_order_id where co.customer_id = ?1 and itm.mark_as_delete  = false and cp.payment_status = 'success' group by status ", nativeQuery = true)      
	List<Object[]> getDashboardCountsByCustomerId(String customerId);
	
	@Query("from ClfOrderItems us where clfOrders.orderId = ?1 and us.markAsDelete = false ")      
	List<ClfOrderItems> getOpenOrderItems(String orderId);
	
	@Query("from ClfOrderItems us where us.itemId = ?1 and us.markAsDelete = false ")      
	ClfOrderItems getItemDetailsOnItemId(String itemId);
	
	
	@Query(value = "select itm.item_id, itm.order_id, itm.classified_type, itm.classified_ads_type, itm.scheme, itm.category, itm.subcategory, itm.lang, itm.clf_content, itm.created_by, itm.created_ts, coir.rate, coir.lines, coir.extra_line_rate, coir.line_count, coir.char_count, coir.total_amount, clo.customer_id, itm.classified_ads_sub_type,itm.ad_id, clo.booking_unit from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders clo on itm.order_id = clo.order_id where itm.item_id = ?1", nativeQuery = true)
	List<Object[]> viewClfItemDetails(String itemId);

	@Query(value = "select itm.scheme, itm.clf_content,itm.classified_ads_sub_type,itm.category_group,itm.sub_group,itm.child_group,roi.no_of_insertions,roi.space_width,roi.space_height,roi.page_position,roi.format_type,roi.fixed_format,roi.page_number,roi.category_discount,roi.multi_discount,roi.additional_discount,roi.surcharge_rate,roi.multi_discount_amount,roi.surcharge_amount,roi.additional_discount_amount,coir.rate,coir.total_amount,coir.cgst,coir.sgst,coir.igst,coir.cgst_value,coir.sgst_value,coir.igst_value,coir.amount,coir.rate_per_square_cms,roi.category_discount_amount from clf_order_items itm  inner join rms_order_items roi on roi.order_id=itm.order_id inner join clf_order_item_rates coir on coir.order_id=itm.order_id where itm.order_id=?1 and itm.mark_as_delete=false" , nativeQuery = true)
	List<Object[]> getInsertionDetailsOnOrderId(String orderId);
}
