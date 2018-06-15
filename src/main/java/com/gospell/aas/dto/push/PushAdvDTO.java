package com.gospell.aas.dto.push;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

public class PushAdvDTO {
	
	@JsonProperty("clientId")
	private String clientId;//发送器ID
	
	
	@JsonProperty("comboId")
	private String comboId;//套餐ID


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
	
	public static void main(String[] args) {
		System.out.println(JsonMapper.toJsonString(new PushAdvDTO()));
	}
}
