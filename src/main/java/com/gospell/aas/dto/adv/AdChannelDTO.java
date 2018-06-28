package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdChannelDTO {

	@JsonProperty("networkName")
	private String networkName;// 发送器名称

	@JsonProperty("channelId")
	private String channelId;// 频道Id

	@JsonProperty("channelName")
	private String channelName;// 频道名称

	@JsonProperty("channelType")
	private String channelType;// 频道类型

	@JsonProperty("adchannelId")
	private String adchannelId;// 频道编号
	
	@JsonProperty("serviceId")
	private String serviceId;//通道ID
	
	@JsonProperty("serviceName")
	private String serviceName;//通道名称

	@JsonProperty("invalid")
	private Boolean invalid;// 是否有效

	@JsonProperty("adcomboUsedList")
	private List<AdcomboUsedDTO> adcomboUsedList;// 占用的套餐列表

	public AdChannelDTO() {
		this.invalid = true;
	}

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

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getAdchannelId() {
		return adchannelId;
	}

	public void setAdchannelId(String adchannelId) {
		this.adchannelId = adchannelId;
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
	
	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	public List<AdcomboUsedDTO> getAdcomboUsedList() {
		return adcomboUsedList;
	}

	public void setAdcomboUsedList(List<AdcomboUsedDTO> adcomboUsedList) {
		this.adcomboUsedList = adcomboUsedList;
	}

}
