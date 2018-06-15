package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetWorkDTO {

	@JsonProperty("networkId")
	private String networkId;// 广告发送器ID

	@JsonProperty("networkName")
	private String networkName;// 广告发送器名称
	
	@JsonProperty("typeName")
	private String typeName;//类型名称

	@JsonProperty("channelList")
	private List<AdChannelDTO>  channelList;//频道

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public List<AdChannelDTO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<AdChannelDTO> channelList) {
		this.channelList = channelList;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	 
}
