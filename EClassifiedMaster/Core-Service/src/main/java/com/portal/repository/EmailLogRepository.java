package com.portal.repository;

import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.EmailLog;

public interface EmailLogRepository extends CrudRepository<EmailLog, Integer>{

}
