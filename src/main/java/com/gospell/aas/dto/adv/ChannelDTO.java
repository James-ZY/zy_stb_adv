package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelDTO {

	@JsonProperty("channelId")
	private String channelId;// 频道Id
	
	@JsonProperty("channelName")
	private String channelName;// 频道名称
	
	@JsonProperty("channelType")
	private String channelType;// 频道类型
	
	@JsonProperty("adTypeId")
	private String adTypeId;
	
	@JsonProperty("serviceId")
	private String serviceId;//通道ID
	
	@JsonProperty("serviceName")
	private String serviceName;//通道名称
	
	@JsonProperty("deleteFlag")
	private String deleteFlag;//是否删除
	
	@JsonProperty("isMainChannel")
	private String isMainChannel;//是否是主通道
	
	@JsonProperty("isVideoChannel")
	private String isVideoChannel;//是否是视频通道

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

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getAdTypeId() {
		return adTypeId;
	}

	public void setAdTypeId(String adTypeId) {
		this.adTypeId = adTypeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getIsMainChannel() {
		return isMainChannel;
	}

	public void setIsMainChannel(String isMainChannel) {
		this.isMainChannel = isMainChannel;
	}

	public String getIsVideoChannel() {
		return isVideoChannel;
	}

	public void setIsVideoChannel(String isVideoChannel) {
		this.isVideoChannel = isVideoChannel;
	}
	
	
}
