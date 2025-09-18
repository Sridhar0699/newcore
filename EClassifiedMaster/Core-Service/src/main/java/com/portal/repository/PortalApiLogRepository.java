package com.portal.repository;

import org.springframework.data.repository.CrudRepository;

import com.portal.apilog.entities.PortalApiLog;

public interface PortalApiLogRepository extends CrudRepository<PortalApiLog, Integer>{

}
