package com.portal.rms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.doc.entity.Attachments;

public interface RmsAttachmentsRepo extends CrudRepository<Attachments,String>{

	
	@Transactional
	@Modifying
	@Query(value = "UPDATE attachments   SET customer_id = ?1, changed_by = ?2, changed_ts = ?3 where attatch_id in (?4) and mark_as_delete = false", nativeQuery = true)      
	void updateAttachemets(String customerId, Integer userId, Date changedTs, List<String> attachementIds);

	@Query("from Attachments at where at.customerId=?1 and at.markAsDelete = false")
	List<Attachments> getAllAttachmentsByCustomerId(String customerId);
	
	@Transactional
    @Modifying
    @Query("UPDATE Attachments a SET a.markAsDelete = ?1,a.changedBy = ?2,a.changedTs = ?3 WHERE a.attachId in (?4) AND a.markAsDelete =false")
    void removeAttachmentsByCustomerId( boolean markAsDelete, Integer changedBy, Date changedTs,List<String> attachmentIds);
	
	@Query("from Attachments at where at.attachId in (?1) and at.markAsDelete = false")
	List<Attachments> getAllAttachmentsByAttachmentId(List<String> attachmentIds);
	
	@Query("from Attachments at where at.attachId =?1 and at.markAsDelete = false")
	Attachments getAllAttachmentByAttachmentId(String attachmentIds);
    	
	@Query("from Attachments at where at.orderId=?1 and at.markAsDelete = false")
	List<Attachments> getAllAttachmentsByOrderId(String orderId);
	
}
