package com.gospell.aas.dto.adv;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdComboNotChannelSellDTO {
	
	@JsonProperty("sellId")
	private String sellId;//套餐ID
	
	@JsonProperty("comboId")
	private String comboId;//套餐ID
	
	@JsonProperty("startDate")
	private Date startDate;// 
	
	@JsonProperty("endDate")
	private Date endDate;//

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

	public String getSellId() {
		return sellId;
	}

	public void setSellId(String sellId) {
		this.sellId = sellId;
	}

	 

	
	

}
