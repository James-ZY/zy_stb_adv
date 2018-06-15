package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 换台广告
 * @author Administrator
 *
 */
public class ChangeChannelDTO {
	
	@JsonProperty("postionId")
	private String postionId;//位置Id（0出现在节目的换台条上 1坐标）
	
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
