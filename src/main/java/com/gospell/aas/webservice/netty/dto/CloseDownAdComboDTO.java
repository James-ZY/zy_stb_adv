package com.gospell.aas.webservice.netty.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
 
/**
 * 停播广告
 * @author Administrator
 *
 */
public class CloseDownAdComboDTO {

	@JsonProperty("clientId")
	private String clientId;
	
	@JsonProperty("adcomboIdList")
	private  List<AdComboDeleteDTO> adcomboIdList;//广告Id的集合

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<AdComboDeleteDTO> getAdcomboIdList() {
		return adcomboIdList;
	}

	public void setAdcomboIdList(List<AdComboDeleteDTO> adcomboIdList) {
		this.adcomboIdList = adcomboIdList;
	}

	 

	 

	 
	

}
