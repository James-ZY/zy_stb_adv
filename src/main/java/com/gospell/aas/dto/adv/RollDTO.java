package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Administrator
 *
 */
public class RollDTO {

	@JsonProperty("postionId")
	private String postionId;//位置Id
	
	@JsonProperty("beginPointX")
	private Integer beginPointX;//x坐标
	
	@JsonProperty("beginPointY")
	private Integer beginPointY;//y坐标
	
	@JsonProperty("showTime")
	private Integer showTime;//单次展示时间（插屏图片和滚动广告）
	
	@JsonProperty("showCount")
	private Integer showCount;//展示次数（插屏图片和滚动广告）

	
	
	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public Integer getShowCount() {
		return showCount;
	}

	public void setShowCount(Integer showCount) {
		this.showCount = showCount;
	}

	public String getPostionId() {
		return postionId;
	}

	public void setPostionId(String postionId) {
		this.postionId = postionId;
	}

	public Integer getBeginPointX() {
		return beginPointX;
	}

	public void setBeginPointX(Integer beginPointX) {
		this.beginPointX = beginPointX;
	}

	public Integer getBeginPointY() {
		return beginPointY;
	}

	public void setBeginPointY(Integer beginPointY) {
		this.beginPointY = beginPointY;
	}
	
	
	
}
