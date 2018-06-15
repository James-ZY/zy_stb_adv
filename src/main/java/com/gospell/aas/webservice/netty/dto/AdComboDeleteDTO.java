package com.gospell.aas.webservice.netty.dto;

 

import com.fasterxml.jackson.annotation.JsonProperty;


public class AdComboDeleteDTO {

	@JsonProperty(value = "comboID")
	private String comboId;

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	 
}
