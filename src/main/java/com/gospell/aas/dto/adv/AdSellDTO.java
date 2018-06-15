package com.gospell.aas.dto.adv;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdSellDTO {
	
	@JsonProperty("comboId")
	private String comboId;// 套餐ID
 
	
	@JsonProperty("startDate")
	private Date startDate;// 销售开始时间
	
	@JsonProperty("endDate")
	private Date endDate;// 销售结束时间
 
	@JsonProperty("isChannel")
	private Boolean isChannel;// 是否跟频道相关

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsChannel() {
		return isChannel;
	}

	public void setIsChannel(Boolean isChannel) {
		this.isChannel = isChannel;
	}
	
	

}
