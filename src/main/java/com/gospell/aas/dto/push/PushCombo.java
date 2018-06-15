package com.gospell.aas.dto.push;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

public class PushCombo {

	@JsonProperty("clientId")
	private String clientId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public static void main(String[] args) {
		System.out.println(JsonMapper.toJsonString(new PushCombo()));
	}
	
}
