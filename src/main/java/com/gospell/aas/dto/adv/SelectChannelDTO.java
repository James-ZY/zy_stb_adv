package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SelectChannelDTO {

	@JsonProperty("channelId")
	private String channelId;// 频道Id
	
	@JsonProperty("channelName")
	private String channelName;// 频道Id
	
 
	
 
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	 
	
	
}
