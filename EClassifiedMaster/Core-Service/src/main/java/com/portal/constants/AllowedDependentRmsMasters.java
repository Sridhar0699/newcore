package com.portal.constants;

public enum AllowedDependentRmsMasters {
	
	GDRMSANNEXURE("Rms Annexure"),GDRMSEDITIONS("Rms Editions"),GDRMSMULTIPLEDISCOUNTVAL("Rms MultiplediscountVal"),
	GDRMSPOSITIONINGDISCOUNT("Rms Positioning Discount"),RMSRATES("RMS Rates"),RMSAPPROVALMATRIX("RMS Approval Matrix");
//	GDRMSSCHEMES("Rms Schemes"),GDRMSPAGEPOSITIONS("Rms Page Positions");
	public final String master;
	private AllowedDependentRmsMasters(String master) {
		this.master = master;
	}
	
	public String getValue() {
		return master;
	}
}
