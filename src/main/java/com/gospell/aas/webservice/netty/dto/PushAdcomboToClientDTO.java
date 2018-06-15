package com.gospell.aas.webservice.netty.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 推送广告套餐
 * 
 * @author Administrator
 * 
 */
public class PushAdcomboToClientDTO {

	@JsonProperty("clientId")
	private String clientId;

	@JsonProperty("comboIdList")
	private List<String> comboIdList;//套餐ID
	@JsonProperty("pushList")
	private String pushList; // 当前客户端需要下传的所有广告套餐的集合,用字符串的原因主要是考虑到在netty server少定义逻辑处理

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<String> getComboIdList() {
		return comboIdList;
	}

	public void setComboIdList(List<String> comboIdList) {
		this.comboIdList = comboIdList;
	}

	public String getPushList() {
		return pushList;
	}

	public void setPushList(String pushList) {
		this.pushList = pushList;
	}

	 
}
