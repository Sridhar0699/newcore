package com.portal.constants;

public enum AllowedMasters {
	GDCLASSIFIEDTYPES("Classified Types"),GDCLASSIFIEDSUBCATEGORY("Classified Subcategory"),GDCLASSIFIEDSCHEMES("Classified Schemes"),GDCLASSIFIEDLANGUAGES("Classified Languages"),GDCLASSIFIEDEDITIONS("Classified Editions"),GDCLASSIFIEDADSSUBTYPES("Classified Ads SubTypes"),GDCLASSIFIEDADSTYPES("Classified Ads Types"),GDCLASSIFIEDCATEGORY("Classified Category"), GDHELPMANUALTYPES("Help Manual Types"),GDSTATES("States"),
	;
//GDCITY("City")
	public final String master;

	private AllowedMasters(String master) {
		this.master = master;
	}
	
	public String getValue() {
		return master;
	}
}
