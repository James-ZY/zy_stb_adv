package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvNetWorkDTO {
	
	@JsonProperty(value="advId")
	private String advId;//广告Id
	
	@JsonProperty(value="clientId")
	private String clientId;//客户端ID
	
	@JsonProperty(value="id")
	private String id;

	public String getAdvId() {
		return advId;
	}

	public void setAdvId(String advId) {
		this.advId = advId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
