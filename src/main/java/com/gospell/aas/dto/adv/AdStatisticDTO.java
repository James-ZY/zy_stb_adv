package com.gospell.aas.dto.adv;

import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.gospell.aas.common.mapper.JsonMapper;

public class AdStatisticDTO {

	@JsonProperty("stdSerialNumber")
	private String stdSerialNumber;// 机顶盒序列号
	
	@JsonProperty("smartcardId")
	private String smartcardId;// 智能卡ID
	
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
	
	@JsonProperty("list")
	private List<AdelementStatisticDTO> list;//广告对象

	public String getStdSerialNumber() {
		return stdSerialNumber;
	}

	public void setStdSerialNumber(String stdSerialNumber) {
		this.stdSerialNumber = stdSerialNumber;
	}

	public String getSmartcardId() {
		return smartcardId;
	}

	public void setSmartcardId(String smartcardId) {
		this.smartcardId = smartcardId;
	}
 
	public List<AdelementStatisticDTO> getList() {
		return list;
	}

	public void setList(List<AdelementStatisticDTO> list) {
		this.list = list;
	}

	public static void main(String[] args) throws InterruptedException {
 
		String s="0b240a806ff64ce5863f6c269ae28a79";
		List<AdelementStatisticDTO> i_list = Lists.newArrayList();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < 3; i++) {
			AdelementStatisticDTO dto = new AdelementStatisticDTO();
//			dto.setAdId(s);
//			dto.setDuration(5);
//			dto.setPlayStartDate(format.format(new Date()));
//			 
//			dto.setPlayEndDate(format.format(new Date()));
			
			i_list.add(dto);
		}
		AdStatisticDTO a = new AdStatisticDTO();
		a.setList(i_list);
		String s1 = JsonMapper.toJsonString(a);
		System.out.println(JsonMapper.toJsonString(a));
 
		AdStatisticDTO list1 = JsonMapper.getInstance().fromJson(s1, AdStatisticDTO.class);
		System.out.println(list1);
	}
	
}
