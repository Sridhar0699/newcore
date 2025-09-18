package com.portal.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.da.entities.ClfTemplateContent;

public interface ClfTemplateContentRepo extends CrudRepository<ClfTemplateContent, String>{

	@Transactional
	@Modifying
	@Query(value = "UPDATE clf_template_content  SET mark_as_delete = ?1, changed_by = ?2, changed_ts = ?3 where item_id = ?4 and mark_as_delete = false", nativeQuery = true)      
	void removeClfTemplateContent(boolean b, Integer userId, Date date, String itemId);

//	@Query(value = "from clf_template_content ce where ce.item_id = ?1 and ce.mark_as_delete = false", nativeQuery = true)      
	@Query("FROM ClfTemplateContent ce WHERE ce.itemId = ?1 AND ce.markAsDelete = false")
	List<ClfTemplateContent> getTemplateContentDetailsOnItemId(@NotNull String itemId);
	
	@Query("from ClfTemplateContent ce where ce.itemId in (?1) and ce.markAsDelete = false and ce.attachmentId is not null")
	List<ClfTemplateContent> getTemplateAttachmentsOnItemIds(List<String> itemIds);
	
	@Query(value = "select ctc.item_id,ctc.content from clf_template_content ctc where ctc.item_id = ?1 and ctc.type = 'text' and ctc.mark_as_delete = false",nativeQuery = true)
	List<Object[]> getOrderContent(String itemId);

}
