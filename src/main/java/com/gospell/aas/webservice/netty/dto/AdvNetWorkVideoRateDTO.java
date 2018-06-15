package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 广告发送器添加视频码率
 * 
 * @author Administrator
 * 
 */
public class AdvNetWorkVideoRateDTO {

	@JsonProperty(value = "networkId")
	private String networkId;// 广告发送器Id

	@JsonProperty(value = "videoRate")
	private String videoRate;// 视频码率

	@JsonProperty(value = "pictureRate")
	private String pictureRate;// 图片码率
	
	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getVideoRate() {
		return videoRate;
	}

	public void setVideoRate(String videoRate) {
		this.videoRate = videoRate;
	}

	public String getPictureRate() {
		return pictureRate;
	}

	public void setPictureRate(String pictureRate) {
		this.pictureRate = pictureRate;
	}

}
