package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdComboByUserDTO {

	@JsonProperty(value="id")
	private String id;
	
	@JsonProperty(value="comboName")
	private String comboName;
	
	@JsonProperty(value="adComboId")
	private String adComboId;

	@JsonProperty(value="adType")
	private Integer adType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public String getAdComboId() {
		return adComboId;
	}

	public void setAdComboId(String adComboId) {
		this.adComboId = adComboId;
	}

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}
	
	
	
}
