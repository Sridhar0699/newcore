package com.portal.nm.model;

public class NmNotificationsPayload {
	private NmInboxPushPayload wfInboxPushPayload;
	private NmEmailPayload ffpEmailPayload;

	public NmInboxPushPayload getWfInboxPushPayload() {
		return wfInboxPushPayload;
	}

	public void setWfInboxPushPayload(NmInboxPushPayload wfInboxPushPayload) {
		this.wfInboxPushPayload = wfInboxPushPayload;
	}

	public NmEmailPayload getFfpEmailPayload() {
		return ffpEmailPayload;
	}

	public void setFfpEmailPayload(NmEmailPayload ffpEmailPayload) {
		this.ffpEmailPayload = ffpEmailPayload;
	}

}
