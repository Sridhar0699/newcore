package com.portal.constants;

public enum AllowedDisplayAdMasters {
	
	GDDAEDITIONS("Display Ad Editions"),GDDAEDITIONTYPE("Display Ad Edition Type");
	public final String master;
	private AllowedDisplayAdMasters(String master) {
		this.master = master;
	}
	
	public String getValue() {
		return master;
	}
}
