package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionDTO {
	@JsonProperty("pointId")
	private String pointId;// 位置ID
	
	@JsonProperty("positionName")
	private String positionName;// 类型名称
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("status")
	private Integer status;// （0标清 1高清）
	
	@JsonProperty("beginPointX")
	private Integer beginPointX;
	
	@JsonProperty("beginPointY")
	private Integer beginPointY;
	
	@JsonProperty("endPointX")
	private Integer endPointX;
	
	@JsonProperty("endPointY")
	private Integer endPointY;

	@JsonProperty("velocity")
	private Integer velocity;// 移动速度

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getVelocity() {
		return velocity;
	}

	public void setVelocity(Integer velocity) {
		this.velocity = velocity;
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
	
	
	

}
