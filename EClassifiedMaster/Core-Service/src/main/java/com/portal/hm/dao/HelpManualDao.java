package com.portal.hm.dao;

import com.portal.hm.model.HMDaoCommonModel;
import com.portal.user.entities.UmOrgRoles;

public interface HelpManualDao {

	public boolean createOrUpdateHelpManuals(HMDaoCommonModel hmDaoCommonModel);

	public HMDaoCommonModel getHelpManuals(HMDaoCommonModel hmDaoCommonModel);

	public UmOrgRoles getRolDetail(int roleId);

	public HMDaoCommonModel downloadHelpManual(HMDaoCommonModel hmDaoCommonModel);
	
	public UmOrgRoles getRolDetailByShortId(String roleShortId);

}
