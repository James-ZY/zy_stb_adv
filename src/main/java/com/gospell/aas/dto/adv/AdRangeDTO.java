package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdRangeDTO {

	@JsonProperty("id")
	private String id;//最小x坐标

	@JsonProperty("rangeName")
	private String rangeName;//最小x坐标

	@JsonProperty("beginX")
	private Integer beginX;//最小x坐标
	
	@JsonProperty("beginY")
	private Integer beginY;//最小y坐标
	
	@JsonProperty("endX")
	private Integer endX;//最大X坐标
	
	@JsonProperty("endY")
	private Integer endY;//最大Y坐标
	
	@JsonProperty("flag")
	private Integer flag;//高清标清
	
	@JsonProperty("range")
	private String range;//位置
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}
	
	public Integer getBeginX() {
		return beginX;
	}

	public void setBeginX(Integer beginX) {
		this.beginX = beginX;
	}

	public Integer getBeginY() {
		return beginY;
	}

	public void setBeginY(Integer beginY) {
		this.beginY = beginY;
	}

	public Integer getEndX() {
		return endX;
	}

	public void setEndX(Integer endX) {
		this.endX = endX;
	}

	public Integer getEndY() {
		return endY;
	}

	public void setEndY(Integer endY) {
		this.endY = endY;
	}

	public String getRange() {
		return range;
	}
	
	public void setRange(String range) {
		this.range = range;
	}	

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
