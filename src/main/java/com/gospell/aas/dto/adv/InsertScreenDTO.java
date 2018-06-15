package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 插屏广告
 * @author Administrator
 *
 */
public class InsertScreenDTO {

	@JsonProperty("postionId")
	private Integer postionId;//位置Id（0正中 1坐标）
	
	@JsonProperty("x")
	private Integer x;//出现的坐标x
	
	@JsonProperty("y")
	private Integer y;//结束的坐标y
	
	@JsonProperty("showTime")
	private Integer showTime;//单次展示时间（插屏图片和滚动广告）
	
	@JsonProperty("showCount")
	private Integer showCount;//展示次数（插屏图片和滚动广告）


	@JsonProperty("isFlag")
	private Integer isFlag;//退出方式(0其他 1轨迹)
	
	@JsonProperty("beginPointX")
	private Integer beginPointX;//图片退出轨迹开始坐标X
	
	@JsonProperty("beginPointY")
	private Integer beginPointY;//图片退出轨迹结束坐标Y
	
	@JsonProperty("endPointX")
	private Integer endPointX;//图片退出轨迹结束坐标Y
	
	@JsonProperty("endPointY")
	private Integer endPointY;//图片退出轨迹结束坐标Y
	
	@JsonProperty("velocity")
	private Integer  velocity;//图片退出移动速度

	public Integer getPostionId() {
		return postionId;
	}

	public void setPostionId(Integer postionId) {
		this.postionId = postionId;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Integer isFlag) {
		this.isFlag = isFlag;
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

	public Integer getEndPointX() {
		return endPointX;
	}

	public void setEndPointX(Integer endPointX) {
		this.endPointX = endPointX;
	}

	public Integer getEndPointY() {
		return endPointY;
	}

	public void setEndPointY(Integer endPointY) {
		this.endPointY = endPointY;
	}

	public Integer getVelocity() {
		return velocity;
	}

	public void setVelocity(Integer velocity) {
		this.velocity = velocity;
	}
	
	
	
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

	
	
}
