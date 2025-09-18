package com.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.portal.gd.entities.GdSettingsDefinitions;

public interface GdSettingsDefinitionsRepository extends CrudRepository<GdSettingsDefinitions, Integer> {

	@Query("select gsd,oas from GdSettingsDefinitions gsd left join gsd.omApplSettings oas on oas.refObjId is null or oas.refObjId='' or oas.refObjId=' ' where gsd.gdSettingTypes.settingTypeId =?1 and gsd.settingGroupName IN (?2) and gsd.markAsDelete = false  order by gsd.settingSeqNo")      
	List<Object[]> getAppSettings(String sType, List<String> grps);
	
	@Query("select gsd,oas from GdSettingsDefinitions gsd left join gsd.omApplSettings oas on oas.refObjId IN (?1) where gsd.gdSettingTypes.settingTypeId =?2 and gsd.settingGroupName IN (?3) and gsd.markAsDelete = false order by gsd.settingSeqNo")      
	List<Object[]> getOtherSettings(List<String> refObjId, Integer sType, List<String> grps);
	
	@Query("from GdSettingsDefinitions gsd where gsd.gdSettingTypes.settingTypeId =?1 and gsd.settingGroupName IN (?2) and gsd.markAsDelete = false  order by gsd.settingSeqNo")      
	List<GdSettingsDefinitions> getSettingsBySTypeGrps(Integer sType, List<String> grps);

	@Query("select gsd,oas from GdSettingsDefinitions gsd left join gsd.omApplSettings oas on oas.refObjId is null where gsd.gdSettingTypes.settingTypeId in(?1) and gsd.markAsDelete=false order by gsd.settingGroupName,gsd.settingSeqNo")      
	List<Object[]> getSettingsBySTypeId(List<Integer> settingTypeId);
	
	@Query("select gsd,oas from GdSettingsDefinitions gsd left join gsd.omApplSettings oas on oas.refObjId=?1 where gsd.gdSettingTypes.settingTypeId in(?2,4) and gsd.markAsDelete=false order by gsd.settingGroupName,gsd.settingSeqNo")      
	List<Object[]> getSettingsBySTypeIdAndObjRefId(String refObjId, List<Integer> settingTypeId);
	
	@Query("select oas from GdSettingsDefinitions gsd left join gsd.omApplSettings oas on oas.refObjId IN (?1) where gsd.gdSettingTypes.settingTypeId =?2 and gsd.settingGroupName IN (?3) and gsd.markAsDelete=false order by gsd.settingSeqNo")      
	List<Object> getSettingsByRefIdSTypeSgrp(List<String> refObjId, Integer settingTypeId, List<String> grps);
	
	@Query("from GdSettingsDefinitions gsd where gsd.gdSettingTypes.settingTypeId =?1 and gsd.settingGroupName IN (?2) and gsd.markAsDelete = false  order by gsd.settingSeqNo")      
	List<GdSettingsDefinitions> getSettingsByStypeGrps(Integer settingTypeId, List<String> grps);
	
	@Query("from GdSettingsDefinitions gsd where gsd.settingId in (?1) and gsd.markAsDelete = false ")      
	List<GdSettingsDefinitions> getSettingsBySettingIds(List<Integer> settingIds);
	
	@Query("from GdSettingsDefinitions gsd where gsd.settingGroupName = ?1 and settingShortId = ?2 and gsd.markAsDelete = false ")      
	List<GdSettingsDefinitions> getSettingsByShortIdGroup(String groupName, String settingShortId);
	
}
