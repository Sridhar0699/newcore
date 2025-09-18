package com.portal.rms.repository;

import org.springframework.data.repository.CrudRepository;

import com.portal.clf.entities.ClfPayments;

public interface RmsPaymentsRepo extends CrudRepository<ClfPayments, String>{

}
