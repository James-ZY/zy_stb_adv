package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdSellNumberDTO {
	
	@JsonProperty("advId")
	private String advId;//广告商ID
	
	@JsonProperty("advName")
	private String advName;//广告商名称

	@JsonProperty("total")
	private Integer total;//总的个数||总的时长
	
	@JsonProperty("buyNumber")
	private Integer buyNumber;//买的个数||购买的时长
	
	@JsonProperty("scale")
	private String scale;//占比

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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}
	
	
	

}
