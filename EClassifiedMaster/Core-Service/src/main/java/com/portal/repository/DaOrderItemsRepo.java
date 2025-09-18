package com.portal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.clf.entities.ClfOrderItems;

public interface DaOrderItemsRepo extends CrudRepository<ClfOrderItems, String>{

	@Query(value = "select status,count(*) from clf_order_items itm inner join clf_payment_response_tracking cp on itm.order_id = cp.sec_order_id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id where itm.created_by = ?1 and cp.payment_status = 'success' and co.order_type = 3 and itm.mark_as_delete  = false and co.booking_unit = ?2 group by status", nativeQuery = true)
	List<Object[]> getDashboardCountsByLogin(Integer userId, Integer bookingUnit);
	
	@Query(value = "select status,count(*) from clf_order_items itm inner join clf_payment_response_tracking cp on itm.order_id = cp.sec_order_id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id where cp.payment_status = 'success' and co.order_type = 3 and itm.mark_as_delete  = false and co.booking_unit = ?1 group by status", nativeQuery = true)
	List<Object[]> getDashboardCountsByAdmin(Integer bookingUnit);
	
	@Query(value = "select status,count(*) from clf_order_items itm inner join clf_payment_response_tracking cp on itm.order_id = cp.sec_order_id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders co on co.order_id = itm.order_id inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id where cp.payment_status = 'success' and co.order_type = 3 and itm.mark_as_delete  = false group by status", nativeQuery = true)
	List<Object[]> getDashboardCountsByAdminAndSuperAdmin();
	
	@Query(value = "select status,count(*) from clf_order_items itm inner join clf_orders co on co.order_id = itm.order_id inner join clf_payment_response_tracking cp on itm.order_id = cp.sec_order_id where co.customer_id = ?1 and itm.mark_as_delete  = false and cp.payment_status = 'success' and co.order_type = 2 and co.booking_unit = ?2 group by status ", nativeQuery = true)
	List<Object[]> getDashboardCountsByCustomerId(String customerId, Integer bookingUnit);

	@Query(value = "SELECT itm.item_id, itm.order_id, gct.type, gcat.ads_type, gcast.ads_sub_type, gcc.category_type, gcl.language, itm.clf_content, itm.created_ts, coir.total_amount, itm.ad_id,ds.size,itm.main_attachment_id,itm.art_work_attachment_id FROM clf_order_items itm INNER JOIN gd_classified_types gct ON itm.classified_type = gct.id INNER JOIN gd_classified_ads_types gcat ON itm.classified_ads_type = gcat.id INNER JOIN gd_classified_ads_sub_types gcast ON itm.classified_ads_sub_type = gcast.id INNER JOIN gd_da_classified_category gcc ON itm.category = gcc.id INNER JOIN gd_classified_languages gcl ON itm.lang = gcl.id INNER JOIN clf_order_item_rates coir ON itm.item_id = coir.item_id inner join da_sizes ds on ds.size_id = itm.size_id WHERE itm.order_id IN (?1) AND itm.mark_as_delete = false order by itm.created_ts desc", nativeQuery = true)
	List<Object[]> getOpenOrderItemsDetailsByOrderIdList(List<String> orderId);

//	@Query(value = "select itm.item_id , itm.order_id ,gct.type ,gcat.ads_type ,gcast.ads_sub_type ,gcs.scheme ,gcc.classified_category ,gcs2.classified_subcategory,gcl.language ,itm.clf_content,itm.created_ts, coir.total_amount,gce.edition_name from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_classified_schemes gcs on itm.scheme = gcs.id inner join gd_classified_category gcc on itm.category = gcc.id inner join gd_classified_subcategory gcs2 on itm.subcategory = gcs2.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_editions ce on ce.item_id = itm.item_id inner join gd_classified_editions gce on gce.id = ce.edition_id  where itm.order_id in (?1) and itm.mark_as_delete = false", nativeQuery = true)

//	@Query(value = "select itm.item_id , itm.order_id ,gct.type ,gcat.ads_type ,gcast.ads_sub_type ,gdcc.category_type,gcl.language ,itm.clf_content,itm.created_ts, coir.total_amount from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_editions ce on ce.item_id = itm.item_id inner join gd_da_editions gde on gde.id = ce.edition_id where itm.order_id in (?1) and itm.mark_as_delete = false",nativeQuery = true)

