package com.gospell.aas.dto.adv;

 
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdcomboInfoDTO {
	
	@JsonProperty("id")
	private String id;//套餐ID
	
	@JsonProperty("startTime")
	private String startTime;//广告在每天播放的开始时刻（比如周一早上8点8分8秒)，数据库保存格式为08:00:08

	@JsonProperty("endTime")
	private String endTime;//广告在每天播放的结束时刻（比如周一早上8点8分10秒）

	@JsonProperty("showTime")
	private Integer showTime;//单次展示时间（插屏图片和滚动广告）

	@JsonProperty("intervalTime")
	private Integer intervalTime;//间隔时间（插屏图片和滚动广告）

	@JsonProperty("showCount")
	private Integer showCount;//展示次数（插屏图片和滚动广告）

	@JsonProperty("pictureInterval")
	private Integer pictureInterval;//每轮图片显示间隔时间（滚动广告）

	@JsonProperty("pictureTimes")
	private Integer pictureTimes;//每轮图片显示次数（滚动广告）


	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getShowCount() {
		return showCount;
	}

	public void setShowCount(Integer showCount) {
		this.showCount = showCount;
	}

	public Integer getPictureInterval() {
		return pictureInterval;
	}

	public void setPictureInterval(Integer pictureInterval) {
		this.pictureInterval = pictureInterval;
	}

	public Integer getPictureTimes() {
		return pictureTimes;
	}

	public void setPictureTimes(Integer pictureTimes) {
		this.pictureTimes = pictureTimes;
	}
}
