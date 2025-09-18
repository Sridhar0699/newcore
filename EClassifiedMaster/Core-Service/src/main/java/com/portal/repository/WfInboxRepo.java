package com.portal.repository;

import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfInbox;

public interface WfInboxRepo extends CrudRepository<WfInbox, String>{

}