	@Query(value = "select itm.item_id , itm.order_id ,gct.type ,gcat.ads_type ,gcast.ads_sub_type ,gdcc.category_type,gcl.language ,itm.clf_content,itm.created_ts, coir.total_amount,gde.edition_name from clf_order_items itm inner join gd_classified_types gct on itm.classified_type = gct.id inner join gd_classified_ads_types gcat on itm.classified_ads_type = gcat.id inner join gd_classified_ads_sub_types gcast on itm.classified_ads_sub_type  = gcast.id inner join gd_da_classified_category gdcc on itm.category = gdcc.id inner join gd_classified_languages gcl on itm.lang = gcl.id inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_editions ce on ce.item_id = itm.item_id inner join gd_da_editions gde on gde.id = ce.edition_id where itm.order_id in (?1) and itm.mark_as_delete = false",nativeQuery = true)
	List<Object[]> getPendingCartItemsList(List<String> orderId);
	
	@Query(value = "select itm.item_id, itm.order_id, itm.classified_type, itm.classified_ads_type, itm.scheme, itm.category, itm.subcategory, itm.lang, itm.clf_content, itm.created_by, itm.created_ts, coir.rate, coir.lines, coir.extra_line_rate, coir.line_count, coir.char_count, coir.total_amount, clo.customer_id, itm.classified_ads_sub_type,itm.ad_id, clo.booking_unit,clo.customer_name,clo.edition_type,clo.attached_id,itm.size_id,itm.template_id,itm.art_work_attachment_id from clf_order_items itm inner join clf_order_item_rates coir on itm.item_id = coir.item_id inner join clf_orders clo on itm.order_id = clo.order_id where itm.item_id = ?1 and itm.mark_as_delete = false", nativeQuery = true)
	List<Object[]> viewDaItemDetails(String itemId);
	
	@Query("from ClfOrderItems us where us.itemId = ?1 and us.markAsDelete = false ")
	ClfOrderItems getItemDetailsOnItemId(String itemId);
	
	@Query(value = "select itm.item_id,dt.template_image_id,dt.template_image_url from clf_order_items itm inner join da_templates dt on dt.template_id = itm.template_id where itm.template_id in (?1) and itm.mark_as_delete = false", nativeQuery = true)
	List<Object[]> getDaOrderItemsByTemplateId(List<String> templateId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_order_items  SET status = ?1, changed_by = ?2, changed_ts = ?3 where item_id in (?4) and mark_as_delete = false", nativeQuery = true)
	void updateDisplayAds(String status, Integer userId, Date changedTs, List<String> itemId);

//	@Query(value = "select itm.item_id,itm.order_id,itm.ad_id,ctc,text_id,ctc.template_id,ctc.text_index,ctc.content,ctc.x_coordinate,ctc.y_coordinate,ctc.width,ctc.height,ctc.font,ctc.type,ctc.template_cnt_id,ctc.attatch_url,ctc.attachment_id from clf_template_content ctc inner join clf_order_items itm on itm.item_id = ctc.item_id inner join clf_publish_dates cpd on  cpd.item_id = ctc.item_id where cpd.publish_date = ?1 and itm.mark_as_delete = false and ctc.mark_as_delete = false and cpd.download_status = false and cpd.mark_as_delete = false", nativeQuery = true)
//	List<Object[]> getAdsToBeDownload(Date dateFormatter);
	
	@Query(value = "select itm.item_id,itm.order_id,itm.ad_id,ctc.text_id,ctc.template_id,ctc.text_index,ctc.content,ctc.x_coordinate,ctc.y_coordinate,ctc.width,ctc.height,ctc.font,ctc.type,ctc.template_cnt_id,ctc.attatch_url,ctc.attachment_id from clf_order_items itm inner join clf_template_content ctc on itm.item_id = ctc.item_id inner join clf_publish_dates cpd on itm.item_id = cpd.item_id where cpd.publish_date = ?1 and itm.mark_as_delete = false and ctc.mark_as_delete = false", nativeQuery = true)
	List<Object[]> getAdsToBeDownload(Date dateFormatter);
	
//	@Query(value = "select gcc.classified_category, coi.clf_content, coi.item_id, co.erp_order_id,coi.ad_id,coir.line_count,gcast.ads_sub_type from clf_order_items coi inner join clf_orders co ON coi.order_id = co.order_id inner join clf_publish_dates cpd on  cpd.item_id = coi.item_id inner join gd_classified_category gcc on gcc.id = coi.category inner join clf_order_item_rates coir on coi.item_id = coir.item_id inner join gd_classified_ads_sub_types gcast on coi.classified_ads_type = gcast.id where co.payment_status = 'APPROVED' and cpd.publish_date = ?1 and co.booking_unit = ?2 and cpd.download_status = false and cpd.mark_as_delete = false and coi.mark_as_delete = false", nativeQuery = true)
//	List<Object[]> getAdsTobeDownload(Date date, Integer bookingUnit);

}
