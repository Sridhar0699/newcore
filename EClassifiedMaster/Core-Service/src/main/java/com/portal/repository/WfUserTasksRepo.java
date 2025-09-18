package com.portal.repository;

import org.springframework.data.repository.CrudRepository;

import com.portal.wf.entity.WfUserTasks;

public interface WfUserTasksRepo extends CrudRepository<WfUserTasks, String>{

}
