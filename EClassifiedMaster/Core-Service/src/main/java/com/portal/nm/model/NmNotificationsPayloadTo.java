package com.portal.nm.model;

public class NmNotificationsPayloadTo {
	private NmInboxTo wfInboxPushPayloadTo;
	private NmEmailPayloadTo ffpEmailPayloadTo;

	public NmInboxTo getWfInboxPushPayloadTo() {
		return wfInboxPushPayloadTo;
	}

	public void setWfInboxPushPayloadTo(NmInboxTo wfInboxPushPayloadTo) {
		this.wfInboxPushPayloadTo = wfInboxPushPayloadTo;
	}

	public NmEmailPayloadTo getFfpEmailPayloadTo() {
		return ffpEmailPayloadTo;
	}

	public void setFfpEmailPayloadTo(NmEmailPayloadTo ffpEmailPayloadTo) {
		this.ffpEmailPayloadTo = ffpEmailPayloadTo;
	}

}
