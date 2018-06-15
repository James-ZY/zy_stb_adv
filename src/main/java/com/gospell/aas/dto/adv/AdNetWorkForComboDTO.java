package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdNetWorkForComboDTO {
	
	@JsonProperty("networkId")
	private String networkId;//发送器ID
	
	@JsonProperty("networkName")
	private String networkName;// 发送器名称
	
	@JsonProperty("comboId")
	private String comboId;// 广告套餐Id


	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

 
	
	

}
