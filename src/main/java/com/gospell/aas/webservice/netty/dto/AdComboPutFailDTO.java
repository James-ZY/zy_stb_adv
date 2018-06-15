package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdComboPutFailDTO {
	
	@JsonProperty(value="comboId")
	private String comboId;//广告套Id
	
	@JsonProperty(value="clientId")
	private String clientId;//客户端ID
	
 

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	 
	
	

}
