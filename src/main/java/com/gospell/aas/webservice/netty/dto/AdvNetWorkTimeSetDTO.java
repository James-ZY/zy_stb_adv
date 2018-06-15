package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 广告发送器添加视频码率
 * 
 * @author Administrator
 * 
 */
public class AdvNetWorkTimeSetDTO {

	@JsonProperty(value = "networkId")
	private String networkId;// 广告发送器Id

	@JsonProperty(value = "timeSet")
	private String timeSet;// 发送器获取方法间隔时间

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getTimeSet() {
		return timeSet;
	}

	public void setTimeSet(String timeSet) {
		this.timeSet = timeSet;
	}

}
