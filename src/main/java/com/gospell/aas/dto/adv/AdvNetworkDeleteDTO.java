package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
 
 

public class AdvNetworkDeleteDTO {

	@JsonProperty(value = "clientId")
	private String clientId;

	@JsonProperty(value="dtoList")
	private List<AdvDeleteDTO> dtoList;
	
	@JsonProperty(value="status")
	private Integer status;
	
	 

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<AdvDeleteDTO> getDtoList() {
		return dtoList;
	}

	public void setDtoList(List<AdvDeleteDTO> dtoList) {
		this.dtoList = dtoList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
 

	 
}
