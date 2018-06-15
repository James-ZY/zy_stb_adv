package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 删除广告发送器与频道无关的广告类型的java bean
 * @author Administrator
 *
 */
public class DeleteNetWorkTypeDTO {
	
	@JsonProperty(value="networkId")
	private String networkId;//广告发送器Id
	
	@JsonProperty(value="typeId")
	private String typeId;//广告类型ID

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	 
	 
	
}
