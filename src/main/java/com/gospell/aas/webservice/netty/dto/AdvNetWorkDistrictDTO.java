package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvNetWorkDistrictDTO {

	@JsonProperty("clientId")
	private String clientId;

	@JsonProperty(value="regionId")
	private String regionId;//区域Id
	
	@JsonProperty(value="regionName")
	private String regionName;//区域名称ID
	
	@JsonProperty(value="childrenRegionId")
	private String childrenRegionId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getChildrenRegionId() {
		return childrenRegionId;
	}

	public void setChildrenRegionId(String childrenRegionId) {
		this.childrenRegionId = childrenRegionId;
	}


}
