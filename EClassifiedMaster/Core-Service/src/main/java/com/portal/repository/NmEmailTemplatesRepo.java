package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.communication.entities.NmEmailTemplates;

public interface NmEmailTemplatesRepo extends CrudRepository<NmEmailTemplates, Integer>{

	@Query("from NmEmailTemplates et where (et.orgId=?1 or et.orgId is null or et.orgId = '' or et.orgId = ' ') and et.templateShortId = ?2 or et.templateShortId = 'GD_TEMPLATE'")      
	List<NmEmailTemplates> getNmTemplates(String orgId, String templateShortId);
	
}
