package com.portal.hm.service;

import com.portal.common.models.GenericApiResponse;
import com.portal.hm.model.HMDaoCommonModel;
import com.portal.hm.model.HelpManualDetails;

public interface HMService {

	public GenericApiResponse createOrUpdateHelpManuals(HelpManualDetails helpManualDetails);

	public GenericApiResponse getHelpManuals(HMDaoCommonModel hmDaoCommonModel);

	HMDaoCommonModel downloadHelpManual(HMDaoCommonModel hmDaoCommonModel);

}
