package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 挂角广告
 * @author Administrator
 *
 */
public class CornerDTO {
	
	@JsonProperty("postionId")
	private String postionId;//位置Id（0左上角 1 左下角 2 右上角 3 右下角 4坐标）
	
	@JsonProperty("beginPointX")
	private Integer beginPointX;//x坐标
	
	@JsonProperty("beginPointY")
	private Integer beginPointY;//y坐标

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
