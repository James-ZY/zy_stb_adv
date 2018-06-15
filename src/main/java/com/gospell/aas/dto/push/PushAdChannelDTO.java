package com.gospell.aas.dto.push;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushAdChannelDTO {
	
	@JsonProperty("id")
	private String id;//频道ID
	
	@JsonProperty("name")
	private String name;//频道名称
	
	@JsonProperty("typeId")
	private String typeId;//对应的广告类型

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	 
	

}
