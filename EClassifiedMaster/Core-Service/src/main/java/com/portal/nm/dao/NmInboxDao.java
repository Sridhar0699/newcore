package com.portal.nm.dao;

import java.util.List;

import org.json.JSONObject;

import com.portal.nm.model.NmEmailPayloadTo;
import com.portal.nm.model.NmInboxGetPushPayloadTo;
import com.portal.nm.model.NmInboxTo;

public interface NmInboxDao {

	public List<NmInboxTo> getAllRequesters(String loginId);

	public List<NmInboxTo> getAllApprovers(String loginId, String roleType);

	public JSONObject findUnreadWfInboxDao(String login_id, String last_ts, boolean fromWebSocket);

	boolean addWfInboxPushNotification(NmInboxTo wfInboxTo);

	public List<NmInboxTo> getUnreadWfInboxPushnotifications(String org_id,
			NmInboxGetPushPayloadTo wfInboxGetPushPayloadTo,String logonId);

	boolean updateWfInboxPushnotifications(Integer wf_item_id, String action_type, String action_value, String loginId);

	boolean ffpEmailTrigger(NmEmailPayloadTo ffpEmailPayloadTo, String loginId, String orgId);
	
}