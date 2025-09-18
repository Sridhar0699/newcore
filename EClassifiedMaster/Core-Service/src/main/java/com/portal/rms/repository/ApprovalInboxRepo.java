package com.portal.rms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.rms.entity.ApprovalInbox;

public interface ApprovalInboxRepo extends CrudRepository<ApprovalInbox, String>{

	@Query(value = "from ApprovalInbox ai where ai.currentLevel = ?1 and ai.orderId = ?2 and ai.markAsDelete = false")  
	ApprovalInbox getApprovalLevel(Integer level, String orderId);

	@Query(value = "from ApprovalInbox ai where ai.inboxId = ?1 and ai.markAsDelete = false") 
	ApprovalInbox getApprovalInboxOnId(String inboxId);

	@Query(value = "from ApprovalInbox ai where ai.approverUserId = ?1  and ai.markAsDelete = false order by ai.currentLevel asc") 
	List<ApprovalInbox> getInboxListOnLoginUserIdWithPagination(Integer userId,Pageable pageable);
	
	@Query(value = "from ApprovalInbox ai where ai.approverUserId = ?1  and ai.markAsDelete = false order by ai.currentLevel asc") 
	List<ApprovalInbox> getInboxListOnLoginUserId(Integer userId);

	@Query(value = "from ApprovalInbox ai where   ai.markAsDelete = false order by ai.currentLevel asc")
	List<ApprovalInbox> getInboxListForSuperAdminWithPagination(Pageable pageable);
	
	@Query(value = "from ApprovalInbox ai where   ai.markAsDelete = false order by ai.currentLevel asc")
	List<ApprovalInbox> getInboxListForSuperAdmin();

	@Query(value = "from ApprovalInbox ai where ai.orderId = ?1 and ai.markAsDelete = false  order by ai.currentLevel asc")
	List<ApprovalInbox> getApprovalListByOrderId(String orderId);
	
	@Query(value = "from ApprovalInbox ai where ai.itemId in (?1) and ai.markAsDelete = false")
	List<ApprovalInbox> getApprovalListByItemId(List<String> itemId);
	
	@Query(value = "from ApprovalInbox ai where ai.itemId in (?1) and ai.status = 'PENDING' and ai.markAsDelete = false")
	List<ApprovalInbox> getPendingInboxForCurrentLevel(List<String> itemId);
	@Query(value = "select ai.item_id,ai.order_id,ai.status,ai.current_level,ai.approval_timestamp,ai.approver_user_id,ai.comments,uu.first_name,uu.last_name from approval_inbox ai inner join um_users uu on ai.approver_user_id = uu.user_id where ai.item_id in (?1) and ai.mark_as_delete = false order by ai.current_level desc",nativeQuery = true)
	List<Object[]> getCurrentLevelOfApprovalInbox(List<String> itemIds);

}
