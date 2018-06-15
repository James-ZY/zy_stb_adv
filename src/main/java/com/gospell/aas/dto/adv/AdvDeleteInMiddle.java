package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvDeleteInMiddle {
 
	
	@JsonProperty(value="clientId")
	private String clientId;//客户端ID
 
	
	@JsonProperty(value="status")
	private Integer status;


	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	 
	
	
	

}
