package com.gospell.aas.common.utils.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvertiserDTO {
	
	@JsonProperty(value="id")
	private  String id;//广告商ID
	
	@JsonProperty(value="name")
	private  String name;//广告商名

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
	
	

}
