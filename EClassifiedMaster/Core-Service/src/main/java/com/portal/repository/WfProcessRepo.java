package com.portal.repository;

import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfProcess;

public interface WfProcessRepo extends CrudRepository<WfProcess, String>{

}
