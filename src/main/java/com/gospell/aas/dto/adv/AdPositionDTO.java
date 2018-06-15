package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdPositionDTO{
	@JsonProperty("sdPosition")
	private List<PositionDTO> sdPosition;//标清坐标集合
	
	@JsonProperty("hdPosition")
	private List<PositionDTO> hdPosition;//高清坐标集合

	public List<PositionDTO> getSdPosition() {
		return sdPosition;
	}

	public void setSdPosition(List<PositionDTO> sdPosition) {
		this.sdPosition = sdPosition;
	}

	public List<PositionDTO> getHdPosition() {
		return hdPosition;
	}

	public void setHdPosition(List<PositionDTO> hdPosition) {
		this.hdPosition = hdPosition;
	}
	
	
	
	 
	

}
