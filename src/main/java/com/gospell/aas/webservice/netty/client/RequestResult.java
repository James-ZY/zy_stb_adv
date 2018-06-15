package com.gospell.aas.webservice.netty.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestResult {
	
	@JsonProperty("commandType")
	private String commandType;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("clientId")
	private String clientId;

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	

}
