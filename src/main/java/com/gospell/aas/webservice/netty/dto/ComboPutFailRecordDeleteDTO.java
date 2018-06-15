package com.gospell.aas.webservice.netty.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
 

public class ComboPutFailRecordDeleteDTO {

	@JsonProperty(value = "clientId")
	private String clientId;

	@JsonProperty(value="dtoList")
	private List<AdComboDeleteDTO> dtoList;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<AdComboDeleteDTO> getDtoList() {
		return dtoList;
	}

	public void setDtoList(List<AdComboDeleteDTO> dtoList) {
		this.dtoList = dtoList;
	}
	
	
	

	 
}
