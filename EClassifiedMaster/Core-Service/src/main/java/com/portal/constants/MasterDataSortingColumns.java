package com.portal.constants;

public enum MasterDataSortingColumns {

	GDCLASSIFIEDTYPES("type"), GDCLASSIFIEDSUBCATEGORY("classified_subcategory"), GDCLASSIFIEDSCHEMES("erp_scheme"), GDCLASSIFIEDLANGUAGES(
			"language"), GDCLASSIFIEDEDITIONS("edition_name"), GDCLASSIFIEDADSSUBTYPES("ads_sub_type"), GDCLASSIFIEDADSTYPES(
					"ads_type"), GDCLASSIFIEDCATEGORY("classified_category"), STATE("state"), UMUSERS("first_name"), GDSETTINGSDEFINITIONS(
							"setting_group_name"), GDHELPMANUALTYPES("manual_type"),GDSTATES("state"),GDCITY("city"),GDPAYMENTGATEWAYCONFIG("provider"),
								GDCLASSIFIEDGROUP("classified_group"),GDCLASSIFIEDSUBGROUP("classified_sub_group"),
								GDCLASSIFIEDCHILDGROUP("classified_child_group"),BOOKINGUNITS("booking_location"),GDPAYMENTMODE("payment_mode"),GDSALESOFFICE("sales_office_code"),GDCLASSIFIEDDISTRICT("district_name"),GDCLASSIFIEDDIVISION("division_name"),
								GDEDITIONTYPE("edition_type"),GDRMSEDITIONS("edition_name"),GDRMSEDITIONTYPE("edition_type"),
								GDRMSFIXEDFORMATS("size"),GDRMSPAGEPOSITIONS("page_name"),GDRMSPOSITIONINGDISCOUNT("positioning_type"),
								GDRMSMULTIDISCOUNT("discount"),GDRMSSCHEMES("scheme"),GDPAYMENTMETHOD("payment_method"),GDCUSTOMERTYPES("customer_type"),GDRMSFORMATTYPES("format_type")
								,GDRMSGENERATEDBYOTHERS("generated_by"),GDDISCOUNTSMASTER("discount_name"),GDRMSDISCOUNTTYPES("discount_type"),GDRMSADSTYPE("add_type"),
								GDDAEDITIONTYPE("edition_name"),GDDACLASSIFIEDCATEGORY("category_type"),DASIZES("size"),DATEMPLATES("template_id"),DATEMPLATECONTENT("template_cnt_id"),GDDAEDITIONS("edition_name"),GDWFTYPES("wf_type");

	public final String master;

	private MasterDataSortingColumns(String master) {
		this.master = master;
	}

	public String getValue() {
		return master;
	}
}
