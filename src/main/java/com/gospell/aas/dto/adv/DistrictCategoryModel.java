package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistrictCategoryModel {
	@JsonProperty(value = "categoryId")
	private String categoryId;// 分类ID
	@JsonProperty(value = "categoryName")
	private String categoryName;// 分类名称
	@JsonProperty(value = "selfCategoryId")
	private String selfCategoryId;// 自定义分类名称

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getSelfCategoryId() {
		return selfCategoryId;
	}

	public void setSelfCategoryId(String selfCategoryId) {
		this.selfCategoryId = selfCategoryId;
	}
}
