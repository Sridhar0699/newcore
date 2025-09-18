package com.portal.constants;

public enum MasterData {
	GDCLASSIFIEDTYPES("gd_classified_types"), GDCLASSIFIEDSUBCATEGORY("gd_classified_subcategory"), GDCLASSIFIEDSCHEMES(
			"gd_classified_schemes"), GDCLASSIFIEDLANGUAGES("gd_classified_languages"), GDCLASSIFIEDEDITIONS(
					"gd_classified_editions"), GDCLASSIFIEDADSSUBTYPES(
							"gd_classified_ads_sub_types"), GDCLASSIFIEDADSTYPES(
									"gd_classified_ads_types"), GDCLASSIFIEDCATEGORY("gd_classified_category"), STATE(
											"gd_state"), UMUSERS("um_users"), GDSETTINGSDEFINITIONS(
													"gd_settings_definitions"), GDHELPMANUALTYPES(
															"gd_help_manual_types"), GDSTATES("gd_state"), GDCITY("gd_city"),GDPAYMENTGATEWAYCONFIG("gd_payment_gateway_config"),
																GDCLASSIFIEDGROUP("gd_classified_group"),
																GDCLASSIFIEDSUBGROUP("gd_classified_sub_group"),
																GDCLASSIFIEDCHILDGROUP("gd_classified_child_group"),
																BOOKINGUNITS("booking_units"),GDPAYMENTMODE("gd_payment_mode"),GDSALESOFFICE("gd_sales_office"),GDCLASSIFIEDDISTRICT("gd_classified_district"),GDCLASSIFIEDDIVISION("gd_classified_division"),
																GDEDITIONTYPE("gd_edition_type"),GDRMSEDITIONS("gd_rms_editions"),GDRMSEDITIONTYPE("gd_rms_edition_type"),
																GDRMSFIXEDFORMATS("gd_rms_fixed_formats"),GDRMSPAGEPOSITIONS("gd_rms_page_positions"),GDRMSPOSITIONINGDISCOUNT("gd_rms_positioning_discount"),
																GDRMSMULTIDISCOUNT("gd_rms_multi_discount"),GDRMSSCHEMES("gd_rms_schemes"),GDPAYMENTMETHOD("gd_payment_method"),GDCUSTOMERTYPES("gd_customer_types"),GDRMSFORMATTYPES("gd_rms_format_types")
																,GDRMSGENERATEDBYOTHERS("gd_rms_generated_by_others"),GDDISCOUNTSMASTER("gd_discounts_master"),GDRMSDISCOUNTTYPES("gd_rms_discount_types"),GDRMSADSTYPE("gd_rms_ads_type"),
																GDRMSMULTIPLEDISCOUNTVAL("gd_rms_multiple_discount_val"),GDRMSANNEXURE("gd_rms_annexure"),RMSRATES("rms_rates"),
																GDDAEDITIONTYPE("gd_da_edition_type"),GDDACLASSIFIEDCATEGORY("gd_da_classified_category"),DASIZES("da_sizes"),DATEMPLATES("da_templates"),DATEMPLATECONTENT("da_template_content"),GDDAEDITIONS("gd_da_editions"),GDWFTYPES("gd_wf_types");

	public final String master;

	private MasterData(String master) {
		this.master = master;
	}
	
	public String getValue() {
		return master;
	}
}
