package com.gospell.aas.dto.adv;

 
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdcomboSellDTO {
	
	@JsonProperty("id")
	private String id;//套餐ID
	
	@JsonProperty("comboName")
	private String comboName;//套餐名称
	
	@JsonProperty("startDate")
	private Date startDate;//查询的开始时间
	
	@JsonProperty("endDate")
	private Date endDate;//查询的结束时间
	
	@JsonProperty("totalDay")
	private Integer totalDay;//查询的总天数
	
	@JsonProperty("sellDay")
	private Integer sellDay;//销售的总天数
	
	@JsonProperty("typeName")
	private String typeName;//广告类型名称
	
	@JsonProperty("timeScale")
	private String timeScale;//占比

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

	public Integer getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(Integer totalDay) {
		this.totalDay = totalDay;
	}

	public Integer getSellDay() {
		return sellDay;
	}

	public void setSellDay(Integer sellDay) {
		this.sellDay = sellDay;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTimeScale() {
		return timeScale;
	}

	public void setTimeScale(String timeScale) {
		this.timeScale = timeScale;
	}
	
	

	
}
