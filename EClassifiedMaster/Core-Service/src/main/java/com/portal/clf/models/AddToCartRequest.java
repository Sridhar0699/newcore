package com.portal.clf.models;

import java.util.List;

import com.portal.da.models.DaTempleContentModel;

public class AddToCartRequest {

	private List<ClassifiedsOrderItemDetails> itemList;
	private CustomerDetails customerDetails;
	private PostClassifiedRates postClassifiedRates;
	private String itemId;
	private List<DaTempleContentModel> contentModel;
	
	private Integer classifiedType;

	public List<ClassifiedsOrderItemDetails> getItemList() {
		return itemList;
	}

	public void setItemList(List<ClassifiedsOrderItemDetails> itemList) {
		this.itemList = itemList;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public Integer getClassifiedType() {
		return classifiedType;
	}

	public void setClassifiedType(Integer classifiedType) {
		this.classifiedType = classifiedType;
	}

	public PostClassifiedRates getPostClassifiedRates() {
		return postClassifiedRates;
	}

	public void setPostClassifiedRates(PostClassifiedRates postClassifiedRates) {
		this.postClassifiedRates = postClassifiedRates;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public List<DaTempleContentModel> getContentModel() {
		return contentModel;
	}

	public void setContentModel(List<DaTempleContentModel> contentModel) {
		this.contentModel = contentModel;
	}

}
