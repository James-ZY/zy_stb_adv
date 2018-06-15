package com.gospell.aas.dto.adv;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

public class SelectNetworkDTO {

	@JsonProperty("channelId")
	private List<SelectChannelDTO> channelList;// 频道Id
	
	@JsonProperty("networkId")
	private String networkId;//发送器ID
	
	@JsonProperty("typeId")
	private String typeId;
	
 
	 
	public String getNetworkId() {
		return networkId;
	}


	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}


	public List<SelectChannelDTO> getChannelList() {
		return channelList;
	}


	public void setChannelList(List<SelectChannelDTO> channelList) {
		this.channelList = channelList;
	}


	public String getTypeId() {
		return typeId;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


	public static void main(String[] args) {
		SelectChannelDTO d1 = new SelectChannelDTO();
		d1.setChannelId("1");
		SelectChannelDTO d2 = new SelectChannelDTO();
		d2.setChannelId("2");
		SelectNetworkDTO n =  new SelectNetworkDTO();
		List<SelectChannelDTO> l = new ArrayList<SelectChannelDTO>();
		l.add(d1);
		l.add(d2);
		n.setChannelList(l);
		n.setNetworkId("1");
		System.out.println(JsonMapper.toJsonString(n));
	}
	 
	
	
}
