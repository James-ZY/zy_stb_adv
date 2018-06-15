package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChildAdTypeDTO {

	@JsonProperty("id")
	private String id;//子类型广告的ID
	
	@JsonProperty("typeName")
	private String typeName;//子类型广告的name

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
