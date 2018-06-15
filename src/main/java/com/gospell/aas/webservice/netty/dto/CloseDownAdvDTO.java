package com.gospell.aas.webservice.netty.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
 
/**
 * 停播广告
 * @author Administrator
 *
 */
public class CloseDownAdvDTO {

	@JsonProperty("clientIdList")
	private List<ClientIdDTO> clientIdList;//客户端Id的集合
	
	@JsonProperty("advDelete")
	private  AdvDeleteDTO advDelete;//广告Id的集合

	public List<ClientIdDTO> getClientIdList() {
		return clientIdList;
	}

	public void setClientIdList(List<ClientIdDTO> clientIdList) {
		this.clientIdList = clientIdList;
	}

	public AdvDeleteDTO getAdvDelete() {
		return advDelete;
	}

	public void setAdvDelete(AdvDeleteDTO advDelete) {
		this.advDelete = advDelete;
	}

	 
	

}
