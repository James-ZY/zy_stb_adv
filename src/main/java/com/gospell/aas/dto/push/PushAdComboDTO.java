package com.gospell.aas.dto.push;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.entity.adv.AdChannel;

public class PushAdComboDTO {
	
	@JsonProperty("id")
	private String id;//套餐ID

	@JsonProperty("comName")
	private String comName;//套餐名称

	@JsonProperty("isFlag")
	private Integer isFlag;//是否跟频道相关

	@JsonProperty("startTime")
	private String startTime;//每天播放开始时刻

	@JsonProperty("endTime")
	private String endTime;//每天播放的结束时刻

	@JsonProperty("showTime")
	private Integer showTime;//单次展示时间（插屏图片和滚动广告）
	
	@JsonProperty("intervalTime")
	private Integer intervalTime;//展示间隔时间（插屏图片和滚动广告）

	@JsonProperty("showCount")
	private Integer showCount;//展示次数（插屏图片和滚动广告）

	@JsonProperty("pictureIntervalTime")
	private Integer pictureIntervalTime ;//每轮图片显示间隔时间（滚动广告）

	@JsonProperty("pictureShowCount")

	private Integer pictureShowCount;// 每轮图片显示次数（滚动广告）

	@JsonProperty("week")
	private String week;// 日期规划

	@JsonProperty("adTypeId")
	private String adTypeId;//广告类型Id
	
	@JsonProperty("channelList")
	private List<AdChannel> channelList;//频道
	
	@JsonProperty("regionId")
	private String regionId;//自定义区域id

	@JsonProperty("sdCoordinates")
	private String sdCoordinates;//标清坐标集合

	@JsonProperty("sdCpShowTime")
	private String sdCpShowTime;//标清插屏总的显示时间

	@JsonProperty("hdCoordinates")
	private String hdCoordinates;//高清坐标集合

	@JsonProperty("hdCpShowTime")
	private String hdCpShowTime;//高清插屏总的显示时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public Integer getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Integer isFlag) {
		this.isFlag = isFlag;
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


	public Integer getPictureIntervalTime() {
		return pictureIntervalTime;
	}

	public void setPictureIntervalTime(Integer pictureIntervalTime) {
		this.pictureIntervalTime = pictureIntervalTime;
	}

	public Integer getPictureShowCount() {
		return pictureShowCount;
	}

	public void setPictureShowCount(Integer pictureShowCount) {
		this.pictureShowCount = pictureShowCount;
	}


	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getAdTypeId() {
		return adTypeId;
	}

	public void setAdTypeId(String adTypeId) {
		this.adTypeId = adTypeId;
	}

	public List<AdChannel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<AdChannel> channelList) {
		this.channelList = channelList;
	}
	
 
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSdCoordinates() {
		return sdCoordinates;
	}

	public void setSdCoordinates(String sdCoordinates) {
		this.sdCoordinates = sdCoordinates;
	}

	public String getSdCpShowTime() {
		return sdCpShowTime;
	}

	public void setSdCpShowTime(String sdCpShowTime) {
		this.sdCpShowTime = sdCpShowTime;
	}

	public String getHdCoordinates() {
		return hdCoordinates;
	}

	public void setHdCoordinates(String hdCoordinates) {
		this.hdCoordinates = hdCoordinates;
	}

	public String getHdCpShowTime() {
		return hdCpShowTime;
	}

	public void setHdCpShowTime(String hdCpShowTime) {
		this.hdCpShowTime = hdCpShowTime;
	}

	public static void main(String[] args) {
		List<PushAdComboDTO> result = new ArrayList<PushAdComboDTO>();
		PushAdComboDTO dto = new PushAdComboDTO();
		result.add(dto);
		System.out.println(JsonMapper.toJsonString(result));
	}
	 

 
	

}
