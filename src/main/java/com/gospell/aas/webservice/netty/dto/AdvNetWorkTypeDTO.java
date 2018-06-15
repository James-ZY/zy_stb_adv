package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 广告发送器与开机无关的广告类型的java bean
 * @author Administrator
 *
 */
public class AdvNetWorkTypeDTO {
	
	@JsonProperty(value="networkId")
	private String networkId;//广告发送器Id
	
	@JsonProperty(value="notChannelTypeId")
	private String notChannelTypeId;//广告类型ID

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getNotChannelTypeId() {
		return notChannelTypeId;
	}

	public void setNotChannelTypeId(String notChannelTypeId) {
		this.notChannelTypeId = notChannelTypeId;
	}
	
	 
	
}
