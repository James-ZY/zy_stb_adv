package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboAdvtiserSellDTO {
	
	@JsonProperty("id")
	private String advId;//广告商ID
	
	
	@JsonProperty("count")
	private Integer sellDay;//当前广告商购买的天数

	@JsonProperty("name")
	private String name;//广告商名称

	public Integer getSellDay() {
		return sellDay;
	}

	public void setSellDay(Integer sellDay) {
		this.sellDay = sellDay;
	}

	public String getAdvId() {
		return advId;
	}

	public void setAdvId(String advId) {
		this.advId = advId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
