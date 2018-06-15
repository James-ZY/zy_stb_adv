package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户返回给套餐修改或者添加页面使用
 * @author desheng
 *
 */
public class SelectAdNetworkDTO {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("networkName")
	private String networkName;
	
	@JsonProperty("invalid")
	private Boolean invalid;// 是否有效
	
 
 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}
	
	
}
