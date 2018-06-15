package com.gospell.aas.webservice.netty.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
 
/**
 * 推送单条广告
 * @author Administrator
 *
 */
public class PushSingelAdvDTO {

	@JsonProperty("clientId")
	private String clientId;
	
	@JsonProperty("advIdList")
	private List<String> advIdList;//当前的客户端对应的广告ID集合
	
	@JsonProperty("push")
	private String push; //当前客户端需要下传的所有广告的集合

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<String> getAdvIdList() {
		return advIdList;
	}

	public void setAdvIdList(List<String> advIdList) {
		this.advIdList = advIdList;
	}

	public String getPush() {
		return push;
	}

	public void setPush(String push) {
		this.push = push;
	}

 

	 

	 

	 
	 
	

}
