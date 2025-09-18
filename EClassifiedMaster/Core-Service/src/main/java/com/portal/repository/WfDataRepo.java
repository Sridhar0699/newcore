package com.portal.repository;

import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfData;

public interface WfDataRepo extends CrudRepository<WfData, String>{

}
