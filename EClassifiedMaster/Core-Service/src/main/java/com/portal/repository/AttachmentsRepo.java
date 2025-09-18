package com.portal.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.doc.entity.Attachments;
import com.portal.user.entities.UmUsers;

public interface AttachmentsRepo extends CrudRepository<Attachments, String>{
	
	@Query("from Attachments at where at.attachId = ?1 and at.markAsDelete = false ")      
	List<Attachments> getAttachmentDetails(String attachedId);
	
	@Query("from Attachments at where at.attachId in (?1) and at.markAsDelete = false ")      
	List<Attachments> getListOfAttachmentDetails(List<String> attachedId);
	
	@Query("from Attachments at where at.attachId in (?1) and at.markAsDelete = true ")      
	List<Attachments> getListOfAttachmentDetailsSame(List<String> attachedId);
	
	@Query("from Attachments at where at.orderId = ?1 and at.markAsDelete = false ") 
	Attachments getAttachmentsByOrderId(String orderId);
	
	@Query("from Attachments at where at.attachId = ?1 and at.markAsDelete = false ")      
	Attachments getAttachmentOnAttachmentId(String attachedId);
	
	@Query("from Attachments at where at.userId = ?1 and at.markAsDelete = false ") 
	List<Attachments> getAllAttachmentsOnUserId(Integer userId);
	
	
	@Query("from Attachments us where upper(us.attachName)= upper( ?1 ) and us.markAsDelete = ?2")      
	Attachments getAttachmentByName(String attName, boolean markAsDelete);


}
