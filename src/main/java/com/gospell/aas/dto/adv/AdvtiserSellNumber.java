package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvtiserSellNumber {
	 
	@JsonProperty("advId")
	private String advId;//广告商ID
	
	@JsonProperty("advName")
	private String advName;//广告商名称
	
	@JsonProperty("count")
	private Integer count;//购买的个数||时长

	public String getAdvId() {
		return advId;
	}

	public void setAdvId(String advId) {
		this.advId = advId;
	}

	public String getAdvName() {
		return advName;
	}

	public void setAdvName(String advName) {
		this.advName = advName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}

