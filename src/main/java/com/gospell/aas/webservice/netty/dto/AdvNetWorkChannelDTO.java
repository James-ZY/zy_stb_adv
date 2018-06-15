package com.gospell.aas.webservice.netty.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.dto.adv.ChannelDTO;

/**
 * 广告发送器添加频道
 * @author Administrator
 *
 */
public class AdvNetWorkChannelDTO {
	
	@JsonProperty(value="networkId")
	private String networkId;//广告Id
	
	@JsonProperty(value="channelList")
	private List<ChannelDTO> channelList;

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public List<ChannelDTO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<ChannelDTO> channelList) {
		this.channelList = channelList;
	}
	
	
 
	
	

}
