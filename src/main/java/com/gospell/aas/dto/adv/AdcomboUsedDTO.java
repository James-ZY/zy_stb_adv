package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdcomboUsedDTO {

	@JsonProperty("id")
	private String id;// 套餐ID

	@JsonProperty("comboName")
	private String comboName;// 套餐名称

	@JsonProperty("startTime")
	private String startTime;// 展示开始时间(每天)

	@JsonProperty("endTime")
	private String endTime;// 展示结束时间(每天)
	
	@JsonProperty("channelList")
	private List<String> channelList;// 频道列表

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
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

	public List<String> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<String> channelList) {
		this.channelList = channelList;
	}

}
