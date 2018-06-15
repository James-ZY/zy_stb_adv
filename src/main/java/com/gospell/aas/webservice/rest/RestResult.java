package com.gospell.aas.webservice.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

public class RestResult {
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("content")
	private String content;
 

	
	public RestResult() {
		super();
		this.status = "";
		this.message = "";
		this.content = "";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
 
	public static void main(String[] args) {
		RestResult result = new RestResult();
		System.out.println(JsonMapper.toJsonString(result));
	}
	
}
