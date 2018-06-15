package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *服务器告知是否可以 删除广告发送器与频道无关的广告类型或者频道的java bean
 * @author Administrator
 *
 */
public class IsDelete {
	
	@JsonProperty(value="isDelete")
	private String isDelete;//广告发送器Id

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
 
	 
	 
	
}
