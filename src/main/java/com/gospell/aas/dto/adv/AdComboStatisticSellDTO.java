package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdComboStatisticSellDTO {
	
	@JsonProperty("comboId")
	private String comboId;//套餐ID
	
	@JsonProperty("sellDay")
	private Integer sellDay;//当前ID为comboId的套餐在查询的时间范围内一共销售了多少天

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public Integer getSellDay() {
		return sellDay;
	}

	public void setSellDay(Integer sellDay) {
		this.sellDay = sellDay;
	}
	
	
	

}
