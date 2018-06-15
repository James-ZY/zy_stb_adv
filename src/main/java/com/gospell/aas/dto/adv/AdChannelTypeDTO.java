package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdChannelTypeDTO {
	
	@JsonProperty("networkId")
	private  String networkId;
	
	@JsonProperty("channelTypeList")
	private List<String> channelTypeList;

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public List<String> getChannelTypeList() {
		return channelTypeList;
	}

	public void setChannelTypeList(List<String> channelTypeList) {
		this.channelTypeList = channelTypeList;
	}
	
	
}
