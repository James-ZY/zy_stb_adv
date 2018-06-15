package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboCountDTO {

	@JsonProperty("id")
	private String adcomboId;// 套餐id

	@JsonProperty("comboName")
	private String comboName;// 套餐名字

	@JsonProperty("networkId")
	private String networkId;// 广告发送器id

	@JsonProperty("count")
	private Integer count;// 数量

	public String getAdcomboId() {
		return adcomboId;
	}

	public void setAdcomboId(String adcomboId) {
		this.adcomboId = adcomboId;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
