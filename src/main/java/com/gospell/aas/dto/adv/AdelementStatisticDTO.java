package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdelementStatisticDTO {

	@JsonProperty("playStartDate")
	private String playStartDate; // 广告播放开始时间(格式为yyyy-MM-dd HH:mm:ss)
	
	@JsonProperty("playEndDate")
	private String playEndDate; // 广告播放结束时间(格式为yyyy-MM-dd HH:mm:ss)
	
	@JsonProperty("duration")
	private Integer duration;//持续时间（保留字段）
	
	@JsonProperty("adId")
	private String adId;// 所属广告Id

	public String getPlayStartDate() {
		return playStartDate;
	}

	public void setPlayStartDate(String playStartDate) {
		this.playStartDate = playStartDate;
	}

	public String getPlayEndDate() {
		return playEndDate;
	}

	public void setPlayEndDate(String playEndDate) {
		this.playEndDate = playEndDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}
	
	
	
}
