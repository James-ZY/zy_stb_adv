package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.gospell.aas.common.mapper.JsonMapper;

/**
 * 某广告套餐销售时间javabean
 * @author zhengdesheng
 *
 */
public class AdSellDateDTO {
	
 
 
	
	@JsonProperty("startDate")
	private String startDate;// 销售开始时间
	
	@JsonProperty("endDate")
	private String endDate;// 销售结束时间
 
	 
 

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	 public static void main(String[] args) {
		List<AdSellDateDTO> list = Lists.newArrayList();
		for (int i = 0; i < 3; i++) {
			AdSellDateDTO dto = new AdSellDateDTO();
			dto.setStartDate("2016-11-21");
			dto.setEndDate("2016-11-30");
			list.add(dto);
		}
		System.out.println(JsonMapper.toJsonString(list));
	}
	

}
