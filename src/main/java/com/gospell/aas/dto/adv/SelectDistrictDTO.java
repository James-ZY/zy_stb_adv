package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SelectDistrictDTO {

	@JsonProperty("adDistrictCategorys")
	private List<DistrictCategoryModel> adDistrictCategorys;// 频道Id

	public List<DistrictCategoryModel> getAdDistrictCategorys() {
		return adDistrictCategorys;
	}

	public void setAdDistrictCategorys(List<DistrictCategoryModel> adDistrictCategorys) {
		this.adDistrictCategorys = adDistrictCategorys;
	}
	
}
