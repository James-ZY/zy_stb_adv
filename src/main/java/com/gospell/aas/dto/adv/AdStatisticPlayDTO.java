package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdStatisticPlayDTO {
	
	@JsonProperty("advId")
	private String advId;// 广告ID
 
	
	@JsonProperty("count")
	private String count;// 播放次数


	public String getAdvId() {
		return advId;
	}


	public void setAdvId(String advId) {
		this.advId = advId;
	}


	public String getCount() {
		return count;
	}


	public void setCount(String count) {
		this.count = count;
	}
	
 
	

}
