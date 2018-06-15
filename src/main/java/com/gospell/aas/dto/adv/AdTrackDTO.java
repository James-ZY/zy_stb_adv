package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdTrackDTO {

	@JsonProperty("id")
	private String id;//id

	@JsonProperty("trackName")
	private String trackName;//名称

	@JsonProperty("coordinates")
	private String coordinates;//坐标集合
	
	@JsonProperty("showTime")
	private Integer showTime;//总的显示时间

	@JsonProperty("bgWidth")
	private Integer bgWidth;//背景图片宽度

	@JsonProperty("bgHeight")
	private Integer bgHeight;//背景图片高度

	@JsonProperty("track")
	private String track;//轨迹详情

	@JsonProperty("flag")
	private Integer flag;//高清标清

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public Integer getBgWidth() {
		return bgWidth;
	}

	public void setBgWidth(Integer bgWidth) {
		this.bgWidth = bgWidth;
	}

	public Integer getBgHeight() {
		return bgHeight;
	}

	public void setBgHeight(Integer bgHeight) {
		this.bgHeight = bgHeight;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
