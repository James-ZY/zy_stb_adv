package com.gospell.aas.webservice.netty.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.dto.adv.SelectChannelDTO;

/**
 * 删除广告发送器与频道无关的广告类型的java bean
 * @author Administrator
 *
 */
public class DeleteChannelDTO {
	
	@JsonProperty(value="isDelete")
	private String isDelete;//广告发送器Id

	@JsonProperty(value="channelList")
	private List<SelectChannelDTO> channelList;//频道ID

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public List<SelectChannelDTO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<SelectChannelDTO> channelList) {
		this.channelList = channelList;
	}
	
	
}
