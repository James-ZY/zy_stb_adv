package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *服务器告知是否可以 删除广告发送器的者频道的java bean
 * @author Administrator
 *
 */
public class IsDeleteChannel {
	
	@JsonProperty(value="isDelete")
	private String isDelete;//是否可以删除
	
	@JsonProperty(value="channelId")
	private String channelId;//删除频道的ID

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
 
	 
	 
	
}
