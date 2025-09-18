package com.portal.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.portal.gd.entities.GdSettingsDefinitions;

public interface SettingsRepository extends CrudRepository<GdSettingsDefinitions, Integer> {

	@Query("select gsd,oas from GdSettingsDefinitions gsd left join gsd.omApplSettings oas on oas.refObjId is null or oas.refObjId='' or oas.refObjId=' ' where gsd.gdSettingTypes.settingTypeId =?1 and gsd.settingGroupName IN (?2) and gsd.markAsDelete = false  order by gsd.settingSeqNo")
	List<Object[]> getAppSettings(Integer settingTypeId, List<String> settingGroupName);

	@Query("select gsd,oas from GdSettingsDefinitions gsd left join gsd.omApplSettings oas on oas.refObjId IN (?1) where gsd.gdSettingTypes.settingTypeId =?2 and gsd.settingGroupName IN (?3) and gsd.markAsDelete = false order by gsd.settingSeqNo")
	List<Object[]> getOtherSettings(List<String> refObjIds, Integer sType, List<String> settingGroupName);

}
