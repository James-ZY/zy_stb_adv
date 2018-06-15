package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position{
	@JsonProperty("position")
	private List<PositionDTO> position;//坐标集合

	public List<PositionDTO> getPosition() {
		return position;
	}

	public void setPosition(List<PositionDTO> position) {
		this.position = position;
	}
	
 
	 
	
	
	
	 
	

}
