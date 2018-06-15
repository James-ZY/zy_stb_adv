package com.gospell.aas.dto.adv;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdChannelForComboDTO {
	
	@JsonProperty("networkId")
	private String networkId;//发送器ID
	
	@JsonProperty("networkName")
	private String networkName;// 发送器名称
 
	
	@JsonProperty("channelId")
	private String channelId;// 频道Id
	
	@JsonProperty("channelName")
	private String channelName;// 频道名称
	
	@JsonProperty("channelType")
	private String channelType;// 频道类型
	
	@JsonProperty("comboId")
	private String comboId;// 广告套餐Id
	
	@JsonProperty("startHour")
	private Integer startHour;// 频道相关的套餐的开始的小时，比如早上1点

	@JsonProperty("startMinutes")
	private Integer startMinutes;// 频道相关的套餐的开始的分钟，比如早上5分

	@JsonProperty("startSecond")
	private Integer startSecond;// 频道相关的套餐的开始的秒，比如早上5秒

	@JsonProperty("endHour")
	private Integer endHour;// 频道相关的套餐的结束小时，比如早上1点

	@JsonProperty("endMinutes")
	private Integer endMinutes;// 频道相关的套餐结束分钟，比如早上5分

	@JsonProperty("endSecond")
	private Integer endSecond;// 频道相关的套餐的结束的秒，比如早上5秒
	
	@JsonProperty("validStartTime")
	private Date validStartTime;// 套餐生效开始时间

	@JsonProperty("validEndTime")
	private Date validEndTime;// 套餐生效结束时间
	
	@JsonProperty("typeId")
	private String typeId;

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

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getStartMinutes() {
		return startMinutes;
	}

	public void setStartMinutes(Integer startMinutes) {
		this.startMinutes = startMinutes;
	}

	public Integer getStartSecond() {
		return startSecond;
	}

	public void setStartSecond(Integer startSecond) {
		this.startSecond = startSecond;
	}

	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public Integer getEndMinutes() {
		return endMinutes;
	}

	public void setEndMinutes(Integer endMinutes) {
		this.endMinutes = endMinutes;
	}

	public Integer getEndSecond() {
		return endSecond;
	}

	public void setEndSecond(Integer endSecond) {
		this.endSecond = endSecond;
	}

	public Date getValidStartTime() {
		return validStartTime;
	}

	public void setValidStartTime(Date validStartTime) {
		this.validStartTime = validStartTime;
	}

	public Date getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

 
	
	

}
